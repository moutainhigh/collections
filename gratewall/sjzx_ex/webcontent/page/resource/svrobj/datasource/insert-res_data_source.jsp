<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="800" height="550">
<head>
<title>增加数据源信息</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存数据源表' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存数据源表' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存数据源表' );	// /txn20102001.do
}

// 返 回
function func_record_goBack()
{
	goBack("/txn20102001.do");	// /txn20102001.do
}

// 测试连接
function func_test_connection()
{
	
}

function funTBChanged(){
	var type = getFormFieldValue('record:data_source_type');
	
	if(type=='00'){
	//setDisplay("1",true);
	setDisplay("4",false);
	}else {
	//setDisplay("1",false);
	//setDisplay("2",true);
	}
}

function setDisplay(id,b){
	
	var preStr = "row_"+id;
	if(b=='true'||b==true){
		document.getElementById(preStr).style.display='block';
		var cells = document.getElementById(preStr).cells;
		for(var ii=0;ii<cells.length;ii++){
			cells[ii].className='odd1_b';
		}
	}else{
		document.getElementById(preStr).style.display='none';
	}
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	$(".radioNew").each(function(index){
		$(this).click(function(){
			$(".radioNew").css("background-position-y","bottom");
			$(this).css("background-position-y","top");
			$(this).prev()[0].click();
		});
		$(this).next()[0].onclick=function(){
				$(this).prev()[0].click();
				$(".radioNew").css("background-position-y","bottom");
				$($(".radioNew")[index]).css("background-position-y","top");
			};
	});
	for(var ii=0;ii<2;ii++){
		if($($(".radioNew")[ii]).prev()[0].checked){
			$($(".radioNew")[ii]).css("background-position-y","top");
		}
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body onload="funTBChanged()">
<freeze:title caption="增加数据源信息"/>
<freeze:errors/>

<freeze:form action="/txn20102003">
  <freeze:block property="record" caption="增加数据源信息" width="95%">
  	  <freeze:button name="testConnection" caption="测试连接"  onclick="func_test_connection();"/>
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      
      <freeze:hidden property="data_source_id" caption="数据源ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:radio property="data_source_type" caption="数据源类型" valueset="资源管理_数据源类型" value="00" onclick="funTBChanged();" style="width:95%" colspan="2"/>
      <freeze:browsebox property="service_targets_id" caption="所属服务对象" show="name" valueset="资源管理_服务对象名称"  style="width:95%" notnull="true"/>
      <freeze:text property="data_source_name" caption="数据源名称" datatype="string" maxlength="100" style="width:95%" notnull="true"/>
      <freeze:text property="db_username" caption="数据源用户名" datatype="string" maxlength="50" style="width:95%" notnull="true"/>
      <freeze:password property="db_password" caption="数据源密码" maxlength="50" notnull="true" style="width:95%" />
      <freeze:text property="data_source_ip" caption="数据源IP" datatype="string" maxlength="50" style="width:95%" notnull="true"/>
      <freeze:text property="access_port" caption="访问端口" datatype="string" maxlength="8" style="width:95%" notnull="true"/>
      
      <freeze:text property="db_type" caption="数据库类型" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="db_instance" caption="数据库实例" datatype="string" maxlength="30" style="width:95%"/>
      
      <freeze:text property="access_url" caption="访问URL" datatype="string" notnull="" maxlength="255" colspan="2" style="width:98%"/>
      
      <freeze:textarea property="db_desc" caption="数据源描述" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      
      
      <freeze:hidden property="db_status" caption="数据源状态" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="is_markup" caption="有效标记" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="创建人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="创建时间" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="最后修改时间" datatype="string" maxlength="19" style="width:95%"/>  
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
