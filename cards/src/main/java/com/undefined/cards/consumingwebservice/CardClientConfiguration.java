package com.undefined.cards.consumingwebservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class CardClientConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller(); // transforma o parcea xml en objetos java y viceversa
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("com.undefined.consumingwebservice.wsdl");
        return marshaller;
    }

    @Bean
    public CardClient card(Jaxb2Marshaller marshaller) {
        CardClient card = new CardClient();
        card.setDefaultUri("http://localhost:8088");
        card.setMarshaller(marshaller);
        card.setUnmarshaller(marshaller);
        return card;
    }

}