<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>

<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.wcm.trsserver.task.persistent.TRSServer" %>
<%@ page import="com.trs.wcm.trsserver.task.persistent.TRSServers" %>
<%@ page import="com.trs.wcm.trsserver.task.persistent.TRSGateway" %>
<%@ page import="com.trs.wcm.trsserver.task.persistent.TRSGateways" %>
<%@ page import="com.trs.components.wcm.content.persistent.DocumentShowFieldConfig" %>
<%@ page import="com.trs.infra.config.XMLConfigServer" %>
<%@ page import="com.trs.components.wcm.content.domain.ContentExtFieldMgr" %>
<%@ page import="com.trs.cms.content.WCMSystemObject" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtField" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtFields" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSites" %>
<%@ page import="com.trs.wcm.trsserver.task.persistent.SearchTask" %>
<%@ page import="com.trs.wcm.trsserver.task.persistent.SearchTasks" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@include file="../include/public_server.jsp"%>
<%
	// 1 权限校验
	if(!loginUser.isAdministrator()){
		throw new WCMException("您没有权限操作检索配置！");
	}
	//初始化参数
	int taskId = currRequestHelper.getInt("taskId",0);
	SearchTask searchTask = null;
	String sTaskName = "";
	String sMainTableName = "";
	int nServerId = 0,nGateWayId = 0;
	String sTaskSites = "";
	String sTaskFields = "";
	if(taskId > 0){
		searchTask = SearchTask.findById(taskId);
		if(searchTask == null)
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,CMyString.format(LocaleServer.getString("search_config.jsp.no_result_ofsearch", "没有找到ID为[{0}]的检索任务!"), new int[]{taskId}));
		sTaskName = searchTask.getName();
		sMainTableName = searchTask.getMainTableName();
		nServerId = searchTask.getTRServerId();
		nGateWayId = searchTask.getTRSGWId();
		sTaskSites = searchTask.getSiteIds();
		sTaskFields = searchTask.getFields();
	}
	//从配置文件中读取相关信息
	List list = XMLConfigServer.getInstance().getConfigObjects(DocumentShowFieldConfig.class);
	StringBuffer sbFieldName = new StringBuffer();
	StringBuffer sbFieldDesc = new StringBuffer();
	DocumentShowFieldConfig currDocumentShowFieldConfig = null;
	String sTemp = "";
	for(java.util.Iterator it=list.iterator(); it.hasNext(); ) {
		currDocumentShowFieldConfig = (DocumentShowFieldConfig)it.next();
		sTemp = currDocumentShowFieldConfig.getFieldname();
		if(sTemp.toUpperCase().startsWith("WCMCHNLDOC.")){
			sTemp = sTemp.substring("WCMCHNLDOC.".length());
		}
		if(sTemp.toUpperCase().startsWith("WCMDOCUMENT.")){
			sTemp = sTemp.substring("WCMDOCUMENT.".length());
		}
		if(sbFieldName.length() == 0){
			sbFieldName.append(sTemp);
			sbFieldDesc.append(currDocumentShowFieldConfig.getDesc());
		}else{
			sbFieldName.append(",").append(sTemp);
			sbFieldDesc.append(",").append(currDocumentShowFieldConfig.getDesc());
		}
	}
	//获取扩展字段信息
	ContentExtFieldMgr m_oExtFieldMgr = (ContentExtFieldMgr) DreamFactory
				.createObjectById("ContentExtFieldMgr");
	int nSiteType[] = {0,1,2,4};
	WCMFilter wcmfilter = new WCMFilter("","","");
	WCMSystemObject host = null;
	ContentExtFields fields = null;
	String sDBFieldName = "";
	String sDBFieldDesc = "";
	for(int i=0; i < nSiteType.length ; i++){
		host = new WCMSystemObject(nSiteType[i]);
		fields = m_oExtFieldMgr.getExtFields(host, wcmfilter, true);
		for (int z = 0; z < fields.size(); z++){
			ContentExtField currExtendedField = (ContentExtField)fields.getAt(z);
			if(hasFound(sbFieldName,currExtendedField.getName())) continue;
			sDBFieldName = CMyString.transDisplay(currExtendedField.getName());
			sDBFieldDesc = CMyString.transDisplay(currExtendedField.getPropertyAsString("LOGICFIELDDESC"));
			if(sbFieldName.length() == 0){
				sbFieldName.append(sDBFieldName);
				sbFieldDesc.append(sDBFieldDesc);
			}else{
				sbFieldName.append(",").append(sDBFieldName);
				sbFieldDesc.append(",").append(sDBFieldDesc);
			}
		}
	}
	String[] allFields = sbFieldName.toString().split(",");
	String[] aFieldsDesc = sbFieldDesc.toString().split(",");

	
	boolean bIgnoreSite = (taskId == 0 || CMyString.isEmpty(sTaskSites));

	//默认同步字段
	String [] defaultSynFields = {"DOCTITLE", "DOCKEYWORDS","DOCCHANNEL","SITEID", "DOCPUBURL","DOCPUBTIME","CRTIME"};
	String [] defautlSynFieldsDesc = {LocaleServer.getString("search_config.jsp.label.doctitle","文档标题"),LocaleServer.getString("search_config.jsp.label.keywords","关键词"),LocaleServer.getString("search_config.jsp.label.belong_channel","所在栏目"),LocaleServer.getString("search_config.jsp.label.belong_website","所在站点"),LocaleServer.getString("search_config.jsp.label.publish_address","发布地址"),LocaleServer.getString("search_config.jsp.label.publish_time","发布时间"),LocaleServer.getString("search_config.jsp.label.crtime","创建时间")};

	//默认同步字段
	String [] defaultChnlDocFields = {"WCMCHNLDOC.RECID AS RECID2","WCMDOCUMENT.DOCID AS DOCID2","WCMCHNLDOC.CHNLID","WCMCHNLDOC.DOCCHANNEL","WCMCHNLDOC.DOCPUBURL","WCMCHNLDOC.DOCPUBTIME","WCMCHNLDOC.CRTIME","WCMDOCUMENT.DOCTITLE","WCMCHANNEL.SITEID","WCMDOCUMENT.DOCPUBURL AS SRCPUBURL"};
	String [] defaultChnlDocFieldsDesc = {"栏目文档唯一标识","文档唯一标识","所属栏目","实体所属栏目","发布地址","发布时间","创建时间","文档标题","所属站点","源文档发布地址"};
	
	String [] allChnlDocFields = {"WCMCHNLDOC.RECID AS RECID2","WCMDOCUMENT.DOCID AS DOCID2","WCMCHNLDOC.CHNLID","WCMCHNLDOC.DOCCHANNEL","WCMCHNLDOC.DOCPUBURL","WCMCHNLDOC.DOCPUBTIME","WCMCHNLDOC.MODAL","WCMCHNLDOC.DOCSTATUS","WCMCHNLDOC.CRTIME","WCMCHNLDOC.CRUSER","WCMDOCUMENT.DOCTITLE","WCMCHANNEL.SITEID","WCMDOCUMENT.DOCPUBURL AS SRCPUBURL"};
	String [] allChnlDocFieldsDesc = {"栏目文档唯一标识","文档唯一标识","所属栏目","实体所属栏目","发布地址","发布时间","文档模式类型","文档状态","创建时间","创建者","文档标题","所属站点","源文档发布地址"};

	String [] allChannelFields = {"CHNLNAME","CHNLDATAPATH","WCMCHANNEL.STATUS","CHNLTYPE","CRUSER","CRTIME","CHANNELID","CHNLDESC","PARENTID","CHNLORDER","WCMCHANNEL.SITEID"};
	String [] allChannelFieldsDesc = {LocaleServer.getString("search_config.jsp.label.unique_channel_type","栏目唯一标识"),LocaleServer.getString("search_config.jsp.label.save_place","存储位置"),LocaleServer.getString("search_config.jsp.label.state","状态"),LocaleServer.getString("search_config.jsp.label.channel_type","栏目类型"),LocaleServer.getString("search_config.jsp.label.cruser","创建者"),LocaleServer.getString("search_config.jsp.crTime","创建时间"),LocaleServer.getString("search_config.jsp.label.channelId","栏目ID"),LocaleServer.getString("search_config.jsp.label.channelDesc","栏目描述"),LocaleServer.getString("search_config.jsp.label.parent_channelId","父栏目ID"),LocaleServer.getString("search_config.jsp.label.channel_order","栏目顺序"),LocaleServer.getString("search_config.jsp.label.websiteId","站点ID")};
	String [] defaultSynChannelFields = {"CHANNELID","CHNLDESC","PARENTID","CHNLORDER","WCMCHANNEL.SITEID"};
	String [] defautlSynChannelFieldsDesc = {LocaleServer.getString("search_config.jsp.label.channelId","栏目ID"),LocaleServer.getString("search_config.jsp.label.channelDesc","栏目描述"),LocaleServer.getString("search_config.jsp.label.parent_channelId","父栏目ID"),LocaleServer.getString("search_config.jsp.label.channel_order","栏目顺序"),LocaleServer.getString("search_config.jsp.label.websiteId","站点ID")};

