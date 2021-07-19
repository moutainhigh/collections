package com.gwssi.ebaic.apply.setup.event;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Service("mbrBeforeEvent")
public class MbrBeforeEvent implements OnEventListener {

	public void execEdit(TorchController controller, Map<String, String> formData,EditConfigBo editConfigBo, Object result) {
		String gid = formData.get("gid");
		String sql = "select count(1) as cnt from cp_wk_entmember mbr where mbr.gid = ? ";
		long cnt = DaoUtil.getInstance().queryForOneLong(sql, gid);
		if(cnt<1 || cnt>13){
			throw new EBaicException("主要人员至少一人，最多13人。");
		}
		
	}

	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,Object result) {
		// TODO Auto-generated method stub
		
	}

}
