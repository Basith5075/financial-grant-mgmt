package com.fingrant.FinanceMgmtGrant.service.impl;

import com.fingrant.FinanceMgmtGrant.controller.ExpenseEstimator;
import com.fingrant.FinanceMgmtGrant.entity.EstimationBreakUp;
import com.fingrant.FinanceMgmtGrant.entity.EstimationBreakUpEntity;
import com.fingrant.FinanceMgmtGrant.exception.CustomException;
import com.fingrant.FinanceMgmtGrant.repository.EstimationBreakUpEntityRepository;
import com.fingrant.FinanceMgmtGrant.service.ExpenseEstimatorService;
import com.fingrant.FinanceMgmtGrant.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ExpenseEstimatorServiceImpl implements ExpenseEstimatorService {

    private final Logger LOGGER = LogManager.getLogger(ExpenseEstimatorServiceImpl.class);

    @Autowired
    private EstimationBreakUpEntityRepository estimationBreakUpEntityRepository;

    @Override
    public EstimationBreakUpEntity saveExpenseEstimator(String gaType, String studentId, String semester, String savingEmployee) {

        LOGGER.info("SaveExpenseEstimator method called for saving below details: gaType: {}, studnetId: {}, semester: {}, savingEmployee : {}", gaType, studentId, semester, savingEmployee);

        String semesterName = gaType.toUpperCase() + "_" + semester.toUpperCase();

        String tokenizedStudentId = Util.tokenize(studentId);

        try {

            EstimationBreakUp estimationBreakUp = EstimationBreakUp.valueOf(semesterName);

            if (estimationBreakUpEntityRepository.existsByStudentIdAndSemesterName(tokenizedStudentId, estimationBreakUp.getSemesterName())) {
                return estimationBreakUpEntityRepository.findByStudentIdAndSemesterName(tokenizedStudentId, estimationBreakUp.getSemesterName()).get();
            }

            EstimationBreakUpEntity estimationBreakUpEntity = new EstimationBreakUpEntity();
            estimationBreakUpEntity.setCreditFee(estimationBreakUp.getCreditFee());
            estimationBreakUpEntity.setTuitionFee(estimationBreakUp.getTuitionFee());
            estimationBreakUpEntity.setInsurance(estimationBreakUp.getInsurance());
            estimationBreakUpEntity.setBiWeeklyWages(estimationBreakUp.getBiWeeklyWages());
            estimationBreakUpEntity.setSemesterName(estimationBreakUp.getSemesterName());
            estimationBreakUpEntity.setStudentId(tokenizedStudentId);
            estimationBreakUpEntity.setNumberOfWeeks(estimationBreakUp.getNumberOfWeeks());
            estimationBreakUpEntity.setSavedDate(LocalDate.now());
            estimationBreakUpEntity.setSavingEmployee(savingEmployee);

            LOGGER.info("Saving estimation break up: " + estimationBreakUp);

            estimationBreakUpEntityRepository.save(estimationBreakUpEntity);

            return estimationBreakUpEntity;
        }catch (IllegalArgumentException e) {
            throw new CustomException("Please Provide a valid Semester name and Gatype, some valid values include: " + EstimationBreakUp.values(), e.getMessage());
        }
    }

}
