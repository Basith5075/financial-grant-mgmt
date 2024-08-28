package com.fingrant.FinanceMgmtGrant.service;

public interface BulkBudgetUpdates {

    String getFileFromS3Csv(String bucketName, String objectKey);

    boolean sendSnsNotification(String subject, String message);

}
