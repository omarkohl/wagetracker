package com.okohl.wagetracker.services;

import org.springframework.stereotype.Service;

import com.okohl.wagetracker.domain.WorkPeriod;
import com.okohl.wagetracker.domain.DataRepository;
import com.okohl.wagetracker.domain.Employee;
import java.time.Instant;

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

    public List<WorkPeriod> getWorkPeriods(Employee employee) {
        return this.repository.getWorkPeriods(employee);
    }

    public WorkPeriod addWorkPeriod(Employee employee, WorkPeriod workPeriod) {
        if (!workPeriod.start().isBefore(workPeriod.end())) {
            throw new IllegalArgumentException("End must be after start");
        }
        var now = Instant.now();
        if (workPeriod.start().isAfter(now) || workPeriod.end().isAfter(now)) {
            throw new IllegalArgumentException("Work period cannot be in the future");
        }
        return this.repository.addWorkPeriod(employee, workPeriod);
    }

    public List<Employee> getEmployees() {
        return this.repository.getEmployees();
    }
}
