package com.gwssi.dw.runmgr.services.txn;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import net.sf.json.JSONObject;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.constant.ShareConstants;
import com.gwssi.common.util.AnalyCollectFile;
import com.gwssi.common.util.CalendarUtil;
import com.gwssi.dw.runmgr.services.SelfServiceDefine;
import com.gwssi.dw.runmgr.services.common.ColumnBean;
import com.gwssi.dw.runmgr.services.common.Constants;
import com.gwssi.webservice.server.GSGeneralWebService;

public class TxnSysSvrTest extends TxnService
{
	//数据表名称
	private static final String USER_TABLE_NAME = "sys_svr_user";
	
	//数据表名称
	private static final String CONFIG_TABLE_NAME = "sys_svr_config";
	
	//数据表名称
	private static final String SERVICE_TABLE_NAME = "sys_svr_service";
	
	//数据表名称 add by dwn 2013-04-02
	private static final String SERVICE_CONDITION_TABLE_NAME = "share_service_condition";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_svr_service";
	
    //查询列表
	private static final String USER_ROWSET_FUNCTION = "select sys_svr_test_user list";
	
    //查询列表
	private static final String CONFIG_ROWSET_FUNCTION = "select sys_svr_config list";
	
	protected void prepare(TxnContext arg0) throws TxnException
	{
	}
	
	public void txn50204001( TxnContext context ) throws TxnException
	{
//		BaseTable table = TableFactory.getInstance().getTableObject( this, USER_TABLE_NAME );
		// 此方法在user的dao的配置文件中配置
//		table.executeFunction( USER_ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoSysSvrUser result[] = context.getSysSvrUsers( outputNode );
		//查询服务配置列表
		callService("50203001", context);
	}
	
	public void txn50204002( TxnContext context ) throws TxnException 
	{
		//callService("50203004",context);//查询配置信息
		
		//以下内容新增 by dwn-2013-04-02
		//BaseTable table = TableFactory.getInstance().getTableObject( this, SERVICE_CONDITION_TABLE_NAME );
		// 此方法在user的dao的配置文件中配置
		//table.executeFunction( "queryCondition", context, inputNode, outputNode );
		callService("40210101",context);
		callService("40200101",context);
		Recordset rs = context.getRecordset("config-param");
		while(rs.hasNext()){
			DataBus db = (DataBus)rs.next();
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("condition", db.getValue("frist_connector"));
			jsonObj.put("preParen", db.getValue("left_paren"));
			//jsonObj.put("tableOneId", db.getValue("left_table_no"));
			jsonObj.put("tableOneName", db.getValue("table_name_en"));
			jsonObj.put("tableOneNameCn", db.getValue("table_name_cn"));
			//jsonObj.put("columnOneId", db.getValue("left_column_no"));
			jsonObj.put("columnOneName", db.getValue("column_name_en"));
			jsonObj.put("columnOneNameCn", db.getValue("column_name_cn"));
			jsonObj.put("relation", db.getValue("second_connector"));
			jsonObj.put("paramValue", db.getValue("param_value"));
			jsonObj.put("postParen", db.getValue("right_paren"));
			jsonObj.put("paramType", db.getValue("param_type"));
			String first_connector = db.getValue("frist_connector");
			String second_connector = db.getValue("second_connector");
			String tmpConn = "";
	
			if("AND".equals(first_connector)){
				tmpConn="并且";
			}else if("OR".equals(first_connector)){
				tmpConn="或者";
			}else{
				tmpConn = first_connector;
			}
			String tmpSec = "";
			if("<=".equals(second_connector)){
				tmpSec = "小于等于";
			}else if ("<".equals(second_connector)){
				tmpSec = "小于";
			}else if (">=".equals(second_connector)){
				tmpSec="大于等于";
			}else if ("like".equals(second_connector)){
				tmpSec = "包含";
			}else if ("=".equals(second_connector)){
				tmpSec = "等于";
			}else if ("<>".equals(second_connector)){
				tmpSec = "不等于";
			}else if (">".equals(second_connector)){
				tmpSec = "大于";
			}else if ("IN".equals(second_connector)){
				tmpSec = "包含于";
			}else if ("IS NOT".equals(second_connector)){
				tmpSec = "不等于";
			}else{
				tmpSec = second_connector;
			}
			
			String param_text = "<font color=red>"+tmpConn+"</font> "+db.getValue("table_name_cn")+"的 "+db.getValue("column_name_cn")+" <font color=red>"+tmpSec+"</font> ";
			jsonObj.put("paramText", param_text);
			
			db.setValue("params", jsonObj.toString());
		}
		
		
	}
	
