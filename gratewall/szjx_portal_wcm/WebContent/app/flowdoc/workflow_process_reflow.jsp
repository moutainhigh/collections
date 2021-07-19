<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.cms.process.engine.FlowDoc" %>
<%@ page import="com.trs.cms.process.IFlowContent" %>
<%@ page import="com.trs.cms.process.ProcessConstants" %>
<%@ page import="com.trs.ajaxservice.ProcessService" %>
<%@ page import="com.trs.cms.process.FlowContentFactory" %>
<%@ page import="com.trs.cms.process.IFlowServer" %>
<%@ page import="com.trs.cms.process.engine.FlowDocs" %>
<%@ page import="com.trs.cms.process.engine.FlowDoc" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.cms.process.definition.FlowNode" %>
<%@ page import="com.trs.cms.process.definition.Flow" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.cms.process.definition.FlowEmployMgr" %>
<%@ page import="com.trs.cms.auth.persistent.Users" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.process.definition.FlowNodes" %>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
	//1.初始化参数
	int nContentId = currRequestHelper.getInt("ContentId", 0);
	int nContentType = currRequestHelper.getInt("ContentType", 0);
	
	//2.获取当前文档的流转信息
	IFlowContent content = FlowContentFactory.makeFlowContent(nContentType, nContentId);
	if(content==null){
		throw new WCMException(CMyString.format(LocaleServer.getString("workflow.process.reflow.label.zhidingid","指定id为[ {0} ],Type为[ {1} ]的content没有找到"),new String[]{String.valueOf(nContentId),String.valueOf(nContentType)}));

	}
	IFlowServer m_oFlowServer = (IFlowServer) DreamFactory.createObjectById("IFlowServer");
	FlowDocs oFlowDocs = m_oFlowServer.getFlowDocs(content, null);
	Flow oFlow = null;
	FlowDoc oFirstFlowDoc = null;
	String userNames = null;
	String userIds = null;
	FlowNode oFirstFlowNode = null;
	String sFirstNodeName = null;
	Users users = null;
	String sToUsersCreator = null;
	Flow lastFlow = null;
	oFlow = content.getOwnerFlow();
	if(oFlow == null ){
		oFlow = getFlowOfChannel(nContentId);
	}	
	FlowDoc firstFlowDoc = (FlowDoc)oFlowDocs.getAt(0);
	boolean isShowOld = false;
	if(firstFlowDoc!= null && firstFlowDoc.getNode()!= null && oFlow != null){
		isShowOld = (firstFlowDoc.getNode().getFlowId() == oFlow.getId());
	}
	if(oFlowDocs != null && isShowOld){
		oFirstFlowDoc = (FlowDoc)oFlowDocs.getAt(oFlowDocs.size()-1);
		userNames = CMyString.showNull(oFirstFlowDoc.getToUserNames());
		userIds = CMyString.showNull(oFirstFlowDoc.getToUserIds());
		oFirstFlowNode = oFirstFlowDoc.getNode();
		if(oFirstFlowNode!=null){
			sFirstNodeName = CMyString.showNull(oFirstFlowNode.getName());
		}else{
			if(oFlow == null)
				throw new WCMException(LocaleServer.getString("workflow.process.reflow.label.noflow", "文档所在的栏目没有配置工作流！"));
			else{
				FlowNode stNode = FlowNode.findByName(oFlow.getId(), oFlow.getStartNodeName());
				FlowNodes nodes = stNode.getNextNodes(null);
				if (nodes == null || nodes.size() == 0
				|| (oFirstFlowNode = (FlowNode) nodes.getAt(0)) == null){
					throw new WCMException(LocaleServer.getString("workflow.process.reflow.label.getfirstnodeerr", "无法找到文档流转的第一个节点！"));	
				}
				sFirstNodeName = oFirstFlowNode.getName();
				userNames = "";
				userIds = "";
				users = oFirstFlowNode.getOperUsers(null, true);
				for (int i = 0; i < users.size(); i++) {
					User user = (User) users.getAt(i);
					userNames+= user.getName() + ",";
					userIds += user.getId() + ",";
				}

				//sToUsersCreator = oFirstFlowNode.getPropertyAsString("ToUsersCreator");
			}
		}
	}else{
		if(oFlow == null)
			throw new WCMException(LocaleServer.getString("workflow.process.reflow.label.noflow", "文档所在的栏目没有配置工作流！"));
		else{
			FlowNode stNode = FlowNode.findByName(oFlow.getId(), oFlow.getStartNodeName());
				FlowNodes nodes = stNode.getNextNodes(null);
				if (nodes == null || nodes.size() == 0
                || (oFirstFlowNode = (FlowNode) nodes.getAt(0)) == null){
					throw new WCMException(LocaleServer.getString("workflow.process.reflow.label.getfirstnodeerr", "无法找到文档流转的第一个节点！"));	
				}
				sFirstNodeName = oFirstFlowNode.getName();
				userNames = "";
				userIds = "";
				users = oFirstFlowNode.getOperUsers(null, true);
				for (int i = 0; i < users.size(); i++) {
					User user = (User) users.getAt(i);
					userNames+= user.getName() + ",";
					userIds += user.getId() + ",";
				}
		}
		
	}
	
	String[] toUserNames = userNames.split(",");
	String[] toUserIds = userIds.split(",");
	
