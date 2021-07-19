package cn.gwssi.util.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.component.logger.TxnLogger;
import cn.gwssi.common.context.DataBus;

import com.gwssi.webservice.server.GSGeneralWebService;

public class CellTag extends BodyTagSupport
{
	private static final long	serialVersionUID	= 1L;

	protected static Logger		logger				= TxnLogger
															.getLogger(GSGeneralWebService.class
																	.getName());

	public String				id;

	public String				name;

	public String				key;

	public boolean				pop					= false;

	public boolean				move2top			= false;

	public String				onClick				= "";

	public String				maxsize;

	public String				data;

	public String				valueSet;

	public boolean				date				= false;
	public boolean				dateTime			= false;

	public String				isGroup				= "false";
	
	public String				isPinYinOrder		= "false";

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public boolean isPop()
	{
		return pop;
	}

	public void setPop(boolean pop)
	{
		this.pop = pop;
	}

	public boolean isMove2top()
	{
		return move2top;
	}

	public void setMove2top(boolean move2top)
	{
		this.move2top = move2top;
	}

	public String getOnClick()
	{
		return onClick;
	}

	public void setOnClick(String onClick)
	{
		this.onClick = onClick;
	}

	public String getMaxsize()
	{
		return maxsize;
	}

	public void setMaxsize(String maxsize)
	{
		this.maxsize = maxsize;
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public String getValueSet()
	{
		return valueSet;
	}

	public void setValueSet(String valueSet)
	{
		this.valueSet = valueSet;
	}

	public boolean isDate()
	{
		return date;
	}

	public void setDate(boolean date)
	{
		this.date = date;
	}
	
	public boolean isDateTime()
	{
		return dateTime;
	}

	public void setDateTime(boolean dateTime)
	{
		this.dateTime = dateTime;
	}

	public String getIsGroup()
	{
		return isGroup;
	}

	public void setIsGroup(String isGroup)
	{
		this.isGroup = isGroup;
	}

	public String getIsPinYinOrder()
	{
		return isPinYinOrder;
	}

	public void setIsPinYinOrder(String isPinYinOrder)
	{
		this.isPinYinOrder = isPinYinOrder;
	}

	public int doStartTag()
	{
		
		pageContext.getOut();
		JspWriter out = pageContext.getOut();
		DataBus context = (DataBus) pageContext.getRequest().getAttribute(
				"freeze-databus");
		// 拼接条件数组
		StringBuffer sb = new StringBuffer("var options = { ");
		if (StringUtils.isNotBlank(data)) {
			sb.append("data:").append(data).append(",");
		}
		if (date) {
			sb.append("date:true,");
		}else if(dateTime){
			sb.append("dateTime:true,");
		}
		sb.append("title:{").append("\"name\":").append("\"").append(name)
				.append("\",\"key\":\"").append(key).append("\"},");
		sb.append("pop:").append(pop).append(",");
		if (StringUtils.isBlank(onClick)) {
			sb.append("onClick:")
					.append("function(){toChangeSelect('").append(id).append("');}")
					.append(",");
		} else {
			sb.append("onClick:").append(onClick).append(",");
		}
		sb.append("isGroup:").append(isGroup).append(",");
		sb.append("isPinYinOrder:").append(isPinYinOrder).append(",");
		sb.append("move2top:").append(move2top);
		String checkValue = "";
		Map valueMap = new HashMap();
		String[] keys = key.split(",");
		// 设置选中值
		try {

			DataBus db_selectkey = context.getRecord("select-key");
			if (!db_selectkey.isEmpty()) {
				sb.append(",checkValue: \"");
				int temp = 0;
				for (int i = 0; i < keys.length; i++) {
					//System.out.println(keys[i]+"----"+db_selectkey.getValue(keys[i]));
					if (StringUtils.isNotBlank(db_selectkey.getValue(keys[i]))) {
						valueMap.put(keys[i], db_selectkey.getValue(keys[i]));
						if (temp > 0) {
							sb.append(",");
						}
						sb.append(db_selectkey.getValue(keys[i]));
						temp++;
					}
				}
				sb.append("\"");
				// if (v) {
				// sb.append(",checkValue: \"")
				// .append(db_selectkey.getValue(key)).append("\" ");
				// checkValue = db_selectkey.getValue(key);
				// }
			}
		} catch (TxnException e1) {
			e1.printStackTrace();
		}

		sb.append("};");

		String dataStr = "";
		if (StringUtils.isNotBlank(data)) {
			if (data.indexOf(":") == -1) {
				dataStr = context.getValue(data);
			} else {
				try {
					dataStr = context.getRecord(data.split(":")[0].trim())
							.getValue(data.split(":")[1].trim());
				} catch (TxnException e) {
					logger.error("条件选择控件获取绑定数据错误");
					e.printStackTrace();
				}
			}
		}
		// 输出页面的相关js
		try {
			if(date && dateTime){			
				logger.error("不能同时设置date和dateTime属性！");
				out.println("<div>不能同时设置date和dateTime属性！</div>");
				return EVAL_PAGE;// 标签执行完后，继续执行后面的
			}
			if("true".equals(isPinYinOrder)){
				out.println("<script type=\"text/javascript\" src=\"/script/lib/pinyin.js\" ></script>");
			}
			out.println("<script type=\"text/javascript\">");
			out.println("$(document).ready(function(){ ");
			//System.out.println("w  "+dataStr);
			out.println("var " + data + " = " + dataStr + ";");
			out.println(sb.toString());
			if (StringUtils.isNotBlank(maxsize)) {
				out.println(" options.maxsize=" + maxsize + ";");
			}
			out.println("$('#" + id + "').queryform(options);");
			out.println("});");
			// out.println("function(){");
			// out.println(" toChangeSelect('"+id+"');");
			// out.println("};");
			out.println("</script>");
			if (!date) {
				// 如果不为分组
				if (isGroup.equals("false")) {
					checkValue = (null == valueMap.get(key)) ? "" : valueMap
							.get(key).toString();
					out.print("<input type='hidden' name='select-key:" + key
							+ "' id='" + id + "_choose' value='" + checkValue
							+ "'/>");
				} else {
					if (StringUtils.isNotBlank(key)) {
						for (int i = 0; i < keys.length; i++) {
							checkValue = (null == valueMap.get(keys[i])) ? ""
									: valueMap.get(keys[i]).toString();
							out.println("<input type='hidden' name='select-key:"
									+ keys[i]
									+ "' id='"
									+ id
									+ "_"
									+ keys[i]
									+ "' "
									+ ("value='" + checkValue + "'")
									+ "/>");
						}

					}

				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;// 标签执行完后，继续执行后面的
	}

	public int doEndTag()
	{
		// pageContext.getOut();// 获取JSP页面的输出流 out
		// pageContext.getRequest();// 获取JSP页面的请求对象 request
		// pageContext.getSession();// 获取JSP页面的会话对象 session
		// pageContext.getServletContext();// 获取JSP页面的应用对象 application [Page]
		//
		// JspWriter out = pageContext.getOut();//
		// 用pageContext获取out，他还能获取session等，基本上jsp的内置对象都能获取

		return SKIP_BODY;// 标签执行完后，不继续执行后面的
	}
}
