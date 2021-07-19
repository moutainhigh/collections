<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询文件映射列表</title>
</head>

<script language="javascript">

// 增加文件映射
function func_record_addRecord()
{
	var page = new pageDefine( "insert-xt_ccgl_wjys.jsp", "增加文件映射", "modal" );
	page.addRecord();
}

// 修改文件映射
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn604060104.do", "修改文件映射", "modal" );
	page.addParameter( "record:ysbh_pk", "primary-key:ysbh_pk" );
	page.updateRecord();
}

// 删除文件映射
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn604060105.do", "删除文件映射" );
	page.addParameter( "record:ysbh_pk", "primary-key:ysbh_pk" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询文件映射列表"/>
<freeze:errors/>

<freeze:form action="/txn604060101">
  <freeze:frame property="select-key" width="95%">
      <freeze:hidden property="ysbh_pk" caption="映射编号" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:frame>

  <freeze:grid property="record" caption="查询文件映射列表" keylist="ysbh_pk" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="增加文件映射" txncode="604060103" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改文件映射" txncode="604060104" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除文件映射" txncode="604060105" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="ysbh_pk" caption="映射编号" style="width:10%" visible="false"/>
      <freeze:cell property="cclbbh_pk" caption="类别编号ID" style="width:12%" />
      <freeze:hidden property="wybs" caption="唯一标识" style="width:20%" />
      <freeze:cell property="wjmc" caption="文件名称" style="width:20%" />
      <freeze:hidden property="wjzt" caption="文件状态" style="width:10%" />
      <freeze:cell property="cclj" caption="存储路径" style="width:20%" />
      <freeze:cell property="cjsj" caption="创建时间" style="width:12%" />
      <freeze:hidden property="scxgsj" caption="上次修改时间" style="width:12%" />
      <freeze:hidden property="bz" caption="备注" style="width:20%" />
      <freeze:hidden property="ywbz" caption="业务备注" style="width:12%" />
      <freeze:hidden property="xm_fk" caption="项目fk" style="width:12%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
