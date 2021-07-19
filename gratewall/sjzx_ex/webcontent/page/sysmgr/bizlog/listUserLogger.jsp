<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>用户日志查询</title>
</head>

<freeze:body>
<freeze:title caption="用户日志查询"/>
<freeze:errors/>

<script language="javascript">
function func_record_viewRecord(){
  var page = new pageDefine( "/txn981214.do", "查看业务日志信息", "modal", 650, 400);
  page.addParameter("record:flowno","primary-key:flowno");
  page.goPage( );
}
</script>

<freeze:form action="/txn981216">
  <freeze:block theme="query" property="select-key" caption="日志查询" width="95%">
    <freeze:datebox property="startdate" caption="日期范围" prefix="<table width='100%' border='0' cellpadding='0' cellspacing='0'><tr><td width='40%'>" style="width:100%"/>
    </td><td width='5%'>&nbsp;至：</td><td width='40%'>
    <freeze:datebox property="stopdate" caption="结束日期" value="@DATE" style="width:100%" colspan="0"/>
    </td><td width='15%'></td></tr></table>
    <freeze:text property="trdtype" caption="分类名称" style="width:90%" maxlength="64" datatype="string"/>
  </freeze:block>

  <freeze:grid property="record" caption="操作日志列表" keylist="flowno" width="95%" multiselect="false" navbar="bottom" fixrow="false">
    <freeze:button name="record_viewRecord" caption="查看日志" txncode="981214" enablerule="2" hotkey="VIEW" align="right" onclick="func_record_viewRecord();"/>
    <freeze:cell property="flowno" caption="流水号" width="10%" visible="false"/>
    <freeze:cell property="reqflowno" caption="请求流水号" width="10%" visible="false"/>
    <freeze:cell property="regdate" caption="操作日期" width="10%"/>
    <freeze:cell property="regtime" caption="操作时间" width="10%"/>
    <freeze:cell property="username" caption="操作员代码" width="10%"/>
    <freeze:cell property="opername" caption="操作员姓名" width="10%"/>
    <freeze:cell property="orgid" caption="机构代码" width="10%"/>
    <freeze:cell property="orgname" caption="机构名称" width="10%"/>
    <freeze:cell property="trdcode" caption="交易码" width="8%"/>
    <freeze:cell property="trdname" caption="交易名称" width="17%"/>
    <freeze:cell property="trdtype" caption="分类名称" width="12%"/>
    <freeze:cell property="errcode" caption="错误代码" width="8%"/>
    <freeze:cell property="errdesc" caption="错误描述" width="15%"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
