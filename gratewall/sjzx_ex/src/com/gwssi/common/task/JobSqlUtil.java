package com.gwssi.common.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.genersoft.frame.base.database.DBException;

public class JobSqlUtil
{
	/**
	 * 蛛网图表-接口详细图
	 * 获取调用最多的前20个基础接口
	 * @return
	 */
	public static String getTop20InterfaceSql()
	{
		StringBuffer sb = new StringBuffer(
				"select interface_name,interface_id,table_id,table_name_cn as table_name ");
		sb.append(" from (select count(*) nnm, t3.interface_name,t3.interface_id,t3.table_id,t3.table_name_cn");
		sb.append(" from share_log t, share_service t2, share_interface t3 where t.service_id");
		sb.append("= t2.service_id  and t2.interface_id = t3.interface_id and t.log_type = '02'");
		sb.append("	and t2.is_markup='Y'  and t3.is_markup='Y' and t3.interface_state = 'Y'  group by t3.interface_name,");
		sb.append("t3.interface_id,t3.table_id,t3.table_name_cn  order by nnm desc)a where rownum < =20");
		return sb.toString();
	}

	/**
	 * 蛛网图表-接口详细图
	 * 根据表ID串获取共享表的信息
	 * @return
	 */
	public static String getShareTableByTableId(String tableIds)
	{
		StringBuffer sb = new StringBuffer(
				"select r.table_name_cn,r.table_no,t.service_targets_name");
		sb.append(" from res_share_table r,res_business_topics rs,res_service_targets t");
		sb.append(" where  r.business_topics_id=rs.business_topics_id");
		sb.append(" and rs.service_targets_id=t.service_targets_id ");
		sb.append(" and r.table_no in (").append(tableIds).append(")");
		sb.append(" and r.table_name_cn is not null order by rs.show_order,r.show_order");
		return sb.toString();
	}
	
	/**
	 * 蛛网图表-接口-服务对象详细图
	 * 根据表ID串获取共享表的信息
	 * @return
	 */
	public static String getLeftTableByTableId(String tableIds)
	{
		StringBuffer sb = new StringBuffer(" select r.share_table_id key, r.table_name_cn as title from res_share_table r, res_business_topics rs ");
		sb.append(" where r.table_name_cn is not null and r.business_topics_id = rs.business_topics_id  and table_no in ( "+tableIds+")");
		sb.append(" order by rs.show_order, r.show_order");
		return sb.toString();
	}
	
	/**
	 * 蛛网图表-接口-服务对象详细图
	 * 获取全部服务对象信息
	 * @return
	 */
	public static String getRightServiceObject()
	{
		StringBuffer sb = new StringBuffer("select r.service_targets_id, r.service_targets_name, (select count(1) ");
		sb.append(" from collect_task task where task.service_targets_id = r.service_targets_id) + ");
		sb.append(" (select count(1) from share_service svr ");
		sb.append(" where svr.service_targets_id = r.service_targets_id) svr_count ");
		sb.append(" from res_service_targets r where r.is_markup = 'Y' and r.service_targets_id");
		sb.append(" is not null  order by r.show_order ");
		return sb.toString();
	}
	
