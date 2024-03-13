package com.okohl.wagetracker.adapter.repositories;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class WorkPeriodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* 'end' is a reserved keyword so we rename these two columns */
    @Column(name = "start_time")
    private Instant start;
    @Column(name = "end_time")
    private Instant end;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    private WorkPeriodEntity() {
    }

    public WorkPeriodEntity(EmployeeEntity employee, Instant start, Instant end) {
        this.employee = employee;
        this.start = start;
        this.end = end;
    }

    public Long getId() {
        return id;
    }

    public Instant getStart() {
        return start;
    }

    public Instant getEnd() {
        return end;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }
}
