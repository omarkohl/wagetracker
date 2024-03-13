package com.okohl.wagetracker.adapter.repositories;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

public interface TimeTrackingRepository extends ListCrudRepository<WorkPeriod, Long> {
    public List<WorkPeriod> findByEmployeeId(Long employeeId);
}
