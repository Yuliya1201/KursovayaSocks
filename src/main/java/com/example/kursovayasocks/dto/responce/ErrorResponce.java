package com.example.kursovayasocks.dto.responce;


import lombok.Data;

@Data
public class ErrorResponse {
    private final int status;
    private final String error;
    private final String message;
}

