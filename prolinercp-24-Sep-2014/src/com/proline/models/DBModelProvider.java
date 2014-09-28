package com.proline.models;

import java.util.ArrayList;
import java.util.List;

public class DBModelProvider {
	
	private String partName = null;
	
	private String savedQuery = null;
	
	private List<DBModel> dbModelList;
	
	private String[] titles1 = null;
	
	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	
	public List<DBModel> getDbModelList() {
		return dbModelList;
	}

	public void setDbModelList(List<DBModel> dbModelList) {
		this.dbModelList = dbModelList;
	}  
	public String[] getTitles1() {
		return titles1;
	}

	public void setTitles1(String[] titles1) {
		this.titles1 = titles1;
	}

	public DBModelProvider() {
	}

	public List<DBModel> getModesl() {
		return dbModelList;
	}
	

	public String[] getTitles() {
		String[]  titles = { "First name", "Last name", "Gender", "Married" };

		return titles;
	}

	public String getSavedQuery() {
		return savedQuery;
	}

	public void setSavedQuery(String savedQuery) {
		this.savedQuery = savedQuery;
	}
	
	

}
