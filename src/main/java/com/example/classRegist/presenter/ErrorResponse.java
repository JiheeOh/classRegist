package com.example.classRegist.presenter;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
        HttpStatus code,
        String message
) {
}
