<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ taglib uri="/WEB-INF/gwssi.tld" prefix="gwssi" %>
<freeze:html>
<head>
<title>��ѯ�����б�</title>
</head>
<script type="text/javascript" src="/script/lib/jquery171.js"></script>
<jsp:include page="../../share/common/top_datepicker.html"></jsp:include>
<script language="javascript">

// ���Ӷ���
function func_record_addRecord()
{
	var page = new pageDefine( "insert-res_service_targets.jsp", "���ӷ������","modal");
	page.addRecord();
}

// �޸Ķ���
function func_record_updateRecord(idx)
{
	var svrId = getFormFieldValue("record:service_targets_id", idx);
	var page = new pageDefine( "/txn201004.do", "�޸ķ������","modal");
	page.addValue( svrId, "primary-key:service_targets_id" );
	page.updateRecord();
}
//�鿴����
function func_viewConfig(idx)
{
	var svrId = getFormFieldValue("record:service_targets_id", idx);
	var page = new pageDefine( "/txn201009.do", "�鿴�������","modal" );
	page.addValue( svrId, "primary-key:service_targets_id" );
	page.updateRecord();
}
// ɾ������
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn201006.do", "ɾ���������" );
	page.addParameter( "record:service_targets_id", "primary-key:service_targets_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ɾ����������
function func_deleteRecord(idx)
{
	var page = new pageDefine( "/txn201006.do", "ɾ���������" );
	page.addValue( idx, "primary-key:service_targets_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

//У���������Ƿ�����
function checkServiceUse(id){
	if(id==''){
		alert("δѡ��������");
		return;
	}
	var page = new pageDefine( "/txn201011.ajax", "����������Ƿ�����");
	page.addValue( id, "primary-key:service_targets_id" );
	page.callAjaxService("checkCallBack");
}

function checkCallBack(errorCode, errDesc, xmlResult){
	if(errorCode!="000000"){
		alert(errDesc);
	}else{

		var num = _getXmlNodeValue( xmlResult, "record:service_targets_id" );
		var id = _getXmlNodeValue( xmlResult, "primary-key:service_targets_id" );
		
		if(num==""||num==null){
			func_deleteRecord(id);
		}else{
			alert('�÷�������з�����ʹ�ã�������ɾ��!');
			return;
		}
	}
}

// �޸�����ͣ��״̬
function func_record_changeStatus()
{
	var page = new pageDefine( "/txn201010.do", "����/ͣ��" );
	page.addParameter( "record:service_targets_id", "primary-key:service_targets_id" );
	page.addParameter( "record:service_status", "primary-key:service_status" );
	page.deleteRecord( "��ȷ��Ҫ���ø÷��������" );
}
function func_record_changeOneStatus(id,service_status)
{
	var page = new pageDefine( "/txn201010.do", "����/ͣ��" );
	//var service_status = getFormFieldValue("record:service_status");
	page.addValue( id, "primary-key:service_targets_id" );
	page.addValue( service_status, "primary-key:service_status" );
	if(service_status == 'Y') {
		if(confirm("�������ͣ�ú󣬽����ٶ����ṩ���ݷ�����ȷ��Ҫͣ����")){
			page.updateRecord( "�������ͣ�ú󣬽����ٶ����ṩ���ݷ�����ȷ��Ҫͣ����" );
		}
		
	} else if(service_status == 'N') {
		if(confirm("��ȷ��Ҫ���ø÷��������")){
			page.updateRecord( "��ȷ��Ҫ���ø÷��������" );
		}
		
	} else {
		if(confirm("��ȷ��Ҫ���ø÷��������")){
			page.updateRecord( "��ȷ��Ҫ���ø÷��������" );
		}
		
	}
	
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var ids = getFormAllFieldValues("record:service_targets_id");
	var service_status = document.getElementsByName("record:service_status");
	var names = document.getElementsByName("span_record:service_targets_name");
	//var service_status = getFormAllFieldValues("record:service_status");
	for(var i=0; i<ids.length; i++){
	   names[i].innerHTML = '<a href="#" title="'+names[i].innerHTML+'" onclick="func_viewConfig(\''+i+'\');">'+names[i].innerHTML+'</a>';
	   var htm ='<a href="#" title="�޸�" onclick="func_record_updateRecord('+i+')"><div class="edit"></div></a>&nbsp;';
	   htm+='<a href="#" title="ɾ��" onclick="checkServiceUse(\''+ids[i]+'\');"><div class="delete"></div></a>';
	   if(service_status[i].value=="Y"){
	   htm+='<a href="#" title="ͣ��" onclick="func_record_changeOneStatus(\''+ids[i]+'\',\''+service_status[i].value+'\');"><div class="run"></div></a>';
	   }else
	   {
	   htm+='<a href="#" title="����" onclick="func_record_changeOneStatus(\''+ids[i]+'\',\''+service_status[i].value+'\');"><div class="stop"></div></a>';
	   }
	   document.getElementsByName("span_record:oper")[i].innerHTML +=htm;
	 }
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ�����б�"/>
<freeze:errors/>
<gwssi:panel action="txn201001" target="" parts="t1,t2" styleClass="wrapper">
   <gwssi:cell id="t1" name="�������" key="service_targets_type,service_targets_id" isGroup="true" data="svrTarget" pop="true"  maxsize="10" />
   <!-- <gwssi:cell id="t2" name="����ʱ�� " key="created_time" data="svrInterface" date="true"/>-->
 </gwssi:panel>

<freeze:form action="/txn201001">
  <freeze:frame property="select-key" >
     <freeze:hidden property="service_targets_id" caption="�������ID" />
     <freeze:hidden property="created_time" caption="����ʱ��" />
  </freeze:frame>
  <freeze:grid property="record" checkbox="false" caption="��ѯ��������б�" keylist="service_targets_id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="����" txncode="201003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:hidden property="service_targets_id" caption="�������ID"  />
      <freeze:cell property="@rowid" caption="���" align="middle" style="width:5%"/>
      <freeze:cell property="service_targets_name" caption="�����������" align="center" style="width:35%" />
      <freeze:cell property="service_targets_no" caption="����������" align="center" style="width:20%" />
      <freeze:cell property="service_targets_type" align="center" caption="�����������" valueset="��Դ����_�����������" style="width:15%" />
    
      <freeze:hidden property="creator_name" align="center" caption="������"  style="width:10%"/>
      <freeze:hidden property="created_time" align="center" caption="��������"  style="width:10%"/>
      <freeze:cell property="last_modify_name" align="center" caption="����޸���"  style="width:13%"/>
      <freeze:cell property="last_modify_time" align="center" caption="����޸�����"  style="width:12%"/>
      <freeze:hidden property="ip" caption="��IP"  />
     
      <freeze:hidden property="service_status" align="center" caption="����״̬" style="width:15%" valueset="��Դ����_һ�����״̬" />
      <freeze:cell property="oper" caption="����" align="center" style="width:125px" />
      <freeze:hidden property="is_bind_ip" caption="�Ƿ��IP" valueset="��������"  />
      <freeze:hidden property="service_desc" caption="�����������"  visible="false" />
      
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
