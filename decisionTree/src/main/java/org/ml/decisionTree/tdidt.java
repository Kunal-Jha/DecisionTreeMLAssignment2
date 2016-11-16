package org.ml.decisionTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;

public class tdidt {

	private Node root;
	private int nodeIndex = 0;
	private Graph graph = new DefaultGraph("Tree");

	Double CLASS_POSITIVE = 1.0;
	Double CLASS_NEGATIVE = 0.0;

	public static void main(String[] args) {
		new tdidt();
	}

	public tdidt() {
		// TODO Auto-generated constructor stub
		TrainInput trainingInput = new TrainInput(
				"./assets/gene_expression_training.csv");
		ArrayList<ArrayList<Double>> trainingData = trainingInput.getInput();
		ArrayList<String> attributesList = trainingInput.getAttributes();
		/*
		 * ArrayList<ArrayList<Double>> trainingData1 = getSortedData(
		 * trainingData, 0); for (ArrayList<Double> a : trainingData1) { for
		 * (Double x : a) System.out.print(x + " "); System.out.println(); }
		 */
		Node node = graph.addNode(String.valueOf(nodeIndex));
		nodeIndex++;

		Graph T = tdidt_recursive(trainingData, attributesList, graph, node);
		T.display();
	}

	// Function to sort the data
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

	public Graph tdidt_recursive(ArrayList<ArrayList<Double>> trainingdata,
			ArrayList<String> attributesList, Graph T, Node node) {

		// if the examples are perfectly classified

		double c = trainingdata.get(0).get(trainingdata.get(0).size() - 1);
		if (isPerfectlyClassified(trainingdata)) {
			T.getNode(node.getId()).addAttribute("isLeaf", true);
			T.getNode(node.getId()).addAttribute("attribute", c); // 0 or 1
			return T;
		}

		// if else 3. 4. in algo
		// TODO ----------------
		// TODO ----------------
		// TODO ----------------
		// TODO ----------------
		// TODO ----------------

		// select best test
		Double minInformationGain_InterAttribute = Double.MAX_VALUE;
		int selectedAttributeIndex = 0;
		Double splitPointValue = 0.0;
		for (int i = 0; i < attributesList.size() - 1; i++) {
			Tuple minInformationGainInfo = getSplitPointAtMinimumInformationGainForAttribute(getAttributePlusClassifierColumnList(
					trainingdata, i));

			Double currentInformationGain = minInformationGainInfo
					.getInformationGain();

			if (currentInformationGain < minInformationGain_InterAttribute) {
				minInformationGain_InterAttribute = currentInformationGain;
				selectedAttributeIndex = i;
				splitPointValue = minInformationGainInfo.getSplitPoint();
			}
		}

		node.setAttribute("attribute",
				attributesList.get(selectedAttributeIndex));

		// 7 in algo. Two possibilities for tree to grow. We know the split
		// point and the attribute being analysed
		// remove it from the list of attributes (and examples, right?)

		ArrayList<ArrayList<Double>> lessThanBranchExampleList = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> greaterThanBranchExampleList = new ArrayList<ArrayList<Double>>();

		for (int i = 0; i < trainingdata.size(); i++) {
			if (trainingdata.get(i).get(selectedAttributeIndex) < splitPointValue) {
				trainingdata.get(i).remove(selectedAttributeIndex);// removing
																	// the
																	// already
																	// evaluated
																	// attribute.
				lessThanBranchExampleList.add(trainingdata.get(i));
			} else {
				trainingdata.get(i).remove(selectedAttributeIndex);
				greaterThanBranchExampleList.add(trainingdata.get(i));
			}
		}

		attributesList.remove(selectedAttributeIndex);

		Node lessThanNode = graph.addNode(String.valueOf(nodeIndex++));
		Node greaterThanNode = graph.addNode(String.valueOf(nodeIndex++));

		tdidt_recursive(lessThanBranchExampleList, attributesList, T,
				lessThanNode);
		tdidt_recursive(greaterThanBranchExampleList, attributesList, T,
				greaterThanNode);

		return T;
	}

