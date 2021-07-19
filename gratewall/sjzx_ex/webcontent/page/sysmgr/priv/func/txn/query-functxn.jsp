<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>��ѯ���ܽ����б�</title>
</head>

<freeze:body>
<freeze:errors/>

<script language="javascript">
function func_record_addRecord(){
  var page = new pageDefine( "insert-functxn.jsp", "���ӹ��ܽ���", "modal", 500, 250);
  page.addParameter("select-key:funccode","record:funccode");
  page.addRecord( );
}

function func_record_deleteRecord(){
  var page = new pageDefine( "/txn980315.do", "ɾ�����ܽ���");
  page.addParameter("record:funccode","primary-key:funccode");
  page.addParameter("record:txncode","primary-key:txncode");
  page.deleteRecord("�Ƿ�ɾ��ѡ�еļ�¼");
}

function func_record_addBatch(){
  var page = new pageDefine( "batch-add-functxn.jsp", "�������ӹ��ܽ���", "modal", 500, 400);
  page.addParameter("select-key:funccode","select-key:funccode");
  page.addRecord( );
}

</script>

<freeze:form action="/txn980311">
  <freeze:frame property="select-key" width="95%">
    <freeze:hidden property="funccode" caption="���ܴ���" style="width:90%"/>
  </freeze:frame>

  <freeze:grid property="record" caption="���ܵĽ�����Ϣ�б�" keylist="funccode,txncode" width="95%" fixrow="false">
    <freeze:button name="record_addBatch" caption="���ӽ���" enablerule="0" align="right" onclick="func_record_addBatch();"/>
    <freeze:button name="record_addRecord" caption="���ӽ���" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();" visible="false"/>
    <freeze:button name="record_deleteRecord" caption="ɾ������" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
    <freeze:cell property="funccode" caption="���ܴ���" style="width:10%" visible="false"/>
    <freeze:cell property="txncode" caption="���״���" style="width:15%"/>
    <freeze:cell property="txnname" caption="��������" style="width:40%"/>
    <freeze:cell property="status" caption="״̬" valueset="���ñ�־" style="width:10%" visible="false"/>
    <freeze:cell property="memo" caption="��ע��Ϣ" style="width:45%"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
