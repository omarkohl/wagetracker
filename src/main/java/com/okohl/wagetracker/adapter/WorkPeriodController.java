package com.okohl.wagetracker.adapter;

import org.springframework.web.bind.annotation.RestController;

import com.okohl.wagetracker.domain.Employee;
import com.okohl.wagetracker.domain.WorkPeriod;

import java.time.Instant;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/v0/time-tracking")
public class WorkPeriodController {

    @GetMapping("/{employeeId}/work-periods")
    public List<WorkPeriod> getWorkPeriods(@PathVariable("employeeId") Long employeeId) {
        // For now, return a static list of work periods for the employee with the given
        // id
        if (employeeId == 1L) {
            var john = new Employee(1L, "John Doe");
            return List.of(
                    new WorkPeriod(
                            100L,
                            Instant.parse("2022-01-01T08:00:00Z"),
                            Instant.parse("2022-01-01T16:00:00Z"),
                            john),
                    new WorkPeriod(
                            101L,
                            Instant.parse("2022-01-02T08:00:00Z"),
                            Instant.parse("2022-01-02T16:00:00Z"),
                            john),
                    new WorkPeriod(102L,
                            Instant.parse("2022-01-03T08:00:00Z"),
                            Instant.parse("2022-01-03T16:00:00Z"),
                            john));
        } else {
            // If the employee id is not 1, return an empty list
            return List.of();
        }
    }
}
