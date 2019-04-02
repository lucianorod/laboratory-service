package com.laboratory.repository;

import com.laboratory.model.LaboratoryExam;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LaboratoryExamRepository extends SoftDeleteRepository<LaboratoryExam, Long> {

    Optional<LaboratoryExam> findByLaboratoryIdAndExamId(Long laboratoryId, Long examId);
}
