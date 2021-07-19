package com.gwssi.webservice.client.wsdl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.exolab.castor.xml.schema.Schema;


/**
 * 
 * 
 * 项目名称：bjgs_exchange 类名称：ServiceInfo 类描述：服务信息 创建人：lizheng 创建时间：Apr 8, 2013
 * 5:31:29 PM 修改人：lizheng 修改时间：Apr 8, 2013 5:31:29 PM 修改备注：
 * 
 * @version
 * 
 */
public class ServiceInfo
{
	/** 服务名 */
	private String	name;

	/** WSDL文件位置 */
	private String	wsdllocation;

	private String	endpoint;

	private String	targetnamespace;

	private Schema	wsdlType;

	/** The list of operations that this service defines. */
	List			operations	= new ArrayList();

	public Schema getWsdlType()
	{
		return wsdlType;
	}

	public void setWsdlTypes(Schema wsdlType)
	{
		this.wsdlType = wsdlType;
	}

	public List getOperation()
	{
		return operations;
	}

	public Iterator getOperations()
	{
		return operations.iterator();
	}

	public void addOperation(OperationInfo operation)
	{
		operations.add(operation);
	}

	public String toString()
	{
		return getName();
	}

	public String getTargetnamespace()
	{
		return targetnamespace;
	}

	public void setTargetnamespace(String targetnamespace)
	{
		this.targetnamespace = targetnamespace;
	}

	public String getEndpoint()
	{
		return endpoint;
	}

	public void setEndpoint(String endpoint)
	{
		this.endpoint = endpoint;
	}

	public String getWsdllocation()
	{
		return wsdllocation;
	}

	public void setWsdllocation(String wsdllocation)
	{
		this.wsdllocation = wsdllocation;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
