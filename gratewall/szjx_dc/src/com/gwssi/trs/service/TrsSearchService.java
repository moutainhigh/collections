package com.gwssi.trs.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.util.UuidGenerator;
import com.gwssi.trs.model.SearchCountBO;


@Service("trsSearchService")
public class TrsSearchService extends BaseService{
	
	private static String getDetail_datasourcekey(){
		Properties properties = ConfigManager.getProperties("optimus");
		
		String key= properties.getProperty("regDetail.datasourcekey");


		return key;
	}
	
	public List<Map> querySearchCountBo() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		
		//编写sql语句
        String sql = "select rownum ,ta.*  from(  select * from SEARCH_COUNT t order by t.search_count desc) ta where rownum <5  ";
		

        //封装结果集

        List<Map> list =  dao.pageQueryForList(sql, null);
        return list;
	}

	public void doSearchConntDual(String string) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		List<String> strlist=new ArrayList<String>();
		//编写sql语句
        String sql = "select * from SEARCH_COUNT t where t.search_text= ?";
        strlist.add(string);
        List<SearchCountBO>  list =dao.queryForList(SearchCountBO.class,sql.toString(), strlist);
        BigDecimal b2 = new BigDecimal(1);
        if(list.size()>0){
        	SearchCountBO bo =list.get(0);
        	bo.setSearchCount(bo.getSearchCount().add(b2));
        	dao.update(bo);
        }else{
        	SearchCountBO bo = new SearchCountBO();
        	bo.setSearchCount(b2);
        	bo.setSearchText(string);
        	dao.insert(bo);
        }
       
       
	}
	
}
