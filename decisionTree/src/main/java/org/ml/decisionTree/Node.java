package org.ml.decisionTree;

public class Node {

	public String attribute;
	public Double splitpoint;
	public int attributeIndex;

	public int getAttributeIndex() {
		return attributeIndex;
	}

	@Override
	public String toString() {
		return "Node [attribute=" + attribute + ", splitpoint=" + splitpoint
				+ ", attributeIndex=" + attributeIndex + " isLeaf=" + isLeaf
				+ "]";
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

	public Node leftChild;
	public Node rightChild;

	boolean isLeaf;

	public Node(String attribute) {

		this.attribute = attribute;
		this.isLeaf = false;
		this.attributeIndex = -1;
		leftChild = new Node();
		rightChild = new Node();
	}

	public void setLeft(Node a) {
		this.leftChild = a;
	}

	public Node getLeft() {

		return this.leftChild;
	}

	public Node getRight() {

		return this.rightChild;
	}

	public void setRight(Node a) {
		this.rightChild = a;

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
