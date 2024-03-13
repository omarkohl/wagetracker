package com.okohl.wagetracker.adapter.repositories;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.okohl.wagetracker.domain.Employee;
import com.okohl.wagetracker.domain.DataRepository;
import com.okohl.wagetracker.domain.WorkPeriod;

@Repository
@Primary
public class Database implements DataRepository {

    private static final Logger log = LoggerFactory.getLogger(Database.class);

    private final EmployeeRepository employeeRepository;
    private final TimeTrackingRepository timeTrackingRepository;

    public Database(EmployeeRepository employeeRepository, TimeTrackingRepository timeTrackingRepository) {
        this.employeeRepository = employeeRepository;
        this.timeTrackingRepository = timeTrackingRepository;
    }

    @Override
    public List<WorkPeriod> getWorkPeriods(Employee employee) {
        if (employee == null) {
            return List.of();
        }
        var employeeId = employee.id();
        var dbEmployee = employeeRepository.findById(employeeId);
        if (dbEmployee.isPresent()) {
            log.info("Found employee: " + dbEmployee.get().getName());
            var workPeriods = dbEmployee.get().getWorkPeriods();
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

    @Override
    public WorkPeriod addWorkPeriod(Employee employee, WorkPeriod workPeriod) {
        var dbEmployee = employeeRepository.findById(employee.id());
        if (!dbEmployee.isPresent()) {
            throw new IllegalArgumentException("Employee not found");
        }
        var dbWorkPeriod = new WorkPeriodEntity(dbEmployee.get(),
                workPeriod.start(),
                workPeriod.end());

        var savedWorkPeriod = timeTrackingRepository.save(dbWorkPeriod);
        return new WorkPeriod(
                savedWorkPeriod.getId(),
                savedWorkPeriod.getStart(),
                savedWorkPeriod.getEnd());
    }
}
