package com.undefined.backendforfrontend.dto;

import java.util.List;

import com.undefined.backendforfrontend.utils.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessOrderDTO {
    private Long clientId;
    private List<BoughtProductsDTO> items;
    private PaymentMethod paymethod;
    private CardDTO card;

}
