package com.okohl.wagetracker.domain;

import java.util.List;

public interface DataRepository {
    public List<WorkPeriod> getWorkPeriods(Employee employee);
    public WorkPeriod addWorkPeriod(Employee employee, WorkPeriod workPeriod);
}
