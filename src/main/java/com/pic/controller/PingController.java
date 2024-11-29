package com.pic.controller;

import com.pic.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PingController {

    @GetMapping
    public ResponseEntity<Response> ping() {
        return ResponseEntity.ok().body(
                Response.builder()
                        .message("Pong")
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @GetMapping("/secured")
    public ResponseEntity<Response> secured() {
        return ResponseEntity.ok().body(
                Response.builder()
                        .message("Secured")
                        .status(HttpStatus.OK)
                        .build()
        );
    }
}
