package com.okohl.wagetracker;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.okohl.wagetracker.adapter.repositories.EmployeeEntity;
import com.okohl.wagetracker.adapter.repositories.EmployeeRepository;
import com.okohl.wagetracker.adapter.repositories.TimeTrackingRepository;
import com.okohl.wagetracker.adapter.repositories.WorkPeriodEntity;

@SpringBootApplication
public class WageTrackerApplication {

	private static final Logger log = LoggerFactory.getLogger(WageTrackerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(WageTrackerApplication.class, args);
	}

	/*
	 * This method is used to create some demo data for the application. It
	 * breaks proper encapsulation by directly interacting with the
	 * repositories but it's convenient for the purpose of this demo.
	 */
	@Bean
	@Profile("!test")
	public CommandLineRunner createDemoData(
			TimeTrackingRepository timeTrackingRepository,
			EmployeeRepository employeeRepository) {
		return (args) -> {
			var employee = new EmployeeEntity("John Doe");
			employee = employeeRepository.save(employee);

			var workPeriods = new ArrayList<WorkPeriodEntity>(
					List.of(
							new WorkPeriodEntity(
									employee,
									Instant.parse("2022-01-01T08:00:00Z"),
									Instant.parse("2022-01-01T16:00:00Z")),
							new WorkPeriodEntity(
									employee,
									Instant.parse("2022-01-02T08:00:00Z"),
									Instant.parse("2022-01-02T16:00:00Z")),
							new WorkPeriodEntity(
									employee,
									Instant.parse("2022-01-03T08:00:00Z"),
									Instant.parse("2022-01-03T16:00:00Z")),
							new WorkPeriodEntity(
									employee,
									Instant.parse("2022-01-04T08:00:00Z"),
									Instant.parse("2022-01-04T16:00:00Z")))

			);
			employee.setWorkPeriods(workPeriods);
			employeeRepository.save(employee);
			timeTrackingRepository.saveAll(workPeriods);
			log.info("Saved employee with work periods");

			log.info("WorkPeriods found with findAll():");
			log.info("-------------------------------");
			timeTrackingRepository.findAll().forEach(workPeriod -> {
				log.info(workPeriod.toString());
			});
			log.info("");
		};
	}
}
