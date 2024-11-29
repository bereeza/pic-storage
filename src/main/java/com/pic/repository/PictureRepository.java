package com.pic.repository;

import com.pic.entity.Picture;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PictureRepository extends MongoRepository<Picture, String> {
}
