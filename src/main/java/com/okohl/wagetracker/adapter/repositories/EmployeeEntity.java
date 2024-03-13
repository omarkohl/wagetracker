package com.okohl.wagetracker.adapter.repositories;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "employee")
    private List<WorkPeriodEntity> workPeriods;

    private EmployeeEntity() {
    }

    public EmployeeEntity(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<WorkPeriodEntity> getWorkPeriods() {
        return workPeriods;
    }

    public void setWorkPeriods(List<WorkPeriodEntity> workPeriods) {
        this.workPeriods = workPeriods;
    }
}
