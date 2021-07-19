<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�鿴workflowִ����Ϣ</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery.js"></script>
<style type="text/css">
div{
	background-color:#F1F5FF;
}
</style>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn501030001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var page = new pageDefine("/txn50106007.ajax");	
	page.addParameter("select-key:wf_name","select-key:wf_name");
	page.addParameter("select-key:rep_foldername","select-key:rep_foldername");
	page.addParameter("select-key:workflow_id","select-key:workflow_id");
	page.addParameter("select-key:dbuser","select-key:dbuser");
	page.callAjaxService("Ajax_func_getExeInfo");
}

//���лص�
function Ajax_func_getExeInfo(errCode,errDesc,xmlResults){
	if(errCode=="000000"){
		// var text = _getXmlNodeValues(xmlResults,"record:etl_hostname").toString();
		// alert("�����ȡ�����Ѿ��ں�̨��ʼ�����ˣ�\n�����������Ƚϴ�ִ�й��̿��ܱȽ������������ظ������");
		var start_time = $("record/start_time", xmlResults).text();
		var end_time = $("record/end_time", xmlResults).text();
		if(start_time && start_time!=""){	
			if (end_time && end_time!=""){
				// $("span#currStatus").html("��ֹͣ!");
				// $("input#reExecute").attr("disabled", "disabled");
				func_run();
				window.setTimeout("timeOutCallBack()", 3000);
			}else{
				$("span#currStatus").html("����ִ��!");
				$("input#reExecute").attr("disabled", "disabled");
				window.setTimeout("timeOutCallBack()", 3000);
			}
		}else{
			// $("span#currStatus").html("��ֹͣ!");
			func_run();
			window.setTimeout("timeOutCallBack()", 3000);
		}
	}else{
		alert(errDesc);
	}
}

// ���г�ȡ�������
function func_run()
{
	var page = new pageDefine("/txn501030003.ajax");	
	page.addParameter("select-key:wf_name","record:wf_name");
	page.addParameter("select-key:rep_foldername","record:rep_foldername");
	page.addParameter("select-key:workflow_id","record:workflow_id");
	page.addParameter("select-key:dbuser","record:dbuser");
	page.addParameter("select-key:domain_name","record:domain_name");
	page.addParameter("select-key:server_name","record:server_name");
	page.callAjaxService("Ajax_func_run");
}

//���лص�
function Ajax_func_run(errCode,errDesc,xmlResults){
	// alert("before");
	if(errCode=="000000"){
		$("span#currStatus").html("����ִ��!");
		$("input#reExecute").attr("disabled", "disabled");
	}else{
		alert(errDesc);
	}
}

function timeOutCallBack()
{
	var page = new pageDefine("/txn50106007.ajax");	
	page.addParameter("select-key:wf_name","select-key:wf_name");
	page.addParameter("select-key:rep_foldername","select-key:rep_foldername");
	page.addParameter("select-key:workflow_id","select-key:workflow_id");
	page.addParameter("select-key:dbuser","select-key:dbuser");
	page.callAjaxService("Ajax_timeOutCallBack");
}

function Ajax_timeOutCallBack(errCode,errDesc,xmlResults){
	if(errCode=="000000"){
		var start_time = $("record/start_time", xmlResults).text();
		var end_time = $("record/end_time", xmlResults).text();
		if(start_time && start_time!=""){	
			if (end_time && end_time!=""){
				$("span#currStatus").html("��ֹͣ!");
				$("input#reExecute").removeAttr("disabled");
			}else{
				$("span#currStatus").html("����ִ��!");
				$("input#reExecute").attr("disabled", "disabled");
				window.setTimeout("timeOutCallBack()", 3000);
			}
		}else{
			$("span#currStatus").html("ִ�й�������������!");
			$("input#reExecute").attr("disabled", "disabled");
		}
	}else{
		alert(errDesc);
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ��ȡ����ִ����Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn50106007">
  <freeze:frame property="select-key" width="95%">
      <freeze:hidden property="workflow_id" caption="workflow_id" style="width:95%"/>
      <freeze:hidden property="dbuser" caption="dbuser" style="width:95%"/>
      <freeze:hidden property="wf_name" caption="wf_name" style="width:95%"/>
      <freeze:hidden property="rep_foldername" caption="rep_foldername" style="width:95%"/>
      <freeze:hidden property="domain_name" caption="domain_name" style="width:95%"/>
      <freeze:hidden property="server_name" caption="server_name" style="width:95%"/>
  </freeze:frame>
	<div style="text-align:center;font-size:32px;padding-top:60px;padding-bottom:60px;margin:10px;border:1px solid #7F9DB9;">��ǰ����״̬��<span id="currStatus" style="color:red;font-weight:900;"></span></div>
	<div align="center" style="margin-top:20px;">
		<input id="reExecute" type="button" value="����ִ��" disabled="disabled" style="width:60px;"  onclick="__userInitPage();" class="menu" />
		<input type="button" value="����" class="menu" style="width:60px;" onclick="func_record_goBack();" />
	</div>
</freeze:form>
</freeze:body>
</freeze:html>
