<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<script src="<%=request.getContextPath() %> /script/common/js/validator.js"></script>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸�webservice������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	      var item=getFormFieldValue('record:method_name_cn');
  		  if(!checkItemLength(item,"100","������������")){
  		    return false;
  		  }
	      item=getFormFieldValue('record:collect_table');
  		  if(!checkItem(item,"100","��Ӧ�ɼ���")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:collect_mode');
  		  if(!checkItem(item,"100","�ɼ���ʽ")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:encrypt_mode');
  		  if(!checkItem(item,"100","���ܷ���")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:method_description');
  		  if(!checkItemLength(item,"2000","��������")){
  		    return false;
  		  }
	      var page = new pageDefine( "/txn30102002.do", "���淽����Ϣ");
	    
	      page.addParameter("record:webservice_task_id","primary-key:webservice_task_id");
	      page.addParameter("record:webservice_task_id","record:webservice_task_id");
	      page.addParameter("record:collect_task_id","record:collect_task_id");
	      page.addParameter("record:service_targets_id","record:service_targets_id");
	      
	      page.addParameter("record:service_no","record:service_no");
	      page.addParameter("record:method_name_en","record:method_name_en");
	      page.addParameter("record:method_name_cn","record:method_name_cn");
	      page.addParameter("record:collect_table","record:collect_table");
	      page.addParameter("record:collect_mode","record:collect_mode");
	      page.addParameter("record:is_encryption","record:is_encryption");
	      page.addParameter("record:encrypt_mode","record:encrypt_mode");
	      page.addParameter("record:method_description","record:method_description");
	      page.addParameter("record:method_status","record:method_status");
	      //page.callAjaxService('updateTask');
	      page.updateRecord();
	       //saveRecord( '', '���淽����Ϣ' );
}
function updateTask(errCode,errDesc,xmlResults){
		
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}else{
		    alert("����ɹ�!");
		}
}
// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '����webservice�����' );	// /txn30102001.do
}

// �� ��
function func_record_goBack()
{
	//goBack();	// /txn30102001.do
	
	var page = new pageDefine( "/txn30101004.do", "��ѯ�ɼ�����");
	page.addParameter("record:webservice_task_id","primary-key:webservice_task_id");
	page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	page.updateRecord();
}
function getParameter(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
    var parameter = 'input-data:service_targets_id=<freeze:out  value="${record.service_targets_id}"/>';
	return parameter;
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
	var ids = getFormAllFieldValues("dataItem:webservice_patameter_id");
	for(var i=0; i<ids.length; i++){
	  
	   var htm='<a href="#" title="�޸�" onclick="func_record_updateRecord(\''+ids[i]+'\');"><div class="edit"></div></a>&nbsp;';
	   document.getElementsByName("span_dataItem:oper")[i].innerHTML +=htm;
	 }
	 
	 var paramStyle = document.getElementById("label:dataItem:patameter_style");
	 if(paramStyle){
		 paramStyle.innerText += '(*)';
	 }
}
// �޸ķ�����Ϣ
function func_record_updateRecord(idx)
{
	var selVal = document.getElementById("dataItem:patameter_style");
	if(selVal){
		var index = selVal.selectedIndex;  
		var text = selVal.options[index].text; 
		if(text=='ȫ��'){
			alert("��ѡ�������ʽ.");
			return;
		}
	}
	 
	var page = new pageDefine( "/txn30103004.do", "�޸Ĳ�����Ϣ", "modal" ,1000, 800 );
	page.addValue(idx,"primary-key:webservice_patameter_id");
	var service_targets_id=getFormFieldValue("record:service_targets_id");
	page.addValue(service_targets_id,"primary-key:service_targets_id");
	var patameter_style= getFormFieldValue("dataItem:patameter_style");
	page.addValue(patameter_style,"record:patameter_style");
	page.updateRecord();
}
//�鿴�ɼ����������Ϣ
function func_record_viewRecord(idx)
{
	var page = new pageDefine( "/txn30103006.do", "�鿴������Ϣ", "modal" );
	page.addValue(idx,"primary-key:webservice_patameter_id");
	
	page.updateRecord();
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸ķ�����Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn30102004">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="webservice_task_id" caption="WEBSERVICE����ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸ķ�����Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="webservice_task_id" caption="WEBSERVICE����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="service_targets_id" caption="����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:cell property="service_no" caption="����ID��" datatype="string"  style="width:95%"/>
      <freeze:cell property="method_name_en" caption="�������ƣ�" datatype="string"  style="width:95%"/>
      <freeze:text property="method_name_cn" caption="������������" datatype="string"  style="width:95%"/>
      <freeze:browsebox property="collect_table" caption="��Ӧ�ɼ���" show="name" notnull="true" valueset="��Դ����_��������Ӧ�ɼ���" parameter="getParameter();"  style="width:95%"/>
      <freeze:select property="collect_mode" caption="�ɼ���ʽ" show="name" notnull="true" valueset="��Դ����_�ɼ���ʽ" style="width:95%"/>
      <freeze:hidden property="is_encryption" caption="�Ƿ����"   datatype="string"  style="width:95%"/>
      <freeze:select property="encrypt_mode" caption="���ܷ���"  notnull="true" show="name" valueset="��Դ����_���ܷ���" value="01" style="width:95%"/>
      <freeze:textarea property="method_description" caption="��������" colspan="2" rows="2"  style="width:98%"/>
      <freeze:cell property="web_name_space" caption="�����ռ䣺"  colspan="2" style="width:95%"/>
      <freeze:hidden property="method_status" caption="����״̬" datatype="string"  style="width:95%"/>
  </freeze:block>
<br>
   <freeze:grid property="dataItem" caption="�����б�" keylist="webservice_patameter_id" multiselect="false" checkbox="false" width="95%" navbar="bottom" fixrow="false" >
      <freeze:hidden property="webservice_patameter_id" caption="����ID"  />
      <freeze:hidden property="webservice_task_id" caption="����ID"  />
      <freeze:cell property="@rowid" caption="���"  style="width:5%" align="center" />
      <freeze:cell property="patameter_type" caption="��������" style="width:10%" />
      <freeze:cell property="patameter_name" caption="������" style="width:10%" />
      <freeze:cell property="patameter_value" caption="����ֵ"   style="width:50%" />
      <freeze:select property="patameter_style" caption="������ʽ" valueset="�ɼ�����_������ʽ" style="width:20%" />
      <freeze:cell property="oper" caption="����" align="center" style="width:5%" />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
