package com.proline.rcp.views.charts;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.EntityCollection;

/**Customized for Shrinath
 * @author Debadatta Mishra
 *
 */
public class ProlineBarChartComposite extends ProlineChartComposite {

	public ProlineBarChartComposite(Composite comp, int style,
			JFreeChart chart, boolean useBuffer) {
		super(comp, style, chart, useBuffer);
	}
	
	@Override
	public String getCustomClickableValue(MouseEvent me) {
		String result = null;
		ChartRenderingInfo info = super.getChartRenderingInfo();
   	 if (info != null) {
            EntityCollection entities = info.getEntityCollection();
            if (entities != null) {
           	 Rectangle insets = getClientArea();
                ChartEntity entity = entities.getEntity(
                        (int) ((me.x - insets.x) / super.getScaleX()),
                        (int) ((me.y - insets.y) / super.getScaleY()));
                if ((entity != null) && (entity instanceof CategoryItemEntity)) { 
                	CategoryItemEntity catItemEntity = (CategoryItemEntity) entity;     
                 	result = (String) catItemEntity.getColumnKey();
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
