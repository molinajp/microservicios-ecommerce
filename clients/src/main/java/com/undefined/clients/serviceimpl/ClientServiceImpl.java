package com.undefined.clients.serviceimpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.undefined.clients.dto.ClientDTO;
import com.undefined.clients.service.ClientService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    
    @Value("${mock.url}")
    private String urlForRestService;
    
    private final WebClient webClient;
    
    @Override
    public ClientDTO getClientById(Long id) {
        return webClient.get().uri(urlForRestService + id).retrieve().bodyToMono(ClientDTO.class).block();
    }
}
