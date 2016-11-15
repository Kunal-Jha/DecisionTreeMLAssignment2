package org.ml.decisionTree;

import java.util.ArrayList;

public class Node {

	private String attribute;

	private ArrayList<Node> children = new ArrayList<Node>();

	public Node(String attribute, ArrayList<Node> children) {
		super();
		this.attribute = attribute;
		this.children = children;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public ArrayList<Node> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}

	public Node() {

	}

}
