package com.gwssi.ebaic.torch.event;

import java.util.Map;

import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.web.TorchController;

/**
 * For Before or After.
 * 
 * 适用于 继承自BaseEventListener实现不了的情况。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public interface OnEventListener {
	/**
	 * 执行查询类方法校验  或 设置参数默认值 或 执行后续处理。
	 * 
	 * @param controller
	 * @param params
	 * @param editConfigBo
	 * @param result
	 */
	void execQuery(TorchController controller,Map<String, String> params,QueryConfigBo editConfigBo,Object result);
	/**
	 * 执行编辑类方法校验  或 设置参数默认值 或 执行后续处理。
	 * 
	 * @param controller
	 * @param params
	 * @param editConfigBo
	 * @param result
	 */
	void execEdit(TorchController controller,Map<String, String> params,EditConfigBo editConfigBo,Object result);
}
