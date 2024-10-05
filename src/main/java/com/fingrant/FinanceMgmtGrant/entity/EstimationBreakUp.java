package com.fingrant.FinanceMgmtGrant.entity;

import lombok.Data;

public enum EstimationBreakUp {

    HALF_FALL_2024(1L, 10000.0, 300.0, 1500.0, 200.0, 16, "Fall 2024"),
    HALF_SPRING_2025(2L, 9500.0, 290.0, 1400.0, 180.0, 16, "Spring 2025");

    private Long id;
    private double tuitionFee;
    private double creditFee;
    private double biWeeklyWages;
    private double insurance;
    private int numberOfWeeks;
    private String semesterName;

    EstimationBreakUp(Long id, double tuitionFee, double creditFee, double biWeeklyWages, double insurance, int numberOfWeeks, String semesterName) {
        this.id = id;
        this.tuitionFee = tuitionFee;
        this.creditFee = creditFee;
        this.biWeeklyWages = biWeeklyWages;
        this.insurance = insurance;
        this.numberOfWeeks = numberOfWeeks;
        this.semesterName = semesterName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTuitionFee() {
        return tuitionFee;
    }

    public void setTuitionFee(double tuitionFee) {
        this.tuitionFee = tuitionFee;
    }

    public double getCreditFee() {
        return creditFee;
    }

    public void setCreditFee(double creditFee) {
        this.creditFee = creditFee;
    }

    public double getBiWeeklyWages() {
        return biWeeklyWages;
    }

    public void setBiWeeklyWages(double biWeeklyWages) {
        this.biWeeklyWages = biWeeklyWages;
    }

    public double getInsurance() {
        return insurance;
    }

    public void setInsurance(double insurance) {
        this.insurance = insurance;
    }

    public int getNumberOfWeeks() {
        return numberOfWeeks;
    }

    public void setNumberOfWeeks(int numberOfWeeks) {
        this.numberOfWeeks = numberOfWeeks;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }
}
