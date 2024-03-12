package com.okohl.wagetracker.domain;

/**
 * An employee.
 * <p>
 * The employee's name and ID are recorded.
 * <p>
 */
public record Employee(Long id, String name) {
}
