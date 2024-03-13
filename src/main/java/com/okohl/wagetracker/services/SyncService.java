package com.okohl.wagetracker.services;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.ArrayList;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import java.time.temporal.ChronoUnit;
import java.time.ZoneId;

import com.okohl.wagetracker.domain.PayrollHours;
import com.okohl.wagetracker.domain.PayrollHoursStatus;
import com.okohl.wagetracker.domain.WorkPeriod;

@Service
public class SyncService {
    private final Logger log = LoggerFactory.getLogger(SyncService.class);

    private final TimeTrackingService timeTrackingService;
    private final PayrollService payrollService;

    public SyncService(
            TimeTrackingService timeTrackingService,
            PayrollService payrollService) {
        this.timeTrackingService = timeTrackingService;
        this.payrollService = payrollService;
    }

    public void sync() {
        var employees = timeTrackingService.getEmployees();
        for (var employee : employees) {
            var workPeriods = timeTrackingService.getWorkPeriods(employee);
            // TODO a work period may cross the month boundary! This is
            // currently not implemented but rather the total time would be
            // added to the month of the starting time. The correct solution
            // would presumably be to split the work period into two.
            Map<YearMonth, List<WorkPeriod>> workPeriodsByMonth = new HashMap<>();
            for (var workPeriod : workPeriods) {
                var start = workPeriod.start();
                var startWithZone = start.atZone(ZoneId.of("UTC"));
                YearMonth yearMonth = YearMonth.from(startWithZone);
                workPeriodsByMonth.computeIfAbsent(yearMonth, k -> new ArrayList<>()).add(workPeriod);
            }
            for (var entry : workPeriodsByMonth.entrySet()) {
                YearMonth yearMonth = entry.getKey();
                List<WorkPeriod> workPeriodsInMonth = entry.getValue();
                float totalHours = (workPeriodsInMonth.stream()
                        .mapToLong(wp -> ChronoUnit.SECONDS.between(wp.start(), wp.end()))
                        .sum()) / 3600.0f;
                var existingpH = payrollService.getPayrollHours(yearMonth, employee);
                if (existingpH == null) {
                    // add new payroll hours
                    var newPh = new PayrollHours(null, yearMonth, employee, totalHours, PayrollHoursStatus.UNPROCESSED);
                    payrollService.addPayrollHours(newPh);
                } else {
                    var newPh = new PayrollHours(existingpH.id(), null, null, totalHours, null);
                    payrollService.updatePayrollHours(newPh);
                }
            }
        }
    }
}
