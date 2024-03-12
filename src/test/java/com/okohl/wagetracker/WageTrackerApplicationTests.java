package com.okohl.wagetracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class WageTrackerApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testGetWorkPeriods() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/v0/time-tracking/1/work-periods"))
				.andExpect(status().isOk())
				.andExpect(result -> result.getResponse().getContentAsString().contains("John Doe"));
	}

	@Test
	void contextLoads() {
	}

}