	/**
	 *
	 * @param attributeColumnList
	 *            examples s.t. each row has a single attribute and a classifier
	 *            value
	 * @return the split point that gives minimum information gain
	 */
	private Tuple getSplitPointAtMinimumInformationGainForAttribute(
			ArrayList<ArrayList<Double>> attributeColumnList) {

		ArrayList<ArrayList<Double>> sortedAttributeColumnList = getSortedData(
				attributeColumnList, 0);

		ArrayList<ArrayList<Double>> informationGainValues = new ArrayList<ArrayList<Double>>();
		List<ArrayList<Double>> setOne, setTwo;
		int totalSize = sortedAttributeColumnList.size();
		// part about going through each
		for (int i = 0; i < totalSize - 1; i++) {
			// TODO check the logic for this part boys
			if (sortedAttributeColumnList.get(i).get(1) != sortedAttributeColumnList
					.get(i + 1).get(1)) { // if flip in class value 0-1 or 1-0
				setOne = sortedAttributeColumnList.subList(0, i);
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

				ArrayList<Double> informationGainTuple = new ArrayList<Double>();

				informationGainTuple.add(splitPoint);
				informationGainTuple.add(informationGain);

				informationGainValues.add(informationGainTuple);
			}
		}

		Double minInformationGain = Double.MAX_VALUE, resultSplitPoint = 0.0;
		for (int i = 0; i < informationGainValues.size(); i++) {
			if (informationGainValues.get(i).get(1) < minInformationGain) {
				resultSplitPoint = informationGainValues.get(i).get(0);
				minInformationGain = informationGainValues.get(i).get(1);
			}
		}

		return new Tuple(resultSplitPoint, minInformationGain);
	}

	private Double getEntropy(int positiveCount, int negativeCount) {
		int totalCount = positiveCount + negativeCount;
		// System.out.println(totalCount + " " + positiveCount + " "
		// + negativeCount);
		if (totalCount != 0 && positiveCount != 0 && negativeCount != 0) {
			return ((positiveCount / totalCount)
					* log2(totalCount / positiveCount) + (negativeCount / totalCount)
					* log2(totalCount / negativeCount));
		} else {
			return 0.0;
		}
	}

	private ArrayList<ArrayList<Double>> getAttributePlusClassifierColumnList(
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

	private ArrayList<Double> getAttributeColumnList(
			ArrayList<ArrayList<Double>> examplesList, int columnIndex) {
		ArrayList<Double> result = new ArrayList<Double>();
		for (int i = 0; i < examplesList.size(); i++) {
			result.add(examplesList.get(i).get(columnIndex));
		}
		return result;
	}

	private boolean isPerfectlyClassified(
			ArrayList<ArrayList<Double>> examplesList) {
		double c = examplesList.get(0).get(examplesList.get(0).size() - 1);
		for (int i = 1; i < examplesList.size(); i++) {
			if (c != examplesList.get(i).get(examplesList.get(0).size() - 1)) {
				return false;
			}
		}
		return true;
	}

	private double log2(double value) {
		if (value != 0)
			return Math.log10(value) / Math.log10(2);
		else
			return 0.0;
	}

	private class Tuple {
		private Double informationGain;
		private Double splitPoint;

		public Tuple(Double _informationGain, Double _splitPoint) {
			this.informationGain = _informationGain;
			this.splitPoint = _splitPoint;
		}

		public Double getSplitPoint() {
			return splitPoint;
		}

		public void setSplitPoint(Double splitPoint) {
			this.splitPoint = splitPoint;
		}

		public Double getInformationGain() {
			return informationGain;
		}

		public void setInformationGain(Double informationGain) {
			this.informationGain = informationGain;
		}
	}

}
