package com.fingrant.FinanceMgmtGrant.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AWSConfiguration {

    static Region reg = Region.US_EAST_1;

    @Bean
    @Scope("prototype")
    public S3Client getAmazonS3Client() {
        AwsCredentialsProvider credentials = DefaultCredentialsProvider.create();

        return S3Client.builder()
                .credentialsProvider(credentials)
                .region(reg)
                .build();
    }

    public static Region getReg() {
        return reg;
    }

    public static void setReg(Region reg) {
        AWSConfiguration.reg = reg;
    }
}
