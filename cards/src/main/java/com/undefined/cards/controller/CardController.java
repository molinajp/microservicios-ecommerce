package com.undefined.cards.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.undefined.cards.consumingwebservice.CardClient;
import com.undefined.cards.exception.CardNotPresentException;
import com.undefined.consumingwebservice.wsdl.Card;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CardController {
    private final CardClient cardClient; // autowired

    @Retry(name = "cardsRetry")
    @GetMapping(value = "/cards", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Card> getCardByClientId(@RequestParam String clientId) throws CardNotPresentException {
        // como el id puede cambiar le digo q lo recupere y lo guarde en string id para
        // poder manipularlo
        log.info("MS Cards - Looking for cards with id {}", clientId);
        Card card = cardClient.getCardFromSoap(clientId);
        log.info("MS Cards - Response is card: id: {}, clientId: {}, cardNumber: {} cardIssuer: {}", card.getId(),
                card.getClientId(), card.getCardNumber(), card.getCardIssuer());
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

}