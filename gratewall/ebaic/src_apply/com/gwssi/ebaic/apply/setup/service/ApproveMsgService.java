package com.gwssi.ebaic.apply.setup.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.rodimus.dao.DaoUtil;

/**
 * 
 * 申请平台显示审批意见。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Service("approveMsgService")
public class ApproveMsgService {

	/**
	 * 获取最新一条审批记录。
	 * 
	 * @param gid
	 * @return
	 */
	public Map<String, Object> getLastestOne(String gid) {
		String sql = "select rp.reqprocess_id,rp.process_date,to_char(rp.pro_end_date,'yyyy-mm-dd') as pro_end_date,rp.reg_org,o.org_name,decode(rp.process_result,'10','通过','12','退回修改','13','驳回','14','核准') as process_result ,rp.process_notion from be_wk_reqprocess rp " +
				" left join sysmgr_org o on rp.reg_org=o.org_code where rp.gid = ? order by rp.timestamp desc ";
		Map<String, Object> ret = DaoUtil.getInstance().queryForRow(sql, gid);
		return ret;
	}

}
