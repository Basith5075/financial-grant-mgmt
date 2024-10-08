package com.fingrant.finance.repository;

import com.fingrant.finance.entity.FullTimeEstimationBreakUpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FullTimeEstimationBreakUpEntityRepository extends JpaRepository<FullTimeEstimationBreakUpEntity, Long> {

    boolean existsByRoleNameAndDurationInMonths(String roleName, int durationInMonths);
    Optional<FullTimeEstimationBreakUpEntity> findByRoleNameAndDurationInMonths(String roleName, int durationInMonths);

}
