package com.fingrant.FinanceMgmtGrant.controller;

import com.fingrant.FinanceMgmtGrant.entity.EstimationBreakUp;
import com.fingrant.FinanceMgmtGrant.entity.EstimationBreakUpEntity;
import com.fingrant.FinanceMgmtGrant.service.ExpenseEstimatorService;
import com.fingrant.FinanceMgmtGrant.service.impl.BulkBudgetUpdatesImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estimate")
public class ExpenseEstimator {

    @Autowired
    private ExpenseEstimatorService expenseEstimatorService;

    private static final Logger logger = LogManager.getLogger(ExpenseEstimator.class);

    @GetMapping("/estimate")
    public ResponseEntity<EstimationBreakUpEntity> estimateHalfGa(@RequestParam("ga-type") String gaType,
                                                                  @RequestParam("semester") String semester,
                                                                  @RequestParam("studentId") String studentId,
                                                                  @RequestHeader("eid") String agent) {

        logger.info("Estimation Requested for the StudentId: {}, Semester: {}, GaType: {}, by eid: {}",studentId,semester,gaType,agent);

        return new ResponseEntity<>(expenseEstimatorService.saveExpenseEstimator(gaType, studentId, semester, agent), HttpStatus.OK);

    }

}
