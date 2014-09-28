package com.proline.util.parsers;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.proline.models.ProlineModelBean;
import com.proline.models.ProlineTagType;

public class XMLDOMParser {

	public static void getElementTagNames(Element element) {

		NodeList nodeList = element.getChildNodes();
		for( int i = 0 ; i < nodeList.getLength() ; i++ ) {

			Node node = nodeList.item(i);
			if( node.getNodeType() == Node.ELEMENT_NODE ) {
				Element childElement = (Element) node;
//				System.out.println(childElement.getTagName());
				getElementTagNames(childElement);
			}

		}
	}

	public static void populateInnerTags(Element element,ProlineModelBean pModelBean) {

		NodeList nodeList = element.getChildNodes();
		for( int i = 0 ; i < nodeList.getLength() ; i++ ) {

			Node node = nodeList.item(i);
			if( node.getNodeType() == Node.ELEMENT_NODE ) {
				Element childElement = (Element) node;
				//				System.out.println(childElement.getTagName());
				ProlineModelBean pModelNewBean = null;
//				System.out.println(childElement.getChildNodes().getLength());
				if( childElement.getChildNodes().getLength() == 1 ) {

					pModelNewBean = new ProlineModelBean(childElement.getTagName(),childElement.getTextContent(), ProlineTagType.CHILD);
					//					pModelBean.addChildNode(pModelNewBean);
				}
				else {

					pModelNewBean = new ProlineModelBean(childElement.getTagName(),null, ProlineTagType.PARENT);
					//					pModelBean.addChildNode(pModelNewBean);
				}
				pModelBean.addChildNode(pModelNewBean);
				populateInnerTags(childElement,pModelNewBean);
			}

		}
	}

	public ProlineModelBean getProlineModelBeanList(String filePath) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		File prolineXmlFile = new File(filePath);
		Document document = db.parse(prolineXmlFile);
		return populateTagNames(document);
	}

	private ProlineModelBean populateTagNames(Document document) {
		ProlineModelBean pModelBean = null;
		NodeList nodeList = document.getChildNodes();
		for( int i = 0 ; i < nodeList.getLength() ; i++ ) {
			Node node = nodeList.item(i);
			if( node.getNodeType() == Node.ELEMENT_NODE ) {
				Element element = (Element) node;
				//				System.out.println(element.getTagName());
				
//				System.out.println(element.getTagName()+"----------"+element.getTextContent());

				if( element.getChildNodes().getLength() == 0 ) {
					pModelBean = new ProlineModelBean(element.getTagName(),element.getTextContent(), ProlineTagType.CHILD);
				}
				else {
					pModelBean = new ProlineModelBean(element.getTagName(),null, ProlineTagType.PARENT);
				}
				populateInnerTags(element,pModelBean);
			}
		}
		return pModelBean;

	}

//	public void show(ProlineModelBean pModelBean) {
//		System.out.println(pModelBean.getName()+"-----"+pModelBean.getTagType());
//		List<ProlineModelBean> pModelList = pModelBean.getChildren();
//		for( int i = 0 ; i < pModelList.size() ; i++ ) {
//			ProlineModelBean pModelBean1 = pModelList.get(i);
//			System.out.println(pModelBean1.getName()+"-----"+pModelBean1.getTagType());
//			show(pModelBean1);
//		}
//	}
//
//	public static void main(String[] args) throws Exception {
//		XMLDOMParser parser = new XMLDOMParser();
//		ProlineModelBean pModelBean = parser.getProlineModelBeanList("config/Proline.pss");
//		parser.show(pModelBean);
//	}

}
