package com.proline.rcp.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.proline.core.handler.ChartsOpenViewActionHandler;
import com.proline.core.handler.ProlineDefinitionHandler;
import com.proline.defn.schema.models.ProlineDefinition;
import com.proline.defn.schema.models.ProlineDefinition.SystemDefinition.Level;
import com.proline.models.DBModel;
import com.proline.models.DBModelProvider;
import com.proline.models.DBSystemModel;
import com.proline.rcp.constants.FileConstants;

/**
 * @author Debadatta Mishra (PIKU)
 *
 */
public class ChartsOpenViewActionHandlerImpl implements ChartsOpenViewActionHandler {
	
	private String key;

	@Override
	public Object handle(String key) {
		//Perform the operation to get the data for batch
		Map<String,Object> dataObjectMap = new HashMap<String,Object>();
		this.key = key;
		String[] keys = key.split("-");
		String chartType = keys[0];
		String chartTitle = keys[1];
		String batchName = keys[2];
		ProlineDefinitionHandler proDefnHandler =  new ProlineDefinitionHandler();
		ProlineDefinition proDefn = proDefnHandler.getProlineDefn(FileConstants.PROLINE_DEFN);
//		System.out.println("Batch Name :::"+batchName);
		Map<Integer,Level> levelDetailsMap = proDefnHandler.getSystemDetailsMap(proDefn, batchName);
		//There will be only level
		Level level = levelDetailsMap.get(1);
		String levelName = level.getName();
		DBSystemModel dbSystemModel = proDefnHandler.getSpecificDBSystemModel(levelName);
		DBModelProvider modelProvider = proDefnHandler.handle(proDefn, batchName, 1);
		
		List<DBModel> models = modelProvider.getDbModelList();
		Map<String, Number> chartDetailsMap = new HashMap<String, Number>();
		Map<String,String> tootipMap = new HashMap<String,String>();
		for( DBModel model : models ) {
			Map<Integer,String> tempDataMap = model.getAllData();
			Number num = Double.parseDouble(tempDataMap.get(2));
			chartDetailsMap.put(tempDataMap.get(1), num);
			if( tempDataMap.get(3) != null ) {
				tootipMap.put(tempDataMap.get(1), tempDataMap.get(3));
			}
		}
		dataObjectMap.put("chartDataDetails", chartDetailsMap);
		dataObjectMap.put("chartDataTooTipDetails", tootipMap);
		dataObjectMap.put("dbDetails", dbSystemModel);
		
		return dataObjectMap;
	}

}