%>
<HTML xmlns:TRS>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/system.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<!--wcm-dialog end-->
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
<!--AJAX-->
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/data/locale/publishdistribution.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<!-- Validator-->
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<link href="../../app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
<!--ProcessBar Start-->
<script src="../../app/js/source/wcmlib/components/ProcessBar.js"></script>
<link rel="stylesheet" type="text/css" href="../../app/js/source/wcmlib/components/processbar.css">
<!--ProcessBar End-->
<SCRIPT src="../../app/js/source/wcmlib/tabpanel/TabPanel.js"></SCRIPT>
<link href="../../app/js/source/wcmlib/tabpanel/resource/tabpanel.css" rel="stylesheet" type="text/css" />
<!--carshboard-->
<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<!--page js-->
<script language="JavaScript" src="search_config.js"></script>
<link href="../../app/js/resource/widget.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	.body-box{
		height:400px;
	}
	.row_title{
        width:80px;
        padding-left:10px;
    }
	.ext-safari .row_title{
		display:inline-block;
	}
	.ext-gecko .row_title{
		display:-moz-inline-stack;
	}
    .row_content{
        width:200px;
        padding:0px;
    }
	.check_row{
        padding-left:10px;
    }
    .input_class{
        width:150px;
    }
	.body{
		font-size:12px;
	}
	.divPadding{
		padding-top:5px;
		margin:10px;
	}
	.ext-safari .divPadding{
		padding-top:1px;
	}
	.sel_out{
		width:100%;
		height:100%;
	}
	.ext-gecko .sel_out{
		height:440px;
	}
	.toBeSel{
		width:100%;
		height:100%;
		z-index:-1;
	}
	.ext-gecko .toBeSel{
		height:440px;
	}
	.titleDesc{
		text-align:center;
		font-weight:bold;
		margin:0 auto;
	}
	.tip{
		padding-left:15px;
		text-decoration:underline;
		cursor:pointer;
		color:blue;
	}
