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
	 * 对上传的空文件进行处理，处理的结果是，将UploadFileVO对象的returnId和returnName的值设置为空字符串
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
	 * 对上传的文件名过滤英文逗号，默认的情况下将英文逗号过滤成中文逗号
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
			replaceText="，";
		}
		fileName=fileName.replaceAll(",", replaceText);
		
		return fileName;
	}
	
	public String filterFileNameForEnglishComma(String fileName)
	{
		return filterFileNameForEnglishComma(fileName,"，");
	}
	/**
	 * 对上传的文件名过滤英文单引号，默认的情况下将英文单引号过滤成中文单引号
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
			replaceText="’";
		}
		fileName=fileName.replaceAll("'", replaceText);
		return fileName;
	}
	public String filterFileNameForEnglishSingleQuotes(String fileName)
	{
		return filterFileNameForEnglishSingleQuotes(fileName,"’");
	}
}
