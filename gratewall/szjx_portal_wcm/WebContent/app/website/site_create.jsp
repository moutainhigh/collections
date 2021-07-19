<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="/include/error.jsp"%>
<%@ page import="com.trs.components.wcm.content.domain.intelligence.IIntellMgr" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@include file="/include/public_server.jsp"%>

<%
	String sCurrentSiteKind = currRequestHelper.getString("SiteKind");
	IIntellMgr intellMgr = (IIntellMgr) DreamFactory.createObjectById("IIntellMgr");
	String[] pKinds = intellMgr.getSiteKinds();
	if(pKinds == null || pKinds.length==0){
		throw new WCMException(LocaleServer.getString("site_create.jsp.label.sys_have_no_prew_set_site", "系统没有任何预设站点！"));
	}
	if(sCurrentSiteKind == null || sCurrentSiteKind.length() == 0){
		sCurrentSiteKind = pKinds[0];
	}		
	String[] pStyles = intellMgr.getSiteStyles(sCurrentSiteKind);
	if(pStyles == null || pStyles.length ==0){
		throw new WCMException(CMyString.format(LocaleServer.getString("site_create.jsp.website_have_no_prew_set_site", "站点[[{0}]]下没有任何预设样式!"), new String[]{sCurrentSiteKind}));
	}
	out.clear();
%>

<span id="spanId" style="overflow-y:auto;height:300;" style="BORDER-top: #cccccc 1px solid;BORDER-bottom: #cccccc 1px solid;">
<table align="center" cellpadding="5" cellspacing="5" width="100%">
	<%
		String sStyleName, sImgSrc, sIntroduceContent;        
		for (int i = 0; i < pStyles.length; ) {  
		out.println("<TR>");
		int j = 0;
		for(j=0; j<4 && i < pStyles.length; j++){
		sStyleName = pStyles[j];

		out.println(makeStyleDisplayHTML(sCurrentSiteKind, sStyleName));

		i++;
		}
		for(;j<4;j++){
		out.println("<td width=25% align=\"center\" style=\"border:1px solid #cccccc \">&nbsp;</td>");
		}            
		out.println("</TR>");
		}
	%>	
</table>
</span>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
	<tr>
	<td class="text_txt" align="right"> <span WCMAnt:param="site_create.jsp.num">总共个数为：</span> <span><%=pStyles.length%></span></td>
	</tr>
</table>

<%!
	private String makeImageHTML(String _sImageLink){
		if(_sImageLink == null || _sImageLink.length() == 0){
			return LocaleServer.getString("site_create.label.noimg", "没有附图");
		}
		return "<img src=\"/wcm/file/read_image.jsp?FileName="
                + CMyString.URLEncode(_sImageLink)  + "\" alt=\""+
					LocaleServer.getString("site_create.label.seltemp", "点击图片选定模版")
					+"\" width=\"140\" height=\"108\" border=\"0\" style=\"border:1px solid #cccccc; \">";
	}
	private String makeDemoURL(String _sDemoLink){
		if(_sDemoLink == null || _sDemoLink.length() == 0){
			return " href=\"###\" onclick=\"alert('"+LocaleServer.getString("site_create.label.noaddr", "当前站点没有演示地址！")
				+"');return false;\" ";
		}		
		return "href=\""+_sDemoLink+"\" target=_blank";
	}
	private String makeStyleDisplayHTML(String _sKindName, String _sStyleName)
            throws WCMException {
        IIntellMgr intellMgr = (IIntellMgr) DreamFactory.createObjectById("IIntellMgr");
        String sDemoLink = makeDemoURL(intellMgr.getSiteDemoLink(_sKindName, _sStyleName));
        String sImgFile = intellMgr.getSiteIntroduceImgFilePath(_sKindName, _sStyleName);
        String sIntroduce = intellMgr.getSiteIntroduceContent(_sKindName, _sStyleName);

        String sHTML = ""
                + "<td  width=25%  align=\"center\" style=\"border:1px solid #cccccc \">"
                + "	<table  border=\"0\" cellspacing=\"2\" cellpadding=\"0\" class='text_txt'>"
                + "		  <tr>"
                + "			 <td align=\"center\"  height=108 valign=middle><a "
                + sDemoLink
                + "				title=\""+LocaleServer.getString("site_create.label.prestyle", "预览当前风格")
					+"\">"+makeImageHTML(sImgFile)+"</a></td>"
                + "		  </tr>"
                + "		  <tr>"
				+ "			 <td align='center'><input type='radio' name='SiteStyle' value='"
                + _sStyleName
                + "'			title=\""+LocaleServer.getString("site_create.label.selstyle", "选中当前风格")
					+"\" >&nbsp;<a "
                + sDemoLink
                + "				title=\""+LocaleServer.getString("site_create.label.prestyle", "预览当前风格")
					+"\" class=\"pn\">"
                + _sStyleName + "</a></td>" + "		  </tr></table>" + "</td>";
        return sHTML;
    }
%>