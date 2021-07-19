<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�ļ�ӳ���б�</title>
</head>

<script language="javascript">

// �����ļ�ӳ��
function func_record_addRecord()
{
	var page = new pageDefine( "insert-xt_ccgl_wjys.jsp", "�����ļ�ӳ��", "modal" );
	page.addRecord();
}

// �޸��ļ�ӳ��
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn604060104.do", "�޸��ļ�ӳ��", "modal" );
	page.addParameter( "record:ysbh_pk", "primary-key:ysbh_pk" );
	page.updateRecord();
}

// ɾ���ļ�ӳ��
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn604060105.do", "ɾ���ļ�ӳ��" );
	page.addParameter( "record:ysbh_pk", "primary-key:ysbh_pk" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ�ļ�ӳ���б�"/>
<freeze:errors/>

<freeze:form action="/txn604060101">
  <freeze:frame property="select-key" width="95%">
      <freeze:hidden property="ysbh_pk" caption="ӳ����" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:frame>

  <freeze:grid property="record" caption="��ѯ�ļ�ӳ���б�" keylist="ysbh_pk" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="�����ļ�ӳ��" txncode="604060103" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸��ļ�ӳ��" txncode="604060104" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ���ļ�ӳ��" txncode="604060105" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="ysbh_pk" caption="ӳ����" style="width:10%" visible="false"/>
      <freeze:cell property="cclbbh_pk" caption="�����ID" style="width:12%" />
      <freeze:hidden property="wybs" caption="Ψһ��ʶ" style="width:20%" />
      <freeze:cell property="wjmc" caption="�ļ�����" style="width:20%" />
      <freeze:hidden property="wjzt" caption="�ļ�״̬" style="width:10%" />
      <freeze:cell property="cclj" caption="�洢·��" style="width:20%" />
      <freeze:cell property="cjsj" caption="����ʱ��" style="width:12%" />
      <freeze:hidden property="scxgsj" caption="�ϴ��޸�ʱ��" style="width:12%" />
      <freeze:hidden property="bz" caption="��ע" style="width:20%" />
      <freeze:hidden property="ywbz" caption="ҵ��ע" style="width:12%" />
      <freeze:hidden property="xm_fk" caption="��Ŀfk" style="width:12%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
