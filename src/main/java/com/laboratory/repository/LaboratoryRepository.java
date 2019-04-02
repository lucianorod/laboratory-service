package com.laboratory.repository;

import com.laboratory.model.Laboratory;
import org.springframework.stereotype.Repository;

@Repository
public interface LaboratoryRepository extends SoftDeleteRepository<Laboratory, Long> {
}
