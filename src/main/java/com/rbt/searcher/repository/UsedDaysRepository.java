package com.rbt.searcher.repository;

import com.rbt.searcher.model.entity.EmployeeEntity;
import com.rbt.searcher.model.entity.UsedDaysEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UsedDaysRepository extends JpaRepository<UsedDaysEntity, Long> {
    List<UsedDaysEntity> findAllByDateFromAfterAndDateToBeforeAndEmployee(LocalDate dateFrom, LocalDate dateTo, EmployeeEntity employee);
}
