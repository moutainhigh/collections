package com.gwssi.comselect.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.gwssi.application.log.aspect.LogUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service(value = "personSelectService")
public class PersonSelectService extends BaseService{
	/**
	 * 中心库  数据源
	 */
	private static final String DATASOURS_DC_DC ="dc_dc";
	
    /**
     * 人员查询列表展示
     * @param map
     * @param httpServletRequest 
     * @return
     * @throws OptimusException
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getPersonList(Map map, HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select a.entname,a.unifsocicrediden,b.persname,b.cerno,b.perflag,(case when b.perflag='股东' then '出资额'||c.old_code_name|| TO_CHAR(b.SUBCONAM)||'万元，出资比例'||TO_CHAR(b.CONPROP)||'%' end) as memo ");
		sql.append("from dc_ra_mer_base a,(select * from v_persons_query where 1=1 and ");
		List list=new ArrayList();
		if (map!=null) {
			if (map.get("cerno")!=null) {
				String cerno = (String)map.get("cerno");
				if (cerno.trim().length()>0 && cerno.trim().length()==18) {
					sql.append("  cerno = ? or cerno=FUN_IDCARD_18TO15('"+cerno.trim()+"')");
					list.add(cerno.trim());
				//	list.add(cerno.trim());
				}else if(cerno.trim().length()>0 && cerno.trim().length()==15){
					sql.append("  cerno = ? or cerno=FUN_IDCARD_15TO18('"+cerno.trim()+"')");
					list.add(cerno.trim());
					//list.add(cerno.trim());
				}
			}
			sql.append(" ) b,(select * from codedata where code_index='CA04') c where a.id=b.main_tb_id and a.currency=c.old_code_value  ");
			
		}
		String testsql =  "select * from ("+sql+")  e where rownum <= 100";
		List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(),list);
		LogUtil.insertLog("人员查询", sql.toString(), list.toString(), httpServletRequest, dao);
		return  typechage(pageQueryForList);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getPersonListCount(Map map) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select a.entname,a.unifsocicrediden,b.persname,b.cerno,b.perflag,(case when b.perflag='股东' then '出资额'||c.old_code_name|| TO_CHAR(b.SUBCONAM)||'万元，出资比例'||TO_CHAR(b.CONPROP)||'%' end) as memo ");
		sql.append("from dc_ra_mer_base a,(select * from v_persons_query where 1=1 and ");
		List list=new ArrayList();
		if (map!=null) {
			if (map.get("cerno")!=null) {
				String cerno = (String)map.get("cerno");
				if (cerno.trim().length()>0 && cerno.trim().length()==18) {
					sql.append("  cerno = ? or cerno=FUN_IDCARD_18TO15('"+cerno.trim()+"')");
					list.add(cerno.trim());
				//	list.add(cerno.trim());
				}else if(cerno.trim().length()>0 && cerno.trim().length()==15){
					sql.append("  cerno = ? or cerno=FUN_IDCARD_15TO18('"+cerno.trim()+"')");
					list.add(cerno.trim());
					//list.add(cerno.trim());
				}
			}
			sql.append(" ) b,(select * from codedata where code_index='CA04') c where a.id=b.main_tb_id and a.currency=c.old_code_value  ");
			
		}
		String testsql =  "select count(1) count from ("+sql+")";
		return dao.queryForList(testsql.toString(), list).get(0).get("count").toString();
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
					String format = "yyyy-MM-dd";
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


}
