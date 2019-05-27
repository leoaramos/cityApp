package com.leonardoramos.cityapp.model.graph;

import java.util.ArrayList;

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
public class Node {

	private Double distanceFromSource = Double.MAX_VALUE;
	private boolean visited;
	private ArrayList<Edge> edges = new ArrayList<Edge>(); // now we must create edges

}
