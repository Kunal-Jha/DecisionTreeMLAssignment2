package org.ml.decisionTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class trainInput {
	public static void main(String[] args) {

		BufferedReader buffer = null;

		try {
			String line;
			buffer = new BufferedReader(
					new FileReader(
							"/Users/Kunal/Downloads/programming-assignment1/gene_expression_training.csv"));

			while ((line = buffer.readLine()) != null) {

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (buffer != null)
					buffer.close();
			} catch (IOException crunchifyException) {
				crunchifyException.printStackTrace();
			}
		}
	}

	public static ArrayList<ArrayList<Double>> csvtoArrayList(String csvString) {

		ArrayList<ArrayList<Double>> input = new ArrayList<ArrayList<Double>>();

		if (csvString != null) {
			String[] splitData = csvString.split("\\s*,\\s*");
			ArrayList<Double> row = new ArrayList<Double>();
			for (int i = 0; i < splitData.length; i++) {
				if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
					Double.parseDouble(splitData[i].trim());
				}
			}
		}

		return input;
	}
}
