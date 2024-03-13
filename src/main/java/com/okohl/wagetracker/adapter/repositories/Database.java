package com.okohl.wagetracker.adapter.repositories;

import java.util.List;
import java.util.ArrayList;
import java.time.YearMonth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.okohl.wagetracker.domain.Employee;
import com.okohl.wagetracker.domain.PayrollHours;
import com.okohl.wagetracker.domain.ResourceNotFoundException;
import com.okohl.wagetracker.domain.DataRepository;
import com.okohl.wagetracker.domain.WorkPeriod;

@Repository
@Primary
public class Database implements DataRepository {

    private static final Logger log = LoggerFactory.getLogger(Database.class);

    private final EmployeeRepository employeeRepository;
    private final TimeTrackingRepository timeTrackingRepository;
    private final PayrollHoursRepository payrollHoursRepository;

    public Database(
            EmployeeRepository employeeRepository,
            TimeTrackingRepository timeTrackingRepository,
            PayrollHoursRepository payrollHoursRepository) {
        this.employeeRepository = employeeRepository;
        this.timeTrackingRepository = timeTrackingRepository;
        this.payrollHoursRepository = payrollHoursRepository;
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
            throw new ResourceNotFoundException("Employee " + employee.id() + " not found");
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

    @Override
    public List<PayrollHours> getPayrollHours(YearMonth month) {
        var payrollHours = new ArrayList<PayrollHours>();
        var payrollHoursDb = this.payrollHoursRepository.findByMonth(month);
        for (var ph : payrollHoursDb) {
            var employeeDb = ph.getEmployee();
            var employee = new Employee(employeeDb.getId(), employeeDb.getName());
            payrollHours.add(new PayrollHours(
                    ph.getId(),
                    ph.getMonth(),
                    employee,
                    ph.getHours(),
                    ph.getStatus()));
        }
        return payrollHours;
    }

    @Override
    public List<PayrollHours> getPayrollHours(YearMonth month, Employee employee) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPayrollHours'");
    }

    @Override
    public PayrollHours addPayrollHours(PayrollHours payrollHours) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addPayrollHours'");
    }

    @Override
    public PayrollHours updatePayrollHours(PayrollHours payrollHours) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePayrollHours'");
    }
}
