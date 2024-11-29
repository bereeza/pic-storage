package com.pic.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int code;
    private String message;
}
