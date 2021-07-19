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
import com.gwssi.common.util.JsonDataUtil;

public class PieDataCollect
{
	DBOperation	operation	= null;

	public PieDataCollect()
	{
		operation = DBOperationFactory.createOperation();
	}

	// ��־
	protected static Logger	logger	= TxnLogger.getLogger(IndexJob.class
											.getName());
	

	/**
	 * ��ҳ�ɼ��ֲ���ͼҳ�棬����ѡ����ȡ��ͬʱ��ε�����
	 * @param dateFrom
	 * @param dateTo
	 * @throws DBException
	 */
	public void setPieData(String dateFrom,String dateTo) throws DBException
	{
		ResourceBundle bundle = ResourceBundle.getBundle("index");
		String collect_share_path= bundle.getString("collect_share_path");
		String collect_table_path= bundle.getString("collect_table_path");
		String str_collect_fenbu;
		String str_collect_table;
		Map map=getPieData(dateFrom,dateTo);
		str_collect_fenbu = map.get("dataString").toString();
		str_collect_table = map.get("tableString").toString();
		//System.out.println("---"+collect_share_path+"----"+str_collect_fenbu);
		this.writeFile(collect_share_path, str_collect_fenbu);
		this.writeFile(collect_table_path, str_collect_table);
	}
	
