<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.TxnContext" %>
<%@ page import="cn.gwssi.common.context.DataBus" %>
<freeze:html width="650" height="350">
<head>
<title>��ѯϵͳʹ����������б�</title>
<%
String path=java.util.ResourceBundle.getBundle("app").getString("docFilePath");
TxnContext context = (TxnContext)request.getAttribute("freeze-databus");
String userName=context.getRecord("oper-data").getValue("oper-name");
System.out.println(userName);
%>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script language="javascript">

// ����ϵͳʹ���������
function func_record_addRecord()
{
	var page = new pageDefine( "insert-log_report_create.jsp", "����ϵͳʹ���������", "modal" );
	page.addRecord();
}

// ����ϵͳʹ���������
function func_record_addRecord1()
{
	var page = new pageDefine( "insert-log_report_create1.jsp", "����ϵͳʹ���������", "modal" );
	page.addRecord();
}

// �޸�ϵͳʹ���������
function record_updateRecord()
{
    clickFlag=0;
	var state = getFormFieldValues('record:state');
	if(state=="����"){
	   alert("�ѷ����ı��治���޸�!");
	} else {
		var page = new pageDefine( "/txn620200104.do", "�޸�ϵͳʹ���������");
		page.addParameter( "record:log_report_create_id", "primary-key:log_report_create_id" );
		page.updateRecord();
	}
	
}

//�˻ر���
function func_record_returnRecord(){
	clickFlag=0;
	if(confirm("�Ƿ�ȷ���˻ظñ��棿")){
		var state = getFormFieldValues('record:state');
		if(state!="����"){
			alert("ֻ�з����ı���ſ����˻�");
			//return;
			//_formSubmit(null, '���ڴ����¼ ... ...');
		}else{
			var page = new pageDefine( "/txn620200109.do", "�˻�ϵͳʹ���������");
			page.addParameter("record:log_report_create_id", "select-key:log_report_create_id");
			page.updateRecord();
		}
	}
}

// ����ϵͳʹ���������
function func_record_publishRecord()
{
	clickFlag=0;
	var state = getFormFieldValues('record:state');
	if(state=="����"){
		alert("�����ѷ���");
		//return;
		//_formSubmit( null, '���ڴ����¼ ... ...');
	}else{
		var page = new pageDefine( "/txn620200107.do", "�޸�ϵͳʹ���������");
		page.addParameter( "record:log_report_create_id", "select-key:log_report_create_id" );
		page.updateRecord();
	}
	
}

// ɾ��ϵͳʹ���������
function func_record_deleteRecord()
{
	clickFlag=0;
	var state = getFormFieldValues('record:state');
	
	if(state=='����'){//����
		alert("�ѷ����ı��治����ɾ��");
		//return;
	//	_formSubmit( null, '���ڴ����¼ ... ...');
		
	}else {
		var page = new pageDefine( "/txn620200105.do", "ɾ��ϵͳʹ���������" );
		page.addParameter( "record:log_report_create_id", "primary-key:log_report_create_id" );
		page.addParameter( "record:path", "primary-key:path" );
		//page.addParameter( id, "primary-key:log_report_create_id" );
		//page.addParameter( path, "primary-key:path" );
		page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
	}
	
}

// �޸�ϵͳʹ���������
function addOperRecord(id)
{
  var page = new pageDefine( "/txn620200108.ajax", "�޸�ϵͳʹ���������");
  page.addValue( id, "select-key:log_report_create_id" );
  page.callAjaxService('addRecordBack');
}

function addRecordBack(errCode, errDesc, xmlResults){
      if(errCode == '000000'){
        
	  }
}

