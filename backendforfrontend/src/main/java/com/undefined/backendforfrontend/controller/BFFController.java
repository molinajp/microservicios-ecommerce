package com.undefined.backendforfrontend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.undefined.backendforfrontend.dto.ClientDTO;
import com.undefined.backendforfrontend.dto.ProcessOrderDTO;
import com.undefined.backendforfrontend.dto.ProductDTO;
import com.undefined.backendforfrontend.dto.SalesReportDTO;
import com.undefined.backendforfrontend.dto.TopItemsDTO;
import com.undefined.backendforfrontend.service.BFFService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BFFController {

    private final BFFService bffService;

    @GetMapping("/order-reports")
    public ResponseEntity<SalesReportDTO> getSalesByClient(@RequestParam String clientId) {
        log.info("BFF-OrderReport - Client Id: {}", clientId);
        SalesReportDTO orderResponse = bffService.generateReport(clientId);
        log.info("BFF-OrderReport - Response Ok: {}", orderResponse);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        log.info("BFF-ClientById - Request info for client with id/dni: {}", id);
        ClientDTO client = bffService.getOneClient(id);
        log.info("BFF-ClientById - Response for client with id/dni: {}", client);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = bffService.getAllProducts();
        log.info("BFF-Products - Response is Products: {}", products);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/orders")
    public ResponseEntity<String> registerOrder(@RequestBody ProcessOrderDTO processOrderDto) {
        log.info("BFF Orders - Request is {}", processOrderDto);
        var response = bffService.createOrder(processOrderDto);
        log.info("BFF Orders - Response is {}", response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/top-items")
    public ResponseEntity<List<TopItemsDTO>> getItems(@RequestParam Integer limit) {
        log.info("MS Orders - Looking for top sold products {}", limit);
        List<TopItemsDTO> itemsRespoonse = bffService.getTopItems(limit);
        log.info("MS Orders - Response is {}", itemsRespoonse);
        return new ResponseEntity<>(itemsRespoonse, HttpStatus.OK);
    }
}
