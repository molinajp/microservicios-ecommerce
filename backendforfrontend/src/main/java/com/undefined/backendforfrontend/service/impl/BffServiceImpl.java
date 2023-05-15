package com.undefined.backendforfrontend.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.undefined.backendforfrontend.dto.BoughtProductsDTO;
import com.undefined.backendforfrontend.dto.CardDTO;
import com.undefined.backendforfrontend.dto.ClientDTO;
import com.undefined.backendforfrontend.dto.ItemDTO;
import com.undefined.backendforfrontend.dto.ItemReportDTO;
import com.undefined.backendforfrontend.dto.OrderCreationDTO;
import com.undefined.backendforfrontend.dto.OrderDTO;
import com.undefined.backendforfrontend.dto.ProcessOrderDTO;
import com.undefined.backendforfrontend.dto.ProductDTO;
import com.undefined.backendforfrontend.dto.SalesReportDTO;
import com.undefined.backendforfrontend.dto.TopItemsDTO;
import com.undefined.backendforfrontend.exception.CreateOrderException;
import com.undefined.backendforfrontend.feignclients.CardsAppClient;
import com.undefined.backendforfrontend.feignclients.ClientsAppClient;
import com.undefined.backendforfrontend.feignclients.OrdersAppClient;
import com.undefined.backendforfrontend.feignclients.ProductsAppClient;
import com.undefined.backendforfrontend.service.BFFService;
import com.undefined.backendforfrontend.utils.PaymentMethod;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BffServiceImpl implements BFFService {

    private final ClientsAppClient clientsService;

    private final OrdersAppClient orderService;

    private final ProductsAppClient productService;

    private final CardsAppClient cardsService;

    public SalesReportDTO generateReport(String clientId) {

        ClientDTO validatedClientId = clientsService.getOneClient(Long.parseLong(clientId));

        // recupero la venta
        List<OrderDTO> sales = orderService.getSalesByClientId(validatedClientId.getId());

        List<ItemDTO> itemsForSale = new ArrayList<>();

        sales.forEach(sale -> itemsForSale.addAll(sale.getItems()));

        // recupero id de productos
        List<Long> productIds = itemsForSale.stream().map(item -> item.getProductId()).distinct()
                .collect(Collectors.toList());

        // recupero productos
        List<ProductDTO> products = productService.findProductsByList(productIds);

        boolean saleWithCard = sales.stream().anyMatch(order -> order.getPaymethod().equals(PaymentMethod.TARJETA));
        CardDTO cardInfo = null;
        if (saleWithCard) {
            cardInfo = cardsService.getCardByClientId(clientId);
            String cardNumber = cardInfo.getCardNumber().toString();
            cardInfo.setCardNumber(cardNumber.substring(cardNumber.length() - 4));
        }

        return SalesReportDTO.builder().client(validatedClientId)
                .ordersInfo(ItemReportDTO.builder().orders(sales).card(cardInfo).productDetails(products).build())
                .build();
    }

    @Override
    public ClientDTO getOneClient(Long clientId) {
        return clientsService.getOneClient(clientId);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @Override
    public String createOrder(ProcessOrderDTO processOrderDTO) throws CreateOrderException {

        clientsService.getOneClient(processOrderDTO.getClientId());

        checkCard(processOrderDTO);

        if (productService.checkHealth().get("status").equals("UP")
                && orderService.checkHealth().get("status").equals("UP")) {
            checkUpdateStock(processOrderDTO);

            List<ItemDTO> itemList = new ArrayList<>();

            BigDecimal totalAmount = getTotalAmount(processOrderDTO, itemList);

            log.info("MS Orders - TotalAmount is {}", totalAmount);

            OrderCreationDTO orderCreationDTO = OrderCreationDTO.builder().clientId(processOrderDTO.getClientId())
                    .paymethod(processOrderDTO.getPaymethod().name()).totalAmount(totalAmount).items(itemList).build();

            log.info("MS Orders - Request is {}", orderCreationDTO);

            try {
                orderService.registerOrder(orderCreationDTO);
            } catch (CreateOrderException e) {
                checkUpdateStockRollback(processOrderDTO);
                throw e;
            }

        } else {
            throw new CreateOrderException("Service Down");
        }
        return "Order registered successful";
    }

    public BigDecimal getTotalAmount(ProcessOrderDTO processOrderDTO, List<ItemDTO> itemList) {
        processOrderDTO.getItems().forEach(item -> itemList.add(new ItemDTO(item.getId(), item.getStock())));

        List<ProductDTO> productList = productService
                .findProductsByList(itemList.stream().map(ItemDTO::getProductId).collect(Collectors.toList()));

        productList.forEach(product -> itemList.forEach(item -> {
            if (item.getProductId() == product.getId()) {
                product.setStock(item.getQuantity());
            }
        }));

        return productList.stream().map(product -> product.getPrice().multiply(new BigDecimal(product.getStock())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void checkUpdateStock(ProcessOrderDTO processOrderDTO) {
        List<BoughtProductsDTO> boughtProductsList = processOrderDTO.getItems();

        boughtProductsList.forEach(e -> {
            if (e.getStock() <= 0) {
                throw new CreateOrderException("Stock must be more than 0");
            }
        });

        productService.updateProducts(boughtProductsList);
    }

    public void checkCard(ProcessOrderDTO processOrderDTO) {

        if (processOrderDTO.getPaymethod().equals(PaymentMethod.TARJETA)) {
            CardDTO cardDTO = cardsService.getCardByClientId(String.valueOf(processOrderDTO.getClientId()));

            if (!cardDTO.getCardNumber().equals(processOrderDTO.getCard().getCardNumber())) {

                throw new CreateOrderException("Card number is not present");
            }

        }
    }

    public void checkUpdateStockRollback(ProcessOrderDTO processOrderDTO) {
        List<BoughtProductsDTO> boughtProductsList = processOrderDTO.getItems();

        boughtProductsList.forEach(e -> e.setStock(e.getStock() * -1));

        productService.updateProducts(boughtProductsList);
    }

    @Override
    public List<TopItemsDTO> getTopItems(Integer limit) {
        List<Map<String, String>> listTopItems = orderService.getItems(limit);
        List<TopItemsDTO> newProductsList = new ArrayList<>();
        for (int i = 0; i < listTopItems.size(); i++) {
            Map<String, String> map = listTopItems.get(i);
            String productId = map.get("product_id");
            ProductDTO productDTO = productService.findByIdProduct(Long.parseLong(productId));
            newProductsList.add(new TopItemsDTO(productDTO.getId(), Integer.parseInt(map.get("quantity")),
                    productDTO.getDescription(), productDTO.getPrice()));
        }
        return newProductsList;
    }
}