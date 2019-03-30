package com.laboratory.repository;

import com.laboratory.model.Exam;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends SoftDeleteRepository<Exam, Long> {
}
