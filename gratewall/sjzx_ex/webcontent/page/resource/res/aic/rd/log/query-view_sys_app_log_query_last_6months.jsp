<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery.min.js"></script>
<%
long totalCount=0l;
DataBus db = (DataBus)request.getAttribute("freeze-databus");
if(db!=null){
    DataBus db2 = db.getRecord("select-key");
	if(db2!=null){
		if(null ==db2.getValue("totalCount")){
			totalCount=5000l;
		}else{
			totalCount = new Long(db2.getValue("totalCount")).longValue();
		}
	}
}

%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ��־��ѯ�б�</title>
</head>

<script language="javascript">

//������־��Ϣ
function moreRecord(){
	var page = new pageDefine( "/txn620100101.do", "������־��ѯ" );
	page.addRecord();
}

// ������־��ѯ
function func_record_addRecord()
{
	var page = new pageDefine( "insert-view_sys_app_log_query.jsp", "������־��ѯ", "modal" );
	page.addRecord();
}

// �޸���־��ѯ
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn620100104.do", "�޸���־��ѯ", "modal" );
	page.addParameter( "record:username", "primary-key:username" );
	page.updateRecord();
}

// ɾ����־��ѯ
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn620100105.do", "ɾ����־��ѯ" );
	page.addParameter( "record:username", "primary-key:username" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}
function funTBChanged(){
	var type = getFormFieldValue('select-key:type');
	alert("type:"+type);
	
	if(type=='4'){
	setAllFieldVisible("record:ipaddress",false);
	}else{
	setAllFieldVisible("record:ipaddress",true);
	}
}
//�鿴��־����
function func_record_addRecord(){
  var page = new pageDefine( "/txn981214.do", "�鿴ҵ����־��Ϣ", "modal", 650, 400);
  page.addParameter("record:flowno","primary-key:flowno");
  page.goPage();
}

function exportExcel(){
  var totalCount = "<%=totalCount%>";
  var numPerPage = 5000;
 if(totalCount>numPerPage){
  var page = new pageDefine( "/txn620100120.do", "���õ���ҳ����", "modal", 300, 100);
  page.addValue(totalCount,"select-key:totalCount");
  page.addValue(numPerPage,"select-key:numPerPage");
  page.goPage();
	 
 }else{
	
 /*
 	$("input[type='button']").each(function(){
 		var s = $(this).val();
 		if(s&&s=="����"){
 			
 			//$(this).attr("disabled","");
 		//	alert( $(this).attr("disabled") );
 		}
 		//alert( $(this).attr("disabled"))
 	})
 */
 	clickFlag=0;
  	doExport(1,totalCount);	
 }
}

function doExport(fromCount, toCount){
	document.forms[0].action ="/txn620100119.do?select-key:fromCount="+fromCount+"&select-key:toCount="+toCount;
	document.forms[0].submit();
	document.forms[0].action ="/txn620100106.do";
	
}

function func_record_viewRecord(type){
    var page = new pageDefine( "/txn981219.do", "�鿴��ѯ��־��Ϣ", "modal", 650, 400);
    page.addParameter("record:first_page_query_id","primary-key:first_page_query_id");
  	page.goPage();
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
    document.getElementById("label:select-key:orgname").style.color = "black";
	var operationSpan = document.getElementsByName("span_record:operation");
	var types = getFormAllFieldValues("record:type");
	var oper_type = getFormAllFieldValues("record:oper_type");
	
	for (var i=0; i < operationSpan.length; i++){
	    if("һ���ѯ"==types[i]){
	       if(oper_type[i]=='��ҵ�����ѯ'){
			operationSpan[i].innerHTML = "<a title='�鿴��ϸ' onclick='setCurrentRowChecked(\"record\");func_record_viewRecord();' href='#'><div class='query'></div></a>";
		   }
		}	
		if("ϵͳ����"==types[i]){
			operationSpan[i].innerHTML = "<a title='�鿴��ϸ' onclick='setCurrentRowChecked(\"record\");func_record_addRecord();' href='#'><div class='query'></div></a>";
		}
		if("�߼���ѯ������"==types[i]){
			if(oper_type[i].indexOf('����')!=-1){
			 operationSpan[i].innerHTML = "<a onclick='setCurrentRowChecked(\"record\");func_record_updateRecord();' title='�鿴��ϸ' href='#'><div class='detail'></div></a>";
			}else{
			  operationSpan[i].innerHTML = "<a onclick='setCurrentRowChecked(\"record\");func_record_queryRecord();' title='�鿴��ϸ' href='#'><div class='detail'></div></a>";
			}
		}
		
	}	
}

