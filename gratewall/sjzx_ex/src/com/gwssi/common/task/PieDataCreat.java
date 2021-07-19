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

public class PieDataCreat
{
	DBOperation	operation	= null;

	public PieDataCreat()
	{
		operation = DBOperationFactory.createOperation();
	}

	// ��־
	protected static Logger	logger	= TxnLogger.getLogger(IndexJob.class
											.getName());
	
	
	/**
	 * 
	 * getTotalServiceNum(��ѯ���й���������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @return        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getTotalServiceNum()
	{
		try {
			List serviceObject = new ArrayList();
			String sql = " with a as (select t.service_targets_id,count(*) as total from share_service t where t.is_markup='Y' group by t.service_targets_id) "
					+ " select t.service_targets_id,t.service_targets_type,'/txn53000211.do?select-key:service_targets_id='||t.service_targets_id||'&select-key:count_type=00' as link3,case when t.service_targets_type ='000' then  replace(t.service_targets_name,'�־�','')  when t.service_targets_type ='001' then  t.service_targets_name when t.service_targets_type ='002' then  service_targets_name   end  name,nvl(a.total,'0') as total from res_service_targets t,a "
					+ " where a.service_targets_id(+) = t.service_targets_id and t.is_markup='Y'  order by t.service_targets_type,t.show_order";
			//System.out.println("getTotalServiceNum::sql="+sql);
			serviceObject = operation.select(sql);
			return serviceObject;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ������󱨴�");
			return null;
		}		
	}
	
	/**
	 * 
	 * getShareTotal(���ع���������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @return        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getShareTotal(){
		
		try {
			List shareTotal = new ArrayList();
			String sqlString = "with a as (select t.service_targets_id,count(*) as total from share_service t where t.is_markup='Y' group by t.service_targets_id)  "
							  +" select m.service_targets_type ,sum(m.total) as total from "	
							  +" (select t.service_targets_id,t.service_targets_type,t.service_targets_name as name,nvl(a.total,'0') as total from res_service_targets t,a  where a.service_targets_id(+) = t.service_targets_id and t.is_markup='Y' order by t.service_targets_type,t.show_order) "
							  +" m group by m.service_targets_type order by service_targets_type ";
			shareTotal = operation.select(sqlString);
			return shareTotal;
		} catch (DBException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * 
	 * getShareObjectStatisData_date(����ʱ�䷶Χ��ѯ�������ͳ������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param service_targets_id
	 * @param type
	 * @param start_time
	 * @param end_time
	 * @return        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getShareObjectStatisData_date(String service_targets_id,String type,String start_time,String end_time ){
		try {
			
			List serviceObject = new ArrayList();
			String sortColumn = "";
			if("00".equals(type)){
				sortColumn="share_amount desc";
			}else {
				sortColumn="count desc";
			}
			StringBuffer  sql  = new StringBuffer("");
			sql.append("   with a as ( select t.service_id,t.service_name,t.service_type,t.is_markup,t1.service_targets_name,t1.service_targets_type from share_service t,res_service_targets t1 where t1.service_targets_id=t.service_targets_id and t.service_targets_id='"+service_targets_id+"') "
					+ " select * from ( select a.service_targets_type,a.service_targets_name,a.service_name as name,a.service_type,a.service_id,nvl(m.count,'0') as count,nvl(m.share_amount,'0') as share_amount from ( "
					+ " select t.service_id, sum(t.exec_count) as count,sum(t.sum_record_amount) as share_amount from share_log_statistics t where  t.service_targets_id='"+service_targets_id+"'");
			if(start_time!=null&&!"".equals(start_time)){
				sql.append(" and t.log_date>='"+start_time+"'");
			}
			if(end_time!=null&&!"".equals(end_time)){
				sql.append(" and t.log_date<='"+end_time+"'");
			}
			sql.append(" group by t.service_targets_id,t.service_id )m,a where a.service_id = m.service_id(+) and a.is_markup='Y' ) order by "+sortColumn);
			/*String sql = "   with a as ( select t.service_id,t.service_name,t.service_type,t.is_markup,t1.service_targets_name,t1.service_targets_type from share_service t,res_service_targets t1 where t1.service_targets_id=t.service_targets_id and t.service_targets_id='"+service_targets_id+"') "
					+ " select * from ( select a.service_targets_type,a.service_targets_name,a.service_name as name,a.service_type,a.service_id,nvl(m.count,'0') as count,nvl(m.share_amount,'0') as share_amount from ( "
					+ " select t.service_id, sum(t.exec_count) as count,sum(t.sum_record_amount) as share_amount from share_log_statistics t where  t.service_targets_id='"+service_targets_id+"' and  substr(t.log_date,0,7)= to_char(sysdate-30,'yyyy-mm')  group by t.service_targets_id,t.service_id  "
					+ " )m,a where a.service_id = m.service_id(+) and a.is_markup='Y' ) order by "+sortColumn;*/
			
