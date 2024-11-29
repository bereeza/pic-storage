package com.pic.controller;

import com.pic.dto.Response;
import com.pic.dto.picture.PictureSaveDto;
import com.pic.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/pic")
@RequiredArgsConstructor
public class PictureController {
    private final PictureService pictureService;

    @PostMapping
    public ResponseEntity<Response> savePicturePost(
            @RequestParam("author") String author,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file) {

        PictureSaveDto picture = PictureSaveDto.builder()
                .author(author)
                .description(description)
                .build();

        pictureService.save(picture, file);

        return ResponseEntity.ok()
                .body(Response.builder()
                        .status(HttpStatus.OK)
                        .message("Picture post saved")
                        .build());
    }
}
