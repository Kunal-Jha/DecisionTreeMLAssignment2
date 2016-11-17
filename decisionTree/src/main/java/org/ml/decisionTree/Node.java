package org.ml.decisionTree;

import java.util.ArrayList;

public class Node {

	public String attribute;
	public Double splitpoint;
	public int attributeIndex;

	public int getAttributeIndex() {
		return attributeIndex;
	}

	public void setAttributeIndex(int attributeIndex) {
		this.attributeIndex = attributeIndex;
	}

	public Double getSplitpoint() {
		return splitpoint;
	}

	public void setSplitpoint(Double splitpoint) {
		this.splitpoint = splitpoint;
	}

	public ArrayList<Node> children;
	boolean isLeaf;

	public Node(String attribute) {

		this.attribute = attribute;
		this.children = new ArrayList<Node>();
		this.isLeaf = false;
		this.attributeIndex = -1;
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
