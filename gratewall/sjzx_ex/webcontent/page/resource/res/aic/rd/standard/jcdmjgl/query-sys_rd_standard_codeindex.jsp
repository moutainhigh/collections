<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询代码集列表</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// 增加代码集
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_standard_codeindex.jsp", "增加代码集", "modal" );
	page.addRecord();
}

// 修改代码集
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn7000404.do", "修改代码集", "modal" );
	page.addParameter( "record:sys_rd_standar_codeindex", "primary-key:sys_rd_standar_codeindex" );
	page.updateRecord();
}
// 删除代码集
function func_record_deleteRecord()
{ 
     var page = new pageDefine( "/txn7000411.ajax", "查询基础代码表" ); 
     page.addParameter("record:sys_rd_standar_codeindex", "select-key:sys_rd_standar_codeindex"); 
	 page.callAjaxService('doCallback');
	 page.callAjaxService( null, true );
}

function doCallback(errorCode, errDesc, xmlResults) 
{ 
    //被调用函数，读取使用Ajax技术得到的数据
	if ( errorCode != '000000' ) {
		alert("查询记录失败，请检查后重试");
		return;
	} 
	var codeIndex = _getXmlNodeValues(xmlResults, 'record:sys_rd_standar_codeindex');
	
	if(codeIndex != ''){
		alert("请先删除代码集里的代码值！");
		return;
	}
	
	var page = new pageDefine( "/txn7000405.do", "删除基础代码集" );
	page.addParameter( "record:sys_rd_standar_codeindex", "primary-key:sys_rd_standar_codeindex" );
	page.deleteRecord( "是否删除选中的记录" );
	
}

// 查看代码集
function func_record_viewRecord()
{
	var page = new pageDefine( "/txn7000406.do", "查看代码集", "modal" );
	page.addParameter( "record:sys_rd_standar_codeindex", "primary-key:sys_rd_standar_codeindex" );
	
	page.viewRecord();
}

//查看代码-用于按钮
function func_record_codeRecord_button()
{
	var page = new pageDefine( "/txn7000411.do", "查看代码集");
	page.addParameter( "record:sys_rd_standar_codeindex", "select-key:sys_rd_standar_codeindex" );
	page.addParameter( "record:codeindex_name", "select-key:codeindex_name" );
	page.goPage();
}

// 查看代码
function func_record_codeRecord()
{
	if (event.srcElement.tagName.toUpperCase() == 'LABEL'){
		return;
	}
	if (event.srcElement.tagName.toUpperCase() == 'INPUT'){
		return;
	}
	if (event.srcElement.tagName.toUpperCase() == 'A'){
		return;
	}
	var page = new pageDefine( "/txn7000411.do", "查看代码集");
	page.addParameter( "record:sys_rd_standar_codeindex", "select-key:sys_rd_standar_codeindex" );
	page.addParameter( "record:codeindex_name", "select-key:codeindex_name" );
	page.goPage();
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{

}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询代码集列表"/>
<freeze:errors/>

<freeze:form action="/txn7000401">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="sys_rd_standar_codeindex" caption="代码集标识符" datatype="string" maxlength="255" style="width:90%"/>
       <freeze:text property="codeindex_name" caption="代码集名称" datatype="string" maxlength="255" style="width:90%"/>
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="查询代码集列表" keylist="sys_rd_standar_codeindex" width="95%" navbar="bottom" fixrow="false" onclick="func_record_codeRecord();">
      <freeze:button name="record_addRecord" caption="增加代码集" txncode="7000403" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改代码集" txncode="7000404" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除代码集" txncode="7000405" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:button name="record_viewRecord" caption="查看代码集" txncode="7000406" enablerule="1" hotkey="" align="right" onclick="func_record_viewRecord();"/>
      <freeze:button name="record_codeRecord" caption="基础代码" txncode="7000411" enablerule="1" hotkey="CODE" align="right" onclick="func_record_codeRecord_button();"/>
      <freeze:cell property="sys_rd_standar_codeindex" caption="代码集标识符" style="width:10%" />
      <freeze:cell property="codeindex_name" caption="代码集名称" style="width:26%" />
      <freeze:cell property="codeindex_category" caption="代码集类型" valueset="代码集类型" style="width:13%" />
      <freeze:cell property="representation" caption="表示" style="width:10%" />
      <freeze:cell property="standard_codeindex_version" caption="版本" style="width:13%" />
      <freeze:cell property="code_table" caption="代码表" style="width:22%" />
      <freeze:cell property="coding_methods" caption="编码方法" style="width:20%" visible="false" />
      <freeze:cell property="description" caption="说明" style="width:20%" visible="false" />
 
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
