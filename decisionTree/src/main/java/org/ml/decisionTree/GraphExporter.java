package org.ml.decisionTree;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.jgrapht.ext.DOTExporter;


public class GraphExporter   {

	public static void saveGraphDefault(Graph<Node, Edge> graph, String path) throws IOException {
		saveGraph(graph, new File(path + ".dot").toString());
	}

	public static void saveGraph(Graph<Node, Edge> graph, String path) throws IOException {
		DOTExporter<Node, Edge> dot = GraphProviders.getDOTExporter();
		FileWriter fwriter = new FileWriter(new File(path));
		dot.export(fwriter, graph);
		fwriter.flush();
	}


}
