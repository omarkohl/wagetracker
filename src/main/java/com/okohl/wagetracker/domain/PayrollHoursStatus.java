package com.okohl.wagetracker.domain;

/**
 * The status of a payroll.
 * <p>
 * The possible statuses are:
 * <ul>
 * <li>UNPROCESSED: The payroll has not been processed yet.</li>
 * <li>CONFIRMED: The payroll has been confirmed by HR.</li>
 * <li>COMPLETED: The payroll has been completed and the employee has been
 * paid.</li>
 * </ul>
 * <p>
 */
public enum PayrollHoursStatus {
    UNPROCESSED,
    CONFIRMED,
    COMPLETED,
}
