package com.proline.models.dashboards;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Debadatta Mishra
 *
 */
@XmlRootElement(name="DashBoard")
@XmlAccessorType(XmlAccessType.FIELD)
public class DashBoards {
	
	@XmlElement(name="AvailableCharts")
	private AvailableCharts avlCharts;

	public AvailableCharts getAvlCharts() {
		return avlCharts;
	}

	public void setAvlCharts(AvailableCharts avlCharts) {
		this.avlCharts = avlCharts;
	}
}
