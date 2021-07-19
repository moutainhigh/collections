package com.gwssi.rodimus.sms;

import com.gwssi.rodimus.config.BaseConfigManager;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.sms.domain.SmsWkTemplateBO;

/**
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class TemplateManager extends BaseConfigManager<SmsWkTemplateBO>{
	
	public static TemplateManager instance = new TemplateManager();
	
	@Override
	protected String getCachePrefix() {
		return "sms_tpl_";
	}
// 罗建龙
	@Override
	public SmsWkTemplateBO getConfigFromDb(String key) {
		SmsWkTemplateBO ret = DaoUtil.getInstance().queryForRowBo("select c.* from sms_wk_template c where c.id = ?", SmsWkTemplateBO.class, key);
		return ret;
	}
}
