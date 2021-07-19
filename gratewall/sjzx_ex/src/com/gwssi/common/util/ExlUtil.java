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
 * excel������ɼ���ṹ
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
		workbookSettings.setEncoding("ISO-8859-1"); // ��ʱû��
		Workbook wb;
		wb = Workbook.getWorkbook(is);
//		Sheet sheet = wb.getSheet(0);// �õ��������еĵ�һ��������
		
		Sheet[] sheets = wb.getSheets();// ��ȡ���е�sheet  
		ServiceDAO daoTable	= new ServiceDAOImpl();; // �������ݱ�Dao
		StringBuffer sql_code_table = new StringBuffer();
		String temp_code_table = "";
		String temp_dataitem = "";
		String id2 = UuidGenerator.getUUID();
		for (int x = 0; x < sheets.length; x++) {  
		   Sheet s = wb.getSheet(x);// ��ȡsheet  
		   if (s.getRows() == 0) {// �ж�sheet�Ƿ�Ϊ��  
			   System.out.println("Sheet" + (x + 1) + "Ϊ��!");  
			   continue;  
		   } else {  
			   try {
					String sheetName = sheets[x].getName();
					if (sheets[x] != null) {
						// ��ȡ���������
						int rsColumns = sheets[x].getColumns();
						System.out.println("lieshu==="+rsColumns);
						// ��ȡ���������
						int rsRows = sheets[x].getRows();
						System.out.println("hang shu==="+rsRows);
						// ѭ���ļ�������� VoResCollectTable
						List<VoResCollectDataitem> boList = new ArrayList();
						//SqlStatement stmt = new SqlStatement();

						// �����ж�excel��ʽ�Ƿ���ϱ�׼
						List listTable = new ArrayList();
						listTable.add("������");
						listTable.add("����������");
						listTable.add("������");
						listTable.add("������");
						listTable.add("�����������");
						
						List listItem = new ArrayList();
						listItem.add("����������");
						listItem.add("��������������");
						listItem.add("����������");
						listItem.add("�������");
						listItem.add("�Ƿ�����");
						listItem.add("�Ƿ�����");
						listItem.add("��Ӧ�����");
						listItem.add("����������");
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
							System.out.println("Excel�����ϸ�ʽ�淶");
							throw new TxnDataException("error", "Excel�����ϸ�ʽ�淶!");
							// ����ǰ̨����ʾ��Ӧ��Ϣ
						} else {
							List<Map> rwlist1 = new ArrayList();
							for(int j = 0; j < 5; j++){
								if (sheets[x].getRow(0)[j].getContents().equals("������")) {
									vorct.setTable_name_en(sheets[x].getRow(1)[j].getContents());
								} else if (sheets[x].getRow(0)[j].getContents().equals("����������")) {
									vorct.setTable_name_cn(sheets[x].getRow(1)[j].getContents());
								} else if (sheets[x].getRow(0)[j].getContents().equals("������")) {
									temp_code_table = sheets[x].getRow(1)[j].getContents();
									sql_code_table = new StringBuffer();
									sql_code_table.append("select * from codedata t where codename='" + temp_code_table + "' and codetype = '��Դ����_������'");
									System.out.println("������sql=="+sql_code_table.toString());
									rwlist1 = daoTable.query(sql_code_table.toString());//dao.queryMethodList(sql.toString());
									if(rwlist1.size() > 0){
										vorct.setTable_type(rwlist1.get(0).get("CODEVALUE").toString());
									}
								} else if (sheets[x].getRow(0)[j].getContents().equals("������")) {
									vorct.setTable_desc(sheets[x].getRow(1)[j].getContents());
								} else if (sheets[x].getRow(0)[j].getContents().equals("�����������")) {
									temp_code_table = sheets[x].getRow(1)[j].getContents();
									sql_code_table = new StringBuffer();
									sql_code_table.append("select service_targets_id,service_targets_name from res_service_targets where is_markup='Y' and service_targets_name='" + temp_code_table + "'");
									System.out.println("�����������sql=="+sql_code_table.toString());
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
									if (sheets[x].getRow(2)[j].getContents().equals("����������")) {
										vo.setDataitem_name_en(sheets[x].getRow(i)[j].getContents());
									} else if (sheets[x].getRow(2)[j].getContents().equals("��������������")) {
										vo.setDataitem_name_cn(sheets[x].getRow(i)[j].getContents());
									} else if (sheets[x].getRow(2)[j].getContents().equals("����������")) {
										temp_code_table = sheets[x].getRow(i)[j].getContents();
										sql_code_table = new StringBuffer();
										sql_code_table.append("select * from codedata t where codename='" + temp_code_table + "' and codetype = '��Դ����_����������'");
										System.out.println("����������sql=="+sql_code_table.toString());
										rwlist = daoTable.query(sql_code_table.toString());//dao.queryMethodList(sql.toString());
										if(rwlist.size() > 0){
											vo.setDataitem_type(rwlist.get(0).get("CODEVALUE").toString());
										}
									} else if (sheets[x].getRow(2)[j].getContents().equals("�������")) {
										if(sheets[x].getRow(i)[j].getContents()!=null&&!"".equals(sheets[x].getRow(i)[j].getContents())){
											vo.setDataitem_long(sheets[x].getRow(i)[j].getContents());
										}else{
											vo.setDataitem_long("");
										}
									} else if (sheets[x].getRow(2)[j].getContents().equals("�Ƿ�����")) {
										if(sheets[x].getRow(i)[j].getContents().trim()!=null&&sheets[x].getRow(i)[j].getContents().trim().equals("��")){
											vo.setIs_key(CollectConstants.IS_KEY);//�Ƿ�����
										}else{
											vo.setIs_key(CollectConstants.EXLIMP_TABLE_STATUS_N);
										}
									} else if (sheets[x].getRow(2)[j].getContents().equals("�Ƿ�����")) {
										if(sheets[x].getRow(i)[j].getContents().trim()!=null&&sheets[x].getRow(i)[j].getContents().trim().equals("��")){
											vo.setIs_code_table(CollectConstants.EXLIMP_TABLE_STATUS_Y);
										}else{
											vo.setIs_code_table(CollectConstants.EXLIMP_TABLE_STATUS_N);
										}
									} else if (sheets[x].getRow(2)[j].getContents().equals("��Ӧ�����")) {
										temp_code_table = sheets[x].getRow(i)[j].getContents();
										sql_code_table = new StringBuffer();
										sql_code_table.append("select * from codedata t where codename='" + temp_code_table + "' and codetype = '��Դ����_��Ӧ�����'");
										System.out.println("��Ӧ�����sql=="+sql_code_table.toString());
										rwlist = daoTable.query(sql_code_table.toString());//dao.queryMethodList(sql.toString());
										if(rwlist.size() > 0){
											vo.setCode_table(rwlist.get(0).get("CODEVALUE").toString());
										}
//										vo.setCode_table(sheets[x].getRow(i)[j].getContents());
									} else if (sheets[x].getRow(2)[j].getContents().equals("����������")) {
										try{
											vo.setDataitem_long_desc(sheets[x].getRow(i)[j].getContents());
										}catch(Exception e){
											vo.setDataitem_long_desc("");
										}
										
									}
								}
								vo.setIs_markup(ExConstant.IS_MARKUP_Y);//��Ч���
								vo.setCreator_id(request.getRecord("oper-data").getValue("userID"));//������
								vo.setCreated_time(CalendarUtil.getCurrentDateTime());//����ʱ��
								vo.setLast_modify_id("");
								vo.setLast_modify_time("");
								
								String id = UuidGenerator.getUUID();
								
								vo.setCollect_dataitem_id(id);
								vo.setCollect_table_id(id2);
								vorct.setCollect_table_id(id2);
								vorct.setIs_markup(ExConstant.IS_MARKUP_Y);//��Ч���
								vorct.setCreator_id(request.getRecord("oper-data").getValue("userID"));//������
								vorct.setCreated_time(CalendarUtil.getCurrentDateTime());//����ʱ��
								vorct.setCj_ly(CollectConstants.TYPE_CJLY_OUT);//�ⲿ�ɼ�
								vorct.setIf_creat(CollectConstants.TYPE_IF_CREAT_NO);//δ����
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
								System.out.println("insertSql�����������RES_COLLECT_DATAITEM===" + insertSql);
									
								try{
									stmt.addSqlStmt(insertSql.toString());
								}catch (Exception e) {
									// TODO Auto-generated catch block
									throw new TxnDataException("error", "�����������ʧ��!");
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
							System.out.println("insertSql�������ݱ�===" + insertSql_rct);
							try{
								stmt.addSqlStmt(insertSql_rct.toString());
							}catch (Exception e) {
								// TODO Auto-generated catch block
								throw new TxnDataException("error", "�������ݱ�RES_COLLECT_TABLEʧ��!");
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new TxnDataException("error", "Excel�����ϸ�ʽ�淶!");
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
