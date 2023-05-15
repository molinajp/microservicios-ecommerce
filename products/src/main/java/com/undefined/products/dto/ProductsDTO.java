package com.undefined.products.dto;

import java.math.BigDecimal;

import com.undefined.products.model.Products;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductsDTO {

    private Long id;
    private String description;
    private Integer stock;
    private BigDecimal price;

    public static ProductsDTO productsToDTO(Products products) {
        return ProductsDTO.builder().id(products.getId()).description(products.getDescription())
                .stock(products.getStock()).price(products.getPrice()).build();
    }
}
