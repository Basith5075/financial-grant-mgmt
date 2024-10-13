package com.fingrant.finance.service.impl;

import com.fingrant.finance.entity.FullTimeEstimationBreakUp;
import com.fingrant.finance.entity.FullTimeEstimationBreakUpEntity;
import com.fingrant.finance.entity.GaEstimationBreakUp;
import com.fingrant.finance.entity.GaEstimationBreakUpEntity;
import com.fingrant.finance.exception.CustomException;
import com.fingrant.finance.repository.FullTimeEstimationBreakUpEntityRepository;
import com.fingrant.finance.repository.GaEstimationBreakUpEntityRepository;
import com.fingrant.finance.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ExpenseEstimatorServiceImplTest {

    @Mock
    GaEstimationBreakUpEntityRepository estimationBreakUpEntityRepository;
    @Mock
    FullTimeEstimationBreakUpEntityRepository fullTimeEstimationBreakUpEntityRepository;

    @InjectMocks
    ExpenseEstimatorServiceImpl expenseEstimatorService;

    private String gaTypeHalf;
    private String studentId;
    private String semester;
    private String savingEmployee;
    private String tokenizedStudentId;
    private String semesterName;
    private GaEstimationBreakUpEntity estimationBreakUpEntity;
    private FullTimeEstimationBreakUpEntity estimationBreakUpFullTime;
    private String role;
    private int durationInMonths;
    private double bonus;
    private double interviewScore;
    private String savingEmployeeRole;

    @BeforeEach
    void setUp() {

        // Given

        MockitoAnnotations.openMocks(this);
        gaTypeHalf = "half";
        studentId = "T00700187";
        semester = "fall_2024";
        semesterName = gaTypeHalf.toUpperCase() + "_" + semester.toUpperCase();
        tokenizedStudentId = Util.tokenize(studentId);

        estimationBreakUpEntity = new GaEstimationBreakUpEntity(
                1L,                        // id
                tokenizedStudentId,                 // studentId
                15000.00,                  // tuitionFee
                1200.00,                   // creditFee
                500.00,                    // biWeeklyWages
                200.00,                    // insurance
                16,                        // numberOfWeeks
                300.00,                    // miscellaneous
                "HALF_FALL_2024",            // gaTypeSemesterName
                LocalDate.of(2024, 10, 1), // savedDate
                LocalDate.of(2024, 10, 10),// updatedDate
                "wlz786",                // savingEmployee
                18000.00                   // totalExpectedCost
        );

        durationInMonths = 6;
        role = "SOFTWARE_ENGINEER_DOTNET";
        bonus = 450;
        interviewScore = 1.5;
        savingEmployeeRole = "ADMIN";

        estimationBreakUpFullTime = new FullTimeEstimationBreakUpEntity();
        // Set the properties using setter methods (assuming Lombok's @Data generates them)
        estimationBreakUpFullTime.setId(1L);
        estimationBreakUpFullTime.setRoleName(role);
        estimationBreakUpFullTime.setSalaryWages(85000.00);
        estimationBreakUpFullTime.setBonusesAndCommissions(5000.00);
        estimationBreakUpFullTime.setHealthInsurance(6000.00);
        estimationBreakUpFullTime.setRetirementContributions(4000.00);
        estimationBreakUpFullTime.setPayrollTaxes(7000.00);
        estimationBreakUpFullTime.setUnemploymentInsurance(1000.00);
        estimationBreakUpFullTime.setInterviewScore(interviewScore);
        estimationBreakUpFullTime.setDurationInMonths(durationInMonths);
        estimationBreakUpFullTime.setWorkersCompensationInsurance(1200.00);
        estimationBreakUpFullTime.setTotalExpectedCost(110200.00);
        estimationBreakUpFullTime.setSavedDate(LocalDate.of(2024, 10, 1));
        estimationBreakUpFullTime.setUpdatedDate(LocalDate.of(2024, 10, 10));
        estimationBreakUpFullTime.setSavingEmployee("wlz977");

    }

    @Test
    void saveGaExpenseEstimator() {

        // Given -- Is already defined in setup method

        // When
        when(estimationBreakUpEntityRepository.existsByStudentIdAndGaTypeSemesterName(tokenizedStudentId, semesterName)).thenReturn(true);
        when(estimationBreakUpEntityRepository.findByStudentIdAndGaTypeSemesterName(tokenizedStudentId, semesterName)).thenReturn(Optional.of(estimationBreakUpEntity));

        // then
        GaEstimationBreakUpEntity actualGaBreakUp = expenseEstimatorService.saveGaExpenseEstimator(gaTypeHalf, studentId, semester, "wlz786");
        assertThat(actualGaBreakUp.getStudentId()).isEqualTo(estimationBreakUpEntity.getStudentId());
        assertThat(actualGaBreakUp.getGaTypeSemesterName()).isEqualTo(estimationBreakUpEntity.getGaTypeSemesterName());

        verify(estimationBreakUpEntityRepository, times(1)).existsByStudentIdAndGaTypeSemesterName(tokenizedStudentId, semesterName);
        verify(estimationBreakUpEntityRepository, times(1)).findByStudentIdAndGaTypeSemesterName(tokenizedStudentId, semesterName);
    }

    @Test
    void saveGaExpenseEstimatorSaveNew() {
        // Given -- Is already defined in setup method

        // When
        when(estimationBreakUpEntityRepository.existsByStudentIdAndGaTypeSemesterName(tokenizedStudentId, semesterName)).thenReturn(false);
        when(estimationBreakUpEntityRepository.save(any(GaEstimationBreakUpEntity.class))).thenReturn(estimationBreakUpEntity);

        // then
        GaEstimationBreakUpEntity actualGaBreakUp = expenseEstimatorService.saveGaExpenseEstimator(gaTypeHalf, studentId, semester, "wlz786");
        assertThat(actualGaBreakUp.getStudentId()).isEqualTo(estimationBreakUpEntity.getStudentId());
        assertThat(actualGaBreakUp.getGaTypeSemesterName()).isEqualTo(estimationBreakUpEntity.getGaTypeSemesterName());

        verify(estimationBreakUpEntityRepository, times(1)).existsByStudentIdAndGaTypeSemesterName(tokenizedStudentId, semesterName);
        verify(estimationBreakUpEntityRepository, times(1)).save(any(GaEstimationBreakUpEntity.class));
    }

    @Test
    void saveGaExpenseEstimatorSaveIllegalArgs() {
        // Given -- Is already defined in setup method

        // then
        CustomException customException = assertThrows(CustomException.class, () -> expenseEstimatorService.saveGaExpenseEstimator(gaTypeHalf, studentId, "fall_2029", "wlz786"));
        assertThat(customException.getMessage()).isEqualTo("Please Provide a valid Semester name and Gatype, some valid values include: " + Arrays.toString(GaEstimationBreakUp.values()));
        assertThat(customException.getErrorCode()).isEqualTo("E204");
    }

    @Test
    void saveFullTimeRoleEstimator() {

        // Given -- Is already defined in setup method

        // When
        when(fullTimeEstimationBreakUpEntityRepository.existsByRoleNameAndDurationInMonths(role, durationInMonths)).thenReturn(true);
        when(fullTimeEstimationBreakUpEntityRepository.findByRoleNameAndDurationInMonths(role, durationInMonths)).thenReturn(Optional.of(estimationBreakUpFullTime));

        // then

        FullTimeEstimationBreakUpEntity actualFullTimeEstimationEntity = expenseEstimatorService.saveFullTimeRoleEstimator(role, durationInMonths, bonus, interviewScore, savingEmployeeRole);

        assertThat(actualFullTimeEstimationEntity.getRoleName()).isEqualTo(role);
        assertThat(actualFullTimeEstimationEntity.getDurationInMonths()).isEqualTo(durationInMonths);
        assertThat(actualFullTimeEstimationEntity.getInterviewScore()).isEqualTo(interviewScore);
        assertThat(actualFullTimeEstimationEntity.getSalaryWages()).isEqualTo(85000.00);

        verify(fullTimeEstimationBreakUpEntityRepository, times(1)).existsByRoleNameAndDurationInMonths(role, durationInMonths);
        verify(fullTimeEstimationBreakUpEntityRepository, times(1)).findByRoleNameAndDurationInMonths(role, durationInMonths);
    }

    @Test
    void saveFullTimeRoleEstimatorInvalidEmployeeRole() {

        CustomException customException = assertThrows(CustomException.class, () -> expenseEstimatorService.saveFullTimeRoleEstimator(role, durationInMonths, bonus, interviewScore, "WRONG_ROLE"));
        assertThat(customException.getErrorCode()).isEqualTo("E321");
        assertThat(customException.getMessage()).isEqualTo("You are not authorized to perform this action. Please contact your system administrator.");

    }

    @Test
    void saveFullTimeRoleEstimatorNewRecord() {

        // When
        when(fullTimeEstimationBreakUpEntityRepository.existsByRoleNameAndDurationInMonths(role, durationInMonths)).thenReturn(false);
        when(fullTimeEstimationBreakUpEntityRepository.save(any(FullTimeEstimationBreakUpEntity.class))).thenReturn(estimationBreakUpFullTime);

        // then

        FullTimeEstimationBreakUpEntity actualFullTimeEstimationEntity = expenseEstimatorService.saveFullTimeRoleEstimator(role, durationInMonths, bonus, interviewScore, savingEmployeeRole);

        assertThat(actualFullTimeEstimationEntity.getRoleName()).isEqualTo(role);
        assertThat(actualFullTimeEstimationEntity.getDurationInMonths()).isEqualTo(durationInMonths);
        assertThat(actualFullTimeEstimationEntity.getInterviewScore()).isEqualTo(interviewScore);
        assertThat(actualFullTimeEstimationEntity.getSalaryWages()).isEqualTo(85000.00);

        verify(fullTimeEstimationBreakUpEntityRepository, times(1)).existsByRoleNameAndDurationInMonths(role, durationInMonths);
        verify(fullTimeEstimationBreakUpEntityRepository, times(1)).save(any(FullTimeEstimationBreakUpEntity.class));
    }

    @Test
    void saveFullTimeRoleEstimatorIllegalArgs() {

        CustomException customException = assertThrows(CustomException.class, () -> expenseEstimatorService.saveFullTimeRoleEstimator("INVALID_ROLE", durationInMonths, bonus, interviewScore, savingEmployeeRole));
        assertThat(customException.getErrorCode()).isEqualTo("R456");
        assertThat(customException.getMessage()).isEqualTo("Role Does not exist !! \nExample of a valid role" + Arrays.toString(FullTimeEstimationBreakUp.values()));

    }

}
