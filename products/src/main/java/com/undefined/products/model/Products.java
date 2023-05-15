package com.undefined.products.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.undefined.products.dto.ProductsDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "Products")
public class Products {

    @Id
    private Long id;

    private String description;
    private Integer stock;
    private BigDecimal price;

    public static Products dtoToProducts(ProductsDTO productsDTO) {

        return Products.builder().id(productsDTO.getId()).description(productsDTO.getDescription())
                .stock(productsDTO.getStock()).price(productsDTO.getPrice()).build();
    }
}
