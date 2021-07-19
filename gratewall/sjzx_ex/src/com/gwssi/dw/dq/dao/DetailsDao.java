package com.gwssi.dw.dq.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import net.sf.json.JSONObject;

import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;

public class DetailsDao
{
	private String		connType = null;

	public DetailsDao(String connType)
	{
		super();
		this.connType = connType;
	}

	/**
	 * 更新实体明细的两种状态:是否显示,是否启用
	 * @param str 数组,[id,isShow,isActive,id,isShow,isActive]格式
	 * @throws SQLException 
	 */
	public String updateStatus(String[] str) {
		String msg = "true";
		String sql = "update DQ_ENTITY_DETAILS set ISSHOW=?,ISFILTER=? where DETAILS_ID=?";
		Connection conn = null;
		try {
			conn = DbUtils.getConnection(this.connType);
			PreparedStatement ps = conn.prepareStatement(sql);
			for(int i=0,len = str.length/3;i < len;i++) {
				ps.setString(1, str[i*3+1]);
				ps.setString(2, str[i*3+2]);
				ps.setString(3, str[i*3]);
				ps.executeUpdate();
			}
			ps.close();
		} catch (DBException e) {
//			e.printStackTrace();
			msg = "false";
		} catch (SQLException e) {
//			e.printStackTrace();
			msg = "false";
		} finally {
			if (conn != null) {
				try {
					DbUtils.freeConnection(conn);
				} catch (DBException e) {
//					e.printStackTrace();
				}
				conn = null;
			}
		}
		return msg;
	}
	
	public String getDetail(String id) {
		String back = "";
		String sql = "select DETAILS_ID,DETAILS_NAME,DETAILS_WIDTH,ISSHOW,FLAG_SORT,ISFILTER,DETAILS_NUM,DETAILS_HREF,DETAILS_PARAMS,DETAILS_PARAMSALIAS " +
				"from DQ_ENTITY_DETAILS where DETAILS_ID='" +
				id +
				"'";
		Map map;
		try {
			map = DbUtils.selectOne(sql, this.connType);
			back = JSONObject.fromObject(map).toString();
		} catch (DBException e) {
//			e.printStackTrace();
		}
		return back;
	}
	
	public String updateDetail(Map map) {
		Connection conn = null;
		String msg = "true";
		String sql = "update DQ_ENTITY_DETAILS set DETAILS_NAME=?,DETAILS_WIDTH=?,ISSHOW=?,ISFILTER=?," +
				"DETAILS_NUM=?,DETAILS_HREF=?,DETAILS_PARAMS=?,DETAILS_PARAMSALIAS=?,FLAG_SORT=? " +
				"where DETAILS_ID=?";
		try {
			conn = DbUtils.getConnection(this.connType);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, (String)map.get("details_name"));
			ps.setString(2, (String)map.get("details_width"));
			ps.setString(3, (String)map.get("isShow"));
			ps.setString(4, (String)map.get("isFilter"));
			ps.setString(5, (String)map.get("details_num"));
			ps.setString(6, (String)map.get("details_href"));
			ps.setString(7, (String)map.get("details_params"));
			ps.setString(8, (String)map.get("details_paramsalias"));
			ps.setString(9, (String)map.get("flag_sort"));
			ps.setString(10, (String)map.get("details_id"));
			ps.executeUpdate();
			ps.close();
		} catch (DBException e) {
//			e.printStackTrace();
			msg = "false";
		} catch (SQLException e) {
//			e.printStackTrace();
			msg = "false";
		} finally {
			if (conn != null) {
				try {
					DbUtils.freeConnection(conn);
				} catch (DBException e) {
//					e.printStackTrace();
				}
				conn = null;
			}
		}
		return msg;
	}

}
