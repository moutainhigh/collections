package com.gwssi.dw.dq.dao;

import java.util.Map;

import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;

public class LinkDao
{
	private String connType;

	public LinkDao(String connType)
	{
		this.connType = connType;
	}
	
	public String getURL(String code, String userId, String type) {
		String sql = "select LINK from DQ_BASIC_LINK where LINK_CODE='" + code +"' and LINK_TYPE='" + type + "'";
		String link = "";
		try {
			Map map = DbUtils.selectOne(sql, this.connType);
			link = (String)map.get("LINK");
			link += "&CAMUsername=" + userId + "&CAMPassword=" + userId;
		} catch (DBException e) {
//			e.printStackTrace();
		}
		return link;
	}

}
