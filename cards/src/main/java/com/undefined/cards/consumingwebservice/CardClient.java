package com.undefined.cards.consumingwebservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import com.undefined.cards.exception.CardNotPresentException;
import com.undefined.consumingwebservice.wsdl.Card;
import com.undefined.consumingwebservice.wsdl.CardInput;
import com.undefined.consumingwebservice.wsdl.Cards;

import lombok.Setter;

@Setter
//Es nuestra comunicacion con el soap service actualmente dockerizado
public class CardClient extends WebServiceGatewaySupport {

    @Value("${url.mock}") // por atras spring va al .yaml a buscar el valor
    private String url;

    public Card getCardFromSoap(String clientId) throws CardNotPresentException {

        CardInput request = new CardInput();
        request.setClientId(clientId);

        Cards response = (Cards) getWebServiceTemplate().marshalSendAndReceive(url, request);
        var optional = response.getCard().stream().filter(cd -> cd.getClientId().equalsIgnoreCase(clientId))
                .findFirst();
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new CardNotPresentException("Card is not present in soap service");
        }
    }

}