	/**
	 * �ɼ��ֲ���ͼ����
	 * @throws DBException 
	 */
	private Map getPieData(String dateFrom,String dateTo) throws DBException{
		
		//��ͼ����
		StringBuffer dataString=new StringBuffer();
		
		List contentList = getColletSql(dateFrom,dateTo);
		int len=contentList.size();
		int inNum=0,outNum=0,qxNum=0;
		int intimes=0,outtimes=0,qxtimes=0;
		int inamount=0,outamount=0,qxamount=0;
		int taskNum[][]= new int[3][7];
		int intTtotal[] =new int[7];
		String data1="option.series[1].data=[";//��Ȧ
		String data0="option.series[0].data=[";//��Ȧ
		String tiptaskmap="";//����������ʾ����
		String tiptimesmap="";//�ɼ�������ʾ����
		String tipamountmap="";//�ɼ�����ʾ����
		
		for(int i=0;i<len;i++){
			//System.out.println("-------"+i);
			
			boolean isFirst=true;
			Map tmp = (Map)contentList.get(i);
			//System.out.println(tmp);
			String type =tmp.get("SERVICE_TARGETS_TYPE").toString();
			String name =tmp.get("SERVICE_TARGETS_NAME").toString();
			String id   =tmp.get("SERVICE_TARGETS_ID").toString();
			String t1   =tmp.get("T1").toString();
			String t2   =tmp.get("T2").toString();
			String t3   =tmp.get("T3").toString();
			String t4   =tmp.get("T4").toString();
			String t5   =tmp.get("T5").toString();
			String t6   =tmp.get("T6").toString();
			String t7   =tmp.get("T7").toString();
			String times=tmp.get("EXCTIMES").toString();
			String amount=tmp.get("AMOUNT").toString();
			int intT[]={Integer.parseInt(t1),Integer.parseInt(t2),
					Integer.parseInt(t3),Integer.parseInt(t4),
					Integer.parseInt(t5),Integer.parseInt(t6),
					Integer.parseInt(t7)};
			int total =intT[0]+intT[1]+intT[2]+intT[3]+intT[4]+intT[5]+intT[6];
			
			data1+=("{value:"+total+",name:'"+name+"',sid:'"+id+"'}");
			if(i!=(len-1)){
				data1+=",";
			}else {
				data1+="];";
			}
			tiptaskmap+="taskmap['"+name+"']='"+gettoolText(intT)+"';";
			tiptimesmap+="timesmap['"+name+"']='�ɼ�����:"+times+"��';";
			tipamountmap+="amountmap['"+name+"']='�ɼ�������:"+amount+"��';";
			if("000".equals(type)){
				qxNum++;
				for(int j=0;j<7;j++){
					taskNum[0][j]+=intT[j];
					intTtotal[j]+=intT[j];
				}				
				qxtimes+=Integer.parseInt(times);
				qxamount+=Integer.parseInt(amount);
			}else if("001".equals(type)){
				inNum++;
				for(int j=0;j<7;j++){
					taskNum[1][j]+=intT[j];
					intTtotal[j]+=intT[j];
				}
				intimes+=Integer.parseInt(times);
				inamount+=Integer.parseInt(amount);
			}else if("002".equals(type)){
				outNum++;
				for(int j=0;j<7;j++){
					taskNum[2][j]+=intT[j];
					intTtotal[j]+=intT[j];
				}
				outtimes+=Integer.parseInt(times);
				outamount+=Integer.parseInt(amount);
			}		
			
		}	
		
		int inTaskNum=0,outTaskNum=0,qxTaskNum=0;
		String inStr="";
		String outStr="";
		String qxStr="";
		for(int i=0;i<7;i++){
			inTaskNum+=taskNum[1][i];
			outTaskNum+=taskNum[2][i];
			qxTaskNum+=taskNum[0][i];
		}
		int xtNum=0;
		if(qxTaskNum!=0){//���ط־�
			xtNum++;
			data0+=("{value:"+qxTaskNum+",name:'���ط־�'}");
			if((inTaskNum+outTaskNum)>0){
				data0+=",";
			}else {
				data0+="];";
			}
			tiptaskmap+="taskmap['���ط־�']='��"+qxNum+"��ϵͳ<BR>"+gettoolText(taskNum[0])+"';";
			tiptimesmap+="timesmap['���ط־�']='�ɼ�����:"+qxtimes+"��';";
			tipamountmap+="amountmap['���ط־�']='�ɼ�������:"+qxamount+"��';";
		}
		if(inTaskNum!=0){//�ڲ�ϵͳ
			xtNum++;
			data0+=("{value:"+inTaskNum+",name:'�ڲ�ϵͳ'}");
			if(outTaskNum>0){
				data0+=",";
			}else {
				data0+="];";
			}
			tiptaskmap+="taskmap['�ڲ�ϵͳ']='��"+inNum+"���������<BR>"+gettoolText(taskNum[1])+"';";
			tiptimesmap+="timesmap['�ڲ�ϵͳ']='�ɼ�����:"+intimes+"��';";
			tipamountmap+="amountmap['�ڲ�ϵͳ']='�ɼ�������:"+inamount+"��';";	
		}
		if(outTaskNum!=0){//�ⲿϵͳ
			xtNum++;
			data0+=("{value:"+outTaskNum+",name:'�ⲿϵͳ'}];");
			tiptaskmap+="taskmap['�ⲿϵͳ']='��"+outNum+"���������<BR>"+gettoolText(taskNum[2])+"';";
			tiptimesmap+="timesmap['�ⲿϵͳ']='�ɼ�����:"+outtimes+"��';";
			tipamountmap+="amountmap['�ⲿϵͳ']='�ɼ�������:"+outamount+"��';";
		}
		dataString.append(data0).append(data1)
		.append(tiptaskmap).append(tiptimesmap).append(tipamountmap)
		.append("map=taskmap;");
		//System.out.println(dataString.toString());
		
		
		//ҳ��������
		Map tableMap1=new HashMap();
		tableMap1.put("typeNum", xtNum);
		tableMap1.put("targetsNum", len);
		tableMap1.put("WebService", intTtotal[0]);
		tableMap1.put("upload", intTtotal[1]);
		tableMap1.put("ftp", intTtotal[2]);
		tableMap1.put("database", intTtotal[3]);
		tableMap1.put("jms", intTtotal[4]);
		tableMap1.put("socket", intTtotal[5]);
		tableMap1.put("etl", intTtotal[6]);
		
		List tablecontent = getColletTableSql();
		
		List list =new ArrayList();
		list.add(tableMap1);
		list.add((Map)tablecontent.get(0));
		String str = JsonDataUtil.toJSONString(list);
		String tableString = "var table_obj="+str+";";
		
		Map map=new HashMap();
		map.put("dataString", dataString.toString());
		map.put("tableString",tableString );
		return map;
	}
	
