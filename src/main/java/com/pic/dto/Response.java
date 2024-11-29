package com.pic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
@AllArgsConstructor
public class Response {
    private String message;
    private HttpStatus status;
}
