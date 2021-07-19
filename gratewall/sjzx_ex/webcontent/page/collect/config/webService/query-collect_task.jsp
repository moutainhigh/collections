<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�ɼ������б�</title>
</head>

<script language="javascript">

// ���Ӳɼ�����
function func_record_addRecord()
{
    //var page = new pageDefine( "/txn30101007.do", "���Ӳɼ�����");
    //page.addValue(" ","primary-key:collect_task_id");
    var page = new pageDefine( "/page/collect/config/insert-collect_task_tab.jsp", "���Ӳɼ�����");
	page.addRecord();
	
}

// �޸Ĳɼ�����
function func_record_updateRecord(idx,type,status,collect_status)
{
	
	if(type=="WebService"){
		var page = new pageDefine( "/txn30101004.do", "�޸Ĳɼ�����webservice" );
		page.addValue(idx,"primary-key:collect_task_id");
		page.updateRecord();
	}else if(type=="SOCKET"){
			var page = new pageDefine( "/txn30101052.do", "�޸Ĳɼ�����socket" );
			page.addValue(idx,"primary-key:collect_task_id");
			page.updateRecord();
	}else if(type=="JMS��Ϣ"){
			var page = new pageDefine( "/txn30101062.do", "�޸Ĳɼ�����jms" );
			page.addValue(idx,"primary-key:collect_task_id");
			page.updateRecord();
	}else if(type=="FTP"){
			var page = new pageDefine( "/txn30101013.do", "�޸Ĳɼ�����ftp" );
			page.addValue(idx,"primary-key:collect_task_id");
			page.updateRecord();
	}else if(type=="�ļ��ϴ�"){
		if(status=="N"){
			alert("ͣ�õķ�����������޸Ĳ���!");
		}else if(status=="Y"){
			//if(collect_status!="δ�ɼ�"){
				//alert("����ɵĲɼ����������������޸�!");
			//}else{
				
				var page = new pageDefine( "/txn30101022.do", "�޸Ĳɼ������ļ��ϴ�" );
				page.addValue(idx,"primary-key:collect_task_id");
				page.updateRecord();
			//}
			
		}	
	}else if(type=="���ݿ�"){
		var page = new pageDefine( "/txn30101034.do", "�޸Ĳɼ��������ݿ�");
		page.addValue(idx,"primary-key:collect_task_id");
		page.updateRecord();
	}else {
		alert("����Ŭ�����С���");
	}
	
}

