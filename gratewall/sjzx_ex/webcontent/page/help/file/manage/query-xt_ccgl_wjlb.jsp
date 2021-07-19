<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询文件类别列表</title>
</head>

<script language="javascript">

// 增加文件类别
function func_record_addRecord()
{
	var page = new pageDefine( "insert-xt_ccgl_wjlb.jsp", "增加文件类别", "modal" );
	page.addRecord();
}

// 修改文件类别
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn604050104.do", "修改文件类别", "modal" );
	page.addParameter( "record:cclbbh_pk", "primary-key:cclbbh_pk" );
	page.updateRecord();
}

// 删除文件类别
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn604050105.do", "删除文件类别" );
	page.addParameter( "record:cclbbh_pk", "primary-key:cclbbh_pk" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询文件类别列表"/>
<freeze:errors/>

<freeze:form action="/txn604050101">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="cclbmc" caption="处处类别名称" datatype="string" maxlength="60" style="width:95%"/>
      <freeze:text property="lbmcbb" caption="类别名称版本" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="zt" caption="状态" datatype="string" maxlength="1" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="查询文件类别列表" keylist="cclbbh_pk" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="增加文件类别" txncode="604050103" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改文件类别" txncode="604050104" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除文件类别" txncode="604050105" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="cclbbh_pk" caption="文件类别编号" style="width:10%" visible="false"/>
      <freeze:cell property="cclbmc" caption="处处类别名称" style="width:14%" />
      <freeze:cell property="lbmcbb" caption="类别名称版本" style="width:9%" />
      <freeze:cell property="ccgml" caption="存储根目录" style="width:18%" />
      <freeze:cell property="ejmlgz" caption="二级目录规则" style="width:9%" />
      <freeze:cell property="gzfzzd" caption="规则辅助字段" style="width:20%" />
      <freeze:cell property="zt" caption="状态" style="width:10%" />
      <freeze:cell property="bz" caption="备注" style="width:20%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
