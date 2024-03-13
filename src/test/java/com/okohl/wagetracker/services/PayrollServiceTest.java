package com.okohl.wagetracker.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.YearMonth;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.okohl.wagetracker.domain.DataRepository;

public class PayrollServiceTest {
    @Test
    void testGetPayrollHours() {
        DataRepository mockRepository = Mockito.mock(DataRepository.class);
        var service = new PayrollService(mockRepository);
        var payrollHours = service.getPayrollHours(YearMonth.parse("2024-01"));
        assertEquals(0, payrollHours.size());
    }
}
