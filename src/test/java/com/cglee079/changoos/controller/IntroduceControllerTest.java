package com.cglee079.changoos.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cglee079.changoos.service.CommonStrService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/**-context.xml")
public class IntroduceControllerTest {
	@Mock
	CommonStrService commonStringService;
	
	@InjectMocks
	private IntroduceController introduceController;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(introduceController).build();
	}
	
	@Test
	public void testIntroduce() throws Exception {
		when(commonStringService.get("INTRO", "001")).thenReturn("자기소개");
		when(commonStringService.get("INTRO", "002")).thenReturn("이력내용");
		
		mockMvc.perform(get("/introduce"))
			.andExpect(status().isOk())
			.andExpect(view().name("introduce/introduce_view"))
			.andExpect(model().attribute("intro001", "자기소개"))
			.andExpect(model().attribute("intro002", "이력내용"));
	}
}
