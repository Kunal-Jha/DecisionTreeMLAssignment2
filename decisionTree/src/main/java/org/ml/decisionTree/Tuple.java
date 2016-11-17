package org.ml.decisionTree;

public class Tuple {
	Double informationGain;
	Double splitPoint;
	int selectedIndex;
	Double colSplitPoint; // Not applicable for col split

	public Double getColSplitPoint() {
		return colSplitPoint;
	}

	public void setColSplitPoint(Double colSplitPoint) {
		this.colSplitPoint = colSplitPoint;
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public void setInformationGain(Double informationGain) {
		this.informationGain = informationGain;
	}

	public void setSplitPoint(Double splitPoint) {
		this.splitPoint = splitPoint;
	}

	public Tuple(Double _informationGain, Double _splitPoint, int index) {
		this.informationGain = _informationGain;
		this.splitPoint = _splitPoint;
		this.selectedIndex = index;
	}

	public Double getSplitPoint() {
		return splitPoint;
	}

	public Double getInformationGain() {
		return informationGain;
	}

}