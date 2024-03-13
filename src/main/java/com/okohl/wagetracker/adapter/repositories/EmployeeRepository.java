package com.okohl.wagetracker.adapter.repositories;

import org.springframework.data.repository.ListCrudRepository;

public interface EmployeeRepository extends ListCrudRepository<EmployeeEntity, Long> {
}
