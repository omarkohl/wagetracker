package com.okohl.wagetracker.domain;

import java.time.Instant;

/**
 * A period of time worked by an employee.
 * <p>
 * The start and end times of the work period are recorded.
 * <p>
 */
public record WorkPeriod(
        Long id,
        Instant start,
        Instant end) {
}