	/**
	 * ƴ�ӱ�ͼ��ʾ����
	 */
	private String gettoolText(int []num){
		String toolText="";
		boolean isFirst=true;
		if(num[0]!=0){
			toolText+="WebService�ɼ�����"+num[0]+"��";
			isFirst=false;				
		}
		if(num[1]!=0){
			if(isFirst){
				toolText+="�ļ��ϴ��ɼ�����"+num[1]+"��";
				isFirst=false;
			}else{
				toolText+="<BR>�ļ��ϴ��ɼ�����"+num[1]+"��";
			}
		}
		if(num[2]!=0){
			if(isFirst){
				toolText+="FTP�ɼ�����"+num[2]+"��";
				isFirst=false;
			}else{
				toolText+="<BR>FTP�ɼ�����"+num[2]+"��";
			}
		}
		if(num[3]!=0){
			if(isFirst){
				toolText+="���ݿ�ɼ�����"+num[3]+"��";
				isFirst=false;
			}else{
				toolText+="<BR>���ݿ�ɼ�����"+num[3]+"��";
			}
		}
		if(num[4]!=0){
			if(isFirst){
				toolText+="JMS��Ϣ�ɼ�����"+num[4]+"��";
				isFirst=false;
			}else{
				toolText+="<BR>JMS��Ϣ�ɼ�����"+num[4]+"��";
			}
		}
		if(num[5]!=0){
			if(isFirst){
				toolText+="SOCKET�ɼ�����"+num[5]+"��";
				isFirst=false;
			}else{
				toolText+="<BR>SOCKET�ɼ�����"+num[5]+"��";
			}
		}
		if(num[6]!=0){
			if(isFirst){
				toolText+="ETL�ɼ�����"+num[6]+"��";
				isFirst=false;
			}else{
				toolText+="<BR>ETL�ɼ�����"+num[6]+"��";
			}
		}
		
		return toolText;
	}
	/**
	 * �ɼ��ֲ���ͼ����SQL    MLpie_l4 ����ͼ����
	 */
	private List getColletSql(String dateFrom,String dateTo) throws DBException
	{
		List collect = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select k.*,nvl(d.exctimes,0) exctimes,nvl(d.amount,0) amount from")
			.append("(select r.service_targets_id,r.service_targets_name,")
			.append("r.service_targets_type,r.show_order,")
			.append("count(case when c.collect_type = '00' then 1 end) t1,")
			.append("count(case when c.collect_type = '01' then 1 end) t2,")
			.append("count(case when c.collect_type = '02' then 1 end) t3,")
			.append("count(case when c.collect_type = '03' then 1 end) t4,")
			.append("count(case when c.collect_type = '04' then 1 end) t5,")
			.append("count(case when c.collect_type = '05' then 1 end) t6,")
			.append("count(case when c.collect_type = '06' then 1 end) t7")
			.append(" from view_collect_task c, res_service_targets r")
			.append(" where c.service_targets_id = r.service_targets_id")
			.append(" and r.is_markup = 'Y'")
			.append(" group by r.service_targets_id,r.service_targets_name,")
			.append(" r.service_targets_type,r.show_order) k,")
			.append(" (select j.service_targets_id,nvl(count(1), 0) as exctimes,")
			.append(" nvl(sum(j.collect_data_amount), 0) as amount")
			.append(" from collect_joumal j where j.is_formal = 'Y'");
		if(StringUtils.isNotBlank(dateFrom)&& StringUtils.isBlank(dateTo)){
			//��ʼʱ�����
			sql.append(" and j.task_start_time >='"+dateFrom+"'");
		}else if(StringUtils.isBlank(dateFrom) && StringUtils.isNotBlank(dateTo)){
			//����ʱ����ǰ
			sql.append(" and j.task_start_time <='"+dateTo+"'");			
		}else if(StringUtils.isNotBlank(dateFrom) && StringUtils.isNotBlank(dateTo)){
			//ʱ������
			sql.append(" and j.task_start_time >='"+dateFrom+"'")
			.append(" and j.task_start_time <='"+dateTo+"'");
		}else{
			//�������Ĭ�� �鵱��
			sql.append(" and j.task_start_time >=to_char(add_months(sysdate, 0), 'yyyy-MM') || '-01'")
				.append(" and j.task_start_time <to_char(add_months(sysdate, 1), 'yyyy-MM') || '-01'");
		}
		
			sql.append(" group by j.service_targets_id) d")
			.append(" where d.service_targets_id(+) = k.service_targets_id ")
			.append(" order by k.service_targets_type,k.show_order");
		           
		
		
		System.out.println("---piesql---"+sql.toString());
		collect = operation.select(sql.toString());

		return collect;
	}
	/**
	 * �ɼ��ֲ�table����SQL  MLpie_l4 �Ҳ�������
	 */
	private List getColletTableSql() throws DBException
	{
		List collect = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select c.curtimes,c.curamount,l.lasttimes,l.lastamount from")
	    .append(" (select 'type' as type ,nvl(count(1),0) as curtimes,nvl(sum(j.collect_data_amount),0) as curamount")
	    .append(" from collect_joumal j")
		.append(" where j.task_start_time>=to_char(add_months(sysdate, 0), 'yyyy-MM') || '-01' ")
		.append(" and  j.task_start_time < to_char(add_months(sysdate, 1), 'yyyy-MM') || '-01') c,")
		.append(" (select 'type' as type ,nvl(count(1),0) as lasttimes,nvl(sum(j.collect_data_amount),0) as lastamount")
        .append(" from collect_joumal j")
        .append(" where j.is_formal='Y' ")
        //.append(" and (j.return_codes='TAX0010') ")//��Ų�ȫ��������
        .append(" and j.task_start_time>=to_char(add_months(sysdate, -1), 'yyyy-MM') || '-01' ")
        .append(" and  j.task_start_time < to_char(add_months(sysdate, 0), 'yyyy-MM') || '-01') l")
        .append(" where c.type = l.type");
        
		//System.out.println("---tablesql---"+sql.toString());
		collect = operation.select(sql.toString());

		return collect;
	}
	
	  
	
