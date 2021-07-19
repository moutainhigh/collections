<%@page import="cn.gwssi.common.dao.resource.PublicResource"%>
<%@page import="cn.gwssi.common.dao.resource.code.CodeMap"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>

<script src="<%=request.getContextPath() %> /script/common/js/validator.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-data2table/js/jquery.tablet.js"></script>
<link href="<%=request.getContextPath()%>/script/jquery-plugin-Selector/css/jquery.dataSelector.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %> /script/common/js/validator.js"></script>
<freeze:html width="1000" height="700">
<head>
<title>修改采集数据表信息</title>
<style type="text/css">
#dataitem_tb table{
	width: 95%;
}
</style>
</head>
<%
DataBus context = (DataBus) request.getAttribute("freeze-databus");
if(StringUtils.isNotBlank(context.getValue("jsondata"))){
    out.println("<script>var jsondata=eval('('+'"+context.getValue("jsondata")+"'+')');</script>");
}
%>
<script language="javascript">
<%
TxnContext tbTypeContext = new TxnContext();
CodeMap codeMap = PublicResource.getCodeFactory();
Recordset rs = codeMap.lookup(tbTypeContext, "资源管理_数据项类型");
out.print("var dataitem_types = new Array; ");
for(int ii=0; ii<rs.size(); ii++){
	out.println("dataitem_types.push({\"key\": \""+rs.get(ii).get("codevalue")
		+"\", \"title\": \""+rs.get(ii).get("codename")+"\"});");
}
%>
<%
TxnContext codeTbContext = new TxnContext();
rs = codeMap.lookup(codeTbContext, "资源管理_对应代码表");
out.print("var code_table_list = new Array;");
for(int ii=0; ii<rs.size(); ii++){
	out.println("code_table_list.push({\"key\": \""+rs.get(ii).get("codevalue")
		+"\", \"title\": \""+rs.get(ii).get("codename")+"\"});");
}
%>
// 生成表

	function func_record_createTable() {
		var collect_table_id = getFormFieldValue('record:collect_table_id');
		//var ids = getFormAllFieldValues("dataItem:dataitem_name_en");
		var ids = new Array;
		$('#dataitem_tb').find("tr:gt(0)").each(function() {
			ids.push($(this).find("td:eq(1)").text());
		})
		var key = new Array;
		$('#dataitem_tb').find("tr:gt(0)").each(function() {
			key.push($(this).find("td:eq(5) select option:selected").val());
		})

		var num = 0;
		if (ids != null) {
			for (i = 0; i < ids.length; i++) {
				if (key[i] == '1') {
					num = num + 1;
				}
			}
		}
		if (num > 1) {
			alert("只能有一个数据项是主键!");
			clickFlag = 0;
			return false;
		}

		var page = new pageDefine("/txn20201009.ajax", "生成采集数据表!");
		page.addParameter("primary-key:collect_table_id", "primary-key:collect_table_id");
		page.addParameter("record:table_name_en", "primary-key:table_name_en");
		page.callAjaxService('creatTableCheck');
	}
	function creatTableCheck(errCode, errDesc, xmlResults) {
		is_name_used = 1;
		if (errCode != '000000') {
			alert('处理错误[' + errCode + ']==>' + errDesc);
			return;
		}
		is_name_used = _getXmlNodeValues(xmlResults, 'record:name_nums');
		if (is_name_used > 0) {

			if (confirm("采集库已存在此数据表名称且数据表里已有数据，是否继续生成该数据表?")) {
				var page = new pageDefine("/txn20201008.ajax", "生成采集数据表!");
				page.addParameter("record:collect_table_id",
						"record:collect_table_id");
				page.addValue(is_name_used, "record:name_nums");
				page.callAjaxService('creatTable');
			}

			//alert("采集库已存在此数据表名称且数据表里已有数据,不能再生成该表!");
			//return false;

		} else if (is_name_used == -1) {

			if (confirm("采集库已存在此数据表名称,但数据表里没有数据，是否继续生成该数据表?")) {
				var page = new pageDefine("/txn20201008.ajax", "生成采集数据表!");
				page.addParameter("record:collect_table_id",
						"record:collect_table_id");
				page.addValue(is_name_used, "record:name_nums");
				page.callAjaxService('creatTable');
			}

			//alert("采集库已存在此数据表,不能再生成该表!");
			//return false;
		} else {
			if (confirm("是否生成数据表?")) {
				var page = new pageDefine("/txn20201008.ajax", "生成采集数据表!");
				page.addParameter("record:collect_table_id",
						"record:collect_table_id");
				page.addValue(is_name_used, "record:name_nums");
				page.callAjaxService('creatTable');
			}
		}
	}
	function creatTable(errCode, errDesc, xmlResults) {
		is_name_used = 1;
		if (errCode != '000000') {
			alert('处理错误[' + errCode + ']==>' + errDesc);
			return;
		} else {
			alert("生成表成功!");
			goBackWithUpdate();
		}
	}
	function func_record_goBack()
	{

		//var page = new pageDefine( "/txn20201001.do", "查询采集数据表");
		//page.updateRecord();
		goBackWithUpdate();
	}
	// 请在这里添加，页面加载完成后的用户初始化操作
	function __userInitPage() {

	}

	_browse.execute('__userInitPage()');
