// CHECKSTYLE:OFF

package com.undefined.cards;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.undefined.cards.consumingwebservice.CardClient;
import com.undefined.cards.exception.CardNotPresentException;
import com.undefined.consumingwebservice.wsdl.Card;
import com.undefined.consumingwebservice.wsdl.CardInput;
import com.undefined.consumingwebservice.wsdl.Cards;

@ExtendWith(MockitoExtension.class)
class CardClientTest {

    @InjectMocks
    private CardClient cardClient;

    @Mock
    private WebServiceTemplate webServiceTemplate;

    private String url;
    private Cards cards;
    private Card card;

    @BeforeEach
    void setUp() {
        url = "algo";
        cardClient.setUrl(url);
        cards = Mockito.spy(Cards.class);
        card = new Card();
        var clientId = "1";
        card.setClientId(clientId);
    }

    @Test
    void getCardFromSoapOk() throws CardNotPresentException {
        when(webServiceTemplate.marshalSendAndReceive(ArgumentMatchers.<String>notNull(),
                ArgumentMatchers.any(CardInput.class))).thenReturn(cards);
        doReturn(List.of(card)).when(cards).getCard();
        assertThat(cardClient.getCardFromSoap("1")).isInstanceOf(Card.class);
    }

    @Test
    void getCardFromSoapException() throws CardNotPresentException {
        when(webServiceTemplate.marshalSendAndReceive(ArgumentMatchers.<String>notNull(),
                ArgumentMatchers.any(CardInput.class))).thenReturn(new Cards());
        assertThatThrownBy(() -> cardClient.getCardFromSoap("1")).isInstanceOf(CardNotPresentException.class);
    }

}
