package com.fingrant.FinanceMgmtGrant.service.impl;

import com.fingrant.FinanceMgmtGrant.service.BulkBudgetUpdates;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class BulkBudgetUpdatesImpl implements BulkBudgetUpdates {

    private static final Logger logger = LogManager.getLogger(BulkBudgetUpdatesImpl.class);

    @Autowired
    S3Client s3Client;

    private static String content = "This is the content of the file being uploaded to S3.";

    @Override
    public String getFileFromS3Csv(String bucketName, String objectKey) {
        try {
            logger.info("getBudgetFromS3Csv() called");

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            RequestBody body = RequestBody.fromBytes(content.getBytes());

            s3Client.putObject(putObjectRequest, body);
        }catch (Exception e){
            logger.error("Something went wrong {} ", e.getMessage());
            return "failed to upload file";
        }

        logger.info("SuccessFully Inserted the File into S3");

        return "successfully inserted the file !!";
    }

    private FileSystemResource getFileSystemResource(String fileName) {

        return new FileSystemResource(fileName);
    }


}
