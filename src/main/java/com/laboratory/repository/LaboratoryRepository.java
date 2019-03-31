package com.laboratory.repository;

import com.laboratory.model.Laboratory;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface LaboratoryRepository extends SoftDeleteRepository<Laboratory, Long> {

    Collection<Laboratory> findByIdIn(Collection<Long> ids);
}
