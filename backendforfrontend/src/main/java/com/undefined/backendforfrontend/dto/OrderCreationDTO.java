package com.undefined.backendforfrontend.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreationDTO {

    private Long clientId;
    private BigDecimal totalAmount;
    private Date orderDate;
    private String paymethod;
    private List<ItemDTO> items;
    
}

