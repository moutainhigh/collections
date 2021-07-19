/**
 * 
 */
package com.gwssi.dw.dq.reportModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>Title: </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) Sep 25, 2008</p>
 * <p>Company: 长城软件</p>
 * @author zhouyi
 * @version 1.0
 */
public class DqItem
{
	//指标集 或者 分组
	private String id;//唯一标识
	
	private String text;//显示文本
	
	private String type;//查询条目的类型 包括 指标集合和分组
	
	private List metadatas;//查询条目中的元数据集合
	
	private boolean showCode = false;
	//
	//private 
	
	private DqItem(String type){
		this.type = type;
		metadatas = new ArrayList();
	}
	
	public static DqItem getTargetsInstance(){
		return new DqItem("targets");
	}
	
	public static DqItem getGroupInstance(){
		return new DqItem("group");
	}
	
	public String getType(){
		return this.type;
	}

	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return the text
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * @return the metadatas
	 */
	public List getMetadatas()
	{
		return metadatas;
	}

	/**
	 * @param metadatas the metadatas to set
	 */
	public void setMetadatas(List metadatas)
	{
		this.metadatas = metadatas;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	public void addMetadata(DqMetadata metadata) {
		this.metadatas.add(metadata);
	}
	
	public DqMetadata getMetadata(int index){
		return  (DqMetadata)this.metadatas.get(index);
	}

	/**
	 * @return the showCode
	 */
	public boolean isShowCode()
	{
		return showCode;
	}

	/**
	 * @param showCode the showCode to set
	 */
	public void setShowCode(boolean showCode)
	{
		this.showCode = showCode;
	}
}
