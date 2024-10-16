package com.fingrant.finance.repository;

import com.fingrant.finance.entity.GaEstimationBreakUpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GaEstimationBreakUpEntityRepository extends JpaRepository<GaEstimationBreakUpEntity, Long> {

    boolean existsByStudentIdAndGaTypeSemesterName(String studentId, String semesterName);
    Optional<GaEstimationBreakUpEntity> findByStudentIdAndGaTypeSemesterName(String studentId, String semesterName);

}
