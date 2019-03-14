package com.ab.ethioflix;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import com.ab.ethioflix.controllers.HomeController;
import com.ab.ethioflix.controllers.LoginController;
import com.ab.ethioflix.services.MovieService;
import com.ab.ethioflix.services.RoleService;
import com.ab.ethioflix.services.UserService;



public class HomeControllerTest {
	
	@Mock
	private MovieService movieService;
	
	@InjectMocks
	HomeController homeController;
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(homeController).setViewResolvers(viewResolver()).build();
	}
	
	@Test
	public void testDiscriptionPage() throws Exception {
		mockMvc.perform(get("/discription"))
		.andExpect(status().isOk())
		.andExpect(view().name("discription"))
		;
	}
	
	@Test
	public void testHomePage() throws Exception {
		mockMvc.perform(get("/"))
		.andExpect(status().isOk())
		.andExpect(view().name("home"));
	}
	
	private ViewResolver viewResolver()
	{
	    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

	    viewResolver.setPrefix("classpath:templates/");
	    viewResolver.setSuffix(".html");


	    return viewResolver;
	}

}
