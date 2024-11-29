package com.pic.controller;

import com.pic.dto.user.UserInfoDto;
import com.pic.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/u")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserInfoDto> getCurrentUser() {
        UserInfoDto user = userService.getCurrentUser();

        return ResponseEntity.ok().body(user);
    }
}
