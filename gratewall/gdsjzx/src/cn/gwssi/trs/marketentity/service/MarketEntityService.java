package cn.gwssi.trs.marketentity.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import cn.gwssi.resource.CodeToValue;
import cn.gwssi.resource.Conts;

@Service
public class MarketEntityService extends BaseService{

	/**
	 * 从数据库T_SCZT_JBXXINHTML获取需要展示基本信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	/**
	 * 1.公司
	 * 2.个体工商户
	 * 3.合伙企业
	 * 4.农民专业合作经济
	 * 5.个人独资
	 * 6.外国（地区）企业分支机构信息
	 * 7.外资非公司企业
	 * 10案件基本信息（後來加的）
	 * */
	public Map findshowRegJbxx(String type) throws OptimusException{
		Map map=new LinkedHashMap();
		String sql=null;
		IPersistenceDAO dao= this.getPersistenceDAO();
		if(type!=null && type.trim()!=""){
			if("2".equals(type)){
				sql="select * from T_SCZT_JBXXINHTML where state='1' and type = '3'";
			}else if("3".equals(type)){
				sql="select * from T_SCZT_JBXXINHTML where state='1' and type = '5' order by sort";
			}else if("1".equals(type) || "4".equals(type) || "5".equals(type)){
				sql="select * from T_SCZT_JBXXINHTML where state='1' and type = '1' order by sort";
			}else if("6".equals(type)){
				sql="select * from T_SCZT_JBXXINHTML where state='1' and type = '6' order by sort";
			}else if( "7".equals(type)){
				sql="select * from T_SCZT_JBXXINHTML where state='1' and type = '7' order by sort";
			}else if( "9".equals(type)){
				sql="select * from T_SCZT_JBXXINHTML where state='1' and type = '4'  order by sort";
			}else if( "0".equals(type)){
				sql="select * from T_SCZT_JBXXINHTML where state='1' and type = '2' order by sort";
			}else{
				sql="select * from T_SCZT_JBXXINHTML where state='1' and type = '1' order by sort";
			}
		}else{
			sql="select * from T_SCZT_JBXXINHTML where state='1' and type = '1' order by sort";
		}
		List<Map> datalist=dao.queryForList(sql.toString(), null);
		for(int i=0;i<datalist.size();i++){
			Map<String,String> m=datalist.get(i);
			// for (String key : m.keySet()) {
			map.put(m.get("fieldcn"), m.get("fieldeng"));
			//	  }
		}
		return map;
	}

