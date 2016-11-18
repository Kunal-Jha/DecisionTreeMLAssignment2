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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((colSplitPoint == null) ? 0 : colSplitPoint.hashCode());
		result = prime * result
				+ ((informationGain == null) ? 0 : informationGain.hashCode());
		result = prime * result + selectedIndex;
		result = prime * result
				+ ((splitPoint == null) ? 0 : splitPoint.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tuple other = (Tuple) obj;
		if (colSplitPoint == null) {
			if (other.colSplitPoint != null)
				return false;
		} else if (!colSplitPoint.equals(other.colSplitPoint))
			return false;
		if (informationGain == null) {
			if (other.informationGain != null)
				return false;
		} else if (!informationGain.equals(other.informationGain))
			return false;
		if (selectedIndex != other.selectedIndex)
			return false;
		if (splitPoint == null) {
			if (other.splitPoint != null)
				return false;
		} else if (!splitPoint.equals(other.splitPoint))
			return false;
		return true;
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

	@Override
	public String toString() {
		return "Tuple [informationGain=" + informationGain + ", splitPoint=" + splitPoint + ", selectedIndex="
				+ selectedIndex + ", colSplitPoint=" + colSplitPoint + "]";
	}

}