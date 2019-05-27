package com.leonardoramos.cityapp.model.graph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
/**
 * This entity is used only to represent temporary data to calculate routes, and
 * it's not persisted in database.
 * 
 * @author Leonardo Antonio Ramos
 *
 */
public class Edge {

	private int fromNodeIndex;
	private int toNodeIndex;
	private Double length;

	// determines the neighbouring node of a supplied node, based on the two nodes
	// connected by this edge
	public int getNeighbourIndex(int nodeIndex) {
		if (this.fromNodeIndex == nodeIndex) {
			return this.toNodeIndex;
		} else {
			return this.fromNodeIndex;
		}
	}

}
