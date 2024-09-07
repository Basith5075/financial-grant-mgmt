package com.fingrant.FinanceMgmtGrant.service.impl;

import com.fingrant.FinanceMgmtGrant.app.AWSConfiguration;
import com.fingrant.FinanceMgmtGrant.service.BulkBudgetUpdates;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

@Service
public class BulkBudgetUpdatesImpl implements BulkBudgetUpdates {

    private static final Logger logger = LogManager.getLogger(BulkBudgetUpdatesImpl.class);

    @Autowired
    private ObjectFactory<S3Client> s3ClientFactory ;

    @Autowired
    private ApplicationContext context;

    private SnsClient snsClient;

    private static String content = "This is the content of the file being uploaded to S3.";

    @Override
    public String uploadFileToS3Csv(String bucketName, String objectKey, String region) {
        try {
            logger.info("getBudgetFromS3Csv() called");
            if("US_EAST_2".equals(region))
                AWSConfiguration.setReg(Region.US_EAST_2);
            S3Client s3Client = s3ClientFactory.getObject();
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            RequestBody body = RequestBody.fromBytes(content.getBytes());

            s3Client.putObject(putObjectRequest, body);

            ((ConfigurableApplicationContext) context).getBeanFactory().destroyBean(s3Client);

        }catch (Exception e){
            logger.error("Something went wrong {} ", e.getMessage());
            return "failed to upload file";
        }

        logger.info("SuccessFully Inserted the File into S3");

        return "successfully inserted the file !!";
    }

    private boolean SnsNotificationService(SnsClient snsClient, String topicArn, String subject, String message){

        try {
            logger.info("SnsNotificationService() called with snsclient: {}, topicArn: {}, subject: {}, message: {}", snsClient, topicArn, subject, message);
            // Create a publish request
            PublishRequest publishRequest = PublishRequest.builder()
                    .topicArn(topicArn)
                    .subject(subject)  // Subject of the email
                    .message(message)  // Body of the email
                    .build();

            // Publish the message
            PublishResponse publishResponse = snsClient.publish(publishRequest);
            logger.info("Message sent with ID: {} ", publishResponse.messageId());

            return true;
        } catch (SnsException e) {
            logger.error("SnsNotificationService() called with SnsException {}", e.getMessage());
            return false;
        }catch (Exception e) {
            logger.error("SnsNotificationService() called with generic exception {}", e.getMessage());
            return false;
        }
    }

    public boolean sendSnsNotification(String subject, String message){
        try {

            logger.info("sendSnsNotification() called with subject{} , and message {}", subject, message);

                SnsClient snsClient = SnsClient.builder().build();
                String topicArn = "arn:aws:sns:us-east-1:851725245212:s3-file-qa-sns";
          return SnsNotificationService(snsClient, topicArn, subject, message);

        }catch (Exception e){
            logger.error("Issue in sending sns notification {} ", e.getMessage());
            return false;
        }

    }

    private FileSystemResource getFileSystemResource(String fileName) {

        return new FileSystemResource(fileName);
    }
}
