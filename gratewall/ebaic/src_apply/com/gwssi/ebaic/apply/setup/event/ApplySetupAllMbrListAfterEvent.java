package com.gwssi.ebaic.apply.setup.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.RequestUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.db.result.PageBo;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

/**
 * 新增主要人员 Before Event。
 * 
 * @author chaiyoubing
 */
@Service("ApplySetupAllMbrListAfter")
public class ApplySetupAllMbrListAfterEvent implements OnEventListener {

	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		
		/**
		 * 1、得到前台传过来的业务gid
		 */
		OptimusRequest request = RequestUtil.getOptimusRequest();
		String gid = request.getParameter("gid");
		if(StringUtil.isBlank(gid)) {
			 throw new EBaicException("传入的业务gid不能为空。");
		}
		params.put("gid", gid);
		
		/**
		 * 2、查询be_wk_ent 表是否设立董事会isBorad，是否设立监事会isSups
		 */
		StringBuffer sql = new StringBuffer();
		sql.append("select ent.is_board,ent.is_suped from be_wk_ent ent where ent.gid = ? ");
		Map<String, Object> entMap = DaoUtil.getInstance().queryForRow(sql.toString(), gid);
		if(entMap.isEmpty()&&entMap.size()==0){
			throw new EBaicException("通过GID未从be_wk_ent表中查到数据");
		}
		/**
		 * 3、得到通过配置查询到的结果集
		 */
		PageBo pageBo =(PageBo) result;
		List<Map<String, Object>> list = pageBo.getResult();
		if(list.isEmpty()){
			throw new EBaicException("通过torch配置未查询到数据");
		}
		/**
		 * 4、将be_wk_ent和配置查询到的结果集处理成前台需要的格式
		 */
		Map<String, Object> ret = dealResultsToFront(entMap,list);
		list.add(ret);
		pageBo.setResult(list);
	}

	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		
	}
	
	/**
	 * 处理查询的结果，将其格式处理为
	 * var data = {
		    		isBorad : true ,
		    		isSups : false ,
		    		directors : [
		    			   {
		    				   invId : 'xxxxxxxxxxxxxxxx',
		    				   name : '张三丰',
		    				   position : '执行董事',
		    				   leRep : true
		    			   }
		    		],
		    		managers : [
							{
			    				   invId : 'xxxxxxxxxxxxxxxx',
								   name : '麦格',
								   position : '经理',
								   leRep : true
							}     
		    		],
		    		supervisors:[
				    		{
				 				   invId : 'xxxxxxxxxxxxxxxx',
								   name : '麦格',
								   position : '经理',
								   leRep : true
							}  
		    			]
		    	};
	 */
	
	public Map<String,Object> dealResultsToFront(Map<String, Object> map,List<Map<String, Object>> list){
		
		Map<String,Object> ret = new HashMap<String, Object>();
		ret.put("isBorad", map.get("isBorad"));
		ret.put("isSups", map.get("isSups"));
		List<Map<String,Object>> directorsList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> managersList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> supervisorsList = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> row : list){
			String positionType = (String)row.get("positionType");
			if("1".equals(positionType)){//董事
				directorsList.add(row);
			}
			if("2".equals(positionType)){//经理
				managersList.add(row);
			}
			if("3".equals(positionType)){//监事
				supervisorsList.add(row);
			}
		}
		ret.put("directors",directorsList);
		ret.put("managers", managersList);
		ret.put("supervisors", supervisorsList);
		return ret;
	}

}
