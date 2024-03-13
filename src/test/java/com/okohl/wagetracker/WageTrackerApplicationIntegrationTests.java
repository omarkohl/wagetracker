package com.okohl.wagetracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.okohl.wagetracker.adapter.repositories.EmployeeEntity;
import com.okohl.wagetracker.adapter.repositories.EmployeeRepository;
import com.okohl.wagetracker.adapter.repositories.TimeTrackingRepository;
import com.okohl.wagetracker.adapter.repositories.WorkPeriodEntity;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.List;
import java.util.ArrayList;
import java.time.Instant;
import java.util.Random;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class WageTrackerApplicationIntegrationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TimeTrackingRepository timeTrackingRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	private Long validEmployeeId;

	@BeforeEach
	void setupDb() {
		var employee = new EmployeeEntity("John Doe");
		employee = employeeRepository.save(employee);
		validEmployeeId = employee.getId();

		var workPeriods = new ArrayList<WorkPeriodEntity>(
				List.of(
						new WorkPeriodEntity(
								employee,
								Instant.parse("2023-02-01T08:00:00Z"),
								Instant.parse("2023-02-01T16:00:00Z")),
						new WorkPeriodEntity(
								employee,
								Instant.parse("2023-02-02T08:00:00Z"),
								Instant.parse("2023-02-02T16:00:00Z")))

		);
		employee.setWorkPeriods(workPeriods);
		employeeRepository.save(employee);
		timeTrackingRepository.saveAll(workPeriods);
	}

	@Test
	void testGetWorkPeriods() throws Exception {
		var url = "/v0/time-tracking/" + validEmployeeId + "/work-periods";
		mockMvc.perform(MockMvcRequestBuilders.get(url))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(content().string(containsString("2023-02-02T16:00:00Z")));
	}

	@Test
	void testGetWorkPeriodsForUnknownEmployee() throws Exception {
		// choose a random long that is not validEmployeeId
		Random random = new Random();
		Long invalidEmployeeId;
		do {
			invalidEmployeeId = random.nextLong();
		} while (invalidEmployeeId == validEmployeeId);
		var url = "/v0/time-tracking/" + invalidEmployeeId + "/work-periods";
		mockMvc.perform(MockMvcRequestBuilders.get(url))
				.andExpect(status().isOk())
				.andExpect(content().string("[]"));
	}

	@Test
	void testAddWorkPeriod() throws Exception {
		var url = "/v0/time-tracking/" + validEmployeeId + "/work-periods";
		var json = "{\"start\":\"2023-02-03T08:00:00Z\",\"end\":\"2023-02-03T16:00:00Z\"}";
		mockMvc.perform(MockMvcRequestBuilders.post(url)
				.contentType("application/json")
				.content(json))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(content().string(containsString("2023-02-03T16:00:00Z")));

		// verify that the work period was added
		mockMvc.perform(MockMvcRequestBuilders.get(url))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(content().string(containsString("2023-02-03T16:00:00Z")));

	}

	@Test
	void contextLoads() {
	}

}
