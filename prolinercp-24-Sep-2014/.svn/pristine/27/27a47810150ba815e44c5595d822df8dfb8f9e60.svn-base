package com.proline.core.handler;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.proline.defn.schema.models.ObjectFactory;
import com.proline.defn.schema.models.ProlineDefinition;
import com.proline.defn.schema.models.ProlineDefinition.SystemDefinition;
import com.proline.defn.schema.models.ProlineDefinition.SystemDefinition.Level;
import com.proline.defn.schema.models.ProlineDefinition.SystemDefinition.Level.ColumnDefinition;
import com.proline.defn.schema.models.ProlineDefinition.SystemDefinition.Level.ColumnDefinition.Column;
import com.proline.defn.schema.models.ProlineDefinition.SystemDefinition.Level.ColumnDefinition.Column.DataDefinition;
import com.proline.defn.schema.models.ProlineDefinition.SystemDefinition.Level.Condition;
import com.proline.models.DBModel;
import com.proline.models.DBModelProvider;
import com.proline.models.DBSystemModel;
import com.proline.models.DatabaseConnectionsModel;
import com.proline.rcp.constants.FileConstants;
import com.proline.rcp.util.ProlineLogger;
import com.proline.util.database.DatabaseUtil;
import com.proline.util.parsers.DBConnectionSystemParser;

/**
 * @author Debadatta Mishra
 *
 */
public class ProlineDefinitionHandler {
	
	public Map<Integer,Level> getAllLevelsByName( List<SystemDefinition> sysDefList , String id) {
		Map<Integer,Level> levelMap = new HashMap<Integer,Level>();
		ProlineLogger.logInfo("prolinedefinitionHandler class getalllevelsbyname()");
		for( SystemDefinition sysDefn  : sysDefList ) {
			if( id.equals(sysDefn.getDescription())) { 
				List<Level> levelList = sysDefn.getLevel();
				for( Level level : levelList ) {
					levelMap.put(level.getId(), level);
				}
			}
		}
		return levelMap;
	}
	
	public Map<Integer,Level> getSystemDetailsMap(ProlineDefinition proDefn,String treeName) {
		Map<Integer,Level> levelMap = new HashMap<Integer,Level>();
		List<SystemDefinition> sysDefList = proDefn.getSystemDefinition();
		ProlineLogger.logInfo("prolinedefinitionHandler class getSystemDetailsMap()");
		for(SystemDefinition sysDefn : sysDefList ) {
			String description = sysDefn.getDescription();
			if( description.equals(treeName)) {
				List<Level> levelList = sysDefn.getLevel();
				for( Level level : levelList ) {
					levelMap.put(level.getId(), level);
				}
			}
		}
		return levelMap;
	}
	
	public ProlineDefinition getProlineDefn(String filePath) {
		ProlineDefinition proDefn = null;
		try {
			ProlineLogger.logInfo("prolinedefinitionHandler class getProlineDefn()");
			JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			proDefn = (ProlineDefinition) unmarshaller.unmarshal(new File(filePath));
		} catch (Exception e) {
			e.printStackTrace();
			ProlineLogger.logError(" getProlineDefn( ) in ProlineDefinitionHandler class..",e);
		}
		return proDefn;
	}
	
	private Map<String,DBSystemModel> getDatabaseConnectionsDetails() {
		DBConnectionSystemParser dbConnParser = new DBConnectionSystemParser();
		DatabaseConnectionsModel dbConnModel = null;
		try {
			ProlineLogger.logInfo("prolinedefinitionHandler class getDatabaseConnectionsDetails()");
			dbConnModel = dbConnParser.getDBConnectionSystemModel(FileConstants.PROLINE_DB_CONN_DETAILS);
		} catch (Exception e) {
			e.printStackTrace();
			ProlineLogger.logError(" getDatabaseConnectionsDetails( ) in ProlineDefinitionHandler class..",e);
		}
		return dbConnModel.getDbMoldelMap();
	}
	
	public DBSystemModel getSpecificDBSystemModel( String name) {
		return getDatabaseConnectionsDetails().get(name);
	}
	
