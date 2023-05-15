package com.undefined.products.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.undefined.products.dto.BoughtProductsDTO;
import com.undefined.products.dto.ProductsDTO;
import com.undefined.products.exception.ProductExcepcion;
import com.undefined.products.service.impl.ProductsServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProductsControllerTest {

    @InjectMocks // recibira como dependencias los mocks que indicamos como parametros
    private ProductsController productsController;

    @Mock // nos indica que vamos a usar un objeto simulado
    private ProductsServiceImpl productsServiceImpl;

    private List<ProductsDTO> productList;
    private ProductsDTO product;
    private List<Long> listToProducts;
    private List<BoughtProductsDTO> boughtProductsDTO;

    @BeforeEach
    void setUp() {
        productList = new ArrayList<>();
        product = new ProductsDTO();
        listToProducts = new ArrayList<>();
        boughtProductsDTO = new ArrayList<>();
        boughtProductsDTO.add(BoughtProductsDTO.builder().id(1L).stock(1).build());
    }

    @Test
    void testFindAllProducts() {
        when(productsServiceImpl.findAllProducts()).thenReturn(productList);
        assertThat(productsController.findAllProducts()).isInstanceOf(ResponseEntity.class);
    }

    @Test
    void testUpdateProducts() throws ProductExcepcion {
        when(productsServiceImpl.updateProducts(boughtProductsDTO)).thenReturn("");
        assertThat(productsController.updateProducts(boughtProductsDTO)).isInstanceOf(ResponseEntity.class);

    }

    @Test
    void testFindByIdProduct() {
        when(productsServiceImpl.findByIdProduct(1L)).thenReturn(product);
        assertThat(productsController.findByIdProduct(1L)).isInstanceOf(ResponseEntity.class);
    }

    @Test
    void testFindByIdProductThrowsException() {
        when(productsServiceImpl.findByIdProduct(1L)).thenCallRealMethod();
        assertThatThrownBy(() -> productsController.findByIdProduct(1L)).isInstanceOf(Exception.class);
    }

    @Test
    void testFinProductsByList() {
        when(productsServiceImpl.findProductsByList(listToProducts)).thenReturn(productList);
        assertThat(productsController.finProductsByList(listToProducts)).isInstanceOf(ResponseEntity.class);
    }
}
