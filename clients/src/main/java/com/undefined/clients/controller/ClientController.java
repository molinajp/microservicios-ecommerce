package com.undefined.clients.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.undefined.clients.dto.ClientDTO;
import com.undefined.clients.service.ClientService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService clientService;
    
    @GetMapping("/clients/{id}")
    @CircuitBreaker(name = "clientsCircuitBreaker")
    @Retry(name = "clientsRetry")
    public ResponseEntity<ClientDTO> getOneClient(@PathVariable Long id) {
        log.info("MS Clients - Looking for client with id {}", id);
        ClientDTO client = clientService.getClientById(id);
        log.info("MS Clients - Response is {}", client);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }
}
