package com.undefined.clients;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.undefined.clients.controller.ClientController;
import com.undefined.clients.dto.ClientDTO;
import com.undefined.clients.serviceimpl.ClientServiceImpl;

@ExtendWith(MockitoExtension.class)
class ControllerTest {
    
    @InjectMocks
    private ClientController controller;
    
    @Mock
    private ClientServiceImpl service;
    
    @Test
    void testGetOneClientOK() {
        when(service.getClientById(1L)).thenReturn(new ClientDTO());
        assertThat(controller.getOneClient(1L)).isInstanceOf(ResponseEntity.class);
    }
    
    @Test
    void testGetOneClientThrowsException() {
        when(service.getClientById(1L)).thenCallRealMethod();
        assertThatThrownBy(() -> controller.getOneClient(1L)).isInstanceOf(Exception.class);
    }
}
