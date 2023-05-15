package com.undefined.backendforfrontend.exception;

public class CreateOrderException extends RuntimeException {

    private static final long serialVersionUID = 1533492029394631108L;

    public CreateOrderException(String message) {
        super(message);
    }
}
