package com.undefined.backendforfrontend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardDTO {

    private String id;
    private String clientId;
    private String cardNumber;
    private String cardIssuer;
}
