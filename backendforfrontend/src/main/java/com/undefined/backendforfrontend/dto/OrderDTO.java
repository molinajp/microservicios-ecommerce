package com.undefined.backendforfrontend.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.undefined.backendforfrontend.utils.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private BigDecimal totalAmount;
    private Date orderDate;
    private PaymentMethod paymethod;
    private List<ItemDTO> items;

}
