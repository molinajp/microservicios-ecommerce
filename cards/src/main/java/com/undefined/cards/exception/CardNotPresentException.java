package com.undefined.cards.exception;

public class CardNotPresentException extends Exception {

    private static final long serialVersionUID = 1533492029394631108L;

    public CardNotPresentException(String message) {
        super(message);
    }

}
