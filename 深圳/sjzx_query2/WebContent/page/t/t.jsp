<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<title>增加共享服务基础接口配置信息</title>
<link href="script/jquery-plugin-Selector/css/jquery.dataSelector.css" rel="stylesheet" type="text/css" />
<link href="script/jquery-plugin-tab/css/jquery.tabs.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery171.js"></script>
<script type="text/javascript" src="script/jquery-ui.custom.js"></script>
<script type="text/javascript" src="script/jquery-plugin-Selector/js/jquery.dataSelector.js"></script>
<script type="text/javascript" src="script/jquery-plugin-tab/jquery.tabs.js"></script>
<script type="text/javascript" src="script/jquery-plugin-data2table/js/jquery.tablet.js"></script>
<script type="text/javascript" src="script/public-func.js"></script>
<script type="text/javascript" src="script/gwssi-select.js"></script>
<script type="text/javascript" src="script/gwssi-grid.js"></script>
<script type="text/javascript" src="script/gwssi-form.js"></script>
<script type="text/javascript" src="script/share/share_interface.js"></script>
<script type="text/javascript" src="script/ye.js"></script>
<style type="text/css">
.sec_left{
}
</style>
</head>
<body>
<table border="0" cellpadding="0" cellspacing="0" width="95%" align="center" style="border-collapse:collapse;">
<tr><td><div style="width:100%;">
<dl class="tabs" id="tabs">
     <dt>
	        <a>配置查询范围</a>
	      	<a>配置查询条件</a>
	      	<a>预览</a>
     </dt>
     <dd><div>
      <table class="dd_table" border=1 cellpadding=3 cellspacing=0 width="100%" align="center">
		<tr>
		    <!-- <td width="120">选择业务系统：</td> -->
		    <td width="120">选择主题：</td>
			<td width="120">选择数据表：</td>
			<td width="50"></td>
			<td width="120">已选数据表：</td>
			<td width="120">请选择数据列</td>
			<td width="120">结果集</td>
			</tr>
			<tr>
				<!-- <td width="120">
					<div id="busiSystem_div"></div>
				</td> -->
				<td width="120">
					<div id="busiTopic_div"></div>
				</td>
				<td width="120">
					<div id="tbl_div"></div>
				</td>
				<td align="center" valign="middle" width="50">
				<!--  
	   			<button onclick="leftToRight()" > >&nbsp;</button> 
	   			<br/>
	   			<button onclick="leftToRightAll()" > &gt;&gt; </button> 
	   			<br/>
	   			-->
	   			
	 	  		<!-- <button onclick="rightToLeft()"> <&nbsp; </button> 
	   			<br/>
	   			<button onclick="rightToLeftAll()" > &lt;&lt; </button> --> 
	   			</td>
	   			<td width="120"><div id="checkTbl_div"></div></td>
	   			<td width="120"><div id="tab1_col_all_div"></div></td>
	   			<td width="120"  valign="top" ><div id="tab1_col_selected_div"></div></td>
			</tr>
		</table>
		<br />
		<div id="relation_div">
			<table class="dd_table" border=1 cellpadding=3 cellspacing=0 width="100%" align="center">
			<tr><td width="15%">表选择</td><td width="25%"><div id="leftTbl_div"></div></td><td colspan="2"></td>
				<td width="15%">表选择</td><td width="25%"><div id="rightTbl_div"></div></td>
			</tr>
			<tr><td width="15%">数据项选择</td><td width="25%"><div id="leftItem_div"></div></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;条件</td>
				<td><div id="condition_paren"></div></td>
				<td width="15%">数据项选择</td><td width="25%"><div id="rightItem_div"></div>
			</tr>
			<tr><td colspan="6" align="right"><input type="button" value="添加关联条件" onclick="addRelation();"/>&nbsp;&nbsp;</td></tr>
			<tr><td colspan="6"><div id="selected_columns"></div></td></tr>
			</table>
		</div><br /></div>
 </dd>
  <dd><div>
  <table class="dd_table" border=1 cellpadding=3 cellspacing=0 width="100%" align="center">
		<tr align="center">
		   <td width="5%">逻辑条件</td>
		   <td width="5%">括弧</td>
		   <td>选择表</td>
		   <td>选择数据项</td>
		   <td>条件</td>
		   <td>值</td>
		   <td>括弧</td>
		 </tr>
		 <tr>
		   <td><div id="condition_cond"></div></td>
		   <td><div id="condition_left"></div></td>
           <td><div id="condition_div"></div></td>
           <td><div id="condition_item_div"></div></td>
           <td><div id="condition_middle"></div></td>
	       <td onmouseover="showSelectedValue(this);">
	       <input type="text" name="param_value" id="paramValue" /> 
	       <select id='pvs' style='display:none;width:100px;'></select> 
	       </td>
	       <td><div id="condition_right"></div></td>
	     </tr>
     <tr><td colspan="7" align="right"><input type="button" value="添加查询条件" onclick="addParam();"/>&nbsp;&nbsp;</td></tr>
     <tr><td colspan="7"><div id="table_param"></div></td></tr>
	</table><br /></div>
  </dd>
  <dd>  <div>
   <table class="dd_table" border=1 cellpadding=3 cellspacing=0 width="100%" align="center">
		<tr><td width="15%">选择数据表为:</td><td align="left"><div id="cond_checkTable">无</div></td></tr>
		<tr><td>数据表关联关系:</td><td><div id="cond_condition">无</div></td></tr>
		<tr><td>数据表查询条件:</td><td><div id="cond_param">无</div></td></tr>
		<tr><td>SQL语句:</td><td><div id="sql_last"></div></td></tr>
		<tr><td colspan="2"><a href="javascript:preview();">点击查看预览</a></td></tr>
		<tr><td colspan="2"><iframe id="preview_data" name="preview_data" src="" width="100%" style="width: 100%;"></iframe></td></tr>
	</table><br /></div>
  </dd>
</dl>
		</div>
		</td>
	</tr>
</table>


<form id="form1" name="form1" action="" method="post" target="preview_data">
  <input type="hidden" name="tableIds" id="tableIds"/>
  <input type="hidden" name="table_sql" id="table_sql"/>
  <input type="hidden" name="buildSql" id="buildSql"/>
</form>

</body>
</html>
