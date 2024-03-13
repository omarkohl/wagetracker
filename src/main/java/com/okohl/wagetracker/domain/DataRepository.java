package com.okohl.wagetracker.domain;

import java.util.List;
import java.time.YearMonth;

public interface DataRepository {
    public List<WorkPeriod> getWorkPeriods(Employee employee);
    public WorkPeriod addWorkPeriod(Employee employee, WorkPeriod workPeriod);
    public PayrollHours getPayrollHours(Long id);
    public List<PayrollHours> getPayrollHours();
    public List<PayrollHours> getPayrollHours(YearMonth month);
    public PayrollHours getPayrollHours(YearMonth month, Employee employee);
    public PayrollHours addPayrollHours(PayrollHours payrollHours);
    public PayrollHours updatePayrollHours(PayrollHours payrollHours);
    public List<Employee> getEmployees();
}
