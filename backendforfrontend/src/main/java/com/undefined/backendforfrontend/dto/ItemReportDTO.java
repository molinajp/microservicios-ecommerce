package com.undefined.backendforfrontend.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemReportDTO {

    List<OrderDTO> orders;
    List<ProductDTO> productDetails;
    @JsonInclude(JsonInclude.Include.NON_NULL) 
    CardDTO card;
}
