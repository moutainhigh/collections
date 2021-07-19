package com.gwssi.trs.service;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.trs.Conts;

/**
 * 该类为广东全文检索拷贝 柴浩伟修改
 * @author chaihw
 *
 */
@Service
public class CaseService extends BaseService{
	
	
	//主题库数据源key 全文检索 详细信息库 key
	//private static String regdetail_ds_key=""'
	
	private static String getDetail_datasourcekey(){
		Properties properties = ConfigManager.getProperties("optimus");
		
		String key= properties.getProperty("regDetail.datasourcekey");


		return key;
	}
	/**
	 * 案件信息，简易案件信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryRegJbxx(Map map,String type) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		System.out.println("regDetail_datasourcekey:"+getDetail_datasourcekey());													
		//StringBuffer sql=new StringBuffer("select a.*,b.*,c.*,d.* from T_SCZT_SCZTJBXX a,T_SCZT_SCZTBCXX b,T_SCZT_SCZTLSXX c,T_SCZT_WGQYSCJYHDJBXX d where a.pripid=b.pripid and a.pripid=c.pripid and a.pripid=d.WGQYSCJYHDJBXXID ");
		StringBuffer sql=null;
		if("0".equals(type)){//案件信息
			sql=new StringBuffer("select * from v_case_cf_baseinfo");
		}else if("1".equals(type)){//简易案件信息
			sql=new StringBuffer("select * from v_case_se_baseinfo");
		}
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			//System.out.println("pripid_______________________" + pripid);
			//String sourceflag=(String)map.get("sourceflag"); 无平台 代码
			sql.append(" where  1=1" );
/*			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.sourceflag = ?");
				list.add(sourceflag);
			}*/
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and priPid = ? ");
				list.add(pripid);
			}
		}else{
			return null;
		}
		return typechage(dao.queryForList(sql.toString(), list));
	}
	
	/**
	 * 类型转换(把GregorianCalendar 转换为String)
	 * @return
	 */
	public List<Map> typechage(List<Map> list){
		List<Map> changtype =new ArrayList<Map>();
		for(Map<String,Object> map1:list){
			
			Map<String,Object> newMap= new HashMap<String,Object>();
			for(String s :map1.keySet()){
				Object obj=map1.get(s);
				
				if (obj!=null&&(obj.getClass()==GregorianCalendar.class)){
					GregorianCalendar gcal =(GregorianCalendar)obj;
					String format = "yyyy-MM-dd HH:mm:ss";
					SimpleDateFormat formatter = new SimpleDateFormat(format);
					newMap.put(s,  formatter.format(gcal.getTime()).toString());
				}else{
					newMap.put(s, map1.get(s));
				}
			}
			changtype.add(newMap);
		}
		
		return  changtype;
	}
	/**
	 * 市场主体标记信息(T_SCZT_SCZTBJXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryRegBjxx(Map map) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		//select * from T_SCZT_SCZTBJXX select * from T_SCZT_Q where PriPID = 'e2d36701-0130-1000-e000-24930a0c0115'
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_SCZTBJXX ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_SCZTBJXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.sourceflag = ?");
				list.add(sourceflag);
			}
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and a.priPid = ?");
				list.add(pripid);
			}
		}else{
			return null;
		}
		return dao.queryForList(sql.toString(), list);
	}
	
	/**
	 * 案件当事人信息(CASE_CF_PARTYINFO)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> querydangshirenxx(Map map) throws OptimusException{	
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		StringBuffer sql=new StringBuffer("select * from CASE_CF_PARTYINFO t");
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" where t.caseid = ?");
				list.add(pripid);
			}else{
				return null;
			}
			
		}else{
			return null;
		}
		return dao.queryForList(sql.toString(), list);
	}
	
	/**
	 * 违法行为及处罚信息(CASE_CF_IRREGPUNISHINFO)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryweifaxx(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		StringBuffer sql=new StringBuffer("select * from CASE_CF_IRREGPUNISHINFO t");
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and caseid = ?");
				list.add(pripid);
			}
			
		}else{
			return null;
		}

		List<Map> list1=dao.queryForList(sql.toString(), list);
		return list1;
	}
	

	/**
	 * 案源信息(CASE_CF_SRCINF)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryanyuanxx(Map map) throws OptimusException{	
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		StringBuffer sql=new StringBuffer("select * from CASE_CF_SRCINF t");
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			System.out.println(pripid);
			sql.append(" where 1=1 ");

			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and t.caseid = ?");
				list.add(pripid);
			}
			
		}else{
			return null;
		}
		//System.out.println(dao.queryForList(sql.toString(), list));
		List<Map> queryForList = dao.queryForList(sql.toString(),list);
		return queryForList;
	}
	
	/**
	 * 案件移送信息(CASE_CF_TRANS)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryanjianyisongxx(Map map) throws OptimusException{	
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		StringBuffer sql=new StringBuffer("select * from CASE_CF_TRANS t");
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and t.caseid = ?");
				list.add(pripid);
			}
			
		}else{
			return null;
		}
		return dao.queryForList(sql.toString(), list);
	}
	
	/**
	 * 简易案件当事人信息(CASE_CF_PARTYINFO)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> querysimpledangshirenxx(Map map) throws OptimusException{	
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		StringBuffer sql=new StringBuffer("select * from CASE_SE_PARTYINFO t");
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" where t.caseid = ?");
				list.add(pripid);
			}else{
				return null;
			}
			
		}else{
			return null;
		}
		return dao.queryForList(sql.toString(), list);
	}
	
	/**
	 * 简易案件违法行为及处罚信息(CASE_CF_IRREGPUNISHINFO)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> querysimpleweifaxx(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		StringBuffer sql=new StringBuffer("select * from CASE_SE_IRREGPUNISHINFO t");
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and caseid = ?");
				list.add(pripid);
			}
			
		}else{
			return null;
		}

		List<Map> list1=dao.queryForList(sql.toString(), list);
		return list1;
	}
	

	/**
	 * 简易案件案源信息(CASE_CF_SRCINF)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> querysimpleanyuanxx(Map map) throws OptimusException{	
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		StringBuffer sql=new StringBuffer("select * from CASE_SE_SRCINF t");
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			sql.append(" where 1=1 ");

			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and t.caseid = ?");
				list.add(pripid);
			}
			
		}else{
			return null;
		}
		return dao.queryForList(sql.toString(), list);
	}
	
	/**
	 * 冻结股东信息(T_SCZT_DJGDXX)--
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryRegDjgdxx(Map map) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_DJGDXX ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_DJGDXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.sourceflag = ?");
				list.add(sourceflag);
			}
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and a.priPid = ?");
				list.add(pripid);
			}
			
		}else{
			return null;
		}
		return dao.queryForList(sql.toString(), list);
	}
	
	/**
	 * 证照信息(T_SCZT_ZZXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryRegZzxx(Map map) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_ZZXX ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_ZZXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.sourceflag = ?");
				list.add(sourceflag);
			}
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and a.priPid = ?");
				list.add(pripid);
			}
			
		}else{
			return null;
		}
		sql.append(" order by a.valfrom desc ");
		return dao.queryForList(sql.toString(), list);
	}
	
	/**
	 * 清算信息(T_SCZT_QSXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryRegQsxx(Map map) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_QSXX ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_QSXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.sourceflag = ?");
				list.add(sourceflag);
			}
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and a.priPid = ?");
				list.add(pripid);
			}
			
		}else{
			return null;
		}
		sql.append(" order by a.LigEndDate desc ");
		return dao.queryForList(sql.toString(), list);
	}
	
	/**
	 * 注吊销信息(T_SCZT_ZDXXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryRegZdxxx(Map map) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_ZDXXX ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_ZDXXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.sourceflag = ?");
				list.add(sourceflag);
			}
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and a.priPid = ?");
				list.add(pripid);
			}
			
		}else{
			return null;
		}
		sql.append(" order by a.CanDate desc ");
		return dao.queryForList(sql.toString(), list);
	}
	
	/**
	 * 变更信息(T_SCZT_BGXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryRegBgxx(Map map) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_BGXX ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_BGXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.sourceflag = ?");
				list.add(sourceflag);
			}
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and a.entityno = ?");
				list.add(pripid);
			}
			
		}else{
			return null;
		}
		//order by case when col is null then 0 else 1 end,col desc 
		sql.append(" order by case when AltDate is null then 0 else 1 end desc,AltDate desc ");
		return dao.queryForList(sql.toString(), list);
	}
	
	/**
	 * 归档信息(T_SCZT_GDXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryRegGdxx(Map map) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_GDXX ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_GDXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.sourceflag = ?");
				list.add(sourceflag);
			}
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and a.priPid = ?");
				list.add(pripid);
			}
			
		}else{
			return null;
		}
		//dao.queryForList(sql.toString(), list);
		return dao.pageQueryForList(sql.toString(), list);
	}
	
	/**
	 * 出资业务信息(T_SCZT_CZYWXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryRegCzywxx(Map map) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_CZYWXX ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_CZYWXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and sourceflag = ?");
				list.add(sourceflag);
			}
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and priPid = ?");
				list.add(pripid);
			}
			
		}else{
			return null;
		}
		return dao.queryForList(sql.toString(), list);
	}
	
	/**
	 * 业务环节信息(T_SCZT_YWHJXX)--
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryRegYwhjxx(Map map) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_YWHJXX ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_YWHJXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and sourceflag = ?");
				list.add(sourceflag);
			}
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and priPid = ?");
				list.add(pripid);
			}
			
		}else{
			return null;
		}
		return dao.queryForList(sql.toString(), list);
	}
	
	/**
	 * 法定代表人(T_SCZT_FDDBR)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryRegFddbr(Map map) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_FDDBR ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_FDDBR);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.sourceflag = ?");
				list.add(sourceflag);
			}
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and a.priPid = ?");
				list.add(pripid);
			}
			
		}else{
			return null;
		}
		return dao.queryForList(sql.toString(), list);
	}
	
	/**
	 * 名称基本信息(T_SCZT_MCJBXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryRegMcjbxx(Map map) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_MCJBXX ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_MCJBXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.sourceflag = ?");
				list.add(sourceflag);
			}
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and a.priPid = ?");
				list.add(pripid);
			}
			
		}else{
			return null;
		}
		return dao.queryForList(sql.toString(), list);
	}
	
	/**
	 * 人员信息表
	 * @param map
	 * @author chaihw
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryRegRyxx(Map map) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		//StringBuffer sql=new StringBuffer("select a.*,b.*,c.* from T_SCZT_GJCYXX a,T_SCZT_RYJBXX b,T_SCZT_RYQTZJXX c where a.pripid=b.RYJBXXID and b.RYJBXXID=c.RYQTZJXXID ");
		StringBuffer sql=new StringBuffer(Conts.V_NZWZ_RENYUANXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			//String sourceflag=(String)map.get("sourceflag"); 没有平台
			sql.append(" where 1=1 ");
/*			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.sourceflag =? ");
				list.add(sourceflag);
			}*/
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and priPid =? ");
				list.add(pripid);
			}
			//return dao.queryForList(sql.toString(), list);
			return dao.pageQueryForList(sql.toString(), list);
		}else{
			return null;
		}
	
	}
	
	/**
	 * 待办事宜(T_SCZT_DBSY)/待办附加信息(T_SCZT_DBFJXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryRegDbsy(Map map) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		//StringBuffer sql=new StringBuffer("select a.*,b.* from T_SCZT_DBSY a,T_SCZT_DBFJXX b where a.pripid=b.DBFJXXID ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_DBSY);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and sourceflag = ?");
				list.add(sourceflag);
			}
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and priPid = ?");
				list.add(pripid);
			}
			
		}else{
			return null;
		}
		return dao.queryForList(sql.toString(), list);
	}
	
	/**
	 * 投资人及出资信息(T_SCZT_TZRJCZXX)/投资人及出资其他信息(T_SCZT_TZRJCZQTXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryRegTzrjczxx(Map map) throws OptimusException{
		List<Map> returnMap  = new ArrayList<Map>();
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		//StringBuffer sql=new StringBuffer("select a.*,b.* from T_SCZT_TZRJCZXX a,T_SCZT_TZRJCZQTXX b where a.PriPID=b.TZRJCZQTXXID ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_TZRJCZXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
/*			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and sourceflag = ?");
				list.add(sourceflag);
			}*/
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" where priPid = ?");
				list.add(pripid);
				return 	dao.queryForList(sql.toString(), list);
			}else{
				return null;
			}
		
		}else{
			return null;
		}
	}
	
	
	/**
	 * 点击列表查询详细记录
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public Map queryRegXxInfo(Map<String, String> map,Map<String, String> param) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		StringBuffer sql=new StringBuffer("select * from ");
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String tableName = (String) map.get("tableName");
			String pkId = (String) map.get("pkId");
			String pripid = (String) map.get("priPid");
			if(StringUtils.isNotBlank(tableName)){
				sql.append(tableName);
			}
			if(StringUtils.isNotBlank(pkId)){
				
			}
			if(param!=null&&param.size()>0){
				sql.append(" where ");
				for (String key : param.keySet()) {
					//System.out.println("key= "+ key + " and value= " + map.get(key));
					sql.append(key);
					sql.append("= ?,");
					list.add(param.get(key));
				}
			}
		}else{
			return null;
		}
		String querySql = sql.toString();
		if(sql.toString().contains(",")){
			querySql = querySql.substring(0, querySql.length()-1);
		}
		return (Map) dao.queryForResultSet(querySql, list, null);
	}
	
	/**
	 * 统计信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public int queryRegCount(Map<String, String> param) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_COUNT);
		List<String> list = null;
		if(param!=null&&param.size()>0){
			sql.append(" where ");
			list = new ArrayList<String>();
			for (String key : param.keySet()) {
				//System.out.println("key= "+ key + " and value= " + map.get(key));
				sql.append(key);
				sql.append("= ?,");
				list.add(param.get(key));
			}
		}
		String querySql = sql.toString();
		if(sql.toString().contains(",")){
			querySql = querySql.substring(0, querySql.length()-1);
		}
		return dao.queryForInt(querySql, list);
	}

	public List<Map> queryRegTest() {
		    List<Map> data= new ArrayList<Map>();
		        String text="sssss";
		        String date1="2014-10-21";
		        String ischecklicence="0";
		        Map map=new HashMap();
		        map.put("text", text);
		        map.put("date1", date1);
		        map.put("ischecklicence",ischecklicence);
		        map.put("dom", "天河客运站");
		        data.add(map);
		        /*"checkboxfield="2,3",
		        "radiofield_name" : "1",
		        "password_name": "123",
		        "textarea_name": "sssssssss",
		        "combox_name": "2",
		        "hidden_name": "2333",
		        "comboxtree_name": ""*/
		
		
		return data;
	}

	/**
	 * 股权冻结
	 * @param params
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map> queryGuQuanDongJie(Map<String, String> params) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;												
		StringBuffer sql= new StringBuffer();
	
		sql.append(Conts.V_NZWZ_GUQUAN_DONGJIE); 
		List<String> list = new ArrayList<String>();
		if(params!=null&&params.size()>0){
			String pripid = (String) params.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and  priPid = ? ");
				list.add(pripid);
			}
			return dao.pageQueryForList(sql.toString(), list);	
		}else{
			return null;
		}
	}
	/**
	 * 股权出质
	 * @param params
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map> queryGuQuanChuZhi(Map<String, String> params) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;												
		StringBuffer sql= new StringBuffer();
	
		sql.append(Conts.V_NZWZ_GUQUAN_CHUZHI); 
		List<String> list = new ArrayList<String>();
		if(params!=null&&params.size()>0){
			String pripid = (String) params.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and  priPid = ? ");
				list.add(pripid);
			}
			return dao.pageQueryForList(sql.toString(), list);	
		}else{
			return null;
		}
	}
	/**
	 *注销信息
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public List<?> queryZhuXiaoXx(Map<String, String> params) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;												
		StringBuffer sql= new StringBuffer();
	
		sql.append(Conts.V_NZWZ_ZHUXIAOXX); 
		List<String> list = new ArrayList<String>();
		if(params!=null&&params.size()>0){
			String pripid = (String) params.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and  priPid = ? ");
				list.add(pripid);
			}
			return dao.pageQueryForList(sql.toString(), list);	
		}else{
			return null;
		}
	}
	/**
	 * 吊销信息
	 * @param params
	 * @return
	 * @throws OptimusException 
	 */
	public List<?> queryDiaoXiaoXx(Map<String, String> params) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;												
		StringBuffer sql= new StringBuffer();
	
		sql.append(Conts.V_NZWZ_DiaoXiaoXX); 
		List<String> list = new ArrayList<String>();
		if(params!=null&&params.size()>0){
			String pripid = (String) params.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and  priPid = ? ");
				list.add(pripid);
			}
			return dao.pageQueryForList(sql.toString(), list);	
		}else{
			return null;
		}
	}
	/**
	 * 许可信息
	 * @param params
	 * @return
	 * @throws OptimusException 
	 */
	public List<?> queryXukexx(Map<String, String> params) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;												
		StringBuffer sql= new StringBuffer();
	
		sql.append(Conts.V_NZWZ_XuKeXX); 
		List<String> list = new ArrayList<String>();
		if(params!=null&&params.size()>0){
			String pripid = (String) params.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and  priPid = ? ");
				list.add(pripid);
			}
			return dao.pageQueryForList(sql.toString(), list);	
		}else{
			return null;
		}
	}
	/**
	 * 迁移 信息
	 * @param params
	 * @return
	 * @throws OptimusException 
	 */
	public List<?> queryQianYixx(Map<String, String> params) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;												
		StringBuffer sql= new StringBuffer();
	
		sql.append(Conts.V_NZWZ_QianYiXX); 
		List<String> list = new ArrayList<String>();
		if(params!=null&&params.size()>0){
			String pripid = (String) params.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and  priPid = ? ");
				list.add(pripid);
			}
			return dao.pageQueryForList(sql.toString(), list);	
		}else{
			return null;
		}
	}
	/**
	 * 清算信息 以及清算成员信息
	 * @param params
	 * @param i
	 * @return
	 * @throws OptimusException
	 */
	public List<?> queryQingsuan(Map<String, String> params, int i) throws OptimusException {

		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;												
		StringBuffer sql= new StringBuffer();
	
		if (i==0){
			sql.append(Conts.V_NZWZ_QingsuanXX); 
		}else if(i==1){
			sql.append(Conts.V_NZWZ_QingsuanChengYuanXX); 	
		}

		List<String> list = new ArrayList<String>();
		if(params!=null&&params.size()>0){
			String pripid = (String) params.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and  priPid = ? ");
				list.add(pripid);
			}
			return dao.pageQueryForList(sql.toString(), list);	
		}else{
			return null;
		}
		
	}
	/**
	 * 内资外资财务负责人
	 * @param params
	 * @return
	 * @throws OptimusException 
	 */
	public List<?> queryCaiwunfuz(Map<String, String> params) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;												
		StringBuffer sql= new StringBuffer();
	
		sql.append(Conts.V_NZWZ_CaiWuFuZeXX); 
		List<String> list = new ArrayList<String>();
		if(params!=null&&params.size()>0){
			String pripid = (String) params.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and  priPid = ? ");
				list.add(pripid);
			}
			return dao.pageQueryForList(sql.toString(), list);	
		}else{
			return null;
		}
	}
	/**
	 * 联络员信息
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public List<?> queryLianluoyuanxx(Map<String, String> params) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;												
		StringBuffer sql= new StringBuffer();
	
		sql.append(Conts.V_NZWZ_LianLuoYuanXX); 
		List<String> list = new ArrayList<String>();
		if(params!=null&&params.size()>0){
			String pripid = (String) params.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and  priPid = ? ");
				list.add(pripid);
			}
			return dao.pageQueryForList(sql.toString(), list);	
		}else{
			return null;
		}
	}
	/**
	 * 个体详细信息
	 * 	个体经营者基本信息 ：	1001
		个体许可信息 ：	1002
		个体注销信息  1003	
		个体吊销信息：1004
	 * @param params
	 * @param i
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map> queryGeti(Map<String, String> params, int i) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;												
		StringBuffer sql= new StringBuffer();
		
		if(i==1001){
		sql.append(Conts.V_GETI_JINYIN); 
		}else if(i==1002){
			sql.setLength(0);
			sql.append(Conts.V_GETI_XUKE);
		}else if(i==1003){
			sql.setLength(0);
			sql.append(Conts.V_GETI_ZHUXIAO);
		}else if(i==1004){
			sql.setLength(0);
			sql.append(Conts.V_GETI_DIAOXIAO);
		}else if(i==1005){
			sql.setLength(0);
			sql.append(Conts.V_GETI_BIANGENG);
		}else{
			return null;
		}
		
		List<String> list = new ArrayList<String>();
		if(params!=null&&params.size()>0){
			String pripid = (String) params.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and  priPid = ? ");
				list.add(pripid);
			}
			return dao.pageQueryForList(sql.toString(), list);	
		}else{
			return null;
		}
	}
	/**
	 * 集团成员信息
	 * 集团成员信息： 4001	
	 * @param params
	 * @param i
	 * @return
	 * @throws OptimusException 
	 */
	public List<?> queryJiTuanXX(Map<String, String> params, int i) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;												
		StringBuffer sql= new StringBuffer();
		
		if(i==4001){
		sql.append(Conts.V_JITUAN_CHENGYUANXX); 
		}else if(i==4002){
			sql.setLength(0);
			sql.append(Conts.V_JITUAN_ZhuXiaoXX);
		}else if(i==4003){
			sql.setLength(0);
			sql.append(Conts.V_JITUAN_BianGengXX);
		}else{
			return null;
		}
		
		List<String> list = new ArrayList<String>();
		if(params!=null&&params.size()>0){
			String pripid = (String) params.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and  priPid = ? ");
				list.add(pripid);
			}
			return dao.pageQueryForList(sql.toString(), list);	
		}else{
			return null;
		}
	}
	/**
	 * 内资外资变更信息
	 * @param params
	 * @return
	 * @throws OptimusException 
	 */
	public List<?> queryNZWZBianGenXx(Map<String, String> params) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;												
		StringBuffer sql= new StringBuffer();
	
		sql.append(Conts.V_NZWZ_BianGengXX); 
		List<String> list = new ArrayList<String>();
		if(params!=null&&params.size()>0){
			String pripid = (String) params.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and  priPid = ? ");
				list.add(pripid);
			}
			
		sql.append("order  by altdate desc");
			return dao.pageQueryForList(sql.toString(), list);	
		}else{
			return null;
		}
	}
	/**
	 * 企业变更信息查询
	 * @param alterno
	 * @param alterbef
	 * @param alterafter
	 * @return
	 * @throws OptimusException 
	 */
	public List<?> queryAlterDetail(String alterno, String alterbef,
			String alterafter) throws OptimusException {
		
		
		
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;												
		StringBuffer sql= new StringBuffer();
	
		//sql.append(Conts.V_NZWZ_BianGenXX); 
		sql.append("SELECT  COALESCE(f_getcode('CD04','").append(alterno).append("'),'").append(alterno).append("') as altitemCn ,");
		sql.append("  COALESCE(f_getcode('CA16','").append(alterbef).append("'),'").append(alterbef).append("') as altbe ,");
		sql.append("  COALESCE(f_getcode('CA16','").append(alterafter).append("'),'").append(alterafter).append("') as altaf ");
		sql.append( " from dual");
		List<String> list = new ArrayList<String>();

		return dao.pageQueryForList(sql.toString(), null);	


	}
	
	public static void main(String []args){
		String alterno ="111";
		StringBuffer sql= new StringBuffer();
		sql.append("  COALESCE(f_getcode('CD04',").append(alterno).append("),").append(alterno).append(") as altitemCn");
		System.out.println(sql);
	}
	
	
}





