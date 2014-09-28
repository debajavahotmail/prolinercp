package com.proline.models.dashboards;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Debadatta Mishra
 *
 */
@XmlRootElement(name="Chart")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"title", "xAxis", "yAxis","batchDetail","dbQuery"})
public class DashBoardChart {
	
	@XmlAttribute(name="type") private ChartType type;
	@XmlElement(name="Title") private String title;
	@XmlElement(name="xAxis") private String xAxis;
	@XmlElement(name="yAxis") private String yAxis;
	@XmlElement(name="BatchDetails") private String batchDetail;
	@XmlElement(name="DatabaseQuery") private String dbQuery;
	
	public DashBoardChart() {
		
	}

	public ChartType getType() {
		return type;
	}

	public void setType(ChartType type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getxAxis() {
		return xAxis;
	}

	public void setxAxis(String xAxis) {
		this.xAxis = xAxis;
	}

	public String getyAxis() {
		return yAxis;
	}

	public void setyAxis(String yAxis) {
		this.yAxis = yAxis;
	}

	public String getBatchDetail() {
		return batchDetail;
	}

	public void setBatchDetail(String batchDetail) {
		this.batchDetail = batchDetail;
	}

	public String getDbQuery() {
		return dbQuery;
	}

	public void setDbQuery(String dbQuery) {
		this.dbQuery = dbQuery;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DashBoardChart other = (DashBoardChart) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DashBoardChart [type=" + type + ", title=" + title + ", xAxis="
				+ xAxis + ", yAxis=" + yAxis + ", batchDetail=" + batchDetail
				+ ", dbQuery=" + dbQuery + "]";
	}
}
