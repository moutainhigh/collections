<%@ page contentType="text/html; charset=GBK" %>
<%@page import="cn.gwssi.common.context.DataBus"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="800" height="450">
<head>
<title>增加共享服务基础接口配置信息</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery-ui.custom.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-Selector/js/jquery.dataSelector.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-tab/jquery.tabs.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-data2table/js/jquery.tablet.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/share/share_interface.js"></script>
<link href="<%=request.getContextPath()%>/script/jquery-plugin-Selector/css/jquery.dataSelector.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/script/jquery-plugin-tab/css/jquery.tabs.css" rel="stylesheet" type="text/css" />
<%
DataBus db = (DataBus)request.getAttribute("freeze-databus");

%>
<style type="text/css">
.sec_left{
}
</style>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存共享服务-基础接口表' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存共享服务-基础接口表' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存共享服务-基础接口表' );	// /txn401001.do
}

// 返 回
function func_record_goBack()
{
	window.close();
	//goBack();	// /txn401001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
   var condition=eval('(<%=db.getValue("tableCondition")%>)');
   var cond_html="";
   for(var i=0;i<condition.length;i++){
     var cond=condition[i];
     cond_html+="["+cond.leftTable.name_cn+"] 的 "+cond.leftColumn.name_cn+" <span style='color:red'> "+cond.middleParen+" </span>";
     cond_html+="["+cond.rightTable.name_cn+"] 的 "+cond.rightColumn.name_cn+"<br/>";
   }
   document.getElementById('span_record:table_condition').innerHTML=cond_html;
   var param=eval('(<%=db.getValue("tableParam")%>)');
   var param_html="";
   for(var i=0;i<param.length;i++){
     var parm=param[i];
     param_html+=parm.cond+" ["+parm.leftTable.name_cn+"] 的 "+parm.leftColumn.name_cn+" <span style='color:red'> "+parm.middleParen.value+" </span>";
     param_html+=parm.paramValue+"<br/>";
   }
   document.getElementById('span_record:table_param').innerHTML=param_html;
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查看基础接口配置信息"/>
<freeze:errors/>

<freeze:form action="/txn401002">
<freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="interface_id" caption="接口ID" style="width:95%"/>
  </freeze:frame>
<freeze:block property="record" caption="查看接口基本信息" width="95%">
  <freeze:hidden property="interface_id" caption="接口ID" datatype="string" maxlength="32" style="width:95%"/>
  <freeze:cell property="interface_name" caption="接口名称" datatype="string"  style="width:75%" />
  <freeze:cell property="created_time" caption="创建时间" datatype="string"  style="width:75%" />
  <freeze:cell property="interface_description" caption="接口说明"  style="width:75%" colspan="2"/>
  <freeze:cell property="crename" caption="创建人" datatype="string"  style="width:95%"/>
  <freeze:cell property="cretime" caption="创建时间" datatype="string"  style="width:95%"/>
  <freeze:cell property="modname" caption="最后修改人" datatype="string"  style="width:95%"/>
  <freeze:cell property="modtime" caption="最后修改时间" datatype="string"  style="width:95%"/>
  
  <freeze:hidden property="table_code" caption="接口表名称串" datatype="string"/>
  <freeze:hidden property="condition" caption="关联条件" datatype="string"/>
  <freeze:hidden property="param" caption="查询条件" datatype="string"/>
  <freeze:hidden property="interface_state" caption="接口状态" datatype="string"/>
  <freeze:hidden property="is_markup" caption="是否有效" datatype="string"/>
  <freeze:hidden property="creator_id" caption="创建人ID" datatype="string"/>
</freeze:block>
<br/>
<freeze:block property="record" caption="查看接口配置信息" width="95%">
  <freeze:hidden property="interface_id" caption="接口ID" datatype="string" maxlength="32" style="width:95%"/>
  <freeze:cell property="table_name_cn" caption="接口所选数据表" datatype="string"  style="width:75%" colspan="2"/>
  <freeze:cell property="table_condition" caption="接口关联条件" datatype="string"  style="width:75%" colspan="2"/>
  <freeze:cell property="table_param" caption="接口查询条件" datatype="string"  style="width:75%" colspan="2"/>
  <freeze:cell property="sql" caption="接口SQL" datatype="string" colspan="2" />
</freeze:block>
<Br/>
<p align="center" class="print-menu">
    	<table cellspacing='0' cellpadding='0' class='button_table'>
    	<tr><td class='btn_left'></td>
    	<td><input type="hidden" type="button" name="record_goBackNoUpdate" value="关 闭" class="menu" onclick="func_record_goBack()" />
    	<div class="btn_cancel" onclick="func_record_goBack()"></div>
    	</td><td class='btn_right'></td></tr>
    	</table>
    </p>
</freeze:form>
</freeze:body>
</freeze:html>
