package com.okohl.wagetracker;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.time.YearMonth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.okohl.wagetracker.adapter.repositories.EmployeeEntity;
import com.okohl.wagetracker.adapter.repositories.EmployeeRepository;
import com.okohl.wagetracker.adapter.repositories.PayrollHoursEntity;
import com.okohl.wagetracker.adapter.repositories.PayrollHoursRepository;
import com.okohl.wagetracker.adapter.repositories.TimeTrackingRepository;
import com.okohl.wagetracker.adapter.repositories.WorkPeriodEntity;
import com.okohl.wagetracker.domain.PayrollHoursStatus;

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
			EmployeeRepository employeeRepository,
			PayrollHoursRepository payrollHoursRepository) {
		return (args) -> {
			var emilio = new EmployeeEntity("Emilio López");
			emilio = employeeRepository.save(emilio);
			var erika = new EmployeeEntity("Erika Müller");
			erika = employeeRepository.save(erika);

			var workPeriods = new ArrayList<WorkPeriodEntity>(
					List.of(
							new WorkPeriodEntity(
									emilio,
									Instant.parse("2022-01-01T08:00:00Z"),
									Instant.parse("2022-01-01T16:00:00Z")),
							new WorkPeriodEntity(
									emilio,
									Instant.parse("2022-01-02T08:00:00Z"),
									Instant.parse("2022-01-02T16:00:00Z")),
							new WorkPeriodEntity(
									emilio,
									Instant.parse("2022-01-03T08:00:00Z"),
									Instant.parse("2022-01-03T16:00:00Z")),
							new WorkPeriodEntity(
									emilio,
									Instant.parse("2022-01-04T08:00:00Z"),
									Instant.parse("2022-01-04T16:00:00Z")))

			);
			var payrollHours = new ArrayList<PayrollHoursEntity>(
					List.of(
							new PayrollHoursEntity(
									YearMonth.parse("2022-01"),
									emilio,
									140.0f,
									PayrollHoursStatus.UNPROCESSED),
							new PayrollHoursEntity(
									YearMonth.parse("2022-02"),
									emilio,
									160.0f,
									PayrollHoursStatus.UNPROCESSED),
							new PayrollHoursEntity(
									YearMonth.parse("2022-03"),
									emilio,
									180.0f,
									PayrollHoursStatus.UNPROCESSED),
							new PayrollHoursEntity(
									YearMonth.parse("2022-01"),
									erika,
									200.0f,
									PayrollHoursStatus.UNPROCESSED)));
			timeTrackingRepository.saveAll(workPeriods);
			payrollHoursRepository.saveAll(payrollHours);
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
