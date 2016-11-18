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
	TDIDTUtils utility;
	ArrayList<ArrayList<Double>> trainingData;
	double accuracy;
	ArrayList<String> attributesList;

	public static void main(String[] args) {
		tdidt algo = new tdidt("./assets/gene_expression_training.csv");
		algo.tdidt_recursive(algo.trainingData, algo.attributesList, algo.root,
				algo.root, algo.nodeIndex);
		algo.drawTree();
		algo.accuracyTest("./assets/gene_expression_test.csv");
	}

	tdidt(String path) {
		TrainInput trainingInput = new TrainInput(path);
		this.trainingData = trainingInput.getInput();
		this.attributesList = trainingInput.getAttributes();
		this.root = new Node("0");
		this.utility = new TDIDTUtils();
		this.graph = new DefaultDirectedGraph<Node, DefaultEdge>(
				DefaultEdge.class);
		this.accuracy = 0;
	}

	public void getDOTFile(DirectedGraph<Node, DefaultEdge> graph)
			throws IOException {

		DOTExporter<Node, DefaultEdge> dot = new DOTExporter<Node, DefaultEdge>(
				new VertexNameProvider<Node>() {
					@Override
					public String getVertexName(Node currentnode) {
						return String.valueOf(currentnode.getAttribute());

					}
				}, null, null);
		String path = "./assets/trainingDataTree.dot";
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

	/****************/
	int DEPTH_ALLOWED = 3;
	int currentDepth = 1;

	/****************/

	public Node tdidt_recursive(ArrayList<ArrayList<Double>> traindata,
			ArrayList<String> attributesList, Node root, Node currentNode,
			int nodeCount) {

		System.out.println(currentDepth + " " + traindata.size());

		// if the examples are perfectly classified
		if (!traindata.isEmpty()) {
			if (utility.isPerfectlyClassified(traindata)) {
				currentNode.setLeaf(true);
				currentNode.setSplitpoint(Double.MIN_VALUE);
				return root;

			}

			Tuple attribute = utility.chooseAttribute(traindata,
					attributesList, currentDepth, DEPTH_ALLOWED);

			if ((attribute == null)) {
				utility.dealWithNoSplit(traindata, currentNode);

				return root;
			}
			if (nodeIndex == 0) {
			}
			ArrayList<ArrayList<Double>> lessThanBranchData = new ArrayList<ArrayList<Double>>();
			ArrayList<ArrayList<Double>> greaterThanBranchData = new ArrayList<ArrayList<Double>>();

			for (int i = 0; i < traindata.size(); i++) {

				if (Double.compare(
						traindata.get(i).get(attribute.getSelectedIndex()),
						attribute.getSplitPoint()) < 0) {
					traindata.get(i).remove(attribute.getSelectedIndex());// removing

					lessThanBranchData.add(traindata.get(i));
				} else {
					traindata.get(i).remove(attribute.getSelectedIndex());
					greaterThanBranchData.add(traindata.get(i));
				}
			}

			String attributeName = attributesList.get(attribute
					.getSelectedIndex());

			if (!attributesList.isEmpty()) {
				attributesList.remove(attribute.getSelectedIndex());
			}
			this.nodeIndex++;

			Node leftNode = new Node("\""
					+ /* Integer.toString(nodeIndex) + */"" + attributeName
					+ " < " + attribute.getColSplitPoint() + "\"");

			this.nodeIndex++;
			Node rightNode = new Node("\""
					+ /* Integer.toString(nodeIndex) + */"" + attributeName
					+ " > " + attribute.getColSplitPoint() + "\"");

			currentNode.setLeft(leftNode);
			currentNode.setRight(rightNode);
			currentNode.setSplitpoint(attribute.getSplitPoint());
			currentNode.setAttributeIndex(attribute.getSelectedIndex());

			if (currentDepth < DEPTH_ALLOWED) {
				currentDepth++;
				System.out.println(leftNode.attribute + " " + currentDepth);
				tdidt_recursive(lessThanBranchData, attributesList, root,
						leftNode, this.nodeIndex);
				currentDepth--;
			} else {
				utility.dealWithNoSplit(traindata, leftNode);
			}

			if (currentDepth < DEPTH_ALLOWED) {
				currentDepth++;
				System.out.println(rightNode.attribute + " " + currentDepth);
				tdidt_recursive(greaterThanBranchData, attributesList, root,
						rightNode, this.nodeIndex);
				currentDepth--;
			} else {

				utility.dealWithNoSplit(traindata, rightNode);

			}

		}
		return root;
	}

	public double accuracyTest(String path) {
		TrainInput testingInput = new TrainInput(path);
		Double error = 0.0;
		ArrayList<ArrayList<Double>> testingData = testingInput.getInput();
		for (ArrayList<Double> a : testingData) {
			if (Double.compare(a.get(a.size() - 1), test(this.root, a)) == 0) {
				error++;
			}

		}

		this.accuracy = (1 - (error / testingData.size())) * 100;
		return this.accuracy;
	}

	public Double test(Node currentNode, ArrayList<Double> pattern) {
		if (currentNode.isLeaf() == false) {
			int index = currentNode.getAttributeIndex();
			if (Double.compare(pattern.get(index), currentNode.getSplitpoint()) < 0)
				return test(currentNode.getLeft(), pattern);
			else
				return test(currentNode.getRight(), pattern);
		}

		return currentNode.getSplitpoint();
	}

}