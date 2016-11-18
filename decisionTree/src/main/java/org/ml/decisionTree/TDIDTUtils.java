package org.ml.decisionTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TDIDTUtils {

	Double CLASS_POSITIVE = 1.0;
	Double CLASS_NEGATIVE = 0.0;

	public ArrayList<ArrayList<Double>> getAttributeClassifierColumn(
			ArrayList<ArrayList<Double>> examplesList, int columnIndex) {
		ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < examplesList.size(); i++) {
			ArrayList<Double> row = new ArrayList<Double>();
			row.add(examplesList.get(i).get(columnIndex));
			row.add(examplesList.get(i).get(examplesList.get(i).size() - 1));
			result.add(row);
		}
		return result;
	}

	public Tuple chooseAttribute(ArrayList<ArrayList<Double>> trainingData,
			ArrayList<String> attributesList, int nodecount, int depthAllowed) {

		if (nodecount > depthAllowed) {
			return null;
		}
		Double maxInformationGain_InterAttribute = Double.MIN_VALUE;
		int selectedAttributeIndex = 0;

		Double splitPointValue = 0.0;
		Double colSplitPointValue = 0.0;
		for (int i = 0; i < attributesList.size() - 1; i++) {
			Tuple columnTuple = this.getBestSplitforAttribute(this
					.getAttributeClassifierColumn(trainingData, i), i);
			columnTuple.setSelectedIndex(i);

			Double currentInformationGain = columnTuple.getInformationGain();
			// Get max information gain
			if (currentInformationGain > maxInformationGain_InterAttribute) {
				maxInformationGain_InterAttribute = currentInformationGain;
				splitPointValue = columnTuple.getSplitPoint();
				selectedAttributeIndex = i;
				colSplitPointValue = columnTuple.getSplitPoint();
			}
		}

		Tuple result = new Tuple(maxInformationGain_InterAttribute,
				splitPointValue, selectedAttributeIndex);
		result.colSplitPoint = colSplitPointValue;
		return result;

	}

	public Tuple getBestSplitforAttribute(
			ArrayList<ArrayList<Double>> attributeColumnList, int attributeIndex) {
		ArrayList<ArrayList<Double>> sortedAttributeColumnList = getSortedData(
				attributeColumnList, 0);
		ArrayList<ArrayList<Double>> splitsGainValues = new ArrayList<ArrayList<Double>>();
		List<ArrayList<Double>> setOne, setTwo;
		int totalSize = sortedAttributeColumnList.size();
		// part about going through each
		for (int i = 0; i < totalSize - 1; i++) {
			// TODO check the logic for this part boys
			if (sortedAttributeColumnList.get(i).get(1) != sortedAttributeColumnList
					.get(i + 1).get(1)) { // if flip in class value 0-1 or 1-0

				setOne = sortedAttributeColumnList.subList(0, i);
				//System.out.println(setOne.toString());
				setTwo = sortedAttributeColumnList
						.subList(i + 1, totalSize - 1);

				// set One positives and negatives needed for entropy
				int setOnePositiveCount = 0, setOneNegativeCount = 0;
				for (int j = 0; j < setOne.size(); j++) {
					if (setOne.get(j).get(1).equals(CLASS_POSITIVE)) {
						setOnePositiveCount++;
					} else if (setOne.get(j).get(1).equals(CLASS_NEGATIVE)) {
						setOneNegativeCount++;
					}
				}
				// set Two positives and negatives needed for entropy
				int setTwoPositiveCount = 0, setTwoNegativeCount = 0;
				for (int j = 0; j < setTwo.size(); j++) {
					if (setTwo.get(j).get(1).equals(CLASS_POSITIVE)) {
						setTwoPositiveCount++;
					} else if (setTwo.get(j).get(1).equals(CLASS_NEGATIVE)) {
						setTwoNegativeCount++;
					}
				}

				double informationGain = getEntropy(setOnePositiveCount
						+ setTwoPositiveCount, setOneNegativeCount
						+ setTwoNegativeCount)
						- ((getEntropy(setOnePositiveCount, setOneNegativeCount)
								* (i + 1) / totalSize) + (getEntropy(
								setTwoPositiveCount, setTwoNegativeCount)
								* (totalSize - i) / totalSize));

				double splitPoint = (sortedAttributeColumnList.get(i).get(0) + sortedAttributeColumnList
						.get(i + 1).get(0)) / 2.0;

				if(Double.compare(splitPoint, 0.0) == 0){
					System.out.println("-----" + splitPoint);
				}



				ArrayList<Double> rowInfoGain = new ArrayList<Double>();

				rowInfoGain.add(splitPoint);
				rowInfoGain.add(informationGain);


				splitsGainValues.add(rowInfoGain);
			}
		}

		Double maxInformationGain = Double.MIN_VALUE, resultSplitPoint = 0.0;
		//int index = -1;
		for (int i = 0; i < splitsGainValues.size(); i++) {
			if (splitsGainValues.get(i).get(1) >= maxInformationGain) {
				resultSplitPoint = splitsGainValues.get(i).get(0);
				maxInformationGain = splitsGainValues.get(i).get(1);

			}
		}

		return new Tuple(resultSplitPoint, maxInformationGain, attributeIndex);
	}

	public Double getEntropy(int positiveCount, int negativeCount) {
		int totalCount = positiveCount + negativeCount;
		// System.out.println(totalCount + " " + positiveCount + " "
		// + negativeCount);
		if (totalCount != 0 && positiveCount != 0 && negativeCount != 0) {
			return (((double)positiveCount / totalCount)
					* log2((double)totalCount / positiveCount) + ((double)negativeCount / totalCount)
					* log2((double)totalCount / negativeCount));
		} else {
			return 0.0;
		}
	}

	public boolean isPerfectlyClassified(
			ArrayList<ArrayList<Double>> examplesList) {

		for (int i = 0; i < examplesList.size() - 1; i++) {
			if ((examplesList.get(i + 1).get(examplesList.get(0).size() - 1) != (examplesList
					.get(i).get(examplesList.get(0).size() - 1)))) {
				return false;
			}
		}
		return true;
	}

	// TODO:Check Dataset
	public Node dealWithNoSplit(ArrayList<ArrayList<Double>> traindata,
			Node currentNode) {
		int countPos = 0;
		int countNeg = 0;

		for (ArrayList<Double> row : traindata) {

			//for (Double value : row) {
				if (row.get(row.size()-1) == 0)
					countNeg+=1;
				else
					countPos++;
			//}
		}
		currentNode.setLeaf(true);
		if (countNeg > countPos) {
			Node x = new Node();
			x.setAttribute("0");
			x.setSplitpoint(0.0);
			currentNode.setLeft(x);

		} else {

			Node x = new Node();
			x.setAttribute("1");
			x.setSplitpoint(1.0);
			currentNode.setRight(x);
		}

		return currentNode;
	}

	public ArrayList<ArrayList<Double>> getSortedData(
			ArrayList<ArrayList<Double>> examplesList, final int columnIndex) {
		Collections.sort(examplesList, new Comparator<ArrayList<Double>>() {

			@Override
			public int compare(ArrayList<Double> arg0, ArrayList<Double> arg1) {
				return arg0.get(columnIndex).compareTo(arg1.get(columnIndex));
			}

		});
		return examplesList;
	}

	public double log2(double value) {
		if (value != 0)
			return Math.log10(value) / Math.log10(2);
		else
			return 0.0;
	}

}