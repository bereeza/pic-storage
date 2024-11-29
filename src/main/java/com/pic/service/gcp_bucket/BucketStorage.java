package com.pic.service.gcp_bucket;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;

public final class BucketStorage {
    private static BucketStorage BUCKET_INSTANCE;

    @Value("${gcp.project-id}")
    private static String projectId;

    private BucketStorage() {}

    public static Storage getInstance() {
        if (BUCKET_INSTANCE == null) {
            BUCKET_INSTANCE = new BucketStorage();
        }

        return StorageOptions.newBuilder()
                .setProjectId(projectId)
                .build().getService();
    }
}
