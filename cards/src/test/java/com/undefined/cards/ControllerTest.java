package com.undefined.cards;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.undefined.cards.consumingwebservice.CardClient;
import com.undefined.cards.controller.CardController;
import com.undefined.cards.exception.CardNotPresentException;
import com.undefined.consumingwebservice.wsdl.Card;

@ExtendWith(MockitoExtension.class)
class ControllerTest {
    @InjectMocks // recibira como dependencias los mocks que indicamos como parametros
    private CardController cardController;

    @Mock // nos indica que vamos a usar un objeto simulado
    private CardClient service;

    @Test
    void testGetOneCard() throws CardNotPresentException {
        when(service.getCardFromSoap("1")).thenReturn(new Card());
        assertThat(cardController.getCardByClientId("1")).isInstanceOf(ResponseEntity.class);
    }

    @Test
    void testOneCardException() throws CardNotPresentException {
        when(service.getCardFromSoap("1")).thenCallRealMethod();
        assertThatThrownBy(() -> cardController.getCardByClientId("1")).isInstanceOf(Exception.class);
    }
}