			serviceObject = operation.select(sql.toString());
			return serviceObject;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ������󱨴�");
			return null;
		}	
	}
	
	/**
	 * 
	 * getShareObjectStatisData(��ȡ�����������ͳ������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param service_targets_type
	 * @return        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getShareObjectStatisData(String service_targets_id,String type ){
		try {
			
			List serviceObject = new ArrayList();
			String sortColumn = "";
			if("00".equals(type)){
				sortColumn="share_amount desc";
			}else {
				sortColumn="count desc";
			}
			String sql = "   with a as ( select t.service_id,t.service_name,t.service_type,t.is_markup,t1.service_targets_name,t1.service_targets_type from share_service t,res_service_targets t1 where t1.service_targets_id=t.service_targets_id and t.service_targets_id='"+service_targets_id+"') "
					+ " select * from ( select a.service_targets_type,a.service_targets_name,a.service_name as name,a.service_type,a.service_id,nvl(m.count,'0') as count,nvl(m.share_amount,'0') as share_amount from ( "
					+ " select t.service_id, sum(t.exec_count) as count,sum(t.sum_record_amount) as share_amount from share_log_statistics t where  t.service_targets_id='"+service_targets_id+"' and  substr(t.log_date,0,7)= to_char(sysdate-30,'yyyy-mm')  group by t.service_targets_id,t.service_id  "
					+ " )m,a where a.service_id = m.service_id(+) and a.is_markup='Y' ) order by "+sortColumn;
			
			serviceObject = operation.select(sql);
			return serviceObject;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ������󱨴�");
			return null;
		}	
	}
	
	/**
	 * 
	 * getServiceNum_date(ͳ��ĳһʱ����ڵķ�������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param service_targets_type
	 * @param count_type
	 * @return        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getServiceNum_date(String service_targets_type,String count_type,String start_time,String end_time)
	{
		try {
			
			List serviceObject = new ArrayList();
			StringBuffer sql=new StringBuffer("");
			if ("01".equals(count_type)){//����������
				sql.append( "  with a as (select t.service_targets_id,count(*) as total from share_service t where t.is_markup='Y' group by t.service_targets_id)   "
					+"  select t.*, nvl(t1.total,'0') as total from ( select t.service_targets_id,t.service_targets_type,'/txn53000211.do?select-key:service_targets_id='||t.service_targets_id||'&select-key:count_type=00' as link3,"
					+"   t.service_targets_name as name from res_service_targets t,a where a.service_targets_id(+) = t.service_targets_id and t.is_markup='Y' and t.service_targets_type='"+service_targets_type+"' ) t, "
					+"  ( select a.service_targets_id, sum(a2.sum_record_amount) as total from a,res_service_targets a1,share_log_statistics a2 where a.service_targets_id = a1.service_targets_id and a1.service_targets_id = a2.service_targets_id  "
					+"  and a1.service_targets_type='"+service_targets_type+"' and a1.is_markup='Y'");
					/*and a2.log_date >=to_char(add_months(sysdate, 0), 'yyyy-MM') || '-01' and a2.log_date <to_char(add_months(sysdate, 1), 'yyyy-MM') || '-01'   group by a.service_targets_id )t1 "
					+"   where t.service_targets_id = t1.service_targets_id(+) order by 5 desc ");*/
				if(start_time!=null&&!"".equals(start_time)){
					sql.append(" and a2.log_date >='"+start_time+"'");
				}
				if(end_time!=null&&!"".equals(end_time)){
					sql.append(" and a2.log_date<='"+end_time+"'");
				}
				sql.append(" group by a.service_targets_id )t1   where t.service_targets_id = t1.service_targets_id(+) order by 5 desc ");
			}else if("02".equals(count_type)){//�������
				sql.append( "  with a as (select t.service_targets_id,count(*) as total from share_service t where t.is_markup='Y' group by t.service_targets_id)   "
					+"  select t.*, nvl(t1.total,'0') as total from ( select t.service_targets_id,t.service_targets_type,'/txn53000211.do?select-key:service_targets_id='||t.service_targets_id||'&select-key:count_type=00' as link3,"
					+"   t.service_targets_name as name from res_service_targets t,a where a.service_targets_id(+) = t.service_targets_id and t.is_markup='Y' and t.service_targets_type='"+service_targets_type+"' ) t, "
					+"  ( select a.service_targets_id, sum(a2.exec_count) as total from a,res_service_targets a1,share_log_statistics a2 where a.service_targets_id = a1.service_targets_id and a1.service_targets_id = a2.service_targets_id  "
					+"  and a1.service_targets_type='"+service_targets_type+"' and a1.is_markup='Y'");
				/*and a2.log_date >=to_char(add_months(sysdate, 0), 'yyyy-MM') || '-01' and a2.log_date <to_char(add_months(sysdate, 1), 'yyyy-MM') || '-01'   group by a.service_targets_id )t1 "
					+"   where t.service_targets_id = t1.service_targets_id(+) order by 5 desc ");*/
				
				if(start_time!=null&&!"".equals(start_time)){
					sql.append(" and a2.log_date >='"+start_time+"'");
				}
				if(end_time!=null&&!"".equals(end_time)){
					sql.append(" and a2.log_date<='"+end_time+"'");
				}
				sql.append("   group by a.service_targets_id )t1  where t.service_targets_id = t1.service_targets_id(+) order by 5 desc ");
				
			}else{//�����������
				sql.append( " with a as (select t.service_targets_id,count(*) as total from share_service t where t.is_markup='Y' group by t.service_targets_id) "
					+ " select t.service_targets_id,t.service_targets_type,'/txn53000211.do?select-key:service_targets_id='||t.service_targets_id||'&select-key:count_type=00' as link3,t.service_targets_name as name,nvl(a.total,'0') as total from res_service_targets t,a "
					+ " where a.service_targets_id(+) = t.service_targets_id and t.is_markup='Y' and t.service_targets_type='"+service_targets_type+"' order by 5 desc");
			}
			
			System.out.println("ʱ�亯��@@@@����������ͣ�"+service_targets_type+"getServiceNum::sql="+sql.toString());
			serviceObject = operation.select(sql.toString());
			return serviceObject;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ������󱨴�");
			return null;
		}		
	}
	
	/**
	 * 
	 * getShareTotalByType(���ݷ���������Ͳ�ѯ�������������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @return        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getShareTotalByType(String type){
		
		try {
			List shareTotal = new ArrayList();
			String sqlString = "with a as (select t.service_targets_id,count(*) as total from share_service t where t.is_markup='Y' group by t.service_targets_id)  "
							  +" select m.service_targets_type ,sum(m.total) as total from "	
							  +" (select t.service_targets_id,t.service_targets_type,t.service_targets_name as name,nvl(a.total,'0') as total from res_service_targets t,a  where a.service_targets_id(+) = t.service_targets_id and t.service_targets_type='"+type+"' and t.is_markup='Y'  order by t.service_targets_type,t.show_order) "
							  +" m group by m.service_targets_type order by service_targets_type ";
			shareTotal = operation.select(sqlString);
			return shareTotal;
		} catch (DBException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * 
	 * getServiceNum(���ݷ�������ȡ�������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @return
	 * @throws DBException        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getServiceNum(String service_targets_type,String count_type)
	{
		try {
			
			List serviceObject = new ArrayList();
			String sql="";
			if ("01".equals(count_type)){//����������
				sql = "  with a as (select t.service_targets_id,count(*) as total from share_service t where t.is_markup='Y' group by t.service_targets_id)   "
					+"  select t.*, nvl(t1.total,'0') as total from ( select t.service_targets_id,t.service_targets_type,'/txn53000211.do?select-key:service_targets_id='||t.service_targets_id||'&select-key:count_type=00' as link3,"
					+"   t.service_targets_name as name from res_service_targets t,a where a.service_targets_id(+) = t.service_targets_id and t.is_markup='Y' and t.service_targets_type='"+service_targets_type+"' ) t, "
					+"  ( select a.service_targets_id, sum(a2.sum_record_amount) as total from a,res_service_targets a1,share_log_statistics a2 where a.service_targets_id = a1.service_targets_id and a1.service_targets_id = a2.service_targets_id  "
					+"  and a1.service_targets_type='"+service_targets_type+"' and a1.is_markup='Y'  and a2.log_date >=to_char(add_months(sysdate, 0), 'yyyy-MM') || '-01' and a2.log_date <to_char(add_months(sysdate, 1), 'yyyy-MM') || '-01'   group by a.service_targets_id )t1 "
					+"   where t.service_targets_id = t1.service_targets_id(+) order by 5 desc ";
			}else if("02".equals(count_type)){//�������
				sql = "  with a as (select t.service_targets_id,count(*) as total from share_service t where t.is_markup='Y' group by t.service_targets_id)   "
					+"  select t.*, nvl(t1.total,'0') as total from ( select t.service_targets_id,t.service_targets_type,'/txn53000211.do?select-key:service_targets_id='||t.service_targets_id||'&select-key:count_type=00' as link3,"
					+"   t.service_targets_name as name from res_service_targets t,a where a.service_targets_id(+) = t.service_targets_id and t.is_markup='Y' and t.service_targets_type='"+service_targets_type+"' ) t, "
					+"  ( select a.service_targets_id, sum(a2.exec_count) as total from a,res_service_targets a1,share_log_statistics a2 where a.service_targets_id = a1.service_targets_id and a1.service_targets_id = a2.service_targets_id  "
					+"  and a1.service_targets_type='"+service_targets_type+"' and a1.is_markup='Y'  and a2.log_date >=to_char(add_months(sysdate, 0), 'yyyy-MM') || '-01' and a2.log_date <to_char(add_months(sysdate, 1), 'yyyy-MM') || '-01'   group by a.service_targets_id )t1 "
					+"   where t.service_targets_id = t1.service_targets_id(+) order by 5 desc ";
			}else{//�����������
				sql = " with a as (select t.service_targets_id,count(*) as total from share_service t where t.is_markup='Y' group by t.service_targets_id) "
					+ " select t.service_targets_id,t.service_targets_type,'/txn53000211.do?select-key:service_targets_id='||t.service_targets_id||'&select-key:count_type=00' as link3,t.service_targets_name as name,nvl(a.total,'0') as total from res_service_targets t,a "
					+ " where a.service_targets_id(+) = t.service_targets_id and t.is_markup='Y' and t.service_targets_type='"+service_targets_type+"' order by 5 desc";
			}
			
			System.out.println("@@@@����������ͣ�"+service_targets_type+"getServiceNum::sql="+sql);
			serviceObject = operation.select(sql);
			return serviceObject;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ������󱨴�");
			return null;
		}		
	}
	
	/**
	 * 
	 * getObjectNum(��ȡ�����������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @return        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getObjectNum()
	{
		try {
			List serviceObject = new ArrayList();
			String sql = " select count(*) as total_num from res_service_targets t where t.is_markup='Y' ";
			//System.out.println("getTotalServiceNum::sql="+sql);
			serviceObject = operation.select(sql);
			return serviceObject;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ������󱨴�");
			return null;
		}		
	}
	
	/**
	 * 
	 * getObjectNum2(��ȡĳ�����͵ķ����������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @return        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getObjectNum2(String type)
	{
		try {
			List serviceObject = new ArrayList();
			String sql = " select count(*) as total_num from res_service_targets t where t.is_markup='Y' and t.service_targets_type='"+type+"'";
			//System.out.println("getTotalServiceNum::sql="+sql);
			serviceObject = operation.select(sql);
			return serviceObject;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ������󱨴�");
			return null;
		}		
	}
	
	/**
	 * 
	 * getServiceNum(��ȡ����������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @return        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getServiceNum()
	{
		try {
			List serviceObject = new ArrayList();
			String sql = " with a as (select t.service_targets_id,count(*) as total from share_service t where t.is_markup='Y' group by t.service_targets_id) "
						+" select sum(m.total) as total_num from (select t.service_targets_id,t.service_targets_type,t.service_targets_name as name,nvl(a.total,'0') as total "
						+" from res_service_targets t,a  where a.service_targets_id(+) = t.service_targets_id and t.is_markup='Y' order by t.service_targets_type,t.show_order)  m ";
			//System.out.println("getServiceNum::sql="+sql);
			serviceObject = operation.select(sql);
			return serviceObject;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ������󱨴�");
			return null;
		}		
	}
	
	
	
	/**
	 * 
	 * getServiceNum2(������һ�仰�����������������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @return        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getServiceNum2(String type)
	{
		try {
			List serviceObject = new ArrayList();
			String sql = " with a as (select t.service_targets_id,count(*) as total from share_service t where t.is_markup='Y' group by t.service_targets_id) "
						+" select sum(m.total) as total_num from (select t.service_targets_id,t.service_targets_type,t.service_targets_name as name,nvl(a.total,'0') as total "
						+" from res_service_targets t,a  where a.service_targets_id(+) = t.service_targets_id and t.is_markup='Y' and t.service_targets_type='"+type+"' order by t.service_targets_type,t.show_order)  m ";
			//System.out.println("getServiceNum::sql="+sql);
			serviceObject = operation.select(sql);
			return serviceObject;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ������󱨴�");
			return null;
		}		
	}
	
	/**
	 * ���ݷ�������ѯ�������
	 * getServiceNum3(������һ�仰�����������������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param id
	 * @return        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getServiceNum3(String id)
	{
		try {
			List serviceObject = new ArrayList();
			String sql = " with a as (select t.service_targets_id,count(*) as total from share_service t where t.is_markup='Y' and t.service_targets_id='"+id+"' group by t.service_targets_id) "
						+" select sum(m.total) as total_num from (select t.service_targets_id,t.service_targets_type,t.service_targets_name as name,nvl(a.total,'0') as total "
						+" from res_service_targets t,a  where a.service_targets_id(+) = t.service_targets_id and t.is_markup='Y' order by t.service_targets_type,t.show_order)  m ";
			//System.out.println("getServiceNum::sql="+sql);
			serviceObject = operation.select(sql);
			return serviceObject;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ������󱨴�");
			return null;
		}		
	}
	
	/**
	 * 
	 * getShareServiceTypeNum(��ȡ����������:webService,���ݿ⡭��)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @return        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getShareServiceTypeNum(){
		try {
			List serviceObject = new ArrayList();
			String sql = " select m.codename as name ,this_share_total,last_share_total from  "
						+" ( select m1.codename,nvl(m.share_total,'0') as last_share_total from ( "
						+" select t.SERVICE_TYPE,sum(t1.share_total) as share_total from SHARE_SERVICE t, "
						+" (select service_targets_id,service_id,sum(exec_count) as exec_counts ,sum(a1.sum_record_amount) as share_total from "
						+" share_log_statistics a1 where substr(a1.log_date,0,7)=to_char(trunc(trunc(add_months(sysdate,-1),'mm'),'day')+7,'yyyy-mm') group by service_targets_id,service_id  order by service_targets_id,service_id) "
						+"  t1 where t.service_id = t1.service_id group by SERVICE_TYPE ) m, "
						+" ( SELECT * FROM CODEDATA m1 where m1.codetype='��Դ����_����Դ����' ) m1 where m1.codevalue=m.service_type(+) order by m1.codevalue ) m,"
						+" (select m1.codename,nvl(m.share_total,'0') as this_share_total from ( "
						+" select t.SERVICE_TYPE,sum(t1.share_total) as share_total from SHARE_SERVICE t, "
						+" (select service_targets_id,service_id,sum(exec_count) as exec_counts ,sum(a1.sum_record_amount) as share_total from share_log_statistics a1 "
						+" where  a1.log_date >=to_char(add_months(sysdate, 0), 'yyyy-MM') || '-01' and a1.log_date <to_char(add_months(sysdate, 1), 'yyyy-MM') || '-01' group by service_targets_id,service_id  order by service_targets_id,service_id) "
						+" t1 where t.service_id = t1.service_id group by SERVICE_TYPE ) m,"
						+" ( SELECT * FROM CODEDATA m1 where m1.codetype='��Դ����_����Դ����' ) m1 where m1.codevalue=m.service_type(+) order by m1.codevalue )m1 "
						+"  where m.codename = m1.codename ";
			//System.out.println("getServiceNum::sql="+sql);
			serviceObject = operation.select(sql);
			return serviceObject;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ������󱨴�");
			return null;
		}	
	}
	/**
	 * 
	 * getShareServiceTypeNumForSingle(��ȡ����������:����������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @return        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getShareServiceTypeNumForSingle(){
		try {
			List serviceObject = new ArrayList();
			String sql = " select t.codename as name,nvl(t.this_share_total,'0') as this_share_total ,nvl(t1.last_share_total,'0') as last_share_total from  "
						+" (select t1.codevalue,t1.codename,t.share_total as this_share_total from ( select is_single,sum(share_total) as share_total from ( "
						+" select t1.*,t.is_single from SHARE_SERVICE t, (select service_targets_id,service_id,sum(exec_count) as exec_counts ,sum(a1.sum_record_amount) as share_total  "
						+" from share_log_statistics a1 where a1.log_date>=to_char(add_months(sysdate, 0), 'yyyy-MM') || '-01' and  a1.log_date < to_char(add_months(sysdate, 1), 'yyyy-MM') || '-01'  group by service_targets_id,service_id  order by service_targets_id,service_id) "
						+" t1 where t.service_id = t1.service_id  )m  group by is_single) t,(SELECT * FROM CODEDATA m1 where m1.codetype='�Ƿ񵥻�') t1 where t.is_single(+) = t1.codevalue ) t, "
						+"  (select t1.codevalue,t1.codename,t.share_total as last_share_total from ( select is_single,sum(share_total) as share_total from ( "
						+" select t1.*,t.is_single from SHARE_SERVICE t,(select service_targets_id,service_id,sum(exec_count) as exec_counts ,sum(a1.sum_record_amount) as share_total  "
						+" from share_log_statistics a1 where a1.log_date>=to_char(add_months(sysdate, -1), 'yyyy-MM') || '-01' and  a1.log_date < to_char(add_months(sysdate, 0), 'yyyy-MM') || '-01' group by service_targets_id,service_id   "
						+" order by service_targets_id,service_id) t1 where t.service_id = t1.service_id )m  group by is_single) t,(SELECT * FROM CODEDATA m1 where m1.codetype='�Ƿ񵥻�') t1 "
						+" where t.is_single(+) = t1.codevalue ) t1  where t.codevalue = t1.codevalue ";
			//System.out.println("getServiceNum::sql="+sql);
			serviceObject = operation.select(sql);
			return serviceObject;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ������󱨴�");
			return null;
		}	
	}
	
	/**
	 * 
	 * getShareServiceTypeNumForSingle2(������һ�仰�����������������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @return        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getShareServiceTypeNumForSingle2(String type){
		try {
			List serviceObject = new ArrayList();
			String sql = " select t.codename as name,nvl(t.this_share_total,'0') as this_share_total ,nvl(t1.last_share_total,'0') as last_share_total from  "
						+" (select t1.codevalue,t1.codename,t.share_total as this_share_total from ( select is_single,sum(share_total) as share_total from ( "
						+" select t1.*,t.is_single from (select t.* from res_service_targets t1,share_service t where t.service_targets_id = t1.service_targets_id and t1.service_targets_type='"+type+"') t, (select service_targets_id,service_id,sum(exec_count) as exec_counts ,sum(a1.sum_record_amount) as share_total  "
						+" from share_log_statistics a1 where  a1.log_date>=to_char(add_months(sysdate, 0), 'yyyy-MM') || '-01' and  a1.log_date < to_char(add_months(sysdate, 1), 'yyyy-MM') || '-01' group by service_targets_id,service_id  order by service_targets_id,service_id) "
						+" t1 where t.service_id = t1.service_id  )m  group by is_single) t,(SELECT * FROM CODEDATA m1 where m1.codetype='�Ƿ񵥻�') t1 where t.is_single(+) = t1.codevalue ) t, "
						+"  (select t1.codevalue,t1.codename,t.share_total as last_share_total from ( select is_single,sum(share_total) as share_total from ( "
						+" select t1.*,t.is_single from (select t.* from res_service_targets t1,share_service t where t.service_targets_id = t1.service_targets_id and t1.service_targets_type='"+type+"') t,(select service_targets_id,service_id,sum(exec_count) as exec_counts ,sum(a1.sum_record_amount) as share_total  "
						+" from share_log_statistics a1 where a1.log_date>=to_char(add_months(sysdate, -1), 'yyyy-MM') || '-01' and  a1.log_date < to_char(add_months(sysdate, 0), 'yyyy-MM') || '-01' group by service_targets_id,service_id   "
						+" order by service_targets_id,service_id) t1 where t.service_id = t1.service_id )m  group by is_single) t,(SELECT * FROM CODEDATA m1 where m1.codetype='�Ƿ񵥻�') t1 "
						+" where t.is_single(+) = t1.codevalue ) t1  where t.codevalue = t1.codevalue ";
			//System.out.println("getServiceNum::sql="+sql);
			serviceObject = operation.select(sql);
			return serviceObject;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ������󱨴�");
			return null;
		}	
	}
	
	/**
	 * 
	 * getShareServiceTypeNumForSingle3(������һ�仰�����������������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param id
	 * @return        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getShareServiceTypeNumForSingle3(String id){
		try {
			List serviceObject = new ArrayList();
			String sql = " select t.codename as name,nvl(t.this_share_total,'0') as this_share_total ,nvl(t1.last_share_total,'0') as last_share_total from  "
						+" (select t1.codevalue,t1.codename,t.share_total as this_share_total from ( select is_single,sum(share_total) as share_total from ( "
						+" select t1.*,t.is_single from (select * from share_service where service_targets_id='"+id+"') t, (select service_targets_id,service_id,sum(exec_count) as exec_counts ,sum(a1.sum_record_amount) as share_total  "
						+" from share_log_statistics a1 where  a1.log_date>=to_char(add_months(sysdate, 0), 'yyyy-MM') || '-01' and  a1.log_date < to_char(add_months(sysdate, 1), 'yyyy-MM') || '-01' group by service_targets_id,service_id  order by service_targets_id,service_id) "
						+" t1 where t.service_id = t1.service_id  )m  group by is_single) t,(SELECT * FROM CODEDATA m1 where m1.codetype='�Ƿ񵥻�') t1 where t.is_single(+) = t1.codevalue ) t, "
						+"  (select t1.codevalue,t1.codename,t.share_total as last_share_total from ( select is_single,sum(share_total) as share_total from ( "
						+" select t1.*,t.is_single from (select * from share_service where service_targets_id='"+id+"') t,(select service_targets_id,service_id,sum(exec_count) as exec_counts ,sum(a1.sum_record_amount) as share_total  "
						+" from share_log_statistics a1 where a1.log_date>=to_char(add_months(sysdate, -1), 'yyyy-MM') || '-01' and  a1.log_date < to_char(add_months(sysdate, 0), 'yyyy-MM') || '-01' group by service_targets_id,service_id   "
						+" order by service_targets_id,service_id) t1 where t.service_id = t1.service_id )m  group by is_single) t,(SELECT * FROM CODEDATA m1 where m1.codetype='�Ƿ񵥻�') t1 "
						+" where t.is_single(+) = t1.codevalue ) t1  where t.codevalue = t1.codevalue ";
			//System.out.println("getServiceNum::sql="+sql);
			serviceObject = operation.select(sql);
			return serviceObject;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ������󱨴�");
			return null;
		}	
	}
	
	/**
	 * 
	 * getShareServiceStatue(��ȡ����������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @return        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getShareServiceState(){
		try {
			List serviceObject = new ArrayList();
			String sql = "  select t1.type,t.this_exec_counts,t.this_share_total,t1.last_exec_counts,t1.last_share_total from   "
						+" (select 'type' as type ,nvl(sum(exec_count),'0') as this_exec_counts ,nvl(sum(a1.sum_record_amount),'0') as this_share_total "
						+"  from share_log_statistics a1 where  a1.log_date>=to_char(add_months(sysdate, 0), 'yyyy-MM') || '-01' and  a1.log_date < to_char(add_months(sysdate, 1), 'yyyy-MM') || '-01' ) t, "
						+"  (select 'type' as type, nvl(sum(exec_count),'0') as last_exec_counts ,nvl(sum(a1.sum_record_amount),'0') as last_share_total "
						+"  from share_log_statistics a1 where a1.log_date>=to_char(add_months(sysdate, -1), 'yyyy-MM') || '-01' and  a1.log_date < to_char(add_months(sysdate, 0), 'yyyy-MM') || '-01' ) t1 "
						+"  where t.type = t1.type ";
			//System.out.println("getServiceNum::sql="+sql);
			serviceObject = operation.select(sql);
			return serviceObject;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ������󱨴�");
			return null;
		}	
	}
	
	/**
	 * 
	 * getShareServiceState2(������һ�仰�����������������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @return        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getShareServiceState2(String type){
		try {
			List serviceObject = new ArrayList();
			String sql = "  select t1.type,t.this_exec_counts,t.this_share_total,t1.last_exec_counts,t1.last_share_total from   "
						+" (select 'type' as type ,nvl(sum(exec_count),'0') as this_exec_counts ,nvl(sum(a1.sum_record_amount),'0') as this_share_total "
						+"  from share_log_statistics a1 ,res_service_targets a2 where a1.log_date>=to_char(add_months(sysdate, 0), 'yyyy-MM') || '-01' and  a1.log_date < to_char(add_months(sysdate, 1), 'yyyy-MM') || '-01' and a1.service_targets_id = a2.service_targets_id and a2.service_targets_type='"+type+"' ) t, "
						+"  (select 'type' as type, nvl(sum(exec_count),'0') as last_exec_counts ,nvl(sum(a1.sum_record_amount),'0') as last_share_total "
						+"  from share_log_statistics a1 ,res_service_targets a2 where a1.log_date>=to_char(add_months(sysdate, -1), 'yyyy-MM') || '-01' and  a1.log_date < to_char(add_months(sysdate, 0), 'yyyy-MM') || '-01' and a1.service_targets_id = a2.service_targets_id and a2.service_targets_type='"+type+"'  ) t1 "
						+"  where t.type = t1.type ";
			//System.out.println("getServiceNum::sql="+sql);
			serviceObject = operation.select(sql);
			return serviceObject;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ������󱨴�");
			return null;
		}	
	}
	
	/**
	 * 
	 * getShareServiceState2ForNew(����������������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param type
	 * @return        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getShareServiceState2ForNew(String type){
		try {
			List serviceObject = new ArrayList();
			String sql = "  select year,month,nvl(sum(exec_count), '0') as this_exec_counts,nvl(sum(s.sum_record_amount), '0') as this_share_total,   "
						+" sum(case when ss.is_single=0 then s.sum_record_amount end) s1,sum(case when ss.is_single=1 then s.sum_record_amount end) s2 "
						+"  from share_log_statistics s, res_service_targets r,share_service ss where s.service_targets_id = r.service_targets_id "
						+"  and r.service_targets_type = '"+type+"' and s.service_id=ss.service_id and s.log_date >= to_char(add_months(sysdate, -1), 'yyyy-MM') || '-01'  "
						+"  and s.log_date < to_char(add_months(sysdate, 1), 'yyyy-MM') || '-01'  group by year, month ";
			//System.out.println("getServiceNum::sql="+sql);
			serviceObject = operation.select(sql);
			return serviceObject;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ������󱨴�");
			return null;
		}	
	}
	
	/**
	 * 
	 * getShareServiceState3(������һ�仰�����������������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @return        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getShareServiceState3(String id){
		try {
			List serviceObject = new ArrayList();
			String sql = "  select t1.type,t.this_exec_counts,t.this_share_total,t1.last_exec_counts,t1.last_share_total from   "
						+" (select 'type' as type ,nvl(sum(exec_count),'0') as this_exec_counts ,nvl(sum(a1.sum_record_amount),'0') as this_share_total "
						+"  from share_log_statistics a1 where a1.log_date>=to_char(add_months(sysdate, 0), 'yyyy-MM') || '-01' and  a1.log_date < to_char(add_months(sysdate, 1), 'yyyy-MM') || '-01' and a1.service_targets_id='"+id+"' ) t, "
						+"  (select 'type' as type, nvl(sum(exec_count),'0') as last_exec_counts ,nvl(sum(a1.sum_record_amount),'0') as last_share_total "
						+"  from share_log_statistics a1 where a1.log_date>=to_char(add_months(sysdate, -1), 'yyyy-MM') || '-01' and  a1.log_date < to_char(add_months(sysdate, 0), 'yyyy-MM') || '-01' and a1.service_targets_id='"+id+"' ) t1 "
						+"  where t.type = t1.type ";
			//System.out.println("getServiceNum::sql="+sql);
			serviceObject = operation.select(sql);
			return serviceObject;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ������󱨴�");
			return null;
		}	
	}
	
	/**
	 * 
	 * getServiceType(��ȡ���������������:webService�����ݿ⡭��)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @return        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getServiceType()
	{
		try {
			List serviceObject = new ArrayList();
			String sql = " with a as (select t1.service_targets_id from res_service_targets t1 where t1.is_markup = 'Y') "
						+" select t1.codename as name, nvl(t.total,'0') as total from ( select service_type,count(*) as total from share_service t,a where a.service_targets_id = t.service_targets_id and t.is_markup = 'Y' group by service_type)t, "
						+" ( SELECT * FROM CODEDATA m1 where m1.codetype='��Դ����_����Դ����' ) t1 where t.service_type(+) = t1.codevalue order by codevalue ";
			//System.out.println("getServiceNum::sql="+sql);
			serviceObject = operation.select(sql);
			return serviceObject;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ������󱨴�");
			return null;
		}		
	}
	
	/**
	 * 
	 * getServiceType2(������һ�仰�����������������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @return        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getServiceType2(String type)
	{
		try {
			List serviceObject = new ArrayList();
			String sql = " with a as (select t1.service_targets_id from res_service_targets t1 where t1.is_markup = 'Y' and t1.service_targets_type='"+type+"') "
						+" select t1.codename as name, nvl(t.total,'0') as total from ( select service_type,count(*) as total from share_service t,a where a.service_targets_id = t.service_targets_id and t.is_markup = 'Y' group by service_type)t, "
						+" ( SELECT * FROM CODEDATA m1 where m1.codetype='��Դ����_����Դ����' ) t1 where t.service_type(+) = t1.codevalue order by codevalue ";
			//System.out.println("getServiceNum::sql="+sql);
			serviceObject = operation.select(sql);
			return serviceObject;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ������󱨴�");
			return null;
		}		
	}
	
	/**
	 * 
	 * getServiceType3(������һ�仰�����������������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param id
	 * @return        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getServiceType3(String id)
	{
		try {
			List serviceObject = new ArrayList();
			String sql = " with a as (select t1.service_targets_id from res_service_targets t1 where t1.is_markup = 'Y' and t1.service_targets_id='"+id+"') "
						+" select t1.codename as name, nvl(t.total,'0') as total from ( select service_type,count(*) as total from share_service t,a where a.service_targets_id = t.service_targets_id and t.is_markup = 'Y' group by service_type)t, "
						+" ( SELECT * FROM CODEDATA m1 where m1.codetype='��Դ����_����Դ����' ) t1 where t.service_type(+) = t1.codevalue order by codevalue ";
			//System.out.println("@@@@@getServiceType3::sql="+sql);
			serviceObject = operation.select(sql);
			return serviceObject;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ������󱨴�");
			return null;
		}		
	}
	
	/**
	 * 
	 * getServiceTypeForSingle(��ȡ�����������������������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @return        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getServiceTypeForSingle()
	{
		try {
			List serviceObject = new ArrayList();
			String sql = "  with a as (select t1.service_targets_id from res_service_targets t1 where t1.is_markup = 'Y') "
						+" select t1.codename as name, t.total from ( select is_single,count(*) as total from share_service t,a  "
						+" where a.service_targets_id = t.service_targets_id and t.is_markup = 'Y' group by is_single)t, "
						+" ( SELECT * FROM CODEDATA m1 where m1.codetype='�Ƿ񵥻�' ) t1 where t.is_single = t1.codevalue ";
			//System.out.println("getServiceNum::sql="+sql);
			serviceObject = operation.select(sql);
			return serviceObject;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ������󱨴�");
			return null;
		}		
	}
	
	/**
	 * 
	 * getServiceTypeForSingle2(������һ�仰�����������������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @return        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getServiceTypeForSingle2(String type)
	{
		try {
			List serviceObject = new ArrayList();
			String sql = "  with a as (select t1.service_targets_id from res_service_targets t1 where t1.is_markup = 'Y' and t1.service_targets_type='"+type+"') "
						+" select t1.codename as name, t.total from ( select is_single,count(*) as total from share_service t,a  "
						+" where a.service_targets_id = t.service_targets_id and t.is_markup = 'Y' group by is_single)t, "
						+" ( SELECT * FROM CODEDATA m1 where m1.codetype='�Ƿ񵥻�' ) t1 where t.is_single = t1.codevalue ";
			//System.out.println("getServiceNum::sql="+sql);
			serviceObject = operation.select(sql);
			return serviceObject;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ������󱨴�");
			return null;
		}		
	}
	
	/**
	 * 
	 * getServiceTypeForSingle3(������һ�仰�����������������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @return        
	 * List       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getServiceTypeForSingle3(String id)
	{
		try {
			List serviceObject = new ArrayList();
			String sql = "  with a as (select t1.service_targets_id from res_service_targets t1 where t1.is_markup = 'Y' and t1.service_targets_id='"+id+"') "
						+" select t1.codename as name, nvl(t.total,'0') as total from ( select is_single,count(*) as total from share_service t,a  "
						+" where a.service_targets_id = t.service_targets_id and t.is_markup = 'Y' group by is_single)t, "
						+" ( SELECT * FROM CODEDATA m1 where m1.codetype='�Ƿ񵥻�' ) t1 where t.is_single(+) = t1.codevalue ";
			//System.out.println("getServiceNum::sql="+sql);
			serviceObject = operation.select(sql);
			return serviceObject;
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ������󱨴�");
			return null;
		}		
	}
	
	
	/**
	 * 
	 * getPie1TableData(������һ�仰�����������������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)            
	 * void       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public void getPie1TableData(){
		
		List ObjectNum = getObjectNum();
		List ServiceNum = getServiceNum();
		List sort1 = getServiceType();
		List sort2 = getServiceTypeForSingle();
		List ServiceType2 = getShareServiceTypeNumForSingle();
		List ServiceState = getShareServiceState();
		HashMap table1 = new HashMap();
		if(ObjectNum!=null){
			Map map = (Map) ObjectNum.get(0);
			table1.put("object_num", map.get("TOTAL_NUM"));
		}else{
			table1.put("object_num", "0");
		}
		if(ServiceNum!=null){
			Map map = (Map) ServiceNum.get(0);
			table1.put("service_num", map.get("TOTAL_NUM"));
		}else{
			table1.put("service_num", "0");
		}
		
		
		HashMap table2 = new HashMap();
		HashMap table2_pro  = new HashMap();
		if(sort1!=null){
			for(int i=0;i<sort1.size();i++){
				Map map = (Map) sort1.get(i);
				table2.put(map.get("NAME"), map.get("TOTAL"));
				table2_pro.put(i, map.get("NAME"));
			}
		}else{
			
		}
		HashMap table3 = new HashMap();
		if(sort2!=null){
			for(int i=0;i<sort2.size();i++){
				Map map = (Map)sort2.get(i);
				table3.put(map.get("NAME"), map.get("TOTAL"));
			}
		}else{
			
		}
		
		HashMap table4 = new HashMap();
		if(ServiceState!=null){
			for(int i=0;i<ServiceState.size();i++){
				Map map =(Map)ServiceState.get(i);
				HashMap map_tmp = new HashMap();
				HashMap map_tmp1 = new HashMap();
				map_tmp.put("this_exec_counts", map.get("THIS_EXEC_COUNTS"));
				map_tmp.put("last_exec_counts", map.get("LAST_EXEC_COUNTS"));
				map_tmp1.put("this_share_total", map.get("THIS_SHARE_TOTAL"));
				map_tmp1.put("last_share_total", map.get("LAST_SHARE_TOTAL"));
				table4.put("����������", map_tmp1);
				table4.put("�������", map_tmp);
			}
		}
		
		HashMap table5 = new HashMap();
		if(ServiceType2!=null){
			for(int i=0;i<ServiceType2.size();i++){
				Map map = (Map)ServiceType2.get(i);
				HashMap map_tmp = new HashMap();
				map_tmp.put("this_share_total", map.get("THIS_SHARE_TOTAL"));
				map_tmp.put("last_share_total", map.get("LAST_SHARE_TOTAL"));
				table5.put(map.get("NAME"), map_tmp);
			}
		}else{
			
		}
		
		List list =new ArrayList();
		list.add(table1);
		list.add(table2);
		list.add(table3);
		list.add(table4);
		list.add(table5);
		
		String str = JsonDataUtil.toJSONString(list);
		String content = "var table_obj="+str;
		ResourceBundle bundle = ResourceBundle.getBundle("index");
		// ��ȡ·��
		String table_path = bundle.getString("Pie1_table_date_path");
		//System.out.println("content="+content);
		writeFile(table_path, content);
		
	}
	
	/**
	 * 
	 * getMLPie2Str(��ȡ������ͼ������Ϣ)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @return        
	 * String       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public String getMLPie2Str(String type,String count_type){
		Map dataMap = new HashMap();
		FreemarkerUtil freeUtil = new FreemarkerUtil();
		
		List  sys_tem =getServiceNum(type,count_type) ;
		
		List list=new ArrayList();
		for(int i=0;i<sys_tem.size();i++){
			
			Map map = (Map) sys_tem.get(i);
			Map t_map = new HashMap();
			t_map.put("NAME",map.get("NAME").toString());
			t_map.put("LINK3",map.get("LINK3").toString());//���ӵ�ַ
			t_map.put("TOTAL",map.get("TOTAL").toString());//������
			list.add(t_map);
		}
		
		
		
		List TotaList = getShareTotalByType(type);
		if(TotaList!=null){
			for (int i = 0; i < TotaList.size(); i++) {
				Map map = (Map) TotaList.get(i);
				dataMap.put("total_share", map.get("TOTAL").toString());
			}
		}
		String sys_name="";
		if("000".equals(type)){
			sys_name="����ϵͳ";
		}else if("001".equals(type)){
			sys_name="�ڲ�ϵͳ";
		}else if("002".equals(type)){
			sys_name="�ⲿϵͳ";
		}
		
		dataMap.put("sys_name", sys_name);
		dataMap.put("sys", list);
		dataMap.put("total_user", sys_tem.size());
		if("00".equals(count_type)){
			dataMap.put("y_name", "�����������(��)");
		}else if ("01".equals(count_type)){
			dataMap.put("y_name", "����������(��)");
		}else if ("02".equals(count_type)){
			dataMap.put("y_name", "�������(��)");
		}else{
			dataMap.put("y_name", "�����������(��)");
		}
		
		String chartXML = freeUtil.exportXmlString("MLPie_L2_Tem.xml", dataMap);
		
		/*ResourceBundle bundle = ResourceBundle.getBundle("index");
		// ��ȡ·��
		String pie1_path = bundle.getString("Pie2_path");
		if(chartXML!=null){
			System.out.println("���ɵڶ�����ͼ��xml�ļ�");
			writeFile(pie1_path, chartXML);
		}*/
		System.out.println("�ڶ���getMLPieStr:::chartXML::"+chartXML);
		return chartXML;
	}
	
	/**
	 * 
	 * getMLPie2Str_eChart(��ȡ����echart�ı�ͼ������Ϣ)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param type
	 * @param count_type
	 * @return        
	 * String       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public String getMLPie2Str_eChart(String type,String count_type,String start_time,String end_time){
		Map dataMap = new HashMap();
		FreemarkerUtil freeUtil = new FreemarkerUtil();
		
		List  sys_tem =null;
		if((start_time!=null&&!"".equals(start_time))||((end_time!=null&&!"".equals(end_time)))){
			sys_tem = getServiceNum_date(type,count_type,start_time,end_time) ;
		}else{
			sys_tem = getServiceNum(type,count_type) ;//����
		}
		
		
		List list=new ArrayList();
		for(int i=0;i<sys_tem.size();i++){
			
			Map map = (Map) sys_tem.get(i);
			Map t_map = new HashMap();
			t_map.put("NAME",map.get("NAME").toString());
			t_map.put("LINK3",map.get("LINK3").toString());//���ӵ�ַ
			t_map.put("TOTAL",map.get("TOTAL").toString());//������
			list.add(t_map);
		}
		
		
		
		List TotaList = getShareTotalByType(type);
		if(TotaList!=null){
			for (int i = 0; i < TotaList.size(); i++) {
				Map map = (Map) TotaList.get(i);
				dataMap.put("total_share", map.get("TOTAL").toString());
			}
		}
		String sys_name="";
		if("000".equals(type)){
			sys_name="����ϵͳ";
		}else if("001".equals(type)){
			sys_name="�ڲ�ϵͳ";
		}else if("002".equals(type)){
			sys_name="�ⲿϵͳ";
		}
		
		dataMap.put("sys_name", sys_name);
		dataMap.put("sys", list);
		dataMap.put("total_user", sys_tem.size());
		if("00".equals(count_type)){
			dataMap.put("y_name", "�����������(��)");
		}else if ("01".equals(count_type)){
			dataMap.put("y_name", "����������(��)");
		}else if ("02".equals(count_type)){
			dataMap.put("y_name", "�������(��)");
		}else{
			dataMap.put("y_name", "�����������(��)");
		}
		
		String chartXML = freeUtil.exportXmlString("MLPie_L2_EChart_Tem.js", dataMap);
		
		/*ResourceBundle bundle = ResourceBundle.getBundle("index");
		// ��ȡ·��
		String pie1_path = bundle.getString("Pie2_path");
		if(chartXML!=null){
			System.out.println("���ɵڶ�����ͼ��xml�ļ�");
			writeFile(pie1_path, chartXML);
		}*/
		System.out.println("�ڶ���getMLPieStr:::chartXML::"+chartXML);
		return chartXML;
	}
		
	/**
	 * 
	 * getPie2TableData(��ȡ������ͼ�еı����Ϣ)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)            
	 * void       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public String getPie2TableData(String type){
		
		List ObjectNum = getObjectNum2(type);
		List ServiceNum = getServiceNum2(type);
		List sort1 = getServiceType2(type);
		List sort2 = getServiceTypeForSingle2(type);
		List ServiceType2 = getShareServiceTypeNumForSingle2(type);
		List ServiceState = getShareServiceState2(type);
		
		//��ѯ��4��5������
		List serviceStateNew = getShareServiceState2ForNew(type);
		
		
		HashMap table1 = new HashMap();
		if(ObjectNum!=null){
			Map map = (Map) ObjectNum.get(0);
			table1.put("object_num", map.get("TOTAL_NUM"));
		}else{
			table1.put("object_num", "0");
		}
		if(ServiceNum!=null){
			Map map = (Map) ServiceNum.get(0);
			table1.put("service_num", map.get("TOTAL_NUM"));
		}else{
			table1.put("service_num", "0");
		}
		
		
		HashMap table2 = new HashMap();
		HashMap table2_pro  = new HashMap();
		if(sort1!=null){
			for(int i=0;i<sort1.size();i++){
				Map map = (Map) sort1.get(i);
				table2.put(map.get("NAME"), map.get("TOTAL"));
				table2_pro.put(i, map.get("NAME"));
			}
		}else{
			
		}
		HashMap table3 = new HashMap();
		if(sort2!=null){
			for(int i=0;i<sort2.size();i++){
				Map map = (Map)sort2.get(i);
				table3.put(map.get("NAME"), map.get("TOTAL"));
			}
		}else{
			
		}
		
		/*HashMap table4 = new HashMap();
		if(ServiceState!=null){
			for(int i=0;i<ServiceState.size();i++){
				Map map =(Map)ServiceState.get(i);
				HashMap map_tmp = new HashMap();
				HashMap map_tmp1 = new HashMap();
				map_tmp.put("this_exec_counts", map.get("THIS_EXEC_COUNTS"));
				map_tmp.put("last_exec_counts", map.get("LAST_EXEC_COUNTS"));
				map_tmp1.put("this_share_total", map.get("THIS_SHARE_TOTAL"));
				map_tmp1.put("last_share_total", map.get("LAST_SHARE_TOTAL"));
				table4.put("����������", map_tmp1);
				table4.put("�������", map_tmp);
			}
		}*/
		
		/*HashMap table5 = new HashMap();
		if(ServiceType2!=null){
			for(int i=0;i<ServiceType2.size();i++){
				Map map = (Map)ServiceType2.get(i);
				HashMap map_tmp = new HashMap();
				map_tmp.put("this_share_total", map.get("THIS_SHARE_TOTAL"));
				map_tmp.put("last_share_total", map.get("LAST_SHARE_TOTAL"));
				table5.put(map.get("NAME"), map_tmp);
			}
		}else{
			
		}*/
		
		
		HashMap  table4 = new HashMap();
		HashMap  table5 = new HashMap();
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;//��ǰ��
		
		if(serviceStateNew!=null){
			HashMap map5_single = new HashMap();//����
			HashMap map5_pl = new HashMap();//����
			HashMap map4_tmp = new HashMap();//����������
			HashMap map4_tmp1 = new HashMap();//�������
			
			for(int i=0;i<serviceStateNew.size();i++){
				Map map = (Map)serviceStateNew.get(i);	
				//s1:����s2:����
				if(map.get("MONTH").toString().equals(""+month)){//����
					map4_tmp.put("this_share_total", map.get("THIS_SHARE_TOTAL"));
					map4_tmp1.put("this_exec_counts", map.get("THIS_EXEC_COUNTS"));
					map5_single.put("this_share_total", map.get("S2"));
					map5_pl.put("this_share_total", map.get("S1"));
				}else{//����
					map4_tmp.put("last_share_total", map.get("THIS_SHARE_TOTAL"));
					map4_tmp1.put("last_exec_counts", map.get("THIS_EXEC_COUNTS"));
					map5_single.put("last_share_total", map.get("S2"));
					map5_pl.put("last_share_total", map.get("S1"));
				}
			}
			
			table4.put("����������", map4_tmp);
			table4.put("�������", map4_tmp1);
			table5.put("����", map5_single);
			table5.put("����", map5_pl);
		}
		
		
		
		List list =new ArrayList();
		list.add(table1);
		list.add(table2);
		list.add(table3);
		list.add(table4);
		list.add(table5);
		
		String str = JsonDataUtil.toJSONString(list);
		//System.out.println("content2="+str);
		return str;
		/*String content = "var table_obj="+str;
		ResourceBundle bundle = ResourceBundle.getBundle("index");
		// ��ȡ·��
		String table_path = bundle.getString("Pie2_table_date_path");
		System.out.println("content2="+content);
		writeFile(table_path, content);*/
		
	}
	
	/**
	 * 
	 * getMLPie3Str(��ȡ������ͼ������Ϣ)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param service_targets_id
	 * @param service_targets_name
	 * @return        
	 * String       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public String[] getMLPie3Str(String service_targets_id,String count_type){
		String[] Pie3Str = new String[2];
		Map dataMap = new HashMap();
		FreemarkerUtil freeUtil = new FreemarkerUtil();
		
		List sys_tem =getShareObjectStatisData(service_targets_id,count_type);
		
		int count_num = 0;
		int amount_num = 0;
		String service_targets_name = "";
		String type="";
		List list=new ArrayList();
		for(int i=0;i<sys_tem.size();i++){
			
			Map map = (Map) sys_tem.get(i);
			Map t_map = new HashMap();
			t_map.put("name",map.get("NAME").toString());
			if("00".equals(count_type)){
				t_map.put("share_amount",map.get("SHARE_AMOUNT").toString());//����������
			}else{
				t_map.put("share_amount",map.get("COUNT").toString());//ִ�д���
			}
			
			if(i==0){
				service_targets_name = map.get("SERVICE_TARGETS_NAME").toString();
				type = map.get("SERVICE_TARGETS_TYPE").toString();
			}
			
			count_num +=Integer.parseInt(map.get("COUNT").toString());
			amount_num +=Integer.parseInt(map.get("SHARE_AMOUNT").toString());
			list.add(t_map);
		}
		
		Date date = new Date();
		String nowDate = new SimpleDateFormat("yyyy-mm-dd").format(date);
		
		int current_month_days = Integer.parseInt(nowDate.substring(8,10));
		int avg_count = count_num/current_month_days;
		int avg_aomunt = amount_num/current_month_days;
		
		String y_name = "";
		if("00".equals(count_type)){
			y_name="����������(��)";
		}else{
			y_name="�������(��)";
		}
		
		String link2 ="/txn53000210.do?select-key:type="+type;
		dataMap.put("link2", link2);
		dataMap.put("sys", list);
		dataMap.put("object_name", service_targets_name);
		dataMap.put("y_name", y_name);
		dataMap.put("object_num", sys_tem.size());
		dataMap.put("day_ave_visit", avg_count);
		dataMap.put("day_ave_data", avg_aomunt);
		
		String chartXML = freeUtil.exportXmlString("MLPie_L3_Tem.xml", dataMap);
		
		System.out.println("������getMLPieStr:::chartXML::"+chartXML);
		Pie3Str[0] = chartXML;
		Pie3Str[1] = link2;
		return Pie3Str;
	}
	
	/**
	 * 
	 * getMLPie3Str_eChart(��ȡechart������������Ϣ)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param service_targets_id
	 * @param count_type
	 * @return        
	 * String[]       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public String[] getMLPie3Str_eChart(String service_targets_id,String count_type,String start_time,String end_time){
		String[] Pie3Str = new String[2];
		Map dataMap = new HashMap();
		FreemarkerUtil freeUtil = new FreemarkerUtil();
		
		List sys_tem = null;
			//getShareObjectStatisData(service_targets_id,count_type);
		
		if((start_time!=null&&!"".equals(start_time))||((end_time!=null&&!"".equals(end_time)))){//�Զ���
			sys_tem = getShareObjectStatisData_date(service_targets_id,count_type,start_time,end_time) ;
		}else{
			sys_tem = getShareObjectStatisData(service_targets_id,count_type) ;//����
		}
		
		int count_num = 0;
		int amount_num = 0;
		String service_targets_name = "";
		String type="";
		List list=new ArrayList();
		for(int i=0;i<sys_tem.size();i++){
			
			Map map = (Map) sys_tem.get(i);
			Map t_map = new HashMap();
			t_map.put("name",map.get("NAME").toString());
			if("00".equals(count_type)){
				t_map.put("share_amount",map.get("SHARE_AMOUNT").toString());//����������
			}else{
				t_map.put("share_amount",map.get("COUNT").toString());//ִ�д���
			}
			
			if(i==0){
				service_targets_name = map.get("SERVICE_TARGETS_NAME").toString();
				type = map.get("SERVICE_TARGETS_TYPE").toString();
			}
			
			count_num +=Integer.parseInt(map.get("COUNT").toString());
			amount_num +=Integer.parseInt(map.get("SHARE_AMOUNT").toString());
			list.add(t_map);
		}
		
		Date date = new Date();
		String nowDate = new SimpleDateFormat("yyyy-mm-dd").format(date);
		
		int current_month_days = Integer.parseInt(nowDate.substring(8,10));
		int avg_count = count_num/current_month_days;
		int avg_aomunt = amount_num/current_month_days;
		
		String y_name = "";
		if("00".equals(count_type)){
			y_name="����������(��)";
		}else{
			y_name="�������(��)";
		}
		
		
		
		String link2 ="/txn53000210.do?select-key:type="+type+"&select-key:start_time="+start_time+"&select-key:end_time="+end_time;
		dataMap.put("link2", link2);
		dataMap.put("sys", list);
		dataMap.put("object_name", service_targets_name);
		dataMap.put("y_name", y_name);
		dataMap.put("object_num", sys_tem.size());
		dataMap.put("day_ave_visit", avg_count);
		dataMap.put("day_ave_data", avg_aomunt);
		//���ʱ���ֶ�
		String date1=start_time+"��"+end_time;
		
		dataMap.put("date", date1);
		
		String chartXML = freeUtil.exportXmlString("MLPie_L3_EChart_Tem.js", dataMap);
		
		System.out.println("������getMLPieStr:::chartXML::"+chartXML);
		Pie3Str[0] = chartXML;
		Pie3Str[1] = link2;
		return Pie3Str;
	}
	
	/**
	 * 
	 * getPie3TableData(��ȡ������ͼ�����Ϣ)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param type
	 * @return        
	 * String       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public String getPie3TableData(String type){
		
		//List ObjectNum = getObjectNum2(type);
		List ServiceNum = getServiceNum3(type);
		List sort1 = getServiceType3(type);
		List sort2 = getServiceTypeForSingle3(type);
		List ServiceType2 = getShareServiceTypeNumForSingle3(type);
		List ServiceState = getShareServiceState3(type);
		HashMap table1 = new HashMap();
		
		if(ServiceNum!=null){
			Map map = (Map) ServiceNum.get(0);
			table1.put("service_num", map.get("TOTAL_NUM"));
		}else{
			table1.put("service_num", "0");
		}
		
		
		HashMap table2 = new HashMap();
		HashMap table2_pro  = new HashMap();
		if(sort1!=null){
			for(int i=0;i<sort1.size();i++){
				Map map = (Map) sort1.get(i);
				table2.put(map.get("NAME"), map.get("TOTAL"));
				table2_pro.put(i, map.get("NAME"));
			}
		}else{
			
		}
		HashMap table3 = new HashMap();
		if(sort2!=null){
			for(int i=0;i<sort2.size();i++){
				Map map = (Map)sort2.get(i);
				table3.put(map.get("NAME"), map.get("TOTAL"));
			}
		}else{
			
		}
		
		HashMap table4 = new HashMap();
		if(ServiceState!=null){
			for(int i=0;i<ServiceState.size();i++){
				Map map =(Map)ServiceState.get(i);
				HashMap map_tmp = new HashMap();
				HashMap map_tmp1 = new HashMap();
				map_tmp.put("this_exec_counts", map.get("THIS_EXEC_COUNTS"));
				map_tmp.put("last_exec_counts", map.get("LAST_EXEC_COUNTS"));
				map_tmp1.put("this_share_total", map.get("THIS_SHARE_TOTAL"));
				map_tmp1.put("last_share_total", map.get("LAST_SHARE_TOTAL"));
				table4.put("����������", map_tmp1);
				table4.put("�������", map_tmp);
			}
		}
		
		HashMap table5 = new HashMap();
		if(ServiceType2!=null){
			for(int i=0;i<ServiceType2.size();i++){
				Map map = (Map)ServiceType2.get(i);
				HashMap map_tmp = new HashMap();
				map_tmp.put("this_share_total", map.get("THIS_SHARE_TOTAL"));
				map_tmp.put("last_share_total", map.get("LAST_SHARE_TOTAL"));
				table5.put(map.get("NAME"), map_tmp);
			}
		}else{
			
		}
		
		List list =new ArrayList();
		list.add(table1);
		list.add(table2);
		list.add(table3);
		list.add(table4);
		list.add(table5);
		
		String str = JsonDataUtil.toJSONString(list);
		//System.out.println("content3="+str);
		return str;
		/*String content = "var table_obj="+str;
		ResourceBundle bundle = ResourceBundle.getBundle("index");
		// ��ȡ·��
		String table_path = bundle.getString("Pie2_table_date_path");
		System.out.println("content2="+content);
		writeFile(table_path, content);*/
		
	}
	
	
	/**
	 * 
	 * getMLPieStr(��ȡ��ͼ������Ϣ�ַ���)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @return        
	 * String       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public String getMLPieStr(){
		Map dataMap = new HashMap();
		FreemarkerUtil freeUtil = new FreemarkerUtil();
		
		ArrayList  qx_tem = new ArrayList();
		ArrayList  in_tem = new ArrayList();
		ArrayList  out_tem = new ArrayList();
		int  in_num = 1;
		int out_num = 1;
		int qx_num = 1;
		
		List serviceNumList = getTotalServiceNum();
		if(serviceNumList!=null){
			for (int i = 0; i < serviceNumList.size(); i++) {
				Map map = (Map) serviceNumList.get(i);
				String type = map.get("SERVICE_TARGETS_TYPE").toString();
				
				if(type!=null&&!"".equals(type)){
					if("000".equals(type)){
						map.put("color", Integer.toHexString(256-qx_num*16));
						qx_num++;
						qx_tem.add(map);
					}else if ("001".equals(type)){
						map.put("color", Integer.toHexString(65280+in_num*16));
						in_num++;
						in_tem.add(map);
					}else if ("002".equals(type)){
						map.put("color", Integer.toHexString(16711680+out_num*16*16*16));
						out_num++;
						out_tem.add(map);
					}else{
						
					}
				}
			}
		}
		
		
		List shareTotaList = getShareTotal();
		if(shareTotaList!=null){
			for (int i = 0; i < shareTotaList.size(); i++) {
				Map map = (Map) shareTotaList.get(i);
				String type = map.get("SERVICE_TARGETS_TYPE").toString();
				
				if(type!=null&&!"".equals(type)){
					if("000".equals(type)){
						dataMap.put("qx_share_total", map.get("TOTAL").toString());
					}else if ("001".equals(type)){
						dataMap.put("in_share_total", map.get("TOTAL").toString());
					}else if ("002".equals(type)){
						dataMap.put("out_share_total", map.get("TOTAL").toString());
					}else{
						
					}
				}
			}
		}
		String qx_link2="select-key:type=000&select-key:count_type=00";
		String in_link2="select-key:type=001&select-key:count_type=00";
		String out_link2="select-key:type=002&select-key:count_type=00";
		
		dataMap.put("in_link2", in_link2);
		dataMap.put("out_link2", out_link2);
		dataMap.put("qx_link2", qx_link2);
		dataMap.put("in_count", in_tem.size());
		dataMap.put("out_count", out_tem.size());
		dataMap.put("qx_count", qx_tem.size());
		dataMap.put("in_sys", in_tem);
		dataMap.put("out_sys", out_tem);
		dataMap.put("qx_sys", qx_tem);
		int total_share = Integer.parseInt(dataMap.get("qx_share_total").toString())+Integer.parseInt(dataMap.get("in_share_total").toString())+Integer.parseInt(dataMap.get("out_share_total").toString());
		int total_user = in_tem.size()+out_tem.size()+qx_tem.size();
		dataMap.put("total_share", total_share+"");
		dataMap.put("total_user", total_user+"");
		
		
		
		String chartXML = freeUtil.exportXmlString("MLPie_L1_Tem.xml", dataMap);
		
		ResourceBundle bundle = ResourceBundle.getBundle("index");
		// ��ȡ·��
		String pie1_path = bundle.getString("Pie1_path");
		if(chartXML!=null){
			//System.out.println("���ɵ�һ����ͼ��xml�ļ�");
			writeFile(pie1_path, chartXML);
		}
		//System.out.println("getMLPieStr:::chartXML::"+chartXML);
		return chartXML;
	}
	
	/**
	 * 
	 * getMLPieStr_eCharts(eCharts��ͼ��������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @return        
	 * String       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public String getMLPieStr_eCharts(){
		Map dataMap = new HashMap();
		FreemarkerUtil freeUtil = new FreemarkerUtil();
		ArrayList legend_list = new ArrayList();//ͼ������
		
		
		
		
		ArrayList  qx_tem = new ArrayList();
		ArrayList  in_tem = new ArrayList();
		ArrayList  out_tem = new ArrayList();
		
		List serviceNumList = getTotalServiceNum();
		if(serviceNumList!=null){
			for (int i = 0; i < serviceNumList.size(); i++) {
				Map map = (Map) serviceNumList.get(i);
				String type = map.get("SERVICE_TARGETS_TYPE").toString();
				
				if(type!=null&&!"".equals(type)){
					if("000".equals(type)){
						qx_tem.add(map);
					}else if ("001".equals(type)){
						in_tem.add(map);
					}else if ("002".equals(type)){
						out_tem.add(map);
					}else{
						
					}
				}
			}
		}
		
		
		List shareTotaList = getShareTotal();
		if(shareTotaList!=null){
			for (int i = 0; i < shareTotaList.size(); i++) {
				Map map = (Map) shareTotaList.get(i);
				String type = map.get("SERVICE_TARGETS_TYPE").toString();
				
				if(type!=null&&!"".equals(type)){
					if("000".equals(type)){
						dataMap.put("qx_share_total", map.get("TOTAL").toString());
					}else if ("001".equals(type)){
						dataMap.put("in_share_total", map.get("TOTAL").toString());
					}else if ("002".equals(type)){
						dataMap.put("out_share_total", map.get("TOTAL").toString());
					}else{
						
					}
				}
			}
		}
		
		String qx_link2="/txn53000210.do?select-key:type=000&select-key:count_type=00";
		String in_link2="/txn53000210.do?select-key:type=001&select-key:count_type=00";
		String out_link2="/txn53000210.do?select-key:type=002&select-key:count_type=00";
		
		dataMap.put("in_link2", in_link2);
		dataMap.put("out_link2", out_link2);
		dataMap.put("qx_link2", qx_link2);
		/*dataMap.put("in_count", in_tem.size());
		dataMap.put("out_count", out_tem.size());
		dataMap.put("qx_count", qx_tem.size());
		dataMap.put("in_sys", in_tem);
		dataMap.put("out_sys", out_tem);
		dataMap.put("qx_sys", qx_tem);
		int total_share = Integer.parseInt(dataMap.get("qx_share_total").toString())+Integer.parseInt(dataMap.get("in_share_total").toString())+Integer.parseInt(dataMap.get("out_share_total").toString());
		int total_user = in_tem.size()+out_tem.size()+qx_tem.size();
		dataMap.put("total_share", total_share+"");
		dataMap.put("total_user", total_user+"");*/
		
		//ehcarts
		dataMap.put("legend_name", serviceNumList);//ͼ��
		//dataMap.put("legend_list", serviceNumList);//ͼ��
		
		
		String chartXML = freeUtil.exportXmlString("MLPie_L1_EChart_Tem.js", dataMap);
		
		System.out.println("eCharts��ͼ-chartXML="+chartXML);
		
		ResourceBundle bundle = ResourceBundle.getBundle("index");
		// ��ȡ·��
		String pie1_path = bundle.getString("Pie1_ECharts");
		if(chartXML!=null){
			//System.out.println("���ɵ�һ����ͼ��xml�ļ�");
			writeFile(pie1_path, chartXML);
		}
		//System.out.println("getMLPieStr:::chartXML::"+chartXML);
		return chartXML;
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
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
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
