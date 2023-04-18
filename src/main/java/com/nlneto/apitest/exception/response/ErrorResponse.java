package com.nlneto.apitest.exception.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private final Date date;
    private final String message;
    private final String description;
}
