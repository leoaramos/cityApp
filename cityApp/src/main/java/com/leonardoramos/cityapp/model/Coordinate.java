package com.leonardoramos.cityapp.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class Coordinate {

	@Column(name = "lat", nullable = false)
	@JsonProperty(value = "lat")
	private Double latitude;

	@Column(name = "long", nullable = false)
	@JsonProperty(value = "long")
	private Double longitude;
}
