package com.okohl.wagetracker.services;

import org.springframework.stereotype.Service;

import com.okohl.wagetracker.domain.WorkPeriod;
import com.okohl.wagetracker.domain.DataRepository;
import com.okohl.wagetracker.domain.Employee;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.List;

@Service
public class TimeTrackingService {

    private static final Logger log = LoggerFactory.getLogger(TimeTrackingService.class);

    private DataRepository repository;

    public TimeTrackingService(DataRepository repository) {
        this.repository = repository;
    }

    public List<WorkPeriod> getWorkPeriods(Long employeeId) {
        return this.repository.getWorkPeriods(new Employee(employeeId, ""));
    }
}
