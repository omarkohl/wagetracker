package com.okohl.wagetracker.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
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
        List<WorkPeriod> workPeriods = service.getWorkPeriods(1L);
        assert workPeriods.size() == 1;
    }
}
