package com.fingrant.FinanceMgmtGrant.repository;

import com.fingrant.FinanceMgmtGrant.entity.EstimationBreakUpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstimationBreakUpEntityRepository extends JpaRepository<EstimationBreakUpEntity, Long> {

    boolean existsByStudentIdAndSemesterName(String studentId, String semesterName);
    Optional<EstimationBreakUpEntity> findByStudentIdAndSemesterName(String studentId, String semesterName);

}
