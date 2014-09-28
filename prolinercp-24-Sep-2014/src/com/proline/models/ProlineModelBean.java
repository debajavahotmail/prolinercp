package com.proline.models;

import java.util.ArrayList;
import java.util.List;

public class ProlineModelBean {
	
	private String tagName;
	private String tagValue;
	private ProlineTagType tagType;
	private ProlineModelBean parent = null;
	private List<ProlineModelBean> children = null;
	
	public ProlineModelBean(String tagName,String tagValue,ProlineTagType tagType) 
	{
		this.parent = null;
		this.tagValue = tagValue;
		this.tagName = tagName;
		this.tagType = tagType;
		this.children = new ArrayList<ProlineModelBean>();
	} 

	public void addChildNode(ProlineModelBean child) 
	{
		child.parent = this;
		if (!children.contains(child))
			children.add(child);
	}

	public List<ProlineModelBean> getChildren()
	{
		return children;
	}

	public ProlineModelBean getParent() 
	{
		System.out.println("parent = "+parent);
		return parent;
	}

	public String getTagName() {
		System.out.println("tagname = "+tagName);
		return tagName;
	}

	public String getTagValue() {
		System.out.println("tagvalue = "+tagValue);
		return tagValue;
	}

	public ProlineTagType getTagType() {
		System.out.println("tagtype = "+tagType);
		return tagType;
	}

}
