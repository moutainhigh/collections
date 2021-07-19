package com.gwssi.sms.controller;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.gwssi.cxf.validatecode.manager.MyX509TrustManager;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.util.StringUtil;
import com.gwssi.sms.service.SMSService;
import com.gwssi.sms.util.TmStringUtils;

@Controller
@RequestMapping("/phone")
public class SMSController {

	@Autowired
	private SMSService smsService;
	@SuppressWarnings("unused")
	@RequestMapping("/getCode")
	public void senderSMS(OptimusRequest req, OptimusResponse res)
			throws Exception {
		HttpServletRequest request = req.getHttpRequest();
		String phone = req.getParameter("phone");
		phone=URLDecoder.decode(phone, "utf-8");
		//String strJson = null;
		//String key = "27C31DC6A30042BCA2020B41A8D38326";
		//String type = "0";
		//String senderCode;
		//String mess;
		if (TmStringUtils.isNotEmpty(phone)) {
			Map map = smsService.isExitCode(phone);//是否存在有效的验证码
			if (map != null&&map.size()!=0) {
				BigDecimal bd = (BigDecimal) map.get("code");
					res.addAttr("msg", this.executeSendSMS(phone, bd.intValue()));
			} else {
					res.addAttr("msg",this.executeSendSMS(phone, 0));
			}

		} else {
			res.addAttr("msg", "手机号不能为空");
		}

	}

	@SuppressWarnings("unused")
	public String executeSendSMS(String phoneNum, int code) {
		String strJson = null;
		String key = "27C31DC6A30042BCA2020B41A8D38326";
		String type = "0";
		String phone =phoneNum.trim();
		int a = code == 0 ? (int) (Math.random() * (9999 - 1000 + 1)) + 1000
				: code;// 产生1000-9999的随机数
		String mess;
		boolean isSend=false;
		String returnResult="";
		boolean isInsert;
		String oftenSend = smsService.isOftenSendSMS(phone.trim());
		if(oftenSend==null||"0".equals(oftenSend)){
			try {
				mess = URLEncoder.encode("您本次反馈请求的验证码:【" + a
						+ "】,有效期为15分钟，如不是本人操作请忽略。", "utf-8");
				String url = "https://api.szaic.gov.cn/sms/" + key.trim() + "/" + type.trim()
						+ "/" + phone.trim() + "/" + mess.trim();
				strJson = MyX509TrustManager.getClient(url);
			} catch (Exception e) {
				e.printStackTrace();
			}
			JSONObject json = JSONObject.parseObject(strJson);
			if (json==null){
				isSend=false;
			} else if("0".equals(json.get("code"))) {
				isSend=true;
			}else{
				isSend=false;
			}
			if (code != 0) {
				isInsert = true;
			} else {
				isInsert = smsService.savePhoneSMS(phoneNum, a);
			}
			
			if (isSend && isInsert) {
				returnResult ="验证码已经发送";
			} else {
				if (!isSend) {
					returnResult = "短信未发出，发送失败";
				} else if (!isInsert) {
					returnResult = "验证码未保存，发送失败";
				} else {
					returnResult = "服务端异常,验证码未发送，请稍后尝试";
				}
			}
		}else{
			returnResult = "请不要频繁发送短信!";
		}
		
		return returnResult;
	}

	@RequestMapping("/validateCode")
	public void validateCode(OptimusRequest req, OptimusResponse res)
			throws Exception {
		HttpServletRequest request = req.getHttpRequest();
		String code = req.getParameter("code");
		String phone = req.getParameter("phone");
		if (code==null||code.trim().length()==0) {
			res.addAttr("msg", "验证码不能为空");
		}else{
			res.addAttr("msg",smsService.validateCode(phone.trim(), code.trim())?"验证码正确":"验证码错误");
		}
	}


}
