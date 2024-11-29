package com.pic.service.gcp_bucket;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class BucketService {

    @Value("${gcp.project-bucket}")
    private String projectBucket;

    public BlobId save(MultipartFile file) {
        Storage storage = BucketStorage.getInstance();
        BlobId id = BlobId.of(projectBucket, file.getOriginalFilename());
        BlobInfo info = BlobInfo.newBuilder(id).build();

        try {
            storage.create(info, file.getBytes());
            return id;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public byte[] getFile(String file) {
        Storage storage = BucketStorage.getInstance();
        BlobId id = BlobId.of(projectBucket, file);
        Blob blob = storage.get(id);

        if (blob == null) {
            throw new IllegalArgumentException("File not find");
        }

        return blob.getContent();
    }

    public void delete(String pictureUrl) {
        Storage storage = BucketStorage.getInstance();
        BlobId id = BlobId.of(projectBucket, pictureUrl);
        storage.delete(id);
    }
}
