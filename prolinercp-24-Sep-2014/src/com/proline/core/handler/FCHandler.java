package com.proline.core.handler;

import java.util.List;
import java.util.Map;
import com.proline.models.DBConnSystemModel;
import com.proline.models.DBConnectionCBSModel;
import com.proline.models.DBDetailsModel;
import com.proline.models.DBModelProvider;
import com.proline.models.DBSystemModel;
import com.proline.models.DatabaseConnectionsModel;
import com.proline.rcp.constants.FileConstants;
import com.proline.util.database.DatabaseUtil;
import com.proline.util.parsers.DBConnSysModelParser;
import com.proline.util.parsers.DBConnectionSystemParser;

public class FCHandler {
	
	private DBConnectionCBSModel getProlineDefinition() {
		DBConnSysModelParser parser = new DBConnSysModelParser();
		DBConnectionCBSModel model = null;
		try {
			model = parser.getDBConnectionCBSModel(FileConstants.PROLINE_DEFN);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	private Map<String,DBSystemModel> getDatabaseConnectionsDetails() {
		DBConnectionSystemParser dbConnParser = new DBConnectionSystemParser();
		DatabaseConnectionsModel dbConnModel = null;
		try {
			dbConnModel = dbConnParser.getDBConnectionSystemModel(FileConstants.PROLINE_DB_CONN_DETAILS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbConnModel.getDbMoldelMap();
	}
	
	
	public DBModelProvider handleOutgoingPayments( String treeName) {
		String hostName = null;
		String userName = null;
		String schemaName = null;
		String password = null;
		String query = null;
		System.out.println("TreeName in handle payment------"+treeName);
		DBConnectionCBSModel model = getProlineDefinition();//Get the proline definition
		System.out.println("Model--------"+model);
		List<DBConnSystemModel> dbConSysModelList = model.getDbConSysModelList();
		System.out.println("dbConSysModelList--------"+dbConSysModelList);
		for( DBConnSystemModel dbConSysModel : dbConSysModelList ) {
			System.out.println("dbConSysModel.getDescription()----------"+dbConSysModel.getDescription());
			if(dbConSysModel.getDescription().equals(treeName)) {
				System.out.println("Inside if block .............");
				//So you got the name
				//Take the first one
				DBDetailsModel dbDetails = dbConSysModel.getDbDetailModelList().get(0);
				query = dbDetails.getPq();
				try {
					Map<String,DBSystemModel> dbMoldelMap = getDatabaseConnectionsDetails();
					DBSystemModel dbSysModel = dbMoldelMap.get(dbDetails.getName());
					System.out.println("Database Name :::"+dbSysModel.getDatabaseName());
					System.out.println("Host Name :::"+dbSysModel.getHostName());
					hostName = dbSysModel.getHostName();
					System.out.println("User Name :::"+dbSysModel.getUserName());
					userName = dbSysModel.getUserName();
					System.out.println("Passworde :::"+dbSysModel.getPassword());
					password = dbSysModel.getPassword();
					schemaName = dbSysModel.getSchemaName();
					break;
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		DBModelProvider  modelProvider = DatabaseUtil.getDBModelProvider(hostName,userName, password,schemaName, query);
		return modelProvider;
	}
}
