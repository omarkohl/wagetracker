package com.okohl.wagetracker.adapter.controller;

import org.springframework.web.bind.annotation.RestController;

import com.okohl.wagetracker.domain.PayrollHours;
import com.okohl.wagetracker.services.PayrollService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/v0/payroll")
public class PayrollController {

    private final PayrollService payrollService;

    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    @GetMapping("")
    public List<PayrollHours> getPayrollHours() {
        return payrollService.getPayrollHours();
    }
}
