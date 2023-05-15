package com.undefined.backendforfrontend.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.undefined.backendforfrontend.dto.ClientDTO;


@FeignClient(name = "clients-service", path = "${clients-service.base-path}")
public interface ClientsAppClient {

    @GetMapping("/clients/{id}")
    public ClientDTO getOneClient(@PathVariable Long id);
    
}
