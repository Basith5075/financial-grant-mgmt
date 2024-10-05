package com.fingrant.FinanceMgmtGrant.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "estimation_break_up")
@Data
public class EstimationBreakUpEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String studentId;
    private double tuitionFee;
    private double creditFee;
    private double biWeeklyWages;
    private double insurance;
    private int numberOfWeeks;
    private String semesterName;
    private LocalDate savedDate;
    private LocalDate updatedDate;
    private String savingEmployee;
}
