package com.proline.models;

import java.util.HashMap;
import java.util.Map;

public class AppServerConnectionsModel {

		private Map<String,AppServerSystemModel> AppServerMoldelMap = new HashMap<String,AppServerSystemModel>();

		public Map<String, AppServerSystemModel> getAppServerMoldelMap() {
			return AppServerMoldelMap;
		}

		public void setAppServerMoldelMap(Map<String, AppServerSystemModel> AppServerMoldelMap) {
			this.AppServerMoldelMap = AppServerMoldelMap;
		}

}
