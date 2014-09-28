package com.proline.rcp.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.proline.core.handler.DashboardException;
import com.proline.models.ProlineModelBean;
import com.proline.models.ProlineTagType;
import com.proline.models.dashboards.AvailableCharts;
import com.proline.models.dashboards.ChartType;
import com.proline.models.dashboards.DashBoardChart;
import com.proline.models.dashboards.DashBoards;
import com.proline.rcp.constants.FileConstants;
import com.proline.rcp.util.PluginUtil;
import com.proline.util.parsers.XMLDOMParser;

/**
 * @author Debadatta Mishra (PIKU)
 *
 */
public class DashBoardConfigurationHandler extends AbstractHandler {

	private Text titleText;
	private Text xAxisText;
	private Text yAxisText;
	private Text dbQueryText;
	private Label errorLabel = null;
	private Combo chartTypeCombo = null;
	private String[] chartTypeComboItems = new String[] { "PIE", "BAR" };//Later on you can add more, all words should be in Upper Case only
	private Combo batchCombo = null;
	private String batchComboItems[] = null;//new String[] { "Batch-1", "Batch-2" };

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Call here to get the batch details
		Shell shell = new Shell(SWT.CLOSE);
		shell.setImage(FileConstants.DASHBOARD_CONFIG_IMG);

		try {
			openShell(shell);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private void openShell(final Shell shell) throws Exception {
		shell.setSize(659, 435);
		shell.setText("Dashboard confguration");
		shell.setLayout(null);

		Label titleLabel = new Label(shell, SWT.NONE);
		titleLabel.setBounds(10, 23, 80, 21);
		titleLabel.setText("Title");
		titleLabel.setAlignment(SWT.RIGHT);

		titleText = new Text(shell, SWT.BORDER);
		titleText.setBounds(127, 21, 304, 21);
		addCommonTextListener(titleText, false);

		Label xAxisLabel = new Label(shell, SWT.NONE);
		xAxisLabel.setBounds(10, 58, 80, 21);
		xAxisLabel.setText("X-Axis Text");
		xAxisLabel.setAlignment(SWT.RIGHT);

		xAxisText = new Text(shell, SWT.BORDER);
		xAxisText.setBounds(127, 56, 304, 21);

		Label yAxisLabel = new Label(shell, SWT.NONE);
		yAxisLabel.setBounds(10, 95, 80, 21);
		yAxisLabel.setText("Y-Axis Text");
		yAxisLabel.setAlignment(SWT.RIGHT);

		yAxisText = new Text(shell, SWT.BORDER);
		yAxisText.setBounds(127, 93, 304, 21);

		Label batchLabel = new Label(shell, SWT.NONE);
		batchLabel.setBounds(10, 135, 80, 21);
		batchLabel.setText("Batch Details");
		batchLabel.setAlignment(SWT.RIGHT);

		batchCombo = new Combo(shell, SWT.READ_ONLY);
		batchCombo.setBounds(127, 133, 304, 23);
		if( batchComboItems == null) {
			List<String> batchesList = getAllBatches();
			batchComboItems = batchesList.toArray(new String[batchesList.size()]);
		}
			
		batchCombo.setItems(batchComboItems);
		batchCombo.select(0);

		Label dbQueryLabel = new Label(shell, SWT.NONE);
		dbQueryLabel.setBounds(10, 222, 91, 21);
		dbQueryLabel.setText("Database Query");
		dbQueryLabel.setAlignment(SWT.RIGHT);

		dbQueryText = new Text(shell, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		dbQueryText.setBounds(127, 220, 304, 109);
		addCommonTextListener(dbQueryText, true);

		Label chartTypeLabel = new Label(shell, SWT.READ_ONLY);
		chartTypeLabel.setBounds(10, 177, 80, 21);
		chartTypeLabel.setText("Chart Type");
		chartTypeLabel.setAlignment(SWT.RIGHT);

		chartTypeCombo = new Combo(shell, SWT.READ_ONLY);
		chartTypeCombo.setBounds(127, 175, 304, 23);
		chartTypeCombo.setItems(chartTypeComboItems);
		chartTypeCombo.select(0);

		errorLabel = new Label(shell, SWT.NONE);
		errorLabel.setBounds(10, 352, 425, 29);
		Font boldFont = JFaceResources.getFontRegistry().getBold(
				JFaceResources.DEFAULT_FONT);
		errorLabel.setFont(boldFont);
		Color redColor = Display.getDefault().getSystemColor(SWT.COLOR_RED);
		errorLabel.setForeground(redColor);

		Button saveButton = new Button(shell, SWT.NONE);
		saveButton.setBounds(473, 352, 75, 29);
		saveButton.setText("Save");

		saveButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				boolean flag = isDataValid();
				System.out.println("Flag :::"+flag);
				try {
					if(flag) {
						saveDashboardInfo(flag);
						shell.dispose();
					}
				} catch (Exception e2) {
					PluginUtil.showErrorInfo("Dashboard configuraton error", e2);
				}
			}
		});

