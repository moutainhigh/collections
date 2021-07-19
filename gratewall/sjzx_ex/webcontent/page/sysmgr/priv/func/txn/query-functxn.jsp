<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>查询功能交易列表</title>
</head>

<freeze:body>
<freeze:errors/>

<script language="javascript">
function func_record_addRecord(){
  var page = new pageDefine( "insert-functxn.jsp", "增加功能交易", "modal", 500, 250);
  page.addParameter("select-key:funccode","record:funccode");
  page.addRecord( );
}

function func_record_deleteRecord(){
  var page = new pageDefine( "/txn980315.do", "删除功能交易");
  page.addParameter("record:funccode","primary-key:funccode");
  page.addParameter("record:txncode","primary-key:txncode");
  page.deleteRecord("是否删除选中的记录");
}

function func_record_addBatch(){
  var page = new pageDefine( "batch-add-functxn.jsp", "批量增加功能交易", "modal", 500, 400);
  page.addParameter("select-key:funccode","select-key:funccode");
  page.addRecord( );
}

</script>

<freeze:form action="/txn980311">
  <freeze:frame property="select-key" width="95%">
    <freeze:hidden property="funccode" caption="功能代码" style="width:90%"/>
  </freeze:frame>

  <freeze:grid property="record" caption="功能的交易信息列表" keylist="funccode,txncode" width="95%" fixrow="false">
    <freeze:button name="record_addBatch" caption="增加交易" enablerule="0" align="right" onclick="func_record_addBatch();"/>
    <freeze:button name="record_addRecord" caption="增加交易" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();" visible="false"/>
    <freeze:button name="record_deleteRecord" caption="删除交易" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
    <freeze:cell property="funccode" caption="功能代码" style="width:10%" visible="false"/>
    <freeze:cell property="txncode" caption="交易代码" style="width:15%"/>
    <freeze:cell property="txnname" caption="交易名称" style="width:40%"/>
    <freeze:cell property="status" caption="状态" valueset="启用标志" style="width:10%" visible="false"/>
    <freeze:cell property="memo" caption="备注信息" style="width:45%"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
