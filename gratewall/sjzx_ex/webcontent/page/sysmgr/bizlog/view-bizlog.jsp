<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="800" height="350">
<head>
<title>��ѯҵ����־</title>
</head>

<freeze:body>
<freeze:title caption="��ѯϵͳ������־"/>
<freeze:errors/>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script language="javascript">
function func_record_goBack(){
  goBackNoUpdate( "/txn981211.do" );
}

function onloadFunction(){
	document.getElementById("span_record:resultdata").innerHTML = getFormFieldValue("record:resultdata");
	$(".tailrow:eq(0)").remove();
	$(".menu:eq(0)").remove();
	$(".menu:eq(1)").remove();
	$(".btn_query").hide();
	$(".btn_reset").hide();
}

_browse.execute("onloadFunction()");
</script>

<freeze:form action="/txn981214">
  <freeze:frame property="primary-key" width="95%">
    <freeze:hidden property="flowno" caption="��ˮ��" width="90%"/>
  </freeze:frame>

  <freeze:block theme="query" property="record" caption="��־��Ϣ" width="95%">
    <freeze:hidden property="flowno" caption="��ˮ��" style="width:90%"/>
    <freeze:hidden property="reqflowno" caption="������ˮ��" style="width:90%"/>
    <freeze:cell property="regdate" caption="��������" datatype="date" style="width:90%"/>
    <freeze:cell property="regtime" caption="����ʱ��"  datatype="date" style="width:90%"/>
    <freeze:cell property="trdtype" caption="��������" style="width:90%"/>
    <freeze:cell property="username" caption="�û��ʺ�" style="width:90%"/>
    <freeze:cell property="opername" caption="����Ա����" style="width:90%"/>
    <freeze:cell property="orgid" caption="��������" style="width:90%" visible="false"/>
    <freeze:cell property="orgname" caption="��������" style="width:90%"/>
    <freeze:cell property="trdcode" caption="������" style="width:90%"/>
    <freeze:cell property="trdname" caption="��������" style="width:90%"/>
    <freeze:cell property="errcode" caption="�������" style="width:90%"/>
    <freeze:cell property="errdesc" caption="��������" colspan="2" style="width:36.5%"/>
  </freeze:block>
  <br />
  <freeze:block theme="query" property="record" caption="����" captionWidth="0" columns="1" width="95%">
    <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
    <freeze:cell property="resultdata" caption="������" style="width:100%" />
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
