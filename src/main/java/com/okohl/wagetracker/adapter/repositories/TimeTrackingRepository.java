package com.okohl.wagetracker.adapter.repositories;

import org.springframework.data.repository.ListCrudRepository;

public interface TimeTrackingRepository extends ListCrudRepository<WorkPeriod, Long> {

}
