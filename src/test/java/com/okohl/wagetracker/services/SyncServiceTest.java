package com.okohl.wagetracker.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.okohl.wagetracker.domain.Employee;
import com.okohl.wagetracker.domain.PayrollHours;
import com.okohl.wagetracker.domain.PayrollHoursStatus;
import com.okohl.wagetracker.domain.WorkPeriod;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.List;
import java.time.Instant;
import java.time.YearMonth;

public class SyncServiceTest {
    @Test
    void testSyncNoEmployees() {
        TimeTrackingService mockTimeTrackingService = Mockito.mock(TimeTrackingService.class);
        PayrollService mockPayrollService = Mockito.mock(PayrollService.class);

        when(mockTimeTrackingService.getEmployees()).thenReturn(List.of());

        var syncService = new SyncService(mockTimeTrackingService, mockPayrollService);
        syncService.sync();
        verify(mockTimeTrackingService).getEmployees();
        verify(mockPayrollService, Mockito.never()).addPayrollHours(Mockito.any());
        verify(mockPayrollService, Mockito.never()).updatePayrollHours(Mockito.any());
    }

    @Test
    void testSync() {
        TimeTrackingService mockTimeTrackingService = Mockito.mock(TimeTrackingService.class);
        PayrollService mockPayrollService = Mockito.mock(PayrollService.class);

        var esmeralda = new Employee(1L, "Esmeralda Ponce");
        var elina = new Employee(3L, "Elina Zavala");
        when(mockTimeTrackingService.getEmployees()).thenReturn(List.of(esmeralda, elina));
        when(mockTimeTrackingService.getWorkPeriods(esmeralda)).thenReturn(List.of(
                new WorkPeriod(100L, Instant.parse("2024-01-01T08:00:00Z"), Instant.parse("2024-01-01T16:00:00Z")),
                new WorkPeriod(101L, Instant.parse("2024-02-02T08:00:00Z"), Instant.parse("2024-02-02T16:00:00Z"))));
        when(mockTimeTrackingService.getWorkPeriods(elina)).thenReturn(List.of(
                new WorkPeriod(102L, Instant.parse("2024-01-01T08:00:00Z"), Instant.parse("2024-01-01T16:00:00Z")),
                new WorkPeriod(103L, Instant.parse("2024-03-03T08:00:00Z"), Instant.parse("2024-03-03T16:00:00Z"))));

        var syncService = new SyncService(mockTimeTrackingService, mockPayrollService);
        syncService.sync();
        verify(mockTimeTrackingService).getEmployees();
        verify(mockTimeTrackingService).getWorkPeriods(esmeralda);
        verify(mockTimeTrackingService).getWorkPeriods(elina);
        verify(mockPayrollService, times(4)).addPayrollHours(any(PayrollHours.class));
    }
}
