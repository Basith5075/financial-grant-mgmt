package com.fingrant.finance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fingrant.finance.entity.Budget;
import com.fingrant.finance.service.BudgetService;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BudgetController.class)
class BudgetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BudgetService budgetService;

    Budget budget, budget1;

    List<Budget> budgetList;

    private final String url = "/v1/budget";

    @BeforeEach
    void setUp() throws ParseException {

        MockitoAnnotations.openMocks(this);

        budget = new Budget();
        budget.setId(1L);
        budget.setName("Research Grant 2024");
        budget.setTotalAmount(new BigDecimal("50000.00"));
        budget.setPersonnelBudget(new BigDecimal("25000.00"));
        budget.setEquipmentBudget(new BigDecimal("15000.00"));
        budget.setTravelBudget(new BigDecimal("3000.00"));
        budget.setSuppliesBudget(new BigDecimal("2000.00"));
        budget.setOtherBudget(new BigDecimal("5000.00"));
        budget.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse("2024-01-01"));
        budget.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse("2024-12-31"));
        budget.setGrantId(101L);

        budget1 = new Budget();
        budget1.setId(2L);
        budget1.setName("Tech Innovation Grant 2025");
        budget1.setTotalAmount(new BigDecimal("120000.00"));
        budget1.setPersonnelBudget(new BigDecimal("60000.00"));
        budget1.setEquipmentBudget(new BigDecimal("30000.00"));
        budget1.setTravelBudget(new BigDecimal("10000.00"));
        budget1.setSuppliesBudget(new BigDecimal("5000.00"));
        budget1.setOtherBudget(new BigDecimal("15000.00"));
        budget1.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-05-01"));
        budget1.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse("2026-04-30"));
        budget1.setGrantId(202L);

        budgetList = new LinkedList<>();

        budgetList.add(budget);
        budgetList.add(budget1);
    }

    @Test
    void createBudget() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String jsonData = ow.writeValueAsString(budget);

        Mockito.when(budgetService.createBudget(budget)).thenReturn(budget);

        this.mockMvc.perform((post(url).
                contentType(MediaType.APPLICATION_JSON)
                .content(jsonData))).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void createBudgetException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        budget.setName(null);

        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String jsonData = ow.writeValueAsString(budget);

        this.mockMvc.perform((post(url).
                        contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))).andDo(print())
                .andExpect(status().isBadRequest());

    }


    @Test
    void getAllBudgets() throws Exception {

        Mockito.when(budgetService.getAllBudgets()).thenReturn(budgetList);

        this.mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getBudgetById() throws Exception {

        Mockito.when(budgetService.getBudgetById(budget.getId())).thenReturn(budget);

        this.mockMvc.perform(get(url+"/"+budget.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getBudgetByIdException() throws Exception {

        Mockito.when(budgetService.getBudgetById(12L)).thenThrow(new NoSuchElementException("No budget found with id 12"));

        this.mockMvc.perform(get(url+"/12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

    }


    @Test
    void getBudgetByName() throws Exception {

        Mockito.when(budgetService.getBudgetByName(budget.getName())).thenReturn(budget);

        this.mockMvc.perform(get(url+"/getBudgetbyname/"+budget.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateBudget() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String jsonData = ow.writeValueAsString(budget);

        Budget updatedBudget = new Budget();

        BeanUtils.copyProperties(updatedBudget,budget1);

        updatedBudget.setId(1L);

        Mockito.when(budgetService.updateBudget(1L, budget)).thenReturn(updatedBudget);

        MvcResult mvcResult = this.mockMvc.perform(put(url + "/1")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonData))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        Budget returnedBudget = objectMapper.readValue(contentAsString, Budget.class);

        assertThat(returnedBudget.getId()).isEqualTo(1L);
        assertThat(returnedBudget.getName()).isEqualTo(updatedBudget.getName());


    }

    @Test
    void deleteBudget() throws Exception {

        Mockito.when(budgetService.deleteBudget(budget.getId())).thenReturn(true);

        this.mockMvc.perform(delete(url+"/"+budget.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}