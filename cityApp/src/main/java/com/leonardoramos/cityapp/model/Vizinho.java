package com.leonardoramos.cityapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "id")
public class Vizinho extends City {

	private Float distancia;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = City.class)
	@JsonIgnore
	private City cidadeVizinha;
	
	@Column(name = "cityTwo_id", nullable = false, insertable = false, updatable = false)
	@JsonProperty(value = "id")
	private Long cidadeVizinhaId;


}
