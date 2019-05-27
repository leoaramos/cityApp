package com.leonardoramos.cityApp.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.leonardoramos.cityapp.CityAppApplication;
import com.leonardoramos.cityapp.model.City;
import com.leonardoramos.cityapp.model.Coordinate;
import com.leonardoramos.cityapp.repository.CityRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { CityAppApplication.class }, webEnvironment = WebEnvironment.DEFINED_PORT)
public class CityRepositoryTest {

	@Autowired
	private CityRepository cityRepository;

	@Test
	public void testCreateCity() {
		City city = new City();
		city.setCoordinate(new Coordinate(-15.17, 14.24));
		city.setFoundationDate(LocalDate.now());
		city.setName("Cidade Teste");
		city.setPopulation(95485);
		cityRepository.save(city);
		assertNotNull(city.getId());
	}

	@Test
	public void testUpdateCity() {
		City city = new City();
		city.setCoordinate(new Coordinate(-15.17, 14.24));
		city.setFoundationDate(LocalDate.now());
		city.setName("Cidade Teste");
		city.setPopulation(95485);
		cityRepository.save(city);
		cityRepository.flush();
		assertNotNull(city.getId());
		Long id = city.getId();

		City city2 = cityRepository.findById(id).get();
		city2.setName("Cidade Alterada");
		city2.setPopulation(9458);
		cityRepository.save(city2);
		cityRepository.flush();

		city2 = cityRepository.findById(id).get();
		assertEquals("Cidade Alterada", city2.getName());
		assertEquals(9458, city2.getPopulation().longValue());
	}

}
