package com.gwssi.ebaic.apply.setup.event;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

@Service("applySetupInvSaveBeforeEvent")
public class ApplySetupInvSaveBeforeEvent implements OnEventListener{

	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		if(params == null || params.isEmpty()){
			throw new EBaicException("获取表单信息异常！");
		}
		
		String gid = params.get("gid");
		if(StringUtils.isEmpty(gid)){
			throw new EBaicException("获取gid异常！");
		}
		
		
		
	}
	
	/**
	 * 是否所有股东信息都已填写完毕
	 * 未填写完毕则返回true,无则返回false
	 */
	public boolean hasInvInfoSaving(String gid){
		
		String sql = " select count(1) as cnt from be_wk_investor i where (i.modify_sign = '0' or i.modify_sign is null) and i.gid = ? ";
		ArrayList<String> sqlParams = new ArrayList<String>();
		sqlParams.add(gid);
		long count = DaoUtil.getInstance().queryForOneLong(sql, sqlParams);
		if(count > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 校验是否所有股东出资之和大于0，校验所有股东出资之和等于注册资本
	 * true则通过，false未通过
	 * @return
	 */
	public boolean hasConAmPassed(String gid){
		
		String sql = " select count(i.sub_con_am) from be_wk_investor i where i.gid =? ";
		ArrayList<String> sqlParams = new ArrayList<String>();
		sqlParams.add(gid);
		long count = DaoUtil.getInstance().queryForOneLong(sql, sqlParams);
		if(count > 0){
			return true;
		}
		return false;
		
	}
	
	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		// TODO Auto-generated method stub
		
	}


}
