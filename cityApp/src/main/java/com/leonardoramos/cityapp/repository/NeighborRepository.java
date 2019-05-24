package com.leonardoramos.cityapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leonardoramos.cityapp.model.Neighbor;

@Repository
public interface NeighborRepository extends JpaRepository<Neighbor, Long> {

}
