package com.leonardoramos.cityapp;

import java.time.LocalDate;
import java.util.stream.LongStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.leonardoramos.cityapp.model.City;
import com.leonardoramos.cityapp.model.Coordinate;
import com.leonardoramos.cityapp.repository.CityRepository;

@SpringBootApplication
public class CityAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CityAppApplication.class, args);
	}

	@Bean
	CommandLineRunner init(CityRepository repository) {
		return args -> {
			repository.deleteAll();

			LongStream.range(1, 11)
					.mapToObj(i -> new City(i, "Name " + i, 999, LocalDate.now(), new Coordinate(88.44, 44.12), null))
					.map(v -> repository.save(v)).forEach(System.out::println);
		};
	}

}
