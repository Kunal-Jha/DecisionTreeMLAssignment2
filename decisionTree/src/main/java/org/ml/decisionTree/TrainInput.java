package org.ml.decisionTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TrainInput {
	private String filePath;
	private ArrayList<String> attributes = new ArrayList<String>();
	private ArrayList<ArrayList<Double>> input = new ArrayList<ArrayList<Double>>();

	public void readData() {

		BufferedReader buffer = null;

		try {
			String line;
			buffer = new BufferedReader(new FileReader(this.filePath));

			String[] splitHeader = buffer.readLine().split("\\s*,\\s*");
			for (int i = 0; i < splitHeader.length; i++) {
				if (!(splitHeader[i] == null)
						|| !(splitHeader[i].length() == 0)) {
					this.attributes.add(splitHeader[i]);

				}
			}

			while ((line = buffer.readLine()) != null) {
				csvtoArrayList(line);
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

	public void csvtoArrayList(String csvString) {

		if (csvString != null) {
			String[] splitData = csvString.split("\\s*,\\s*");
			ArrayList<Double> row = new ArrayList<Double>();
			for (int i = 0; i < splitData.length; i++) {
				if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
					row.add(Double.parseDouble(splitData[i].trim()));
				}
			}
			this.input.add(row);
		}

	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public ArrayList<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(ArrayList<String> attributes) {
		this.attributes = attributes;
	}

	public ArrayList<ArrayList<Double>> getInput() {
		return input;
	}

	public void setInput(ArrayList<ArrayList<Double>> input) {
		this.input = input;
	}

	public TrainInput(String filePath) {
		this.filePath = filePath;
		this.readData();
	}

}
