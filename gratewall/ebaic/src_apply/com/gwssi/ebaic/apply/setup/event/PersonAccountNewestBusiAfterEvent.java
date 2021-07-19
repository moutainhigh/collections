package com.gwssi.ebaic.apply.setup.event;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.apply.util.ReqStateMappingUtil;
import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.step.StepUtil;
import com.gwssi.torch.db.result.PageBo;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

/**
 * 个人账户首页 最新办理业务 AFTER EVENT。
 * 
 * @author chaiyoubing
 */
@Service("PersonAccountNewestBusiAfter")
public class PersonAccountNewestBusiAfterEvent implements OnEventListener {

	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		PageBo pageBo =(PageBo) result;
		List<Map<String, Object>> list = pageBo.getResult();
		//将列表中的操作项查询出来
		StepUtil.getList("appy_person_account_list", list);
		
		
		//处理列表中的状态，业务类型转化为文本
		if(!list.isEmpty()&&list.size()>0){
			Map<String, Object> map = list.get(0);
			
			ReqStateMappingUtil.changeOperTypeText(map);
			ReqStateMappingUtil.changeStateText(map);
		
		}
	}

	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		
	}

	
}