	/**
	 * getShareInterfaceTable(查询调用最多的20个共享接口对应的服务对象信息)    
	 *   
	 * @return        
	 * String       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public static String getShareInterfaceTable()
	{
		StringBuffer sb = new StringBuffer("select INTERFACE_ID,wm_concat(m1.service_targets_name) SERVICE_TARGETS_NAME from share_service m,res_service_targets m1 ");
		sb.append(" where m.interface_id in ( select interface_id from ( ");
		sb.append(" select count(*) nnm, t3.interface_name, t3.interface_id, t3.table_id ");
		sb.append(" from share_log t, share_service t2, share_interface t3 ");
		sb.append(" where t.service_id = t2.service_id and t2.interface_id = t3.interface_id and t.log_type = '02' and t2.is_markup = 'Y' and t3.is_markup = 'Y' ");
		sb.append(" group by t3.interface_name, t3.interface_id, t3.table_id order by nnm desc) a where rownum < = 20 ");
		sb.append(" ) and m.service_targets_id = m1.service_targets_id  group by interface_id");
		return sb.toString();
	}

	/**
	 * 获取服务对象统计信息 根节点SQL
	 * @param id
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 * @throws DBException
	 */
	public static String getServiceTargetsInfoRoot(String id,String dateFrom,String dateTo) throws DBException{
		//List rootlist = new ArrayList();
		//List leaflist = new ArrayList();
		String date=" 1=1 ";
		if(StringUtils.isBlank(dateFrom) && StringUtils.isBlank(dateTo)){
			//都为空 默认查最近三个月
			date="log_date>=to_char(add_months(sysdate, -2), 'yyyy-MM') || '-01'"
				+" and log_date < to_char(add_months(sysdate, 1), 'yyyy-MM') || '-01'";
		}else if(!StringUtils.isBlank(dateFrom) && !StringUtils.isBlank(dateTo)){
			//都不空 查区间
			date="log_date>='"+dateFrom+"'"
				+" and log_date <= '"+dateTo+"'";
		}else if(!StringUtils.isBlank(dateFrom) && StringUtils.isBlank(dateTo)){
			//无结束时间
			date="log_date>='"+dateFrom+"'";
		}else if(StringUtils.isBlank(dateFrom) && !StringUtils.isBlank(dateTo)){
			//无开始时间
			date="log_date <= '"+dateTo+"'";
		}
			
		StringBuffer rootsql = new StringBuffer();
		
		//获取所有服务对象数据总量 构成树的根
		rootsql.append("select r.service_targets_id,nvl(c_count,0) c_count,nvl(c_amount,0) c_amount,nvl(c_err,0) c_err,nvl(s_count,0) s_count,nvl(s_amount,0) s_amount,nvl(s_err,0) s_err,")
			.append(" r.service_targets_name ,c.codename as service_status ,r.is_share,r.is_collect from ")
			.append(" (select (case WHEN c.service_targets_id IS NULL then s.service_targets_id else c.service_targets_id end) as service_targets_id,")
			.append(" c.c_count,c.c_amount,c.c_err,s.s_count,s.s_amount,s.s_err from")
			.append(" (select c1.service_targets_id,nvl(sum(c1.sum_record_amount),0) c_amount,nvl(sum(c1.exec_count),0) c_count,nvl(sum(c1.error_num),0) c_err")
			.append(" from collect_log_statistics c1,collect_task c2 where ")
			.append(date)
			.append(" and c1.log_date not in(select exception_Date from exception_date)")
			.append(" and c1.task_id(+) = c2.collect_task_id and c2.is_markup = 'Y'")
			.append(" group by c1.service_targets_id) c full outer join")
			.append(" (select s1.service_targets_id,nvl(sum(s1.sum_record_amount),0) s_amount,nvl(sum(s1.exec_count),0) s_count,nvl(sum(s1.error_num),0) s_err")
			.append(" from share_log_statistics s1,share_service s2 where ")
			.append(date)
			.append(" and s1.log_date not in(select exception_Date from exception_date)")
			.append(" and s1.service_id(+) = s2.service_id and s2.is_markup = 'Y'")
			.append(" group by s1.service_targets_id) s")
			.append(" on c.service_targets_id=s.service_targets_id ) d,")
			.append(" res_service_targets r,codedata c")
			.append(" where d.service_targets_id(+)=r.service_targets_id");
		if(StringUtils.isNotBlank(id)){
			rootsql.append(" and r.service_targets_id='").append(id).append("'");
		}	
		
		rootsql.append(" and r.is_markup='Y' and r.is_formal='Y'")
			.append(" and c.codetype='资源管理_一般服务状态' and r.service_status=c.codevalue")
			.append(" order by r.show_order");
		//System.out.println(rootsql.toString());
		
		
        //rootlist = operation.select(rootsql.toString());
		//leaflist = operation.select(leafsql.toString());
		
		return rootsql.toString();
	}
	/**
	 * 获取服务对象统计信息 子节点SQL
	 * @param id
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 * @throws DBException
	 */
	public static String getServiceTargetsInfoLeaf(String id,String dateFrom,String dateTo) throws DBException{
		
		String date=" 1=1 ";
		if(StringUtils.isBlank(dateFrom) && StringUtils.isBlank(dateTo)){
			//都为空 默认查最近三个月
			date="log_date>=to_char(add_months(sysdate, -2), 'yyyy-MM') || '-01'"
				+" and log_date < to_char(add_months(sysdate, 1), 'yyyy-MM') || '-01'";
		}else if(!StringUtils.isBlank(dateFrom) && !StringUtils.isBlank(dateTo)){
			//都不空 查区间
			date="log_date>='"+dateFrom+"'"
				+" and log_date <= '"+dateTo+"'";
		}else if(!StringUtils.isBlank(dateFrom) && StringUtils.isBlank(dateTo)){
			//无结束时间
			date="log_date>='"+dateFrom+"'";
		}else if(StringUtils.isBlank(dateFrom) && !StringUtils.isBlank(dateTo)){
			//无开始时间
			date="log_date <= '"+dateTo+"'";
		}
			
		
		
		//获取任务和服务数据统计 构成叶子节点
		StringBuffer leafsql= new StringBuffer();
		leafsql.append("select t1.*,r.service_targets_name from(")
		.append(" with a as")
		.append(" (select c.codetype,c.codevalue,c.codename from codedata c")
		.append(" where c.codetype in ('采集任务_采集类型','资源管理_归档服务状态','资源管理_数据源类型'))")
		.append(" select c2.service_targets_id,c2.collect_task_id taskorservice_id,")		
		.append(" '(采集)' || c2.task_name mc,nvl(c1.c_amount,0) c_amount,")		 
		.append(" nvl(c1.c_count,0) c_count,nvl(c1.c_err,0) c_err,")	
		.append(" null s_count,null s_err,null s_amount,c3.codename ,")		       
		.append(" c4.codename status_cn,'' service_no,'C' cors")		       
		.append(" from (select service_targets_id,")       
		.append(" nvl(sum(sum_record_amount), 0) c_amount,")		  
		.append(" nvl(sum(exec_count), 0) c_count,")		               
		.append(" nvl(sum(error_num), 0) c_err,task_id")		               
		.append(" from collect_log_statistics where ")		               
		.append(date)		               
		.append(" and log_date not in (select exception_Date from exception_date)")		          
		.append(" group by service_targets_id, task_id) c1,")		          
		.append(" (select * from collect_task where is_markup = 'Y') c2,")		         
		.append(" (select a.codevalue,a.codename from a where a.codetype='采集任务_采集类型') c3,")		       
		.append(" (select a.codevalue,a.codename from a where a.codetype='资源管理_归档服务状态') c4")		       
		.append(" where c1.task_id(+) = c2.collect_task_id")		       
		.append(" and c2.task_status=c4.codevalue(+)")		 
		.append(" and c2.collect_type=c3.codevalue(+)")		 
		.append(" union all")		 
		.append(" select c2.service_targets_id,")		
		.append(" c2.service_id taskorservice_id,")		
		.append(" '(共享)' || c2.service_name mc,")		       
		.append(" null c_count,null c_err,null c_amount,")		       
		.append(" nvl(c1.s_count,0) s_count,nvl(c1.s_err,0) s_err,")		       
		.append(" nvl(c1.s_amount,0) s_amount,")		       
		.append(" c3.codename ,c4.codename status_cn,c2.service_no,'S' cors")		       
		.append(" from (select service_targets_id,")		       
		.append(" nvl(sum(sum_record_amount), 0) s_amount,")		       
		.append(" nvl(sum(exec_count), 0) s_count,")		      
		.append(" nvl(sum(error_num), 0) s_err,service_id")		  
		.append(" from share_log_statistics where ")		               
		.append(date)		               
		.append(" and log_date not in (select exception_Date from exception_date)")		               
		.append(" group by service_targets_id, service_id) c1,")		               
		.append(" (select * from share_service where is_markup = 'Y') c2,")		          
		.append(" (select a.codevalue,a.codename from a where a.codetype='资源管理_数据源类型') c3,")		            
		.append(" (select a.codevalue,a.codename from a where a.codetype='资源管理_归档服务状态') c4")		         
		.append(" where c1.service_id(+) = c2.service_id")		       
		.append(" and c2.SERVICE_STATE=c4.codevalue(+)")		       
		.append(" and c2.SERVICE_TYPE=c3.codevalue(+)) t1,res_service_targets r")		       
		.append(" where t1.service_targets_id(+)=r.service_targets_id and r.is_markup='Y'");
		if(StringUtils.isNotBlank(id)){
			leafsql.append(" and r.service_targets_id='").append(id).append("'");
		}
		leafsql.append(" and r.is_formal='Y'")		 
		.append(" order by r.show_order, to_number(replace(t1.service_no, 'service', ''))");		 
			
		//System.out.println(leafsql.toString());
        //rootlist = operation.select(rootsql.toString());
		//leaflist = operation.select(leafsql.toString());
		
		return leafsql.toString();
	}
	
	
	
	
	
}
