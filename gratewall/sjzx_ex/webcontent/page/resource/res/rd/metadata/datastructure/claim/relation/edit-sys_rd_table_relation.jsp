<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery162.js"></script>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="1024" height="700">
<head>
<title>��ѯ���ϵ�б�</title>
</head>
<%
DataBus context = (DataBus) request.getAttribute("freeze-databus");
DataBus db2 = context.getRecord("record2");
String sb1 = db2.getValue("table_fk_str");
String sb2 = db2.getValue("table_fk_name_str");
String sb3 = db2.getValue("ds_id_str");
String sb4 = db2.getValue("ds_name_str");
%>
<script language="javascript"> 
var sb1 = "";
var sb2 = "";
var sb3 = "";
var sb4 = "";
var asb1 = new Array;
var asb2 = new Array;
var asb3 = new Array;
var asb4 = new Array;

function getTableFkName() {
	var ss = ""
	var s2 = getFormFieldValue( "record2:columns" );
	if(asb1.length>0){
		for(var j=0; j<asb1.length; j++){
			if(asb1[j]==s2){
				ss = asb2[j]
				break;
			}
		}	
	}
	return ss;
}

function func_record_saveAndExit(){
   if( validateForm()){
   	var page = new pageDefine("/txn80002603.ajax", "�������ϵ��Ϣ");
	page.addParameter( "select-key:sys_rd_table_id", "record:sys_rd_table_id" );
	page.addParameter( "select-key:sys_rd_system_id", "record:sys_rd_system_id" );
	page.addParameter( "select-key:sys_rd_data_source_id", "record:sys_rd_data_source_id" );
	page.addParameter( "select-key:table_code", "record:table_code" );
	page.addParameter( "select-key:table_name", "record:table_name" );
	page.addParameter( "record2:remarks", "record:remarks" );
	//��������ֶ�
	page.addParameter( "record2:columns", "record:table_fk" );
	//��������ֶ�����
    page.addValue(getTableFkName(),"record:table_fk_name" );
    //��������������Դ
    page.addParameter( "record2:ref_sys_rd_data_source_id", "record:ref_sys_rd_data_source_id" );
    //������ID
    page.addValue( getRefTableId(), "record:ref_sys_rd_table_id" );
    //������
    page.addParameter( "record2:relation_table_code_str", "record:relation_table_code");
    //��������������
    page.addValue( getRefTableName(), "record:relation_table_name");
   //�������ֶ�
    page.addParameter( "record2:ref_columns", "record:relation_table_fk");
	//�������ֶ�����
	 
	page.addValue( getRefColumnName(), "record:relation_table_fk_name");
	
    page.callAjaxService("callBack3()");
   }
}
function validateForm(){
	
	var columns =  getFormFieldValue( "record2:columns" );
	var ref_columns = getFormFieldValue( "record2:ref_columns" );
	var relation_table_code_str = getFormFieldValue( "record2:relation_table_code_str");
	
	if(columns==""||columns==null){
		alert("��ѡ����������ֶ�")
		return false;
	}
	if(relation_table_code_str==""||relation_table_code_str==null){
		alert("��ѡ�������")
		return false;
	}
	if(ref_columns==""||ref_columns==null){
		alert("��ѡ�����������ֶ�")
		return false;
	}
	return true;
	
}
function callBack3(errCode, errDesc,xmlResults ){
	if(errCode!='000000'){
	    if(errCode=='999999'){
		    alert("��ϵ�Ѿ����ڣ�")
			
	    }else{
	    	alert("�������ϵʱ��������")
			 
	    }
		return 
	}
		//ˢ���б�
    var page = new pageDefine("/txn80002606.do", "");
	page.addParameter( "record2:sys_rd_table_id", "select-key:sys_rd_table_id" );
	page.addParameter( "record2:sys_rd_system_id", "select-key:sys_rd_system_id" );
	page.addParameter( "record2:sys_rd_data_source_id", "select-key:sys_rd_data_source_id" );
	page.addParameter( "record2:table_code", "select-key:table_code" );
	page.addParameter( "record2:table_name", "select-key:table_name" );
	
    page.goPage();
    
}
// ���ӱ��ϵ
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_table_relation.jsp", "���ӱ��ϵ", "modal" );
	page.addRecord();
}

// �޸ı��ϵ
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn80002604.do", "�޸ı��ϵ", "modal" );
	page.addParameter( "record:sys_rd_table_relation_id", "primary-key:sys_rd_table_relation_id" );
	page.updateRecord();
}



