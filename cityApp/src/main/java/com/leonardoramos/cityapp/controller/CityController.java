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

	/**
	 * Update a city identified by id. The list of neighboors are not affected; use
	 * the {@link #addNeighbor(long, long) addNeighbor} method for this purpose.
	 * 
	 * @param long id
	 * @param City city
	 * @return ResponseEntity<City>
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<City> update(@PathVariable("id") long id, @RequestBody City city) {
		return repository.findById(id).map(record -> {
			record.setName(city.getName());
			record.setPopulation(city.getPopulation());
			record.setFoundationDate(city.getFoundationDate());
			record.setCoordinate(city.getCoordinate());
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

	/**
	 * Calculates the distance between two cities through their coordinates, using
	 * Haversine algorithm
	 * 
	 * @param City from
	 * @param City to
	 * @return Double distante
	 */
	public Double getDistance(City from, City to) {
		double deltaLat = Math.toRadians(from.getCoordinate().getLatitude() - to.getCoordinate().getLatitude());
		double deltaLon = Math.toRadians(from.getCoordinate().getLongitude() - from.getCoordinate().getLongitude());
		double distance = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
				+ Math.cos(Math.toRadians(to.getCoordinate().getLatitude()))
						* Math.cos(Math.toRadians(from.getCoordinate().getLatitude())) * Math.sin(deltaLon / 2)
						* Math.sin(deltaLon / 2);
		distance = 2 * Math.atan2(Math.sqrt(distance), Math.sqrt(1 - distance));
		return 6371.0 * distance;
	}

	@GetMapping(path = { "{cityId}/neighbor/{neighborId}" })
	public ResponseEntity<City> addNeighbor(@PathVariable("cityId") long cityId,
			@PathVariable("neighborId") long neighborId) {
		try {
			// somente inclui se já não houver um vizinho com estes dados
			City cityFrom = repository.findById(cityId).get();
			City cityTo = repository.findById(neighborId).get();
			for (Neighbor neighbor : cityFrom.getNeighboors()) {
				if (neighbor.getCityTo().equals(cityTo)) {
					return ResponseEntity.badRequest().build();
				}
			}
			Neighbor neighborOne = new Neighbor();
			Neighbor neighborTwo = new Neighbor();
			if (repository.findById(neighborId).get() != null) {
				neighborOne.setCityFrom(cityFrom);
				neighborOne.setDistance(getDistance(cityFrom, cityTo));
				neighborOne.setCityTo(cityTo);
				neighborOne.setCityToId(cityTo.getId());
				neighborRepository.save(neighborOne);
				cityFrom.getNeighboors().add(neighborOne);
				// inclui também a relação contrária
				neighborTwo.setCityFrom(cityTo);
				neighborTwo.setDistance(getDistance(cityTo, cityFrom));
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

	/**
	 * Remove a Neighbor entity from database and the relationship between two
	 * cities.
	 * 
	 * @param long cityId
	 * @param long neighborCityId
	 * @return ResponseEntity<City>
	 */
	@DeleteMapping(path = { "{cityId}/neighbor/{neighborId}" })
	public ResponseEntity<City> removeNeighbor(@PathVariable("cityId") long cityId,
			@PathVariable("neighborId") long neighborCityId) {
		try {
			City cityFrom = repository.findById(cityId).get();
			City cityTo = repository.findById(neighborCityId).get();
			for (Neighbor neighbor : cityFrom.getNeighboors()) {
				if (neighbor.getCityToId().longValue() == neighborCityId) {
					cityFrom.getNeighboors().remove(neighbor);
					repository.save(cityFrom);
					neighborRepository.delete(neighbor);
					break;
				}
			}
			for (Neighbor neighbor : cityTo.getNeighboors()) {
				if (neighbor.getCityToId().longValue() == cityId) {
					cityTo.getNeighboors().remove(neighbor);
					repository.save(cityTo);
					neighborRepository.delete(neighbor);
					break;
				}
			}
			return ResponseEntity.ok(cityFrom);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

}
