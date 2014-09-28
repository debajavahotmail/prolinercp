package com.proline.rcp.views.charts;

import java.util.Map;

import org.jfree.chart.labels.PieToolTipGenerator;
import org.jfree.data.general.PieDataset;

/**
 * @author Debadatta Mishra
 *
 */
public class ProlinePieToolTipGenerator implements PieToolTipGenerator {

	private Map<String,String> descMap;
	
	public ProlinePieToolTipGenerator(Map<String,String> descMap) {
		this.descMap = descMap;
	}
	
	@Override
	public String generateToolTip(PieDataset dataset, @SuppressWarnings("rawtypes") Comparable pieChartKey) {
		return descMap.get(pieChartKey);
	}
	
}
