<%
/*
 * @Header ����ά���б����ã�
 * @Revision
 * @Date 2007-03-01
 * @author adaFang
 * =====================================================
 *  ���������Ŀ��
 * =====================================================
 */
%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>�����б�</title>
</head>
<freeze:body>
<freeze:title caption="�����б�"/>
<freeze:errors/>

<script language="javascript">

function func_record_addRecord(){
  var page = new pageDefine( "insert-jggl.jsp", "���ӻ���", "modal", 650,400);
  page.addRecord( );
}

function func_record_updateRecord(){
  var page = new pageDefine( "/txn806004.do", "�޸Ļ���", "modal", 650,400);
  page.addParameter("record:jgid_pk","primary-key:jgid_pk");
  page.updateRecord( );
}

function func_record_deleteRecord(){
  var page = new pageDefine( "/txn806005.do", "ɾ������");
  page.addParameter("record:jgid_pk","primary-key:jgid_pk");
  page.deleteRecord("�Ƿ�ɾ��ѡ�еļ�¼");
}

</script>

<freeze:form action="/txn806001">
  <freeze:grid property="record" caption="�����б�" keylist="jgid_pk" width="100%" navbar="bottom" fixrow="false" >
    <freeze:button name="record_addRecord" caption="����" txncode="806003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
    <freeze:button name="record_updateRecord" caption="�޸�" txncode="806004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
    <freeze:button name="record_deleteRecord" caption="ɾ��" txncode="806005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
    <freeze:cell property="jgid_pk" caption="����ID" style="width:10%"/>
    <freeze:cell property="sjjgid_fk" caption="�ϼ�����ID" style="width:10%"/>
    <freeze:cell property="jgbh" caption="�������" style="width:10%"/>
    <freeze:cell property="jgmc" caption="��������" style="width:10%"/>
    <freeze:cell property="jgjc" caption="�������" style="width:10%"/>
    <freeze:cell property="jglx" caption="��������" style="width:10%"/>
    <freeze:cell property="jgfzr" caption="����������" style="width:10%"/>
    <freeze:cell property="sfyx" caption="�Ƿ���Ч" style="width:10%"/>
    <freeze:cell property="plxh" caption="�������" style="width:10%"/>
    <freeze:cell property="bz" caption="��ע" style="width:10%"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