// ɾ���ɼ�����
function func_record_deleteRecord(idx,type)
{
	if(type=="WebService"||type=="FTP"||type=="SOCKET"||type=="JMS��Ϣ"){
		var page = new pageDefine( "/txn30101005.do", "ɾ���ɼ�����" );
		page.addValue(idx,"primary-key:collect_task_id" );
		page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
	}else if(type=="�ļ��ϴ�"){
		var page = new pageDefine( "/txn30101024.do", "ɾ���ɼ�����" );
		page.addValue(idx,"primary-key:collect_task_id" );
		page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
	}else if(type=="���ݿ�"){
		var page = new pageDefine( "/txn30101036.do", "ɾ���ɼ�����" );
		page.addValue(idx,"primary-key:collect_task_id" );
		page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
	}else {
		alert("����Ŭ�����С���");
	}
}
//�鿴�ɼ����ݱ���Ϣ
function func_record_viewRecord(idx,type)
{
	if(type=="WebService"){
		var page = new pageDefine( "/txn30101006.do", "�鿴�ɼ�����" );
		page.addValue(idx, "primary-key:collect_task_id" );
		page.updateRecord();
	}else if(type=="SOCKET"||type=="JMS��Ϣ"){
	
		var page = new pageDefine( "/txn30101016.do", "�鿴�ɼ�����" );
		page.addValue(idx, "primary-key:collect_task_id" );
		page.updateRecord();
	}else if(type=="FTP"){
		var page = new pageDefine( "/txn30101015.do", "�鿴�ɼ�����" );
		page.addValue(idx, "primary-key:collect_task_id" );
		page.updateRecord();
	}else if(type=="�ļ��ϴ�"){
		alert("����Ŭ�����С���");
	}else {
		alert("����Ŭ�����С���");
	}
}
// �鿴�ɼ�����-ҳ�水ťչʾ
function func_record_viewRecord1(index,type)
{	

	var gridname = getGridDefine("record");
	var id = gridname.getAllFieldValues( "collect_task_id" )[index];
	if(type=="WebService"){
		var page = new pageDefine( "/txn30101006.do", "�鿴�ɼ�����" );
		page.addValue( id, "primary-key:collect_task_id" );
		page.updateRecord();
	}else if(type=="SOCKET"||type=="JMS��Ϣ"){
		var page = new pageDefine( "/txn30101016.do", "�鿴�ɼ�����" );
		page.addValue( id, "primary-key:collect_task_id" );
		page.updateRecord();
	}else if(type=="FTP"){
		var page = new pageDefine( "/txn30101015.do", "�鿴�ɼ�����" );
		page.addValue(id, "primary-key:collect_task_id" );
		page.updateRecord();
	}else if(type=="�ļ��ϴ�"){
		var page = new pageDefine( "/txn30101025.do", "�鿴�ɼ�����" );
		page.addValue(id, "primary-key:collect_task_id" );
		page.updateRecord();
		
	}else if(type=="���ݿ�"){
		var page = new pageDefine( "/txn30101035.do", "�鿴�ɼ�����" );
		page.addValue(id, "primary-key:collect_task_id" );
		page.updateRecord();
		
	}else {
		alert("����Ŭ�����С���");
	}
}
//��ȡ����
function func_record_getData(idx,type)
{
	if(type=="WebService"){
		var page = new pageDefine( "/txn30101008.ajax", "��ȡ����");
	 	page.addValue(idx,"primary-key:collect_task_id");
	 	page.addValue(type,"primary-key:collect_type");
		page.callAjaxService('getData');
	}else if(type=="FTP"){
		var page = new pageDefine( "/txn30101008.ajax", "��ȡ����");
	 	page.addValue(idx,"primary-key:collect_task_id");
	 	page.addValue(type,"primary-key:collect_type");
		page.callAjaxService('getData');
	}else if(type=="�ļ��ϴ�"){
		alert("����Ŭ�����С���");
	}else {
		alert("����Ŭ�����С���");
	}
}
function getData(errCode,errDesc,xmlResults){
		
		
		    alert("��ȡ���ݳɹ�!");
}
function getParameter(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
    var parameter = 'input-data:service_targets_id=' + getFormFieldValue('record:service_targets_id');
	return parameter;
}
function func_record_changeOneStatus(id,task_status,type)
{
	if(type=="WebService"||type=="SOCKET"||type=="JMS��Ϣ"||type=="FTP"||type=="�ļ��ϴ�"||type=="���ݿ�"){
		var page = new pageDefine( "/txn30101010.do", "����/ͣ��" );
		page.addValue( id, "primary-key:collect_task_id" );
		page.addValue( task_status, "primary-key:task_status" );
		page.deleteRecord( "�Ƿ��޸ķ���״̬" );
	//}else if(type=="�ļ��ϴ�"){
		//alert("����Ŭ�����С���");
	}else {
		alert("����Ŭ�����С���");
	}
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{

	var ids = getFormAllFieldValues("record:collect_task_id");
	var data_source_id=getFormAllFieldValues("record:data_source_id");
	var task_status = getFormAllFieldValues("record:task_status");
	var type = getFormAllFieldValues("record:collect_type");
	var collect_status = getFormAllFieldValues("record:collect_status");
	
	var log_file_path =  getFormAllFieldValues("record:log_file_path");
	
	for(var i=0; i<ids.length; i++){
	   //var htm='<a href="#" title="�鿴" onclick="func_record_viewRecord(\''+ids[i]+'\',\''+type[i]+'\');"><div class="detail"></div></a>&nbsp;';
	   var htm='<a href="#" title="�޸�" onclick="func_record_updateRecord(\''+ids[i]+'\',\''+type[i]+'\',\''+task_status[i]+'\',\''+collect_status[i]+'\');"><div class="edit"></div></a>&nbsp;';
	   htm+='<a href="#" title="ɾ��" onclick="func_record_deleteRecord(\''+ids[i]+'\',\''+type[i]+'\');"><div class="delete"></div></a>&nbsp;&nbsp;';
	   if(task_status[i]=="Y"){
	   htm+='<a href="#" title="ͣ��" onclick="func_record_changeOneStatus(\''+ids[i]+'\',\''+task_status[i]+'\',\''+type[i]+'\');"><div class="run"></div></a>&nbsp;';
	   }else
	   {
	   htm+='<a href="#" title="����" onclick="func_record_changeOneStatus(\''+ids[i]+'\',\''+task_status[i]+'\',\''+type[i]+'\');"><div class="stop"></div></a>&nbsp;';
	   }
	   
	   if(type[i]=="�ļ��ϴ�"){
	   
	   }  
	   //htm+='<a href="#" title="��ȡ����" onclick="func_record_getData(\''+ids[i]+'\',\''+type[i]+'\',\''+data_source_id[i]+'\');"><div class="download"></div></a>';
	   
	   //�ɼ���־  add by dwn 20130625
	   var filepath = log_file_path[i];
		var num = filepath.lastIndexOf("/");
		var filename = filepath.substring(num+1);
		var url = "/downloadFile?file="+filepath+"&&fileName="+filename;
	   
	   var colSta_htm="";
	   if(collect_status[i]=="�ɼ��ɹ�"){
	   		colSta_htm='<a href="'+url+'" title="������־�ļ�" onclick=""><div class="collect_normal"></div></a>&nbsp;';
	   }else if(collect_status[i]=="�ɼ�ʧ��"){
	   		colSta_htm='<a href="'+url+'" title="������־�ļ�" onclick=""><div class="collect_failed"></div></a>&nbsp;';
	   }
	   document.getElementsByName("span_record:collect_status")[i].innerHTML =collect_status[i]+colSta_htm;
	   document.getElementsByName("span_record:oper")[i].innerHTML +=htm;
	 }
	 
	 var names = getFormAllFieldValues("record:task_name");
	 for(var i=0; i<names.length; i++){
	   htm = '<a href="#" title="����鿴��ϸ��Ϣ" onclick="func_record_viewRecord1(\''+i+'\',\''+type[i]+'\');">'+names[i]+'</a>';
	   document.getElementsByName("span_record:task_name")[i].innerHTML =htm;
	}
	
	
	
}

_browse.execute( '__userInitPage()' );

</script>
<freeze:body >
<freeze:title caption="��ѯ�ɼ������б�"/>
<freeze:errors/>

<freeze:form action="/txn30101001">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>
<freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:browsebox property="service_targets_id" caption="�����������" show="name" valueset="��Դ����_�ɼ���������������"  style="width:95%"/>
      <freeze:text property="task_name" caption="��������"   style="width:95%"/>
      <freeze:select property="collect_type" caption="�ɼ�����"  show="name" valueset="�ɼ�����_�ɼ�����"  style="width:95%"/>
      <freeze:select property="collect_status" caption="�ɼ�״̬"  show="name" valueset="�ɼ�����_�ɼ�״̬"  style="width:95%"/>
      <freeze:select property="task_status" caption="����״̬" valueset="��Դ����_һ�����״̬" show="name" style="width:95%"/>
      <freeze:datebox property="created_time_start" caption="��������" prefix="<table width='95%' border='0' cellpadding='0' cellspacing='0'><tr><td width='47.5%'>" style="width:100%"/></td>
      <td width='5%'>��</td><td width='47.5%'>
      <freeze:datebox property="created_time_end" caption="��������" style="width:100%" colspan="0"/>
      </td></tr></table>
  </freeze:block>
<br/>
  <freeze:grid property="record" caption="��ѯ�ɼ������б�" keylist="collect_task_id" width="95%"  multiselect="false" checkbox="false" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="���Ӳɼ�����" txncode="30101003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
     
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" />
      <freeze:cell property="@rowid" caption="���"  style="width:6%" align="center" />
      <freeze:cell property="service_targets_id" caption="�������" show="name" valueset="��Դ����_���������������" style="width:10%; text-align:center;" />
      <freeze:hidden property="data_source_id" caption="����Դ" style="width:18%"/>
       <freeze:cell property="collect_type" caption="�ɼ�����"  show="name" valueset="�ɼ�����_�ɼ�����" style="width:10%" />
      <freeze:cell property="task_name" caption="��������" style="width:20%"/>
      <freeze:hidden property="created_time" caption="����ʱ��" style="width:12%" />
      
      <freeze:cell property="scheduling_day1" caption="�ɼ�����" style="width:30%"/>
      <freeze:cell property="start_time" caption="��ʼʱ��" style="width:10%"/>
      
      <freeze:cell property="oper" caption="����" style="width:12%; text-align: center;" />
      
      <freeze:hidden property="task_description" caption="��������"   />
      <freeze:hidden property="record" caption="����˵��"   />
      <freeze:hidden property="task_status" caption="����״̬"  style="width:5%"  />
      <freeze:hidden property="is_markup" caption="��Ч���"  />
      <freeze:hidden property="creator_id" caption="������ID"  />
      <freeze:hidden property="last_modify_id" caption="����޸���ID" />
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��" />
      <freeze:hidden property="log_file_path" caption="��־�ļ�·��" />
  </freeze:grid>

</freeze:form>

</freeze:body>
</freeze:html>
