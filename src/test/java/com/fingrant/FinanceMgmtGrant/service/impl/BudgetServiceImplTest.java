package com.fingrant.FinanceMgmtGrant.service.impl;

import com.fingrant.FinanceMgmtGrant.entity.Budget;
import com.fingrant.FinanceMgmtGrant.repository.BudgetRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


class BudgetServiceImplTest {

    @InjectMocks
    private BudgetServiceImpl budgetService;

    @Mock
    private BudgetRepository budgetRepository;

    Budget budget = new Budget();
    Budget budget1 = new Budget();

    List<Budget> budgetList = new ArrayList<>();

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

        budgetList.add(budget);
        budgetList.add(budget1);

    }

    @AfterEach
    void tearDown() {
        budget = null;
    }

    @Test
    void testCreateBudget() throws ParseException {

        // Given -- Already defined in Before method
        // When
        Mockito.when(budgetRepository.save(budget)).thenReturn(budget);
        // then
        Budget actualBudget = budgetService.createBuget(budget);
        assertThat(actualBudget.getName()).isEqualTo(budget.getName());
        assertThat(actualBudget.getEndDate()).isEqualTo(budget.getEndDate());
        verify(budgetRepository,times(1)).save(budget);
    }

    @Test
    void testGetBudgetById() {

        // Given -- Already defined in Before method
        // When
        Mockito.when(budgetRepository.findById(1L)).thenReturn(Optional.of(budget));
        // then
        Budget actualBudget = budgetService.getBudgetById(1L);
        assertThat(actualBudget.getName()).isEqualTo(budget.getName());
        verify(budgetRepository,times(1)).findById(1L);
    }

    @Test
    void getAllBudgets() {
        // Given -- Already defined in Before method
        // When
        Mockito.when(budgetRepository.findAll()).thenReturn(budgetList);

        //then
        List<Budget> actualBudgets = budgetService.getAllBudgets();
        assertThat(actualBudgets).hasSize(2);
        assertThat(actualBudgets.get(0).getName()).isEqualTo(budget.getName());
        assertThat(actualBudgets.get(1).getName()).isEqualTo(budget1.getName());
        verify(budgetRepository,times(1)).findAll();
    }

    @Test
    void getBudgetByName() {
     // Given -- Already defined in Before method
     // When
        Mockito.when(budgetRepository.findBudgetByName(budget.getName())).thenReturn(budget);

     // then
        Budget actualBudget = budgetService.getBudgetByName("Research Grant 2024");

        assertThat(actualBudget.getName()).isEqualTo(budget.getName());
        verify(budgetRepository,times(1)).findBudgetByName(budget.getName());

    }
//
    @Test
    void deleteBudget() {

        // Given -- Already defined in Before method
        // When
        when(budgetRepository.findById(1L)).thenReturn(Optional.of(budget));

        // then

        boolean result = budgetService.deleteBudget(1L);

        assertThat(result).isTrue();

        verify(budgetRepository,times(1)).findById(1L);
        verify(budgetRepository,times(1)).delete(budget);
    }
}