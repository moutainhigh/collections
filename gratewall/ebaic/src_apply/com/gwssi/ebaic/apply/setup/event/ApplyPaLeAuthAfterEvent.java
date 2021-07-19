package com.gwssi.ebaic.apply.setup.event;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.common.util.SubmitUtil;
import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;
/**
 * 法定代表人授权。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Service("applyPaLeAuthAfter")
public class ApplyPaLeAuthAfterEvent implements OnEventListener{

	/* (non-Javadoc)
	 * @see com.gwssi.ebaic.torch.event.OnEventListener#execEdit(com.gwssi.torch.web.TorchController, java.util.Map, com.gwssi.torch.domain.edit.EditConfigBo, java.lang.Object)
	 */
	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		String gid = StringUtil.safe2String(params.get("gid"));
		if(StringUtil.isBlank(gid)){
			throw new EBaicException("gid不能为空。");
		}
		//原来是退回修改的改为4 原来是未提交的改为1
		//根据是否有审查信息
		String sql = "select case when r.censor_result is null and r.approve_result is null then '1' else '4' end as state from be_wk_requisition r where gid=?";
		Map<String, Object> map = DaoUtil.getInstance().queryForRow(sql, gid);
		if(map==null || map.isEmpty()){
			throw new EBaicException("没有查到指定的业务数据（"+gid+"）。");
		}
		String state =StringUtil.safe2String(map.get("state"));
		if(StringUtil.isBlank(state)){
			state = "1";
		}
		sql ="update be_wk_requisition set state=?,auth_type='1' ,timestamp=sysdate where gid=?";
		DaoUtil.getInstance().execute(sql,state, gid);
		SubmitUtil.processRecord(gid, "09",state, "法人授权","");
	}
	
	
	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		// do nothing 
		System.out.println("query after");
	}

}
