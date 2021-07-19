<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.components.wcm.resource.Statuses" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDocs" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.ViewDocument" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtField" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtFields" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@include file="document_addedit_extendedfield.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%!
	// 判断用户对某对象是否有权限
	private boolean hasRight(User _currUser, CMSObj _objCurrent,int _nRightIndex) throws WCMException{
		if(_objCurrent instanceof ViewDocument){
			return ((ViewDocument)_objCurrent).hasRight(_currUser,_nRightIndex);
		}
		else if(_objCurrent instanceof Document){
			return DocumentAuthServer.hasRight(_currUser,((Document)_objCurrent).getChannel(),(Document)_objCurrent,_nRightIndex);
		}
		else if(_objCurrent instanceof ChnlDoc){
			return DocumentAuthServer.hasRight(_currUser,(ChnlDoc)_objCurrent,_nRightIndex);
		}
		return AuthServer.hasRight(_currUser,_objCurrent,_nRightIndex);
	}

	private String getExtFieldHtml(ContentExtField currExtendedField,String sValue) throws Throwable{
		if(currExtendedField == null) return "";

		sValue = PageViewUtil.toHtmlValue(sValue);
		return getPropertyHtml(currExtendedField,sValue,false);
	}
%>
<%
	int nChnlId = currRequestHelper.getInt("ChannelId",0);
	Channel chnl = Channel.findById(nChnlId);
	IChannelService currChannelService = (IChannelService)DreamFactory.createObjectById("IChannelService");
	ContentExtFields currExtendedFields  = currChannelService.getExtFields(chnl, null);
	String strDBName = DBManager.getDBManager().getDBType().getName();
