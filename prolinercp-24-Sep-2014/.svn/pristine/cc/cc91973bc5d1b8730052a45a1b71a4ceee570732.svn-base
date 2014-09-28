package com.proline.rcp.views.charts;

import java.util.Map;

import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.data.category.CategoryDataset;

/**Fully customized fo Shrinath
 * @author Debadatta Mishra (PIKU)
 *
 */
public class ProlineBarChartToolTip implements CategoryToolTipGenerator {

private Map<String,String> descMap;
	
	public ProlineBarChartToolTip(Map<String,String> descMap) {
		this.descMap = descMap;
	}
	
	@Override
	public String generateToolTip(CategoryDataset cd, int arg1, int arg2) {
		String key = (String) cd.getColumnKey(arg2);
		return descMap.get(key);
	}

}
