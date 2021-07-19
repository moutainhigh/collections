package com.gwssi.common.task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.gwssi.common.component.logger.TxnLogger;
import cn.gwssi.template.freemarker.FreemarkerUtil;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;
import com.gwssi.common.util.JSONUtils;
import com.gwssi.common.util.JsonDataUtil;

public class InterfaceData
{
	DBOperation	operation	= null;

	public InterfaceData()
	{
		operation = DBOperationFactory.createOperation();
	}

	// 日志
	protected static Logger	logger	= TxnLogger.getLogger(IndexJob.class
											.getName());
	
	
	/**
	 * 
	 * getShare_Interface()
	 * @return  
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public String getShare_Interface()
	{
		try {
			List share_Interface = new ArrayList();
			String sql = "select t.interface_id," +
					"t.interface_name,t.table_id," +
					"y.yhxm,t.last_modify_time" +
					" from share_interface t ,xt_zzjg_yh_new y" +
					" where t.is_markup = 'Y' and t.creator_id=y.yhid_pk" +
					" order by t.last_modify_time desc";
			share_Interface = operation.select(sql);
			
			return JsonDataUtil.toJSONString(share_Interface);
		} catch (DBException e) {
			
			e.printStackTrace();
			System.out.println("查询接口报错！");
			return null;
		}		
	}
	/**
	 * 
	 * getShare_Table()
	 * @return              
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public String getShare_Table()
	{
		try {
			List share_Interface = new ArrayList();
			String sql = "select t.interface_id," +
					"''''||replace(table_id,',',''',''')||'''' table_id" +
					" from share_interface t" +
					" where t.is_markup = 'Y'";
			share_Interface = operation.select(sql);
			String tablesql = "select t1.table_name_cn,t3.service_targets_name,t2.topics_name" +
					" from res_share_table t1, res_business_topics t2,res_service_targets t3" +
					" where t1.business_topics_id = t2.business_topics_id(+)" +
					" and t3.service_targets_id=t2.service_targets_id" +
					" and t1.table_no in (";
			Map share_table=new HashMap();
			if(share_Interface!=null && share_Interface.size()>0){
				int len=share_Interface.size();
				for(int i=0;i<len;i++){
					Map map =(Map)share_Interface.get(i);
					String tmp=tablesql+map.get("TABLE_ID")+")";
					//System.out.println(tmp);
					List tmpList= operation.select(tmp);
					if(tmpList!=null && tmpList.size()>0){
						share_table.put(map.get("INTERFACE_ID"),tmpList);
					}
				}
			}
			return JsonDataUtil.toJSONString(share_table);
		} catch (DBException e) {
			
			e.printStackTrace();
			System.out.println("查询接口报错！");
			return null;
		}		
	}
	/**
	 * 将准备好的数据文件写入到指定文件夹 格式为Json文件
	 * 
	 * @param fileName
	 * @param content
	 */
	public void writeFile(String fileName, String content)
	{
		Writer writer;
		logger.info("fileName = " + fileName);
		File file = new File(fileName);
		if (StringUtils.isNotBlank(fileName)) {
			try {
				if (file.exists()) {
					file.delete();
				}
				writer = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(file), "UTF-8"));
				writer.write(content);
				writer.close();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**    
	 * main(这里用一句话描述这个方法的作用)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param args        
	 * void       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1    
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
