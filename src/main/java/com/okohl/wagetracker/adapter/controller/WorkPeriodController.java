package com.okohl.wagetracker.adapter.controller;

import org.springframework.web.bind.annotation.RestController;

import com.okohl.wagetracker.domain.Employee;
import com.okohl.wagetracker.domain.WorkPeriod;
import com.okohl.wagetracker.services.TimeTrackingService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import java.net.URI;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/v0/time-tracking")
public class WorkPeriodController {

    // Note this is duplicating the RequestMapping above, which is not good.
    // Needs a better solution.
    private final String baseURL = "/v0/time-tracking/";

    private final TimeTrackingService timeTrackingService;

    public WorkPeriodController(TimeTrackingService timeTrackingService) {
        this.timeTrackingService = timeTrackingService;
    }

    @GetMapping("/{employeeId}/work-periods")
    public List<WorkPeriod> getWorkPeriods(@PathVariable("employeeId") Long employeeId) {
        return timeTrackingService.getWorkPeriods(new Employee(employeeId, ""));
    }

    @PostMapping("/{employeeId}/work-periods")
    public ResponseEntity<WorkPeriod> addWorkPeriod(
            @PathVariable("employeeId") Long employeeId,
            @RequestBody WorkPeriod workPeriod) {
        var createdWorkPeriod = timeTrackingService.addWorkPeriod(new Employee(employeeId, ""), workPeriod);
        var createdUri = URI.create(this.baseURL + employeeId + "/work-periods/" + createdWorkPeriod.id());
        return ResponseEntity.created(createdUri).body(createdWorkPeriod);
    }
}
