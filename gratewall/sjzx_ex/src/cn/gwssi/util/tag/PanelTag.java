package cn.gwssi.util.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;

public class PanelTag extends BodyTagSupport
{
	private static final long	serialVersionUID	= 1L;

	/**
	 * ��ɲ��֣�����Ļ��Զ��Ÿ���
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
		pageContext.getOut();// ��ȡJSPҳ�������� out
		JspWriter out = pageContext.getOut();// ��pageContext��ȡout�������ܻ�ȡsession�ȣ�������jsp�����ö����ܻ�ȡ
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
		return EVAL_PAGE;// ��ǩִ����󣬼���ִ�к����
	}

	public int doEndTag()
	{
		pageContext.getOut();// ��ȡJSPҳ�������� out

		JspWriter out = pageContext.getOut();// ��pageContext��ȡout�������ܻ�ȡsession�ȣ�������jsp�����ö����ܻ�ȡ
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
			out.println("if(val && val!='���ѡ������' && val != 'date_all'){");
			out.println("	document.getElementById('form_choose').submit();");
			out.println("}else{if(val && val=='date_all'){");
			out.println("document.getElementById('form_choose').submit();}}}");
			out.println("</script>");

		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;// ��ǩִ����󣬲�����ִ�к����
	}
}
