package com.proline.models;

import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;

public class TreeViewerModel {
	
	private TreeViewer treeViewer;
	private String childTreeName;
	private int levelNo;
	private List<String> columnNames;
	
	private DBModel dbModel;
	
	public DBModel getDbModel() {
		return dbModel;
	} 
	public void setDbModel(DBModel dbModel) {
		this.dbModel = dbModel;
	}
	public TreeViewer getTreeViewer() {
		return treeViewer;
	}
	public void setTreeViewer(TreeViewer treeViewer) {
		this.treeViewer = treeViewer;
	}
	public String getChildTreeName() {
		return childTreeName;
	}
	public void setChildTreeName(String childTreeName) {
		this.childTreeName = childTreeName;
	}
	public int getLevelNo() {
		return levelNo;
	}
	public void setLevelNo(int levelNo) {
		this.levelNo = levelNo;
	}
	public List<String> getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}
}
