package com.fingrant.finance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be left blank")
    @Column( name = "name",nullable = false, unique = true)
    private String name;

    @DecimalMin(message = "totalAmount cannot be left blank", value = "0")
    @Column(nullable = false)
    private BigDecimal totalAmount;

    @DecimalMin(message = "totalAmount cannot be left blank", value = "0")
    @Column(nullable = false)
    private BigDecimal personnelBudget;

    @Column(nullable = false)
    private BigDecimal equipmentBudget;

    private BigDecimal travelBudget;

    private BigDecimal suppliesBudget;

    private BigDecimal otherBudget;

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Min(message = "Cannot be left blank",value = 0)
    @Column(nullable = false)
    private Long grantId;

}
