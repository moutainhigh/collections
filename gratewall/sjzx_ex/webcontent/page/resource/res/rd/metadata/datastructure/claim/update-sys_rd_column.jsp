<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.TxnContext"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="680" height="350">
<head>
<%
TxnContext context = (TxnContext)request.getAttribute("freeze-databus");
DataBus db = context.getRecord("record");

if(db.getValue("use_type").equals("0"))
db.setValue("use_type","1");
else if(db.getValue("use_type").equals("1"))
db.setValue("use_type","0");

%>
<title>�޸��ֶ���Ϣ</title>
<style type="text/css">
.cls-no{
	display:none;
}
.cls-yes{
	display:block;
}
</style>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	var rdVaule = getFormFieldValue('record:standard_status');
	if(rdVaule=='1'){
		var jc_data_element = getFormFieldValues("record:jc_data_element");
		if(jc_data_element!="" && jc_data_element!=null){       
      		var page = new pageDefine("/txn7000307.ajax", "Ԫ����");
      		page.addValue(jc_data_element, "select-key:identifier");
      		page.callAjaxService('doCallback_check');
    	}
	}
	 
    
	saveAndExit( '', '������������ֶα�' );	// /txn80002501.do
}
//У���Ƿ�ͱ�
function doCallback_check(errorCode, errDesc, xmlResults) 
{ 
    //�����ú�������ȡʹ��Ajax�����õ�������
    if( errorCode != '000000' ){
        alert("����ʧ��");
        return;
    } else{
        
    		var data_type = _getXmlNodeValue(xmlResults, "record:data_type");
    		var data_length = _getXmlNodeValue(xmlResults, "record:data_length");
    		var column_type = getFormFieldValue('record:column_type');
    		var column_length = getFormFieldValue('record:column_length');
    		
    		if(data_type!=column_type){
    			alert("�ֶ����Ͳ����Ϻϱ��ԣ�");
    			return;
    		}
    		
    		if(data_length!=column_length){
    			alert("�ֶγ��Ȳ����Ϻϱ��ԣ�");
    			return;
    		}
    		
    		//У����ȡ�����Ƿ���������
    		var domain_value = _getXmlNodeValue(xmlResults, "record:value_domain");
        	var unit = _getXmlNodeValue(xmlResults, "record:unit");
        	var cn_name = _getXmlNodeValue(xmlResults, "record:cn_name");
        	var jc_data_index = _getXmlNodeValue(xmlResults,"record:jc_standar_codeindex");
        	var memo = _getXmlNodeValue(xmlResults, "record:memo");
        	
        	var domain_value_page = getFormFieldValue('record:domain_value');
        	var unit_page = getFormFieldValue('record:unit');
        	var column_name_page = getFormFieldValue('record:column_name');
        	var jc_data_index_page = getFormFieldValue('record:jc_data_index');
        	var description_page = getFormFieldValue('record:description');
        	
        	if(domain_value_page!=domain_value){
        		alert("ֵ�򲻷��Ϻϱ���!");
        		return;
        	} 
        	
        	if(unit_page!=unit ){
        		alert("������λ�����Ϻϱ���!");
        		return;
        	} 
    		
    		if(column_name_page!=cn_name){
        		alert("�ֶ������������Ϻϱ���");
        		return;
        	} 
        	
        	if(jc_data_index_page!=jc_data_index){
        		alert("�������뼯�����Ϻϱ���");
        		return;
        	} 
    		
    }
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn80002501.do
}

function funTBChanged(){
	var rdVaule = getFormFieldValue('record:standard_status');
	
	if(rdVaule=='4'){
	
	setDisplay("4",false);
	setFormFieldNotnull('record:jc_data_element',0,false);
	}else {
	
	setDisplay("4",true);
	setFormFieldNotnull('record:jc_data_element',0,true);
	}
}

function selectJcdataelement()
{ 	
    var jc_data_element = getFormFieldValues("record:jc_data_element"); 
   
    if(jc_data_element!="" && jc_data_element!=null){       
      var page = new pageDefine("/txn7000307.ajax", "Ԫ����");
      page.addValue(jc_data_element, "select-key:identifier");
      page.callAjaxService('doCallback_save');
    }

    //_gridRecord.loadData(page);
}

function doCallback_save(errorCode, errDesc, xmlResults) 
{ 
    //�����ú�������ȡʹ��Ajax�����õ�������
    if( errorCode != '000000' ){
        alert("����ʧ��");
        return;
    } else{
        var standard_status = getFormFieldValues("record:standard_status");
        
        if(standard_status=='4'){
    	
    	}else{
    		var domain_value = _getXmlNodeValue(xmlResults, "record:value_domain");
        	var unit = _getXmlNodeValue(xmlResults, "record:unit");
        	var cn_name = _getXmlNodeValue(xmlResults, "record:cn_name");
        	var jc_data_index = _getXmlNodeValue(xmlResults,"record:jc_standar_codeindex");
        	var memo = _getXmlNodeValue(xmlResults, "record:memo");
        	
        	setFormFieldValue("record:column_name",0,cn_name);
        	setFormFieldValue("record:domain_value",0,domain_value);
        	setFormFieldValue("record:unit",0,unit);
        	setFormFieldValue("record:jc_data_index",0,jc_data_index);
        	setFormFieldValue("record:description",0,memo);
        	
    	}
    }
}

