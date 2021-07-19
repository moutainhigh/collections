package com.gwssi.trs.service;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;












import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.application.log.aspect.LogUtil;
import com.gwssi.comselect.utils.StringUtil;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.trs.Conts;
import com.gwssi.trs.controller.TrsControllerX;

/**
 * 该类为广东全文检索拷贝 柴浩伟修改
 * @author chaihw
 *
 */
@Service
public class MarketEntityService extends BaseService{
	private static  Logger log=Logger.getLogger(MarketEntityService.class);
	
	//主题库数据源key 全文检索 详细信息库 key
	//private static String regdetail_ds_key=""'
	
	private static String getDetail_datasourcekey(){
		Properties properties = ConfigManager.getProperties("optimus");
		
		String key= properties.getProperty("regDetail.datasourcekey");


		return key;
	}
	/**
	 * 查询法定送达联系人
	 * @param pripid
	 * @return
	 * @throws OptimusException 
	 */
	public String queryLawpersonxx(String pripid) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO("dc_dc");												
		StringBuffer sql= new StringBuffer(" select t.persname from dc_ra_mer_persons t");
		List<String> list = new ArrayList<String>();
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and t.main_tb_id = ?  and t.licflag = 'flwssdr' ");
				list.add(pripid);
			}
			List<Map> temp = dao.queryForList(sql.toString(), list);
			String res = "";
			if(temp != null && temp.size() > 0){
				res = (String)temp.get(0).get("persname");
			}
		return res;
	}
	
	/**
	 * 查询合伙人信息
	 * @param pripid
	 * @return
	 * @throws OptimusException 
	 */
	public String queryHehuorenxx(String pripid) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO("dc_dc");												
		StringBuffer sql= new StringBuffer(" select t.name from v_qy_nzwz_hehuoren_xinxi t");
		List<String> list = new ArrayList<String>();
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and t.pripid = ?  ");
				list.add(pripid);
			}
			List<Map> temp = dao.queryForList(sql.toString(), list);
			String res = "";
			if(temp != null && temp.size() > 0){
				res = (String)temp.get(0).get("name");
			}
		return res;
	}
	
	/**
	 * 市场主体基本信息(T_SCZT_SCZTJBXX)、补充(T_SCZT_SCZTBCXX)、隶属(T_SCZT_SCZTLSXX)、外国(地区)企业在中国从事生产经营活动基本信息(补充)(T_SCZT_WGQYSCJYHDJBXX)
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
		if("0".equals(type)){//内资
			sql=new StringBuffer(Conts.SCZTJBXXSQL);
		}else if("1".equals(type)){//个体其他
			sql=new StringBuffer(Conts.SCZTJBXXSQLGT);
		}else if("2".equals(type)){//外资
			sql=new StringBuffer(Conts.SCZTJBXXSQLWZ);
		}else if("4".equals(type)){//集团
			sql=new StringBuffer(Conts.SCZTJBXXSSQLJT);
		}
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			//String sourceflag=(String)map.get("sourceflag"); 无平台 代码
			sql.append(" where  1=1" );
/*			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.sourceflag = ?");
				list.add(sourceflag);
			}*/
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and pripid = ? ");
				list.add(pripid);
			}
		}else{
			return null;
		}
		return typechage(dao.queryForList(sql.toString(), list));
	}
	
	/**
	 * 企业隶属信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryLishuJbxx(Map map,String type) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	
		IPersistenceDAO dao= this.getPersistenceDAO("dc_dc");												
		StringBuffer sql= new StringBuffer(" select * from V_QY_NZWZ_LISHU_XX ");
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and brpripid = ? ");
				list.add(pripid);
			}
		}else{
			return null;
		}
		return typechage(dao.queryForList(sql.toString(), list));
	}
	
	/**
	 * 企业注销信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryZhuxiaoJbxx(Map map,String type) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	
		IPersistenceDAO dao= this.getPersistenceDAO("dc_dc");												
		StringBuffer sql= new StringBuffer(" select * from V_QY_NZWZ_ZHUXIAOXX ");
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and pripid = ? ");
				list.add(pripid);
			}
		}else{
			return null;
		}
		return typechage(dao.queryForList(sql.toString(), list));
	}
	
	
	/**
	 * 企业清算信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryQingsuanJbxx(Map map,String type) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	
		IPersistenceDAO dao= this.getPersistenceDAO("dc_dc");												
		StringBuffer sql= new StringBuffer(" select * from v_qy_nzwz_qingsuanxx ");
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and pripid = ? ");
				list.add(pripid);
			}
		}else{
			return null;
		}
		return typechage(dao.queryForList(sql.toString(), list));
	}
	
	
	/**
	 * 财务负责信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryCaiwufuzeJbxx(Map map,String type) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	
		IPersistenceDAO dao= this.getPersistenceDAO("dc_dc");												
		StringBuffer sql= new StringBuffer(" select * from V_QY_NZWZ_CAIWU_FUZEXX ");
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and pripid = ? ");
				list.add(pripid);
			}
		}else{
			return null;
		}
		return typechage(dao.queryForList(sql.toString(), list));
	}
	
	/**
	 * 工商联络员信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryLianluoyuanJbxx(Map map,String type) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	
		IPersistenceDAO dao= this.getPersistenceDAO("dc_dc");												
		StringBuffer sql= new StringBuffer(" select * from v_qy_nzwz_lianluoyuan_xinxi ");
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and pripid = ? ");
				list.add(pripid);
			}
		}else{
			return null;
		}
		return typechage(dao.queryForList(sql.toString(), list));
	}
	
	/**
	 * 法定代表人信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryDaibiaorenJbxx(Map map,String type,HttpServletRequest httpServletRequest) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	
		IPersistenceDAO dao= this.getPersistenceDAO("dc_dc");												
		StringBuffer sql= new StringBuffer(" select * from v_qy_nzwz_daibiaoren_xinxi ");
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and pripid = ? ");
				list.add(pripid);
			}
		}else{
			return null;
		}
		List<Map> queryForList = dao.queryForList(sql.toString(), list);
		//LogUtil.insertLog("商事主体信息_法定人表人", sql.toString(), list.toString(), httpServletRequest, dao);
		return typechage(queryForList);
	}
	
	/**
	 * 合伙人信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryHehuorenJbxx(Map map,String type) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	
		IPersistenceDAO dao= this.getPersistenceDAO("dc_dc");												
		StringBuffer sql= new StringBuffer(" select * from v_qy_nzwz_hehuoren_xinxi ");
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and pripid = ? ");
				list.add(pripid);
			}
		}else{
			return null;
		}
		return typechage(dao.queryForList(sql.toString(), list));
	}
	
	/**
	 * 企业迁入信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryQianruJbxx(Map map,String type) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	
		IPersistenceDAO dao= this.getPersistenceDAO("dc_dc");												
		StringBuffer sql= new StringBuffer(" select * from v_qy_nzwz_qianru_xx ");
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and pripid = ? ");
				list.add(pripid);
			}
		}else{
			return null;
		}
		return typechage(dao.queryForList(sql.toString(), list));
	}
	
	/**
	 * 企业迁出信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryQianchuJbxx(Map map,String type) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	
		IPersistenceDAO dao= this.getPersistenceDAO("dc_dc");												
		StringBuffer sql= new StringBuffer(" select * from v_qy_nzwz_qianchu_xx ");
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and pripid = ? ");
				list.add(pripid);
			}
		}else{
			return null;
		}
		return typechage(dao.queryForList(sql.toString(), list));
	}
	/**
	 * 办税人员信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryBanshuiJbxx(Map map,String type) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	
		IPersistenceDAO dao= this.getPersistenceDAO("dc_dc");												
		StringBuffer sql= new StringBuffer(" select * from v_qy_nzwz_banshui_xx ");
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and pripid = ? ");
				list.add(pripid);
			}
		}else{
			return null;
		}
		return typechage(dao.queryForList(sql.toString(), list));
	}
	
	/**
	 * 税务代理人信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryShuiwudailiJbxx(Map map,String type) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	
		IPersistenceDAO dao= this.getPersistenceDAO("dc_dc");												
		StringBuffer sql= new StringBuffer(" select * from v_qy_nzwz_shuiwudaili_xx ");
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and pripid = ? ");
				list.add(pripid);
			}
		}else{
			return null;
		}
		return typechage(dao.queryForList(sql.toString(), list));
	}
	
	/**
	 * 多证合一信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryDuozhengJbxx(Map map,String type) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	
		IPersistenceDAO dao= this.getPersistenceDAO("dc_dc");												
		StringBuffer sql= new StringBuffer(" select * from v_qy_nzwz_duozheng_xx ");
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and pripid = ? ");
				list.add(pripid);
			}
		}else{
			return null;
		}
		return typechage(dao.queryForList(sql.toString(), list));
	}
	
	/**
	 * 个体经营者信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryGTjingyingJbxx(Map map,String type) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	
		IPersistenceDAO dao= this.getPersistenceDAO("dc_dc");												
		StringBuffer sql= new StringBuffer(" select * from V_QY_GT_JINYIN ");
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and pripid = ? ");
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
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
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
	 * 市场主体隶属信息补充信息
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryRegLsxx(Map map) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_SCZTLSBCXX ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_SCZTLSXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
/*			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and a.sourceflag = ?");
				list.add(sourceflag);
			}*/
