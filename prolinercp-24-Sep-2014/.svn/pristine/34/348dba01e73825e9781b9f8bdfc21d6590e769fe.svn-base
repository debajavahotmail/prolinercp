package com.proline.rcp.views.charts;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.PieSectionEntity;
import org.jfree.chart.entity.StandardEntityCollection;

/**
 * Fully customized for Shrinath
 * 
 * @author Debadatta Mishra (PIKU)
 *
 */
public class ProlinePieChartComposite extends ProlineChartComposite {

	public ProlinePieChartComposite(Composite comp, int style,
			JFreeChart chart, boolean useBuffer) {
		super(comp, style, chart, useBuffer);
	}

	@Override
	public String getCustomClickableValue(MouseEvent me) {
		String result = null;
		ChartRenderingInfo info = super.getChartRenderingInfo();
		if (info != null) {
			StandardEntityCollection entities = (StandardEntityCollection) info
					.getEntityCollection();
			if (entities != null) {
				Rectangle insets = getClientArea();
				ChartEntity entity = entities.getEntity(
						(int) ((me.x - insets.x) / super.getScaleX()),
						(int) ((me.y - insets.y) / super.getScaleY()));
				if ((entity != null) && (entity instanceof PieSectionEntity)) {
					PieSectionEntity catItemEntity = (PieSectionEntity) entity;
					result = (String) catItemEntity.getSectionKey();
				}
			}
		}
		return result;
	}

	@Override
	protected Menu createPopupMenu(boolean arg0, boolean arg1, boolean arg2,
			boolean arg3) {
		return null;
	}

}
