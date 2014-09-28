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

import com.proline.models.AppServerConnectionsModel;
import com.proline.models.AppServerSystemModel;
import com.proline.models.DBSystemModel;
import com.proline.models.DatabaseConnectionsModel;


public class AppSereverConnectionSystemParser {
		
		public AppServerConnectionsModel getAppServerConnectionSystemModel(String filePath) throws Exception {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			File prolineXmlFile = new File(filePath);
			Document document = db.parse(prolineXmlFile);
			return getAppServerConnectionsModel(document);
		}
		
		private AppServerConnectionsModel getAppServerConnectionsModel(Document document) {
			NodeList nodeList = document.getChildNodes();
			Node node = nodeList.item(0);
			Element rootElement = (Element) node;
//			System.out.println("element------->"+rootElement.getTagName());
			NodeList childNodeList = rootElement.getChildNodes();
			Node childNode = childNodeList.item(1);
			Element dbConnElement = (Element) childNode;
//			System.out.println("element------->"+dbConnElement.getTagName());
			NodeList systemNodeList = dbConnElement.getChildNodes();
			
			AppServerConnectionsModel AppSereverConnModel = new AppServerConnectionsModel();
			for( int i = 0 ; i < systemNodeList.getLength() ; i++ ) {
				Node sysNode = systemNodeList.item(i);
				if( sysNode.getNodeType() == Node.ELEMENT_NODE ) {
					Element sysElement = (Element) sysNode;
//					System.out.println("element------->"+sysElement.getTagName());
					String id = sysElement.getAttribute("id");
					AppServerSystemModel AppServerSysModel = new AppServerSystemModel();
					AppServerSysModel.setId(id);
//					System.out.println("Id------"+id);
					setInnerSystemDetails(sysElement,AppServerSysModel);
					AppSereverConnModel.getAppServerMoldelMap().put(id	, AppServerSysModel);
				}
			}
			return AppSereverConnModel;
		}
		
		
		private void setInnerSystemDetails( Element element,AppServerSystemModel AppServerSysModel ) {
			NodeList nodeList = element.getChildNodes();
			for( int i = 0 ; i < nodeList.getLength() ; i++ ) {
				Node node = nodeList.item(i);
				if( node.getNodeType() == Node.ELEMENT_NODE ) {
					Element childElement = (Element) node;
//					System.out.println(childElement.getTextContent());
					if(childElement.getTagName().equals("HOST"))
						AppServerSysModel.setHostName(childElement.getTextContent());
					else if(childElement.getTagName().equals("NAME"))
						AppServerSysModel.setUserName(childElement.getTextContent());
					else if(childElement.getTagName().equals("PWD"))
						AppServerSysModel.setPassword(childElement.getTextContent());
				}
			}	
		}
		
		public static void main(String[] args) throws Exception {
			AppSereverConnectionSystemParser  parser = new AppSereverConnectionSystemParser ();
			AppServerConnectionsModel appServerConnModel = parser.getAppServerConnectionSystemModel("config/AppSereverConnectionDetails.xml");
			Map<String,AppServerSystemModel> appServerMoldelMap = appServerConnModel.getAppServerMoldelMap();
			Iterator<Map.Entry<String,AppServerSystemModel>> itr = appServerMoldelMap.entrySet().iterator();
			while( itr.hasNext() ) {
				Map.Entry<String,AppServerSystemModel> me = itr.next();
				System.out.println("Key----->"+me.getKey());
				AppServerSystemModel appSerModel = me.getValue();
				//System.out.println(appSerModel.getDatabaseName());
				System.out.println(appSerModel.getHostName());
			}
			
		}
}
