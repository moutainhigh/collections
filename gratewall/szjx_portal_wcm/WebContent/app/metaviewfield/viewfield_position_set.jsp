<%--
/** Title:			Document_addedit.jsp
 *  Description:
 *		记录位置设置页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			TRS WCM 5.2
 *  Created:		2006-03-28 19:56:25
 *  Vesion:			1.0
 *  Last EditTime:	2006-03-28 / 2006-03-28
 *	Update Logs:
 *		TRS WCM 5.2@2006-03-28 产生此文件
 *
 *  Parameters:
 *		see viewfield_position_set.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>									 `
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewFields" %>
<%@ page import="com.trs.components.metadata.definition.IMetaDataDefMgr" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>

<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nViewFieldId = currRequestHelper.getInt("ObjectId", 0);
//5.获取填充页面的数据。
	String sFieldName ="";
	String sViewName ="";
	int nViewFieldOrder = 0;
	int nSize = 0;
	int nCurrId = 0;
	int nViewId = 0;
	int nMaxId = 0;
	int nMinId = 0;
	MetaViewField currViewField = MetaViewField.findById(nViewFieldId);
	if(currViewField == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("viewfield_position_set.jsp.field_not_found", "没有找到ID为[{0}]的字段！"),new int[]{nViewFieldId}));
	}
	sFieldName = currViewField.getPropertyAsString("FIELDNAME", "");
	nViewFieldOrder = currViewField.getPropertyAsInt("FIELDORDER", 0);
	nCurrId = currViewField.getId();

	MetaView oMetaView = MetaView.findById(currViewField.getViewId());
	if(oMetaView == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("viewfield_position_set.jsp.view_not_found", "没有找到ID为[{0}]的视图！"), new int[]{nViewFieldId}));
	}
	nViewId = oMetaView.getId(); 
	sViewName = oMetaView.getPropertyAsString("VIEWNAME", "");

	IMetaDataDefMgr m_oDataDefMgr = (IMetaDataDefMgr) DreamFactory
            .createObjectById("IMetaDataDefMgr");
	WCMFilter filter = new WCMFilter("", "", "fieldorder desc");
	MetaViewFields oMetaViewFields = m_oDataDefMgr.getViewFields(loginUser, oMetaView, filter );
	if(oMetaViewFields == null){
		oMetaViewFields = new MetaViewFields(loginUser);
	}

	nSize = oMetaViewFields.size();
	//系统默认按照fieldorder desc排序。
	MetaViewField oMetaViewFieldMax = (MetaViewField)oMetaViewFields.getAt(nSize-1);
	nMinId = oMetaViewFieldMax.getId();
	MetaViewField oMetaViewFieldMin = (MetaViewField)oMetaViewFields.getAt(0);
	nMaxId = oMetaViewFieldMin.getId();

//6.业务代码
	
//7.结束
	out.clear();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style>
	body{
		font-size:12px;
		overflow:auto;
	}
	div{
		width:100%;
		display:block;
		line-height:24px;
		height:24px;
	}
	SPAN{
		display:inline;
	}
	li{
		list-style-type: circle;
	}
	.num{
		font-weight:bold;
		color:red;
	}
</style>

<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/metaviewdata.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<!--FloatPanel Inner Start-->
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
<!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!--wcm-dialog end-->
<!--AJAX-->
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script>
	window.m_fpCfg = {
		m_arrCommands : [{
			cmd : 'viewfieldpositionset',
			name : wcm.LANG.METAVIEWDATA_1 || '确定'
		}],
		size : [400,120]
	};	
</script>
<script>
function viewfieldpositionset(){
	var frmData = $('frmData');
	var currId = <%=nCurrId%>;
	var currOrder = <%=nViewFieldOrder%>;
	var MaxOrder = <%=nSize%>;
	var arrIdAndOrder = (frmData.viewFieldOrder.value).split(",");
	var selectedOrder = parseInt(arrIdAndOrder[0]);
	var selectedId = parseInt(arrIdAndOrder[1]);
	var targetId ;
	var bPosition ;
	if(selectedOrder > MaxOrder ){
		targetId = <%=nMaxId%>;
		bPosition = 0;
	}		
	if(selectedOrder <= -1){
		targetId = <%=nMaxId%>;
		bPosition = 1;
	}else{
		targetId = selectedId;
		bPosition = 0;  	  
	}
	params = {fromId : currId, toId : targetId, position : bPosition};
	BasicDataHelper.call("wcm6_MetaDataDef", 'changeViewFieldOrder', params, true, function(transport, json){		
		notifyFPCallback();
		FloatPanel.close();
	});
	return false;
}



</script>

</head>

<body>  

<form  name="frmData" id="frmData" action="viewfield_position_set_dowith.jsp" onsubmit="return false;">
	<input type="hidden" name="currId" id="currId" value="<%=nCurrId%>">
	<input type="hidden" name="viewId" id="viewId" value="<%=nViewId%>">

	<table width="100%" cellspacing="1" cellpadding="2" style="marging-top:10px; font-size:12px">
		<tr>
			<td align="left">
				<%=CMyString.format(LocaleServer.getString("viewfield_position_set.jsp.adjust_order", "为字段<font color='blue'>[{0}]</font>调整顺序:"), new String[]{currViewField.getPropertyAsString("ANOTHERNAME", "")})%>
			</td> 
		</tr>
		<tr>
			<td>
				<div id="divBasicViewFieldOrder">
					<span class="spAttrClue" WCMAnt:param="viewfield_position_set.jsp.fontViewField">
						前一字段：
					</span>
					<span class="spAttrItem">
						<select name="viewFieldOrder" id="viewFieldOrder" style="width:170px;" _value="<%=currViewField.getPropertyAsInt("FIELDORDER", 0)+","+currViewField.getId()%>">
							<option value="-1" WCMAnt:param="viewfield_position_set.jsp.font">最前面</option>
							<%
								for (int i = 0, length = oMetaViewFields.size(); i < length; i++) {
									MetaViewField oMetaViewField = (MetaViewField)oMetaViewFields.getAt(i);
									if (oMetaViewField == null)
										continue;
							%>
								<option value=
								"<%=oMetaViewField.getPropertyAsInt("FIELDORDER", 0)+","+oMetaViewField.getId() %>">
									<%=oMetaViewField.getPropertyAsString("ANOTHERNAME", "")%>
								</option>
							<%
								}
							%>
						</select>
					</span>
				</div>
			</td>
		</tr>		
		<tr>
			<td align="center" height="5px">&nbsp; </td>
		<tr>
	</table>
</form>
<script>
Event.observe(window, 'load', function(){
	var dom = $("viewFieldOrder");
	var opts = dom.options;
	if(getParameter("objectid") == 0){
		dom.selectedIndex = (opts.length - 1);
		return;
	}
	var nViewFieldOrder = dom.getAttribute("_value");
	dom.value = nViewFieldOrder;
	dom.selectedIndex -= 1;
	if(dom.selectedIndex < 0){
		dom.selectedIndex = 0;
	}
	opts[dom.selectedIndex].setAttribute("value", nViewFieldOrder);
	if(opts.remove){//IE
		opts.remove(dom.selectedIndex + 1);
	}else{//Not IE
		dom.removeChild(opts[dom.selectedIndex + 1]);
	}
})
</script>
</body>
</html>