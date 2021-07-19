<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ��ȡ��������б�</title>
</head>

<script language="javascript">
// ���ӳ�ȡ�������
function func_connect()
{
	
	var page = new pageDefine("/txn501030002.ajax");

	page.addParameter("record:etl_hostname","record:etl_hostname");
	page.addParameter("record:etl_portno","record:etl_portno");
	page.addParameter("record:etl_domainname","record:etl_domainname");
	page.addParameter("record:rep_name_en","record:rep_name_en");
	
	page.addParameter("record:user_id","record:user_id");
	page.addParameter("record:user_password","record:user_password");
	page.addParameter("record:rep_foldername","record:rep_foldername");
	page.addParameter("record:wf_name","record:wf_name");	

	page.callAjaxService("Ajax_func_connect");	
}
//���ӳɹ����ҳ�渳ֵ
function func_connect_sucess(arg1,arg2,arg3){
		var wf_flag = document.getElementsByName("record:_flag");
		for( i=0;i<wf_flag.length;i++){
			var wf_radio = wf_flag[i];
			if(wf_radio.checked==true){
				//�б�Ԫ�ص�˳���ܱ仯
				wf_radio.parentNode.parentNode.childNodes(4).firstChild.innerText=arg1;
				wf_radio.parentNode.parentNode.childNodes(5).firstChild.innerText=arg2;
				wf_radio.parentNode.parentNode.childNodes(6).firstChild.innerText=arg3;
				break;
			}
		}
}
//���ӻص�
function Ajax_func_connect(errCode,errDesc,xmlResults){
	if(errCode=="000000"){
		//�������ӳɹ����
		var wf_db_source = _getXmlNodeValues(xmlResults,"record:wf_db_source").toString();
		var wf_db_target = _getXmlNodeValues(xmlResults,"record:wf_db_target").toString();
		var wf_state = _getXmlNodeValues(xmlResults,"record:wf_state").toString();
		func_connect_sucess(wf_db_source,wf_db_target,wf_state);
		alert("���ӳɹ�!");
	}else{
		alert(errDesc);
	}
}
//����,����ǰ�ж������Ƿ�ɹ�
function connect_failure(){
		var state = true;
		var wf_flag = document.getElementsByName("record:_flag");
		for( i=0;i<wf_flag.length;i++){
			var wf_radio = wf_flag[i];
			if(wf_radio.checked==true){
				//�б�Ԫ�ص�˳���ܱ仯
				if(wf_radio.parentNode.parentNode.childNodes(4).firstChild.innerText!=""){
					state=false;
					break;
				}
			}
		}
		return state;
}
// ���г�ȡ�������
function func_run()
{
	var page = new pageDefine("view-workflow_execute_info.jsp", "�鿴��ȡ����ִ����Ϣ", "modal");	
	page.addParameter("record:wf_name","select-key:wf_name");
	page.addParameter("record:rep_foldername","select-key:rep_foldername");
	page.addParameter("record:workflow_id","select-key:workflow_id");
	page.addParameter("record:dbuser","select-key:dbuser");
	page.addParameter("record:domain_name","select-key:domain_name");
	page.addParameter("record:server_name","select-key:server_name");
	page.goPage();
	// page.callAjaxService("Ajax_func_run");
}

//���лص�
function Ajax_func_run(errCode,errDesc,xmlResults){
	if(errCode=="000000"){
		// var text = _getXmlNodeValues(xmlResults,"record:etl_hostname").toString();
		alert("�����ȡ�����Ѿ��ں�̨��ʼ�����ˣ�\n�����������Ƚϴ�ִ�й��̿��ܱȽ������������ظ������");
	}else{
		alert(errDesc);
	}
}
// ���ȳ�ȡ�������
function func_dispatch()
{
	var page = new pageDefine( "detail-scheduler.jsp", "���ȳ�ȡ�������", "modal" );
	page.addParameter( "record:workflow_id", "record:workflow_id" );
	page.addParameter( "record:dbuser", "record:dbuser" );
	page.addParameter( "record:wf_name", "record:wf_name" );
	page.addParameter( "record:rep_foldername", "record:rep_foldername" );
	page.addParameter( "record:domain_name", "record:domain_name" );
	page.addParameter( "record:server_name", "record:server_name" );
	page.goPage();
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ��ȡ��������б�"/>
<freeze:errors/>

<freeze:form action="/txn501030001">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:select property="rep_id" caption="��Ŀ����" valueset="ETL��Ŀ" show="name" style="width:55%"/>
  </freeze:block>

  <freeze:grid property="record" caption="��ȡ�����б�" keylist="workflow_id" width="95%" navbar="bottom" fixrow="false" multiselect="false">
      <freeze:button name="record_run" caption="����" txncode="50106007" enablerule="1" align="right" onclick="func_run();"/>
      <freeze:button name="record_dispatch" caption="����" txncode="50106006" enablerule="1" align="right" onclick="func_dispatch();"/>
      <freeze:hidden property="sys_etl_wf_id" caption="ID" style="width:10%" />    
      <freeze:hidden property="rep_folderid" caption="�ļ���ID" style="width:15%" />
      <freeze:cell property="rep_foldername" caption="�ļ�������" align="center" style="width:15%" />
      <freeze:hidden property="workflow_id" caption="workflow_ID" style="width:15%" />
      <freeze:cell property="wf_name" caption="��ȡ��������" align="center" style="width:15%" />
      <freeze:cell property="wf_ms" caption="��ȡ��������" align="center" style="width:20%" />
      <freeze:cell property="wf_db_source" caption="Դ���ݿ�" align="center" style="width:20%" />
      <freeze:cell property="wf_db_target" caption="Ŀ�����ݿ�" align="center" style="width:20%" />
      <freeze:cell property="wf_state" caption="״̬" align="center" style="width:10%" />
      <freeze:hidden property="dbuser" caption="���ݿ��û�" style="width:10%" />
      <freeze:hidden property="domain_name" caption="����" style="width:10%" />
      <freeze:hidden property="server_name" caption="������" style="width:10%" /> 
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
