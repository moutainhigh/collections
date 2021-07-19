<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<%@ page import="com.trs.components.common.publish.widget.Layout" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
	// 1.获取参数 
	int nRatioType  = currRequestHelper.getInt("RatioType", Layout.RATIO_TYPE_FIXED);
	String sRatio  = currRequestHelper.getString("Ratio");
	int pageWidth  = currRequestHelper.getInt("pageWidth",0);
	// 2.权限校验

	// 3.业务处理
	out.clear();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
 <head>
  <title>布局新建修改页面</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="Author" content="CH">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
   <link href="layout_addedit.css" rel="stylesheet" type="text/css" />
  <!--基础的js-->
	<script src="../../js/easyversion/lightbase.js"></script>
	<script src="../../js/source/wcmlib/WCMConstants.js"></script>
	<script src="../../js/easyversion/extrender.js"></script>
	<script src="../../js/easyversion/elementmore.js"></script>
	<script src="../js/adapter4Top.js"></script>
	<!--validator start-->
	<script src="../../js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
	<script src="../../js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
	<script src="../../js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
	<script src="../../js/source/wcmlib/com.trs.validator/Validator.js"></script>
	<link href="../../js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
	<!--validator end-->
	<!--使用ajax发送请求的js-->
	<script src="../../js/easyversion/ajax.js"></script>
	<script src="../../js/easyversion/basicdatahelper.js"></script>
	<script src="../../js/easyversion/web2frameadapter.js"></script>
<!--引入的JS文件    end-->
<script src="layout_addedit.js"></script>

  <script language="javascript">
	<!--
		window.m_cbCfg = {
			btns : [
				{
					text : '确定',
					cmd : function(){
						return onOk();
					}
				},
				{
					extraCls : 'wcm-btn-close',
					text : '取消'
				}
			]
		};		
	//-->
	</script>
  <script language="javascript">
  <!--
	var ratioText = "px";
	var ratio ="<%=CMyString.filterForJs(sRatio)%>";
	var RatioType = <%=nRatioType%>;
	var Column = ratio.split(":").length;
	var RATIO_TYPE_FIXED = "<%=Layout.RATIO_TYPE_FIXED%>";
	var RATIO_TYPE_PERCENTAGE = "<%=Layout.RATIO_TYPE_PERCENTAGE%>";
	var pageWidth = "<%=pageWidth%>"
  //-->
  </script>
 </head>

 <body>
 <form action="" name="postform" >
	<input type="hidden" name="Ratio" value=""/>
	<table border="0" cellspacing="0" cellpadding="0"  style="border:1px solid #acddfd;margin:0 auto;">
	<tbody>
		<tr>
			<td class="name" WCMAnt:param="layout_modify.jsp.columns">列数：</td>
			<td><select name="Columns" id="Columns" onchange="changeColumns(this.value)">
			<%
				for(int i=1;i<=Layout.MAX_COLUMNS;i++){
			%>
					<option value="<%=i%>"><%=i%></option>
			<%}%>
			</select></td>
		</tr>
		<tr>
			<td class="name" WCMAnt:param="layout_modify.jsp.ratio_type">比例类型：</td>
			<td>
				<select name="RatioType" id="RatioType" onchange="changeRatioType(this.value)">
				<option value="<%=Layout.RATIO_TYPE_FIXED%>" WCMAnt:param="layout_modify.jsp.fixed_ratio">固定比</option>
				<option value="<%=Layout.RATIO_TYPE_PERCENTAGE%>" WCMAnt:param="layout_modify.jsp.percentage_ratio">百分比</option>
				</select>
			</td>
		</tr>
	
		<tr class='ratio'>
			<td class="name" WCMAnt:param="layout_modify.jsp.each_column_ratio">各列比例：</td>
			<td id = "ratiovalue">
			</td>
		</tr>
		<tr>
			<td class="name"></td>
			<td class='desc' width="350px" WCMAnt:param="layout_modify.jsp.introduction"> 
				说明：各列比例值只能输入整数且不允许为空，若为自适应列，请输入*，有且只能有一列为自适应列。
			</td>
		</tr>
	</tbody>
	</table>
</form>
 </body>
</html>