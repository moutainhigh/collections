<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@page import="cn.gwssi.common.dao.resource.PublicResource"%>
<%@page import="cn.gwssi.common.dao.resource.code.CodeMap"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script src="<%=request.getContextPath() %> /script/common/js/validator.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-data2table/js/jquery.tablet.js"></script>
<link href="<%=request.getContextPath()%>/script/jquery-plugin-Selector/css/jquery.dataSelector.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %> /script/common/js/validator.js"></script>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="1000" height="400">
<head>
<title>���Ӳɼ����ݱ���Ϣ</title>
<style type="text/css">
</style>
</head>

<script language="javascript">
<%
TxnContext tbTypeContext = new TxnContext();
CodeMap codeMap = PublicResource.getCodeFactory();
Recordset rs = codeMap.lookup(tbTypeContext, "��Դ����_����������");
String tmp = "";
out.print("var dataitem_types = new Array; ");
for(int ii=0; ii<rs.size(); ii++){
	out.println("dataitem_types.push({\"key\": \""+rs.get(ii).get("codevalue")
		+"\", \"title\": \""+rs.get(ii).get("codename")+"\"});");
}
%>
<%
TxnContext codeTbContext = new TxnContext();
rs = codeMap.lookup(codeTbContext, "��Դ����_��Ӧ�����");
out.print("var code_table_list = new Array;");
for(int ii=0; ii<rs.size(); ii++){
	out.println("code_table_list.push({\"key\": \""+rs.get(ii).get("codevalue")
		+"\", \"title\": \""+rs.get(ii).get("codename")+"\"});");
}
%>
var now_adding = -1;
var is_name_used = 1;
var row_index = -1;
var now_line_no = -1;
var table_id="";
// �� ��
function func_record_saveRecord(){
	var en=getFormFieldValue('record:table_name_en');
	if(!checkEnName(en,'������')){
	return false;
	}
	var page = new pageDefine("/txn20201000.ajax", "������ݱ����Ƿ��Ѿ�ʹ��", "window");	
	page.addParameter("record:table_name_en","primary-key:table_name_en");
	page.addParameter("record:collect_table_id","primary-key:collect_table_id");
	page.callAjaxService('nameCheckCallback');
}
function nameCheckCallback(errCode,errDesc,xmlResults){
		is_name_used = 1;
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}
		is_name_used=_getXmlNodeValues(xmlResults,'record:name_nums');
		if(is_name_used>0){
  			alert("�������Ѵ��ڣ�����������");
  		}else{
  		  var item=getFormFieldValue('record:service_targets_id');
  		  if(!checkItem(item,"100","�����������")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:table_name_en');
  		  if(!checkItem(item,"100","������")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:table_name_cn');
  		  if(!checkItem(item,"100","����������")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:table_type');
  		  if(!checkItem(item,"100","������")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:table_desc');
  		  if(!checkItemLength(item,"1000","������")){
  		    return false;
  		  }
	      saveRecord( '', '����ɼ����ݱ���Ϣ��' );
  		}
}
function insertTable(errCode,errDesc,xmlResults){
		
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}else{
		    table_id=_getXmlNodeValues(xmlResults,'record:collect_table_id');
		    setFormFieldValue("record:collect_table_id",table_id);
		    setFormFieldValue("primary-key:collect_table_id",table_id);
		    alert("����ɹ�!");
		}
}


// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '����ɼ����ݱ���Ϣ��' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	var en=getFormFieldValue('record:table_name_en');
	if(!checkEnName(en,'������')){
	return false;
	}
	var page = new pageDefine("/txn20201000.ajax", "������ݱ����Ƿ��Ѿ�ʹ��");	
	page.addParameter("record:table_name_en","primary-key:table_name_en");
	page.callAjaxService('nameCheckCallback2');
}
function nameCheckCallback2(errCode,errDesc,xmlResults){
		is_name_used = 1;
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}
		is_name_used=_getXmlNodeValues(xmlResults,'record:name_nums');
		if(is_name_used>0){
  			alert("�������Ѵ��ڣ�����������");
  		}else{
  		   saveAndExit( '', '����ɼ����ݱ���Ϣ��' );
  		}
}