	/**
	 * TopTable.jspҳ������ͼ����
	 * @throws DBException 
	 */
	public List getCollectSql2(String service_targets_id,String dateFrom,String dateTo) throws DBException
	{	
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from")
			.append(" (select t.service_targets_name from res_service_targets t")
			.append(" where t.service_targets_id = '"+ service_targets_id+"'),")
			.append(" (select t.collect_table_id,  r.table_name_cn,r.table_name_en ,nvl(sum(t.sum_record_amount)/10000,0)  amount,")
			.append(" nvl(sum(t.exec_count),0) exec_count")
			.append(" from COLLECT_LOG_STATISTICS t, res_collect_table r")
			.append(" where t.service_targets_id = '"+service_targets_id+"'")
			.append(" and t.collect_table_id=r.collect_table_id");
		if(StringUtils.isNotBlank(dateFrom)&& StringUtils.isBlank(dateTo)){
			//��ʼʱ�����
			sql.append(" and t.log_date >='"+dateFrom+"'");
		}else if(StringUtils.isBlank(dateFrom) && StringUtils.isNotBlank(dateTo)){
			//����ʱ����ǰ
			sql.append(" and t.log_date <='"+dateTo+"'");			
		}else if(StringUtils.isNotBlank(dateFrom) && StringUtils.isNotBlank(dateTo)){
			//ʱ������
			sql.append(" and t.log_date >='"+dateFrom+"'")
			.append(" and t.log_date <='"+dateTo+"'");
		}else{
			//�������Ĭ�� �鵱��
			sql.append(" and t.log_date >=to_char(add_months(sysdate, 0), 'yyyy-MM') || '-01'")
				.append(" and t.log_date <to_char(add_months(sysdate, 1), 'yyyy-MM') || '-01'");
		}
		sql.append(" group by t.collect_table_id, r.table_name_cn,r.table_name_en)");
		System.out.println("sql2pie----"+sql);
		//TopTable.jspҳ������ͼ����
		return operation.select(sql.toString());
       
		
	}
	
	/**
	 * TopTable.jspҳ���Ҳ�table����
	 */
	public List getCollectTableSql2(String service_targets_id) throws DBException
	{
		String sql2 = "select * from(select c.ctimes,c.camount,l.ltimes,l.lamount from"
				+" (select nvl(sum(t.exec_count),'0') ctimes,nvl(sum(sum_record_amount),'0') camount from COLLECT_LOG_STATISTICS t "
				+" where t.service_targets_id='"+service_targets_id+"'"
				+" and t.log_date>=to_char(add_months(sysdate, 0), 'yyyy-MM') || '-01'"
				+" and  t.log_date < to_char(add_months(sysdate, 1), 'yyyy-MM') || '-01') c,"
				+" (select nvl(sum(t.exec_count),'0') ltimes,nvl(sum(sum_record_amount),'0') lamount from COLLECT_LOG_STATISTICS t "
				+" where t.service_targets_id='"+service_targets_id+"'"
				+" and t.log_date>=to_char(add_months(sysdate, -1), 'yyyy-MM') || '-01'"
				+" and  t.log_date < to_char(add_months(sysdate, 0), 'yyyy-MM') || '-01') l),"
				+"(select count(case when c.collect_type='00' then 1 end) t1,"
				+" count(case when c.collect_type='01' then 1 end) t2,"
				+" count(case when c.collect_type='02' then 1 end) t3,"
		        +" count(case when c.collect_type='03' then 1 end) t4,"
		        +" count(case when c.collect_type='04' then 1 end) t5,"
		        +" count(case when c.collect_type='05' then 1 end) t6,"
		        +" count(case when c.collect_type='06' then 1 end) t7"
		        +" from view_collect_task c, res_service_targets r"
		        +" where c.service_targets_id = r.service_targets_id"
		        +" and c.service_targets_id='"+service_targets_id+"'"
		        +" and r.is_markup = 'Y'"
		        +" group by r.service_targets_id,r.service_targets_name,r.service_targets_type)";
		System.out.println("sql2table-----"+sql2);
		return operation.select(sql2);
	}
	   
	/**
	 * ��׼���õ������ļ�д�뵽ָ���ļ��� ��ʽΪJson�ļ�
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
	 * main(������һ�仰�����������������)    
	 *  
	 * @param args        
	 * void       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1    
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
