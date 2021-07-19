<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@page import="com.gwssi.common.constant.CollectConstants"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�ɼ����ݱ��б�</title>
</head>
<script language='javascript' src='/script/uploadfile.js'></script>
<script language="javascript">

// ���Ӳɼ����ݱ���Ϣ
function func_record_addRecord()
{

    var page = new pageDefine( "/txn20201007.do", "�����ɼ����ݱ���Ϣ", "modal");
    page.addValue(" ","primary-key:collect_table_id");
	page.addRecord();
}

// ��ѯ�ɼ����ݱ���Ϣ �����޸�
function func_record_updateRecord(idx,ly,ifCreat)
{
	var page;
	if(ly!=null&&ly=='<%=CollectConstants.TYPE_CJLY_OUT_NAME%>'&&ifCreat=='<%=CollectConstants.TYPE_IF_CREAT_NO_NAME%>'){
		 page = new pageDefine( "/txn20201004.do", "�޸Ĳɼ����ݱ���Ϣ","modal", "1000", document.clientHeight);
		 page.addValue(idx,"primary-key:collect_table_id");
		 page.updateRecord();
	}else if(ly!=null&&ly=='<%=CollectConstants.TYPE_CJLY_OUT_NAME%>'&&ifCreat=='<%=CollectConstants.TYPE_IF_CREAT_YES_NAME%>'){
		//alert("���ݱ�������,�������޸�!");
	    //return;
	    page = new pageDefine( "/txn20201004.do", "�޸Ĳɼ����ݱ���Ϣ","modal", "1000", document.clientHeight);
		page.addValue(idx,"primary-key:collect_table_id");
		page.updateRecord();
	}
	else{
	    alert("�ڲ��ɼ�������������!");
	    return;
	}
	
}
function checkCollectTableUse(id,ly){
	if(id==''){
		alert("δѡ��ɼ����ݱ�");
		return;
	}
	if(ly!=null&&ly=='<%=CollectConstants.TYPE_CJLY_IN_NAME%>'){
		alert('�ڲ��ɼ����������ɾ����');
		return;
	}
	
	var page = new pageDefine( "/txn20201011.ajax", "���ɼ����ݱ��Ƿ�����");
	page.addValue(id, "primary-key:collect_table_id" );
	page.callAjaxService("checkNameUsed");
}

function checkNameUsed(errorCode, errDesc, xmlResult){
	if(errorCode!="000000"){
		alert(errDesc);
	}else{
		var num = _getXmlNodeValue(xmlResult, "record:collect_table_id");
		var id = _getXmlNodeValue(xmlResult, "primary-key:collect_table_id" );
		
		if(num==""||num==null){
			func_record_deleteRecord(id);
		}else{
			alert('�ɼ������Ѿ�ʹ�ô˲ɼ����ݱ�,�벻Ҫɾ��!');
			return;
		}
	}
}

// ɾ���ɼ����ݱ���Ϣ
function func_record_deleteRecord(idx)
{
		var page = new pageDefine( "/txn20201005.do", "ɾ���ɼ����ݱ���Ϣ" );
		page.addValue(idx,"primary-key:collect_table_id");
		page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}
//�鿴�ɼ����ݱ���Ϣ
function func_record_viewRecord(idx)
{
	var page = new pageDefine( "/txn20201006.do", "�鿴�ɼ����ݱ���Ϣ", "modal" );
	page.addValue(idx, "primary-key:collect_table_id" );
	page.updateRecord();
}
//�鿴�ɼ����ݱ���Ϣ
function importExcel()
{
	//var page = new pageDefine( "/txn20201010.do", "����excel" );
	//page.updateRecord();
	var page = new pageDefine( "importExcel-res_collect_table.jsp", "����excel");
	page.updateRecord();
}
//ͬ����ṹ
function synchroTable()
{
	 if(confirm("�Ƿ�ͬ���ڲ�ϵͳ��ṹ?")){
	 	//clickFlag=1;
		var page = new pageDefine( "/txn20201012.ajax", "ͬ����ṹ");
		_showProcessHintWindow("����ͬ���ڲ�ϵͳ��ṹ,���Ժ�...");
		page.callAjaxService("systableCallBack");
		//page.updateRecord();
	}
	 
}

function systableCallBack(errorCode, errDesc, xmlResults) {

		if (errorCode != "000000") {
			_hideProcessHintWindow();
			alert("ͬ���ڲ�ϵͳ��ṹʧ��!");
		} else {
			//var result = _getXmlNodeValue(xmlResults, "record:result");
			
			_hideProcessHintWindow();
			alert("ͬ���ڲ�ϵͳ��ṹ�ɹ�!");
			//_showProcessHintWindow("<span style='color:green;'>���ӳɹ�!</span><br/><a href='javascript:_hideProcessHintWindow();'>�ر�</a>");
			window.location.href="/txn20201001.do";
		}
	}
