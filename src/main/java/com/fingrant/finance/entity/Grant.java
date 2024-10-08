package com.fingrant.finance.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "grants")
@Data
public class Grant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String grantName;

    @Column(nullable = false)
    private String fundingAgency;

    @Column(nullable = false)
    private String projectDescription;

    @Column(nullable = false)
    private BigDecimal totalBudget;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;
}
