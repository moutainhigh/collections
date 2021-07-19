package com.gwssi.rodimus.validate.msg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.gwssi.rodimus.util.StringUtil;

/**
 * 验证错误信息。
 * 
 * @author liuhailong
 */
public class ValidateMsg implements Serializable{
	
	private static final long serialVersionUID = 7641536340189767539L;
	
	private List<ValidateMsgEntity> msgList = new ArrayList<ValidateMsgEntity>();
	
	public void clear(){
		if(msgList==null){
			msgList = new ArrayList<ValidateMsgEntity>();
		}
		msgList.clear();
	}
	
	public void addRuleResult(String ruleCode,String type,String name, String msg,String errCode , String ruleId,String ruleStepId) {
		if(msgList==null){
			msgList = new ArrayList<ValidateMsgEntity>();
		}
		
		String vMsgString = msg;//String.format("[%s]%s", ruleCode,msg);
		ValidateMsgEntity vMsg = new ValidateMsgEntity();
		vMsg.setMsg(vMsgString);
		vMsg.setName(name);
		vMsg.setType(type);
		vMsg.setRuleId(ruleId);
		vMsg.setRuleStepId(ruleStepId);
		vMsg.setErrCode(errCode);
		msgList.add(vMsg);
	}
	
	/**
	 * @param ruleCode 规则编码
	 * @param type 1-提示，0-锁定
	 * @param name 规则名称
	 * @param msg 错误消息
	 */
	public void addRuleResult(String ruleCode,String type,String name, String msg) {
		if(msgList==null){
			msgList = new ArrayList<ValidateMsgEntity>();
		}
		
		String vMsgString = msg;//String.format("[%s]%s", ruleCode,msg);
		ValidateMsgEntity vMsg = new ValidateMsgEntity();
		vMsg.setMsg(vMsgString);
		vMsg.setName(name);
		vMsg.setType(type);
		msgList.add(vMsg);
	}
	/**
	 * 
	 * 添加错误信息
	 * 
	 * @param msgList 错误信息List
	 * @param value 字段值
	 * @param validateMsg 错误信息提示（可用格式化字符）
	 * @param params 格式化字符对应的值（可选）
	 */
	public void add(String label, String value,String validateMsg, Object... params) {
		if(msgList==null){
			msgList = new ArrayList<ValidateMsgEntity>();
		}
		
		label = (label==null)?"":label;
		label = label.trim();
		String ret = label ;
		
		String errMsg = "";
		if(!StringUtil.isBlank(validateMsg)){
			errMsg = String.format(validateMsg, params);
		}
		if(!StringUtil.isBlank(errMsg)){
			ret = String.format("%s：%s", ret,errMsg);
		}
		
		ValidateMsgEntity vMsg = new ValidateMsgEntity();
		vMsg.setMsg(ret);
		msgList.add(vMsg);
	}
	/**
	 * 获得所有错误信息。
	 * 
	 * @return
	 */
	public List<ValidateMsgEntity> getAllMsg(){
		return this.msgList;
	}
	
	public String getAllMsgString(){
		StringBuffer s = new StringBuffer("");
		if(msgList.size()>0){
			for (ValidateMsgEntity vmsg:msgList) {
				//如果规则错误信息不为空，说明规则不通过
				if (StringUtils.isNotBlank(vmsg.getMsg())) {
					s.append(vmsg.getMsg()).append(";");
					
				}
			}
			if(StringUtils.isNotBlank(s.toString())){
				return s.substring(0, s.length()-1)+"。";
			}
		}
		return "";				
		
	}

	public String toString(){
		String ret = JSON.toJSONString(this,true);
		return ret;
	}

	public String getLockedMsg(){
		StringBuffer sb = new StringBuffer();
		if(msgList!=null && msgList.size()>0){
			boolean isFirst = true ; 
			for(ValidateMsgEntity entry : msgList){
				if("1".equals(entry.getType())){
					if(!isFirst){
						sb.append("；");
					}else{
						isFirst = false;
					}
					sb.append(entry.getMsg());
				}
			}
			if(sb.length()>0){
				sb.append("。");
			}
		}
		return sb.toString();
	}

	public boolean isEmpty() {
		if(msgList==null){
			return true;
		}
		return msgList.isEmpty();
	}
}