%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
 <head>
  <title WCMAnt:param="documents_modify.jsp.chengedocs"> 批量修改文档 </title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="Author" content="CH">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <script language="javascript" src="../../app/js/easyversion/lightbase.js" type="text/javascript"></script>
  <script src="../../app/js/runtime/myext-debug.js"></script>
  <script src="../../app/js/source/wcmlib/WCMConstants.js"></script>

  <script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
  <script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
  <script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
  <script src="../../app/js/easyversion/calendar_lang/cn.js" WCMAnt:locale="../../app/js/easyversion/calendar_lang/$locale$.js"></script>
  
	<!--validator start-->
	<script src="../js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
	<script src="../js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
	<script src="../js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
	<script src="../js/source/wcmlib/com.trs.validator/Validator.js"></script>
	<link href="../js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />

  <script language="javascript" src="../../app/js/easyversion/calendar.js" type="text/javascript"></script>	
  <link href="documents_modify.css" rel="stylesheet" type="text/css" />

  <script language="javascript">
  <!--
	window.m_fpCfg = {
		m_arrCommands : [{
			cmd : 'save',
			name : wcm.LANG.CHANNEL_TRUE||'确定'
		}],
		size : [450, 300]
	};
		
	var calenderControls = [];
	Event.observe(window, 'load', function(){
		if(calenderControls.length > 0){
			for (var i = 0; i < calenderControls.length; i++){
				wcm.TRSCalendar.get({input:calenderControls[i],handler:'btn' + calenderControls[i], dtFmt:'yyyy-mm-dd HH:MM', withtime:true});
			}
		}
	});

	function save(){
		//校验
		if(!ValidationHelper.doValid('postForm')){
			//alert("输入的参数有误！")
			return false;
		}
		var name = $("property-select").value;
		// 添加清空字段的确认处理
		var docAuthor = $('docAuthorEl').value;
		var docSource = $('docSourceEl').value;
		var docReltime = $('docReltimeEl').value;
		if(!docAuthor && name == "DOCAUTHOR"){
			var bRst = confirm("作者属性值设置为空，这将导致清空以前的数据，你确定进行保存？");
			if(!bRst) return false;
		}
		if(!docSource && name == "DOCSOURCENAME"){
			var bRst = confirm("文档来源属性值为空，这将导致清空以前的数据，你确定进行保存？");
			if(!bRst) return false;
		}
		if(!docReltime && name == "DOCRELTIME"){
			var bRst = confirm("撰写时间属性值为空，这将导致清空以前的数据，你确定进行保存？");
			if(!bRst) return false;
		}
		//需要判断如果修改的值为扩展字段时的大小判断
		var extendField = document.getElementById(name);
		if(extendField && extendField.hasAttributes("max_len")){
			if(extendField.max_len < extendField.value.length){
				alert("输入的值大于最大长度" + extendField.max_len + ".");
				return false;;
			}
		}
		// 获取参数
		var param = {};
		var els = document.getElementsByName(name),arr=[];
		for (var i = 0; i < els.length; i++){
			arr.push($F(els[i]));
		}
		param[name]=arr.join(",");
		notifyFPCallback(param);
	}
	Event.observe(window,"load",function(){
		init();
	})
	function init(){
		// 用服务的方式首先获取到系统中所有的状态
		wcm.TRSCalendar.get({input:'docReltimeEl',handler:'timeBtn',wot:false,withtime:true,dtFmt:'yyyy-MM-dd HH:mm',bBlurClose:true}); //添加失去焦点时，隐藏日期控件
		//ValidationHelper.initValidation();
		// 初始化提示信息
		var docids = FloatPanel.dialogArguments.ChnlDocIds;
		$("desc").innerHTML =String.format( "将修改<span class=\'docnum\'>{0}</span>篇文档", docids.split(",").length) ;
		var pSelect = $("property-select");
		Event.observe(pSelect,"change",function(){
			var els = document.getElementsByClassName("display-block");
			Element.removeClassName(els[0],"display-block");
			Element.addClassName($(pSelect.value+"-LI"),"display-block");
		});
	}
	function getHelper(_sServceFlag){
		return new com.trs.web2frame.BasicDataHelper();
	}
  //-->
  </script>

 </head>

 <body>
	<span class="desc" id="desc"></span>
	<hr/>
	<form id="postForm">
	<table border="0" cellspacing="0" cellpadding="0" id="properties" width="100%" class="properties">
	<tr class="property">
		<td WCMAnt:param="documents_modify.jsp.attrname" style="text-align:center;">属性名称:</td><td>
		<select name="" id="property-select">
			<option value="DOCAUTHOR"  WCMAnt:param="documents_modify.jsp.author">作者</option>
			<option value="DOCSOURCENAME"  WCMAnt:param="documents_modify.jsp.docresouce">文档来源</option>
			<option value="DOCRELTIME"  WCMAnt:param="documents_modify.jsp.writetime">撰写时间</option>
			<%
			for (int i = 0; i < currExtendedFields.size(); i++){
				ContentExtField field = (ContentExtField)currExtendedFields.getAt(i);
			%>
				<option value="<%=field.getName()%>"><%=field.getDesc()%></option>
			<%}%>
		</select>
		</td></tr>
	<tr class="values"><td  WCMAnt:param="documents_modify.jsp.newattrvalue" style="text-align:center;">新属性值:</td>
	<td>
	<ul>
		<li id="DOCAUTHOR-LI" class="display-block"><input id="docAuthorEl" validation="type:string,required:0,max_len:20,desc:作者" style="margin-right:25px;" type="text" name="DOCAUTHOR" value="" validation_desc="作者" WCMAnt:paramattr="validation_desc:documents_modify.author"></li>
		<li id="DOCSOURCENAME-LI"><input id="docSourceEl" validation="type:string,required:0,max_len:20,desc:文档来源" style="margin-right:25px;" type="text" name="DOCSOURCENAME" value="" validation_desc="文档来源" WCMAnt:paramattr="validation_desc:documents_modify.source"></li>
		<li id="DOCRELTIME-LI"><input type="text" name="DOCRELTIME" id="docReltimeEl" value=""><div class="timeBtn" id="timeBtn"></div></li>
	<%
	for (int i = 0; i < currExtendedFields.size(); i++){
		ContentExtField field = (ContentExtField)currExtendedFields.getAt(i);
	%>
		<li id="<%=field.getName()%>-LI">
			<%=getPropertyHtml(field,"",false)%>
		</li>
	<%}%>
	</ul>
	</td></tr>
	
	</form>
 </body>
</html>