package org.ml.decisionTree;

import org.graphstream.graph.Graph;

public class tdidt {

	private Node root;
	private Graph graph;

	public tdidt() {
		// TODO Auto-generated constructor stub
	}

	private double log2(double value) {
		if (value != 0)
			return Math.log10(value) / Math.log10(2);
		else
			return 0.0;
	}

}
