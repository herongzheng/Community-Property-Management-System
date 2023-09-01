package com.laioffer.communitymanagement.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.laioffer.communitymanagement.exception.AmazonS3UploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@Service
public class ImageUploadService {

    private final AmazonS3 amazonS3;
    private final String bucketName;

    public ImageUploadService(AmazonS3 amazonS3,
                                @Value("${aws.bucket-name}") String bucketName) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
    }

    public String uploadImage(MultipartFile file) {
        URL s3ObjectUrl = null;
        String fileName = UUID.randomUUID().toString();
        try {
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName,
                    file.getInputStream(), null));
            s3ObjectUrl = amazonS3.getUrl(bucketName, fileName);
        } catch (IOException exception) {
            throw new AmazonS3UploadException("Failed to upload file to AWS S3");
        }
        return s3ObjectUrl.toString();
    }
}
