package com.undefined.backendforfrontend.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.undefined.backendforfrontend.dto.BoughtProductsDTO;
import com.undefined.backendforfrontend.dto.CardDTO;
import com.undefined.backendforfrontend.dto.ClientDTO;
import com.undefined.backendforfrontend.dto.ItemDTO;
import com.undefined.backendforfrontend.dto.OrderCreationDTO;
import com.undefined.backendforfrontend.dto.OrderDTO;
import com.undefined.backendforfrontend.dto.ProcessOrderDTO;
import com.undefined.backendforfrontend.dto.ProductDTO;
import com.undefined.backendforfrontend.dto.SalesReportDTO;
import com.undefined.backendforfrontend.exception.CreateOrderException;
import com.undefined.backendforfrontend.feignclients.CardsAppClient;
import com.undefined.backendforfrontend.feignclients.ClientsAppClient;
import com.undefined.backendforfrontend.feignclients.OrdersAppClient;
import com.undefined.backendforfrontend.feignclients.ProductsAppClient;
import com.undefined.backendforfrontend.utils.PaymentMethod;

@ExtendWith(MockitoExtension.class)
class BffServiceImplTest {

    @InjectMocks
    private BffServiceImpl service;

    @Mock
    private ClientsAppClient clientsService;

    @Mock
    private OrdersAppClient orderService;

    @Mock
    private ProductsAppClient productService;

    @Mock
    private CardsAppClient cardsService;

    SalesReportDTO salesReportDTO;
    ClientDTO clientDTO;
    OrderDTO orderDTO;
    List<OrderDTO> orderList;
    List<ProductDTO> productList;
    CardDTO cardInfo;
    List<ItemDTO> items;
    ProcessOrderDTO processOrderDTO;
    List<BoughtProductsDTO> boughtProductsDTO;
    Map<String, String> map;
    ProcessOrderDTO processOrderDTODiferentCard;

    @BeforeEach
    void setUp() {

        clientDTO = ClientDTO.builder().id(1L).dni("44").name("cano").lastName("como").address("Cicilia").build();

        orderList = new ArrayList<>();

        items = new ArrayList<>();

        items.add(ItemDTO.builder().productId(1L).quantity(12).build());

        orderDTO = OrderDTO.builder().totalAmount(new BigDecimal(123)).orderDate(new Date())
                .paymethod(PaymentMethod.TARJETA).items(items).build();

        orderList.add(orderDTO);

        productList = new ArrayList<>();

        productList.add(ProductDTO.builder().id(1L).description("cualca").price(new BigDecimal(123)).stock(3).build());

        cardInfo = CardDTO.builder().id("1").clientId("2").cardNumber("12341234").cardIssuer("Nose").build();

        salesReportDTO = SalesReportDTO.builder().client(clientDTO).ordersInfo(null).build();

        boughtProductsDTO = new ArrayList<>();

        boughtProductsDTO.add(BoughtProductsDTO.builder().id(1L).stock(4).build());

        processOrderDTO = ProcessOrderDTO.builder().clientId(1L).items(boughtProductsDTO)
                .paymethod(PaymentMethod.TARJETA).card(cardInfo).build();
        map = new HashMap<>();
        map.put("status", "UP");

        processOrderDTODiferentCard = ProcessOrderDTO.builder().clientId(1L).items(boughtProductsDTO)
                .paymethod(PaymentMethod.TARJETA).card(new CardDTO()).build();
    }

    @Test
    void givenClientId_thenReturnSalesReportDTO() {
        when(clientsService.getOneClient(1L)).thenReturn(clientDTO);
        when(orderService.getSalesByClientId(1L)).thenReturn(orderList);
        when(productService.findProductsByList(ArgumentMatchers.<Long>anyList())).thenReturn(productList);
        when(cardsService.getCardByClientId("1")).thenReturn(cardInfo);
        assertThat(service.generateReport("1")).isInstanceOf(SalesReportDTO.class);
    }

    @Test
    void returnAllProducts() {
        when(productService.getAllProducts()).thenReturn(productList);
        assertThat(service.getAllProducts()).isInstanceOf(productList.getClass());

    }

    @Test
    void createOrderOk() throws CreateOrderException {
        when(clientsService.getOneClient(1L)).thenReturn(clientDTO);
        when(cardsService.getCardByClientId("1")).thenReturn(cardInfo);
        when(productService.checkHealth()).thenReturn(map);
        when(orderService.checkHealth()).thenReturn(map);
        when(productService.updateProducts(ArgumentMatchers.<BoughtProductsDTO>anyList())).thenReturn(" ");
        when(productService.findProductsByList(ArgumentMatchers.<Long>anyList())).thenReturn(productList);
        when(orderService.registerOrder(ArgumentMatchers.any(OrderCreationDTO.class))).thenReturn(orderDTO);
        assertThat(service.createOrder(processOrderDTO)).isEqualTo("Order registered successful");
    }

    @Test
    void createOrderFaildCheckCard() throws CreateOrderException {
        when(clientsService.getOneClient(1L)).thenReturn(clientDTO);
        when(cardsService.getCardByClientId("1")).thenReturn(cardInfo);
        assertThatThrownBy(() -> service.createOrder(processOrderDTODiferentCard))
                .isInstanceOf(CreateOrderException.class);

    }

    @Test
    void createOrderFaildRegisterOrder() throws CreateOrderException {
        when(clientsService.getOneClient(1L)).thenReturn(clientDTO);
        when(cardsService.getCardByClientId("1")).thenReturn(cardInfo);
        when(productService.checkHealth()).thenReturn(map);
        when(orderService.checkHealth()).thenReturn(map);
        when(productService.updateProducts(ArgumentMatchers.<BoughtProductsDTO>anyList())).thenReturn(" ");
        when(productService.findProductsByList(ArgumentMatchers.<Long>anyList())).thenReturn(productList);
        when(orderService.registerOrder(ArgumentMatchers.any(OrderCreationDTO.class)))
                .thenThrow(CreateOrderException.class);
        when(productService.updateProducts(ArgumentMatchers.<BoughtProductsDTO>anyList())).thenReturn(" ");
        assertThatThrownBy(() -> service.createOrder(processOrderDTO)).isInstanceOf(CreateOrderException.class);

    }

}
