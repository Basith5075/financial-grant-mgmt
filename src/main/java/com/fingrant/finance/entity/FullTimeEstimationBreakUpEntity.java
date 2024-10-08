package com.fingrant.finance.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "full_time_estimation_breakup")
@Data
public class FullTimeEstimationBreakUpEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleName;
    private double salaryWages;
    private double bonusesAndCommissions;
    private double healthInsurance;
    private double retirementContributions;
    private double payrollTaxes;
    private double unemploymentInsurance;
    private double interviewScore;
    private int durationInMonths;
    private double workersCompensationInsurance;
    private double totalExpectedCost;
    private LocalDate savedDate;
    private LocalDate updatedDate;
    private String savingEmployee;

}
