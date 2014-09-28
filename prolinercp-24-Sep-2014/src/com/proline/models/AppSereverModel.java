package com.proline.models;

import java.util.HashMap;

public class AppSereverModel {

	public AppSereverModel() {

	}
	private HashMap<Integer, String> indexMap = new HashMap<Integer, String>();

	public void addDataIndex(int index, String data) {
		indexMap.put(index, data);
	}

	public String getData(int index) {
		return indexMap.get(index);
	}

	public HashMap<Integer, String> getAllData() {
		return indexMap;
	}
}
