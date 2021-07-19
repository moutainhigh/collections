<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.components.wcm.content.domain.intelligence.IIntellMgr" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@include file="/include/public_server.jsp"%>

<%
	String sCurrentSiteKind = currRequestHelper.getString("SiteKind");
	IIntellMgr intellMgr = (IIntellMgr) DreamFactory.createObjectById("IIntellMgr");
	String[] pKinds = intellMgr.getSiteKinds();
	if(pKinds == null || pKinds.length==0){
		throw new WCMException(LocaleServer.getString("website_create.jsp.label.sys_no_prew_website", "系统没有任何预设站点！"));
	}
	if(sCurrentSiteKind == null || sCurrentSiteKind.length() == 0){
		sCurrentSiteKind = pKinds[0];
	}		
	String[] pStyles = intellMgr.getSiteStyles(sCurrentSiteKind);
	if(pStyles == null || pStyles.length ==0){
		throw new WCMException(CMyString.format(LocaleServer.getString("website_create.jsp.obj_notfound", "站点[{0}]下没有任何预设样式！!"), new String[]{sCurrentSiteKind}));
	}
	out.clear();
%>
<html>
<head>
<title WCMAnt:param="website_create.jsp.title">智能建站</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<script src="../../app/js/source/wcmlib/components/ProcessBar.js"></script>
<link rel="stylesheet" type="text/css" href="../../app/js/source/wcmlib/components/processbar.css">
<script src="../js/data/locale/website.js"></script>
<!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!--wcm-dialog end-->
<!--validator-->
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<link href="../../app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />

<link href="../css/website_quicknew.css" type="text/css" rel="stylesheet">
<style type="text/css">
	input{
		height:20px;
	}
	.text_txt {
		font-size: 12px;
		color: #000000;
	}
</style>
<script language="javascript">
window.m_fpCfg = {
	m_arrCommands : [{
		cmd : 'quicknewWebSite',
		name : wcm.LANG.WEBSITE_TRUE||'确定'
	}],
	size : [730, 420]
};
</script>
<script language="javascript">
<!--
	//WebFXTabPane.getCookie = function(){
     //   return 0;
    //}
	var oWebSiteHelper = BasicDataHelper;
	Event.observe(window, 'load', function(){
		ValidationHelper.addInvalidListener(function(){
			FloatPanel.disableCommand('quicknewWebSite', true);
		}, "frmAction");
		ValidationHelper.addValidListener(function(){
			FloatPanel.disableCommand('quicknewWebSite', false);
		}, "frmAction");
		ValidationHelper.initValidation();
	}, false);
//-->
</script>

<SCRIPT LANGUAGE="JavaScript">
<!--
	function setSiteKind(_sKindName){
		new Ajax.Request("site_create.jsp", {method:'get', parameters:"SiteKind=" + encodeURI(_sKindName), onComplete:function(response){
			$('divId').innerHTML = response.responseText;
			var tempStyle = document.getElementsByName("SiteStyle");
			if(tempStyle[0])tempStyle[0].checked=true;
		}});
	}
	
	function quicknewWebSite(){
		ProcessBar.start(wcm.LANG.WEBSITE_10||'执行智能建站');
		oWebSiteHelper.call("wcm6_website", "autoCreate", "frmAction", true, function(_transport, _json){
			ProcessBar.close();
			try{
				notifyFPCallback($v(_json, "result"));
				FloatPanel.close();
			}catch(error){
				//TODO logger
				//alert(error.message);
			}
		});
		return false;
	}
    //判断站点名称时候已经存在
    function checkSiteName(){
		oWebSiteHelper.call('wcm6_website','existsSimilarName', {siteName:$F('SiteName')}, true, function(transport, json){
			if($v(json, "result") == 'false'){
				ValidationHelper.successRPCCallBack();
			}else{
				ValidationHelper.failureRPCCallBack(wcm.LANG.WEBSITE_9||"站点名称已经存在");
			}
		});
    }
