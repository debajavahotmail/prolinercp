package com.proline.models.dashboards;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Debadatta Mishra
 *
 */
@XmlRootElement(name="AvailableCharts")
public class AvailableCharts {
	
	private List<DashBoardChart> chartList = new ArrayList<DashBoardChart>();

	@XmlElement(name="Chart")
	public List<DashBoardChart> getChartList() {
		return chartList;
	}

	public void setChartList(List<DashBoardChart> chartList) {
		this.chartList = chartList;
	}
	
}
