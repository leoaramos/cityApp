package com.leonardoramos.cityapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CityAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CityAppApplication.class, args);
	}

//	@Bean
//	CommandLineRunner init(CityRepository repository) {
//		return args -> {
//			repository.deleteAll();
//
//			LongStream.range(1, 11)
//					.mapToObj(i -> new City(i, "Name " + i, 999, LocalDate.now(), new Coordinate(88.44, 44.12), null))
//					.map(v -> repository.save(v)).forEach(System.out::println);
//		};
//	}

}
