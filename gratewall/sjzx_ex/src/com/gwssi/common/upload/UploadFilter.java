package com.gwssi.common.upload;

import java.util.Iterator;
import java.util.List;

public class UploadFilter
{
	private static final UploadFilter filter=new UploadFilter();
	private UploadFilter()
	{}
	public static UploadFilter instance()
	{
		return filter;
	}
	/**
	 * ���ϴ��Ŀ��ļ����д�������Ľ���ǣ���UploadFileVO�����returnId��returnName��ֵ����Ϊ���ַ���
	 * @param voList
	 */
	public void handleEmptyUploadFile(List voList)
	{
		if(voList==null)
		{
			System.err.println("voList is null");
			return;
		}
		Iterator it=voList.iterator();
		while(it.hasNext())
		{
			Object obj=it.next();
			if(obj instanceof UploadFileVO)
			{
				UploadFileVO vo=(UploadFileVO)obj;
				if(vo.getReturnId()==null)
				{
					vo.setReturnId("");
					vo.setReturnName("");
				}
			}
		}
		
	}
	/**
	 * ���ϴ����ļ�������Ӣ�Ķ��ţ�Ĭ�ϵ�����½�Ӣ�Ķ��Ź��˳����Ķ���
	 * @param fileName
	 * @param replaceText
	 * @return
	 */
	public String filterFileNameForEnglishComma(String fileName,String replaceText)
	{
		if(fileName==null)
		{
			System.err.println("fileName is null.");
			return null;
		}
		if(replaceText==null)
		{
			replaceText="��";
		}
		fileName=fileName.replaceAll(",", replaceText);
		
		return fileName;
	}
	
	public String filterFileNameForEnglishComma(String fileName)
	{
		return filterFileNameForEnglishComma(fileName,"��");
	}
	/**
	 * ���ϴ����ļ�������Ӣ�ĵ����ţ�Ĭ�ϵ�����½�Ӣ�ĵ����Ź��˳����ĵ�����
	 * @param fileName
	 * @param replaceText
	 * @return
	 */
	public String filterFileNameForEnglishSingleQuotes(String fileName,String replaceText)
	{
		if(fileName==null)
		{
			System.err.println("fileName is null.");
			return null;
		}
		if(replaceText==null)
		{
			replaceText="��";
		}
		fileName=fileName.replaceAll("'", replaceText);
		return fileName;
	}
	public String filterFileNameForEnglishSingleQuotes(String fileName)
	{
		return filterFileNameForEnglishSingleQuotes(fileName,"��");
	}
}
