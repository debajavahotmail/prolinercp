package com.proline.rcp.views.charts;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;

/**Customized for Shrinath Proline
 * @author Debadatta Mishra
 *
 */
public abstract class ProlineChartComposite extends ChartComposite {
	
	public ProlineChartComposite(Composite comp, int style, JFreeChart chart,
            boolean useBuffer) {

        super(comp, style, chart,
                DEFAULT_WIDTH,
                DEFAULT_HEIGHT,
                DEFAULT_MINIMUM_DRAW_WIDTH,
                DEFAULT_MINIMUM_DRAW_HEIGHT,
                DEFAULT_MAXIMUM_DRAW_WIDTH,
                DEFAULT_MAXIMUM_DRAW_HEIGHT,
                useBuffer,
                true,  // properties
                true,  // save
                true,  // print
                true,  // zoom
                true   // tooltips
                );
    }
	
	public abstract String getCustomClickableValue(MouseEvent me);

}
