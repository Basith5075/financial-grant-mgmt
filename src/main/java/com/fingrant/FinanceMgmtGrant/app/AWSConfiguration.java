package com.fingrant.FinanceMgmtGrant.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AWSConfiguration {


    @Bean
    public S3Client getAmazonS3Client() {
        AwsCredentialsProvider credentials = DefaultCredentialsProvider.create();

        return S3Client.builder()
                .credentialsProvider(credentials)
                .region(Region.US_EAST_1)
                .build();
    }
}
