package com.proline.models.dashboards;

import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.JFreeChart;

import com.proline.rcp.constants.FileConstants;
import com.proline.rcp.util.ChartUtils;
import com.proline.rcp.views.charts.ProlineBarChartComposite;
import com.proline.rcp.views.charts.ProlineChartComposite;
import com.proline.rcp.views.charts.ProlinePieChartComposite;

/**
 * @author Debadatta Mishra (PIKU)
 *
 */
public enum ChartType {

	BAR {
		@Override
		public String getImage() {
			return FileConstants.BAR_CHART_ICON;
		}

		public ProlineChartComposite getChartComposite(Composite topComposite, int style, JFreeChart chart,boolean useBuffer) {
			return new ProlineBarChartComposite(topComposite, style, chart, useBuffer);
		}

		public JFreeChart getChart(ChartBuilder builder) {
			ChartModel model = builder.getChartModel();
			return ChartUtils.getBarChart(model.getDataMap(),
					model.isColorFlag(), model.getTitle(),
					model.getxAxisTitle(), model.getyAxisTitle(),
					model.getToolTipMap());
		}

	},
	PIE {
		@Override
		public String getImage() {
			return FileConstants.PIE_CHART_ICON;
		}

		public ProlineChartComposite getChartComposite(Composite topComposite, int style, JFreeChart chart,boolean useBuffer) {
			return new ProlinePieChartComposite(topComposite, style, chart, useBuffer);
		}

		public JFreeChart getChart(ChartBuilder builder) {
			ChartModel model = builder.getChartModel();
			return ChartUtils.getPieChart(model.getDataMap(), model.isColorFlag(), model.getTitle(),model.getToolTipMap());
		}
	};

	public abstract String getImage();

	public abstract ProlineChartComposite getChartComposite(Composite comp, int style, JFreeChart chart,boolean useBuffer);

	public abstract JFreeChart getChart(ChartBuilder builder);

}
