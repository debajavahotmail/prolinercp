package com.proline.rcp.util;

import java.awt.Color;
import java.awt.GradientPaint;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import com.proline.models.dashboards.DashBoardChart;
import com.proline.models.dashboards.DashBoards;
import com.proline.rcp.constants.FileConstants;
import com.proline.rcp.views.charts.ProlineBarChartToolTip;
import com.proline.rcp.views.charts.ProlinePieToolTipGenerator;

/**
 * @author Debadatta Mishra (PIKU)
 *
 */
public class ChartUtils {

	private static List<DashBoardChart> getCharts() {
		List<DashBoardChart> chartsList = new ArrayList<DashBoardChart>();
		InputStream inStream = null;
		try {
			inStream = new FileInputStream(FileConstants.DASHBOARD_CONFIG_XML_FILE);
			JAXBContext context = JAXBContext.newInstance(DashBoards.class);
			Unmarshaller unMarshaller = context.createUnmarshaller();
			DashBoards dashBoard = (DashBoards)unMarshaller.unmarshal(inStream);
			chartsList = dashBoard.getAvlCharts().getChartList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(inStream != null) {
					inStream.close();
				}
			} catch (Exception e2) {
			}
		}
		return chartsList;
	}

	public static List<DashBoardChart> getListOfCharts() {
		return getCharts();
	}

	private static CategoryDataset getBarDataSource( Map<String,Number> dataMap , boolean colourFlag ) {
		final DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
		String defaultSeries = "default";
		//Iterate the map and populate the data
		for( Iterator<Map.Entry<String, Number>> itr = dataMap.entrySet().iterator() ; itr.hasNext() ; ) {
			Map.Entry<String, Number> me = (Map.Entry<String, Number>) itr.next();
			if(colourFlag)
				barDataset.addValue(me.getValue(), me.getKey(), me.getKey());
			else
				barDataset.addValue(me.getValue(), defaultSeries, me.getKey());
		}
		return barDataset;
	}

	private static PieDataset getPieDataSource( Map<String,Number> dataMap  ) {
		DefaultPieDataset pieDataset = new DefaultPieDataset();
		for( Iterator<Map.Entry<String, Number>> itr = dataMap.entrySet().iterator() ; itr.hasNext() ; ) {
			Map.Entry<String, Number> me = (Map.Entry<String, Number>) itr.next();
			pieDataset.setValue(me.getKey(), me.getValue());
		}
		return pieDataset;
	}
	
	public static JFreeChart getPieChart(Map<String,Number> dataMap, boolean colorFlag ,final String title) {
		PieDataset dataset = getPieDataSource(dataMap); 
		JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setSimpleLabels(true);
		PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
				"{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
		plot.setLabelGenerator(gen);
		
		customizeLookAndFeel(chart, plot);
		return chart;
	}
	
	public static JFreeChart getPieChart(Map<String,Number> dataMap, boolean colorFlag ,final String title, Map<String,String> toolTipMap) {
		if( toolTipMap == null )
			return getPieChart(dataMap, colorFlag ,title);
		PieDataset dataset = getPieDataSource(dataMap); 
		JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setSimpleLabels(true);
		
		if(toolTipMap != null && toolTipMap.size() != 0) {
			ProlinePieToolTipGenerator toolTipGenerator = new ProlinePieToolTipGenerator(toolTipMap);
			plot.setToolTipGenerator(toolTipGenerator);
		}
		
		PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
				"{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
		plot.setLabelGenerator(gen);
		
		customizeLookAndFeel(chart, plot);
		return chart;
	}
	
	public static JFreeChart getBarChart(Map<String, Number> dataMap,
			boolean colorFlag, final String title, String xAxisTitle,
			String yAxisTitle, Map<String, String> toolTipMap) {
		CategoryDataset cateGorySet = getBarDataSource(dataMap, colorFlag);
		return getBarChart(cateGorySet, title, xAxisTitle, yAxisTitle,toolTipMap);
	}
	
	private static JFreeChart getBarChart(final CategoryDataset dataset, final String title,String xAxisTitle,String yAxisTitle,Map<String, String> toolTipMap) {
		ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
		BarRenderer.setDefaultBarPainter(new StandardBarPainter());
		final JFreeChart chart = ChartFactory.createBarChart(title, xAxisTitle, yAxisTitle, dataset);
		final CategoryPlot plot = chart.getCategoryPlot();
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setSeriesPaint(0, new GradientPaint( 0.0f, 0.0f, Color.BLUE, 0.0f, 0.0f, Color.BLUE ));
		
		if(toolTipMap != null && toolTipMap.size() != 0) {
			ProlineBarChartToolTip bctp = new ProlineBarChartToolTip(toolTipMap);
			renderer.setBaseToolTipGenerator(bctp);
		}
		customizeLookAndFeel(chart, plot);
		return chart;
	}
	
	private static void customizeLookAndFeel(JFreeChart chart,Plot plot) {
		Color clr = Color.WHITE;
		plot.setBackgroundPaint(clr);
		plot.setOutlineVisible(false);
		chart.getTitle().setPaint(Color.GRAY);
		chart.setBorderPaint(clr);
		chart.setBorderPaint(clr);
		chart.setBorderVisible(false);
	}

}
