package com.fingrant.finance.service;

import com.fingrant.finance.entity.FullTimeEstimationBreakUpEntity;
import com.fingrant.finance.entity.GaEstimationBreakUpEntity;

public interface ExpenseEstimatorService {

    GaEstimationBreakUpEntity saveGaExpenseEstimator(String gaType, String studentId, String semester, String savingEmployee);

    FullTimeEstimationBreakUpEntity saveFullTimeRoleEstimator(String role, int durationInMonths , double bonus, double interviewScore, String savingEmployeeRole);
}