		Button cancelButton = new Button(shell, SWT.NONE);
		cancelButton.setBounds(558, 352, 75, 29);
		cancelButton.setText("Cancel");
		cancelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		shell.open();
		shell.layout();
	}

	private void saveDashboardInfo(boolean flag) throws Exception {
		DashBoards newDashBoard = getPopulatedDashboardDetails();
		DashBoards existingDashBoard = null;
		File file = new File(FileConstants.DASHBOARD_CONFIG_XML_FILE);
		if (file.exists()) {
			// It means already xml file is there
			// Parse the XML file and get the dash board
			try {
				existingDashBoard = getDataFromXML();
				AvailableCharts existingCharts = existingDashBoard.getAvlCharts();
				List<DashBoardChart> chartList = existingCharts.getChartList();
				DashBoardChart newDashboardChart = newDashBoard.getAvlCharts().getChartList().get(0);
				if (chartList.contains(newDashboardChart)) {
					throw new DashboardException("Same Dashboard configuration with same title already exists in the system");
				}
				chartList.add(newDashboardChart);
				existingCharts.setChartList(chartList);
				existingDashBoard.setAvlCharts(existingCharts);
			} 
			catch(DashboardException dbe) {
				throw dbe;
			}
			catch (Exception e) {
				existingDashBoard = newDashBoard;
			}
		} else {
			// Get the newly populated values
			existingDashBoard = newDashBoard;
		}
		saveToXMLFile(existingDashBoard);
	}

	private DashBoards getPopulatedDashboardDetails() {
		DashBoards dashBoard = new DashBoards();
		List<DashBoardChart> chartList = new ArrayList<DashBoardChart>();
		DashBoardChart dashBoardChart = new DashBoardChart();
		dashBoardChart.setTitle(titleText.getText());
		String chartTypeComboValue = chartTypeComboItems[chartTypeCombo
		                                                 .getSelectionIndex()];
		dashBoardChart.setType(ChartType.valueOf(chartTypeComboValue));
		String xAxisValue = xAxisText.getText();
		xAxisValue = (xAxisValue == null || xAxisValue.trim().length() == 0) ? "X-Axis" : xAxisValue;
		String yAxisValue = yAxisText.getText();
		yAxisValue = (yAxisValue == null || yAxisValue.trim().length() == 0) ? "Y-Axis" : yAxisValue;
		dashBoardChart.setxAxis(xAxisValue);
		dashBoardChart.setyAxis(yAxisValue);
		String batchComboValue = batchComboItems[batchCombo.getSelectionIndex()];
		dashBoardChart.setBatchDetail(batchComboValue);
		dashBoardChart.setDbQuery(dbQueryText.getText());
		chartList.add(dashBoardChart);
		AvailableCharts avlChart = new AvailableCharts();
		avlChart.setChartList(chartList);
		dashBoard.setAvlCharts(avlChart);
		return dashBoard;
	}

	public static DashBoards getDataFromXML() throws Exception {
		InputStream inStream = null;
		DashBoards dashBoard = null;
		try {
			inStream = new FileInputStream(FileConstants.DASHBOARD_CONFIG_XML_FILE);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
			throw fnfe;
		}
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(DashBoards.class);
			Unmarshaller un = context.createUnmarshaller();
			dashBoard = (DashBoards) un.unmarshal(inStream);
		} catch (JAXBException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (inStream != null)
					inStream.close();
			} catch (Exception e2) {
				// Do not do anything
			}
		}
		return dashBoard;
	}

	private void saveToXMLFile(DashBoards dashBoard) throws Exception {
		OutputStream outStream = null;
		try {
			outStream = new FileOutputStream(
					FileConstants.DASHBOARD_CONFIG_XML_FILE);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
			throw fnfe;
		} catch (Exception ie) {
			ie.printStackTrace();
			throw ie;
		}

		JAXBContext context;
		try {
			context = JAXBContext.newInstance(DashBoards.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			marshaller.marshal(dashBoard, outStream);
		} catch (JAXBException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (outStream != null)
					outStream.flush();
				if (outStream != null)
					outStream.close();
			} catch (Exception e2) {
				// Do not do anything
			}
		}
	}

	public boolean isDataValid() {
		boolean flag = true;
		if (titleText.getText() == null
				|| titleText.getText().trim().length() == 0) {
			errorLabel
			.setText("Title can not be blank , provide a title information");
			flag = false; 
		}
		else if (dbQueryText.getText() == null
				|| dbQueryText.getText().trim().length() == 0) {
			errorLabel.setText("Provide a complete executable database query");
			flag = false;
		}
		return flag;
	}

	private void addCommonTextListener(Text text, final boolean allowHash) {
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				errorLabel.setText("");
				char hashChar = e.character;
				if (hashChar == '#' && !allowHash) {
					e.doit = false;
				}
			}
		});
	}
	
	private List<String> getAllBatches() throws Exception {
		List<String> batchesList = new ArrayList<String>();
		XMLDOMParser parser = new XMLDOMParser();
		ProlineModelBean pModelBean = parser.getProlineModelBeanList(FileConstants.PROLINE);
		if (pModelBean.getTagType() == ProlineTagType.PARENT) {
			List<ProlineModelBean> pModelList = pModelBean.getChildren();
			for (int i = 0; i < pModelList.size(); i++) {
				ProlineModelBean pModelBean1 = pModelList.get(i);
				if (pModelBean1.getTagType() == ProlineTagType.PARENT) {
					List<ProlineModelBean> pModelList1 = pModelBean1.getChildren();
					for (int j = 0; j < pModelList1.size(); j++) {
						ProlineModelBean pModelBean2 = pModelList1.get(j);
						if (pModelBean2.getTagType() == ProlineTagType.PARENT) {
							List<ProlineModelBean> pModelList2 = pModelBean2.getChildren();
							for (int k = 0; k < pModelList2.size(); k++) {
								ProlineModelBean pModelBean3 = pModelList2.get(k);
								if (pModelBean3.getTagType() == ProlineTagType.PARENT) {
								}
								else {
									batchesList.add(pModelBean3.getTagValue());
									// Populate here only
								}
							}
						}
					}
				} 
			}
		}
		return batchesList;
	}

}
