package com.leonardoramos.cityapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leonardoramos.cityapp.model.City;
import com.leonardoramos.cityapp.model.Neighbor;
import com.leonardoramos.cityapp.repository.CityRepository;
import com.leonardoramos.cityapp.repository.NeighborRepository;

@RestController
@RequestMapping({ "/city" })
public class CityController {

	private CityRepository repository;
	private NeighborRepository neighborRepository;

	public CityController(CityRepository cityRepository, NeighborRepository neighborRepository) {
		this.repository = cityRepository;
		this.neighborRepository = neighborRepository;
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

	@PutMapping(value = "/{id}")
	public ResponseEntity<City> update(@PathVariable("id") long id, @RequestBody City city) {
		return repository.findById(id).map(record -> {
			record.setName(city.getName());
			record.setPopulation(city.getPopulation());
			record.setFoundationDate(city.getFoundationDate());
			record.setCoordinate(city.getCoordinate());
			record.setNeighboors(city.getNeighboors());
			City updated = repository.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.badRequest().build());
	}

	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		try {
			return repository.findById(id).map(record -> {
				if (record.getNeighboors().size() <= 1) {
					repository.deleteById(id);
					return ResponseEntity.ok().build();
				} else {
					return ResponseEntity.badRequest().build();
				}
			}).get();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping(path = { "{cityId}/neighbor/{neighborId}" })
	public ResponseEntity<City> addNeighbor(@PathVariable("cityId") long cityId,
			@PathVariable("neighborId") long neighborId) {
		try {
			City cityFrom = repository.findById(cityId).get();
			City cityTo = repository.findById(neighborId).get();
			Neighbor neighborOne = new Neighbor();
			Neighbor neighborTwo = new Neighbor();
			if (repository.findById(neighborId).get() != null) {
				neighborOne.setCityFrom(cityFrom);
				neighborOne.setDistance(44f);
				neighborOne.setCityTo(cityTo);
				neighborOne.setCityToId(cityTo.getId());
				neighborRepository.save(neighborOne);
				cityFrom.getNeighboors().add(neighborOne);
				//inclui também a relação contrária
				neighborTwo.setCityFrom(cityTo);
				neighborTwo.setDistance(44f);
				neighborTwo.setCityTo(cityFrom);
				neighborTwo.setCityToId(cityFrom.getId());
				neighborRepository.save(neighborTwo);
				cityTo.getNeighboors().add(neighborTwo);
			}
			repository.save(cityFrom);
			repository.save(cityTo);
			return ResponseEntity.ok(cityFrom);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

}
