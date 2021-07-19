<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template master-detail-1/master-table-query.jsp --%>
<freeze:html width="950" height="650">
<head>
<title>查询代码分项列表</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// 查询明细数据
function _loadDetail()
{
	_formSubmit( null, '查询明细数据... ...' );
}

// 返 回
function func_record_goBackNoUpdate()
{
	 goBackWithUpdate("/txn301011.do");	// /txn301051.do
}
// 返 回
function func_record_goBack()
{
	goBack();	// 
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var operationSpan = document.getElementsByName("span_record:operation");
	for (var i=0; i < operationSpan.length; i++){
		operationSpan[i].innerHTML = "<a onclick='setCurrentRowChecked(\"record\");func_record_updateRecord();' title='修改' href='#'><div class='edit'></div></a>";
	}		
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:errors/>

<freeze:form action="/txn3010111">

  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:hidden property="jc_dm_id" caption="代码ID" datatype="string" maxlength="36" style="width:95%"/>
      <freeze:text property="jcsjfx_dm" caption="分项代码" datatype="string"style="width:95%"/>
      <freeze:text property="jcsjfx_mc" caption="分项名称" datatype="string" style="width:95%"/>    
  </freeze:block>
  <br>
  <freeze:grid checkbox="false" rowselect="false" property="record" caption="代码分项列表" keylist="jcsjfx_id" width="95%" navbar="bottom" fixrow="false">
     <freeze:button name="record_goBack" caption="返回" hotkey="CLOSE" align="right" onclick="func_record_goBack();"/>
     <freeze:hidden property="sy_zt" caption="使用状态" style="width:30%" />
      <freeze:cell property="jcsjfx_id" caption="数据分项ID" style="width:10%" visible="false"/>
      <freeze:hidden property="jc_dm_id" caption="代码ID" style="width:16%" />
      <freeze:cell property="jcsjfx_dm" caption="数据分项代码" style="width:45%" />
      <freeze:cell property="jcsjfx_mc" caption="数据分项名称" style="width:50%" />
      <freeze:cell property="jcsjfx_cjm" caption="数据分项层级码" style="width:16%" visible="false" />
      <freeze:cell property="jcsjfx_fjd" caption="数据分项父节点代码" style="width:16%" visible="false" />
      <freeze:cell property="szcc" caption="所在层次" style="width:10%" visible="false" />
      <freeze:cell property="xssx" caption="显示顺序" style="width:10%" visible="false" />
      <freeze:cell property="sfmx" caption="是否明细" style="width:10%" visible="false" />
      <freeze:cell property="fx_ms" caption="分项描述" style="width:15%" visible="false" />
      <freeze:cell property="sy_zt" caption="使用状态" valueset="基础数据分项使用状态" style="width:30%" visible="false"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
