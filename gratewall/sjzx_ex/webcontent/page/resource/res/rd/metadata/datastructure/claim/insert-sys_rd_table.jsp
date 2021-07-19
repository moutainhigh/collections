<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>


<%-- template single/single-table-insert.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>认领物理表</title>
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
function func_record_saveRecord()
{
	saveRecord( '', '保存已认领表' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存已认领表' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存已认领表' );	// /txn80002201.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn80002201.do
}

function getParameter(){
	//从当前页面取值，组成key=value格式的串
    var parameter = 'input-data:sys_rd_data_source_id=' + getFormFieldValue('record:sys_rd_data_source_id');
	return parameter;
}

function funTBChanged(){
	var rdVaule = getFormFieldValue('record:table_type');
	
	if(rdVaule=='1'){
	setDisplay("4",false);
	setDisplay("5",false);
	setDisplay("6",false);
	setFormFieldNotnull('record:sys_name',0,true);
	}else {	
	setDisplay("4",false);
	setDisplay("5",false);
	setDisplay("6",false);
	setFormFieldNotnull('record:sys_name',0,false);	
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

function getParameter(){
	//从当前页面取值，组成key=value格式的串
    var parameter = 'input-data:sys_rd_data_source_id=' + getFormFieldValue('record:sys_rd_data_source_id')
    				+ '&input-data:sys_name=' + getFormFieldValue('record:sys_name');
	return parameter;
}
//传物理表给代码集主题名称
function getTCodeParameter(){
	//从当前页面取值，组成key=value格式的串
    var parameter = 'input-data:table_code=' + getFormFieldValue('record:table_code');
	return parameter;
}

/**
 * 取表主键
 */
function getZBPKParameter(){
	
	var sys_rd_data_source_id = getFormFieldValue('record:sys_rd_data_source_id');
	var parent_table = getFormFieldValue('record:parent_table');
	var sys_name = getFormFieldValue('record:sys_name');
    var parameter = 'input-data:parent_table='+parent_table
    	+'&input-data:sys_rd_data_source_id='+sys_rd_data_source_id 
    	+'&input-data:sys_name='+sys_name ;
	return parameter;
}

function getZdParameter(){
	//从当前页面取值，组成key=value格式的串
    var parameter = 'input-data:sys_rd_unclaim_table_id=' + getFormFieldValue('record:sys_rd_unclaim_table_id')
    				+ '&input-data:sys_name=' + getFormFieldValue('record:sys_name');
	return parameter;
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{	
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
		$(this).next().click(function(){
			$(this).prev()[0].click();
			if($(this).prev()[0].checked){
				$(this).css("background-position-y","top");
			}else{
				$(this).css("background-position-y","bottom");
			}
		});
	});
	$(".radioNew")[0].click();
	$("input[type='checkbox']").after("<label class='checkboxNew'></label>");
	$("input[type='checkbox']").css("display","");
	$("input[type='checkbox']").css("margin-left","-1000");
	$(".checkboxNew").each(function(index){
		$(this).click(function(){
			$(this).prev()[0].click();
			if($(this).prev()[0].checked)
				$(this).css("background-position-y","top");
			else
				$(this).css("background-position-y","bottom");
		});
		$($(this).next()[0]).css("cursor","default");
/*		$(this).next().click(function(){
			$(this).prev()[0].click();
			if($(this).prev()[0].checked){
				$(this).css("background-position-y","top");
			}else{
				$(this).css("background-position-y","bottom");
			}
		});
*/	});
	document.getElementById('label:record:table_type').innerHTML='表类型：';
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body onload="funTBChanged()">
<freeze:title caption="认领物理表"/>
<freeze:errors/>

<freeze:form action="/txn80002203">
  <freeze:block property="record" caption="认领物理表" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回"hotkey="CLOSE" onclick="func_record_goBack();"/>
     
      <freeze:text property="table_code" caption="物理表" datatype="string" maxlength="100"  style="width:95%" readonly="true"/>
      <freeze:browsebox property="sys_name" caption="主题名称" valueset="根据物理表取业务主题" show="name" parameter="getTCodeParameter()" notnull="true"  style="width:95%"/>
      <freeze:text property="first_record_count" caption="数据量" datatype="string"  maxlength="" style="width:95%" readonly="true"/>
      <freeze:text property="table_primary_key" caption="主键" datatype="string" maxlength="100" style="width:95%" readonly="true"/>
      <freeze:radio property="table_type" caption="表类型" notnull="true" value="1" valueset="表类型"  onclick="funTBChanged();" colspan="2" style="width:95%"/>
      <freeze:text property="table_name" caption="说明" colspan="2" notnull="true" datatype="string" maxlength="100" style="width:39%"/>
      <freeze:browsebox property="parent_table" caption="主表" show="mix" valueset="根据数据源取物理表" parameter="getParameter()" colspan="2" style="width:95%" />
      <freeze:browsebox property="parent_pk" caption="主表主键" show="mix" valueset="根据主表取主表主键" parameter="getZBPKParameter()" colspan="2" style="width:95%" />
      <freeze:browsebox property="table_fk" caption="外键" show="mix" valueset="根据物理表取外键" parameter="getZdParameter()" colspan="2" style="width:95%" />     
      <freeze:checkbox property="authority" caption="权限" colspan="2" valueset="权限"  style="width:95%"/>
     <!--   <freeze:checkbox property="is_query" caption="权限" colspan="2" valueset="权限"  style="width:95%"/>-->
      <freeze:text property="table_use" caption="用途" datatype="string" maxlength="100" colspan="2" style="width:98%"/>
  	  <freeze:textarea property="memo" caption="备注" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
  	  
  	  <freeze:hidden property="table_index" caption="索引" datatype="string" maxlength="100" style="width:95%" readonly="true"/>
  	  <freeze:hidden property="sys_rd_unclaim_table_id" caption="未认领表id" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sys_rd_system_id" caption="业务主体ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sys_rd_table_id" caption="数据表ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sys_no" caption="业务主体编号" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="sys_rd_data_source_id" caption="数据源ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_record_count" caption="最后一次同步数据量" datatype="string" maxlength="" style="width:95%"/>
      <freeze:hidden property="gen_code_column" caption="总局代码字段" datatype="string" maxlength="30" style="width:95%"/>
      <freeze:hidden property="prov_code_column" caption="省局代码字段" datatype="string" maxlength="30" style="width:95%"/>
      <freeze:hidden property="city_code_column" caption="市局代码字段" datatype="string" maxlength="30" style="width:95%"/>
      <freeze:hidden property="content" caption="代码字段内容" datatype="string" maxlength="30" style="width:95%"/>
      <freeze:hidden property="claim_operator" caption="认领人" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="claim_date" caption="认领日期" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:hidden property="changed_status" caption="变化状态" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="object_schema" caption="表模式" datatype="string" maxlength="36" style="width:95%"/>
      <freeze:hidden property="table_no" caption="数据表编号" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="table_sql" caption="数据表sql" maxlength="3000" style="width:98%"/>
      <freeze:hidden property="table_sort" caption="排序字段" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="table_dist" caption="区县字段" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="table_time" caption="时间字段" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="is_query" caption="是否可查询" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="is_trans" caption="是否可共享" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="is_download" caption="是否可下载" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="sort" caption="排序" datatype="string" maxlength="" style="width:95%"/>
      <freeze:hidden property="timestamp" caption="时间戳" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