function getParameter(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
    var parameter = 'input-data:sys_rd_table_id=' + getFormFieldValue('select-key:sys_rd_table_id')
	return parameter;
}
function getParameter2(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
    var parameter = 'input-data:sys_rd_data_source_id=' + getFormFieldValue('record2:ref_sys_rd_data_source_id')
	return parameter;
}

function getParameter3(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
    var parameter = 'input-data:sys_rd_data_source_id=' + getFormFieldValue('record2:ref_sys_rd_data_source_id')
                    +'&input-data:sys_rd_table_id=' + getFormFieldValue('select-key:sys_rd_table_id')
	return parameter;
}

// �� ��
function func_record_goBack()
{
	//goBack("/txn80002201.do");	//
	goBack("/txn80002208.do");
}

function reset1(){
    var s =  getFormFieldValue("record2:ref_sys_rd_data_source_id") ;
	if(s==""||s==null){
		$("select[name='record2:relation_table_code_str']").html("<option name='record2:relation_table_code_str'>��ѡ��</option>");
		$("select[name='record2:ref_columns']").html("<option name='record2:ref_columns'>��ѡ��</option>");
	}else{
		AjaxGetTablesByDS();
	}
}

function AjaxGetTablesByDS(){
	var page = new pageDefine("/txn80002201.ajax", "��������Դȡ����Ϣ");
	page.addParameter( "record2:ref_sys_rd_data_source_id", "select-key:sys_rd_data_source_id" );
	page.addParameter( "record2:ref_sys_rd_data_source_id", "select-key:show_all" );
	page.callAjaxService("callBack()");

}

function reset3(){
	var s =  getFormFieldValue("record2:sys_rd_system_id_1") ; 
	if(s){
		var page = new pageDefine("/txn60210008.ajax?record:sys_id="+s, "��������ȡ����Ϣ");
		page.callAjaxService("callBack13()");
	}
}

var a= new Array;
var b = new Array;
var c = new Array;
var d = new Array;
var e = new Array;

function callBack13(errCode, errDesc,xmlResults ){
	if(errCode!='000000'){
		alert("��������ȡ��������Ϣʱ��������")
		return
	}
	  a = _getXmlNodeValues(xmlResults,'context/record/table_name_cn');
	  b = _getXmlNodeValues(xmlResults,'context/record/table_name');
	  c = _getXmlNodeValues(xmlResults,'context/record/sys_id');
	  var f = new Array;
	  f = _getXmlNodeValues(xmlResults,'context/record/table_no');
	var s1 =  getFormFieldValue("record2:table_code") ;
	var str1="<option name='record2:relation_table_code_str'>��ѡ��</option>" ;
	if(a.length>0){
		for(var j=0; j<a.length ; j++){
		   if(s1!=""&&s1!=a[j]){
		  	 str1 = str1 + "<option name='record2:relation_table_code_str' value='"+ f[j]+"'>" + a[j]+" ("+b[j] +")"+  "</option>"
		   }		
		}
	
	}
	$("select[name='record2:relation_table_code_str']").html(str1);

}

function callBack(errCode, errDesc,xmlResults ){
	if(errCode!='000000'){
		alert("ȡ��������Ϣʱ��������")
		return
	}
	  a = _getXmlNodeValues(xmlResults,'context/record/table_name')
	  b = _getXmlNodeValues(xmlResults,'context/record/table_code')
	  c = _getXmlNodeValues(xmlResults,'context/record/sys_rd_table_id')
	var s1 =  getFormFieldValue("record2:table_code") ;
	var str1="<option name='record2:relation_table_code_str'>��ѡ��</option>" ;
	if(a.length>0){
		for(var j=0; j<a.length ; j++){
		   if(s1!=""&&s1!=a[j]){
		  	 str1 = str1 + "<option name='record2:relation_table_code_str' value='"+ a[j]+"'>" + a[j]+" ("+b[j] +")"+  "</option>"
		   }		
		}
	
	}
	$("select[name='record2:relation_table_code_str']").html(str1);

}

function reset2(){
 	var s =  getFormFieldValue("record2:relation_table_code_str") ;
	if(s==""||s==null){
		$("select[name='record2:ref_columns']").html("<option name='record2:ref_columns'>��ѡ��</option>");
	}else{
		AjaxGetColumnsByTable(s);
	}
}

function AjaxGetColumnsByTable(ss){
//	var ss =  getRefTableId();
 	if(ss!=null&&ss!=""){
 		var s = getFormFieldValue("record2:relation_table_code_str") ;
 		var page = new pageDefine("/txn60210009.ajax?record:table_no="+ss, "���ݱ���Ϣȡ�ֶ���Ϣ");
		page.callAjaxService("callBack2()");
 	}
	
}

