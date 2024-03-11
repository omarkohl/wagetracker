package com.okohl.wagetracker;

import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class WordPeriodController {

    @GetMapping("/work-periods")
    public List<WorkPeriod> getWorkPeriods() {
        return List.of(
                new WorkPeriod(Instant.parse("2022-01-01T08:00:00Z"), Instant.parse("2022-01-01T16:00:00Z")),
                new WorkPeriod(Instant.parse("2022-01-02T08:00:00Z"), Instant.parse("2022-01-02T16:00:00Z")),
                new WorkPeriod(Instant.parse("2022-01-03T08:00:00Z"), Instant.parse("2022-01-03T16:00:00Z")));
    }
}