function setDisplay(id,b){
	
	var preStr = "row_"+id;
	if(b=='true'||b==true){
		document.getElementById(preStr).style.display='block';
	}else{
		document.getElementById(preStr).style.display='none';
	}
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var code = getFormFieldValue("record:standard_status");
	$(".radioNew").each(function(index){
		$($(this).prev()[0]).css("display","inline");
		$($(this).prev()[0]).css("margin-left","-1000");
		$(this).click(function(){
			$(".radioNew").css("background-position-y","bottom");
			$(this).prev()[0].click();
			if($(this).prev()[0].checked){
				$(this).css("background-position-y","top");
			}
		});
		$($(this).next()[0]).click(function(){
			$(".radioNew").css("background-position-y","bottom");
			$(this).prev().prev()[0].click();
			if($(this).prev().prev()[0].checked){
				$($(this).prev()[0]).css("background-position-y","top");
			}
		});
		if($(this).prev()[0].checked){
			$(this).css("background-position-y","top");
		}
	});
	document.getElementById('label:record:standard_status').innerHTML='�ϱ��ԣ�';
	
	for (var i = 0; i < document.getElementById('record:use_type').options.length; i++) {        
            if (document.getElementById('record:use_type').options[i].value == "") {        
                document.getElementById('record:use_type').options.remove(i);        
                break;        
            }    
            }
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body onload="funTBChanged()">
<freeze:title caption="�޸��ֶ���Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn80002502">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_column_id" caption="�ֶ�ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸��ֶ���Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      
      <freeze:text property="column_code" caption="�ֶ���" datatype="string" maxlength="100" style="width:95%" readonly="true"/>
      <freeze:select property="sys_column_type" caption="�ֶ�����" valueset="�ֶ�����"  style="width:95%"/>
      <freeze:hidden property="column_type" caption="�ֶ�����"   style="width:95%" />
      <freeze:text property="column_length" caption="�ֶγ���" datatype="string" maxlength="" style="width:95%" readonly="true"/>
      <freeze:select property="is_null" caption="�Ƿ��Ϊ��" valueset="�Ƿ���������"  style="width:95%" readonly="true"/>
      <freeze:text property="default_value" caption="ȱʡֵ" datatype="string" maxlength="64" style="width:95%" readonly="true"/>
      <freeze:select property="use_type" caption="�Ƿ���Ч" valueset="��������"   style="width:95%" />
      <freeze:radio property="standard_status" caption="�ϱ���" notnull="true" value="1" valueset="�ϱ���" onclick="funTBChanged();" colspan="2" style="width:95%"/>
      <freeze:browsebox property="jc_data_element" caption="��������Ԫ" show="mix" valueset="����Ԫ��ʶ��" onchange="selectJcdataelement();" style="width:95%"/>
      <freeze:browsebox property="jc_data_index" caption="�������뼯" show="mix" valueset="ȡ�������뼯"  style="width:95%"/>
      <freeze:text property="column_name" caption="�ֶ�������" notnull="true" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:browsebox property="column_codeindex" caption="ϵͳ���뼯" show="mix" valueset="ȡϵͳ���뼯"  style="width:98%"/>
      <freeze:text property="domain_value" caption="ֵ��" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="unit" caption="������λ" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:select property="column_level" caption="�ֶμ���" valueset="�ֶμ���"  style="width:95%"/>
      <freeze:text property="basic_flag" caption="�����ֶα�ʶ" datatype="string" maxlength="50" style="width:95%"/>
      <freeze:text property="data_from" caption="������Դ" datatype="string" maxlength="50" style="width:95%"/>
      <freeze:text property="data_from_column" caption="������Դ�ֶ�" datatype="string" maxlength="50" style="width:95%"/>
      <freeze:text property="transform_desp" caption="����ת������" datatype="string" maxlength="200" colspan="2" style="width:98%"/>
      <freeze:textarea property="description" caption="˵��" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      
      
      
      <freeze:hidden property="sys_rd_column_id" caption="�ֶ�ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sys_rd_table_id" caption="�����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sys_rd_data_source_id" caption="����ԴID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="table_code" caption="�������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="alia_name" caption="�ֶα���" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="code_name" caption="ϵͳ������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="is_extra_col" caption="�Ƿ�Ϊ�ز��ֶ�" datatype="string" maxlength="2" style="width:95%"/>
      
      <freeze:hidden property="claim_operator" caption="������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="claim_date" caption="����ʱ��" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:hidden property="is_primary_key" caption="�Ƿ�����" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="is_index" caption="�Ƿ�����" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="changed_status" caption="�ֶα仯״̬" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="sync_sign" caption="ͬ����ʶ��" datatype="string" maxlength="20" style="width:95%"/>
      
      
      <freeze:hidden property="sort" caption="����" datatype="string" maxlength="" style="width:95%"/>
      <freeze:hidden property="timestamp" caption="ʱ���" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
