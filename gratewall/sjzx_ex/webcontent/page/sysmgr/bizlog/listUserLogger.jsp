<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>�û���־��ѯ</title>
</head>

<freeze:body>
<freeze:title caption="�û���־��ѯ"/>
<freeze:errors/>

<script language="javascript">
function func_record_viewRecord(){
  var page = new pageDefine( "/txn981214.do", "�鿴ҵ����־��Ϣ", "modal", 650, 400);
  page.addParameter("record:flowno","primary-key:flowno");
  page.goPage( );
}
</script>

<freeze:form action="/txn981216">
  <freeze:block theme="query" property="select-key" caption="��־��ѯ" width="95%">
    <freeze:datebox property="startdate" caption="���ڷ�Χ" prefix="<table width='100%' border='0' cellpadding='0' cellspacing='0'><tr><td width='40%'>" style="width:100%"/>
    </td><td width='5%'>&nbsp;����</td><td width='40%'>
    <freeze:datebox property="stopdate" caption="��������" value="@DATE" style="width:100%" colspan="0"/>
    </td><td width='15%'></td></tr></table>
    <freeze:text property="trdtype" caption="��������" style="width:90%" maxlength="64" datatype="string"/>
  </freeze:block>

  <freeze:grid property="record" caption="������־�б�" keylist="flowno" width="95%" multiselect="false" navbar="bottom" fixrow="false">
    <freeze:button name="record_viewRecord" caption="�鿴��־" txncode="981214" enablerule="2" hotkey="VIEW" align="right" onclick="func_record_viewRecord();"/>
    <freeze:cell property="flowno" caption="��ˮ��" width="10%" visible="false"/>
    <freeze:cell property="reqflowno" caption="������ˮ��" width="10%" visible="false"/>
    <freeze:cell property="regdate" caption="��������" width="10%"/>
    <freeze:cell property="regtime" caption="����ʱ��" width="10%"/>
    <freeze:cell property="username" caption="����Ա����" width="10%"/>
    <freeze:cell property="opername" caption="����Ա����" width="10%"/>
    <freeze:cell property="orgid" caption="��������" width="10%"/>
    <freeze:cell property="orgname" caption="��������" width="10%"/>
    <freeze:cell property="trdcode" caption="������" width="8%"/>
    <freeze:cell property="trdname" caption="��������" width="17%"/>
    <freeze:cell property="trdtype" caption="��������" width="12%"/>
    <freeze:cell property="errcode" caption="�������" width="8%"/>
    <freeze:cell property="errdesc" caption="��������" width="15%"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
