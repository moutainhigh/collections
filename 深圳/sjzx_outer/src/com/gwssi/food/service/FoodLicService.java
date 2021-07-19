package com.gwssi.food.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.entSelect.util.StringUtil;
import com.gwssi.entSelect.util.util;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service("FoodLicService")
public class FoodLicService extends BaseService{
	
	/*
	 * 查询食品许可证列表
	 */
	public List<Map> queryfoodLicList(String entname,String licno) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = null;
		List<String> params = new ArrayList<String>();
		if(StringUtil.isEmpty(licno) && StringUtil.isEmpty(entname)){
			return null;
		}
		sql = new StringBuffer("select * from V_OUTER_FOOD_LICENSE t where 1=1 ");
		if(StringUtil.isNotEmpty(licno)){			
			sql.append(" and t.licno = ?");
			params.add(licno);
		}
		if(StringUtil.isNotEmpty(entname)){
			sql.append(" and t.entname = ?");
			params.add(entname);
		}
		sql.append(" and t.valid_date>=to_char(sysdate,'yyyy-mm-dd') and t.newflag='0'"); //有效
		return util.typechage(dao.pageQueryForList(sql.toString(), params));
	}
	
	/*
	 * 查询食品许可证详细信息
	 */
	public List<Map> queryfoodLicDetail(String foodid,String lictype) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");
		StringBuffer sql = new StringBuffer();
		List<String> params = new ArrayList<String>();
		if(StringUtil.isEmpty(foodid) && StringUtil.isEmpty(lictype)){
			return null;
		}
		if("1".equals(lictype)){ //食品经营许可证
			sql.append("select * from v_outer_food_jyxk t where 1=1");
			if(StringUtil.isNotEmpty(foodid)){			
				sql.append(" and t.licid = ?");
				params.add(foodid);
			}
			return util.typechage(cernoChange(dao.pageQueryForList(sql.toString(), params)));
		}else if("3".equals(lictype)){ //食品生产许可证
			sql.append("select * from v_outer_food_scxk t where 1=1");
			if(StringUtil.isNotEmpty(foodid)){			
				sql.append(" and t.licid = ?");
				params.add(foodid);
			}
			return util.typechage(cernoChange(dao.pageQueryForList(sql.toString(), params)));
		}else if("31".equals(lictype)){//食品生产许可证(产品信息)
			sql.append("select * from v_outer_food_PRODUCT t where 1=1");
			if(StringUtil.isNotEmpty(foodid)){			
				sql.append(" and t.main_tb_id = ?");
				params.add(foodid);
			}
			return util.typechage(dao.pageQueryForList(sql.toString(), params));
		}else if("5".equals(lictype)){ //食品流通许可证
			sql.append("select * from V_OUTER_FOOD_SPLT t where 1=1");
			if(StringUtil.isNotEmpty(foodid)){			
				sql.append(" and t.licid = ?");
				params.add(foodid);
			}
			return util.typechage(dao.pageQueryForList(sql.toString(), params));
		}else if("6".equals(lictype)){ //餐饮服务许可证
			sql.append("select * from V_OUTER_FOOD_CYFW t where 1=1");
			if(StringUtil.isNotEmpty(foodid)){			
				sql.append(" and t.licid = ?");
				params.add(foodid);
			}
			return util.typechage(dao.pageQueryForList(sql.toString(), params));
		}else{
			return null;
		}
	}
	
	/*
	 * 身份证转码
	 */
	public List<Map> cernoChange(List<Map> list) throws OptimusException{
		List<Map> res = new ArrayList<Map>();
		for(int i=0;i<list.size();i++){
			Map map = list.get(i);
			String entScCode = (String)map.get("entScCode");
			if(StringUtil.isNotEmpty(entScCode) && !"9".equals(entScCode.substring(0, 1)) && entScCode.length() == 18){
				String newentScCode = entScCode.replace(entScCode.substring(4), "**************");
				map.put("entScCode", newentScCode);
			}
			res.add(map);
		}
		
		return res;
	}
}
