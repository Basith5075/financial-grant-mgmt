package com.fingrant.FinanceMgmtGrant.entity;

public enum FullTimeEstimationBreakUp {


    SOFTWARE_ENGINEER_JAVA(1L,5833.0, 667.0, 292.0, 446.0, 35.0, 83.0),
    SR_SOFTWARE_ENGINEER_JAVA(3L,7833.0, 667.0, 552.0, 446.0, 35.0, 83.0),
    SOFTWARE_ENGINEER_DOTNET(2L,4558.0, 667.0, 292.0, 446.0, 35.0, 83.0),
    SR_SOFTWARE_ENGINEER_DOTNET(4L,6768.0, 667.0, 292.0, 446.0, 35.0, 83.0);

    private Long id;
    private double salaryWages;
    private double healthInsurance;
    private double retirementContributions;
    private double payrollTaxes;
    private double unemploymentInsurance;
    private double workersCompensationInsurance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getSalaryWages() {
        return salaryWages;
    }

    public void setSalaryWages(double salaryWages) {
        this.salaryWages = salaryWages;
    }

    public double getHealthInsurance() {
        return healthInsurance;
    }

    public void setHealthInsurance(double healthInsurance) {
        this.healthInsurance = healthInsurance;
    }

    public double getRetirementContributions() {
        return retirementContributions;
    }

    public void setRetirementContributions(double retirementContributions) {
        this.retirementContributions = retirementContributions;
    }

    public double getPayrollTaxes() {
        return payrollTaxes;
    }

    public void setPayrollTaxes(double payrollTaxes) {
        this.payrollTaxes = payrollTaxes;
    }

    public double getUnemploymentInsurance() {
        return unemploymentInsurance;
    }

    public void setUnemploymentInsurance(double unemploymentInsurance) {
        this.unemploymentInsurance = unemploymentInsurance;
    }

    public double getWorkersCompensationInsurance() {
        return workersCompensationInsurance;
    }

    public void setWorkersCompensationInsurance(double workersCompensationInsurance) {
        this.workersCompensationInsurance = workersCompensationInsurance;
    }

    FullTimeEstimationBreakUp(Long id, double salaryWages, double healthInsurance, double retirementContributions, double payrollTaxes, double unemploymentInsurance, double workersCompensationInsurance) {
        this.id = id;
        this.salaryWages = salaryWages;
        this.healthInsurance = healthInsurance;
        this.retirementContributions = retirementContributions;
        this.payrollTaxes = payrollTaxes;
        this.unemploymentInsurance = unemploymentInsurance;
        this.workersCompensationInsurance = workersCompensationInsurance;
    }
}
