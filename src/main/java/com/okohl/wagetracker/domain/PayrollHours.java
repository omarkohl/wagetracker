package com.okohl.wagetracker.domain;

import java.time.YearMonth;

/**
 * The hours worked by an employee in a given month.
 * <p>
 * The status of the payroll is also recorded.
 * <p>
 */
public record PayrollHours(
        Long id,
        YearMonth month,
        Employee employee,
        Float hours,
        PayrollHoursStatus status) {
}
