package com.proline.models;

import java.util.List;

public class AppServerModelProvider {
	
	private List<AppSereverModel> AppServerModelList;
	
		private String titles1 = null;
		
		
		public List<AppSereverModel> getAppServerModelList() {
			return AppServerModelList;
		}
	
		public void setAppServerModelList(List<AppSereverModel> AppServerModelList) {
			this.AppServerModelList = AppServerModelList;
		}
	
		public String getTitles1() {
			return titles1;
		}
	
		public void setTitles1(String titles1) {
			this.titles1 = titles1;
		}
	
		public AppServerModelProvider() {
		}
	
		public String[] getTitles() {
			String[]  titles = { "First name", "Last name", "Gender", "Married" };
	
			return titles;
		}
}
