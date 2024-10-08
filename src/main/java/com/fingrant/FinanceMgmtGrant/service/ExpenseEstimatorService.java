package com.fingrant.FinanceMgmtGrant.service;

import com.fingrant.FinanceMgmtGrant.entity.FullTimeEstimationBreakUpEntity;
import com.fingrant.FinanceMgmtGrant.entity.GaEstimationBreakUpEntity;

public interface ExpenseEstimatorService {

    GaEstimationBreakUpEntity saveGaExpenseEstimator(String gaType, String studentId, String semester, String savingEmployee);

    FullTimeEstimationBreakUpEntity saveFullTimeRoleEstimator(String role, int durationInMonths , double bonus, double interviewScore, String savingEmployeeRole);
}