function toTurn(id,state){
  if(state=="����"){
  	addOperRecord(id);
  }
  
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
	var rootPath = '<%=request.getContextPath()%>';
	var operate = getFormAllFieldValues("record:operate1");
	var path = getFormAllFieldValues("record:path");
	var filename = getFormAllFieldValues("record:filename");
	var states = getFormAllFieldValues("record:state");
	var ids = getFormAllFieldValues("record:log_report_create_id");
	var reportNames = getFormAllFieldValues("record:report_name");
	var creator = getFormAllFieldValues("record:creator");
	for(var i=0;i<operate.length;i++){
		var url = filename[i];
		var id = ids[i];
		var state = states[i]; 
		var path1 = path[i];
		var reportName = reportNames[i];
	    var userName='<%=userName%>';
		if(state=="����" || userName==creator[i]){
		    var rname=encodeURI(reportName)+'.doc';
  	        var url = "/downloadFile?file=<%=path%>"+url+"&&fileName="+rname;
  	        var ihtml ="<a title='����' href='"+url+"' onclick='toTurn(\""+id+"\",\""+state+"\")';><div class='download'></div></a>";
			document.getElementsByName("span_record:operate1")[i].innerHTML = ihtml;
		}
	}
	
	$(".radioNew").each(function(index){
		$($(this).prev()[0]).css("display","");
		$($(this).prev()[0]).css("margin-left","-1000");
		$(this).click(function(){
			$(".radioNew").css("background-position-y","bottom");
			$(this).prev()[0].click();
			if($(this).prev()[0].checked){
				$(this).css("background-position-y","top");
			}
		});
		if($(this).prev()[0].checked){
			$(this).css("background-position-y","top");
		}
	});
}


_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯϵͳʹ����������б�"/>
<freeze:errors/>

<freeze:form action="/txn620200101">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:hidden property="log_report_create_id" caption="��־����id" datatype="string" maxlength="32" style="width:90%"/>
      <freeze:text property="report_name" caption="��������"  style="width:90%"/>
      <freeze:select property="state" caption="����״̬" valueset="����״̬" style="width:90%"/>
      <freeze:datebox property="create_date_start" caption="��������" prefix="<table width='90%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%"/>
    </td><td width='5%'>��</td><td width='45%'>
    <freeze:datebox property="create_date_end" caption="��������" style="width:100%" colspan="0"/>
    </td></tr></table>
      <freeze:datebox property="publish_date_start" caption="��������" prefix="<table width='90%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%"/>
    </td><td width='5%'>��</td><td width='45%'>
    <freeze:datebox property="publish_date_end" caption="��������" style="width:100%" colspan="0"/>
    </td></tr></table>
  </freeze:block>
<br />
  <freeze:grid property="record" caption="��ѯϵͳʹ����������б�" keylist="log_report_create_id" width="95%" navbar="bottom" fixrow="false" multiselect="false">
      <freeze:button name="record_addRecord" caption="����" txncode="620200103" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸�" txncode="620200104" enablerule="1" hotkey="UPDATE" align="right" onclick="record_updateRecord();"/>
      <freeze:button name="record_publishRecord" caption="����" txncode="620200107" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_publishRecord();"/>
      <freeze:button name="record_publishRecord" caption="�˻�" txncode="620200109" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_returnRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��" txncode="620200105" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:hidden property="log_report_create_id" caption="��־����id" style="width:10%"/>
      <freeze:cell property="report_name" caption="��������" style="width:30%"  />
      <freeze:cell property="create_date" caption="��������" style="width:15%" />
      <freeze:cell property="last_mender" caption="����޸���" style="width:10%" />
      <freeze:cell property="publish_date" caption="��������" style="width:10%" />
      <freeze:cell property="publish_person" caption="������" style="width:10%" />
      <freeze:cell property="state" caption="״̬" valueset="����״̬" style="width:8%"/>
      <freeze:cell property="operate1" caption="����" style="width:5%" />
      <freeze:hidden property="creator" caption="�ϴ���" style="width:22%" />
      <freeze:hidden property="report_type" caption="��������" style="width:20%" visible="false" />
      <freeze:hidden property="filename" caption="�ļ���" style="width:20%" visible="false" />
      <freeze:hidden property="path" caption="·��" style="width:20%"  />
      <freeze:hidden property="timestamp" caption="ʱ���" style="width:12%" />
  </freeze:grid>
<a id="testa" style="display:none;">aaa</a>
</freeze:form>
</freeze:body>
</freeze:html>
