package com.fingrant.finance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fingrant.finance.service.BulkBudgetUpdates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AWSController.class)
class AWSControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BulkBudgetUpdates bulkBudgetUpdates;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void uploadFileToS3() throws Exception {

        Mockito.when(bulkBudgetUpdates.uploadFileToS3Csv("sample-bucket", "file1.txt", "US_EAST_1")).thenReturn("successfully inserted the file !!");
        String result = this.mockMvc.perform(get("/uploadFile").contentType(MediaType.APPLICATION_JSON).param("bucketName", "sample-bucket").param("objectKey", "file1.txt").param("region", "US_EAST_1")).andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertEquals("successfully inserted the file !!", result);
    }

    @Test
    void sendSns() throws Exception {

        Map<String, String> sampleBody = new HashMap<>();
        sampleBody.put("message", "Sample Message");
        sampleBody.put("subject", "Sample Subject");

        ObjectMapper objectMapper = new ObjectMapper();

        final String jsonData = objectMapper.writeValueAsString(sampleBody);

        Mockito.when(bulkBudgetUpdates.sendSnsNotification("Sample Subject", "Sample Message")).thenReturn(true);

        String result = this.mockMvc.perform(post("/sendSns").contentType(MediaType.APPLICATION_JSON).content(jsonData)).andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertEquals("success", result);

    }
}