%>
<%!
	private Flow getFlowOfChannel(int nDocId){
		Flow oFlow = null;
		try{
			Document oDocument = Document.findById(nDocId);
			oFlow = ((FlowEmployMgr) DreamFactory.createObjectById("FlowEmployMgr")).getFlow(oDocument.getChannel());
		}catch(Exception ex){
			//
		}
		return oFlow;

	}
%>
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="workflow_process_reflow.jsp.title">WCM</title>
<link href="../css/workflow.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<script src="../../app/js/wcm52/CTRSHashtable.js"></script>
<script src="../../app/js/wcm52/CTRSRequestParam.js"></script>
<script src="../../app/js/wcm52/CTRSAction.js"></script>
<script src="../../app/js/wcm52/CTRSArray.js"></script>
<script src="../../app/js/wcm52/CTRSString.js"></script>
<script src="../js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/iflowcontent.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/TempEvaler.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.workflow/WorkFlow.js"></script>
<!-- CarshBoard Inner Start -->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!-- CarshBoard Inner End -->
 <!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!--wcm-dialog end-->
<script src="./workflow_process_reflow.js"></script>
<STYLE>
	table{
		font-size: 12px;
	}
	.font_red{
		color: red;
	}
	.spDocTitle{
		width: 350px;
		height: 20px;
		line-height: 20px;
		white-space:nowrap;
		text-overflow:ellipsis;
		overflow:hidden;
		padding-left: 5px;
	}
	.options_box{
		width: 450px;
	}
	.spOptionTip{
		width: 80px;
		color: gray;
	}
	.spOptionItem{
		margin-right: 20px;
	}
	.dvOption{
		height: 20px;
		padding: 3px;
		border-bottom: aliceblue 1px solid;
		margin-top: 10px;
	}
	body{
		margin: 0;
		padding: 0;
		background: #fff;
	}
	.box_header_center{
		width:33%;
	}
</STYLE>
</HEAD>
<BODY align="center">
<div id="dy_adjust">
</div>
<script>
window.m_cbCfg = {
        btns : [
            {
                text : wcm.LANG['IFLOWCONTENT_90'] || '确定',
                cmd : function(){return doOK();}
            },
            {
                text : wcm.LANG['IFLOWCONTENT_91'] ||　'取消'
            }
        ]
    };
	function $style(_sCssText){
		var eStyle=document.createElement('STYLE');
		eStyle.setAttribute("type", "text/css");
		if(eStyle.styleSheet){// IE
			eStyle.styleSheet.cssText = _sCssText;
		} else {// w3c
			var cssText = document.createTextNode(_sCssText);
			eStyle.appendChild(cssText);
		}
		return eStyle;
	}
	function $removeChilds(_node){
		_node.innerHTML = "";
	}
	function adjustScale(){
		var sStyleId = 'dy_adjust';
		var eStyleDiv = $(sStyleId);
		var nBoxWidth = Element.getDimensions(document.body)["width"];
		/*if(PageContext.FromUrl == true) {
			nBoxWidth = nBoxWidth * 0.6;
		}//*/
		var cssStr = '#dvContainer{\
			width: ' + (nBoxWidth) + 'px;\
		}';
		cssStr += '.options_box{\
			width: ' + (nBoxWidth - 20) + 'px;\
		}';
		cssStr += '.spDocTitle{\
			width: ' + (nBoxWidth - 20) + 'px;\
		}';
		cssStr += '#txtComment{\
			width: ' + (nBoxWidth - 50) + 'px;\
			height: ' + '100' + 'px;\
		}';		
		var eStyle = $style(cssStr);
		$removeChilds(eStyleDiv);
		eStyleDiv.appendChild(eStyle);
	}
	adjustScale();
