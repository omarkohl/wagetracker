package com.okohl.wagetracker.services;

import org.springframework.stereotype.Service;

import com.okohl.wagetracker.adapter.repositories.EmployeeRepository;
import com.okohl.wagetracker.adapter.repositories.TimeTrackingRepository;
import com.okohl.wagetracker.domain.WorkPeriod;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimeTrackingService {

    private static final Logger log = LoggerFactory.getLogger(TimeTrackingService.class);

    private final TimeTrackingRepository timeTrackingRepository;
    private final EmployeeRepository employeeRepository;

    public TimeTrackingService(
            TimeTrackingRepository timeTrackingRepository,
            EmployeeRepository employeeRepository) {
        this.timeTrackingRepository = timeTrackingRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<WorkPeriod> getWorkPeriods(Long employeeId) {
        if (employeeId == null) {
            return List.of();
        }
        var employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            log.info("Found employee: " + employee.get().getName());
            var workPeriods = employee.get().getWorkPeriods();
            // convert the DB work periods to domain WorkPeriods. TODO should not be done
            // here in the Service, separation of concerns.
            var ret = new ArrayList<WorkPeriod>();
            for (var wp : workPeriods) {
                ret.add(new WorkPeriod(
                        wp.getId(),
                        wp.getStart(),
                        wp.getEnd()));
            }
            return ret;
        } else {
            return List.of();
        }
    }
}
