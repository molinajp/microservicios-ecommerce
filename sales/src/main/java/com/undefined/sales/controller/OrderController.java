package com.undefined.sales.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.undefined.sales.constant.LogConstant;
import com.undefined.sales.dto.OrderDTO;
import com.undefined.sales.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @CircuitBreaker(name = "salesCircuitBreaker")
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getSalesByClientId(@RequestParam Long clientId) {
        log.info("MS Orders - Looking for orders with client id {}", clientId);
        List<OrderDTO> orderResponse = orderService.getSalesByClientId(clientId);
        log.info(LogConstant.RESPONSE_IS, orderResponse);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @CircuitBreaker(name = "salesCircuitBreaker")
    @GetMapping("/top-items")
    public ResponseEntity<List<Map<Long, Integer>>> getItems(@RequestParam Integer limit) {
        log.info("MS Orders - Looking for top sold products {}", limit);
        List<Map<Long, Integer>> itemsRespoonse = orderService.getTopItems(limit);
        log.info(LogConstant.RESPONSE_IS, itemsRespoonse);
        return new ResponseEntity<>(itemsRespoonse, HttpStatus.OK);
    }

    @CircuitBreaker(name = "salesCircuitBreaker")
    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> registerOrder(@RequestBody OrderDTO orderDTO) {
        log.info("MS Orders - Request is {}", orderDTO);
        OrderDTO orderResponse = orderService.registerOrder(orderDTO);
        log.info(LogConstant.RESPONSE_IS, orderResponse);
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

}
