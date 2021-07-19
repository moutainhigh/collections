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
import com.gwssi.optimus.core.web.event.OptimusRequest;

@Service(value = "repeatSelectService")
public class RepeatSelectService extends BaseService{
	/**
	 * 中心库  数据源
	 */
	private static final String DATASOURS_DC_DC ="dc_dc";
	
    /**
     * 重复地址查询列表表展示
     * @param map
     * @return
     * @throws OptimusException
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getSXList(Map map,HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select * from v_repeat_base t where 1=1 ");
		List list=new ArrayList();
		if (map!=null) {
			Object object = map.get("dom");
			if (object!=null) {
				if (object.toString().length()>0) {
					sql.append(" and DOM = ?");
					list.add(object.toString());
				}
			}
			
		}
		//String testsql =  "select * from ("+sql+")  e where rownum <= 100";
		List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(),list);
		LogUtil.insertLog("重复地址查询", sql.toString(), list.toString(), httpServletRequest, dao);
		/*sql.append(" ORDER BY REG_DATE desc");*/
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
