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
<title>修改字段信息</title>
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

// 保 存
function func_record_saveAndExit()
{
	var rdVaule = getFormFieldValue('record:standard_status');
	if(rdVaule=='1'){
		var jc_data_element = getFormFieldValues("record:jc_data_element");
		if(jc_data_element!="" && jc_data_element!=null){       
      		var page = new pageDefine("/txn7000307.ajax", "元数据");
      		page.addValue(jc_data_element, "select-key:identifier");
      		page.callAjaxService('doCallback_check');
    	}
	}
	 
    
	saveAndExit( '', '保存已认领表字段表' );	// /txn80002501.do
}
//校验是否和标
function doCallback_check(errorCode, errDesc, xmlResults) 
{ 
    //被调用函数，读取使用Ajax技术得到的数据
    if( errorCode != '000000' ){
        alert("调用失败");
        return;
    } else{
        
    		var data_type = _getXmlNodeValue(xmlResults, "record:data_type");
    		var data_length = _getXmlNodeValue(xmlResults, "record:data_length");
    		var column_type = getFormFieldValue('record:column_type');
    		var column_length = getFormFieldValue('record:column_length');
    		
    		if(data_type!=column_type){
    			alert("字段类型不符合合标性！");
    			return;
    		}
    		
    		if(data_length!=column_length){
    			alert("字段长度不符合合标性！");
    			return;
    		}
    		
    		//校验提取数据是否做过更改
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
        		alert("值域不符合合标性!");
        		return;
        	} 
        	
        	if(unit_page!=unit ){
        		alert("计量单位不符合合标性!");
        		return;
        	} 
    		
    		if(column_name_page!=cn_name){
        		alert("字段中文名不符合合标性");
        		return;
        	} 
        	
        	if(jc_data_index_page!=jc_data_index){
        		alert("基础代码集不符合合标性");
        		return;
        	} 
    		
    }
}

// 返 回
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
      var page = new pageDefine("/txn7000307.ajax", "元数据");
      page.addValue(jc_data_element, "select-key:identifier");
      page.callAjaxService('doCallback_save');
    }

    //_gridRecord.loadData(page);
}

function doCallback_save(errorCode, errDesc, xmlResults) 
{ 
    //被调用函数，读取使用Ajax技术得到的数据
    if( errorCode != '000000' ){
        alert("调用失败");
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

// 请在这里添加，页面加载完成后的用户初始化操作
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
	document.getElementById('label:record:standard_status').innerHTML='合标性：';
	
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
<freeze:title caption="修改字段信息"/>
<freeze:errors/>

<freeze:form action="/txn80002502">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_column_id" caption="字段ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改字段信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      
      <freeze:text property="column_code" caption="字段名" datatype="string" maxlength="100" style="width:95%" readonly="true"/>
      <freeze:select property="sys_column_type" caption="字段类型" valueset="字段类型"  style="width:95%"/>
      <freeze:hidden property="column_type" caption="字段类型"   style="width:95%" />
      <freeze:text property="column_length" caption="字段长度" datatype="string" maxlength="" style="width:95%" readonly="true"/>
      <freeze:select property="is_null" caption="是否可为空" valueset="是否允许下载"  style="width:95%" readonly="true"/>
      <freeze:text property="default_value" caption="缺省值" datatype="string" maxlength="64" style="width:95%" readonly="true"/>
      <freeze:select property="use_type" caption="是否有效" valueset="布尔型数"   style="width:95%" />
      <freeze:radio property="standard_status" caption="合标性" notnull="true" value="1" valueset="合标性" onclick="funTBChanged();" colspan="2" style="width:95%"/>
      <freeze:browsebox property="jc_data_element" caption="基础数据元" show="mix" valueset="数据元标识符" onchange="selectJcdataelement();" style="width:95%"/>
      <freeze:browsebox property="jc_data_index" caption="基础代码集" show="mix" valueset="取基础代码集"  style="width:95%"/>
      <freeze:text property="column_name" caption="字段中文名" notnull="true" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:browsebox property="column_codeindex" caption="系统代码集" show="mix" valueset="取系统代码集"  style="width:98%"/>
      <freeze:text property="domain_value" caption="值域" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="unit" caption="计量单位" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:select property="column_level" caption="字段级别" valueset="字段级别"  style="width:95%"/>
      <freeze:text property="basic_flag" caption="基础字段标识" datatype="string" maxlength="50" style="width:95%"/>
      <freeze:text property="data_from" caption="数据来源" datatype="string" maxlength="50" style="width:95%"/>
      <freeze:text property="data_from_column" caption="数据来源字段" datatype="string" maxlength="50" style="width:95%"/>
      <freeze:text property="transform_desp" caption="数据转换描述" datatype="string" maxlength="200" colspan="2" style="width:98%"/>
      <freeze:textarea property="description" caption="说明" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      
      
      
      <freeze:hidden property="sys_rd_column_id" caption="字段ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sys_rd_table_id" caption="物理表ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sys_rd_data_source_id" caption="数据源ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="table_code" caption="物理表名" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="alia_name" caption="字段别名" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="code_name" caption="系统代码名" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="is_extra_col" caption="是否为必查字段" datatype="string" maxlength="2" style="width:95%"/>
      
      <freeze:hidden property="claim_operator" caption="认领人" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="claim_date" caption="认领时间" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:hidden property="is_primary_key" caption="是否主键" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="is_index" caption="是否索引" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="changed_status" caption="字段变化状态" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="sync_sign" caption="同步标识串" datatype="string" maxlength="20" style="width:95%"/>
      
      
      <freeze:hidden property="sort" caption="排序" datatype="string" maxlength="" style="width:95%"/>
      <freeze:hidden property="timestamp" caption="时间戳" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
