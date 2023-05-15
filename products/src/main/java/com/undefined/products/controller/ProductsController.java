package com.undefined.products.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.undefined.products.dto.BoughtProductsDTO;
import com.undefined.products.dto.ProductsDTO;
import com.undefined.products.exception.ProductExcepcion;
import com.undefined.products.service.ProductsService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductsController {

    private final ProductsService productsService;

    @CircuitBreaker(name = "productsCircuitBreaker")
    @GetMapping("/products")
    public ResponseEntity<List<ProductsDTO>> findAllProducts() {
        log.info("MS Products - Request to get all Products");
        return new ResponseEntity<>(productsService.findAllProducts(), HttpStatus.OK);
    }

    @CircuitBreaker(name = "productsCircuitBreaker")
    @PutMapping("/bought-products")
    public ResponseEntity<String> updateProducts(@RequestBody List<BoughtProductsDTO> boughtProducts)
            throws ProductExcepcion {
        log.info("MS Products - Request to update Bought Products");
        return new ResponseEntity<>(productsService.updateProducts(boughtProducts), HttpStatus.OK);
    }

    @CircuitBreaker(name = "productsCircuitBreaker")
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductsDTO> findByIdProduct(@PathVariable Long id) {
        log.info("Request to get Product : {}", id);
        ProductsDTO product = productsService.findByIdProduct(id);
        log.info("MS Products - Response is {}", product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @CircuitBreaker(name = "productsCircuitBreaker")
    @GetMapping("/list-products")
    public ResponseEntity<List<ProductsDTO>> finProductsByList(@RequestParam List<Long> listToProducts) {
        log.info("MS Products - Request get products by list: {}", listToProducts);
        return new ResponseEntity<>(productsService.findProductsByList(listToProducts), HttpStatus.OK);
    }
}
