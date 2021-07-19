package com.gwssi.doublePublic.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CatalogService {


	@Resource(name = "jdbcTemplate")
	private JdbcTemplate template;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getList(String index, String rows, String keyWord,String title) {
		
		String totalSql = null;
		int pageIndex = Integer.valueOf(index);
		int pageSize = Integer.valueOf(rows);
		Integer i = null;
		String sql = null;
		List list = null;
		totalSql = "select count(1) from V_Dc_t_Busi_Sz_Xzxk where xk_xzxkLb = ?";
		i = template.queryForObject(totalSql,new Object[]{title}, Integer.class);
		//System.out.println(totalSql + " ==>" + "参数" + keyWord);
		//System.out.println("总数" + i);
		String sql2 = "select * from V_Dc_t_Busi_Sz_Xzxk where xk_xzxkLb = ?";
		sql = " SELECT * FROM ( SELECT A.*,rownum rn FROM (" + sql2+ " ) A WHERE rownum <=" + (pageIndex) * pageSize + " ) WHERE rn>" + (pageIndex - 1) * pageSize;
		list = template.queryForList(sql,new Object[]{title});
		//System.out.println(sql + " ==>" + "参数  " + keyWord);
		//System.out.println(list);
		List results = new ArrayList();
		Map map = new HashMap();
		map.put("total", i);
		map.put("list", list);

		results.add(map);
		return results;
	}
	
	

}
