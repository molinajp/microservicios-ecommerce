package com.undefined.sales.exceptions;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderException extends RuntimeException {

    private static final long serialVersionUID = -3534520956070708260L;
    private final Date timestamp;
    private final Integer status;
    private final String detail;
}
