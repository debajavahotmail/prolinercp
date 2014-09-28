package com.proline.models.dashboards;

import java.util.Map;

/**
 * @author Debadatta Mishra
 *
 */
public class ChartBuilder {
	
	private ChartModel chartModel;
	
	public ChartBuilder() {
		chartModel = new ChartModel();
	}
	
	public ChartBuilder buildDataMap(Map<String,Number> dataMap){
		chartModel.setDataMap(dataMap);
		return this;
	}
	
	public ChartBuilder buildTooTipMap(Map<String,String> toolTipMap){
		chartModel.setToolTipMap(toolTipMap);
		return this;
	}
	
	public ChartBuilder buildColor(boolean colorFlag){
		chartModel.setColorFlag(colorFlag);
		return this;
	}
	
	public ChartBuilder buildTitle(String title){
		chartModel.setTitle(title);
		return this;
	}
	
	public ChartBuilder buildXAxisTitle(String xAxisTitle){
		chartModel.setxAxisTitle(xAxisTitle);
		return this;
	}
	
	public ChartBuilder buildYAxisTitle(String yAxisTitle){
		chartModel.setyAxisTitle(yAxisTitle);
		return this;
	}
	
	public ChartModel getChartModel(){
		return chartModel;
	}

}
