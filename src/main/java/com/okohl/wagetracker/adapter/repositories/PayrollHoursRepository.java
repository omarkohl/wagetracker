package com.okohl.wagetracker.adapter.repositories;

import java.util.List;
import java.time.YearMonth;

import org.springframework.data.repository.ListCrudRepository;

public interface PayrollHoursRepository extends ListCrudRepository<PayrollHoursEntity, Long> {
    public List<PayrollHoursEntity> findByMonth(YearMonth month);
}
