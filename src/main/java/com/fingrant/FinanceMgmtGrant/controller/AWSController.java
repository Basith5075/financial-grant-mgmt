package com.fingrant.FinanceMgmtGrant.controller;

import com.fingrant.FinanceMgmtGrant.service.BulkBudgetUpdates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class AWSController {

    @Autowired
    private BulkBudgetUpdates bulkBudgetUpdates;

    @GetMapping("/uploadFile")
    public String uploadFileToS3(@RequestParam("bucketName") String bucketName, @RequestParam("objectKey") String objectKey, @RequestParam("region") String region ){
        return bulkBudgetUpdates.uploadFileToS3Csv(bucketName, objectKey, region );
    }

    @PostMapping("/sendSns")
    public String sendSns(@RequestBody Map<String, String> payload){

        if (bulkBudgetUpdates.sendSnsNotification(payload.get("subject"), payload.get("message")))
            return "success";
        else
            return "failed to send the message !!";
    }

}
