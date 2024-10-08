package com.fingrant.FinanceMgmtGrant.repository;

import com.fingrant.FinanceMgmtGrant.entity.FullTimeEstimationBreakUpEntity;
import com.fingrant.FinanceMgmtGrant.entity.GaEstimationBreakUpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FullTimeEstimationBreakUpEntityRepository extends JpaRepository<FullTimeEstimationBreakUpEntity, Long> {

    boolean existsByRoleNameAndDurationInMonths(String roleName, int durationInMonths);
    Optional<FullTimeEstimationBreakUpEntity> findByRoleNameAndDurationInMonths(String roleName, int durationInMonths);

}
