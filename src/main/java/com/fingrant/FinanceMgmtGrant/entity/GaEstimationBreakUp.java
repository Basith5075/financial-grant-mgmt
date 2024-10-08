package com.fingrant.FinanceMgmtGrant.entity;

public enum GaEstimationBreakUp {

    HALF_FALL_2024(1L, 3500.0, 1555.0, 750.0, 200.0, 16, "Fall 2024",250),
    FULL_FALL_2024(3L, 3500.0, 3210.0, 1500.0, 400.0, 16, "Fall 2024",500),
    HALF_SPRING_2025(2L, 3500.0, 1555.0, 750.0, 200.0, 16, "Fall 2024",250),
    FULL_SPRING_2024(4L, 3500.0, 3210.0, 1500.0, 400.0, 16, "Fall 2024",500);

    private Long id;
    private double tuitionFee;
    private double creditFee;
    private double biWeeklyWages;
    private double insurance;
    private int numberOfWeeks;
    private String semesterName;
    private double miscellaneous;

    GaEstimationBreakUp(Long id, double tuitionFee, double creditFee, double biWeeklyWages, double insurance, int numberOfWeeks, String semesterName, double miscellaneous) {
        this.id = id;
        this.tuitionFee = tuitionFee;
        this.creditFee = creditFee;
        this.biWeeklyWages = biWeeklyWages;
        this.insurance = insurance;
        this.numberOfWeeks = numberOfWeeks;
        this.semesterName = semesterName;
        this.miscellaneous = miscellaneous;
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

    public double getMiscellaneous() {
        return miscellaneous;
    }

    public void setMiscellaneous(double miscellaneous) {
        this.miscellaneous = miscellaneous;
    }
}
