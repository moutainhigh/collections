package com.gwssi.ebaic.common.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.torch.util.JSON;
import com.gwssi.torch.util.StringUtil;

@Controller
@RequestMapping("/dict/addr")
public class AddressController {
	
	@RequestMapping("/list")
	public void list(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String sql = "select d.dm as value, d.wb as text, d.fdm from t_pt_dmsjb d where d.dmb_id = 'CA01' and d.cj='3' order by d.fdm asc, d.dm asc";
		List<Map<String,Object>> dataList = DaoUtil.getInstance().queryForList(sql);
		Map<String,List<Map<String,Object>>> dataMap = new HashMap<String,List<Map<String,Object>>>();
		for(Map<String,Object> row : dataList){
			String fdm = StringUtil.safe2String(row.get("fdm"));
			List<Map<String,Object>> cityList = dataMap.get(fdm);
			if(cityList==null){
				cityList = new ArrayList<Map<String,Object>>();
				dataMap.put(fdm, cityList);
			}
			row.remove("fdm");
			cityList.add(row);
		}
		
		sql = "select d.dm as value, d.wb as text from t_pt_dmsjb d where d.dmb_id = 'CA01' and d.cj='2' order by d.fdm asc, d.dm asc";
		List<Map<String,Object>> provList = DaoUtil.getInstance().queryForList(sql);
		for(Map<String,Object> row : provList){
			String value = StringUtil.safe2String(row.get("value"));
			List<Map<String,Object>> cityList = dataMap.get(value);
			row.put("sub", cityList);
		}
		
		
		Map<String,Object> ret = new HashMap<String,Object>();
		List<Object> listWrapper = new ArrayList<Object>();
		Map<String,Object> mapWapper = new HashMap<String,Object>();
		mapWapper.put("data", provList);
		listWrapper.add(mapWapper);
		ret.put("data", listWrapper);
		response.addResponseBody(JSON.toJSON(ret));
	}
}
