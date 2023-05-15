package com.undefined.backendforfrontend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalesReportDTO {

    ClientDTO client;
    ItemReportDTO ordersInfo;
}
