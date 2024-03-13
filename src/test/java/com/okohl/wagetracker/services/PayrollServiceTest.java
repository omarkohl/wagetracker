package com.okohl.wagetracker.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.YearMonth;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;

import com.okohl.wagetracker.domain.DataRepository;
import com.okohl.wagetracker.domain.PayrollHours;
import com.okohl.wagetracker.domain.PayrollHoursStatus;
import com.okohl.wagetracker.domain.Employee;

public class PayrollServiceTest {
    @Test
    void testGetPayrollHours() {
        DataRepository mockRepository = Mockito.mock(DataRepository.class);
        when(mockRepository.getPayrollHours(any(YearMonth.class))).thenReturn(
                List.of(
                        new PayrollHours(
                                100L,
                                YearMonth.parse("2024-01"),
                                new Employee(1L, "John Doe"),
                                40.0f,
                                PayrollHoursStatus.UNPROCESSED),
                        new PayrollHours(
                                101L,
                                YearMonth.parse("2024-01"),
                                new Employee(2L, "Jane Doe"),
                                40.0f,
                                PayrollHoursStatus.UNPROCESSED)));

        var service = new PayrollService(mockRepository);
        var payrollHours = service.getPayrollHours(YearMonth.parse("2024-01"));
        assertEquals(2, payrollHours.size());
    }

    @Test
    void testGetPayrollHoursWithEmployee() {
        DataRepository mockRepository = Mockito.mock(DataRepository.class);
        when(mockRepository.getPayrollHours(any(YearMonth.class), any(Employee.class))).thenReturn(
                List.of(
                        new PayrollHours(
                                100L,
                                YearMonth.parse("2024-01"),
                                new Employee(1L, "John Doe"),
                                40.0f,
                                PayrollHoursStatus.UNPROCESSED),
                        new PayrollHours(
                                101L,
                                YearMonth.parse("2024-01"),
                                new Employee(1L, "John Doe"),
                                40.0f,
                                PayrollHoursStatus.UNPROCESSED)));

        var service = new PayrollService(mockRepository);
        var payrollHours = service.getPayrollHours(YearMonth.parse("2024-01"), new Employee(100L, ""));
        assertEquals(2, payrollHours.size());
    }

    @Test
    void testAddPayrollHours() {
        DataRepository mockRepository = Mockito.mock(DataRepository.class);
        when(mockRepository.addPayrollHours(any(PayrollHours.class))).thenReturn(
                new PayrollHours(
                        100L,
                        YearMonth.parse("2024-01"),
                        new Employee(1L, "John Doe"),
                        40.0f,
                        PayrollHoursStatus.UNPROCESSED));

        var service = new PayrollService(mockRepository);
        var payrollHours = new PayrollHours(
                null,
                YearMonth.parse("2024-01"),
                new Employee(1L, "John Doe"),
                40.0f,
                PayrollHoursStatus.UNPROCESSED);
        PayrollHours ph2 = service.addPayrollHours(payrollHours);
        assertEquals(100L, ph2.id());
        // verify mockRepository was called
        verify(mockRepository).addPayrollHours(eq(payrollHours));
    }

    @Test
    void testUpdatePayrollHours() {
        DataRepository mockRepository = Mockito.mock(DataRepository.class);
        when(mockRepository.updatePayrollHours(any(PayrollHours.class))).thenReturn(
                new PayrollHours(
                        100L,
                        YearMonth.parse("2024-01"),
                        new Employee(1L, "John Doe"),
                        40.0f,
                        PayrollHoursStatus.UNPROCESSED));

        var service = new PayrollService(mockRepository);
        var payrollHours = new PayrollHours(
                100L,
                YearMonth.parse("2024-01"),
                new Employee(1L, "John Doe"),
                40.0f,
                PayrollHoursStatus.UNPROCESSED);
        PayrollHours ph2 = service.updatePayrollHours(payrollHours);
        assertEquals(100L, ph2.id());
        // verify mockRepository was called
        verify(mockRepository).updatePayrollHours(eq(payrollHours));
    }
}
