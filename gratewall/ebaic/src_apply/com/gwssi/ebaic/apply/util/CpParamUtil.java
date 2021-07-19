package com.gwssi.ebaic.apply.util;

import com.gwssi.ebaic.domain.BeWkEntBO;
import com.gwssi.ebaic.domain.BeWkReqBO;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.util.StringUtil;

/**
 * 
 * @author liuhailong
 */
public class CpParamUtil {
	
	/**
	 * 
	 * @return
	 */
	public static String getId(){
		String id = ParamUtil.get("id",false);
		if(StringUtil.isBlank(id)){
			id = ParamUtil.get("req_id",false);
			if(StringUtil.isBlank(id)){
				id = ParamUtil.get("gid",false);
				if(StringUtil.isBlank(id)){
					id = ParamUtil.get("reqId",false);
					if(StringUtil.isBlank(id)){
						id = ParamUtil.get("ReqId",false);
						if(StringUtil.isBlank(id)){
							id = ParamUtil.get("Gid",false);
							if(StringUtil.isBlank(id)){
								id = ParamUtil.get("Id",false);
								if(StringUtil.isBlank(id)){
									id = ParamUtil.get("ID",false);
								}
							}
						}
					}
				}
			}
		}
		return id;
	}
	/**
	 * 
	 * @return
	 */
	public static BeWkReqBO getReqBo() {
		String id = getId();
		BeWkReqBO ret = DaoUtil.getInstance().queryForRowBo("select * from be_wk_requisition r where r.gid = ?", BeWkReqBO.class, id);
		if(ret==null){
			return new BeWkReqBO();
		}
		return ret;
	}
	/**
	 * 
	 * @return
	 * @throws OptimusException
	 */
	public static BeWkEntBO getEntBo() {
		String id = getId();
		String sql = "select * from cp_wk_ent t where t.gid = ?";
		BeWkEntBO ret = DaoUtil.getInstance().queryForRowBo(sql, BeWkEntBO.class, id);
		if(ret==null){
			return new BeWkEntBO();
		}
		return ret;
	}
}
