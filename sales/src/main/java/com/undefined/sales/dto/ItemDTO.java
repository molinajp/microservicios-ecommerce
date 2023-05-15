package com.undefined.sales.dto;

import com.undefined.sales.model.ItemEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    private Long productId;
    private Integer quantity;

    public static ItemDTO itemToDto(ItemEntity itemEntity) {
        return ItemDTO.builder().productId(itemEntity.getProductId()).quantity(itemEntity.getQuantity()).build();
    }
}
