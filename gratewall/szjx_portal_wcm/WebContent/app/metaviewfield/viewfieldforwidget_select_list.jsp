<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.metadata.definition.MetaDBField" %>
<%@ page import="com.trs.components.metadata.definition.MetaDBFields" %>
<%@ page import="com.trs.components.metadata.definition.MetaDBTable" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewFields" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.metadata.right.AuthServerMgr" %>
<%@ page import="com.trs.components.metadata.MetaDataConstants" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%
try{
%>
<%@include file="../include/public_processor.jsp"%>
<%
	//校验当前视图id是否大于0
	String sViewId = request.getParameter("ViewId");
	if(CMyString.isEmpty(sViewId)){
		throw new WCMException("必须传入视图ID!");
	}
	int nViewId = Integer.parseInt(sViewId);
	if(nViewId == 0){
		throw new WCMException("传入的参数视图Id必须大于0!");
	}
	MetaView view = MetaView.findById(nViewId);
	if(view == null){
		throw new WCMException("没有找到ID为[" + nViewId + "]的视图!");
	}
	//调用服务，获取字段集合
	MetaViewFields oMetaDBFields = (MetaViewFields) processor.excute("wcm61_metaviewfield", "queryViewFieldInfos");
%>
<%out.clear();%>
<?xml encoding="UTF-8" version="1.0"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>视图字段选择列表</title>
	<link rel="stylesheet" type="text/css" href="../../app/js/resource/widget.css"/>
    <link rel="stylesheet" type="text/css" href="viewfield_select_list.css">
</head>
<body>
<form method="post" action="">
<script src="../../app/js/easyversion/cssrender.js"></script>
<div class="layout-container" id='divcontent'>
	<!--检索框-->
	<div style=" overflow: hidden;">
		<div class="search" id="search" style="padding-left:145px; float: right;"></div>
	</div>
	<!--列表-->
	<div id='wcm_table_grid' class="layout_center_container table_grid_data">
		<table id="table_cnt"  cellspacing=0 border="0" cellpadding=0>
			<tbody id="table_tbody">
				<%
					//5. 遍历生成表现
						//准备好要显示的数据
					String sType ="";
					int nNum = oMetaDBFields.size();
					//每行显示4列，定义一个中间变量
					int nColum = 0;
					for (int i = 1; i <= nNum; i++) {
						try{
							MetaViewField metaViewField = (MetaViewField)oMetaDBFields.getAt(i - 1);
							if (metaViewField == null)
								continue;
							int nRowId =metaViewField.getId();
							String sCruser = metaViewField.getPropertyAsString("cruser");
							String sCrtime = metaViewField.getPropertyAsDateTime("crtime").toString("MM-dd hh:mm");
							String sAnotherName = CMyString.transDisplay(metaViewField.getAnotherName());
							String sName = CMyString.transDisplay(metaViewField.getName());

							if(nColum%4==0){
								out.println("<tr>");
							}	
							nColum++;
				%>
							<td  width="25%" class="nameField" title="英文名称: <%=sName%>&#13;创建者: <%=sCruser%>&#13;创建时间：<%=sCrtime%> ">
								<div class="sp_name" _id="<%=nRowId%>">
									<span><%=(i)%></span>.&nbsp;&nbsp;
									<input type="radio" id="chk_<%=nRowId%>" _id="<%=nRowId%>" name="fieldName" _chkName="<%=sName%>" _chkAnotherName="<%=sAnotherName%>" value="<%=CMyString.transDisplay(sName)%>" />
									<label for="chk_<%=nRowId%>" ><%=sAnotherName %></label>
								</div>
							</td>
				<%
							if(nColum%4==0){
								out.println("</tr>");
							}
						}catch(Exception ex){
								ex.printStackTrace();
						}//end try
					}//end for
					if(nNum == 0){
				%>
						<tr><td colspan="4" class="no_object_found" WCMAnt:param="list.NoObjectFound">不好意思, 没有找到符合条件的对象!</td></tr>
				<%
					}
				%>
			</tbody>
		</table>
	</div>
</div>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/easyversion/extrender.js"></script>
<script src="../../app/js/easyversion/elementmore.js"></script>
<script src="../../app/js/source/wcmlib/ListQuery.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js" WCMAnt:locale="../../app/js/source/wcmlib/com.trs.validator/lang/$locale$.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<!--wcm-crashboard start-->
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<!--wcm-crashboard end-->
<script src="viewfieldforwidget_select_list.js"></script>
</form>
</body>
</html>
<%
}catch(Throwable tx){	
	throw new WCMException("viewfield_select_query.jsp运行期异常!", tx);
}
%>