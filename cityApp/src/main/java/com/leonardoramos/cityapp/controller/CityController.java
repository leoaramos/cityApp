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
	public ResponseEntity<List<City>> findAll() {
		try {
			return ResponseEntity.ok(repository.findAll());
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping(path = { "/{id}" })
	public ResponseEntity<City> findById(@PathVariable long id) {
		return repository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.badRequest().build());
	}

	@PostMapping
	public ResponseEntity<City> create(@RequestBody City city) {
		try {
			repository.save(city);
			return ResponseEntity.ok(city);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		return repository.findById(id).map(record -> {
			if (record.getNeighboors().size() <= 1) {
				repository.deleteById(id);
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.badRequest().build();
			}
		}).get();
	}

}