function callBack2(errCode, errDesc,xmlResults ){
	if(errCode!='000000'){
		alert("ȡ�ֶ���Ϣʱ��������")
		return
	}
	  d = _getXmlNodeValues(xmlResults,'context/record/column_name_cn')
	  e = _getXmlNodeValues(xmlResults,'context/record/column_name')
	  //f = _getXmlNodeValues(xmlResults,'context/record/sys_rd_table_id')
	
	var str1="<option name='record2:ref_columns'>��ѡ��</option>" ;
	if(d.length>0){
		for(var j=0; j<d.length ; j++){
			str1 = str1 + "<option name='record2:ref_columns' value='"+ d[j]+"'>" + d[j]+" ("+e[j] +")"+  "</option>"
		}
	
	}
	$("select[name='record2:ref_columns']").html(str1);
}


function getRefTableId(){
	var ss = ""
	if(a.length>0){
		var str = $("select[name='record2:relation_table_code_str']").val();
		if(str!=null&&str!=""){
			for(var j=0; j<a.length; j++){
				if(a[j]==str){
					ss = c[j]
					break;
				}
			}
		}
	}
	return ss;
}

function getRefTableName(){
	var ss = ""
	if(a.length>0){
		var str = $("select[name='record2:relation_table_code_str']").val();
		if(str!=null&&str!=""){
			for(var j=0; j<a.length; j++){
				if(a[j]==str){
					ss = b[j]
					break;
				}
			}
		}
	}
	return ss;
}


function getRefColumnName(){
	var ss = ""
	if(d.length>0){
		var str = $("select[name='record2:ref_columns']").val();
		if(str!=null&&str!=""){
			for(var j=0; j<d.length; j++){
				if(d[j]==str){
					ss = e[j]
					break;
				}
			}
		}
	}
	return ss;
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	sb1 = '<%=sb1%>';
	sb2 = '<%=sb2%>';
	sb3 = '<%=sb3%>';
	sb4 = '<%=sb4%>';
	
	asb1 =  sb1.split(",")
	asb2 =  sb2.split(",")
	asb3 =  sb3.split(",")
	asb4 =  sb4.split(",")
 
	var str1="<option name='record2:columns'>��ѡ��</option>" ;
	if(asb1.length>0){
		for(var j=0; j<asb1.length ; j++){
			str1 = str1 + "<option name='record2:columns' value='"+ asb1[j]+"'>" + asb1[j]+" ("+asb2[j] +")"+  "</option>"
		}
	}
	
	$("select[name='record2:columns']").html(str1);

	var str2="<option name='record2:ref_sys_rd_data_source_id'>��ѡ��</option>" ;
	if(asb3.length>0){
		for(var j=0; j<asb3.length ; j++){
		   
			str2 = str2 + "<option name='record2:ref_sys_rd_data_source_id' value='"+asb3[j]+"'>" +asb4[j] +  "</option>"
			
			
		}
	}
	
	$("select[name='record2:ref_sys_rd_data_source_id']").html(str2);
	
	$("select[name='record2:ref_columns']").html("<option name='record2:ref_columns'>��ѡ��</option>" );
	
	
	//��ʼ��ɾ����ť
	var operationSpan = document.getElementsByName("span_record:operation");
	for (var i=0; i < operationSpan.length; i++){
		operationSpan[i].innerHTML = "<a onclick='setCurrentRowChecked(\"record\"); func_record_deleteRecord();' href='#'>ɾ��</a>";
	}		
	
	//�޸�չʾ��ʽ����������������Ӣ������
	var table_name = getFormFieldValue("record2:table_name");
	var table_code = getFormFieldValue("record2:table_code");
	setFormFieldValue("record2:table",table_name+"("+table_code+")");
	document.getElementById('record2:ref_sys_rd_data_source_id').value='d0b41f492e824e08ba060b0b6abe2733';
	
}

