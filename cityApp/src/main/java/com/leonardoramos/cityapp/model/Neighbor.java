package com.leonardoramos.cityapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Neighbor {

	@Id
	@GeneratedValue
	@JsonIgnore
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = City.class) 
	@JoinTable(name = "cities_neighboors", joinColumns = @JoinColumn(name = "neighboors_id"))
	@JsonIgnore
	private City cityFrom;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = City.class)
	@JoinColumn(name = "city_to")
	private City cityTo;

	@Column(name = "city_to_id")
	@JsonProperty(value = "id")
	private Long cityToId;

	@NonNull
	private Float distance;

}