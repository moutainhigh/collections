package com.gwssi.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.func.SqlStatement;

import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.resource.collect.vo.VoResCollectDataitem;
import com.gwssi.resource.collect.vo.VoResCollectTable;
import com.gwssi.webservice.server.ServiceDAO;
import com.gwssi.webservice.server.ServiceDAOImpl;

/**
 * excel导入需采集表结构
 * 
 * @author elvislee
 * 
 */
public class ExlUtil
{

	public  SqlStatement  impTodataitem(InputStream is,SqlStatement stmt,TxnContext request) throws BiffException,
			IOException, TxnDataException
	{
		WorkbookSettings workbookSettings = new WorkbookSettings();
		workbookSettings.setEncoding("ISO-8859-1"); // 暂时没用
		Workbook wb;
		wb = Workbook.getWorkbook(is);
//		Sheet sheet = wb.getSheet(0);// 得到工作薄中的第一个工作表
		
		Sheet[] sheets = wb.getSheets();// 获取所有的sheet  
		ServiceDAO daoTable	= new ServiceDAOImpl();; // 操作数据表Dao
		StringBuffer sql_code_table = new StringBuffer();
		String temp_code_table = "";
		String temp_dataitem = "";
		String id2 = UuidGenerator.getUUID();
		for (int x = 0; x < sheets.length; x++) {  
		   Sheet s = wb.getSheet(x);// 获取sheet  
		   if (s.getRows() == 0) {// 判断sheet是否为空  
			   System.out.println("Sheet" + (x + 1) + "为空!");  
			   continue;  
		   } else {  
			   try {
					String sheetName = sheets[x].getName();
					if (sheets[x] != null) {
						// 获取表格总列数
						int rsColumns = sheets[x].getColumns();
						System.out.println("lieshu==="+rsColumns);
						// 获取表格总行数
						int rsRows = sheets[x].getRows();
						System.out.println("hang shu==="+rsRows);
						// 循环文件里的数据 VoResCollectTable
						List<VoResCollectDataitem> boList = new ArrayList();
						//SqlStatement stmt = new SqlStatement();

						// 用来判断excel格式是否符合标准
						List listTable = new ArrayList();
						listTable.add("表名称");
						listTable.add("表中文名称");
						listTable.add("表类型");
						listTable.add("表描述");
						listTable.add("所属服务对象");
						
						List listItem = new ArrayList();
						listItem.add("数据项名称");
						listItem.add("数据项中文名称");
						listItem.add("数据项类型");
						listItem.add("数据项长度");
						listItem.add("是否主键");
						listItem.add("是否代码表");
						listItem.add("对应代码表");
						listItem.add("数据项描述");
						VoResCollectTable vorct = new VoResCollectTable();
						for (int first = 0; first < 8; first++) {
							String temp = sheets[x].getRow(2)[first].getContents().trim();
							for (int z = 0; z < listItem.size(); z++) {
								if (temp.contains(listItem.get(z).toString())
										|| listItem.get(z) == temp) {
									listItem.remove(z);
								}
							}
						}
						for (int first = 0; first < 5; first++) {
							String temp = sheets[x].getRow(0)[first].getContents();
							for (int z = 0; z < listTable.size(); z++) {
								if (temp.contains(listTable.get(z).toString())
										|| listTable.get(z) == temp) {
									listTable.remove(z);
								}
							}
						}
						if (listItem.size() != 0 || listTable.size() != 0) {
							System.out.println("listItem.size()=" + listItem.size());
							System.out.println("listItem.size()=" + listItem.get(0));
							System.out.println("listTable.size()=" + listTable.size());
							System.out.println("Excel不符合格式规范");
							throw new TxnDataException("error", "Excel不符合格式规范!");
							// 返回前台，提示相应信息
						} else {
							List<Map> rwlist1 = new ArrayList();
							for(int j = 0; j < 5; j++){
								if (sheets[x].getRow(0)[j].getContents().equals("表名称")) {
									vorct.setTable_name_en(sheets[x].getRow(1)[j].getContents());
								} else if (sheets[x].getRow(0)[j].getContents().equals("表中文名称")) {
									vorct.setTable_name_cn(sheets[x].getRow(1)[j].getContents());
								} else if (sheets[x].getRow(0)[j].getContents().equals("表类型")) {
									temp_code_table = sheets[x].getRow(1)[j].getContents();
									sql_code_table = new StringBuffer();
									sql_code_table.append("select * from codedata t where codename='" + temp_code_table + "' and codetype = '资源管理_表类型'");
									System.out.println("表类型sql=="+sql_code_table.toString());
									rwlist1 = daoTable.query(sql_code_table.toString());//dao.queryMethodList(sql.toString());
									if(rwlist1.size() > 0){
										vorct.setTable_type(rwlist1.get(0).get("CODEVALUE").toString());
									}
								} else if (sheets[x].getRow(0)[j].getContents().equals("表描述")) {
									vorct.setTable_desc(sheets[x].getRow(1)[j].getContents());
								} else if (sheets[x].getRow(0)[j].getContents().equals("所属服务对象")) {
									temp_code_table = sheets[x].getRow(1)[j].getContents();
									sql_code_table = new StringBuffer();
									sql_code_table.append("select service_targets_id,service_targets_name from res_service_targets where is_markup='Y' and service_targets_name='" + temp_code_table + "'");
									System.out.println("所属服务对象sql=="+sql_code_table.toString());
									rwlist1 = daoTable.query(sql_code_table.toString());//dao.queryMethodList(sql.toString());
									if(rwlist1.size() > 0){
										vorct.setService_targets_id(rwlist1.get(0).get("SERVICE_TARGETS_ID").toString());
									}
								}
							}
							VoResCollectDataitem vo = new VoResCollectDataitem();
							StringBuffer insertSql = new StringBuffer();
							for (int i = 3; i < rsRows; i++) {
								String temp = "";
								List<Map> rwlist = new ArrayList();
								vo = new VoResCollectDataitem();
								for (int j = 0; j < rsColumns; j++) {
									if (sheets[x].getRow(2)[j].getContents().equals("数据项名称")) {
										vo.setDataitem_name_en(sheets[x].getRow(i)[j].getContents());
									} else if (sheets[x].getRow(2)[j].getContents().equals("数据项中文名称")) {
										vo.setDataitem_name_cn(sheets[x].getRow(i)[j].getContents());
									} else if (sheets[x].getRow(2)[j].getContents().equals("数据项类型")) {
										temp_code_table = sheets[x].getRow(i)[j].getContents();
										sql_code_table = new StringBuffer();
										sql_code_table.append("select * from codedata t where codename='" + temp_code_table + "' and codetype = '资源管理_数据项类型'");
										System.out.println("数据项类型sql=="+sql_code_table.toString());
										rwlist = daoTable.query(sql_code_table.toString());//dao.queryMethodList(sql.toString());
										if(rwlist.size() > 0){
											vo.setDataitem_type(rwlist.get(0).get("CODEVALUE").toString());
										}
									} else if (sheets[x].getRow(2)[j].getContents().equals("数据项长度")) {
										if(sheets[x].getRow(i)[j].getContents()!=null&&!"".equals(sheets[x].getRow(i)[j].getContents())){
											vo.setDataitem_long(sheets[x].getRow(i)[j].getContents());
										}else{
											vo.setDataitem_long("");
										}
									} else if (sheets[x].getRow(2)[j].getContents().equals("是否主键")) {
										if(sheets[x].getRow(i)[j].getContents().trim()!=null&&sheets[x].getRow(i)[j].getContents().trim().equals("是")){
											vo.setIs_key(CollectConstants.IS_KEY);//是否主键
										}else{
											vo.setIs_key(CollectConstants.EXLIMP_TABLE_STATUS_N);
										}
									} else if (sheets[x].getRow(2)[j].getContents().equals("是否代码表")) {
										if(sheets[x].getRow(i)[j].getContents().trim()!=null&&sheets[x].getRow(i)[j].getContents().trim().equals("是")){
											vo.setIs_code_table(CollectConstants.EXLIMP_TABLE_STATUS_Y);
										}else{
											vo.setIs_code_table(CollectConstants.EXLIMP_TABLE_STATUS_N);
										}
									} else if (sheets[x].getRow(2)[j].getContents().equals("对应代码表")) {
										temp_code_table = sheets[x].getRow(i)[j].getContents();
										sql_code_table = new StringBuffer();
										sql_code_table.append("select * from codedata t where codename='" + temp_code_table + "' and codetype = '资源管理_对应代码表'");
										System.out.println("对应代码表sql=="+sql_code_table.toString());
										rwlist = daoTable.query(sql_code_table.toString());//dao.queryMethodList(sql.toString());
										if(rwlist.size() > 0){
											vo.setCode_table(rwlist.get(0).get("CODEVALUE").toString());
										}
//										vo.setCode_table(sheets[x].getRow(i)[j].getContents());
									} else if (sheets[x].getRow(2)[j].getContents().equals("数据项描述")) {
										try{
											vo.setDataitem_long_desc(sheets[x].getRow(i)[j].getContents());
										}catch(Exception e){
											vo.setDataitem_long_desc("");
										}
										
									}
								}
								vo.setIs_markup(ExConstant.IS_MARKUP_Y);//有效标记
								vo.setCreator_id(request.getRecord("oper-data").getValue("userID"));//创建人
								vo.setCreated_time(CalendarUtil.getCurrentDateTime());//创建时间
								vo.setLast_modify_id("");
								vo.setLast_modify_time("");
								
								String id = UuidGenerator.getUUID();
								
								vo.setCollect_dataitem_id(id);
								vo.setCollect_table_id(id2);
								vorct.setCollect_table_id(id2);
								vorct.setIs_markup(ExConstant.IS_MARKUP_Y);//有效标记
								vorct.setCreator_id(request.getRecord("oper-data").getValue("userID"));//创建人
								vorct.setCreated_time(CalendarUtil.getCurrentDateTime());//创建时间
								vorct.setCj_ly(CollectConstants.TYPE_CJLY_OUT);//外部采集
								vorct.setIf_creat(CollectConstants.TYPE_IF_CREAT_NO);//未生成
								vorct.setTable_status("");
								vorct.setLast_modify_id("");
								vorct.setLast_modify_time("");
								insertSql = new StringBuffer();
								insertSql
								.append("insert into RES_COLLECT_DATAITEM(COLLECT_DATAITEM_ID,COLLECT_TABLE_ID,DATAITEM_NAME_EN,DATAITEM_NAME_CN,DATAITEM_TYPE,DATAITEM_LONG,IS_KEY,IS_CODE_TABLE,CODE_TABLE,DATAITEM_LONG_DESC,IS_MARKUP,CREATOR_ID,CREATED_TIME,LAST_MODIFY_ID,LAST_MODIFY_TIME) values('");
								insertSql.append(vo.getCollect_dataitem_id() + "','"
									+ vo.getCollect_table_id() + "','"
									+ vo.getDataitem_name_en() + "','"
									+ vo.getDataitem_name_cn() + "','"
									+ vo.getDataitem_type() + "','"
									+ vo.getDataitem_long() + "','"
									+ vo.getIs_key() + "','"
									+ vo.getIs_code_table() + "','"
									+ vo.getCode_table() + "','"
									+ vo.getDataitem_long_desc() + "','"
									+ vo.getIs_markup() + "','"
									+ vo.getCreator_id() + "','"
									+ vo.getCreated_time() + "','"
									+ vo.getLast_modify_id() + "','"
									+ vo.getLast_modify_time() + "')");
								System.out.println("insertSql插入数据项表RES_COLLECT_DATAITEM===" + insertSql);
									
								try{
									stmt.addSqlStmt(insertSql.toString());
								}catch (Exception e) {
									// TODO Auto-generated catch block
									throw new TxnDataException("error", "插入数据项表失败!");
								}
							}
							StringBuffer insertSql_rct = new StringBuffer();
							System.out.println("fj id==="+request.getRecord("record").getValue("fj_fk"));
							System.out.println("fj mc==="+request.getRecord("record").getValue("fjmc"));
							insertSql_rct.append("insert into RES_COLLECT_TABLE(COLLECT_TABLE_ID,SERVICE_TARGETS_ID,TABLE_NAME_EN,TABLE_NAME_CN,TABLE_TYPE,TABLE_DESC,TABLE_STATUS,IS_MARKUP,CREATOR_ID,CREATED_TIME,LAST_MODIFY_ID,LAST_MODIFY_TIME,CJ_LY,IF_CREAT,FJ_FK,FJMC) values('");
							insertSql_rct.append(vorct.getCollect_table_id() + "','"
							+ vorct.getService_targets_id() + "','"
							+ vorct.getTable_name_en() + "','"
							+ vorct.getTable_name_cn() + "','"
							+ vorct.getTable_type() + "','"
							+ vorct.getTable_desc() + "','"
							+ vorct.getTable_status() + "','"
							+ vorct.getIs_markup() + "','"
							+ vorct.getCreator_id() + "','"
							+ vorct.getCreated_time() + "','"
							+ vorct.getLast_modify_id() + "','"
							+ vorct.getLast_modify_time() + "','"
							+ vorct.getCj_ly() +"','"
							+ vorct.getIf_creat()+"','"
							+ request.getRecord("record").getValue("fj_fk")+"','"
							+ request.getRecord("record").getValue("fjmc")+"')");
							System.out.println("insertSql插入数据表===" + insertSql_rct);
							try{
								stmt.addSqlStmt(insertSql_rct.toString());
							}catch (Exception e) {
								// TODO Auto-generated catch block
								throw new TxnDataException("error", "插入数据表RES_COLLECT_TABLE失败!");
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new TxnDataException("error", "Excel不符合格式规范!");
				}
				
		   }
		}
		return stmt;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws BiffException, IOException
	{
		String filePath = "D:/RES_COLLECT_DATAITEM.xls";
		InputStream fs = null;
		fs = new FileInputStream(filePath);
		//ExlUtil.impTodataitem(fs);
	}

}
