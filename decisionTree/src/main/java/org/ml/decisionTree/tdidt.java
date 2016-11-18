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
		// algo.accuracyTest("./assets/gene_expression_test.csv");
	}

	tdidt(String path) {
		// TODO Auto-generated constructor stub
		TrainInput trainingInput = new TrainInput(path);
		this.trainingData = trainingInput.getInput();
		this.attributesList = trainingInput.getAttributes();
		this.root = new Node(Integer.toString(nodeIndex));
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
	/****************/
	int DEPTH_ALLOWED = 3;
	int currentDepth = 1;
	/****************/
	
	public Node tdidt_recursive(ArrayList<ArrayList<Double>> traindata,
			ArrayList<String> attributesList, Node root, Node currentNode,
			int nodeCount) {

		// if the examples are perfectly classified
		if (!traindata.isEmpty() ) {
			if (utility.isPerfectlyClassified(traindata)) {
				currentNode.setLeaf(true);
				currentNode.setSplitpoint(Double.MIN_VALUE);
				return root;

			}
		}

		Tuple attribute = utility.chooseAttribute(traindata, attributesList,
				currentDepth, DEPTH_ALLOWED);

		if ((attribute == null)) {
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

		String attributeName = attributesList.get(attribute.getSelectedIndex());
		
		// TODO problem is here. delete more efficiently
		if(!attributesList.isEmpty()){
			attributesList.remove(attribute.getSelectedIndex());
		}
		// TODO Check passing of Node Index it should be equal to 0s
		// Storing Tree Information
		this.nodeIndex++;
		
		Node leftNode = new Node("\"" +/* Integer.toString(nodeIndex) +*/ ""+ attributeName + " < " + attribute.getColSplitPoint() + "\"");
		
		this.nodeIndex++;
		Node rightNode = new Node("\""+/*Integer.toString(nodeIndex) +*/ ""+ attributeName+ " > " + attribute.getColSplitPoint() +"\"");
		
		

		
		currentNode.setLeft(leftNode);
		currentNode.setRight(rightNode);
		currentNode.setSplitpoint(attribute.getColSplitPoint());
		currentNode.setAttributeIndex(attribute.getSelectedIndex());
		
		//System.out.println("- " + leftNode.toString());
		//System.out.println(rightNode.toString());
		
		if(currentDepth < DEPTH_ALLOWED) {
			currentDepth ++;
			System.out.println(leftNode.attribute + " " + currentDepth);
			tdidt_recursive(lessThanBranchData, attributesList, root, leftNode,
				this.nodeIndex);
			currentDepth --;
		} else {
			utility.dealWithNoSplit(traindata, leftNode);
		}
		
		if(currentDepth < DEPTH_ALLOWED) {
			currentDepth ++;
			System.out.println(rightNode.attribute + " " + currentDepth);
			tdidt_recursive(greaterThanBranchData, attributesList, root, rightNode,
				this.nodeIndex);
			currentDepth --;
		} else {
			
			utility.dealWithNoSplit(traindata, rightNode);

		}
		//System.out.println(currentDepth);

		return root;
	}

	public double accuracyTest(String path) {
		TrainInput testingInput = new TrainInput(path);
		Double error = 0.0;
		ArrayList<ArrayList<Double>> testingData = testingInput.getInput();
		for (ArrayList<Double> a : testingData) {
			if (a.get(a.size() - 1).equals(test(this.root, a))) {
				error++;
			}

		}

		this.accuracy = (1 - (error / testingData.size())) * 100;
		return this.accuracy;
	}

	public Double test(Node currentNode, ArrayList<Double> pattern) {
		if (currentNode.isLeaf() == false) {
			int index = currentNode.getAttributeIndex();
			if (pattern.get(index) < currentNode.getSplitpoint())
				return test(currentNode.getLeft(), pattern);
			else
				return test(currentNode.getRight(), pattern);
		}

		return currentNode.getSplitpoint();
	}

}