function func_record_updateRecord()
{
	var page = new pageDefine( "/txn60340004.do", "�޸�������־", "modal" );
	page.addParameter( "record:first_page_query_id", "primary-key:download_log_id" );
	page.updateRecord();
}

function func_record_queryRecord()
{
	var page = new pageDefine( "/txn60340006.do", "�鿴������־", "modal" );
	page.addParameter( "record:first_page_query_id", "primary-key:download_log_id" );
	page.updateRecord();
}

//����ѡ��
function sjjg_select(){
  selectJG("tree","select-key:jgid_fk","select-key:orgname");
}

function selectYh(){
    var parameter = getFormFieldValue('select-key:jgid_fk');
    if(parameter==""){
      alert("����ѡ�����");
      return;
    }
	return 'jgid_fk=' + parameter;
}
// �ж��Ƿ����ĺ��� 
function ischinese(s) { 
	var ret=true; 
	for(var i=0;i<s.length;i++) ret=ret && (s.charCodeAt(i)>=10000); return ret; 
 }

function doSub(){
  var username=document.getElementById('select-key:username');
  if(username){
    //alert(username.value+"---"+!ischinese(username.value));
    if(username.value!='' && !ischinese(username.value)){
       alert('�û���������Ϊ����!');
       return false;
    }else{
       return true;
    }
  
  }else{
     return true;
  }
}

function clean(){
 document.getElementById("select-key:orgname").value = "";
 document.getElementById("select-key:jgid_fk").value = "";
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption=""/>
<freeze:errors/>

<freeze:form action="/txn620100106" onsubmit="return doSub();">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%" >
      <freeze:text property="username" caption="�û�����" datatype="string" maxlength="100" style="width:90%"/>
      <freeze:hidden property="query_time" caption="����ʱ��" datatype="string" maxlength="32" style="width:90%"/>
      <freeze:datebox property="query_date_start" caption="����ʱ��" prefix="<table width='90%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%"/>
    </td><td width='5%'>��</td><td width='45%'>
    <freeze:datebox property="query_date_end" caption="����ʱ��" style="width:100%" colspan="0"/>
    </td></tr></table> 
      <freeze:text property="orgname" caption="��������" style="width:70%"  readonly="true" postfix="��<a id='orgchoice' href='javascript:void(0);' onclick='sjjg_select();'>ѡ��</a>��&nbsp;<a id='clean' href='javascript:void(0);' onclick='clean();'>���</a>"></freeze:text>
      <freeze:hidden property="jgid_fk"></freeze:hidden>
      <freeze:text property="ipaddress" caption="IP" datatype="string" maxlength="100" style="width:90%"/>
      <freeze:select property="type" caption="��־����" valueset="��������"  style="width:90%" />
  </freeze:block>
<br />
  <freeze:grid property="record" caption="���������־��ѯ�б�" keylist="username" width="95%" rowselect="false" navbar="bottom" fixrow="false" checkbox="false">
      <freeze:button name="export"  caption="����" enablerule="0"  align="right" onclick='exportExcel();'/>
      <freeze:button name="record_addRecord" caption="����" txncode="620100101" enablerule="0" hotkey="MORE" align="right" onclick="moreRecord();"/>
      <freeze:hidden property="flowno" caption="��ˮ��"   />
      <freeze:hidden property="first_page_query_id" caption="id"/>
      <freeze:cell property="@rowid" caption="���"  style="width:6%" align="center" />
      <freeze:cell property="username" caption="�û�����" style="width:8%" align="center"/>
      <freeze:cell property="orgid" caption="��������" valueset="����" style="width:23%" align="center"/>
      <freeze:cell property="type" caption="��־����" valueset="��������" style="width:10%" align="center" />
      <freeze:cell property="oper_type" caption="��������"  style="width:12%" align="center" />
      <freeze:hidden property="oper_type"  caption="��������"  style="width:8%" />
      <freeze:cell property="query_time" caption="����ʱ��" style="width:20%" align="center"/>
      <freeze:cell property="ipaddress" caption="IP" style="8%" align="center"/>
  	  <freeze:cell property="operation" caption="����" align="center" style="width:5%" align="center"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
