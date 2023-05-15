package com.undefined.cards.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorsDto {

    private List<ExceptionDto> errors;

    public void addException(ExceptionDto exception) {
        errors.add(exception);
    }
}