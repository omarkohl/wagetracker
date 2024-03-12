package com.okohl.wagetracker.services;

import org.springframework.stereotype.Service;

import com.okohl.wagetracker.domain.WorkPeriod;
import java.time.Instant;
import java.util.List;

@Service
public class TimeTrackingService {
    public List<WorkPeriod> getWorkPeriods(Long employeeId) {
        // For now, return a static list of work periods for the employee with the given
        // id
        if (employeeId == 1L) {
            return List.of(
                    new WorkPeriod(
                            100L,
                            Instant.parse("2022-01-01T08:00:00Z"),
                            Instant.parse("2022-01-01T16:00:00Z")),
                    new WorkPeriod(
                            101L,
                            Instant.parse("2022-01-02T08:00:00Z"),
                            Instant.parse("2022-01-02T16:00:00Z")),
                    new WorkPeriod(102L,
                            Instant.parse("2022-01-03T08:00:00Z"),
                            Instant.parse("2022-01-03T16:00:00Z")));
        } else {
            // If the employee id is not 1, return an empty list
            return List.of();
        }
    }
}
