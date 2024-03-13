package com.okohl.wagetracker.adapter.controller;

import org.springframework.web.bind.annotation.RestController;

import com.okohl.wagetracker.domain.PayrollHours;
import com.okohl.wagetracker.services.PayrollService;

import java.util.List;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/v0/payroll")
public class PayrollController {

    // Note this is duplicating the RequestMapping above, which is not good.
    // Needs a better solution.
    private final String baseURL = "/v0/payroll";

    private final PayrollService payrollService;

    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    @GetMapping("")
    public List<PayrollHours> getPayrollHours() {
        return payrollService.getPayrollHours();
    }

    @PostMapping("")
    public ResponseEntity<PayrollHours> addPayrollHours(@RequestBody PayrollHours payrollHours) {
        var createdPayrollHours = payrollService.addPayrollHours(payrollHours);
        var createdUri = URI.create(this.baseURL + "/" + createdPayrollHours.id());
        return ResponseEntity.created(createdUri).body(createdPayrollHours);
    }
}
