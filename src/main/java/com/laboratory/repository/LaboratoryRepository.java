package com.laboratory.repository;

import com.laboratory.model.Laboratory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaboratoryRepository extends JpaRepository<Laboratory, Long> {
}
