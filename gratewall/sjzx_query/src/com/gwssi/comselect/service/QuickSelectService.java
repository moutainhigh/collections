package com.gwssi.comselect.service;

import java.util.ArrayList;
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

@Service(value = "quickSelectService")
public class QuickSelectService extends BaseService{
	/**
	 * 中心库  数据源
	 */
	private static final String DATASOURS_DC_DC ="dc_dc";
	
    /**
     * 快速列表表展示
     * @param map
     * @param httpServletRequest 
     * @return
     * @throws OptimusException
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getSXList(Map map, HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select case when s.unifsocicrediden is null then s.regno else s.unifsocicrediden end as regno,s.entname,"
        + "func_getcode('C00021',s.enttype) as enttype,"
        + "s.estdate,"
        + "func_getcode('C00013',s.currency) as REGCAPCUR,"
        + "s.regcap as reccap,"
        + "s.lerep,"
        + "s.dom,"
        + "s.ENTSTATUS as REGSTATE,"
        + "s.ENTSTATUS as ENTSTATUS,"
        + "s.id as pripid,"
        + "s.enttype as type,"
        + "s.pripid as entid ,"
        + "s.opetype from  dc_ra_mer_base s where 1=1");
		List list=new ArrayList();
		if (map!=null) {
			Object entname = map.get("entname");
			if (entname!=null) {
				if (entname.toString().trim().length()>0) {
					Object select = map.get("select");
					if("1".equals(select.toString())){
						sql.append(" and entname = ?");
						list.add(entname.toString().trim());
					}else if("2".equals(select.toString().trim())){
						sql.append(" and entname like ?");
						list.add("%" + entname.toString().trim() + "%");
					}else{
						sql.append(" and entname = ?");
						list.add(entname.toString().trim());
					}
				}
			}
			
			Object regno = map.get("regno");
			if (regno!=null) {
				if (regno.toString().trim().length()>0) {
					sql.append(" and (regno = ? or unifsocicrediden = ?)");
					list.add(regno.toString().trim());
					list.add(regno.toString().trim());
				}
			}
			
			Object entnameOld = map.get("entnameOld");
			if (entnameOld!=null) {
				if (entnameOld.toString().trim().length()>0) {
					Object selectOld = map.get("selectOld");
					if("2".equals(selectOld.toString())){
					sql.append(" and id in (select main_tb_id from dc_ra_mer_altitem a where a.altitem = '01' and (altbe like ? or altaf like ?)) union select case when s.unifsocicrediden is null then s.regno else s.unifsocicrediden end as regno,s.entname,"
						        + "func_getcode('C00021',s.enttype) as enttype,"
						        + "s.estdate,"
						        + "func_getcode('C00013',s.currency) as REGCAPCUR,"
						        + "s.regcap as reccap,"
						        + "s.lerep,"
						        + "s.dom,"
						        + "s.ENTSTATUS as REGSTATE,"
						        + "s.ENTSTATUS as ENTSTATUS,"
						        + "s.id as pripid,"
						        + "s.enttype as type,"
						        + "s.pripid as entid ,"
						        + "s.opetype from  dc_ra_mer_base s where s.entname like ? ");
					list.add("%"+entnameOld.toString().trim()+"%");
					list.add("%"+entnameOld.toString().trim()+"%");
					list.add("%"+entnameOld.toString().trim()+"%");
				}else{
					sql.append(" and id in (select main_tb_id from dc_ra_mer_altitem a where a.altitem = '01' and (altbe = ? or altaf = ?)) union select case when s.unifsocicrediden is null then s.regno else s.unifsocicrediden end as regno,s.entname,"
						        + "func_getcode('C00021',s.enttype) as enttype,"
						        + "s.estdate,"
						        + "func_getcode('C00013',s.currency) as REGCAPCUR,"
						        + "s.regcap as reccap,"
						        + "s.lerep,"
						        + "s.dom,"
						        + "s.ENTSTATUS as REGSTATE,"
						        + "s.ENTSTATUS as ENTSTATUS,"
						        + "s.id as pripid,"
						        + "s.enttype as type,"
						        + "s.pripid as entid ,"
						        + "s.opetype from  dc_ra_mer_base s where s.entname = ? ");
					list.add(entnameOld.toString().trim());
					list.add(entnameOld.toString().trim());
					list.add(entnameOld.toString().trim());
				}
			}
		}
			
			
		}
		List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(),list);
		LogUtil.insertLog("快速查询", sql.toString(), list.toString(), httpServletRequest, dao);
		return pageQueryForList;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getSXListCount(Map<String, String> map) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select * from v_repeat_base t where 1=1 ");
		List list=new ArrayList();
		if (map!=null) {
			Object object = map.get("dom");
			if (object!=null) {
				if (object.toString().length()>0) {
					sql.append(" and DOM = ?");
					list.add(object.toString().trim());
				}
			}
			
		}
		String testsql =  "select count(1) count from ("+sql+")";
		return dao.queryForList(testsql.toString(), list).get(0).get("count").toString();
	}

}