	public DBModelProvider handle(String treeName, int levelNo,String direcQuery) {
		ProlineLogger.logInfo("prolinedefinitionHandler class handle(3 )");
		String hostName = null;
		String userName = null;
		String schemaName = null;
		String password = null;
		String query = null;
		ProlineDefinition proDefn = getProlineDefn(FileConstants.PROLINE_DEFN);
		Map<Integer,Level> levelMap = getSystemDetailsMap(proDefn, treeName);
		//Now get the DB name based upon the level
		Level level = levelMap.get(levelNo);
		String dbId = level.getName();
		query = direcQuery;//level.getPrimaryQuery();
		try {
			Map<String,DBSystemModel> dbMoldelMap = getDatabaseConnectionsDetails();
			DBSystemModel dbSysModel = dbMoldelMap.get(dbId);
			hostName = dbSysModel.getHostName();
			userName = dbSysModel.getUserName();
			password = dbSysModel.getPassword();
			schemaName = dbSysModel.getSchemaName();
			
		} catch (Exception e) {
			e.printStackTrace();
			ProlineLogger.logError("DBModelProvider handle(3 ) in ProlineDefinitionHandler class..",e);
		}
		DBModelProvider  modelProvider = DatabaseUtil.getDBModelProvider(hostName,userName, password,schemaName, query);
		return modelProvider;
	}

	
	public DBModelProvider handle(String treeName, int levelNo) {
		String hostName = null;
		String userName = null;
		String schemaName = null;
		String password = null;
		String query = null;
		ProlineLogger.logInfo("DBModelProvider handle(2 ) in ProlineDefinitionHandler class..");
		ProlineDefinition proDefn = getProlineDefn(FileConstants.PROLINE_DEFN);
		Map<Integer,Level> levelMap = getSystemDetailsMap(proDefn, treeName);
		//Now get the DB name based upon the level
		Level level = levelMap.get(levelNo);
		String dbId = level.getName();
		query = level.getPrimaryQuery();
		try {
			Map<String,DBSystemModel> dbMoldelMap = getDatabaseConnectionsDetails();
			DBSystemModel dbSysModel = dbMoldelMap.get(dbId);
			hostName = dbSysModel.getHostName();
			userName = dbSysModel.getUserName();
			password = dbSysModel.getPassword();
			schemaName = dbSysModel.getSchemaName();
			
		} catch (Exception e) {
			e.printStackTrace();
			ProlineLogger.logError("DBModelProvider handle(2 ) in ProlineDefinitionHandler class..",e);
		}
		DBModelProvider  modelProvider = DatabaseUtil.getDBModelProvider(hostName,userName, password,schemaName, query);
		return modelProvider;
	}
	
	public DBModelProvider handle(ProlineDefinition proDefn,String treeName) {
		String hostName = null;
		String userName = null;
		String schemaName = null;
		String password = null;
		String query = null;
		Map<Integer,Level> levelMap = getSystemDetailsMap(proDefn, treeName);
		Level level = levelMap.get(1);
		String dbId = level.getName();
		System.out.println("PDefnHandler --> dbId = "+dbId);
		ProlineLogger.logInfo("DBModelProvider handle(2 parameters ) in ProlineDefinitionHandler class..");
		query = level.getPrimaryQuery(); 		
		try {
			Map<String,DBSystemModel> dbMoldelMap = getDatabaseConnectionsDetails();
			DBSystemModel dbSysModel = dbMoldelMap.get(dbId);
			System.out.println("appSerSysModel = "+dbSysModel);
			hostName = dbSysModel.getHostName();
			System.out.println("hostName = "+hostName );
			userName = dbSysModel.getUserName();
			System.out.println("userName = "+userName);
			password = dbSysModel.getPassword();
			System.out.println("password = "+password);
			schemaName = dbSysModel.getSchemaName();
			
		} catch (Exception e) {
			e.printStackTrace();
			ProlineLogger.logError("DBModelProvider handle(2 parameters) in ProlineDefinitionHandler class..",e);
		}
		DBModelProvider  modelProvider = DatabaseUtil.getDBModelProvider(hostName,userName, password,schemaName, query);
		return modelProvider;
	}

