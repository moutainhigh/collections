package cn.gwssi.datachange.datashare.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import cn.gwssi.resource.Conts;

@Service
public class ShareResourceService extends BaseService {

	public List<Map> queryShareRsList(Map<String, String> params) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		List listParams=new ArrayList();
		StringBuffer sql=new StringBuffer(Conts.T_PT_GXZYTXX).append(" where 1=1 ");
		if(StringUtils.isNotBlank(params.get("tablecode"))){
			sql.append(" and a.tablecode like  '%");
			sql.append(params.get("tablecode"));
			sql.append("%' ");
		}
		if(StringUtils.isNotBlank(params.get("tablename"))){
			sql.append(" and a.tablename like '%");
			sql.append(params.get("tablename"));
			sql.append("%'");
		}
		if(StringUtils.isNotBlank(params.get("createperson"))){
			sql.append(" and a.createperson like '%");
			sql.append(params.get("createperson"));
			sql.append("%'");
		}
		if(StringUtils.isNotBlank(params.get("rsid"))){
			sql.append(" and a.gxzyid = '");
			sql.append(params.get("rsid"));
			sql.append("'");
		}
		sql.append(" order by a.createtime desc");
		List<Map> queryMap=dao.queryForList(sql.toString(), null);
		return queryMap;
	}
	
	
	
	public void insertDataSum(List<String> tableName) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=null;
		for(int i=0;i<tableName.size();i++){
			//update t_share_resource set datasum=convert(varchar(32),(select count(1) from T_DM_ZWCSFSDM)) where tablename='T_DM_ZWCSFSDM'
			sql=new StringBuffer("update "+tableName.get(i)+"set datasum=" +
					"convert(varchar(32),(select count(1) from T_DM_ZWCSFSDM)) where tablename="+tableName.get(i)+")) where tablename='"+tableName.get(i)+"'");
			dao.queryForList(sql.toString(),null);
		}
	}

	//查找所有表名
	//@SuppressWarnings
	public List<Map> querytname() throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=null;
		List<Map> tzdname=null;
		//--读取库中的所有表名
		sql = new StringBuffer("select name from sysobjects where type='u'");
		tzdname=dao.queryForList(sql.toString(),null);
		return tzdname;
	}

	//查找表下面的字段
	public List<Map> querytdata(Map<String,String> params) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=null;
		List<Map> tzdname=null;
		if(StringUtils.isNotBlank(params.get("tname"))){
			//--读取指定表的所有列名
			//varchar 39  int 56 datetime 61 numeric 63  char 47  date  49 timestamp 37
			//select * from syscolumns where id=(select max(id) from sysobjects where type='u' and name='t_pt_rrrrr')
			sql=new StringBuffer("select type,length,name from syscolumns where id=(select id from sysobjects where type='u' and name='"+params.get("tname")+"')");		
			tzdname=dao.queryForList(sql.toString(),null);
		}
		return tzdname;
	}

	public String querystate(Map map) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		String rsid=(String) map.get("rsid");
		String state=(String) map.get("state");
		if(StringUtils.isNotBlank(rsid) || StringUtils.isNotBlank(state)){
			//update t_share_resource set state='state' where rsid='rsid' 
			String sql="update t_share_resource set state='"+state+"' where rsid='"+rsid+"'";		
			dao.execute(sql, null);
			return "success";
		}
		return "faile";
	}

	//详情信息
	public List<Map> queryShareLDetail(String tablepkid) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		List<Map> rsDetail=null;
		if(StringUtils.isNotBlank(tablepkid)){
			String sql=Conts.T_PT_GXZYLXX;
			sql +=" where a.tablepkid='"+tablepkid+"'";
			rsDetail=dao.queryForList(sql, null);
		}
		return rsDetail;
	}


	/*//例子
	public void execute(JobExecutionContext jobMap) throws JobExecutionException {
		System.out.println("可传参数的任务调度准备。。。");
		IPersistenceDAO dao= this.getPersistenceDAO();
		JobDetail jobName = jobMap.getJobDetail();
		JobDataMap dataMap = jobMap.getJobDetail().getJobDataMap();
		//传入的方法，调用类中的方法if做判断
		String strData = dataMap.getString("method");
		System.out.println(strData);
		System.out.println(jobMap);
		Scheduler sched=null;
		try {
			sched=SchedFacory.getScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		  JobKey jobKey = jobMap.getJobDetail().getKey();
	      TriggerKey triggerKey = jobMap.getTrigger().getKey();       


	       new Thread(new SchedEventLister(triggerKey)).start(); 		


		StringBuffer sql=null;
		List<Map> tzdname=null;
		//--读取库中的所有表名
		 sql = new StringBuffer("select distinct tablename from t_share_resource");
		 List<Map> tables=null;
		try {
			tables = dao.queryForList(sql.toString(), null);
		} catch (OptimusException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 if(tables!=null){
// update t_share_resource set datasum=convert(varchar(32),(select count(1) from T_DM_ZJLXDM)) where tablename='a21123asd1d3'
			 for(Map tabMap:tables){
				 String sqlcount = "update t_share_resource set datasum=convert(varchar(32),(select count(1) from "+tabMap.get("tablename")+")) where tablename='"+tabMap.get("tablename")+"'";
				 try {
					dao.execute(sqlcount, null);
				} catch (OptimusException e) {
					e.printStackTrace();
				}
			 }
		 }
		 //启动线程监听触发器的状态
		 new Thread(new SchedEventLister(triggerKey)).start();

	}*/

	//定时调度，清算总量
	public void insertDataSum() {
		IPersistenceDAO dao= this.getPersistenceDAO();

		StringBuffer sql=null;
		List<Map> tzdname=null;
		//--读取库中的所有表名
		sql = new StringBuffer("select distinct tablename from t_share_resource");
		List<Map> tables=null;
		try {
			tables = dao.queryForList(sql.toString(), null);
		} catch (OptimusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(tables!=null){
			// update t_share_resource set datasum=convert(varchar(32),(select count(1) from T_DM_ZJLXDM)) where tablename='a21123asd1d3'
			for(Map tabMap:tables){
				String sqlcount = "update t_share_resource set datasum=convert(varchar(32),(select count(1) from "+tabMap.get("tablename")+")) where tablename='"+tabMap.get("tablename")+"'";
				try {
					dao.execute(sqlcount, null);
				} catch (OptimusException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 获取主题
	 * @param subject
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> selectShareTitle() throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		List<Map> rsDetail=null;
		String sql=Conts.T_PT_GXZYTXX_TITLE;
		return rsDetail=dao.queryForList(sql, null);
	}
	
	/**
	 * 根据主题获取表
	 * @param subject
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> selectShareTable(String subject) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		List<Map> rsDetail=null;
		String sql=Conts.T_PT_GXZYTXX;
		if(StringUtils.isNotBlank(subject)){
			sql +=" where a.subject='"+subject+"'";
		}
		return rsDetail=dao.queryForList(sql, null);
	}

	/**
	 * 根据表名获取表列
	 * @param subject
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> selectShareColumn(String tablecode) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		List<Map> rsDetail=null;
		String sql=Conts.T_PT_GXZYLXX;
		if(StringUtils.isNotBlank(tablecode)){
			sql +=" where a.tablecode='"+tablecode+"'";
		}
		return dao.queryForList(sql, null);
	}
}
