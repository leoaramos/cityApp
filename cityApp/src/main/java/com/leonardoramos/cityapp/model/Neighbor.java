package com.leonardoramos.cityapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	@JoinColumn(name = "cityOne_id")
	@JsonIgnore
	private City cityOne;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = City.class)
	@JoinColumn(name = "cityTwo_id")
	@JsonIgnore
	private City cityTwo;

	@Column(name = "cityTwo_id", nullable = false, insertable = false, updatable = false)
	@JsonProperty(value = "id")
	private Long cityTwoId;

	@NonNull
	private Float distance;
	
}