	public DBModelProvider handle(ProlineDefinition proDefn,String treeName, int levelNo) {
		String hostName = null;
		String userName = null;
		String schemaName = null;
		String password = null;
		String query = null;
		ProlineLogger.logInfo("DBModelProvider handle(3 parameters ) in ProlineDefinitionHandler class..");
		Map<Integer,Level> levelMap = getSystemDetailsMap(proDefn, treeName);
		//Now get the DB name based upon the level
		Level level = levelMap.get(levelNo);
		String dbId = level.getName();
		System.out.println("PDefnHandler --> dbId = "+dbId);
		ProlineLogger.logInfo("PDefnHandler --> dbId = "+dbId);
		query = level.getPrimaryQuery(); 		
		try {
			Map<String,DBSystemModel> dbMoldelMap = getDatabaseConnectionsDetails();
			DBSystemModel dbSysModel = dbMoldelMap.get(dbId);
			ProlineLogger.logInfo("dbSysModel = "+dbSysModel);
			System.out.println("dbSysModel = "+dbSysModel);
			hostName = dbSysModel.getHostName();
			System.out.println("hostName = "+hostName );
			ProlineLogger.logInfo("hostName = "+hostName );
			userName = dbSysModel.getUserName();
			System.out.println("userName = "+userName);
			ProlineLogger.logInfo("userName = "+userName );
			password = dbSysModel.getPassword();
			System.out.println("password = "+password);
			ProlineLogger.logInfo("password = "+password);
			schemaName = dbSysModel.getSchemaName();
			
		} catch (Exception e) {
			e.printStackTrace();
			ProlineLogger.logError("DBModelProvider handle in ProlineDefinitionHandler class..",e);
		}
		DBModelProvider  modelProvider = DatabaseUtil.getDBModelProvider(hostName,userName, password,schemaName, query);
		return modelProvider;
	}
	
	public DBModelProvider handle(ProlineDefinition proDefn,String treeName, int levelNo,DBModel dbModel,List<String> columnNames) {
		ProlineLogger.logInfo("DBModelProvider handle(5 parameters) in ProlineDefinitionHandler class");
		String hostName = null;
		String userName = null;
		String schemaName = null;
		String password = null;
		String query = null;
		Map<Integer,Level> levelMap = getSystemDetailsMap(proDefn, treeName);
		//Now get the DB name based upon the level
		Level level = levelMap.get(levelNo);
		String dbId = level.getName();
		query = level.getPrimaryQuery();
		List<Condition> conditionsList = level.getCondition(); 
		Condition condition = conditionsList.get(0);
		int columnNo = condition.getSourceColumnIndex();
		String destnColName = condition.getDestinationColName();
		String operator = condition.getOperator();
		QueryBuilder queryBuilder = new QueryBuilder();
		String formedQuery = queryBuilder.buildQuery(query, conditionsList, dbModel,columnNames);
		query = formedQuery;

		try { 
			Map<String,DBSystemModel> dbMoldelMap = getDatabaseConnectionsDetails();
			DBSystemModel dbSysModel = dbMoldelMap.get(dbId);
			hostName = dbSysModel.getHostName();
			userName = dbSysModel.getUserName();
			password = dbSysModel.getPassword();
			schemaName = dbSysModel.getSchemaName();
			
		} catch (Exception e) {
			e.printStackTrace();
			ProlineLogger.logError("DBModelProvider handle(5 parameters) in ProlineDefinitionHandler class..",e);
		}
		DBModelProvider  modelProvider = DatabaseUtil.getDBModelProvider(hostName,userName, password,schemaName, query);
		return modelProvider;
	} 
	
	public Map<Integer,Map<String,String>> getKeyValueMap(String treeName , int levelNo ) {
		Map<Integer,Map<String,String>> indexKeyValueMap = new HashMap<Integer,Map<String,String>>();
		ProlineDefinition proDefn = getProlineDefn(FileConstants.PROLINE_DEFN);
		Map<Integer,Level> levelMap = getSystemDetailsMap(proDefn, treeName);
		Level level = levelMap.get(levelNo);
		ColumnDefinition colDefn = level.getColumnDefinition();
		if( colDefn != null ) {
			List<Column> columnList = colDefn.getColumn();
			for(Column col : columnList ) {
				int columnIndex = col.getColumnIndex();
				List<DataDefinition> dataDefnList = col.getDataDefinition();
				Map<String,String> keyValueMap = new HashMap<String,String>();
				for( DataDefinition dataDefn : dataDefnList ) {
					keyValueMap.put(dataDefn.getKey(), dataDefn.getValue());
				}
				indexKeyValueMap.put(columnIndex, keyValueMap);
				//System.out.println("columnIndex = "+columnIndex+"keyValueMap = "+keyValueMap);
			}
		}
		
		return indexKeyValueMap;
	}
}
