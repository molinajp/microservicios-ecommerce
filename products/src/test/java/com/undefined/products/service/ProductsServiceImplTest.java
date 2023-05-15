package com.undefined.products.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.undefined.products.dto.BoughtProductsDTO;
import com.undefined.products.dto.ProductsDTO;
import com.undefined.products.exception.ProductExcepcion;
import com.undefined.products.model.Products;
import com.undefined.products.repository.ProductsRepository;
import com.undefined.products.service.impl.ProductsServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProductsServiceImplTest {

    @InjectMocks
    private ProductsServiceImpl productsServiceImpl;

    @Mock
    private ProductsRepository productsRepository;

    List<ProductsDTO> productList;
    ProductsDTO productDTO;
    Products product;
    Products productFailStock;
    Optional<ProductsDTO> optional;
    List<Long> listToProducts;
    List<BoughtProductsDTO> boughtProductsDTO;

    @BeforeEach
    void setUp() {
        product = Products.builder().id(1L).description("Prod").price(new BigDecimal(123)).stock(3).build();
        productFailStock = Products.builder().id(1L).description("Prod").price(new BigDecimal(123)).stock(1).build();
        productList = new ArrayList<>();
        listToProducts = new ArrayList<>();
        productDTO = new ProductsDTO();
        listToProducts.add(1L);
        boughtProductsDTO = new ArrayList<>();
        boughtProductsDTO.add(BoughtProductsDTO.builder().id(1L).stock(2).build());
        productList.add(ProductsDTO.builder().id(1L).description("Prod").price(new BigDecimal(123)).stock(3).build());

    }

    @Test
    void findAllProducts() {
        when(productsServiceImpl.findAllProducts()).thenReturn(productList);
        assertThat(productsRepository.findAll()).isInstanceOf(productList.getClass());

    }

    @Test
    void updateProducts() throws ProductExcepcion {
        when(productsRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(product));
        assertThat(productsServiceImpl.updateProducts(boughtProductsDTO)).isInstanceOf(String.class);

    }

    @Test
    void updateProductsFailStock() throws ProductExcepcion {
        when(productsRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(productFailStock));
        assertThatThrownBy(() -> productsServiceImpl.updateProducts(boughtProductsDTO))
                .isInstanceOf(ProductExcepcion.class);
    }

    @Test
    void findByIdProduct() {
        when(productsRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(product));
        assertThat(productsServiceImpl.findByIdProduct(1L)).isInstanceOf(productDTO.getClass());
    }

    @Test
    void findProductsByList() {
        when(productsRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(product));
        assertThat(productsServiceImpl.findProductsByList(listToProducts)).isInstanceOf(productList.getClass());

    }
}
