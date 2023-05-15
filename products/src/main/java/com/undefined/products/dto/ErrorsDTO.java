package com.undefined.products.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorsDTO {
    private List<ExceptionsDTO> errors;

    public void addException(final ExceptionsDTO exception) {
        errors.add(exception);
    }
}
