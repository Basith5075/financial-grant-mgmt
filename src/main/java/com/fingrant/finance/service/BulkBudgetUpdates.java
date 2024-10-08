package com.fingrant.finance.service;

public interface BulkBudgetUpdates {

    String uploadFileToS3Csv(String bucketName, String objectKey, String region);

    boolean sendSnsNotification(String subject, String message);

}