//-->
</SCRIPT>
</head>

<body>
<form name="frmAction" id="frmAction" >

	<div  class="tab-body" id="tab-body-2" style="padding-top:20px;">
		<table border="0" cellspacing="1" cellpadding="0" width="95%" style="height:60px; overflow-y:hidden;">
			<tr>
				<td align="right" class="text_txt" height="18px;" width="15%" WCMAnt:param="website_create.jsp.sitename">站点名称：</td>
				<td>
				<input type="text" name="SiteName" id="SiteName" size="50" validation="type:'string',required:'',max_len:'50',no_desc:'',rpc:'checkSiteName'">
				</td>
			</tr>
			<tr>
				<td align="right" class="text_txt" height="18px;" WCMAnt:param="website_create.jsp.sitedesc">站点显示名称：</td>
				<td>
				<input type="text" name="SiteDesc" id="SiteDesc" size="50" validation="type:'string',required:'',max_len:'200',no_desc:''">
				</td>
			</tr>
		</table>
	</div>
	<div  class="tab-body" id="tab-body-1">
		<table border="0" cellspacing="1" cellpadding="0" width="95%" style="margin-top:0px;">
			<tr><td valign="top">
				<table border="0" cellspacing="0" cellpadding="0" align="center" width="100%">
					<tr><td valign="top">					
						<table border="0" cellspacing="0" cellpadding="0" width="100%" style="margin-bottom:5px;">
								<tr><td><span class="text_txt" style="margin-left:35px"><span WCMAnt:param="website_create.jsp.sitetype">站点类型：</span>
									<select id="SiteKind" name="SiteKind" onchange="setSiteKind(this.value);" class="text_txt">
										<%
										for (int i = 0; i < pKinds.length; i++) {
											if(pKinds[i] == null)continue;
											out.println("<option value=\""+pKinds[i]+"\">"+pKinds[i]+"</option>");            
										}
										%>
									</select>
							</span></td></tr>
						</table>
						<script>
							document.getElementById("SiteKind").value="<%=CMyString.filterForJs(sCurrentSiteKind)%>"
						</script>
						<div id="divId">
						<span id="spanId" style="overflow-y:auto;height:250px;" style="BORDER-top: #cccccc 1px solid;BORDER-bottom: #cccccc 1px solid;">
						<table align="center" cellpadding="5" cellspacing="5" width="100%">
							<%
								String sStyleName, sImgSrc, sIntroduceContent;        
								for (int i = 0; i < pStyles.length; ) {  
								out.println("<tr>");
								int j = 0;
								for(j=0; j<4 && i < pStyles.length; j++){
								sStyleName = pStyles[j];

								out.println(makeStyleDisplayHTML(sCurrentSiteKind, sStyleName));

								i++;
								}
								for(;j<4;j++){
								out.println("<td width=25% align=\"center\" style=\"border:1px solid #cccccc \">&nbsp;</td>");
								}            
								out.println("</tr>");
								}
							%>	
						</table>
						<script>
							var tempStyle = document.getElementsByName("SiteStyle");if(tempStyle[0])tempStyle[0].checked=true;
						</script>
						</span>
						<table border="0" cellspacing="0" cellpadding="0" width="100%">
							<tr>
							<td class="text_txt" align="right"> <span WCMAnt:param="website_create.jsp.num">总共个数为：</span> <span><%=pStyles.length%></span></td>
							</tr>
						</table>
						</div>
					</td></tr>
				</table>
			</td></tr>
		</table>
	</div>
</form>
</body>
</html>
<%!
	private String makeImageHTML(String _sImageLink){
		if(_sImageLink == null || _sImageLink.length() == 0){
			return LocaleServer.getString("site_create.label.noimg", "没有附图");
		}
		return "<img src=\"/wcm/file/read_image.jsp?FileName="
                + CMyString.URLEncode(_sImageLink)
                + "\" alt=\""+LocaleServer.getString("site_create.label.seltemp", "点击图片选定模版")
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