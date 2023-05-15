package com.undefined.sales.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.undefined.sales.model.OrderEntity;
import com.undefined.sales.utils.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long clientId;
    private Date orderDate;
    private PaymentMethod paymethod;
    private BigDecimal totalAmount;
    private List<ItemDTO> items;

    public static OrderDTO orderToDto(OrderEntity orderEntity) {

        return OrderDTO.builder().clientId(orderEntity.getClientId())
                .items(orderEntity.getItem().stream().map(ItemDTO::itemToDto).collect(Collectors.toList()))
                .orderDate(orderEntity.getOrderDate()).paymethod(orderEntity.getPaymethod())
                .totalAmount(orderEntity.getTotalAmount()).build();

    }

}
