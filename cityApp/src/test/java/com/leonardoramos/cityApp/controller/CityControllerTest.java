package com.leonardoramos.cityApp.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.leonardoramos.cityapp.CityAppApplication;
import com.leonardoramos.cityapp.controller.CityController;
import com.leonardoramos.cityapp.model.City;
import com.leonardoramos.cityapp.model.Coordinate;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CityController.class)
@ContextConfiguration(classes = CityAppApplication.class)
public class CityControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CityController cityController;

	@Test
	public void testCreate() throws Exception {
		City city = new City();
		city.setName("Cidade Teste");
		city.setFoundationDate(LocalDate.now());
		city.setPopulation(5486);
		city.setCoordinate(new Coordinate(-55.0, -11.0));

		mvc.perform(get("/city").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].name", is(city.getName())));

	}

}