	public void txn50204003( TxnContext context ) throws TxnException 
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, SERVICE_TABLE_NAME );

		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		
		if(context.getRecord("record").getValue("service_type").equals(Constants.SERVICE_TYPE_GENERAL)){
			String sql = "SELECT table_no, table_name_cn FROM sys_table_semantic WHERE table_no='"+context.getRecord("record").getValue("table_no")+"'";
			table.executeSelect(sql, context, "table-info");
			context.getRecord("record").setValue("table_name", context.getRecord("table-info").getValue("table_name_cn"));
			context.getRecord("record").setValue("table_no", context.getRecord("table-info").getValue("table_no"));
			if((context.getRecord("record").getValue("param_columns") != null) && (!context.getRecord("record").getValue("param_columns").trim().equals(""))){
				String[] columnIds = context.getRecord("record").getValue("param_columns").split(Constants.SERVICE_COLUMN_SEPARATOR);
				for(int i = 0;i < columnIds.length;i++){
					String id = columnIds[i];
					sql = "select column_name, column_name_cn from sys_column_semantic where column_no='"+id+"'";
					table.executeRowset(sql, context, "param-column");
				}
			}
		}else{
			String className = context.getRecord("record").getValue("table_no");
			try {
				Class clazz = Class.forName(Constants.SELF_SERVICE_PACKAGE+className);
				SelfServiceDefine ssd = (SelfServiceDefine)clazz.newInstance();
				List list = ssd.getParamColumns();
				for(int i = 0;i < list.size();i++){
					ColumnBean column = (ColumnBean)list.get(i);
					DataBus db = new DataBus();
					db.setValue("column_name", column.getName());
					db.setValue("column_name_cn", column.getDesc());
					context.addRecord("param-column", db);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			context.getRecord("record").setValue("table_name", context.getRecord("record").getValue("table_no"));
		}
		
//		if(context.getRecord("record").getValue("service_type").equals(Constants.SERVICE_TYPE_GENERAL)){
//			String[] params = context.getRecord("record").getValue("param_columns").split(Constants.SERVICE_COLUMN_SEPARATOR);
//			for(int i = 0;i < params.length;i++){
//				String id = params[i];
//				table.executeSelect("", context, new DataBus());
//			}
//		}
//		GSGeneralWebService service = new GSGeneralWebService();
//		Map params = new HashMap();
//		params.put("LOGIN_NAME", "tt");
//		params.put("PASSWORD", "ACCC9105DF5383111407FD5B41255E23");
//		params.put(Constants.SERVICE_PARAM_SERVICE_CODE, "test");
//		params.put("EST_DATE", "2000-03-08");
//		Map result = service.query(params);
	}
	
	/**
	 * 
	 * txn50204004(接口生成数据文件)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param context
	 * @throws TxnException        
	 * void       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public void txn50204004( TxnContext context ) throws TxnException
	{
		
		String file_map = context.getRecord("select-key").getValue("file_map");
		String file_str[] = file_map.split("#~#");
		HashMap<String,String> fileMap = new HashMap<String,String>();
		for(int i=0;i<file_str.length;i++){
			String tmpStr = file_str[i];
			fileMap.put(tmpStr.substring(0, tmpStr.indexOf("=")), tmpStr.substring(tmpStr.indexOf("=")+1));
		}
		
		
		String[] colNameEnArray = fileMap.get("column_name_en").split(",");
		String[] colNameCnArray = fileMap.get("column_name_cn").split(","); 
		
		fileMap.remove("column_name_en");
		fileMap.remove("column_name_cn");
		fileMap.remove("JSJLS");
		fileMap.put("JSJLS",Integer.toString(ShareConstants.SERVICE_DEFAULT_MAX_RECORDS));
		GSGeneralWebService filews = new GSGeneralWebService();
		HashMap fileresult;
		try {
			fileresult = (HashMap)filews.query(fileMap);
			HashMap[] filemapArray = (HashMap[])fileresult.get("GSDJ_INFO_ARRAY");
			
			String curTime = CalendarUtil.getCurrentDateTime().replace("-", "").replace(" " , "").replace(":", "");
			String txtName = "shareFile_"+curTime+".txt";
			String  excelName= "shareFile_"+curTime+".xls";
			String fileName = ExConstant.SHARE_CONFIG+File.separator+txtName;
			
			String excelFile = ExConstant.SHARE_CONFIG+File.separator+excelName;
			
			String txtUrl = "/downloadFile?file="+fileName+"&&fileName="+txtName;
			String excelUrl = "/downloadFile?file="+excelFile+"&&fileName="+excelName;
			WritableWorkbook book = Workbook.createWorkbook(new File(excelFile));
			WritableSheet sheet = book.createSheet("共享服务数据",0);
			
			
			StringBuffer filecontext = new StringBuffer();
			if(filemapArray != null && filemapArray.length != 0){
				
				for(int i=0;i<colNameCnArray.length;i++){
					
					if(i==(colNameCnArray.length-1)){
						filecontext.append(colNameCnArray[i]+"\r\n");
					}else{
						filecontext.append(colNameCnArray[i]+",");
					}
					
					Label label = new Label(i,0,colNameCnArray[i]);
					sheet.addCell(label);
				}
				
				for(int i = 0 ;filemapArray != null && i < filemapArray.length;i++){
					for(int j = 0; j < colNameEnArray.length; j++){
						HashMap tmpMap1 = (HashMap)filemapArray[i];
						String tmpStr = "";	
						if(tmpMap1.get(colNameEnArray[j].toUpperCase())!=null){
							tmpStr = tmpMap1.get(colNameEnArray[j].toUpperCase()).toString();
						}
							
						if(j==(colNameEnArray.length-1)){
							filecontext.append(tmpStr+"\r\n");
						}else{
							filecontext.append(tmpStr+",");
						}
						
						Label label = new Label(j,i+1,tmpStr);
						sheet.addCell(label);
					}
				}
			}
			
			
			
			AnalyCollectFile ac = new AnalyCollectFile();
			ac.writeFile(fileName,filecontext.toString());
			
			
			book.write();
			book.close();
			
			context.getRecord("record").setValue("txtUrl", txtUrl);
			context.getRecord("record").setValue("excelUrl", excelUrl);
			
			context.getRecord("record").setValue("result", ShareConstants.GET_INTERFACE_DATA_SUCCESS);
			
		} catch (DBException e) {
			// TODO Auto-generated catch block
			context.getRecord("record").setValue("result", ShareConstants.GET_INTERFACE_DATA_FAIL);
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			context.getRecord("record").setValue("result", ShareConstants.GET_INTERFACE_DATA_FAIL);
		}catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			context.getRecord("record").setValue("result", ShareConstants.GET_INTERFACE_DATA_FAIL);
		}
		
		
	}

}