</style>
<script language="javascript">
	window.m_fpCfg = {
		m_arrCommands : [{
			cmd : 'save',
			name : wcm.LANG.PUBLISHDISTRUBUTION_SURE || '确定'
		}],
		size : [500, 450]
	};
</script>
<script language="javascript">
<!--
	ValidationHelper.initValidation();
	ValidationHelper.addValidListener(function(){
		FloatPanel.disableCommand('link', false);
	}, "trsServerForm");
	ValidationHelper.addInvalidListener(function(){
		FloatPanel.disableCommand('link', true);
	}, "trsServerForm");
<%
	if(!loginUser.isAdministrator()){
%>
		FloatPanel.disableCommand('link', true);
<%
	}
%>
//-->
</script>
</head>
<body class="body">
	<form id="addEditForm">
	<input type="hidden" name="taskId" id="taskId" value="<%=taskId%>"/>
	<input type="hidden" name="serverId" id="serverId" value=""/>
	<input type="hidden" name="gateWayId" id="gateWayId" value=""/>
	<input type="hidden" name="fields" id="fields" value =""/>
	<input type="hidden" name="siteIds" id="siteIds" value =""/>
	<input type="hidden" name="ignoreSite" id="ignoreSite" value ="false"/>
	<div class="wcm-tab-panel" id="tabPanel">
		<div class="head-box" style="padding-left:20px;">
			<div class="tab-head" id="tab-head-1" item="tab-body-1"><a href="#" WCMAnt:param="search_config.jsp.baseprops">基本属性</a></div>
			<div class="tab-head" id="tab-head-2" item="tab-body-2"><a href="#" WCMAnt:param="search_config.jsp.server">Server配置</a></div>
			<div class="tab-head" id="tab-head-3" item="tab-body-3"><a href="#" WCMAnt:param="search_config.jsp.gateway">GateWay配置</a></div>
			<div class="tab-head" id="tab-head-4" item="tab-body-4"><a href="#" WCMAnt:param="search_config.jsp.synfields">同步字段配置</a></div>
			<div class="tab-head" id="tab-head-5" item="tab-body-5"><a href="#" WCMAnt:param="search_config.jsp.siteselect">站点选择</a></div>
		</div>
		<div class="body-box" style="padding:10px;">
			<div class="tab-body" id="tab-body-1">
				<div style="width:100%;height:390px;">
				<form id="trsPropForm">
				<div class="divPadding">
					<span class="row_title" WCMAnt:param="publishdistribution_add_edit.jsp.searchSource">检索数据源:</span>
					<span class="row_content">
						<span style="padding-left:12px;"><select name="taskSource" id="taskSource" onchange="changeSource()" <%=taskId > 0 ? "disabled":""%>>
							<option value="WCMDOCUMENT" WCMAnt:param="publishdistribution_add_edit.jsp.document" <%=sMainTableName.toUpperCase().equals("WCMDOCUMENT") ? "selected":""%>>文档</option>
							<option value="WCMCHNLDOC"  <%=sMainTableName.toUpperCase().equals("WCMCHNLDOC") ? "selected":""%>>栏目文档</option>
							<option value="WCMCHANNEL" WCMAnt:param="publishdistribution_add_edit.jsp.channel" <%=sMainTableName.toUpperCase().equals("WCMCHANNEL") ? "selected":""%>>栏目</option>
						</select></span>
						<span style="color:red">*</span>
					</span>
				</div>
				<div class="divPadding">
					<span class="row_title" WCMAnt:param="publishdistribution_add_edit.jsp.taskName">任务名称:</span>
					<span class="row_content">
						<input type="text" name="taskName" id="taskName" value="<%=sTaskName%>" class="input_class" validation="type:'string',required:'true',max_len:'25',showid:'ValidatorMsg'" validation_desc="任务名称" WCMAnt:paramattr="validation_desc:trsserver_config.jsp.taskName">
						<span style="color:red">*</span>
					</span>
				</div>
				</form>
				</div>
			</div>
			<div class="tab-body" id="tab-body-2">
				<div style="width:100%;height:390px;">
				<form id="trsServerForm">
				<div style="padding-left:20px;padding-top:20px;height:50px;"><span WCMAnt:param="publishdistribution_add_edit.jsp.serverselect">Server选择：</span><select name="server" id="server" style="width:148px;margin-left:13px;" onchange="changeServer()">
					<%
						WCMFilter filter = new WCMFilter("","","");
						TRSServers servers = TRSServers.openWCMObjs(loginUser,filter);
						TRSServer server = null;
					%>
					<option value="0"></option>
					<%
						for(int i=0, nSize = servers.size();i < nSize; i++){
							server = (TRSServer) servers.getAt(i);
							if(server == null) continue;
					%>
						<option value="<%=server.getId()%>" ip="<%=server.getIP()%>" port="<%=server.getPort()%>" user="<%=CMyString.filterForHTMLValue(server.getUserName())%>" password="<%=CMyString.filterForHTMLValue(server.getPassword())%>" tablename ="<%=CMyString.filterForHTMLValue(server.getTableName())%>" <%=nServerId == server.getId() ? "selected" : ""%>><%=CMyString.transDisplay(server.getSName())%></option>
					<%
						}
					%>
				</select>
				<span id="addServer" onclick="addServer()" class="tip"  style="display:none;"><img src="../images/channel/option_add.gif" border=0 alt="" /></span>
				<span id="deleteServer" onclick="deleteServer()" class="tip" WCMAnt:param="publishdistribution_add_edit.jsp.delete" style="display:none;">删除</span></div> 
				<hr/>
				<div class="divPadding">
					<span class="row_title" WCMAnt:param="publishdistribution_add_edit.jsp.address">服务器地址:</span>
					<span class="row_content">
						<input type="text" name="TargetServer" id="TargetServer" value="" class="input_class" validation="type:'string',required:'true',max_len:'25',desc:'服务器地址',showid:'ValidatorMsg'" validation_desc="服务器地址" WCMAnt:paramattr="validation_desc:trsserver_config.jsp.address"/>
						<span style="color:red">*</span>
					</span>
				</div>
				<div class="divPadding">
					<span class="row_title" WCMAnt:param="publishdistribution_add_edit.jsp.port">服务器端口:</span>
					<span class="row_content">
						<input type="text" name="TargetPort" id="TargetPort" value="" class="input_class" validation="type:'int',required:'true',value_range:'1,20000',showid:'ValidatorMsg'" validation_desc="服务器端口" WCMAnt:paramattr="validation_desc:trsserver_config.jsp.port">
						<span style="color:red">*</span>
					</span>
				</div>
				<div class="divPadding">
					<span class="row_title" WCMAnt:param="publishdistribution_add_edit.jsp.username">用户名:</span>
					<span class="row_content">
						<input type="text" name="ServerUser" id="ServerUser" value="" class="input_class" validation="type:'string',required:'',max_len:'25',desc:'用户名',showid:'ValidatorMsg'" validation_desc="用户名" WCMAnt:paramattr="validation_desc:trsserver_config.jsp.username"/>
						<span style="color:red" id="userRed">*</span>
					</span>
				</div>
				<div class="divPadding">
					<span class="row_title" WCMAnt:param="publishdistribution_add_edit.jsp.password">密码：</span>
					<span class="row_content">
						<input type="password" name="ServerPassword" id="ServerPassword" value="" class="input_class" validation="type:'string',required:'',max_len:'25',desc:'密码',showid:'ValidatorMsg'" validation_desc="密码" WCMAnt:paramattr="validation_desc:trsserver_config.jsp.pwd"/>
						<span style="color:red" id="passwordRed">*</span>
					</span>
				</div>
				<div class="divPadding">
					<span class="row_title" WCMAnt:param="publishdistribution_add_edit.jsp.tablename">表名:</span>
					<span class="row_content">
						<input type="text" name="ServerTableName" id="ServerTableName" value="" class="input_class" validation="type:'string',required:'',max_len:'25',desc:'表名',showid:'ValidatorMsg'" validation_desc="表名" WCMAnt:paramattr="validation_desc:trsserver_config.jsp.tablename"/>
						<span style="color:red" id="passwordRed">*</span>
					</span>
				</div>
				<div class="divPadding">
					<span class="row_title" WCMAnt:param="publishdistribution_add_edit.jsp.test">连接测试:</span>
					<span class="row_content">
						<span style="padding-left:15px;text-decoration:underline;cursor:pointer;color:blue;" onclick="testServer()" WCMAnt:param="publishdistribution_add_edit.jsp.click">点击</span>
					</span>
				</div>
				</form>
				</div>
			</div>
			<div class="tab-body" id="tab-body-3">
				<div style="width:100%;height:390px;">
				<form id="trsGateWayForm">
					<div style="padding-left:20px;padding-top:20px;height:50px;"><span WCMAnt:param="publishdistribution_add_edit.jsp.gatewayselect">GateWay选择: </span><select name="gateway" id="gateway" style="width:150px;margin-left:10px;" onchange= "changeGateWay()">
					<%
						filter = new WCMFilter("","","");
						TRSGateways gateways = TRSGateways.openWCMObjs(loginUser,filter);
						TRSGateway gateway = null;
					%>
					<option value="0"></option>
					<%
						for(int k=0, nSize = gateways.size();k < nSize; k++){
							gateway = (TRSGateway) gateways.getAt(k);
							if(gateway == null) continue;
					%>
						<option value="<%=gateway.getId()%>" ip="<%=gateway.getIP()%>" port="<%=gateway.getPort()%>" user="<%=CMyString.filterForHTMLValue(gateway.getUserName())%>" password="<%=CMyString.filterForHTMLValue(gateway.getPassword())%>" <%=nGateWayId == gateway.getId() ? "selected" : ""%>><%=CMyString.transDisplay(gateway.getGName())%></option>
					<%
						}
					%>
				</select>
				<span id="addGateWay" onclick="addGateWay()" style="display:none;" class="tip"><img src="../images/channel/option_add.gif" border=0 alt="" /></span>
				<span id="deleteGateWay" onclick="deleteGateWay()" class="tip" WCMAnt:param="publishdistribution_add_edit.jsp.delete" style="display:none;">删除</span></div> 
				<hr/>
				<div class="divPadding">
					<span class="row_title" WCMAnt:param="publishdistribution_add_edit.jsp.address">服务器地址:</span>
					<span class="row_content">
						<input type="text" name="GateWayServer" id="GateWayServer" value="" class="input_class" validation="type:'string',required:'',max_len:'25',desc:'服务器地址',showid:'ValidatorMsg'" validation_desc="服务器地址" WCMAnt:paramattr="validation_desc:trsserver_config.jsp.address"/>
						<span style="color:red">*</span>
					</span>
				</div>
				<div class="divPadding">
					<span class="row_title" WCMAnt:param="publishdistribution_add_edit.jsp.port">服务器端口:</span>
					<span class="row_content">
						<input type="text" name="GateWayPort" id="GateWayPort" value="" class="input_class" validation="type:'int',required:'true',value_range:'1,20000',showid:'ValidatorMsg'" validation_desc="服务器端口" WCMAnt:paramattr="validation_desc:trsserver_config.jsp.port">
						<span style="color:red">*</span>
					</span>
				</div>
				<div class="divPadding">
					<span class="row_title" WCMAnt:param="publishdistribution_add_edit.jsp.username">用户名:</span>
					<span class="row_content">
						<input type="text" name="GateWayUser" id="GateWayUser" value="" class="input_class" validation="type:'string',required:'false',max_len:'25',desc:'用户名',showid:'ValidatorMsg'" validation_desc="用户名" WCMAnt:paramattr="validation_desc:trsserver_config.jsp.username"/>
					</span>
				</div>
				<div class="divPadding">
					<span class="row_title" WCMAnt:param="publishdistribution_add_edit.jsp.password">密码：</span>
					<span class="row_content">
						<input type="password" name="GateWayPassword" id="GateWayPassword" value="" class="input_class" validation="type:'string',required:'false',max_len:'25',desc:'密码',showid:'ValidatorMsg'" validation_desc="密码" WCMAnt:paramattr="validation_desc:trsserver_config.jsp.pwd"/>
					</span>
				</div>
				<div class="divPadding">
					<span class="row_title" WCMAnt:param="publishdistribution_add_edit.jsp.test">连接测试:</span>
					<span class="row_content">
						<span style="padding-left:15px;text-decoration:underline;cursor:pointer;color:blue;" onclick="testGateWay()" WCMAnt:param="publishdistribution_add_edit.jsp.click">点击</span>
					</span>
				</div>
				</form>
				</div>
			</div>
			<div class="tab-body" id="tab-body-4">
				<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:390px;table-layout:fixed;">
				   <tr>
						<td width="170px" height="20px;" class="titleDesc" WCMAnt:param="publishdistribution_add_edit.jsp.fields">待选字段</td>
						<td>&nbsp;</td>
						<td width="170px" class="titleDesc" WCMAnt:param="publishdistribution_add_edit.jsp.synfields">同步字段</td>
					</tr>
					<tr>
						<td>
							<div style="width:100%;height:100%;">
								<div class="sel_out">
									<span id="document_base">
									<select name="base1" id="base1" class="toBeSel" multiple="true">
									<%
										for (int i = 0; i < allFields.length; i++){
											String sSingleField = allFields[i];
											if(sSingleField.toUpperCase().equals("PUBCONFIG")) continue;
											if(sSingleField.toUpperCase().equals("REFTOCHANNEL")) continue;
											if(sSingleField.toUpperCase().equals("ORDERPRI")) continue;
											String [] temp = null;
											if(sTaskFields != null && sTaskFields != ""){
													temp = sTaskFields.split(",");
											}else{
													temp = defaultSynFields;
											}
											if(hasContain(sSingleField,temp)) continue;
									%>								
									<option value="<%=sSingleField%>"><%=aFieldsDesc[i]%></option>
									<%
										}
									%>
									</select></span>
									<span id="channel_base" style="display:none;">
									<select name="base2" id="base2" class="toBeSel" multiple="true">
									<%
										for (int i = 0; i < allChannelFields.length; i++){
											String sSingleField = allChannelFields[i];
											String [] temp = null;
											if(sTaskFields != null && sTaskFields != ""){
													temp = sTaskFields.split(",");
											}else{
													temp = defaultSynChannelFields;
											}
											if(hasContain(sSingleField,temp)) continue;
									%>								
									<option value="<%=sSingleField%>"><%=allChannelFieldsDesc[i]%></option>
									<%
										}
									%>
									</select>
									</span>
									<span id="chnldoc_base" style="display:none;">
									<select name="base3" id="base3" class="toBeSel" multiple="true">
									<%
										for (int i = 0; i < allChnlDocFields.length; i++){
											String sSingleField = allChnlDocFields[i];
											String [] temp = null;
											if(sTaskFields != null && sTaskFields != ""){
													temp = sTaskFields.split(",");
											}else{
													temp = defaultChnlDocFields;
											}

											if(hasContainField(sSingleField,temp)) continue;
									%>								
									<option value="<%=sSingleField%>"><%=allChnlDocFieldsDesc[i]%></option>
									<%
										}
									%>
									</select>
									</span>
								</div>
							</div>
						</td>
						<td><div style="cursor:pointer;text-align:center;margin:0 auto;"><img src="../images/channel/docprop.gif" border=0 alt="" onclick='doTransfer("base","syn")'/></div><br/><br/><div style="cursor:pointer;text-align:center;margin:0 auto;"><img src="../images/channel/turnleft.gif" border=0 alt="" onclick='doTransfer("syn","base")'/></div></td>
						<td>
							<div style="width:100%;height:100%;">
								<div class="sel_out">
									<span id="document_syn">
									<select name="syn1" id="syn1" class="toBeSel" multiple="true">
									<%
										String [] tempDesc = null;
										String sSingleField = "";
										String sSingleDesc = "";
										if(sTaskFields != null && sTaskFields != ""){
												tempDesc = sTaskFields.split(",");
												for (int i = 0; i < tempDesc.length; i++){
													sSingleField = tempDesc[i];
													if(sSingleField.toUpperCase().equals("DOCID")) continue;
													if(sSingleField.toUpperCase().equals("DOCCONTENT")) continue;
													sSingleDesc = getFieldDesc(sSingleField,allFields,aFieldsDesc,defaultSynFields,defautlSynFieldsDesc);
									%>
									<option value="<%=sSingleField%>"><%=sSingleDesc%></option>	
									<%
												}
										}else{
												for (int i = 0; i < defautlSynFieldsDesc.length; i++){
													sSingleField = defaultSynFields[i];
													sSingleDesc = defautlSynFieldsDesc[i];
									%>
									<option value="<%=sSingleField%>"><%=sSingleDesc%></option>
									<%	
												}
										}
									%>	
									</select></span>
									<span id="channel_syn" style="display:none;">
									<select name="syn2" id="syn2" class="toBeSel" multiple="true">
									<%
										tempDesc = null;
										sSingleField = "";
										sSingleDesc = "";
										if(sTaskFields != null && sTaskFields != ""){
												tempDesc = sTaskFields.split(",");
												for (int i = 0; i < tempDesc.length; i++){
													sSingleField = tempDesc[i];
													sSingleDesc = getFieldDesc(sSingleField,allFields,aFieldsDesc,defaultSynChannelFields,defautlSynChannelFieldsDesc);
									%>
									<option value="<%=sSingleField%>"><%=sSingleDesc%></option>	
									<%
												}
										}else{
												for (int i = 0; i < defautlSynChannelFieldsDesc.length; i++){
													sSingleField = defaultSynChannelFields[i];
													sSingleDesc = defautlSynChannelFieldsDesc[i];
									%>
									<option value="<%=sSingleField%>"><%=sSingleDesc%></option>
									<%	
												}
										}
									%>	
									</select>
									</span>
									<span id="chnldoc_syn" style="display:none;">
									<select name="syn3" id="syn3" class="toBeSel" multiple="true">
									<%
										tempDesc = null;
										sSingleField = "";
										sSingleDesc = "";
										if(sTaskFields != null && sTaskFields != ""){
												tempDesc = sTaskFields.split(",");
												for (int i = 0; i < tempDesc.length; i++){
													sSingleField = tempDesc[i];
													sSingleDesc = getFieldDesc(sSingleField,allChnlDocFields,allChnlDocFieldsDesc,defaultChnlDocFields,defaultChnlDocFieldsDesc);
									%>
									<option value="<%=sSingleField%>"><%=sSingleDesc%></option>	
									<%
												}
										}else{
												for (int i = 0; i < defaultChnlDocFieldsDesc.length; i++){
													sSingleField = defaultChnlDocFields[i];
													sSingleDesc = defaultChnlDocFieldsDesc[i];
									%>
									<option value="<%=sSingleField%>"><%=sSingleDesc%></option>
									<%	
												}
										}
									%>	
									</select>
									</span>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab-body" id="tab-body-5">
				<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:390px;table-layout:fixed;">
				   <tr>
						<td width="170px" height="20px;" class="titleDesc" WCMAnt:param="publishdistribution_add_edit.jsp.websites">待选站点&nbsp;<span style="cursor:pointer;color:blue;" onclick="changeSite()"><input type="checkbox" name="selAllSites" id="selAllSites" <%=bIgnoreSite ? "checked":""%>/><label for="selAllSites">不限制站点</label></span></td>
						<td>&nbsp;</td>
						<td width="170px" class="titleDesc" WCMAnt:param="publishdistribution_add_edit.jsp.searchsites">查询站点</td>
					</tr>
					<tr>
						<td>
							<div style="width:100%;height:100%;">
								<div class="sel_out">
									<select name="baseSites" id="baseSites" class="toBeSel" multiple="true">
									<%									
										//获取系统下所有的站点
										WebSites sites = WebSites.openWCMObjs(loginUser,new WCMFilter("","",""));
										String sSiteIds = "";
										String sSiteDesc = "";
										WebSite site = null;										
										for (int i = 0; i < sites.size(); i++){
											site = (WebSite) sites.getAt(i);
											if(site == null) continue;
											if(sSiteIds == ""){
												sSiteIds += site.getId();
												sSiteDesc += site.getDesc();
											}else{
												sSiteIds += (","+ site.getId());
												sSiteDesc += ("," + site.getDesc());
											}
											if(sTaskSites != null && sTaskSites != ""){
												boolean bFound = false;
												String[] s = sTaskSites.split(",");
												for(int m=0, nLength = s.length; m < nLength ; m++){
													if((site.getId() + "").equalsIgnoreCase(s[m])){
														bFound = true;
														break;
													}
												}
												if(bFound) continue;
											}

									%>								
									<option value="<%=site.getId()%>"><%=CMyString.transDisplay(site.getDesc())%></option>
									<%
										}
									%>
									</select>
								</div>
							</div>
						</td>
						<td><div style="cursor:pointer;text-align:center;margin:0 auto;"><img src="../images/channel/docprop.gif" border=0 alt="" onclick='moveTo("baseSites","synSites")'/></div><br/><br/><div style="cursor:pointer;text-align:center;margin:0 auto;"><img src="../images/channel/turnleft.gif" border=0 alt="" onclick='moveTo("synSites","baseSites")'/></div></td>
						<td>
							<div style="width:100%;height:100%;">
								<div class="sel_out">
									<select name="synSites" id="synSites" class="toBeSel" multiple="true">
									<%
										if(sTaskSites != null && !sTaskSites.equals("")){
											WebSites selSites = WebSites.findByIds(loginUser,sTaskSites);
											WebSite selSite = null;
											for (int i = 0; i < selSites.size(); i++){
												selSite = (WebSite)selSites.getAt(i);
												if(selSite == null) continue;
									%>
										<option value="<%=selSite.getId()%>"><%=CMyString.transDisplay(selSite.getDesc())%></option>
									<%
											}
										}
									%>
									</select>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	</form>
