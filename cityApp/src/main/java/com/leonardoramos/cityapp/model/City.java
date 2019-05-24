package com.leonardoramos.cityapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class City {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	private Integer population;

	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate foundationDate;

	@Embedded
	private Coordinate coordinate;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "cidades_vizinhos")
	@JoinColumn(name = "cidade_origem")
	@JsonManagedReference
	private List<Neighbor> vizinhos = new ArrayList<Neighbor>();
}