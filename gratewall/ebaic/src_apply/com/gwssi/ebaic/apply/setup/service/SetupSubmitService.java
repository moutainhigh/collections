package com.gwssi.ebaic.apply.setup.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.common.util.SubmitUtil;
import com.gwssi.ebaic.domain.TPtYhBO;
import com.gwssi.rodimus.config.ConfigUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.rule.RuleUtil;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.validate.msg.ValidateMsg;

/**
 * 设立提交。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Service("setupSubmitService")
public class SetupSubmitService {

	
	public String beforeSubmit(String gid) {
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("无效的业务标识:"+gid);
		}
		// 当前登录用户
		TPtYhBO user = HttpSessionUtil.getCurrentUser();
		if(user==null){
			throw new RuntimeException("登录超时，请重新登录。");
		}
		//String curUserCerType = user.getCerType();
		//String curUserCerNo = user.getCerNo();
		
		//检查提交业务规则
		Map<String,Object> params = ParamUtil.prepareParams(gid);
		if(params==null || params.isEmpty()){
			throw new RuntimeException("传入的gid参数不正确。");			
		}
		//params.put("gid", gid);
		//params.put("cerNo", curUserCerNo);//当前登录用户
		//params.put("cerType", curUserCerType);
		ValidateMsg msg= RuleUtil.getInstance().runApp("ebaic_setup_submit",params);
		String errors =  msg.getLockedMsg();
		if(StringUtil.isNotBlank(errors)){
			throw new RuntimeException(msg.getAllMsgString());
		}
		
		return "success";
		
		
	}
	/**
	 * 公司设立申请人提交。用于预览页面
	 * 
	 * @param gid
	 */
	public String cpSubmit(String gid,String mobileCode)  {
		
		/**1参数判断**/
		if(StringUtil.isBlank(gid)){
			throw new EBaicException("gid参数不能为空。");			
		}
		/**2判断是否已经登录**/
		TPtYhBO user=HttpSessionUtil.getCurrentUser();
		if(user ==null){
			throw new EBaicException("登录超时，请重新登录。");
		}
		/**3验证码校验**/
		//String mobile = user.getMobile();		
		//SmsVerCodeUtil.verify(mobile, mobileCode);
		if(SubmitUtil.submitAuthCheck(gid)){
			//拥有所有法人和股东的授权，直接提交至内网
			SubmitUtil.cpSetupSubmit(gid);
			SubmitUtil.processRecord(gid,"07", "2", "提交至工商机关审核","");
			String msg = "提交成功，您将在两个工作日内收到反馈结果，请留意系统信息与短信提醒。";
			return msg;
			
		}else{
			//提交待确认
			SubmitUtil.submitToConfirm(gid);
			SubmitUtil.processRecord(gid, "07","1", "提交给法人和股东确认","");
			return ConfigUtil.get("submit.noAuthMsg");
		}
		
		
		// 6.发送短信提醒
				/*String sql = "select t.le_rep_mob as mobile,r.linkman,r.ent_name from be_wk_le_rep t left join be_wk_requisition r on r.gid=t.gid where t.gid = ?";
				Map<String,Object> row = DaoUtil.getInstance().queryForRow(sql, gid);
				if(row!=null && !row.isEmpty()){
					String mobile = StringUtil.safe2String(row.get("mobile"));
					String linkman = StringUtil.safe2String(row.get("linkman"));
					String entName = StringUtil.safe2String(row.get("entName"));
			        Map<String,Object> smsParams = new HashMap<String,Object>();
			        smsParams.put("entName", entName);
			        smsParams.put("linkman", linkman);
			        SmsUtil.send(mobile, SmsBusiType.LE_MAKE_SURE_NOTICE, smsParams);
				}*/
		
	}

	
	
}
