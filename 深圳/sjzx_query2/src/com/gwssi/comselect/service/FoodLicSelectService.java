package com.gwssi.comselect.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getSpQyListCount(Map<String, String> map) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		List list=new ArrayList();
		
		sql.append("select count(1) as count from v_food_license t where 1=1 ");
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
			String ytsl = (String)map.get("ytsl");
			String fztime_begin = (String)map.get("fztime_begin");
			String fztime_end = (String)map.get("fztime_end");
			
			
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
			
			StringBuffer sb = new StringBuffer();
			if (ztyt!=null) {
				if (ztyt.toString().length()>0) {
					sql.append("  and   ( ");
					if(ztyt.toString().trim().contains(",")) {
						String [] temp = ztyt.toString().trim().split(",");
						
												
						sb.append(" t.MAIN_FORMAT like ? ");
						list.add("%"+temp[0] +"%");
						
						for (int i = 1; i < temp.length; i++) {
							sb.append(" or  t.MAIN_FORMAT like ?  ");
							list.add("%"+temp[i]+"%");
						}
						
						sql.append(sb.toString());
					}else {
						sql.append(" t.MAIN_FORMAT like ?");
						list.add("%"+ztyt.toString().trim()+"%");
					}
					
					sql.append(" ) ") ;
					//sql.append(" and t.main_format_name like ?");
					//sql.append(" and t.MAIN_FORMAT like ?");
					//list.add("%"+ztyt.toString().trim()+"%");
				}
			}
			if (jyxm!=null) {
				if (jyxm.toString().length()>0) {
					sql.append(" and t.engage_project like ?");
					list.add("%"+jyxm.toString().trim()+"%");
				}
			}
			if (newflag!=null) {
				if("1".equals(newflag)){//有效
					sql.append(" and (t.valid_date>=to_char(sysdate,'yyyy-mm-dd') and t.newflag='1') ");
				}else if("0".equals(newflag)){ //无效
					sql.append(" and (t.valid_date < to_char(sysdate,'yyyy-mm-dd') or t.newflag='0') ");
				}else {
					//sql.append(" and (t.valid_date<to_char(sysdate,'yyyy-mm-dd'))");
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
			
			
			
			if(StringUtils.isNotEmpty(ytsl)) {
				sql.append(" and t.main_format_name like ? ");
				list.add("%"+ytsl.toString().trim()+"%");
			}
			
			if(StringUtils.isNotEmpty(fztime_begin)) {
				sql.append(" and t.issue_date >= to_date(?,'yyyy-mm-dd') ");
				list.add(fztime_begin.trim());
			}
			
			if(StringUtils.isNotEmpty(fztime_end)) {
				sql.append(" and t.issue_date < to_date(?,'yyyy-mm-dd') ");
				list.add(fztime_end.trim());
			}
			
			
		}
		
		return dao.queryForList(sql.toString(), list).get(0).get("count").toString();
	}
	
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
			String ytsl = (String)map.get("ytsl");
			
			String fztime_begin = (String)map.get("fztime_begin");
			String fztime_end = (String)map.get("fztime_end");
			
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
			
			StringBuffer sb = new StringBuffer();
			if (ztyt!=null) {
				if (ztyt.toString().length()>0) {
					sql.append("  and   ( ");
					if(ztyt.toString().trim().contains(",")) {
						String [] temp = ztyt.toString().trim().split(",");
						
												
						sb.append(" t.MAIN_FORMAT like ? ");
						list.add("%"+temp[0] +"%");
						
						for (int i = 1; i < temp.length; i++) {
							sb.append(" or  t.MAIN_FORMAT like ?  ");
							list.add("%"+temp[i]+"%");
						}
						
						sql.append(sb.toString());
					}else {
						sql.append(" t.MAIN_FORMAT like ?");
						list.add("%"+ztyt.toString().trim()+"%");
					}
					
					sql.append(" ) ") ;
					//sql.append(" and t.main_format_name like ?");
					//sql.append(" and t.MAIN_FORMAT like ?");
					//list.add("%"+ztyt.toString().trim()+"%");
				}
			}
			if (jyxm!=null) {
				if (jyxm.toString().length()>0) {
					sql.append(" and t.engage_project like ?");
					list.add("%"+jyxm.toString().trim()+"%");
				}
			}
			if (newflag!=null) {
				if("1".equals(newflag)){//有效
					sql.append(" and (t.valid_date>=to_char(sysdate,'yyyy-mm-dd') and t.newflag='1') ");
				}else if("0".equals(newflag)){ //无效
					sql.append(" and (t.valid_date < to_char(sysdate,'yyyy-mm-dd') or t.newflag='0') ");
				}else {
					//sql.append(" and (t.valid_date<to_char(sysdate,'yyyy-mm-dd'))");
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
			
			
			
			if(StringUtils.isNotEmpty(ytsl)) {
				sql.append(" and t.main_format_name like ? ");
				list.add("%"+ytsl.toString().trim()+"%");
			}
			
			if(StringUtils.isNotEmpty(fztime_begin)) {
				sql.append(" and t.issue_date >= to_date(?,'yyyy-mm-dd') ");
				list.add(fztime_begin.trim());
			}
			
			if(StringUtils.isNotEmpty(fztime_end)) {
				sql.append(" and t.issue_date < to_date(?,'yyyy-mm-dd') ");
				list.add(fztime_end.trim());
			}
			
			
		}
		//sql.append(" order by ISSUE_DATE DESC");
		
	
	
		
		
		String wrapSQL = "select * from (" +sql.toString()+ ") where rownum<=1000 ";
		
		List<Map> pageQueryForList = dao.pageQueryForList(wrapSQL,list);
		LogUtil.insertLog("食品许可证查询", sql.toString(), list.toString(), httpServletRequest, dao);
		return pageQueryForList;
	}

	public List<Map> exportExcel(String entname,String licno,String supunitbysup,String supdepbysup,String newflag,String lictype,String street,String ztyt,String jyxm,String ytsl,String fztime_begin ,String fztime_end,HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select t.entname, t.licno, t.frname,to_char(t.issue_date,'yyyy-mm-dd') as issue_date, t.valid_date, t.lictype_cn, t.supunitbysup_cn, t.supdepbysup_cn, t.main_format_name, t.engage_project,t.dom,t.street,t.business_area from v_food_license t where 1=1 ");
		List list=new ArrayList();
		
		if (entname!=null) {
			if (entname.length()>0) {
				sql.append(" and t.entname = ?");
				list.add(entname.trim());
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
		
		StringBuffer sb = new StringBuffer();
		if (ztyt!=null) {
			if (ztyt.toString().length()>0) {
				sql.append("  and   ( ");
				if(ztyt.toString().trim().contains(",")) {
					String [] temp = ztyt.toString().trim().split(",");
					
											
					sb.append(" t.MAIN_FORMAT like ? ");
					list.add("%"+temp[0] +"%");
					
					for (int i = 1; i < temp.length; i++) {
						sb.append(" or  t.MAIN_FORMAT like ?  ");
						list.add("%"+temp[i]+"%");
					}
					
					sql.append(sb.toString());
				}else {
					sql.append(" t.MAIN_FORMAT like ?");
					list.add("%"+ztyt.toString().trim()+"%");
				}
				
				sql.append(" ) ") ;
				//sql.append(" and t.main_format_name like ?");
				//sql.append(" and t.MAIN_FORMAT like ?");
				//list.add("%"+ztyt.toString().trim()+"%");
			}
		}
		if (jyxm!=null) {
			if (jyxm.toString().length()>0) {
				sql.append(" and t.engage_project like ?");
				list.add("%"+jyxm.toString().trim()+"%");
			}
		}
		if (newflag!=null) {
			if("1".equals(newflag)){//有效
				sql.append(" and (t.valid_date>=to_char(sysdate,'yyyy-mm-dd') and t.newflag='1') ");
			}else if("0".equals(newflag)){ //无效
				sql.append(" and (t.valid_date < to_char(sysdate,'yyyy-mm-dd') or t.newflag='0') ");
			}else {
				//sql.append(" and (t.valid_date<to_char(sysdate,'yyyy-mm-dd'))");
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
		
		
		
		if(StringUtils.isNotEmpty(ytsl)) {
			sql.append(" and t.main_format_name like ? ");
			//list.add(ytsl);
			list.add("%"+ytsl.toString().trim()+"%");
		}
		
		
		if(StringUtils.isNotEmpty(fztime_begin)) {
			sql.append(" and t.issue_date >= to_date(?,'yyyy-mm-dd') ");
			list.add(fztime_begin.trim());
		}
		
		if(StringUtils.isNotEmpty(fztime_end)) {
			sql.append(" and t.issue_date < to_date(?,'yyyy-mm-dd') ");
			list.add(fztime_end.trim());
		}
		
		
		List<Map> res = dao.queryForList(sql.toString(),list);
		LogUtil.insertLog("食品许可证导出", sql.toString(), list.toString(), httpServletRequest, dao);
		return res;
			
	}
}
