package com.leonardoramos.cityapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@EqualsAndHashCode
public class Neighbor {

	@Id
	@GeneratedValue
	@JsonIgnore
	private Long neighborId;

	@ManyToOne(targetEntity = City.class)
	@JsonBackReference
	@Cascade(CascadeType.ALL)
	private City cityFrom;

	@JsonProperty("id")
	@Column(name = "to_id", nullable = false, updatable = false)
	@Cascade(CascadeType.ALL)
	private Long cityToId;

	@ManyToOne(targetEntity = City.class)
	@JsonIgnore
	@Cascade(CascadeType.ALL)
	private City cityTo;

	private Double distance;

}