// ɾ�����ϵ
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn80002605.do", "ɾ�����ϵ" );
	page.addParameter( "record:sys_rd_table_relation_id", "primary-key:sys_rd_table_relation_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" ); 
		
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body onload="reset1()">
<freeze:title caption="ά������ϵ"/>
<freeze:errors/>
<freeze:form action="/txn80002606">
 	<freeze:block property="select-key" width="95%">
      <freeze:hidden  property="sys_rd_table_id" caption="����ID" style="width:95%"/>
      <freeze:hidden  property="sys_rd_system_id" caption="����ID" style="width:95%"/>
      <freeze:hidden  property="sys_rd_data_source_id" caption="����ID" style="width:95%"/>
      <freeze:hidden  property="table_code" caption="����ID" style="width:95%"/>
      <freeze:hidden  property="table_name" caption="����ID" style="width:95%"/>
   </freeze:block>
  
  <freeze:block property="record2" caption="ά������ϵ" width="95%" columns="2">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_rd_table_relation_id" caption="����ID" datatype="string" maxlength="32" style="width:95%"/>
      <tr>
      	<td colspan='6'>������Ϣ</td>
      </tr>
      <freeze:cell property="sys_rd_data_source_id" caption="����Դ��" valueset="ȡ����Դ" show="name" colspan="2" style="width:80%" />
      <freeze:cell property="table" caption="���ƣ�"   datatype="string" style="width:95%" />
      <freeze:cell property="sys_rd_system_id" caption="���⣺" valueset="ҵ������"  datatype="string"  style="width:80%" />
      <freeze:select caption="ѡ���ֶ�" property="columns" colspan="2" style="width:220"></freeze:select>
      <freeze:hidden property="table_name" caption="���ƣ�"   datatype="string" style="width:95%" />
      <freeze:hidden property="table_code" caption="���룺"   datatype="string" style="width:95%" />
      <freeze:hidden property="table_fk" caption="�����ֶ�" />
      <freeze:hidden property="table_fk_name" caption="�����ֶ�����" />
      <freeze:hidden property="sys_rd_table_id" caption="����ID" datatype="string" maxlength="32" style="width:95%"/>
      <tr>
      	<td colspan='6'>��ѡ���������Ϣ</td>
      </tr> 
        
      <freeze:hidden caption="����Դ" property="ref_sys_rd_data_source_id" colspan="2" onchange="reset1()"></freeze:hidden>
      <freeze:select property="sys_rd_system_id_1" caption="��������" valueset="ҵ������" colspan="2" onchange="reset3();"  style="width:220"></freeze:select>
      <freeze:select property="relation_table_code_str" caption="������" colspan="2" onchange="reset2();" style="width:220"></freeze:select>
      <freeze:select caption="�����ֶ�" property="ref_columns" colspan="2" style="width:220"></freeze:select>
      <freeze:hidden property="table_relation_type" caption="������ϵ����"  style="width:95%" />
    
      <freeze:hidden property="ref_sys_rd_table_id" caption="����"  maxlength="100" style="width:95%"/>
      <freeze:hidden property="relation_table_name" caption="��������������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="relation_table_fk" caption="�������ֶ�" datatype="string" maxlength="100" style="width:95%" />
      <freeze:hidden property="relation_table_fk_name" caption="�������ֶ���������" datatype="string" maxlength="100" style="width:95%"/>
   
     
     <freeze:hidden property="relation_table_code" caption="������"  style="width:95%"/>
     <freeze:textarea property="remarks" caption="ע��" colspan="2" rows="4" maxlength="1000" style="width:98%"/>
     <freeze:hidden property="timestamp" caption="ʱ���" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>
  <br/>
   
  <freeze:grid property="record" caption="��ѯ���ϵ�б�"  checkbox="false"  width="95%" fixrow="false" >
      <freeze:hidden property="sys_rd_table_relation_id" caption="����ID" style="width:12%" />
      <freeze:hidden property="sys_rd_table_id" caption="����ID" style="width:12%" />
      <freeze:hidden property="table_code" caption="�������" style="width:20%" />
      <freeze:hidden property="table_name" caption="������������" style="width:20%" />
      <freeze:hidden property="table_fk" caption="�����ֶ�" style="width:20%" />
      <freeze:cell property="no" caption="���"  style="width:6%" align="center" /> 
      <freeze:cell property="table_fk_name" caption="�����ֶ�����" style="width:20%" />
      <freeze:hidden property="relation_table_code" caption="���������" style="width:20%" />
      <freeze:cell property="relation_table_name" caption="��������������" style="width:20%" />
      <freeze:hidden property="relation_table_fk" caption="�������ֶ�" style="width:20%" />
      <freeze:cell property="relation_table_fk_name" caption="�������ֶ���������" style="width:20%" />
      <freeze:hidden property="sys_rd_system_id" caption="������������ID" style="width:12%" />
      <freeze:hidden property="sys_rd_data_source_id" caption="������������ԴID" style="width:12%" />
      
      <freeze:hidden property="ref_sys_rd_data_source_id" caption="��������������ԴID" style="width:12%" />
      <freeze:hidden property="table_relation_type" caption="������ϵ����" style="width:10%" />    
      <freeze:hidden property="remarks" caption="ע��" style="width:20%"  />
      <freeze:cell property="operation" caption="����" align="center" style="width:5%" />
      <freeze:hidden property="timestamp" caption="ʱ���" style="width:12%" />
  </freeze:grid>
 </freeze:form>

</freeze:body>
</freeze:html>
