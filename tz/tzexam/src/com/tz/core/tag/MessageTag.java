/**
 * Project Name:tzexam
 * File Name:MessageTag.java
 * Package Name:com.tz.core.tag
 * Date:2015年10月20日下午10:00:20
 * Copyright (c) 2015, chenzhou1025@126.com All Rights Reserved.
 *
 */

package com.tz.core.tag;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.context.MessageSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tz.util.TzStringUtils;

/**
 * ClassName:MessageTag <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年10月20日 下午10:00:20 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.6
 * @see
 */

public class MessageTag extends BodyTagSupport{

	private String key;
	private String locale;
	private String params;
	
	
	@Override
	public int doStartTag() throws JspException {
		//怎么让自定义标签和Spring进行管理
		ServletContext context = this.pageContext.getRequest().getServletContext();
		WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
		//拿到国际化的对象
		MessageSource messageSource = (MessageSource)applicationContext.getBean("messageSource");
		//转化数据
		String str = messageSource.getMessage(key, params!=null?params.split(","):null, getLocale(locale));
		try {
			//输出去
			this.pageContext.getOut().print(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	
	
	/*你传递语言获取Locale对象*/
	private Locale getLocale(String local){
		if(TzStringUtils.isNotEmpty(local)){
			if(local.equalsIgnoreCase("zh")){
				return Locale.CHINA;
			}else if(local.equalsIgnoreCase("en")){
				return Locale.US;
			}else{
				return Locale.getDefault();
			}
		}else{
			return Locale.getDefault();
		}
	}
	

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
}
