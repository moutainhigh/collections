<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="850" height="600">
<head>
<title>�鿴�ɼ����ݱ���Ϣ</title>
</head>

<script type="text/javascript">

// �� ��
function func_record_goBack()
{
	goBackNoUpdate();	// /txn20201001.do
}
//�鿴�ɼ����������Ϣ
function func_record_viewRecord(idx)
{
	var page = new pageDefine( "/txn20202006.do", "�鿴�ɼ���������Ϣ", "modal" );
	page.addValue(idx, "primary-key:collect_dataitem_id" );
	var collect_table_id=getFormFieldValue('record:collect_table_id');
	page.addValue(collect_table_id,"primary-key:collect_table_id");
	page.updateRecord();
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	/* var ids = getFormAllFieldValues("dataItem:collect_dataitem_id");
	for(var i=0; i<ids.length; i++){
	   var htm='<a href="#" title="�鿴" onclick="func_record_viewRecord(\''+ids[i]+'\');"><div class="detail"></div></a>&nbsp;';
	 
	   document.getElementsByName("span_dataItem:oper")[i].innerHTML +=htm;
	 } */
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�鿴�ɼ����ݱ���Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn20201006">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="collect_table_id" caption="�ɼ����ݱ�ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�鿴�ɼ����ݱ���Ϣ" width="95%">
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="collect_table_id" caption="�ɼ����ݱ�ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="service_targets_id" caption="�������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:cell property="service_targets_id" caption="�����������"   show="name" valueset="��Դ����_�����������"  style="width:98%"/>
      <freeze:cell property="table_name_en" caption="������"  style="width:95%"/>
      <freeze:cell property="table_name_cn" caption="����������"  style="width:95%"/>
      <freeze:cell property="table_type" caption="������"  show="name" valueset="��Դ����_������"  style="width:95%"/>
      <freeze:cell property="created_time" caption="����ʱ��"  style="width:95%"/>
      <freeze:cell property="table_desc" caption="������" colspan="2"  style="width:98%"/>
       <freeze:cell property="crename" caption="�����ˣ�" datatype="string"  style="width:95%"/>
      <freeze:cell property="cretime" caption="����ʱ�䣺" datatype="string"  style="width:95%"/>
      <freeze:cell property="modname" caption="����޸��ˣ�" datatype="string"  style="width:95%"/>
      <freeze:cell property="modtime" caption="����޸�ʱ�䣺" datatype="string"  style="width:95%"/>
      
      
      <freeze:hidden property="table_status" caption="��״̬" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="is_markup" caption="��Ч���" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="����޸���ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��" datatype="string" maxlength="19" style="width:95%"/>
  </freeze:block>
 <br>
   <freeze:grid property="dataItem" caption="�ɼ��������б�" keylist="collect_dataitem_id" multiselect="false" checkbox="false" width="95%" navbar="bottom" fixrow="false" >
      <freeze:hidden property="collect_dataitem_id" caption="�ɼ�������ID"  />
      <freeze:hidden property="collect_table_id" caption="�ɼ����ݱ�ID"  />
      <freeze:cell property="@rowid" caption="���"  style="width:6%" align="center" />
      <freeze:cell property="dataitem_name_en" caption="����������" style="width:12%" />
      <freeze:cell property="dataitem_name_cn" caption="��������" style="width:12%" />
      <freeze:cell property="dataitem_type" caption="����������"  show="name" valueset="��Դ����_����������" style="width:12%" />
      <freeze:cell property="dataitem_long" caption="�������" style="width:10%" />
      <freeze:cell property="is_key" caption="�Ƿ�����" valueset="��Դ����_�Ƿ�����" style="width:10%" />
      <freeze:hidden property="is_code_table" caption="�Ƿ�����" style="width:10%" />
      <freeze:cell property="code_table" caption="��Ӧ�����" valueset="��Դ����_��Ӧ�����" style="width:12%" />
      <freeze:hidden property="oper" caption="����" align="center" style="width:12%" />
      <freeze:hidden property="dataitem_long_desc" caption="����������" style="width:20%"  />
      <freeze:hidden property="is_markup" caption="��Ч���" style="width:10%" />
      <freeze:hidden property="creator_id" caption="������ID" style="width:12%" />
      <freeze:hidden property="created_time" caption="����ʱ��" style="width:12%" />
      <freeze:hidden property="last_modify_id" caption="����޸���ID" style="width:12%" />
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��" style="width:12%" />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
