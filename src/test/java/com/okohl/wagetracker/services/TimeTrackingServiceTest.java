package com.okohl.wagetracker.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import com.okohl.wagetracker.domain.DataRepository;
import com.okohl.wagetracker.domain.Employee;
import com.okohl.wagetracker.domain.WorkPeriod;
import java.util.List;
import java.time.Instant;

public class TimeTrackingServiceTest {

    @Test
    void testGetWorkPeriods() {
        DataRepository mockRepository = Mockito.mock(DataRepository.class);
        when(mockRepository.getWorkPeriods(any(Employee.class))).thenReturn(
                List.of(
                        new WorkPeriod(100L,
                                Instant.parse("2024-01-01T08:00:00Z"),
                                Instant.parse("2024-01-01T16:00:00Z"))));

        var service = new TimeTrackingService(mockRepository);
        List<WorkPeriod> workPeriods = service.getWorkPeriods(new Employee(1L, ""));
        assert workPeriods.size() == 1;
        assertEquals(1, workPeriods.size());

    }

    @Test
    void testAddWorkPeriod() {
        DataRepository mockRepository = Mockito.mock(DataRepository.class);
        when(mockRepository.addWorkPeriod(any(Employee.class), any(WorkPeriod.class))).thenReturn(
                new WorkPeriod(100L,
                        Instant.parse("2024-01-01T08:00:00Z"),
                        Instant.parse("2024-01-01T16:00:00Z")));

        var service = new TimeTrackingService(mockRepository);
        var workPeriod = new WorkPeriod(
                null,
                Instant.parse("2024-01-01T08:00:00Z"),
                Instant.parse("2024-01-01T16:00:00Z"));
        WorkPeriod wp2 = service.addWorkPeriod(new Employee(1L, ""), workPeriod);
        assertNotNull(wp2.id());
        // verify mockRepository was called
        verify(mockRepository).addWorkPeriod(any(Employee.class), eq(workPeriod));
    }

    @ParameterizedTest
    @ValueSource(strings = { "2024-01-01T08:00:00Z", "2024-01-01T16:00:00Z" })
    void testEndMustBeAfterStart(String endTime) {
        DataRepository mockRepository = Mockito.mock(DataRepository.class);

        var service = new TimeTrackingService(mockRepository);
        var workPeriod = new WorkPeriod(
                null,
                Instant.parse("2024-01-01T16:00:00Z"),
                Instant.parse(endTime));

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addWorkPeriod(new Employee(1L, ""), workPeriod),
                "End must be after start");
    }

    @Test
    void testStartMustBeInThePast() {
        DataRepository mockRepository = Mockito.mock(DataRepository.class);

        var service = new TimeTrackingService(mockRepository);
        var workPeriod = new WorkPeriod(
                null,
                Instant.now().plusSeconds(60 * 60),
                Instant.now().plusSeconds(60 * 60 * 2));

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addWorkPeriod(new Employee(1L, ""), workPeriod),
                "Start must be in the past");
    }

    @Test
    void testEndMustBeInThePast() {
        DataRepository mockRepository = Mockito.mock(DataRepository.class);

        var service = new TimeTrackingService(mockRepository);
        var workPeriod = new WorkPeriod(
                null,
                Instant.now().minusSeconds(60 * 60 * 2),
                Instant.now().plusSeconds(60 * 60 * 2));

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addWorkPeriod(new Employee(1L, ""), workPeriod),
                "End must be in the past");
    }

    @Test
    void testAddWorkPeriodWithInvalidEmployee() {
        DataRepository mockRepository = Mockito.mock(DataRepository.class);
        when(
                mockRepository.addWorkPeriod(
                        any(Employee.class),
                        any(WorkPeriod.class)))
                .thenThrow(new IllegalArgumentException("Employee 1 not found"));

        var service = new TimeTrackingService(mockRepository);
        var workPeriod = new WorkPeriod(
                null,
                Instant.parse("2024-01-01T08:00:00Z"),
                Instant.parse("2024-01-01T16:00:00Z"));

        Exception exc = assertThrows(
                IllegalArgumentException.class,
                () -> service.addWorkPeriod(new Employee(1L, ""), workPeriod));

        assertEquals("Employee 1 not found", exc.getMessage());
    }
}