// �� ��
function func_record_goBack()
{
	//var page = new pageDefine( "/txn20201001.do", "��ѯ�ɼ����ݱ�");
	//page.updateRecord();
	goBack("/txn20201001.do");	// /txn20201001.do
}

// �޸Ĳɼ����������Ϣ
function func_record_updateRecord(idx)
{
	var page = new pageDefine( "/txn20202004.do", "�޸Ĳɼ���������Ϣ" );
	page.addValue(idx,"primary-key:collect_dataitem_id");
	var collect_table_id=getFormFieldValue('record:collect_table_id');
	page.addValue(collect_table_id,"primary-key:collect_table_id");
	
	page.updateRecord();
}

function creatTableCheck(errCode,errDesc,xmlResults){
		is_name_used = 1;
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}
		is_name_used=_getXmlNodeValues(xmlResults,'record:name_nums');
		if(is_name_used>0){
		
		if(confirm("�ɼ����Ѵ��ڴ����ݱ����������ݱ����������ݣ��Ƿ�������ɸ����ݱ�?")){
		   var page = new pageDefine( "/txn20201008.ajax", "���ɲɼ����ݱ�!");
  		   page.addParameter("record:collect_table_id","record:collect_table_id");
  		   page.addValue(is_name_used,"record:name_nums");
  		   page.callAjaxService('creatTable');
		}
		
	  		//alert("�ɼ����Ѵ��ڴ����ݱ����������ݱ�����������,���������ɸñ�!");
	  		//return false;
  		}else if(is_name_used==-1){
  		
	  		if(confirm("�ɼ����Ѵ��ڴ����ݱ�����,�����ݱ���û�����ݣ��Ƿ�������ɸ����ݱ�?")){
			   var page = new pageDefine( "/txn20201008.ajax", "���ɲɼ����ݱ�!");
	  		   page.addParameter("record:collect_table_id","record:collect_table_id");
	  		   page.addValue(is_name_used,"record:name_nums");
	  		   page.callAjaxService('creatTable');
			}
			
			//alert("�ɼ����Ѵ��ڴ����ݱ�,���������ɸñ�!");
  			//return false;
  		}else{
  		 if(confirm("�Ƿ��������ݱ�?")){
	  		   var page = new pageDefine( "/txn20201008.ajax", "���ɲɼ����ݱ�!");
	  		   page.addParameter("record:collect_table_id","record:collect_table_id");
	  		   page.addValue(is_name_used,"record:name_nums");
	  		   page.callAjaxService('creatTable');
  		   }
  		}
}
function creatTable(errCode,errDesc,xmlResults){
		is_name_used[0] = 1;
		if(errCode != '000000'){
			alert('�������['+errCode+']==>'+errDesc);
			return;
		}
		else{
		   alert("���ɱ�ɹ�!");
		}
}


// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var collect_table_id = getFormAllFieldValues("record:collect_table_id");
	
	if(collect_table_id!=""){
	  alert("����ɹ�!");
	}
	
	var ids = getFormAllFieldValues("dataItem:collect_dataitem_id");
	for(var i=0; i<ids.length; i++){
	   var htm='<a href="#" title="�鿴" onclick="func_record_viewRecord(\''+ids[i]+'\');"><div class="detail"></div></a>&nbsp;';
	  
	   htm+='<a href="#" title="�޸�" onclick="func_record_updateRecord(\''+ids[i]+'\');"><div class="edit"></div></a>&nbsp;';
	   htm+='<a href="#" title="ɾ��" onclick="func_record_deleteRecord(\''+ids[i]+'\');"><div class="delete"></div></a>';
	   document.getElementsByName("span_dataItem:oper")[i].innerHTML +=htm;
	 }
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="���Ӳɼ����ݱ���Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn20201003" enctype="multipart/form-data">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="collect_table_id" caption="�ɼ����ݱ�ID" style="width:95%"/>
  </freeze:frame>
  <div style="width:95%;margin-left:20px" >
  <table style="width:65%"  >
   <tr>
   
    <td width="30%">
   
    	<table width="100%" cellpadding="0" cellspacing="0">
    		<tr>
    			<td style="background:url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_l.png') left 50% no-repeat;" width="2" height="25" valign="middle"></td>
    			<td height="25" style="color:white;background:url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_c.png') left 50% repeat-x;">
    				<div style="background:url(<%=request.getContextPath()%>/images/xzcjbg/icon_bg.png) left 50% no-repeat; width:20px; display: inline;"></div>
					��һ��,¼��ɼ��������Ϣ</td>
    			<td width="5" height="25" valign="middle" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_r.png') left 50% no-repeat;"></td>
    		</tr>
    	</table>
    </td>
    <td width="30%" >
    	<table width="100%" cellpadding="0" cellspacing="0">
    		<tr>
    			<td style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;" width="2" height="25" valign="middle"></td>
    			<td height="25" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
    				<div style="background:url(<%=request.getContextPath()%>/images/xzcjbg/icon_bg.png) left 50% no-repeat; width:20px; display: inline;"></div>
					   �ڶ���,¼��ɼ����ֶ���Ϣ</td>
    			<td width="5" height="25" valign="middle" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_r.png') left 50% no-repeat;"></td>
    		</tr>
    	</table>
    </td>
    <td width="30%">
 		<table width="100%" cellpadding="0" cellspacing="0">
    		<tr>
    			<td style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;" width="2" height="25" valign="middle"></td>
    			<td height="25" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
    				<div style="background:url(<%=request.getContextPath()%>/images/xzcjbg/icon_bg.png) left 50% no-repeat; width:20px; display: inline;"></div>
					������,Ԥ�������ɱ�</td>
    			<td width="5" height="25" valign="middle" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_r.png') left 50% no-repeat;"></td>
    		</tr>
    	</table>   
     </td>
     
   </tr> 
  </table>
  </div>
  
  <freeze:block property="record" caption="�ɼ����ݱ���Ϣ" width="95%">
  	  <freeze:button name="record_nextRecord" caption="��һ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:hidden property="collect_table_id" caption="�ɼ����ݱ�ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:browsebox property="service_targets_id" caption="�����������" show="name" notnull="true" valueset="��Դ����_�����������"   style="width:95%"/>
      <freeze:text property="table_name_en" caption="������" datatype="string" notnull="true"  style="width:95%"/>
      <freeze:text property="table_name_cn" caption="����������" datatype="string"  notnull="true"  style="width:95%"/>
      <freeze:select property="table_type" caption="������" show="name" valueset="��Դ����_������" notnull="true" style="width:95%"/>
      <freeze:textarea property="table_desc" caption="������" colspan="2" rows="2"  style="width:98%"/>
      <freeze:hidden property="table_status" caption="��״̬" datatype="string"  style="width:95%"/>
      <freeze:hidden property="is_markup" caption="��Ч���" datatype="string"  style="width:95%"/>
      <freeze:hidden property="creator_id" caption="������ID" datatype="string"  style="width:95%"/>
      <freeze:hidden property="created_time" caption="����ʱ��" datatype="string"  style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="����޸���ID" datatype="string"  style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��" datatype="string"  style="width:95%"/>
      <freeze:hidden property="cj_ly" caption="�ɼ���Դ" datatype="string"  style="width:95%"/>
      <freeze:hidden property="if_creat" caption="�ɼ����Ƿ����ɲɼ���" datatype="string"  style="width:95%"/>
  </freeze:block>
</freeze:form>

</freeze:body>
</freeze:html>
