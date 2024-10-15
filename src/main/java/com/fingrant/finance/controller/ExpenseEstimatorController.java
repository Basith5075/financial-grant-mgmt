package com.fingrant.finance.controller;

import com.fingrant.finance.entity.FullTimeEstimationBreakUpEntity;
import com.fingrant.finance.entity.GaEstimationBreakUpEntity;
import com.fingrant.finance.service.ExpenseEstimatorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/estimate")
public class ExpenseEstimatorController {

    private final ExpenseEstimatorService expenseEstimatorService;

    private static final Logger logger = LogManager.getLogger(ExpenseEstimatorController.class);

    public ExpenseEstimatorController(ExpenseEstimatorService expenseEstimatorService) {
        this.expenseEstimatorService = expenseEstimatorService;
    }

    @GetMapping("/ga")
    public ResponseEntity<GaEstimationBreakUpEntity> estimateGaCost(@RequestParam("ga-type") String gaType,
                                                                    @RequestParam("semester") String semester,
                                                                    @RequestParam("studentId") String studentId,
                                                                    @RequestHeader("eid") String agent) {

        logger.info("Estimation Requested for the StudentId: {}, Semester: {}, GaType: {}, by eid: {}",studentId,semester,gaType,agent);

        return new ResponseEntity<>(expenseEstimatorService.saveGaExpenseEstimator(gaType, studentId, semester, agent), HttpStatus.OK);

    }

    @GetMapping("/fulltime")
    public ResponseEntity<FullTimeEstimationBreakUpEntity> estimateGeneralFullTimeCost(@RequestParam("role") String role,
                                                                                       @RequestParam("durationInMonths") int durationInMonths,
                                                                                       @RequestParam("bonus") double bonus,
                                                                                       @RequestParam("interviewScore") double interviewScore,
                                                                                       @RequestHeader("empRole") String empRole) {

        logger.info("Estimation Requested for the Full Time Role: {}, durationInMonths : {}, bonus: {}, score {}, by eid: {}",role,durationInMonths,bonus, interviewScore,empRole);

        return new ResponseEntity<>(expenseEstimatorService.saveFullTimeRoleEstimator(role, durationInMonths, bonus,interviewScore, empRole), HttpStatus.OK);

    }
}
