package com.gwssi.ebaic.apply.entaccount.event;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.apply.util.ReqStateMappingUtil;
import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.torch.db.result.PageBo;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

/**
 * 
 * @author qiaozy
 *
 */
@Service("entAccountDetailAfterEvent")
public class EntAccountDetailAfterEvent implements OnEventListener {

	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		
		PageBo pageBo =(PageBo) result;
		List<Map<String, Object>> list = pageBo.getResult();
		if(list.isEmpty()){
			return ;
		}
		
		//处理列表中的状态，转化为文本
		Iterator<Map<String, Object>> iterator = list.iterator();
		while(iterator.hasNext()){
			Map<String, Object> map = iterator.next();
			ReqStateMappingUtil.changeIdStateText(map);
			
		}
		
		
	}

	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		
	}

	

}
