package com.undefined.clients;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.undefined.clients.dto.ClientDTO;
import com.undefined.clients.serviceimpl.ClientServiceImpl;

import reactor.core.publisher.Mono;


@ExtendWith(MockitoExtension.class)
class ServiceImplTest {
    
    @InjectMocks
    private ClientServiceImpl clientService;
    
    @Mock
    private WebClient webClient;
    
    private ClientDTO clientDto;
    
    @BeforeEach
    void setUp() {
        clientDto = ClientDTO.builder().id(1L).dni("123").name("Pedro").lastName("Perez").address("Calle 123").build();
    }
    
    @Test
    void testGetClientByIdOK() {
        final var uriSpecMock = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        final var headersSpecMock = Mockito.mock(WebClient.RequestHeadersSpec.class);
        final var responseSpecMock = Mockito.mock(WebClient.ResponseSpec.class);

        when(webClient.get()).thenReturn(uriSpecMock);
        when(uriSpecMock.uri(ArgumentMatchers.<String>notNull())).thenReturn(headersSpecMock);
        when(headersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(ArgumentMatchers.<Class<ClientDTO>>notNull())).thenReturn(Mono.just(clientDto));
        assertThat(clientService.getClientById(1L)).isEqualTo(clientDto);
    }
    
    @Test
    void testGetClientByIdThrowsException() {
        final var uriSpecMock = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        final var headersSpecMock = Mockito.mock(WebClient.RequestHeadersSpec.class);
        final var responseSpecMock = Mockito.mock(WebClient.ResponseSpec.class);

        when(webClient.get()).thenReturn(uriSpecMock);
        when(uriSpecMock.uri(ArgumentMatchers.<String>notNull())).thenReturn(headersSpecMock);
        when(headersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(ArgumentMatchers.<Class<String>>notNull())).thenReturn(Mono.just(""));
        assertThatThrownBy(() -> clientService.getClientById(1L)).isInstanceOf(Exception.class);
    }
}

