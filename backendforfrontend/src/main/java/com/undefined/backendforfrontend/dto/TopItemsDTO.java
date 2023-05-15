package com.undefined.backendforfrontend.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopItemsDTO {

    private Long productId;
    private Integer quantity;
    private String description;
    private BigDecimal price;
}
