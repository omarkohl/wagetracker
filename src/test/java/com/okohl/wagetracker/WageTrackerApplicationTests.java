package com.okohl.wagetracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.okohl.wagetracker.adapter.repositories.TimeTrackingRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class WageTrackerApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TimeTrackingRepository timeTrackingRepository;

	@Test
	void testGetWorkPeriods() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/v0/time-tracking/1/work-periods"))
				.andExpect(status().isOk())
				.andExpect(result -> result.getResponse().getContentAsString().contains("2022-01-02T16:00:00Z"));
	}

	@Test
	void testGetWorkPeriodsForUnknownEmployee() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/v0/time-tracking/2/work-periods"))
				.andExpect(status().isOk())
				.andExpect(result -> result.getResponse().getContentAsString().equals("[]"));
	}

	@Test
	void contextLoads() {
	}

}
