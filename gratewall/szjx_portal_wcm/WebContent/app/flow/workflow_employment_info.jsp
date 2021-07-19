<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.cms.process.definition.Flow" %>
<%@ page import="com.trs.cms.process.definition.Flows" %>
<%@ page import="com.trs.ajaxservice.WebSiteServiceProvider" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.cms.process.IFlowServer" %>
<%@ page import="com.trs.cms.process.definition.FlowEmployMgr" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@include file="../../include/public_server.jsp"%>
<%
	String sObjectIds = currRequestHelper.getString("objectids");
	Flows flows = Flows.findByIds(loginUser, sObjectIds);
	IFlowServer m_oFlowServer = (IFlowServer) DreamFactory.createObjectById("IFlowServer");
	FlowEmployMgr m_oFlowEmployMgr = (FlowEmployMgr) DreamFactory.createObjectById("FlowEmployMgr");	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!--my import-->
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/TempEvaler.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../../app/js/data/locale/flow.js"></script>
<!-- CarshBoard Inner Start -->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!-- CarshBoard Inner End -->
<!-- dialog  Start -->
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT> 
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<!-- dialog  End -->
<!--page scrop-->
<script language="javascript">
<!--
    window.m_cbCfg = {
        btns : [
            {
                text :   wcm.LANG['FLOW_CONFIRM'] || '确定',
                cmd :	closeframe.bind(this, true)
 
            },
            {
                text : wcm.LANG['FLOW_CANCEL'] || '取消'
            }
        ]
    };
    
//-->
function closeframe(_bResume){
	var cbr = wcm.CrashBoarder.get("workflow_info_dialog");
	cbr.notify();
	cbr.hide();
}
</script>
<style type="text/css">
html,body,iframe,div{scrollbar-face-color:#e6e6e6;scrollbar-highlight-color:#fff;scrollbar-shadow-color:#eeeeee;scrollbar-3dlight-color:#eeeeee;scrollbar-arrow-color:#000;scrollbar-track-color:#fff;scrollbar-darkshadow-color:#fff;}
	.alertion_top_title{
		color: red;
		font-weight: bold;
	}
	.alertion_title{
		color: navy;
	}
	body{
		padding:0px;
		margin:0px;
		font-family: Georgia;
	}
	
	.dataItem{
		line-height: 18px;
		list-style-type: decimal;
		margin-top: 5px;
		border-bottom: solid 1px #efefef;
	}
</style>
</HEAD>

<BODY style="overflow:hidden;">
<div style="padding-top:5px;">
	<table border=0 align="center" cellspacing=1 cellpadding=3 style="font-size:12px; width: 100%; background: silver;">
	<tbody>
		<tr>
			<td style="width: 100px; height: 200px; background: #ffffff" align="center">
				<img id="imClue" src="../images/workflow/delete.gif">
			</td>
			<td style="background:#ffffff;height:200px;" align="left" valign="top" >
				<div style="width: 300px;height:100%;overflow:auto;" id="divIfoContainer">
					<div style="width: 100%; height: 30px; line-height: 30px; overflow: visible; border-bottom: 1px solid aliceblue; font-size: 14px; padding-left: 10px; font-weight: normal;">
						<div id="divOptionsDesc">
						<span class="alertion_title" style="color: red" id="spOperation" WCMAnt:param="workflow_employment_info.jsp.readyDeleteFlow">您正准备删除如下工作流：</span>
					</div>
					</div>
					<div style="height:160px;overflow-x:hidden">
						<div style="width: 100%; padding: 3px;" id="divDocInfo">
							<%
							    String flowName = "";
							    String owner = "";
							    for (int i = 0; i < flows.size(); i++) {
							        boolean bEmployed = false;
							        Flow flow = (Flow) flows.getAt(i);
							        if (flow == null)
							            continue;
							        flowName = CMyString.showNull(flow.getName());
							        owner = getFlowOwnerInfo(flow).toString();
							        Channels channels = (Channels) m_oFlowEmployMgr
							                .getChannelsUseingFlow(flow, new Channels(loginUser),
							                        Channel.OBJ_TYPE, Channel.DB_TABLE_NAME, null);

							        if (!(channels == null || channels.isEmpty())) {
							            bEmployed = true;
							        }
							%>
									<div class="dataItem" >
										<span style="padding:2px;"><%=(i + 1)%>.</span>
										<span WCMAnt:paramattr="title:workflow_employment_info.jsp.flowTitle" title="Workflow">
											 <%=flowName%>, 
										</span>
										<span WCMAnt:param="workflow_employment_info.jsp.owner">隶属于</span>
										<span>
											<span class="alertion_title"><%=owner%><%=bEmployed ? "," : "."%></span>
										</span>
										<span style="display: <%=bEmployed ? "" : "none"%>">
											<span WCMAnt:param="workflow_employment_info.jsp.employChannel">被分配到</span>
											<%
											    for (int k = 0; k < channels.size(); k++) {
											            Channel channel = (Channel) channels.getAt(k);
											            if (channel == null)
											                continue;
														if ((k == channels.size() - 1) && channels.size()==1) {
											%>
														<span WCMAnt:param="workflow_employment_info.jsp.channel">栏目</span><span class="alertion_title"> [<%=channel.getDesc()%></span>].
											<%
															continue;
														}else if(k == channels.size() - 1){
											%>
															<span WCMAnt:param="workflow_employment_info.jsp.and">和</span><span class="alertion_title"> [<%=channel.getDesc()%></span>].
											<%			
														continue;
														}
														if(k == 0){
														 
													
											%>
												<span WCMAnt:param="workflow_employment_info.jsp.channel">栏目</span><span class="alertion_title"> [<%=channel.getDesc()%></span>],&nbsp;

											<%			}
														else if(k > 0 && k < channels.size()-1){
											%>
														<span class="alertion_title"> [<%=channel.getDesc()%></span>],&nbsp;
											<%
														}
											    }
											%>
										</span>
									</div>
							<%
							    }
							%>
						</div>
					</div>
				</div>
			</td>
		</tr>
	</tbody>
	</table>
</div>
</BODY>
</HTML>
<%!
	private Object getFlowOwnerInfo(Flow _flow) throws Throwable {
        if (_flow.getOwnerType() != 103) {
            return WebSiteServiceProvider.findSiteTypeDesc(_flow.getOwnerId());

        }
        // else 站点
        BaseObj owner = _flow.getOwner();
        if (owner == null) {
            return "";
        }
        WebSite site = (WebSite) owner;
        return LocaleServer.getString("workflow.employment.info.site", "站点 [")
        + CMyString.filterForHTMLValue(site.getDesc()) + "]";
    }

%>