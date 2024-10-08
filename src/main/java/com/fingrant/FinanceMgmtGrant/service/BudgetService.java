package com.fingrant.FinanceMgmtGrant.service;

import com.fingrant.FinanceMgmtGrant.entity.Budget;
import com.fingrant.FinanceMgmtGrant.exception.CustomException;

import java.util.List;

public interface BudgetService {
    Budget createBuget(Budget budget) throws CustomException;
    Budget getBudgetById(Long id);

    List<Budget> getAllBudgets();

    Budget getBudgetByName(String name);

    Budget updateBudget(Long id,Budget budget);

    boolean deleteBudget(Long id);
}