/*			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and a.priPid = ?");
				list.add(pripid);
			}*/
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" where trim(t.brpriPid) = ?");
				list.add(pripid);
			}else{
				return null;
			}
			
		}else{
			return null;
		}
		//sql.append(" Order by EstDate desc");
		//sql.append(" Order by  info.estdate  desc");
		return dao.queryForList(sql.toString(), list);
	}
	
	/**
	 * 迁入信息(T_SCZT_QRXX)
	 * 迁出信息(T_SCZT_QCXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryRegQrQcxx(Map map) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_QRXX ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_QRQCXX);
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
				sql.append(" and pripid = ?");
				list.add(pripid);
			}
			
		}else{
			return null;
		}
		sql.append(" order by mdate desc");
		List<Map> list1=dao.queryForList(sql.toString(), list);
		return list1;
	}
	
	//[{minstatus=1, qrqctype=迁入, memo=null, moutareregorg=广东省东莞市工商行政管理局长安, pripid=05a57209-0106-1000-e009-631d0a0c0115, archremovemode=null, minrea=null, historyinfoid=1c096e45-0115-1000-e000-19120a0c0115, sourceflag=441900, mindate=null, minletnum=0700890250, moutarea=null}, {minstatus=1, qrqctype=迁出, memo=null, moutareregorg=441904, pripid=05a57209-0106-1000-e009-631d0a0c0115, archremovemode=null, minrea=null, historyinfoid=1c096e45-0115-1000-e000-19120a0c0115, sourceflag=441900, mindate=null, minletnum=null, moutarea=null}]
	/**
	 * 股权冻结信息(T_SCZT_GQDJXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryRegGqdjxx(Map map) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_GQDJXX ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_GQDJXX);
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
		sql.append(" order by a.frofrom desc ");
		return dao.queryForList(sql.toString(), list);
	}
	
	/**
	 * 股权出质信息(T_SCZT_GQCZXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryRegGqczxx(Map map) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_GQCZXX ");
		StringBuffer sql=new StringBuffer(Conts.T_SCZT_GQCZXX);
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
			if(StringUtils.isNotBlank(sourceflag)){
				sql.append(" and b.sourceflag = ?");
				list.add(sourceflag);
			}
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and b.priPid = ?");
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
	 * 出资计划信息(T_SCZT_ZZXX)
	 * @param map
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryRegZzxx(Map map) throws OptimusException{
		/*IPersistenceDAO dao= this.getPersistenceDAO();	*/	IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());;
		//StringBuffer sql=new StringBuffer("select * from T_SCZT_ZZXX ");
		StringBuffer sql=new StringBuffer("select * from v_qy_nzwz_chuzijihua a");
		List<String> list = new ArrayList<String>();
		if(map!=null&&map.size()>0){
			String pripid = (String) map.get("priPid");
			//String sourceflag=(String)map.get("sourceflag");
			sql.append(" where 1=1 ");
//			if(StringUtils.isNotBlank(sourceflag)){
//				sql.append(" and a.sourceflag = ?");
//				list.add(sourceflag);
//			}
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and a.priPid = ?");
				list.add(pripid);
			}
			
		}else{
			return null;
		}
		//sql.append(" order by a.valfrom desc ");
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
	 * @param httpServletRequest 
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> queryRegRyxx(Map map, HttpServletRequest httpServletRequest) throws OptimusException{
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
			List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(), list);
			//LogUtil.insertLog("商事主体信息_人员查询", sql.toString(), list.toString(), httpServletRequest, dao);
			return pageQueryForList;
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
			String alttime = (String)params.get("alttime");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and  priPid = ? ");
				list.add(pripid);
			}
			
			if(i==1005){
				if(StringUtils.isNotBlank(alttime)){
					sql.append(" and  alttime = ? ");
					list.add(alttime);
				}
				sql.append("order  by altdate desc");
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
			String alttime = (String)params.get("alttime");
			String sign = (String)params.get("sign");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and  priPid = ? ");
				list.add(pripid);
			}
			if(StringUtils.isNotBlank(alttime)){
				sql.append(" and  alttime = ? ");
				list.add(alttime);
			}
			if(StringUtils.isNotBlank(sign)){
				if("1".equals(sign)){
					sql.append("and ALTITEMCODE in ('46','A3','48','47','49','70')");
				}else if("2".equals(sign)){
					sql.append("and ALTITEMCODE = '30' ");
				}else if("3".equals(sign)){
					sql.append("and ALTITEMCODE = '03' ");
				}
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
	
	public List<?> nzwzBianGengDetailTitile(String priPid) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());
		String sql = "select to_char(t.altdate,'yyyy/MM/dd') as altdate,t.ALTTIME, wm_concat(t.ALTITEM) ALTITEMS,t.entname from v_qy_nzwz_biangeng_xx t "
				+ "where t.PRIPID = ? group by t.ALTTIME,t.altdate,t.entname order by t.ALTTIME desc";
		List<String> list = new ArrayList<String>();
		list.add(priPid);
		return dao.queryForList(sql, list);
	}
	
	public List<?> nzwzBianGengPersons(String priPid, String sign) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select to_char(t.altdate,'yyyy/MM/dd') as altdate,t.ALTTIME, wm_concat(t.ALTITEM) ALTITEMS,t.entname from v_qy_nzwz_biangeng_xx t where t.PRIPID = ?");
		List<String> list = new ArrayList<String>();
		list.add(priPid);
		if("1".equals(sign)){
			sql.append("and t.ALTITEMCODE in ('46','A3','48','47','49','70')");
		}else if("2".equals(sign)){
			sql.append("and t.ALTITEMCODE = '30' ");
		}else if("3".equals(sign)){
			sql.append("and t.ALTITEMCODE = '03' ");
		}
		sql.append("group by t.ALTTIME,t.altdate,t.entname order by t.ALTTIME desc");
		
		return dao.queryForList(sql.toString(), list);
	}
	
	public List<?> getiBianGengDetailTitile(String priPid) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());
		String sql = "select to_char(t.altdate,'yyyy/MM/dd') as altdate,t.ALTTIME, wm_concat(t.ALTITEM) ALTITEMS from V_QY_GT_BIANGENG_XX t "
				+ "where t.PRIPID = ? group by t.ALTTIME,t.altdate order by t.ALTTIME desc";
		List<String> list = new ArrayList<String>();
		list.add(priPid);
		return dao.queryForList(sql, list);
	}
	
	/**
	 * 如果是合伙企业或者外国代表机构 直接去persons表查询法人
	 * @param priPid
	 * @return
	 * @throws OptimusException 
	 */
	public String queryLerepName(String priPid) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());
		String sql = "select persname from dc_ra_mer_persons t where t.main_tb_id = ? and t.licflag = 'fddbr'";
		List<String> list = new ArrayList<String>();
		list.add(priPid);
		List<Map> names = dao.queryForList(sql, list);
		if(names.size()>0 && names != null){
			return (String)names.get(0).get("persname");
		}
		return "";
	}
	
	/**
	 * 根据法定代表人或出资人员证件号码查询企业信息
	 * @param priPid
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map> queryEntDetail(String cerno) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO(getDetail_datasourcekey());
		String sql = "select * from v_ent_fadingdaibiaoren where cerno = ?";
		List<String> params = new ArrayList<String>();
		if(cerno != null && !"undefined".equals(cerno)){
			params.add(cerno);
			List<Map> res = dao.queryForList(sql, params);
			return res;
		}
		else{
			return null;
		}
	}
	
	/**
	 * 查询电话号码记录日志
	 * @param priPid
	 * @return
	 * @throws OptimusException 
	 */
	public void queryPhone(String flag, String SqlParams, OptimusRequest req) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO("dc_dc");
		LogUtil.insertLog(flag, "", SqlParams, req.getHttpRequest(), dao);		
	}
	
	/**
	 * 查询企业异常名录信息
	 * @param priPid
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map> queryYCXX(Map<String, String> params) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO("dc_dc");												
		StringBuffer sql= new StringBuffer(" select * from dc_ms_abnormal_diretory t ");
		List<String> list = new ArrayList<String>();
		if(params!=null&&params.size()>0){
			String pripid = params.get("priPid");
			sql.append(" where  1=1" );
			if(StringUtils.isNotBlank(pripid)){
				sql.append(" and t.entid = ? ");
				sql.append(" and not (t.type = '3' and t.removetype = '0') ");
				list.add(pripid);
			}
		}else{
			return null;
		}
		List<Map> res = dao.queryForList(sql.toString(), list);
		return typechage(res);
			
	}
	
	/**
	 * 查询严重违法失信信息
	 * @param params
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map> queryYZWFSX(Map<String, String> params) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		List<String> list = new ArrayList<String>();
		if (params != null && params.size() > 0) {
			sql.append("select to_char(t.createtime, 'yyyy-MM-dd') as createtime, t.entid as id, "
					+ " case when t.type='1' then '拟列入'"
					+ " when t.type='2' then '已列入'"
					+ " when t.type='3' then '移除' end as type, "
					+ " case when illrea='1' then '被列入经营异常名录届满3年仍未履行相关义务的'"
					+ "	when illrea='2' then '提交虚假材料或者采取其他欺诈手段隐瞒重要事实，取得公司变更或者注销登记，被撤销登记的' "
					+ " when illrea='3' then '组织策划传销的，或者因为传销行为提供便利条件两年内收到三次以上行政处罚的' "
					+ " when illrea='4' then '因直销违法行为两年内收到三次以上行政处罚的' "
					+ " when illrea='5' then '因不正当竞争行为两年内收到三次以上行政处罚的' "
					+ " when illrea='6' then '因提供的商品或者服务不符合保障人身、财产安全要求，造成人身伤害等严重侵害消费者权益的违法行为，两年内收到三次以上行政处罚的' "
					+ " when illrea='7' then '因发布虚假广告两年内受到三次以上行政处罚的，或者发布关系消费者生命健康的商品或者服务的虚假广告，造成人身伤害的或者其他严重社会不良影响的' "
					+ "	when illrea='8' then '因商品侵权行为五年内收到两次以上行政处罚的' "
					+ " when illrea='9' then '被决定停止受理商标代理业务的' "
					+ " when illrea='10' then '国家工商行政管理总局规定的其他违法工商行政管理法律、行政法规且情节严重的' "
					+ " when illrea='11' then '企业有《严重违法失信企业名单管理暂行办法》第五条第一款第（三）项至第（八）项规定行为之一，两年内累计受到三次以上行政处罚的' end as illrea"
					+ " from dc_ms_dishonesty_directory t where t.type = '2' and t.entid = ?");
			
			String pripid = params.get("priPid");
			if (StringUtils.isNotBlank(pripid)) {
				list.add(pripid);
				return dao.queryForList(sql.toString(), list);
			}
		}
		return null;
	}
	
	/**
	 * 查询企业案件信息
	 * @param priPid
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map> queryCaseXX(String entname,String regno) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO("dc_dc");												
		StringBuffer sql= new StringBuffer();
	    long startTime=System.currentTimeMillis();
	       //执行方法
		sql.append("select a.id,a.caseno,a.casename, a.casestate,a.caseregistertime,a.litiganttype, a.casesourceno, a.LITIGANTNAME from (");
		sql.append(" select id,caseno, casename, casestate,caseregistertime,litiganttype, casesourceno, LITIGANTNAME from dc_case_case  union all");
		sql.append(" select id,caseno, casename, casestate,caseregistertime,litiganttype, casesourceno, LITIGANTNAME from case_case ) a left join  dc_case_sub_partybus b");
		sql.append(" on a.id = b.foreignkey where a.litiganttype = '1'and a.CaseState in ('51', '52')");
		List<String> list = new ArrayList<String>();
		sql.append(" and (1 = 2 ");
		if(!"".equals(entname)){
			sql.append(" or instr(a.LITIGANTNAME,?)>0");
			sql.append(" or b.unitname = ? ");
			list.add(entname);
			list.add(entname);
		}
		if(!"".equals(regno)){
			sql.append(" or b.regno = ? ");
			list.add(regno);
		}
		sql.append(")");
		List<Map> res = dao.queryForList(sql.toString(), list);
	    long endTime=System.currentTimeMillis();
	    float excTime=(float)(endTime-startTime)/1000;
	   log.info("执行时间："+excTime+"s");
		return typechage(res);
			
	}
	
	/**
	 * 查询企业消保信息
	 * @param priPid
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map> queryCPRXX(String entname,String regno) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO("dc_dc");												
		StringBuffer sql= new StringBuffer();
		sql.append(" select regino, inftype,regdepname, infoori, incform, inftype, regtime, a.invmaiid, invobjid, infproid, infowareid, feedbackid from DC_CPR_INFOWARE a,DC_CPR_INVOLVED_MAIN b " +
				" where a.INVMAIID=b.INVMAIID and a.finishtime is not null ");
		List<String> list = new ArrayList<String>();
		sql.append("and (1 = 2 ");
		if(!"".equals(entname)){
			sql.append(" or b.invname = ? ");
			list.add(entname);
		}
		if(!"".equals(regno)){
			sql.append(" or b.compregno = ? ");
			list.add(regno);
		}
		sql.append(")");
		List<Map> res = dao.queryForList(sql.toString(), list);
		return typechage(res);
			
	}
	
	/**
	 * 查询企业食品信息
	 * @param priPid
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map> queryFOODXX(String priPid) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO("dc_dc");												
		StringBuffer sql= new StringBuffer();
		sql.append("select * from v_food_license t where 1=1 ");
		List<String> list = new ArrayList<String>();
		if(!"".equals(priPid)){
			sql.append(" and t.pripid = ? ");
			list.add(priPid);
		}
		List<Map> res = dao.queryForList(sql.toString(), list);
		return typechage(res);
			
	}
	
	/**
	 * 查询企业限制信息
	 * @param priPid
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map> queryXZXX(String priPid) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO("dc_sqlserver");												
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT t.*, s.LimitYYCode, s.LimitItemCode from tdXZSDInfo t LEFT JOIN tdXZSDDetail s on t.ID = s.InfoID where LimitState='1' ");
		List<String> list = new ArrayList<String>();
		if(!"".equals(priPid)){
			sql.append(" and ltrim(rtrim(t.entid)) = ? ");
			list.add(priPid);
		}
		List<Map> res = dao.queryForList(sql.toString(), list);
		return typechage(res);
			
	}
	/**
	 * 查询企业限制信息
	 * @param priPid
	 * @return
	 * @throws OptimusException 
	 */
	public Object queryXZformXX(String priPid) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO("dc_sqlserver");												
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT cast(u.cname as VARCHAR) as limituser, cast(b.cname as VARCHAR) as hzuser, c.deptname as hzunit, t.hztime, t.xzlb, cast(t.memo as VARCHAR) as memo from tdXZSDInfo t LEFT JOIN CodeDB.dbo.tcDept c on t.HZUnit = c.code LEFT JOIN UserDB.dbo.tdUserInfo u on t.limituser = u.cuserid LEFT JOIN UserDB.dbo.tdUserInfo b on t.HZUser = b.cuserid where 1=1 ");
		List<String> list = new ArrayList<String>();
		if(!"".equals(priPid)){
			sql.append(" and t.id = ? ");
			list.add(priPid);
		}
		List<Map> res = dao.queryForList(sql.toString(), list);
		if(typechage(res).size()>0){
			return typechage(res).get(0);
		}else{
			return null;
		}
			
	}
	/**
	 * 查询企业限制信息
	 * @param priPid
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map> queryXZlistXX(String priPid) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO("dc_sqlserver");												
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT t.limityycode, t.limititemcode, t.jcfs, t.limittimebegin, t.limittimeend, t.limitstate, cast(t.memo as VARCHAR) as memo from tdXZSDDetail t where 1=1 ");
		List<String> list = new ArrayList<String>();
		if(!"".equals(priPid)){
			sql.append(" and t.infoid = ? ");
			list.add(priPid);
		}
		List<Map> res = dao.queryForList(sql.toString(), list);
		return typechage(res);
			
	}
	
	
}





