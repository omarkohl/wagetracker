package com.okohl.wagetracker.adapter.repositories;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

public interface TimeTrackingRepository extends ListCrudRepository<WorkPeriodEntity, Long> {
    public List<WorkPeriodEntity> findByEmployeeId(Long employeeId);
}
