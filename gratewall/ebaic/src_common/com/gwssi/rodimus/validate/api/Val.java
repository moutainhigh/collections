package com.gwssi.rodimus.validate.api;

import com.gwssi.optimus.core.common.ThreadLocalManager;
import com.gwssi.rodimus.validate.msg.ValidateMsg;

/**
 * <h2>校验框架</h2>
 * <p>
 * 		<h3>支持以下校验：</h3>
 * 		<ol>
 * 			<li>单个字段；</li>
 * 			<li>表达式；</li>
 * 			<li>基于数据查询。</li>
 * 		</ol>
 * </p> 
 * <p>
 * 		<h3>校验配置信息保存方法：</h3>
 * 		<ol>
 * 			<li>数据库；</li>
 * 			<li>JSON；</li>
 * 			<li>注解。</li>
 * 		</ol>
 * </p>
 * <pre>
 * 与TORCH集成：
select f.function_id,t.id,t.table_name,t.name from sys_edit_config t 
left join sys_function_config f on t.function_id=f.function_id 
where f.function_id = '2121212';
 * </pre>
 * @author liuhailong
 */
public class Val {
	/**
	 * 初始化校验配置。
	 */
	public void init(){
		
	}
	/**
	 * 单个字段校验。
	 */
	public static FieldValidate field = new FieldValidate();
	
	/**
	 * Assert 。
	 * 
	 */
	public static Assert Assert = new Assert();
	
	
	private final static String VALIDATE_MSG_KEY = "VALIDATE_MSG_KEY";
	/**
	 * 获取校验消息。
	 * 
	 * @return
	 */
	public static ValidateMsg getMsg(){
		
		ValidateMsg msg = (ValidateMsg)ThreadLocalManager.get(VALIDATE_MSG_KEY);
		if(msg==null){
			msg = new ValidateMsg();
			ThreadLocalManager.add(VALIDATE_MSG_KEY, msg);
		}
		return msg;
	}
	
}
