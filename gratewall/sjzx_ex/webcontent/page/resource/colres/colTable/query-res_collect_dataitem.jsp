<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�ɼ��������б�</title>
</head>

<script language="javascript">

// ���Ӳɼ��������
function func_record_addRecord()
{
	var page = new pageDefine( "insert-res_collect_dataitem.jsp", "���Ӳɼ��������", "modal" );
	page.addRecord();
}

// �޸Ĳɼ��������
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn20202004.do", "�޸Ĳɼ��������", "modal" );
	page.addParameter( "record:collect_dataitem_id", "primary-key:collect_dataitem_id" );
	page.updateRecord();
}

// ɾ���ɼ��������
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn20202005.do", "ɾ���ɼ��������" );
	page.addParameter( "record:collect_dataitem_id", "primary-key:collect_dataitem_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var ids =document.getElementsByName("span_record:code_table");
	var value=getFormAllFieldValues("record:code_table");
	
	for(var i=0; i<ids.length; i++){
		var val=value[i];
		if(val==null||val==""){
		}else{
		  ids[i].innerHTML='<a href="javascript:func_record_querycode(\''+value[i]+'\');" title="" >'+val+'</a>';
		}
    }
}
function func_record_querycode(val){
	   
    var url="txn20301026.do?select-key:code_table="+val;
    var page = new pageDefine( url, "�鿴���뼯��Ϣ", "���뼯��ѯ" );
	page.addRecord();
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ�ɼ��������б�"/>
<freeze:errors/>

<freeze:form action="/txn20202001">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="collect_table_id" caption="�ɼ����ݱ�ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="dataitem_name_en" caption="����������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="dataitem_name_cn" caption="��������������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="dataitem_type" caption="����������" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="code_table" caption="��Ӧ�����" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="��ѯ�ɼ��������б�" keylist="collect_dataitem_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="���Ӳɼ��������" txncode="20202003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸Ĳɼ��������" txncode="20202004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ���ɼ��������" txncode="20202005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="collect_dataitem_id" caption="�ɼ�������ID" style="width:12%" />
      <freeze:cell property="collect_table_id" caption="�ɼ����ݱ�ID" style="width:12%" />
      <freeze:cell property="dataitem_name_en" caption="����������" style="width:20%" />
      <freeze:cell property="dataitem_name_cn" caption="��������������" style="width:20%" />
      <freeze:cell property="dataitem_type" caption="����������" style="width:12%" />
      <freeze:cell property="dataitem_long" caption="�������" style="width:10%" />
      <freeze:cell property="is_key" caption="�Ƿ�����" style="width:10%" />
      <freeze:cell property="is_code_table" caption="�Ƿ�����" style="width:10%" />
      <freeze:cell property="code_table" caption="��Ӧ�����" style="width:12%" />
      <freeze:cell property="dataitem_long_desc" caption="����������" style="width:20%" visible="false" />
      <freeze:cell property="is_markup" caption="��Ч���" style="width:10%" />
      <freeze:cell property="creator_id" caption="������ID" style="width:12%" />
      <freeze:cell property="created_time" caption="����ʱ��" style="width:12%" />
      <freeze:cell property="last_modify_id" caption="����޸���ID" style="width:12%" />
      <freeze:cell property="last_modify_time" caption="����޸�ʱ��" style="width:12%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
