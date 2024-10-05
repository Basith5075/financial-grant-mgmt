package com.fingrant.FinanceMgmtGrant.service;

import com.fingrant.FinanceMgmtGrant.controller.ExpenseEstimator;
import com.fingrant.FinanceMgmtGrant.entity.EstimationBreakUpEntity;

public interface ExpenseEstimatorService {

    EstimationBreakUpEntity saveExpenseEstimator(String gaType, String studentId, String semester, String savingEmployee);
}