</body>
</html>
<%!
	private boolean hasFound(StringBuffer souce,String stemp){
		if(souce.length() == 0) return false;
		String[] s = souce.toString().split(",");
		for(int m=0, nLength = s.length; m < nLength ; m++){
			if(stemp.equalsIgnoreCase(s[m])){
				return true;
			}
		}
		return false;
	}
	private boolean hasContain(String stemp,String[] defaultField){
		stemp = stemp.toUpperCase();
		if(stemp.startsWith("WCMDOCUMENT.")){
			stemp = stemp.substring("WCMDOCUMENT.".length());
		}	
		if(stemp.startsWith("WCMCHNLDOC.")){
			stemp = stemp.substring("WCMCHNLDOC.".length());
		}
		for(int i=0, nLength = defaultField.length; i < nLength; i++){
			if(stemp.equals(defaultField[i])){
				return true;
			}
		}
		return false;
	}

	private boolean hasContainField(String stemp,String[] defaultField){
		stemp = stemp.toUpperCase();
		for(int i=0, nLength = defaultField.length; i < nLength; i++){
			if(stemp.equals(defaultField[i])){
				return true;
			}
		}
		return false;
	}

	private String getFieldDesc(String sFieldName, String[] allFields,String[] allFieldsDesc, String[] defaultFields, String[] defaultFieldsDesc){
		for (int i = 0; i < allFields.length; i++){
			String stemp = allFields[i];
				stemp = stemp.toUpperCase();
			if(stemp.startsWith("WCMDOCUMENT.")){
				stemp = stemp.substring("WCMDOCUMENT.".length());
			}	
			if(stemp.startsWith("WCMCHNLDOC.")){
				stemp = stemp.substring("WCMCHNLDOC.".length());
			}
			
			if(sFieldName.indexOf(stemp) >=0){
				return allFieldsDesc[i];
			}
		}
		for (int k = 0; k < defaultFields.length; k++){
			String sSingleField = defaultFields[k];
			if(sFieldName.trim().equalsIgnoreCase(sSingleField)){
				return defaultFieldsDesc[k];
			}
		}
		return sFieldName;
	}
%>