</script>
<freeze:body>
<freeze:title caption="修改采集数据表信息"/>
<freeze:errors/>

<freeze:form action="/txn20201014">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="collect_table_id" caption="采集数据表ID" style="width:95%"/>
  </freeze:frame>
 <div style="width:95%;margin-left:20px" >
  <table style="width:65%"  >
   <tr>
   
    <td width="30%">
   
    	<table   cellpadding="0" cellspacing="0">
    		<tr>
    			<td style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;" width="2" height="25" valign="middle"></td>
    			<td height="25" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
    				<div style="background:url(<%=request.getContextPath()%>/images/xzcjbg/icon_bg.png) left 50% no-repeat; width:20px; display: inline;"></div>
					第一步,录入采集表基本信息</td>
    			<td width="5" height="25" valign="middle" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_r.png') left 50% no-repeat;"></td>
    		</tr>
    	</table>
    </td>
    <td width="30%" >
    	<table   cellpadding="0" cellspacing="0">
    		<tr>
    			<td style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;" width="2" height="25" valign="middle"></td>
    			<td height="25" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
    				<div style="background:url(<%=request.getContextPath()%>/images/xzcjbg/icon_bg.png) left 50% no-repeat; width:20px; display: inline;"></div>
					    第二步,录入采集表字段信息</td>
    			<td width="5" height="25" valign="middle" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_r.png') left 50% no-repeat;"></td>
    		</tr>
    	</table>
    </td>
    <td width="30%">
 		<table   cellpadding="0" cellspacing="0">
    		<tr>
    			<td style="background:url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_l.png') left 50% no-repeat;" width="2" height="25" valign="middle"></td>
    			<td height="25" style="color:white;background:url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_c.png') left 50% repeat-x;">
    				<div style="background:url(<%=request.getContextPath()%>/images/xzcjbg/icon_bg.png) left 50% no-repeat; width:20px; display: inline;"></div>
					第三步,预览并生成表</td>
    			<td width="5" height="25" valign="middle" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_r.png') left 50% no-repeat;"></td>
    		</tr>
    	</table>   
     </td>
     
   </tr> 
  </table>
  </div>
<freeze:block property="record" caption="采集数据表信息" width="95%">
      <freeze:hidden property="collect_table_id" caption="采集数据表ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:cell property="service_targets_id" caption="所属服务对象" show="name"  valueset="资源管理_服务对象名称"   style="width:95%"/>
      <freeze:cell property="table_name_en" caption="表名称" datatype="string" style="width:95%"/>
      <freeze:cell property="table_name_cn" caption="表中文名称" datatype="string" style="width:95%"/>
      <freeze:cell property="table_type" caption="表类型" show="name" valueset="资源管理_表类型"  style="width:95%"/>
      <freeze:cell property="table_desc" caption="表描述"   style="width:98%"/>
 </freeze:block>
 <br>
  <freeze:grid property="dataItem" caption="采集数据项列表" keylist="collect_dataitem_id" multiselect="false" checkbox="false" width="95%"  fixrow="false" >
       <freeze:hidden property="collect_dataitem_id" caption="采集数据项ID"  />
      <freeze:hidden property="collect_table_id" caption="采集数据表ID"  />
      <freeze:cell property="index" caption="序号"  style="width:6%" align="center" />
      <freeze:cell property="dataitem_name_en" caption="数据项名称" style="width:12%" />
      <freeze:cell property="dataitem_name_cn" caption="中文名称" style="width:12%" />
      <freeze:cell property="dataitem_type" caption="数据项类型"  show="name" valueset="资源管理_数据项类型" style="width:12%" />
      <freeze:cell property="dataitem_long" caption="数据项长度" style="width:10%" />
      <freeze:cell property="is_key" caption="是否主键" valueset="资源管理_是否主键" style="width:10%" />
      <freeze:hidden property="is_code_table" caption="是否代码表" style="width:10%" />
      <freeze:cell property="code_table" caption="对应代码表" valueset="资源管理_对应代码表" style="width:12%" />
      <freeze:hidden property="dataitem_long_desc" caption="数据项描述" style="width:20%"  />
      <freeze:hidden property="is_markup" caption="有效标记" style="width:10%" />
      <freeze:hidden property="creator_id" caption="创建人ID" style="width:12%" />
      <freeze:hidden property="created_time" caption="创建时间" style="width:12%" />
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" style="width:12%" />
      <freeze:hidden property="last_modify_time" caption="最后修改时间" style="width:12%" />
  </freeze:grid> 
  <table align='center' cellpadding=0 cellspacing=0 width="95%" >
  <tr><td>
  
  
  
  <td align="center" height="50" valign="bottom">
  <div class="btn_gen"  onclick="func_record_createTable();"></div>
&nbsp;&nbsp;&nbsp;&nbsp;
<div class="btn_cancel"  onclick="func_record_goBack();"></div>
  </td></tr>
  
  
  </table>

  
</freeze:form>
</freeze:body>
</freeze:html>
