<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�����������ӿ������б�</title>
</head>

<script language="javascript">
var deleteID;
// ���ӹ����������ӿ�����
function func_record_addRecord()
{
	var page = new pageDefine( "insert-share_interface.jsp", "���ӹ����������ӿ�����","modal");
	page.addRecord();
}

// �޸Ĺ����������ӿ�����
function func_record_updateRecord(id)
{
	var page = new pageDefine( "/txn401004.do", "�޸Ĺ����������ӿ�����","modal" );
	page.addValue( id, "primary-key:interface_id" );
	page.updateRecord();
}

// �鿴�����������ӿ�����
function func_record_viewRecord(id)
{
	var page = new pageDefine( "/txn401010.do", "�鿴�����������ӿ�����","modal" );
	page.addValue( id, "primary-key:interface_id" );
	page.updateRecord();
}
// ɾ�������������ӿ�����
function func_record_deleteRecord(id)
{
	var page = new pageDefine( "/txn401005.do", "ɾ����������ӿ�" );
	page.addParameter( "record:interface_id", "primary-key:interface_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

function deleteCallback(errCode, errDesc, xmlResults)
{
  if (errCode != '000000') {
    //alert('�������['+errCode+']==>'+errDesc);
    return;
  }
  var id = _getXmlNodeValues(xmlResults, "record:interface_id");
  var num = _getXmlNodeValues(xmlResults, "record:interfaceusednum");
  if(num && num[0]>0)
  {
    alert("�з�����ô˽ӿڣ�����ɾ����");
  	return;
  }
	var page = new pageDefine( "/txn401011.do", "ɾ����������ӿ�" );
	var interface_id = id;
	page.addValue(interface_id, "primary-key:interface_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}
function updateCallback(errCode, errDesc, xmlResults)
{
  if (errCode != '000000') {
    //alert('�������['+errCode+']==>'+errDesc);
    return;
  }
  var id = _getXmlNodeValues(xmlResults, "record:interface_id");
  var num = _getXmlNodeValues(xmlResults, "record:interfaceusednum");
  if(num && num[0]>0)
  {
    alert("�з�����ô˽ӿڣ������޸ģ�");
  	return;
  }
    var page = new pageDefine( "/txn401004.do", "�޸Ĺ����������ӿ�����","modal" );
	var interface_id = id;
	page.addValue( interface_id, "primary-key:interface_id" );
	page.updateRecord();
}
// ɾ�������������ӿ�����
function func_deleteRecord(id)
{
  var page = new pageDefine("/txn401012.ajax", "�ӿ�ID��ѯ���ýӿڵķ�������");
  var interface_id = id;
  deleteID = id;
  if (interface_id && interface_id != '') {
    page.addValue(interface_id, "select-key:interface_id");
    page.callAjaxService('deleteCallback');
  }
}
//ɾ�������������ӿ�����
function func_updateRecord(id)
{
  var page = new pageDefine("/txn401012.ajax", "�ӿ�ID��ѯ���ýӿڵķ�������");
  var interface_id = id;
  if (interface_id && interface_id != '') {
    page.addValue(interface_id, "select-key:interface_id");
    page.callAjaxService('updateCallback');
  }
}
// �޸ĵ�������ͣ��״̬
function func_record_changeOneStatus(interface_id,interface_state)
{
	var page = new pageDefine( "/txn401013.do", "����/ͣ��" );
	if(interface_state === 'Y'){
		interface_state = 'N';
	}else{
		interface_state = 'Y';
	}
	page.addValue( interface_id, "primary-key:interface_id" );
	page.addValue( interface_state, "primary-key:interface_state" );
	page.deleteRecord( "�Ƿ��޸ķ�������ӿ�״̬" );
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var ids = getFormAllFieldValues("record:interface_id");
	var interface_state = document.getElementsByName("record:interface_state");
	for(var i=0; i<ids.length; i++){
	   var htm='<a href="#" title="�޸�" onclick="func_updateRecord(\''+ids[i]+'\');"><div class="edit"></div></a>&nbsp;';
	   htm+='<a href="#" title="ɾ��" onclick="func_deleteRecord(\''+ids[i]+'\');"><div class="delete"></div></a>&nbsp;'; 
	   
	   if(interface_state[i].value=="Y"){
	   htm+='<a href="#" title="���ͣ��" onclick="func_record_changeOneStatus(\''+ids[i]+'\',\''+interface_state[i].value+'\');"><div class="run"></div></a>';
	   }else
	   {
	   htm+='<a href="#" title="�������" onclick="func_record_changeOneStatus(\''+ids[i]+'\',\''+interface_state[i].value+'\');"><div class="stop"></div></a>';
	   }
	   document.getElementsByName("span_record:operation")[i].innerHTML +=htm;
	}
	
	var names = getFormAllFieldValues("record:interface_name");
	for(var i=0; i<names.length; i++){
	   htm = '<a href="#" title="����鿴��ϸ��Ϣ" onclick="func_record_viewRecord(\''+ids[i]+'\');">'+names[i]+'</a>';
	   document.getElementsByName("span_record:interface_name")[i].innerHTML =htm;
	}
	 
	var date_s = document.getElementsByName("span_record:created_time");
	for(var ii=0; ii<date_s.length; ii++){
		date_s[ii].innerHTML = date_s[ii].innerHTML.substr(0,10);
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:errors/>

<freeze:form action="/txn40109002">
  <freeze:grid property="record" checkbox="false" caption="��ѯ�ӿ��б�" keylist="interface_id" width="100%"   navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="���ӽӿ�" txncode="401003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button visible="false" name="record_deleteRecord" caption="ɾ���ӿ�" txncode="401005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
     <freeze:cell property="@rowid" caption="���"  style="width:5%" align="center" />
      <freeze:cell property="interface_name" caption="�ӿ�����" style="width:20%" />
      <freeze:cell property="table_name_cn" caption="�ӿ��������ݱ�" style="width:30%;" />
      <freeze:hidden property="interface_state" caption="�ӿ�״̬" valueset="��Դ����_һ�����״̬" align="center" style="width:8%" />
      <freeze:cell property="interface_description" caption="�ӿ�˵��" style="" />
      <freeze:cell property="created_time" align="center" caption="����ʱ��" style="width:12%" />
      <freeze:cell nowrap="true" property="operation" caption="����" style="width:95px" align="center"/>
      
      <freeze:hidden property="is_markup" caption="�����"  />
      <freeze:hidden property="creator_id" caption="������ID"  />
      <freeze:hidden property="interface_description" caption="�ӿ�˵��"/>
      <freeze:hidden property="table_id" caption="����봮" />
      <freeze:hidden property="interface_id" caption="�ӿ�ID"/>
      <freeze:hidden property="sql" caption="sql���"/>
      <freeze:hidden property="last_modify_id" caption="����޸���ID"  />
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��"  />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
