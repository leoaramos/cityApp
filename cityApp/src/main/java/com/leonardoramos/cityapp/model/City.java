package com.leonardoramos.cityapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
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

//	@OneToMany(fetch = FetchType.LAZY, targetEntity = Neighbor.class, mappedBy = "cityTwo")
//	private Set<Neighbor> neighboors = new HashSet<Neighbor>();

//	@OneToMany(fetch = FetchType.LAZY, targetEntity = Vizinho.class)
//	@JsonIgnore
//	private List<Vizinho> vizinhos = new ArrayList<Vizinho>();

	@OneToMany
	@JoinTable(name = "cities_neighboors", joinColumns = @JoinColumn(name = "city_id"), inverseJoinColumns = @JoinColumn(name = "neighbor_id"))
	private List<Neighbor> neighboors = new ArrayList<Neighbor>();

}