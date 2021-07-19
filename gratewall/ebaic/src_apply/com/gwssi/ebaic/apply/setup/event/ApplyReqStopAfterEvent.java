package com.gwssi.ebaic.apply.setup.event;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

@Service("applyReqStopAfterEvent")
public class ApplyReqStopAfterEvent implements OnEventListener {

	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		
		
	}

	public void execEdit(TorchController controller,
			Map<String, String> param, EditConfigBo editConfigBo, Object result) {
		
//		TorchRequest tReq = (TorchRequest)ThreadLocalManager.get(ThreadLocalManager.TORCH_REQUEST);
		
		//TODO 保存process表
		//String sql = " update be_wk_entcontact t set t.cer_val_from = ?,t.cer_val_to = ? where t.gid = ? and t.cer_no = ?";
		
		//DaoUtil.getInstance().execute(sql, );
		
	}

}
