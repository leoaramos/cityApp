package com.leonardoramos.cityapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Neighbor {

	@Id
	@GeneratedValue
	@JsonIgnore
	private Long vizinhoId;

	@ManyToOne(targetEntity = City.class)
	@JsonBackReference
	private City cidadeOrigem;
	
	@JsonProperty("id")
	@Column(name = "destino_id", nullable = false, updatable = false)
	private Long cidadeDestinoId;

	@ManyToOne(targetEntity = City.class)
	@JsonIgnore
	private City cidadeDestino;

	private Float distancia;

}
