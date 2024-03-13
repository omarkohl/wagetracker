package com.okohl.wagetracker.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import com.okohl.wagetracker.domain.DataRepository;
import com.okohl.wagetracker.domain.PayrollHours;
import com.okohl.wagetracker.domain.Employee;
import java.time.YearMonth;

@Service
public class PayrollService {
    public final Logger log = LoggerFactory.getLogger(PayrollService.class);
    public final DataRepository repository;

    public PayrollService(DataRepository repository) {
        this.repository = repository;
    }

    public List<PayrollHours> getPayrollHours(YearMonth month) {
        return repository.getPayrollHours(month);
    }

    public List<PayrollHours> getPayrollHours(YearMonth month, Employee employee) {
        return repository.getPayrollHours(month, employee);
    }
}
