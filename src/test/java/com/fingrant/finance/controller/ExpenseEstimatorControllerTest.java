package com.fingrant.finance.controller;

import com.fingrant.finance.entity.FullTimeEstimationBreakUpEntity;
import com.fingrant.finance.entity.GaEstimationBreakUpEntity;
import com.fingrant.finance.exception.CustomException;
import com.fingrant.finance.service.ExpenseEstimatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExpenseEstimatorController.class)
class ExpenseEstimatorControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseEstimatorService expenseEstimatorService;

    private GaEstimationBreakUpEntity gaEstimationBreakUpEntity;
    private FullTimeEstimationBreakUpEntity fullTimeEstimationBreakUpEntity;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        gaEstimationBreakUpEntity = new GaEstimationBreakUpEntity(
                1L,                        // id
                "T00700187",                 // studentId
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

        fullTimeEstimationBreakUpEntity = new FullTimeEstimationBreakUpEntity();
        // Set the properties using setter methods (assuming Lombok's @Data generates them)
        fullTimeEstimationBreakUpEntity.setId(1L);
        fullTimeEstimationBreakUpEntity.setRoleName("SOFTWARE_ENGINEER_DOTNET");
        fullTimeEstimationBreakUpEntity.setSalaryWages(85000.00);
        fullTimeEstimationBreakUpEntity.setBonusesAndCommissions(5000.00);
        fullTimeEstimationBreakUpEntity.setHealthInsurance(6000.00);
        fullTimeEstimationBreakUpEntity.setRetirementContributions(4000.00);
        fullTimeEstimationBreakUpEntity.setPayrollTaxes(7000.00);
        fullTimeEstimationBreakUpEntity.setUnemploymentInsurance(1000.00);
        fullTimeEstimationBreakUpEntity.setInterviewScore(1.25);
        fullTimeEstimationBreakUpEntity.setDurationInMonths(6);
        fullTimeEstimationBreakUpEntity.setWorkersCompensationInsurance(1200.00);
        fullTimeEstimationBreakUpEntity.setTotalExpectedCost(110200.00);
        fullTimeEstimationBreakUpEntity.setSavedDate(LocalDate.of(2024, 10, 1));
        fullTimeEstimationBreakUpEntity.setUpdatedDate(LocalDate.of(2024, 10, 10));
        fullTimeEstimationBreakUpEntity.setSavingEmployee("wlz977");
    }

    @Test
    void estimateGaCost() throws Exception {

        Mockito.when(expenseEstimatorService.saveGaExpenseEstimator("half", "T00700187", "fall_2024", "wlz999")).thenReturn(gaEstimationBreakUpEntity);

        this.mockMvc.perform(get("/v1/estimate/ga")
                .contentType(MediaType.APPLICATION_JSON)
                .param("ga-type","half")
                .param("semester","fall_2024")
                .param("studentId","T00700187")
                .header("eid","wlz999"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void estimateGeneralFullTimeCost() throws Exception {

        Mockito.when(expenseEstimatorService.saveFullTimeRoleEstimator("SOFTWARE_ENGINEER_DOTNET", 6, 450,1.25, "ADMIN")).thenReturn(fullTimeEstimationBreakUpEntity);

        this.mockMvc.perform(get("/v1/estimate/fulltime")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("role","SOFTWARE_ENGINEER_DOTNET")
                        .param("durationInMonths","6")
                        .param("bonus","450")
                        .param("interviewScore","1.25")
                        .header("empRole","ADMIN"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void estimateGeneralFullTimeCostException() throws Exception {

        Mockito.when(expenseEstimatorService.saveFullTimeRoleEstimator("SOFTWARE_ENGINEER_DOTNET", 6, 450,1.25, "USER")).thenThrow(new CustomException("You are not authorized to perform this action. Please contact your system administrator.", "E321"));

        this.mockMvc.perform(get("/v1/estimate/fulltime")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("role","SOFTWARE_ENGINEER_DOTNET")
                        .param("durationInMonths","6")
                        .param("bonus","450")
                        .param("interviewScore","1.25")
                        .header("empRole","USER"))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }
}