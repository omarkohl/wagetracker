package com.okohl.wagetracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.okohl.wagetracker.adapter.repositories.EmployeeEntity;
import com.okohl.wagetracker.adapter.repositories.EmployeeRepository;
import com.okohl.wagetracker.adapter.repositories.TimeTrackingRepository;
import com.okohl.wagetracker.adapter.repositories.WorkPeriodEntity;
import com.okohl.wagetracker.domain.WorkPeriod;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.ArrayList;
import java.time.Instant;
import java.util.Random;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class WageTrackerApplicationIntegrationTests {

	@Autowired
	private WebTestClient webTestClient;

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
		this.webTestClient
				.get()
				.uri(url)
				.header(ACCEPT, APPLICATION_JSON_VALUE)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(APPLICATION_JSON)
				.expectBodyList(WorkPeriod.class)
				.hasSize(2)
				.consumeWith(response -> {
					String responseBody = new String(response.getResponseBodyContent());
					assertThat(responseBody).contains("2023-02-02T16:00:00Z");
				});
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
		this.webTestClient
				.get()
				.uri(url)
				.header(ACCEPT, APPLICATION_JSON_VALUE)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.length()").isEqualTo(0);
	}

	@Test
	void testAddWorkPeriod() throws Exception {
		var url = "/v0/time-tracking/" + validEmployeeId + "/work-periods";
		var json = "{\"start\":\"2023-02-03T08:00:00Z\",\"end\":\"2023-02-03T16:00:00Z\"}";
		webTestClient.post()
				.uri(url)
				.contentType(APPLICATION_JSON)
				.bodyValue(json)
				.exchange()
				.expectStatus().isCreated()
				.expectHeader().contentType(APPLICATION_JSON)
				.expectBody(WorkPeriod.class)
				.consumeWith(response -> {
					var workPeriod = response.getResponseBody();
					assertThat(workPeriod).isNotNull();
					assertThat(workPeriod.id()).isNotNull();
					assertThat(workPeriod.start()).isEqualTo(Instant.parse("2023-02-03T08:00:00Z"));
					assertThat(workPeriod.end()).isEqualTo(Instant.parse("2023-02-03T16:00:00Z"));
				});

		// verify that the work period was added
		webTestClient.get()
				.uri(url)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(APPLICATION_JSON)
				.expectBodyList(WorkPeriod.class)
				.hasSize(3)
				.consumeWith(response -> {
					String responseBody = new String(response.getResponseBodyContent());
					assertThat(responseBody).contains("2023-02-03T16:00:00Z");
				});

	}

	@Test
	void contextLoads() {
	}

}
