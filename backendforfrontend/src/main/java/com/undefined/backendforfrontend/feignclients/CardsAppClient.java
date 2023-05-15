package com.undefined.backendforfrontend.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.undefined.backendforfrontend.dto.CardDTO;

@FeignClient(name = "cards-service", path = "${cards-service.base-path}")
public interface CardsAppClient {

    @GetMapping(value = "/cards", produces = MediaType.APPLICATION_JSON_VALUE)
    public CardDTO getCardByClientId(@RequestParam String clientId);
}