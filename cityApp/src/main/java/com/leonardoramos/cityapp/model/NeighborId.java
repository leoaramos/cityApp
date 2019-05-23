package com.leonardoramos.cityapp.model;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Data
class NeighborId {
	private Long cityOneId;
	private Long cityTwoId;
}
