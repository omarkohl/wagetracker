package com.okohl.wagetracker.adapter.repositories;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class WorkPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Instant start;
    private Instant end;

    public WorkPeriod() {
    }

    public WorkPeriod(Long id, Instant start, Instant end) {
        this.id = id;
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
}
