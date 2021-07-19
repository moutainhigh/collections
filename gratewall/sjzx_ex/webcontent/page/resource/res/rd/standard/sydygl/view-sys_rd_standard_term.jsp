<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="700" height="270">
<head>
<title>查看术语信息</title>
<style>
body{
background:#ffffff;
}
</style>
</head>

<script language="javascript">

// 打 印
function func_record_printDocument()
{
	print(document);
}

// 返 回
function func_record_goBackNoUpdate()
{
	goBackNoUpdate();	
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( __userInitPage );
</script>
<freeze:body styleClass='body-div'>
<freeze:title caption="查看术语信息"/>
<freeze:errors/>

<freeze:form action="/txn7000106">
 
  <freeze:block  property="record" caption="查看术语信息" width="95%">
      <freeze:hidden property="sys_rd_standar_term_id" caption="术语ID" datatype="string" style="width:95%"/>
      <freeze:cell property="standard_term_cn" caption="术语名称" datatype="string" style="width:95%"/>
      <freeze:cell property="standard_term_en" caption="术语英文" datatype="string" style="width:95%"/>
      <freeze:cell property="standard_term_definition" caption="术语定义" style="width:98%"/>
      <freeze:cell property="memo" caption="备注" style="width:98%"/>
      <!-- <freeze:hidden property="timeshtamp" caption="时间戳" style="width:95%"/>-->
  </freeze:block>
  <p align="center" class="print-menu">
  <!--  <input type="button" name="record_printDocument" value="打 印" class="menu" onclick="func_record_printDocument();" style='width:60px' >-->
  <input type="button" name="record_goBackNoUpdate" value="关闭" class="menu" onclick="func_record_goBackNoUpdate();" style='width:60px' >
  </p>
  <!--  <p align="center" class="print-hide">&nbsp;</p>-->

</freeze:form>
</freeze:body>
</freeze:html>
