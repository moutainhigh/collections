<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:include href="/script/lib/jquery162.js"/>
<freeze:include href="/script/lib/colResizable-1.3.min.js"/> 
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询规范列表</title>
</head>
<%
String path=java.util.ResourceBundle.getBundle("app").getString("docFilePath");
%>
<script language="javascript">

// 增加规范
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_standard.jsp", "增加规范", "modal" );
	page.addParameter( "record:sys_rd_standard_id", "record:sys_rd_standard_id" );
	page.addRecord();
}

// 修改规范
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn7000204.do", "修改规范", "modal" );
	page.addParameter( "record:sys_rd_standard_id", "primary-key:sys_rd_standard_id" );
	page.updateRecord();
}

// 删除代码集
function func_record_deleteRecord()
{ 
     var page = new pageDefine( "/txn7000211.ajax", "查询实体信息" ); 
     page.addParameter("record:sys_rd_standard_id", "select-key:sys_rd_standard_id"); 
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
	var codeIndex = _getXmlNodeValues(xmlResults, 'record:sys_rd_standard_id');
	
	if(codeIndex != ''){
		alert("请先删除代码集里的代码值！");
		return;
	}
	
	var page = new pageDefine( "/txn7000205.do", "删除规范" );
	page.addParameter( "record:sys_rd_standard_id", "primary-key:sys_rd_standard_id" );
	page.deleteRecord( "是否删除选中的记录" );
	
}
// 查询规范
function func_record_viewRecord()
{
	var page = new pageDefine( "/txn7000206.do", "查看规范", "modal" ,750,200);
	page.addParameter( "record:sys_rd_standard_id", "primary-key:sys_rd_standard_id" );
	page.viewRecord();
}
// 查询实体-button专用
function func_record_stxxRecord_button()
{
	var page = new pageDefine( "/txn7000211.do", "实体信息");
	page.addParameter( "record:sys_rd_standard_id", "select-key:sys_rd_standard_id" );
	page.addParameter( "record:standard_name", "select-key:standard_name" );
	
	page.goPage();
}

// 查询实体
function func_record_stxxRecord()
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
	var page = new pageDefine( "/txn7000211.do", "实体信息");
	page.addParameter( "record:sys_rd_standard_id", "select-key:sys_rd_standard_id" );
	page.addParameter( "record:standard_name", "select-key:standard_name" );
	
	page.goPage();
}


// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	//   $("table #record").colResizable();
	var rootPath = '<%=request.getContextPath()%>';
	var paths = getFormAllFieldValues("record:file_path");
	var filenames = getFormAllFieldValues("record:file_name");
	var standard_names = getFormAllFieldValues("record:standard_name");
	for(var i=0;i<standard_names.length;i++){
		var url = paths[i];
		var filename = filenames[i];
		var rname=encodeURI(filename);
  	   // var url = "/downloadFile?file=<%=path%>"+url+"&&fileName="+rname;
  	    var url = "/downloadFile?file="+url+"&&fileName="+rname;
  	    var ihtml ="<a title='下载' href='"+url+"' ;>"+filename+"</a>";
	    document.getElementsByName("span_record:file_name")[i].innerHTML = ihtml;
	}
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询规范列表"/>
<freeze:errors/>

<freeze:form action="/txn7000201">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="standard_name" caption="规范名称" datatype="string" maxlength="100" style="width:36.5%" colspan="2" />
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="查询规范列表" keylist="sys_rd_standard_id" width="95%" navbar="bottom" fixrow="false" onclick="func_record_stxxRecord();">
      <freeze:button name="record_addRecord" caption="增加规范" txncode="7000203" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改规范" txncode="7000204" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除规范" txncode="7000205" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:button name="record_viewRecord" caption="查看规范" txncode="7000206" enablerule="1" hotkey="" align="right" onclick="func_record_viewRecord();"/>
      <freeze:button name="record_stxxRecord" caption="信息实体" txncode="7000211" enablerule="1" hotkey="" align="right" onclick="func_record_stxxRecord_button()"/>
      
      <freeze:hidden property="sys_rd_standard_id" caption="标准ID" style="width:10%" />
      <freeze:cell property="standard_name" caption="规范名称" style="width:16%" />
      <freeze:cell property="standard_issued_unit" caption="规范发布单位" valueset="规范发布单位" style="width:8%" />
      <freeze:cell property="standard_category_no" caption="规范分类号" style="width:10%" />
      <freeze:cell property="standard_issued_time" caption="规范发布时间" style="width:10%" />
      <freeze:cell property="file_name" caption="文件名称"  style="width:16%" />
      <freeze:hidden property="file_path" caption="文件路径" style="width:20%" visible="false" />
      <freeze:hidden property="standard_range" caption="规范范围" style="width:20%" visible="false" />
      <freeze:hidden property="standard_category" caption="规范类型" style="width:9%" />
      <freeze:cell property="memo" caption="备注" style="width:20%" visible="false" />
      <freeze:hidden property="sort" caption="排序号" style="width:9%" />
      <freeze:hidden property="timestamp" caption="时间戳" style="width:12%" />
    
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
