package com.gwssi.ebaic.apply.setup.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.rodimus.dao.DaoUtil;

@Service("setupContactService")
public class SetupContactService {
	
	@SuppressWarnings("rawtypes")
	public Map getContactDetail(String ivtId){
		String sql = " select i.inv,i.tel,i.cer_type,i.cer_no from be_wk_investor i where i.investor_id = ? ";
		List<String> params=new ArrayList<String>();
		params.add(ivtId);
		List<Map<String,Object>> list = null;
		list = DaoUtil.getInstance().queryForList(sql, params);
		
		if(list!=null && list.size()>0){
			return list.get(0);
		}else if(list == null || list.size() == 0){
			String sql1 = "select ent.name,ent.cer_type,ent.cer_no from be_wk_entmember ent where ent.entmember_id = ?";
			List<String> params1 = new ArrayList<String>();
			params1.add(ivtId);
			List<Map<String,Object>> list1 = null;
			list1 = DaoUtil.getInstance().queryForList(sql1, params1);
			if(list1==null || list1.isEmpty()){
				return null;
			}
			return list1.get(0);
		}else{
			return null;
		}
	}

}
