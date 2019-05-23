package com.leonardoramos.cityapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leonardoramos.cityapp.model.City;
import com.leonardoramos.cityapp.repository.CityRepository;

@RestController
@RequestMapping({ "/city" })
public class CityController {

	private CityRepository repository;

	public CityController(CityRepository cityRepository) {
		this.repository = cityRepository;
	}

	@GetMapping
	public List<City> findAll() {
		return repository.findAll();
	}

	@GetMapping(path = { "/{id}" })
	public ResponseEntity<City> findById(@PathVariable long id) {
		return repository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.badRequest().build());
	}

	@PostMapping
	public City create(@RequestBody City city) {
		return repository.save(city);
	}

	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		return repository.findById(id).map(record -> {
			if (record.getVizinhos().size() <= 1) {
				repository.deleteById(id);
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.badRequest().build();
			}
		}).get();
	}

}
