package com.pic.service;

import com.pic.dto.picture.PictureInfoDto;
import com.pic.dto.picture.PictureSaveDto;
import com.pic.entity.Picture;
import com.pic.repository.PictureRepository;
import com.pic.service.gcp_bucket.BucketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PictureService {
    private final PictureRepository pictureRepository;
    private final BucketService bucketService;

    public PictureInfoDto save(PictureSaveDto picInfo, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        try {
            bucketService.save(file);
            String fileUrl = String.format("https://storage.googleapis.com/pic-storage-bucket/%s", file.getOriginalFilename());

            Picture picture = Picture.builder()
                    .author(picInfo.getAuthor())
                    .description(picInfo.getDescription())
                    .url(fileUrl)
                    .build();

            picture = pictureRepository.save(picture);
            return buildPictureInfo(picture);
        } catch (Exception e) {
            log.error("Failed to save picture", e);
            throw new RuntimeException("Failed to save picture", e);
        }
    }

    public PictureInfoDto getPictureById(String id) {
        Optional<Picture> picture = pictureRepository.findById(id);
        if (picture.isEmpty()) {
            throw new IllegalArgumentException("Picture post not found");
        }

        return buildPictureInfo(picture.get());
    }

    public void dropPictureById(String id) {
        Optional<Picture> picture = pictureRepository.findById(id);
        if (picture.isEmpty()) {
            throw new IllegalArgumentException("Picture not found.");
        }

        bucketService.delete(picture.get().getId());
        pictureRepository.delete(picture.get());
    }

    private PictureInfoDto buildPictureInfo(Picture picture) {
        return PictureInfoDto.builder()
                .id(picture.getId())
                .author(picture.getAuthor())
                .description(picture.getDescription())
                .url(picture.getUrl())
                .build();
    }
}
