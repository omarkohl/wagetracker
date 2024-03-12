package com.okohl.wagetracker.adapter;

import org.springframework.web.bind.annotation.RestController;

import com.okohl.wagetracker.domain.WorkPeriod;
import com.okohl.wagetracker.services.TimeTrackingService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/v0/time-tracking")
public class WorkPeriodController {

    private final TimeTrackingService timeTrackingService;

    public WorkPeriodController(TimeTrackingService timeTrackingService) {
        this.timeTrackingService = timeTrackingService;
    }

    @GetMapping("/{employeeId}/work-periods")
    public List<WorkPeriod> getWorkPeriods(@PathVariable("employeeId") Long employeeId) {
        return timeTrackingService.getWorkPeriods(employeeId);
    }
}
