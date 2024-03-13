package com.okohl.wagetracker.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import com.okohl.wagetracker.domain.DataRepository;
import com.okohl.wagetracker.domain.PayrollHours;
import com.okohl.wagetracker.domain.PayrollHoursStatus;
import com.okohl.wagetracker.domain.ResourceNotFoundException;
import com.okohl.wagetracker.domain.Employee;
import java.time.YearMonth;

@Service
public class PayrollService {
    public final Logger log = LoggerFactory.getLogger(PayrollService.class);
    public final DataRepository repository;

    public PayrollService(DataRepository repository) {
        this.repository = repository;
    }

    public List<PayrollHours> getPayrollHours() {
        return repository.getPayrollHours();
    }

    public List<PayrollHours> getPayrollHours(YearMonth month) {
        return repository.getPayrollHours(month);
    }

    public PayrollHours getPayrollHours(YearMonth month, Employee employee) {
        return repository.getPayrollHours(month, employee);
    }

    public PayrollHours addPayrollHours(PayrollHours payrollHours) {
        return repository.addPayrollHours(payrollHours);
    }

    public PayrollHours updatePayrollHours(PayrollHours payrollHours) {
        if (payrollHours == null || payrollHours.id() == null) {
            throw new IllegalArgumentException("PayrollHours and its id cannot be null");
        }
        var existingPh = repository.getPayrollHours(payrollHours.id());
        if (existingPh == null) {
            throw new ResourceNotFoundException("PayrollHours with id " + payrollHours.id() + " not found");
        }
        if (existingPh.status() == PayrollHoursStatus.COMPLETED) {
            throw new IllegalArgumentException("Cannot update a completed PayrollHours");
        }
        var id = existingPh.id();
        var month = existingPh.month();
        var employee = existingPh.employee();
        var totalHours = existingPh.hours();
        var status = existingPh.status();
        if (payrollHours.hours() != null) {
            totalHours = payrollHours.hours();
        }
        if (payrollHours.status() != null) {
            status = payrollHours.status();
        }
        return repository.updatePayrollHours(new PayrollHours(id, month, employee, totalHours, status));
    }
}
