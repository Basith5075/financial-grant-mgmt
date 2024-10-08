package com.fingrant.finance.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import software.amazon.awssdk.services.s3.S3Client;

public class MyS3ClientWrapper implements DisposableBean {

    private final S3Client s3Client;

    private static final Logger logger = LogManager.getLogger(MyS3ClientWrapper.class);

    public MyS3ClientWrapper(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public void destroy() throws Exception {
        // Clean up resources here
        s3Client.close();  // Close S3Client or release resources
        logger.info("S3Client has been cleaned up.");
    }
}
