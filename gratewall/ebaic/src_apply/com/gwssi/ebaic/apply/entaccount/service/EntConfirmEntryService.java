package com.gwssi.ebaic.apply.entaccount.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.domain.SysmgrIdentityBO;
import com.gwssi.rodimus.dao.BaseDaoUtil;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.StringUtil;

@Service(value="entConfirmEntryService")
public class EntConfirmEntryService {
	 /**
     * 查询身份验证手机号
     * 
     * @param regNo
     * @return
     */
    public String queryEntConfirmMobile(String regNo){
    	if(StringUtils.isBlank(regNo)){
    		throw new EBaicException("注册号/社会统一信用代码不能为空。");
    	}
    	StringBuffer sql = new StringBuffer(" select i.flag,i.mobile from sysmgr_identity i where i.cer_no=? and i.person_sign='0' and i.flag<>'3' ");
    	List<String> params = new ArrayList<String>();
    	params.add(regNo);
    	Map<String, Object> retMap = new HashMap<String, Object>();
    	retMap = DaoUtil.getInstance().queryForRow(sql.toString(), params);
    	if(retMap==null || retMap.isEmpty()){
    		throw new EBaicException("未找到身份认证记录，请确认输入信息是否正确。");
    	}
    	String flag = StringUtil.safe2String(retMap.get("flag"));
    	String mobile = StringUtil.safe2String(retMap.get("mobile"));
    	if(!"1".equals(flag) && !"0".equals(flag)){
    		//TODO 自动跳转到手机APP介绍页面
    		throw new EBaicException("身份认证未提交，请先通过移动客户端提交身份认证再进行登录。");
    	}
    	if(StringUtils.isBlank(mobile)){
    		throw new EBaicException("企业联系人手机号为空，请联系系统管理员。");
    	}
    	return mobile;
    	
    }
    /**
     * 查询企业用户身份认证信息
     * 
     * @param identityId
     * @return
     */
    public SysmgrIdentityBO querySysmgrIdentityBO(String identityId){
    	if(StringUtils.isBlank(identityId)){
    		throw new EBaicException("身份认证编号不能为空。");
    	}
    	List<String> params = new ArrayList<String>();
    	params.add(identityId);
    	StringBuffer sql = new StringBuffer(" select i.name,i.identity_id,i.type,i.flag,i.cer_type,i.cer_no ,i.mobile");
    	sql.append(" from sysmgr_identity i where i.identity_id=? ");
    	BaseDaoUtil dao = DaoUtil.getInstance();
    	SysmgrIdentityBO sysmgrIdentityBO = dao.queryForRowBo(sql.toString(), SysmgrIdentityBO.class, params);
		return sysmgrIdentityBO;
    }
}
