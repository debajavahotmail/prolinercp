package com.proline.util.parsers;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.proline.models.DBSystemModel;
import com.proline.models.DatabaseConnectionsModel;


public class DBConnectionSystemParser {
	
	public DatabaseConnectionsModel getDBConnectionSystemModel(String filePath) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		File prolineXmlFile = new File(filePath);
		Document document = db.parse(prolineXmlFile);
		return getDatabaseConnectionsModel(document);
	}
	
	private DatabaseConnectionsModel getDatabaseConnectionsModel(Document document) {
		NodeList nodeList = document.getChildNodes();
		Node node = nodeList.item(0);
		Element rootElement = (Element) node;
//		System.out.println("element------->"+rootElement.getTagName());
		NodeList childNodeList = rootElement.getChildNodes();
		Node childNode = childNodeList.item(1);
		Element dbConnElement = (Element) childNode;
//		System.out.println("element------->"+dbConnElement.getTagName());
		NodeList systemNodeList = dbConnElement.getChildNodes();
		
		DatabaseConnectionsModel dbConnModel = new DatabaseConnectionsModel();
		for( int i = 0 ; i < systemNodeList.getLength() ; i++ ) {
			Node sysNode = systemNodeList.item(i);
			if( sysNode.getNodeType() == Node.ELEMENT_NODE ) {
				Element sysElement = (Element) sysNode;
//				System.out.println("element------->"+sysElement.getTagName());
				String id = sysElement.getAttribute("id");
				DBSystemModel dbSysModel = new DBSystemModel();
				dbSysModel.setId(id);
//				System.out.println("Id------"+id);
				setInnerSystemDetails(sysElement,dbSysModel);
				dbConnModel.getDbMoldelMap().put(id	, dbSysModel);
			}
		}
		return dbConnModel;
	}
	
	
	private void setInnerSystemDetails( Element element,DBSystemModel dbSysModel ) {
		NodeList nodeList = element.getChildNodes();
		for( int i = 0 ; i < nodeList.getLength() ; i++ ) {
			Node node = nodeList.item(i);
			if( node.getNodeType() == Node.ELEMENT_NODE ) {
				Element childElement = (Element) node;
//				System.out.println(childElement.getTextContent());
				if(childElement.getTagName().equals("DBNAME"))
					dbSysModel.setDatabaseName(childElement.getTextContent());
				else if(childElement.getTagName().equals("SCHEMA"))
					dbSysModel.setSchemaName(childElement.getTextContent());
				else if(childElement.getTagName().equals("HOST"))
					dbSysModel.setHostName(childElement.getTextContent());
				else if(childElement.getTagName().equals("NAME"))
					dbSysModel.setUserName(childElement.getTextContent());
				else if(childElement.getTagName().equals("PWD"))
					dbSysModel.setPassword(childElement.getTextContent());
			}
		}	
	}
	
	public static void main(String[] args) throws Exception {
		DBConnectionSystemParser parser = new DBConnectionSystemParser();
		DatabaseConnectionsModel dbConnModel = parser.getDBConnectionSystemModel("config/DBConnectionDetails.xml");
		Map<String,DBSystemModel> dbMoldelMap = dbConnModel.getDbMoldelMap();
		Iterator<Map.Entry<String,DBSystemModel>> itr = dbMoldelMap.entrySet().iterator();
		while( itr.hasNext() ) {
			Map.Entry<String,DBSystemModel> me = itr.next();
			System.out.println("Key----->"+me.getKey());
			DBSystemModel dbModel = me.getValue();
			System.out.println(dbModel.getDatabaseName());
			System.out.println(dbModel.getHostName());
		}
		
		
		
	}

}
