package com.laboratory.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Optional;


@NoRepositoryBean
interface SoftDeleteRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    @Query("select e from #{#entityName} e where e.id = ?1 and e.removed=false")
    @Override
    Optional<T> findById(ID id);

    @Modifying
    @Query("update #{#entityName} e set e.removed=true where e.id=?1")
    @Override
    void deleteById(ID id);
}
