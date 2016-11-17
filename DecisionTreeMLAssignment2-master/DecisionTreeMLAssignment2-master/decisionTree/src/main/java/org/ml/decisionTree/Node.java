package org.ml.decisionTree;

import java.util.ArrayList;

public class Node {

	public String attribute;

	public ArrayList<Node> children = new ArrayList<Node>();
	boolean isLeaf;

	public Node(String attribute) {

		this.attribute = attribute;
		this.children = null;
		this.isLeaf = false;
	}

	public void setLeft(Node a) {
		this.children.add(0, a);
	}

	public Node getLeft() {
		return this.children.get(0);
	}

	public Node getRight() {
		return this.children.get(1);
	}

	public void setRight(Node a) {
		this.children.add(1, a);
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public Node() {

	}

}