package com.gwssi.dw.dq.business;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import com.genersoft.frame.base.database.DBException;
import com.gwssi.dw.dq.DqContants;
import com.gwssi.dw.dq.dao.TemplatesDao;

import net.sf.json.JSONObject;

public class Templates
{
	private TemplatesDao	dao = new TemplatesDao(DqContants.CON_TYPE);

	public String searchTemp(String propertyName,String pager,String pageIndex){
		
		Map map = new HashMap();
		try {
			map =dao.searchTemp(propertyName,pager,pageIndex);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pack2Json(map);
	}
	public String delTemplate(String templateId)
	{
		boolean flag = false;
		try {
			flag = dao.delTemplates(templateId);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag + "";
	}

	private String pack2Json(Object obj)
	{
		return JSONObject.fromObject(obj).toString();
	}
}
