package com.gwssi.ebaic.apply.setup.event;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.apply.util.ReqStateMappingUtil;
import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.step.StepUtil;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.torch.db.result.PageBo;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

/**
 * 个人账户 我的业务列表Before Event。
 * 
 * @author chaiyoubing
 */
@Service("PersonAccountBusiListAfter")
public class PersonAccountBusiListAfterEvent implements OnEventListener {

	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		PageBo pageBo =(PageBo) result;
		List<Map<String, Object>> list = pageBo.getResult();
		if(list.isEmpty()){
			return ;
		}
		Iterator<Map<String, Object>> iter = list.iterator();
		while(iter.hasNext()){
			Map<String, Object> map = iter.next();
			map.put("userType", HttpSessionUtil.getLoginUserType());
		}
		//将列表中的操作项查询出来
		StepUtil.getList("appy_person_account_list", list);
		//处理列表中的状态，转化为文本
		Iterator<Map<String, Object>> iterator = list.iterator();
		while(iterator.hasNext()){
			Map<String, Object> map = iterator.next();
			
			ReqStateMappingUtil.changeStateText(map);
		}
		
	}

	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		
	}

	
}
