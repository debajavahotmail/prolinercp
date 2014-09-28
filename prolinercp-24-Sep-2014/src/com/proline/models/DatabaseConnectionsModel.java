package com.proline.models;

import java.util.HashMap;
import java.util.Map;

public class DatabaseConnectionsModel {
	
	private Map<String,DBSystemModel> dbMoldelMap = new HashMap<String,DBSystemModel>();

	public Map<String, DBSystemModel> getDbMoldelMap() {
		return dbMoldelMap;
	}

	public void setDbMoldelMap(Map<String, DBSystemModel> dbMoldelMap) {
		this.dbMoldelMap = dbMoldelMap; 
	}

}
