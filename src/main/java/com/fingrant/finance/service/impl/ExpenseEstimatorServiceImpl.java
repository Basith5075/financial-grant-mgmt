package com.fingrant.finance.service.impl;

import com.fingrant.finance.entity.*;
import com.fingrant.finance.exception.CustomException;
import com.fingrant.finance.repository.FullTimeEstimationBreakUpEntityRepository;
import com.fingrant.finance.repository.GaEstimationBreakUpEntityRepository;
import com.fingrant.finance.service.ExpenseEstimatorService;
import com.fingrant.finance.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ExpenseEstimatorServiceImpl implements ExpenseEstimatorService {

    private final Logger LOGGER = LogManager.getLogger(ExpenseEstimatorServiceImpl.class);

    private final GaEstimationBreakUpEntityRepository estimationBreakUpEntityRepository;

    private final FullTimeEstimationBreakUpEntityRepository fullTimeEstimationBreakUpEntityRepository;

    public ExpenseEstimatorServiceImpl(GaEstimationBreakUpEntityRepository estimationBreakUpEntityRepository, FullTimeEstimationBreakUpEntityRepository fullTimeEstimationBreakUpEntityRepository) {
        this.estimationBreakUpEntityRepository = estimationBreakUpEntityRepository;
        this.fullTimeEstimationBreakUpEntityRepository = fullTimeEstimationBreakUpEntityRepository;
    }

    @Override
    public GaEstimationBreakUpEntity saveGaExpenseEstimator(String gaType, String studentId, String semester, String savingEmployee) {

        LOGGER.info("SaveExpenseEstimator method called for saving below details: gaType: {}, studnetId: {}, semester: {}, savingEmployee : {}", gaType, studentId, semester, savingEmployee);

        String semesterName = gaType.toUpperCase() + "_" + semester.toUpperCase();

        String tokenizedStudentId = Util.tokenize(studentId);

        try {

            GaEstimationBreakUp estimationBreakUp = GaEstimationBreakUp.valueOf(semesterName);

            if (estimationBreakUpEntityRepository.existsByStudentIdAndGaTypeSemesterName(tokenizedStudentId, estimationBreakUp.getSemesterName())) {
                return estimationBreakUpEntityRepository.findByStudentIdAndGaTypeSemesterName(tokenizedStudentId, estimationBreakUp.getSemesterName()).get();
            }

            GaEstimationBreakUpEntity estimationBreakUpEntity = new GaEstimationBreakUpEntity();
            estimationBreakUpEntity.setCreditFee(estimationBreakUp.getCreditFee());
            estimationBreakUpEntity.setTuitionFee(estimationBreakUp.getTuitionFee());
            estimationBreakUpEntity.setInsurance(estimationBreakUp.getInsurance());
            estimationBreakUpEntity.setBiWeeklyWages(estimationBreakUp.getBiWeeklyWages());
            estimationBreakUpEntity.setGaTypeSemesterName(gaType + "_" +estimationBreakUp.getSemesterName());
            estimationBreakUpEntity.setStudentId(tokenizedStudentId);
            estimationBreakUpEntity.setNumberOfWeeks(estimationBreakUp.getNumberOfWeeks());
            estimationBreakUpEntity.setSavedDate(LocalDate.now());
            estimationBreakUpEntity.setSavingEmployee(savingEmployee);
            estimationBreakUpEntity.setMiscellaneous(estimationBreakUp.getMiscellaneous());
            estimationBreakUpEntity.setTotalExpectedCost(calculateTotalCostOfGa(estimationBreakUp));

            LOGGER.info("Saving estimation break up: " + estimationBreakUpEntity);

            estimationBreakUpEntityRepository.save(estimationBreakUpEntity);

            return estimationBreakUpEntity;
        }catch (IllegalArgumentException e) {
            throw new CustomException("Please Provide a valid Semester name and Gatype, some valid values include: " + GaEstimationBreakUp.values(), e.getMessage());
        }
    }

    @Override
    public FullTimeEstimationBreakUpEntity saveFullTimeRoleEstimator(String role, int durationInMonths, double bonus, double interviewScore, String savingEmployeeRole) {

        LOGGER.info("fullTimeRoleEstimator method called by Saving Employee Role : {}, duration in months {}, bonus {}, interviewScore {}, actualEmployee Role {}", savingEmployeeRole, durationInMonths, bonus, interviewScore, role);

        if(!validateSavingEmployeeRole(savingEmployeeRole)){
            throw new CustomException("You are not authorized to perform this action. Please contact your system administrator.","E321");
        }

        try {

            if(fullTimeEstimationBreakUpEntityRepository.existsByRoleNameAndDurationInMonths(role, durationInMonths)){
                return  fullTimeEstimationBreakUpEntityRepository.findByRoleNameAndDurationInMonths(role,durationInMonths).get();
            }

            FullTimeEstimationBreakUp fullTimeEstimationBreakUp = FullTimeEstimationBreakUp.valueOf(role);

            FullTimeEstimationBreakUpEntity fullTimeEstimationBreakUpEntity = new FullTimeEstimationBreakUpEntity();

            fullTimeEstimationBreakUpEntity.setRoleName(role);
            fullTimeEstimationBreakUpEntity.setSalaryWages(fullTimeEstimationBreakUp.getSalaryWages());
            fullTimeEstimationBreakUpEntity.setBonusesAndCommissions(bonus);
            fullTimeEstimationBreakUpEntity.setHealthInsurance(fullTimeEstimationBreakUp.getHealthInsurance());
            fullTimeEstimationBreakUpEntity.setRetirementContributions(fullTimeEstimationBreakUp.getRetirementContributions());
            fullTimeEstimationBreakUpEntity.setPayrollTaxes(fullTimeEstimationBreakUp.getPayrollTaxes());
            fullTimeEstimationBreakUpEntity.setUnemploymentInsurance(fullTimeEstimationBreakUp.getUnemploymentInsurance());
            fullTimeEstimationBreakUpEntity.setInterviewScore(interviewScore);
            fullTimeEstimationBreakUpEntity.setDurationInMonths(durationInMonths);
            fullTimeEstimationBreakUpEntity.setWorkersCompensationInsurance(fullTimeEstimationBreakUp.getWorkersCompensationInsurance());

            fullTimeEstimationBreakUpEntity.setTotalExpectedCost(calculateTotalCostOfFullTime(fullTimeEstimationBreakUpEntity));

            fullTimeEstimationBreakUpEntity.setSavedDate(LocalDate.now());
            fullTimeEstimationBreakUpEntity.setSavingEmployee(savingEmployeeRole);

            return fullTimeEstimationBreakUpEntityRepository.save(fullTimeEstimationBreakUpEntity);

        }catch(IllegalArgumentException e){
            throw new CustomException("Role Does not exist !!","R456");
        }

    }

    private double calculateTotalCostOfFullTime(FullTimeEstimationBreakUpEntity fullTimeEstimationBreakUpEntity) {

        double totalCost = fullTimeEstimationBreakUpEntity.getSalaryWages() * fullTimeEstimationBreakUpEntity.getDurationInMonths() * fullTimeEstimationBreakUpEntity.getInterviewScore()
                + fullTimeEstimationBreakUpEntity.getBonusesAndCommissions() * fullTimeEstimationBreakUpEntity.getInterviewScore()
                + fullTimeEstimationBreakUpEntity.getHealthInsurance() * fullTimeEstimationBreakUpEntity.getDurationInMonths()
                + fullTimeEstimationBreakUpEntity.getRetirementContributions() * fullTimeEstimationBreakUpEntity.getDurationInMonths()
                + fullTimeEstimationBreakUpEntity.getPayrollTaxes() * fullTimeEstimationBreakUpEntity.getDurationInMonths()
                + fullTimeEstimationBreakUpEntity.getUnemploymentInsurance() * fullTimeEstimationBreakUpEntity.getDurationInMonths()
                + fullTimeEstimationBreakUpEntity.getWorkersCompensationInsurance() * fullTimeEstimationBreakUpEntity.getDurationInMonths();

        return totalCost;

    }

    private double calculateTotalCostOfGa(GaEstimationBreakUp estimationBreakUp){

        double totalCost = estimationBreakUp.getTuitionFee() +
                estimationBreakUp.getCreditFee() +
                estimationBreakUp.getBiWeeklyWages() * estimationBreakUp.getNumberOfWeeks() / 2 +
                estimationBreakUp.getMiscellaneous() +
                estimationBreakUp.getInsurance();

        return totalCost;
    }

    private boolean validateSavingEmployeeRole(String role){
        try{
            EmployeeRole.valueOf(role);
            return true;
        }catch(IllegalArgumentException e){
            return false;
        }
    }
}
