package com.proline.util.parsers;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.proline.models.DBConnSystemModel;
import com.proline.models.DBConnectionCBSModel;
import com.proline.models.DBDetailsModel;

public class DBConnSysModelParser {
	
	public DBConnectionCBSModel getDBConnectionCBSModel(String filePath) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		File prolineXmlFile = new File(filePath);
		Document document = db.parse(prolineXmlFile);
		return getDBConnectionCBSModel(document);
	}
	
	private DBConnectionCBSModel getDBConnectionCBSModel(Document document) {
		DBConnectionCBSModel dbCbsModel = new DBConnectionCBSModel();
		NodeList nodeList = document.getChildNodes();
		for( int i = 0 ; i < nodeList.getLength() ; i++ ) {
			Node node = nodeList.item(i);
			if( node.getNodeType() == Node.ELEMENT_NODE ) {
				Element element = (Element) node;
				setSystemDetails(element,dbCbsModel);
			}
		}
		return dbCbsModel;
	}
	
	private void setSystemDetails(Element element,DBConnectionCBSModel dbCbsModel) {
		//System
		NodeList nodeList = element.getChildNodes();
		for( int i = 0 ; i < nodeList.getLength() ; i++ ) {
			Node node = nodeList.item(i);
			if( node.getNodeType() == Node.ELEMENT_NODE ) {
				Element childElement = (Element) node;
				String id = childElement.getAttribute("id");
				DBConnSystemModel dbConSysModel = new DBConnSystemModel();
				dbConSysModel.setId(id);
				setDBDetails(childElement,dbConSysModel);
				dbCbsModel.getDbConSysModelList().add(dbConSysModel);
			}	
		}
		
	}
	
	public void setDBDetails(Element element,DBConnSystemModel dbConSysModel) {
		NodeList nodeList = element.getChildNodes();
		
		for( int i = 0 ; i < nodeList.getLength() ; i++ ) {
			Node node = nodeList.item(i);
			if( node.getNodeType() == Node.ELEMENT_NODE ) {
				Element childElement = (Element) node;
				if(childElement.getTagName().equals("XREF"))
					dbConSysModel.setXref(childElement.getTextContent());
				else if(childElement.getTagName().equals("DESCPRITION"))
					dbConSysModel.setDescription(childElement.getTextContent());
				else if(childElement.getTagName().equals("DBDetails"))
				{
					DBDetailsModel dbDetails = new DBDetailsModel();
					dbDetails.setId(childElement.getAttribute("id"));
					setInnerDBDetails(childElement,dbDetails);
					dbConSysModel.getDbDetailModelList().add(dbDetails);
				}
			}	
		}
	}
	
	public void setInnerDBDetails(Element element,DBDetailsModel dbDetails) {
		NodeList nodeList = element.getChildNodes();
		for( int i = 0 ; i < nodeList.getLength() ; i++ ) {
			Node node = nodeList.item(i);
			if( node.getNodeType() == Node.ELEMENT_NODE ) {
				Element childElement = (Element) node;
				if(childElement.getTagName().equals("NAME"))
					dbDetails.setName(childElement.getTextContent());
				else if(childElement.getTagName().equals("PQ"))
					dbDetails.setPq(childElement.getTextContent());
				else if(childElement.getTagName().equals("USER"))
					dbDetails.setUser(childElement.getTextContent());
			}	
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		DBConnSysModelParser parser = new DBConnSysModelParser();
		DBConnectionCBSModel model = parser.getDBConnectionCBSModel("config/prolinedefinition.xml");
		
		List<DBConnSystemModel> dbConSysModelList = model.getDbConSysModelList();
		for( DBConnSystemModel dbConSysModel : dbConSysModelList ) {
			System.out.println("Id----"+dbConSysModel.getId() );
			System.out.println("XREF ::::"+dbConSysModel.getXref());
			System.out.println("Description :::"+dbConSysModel.getDescription());
			System.out.println("========================================");
			List<DBDetailsModel> dbDetailList = dbConSysModel.getDbDetailModelList();
			for( DBDetailsModel dbDetails : dbDetailList ) {
				System.out.println("Id----"+dbDetails.getId());
				System.out.println("Name----"+dbDetails.getName());
				System.out.println("User----"+dbDetails.getUser());
				System.out.println("Pq-----"+dbDetails.getPq());
			}
		}
		
	}

}
