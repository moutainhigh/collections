package com.gwssi.ebaic.apply.security.user.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.optimus.plugin.auth.model.TPtYhjsBO;
import com.gwssi.optimus.util.MD5Util;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.indentity.IdentityCardUtil;
import com.gwssi.rodimus.sms.SmsVerCodeUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.util.UUIDUtil;
import com.gwssi.torch.web.TorchController;



@Service("UserRegistBeforeEvent")
public class UserRegistBeforeEvent implements OnEventListener{

	public void execEdit(TorchController controller, Map<String, String> formData,EditConfigBo editConfigBo,Object result)   {
		if(formData == null || formData.isEmpty()){
			throw new EBaicException("获取页面用户信息失败!");
		}
		String cerNo = formData.get("cerNo");
		if(StringUtil.isBlank(cerNo)){
			throw new EBaicException("没有录入身份证号，请重新录入。");
		}
		String loginName = formData.get("loginName");
		if(StringUtil.isBlank(loginName)){
			throw new EBaicException("没有录入登录名，请重新录入。");
		}
		String userPwd = formData.get("userPwd");
		if(StringUtil.isBlank(userPwd)){
			throw new EBaicException("请输入密码。");
		}
		//验证手机验证码
		String mobile = formData.get("mobile");
		if(StringUtils.isEmpty(mobile)) {
			throw new EBaicException("请输入移动电话！");
		}
		String mobileCode = formData.get("mobileCode");
		if(StringUtils.isEmpty(mobileCode)) {
			throw new EBaicException("请输入验证码！");
		}
		SmsVerCodeUtil.verify(mobile, mobileCode);
		
		String userName = formData.get("userName");
		if(StringUtil.isBlank(userName)){
			throw new EBaicException("请输入姓名。");
		}
		String email = formData.get("email");
		if(StringUtil.isBlank(email)){
			throw new EBaicException("请输入电子邮箱。");
		}
		String address = formData.get("address");
		if(StringUtil.isBlank(address)){
			throw new EBaicException("请输入联系地址。");
		}
		
		if(checkRegistNumber(cerNo)){
			throw new EBaicException("当前身份证号已有用户，请重新输入身份证号。");
		}
		if(checkUser(loginName)){
			throw new EBaicException("登录名已经存在。");
		}
//		if(checkedTelephone(mobile)){
//			throw new EBaicException("该移动电话号码已被注册。");
//		}
		
		boolean isIdCardOk = IdentityCardUtil.check(userName, cerNo);
		if(!isIdCardOk){
			throw new EBaicException("姓名和身份证号码不匹配，请查对后重新输入。");
		}
		
		//判断身份证长度
		if(cerNo.length() == 15){
			throw new EBaicException("请输入一个18位的身份证号。");
		}
		
		// 身份校验通过
		formData.put("checkState", "0");
		String sex="";
		String genderPart=cerNo.substring(16, 17);
		if(Integer.parseInt(genderPart)%2==0){
			sex="0";
		}else{
			sex="1";
		}
		// 密码MD5加密
		userPwd = MD5Util.MD5Encode(userPwd);
		formData.put("userPwd", userPwd);
		formData.put("idCard", cerNo);
		formData.put("sex",sex);
		
		String userId = UUIDUtil.getUUID();
		formData.put("userId", userId);
		
		TPtYhjsBO yhjs = new TPtYhjsBO();
		
		yhjs.setUserId(userId);
		yhjs.setId(UUIDUtil.getUUID());
		yhjs.setJsId("default_role");
		DaoUtil.getInstance().insert(yhjs);
	}

	
	/**
	 * 检验该身份证号是否已经有账号
	 * @param cerNo
	 * @return  有账号返回true
	 */
	public boolean checkRegistNumber(String cerNo){
		String sql = "select count(1) from t_pt_yh yh where yh.cer_no=? and yx_bj='1' "
				+ " and ( yh.zy_js_id=?" + // 新网普通用户
				" or yh.zy_js_id='b7e43225ce6e518aad4f2db992518e5f' " + // 某导入的未知角色用户
				" or yh.zy_js_id is null)";// 导入的未配置角色的用户
		List<String> param = new ArrayList<String>();
		param.add(cerNo);
		param.add("default_role");
		long num = 0;
		try {
			num = DaoUtil.getInstance().queryForOneLong(sql, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num >= 1;
	}
	
	/**
	 * 检验登录名是否存在，存在则返回true
	 * @param login_name
	 * @return
	 */
	public boolean checkUser(String loginName) {
		List<String> params = new ArrayList<String>();
		params.add(loginName.toUpperCase());
		long count = 0;
		try {
			count = DaoUtil.getInstance().queryForOneLong(
					"select count(1) from t_pt_yh u where UPPER(u.login_name) = ?",
					params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count > 0;
	}

	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		
		
	}

	
	/**
	 * 检验移动电话码是否已经被注册过 
	 * @param telephone
	 * @return 已经注册过了 有该电话号码 返回true
	 * 
	 */
	public boolean checkedTelephone(String telephone){
		long count = 0;
		String sqlCheckTel = "select count(1) from t_pt_yh where mobile = ? ";
		try {
			count =  DaoUtil.getInstance().queryForOneLong(sqlCheckTel, telephone);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(count >= 1){
			return true;
		}else{
			return false;
		}
	}
}
