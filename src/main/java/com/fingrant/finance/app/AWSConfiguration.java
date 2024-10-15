package com.fingrant.finance.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sns.SnsClient;

import static software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider.create;

@Configuration
public class AWSConfiguration {

    static Region reg = Region.US_EAST_1;

    @Bean
    @Scope("prototype")
    public S3Client getAmazonS3Client() {
        AwsCredentialsProvider credentials = create();

        return S3Client.builder()
                .credentialsProvider(credentials)
                .region(reg)
                .build();
    }

    @Bean
    @Scope("prototype")
    public SnsClient getAmazonSnsClient() {

        return SnsClient.builder().build();
    }

    public static Region getReg() {
        return reg;
    }

    public static void setReg(Region reg) {
        AWSConfiguration.reg = reg;
    }
}
