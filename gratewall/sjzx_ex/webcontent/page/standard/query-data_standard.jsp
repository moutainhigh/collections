<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ���ݹ����б�</title>
</head>

<script language="javascript">

// ���ӹ���
function func_record_addRecord()
{
	var page = new pageDefine( "insert-data_standard.jsp", "���ӹ���","modal" );
	page.addRecord();
}

// �޸Ĺ���
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn603004.do", "�޸Ĺ���","modal" );
	page.addParameter( "record:standard_id", "primary-key:standard_id" );
	page.updateRecord();
}
function func_record_updateRecord(idx)
{
    var svrId = getFormFieldValue("record:standard_id", idx);
	var page = new pageDefine( "/txn603004.do", "�޸Ĺ���","modal" );
	page.addValue( svrId, "primary-key:standard_id" );
	page.updateRecord();
}
// ɾ������
function func_deleteRecord(idx)
{
	var page = new pageDefine( "/txn603005.do", "ɾ������" );
	page.addValue( idx, "primary-key:standard_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn603005.do", "ɾ������" );
	page.addParameter( "record:standard_id", "primary-key:standard_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}
//�鿴�����
function func_viewConfig(idx)
{
	var svrId = getFormFieldValue("record:standard_id", idx);
	var page = new pageDefine( "/txn603016.do", "�鿴�����" ,"modal");
	page.addValue( svrId, "primary-key:standard_id" );
	page.updateRecord();
}


// �����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{

    var ids = getFormAllFieldValues("record:standard_id");
	for(var i=0; i<ids.length; i++){  
	   var htm = '<a href="#" title="�޸�" onclick="func_record_updateRecord('+i+')"><div class="edit"></div></a>&nbsp;';
	   htm += '<a href="#" title="ɾ��" onclick="func_deleteRecord(\''+ids[i]+'\');"><div class="delete"></div></a>';
	   document.getElementsByName("span_record:oper")[i].innerHTML +=htm;
	 }
	var names = getFormAllFieldValues("record:standard_name");
	for(var i=0; i<names.length; i++){
	   htm = '<a href="#" title="����鿴��ϸ��Ϣ" onclick="func_viewConfig(\''+i+'\');">'+names[i]+'</a>';
	   document.getElementsByName("span_record:standard_name")[i].innerHTML =htm;
	}
	 
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ���ݹ����б�"/>
<freeze:errors/>

<freeze:form action="/txn603001">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="standard_name" caption="���ݹ淶����" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="specificate_no" caption="���ͷ����" datatype="string" maxlength="50" style="width:95%"/>
<freeze:datebox property="created_time_start" caption="����ʱ��" datatype="string" maxlength="11" prefix="<table width='100%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%"/>
      </td><td width='5%'>&nbsp;����</td><td width='45%'>
      <freeze:datebox property="created_time_end" caption="����ʱ��" datatype="string" maxlength="11" style="width:100%" colspan="0"/>
      </td><td width='5%'></td></tr></table>  </freeze:block>
<br/>
  <freeze:grid property="record" checkbox="false"   caption="��ѯ�����б�" keylist="standard_id" width="95%" navbar="bottom" >
      <freeze:button name="record_addRecord" caption="����" txncode="603003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:hidden property="standard_id" caption="��׼ID" style="width:10%" visible="false"/>
      <freeze:cell property="@rowid" align="center" caption="���" style="width:5%" />
      <freeze:cell property="standard_name" align="center" caption="���ݹ淶����" style="width:20%" />
      <freeze:cell property="issuing_unit" align="center" caption="������λ" style="width:12%" />
      <freeze:cell property="specificate_no" caption="���ͷ����" style="width:10%" align="center"/>
	  <freeze:cell property="enable_time" align="center" caption="����ʱ��" style="width:12%" />
      <freeze:cell property="fjmc" caption="�ļ�����" style="width:31%" align="center"/>

      <freeze:cell property="oper" nowrap="true" caption="����" align="center" style="width:10%"/>
      
      <freeze:hidden property="standard_type" caption="��׼����"  />
      <freeze:hidden property="specificate_type" caption="��������"  />
      
      <freeze:hidden property="specificate_status" caption="����״̬"  />
      <freeze:hidden property="is_markup" caption="���뼯 ��Ч ��Ч"  />
      <freeze:hidden property="creator_id" caption="������ID"  />
      
      <freeze:hidden property="specificate_status" align="center" caption="��ע"  />
      <freeze:hidden property="last_modify_id" caption="����޸���ID"  />
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��"  />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>