<%@page import="com.gwssi.common.database.DBPoolConnection"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ taglib uri="/WEB-INF/gwssi.tld" prefix="gwssi" %>
<%@ page import="cn.gwssi.common.context.TxnContext" %>
<%@ page import="cn.gwssi.common.context.DataBus" %>
<freeze:html width="650" height="350">
<head>
<title>��ѯϵͳʹ����������б�</title>
<%
String path=java.util.ResourceBundle.getBundle("app").getString("docFilePath");
TxnContext context = (TxnContext)request.getAttribute("freeze-databus");
String userName=context.getRecord("oper-data").getValue("oper-name");
%>
<script type="text/javascript" src="/script/lib/jquery171.js"></script>
<jsp:include page="/page/share/common/top_datepicker.html"></jsp:include>
</head>

<script type="text/javascript">

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
function record_updateRecord(id,state)
{
    clickFlag=0;
	var state = getFormFieldValues('record:state');
	if(state=="����"){
	   alert("�ѷ����ı��治���޸�!");
	} else {
		var page = new pageDefine( "/txn620200104.do", "�޸�ϵͳʹ���������");
		page.addValue( id, "primary-key:log_report_create_id" );
		page.updateRecord();
	}
	
}

//�˻ر���
function func_record_returnRecord(id,state){
	//clickFlag=0;
	if(confirm("�Ƿ�ȷ���˻ظñ��棿")){
		if(state!="����"){
			alert("ֻ�з����ı���ſ����˻�");
		}else{
			var page = new pageDefine( "/txn620200109.do", "�˻�ϵͳʹ���������");
			page.addValue(id, "select-key:log_report_create_id");
			page.updateRecord();
		}
	}
}

// ����ϵͳʹ���������
function func_record_publishRecord(id,state)
{
	//clickFlag=0;
	if(state=="����"){
		alert("�����ѷ���");
	}else{
		if(confirm("�Ƿ�ȷ�Ϸ����ñ��棿")){
			var page = new pageDefine( "/txn620200107.do", "�޸�ϵͳʹ���������");
			page.addValue( id, "select-key:log_report_create_id" );
			page.updateRecord();
		}
	}
	
}

// ɾ��ϵͳʹ���������
function func_record_deleteRecord(id,state)
{	
	if(state=='����'){//����
		alert("�ѷ����ı��治����ɾ��");
	}else {
		var page = new pageDefine( "/txn620200105.do", "ɾ��ϵͳʹ���������" );
		page.addValue( id, "primary-key:log_report_create_id" );
		//page.addParameter( "record:path", "primary-key:path" );
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
	//$('#t2').find('.pack-list li:eq(1)').hide();
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
	     var ihtml ="";
		if(state=="����" || userName==creator[i]){
		    var rname=encodeURI(reportName)+'.doc';
  	        var url = "/downloadFile?file=<%=path%>"+url+"&&fileName="+rname;
  	        ihtml ="<a title='����' href='"+url+"' onclick='toTurn(\""+id+"\",\""+state+"\")';><div class='download'></div></a>";
		}
		ihtml+="&nbsp;<a title='�޸�' href='javascript:void(\"0\");' onclick='record_updateRecord(\""+id+"\",\""+state+"\")';><div class='edit'></div></a>";
		if(state!="����"){
	      ihtml+="&nbsp;<a title='����' href='javascript:void(\"0\");' onclick='func_record_publishRecord(\""+id+"\",\""+state+"\")';><div class='run'></div></a>";
		}else{
		  ihtml+="&nbsp;<a title='�˻�' href='javascript:void(\"0\");' onclick='func_record_returnRecord(\""+id+"\",\""+state+"\")';><div class='stop'></div></a>";
		}
	    ihtml+="&nbsp;<a title='ɾ��' href='javascript:void(\"0\");' onclick='func_record_deleteRecord(\""+id+"\",\""+state+"\")';><div class='delete'></div></a>";
		document.getElementsByName("span_record:operate1")[i].innerHTML = ihtml;
		
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
<gwssi:panel action="txn620200101" target="" parts="t1,t2" styleClass="wrapper">
 	<gwssi:cell id="t1" name="����״̬ " key="state" data="state" />
	<gwssi:cell id="t2" name="�������� " key="created_time" data="created_time" date="true"/>
   
 </gwssi:panel>
<freeze:form action="/txn620200101">
 
<br />
  <freeze:grid property="record" caption="��ѯϵͳʹ����������б�" keylist="log_report_create_id" width="95%" navbar="bottom" checkbox="false"  fixrow="false" multiselect="false">
      <freeze:button name="record_addRecord" caption="����" txncode="620200103" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:hidden property="log_report_create_id" caption="��־����id" />
      <freeze:cell property="@rowid" caption="���" style="width:5%"  align="center"/>
      <freeze:cell property="report_name" caption="��������" style="width:30%"  align="center"/>
      <freeze:cell property="create_date" caption="��������" style="width:10%" align="center"/>
      <freeze:cell property="last_mender" caption="����޸���" style="width:12%" align="center"/>
      <freeze:cell property="publish_date" caption="��������" style="width:10%" align="center"/>
      <freeze:cell property="publish_person" caption="������" style="width:12%" align="center"/>
      <freeze:cell property="state" caption="״̬" valueset="����״̬" style="width:5%" align="center"/>
      <freeze:cell property="operate1" caption="����" style="width:15%" align="center"/>
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
