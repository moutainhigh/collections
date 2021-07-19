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

@Service(value = "FoodLicSelectService")
public class FoodLicSelectService extends BaseService{
	/**
	 * 中心库  数据源
	 */
	private static final String DATASOURS_DC_DC ="dc_dc";
	
    /**
     * 食品许可证查询列表表展示
     * @param map
     * @param httpServletRequest 
     * @return
     * @throws OptimusException
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getList(Map map, HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select * from v_food_license t where 1=1 ");
		List list=new ArrayList();
		if (map!=null) {
			String name = (String)map.get("entname");
			String licno = (String)map.get("licno");
			String supunitbysup = (String)map.get("supunitbysup");
			String supdepbysup = (String)map.get("supdepbysup");
			String newflag = (String)map.get("newflag");
			String lictype = (String)map.get("lictype");
			String street = (String)map.get("street");
			String ztyt = (String)map.get("ztyt");
			String jyxm = (String)map.get("jyxm");
			
			if (name!=null) {
				if (name.toString().length()>0) {
					sql.append(" and t.entname = ?");
					list.add(name.toString().trim());
				}
			}
			if (licno!=null) {
				if (licno.toString().length()>0) {
					sql.append(" and t.licno = ?");
					list.add(licno.toString().trim());
				}
			}
			if (supunitbysup!=null) {
				if (supunitbysup.toString().length()>0) {
					sql.append(" and t.supunitbysup = ?");
					list.add(supunitbysup.toString().trim());
				}
			}
			if (supdepbysup!=null) {
				if (supdepbysup.toString().length()>0) {
					sql.append(" and t.supdepbysup = ?");
					list.add(supdepbysup.toString().trim());
				}
			}
			if (street!=null) {
				if (street.toString().length()>0) {
					sql.append(" and t.street like ?");
					list.add("%"+street.toString().trim()+"%");
				}
			}
			if (ztyt!=null) {
				if (ztyt.toString().length()>0) {
					sql.append(" and t.main_format_name like ?");
					list.add("%"+ztyt.toString().trim()+"%");
				}
			}
			if (jyxm!=null) {
				if (supdepbysup.toString().length()>0) {
					sql.append(" and t.engage_project like ?");
					list.add("%"+jyxm.toString().trim()+"%");
				}
			}
			if (newflag!=null) {
				if("1".equals(newflag)){//有效
					sql.append(" and t.valid_date>=to_char(sysdate,'yyyy-mm-dd') and t.newflag='0'");
				}else if("2".equals(newflag)){
					sql.append(" and t.valid_date<to_char(sysdate,'yyyy-mm-dd')");
				}
			}
			
			if (lictype!=null) {
				if("1".equals(lictype)){//食品经营许可证
					sql.append(" and t.lictype = ?");
					list.add("1");
				}else if("3".equals(lictype)){ //食品生产许可证
					sql.append(" and (t.lictype = ? or t.lictype = ?)");
					list.add("3");
					list.add("4");
				}else if("5".equals(lictype)){//食品流通许可证
					sql.append(" and t.lictype = ?");
					list.add("5");
				}else if("6".equals(lictype)){//餐饮服务许可证
					sql.append(" and t.lictype = ?");
					list.add("6");
				}
			}
			
		}
		
		
		List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(),list);
		LogUtil.insertLog("食品许可证查询", sql.toString(), list.toString(), httpServletRequest, dao);
		return pageQueryForList;
	}

	public List<Map> exportExcel(String entname,String licno,String supunitbysup,String supdepbysup,String newflag,String lictype,String street,String ztyt,String jyxm,HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select t.entname, t.licno, t.frname, t.valid_date, t.lictype_cn, t.supunitbysup_cn, t.supdepbysup_cn, t.main_format_name, t.engage_project,t.dom from v_food_license t where 1=1 ");
		List list=new ArrayList();
		
			if (entname!=null) {
				if (entname.toString().length()>0) {
					sql.append(" and t.entname = ?");
					list.add(entname.toString().trim());
				}
			}
			if (licno!=null) {
				if (licno.toString().length()>0) {
					sql.append(" and t.licno = ?");
					list.add(licno.toString().trim());
				}
			}
			if (supunitbysup!=null) {
				if (supunitbysup.toString().length()>0) {
					sql.append(" and t.supunitbysup = ?");
					list.add(supunitbysup.toString().trim());
				}
			}
			if (supdepbysup!=null) {
				if (supdepbysup.toString().length()>0) {
					sql.append(" and t.supdepbysup = ?");
					list.add(supdepbysup.toString().trim());
				}
			}
			if (street!=null) {
				if (street.toString().length()>0) {
					sql.append(" and t.street like ?");
					list.add("%"+street.toString().trim()+"%");
				}
			}
			if (ztyt!=null) {
				if (ztyt.toString().length()>0) {
					sql.append(" and t.main_format_name like ?");
					list.add("%"+ztyt.toString().trim()+"%");
				}
			}
			if (jyxm!=null) {
				if (supdepbysup.toString().length()>0) {
					sql.append(" and t.engage_project like ?");
					list.add("%"+jyxm.toString().trim()+"%");
				}
			}
			if (newflag!=null) {
				if("1".equals(newflag)){//有效
					sql.append(" and t.valid_date>=to_char(sysdate,'yyyy-mm-dd') and t.newflag='0'");
				}else if("2".equals(newflag)){
					sql.append(" and t.valid_date<to_char(sysdate,'yyyy-mm-dd')");
				}
			}
			
			if (lictype!=null) {
				if("1".equals(lictype)){//食品经营许可证
					sql.append(" and t.lictype = ?");
					list.add("1");
				}else if("3".equals(lictype)){ //食品生产许可证
					sql.append(" and (t.lictype = ? or t.lictype = ?)");
					list.add("3");
					list.add("4");
				}else if("5".equals(lictype)){//食品流通许可证
					sql.append(" and t.lictype = ?");
					list.add("5");
				}else if("6".equals(lictype)){//餐饮服务许可证
					sql.append(" and t.lictype = ?");
					list.add("6");
				}
			}
			
		
		
		
		List<Map> res = dao.queryForList(sql.toString(),list);
		LogUtil.insertLog("食品许可证导出", sql.toString(), list.toString(), httpServletRequest, dao);
		return res;
			
	}
}
