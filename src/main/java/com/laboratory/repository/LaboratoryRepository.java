package com.laboratory.repository;

import com.laboratory.model.Laboratory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaboratoryRepository extends SoftDeleteRepository<Laboratory, Long> {

    @Query("select l from Laboratory l join l.laboratoryExams la " +
            "where lower(la.exam.name) like lower(concat('%', :exam, '%')) and la.exam.removed = false")
    List<Laboratory> findByExam(@Param("exam") String exam);
}
