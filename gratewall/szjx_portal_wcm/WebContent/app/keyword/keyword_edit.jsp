<%--
/** Title:			keyword_addedit.jsp
 *  Description:
 *		Keyword的添加修改页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			TRS WCM 5.2
 *  Created:		2009-06-23 13:44:49
 *  Vesion:			1.0
 *  Last EditTime:	2009-06-23 / 2009-06-23
 *	Update Logs:
 *		TRS WCM 5.2@2009-06-23 产生此文件
 *
 *  Parameters:
 *		see keyword_addedit.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.resource.Keyword" %>
<%@ page import="com.trs.components.wcm.resource.Keywords" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nKeywordId = currRequestHelper.getInt("KeywordId", 0);
	int nSiteId = currRequestHelper.getInt("siteId", 0);
	int nSiteType = 0;
	Keyword currKeyword = null;
	if(nKeywordId > 0){
		currKeyword = Keyword.findById(nKeywordId);
		if(currKeyword == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("keyword_edit.id.zero","获取ID为[{0}]的Keyword失败！"),new int[]{nKeywordId}));
		}
		nSiteType = currKeyword.getSITETYPE();
	}else{//nKeywordId==0 create a new group
		currKeyword = Keyword.createNewInstance();
	}
//5.权限校验

//6.业务代码
	String sKName = CMyString.filterForHTMLValue(currKeyword.getKNAME());
//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="keyword_edit.jsp.title">TRS WCM 修改Keyword</TITLE>
<BASE TARGET="_self">
<link rel="stylesheet" type="text/css" href="../js/resource/widget.css"/>
<style type="text/css">
	.sp{
		font-size:12px;
	}
</style>
<script src="../js/easyversion/lightbase.js"></script>
<script src="../js/easyversion/extrender.js"></script>
<script src="../js/easyversion/elementmore.js"></script>
<script src="../js/source/wcmlib/WCMConstants.js"></script>
<script src="../js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../js/easyversion/ajax.js"></script>
<script src="../js/easyversion/basicdatahelper.js"></script>
<script src="../js/easyversion/web2frameadapter.js"></script>
<script src="../js/easyversion/elementmore.js"></script>
<script src="../js/data/locale/keyword.js"></script>

<script src="../js/source/wcmlib/Observable.js"></script>
<script src="../js/source/wcmlib/Component.js"></script>
<script src="../js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../js/source/wcmlib/dialog/DialogAdapter.js"></script>
<!--validator-->
<script src="../js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../js/source/wcmlib/com.trs.validator/Validator.js"></script>
<SCRIPT LANGUAGE="JavaScript">
window.m_cbCfg = {
	btns : [
		{
			id : 'btnTrue',
			text : wcm.LANG.KEYWORD_6 || '确定',
			cmd : function(){
				return saveKName(this);
			}
		},
		{
			id : 'btnCancel',
			text : wcm.LANG.KEYWORD_7 || '取消',
			cmd : function(){
				
			}
		}
	]
}

var sOldName = "<%=sKName%>";
function saveKName(cb){
	if($('KName').value.trim()==""){
		Ext.Msg.alert(wcm.LANG.KEYWORD_12 || "关键词名称不能为空！");
		return false;
	}
	if($('KName').value.length > 50){
		Ext.Msg.$alert(wcm.LANG.KEYWORD_13 || '关键词名不能超过50个字符!');
		$('KName').focus();
		return false;
	}
	//保存前，判断是否发生重名，然后在checkKeywordName()方法的回调函数中进行保存处理
	var edit = 1;
	if($('KName').value.trim()==sOldName.trim())return ;
	checkKeywordName(edit, cb);
	return false;
}

</SCRIPT>
</HEAD>

<BODY>
<form id="keywordForm" onsubmit="return false;">
<INPUT TYPE="hidden" id="keywordId" NAME="keywordId" Value="<%=nKeywordId%>"/>
<div style="width:100%;height:27px;"><span class="sp" WCMAnt:param="keyword_edit.jsp.keyname">关键词名称：</span><input type="text" id="KName" name="KName" style="border:1px solid gray;" value="<%=sKName%>" validation="type:'string',required:true,max_len:'50',showid:'errMsg',rpc:'checkKeywordName'" validationId="KName"/></div>
<span id="errMsg" style="float:left;padding-left:5px"></span>
</form>
<script language="javascript">
<!--
	function init(){
		var m_arrValidations = [{
				renderTo : "KName",
				desc : wcm.LANG.KEYWORD_1 || '关键词名称'
			}
		];
		ValidationHelper.registerValidations(m_arrValidations);
		ValidationHelper.addValidListener(function(){
			wcmXCom.get('btnTrue').enable();
        }, "KName");
        ValidationHelper.addInvalidListener(function(){
			wcmXCom.get('btnTrue').disable();
        }, "KName");
		ValidationHelper.initValidation();
	};
	function checkKeywordName(edit, cb){
		if($('KName').value.trim()==sOldName.trim())return ;
		var sKName = $F('KName');
		var oPostData = {
			KName : sKName,
			siteId : <%=nSiteId%>,
			siteType : <%=nSiteType%>
		}
		BasicDataHelper.JspRequest(
			WCMConstants.WCM6_PATH + '/keyword/keyword_exists.jsp',
			oPostData,  false,
			function(transport){
				if(transport.responseText == 'false'){
					if(edit && edit==1){
						//编辑状态下的保存KName，不重名则保存
						saveEditValue(cb);
					} else {
						ValidationHelper.successRPCCallBack();
					}
				}else{
					if(edit==1){document.getElementById("KName").blur();}
					ValidationHelper.failureRPCCallBack(wcm.LANG.KEYWORD_14 || "已经存在这个关键词.");
				}
			}
		);
	}
	function saveEditValue(cb){
		var oPostData = {
			KeywordId : $('keywordId').value,
			KName : $('KName').value
		}
		BasicDataHelper.JspRequest('../keyword/keyword_addedit_dowith.jsp',oPostData,false,function(){
			cb.notify();
			cb.close();
		}.bind(this));
	}
//-->
</script>
</BODY>
</HTML>