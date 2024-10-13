package com.fingrant.finance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "ga_estimation_break_up")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GaEstimationBreakUpEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String studentId;
    private double tuitionFee;
    private double creditFee;
    private double biWeeklyWages;
    private double insurance;
    private int numberOfWeeks;
    private double miscellaneous;
    private String gaTypeSemesterName;
    private LocalDate savedDate;
    private LocalDate updatedDate;
    private String savingEmployee;
    private double totalExpectedCost;

    @Override
    public String toString() {
        return "EstimationBreakUpEntity{" +
                "id=" + id +
                ", studentId='" + studentId + '\'' +
                ", tuitionFee=" + tuitionFee +
                ", creditFee=" + creditFee +
                ", biWeeklyWages=" + biWeeklyWages +
                ", insurance=" + insurance +
                ", numberOfWeeks=" + numberOfWeeks +
                ", miscellaneous=" + miscellaneous +
                ", semesterName='" + gaTypeSemesterName + '\'' +
                ", savedDate=" + savedDate +
                ", updatedDate=" + updatedDate +
                ", savingEmployee='" + savingEmployee + '\'' +
                ", totalExpectedCost=" + totalExpectedCost +
                '}';
    }
}