</script>
<center>
<div id="dvContainer" style="border: 0px solid #DEDCDD; text-align: center; margin-top: 5px; ">
<div style="width: 100%; padding: 5px; border: 0px solid silver;overflow: hidden; text-align: left; margin-left: 3px;" id="divContent">
	<div class="options_box">
		<div>
			<span class="box_header_left"></span>
			<span class="box_header_center" WCMAnt:param="workflow_process_render.jsp.document">
				文档		
			</span>
			<span class="box_header_right"></span>
		</div>
		<div class="box_body" style="height: 35px; line-height: 25px; font-size: 14px;" id="divDocs">
		</div>
	</div>
	<div class="options_box" style="margin-top: 10px;">
		<div>
			<span class="box_header_left"></span>
			<span class="box_header_center" WCMAnt:param="workflow_process_render.jsp.desc">
				意见	
			</span>
			<span class="box_header_right"></span>
		</div>
		<div class="box_body" style="width:100%">
			<table border="0" cellspacing="0" cellpadding="5">
			<tbody>
				<tr>
					<td align="center" width="100%"><textarea id="txtComment"></textarea></td>
				</tr>
			</tbody>
			</table>	
		</div>
	</div>
	<div class="options_box" style="margin-top: 10px; height: 150px;">
		<div>
			<span class="box_header_left"></span>
			<span class="box_header_center" WCMAnt:param="workflow_process_render.jsp.process">
				流转		
			</span>
			<span class="box_header_right"></span>
		</div>
		<div class="box_body" style="height: 200px;">
			<div id="dvNextNode" class="dvOption">
				<span class="spOptionTip" id="spOptionTip" WCMAnt:param="workflow_process_render.jsp.nextnode" style="WHITE-SPACE: nowrap">下一个节点</span>
				<span id="spFirstNodeName" style="margin-top: 5px; width:100px;"><%=sFirstNodeName%></span>
			</div>
			<div class="dvOption">
				<span class="spOptionTip" WCMAnt:param="workflow_process_render.jsp.notifyway">
					通知方式：
				</span>
				<span>
					<span class="spOptionItem"><input type="checkbox" id="chk_email" value="email">&nbsp;<span WCMAnt:param="workflow_process_render.jsp.email">邮件</span></span>
					<span class="spOptionItem"><input type="checkbox" id="chk_message" value="message">&nbsp;<span  WCMAnt:param="workflow_process_render.jsp.message">在线短消息</span> </span>
					<span class="spOptionItem"><input type="checkbox" id="chk_sms" value="sms">&nbsp;<span WCMAnt:param="workflow_process_render.jsp.sms">手机短信</span></span>
				</span>
			</div>
			<div class="dvOption">
				<span class="spOptionTip" WCMAnt:param="workflow_process_render.jsp.dealuser">
					处理人员：
				</span>
				<span id="spUsers">
					<%
						for(int i=0; i<toUserIds.length; i++){
					%>
							<SPAN title=<%=toUserNames[i]%> _uname="<%=toUserNames[i]%>" _uid="<%=toUserIds[i]%>"><%=toUserNames[i]%></SPAN>
					<%
						}
					%>
				</span>
				<span>
						(<a href="#" onclick="reasignUsers()" id="lnkResign" WCMAnt:param="workflow_process_render.jsp.reasignUsers">指定</a>)
				</span>
				<textarea id="template_users" select="." style="display: none;">
					<for select="." blankRef="dvNoUser">
						<span _uid="{#UserId}" _uname="{#UserName}" title="{#UserName}">{#UserName}</span>&nbsp;&nbsp;
					</for>
				</textarea>
			</div>
		</div>
	</div>
</div>
</center>
</BODY>
</HTML>