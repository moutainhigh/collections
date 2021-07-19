<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>
<%@ page import="com.trs.components.common.publish.widget.Layout" %>
<%@ page import="com.trs.components.common.publish.widget.SpecialAuthServer" %>

<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
	// 1.获取参数 
	int nLayoutId  = currRequestHelper.getInt("LayoutId", 0);
	Layout currLayout = null;
	if(nLayoutId>0){
		currLayout = Layout.findById(nLayoutId);
		if(currLayout==null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("layout_addedit.jsp.fail2get_layout", "获取ID为[{0}]的布局失败!"), new int[]{nLayoutId}));
		}
		if(!currLayout.canEdit(loginUser)){
			throw new WCMException(ExceptionNumber.ERR_OBJ_LOCKED, CMyString.format(LocaleServer.getString("layout_addedit.jsp.locked", "当前对象被[{0}]锁定，您不能修改!"),  new Object[]{currLayout.getLockerUser()}));
		}
	}else{
		currLayout = Layout.createNewInstance();
	}
	// 2.权限校验
	if (!SpecialAuthServer.hasRight(loginUser, currLayout,SpecialAuthServer.WIDGET_ADD))
		throw new WCMException(LocaleServer.getString("layout_addedit.jsp.label.have_no_right", "对不起，您没有修改和保存布局的权限！"));
	// 3.业务处理
	out.clear();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
 <head>
  <title WCMAnt:param="layout_addedit.jsp.title">布局新建修改页面</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="Author" content="CH">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
	<link href="layout_addedit.css" rel="stylesheet" type="text/css" />
  <!--基础的js-->
	<script src="../../js/easyversion/lightbase.js"></script>
	<script src="../../js/easyversion/extrender.js"></script>
	<script src="../../js/easyversion/elementmore.js"></script>
	<script src="../js/adapter4Top.js"></script>
	<script src="../../js/easyversion/ajax.js"></script>
	<script src="../../js/easyversion/lockutil.js"></script>
	<!--validator start-->
	<script src="../../js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
	<script src="../../js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
	<script src="../../js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
	<script src="../../js/source/wcmlib/com.trs.validator/Validator.js"></script>
	<link href="../../js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
	<!--validator end-->
  <!--引入的JS文件    end-->
<script src="layout_addedit.js"></script>
  <script language="javascript">
  <!--
	var ratioText = "px";
	var ratio ="<%=currLayout.getRatio()!=null?currLayout.getRatio():""%>";
	var RATIO_TYPE_FIXED = "<%=Layout.RATIO_TYPE_FIXED%>";
	var RATIO_TYPE_PERCENTAGE = "<%=Layout.RATIO_TYPE_PERCENTAGE%>";
	var nLayoutId = <%=nLayoutId%>;
	var nObjType = <%=currLayout.getWCMType()%>;
	var RatioType = <%=currLayout.getRatioType()%>;
	var Column = <%=currLayout.getColumns()%>;
	var Name = "<%=(currLayout.getName()!=null)?CMyString.filterForJs(currLayout.getName()):""%>";
	var pageWidth;
  //-->
  </script>
 </head>

 <body>
 <form action="" name="postform" >
	<input type="hidden" name="Ratio" value=""/>
	<table border="0" cellspacing="0" cellpadding="0"  style="border:1px solid #acddfd;margin:0 auto;">
	<tbody>
		<tr>
			<td class="name" WCMAnt:param="layout_addedit.jsp.layout_name">布局名称：</td>
			<td>
				<input type="text" name="LayoutName" id ="LayoutName" value="" validation_desc="布局名称" validation="type:string,required:0,max_len:20,desc:布局名称" /><span style="color:green" WCMAnt:param="layout_addedit.jsp.maxlength"> 最大长度为20</span>
			</td>
		</tr>
		<tr>
			<td class="name" WCMAnt:param="layout_addedit.jsp.columns">列数：</td>
			<td><select name="Columns" id="Columns" onchange="changeColumns(this.value)">
			<%
				for(int i=1;i<=Layout.MAX_COLUMNS;i++){
			%>
					<option value="<%=i%>"><%=i%></option>
			<%}%>
			</select></td>
		</tr>
		<tr>
			<td class="name" WCMAnt:param="layout_addedit.jsp.percentage_type">比例类型：</td>
			<td>
				<select name="RatioType" id="RatioType" onchange="changeRatioType(this.value)">
				<option value="<%=Layout.RATIO_TYPE_FIXED%>" WCMAnt:param="layout_addedit.jsp.fixed_ratio">固定比</option>
				<option value="<%=Layout.RATIO_TYPE_PERCENTAGE%>" WCMAnt:param="layout_addedit.jsp.percentage_ratio">百分比</option>
				</select>
			</td>
		</tr>
	
		<tr class='ratio'>
			<td class="name" WCMAnt:param="layout_addedit.jsp.each_columns_ratio">各列比例：</td>
			<td id = "ratiovalue">
			</td>
		</tr>
		<tr>
			<td class="name"></td>
			<td class='desc' width="350px" WCMAnt:param="layout_addedit.jsp.introduction">
				说明：各列比例值只能输入整数且不允许为空，若为自适应列，请输入*，有且只能有一列为自适应列。
			</td>
		</tr>
	</tbody>
	</table>
</form>
 </body>
</html>