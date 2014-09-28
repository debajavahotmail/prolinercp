package com.proline.util.parsers;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.proline.models.AppServerConnSystemModel;
import com.proline.models.AppServerConnectionCBSModel;
import com.proline.models.AppServerDetailsModel;
import com.proline.models.DBConnSystemModel;
import com.proline.models.DBConnectionCBSModel;
import com.proline.models.DBDetailsModel;

public class AppServerConnSysModelParser {

		
		public AppServerConnectionCBSModel getAppServerConnectionCBSModel(String filePath) throws Exception {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			File prolineXmlFile = new File(filePath);
			Document document = db.parse(prolineXmlFile);
			return getAppServerConnectionCBSModel(document);
		}
		
		private AppServerConnectionCBSModel getAppServerConnectionCBSModel(Document document) {
			AppServerConnectionCBSModel appSerCbsModel = new AppServerConnectionCBSModel();
			NodeList nodeList = document.getChildNodes();
			for( int i = 0 ; i < nodeList.getLength() ; i++ ) {
				Node node = nodeList.item(i);
				if( node.getNodeType() == Node.ELEMENT_NODE ) {
					Element element = (Element) node;
					setSystemDetails(element,appSerCbsModel );
				}
			}
			return appSerCbsModel;
		}
		
		private void setSystemDetails(Element element,AppServerConnectionCBSModel appSerCbsModel) {
			//System
			NodeList nodeList = element.getChildNodes();
			for( int i = 0 ; i < nodeList.getLength() ; i++ ) {
				Node node = nodeList.item(i);
				if( node.getNodeType() == Node.ELEMENT_NODE ) {
					Element childElement = (Element) node;
					String id = childElement.getAttribute("id");
					AppServerConnSystemModel appSerConSysModel = new AppServerConnSystemModel();
					appSerConSysModel.setId(id);
					setDBDetails(childElement,appSerConSysModel);
					appSerCbsModel.getAppServerConSysModelList().add(appSerConSysModel);
				}	
			}
			
		}
		
		public void setDBDetails(Element element,AppServerConnSystemModel appSerConSysModel) {
			NodeList nodeList = element.getChildNodes();
			
			for( int i = 0 ; i < nodeList.getLength() ; i++ ) {
				Node node = nodeList.item(i);
				if( node.getNodeType() == Node.ELEMENT_NODE ) {
					Element childElement = (Element) node;
					if(childElement.getTagName().equals("XREF"))
						appSerConSysModel.setXref(childElement.getTextContent());
					else if(childElement.getTagName().equals("DESCPRITION"))
						appSerConSysModel.setDescription(childElement.getTextContent());
					else if(childElement.getTagName().equals("DBDetails"))
					{
						AppServerDetailsModel appSerDetails = new AppServerDetailsModel();
						appSerDetails.setId(childElement.getAttribute("id"));
						setInnerDBDetails(childElement,appSerDetails);
						appSerConSysModel.getAppServerDetailModelList().add(appSerDetails);
					}
				}	
			}
		}
		
		public void setInnerDBDetails(Element element,AppServerDetailsModel AppSerDetails) {
			NodeList nodeList = element.getChildNodes();
			for( int i = 0 ; i < nodeList.getLength() ; i++ ) {
				Node node = nodeList.item(i);
				if( node.getNodeType() == Node.ELEMENT_NODE ) {
					Element childElement = (Element) node;
					if(childElement.getTagName().equals("NAME"))
						AppSerDetails.setName(childElement.getTextContent());
					else if(childElement.getTagName().equals("PQ"))
						AppSerDetails.setPq(childElement.getTextContent());
				}	
			}
		}
		
		public static void main(String[] args) throws Exception {
			
			AppServerConnSysModelParser parser = new AppServerConnSysModelParser();
			AppServerConnectionCBSModel model = parser.getAppServerConnectionCBSModel("config/Appprolinedefinition.xml");
			
			List<AppServerConnSystemModel> AppSerConSysModelList = model.getAppServerConSysModelList();
			for( AppServerConnSystemModel appSerConSysModel : AppSerConSysModelList ) {
				System.out.println("Id----"+appSerConSysModel.getId() );
				System.out.println("XREF ::::"+appSerConSysModel.getXref());
				System.out.println("Description :::"+appSerConSysModel.getDescription());
				System.out.println("========================================");
				List<AppServerDetailsModel> appServerDetailList = appSerConSysModel.getAppServerDetailModelList();
				for( AppServerDetailsModel dbDetails : appServerDetailList ) {
					System.out.println("Id----"+dbDetails.getId());
					System.out.println("Name----"+dbDetails.getName());
					//System.out.println("User----"+dbDetails.getUser());
					System.out.println("Pq-----"+dbDetails.getPq());
				}
			}
			
		}

	}



