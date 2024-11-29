package com.pic.dto.picture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PictureSaveDto {
    private String author;
    private String description;
}
