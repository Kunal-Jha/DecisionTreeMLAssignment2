package org.ml.decisionTree;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.jgrapht.DirectedGraph;
import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.VertexNameProvider;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class tdidt {

	int nodeIndex = 0;
	DirectedGraph<Node, DefaultEdge> graph;
	Node root;
	static TDIDTUtils utility;
	ArrayList<ArrayList<Double>> trainingData;
	ArrayList<String> attributesList;

	public static void main(String[] args) {
		tdidt algo = new tdidt();
		utility = new TDIDTUtils();
		algo.tdidt_recursive(algo.trainingData, algo.attributesList, algo.root,
				algo.root, algo.nodeIndex);
		//algo.drawTree();
	}

	tdidt() {
		// TODO Auto-generated constructor stub
		TrainInput trainingInput = new TrainInput(
				"./assets/gene_expression_training.csv");
		this.trainingData = trainingInput.getInput();
		this.attributesList = trainingInput.getAttributes();
		this.root = new Node(Integer.toString(nodeIndex));
		//this.graph = new DefaultDirectedGraph<Node, DefaultEdge>(
			//	DefaultEdge.class);

	}

	public static void getDOTFile(DirectedGraph<Node, DefaultEdge> graph)
			throws IOException {

		DOTExporter<Node, DefaultEdge> dot = new DOTExporter<Node, DefaultEdge>(
				new VertexNameProvider<Node>() {
					@Override
					public String getVertexName(Node currentnode) {
						return currentnode.getAttribute();

					}
				}, null, null);
		String path = "";
		FileWriter fwriter = new FileWriter(new File(path));
		dot.export(fwriter, graph);
		fwriter.flush();
	}

	public void drawTree() {
		this.graph.addVertex(root);
		this.drawTreeRecursively(root);
		try {
			this.getDOTFile(graph);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void drawTreeRecursively(Node current) {
		if (current.getLeft() != null) {
			graph.addVertex(current.getLeft());

			graph.addEdge(current, current.getLeft());
			drawTreeRecursively(current.getLeft());
		}
		if (current.getRight() != null) {
			graph.addVertex(current.getRight());

			graph.addEdge(current, current.getRight());
			drawTreeRecursively(current.getRight());
		}
	}

	public Node tdidt_recursive(ArrayList<ArrayList<Double>> traindata,
			ArrayList<String> attributesList, Node root, Node currentNode,
			int nodeCount) {

		// if the examples are perfectly classified
		if (!traindata.isEmpty()) {
			if (utility.isPerfectlyClassified(traindata)) {
				root.setLeaf(true);
				return root;

			}
		}

		Tuple attribute = utility.chooseAttribute(traindata, attributesList,
				nodeCount);
		// when the number of attribute is 7
		if (attribute == null) {
			utility.dealWithNoSplit(traindata, currentNode);
			return root;
		}
		ArrayList<ArrayList<Double>> lessThanBranchData = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> greaterThanBranchData = new ArrayList<ArrayList<Double>>();

		// TODO check the if condition
		for (int i = 0; i < traindata.size(); i++) {
			if (traindata.get(i).get(attribute.getSelectedIndex()) < attribute
					.getColSplitPoint()) {
				traindata.get(i).remove(attribute.getSelectedIndex());// removing
				// the
				// already
				// evaluated
				// attribute.
				lessThanBranchData.add(traindata.get(i));
			} else {
				traindata.get(i).remove(attribute.getSelectedIndex());
				greaterThanBranchData.add(traindata.get(i));
			}
		}

		attributesList.remove(attribute.getSelectedIndex());

		// TODO Check passing of Node Index it should be equal to 0s
		// Storing Tree Information
		this.nodeIndex++;
		Node leftNode = new Node(Integer.toString(nodeIndex) + " L");
		this.nodeIndex++;
		Node rightNode = new Node(Integer.toString(nodeIndex) + " R");
		currentNode.setLeft(leftNode);
		currentNode.setRight(rightNode);

		tdidt_recursive(lessThanBranchData, attributesList, root, leftNode,
				this.nodeIndex);

		tdidt_recursive(greaterThanBranchData, attributesList, root, rightNode,
				this.nodeIndex);

		return root;
	}

}
