package org.ml.decisionTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {
	public static void main(String[] args) {

		BufferedReader buffer = null;

		try {
			String line;
			buffer = new BufferedReader(
					new FileReader(
							"/Users/Kunal/Downloads/programming-assignment1/gene_expression_training.csv"));

			while ((line = buffer.readLine()) != null) {
				System.out.println("Raw CSV data: " + line);
				System.out.println("Converted ArrayList data: "
						+ csvtoArrayList(line) + "\n");
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

	public static ArrayList<String> csvtoArrayList(String csvString) {
		ArrayList<String> result = new ArrayList<String>();

		if (csvString != null) {
			String[] splitData = csvString.split("\\s*,\\s*");
			for (int i = 0; i < splitData.length; i++) {
				if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
					result.add(splitData[i].trim());
				}
			}
		}

		return result;
	}
}
