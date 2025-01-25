package com.supportsystem.application.response.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ErrorDetailsDTO {
    private Date timestamp;
    private String message;
    private String details;
}
