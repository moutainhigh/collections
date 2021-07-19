package com.gwssi.rodimus.indentity.support;

import com.gwssi.rodimus.exception.RodimusException;
import com.gwssi.rodimus.indentity.domain.IdentityCardBO;
import com.gwssi.rodimus.util.StringUtil;

/**
 * 公安部身份核查接口。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class IdentityServiceImpl implements IdentityService{

	/* (non-Javadoc)
	 * @see com.gwssi.rodimus.indentity.support.IdentityService#validate(java.lang.String, java.lang.String)
	 */
	public IdentityCardBO validate(String name, String cerNo) {
		// 0. 调用公安接口
		IdentifyWebserviceClientgs client = new IdentifyWebserviceClientgs();
		IdentifyWebservicePortType service = client.getIdentifyWebserviceHttpPort();
		String info = "";
		try {
			info = service.checkOneIdCard(cerNo, name);
		} catch (Throwable e) {
			throw new RodimusException("身份证核查系统暂时无法工作，请稍后重试。", e);
		}

		IdentityCardBO ret = null;
		String serviceCerNo = "";
		if (StringUtil.isBlank(info)) {
			// 返回数据为空
			throw null;
		}
		String[] tempArray = info.split("##");
		if (tempArray == null || tempArray.length == 0) {
			return null;
		}
		ret = new IdentityCardBO();
		// 返回结果格式正确
		for (int i = 0; i < tempArray.length; i++) {
			if (tempArray[i].startsWith("xm")) {
				String serviceName = tempArray[i].substring("xm".length() + 1);
				ret.setName(serviceName);
			} else if (tempArray[i].startsWith("sfhh")) {
				serviceCerNo = tempArray[i].substring("sfhh".length() + 1);
				ret.setCerNo(serviceCerNo);
			} else if (tempArray[i].startsWith("xb")) {
				String sex = tempArray[i].substring("xb".length() + 1);
				ret.setSex(sex);
			} else if (tempArray[i].startsWith("mz")) {
				String folk = tempArray[i].substring("mz".length() + 1);
				ret.setFolk(folk);
			} else if (tempArray[i].startsWith("csrq")) {
				String csrq = tempArray[i].substring("csrq".length() + 1);
				ret.setBirthday(csrq);
			} else if (tempArray[i].startsWith("xp")) {
				String xp = tempArray[i].substring("xp".length() + 1);
				ret.setPicData(xp);
			}
		}// end of for tempArray
		return ret;
	}
}