// �鿴�ɼ����ݱ���Ϣ
function func_record_viewRecord1(index)
{	
	var gridname = getGridDefine("record");
	var id = gridname.getAllFieldValues( "collect_table_id" )[index];
	var page = new pageDefine( "/txn20201006.do", "�鿴�ɼ����ݱ���Ϣ","modal");
	page.addValue( id, "primary-key:collect_table_id" );
	page.updateRecord();
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var ids = getFormAllFieldValues("record:collect_table_id");
	var ly=getFormAllFieldValues("record:cj_ly");//�ɼ���Դ
	var if_creat=getFormAllFieldValues("record:if_creat");//�Ƿ�������
	for(var i=0; i<ids.length; i++){
	  // var htm='<a href="#" title="�鿴" onclick="func_record_viewRecord(\''+ids[i]+'\');"><div class="detail"></div></a>&nbsp;';
	  
	   var htm='<a href="#" title="�޸�" onclick="func_record_updateRecord(\''+ids[i]+'\',\''+ly[i]+'\',\''+if_creat[i]+'\');"><div class="edit"></div></a>&nbsp;';
	   htm+='<a href="#" title="ɾ��" onclick="checkCollectTableUse(\''+ids[i]+'\',\''+ly[i]+'\');"><div class="delete"></div></a>';
	   document.getElementsByName("span_record:oper")[i].innerHTML +=htm;
	 }
	 
	var names = getFormAllFieldValues("record:table_name_en");
	for(var i=0; i<names.length; i++){
	   htm = '<a href="#" title="����鿴��ϸ��Ϣ" onclick="func_record_viewRecord1(\''+i+'\');">'+names[i]+'</a>';
	   document.getElementsByName("span_record:table_name_en")[i].innerHTML =htm;
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ�ɼ����ݱ��б�"/>
<freeze:errors/>

<freeze:form action="/txn20201001">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:select property="cj_ly" caption="�ɼ���Դ"  show="name" valueset="��Դ����_�ɼ���Դ"  style="width:95%"/>
      <freeze:browsebox property="service_targets_id" caption="�����������" show="name" valueset="��Դ����_�����������"  style="width:95%"/>
      <freeze:select property="table_type" caption="������"  show="name" valueset="��Դ����_������"  style="width:95%"/>
      <freeze:select property="if_creat" caption="���ɲɼ���״̬"  show="name" valueset="��Դ����_����״̬"  style="width:95%"/>
      <freeze:text property="table_name_en" caption="������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_name_cn" caption="����������" datatype="string" maxlength="100" style="width:95%"/>
  </freeze:block>
<br/>
  <freeze:grid property="record" caption="��ѯ�ɼ����ݱ��б�" keylist="collect_table_id" width="95%" multiselect="false" checkbox="false" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="�½��ɼ���" txncode="20201003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_importRecord" caption="�����ṹ" txncode="20201010" enablerule="0" hotkey="UPDATE" align="right" onclick="importExcel();"/>
      <freeze:button name="record_syncRecord" caption="ͬ����ṹ" txncode="20201005" enablerule="0" hotkey="DELETE" align="right" onclick="synchroTable()"/>
      <freeze:hidden property="collect_table_id" caption="�ɼ����ݱ�ID"  />
      <freeze:cell property="@rowid" caption="���"  style="width:4%" align="center" />
      <freeze:cell property="cj_ly" caption="�ɼ���Դ" show="name" valueset="��Դ����_�ɼ���Դ" style="width:10%" />
      <freeze:cell property="service_targets_id" caption="�����������" show="name" valueset="��Դ����_���������������" style="width:14%" />
      <freeze:cell property="table_name_en" caption="������" style="width:17%"/>
      <freeze:cell property="table_name_cn" caption="����������" style="width:15%" />
      <freeze:cell property="table_type" caption="������" show="name" valueset="��Դ����_������" style="width:7%" />
      <freeze:cell property="if_creat" caption="���ɲɼ���״̬" show="name" valueset="��Դ����_����״̬" style="width:13%" />
      <freeze:cell property="table_desc" caption="������"  visible="false" />
      <freeze:hidden property="table_status" caption="��״̬" />
      <freeze:hidden property="is_markup" caption="��Ч���"  />
      <freeze:hidden property="creator_id" caption="������ID"  />
      <freeze:cell property="created_time" caption="����ʱ��"  style="width:10%"/>
      <freeze:cell property="oper" caption="����" align="center"  style="width:10%" />
      <freeze:hidden property="last_modify_id" caption="����޸���ID"  />
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
