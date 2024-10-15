package com.fingrant.finance.service.impl;

import com.fingrant.finance.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BulkBudgetUpdatesImplTest {


    @Mock
    private ObjectFactory<S3Client> s3ClientFactory ;

//    @Mock
//    private ObjectFactory<SnsClient> snsClientFactory ;

    @Mock
    private ConfigurableApplicationContext context;

    @Mock
    private ConfigurableListableBeanFactory beanFactory;

//   @Mock
//   private SnsClient snsClient;

    @Mock
    private S3Client s3Client;

    @InjectMocks
    private BulkBudgetUpdatesImpl bulkBudgetUpdates;

    private static String content = "This is the content of the file being uploaded to S3.";

    private String bucketName;
    private String objectKey ;
    private String region;

//    private String topicArn;
//    private String subject;
//    private String message;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bucketName = "my-bucket";
        objectKey = "my-file.csv";
        region = "US_EAST_2";


        when(s3ClientFactory.getObject()).thenReturn(s3Client);
        when(context.getBeanFactory()).thenReturn(beanFactory);
        //        topicArn = "arn:aws:sns:us-east-1:851725245212:s3-file-qa-sns";
        //        subject = "my-sns topics subject";
        //        message = "Sample SNS topic message";
        //       snsClient = Mockito.mock(SnsClient.class);
        //        when(snsClientFactory.getObject()).thenReturn(snsClient);

    }

    @Test
    void uploadFileToS3Csv() {

       String result = bulkBudgetUpdates.uploadFileToS3Csv(bucketName, objectKey, region);
       assertEquals("successfully inserted the file !!", result);

    }

    @Test
    void uploadFileToS3CsvUSEast1() {

        String result = bulkBudgetUpdates.uploadFileToS3Csv(bucketName, objectKey, "US_EAST_1");
        assertEquals("successfully inserted the file !!", result);

    }

    @Test
    void uploadFileToS3CsvFailedToUploadFile() {
        // When
        doThrow(new RuntimeException("S3 upload error")).when(s3Client).putObject(any(PutObjectRequest.class),  any(RequestBody.class));
        //then
        CustomException customException = assertThrows(CustomException.class, ()-> bulkBudgetUpdates.uploadFileToS3Csv(bucketName, objectKey, "US_EAST_1"));
        assertEquals("Failed to upload file! ", customException.getMessage());
        assertEquals("F101", customException.getErrorCode());
        verify(s3Client,times(1)).putObject(any(PutObjectRequest.class),  any(RequestBody.class));
    }

//    @Test
//    void sendSnsNotification() {
//
//        PublishResponse publishResponse = PublishResponse.builder()
//                .messageId("12345")
//                .build();
//
//        when(snsClient.publish(any(PublishRequest.class))).thenReturn(publishResponse);
//
////        boolean result = bulkBudgetUpdates.snsNotificationService(snsClient, topicArn, subject, message);
//
//
//
//        boolean result = bulkBudgetUpdates.sendSnsNotification(subject, message);
//
//        assertTrue(result);
//
//    }
}