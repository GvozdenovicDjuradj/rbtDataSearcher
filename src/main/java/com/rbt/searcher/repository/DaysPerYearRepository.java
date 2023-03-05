package com.rbt.searcher.repository;

import com.rbt.searcher.model.entity.DaysPerYearEntity;
import com.rbt.searcher.model.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DaysPerYearRepository extends JpaRepository<DaysPerYearEntity, Long> {
    Optional<DaysPerYearEntity> findByEmployeeAndYear(EmployeeEntity employee, int year);
}