	/**
	 * 市场主体标记信息(T_SCZT_SCZTBJXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findRegBjxx(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
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
	 * 冻结股东信息(T_SCZT_DJGDXX)--
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findRegDjgdxx(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
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
	 * 归档信息(T_SCZT_GDXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findRegGdxx(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
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
		return dao.queryForList(sql.toString(), list);
	}

	/**
	 * 出资业务信息(T_SCZT_CZYWXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findRegCzywxx(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
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
	public List<Map> findRegYwhjxx(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
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
	public List<Map> findRegFddbr(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
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
	public List<Map> findRegMcjbxx(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
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
	 * 待办事宜(T_SCZT_DBSY)/待办附加信息(T_SCZT_DBFJXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findRegDbsy(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
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
	 * 点击列表查询详细记录
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public Map findRegXxInfo(Map<String, String> map,Map<String, String> param) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
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
	public int findRegCount(Map<String, String> param) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
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

	public List<Map> findRegTest() {
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
		return data;
	}
	
	/**
	 * 市场主体基本信息(T_SCZT_SCZTJBXX)、补充(T_SCZT_SCZTBCXX)、隶属(T_SCZT_SCZTLSXX)、外国(地区)企业在中国从事生产经营活动基本信息(补充)(T_SCZT_WGQYSCJYHDJBXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findRegJbxx(Map map,String type) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		//StringBuffer sql=new StringBuffer("select a.*,b.*,c.*,d.* from T_SCZT_SCZTJBXX a,T_SCZT_SCZTBCXX b,T_SCZT_SCZTLSXX c,T_SCZT_WGQYSCJYHDJBXX d where a.pripid=b.pripid and a.pripid=c.pripid and a.pripid=d.WGQYSCJYHDJBXXID ");
		StringBuffer sql=null;
		if("0".equals(type)){
			sql=new StringBuffer(Conts.SCZTJBXXSQL);
		}else if("1".equals(type)){
			sql=new StringBuffer(Conts.SCZTJBXXSQLGT);
		}else if("2".equals(type)){
			sql=new StringBuffer(Conts.SCZTJBXXSQLWZ);
		}else{
			sql=new StringBuffer(Conts.SCZTJBXXSQL);
		}
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
				sql.append(" and a.priPid = ? ");
				list.add(pripid);
			}
		}else{
			return null;
		}
		return dao.queryForList(sql.toString(), list);
	}

	/**
	 * 市场主体隶属信息补充信息(T_SCZT_SCZTLSBCXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findRegLsxx(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_SCZTLSBCXX ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_SCZTLSXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.sourceflag = '");
				sql.append(sourceflag);
				sql.append("'");
				list.add(sourceflag);
			}
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and a.priPid = '");
				sql.append(pripid);
				sql.append("'");
				list.add(pripid);
			}
			sql.append("  and a.entname is not null ");

		}else{
			return null;
		}
		sql.append(" order by EstDate desc");
		List<Map> resultList = dao.pageQueryForList(sql.toString(), null);
		if(resultList!=null && resultList.size()>0){
			for(Map m : resultList){
				m.put("regorg", CodeToValue.codeToValue("T_DM_djgXjGdm",(String)m.get("regorg")));
				m.put("country", CodeToValue.codeToValue("t_dm_gjdqdm",(String)m.get("country")));
				m.put("enterprisetype", CodeToValue.codeToValue("t_dm_qylxdm",(String)m.get("enterprisetype")));
				m.put("regcapcur", CodeToValue.codeToValue("t_dm_bzdm",(String)m.get("regcapcur")));
			}
		}
		return resultList;
	}
	
	/**
	 * 迁入信息(T_SCZT_QRXX)
	 * 迁出信息(T_SCZT_QCXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findRegQrQcxx(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_QRXX ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_QRQCXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and sourceflag = '").append(sourceflag).append("'");
				list.add(sourceflag);
			}
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and pripid = '").append(pripid).append("'");
				list.add(pripid);
			}

		}else{
			return null;
		}
		sql.append(" order by mdate desc");
		List<Map> resultList = dao.pageQueryForList(sql.toString(), null);
		if(resultList!=null && resultList.size()>0){
			for(Map m : resultList){
				m.put("moutareregorg", CodeToValue.codeToValue("T_DM_GSGLJGDM",(String)m.get("moutareregorg")));
				m.put("archremovemode", CodeToValue.codeToValue("T_DM_YJFSDM",(String)m.get("archremovemode")));
			}
		}
		return resultList;
	}

	/**
	 * 股权冻结信息(T_SCZT_GQDJXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findRegGqdjxx(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_GQDJXX ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_GQDJXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.sourceflag = '").append(sourceflag).append("'");
				list.add(sourceflag);
			}
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and a.priPid = '").append(pripid).append("'");
				list.add(pripid);
			}

		}else{
			return null;
		}
		sql.append(" order by a.frofrom desc ");
		List<Map> resultList = dao.pageQueryForList(sql.toString(), null);
		if(resultList!=null && resultList.size()>0){
			for(Map m : resultList){
				m.put("frozsign", CodeToValue.codeToValue("T_DM_DJZTDM",(String)m.get("frozsign")));
			}
		}
		return resultList;
	}

	/**
	 * 股权出质信息(T_SCZT_GQCZXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findRegGqczxx(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_GQCZXX ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_GQCZXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and b.sourceflag = '").append(sourceflag).append("'");
				list.add(sourceflag);
			}
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and b.priPid = '").append(pripid).append("'");
				list.add(pripid);
			}

		}else{
			return null;
		}
		List<Map> resultList = dao.pageQueryForList(sql.toString(), null);
		if(resultList!=null && resultList.size()>0){
			for(Map m : resultList){
				m.put("regstatus", CodeToValue.codeToValue("T_DM_OLD_JYZTDM",(String)m.get("regstatus")));
				m.put("regorg", CodeToValue.codeToValue("T_DM_djgXjGdm",(String)m.get("regorg")));
			}
		}
		return resultList;
	}

	/**
	 * 变更信息(T_SCZT_BGXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findRegBgxx(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_BGXX ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_BGXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.sourceflag = '").append(sourceflag).append("'");
				list.add(sourceflag);
			}
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and a.entityno = '").append(pripid).append("'");
				list.add(pripid);
			}
			sql.append(" and a.altitem is not null ");

		}else{
			return null;
		}
		//order by case when col is null then 0 else 1 end,col desc 
		sql.append(" order by case when AltDate is null then 0 else 1 end desc,AltDate desc ");
		List<Map> resultList = dao.pageQueryForList(sql.toString(), null);
		if(resultList!=null && resultList.size()>0){
			for(Map m : resultList){
				m.put("altitem", CodeToValue.codeToValue("T_DM_BGBASX",(String)m.get("altitem")));
			}
		}
		return resultList;
	}
	
	/**
	 * 清算信息(T_SCZT_QSXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findRegQsxx(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_QSXX ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_QSXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.sourceflag = '").append(sourceflag).append("'");
				list.add(sourceflag);
			}
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and a.priPid = '").append(pripid).append("'");
				list.add(pripid);
			}

		}else{
			return null;
		}
		sql.append(" order by a.LigEndDate desc ");
		List<Map> resultList = dao.pageQueryForList(sql.toString(), null);
		return resultList;
	}
	
	/**
	 * 注吊销信息(T_SCZT_ZDXXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findRegZdxxx(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_ZDXXX ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_ZDXXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.sourceflag = '").append(sourceflag).append("'");
				list.add(sourceflag);
			}
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and a.priPid = '").append(pripid).append("'");
				list.add(pripid);
			}

		}else{
			return null;
		}
		sql.append(" order by a.CanDate desc ");
		List<Map> resultList = dao.pageQueryForList(sql.toString(), null);
		if(resultList!=null && resultList.size()>0){
			for(Map m : resultList){
				m.put("canrea", CodeToValue.codeToValue("T_DM_ZXYYDM",(String)m.get("canrea")));
			}
		}
		return resultList;
	}
	
	/**
	 * 证照信息(T_SCZT_ZZXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findRegZzxx(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_ZZXX ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_ZZXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.sourceflag = '").append(sourceflag).append("'");
				list.add(sourceflag);
			}
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and a.priPid = '").append(pripid).append("'");
				list.add(pripid);
			}

		}else{
			return null;
		}
		sql.append(" order by a.valfrom desc ");
		List<Map> resultList = dao.pageQueryForList(sql.toString(), null);
		if(resultList!=null && resultList.size()>0){
			for(Map m : resultList){
				m.put("blictype", CodeToValue.codeToValue("T_DM_ZZLXDM",(String)m.get("blictype")));
			}
		}
		return resultList;
	}

	/**
	 * 自热人投资人及出资信息(T_SCZT_TZRJCZXX)/投资人及出资其他信息(T_SCZT_TZRJCZQTXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findRegTzrjczxx(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		//StringBuffer sql=new StringBuffer("select a.*,b.* from T_SCZT_TZRJCZXX a,T_SCZT_TZRJCZQTXX b where a.PriPID=b.TZRJCZQTXXID ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_ZRTZRJCZXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and c.sourceflag = '").append(sourceflag).append("'");
				list.add(sourceflag);
			}
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and c.priPid = '").append(pripid).append("'");
				list.add(pripid);
			}

		}else{
			return null;
		}
		List<Map> resultList = dao.pageQueryForList(sql.toString(), null);
		if(resultList!=null && resultList.size()>0){
			for(Map m : resultList){
				m.put("certype", CodeToValue.codeToValue("t_dm_zjlxdm",(String)m.get("certype")));
				m.put("currency", CodeToValue.codeToValue("t_dm_bzdm",(String)m.get("currency")));
				m.put("country", CodeToValue.codeToValue("t_dm_gjdqdm",(String)m.get("country")));
			}
		}
		return resultList;
	}
	
	/**
	 * 高级成员信息(T_SCZT_GJCYXX)/人员基本信息(T_SCZT_RYJBXX)/人员其他证件信息(T_SCZT_RYQTZJXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findRegRyxx(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		//StringBuffer sql=new StringBuffer("select a.*,b.*,c.* from T_SCZT_GJCYXX a,T_SCZT_RYJBXX b,T_SCZT_RYQTZJXX c where a.pripid=b.RYJBXXID and b.RYJBXXID=c.RYQTZJXXID ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_RYXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.sourceflag ='").append(sourceflag).append("'");
				list.add(sourceflag);
			}
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and a.priPid ='").append(pripid).append("'");
				list.add(pripid);
			}

		}else{
			return null;
		}
		List<Map> resultList = dao.pageQueryForList(sql.toString(), null);
		if(resultList!=null && resultList.size()>0){
			for(Map m : resultList){
				m.put("posbrform", CodeToValue.codeToValue("t_dm_zwcsfsdm",(String)m.get("posbrform")));
				m.put("sex", CodeToValue.codeToValue("t_dm_xbdm",(String)m.get("sex")));
				m.put("certype", CodeToValue.codeToValue("t_dm_zjlxdm",(String)m.get("certype")));
				m.put("country", CodeToValue.codeToValue("t_dm_gjdqdm",(String)m.get("country")));
				m.put("polstand", CodeToValue.codeToValue("T_DM_ZZMMDM",(String)m.get("polstand")));
				m.put("litedeg", CodeToValue.codeToValue("T_DM_WHCD",(String)m.get("litedeg")));
				m.put("nation", CodeToValue.codeToValue("T_DM_MZDM",(String)m.get("nation")));
			}
		}
		return resultList;
	}
	
	/**
	 * 法人投资人及出资信息(T_SCZT_TZRJCZXX)/投资人及出资其他信息(T_SCZT_TZRJCZQTXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findFrTzrjczxx(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		//StringBuffer sql=new StringBuffer("select a.*,b.* from T_SCZT_TZRJCZXX a,T_SCZT_TZRJCZQTXX b where a.PriPID=b.TZRJCZQTXXID ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_FRTZRJCZXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.sourceflag = '").append(sourceflag).append("'");
				list.add(sourceflag);
			}
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and a.priPid = '").append(pripid).append("'");
				list.add(pripid);
			}

		}else{
			return null;
		}
		List<Map> resultList = dao.pageQueryForList(sql.toString(), null);
		if(resultList!=null && resultList.size()>0){
			for(Map m : resultList){
				m.put("certype", CodeToValue.codeToValue("t_dm_zjlxdm",(String)m.get("certype")));
				m.put("currency", CodeToValue.codeToValue("t_dm_bzdm",(String)m.get("currency")));
				m.put("country", CodeToValue.codeToValue("t_dm_gjdqdm",(String)m.get("country")));
			}
		}
		return resultList;
	}
	
	/**
	 * 守重
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findszInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_SCJG_SZJBXXZSB);
		if(map!=null&&map.size()>0){
			String priPid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.sourceflag = '").append(sourceflag).append("' ");
			}
			if(StringUtils.isNotBlank(priPid)){
				sql.append(" and a.entityNo = '").append(priPid).append("' ");
			}
		}else{
			return null;
		}
		List<Map> resultList = dao.pageQueryForList(sql.toString(), null);
		if(resultList!=null && resultList.size()>0){
			for(Map m : resultList){
				m.put("approvedeptid", CodeToValue.codeToValue("T_DM_djgXjGdm",(String)m.get("approvedeptid")));
				m.put("acceptdeptid", CodeToValue.codeToValue("T_DM_djgXjGdm",(String)m.get("acceptdeptid")));
				m.put("issuedeptid", CodeToValue.codeToValue("T_DM_djgXjGdm",(String)m.get("issuedeptid")));
				m.put("aicid", CodeToValue.codeToValue("T_DM_djgXjGdm",(String)m.get("aicid")));
				m.put("regcapitalcoin", CodeToValue.codeToValue("T_DM_BZDM",(String)m.get("regcapitalcoin")));
				m.put("economicproperty", CodeToValue.codeToValue("T_DM_JJXZDM",(String)m.get("economicproperty")));
				m.put("statusid", CodeToValue.codeToValue("T_DM_SZZT",(String)m.get("statusid")));
				m.put("entitytypeid", CodeToValue.codeToValue("T_DM_QYLXDM",(String)m.get("entitytypeid")));
			}
		}
		return resultList;
	}
	
	/**
	 * 合同签订履行信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findHtqdlxInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_SCJG_HTQDLXQKZSB);
		sql.append(",T_SCJG_SZJBXXZSB b where a.curCompactCreditID=b.curCompactCreditID and a.sourceflag=b.sourceflag");
		if(map!=null&&map.size()>0){
			String priPid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and b.sourceflag = '").append(sourceflag).append("' ");
			}
			if(StringUtils.isNotBlank(priPid)){
				sql.append(" and b.entityNo = '").append(priPid).append("' ");
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * 合同管理机构正式表
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findHtgljgInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_SCJG_HTGLJGZSB);
		sql.append(",T_SCJG_SZJBXXZSB b where a.curCompactCreditID=b.curCompactCreditID and a.sourceflag=b.sourceflag");
		if(map!=null&&map.size()>0){
			String priPid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and b.sourceflag = '").append(sourceflag).append("' ");
			}
			if(StringUtils.isNotBlank(priPid)){
				sql.append(" and b.entityNo = '").append(priPid).append("' ");
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * 拍卖信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findPmInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_SCJG_PMXX);
		sql.append(",t_sczt_scztjbxx b where a.auctionCorpRegNo=b.regno and a.sourceflag=b.sourceflag");
		if(map!=null&&map.size()>0){
			String priPid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and b.sourceflag = '").append(sourceflag).append("' ");
			}
			if(StringUtils.isNotBlank(priPid)){
				sql.append(" and b.priPid = '").append(priPid).append("' ");
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * 拍卖后备案信息 
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findPmhbaInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_SCJG_PMHBAXX);
		sql.append(",(select b.historyInfoID from T_SCJG_PMXX b,t_sczt_scztjbxx c where b.auctionCorpRegNo=c.regno and b.sourceflag=c.sourceflag ");
		if(map!=null&&map.size()>0){
			String priPid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and c.sourceflag = '").append(sourceflag).append("' ");
			}
			if(StringUtils.isNotBlank(priPid)){
				sql.append(" and c.priPid = '").append(priPid).append("' ");
			}
		}else{
			return null;
		}
		sql.append(" ) c where c.historyInfoID=a.AuctionID");
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * 拍卖后备案请求核准结果  
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findPmhbajgInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_SCJG_PMHBAQQHZJG);
		sql.append(",T_SCJG_SZJBXXZSB b where a.curCompactCreditID=b.curCompactCreditID and a.sourceflag=b.sourceflag");
		if(map!=null&&map.size()>0){
			String regno = (String) map.get("regno");
			String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and b.sourceflag = '").append(sourceflag).append("' ");
			}
			if(StringUtils.isNotBlank(regno)){
				sql.append(" and b.registerNo = '").append(regno).append("' ");
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * 拍卖前备案请求核准结果 
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findPmqbajgInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_SCJG_PMQBAQQHZJG);
		sql.append(",T_SCJG_SZJBXXZSB b where a.curCompactCreditID=b.curCompactCreditID and a.sourceflag=b.sourceflag");
		if(map!=null&&map.size()>0){
			String regno = (String) map.get("regno");
			String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and b.sourceflag = '").append(sourceflag).append("' ");
			}
			if(StringUtils.isNotBlank(regno)){
				sql.append(" and b.registerNo = '").append(regno).append("' ");
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * 抵押登记
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findDydjInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_SCJG_DYDJYWZS);
		sql.append(",t_ywbl_dbsy b where a.bizSequence=b.DBSYID and a.SOURCEFLAG=b.SOURCEFLAG and b.pripid is not null");
		if(map!=null&&map.size()>0){
			String priPid = (String) map.get("priPid");
			/*String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and b.sourceflag = '").append(sourceflag).append("' ");
			}*/
			if(StringUtils.isNotBlank(priPid)){
				sql.append(" and b.priPid = '").append(priPid).append("' ");
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * 抵押人情况
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findDyrqkInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_SCJG_DYRQKZS);
		sql.append(" where 1=1 ");
		if(map!=null&&map.size()>0){
			String priPid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.sourceflag = '").append(sourceflag).append("' ");
			}
			if(StringUtils.isNotBlank(priPid)){
				sql.append(" and a.priPid = '").append(priPid).append("' ");
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * 抵押物清单
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findDywqdInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_SCJG_DYWQDZS);
		sql.append(",(select b.pledgeid from T_SCJG_DYDJYWZS b,t_ywbl_dbsy c where b.bizSequence=c.DBSYID and b.SOURCEFLAG=c.SOURCEFLAG and c.pripid is not null ");
		if(map!=null&&map.size()>0){
			String priPid = (String) map.get("priPid");
			/*String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and b.sourceflag = '").append(sourceflag).append("' ");
			}*/
			if(StringUtils.isNotBlank(priPid)){
				sql.append(" and c.priPid = '").append(priPid).append("' ");
			}
		}else{
			return null;
		}
		sql.append(") d where a.pledgeid = d.pledgeid");
		if(map!=null&&map.size()>0){
			String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.sourceflag = '").append(sourceflag).append("' ");
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * 抵押人信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findDyrInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_SCJG_DYRXX);
		sql.append(" where 1=1");
		if(map!=null&&map.size()>0){
			String priPid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(priPid)){
				sql.append(" and a.PriPID = '").append(priPid).append("' ");
			}
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.SOURCEFLAG = '").append(sourceflag).append("' ");
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * 抵押权人信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findDyqrInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_SCJG_DYQRXX);
		sql.append(" where 1=1");
		if(map!=null&&map.size()>0){
			String priPid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(priPid)){
				sql.append(" and a.PriPID = '").append(priPid).append("' ");
			}
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.SOURCEFLAG = '").append(sourceflag).append("' ");
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * 其他行政處罰(其他)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findOtherXzcfInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_QTBM_XZCF);
		sql.append(" where 1=1");
		if(map!=null&&map.size()>0){
			String priPid = (String) map.get("priPid");
			if(StringUtils.isNotBlank(priPid)){
				sql.append(" and a.entno = '").append(priPid).append("' ");
			}
		}else{
			return null;
		}
		return dao.queryForList(sql.toString(), null);
	}
	
	/**
	 * 其他行政許可(其他)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findOtherXzxkInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_QTBM_XZXK);
		sql.append(" where 1=1");
		if(map!=null&&map.size()>0){
			String priPid = (String) map.get("priPid");
			if(StringUtils.isNotBlank(priPid)){
				sql.append(" and a.entno = '").append(priPid).append("' ");
			}
		}else{
			return null;
		}
		return dao.queryForList(sql.toString(), null);
	}
	
	/**
	 * (商標)基本信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findSbInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_SB_SBJBXX);
		sql.append(" where 1=1");
		if(map!=null&&map.size()>0){
			String regno = (String) map.get("regno");
			if(StringUtils.isNotBlank(regno)){
				sql.append(" and a.RegNO = '").append(regno).append("' ");
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * (商標)商品信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findSbspInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_SB_SPXX);
		sql.append(" where 1=1");
		if(map!=null&&map.size()>0){
			String regno = (String) map.get("regno");
			if(StringUtils.isNotBlank(regno)){
				sql.append(" and a.RegNO = '").append(regno).append("' ");
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * (商標)商标代理人
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findSbdlrInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_SB_DLRXX);
		sql.append(",T_SB_SBJBXX b where a.code=b.agentid ");
		if(map!=null&&map.size()>0){
			String regno = (String) map.get("regno");
			if(StringUtils.isNotBlank(regno)){
				sql.append(" and b.RegNO = '").append(regno).append("' ");
			}
		}else{
			return null;
		}
		List<Map> resultList = dao.pageQueryForList(sql.toString(), null);
		if(resultList!=null && resultList.size()>0){
			for(Map m : resultList){
				m.put("agentdistrict", CodeToValue.codeToValue("t_dm_sbregional",(String)m.get("agentdistrict")));
			}
		}
		return resultList;
	}
	
	/**
	 * (商標)商标共有人
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findSbgyrInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_SB_GYRXX);
		sql.append(" where 1=1");
		if(map!=null&&map.size()>0){
			String regno = (String) map.get("regno");
			if(StringUtils.isNotBlank(regno)){
				sql.append(" and a.RegNO = '").append(regno).append("' ");
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * (商標)商标申请人
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findSbsqrInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_SB_SBSQRXX);
		sql.append(" where 1=1");
		if(map!=null&&map.size()>0){
			String regno = (String) map.get("regno");
			if(StringUtils.isNotBlank(regno)){
				sql.append(" and a.RegNO = '").append(regno).append("' ");
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * (老赖)失信违法被执行人
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findLaolaiInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.LAOLAIDETAIL);
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(sourceflag) && StringUtils.isNotBlank(pripid)){
				//sql.append(",(select c.inv name,'法人' tp from t_sczt_frtzrjczxx c where c.sourceflag ='").append(sourceflag).append("' and c.priPid ='").append(pripid).append("'");
				//sql.append(" union all");
				sql.append(",(select c.cerno cerno,'自然人' tp from t_sczt_zrrtzrjczxx c where c.sourceflag = '").append(sourceflag).append("' and c.priPid ='").append(pripid).append("'");
				sql.append(" union all");
				sql.append(" select b.cerno cerno,'高级人员' tp from t_sczt_gjcyxx c left join t_sczt_ryjbxx b on (c.personid = b.ryjbxxid and c.SOURCEFLAG=b.SOURCEFLAG) where c.sourceflag = '").append(sourceflag).append("' and c.priPid ='").append(pripid).append("') d where a.cardnum = d.cerno");
			}else{
				return null;
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * (农资)农资企业信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findnzqyInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_NZ_NZQYXX);
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			//String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" where a.Pripid='").append(pripid).append("'");
			}else{
				return null;
			}
		}else{
			return null;
		}
		List<Map> resultList = dao.pageQueryForList(sql.toString(), null);
		if(resultList!=null && resultList.size()>0){
			for(Map m : resultList){
				m.put("enttype", CodeToValue.codeToValue("T_DM_12315ENTTYPE",(String)m.get("enttype")));
				m.put("locprov", CodeToValue.codeToValue("T_DM_caseVenSpDistrict",(String)m.get("locprov")));
				
			}
		}
		return resultList;
	}
	
	/**
	 * (农资)农资供销企业信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findnzgxqyInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_NZ_NZGXQYXX);
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			//String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" where a.Pripid='").append(pripid).append("'");
			}else{
				return null;
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * (农资)农资企业商品信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findnzqysbInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_NZ_NZQYSPXX);
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			//String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" where a.Pripid='").append(pripid).append("'");
			}else{
				return null;
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * (农资)农资商品标准信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findnzspbzInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_NZ_NZSPBZXX).append(",T_NZ_NZQYSPXX b where a.MDSEName=b.MDSEName");
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and b.Pripid='").append(pripid).append("'");
			}else{
				return null;
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * (农资)抽样工单信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findcygdInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_NZ_CYGDB);
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			//String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" where a.Pripid='").append(pripid).append("'");
			}else{
				return null;
			}
		}else{

			return null;
		}
		List<Map> resultList = dao.pageQueryForList(sql.toString(), null);
		if(resultList!=null && resultList.size()>0){
			for(Map m : resultList){
				m.put("locprov", CodeToValue.codeToValue("T_DM_caseVenSpDistrict",(String)m.get("locprov")));
			}
		}
		return resultList;
	}
	
	/**
	 * (农资)检测报告信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findjcbgInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_NZ_JCBG).append(",T_NZ_CYGDB b where a.sampleID=b.SampleBillID ");
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and b.Pripid='").append(pripid).append("'");
			}else{
				return null;
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * (农资)检验项目结果信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findjyxmjgInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_NZ_JCXMJG).append(",T_NZ_JCBG b,T_NZ_CYGDB c where a.checkReportID=b.CheckReportID and b.sampleID=c.SampleBillID");
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and c.Pripid='").append(pripid).append("'");
			}else{
				return null;
			}
		}else{
			return null;
		}
		List<Map> resultList = dao.pageQueryForList(sql.toString(), null);
		if(resultList!=null && resultList.size()>0){
			for(Map m : resultList){
				m.put("meaunit", CodeToValue.codeToValue("T_DM_12315MEAUNIT",(String)m.get("meaunit")));
			}
		}
		return resultList;
	}
	
	/**
	 * (案件)基本信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findanjianInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_AJ_AJJBXX);
		sql.append(" left join t_aj_dsrxx b on a.caseid=b.caseid where ");
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			//String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" b.priPid ='").append(pripid).append("'");
			}else{
				return null;
			}
		}else{
			return null;
		}
		List<Map> resultList = dao.pageQueryForList(sql.toString(), null);
		if(resultList!=null && resultList.size()>0){
			for(Map m : resultList){
				m.put("exesort", CodeToValue.codeToValue("T_DM_ZXLB",(String)m.get("exesort")));
				m.put("unexereasort", CodeToValue.codeToValue("T_DM_WZXYYLB",(String)m.get("unexereasort")));
			}
		}
		return resultList;
	}
	
	/**
	 * (案件)违法行为及处罚信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findwfxwjcfInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_AJ_WFXWJCFXX);
		//sql.append(" left join t_aj_ajjbxx d on a.caseid=d.caseid left join t_aj_dsrxx b on d.caseid=b.caseid left join t_sczt_scztjbxx c (index SCZTJBXXINDEX) on b.pripid=c.pripid where ");
		sql.append(" left join t_aj_ajjbxx d on a.caseid=d.caseid left join t_aj_dsrxx b on d.caseid=b.caseid where ");
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			//String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" b.priPid ='").append(pripid).append("'");
			}else{
				return null;
			}
		}else{
			return null;
		}
		List<Map> resultList = dao.pageQueryForList(sql.toString(), null);
		if(resultList!=null && resultList.size()>0){
			for(Map m : resultList){
				m.put("illegacttype", CodeToValue.codeToValue("T_DM_AJ_WFXWZL",(String)m.get("illegacttype")));
				m.put("pentype", CodeToValue.codeToValue("T_DM_casePENTYPE",(String)m.get("pentype")));
			}
		}
		return resultList;
	}
	
	/**
	 * (案件)当事人信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> finddsrInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_AJ_DSRXX);
		//sql.append(" left join t_sczt_scztjbxx c (index SCZTJBXXINDEX) on a.pripid=c.pripid where ");
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			//String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" (index AJDSRXXINDEX) where a.priPid ='").append(pripid).append("'");
			}else{
				return null;
			}
		}else{
			return null;
		}
		List<Map> resultList = dao.pageQueryForList(sql.toString(), null);
		if(resultList!=null && resultList.size()>0){
			for(Map m : resultList){
				m.put("enttype", CodeToValue.codeToValue("T_DM_12315ENTTYPE",(String)m.get("enttype")));
			}
		}
		return resultList;
	}
	
	/**
	 * (案件)行政处罚决定书
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findxzcfjdsInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_AJ_XZCFJDS);
		sql.append(" left join t_sczt_scztjbxx b on a.RegNO=str_replace(b.RegNO,' ',null) where ");
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(sourceflag) && StringUtils.isNotBlank(pripid)){
				sql.append(" b.sourceflag ='").append(sourceflag).append("' and b.priPid ='").append(pripid).append("'");
			}else{
				return null;
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * 广告经营基本信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findggjyjbInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_GG_GGJYJBXX);
		//sql.append(" left join t_sczt_scztjbxx b (index SCZTJBXXINDEX) on a.priPid=b.priPid where ");
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			//String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(pripid)){
				//sql.append(" b.sourceflag ='").append(sourceflag).append("' and b.priPid ='").append(pripid).append("'");
				sql.append(" where a.priPid ='").append(pripid).append("'");
			}else{
				return null;
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * (广告)从业人员信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findggcyryInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_GG_CYRYXX);
		sql.append(" left join t_sczt_ryjbxx b(index RYJBXXINDEX) on a.name=str_replace(b.name,' ',null) left join t_sczt_gjcyxx c on (c.personid = b.ryjbxxid and c.SOURCEFLAG=b.SOURCEFLAG) where ");
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			//String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(pripid)){
				//sql.append(" b.sourceflag ='").append(sourceflag).append("' and b.priPid ='").append(pripid).append("'");
				sql.append(" c.priPid ='").append(pripid).append("'");
			}else{
				return null;
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * 广告审查员信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findggscrInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_GG_GGSCYGGZYRYXX);
		sql.append(" left join t_sczt_ryjbxx b(index RYJBXXINDEX) on a.name=b.name left join t_sczt_gjcyxx c on (c.personid = b.ryjbxxid and c.SOURCEFLAG=b.SOURCEFLAG) where ");
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			//String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(pripid)){
				//sql.append(" b.sourceflag ='").append(sourceflag).append("' and b.priPid ='").append(pripid).append("'");
				sql.append(" c.priPid ='").append(pripid).append("'");
			}else{
				return null;
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * 广告分支机构信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findggfzjgInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_GG_GGFZBSJGSLXX);
		sql.append(",T_SCZT_SCZTJBXX b WHERE a.RegNO=str_replace(b.RegNO,' ',null) ");
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(pripid)&&StringUtils.isNotBlank(sourceflag)){
				sql.append(" and b.sourceflag ='").append(sourceflag).append("' and b.priPid ='").append(pripid).append("'");
				//sql.append("' and a.priPid ='").append(pripid).append("'");
			}else{
				return null;
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * 广告监管行政措施信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findggjgxzcsInfo(Map map) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_GG_GGJGXZCSXX);
		sql.append(",T_GG_GGJYJBXX b where a.AdProprietor=b.AdProprietor");
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			//String sourceflag=(String)map.get("sourceflag");
			if(StringUtils.isNotBlank(pripid)){
				//sql.append(" b.sourceflag ='").append(sourceflag).append("' and b.priPid ='").append(pripid).append("'");
				sql.append(" and b.priPid ='").append(pripid).append("'");
			}else{
				return null;
			}
		}else{
			return null;
		}
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * 异常名录
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findYCMLxx(Map<String, String> params) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_CZYWXX ");
		StringBuffer sql=new StringBuffer(Conts.T_JYYCML_QYGTNZ);
		if(params!=null&&params.size()>0){
			String pripid = (String) params.get("priPid");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and a.priPid = '");
				sql.append(pripid);
				sql.append("'");
			}
		}else{
			return null;
		}
		List<Map> resultList = dao.pageQueryForList(sql.toString(), null);
		if(resultList!=null && resultList.size()>0){
			for(Map m : resultList){
				m.put("decorg", CodeToValue.codeToValue("T_DM_djgXjGdm",(String)m.get("decorg")));
				if("企业".equals((String)m.get("biaoji"))){
					m.put("specause", CodeToValue.codeToValue("T_DM_LRYCMLYY",(String)m.get("specause")));
					m.put("remexcpres", CodeToValue.codeToValue("T_DM_YCYCMLYY",(String)m.get("remexcpres")));
				}else if("农专".equals((String)m.get("biaoji"))){
					m.put("specause", CodeToValue.codeToValue("T_DM_NZLRYCMLYY",(String)m.get("specause")));
					m.put("remexcpres", CodeToValue.codeToValue("T_DM_NZYCYCMLYY",(String)m.get("remexcpres")));
				}else if("个体".equals((String)m.get("biaoji"))){
					m.put("specause", CodeToValue.codeToValue("T_DM_GTLRYCMLYY",(String)m.get("specause")));
					m.put("remexcpres", CodeToValue.codeToValue("T_DM_GTYCYCMLYY",(String)m.get("remexcpres")));
				}else{
					m.put("specause", CodeToValue.codeToValue("T_DM_LRYCMLYY",(String)m.get("specause")));
					m.put("remexcpres", CodeToValue.codeToValue("T_DM_YCYCMLYY",(String)m.get("remexcpres")));
				}
				m.put("acceptdeptid", CodeToValue.codeToValue("T_DM_djgXjGdm",(String)m.get("acceptdeptid")));
			}
		}
		return resultList;
	}
}