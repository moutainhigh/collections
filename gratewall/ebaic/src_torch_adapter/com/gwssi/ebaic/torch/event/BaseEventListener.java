package com.gwssi.ebaic.torch.event;

import java.util.Map;

import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

/**
 * Before / After 基类，优先使用这个。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public abstract class BaseEventListener implements OnEventListener {

	/* (non-Javadoc)
	 * @see com.gwssi.ebaic.torch.event.OnEventListener#execQuery(com.gwssi.torch.web.TorchController, java.util.Map, com.gwssi.torch.domain.query.QueryConfigBo, java.lang.Object)
	 */
	public void execQuery(TorchController controller,Map<String,String> formData, QueryConfigBo queryConfigBo,Object result) {
		exec(formData);
	}
	
	/* (non-Javadoc)
	 * @see com.gwssi.ebaic.torch.event.OnEventListener#execEdit(com.gwssi.torch.web.TorchController, java.util.Map, com.gwssi.torch.domain.edit.EditConfigBo, java.lang.Object)
	 */
	public void execEdit(TorchController controller,Map<String,String> formData, EditConfigBo editConfigBo,Object result) {
		exec(formData);
	}
	
	/**
	 * @param formData
	 */
	public abstract void exec(Map<String,String> formData);

}
