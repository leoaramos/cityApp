package com.leonardoramos.cityapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leonardoramos.cityapp.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

}
