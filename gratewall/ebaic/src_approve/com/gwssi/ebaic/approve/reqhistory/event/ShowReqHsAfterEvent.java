package com.gwssi.ebaic.approve.reqhistory.event;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.approve.util.ProcessUtil;
import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.optimus.core.cache.dictionary.DicData;
import com.gwssi.optimus.core.cache.dictionary.DictionaryManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.torch.db.result.PageBo;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

/**
 * 显示业务办理历史。
 * 
 * @author xupeng
 */
@Service("showReqHsAfterEvent")
public class ShowReqHsAfterEvent implements OnEventListener{

	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		// 获得查询结果list
		PageBo pageBo = (PageBo) result;
		List<Map<String,Object>> resutList = pageBo.getResult();
		if(resutList==null || resutList.isEmpty()){
			return ;
		}
		// 加载码表
		DicData dicData = null;
		DicData dicDataResult = null;
		try {
			dicData = DictionaryManager.getData("DFCS05EBAIC");
			dicDataResult = DictionaryManager.getData("DFCA36");
		} catch (OptimusException e) {
			throw new EBaicException("加载码表失败："+e.getMessage());
		}
		// 逐行处理
		for(Map<String, Object> row : resutList){
			// 获取组织机构信息
			String regOrg = (String) row.get("regOrg");
			regOrg = ProcessUtil.getRegOrgByFk(regOrg);
			row.put("regOrg", regOrg);
			// 转换码表,DFCS05_EBAIC
			String processStep = (String) row.get("processStep");
			if(dicData!=null){
				processStep = dicData.getText(processStep);
				row.put("processStep", processStep);
			}
			// 转换码表,DFCA36
			String processResult = (String) row.get("processResult");
			if(dicDataResult!=null){
				processResult = dicDataResult.getText(processResult);
				row.put("processResult", processResult);
			}
		}// end of for
	}

	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		// do nothing
	}

}
