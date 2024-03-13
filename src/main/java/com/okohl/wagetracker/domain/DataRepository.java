package com.okohl.wagetracker.domain;

import java.util.List;
import java.time.YearMonth;

public interface DataRepository {
    public List<WorkPeriod> getWorkPeriods(Employee employee);
    public WorkPeriod addWorkPeriod(Employee employee, WorkPeriod workPeriod);
    public List<PayrollHours> getPayrollHours(YearMonth month);
    public List<PayrollHours> getPayrollHours(YearMonth month, Employee employee);
}
