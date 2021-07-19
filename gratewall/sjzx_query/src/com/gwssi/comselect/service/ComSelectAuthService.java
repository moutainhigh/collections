package com.gwssi.comselect.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.comselect.model.EntSelectQueryBo;
import com.gwssi.comselect.utils.ComSqlUtil;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.util.StringUtil;
import com.gwssi.util.DictionaryManager;

@Service(value = "comSelectAuthService")
public class ComSelectAuthService extends BaseService{

	@Autowired
	DictionaryManager dictionaryManager;
	
	/**
	 * 数据中心库
	 * @return
	 */
	private static String getDetail_datasourcekey(){
		Properties properties = ConfigManager.getProperties("optimus");
		
		String key= properties.getProperty("regDetail.datasourcekey");


		return key;
	}
	/**
	 * 代码集库
	 */
	public static String getDc_code_KEY(){
		Properties properties = ConfigManager.getProperties("optimus");
		
		String key= properties.getProperty("dcCode.datasourcekey");

		return key;
	}
	

	/**
	 * 大类  查找一个节点的所有直属子节点（所有后代）。
	 * @param str
	 * @return
	 * @throws OptimusException
	 */
	private List<String> queryEntTypeChild(String str[]) throws OptimusException{
		if(str==null||str.length<1){
			return null;
		}
		IPersistenceDAO dao = getPersistenceDAO(this.getDc_code_KEY());
		HashSet h=new HashSet();
		for (int i=0;i<str.length;i++){
			StringBuffer sql = new StringBuffer();
			List<String> str1 = new ArrayList<String>(); 
			sql.append(
					"select m.code from com_enttype_new m where m.is_stand ='Y' start with m.code= ? connect by m.pid =prior m.code ");
	
			sql.append(" order  by  code ");
			
			str1.add(str[i]);
			List<Map> list1 = dao.queryForList(sql.toString(), str1);
			if(list1!=null && list1.size()>0){
				for(Map map1 :list1){
					h.add(map1.get("code"));
				}
			}
		}
		
		List<String> list2 = new ArrayList<String> ();  
		list2.addAll(h);  
		return list2;
	}
	/**
	 * 获取管辖区域
	 * @return
	 * @throws OptimusException 
	 */
	public Map findRegorg() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(this.getDc_code_KEY());
		Map<String, String> map = new HashMap<String,String>();

		List<String> str1 = new ArrayList<String>(); 
		
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		
		//获取当前人所属的监管所
		String yyjcorgcode =user.getUpOrgCode();
		
		if(StringUtils.isEmpty(yyjcorgcode)){
		
			map.put("codeindexCode", null);
		}else{
			StringBuffer sql = new StringBuffer();
			sql.append("select * from REGNO_TRANS t where t.code = ? ");
			str1.add(yyjcorgcode);
			List<Map> list1=dao.queryForList(sql.toString(), str1);
			
		
			if(list1!=null&&list1.size()>0){
				 map=list1.get(0);
			}
		}
		return map;
	}
}
