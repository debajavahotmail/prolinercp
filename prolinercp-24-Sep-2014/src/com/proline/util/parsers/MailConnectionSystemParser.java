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
import com.proline.models.MailConnectionsModel;
import com.proline.models.MailSystemModel;

public class MailConnectionSystemParser {

	public MailConnectionsModel getMailConnectionSystemModel(String filePath) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		File prolineXmlFile = new File(filePath);
		Document document = db.parse(prolineXmlFile);
		return getMailConnectionsModel(document);		
		
	} 
	
	private MailConnectionsModel getMailConnectionsModel(Document document) {
		NodeList nodeList = document.getChildNodes();
		Node node = nodeList.item(0);
		Element rootElement = (Element) node;
//		System.out.println("element------->"+rootElement.getTagName());
		NodeList childNodeList = rootElement.getChildNodes();
		Node childNode = childNodeList.item(1);
		Element mailConnElement = (Element) childNode;
//		System.out.println("element------->"+dbConnElement.getTagName());
		NodeList systemNodeList = mailConnElement.getChildNodes();
		
		MailConnectionsModel mailConnModel = new MailConnectionsModel();
		for( int i = 0 ; i < systemNodeList.getLength() ; i++ ) {
			Node sysNode = systemNodeList.item(i);
			if( sysNode.getNodeType() == Node.ELEMENT_NODE ) {
				Element sysElement = (Element) sysNode;
//				System.out.println("element------->"+sysElement.getTagName());
				String id = sysElement.getAttribute("id");
				MailSystemModel mailSysModel = new MailSystemModel();
				mailSysModel.setId(id);
//				System.out.println("Id------"+id);
				setInnerSystemDetails(sysElement,mailSysModel);
				mailConnModel.getMailModelMap().put(id	, mailSysModel);
			}
		}
		return mailConnModel;
	}
	
	
	private void setInnerSystemDetails( Element element,MailSystemModel mailSysModel ) {
		NodeList nodeList = element.getChildNodes();
		for( int i = 0 ; i < nodeList.getLength() ; i++ ) {
			Node node = nodeList.item(i);
			if( node.getNodeType() == Node.ELEMENT_NODE ) {
				Element childElement = (Element) node;
//				System.out.println(childElement.getTextContent());
				if(childElement.getTagName().equals("PORT"))
					mailSysModel.setPort(childElement.getTextContent());
				else if(childElement.getTagName().equals("CONTENT"))
					mailSysModel.setContent(childElement.getTextContent());
				else if(childElement.getTagName().equals("HOST"))
					mailSysModel.setHostName(childElement.getTextContent());
				else if(childElement.getTagName().equals("USER"))
					mailSysModel.setUserName(childElement.getTextContent());
				else if(childElement.getTagName().equals("PWD"))
					mailSysModel.setPassword(childElement.getTextContent());
				else if(childElement.getTagName().equals("SUBJECT"))
					mailSysModel.setSubject(childElement.getTextContent());
			}
		}	
	}
	
	public static void main(String[] args) throws Exception {
		MailConnectionSystemParser parser = new MailConnectionSystemParser();
		MailConnectionsModel mailConnModel = parser.getMailConnectionSystemModel("config/MailDetails.xml");
		Map<String,MailSystemModel> mailMoldelMap = mailConnModel.getMailModelMap();
		Iterator<Map.Entry<String,MailSystemModel>> itr = mailMoldelMap.entrySet().iterator();
		while( itr.hasNext() ) {
			Map.Entry<String,MailSystemModel> me = itr.next();
			System.out.println("Key----->"+me.getKey());
			MailSystemModel mailModel = me.getValue();
			System.out.println(mailModel.getPort());
			System.out.println(mailModel.getHostName());
		}
		
		
		
	}
}
