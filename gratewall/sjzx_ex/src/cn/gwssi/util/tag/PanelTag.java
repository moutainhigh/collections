package cn.gwssi.util.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;

public class PanelTag extends BodyTagSupport
{
	private static final long	serialVersionUID	= 1L;

	/**
	 * 组成部分，多个的话以逗号隔开
	 */
	public String				parts;

	public String				styleClass;

	public String				action;

	public String				target;

	public String getParts()
	{
		return parts;
	}

	public void setParts(String parts)
	{
		this.parts = parts;
	}

	public String getStyleClass()
	{
		return styleClass;
	}

	public void setStyleClass(String styleClass)
	{
		this.styleClass = styleClass;
	}

	public String getAction()
	{
		return action;
	}

	public void setAction(String action)
	{
		this.action = action;
	}

	public String getTarget()
	{
		return target;
	}

	public void setTarget(String target)
	{
		this.target = target;
	}

	public int doStartTag()
	{
		pageContext.getOut();// 获取JSP页面的输出流 out
		JspWriter out = pageContext.getOut();// 用pageContext获取out，他还能获取session等，基本上jsp的内置对象都能获取
		try {
			StringBuffer sb_form=new StringBuffer();
			sb_form.append("<form method='post' id='form_choose' action='/").append(action).append(".do'");
			if (StringUtils.isNotBlank(target)) {
				sb_form.append(" target='").append(target).append("' ");
			}
			sb_form.append("style='margin-top:0px;'>");
			out.println(sb_form.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;// 标签执行完后，继续执行后面的
	}

	public int doEndTag()
	{
		pageContext.getOut();// 获取JSP页面的输出流 out

		JspWriter out = pageContext.getOut();// 用pageContext获取out，他还能获取session等，基本上jsp的内置对象都能获取
		try {
			if (StringUtils.isBlank(styleClass)) {
				styleClass = "wrapper";
			}
			
			out.println("<table style='border-collapse: collapse;' cellpadding=0 cellspacing=0 width='100%'>");
			out.println("<tr><td align='center' style='padding: 5px;'><div style='width:95%;'><div class='"
					+ styleClass + "'>");
			if (StringUtils.isNotBlank(parts)) {
				String[] ps = parts.split(",");
				for (int i = 0; i < ps.length; i++) {
					out.println("<div id='" + ps[i] + "'></div>");
				}
				out.println("</div></div></td></tr><table></form>");
			}
			out.println("<script type=\"text/javascript\">");
			out.println("function toChangeSelect(tid){");
			out.println("$('#'+tid).queryform('getSelectedNode');");
			out.println("}");
			out.println("function changeDate(){");
			out.println("var val = arguments[0];");
			out.println("if(val && val!='点击选择日期' && val != 'date_all'){");
			out.println("	document.getElementById('form_choose').submit();");
			out.println("}else{if(val && val=='date_all'){");
			out.println("document.getElementById('form_choose').submit();}}}");
			out.println("</script>");

		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;// 标签执行完后，不继续执行后面的
	}
}
