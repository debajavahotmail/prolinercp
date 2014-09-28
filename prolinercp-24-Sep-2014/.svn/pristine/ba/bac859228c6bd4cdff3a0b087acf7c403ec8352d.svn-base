package com.proline.util.database;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.proline.models.DBModel;
import com.proline.models.DBModelProvider;
import com.proline.rcp.util.PasswordConvertionUtil;
import com.proline.rcp.util.ProlineLogger;

/**
 * @author Debadatta Mishra
 *
 */
public class DatabaseUtil {

	public static Connection getConnection(String host, String userName,String pwd, String schema) {
		System.out.println("Swamy test The incoming parametes is getConnection()"+host+userName+pwd+schema);
		Connection conn = null;
		//String encrypedPassword=desAlgo.encrypt(publicKeyForPWDEncrypt, pwd);
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			ProlineLogger.logError("DatabaseUtil classs getConnection()",e);
			String exception=e.getMessage();			
			MessageDialog.openError(new Shell(), "ClassNotFoundException",exception );
		}
		try {
			String dbUrl = "jdbc:oracle:thin:@"+host+":1521:"+schema;
			conn = DriverManager.getConnection(dbUrl, userName, pwd);
		} catch (SQLException  e) {
			e.printStackTrace();
			System.out.println("ClassNotFoundException"+e);
			ProlineLogger.logError("DatabaseUtil classs getConnection()",e);
			String exception=e.getMessage();			
			MessageDialog.openError(new Shell(), "ClassNotFoundException",exception );
		}
		return conn;
	}


	public static DBModelProvider getDBModelProvider(String host, String userName,String pwd, String schema,String query) {
		PasswordConvertionUtil desAlgo=new PasswordConvertionUtil();
		ProlineLogger.logInfo("DatabaseUtil classs getDBModelProvider() current db host, user ,pwd,schema and qry is :"+host+","+userName+","+pwd+","+schema+","+query);
		System.out.println("DatabaseUtil classs getDBModelProvider() current db host, user ,pwd,schema and qry is :"+host+","+userName+","+pwd+","+schema+","+query);
		DBModelProvider dbModelProvider = new DBModelProvider();
		dbModelProvider.setSavedQuery(query);
		Connection conn =null;
		String publicKeyForPWDEncrypt="yerriswamy@PROSYSSOLS.com+917676665055";
		try {			
			System.out.println("The encrypted key value is :"+pwd+"The lenth is :"+pwd.length());
			System.out.println("The key value is :"+publicKeyForPWDEncrypt+"The lenth is :"+publicKeyForPWDEncrypt.length());
			//String testString="c+cQ+cbw7o0=";
//			String originalPassword=desAlgo.decrypt(publicKeyForPWDEncrypt,pwd);//Commented for Testing
			String originalPassword=pwd;
			System.out.println("The decripted 2 key value is :"+originalPassword+"The lenth is :"+originalPassword.length());
			conn = getConnection(host,userName,originalPassword,schema);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		//Commented Temporarily
//		catch (InvalidKeyException | NoSuchAlgorithmException
//				| InvalidKeySpecException | NoSuchPaddingException
//				| InvalidAlgorithmParameterException
//				| IllegalBlockSizeException | BadPaddingException | IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}		 
		ProlineLogger.logInfo("DatabaseUtil classs getDBModelProvider() got the connection is "+conn);
		ProlineLogger.logInfo("DatabaseUtil classs getDBModelProvider() db connection success now excuting the stmt...");
		ProlineLogger.logInfo("DatabaseUtil classs getDBModelProvider() got the db connection success now excuting the stmt... "+query);
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			List<String> columnNames = new ArrayList<String>();
			if (rs != null) {
				ResultSetMetaData columns = rs.getMetaData();
				int i = 0;
				//here rowcount column adding...
				columnNames.add(" ");
				//getColumnModel().getColumn(0).setPreferredWidth(27);
				while (i < columns.getColumnCount()) {					
					i++;
					columnNames.add(columns.getColumnName(i));
				}
				dbModelProvider.setTitles1(columnNames.toArray(new String[columnNames.size()]));
				List<DBModel> dbModelList = new ArrayList<DBModel>();
				while (rs.next()) {
					DBModel model = new DBModel();
					for (i = 1; i < columnNames.size(); i++) {	
						String data = rs.getString(columnNames.get(i));						
						model.addDataIndex(i, data);					
					}	
					//System.out.println("The dbmodellist is :"+dbModelList.size());
					//This is rowcount column data adding
					for (int j = 0; j <1+dbModelList.size(); j++) {						
						model.addDataIndex(0,""+(j+1) );
					}
					dbModelList.add(model);
					/*System.out.println("The dbmodellist is :"+dbModelList.size());
					for (int j = 0; j < dbModelList.size(); j++) {						
						model.addDataIndex(0,""+j );
					}*/
				}
				dbModelProvider.setDbModelList(dbModelList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ProlineLogger.logError("DatabaseUtil classs getDBModelProvider()",e);
			System.out.println("SQLException"+e);
			String exception=e.getMessage();
			MessageDialog.openError(new Shell(), "SQLSyntaxErrorException",exception );		
		}
		finally {
			try {
				if( rs != null ) rs.close();
				if( stmt != null ) stmt.close();
				if( conn != null ) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				ProlineLogger.logError("DatabaseUtil classs getDBModelProvider() finally block",e);		
				String exception=e.getMessage();
				MessageDialog.openError(new Shell(), "SQLSyntaxErrorException",exception );
			}
		}
		return dbModelProvider;
	}
	
	//Mainly used for prepared statement
		public static DBModelProvider getDBModelProvider(String host, String userName,
				String pwd, String schema,String query,List<String> dataList) {
			DBModelProvider dbModelProvider = new DBModelProvider();
			dbModelProvider.setSavedQuery(query);
			Connection conn = getConnection(host,userName,pwd,schema);
			PreparedStatement pStatement = null;
			ResultSet rs = null;
			try {
				pStatement = conn.prepareStatement(query);
				for( int i = 0 ; i < dataList.size() ; i++ ) {
					pStatement.setString(i+1, dataList.get(i));
				}
				rs = pStatement.executeQuery();

				List<String> columnNames = new ArrayList<String>();
				if (rs != null) {
					ResultSetMetaData columns = rs.getMetaData();
					
					int i = 0;
					columnNames.add(" ");
					//getColumnModel().getColumn(0).setPreferredWidth(27);
					while (i < columns.getColumnCount()) {					
						i++;
						columnNames.add(columns.getColumnName(i));
					}
					dbModelProvider.setTitles1(columnNames.toArray(new String[columnNames.size()]));
					List<DBModel> dbModelList = new ArrayList<DBModel>();
					while (rs.next()) {
						DBModel model = new DBModel();
						for (i = 1; i < columnNames.size(); i++) {	
							String data = rs.getString(columnNames.get(i));						
							model.addDataIndex(i, data);					
						}	
						//This is rowcount column data adding
						for (int j = 0; j <1+dbModelList.size(); j++) {						
							model.addDataIndex(0,""+(j+1) );
						}
						dbModelList.add(model);
					}
					dbModelProvider.setDbModelList(dbModelList);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				ProlineLogger.logError("DatabaseUtil classs getDBModelProvider()",e);
				System.out.println("SQLException"+e);
				String exception=e.getMessage();
				MessageDialog.openError(new Shell(), "SQLSyntaxErrorException",exception );		
			}
			finally {
				try {
					if( rs != null ) rs.close();
					if( pStatement != null ) pStatement.close();
					if( conn != null ) conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					ProlineLogger.logError("DatabaseUtil classs getDBModelProvider() finally block",e);		
					String exception=e.getMessage();
					MessageDialog.openError(new Shell(), "SQLSyntaxErrorException",exception );
				}
			}
			return dbModelProvider;
		}
}
