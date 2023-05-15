package com.undefined.products.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionsDTO {

    private Date timestamp;

    private int code;

    private String details;

}
