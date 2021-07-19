<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ taglib uri="/WEB-INF/gwssi.tld" prefix="gwssi" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�ɼ������б�</title>
</head>

<script type="text/javascript" src="/script/lib/jquery171.js"></script>
<jsp:include page="/page/share/common/top_datepicker.html"></jsp:include>
<script type="text/javascript">

// ���Ӳɼ�����
function func_record_addRecord()
{
    //var page = new pageDefine( "/txn30101007.do", "���Ӳɼ�����");
    //page.addValue(" ","primary-key:collect_task_id");
    //��ת�������ɼ�����ı�ǩҳ
    var page = new pageDefine( "/page/collect/config/insert-collect_task_tab.jsp", "���Ӳɼ�����");
	page.addRecord();
	
}

// �޸Ĳɼ�����
function func_record_updateRecord(idx,type,status,collect_status)
{
	
	if(type=="WebService"){
		var page = new pageDefine( "/txn30101004.do", "�޸Ĳɼ�����webservice" );
		page.addValue(idx,"primary-key:collect_task_id");
		//page.updateRecord();
	}else if(type=="SOCKET"){
			var page = new pageDefine( "/txn30101052.do", "�޸Ĳɼ�����socket" );
			page.addValue(idx,"primary-key:collect_task_id");
			//page.updateRecord();
	}else if(type=="JMS��Ϣ"){
			var page = new pageDefine( "/txn30101062.do", "�޸Ĳɼ�����jms" );
			page.addValue(idx,"primary-key:collect_task_id");
			//page.updateRecord();
	}else if(type=="FTP"){
			var page = new pageDefine( "/txn30101013.do", "�޸Ĳɼ�����ftp" );
			page.addValue(idx,"primary-key:collect_task_id");
			//page.updateRecord();
	}else if(type=="�ļ��ϴ�"){
		if(status=="N"){
			alert("ͣ�õķ�����������޸Ĳ���!");
			return;
		}else if(status=="Y"){
			//if(collect_status!="δ�ɼ�"){
				//alert("����ɵĲɼ����������������޸�!");
			//}else{
				
				var page = new pageDefine( "/txn30101022.do", "�޸Ĳɼ������ļ��ϴ�" );
				page.addValue(idx,"primary-key:collect_task_id");
				//page.updateRecord();
			//}
			
		}	
	}else if(type=="���ݿ�"){
		//var page = new pageDefine( "/txn30101034.do", "�޸Ĳɼ��������ݿ�");
		//page.addValue(idx,"primary-key:collect_task_id");
		//page.updateRecord();
		var page = new pageDefine( "/txn30101037.do", "�޸Ĳɼ��������ݿ�");
		//var taskId = getFormFieldValue("primary-key:collect_task_id");
		page.addValue(idx,"primary-key:collect_task_id");
		//page.addParameter("primary-key:collect_task_id","primary-key:collect_task_id");
		page.goPage();
	}else if(type=="ETL"){
		var page = new pageDefine( "/txn30300004.do", "�޸Ĳɼ��������ݿ�");
		page.addValue(idx,"primary-key:etl_id");
		//page.updateRecord();
	}else {
		alert("����Ŭ�����С���");
		return;
	}
	page.addParameter("select-key:service_targets_id","select-key:service_targets_id");
	page.addParameter("select-key:collect_type","select-key:collect_type");
	page.addParameter("select-key:task_status","select-key:task_status");
	page.updateRecord();
	
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
	}else if(type=="ETL"){
		var page = new pageDefine( "/txn30300005.do", "ɾ���ɼ�����" );
		page.addValue(idx,"primary-key:etl_id" );
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
		var page = new pageDefine( "/txn30101006.do", "�鿴�ɼ�����", "modal", "800", "600" );
		page.addValue( id, "primary-key:collect_task_id" );
		page.updateRecord();
	}else if(type=="SOCKET"||type=="JMS��Ϣ"){
		var page = new pageDefine( "/txn30101016.do", "�鿴�ɼ�����", "modal", "800", "600" );
		page.addValue( id, "primary-key:collect_task_id" );
		page.updateRecord();
	}else if(type=="FTP"){
		var page = new pageDefine( "/txn30101015.do", "�鿴�ɼ�����", "modal", "800", "600" );
		page.addValue(id, "primary-key:collect_task_id" );
		page.updateRecord();
	}else if(type=="�ļ��ϴ�"){
		var page = new pageDefine( "/txn30101025.do", "�鿴�ɼ�����" , "modal", "800", "600");
		page.addValue(id, "primary-key:collect_task_id" );
		page.updateRecord();
		
	}else if(type=="���ݿ�"){
		var page = new pageDefine( "/txn30101035.do", "�鿴�ɼ�����", "modal", "800", "600" );
		page.addValue(id, "primary-key:collect_task_id" );
		
		page.updateRecord();
		
	}else if(type=="ETL"){
		var page = new pageDefine( "/txn30300008.do", "�鿴�ɼ�����", "modal", "800", "600" );
		page.addValue(id, "primary-key:etl_id" );
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

//�鿴�������
function func_viewConfig(idx) {
	
	var svrId = getFormFieldValue("record:service_targets_id1", idx);
	var page = new pageDefine( "/txn201009.do", "�鿴�������","modal" );
	page.addValue( svrId, "primary-key:service_targets_id" );
	page.updateRecord();
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
	//alert(ids.length);
	for(var i=0; i<ids.length; i++){
	   //var htm='<a href="#" title="�鿴" onclick="func_record_viewRecord(\''+ids[i]+'\',\''+type[i]+'\');"><div class="detail"></div></a>&nbsp;';
	   var htm='';
	   if(task_status[i]!="G"){//�ǹ鵵��¼�ſ����޸�
		   htm='<a href="#" title="�޸�" onclick="func_record_updateRecord(\''+ids[i]+'\',\''+type[i]+'\',\''+task_status[i]+'\',\''+collect_status[i]+'\');"><div class="edit"></div></a>&nbsp;';
		   htm+='<a href="#" title="ɾ��" onclick="func_record_deleteRecord(\''+ids[i]+'\',\''+type[i]+'\');"><div class="delete"></div></a>&nbsp;&nbsp;';
		   if(type[i]=="ETL" ){
		   }else{
		   		if(task_status[i]=="Y"){
		   		htm+='<a href="#" title="ͣ��" onclick="func_record_changeOneStatus(\''+ids[i]+'\',\''+task_status[i]+'\',\''+type[i]+'\');"><div class="run"></div></a>&nbsp;';
		   	  }else
		   	  {
		   		htm+='<a href="#" title="����" onclick="func_record_changeOneStatus(\''+ids[i]+'\',\''+task_status[i]+'\',\''+type[i]+'\');"><div class="stop"></div></a>&nbsp;';
		   	  }
		   }
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
	   //console.log(document.getElementsByName("span_record:collect_status").length);
	   //document.getElementsByName("span_record:collect_status")[i].innerHTML =collect_status[i]+colSta_htm;
	   document.getElementsByName("span_record:oper")[i].innerHTML +=htm;
	 }
	 
	 var names = getFormAllFieldValues("record:task_name");
	 var targetnames = getFormAllFieldValues("record:service_targets_id");
	 for(var i=0; i<names.length; i++){
	   htm = '<a href="#" title="����鿴��ϸ��Ϣ" onclick="func_record_viewRecord1(\''+i+'\',\''+type[i]+'\');">'+names[i]+'</a>';
	   
	   
	   if(type[i]=="�ļ��ϴ�"){
	   		var filepath = log_file_path[i];
	   		
			var num = filepath.lastIndexOf("/");
			var filename = filepath.substring(num+1);
			var url = "/downloadFile?file="+filepath+"&&fileName="+filename;
	   
	   		var colSta_htm="";
	   		
	   		//00:δ�ɼ���01�����ڲɼ���02���ɼ��ɹ���03���ɼ�ʧ��
	   		if(collect_status[i]=="02"){
	   			colSta_htm='<a href="'+url+'" title="������־�ļ�" onclick=""><div class="collect_normal"></div></a>&nbsp;';
	   		}else if(collect_status[i]=="03"){
	   			colSta_htm='<a href="'+url+'" title="������־�ļ�" onclick=""><div class="collect_failed"></div></a>&nbsp;';
	   		}
	   		htm = htm + colSta_htm;
	   		
	   }
	   
	   document.getElementsByName("span_record:task_name")[i].innerHTML =htm;
	   
	   var htm2 = '<a href="#" title="����鿴��ϸ��Ϣ" onclick="func_viewConfig(\''
			+ i + '\');">' + targetnames[i] + '</a>';
		document.getElementsByName("span_record:service_targets_id")[i].innerHTML = htm2;
	}
	
	
	
}

_browse.execute( '__userInitPage()' );

</script>
<freeze:body >
<freeze:title caption="��ѯ�ɼ������б�"/>
<freeze:errors/>
<gwssi:panel action="txn30101001" target="" parts="t1,t2,t3" styleClass="wrapper">
  <gwssi:cell id="t1" name="��������" key="service_targets_type,service_targets_id" isGroup="true" data="svrTarget" maxsize="10" />
  <gwssi:cell id="t2" name="�ɼ�����" key="collect_type" data="type_data" pop="false" move2top="false" maxsize="10" />
  <gwssi:cell id="t3" name="����״̬" key="task_status" data="state_data"/>
</gwssi:panel>
<freeze:form action="/txn30101001.do">
  <freeze:grid property="record" caption="��ѯ�ɼ������б�" keylist="collect_task_id" width="95%"  multiselect="false" checkbox="false" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="���Ӳɼ�����" txncode="30101003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
     
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" />
      <freeze:hidden property="service_targets_id1" caption="�������ID"  />
      <freeze:cell property="@rowid" caption="���"  style="width:5%" align="center" />
      <freeze:cell property="service_targets_id" caption="�������" show="name" valueset="��Դ����_���������������" style="width:10%; text-align:center;" />
      <freeze:cell property="task_name" caption="��������" style="width:20%"/>
      <freeze:cell property="collect_type" caption="�ɼ�����" valueset="�ɼ�����_�ɼ�����" style="width:10%;text-align: center;" />
      <freeze:cell property="scheduling_day1" caption="�ɼ�����" style="width:18%;text-align: center;"/>
      <freeze:cell property="start_time" caption="��ʼʱ��" style="width:10%;text-align: center;"/>
       <freeze:cell property="name" align="center" caption="����޸���"  style="width:10%"/>
	  <freeze:cell property="time" align="center" caption="����޸�����"  style="width:10%"/>
      <freeze:cell property="oper" caption="����" style="width:12%; text-align: center;" />
      
      <freeze:hidden property="data_source_id" caption="����Դ" style="width:18%"/>
      <freeze:hidden property="created_time" caption="����ʱ��" style="width:12%" />
      <freeze:hidden property="task_description" caption="��������"   />
      <freeze:hidden property="record" caption="����˵��"   />
      <freeze:hidden property="task_status" caption="����״̬"  style="width:5%"  />
      <freeze:hidden property="is_markup" caption="��Ч���"  />
      <freeze:hidden property="creator_id" caption="������ID"  />
      <freeze:hidden property="last_modify_id" caption="����޸���ID" />
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��" />
      <freeze:hidden property="log_file_path" caption="��־�ļ�·��" />
      <freeze:hidden property="collect_status" caption="�ɼ�״̬"  />
  </freeze:grid>

</freeze:form>

</freeze:body>
</freeze:html>
