package com.leonardoramos.cityapp.model.graph;

public class Main {
	public static void main(String[] args) {
		Edge[] edges = { new Edge(0, 2, 1.0), new Edge(0, 3, 4.0), new Edge(0, 4, 2.0), new Edge(0, 1, 3.0),
				new Edge(1, 3, 2.0), new Edge(1, 4, 3.0), new Edge(1, 5, 1.0), new Edge(2, 4, 1.0), new Edge(3, 5, 4.0),
				new Edge(4, 5, 2.0), new Edge(4, 6, 7.0), new Edge(4, 7, 2.0), new Edge(5, 6, 4.0),
				new Edge(6, 7, 5.0) };
		Graph g = new Graph(edges);
		g.calculateShortestDistances();
		g.printResult(); // let's try it !
	}

}