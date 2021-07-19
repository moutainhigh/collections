package com.gwssi.comselect.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.gwssi.application.log.aspect.LogUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;

@Service(value = "hlSelectService")
public class HLSelectService extends BaseService{
	/**
	 * 中心库  数据源
	 */
	private static final String DATASOURS_DC_DC ="dc_dc";
	
    /**
     * 异常名录查询列表展示
     * @param map
     * @param httpServletRequest 
     * @return
     * @throws OptimusException
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getSXList(Map map, HttpServletRequest httpServletRequest) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO(DATASOURS_DC_DC);
		StringBuffer sql=new StringBuffer();
		sql.append("select * from dc_ns_exchange_rate where 1=1");
		List list=new ArrayList();
		if (map!=null) {
			Object staTime = map.get("staTime");
			if (staTime!=null) {
				if (staTime.toString().length()>0) {
						sql.append(" and sta_time >= to_date(?,'yyyy-mm-dd HH24:mi:ss')");
						list.add(staTime.toString().trim());
					}
				}
			}
			
			Object exchangeRate = map.get("exchangeRate");
			if (exchangeRate!=null) {
				if (exchangeRate.toString().length()>0) {
					sql.append(" and exchange_rate = ?");
					list.add(exchangeRate.toString().trim());
				}
			
			
		}
		sql.append(" order by sta_time desc");
		List<Map> pageQueryForList = dao.pageQueryForList(sql.toString(),list);
		//LogUtil.insertLog("前海汇率查询", sql.toString(), list.toString(), httpServletRequest, dao);
		return typechage(pageQueryForList);
	}
	
	public void updateListInsert(Map<String, String> form) throws OptimusException{
//		HttpSession session = WebContext.getHttpSession();
//		User user = (User) session.getAttribute(OptimusAuthManager.USER);
//		String userId = "";
//		String userName = "";
//		if(user != null){
//			userId = user.getUserId().toUpperCase();
//			userName = user.getUserName();
//		}
		
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		sql.append("insert into DC_NS_EXCHANGE_RATE(STA_TIME, EXCHANGE_RATE) VALUES(?,?)");
		List params = new ArrayList<String>();
		params.add(Calendar.getInstance());
		params.add(form.get("exchangeRate"));
		dao.execute(sql.toString(), params);		
	}
	
	public void deleteHL(String id) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		sql.append("delete from DC_NS_EXCHANGE_RATE t where t.id = ? ");
		List params = new ArrayList<String>();
		params.add(id);
		dao.execute(sql.toString(), params);
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

}
