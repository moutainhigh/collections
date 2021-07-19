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
public abstract class EditBaseEventListener implements OnEventListener {

	/* (non-Javadoc)
	 * @see com.gwssi.ebaic.torch.event.OnEventListener#execQuery(com.gwssi.torch.web.TorchController, java.util.Map, com.gwssi.torch.domain.query.QueryConfigBo, java.lang.Object)
	 */
	public void execQuery(TorchController controller,Map<String,String> formData, QueryConfigBo queryConfigBo,Object result) {
		// do nothing
	}
	
	/* (non-Javadoc)
	 * @see com.gwssi.ebaic.torch.event.OnEventListener#execEdit(com.gwssi.torch.web.TorchController, java.util.Map, com.gwssi.torch.domain.edit.EditConfigBo, java.lang.Object)
	 */
	public void execEdit(TorchController controller,Map<String,String> formData, EditConfigBo editConfigBo,Object result) {
		exec(controller,formData, editConfigBo,result);
	}

	/* (non-Javadoc)
	 * @see com.gwssi.ebaic.torch.event.OnEventListener#execQuery(com.gwssi.torch.web.TorchController, java.util.Map, com.gwssi.torch.domain.query.QueryConfigBo, java.lang.Object)
	 */
	public abstract void exec(TorchController controller,Map<String,String> formData, EditConfigBo queryConfigBo,Object result);

}
