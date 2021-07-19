<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<script
	src="<%=request.getContextPath()%> /script/common/js/validator.js"></script>
<script language="javascript"
	src="<%=request.getContextPath()%>/script/lib/jquery.min.js"></script>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
	<title>修改webservice任务信息</title>
</head>

<script language="javascript">


function func_record_addRecord(){
	var wsTaskId = getFormFieldValue("select-key:database_task_id");
	var page = new pageDefine("/txn30103033.do","根据Id查询单个方法","modal","650","350");
   	//var page = new pageDefine("insert-collect_parameter.jsp","根据Id查询单个方法","modal","650","350");
	page.addValue(wsTaskId, "select-key:database_task_id");
	page.updateRecord();
}

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存database任务表' );	// /txn30102001.do
}

// 返 回
function func_record_goBack()
{
	var page = new pageDefine( "/txn30101004.do", "查询采集服务");
	page.addParameter("record:database_task_id","primary-key:database_task_id");
	page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	page.updateRecord();
}

function getParameter(){
	//从当前页面取值，组成key=value格式的串
    var parameter='input-data:service_targets_id='+getFormFieldValue('record:service_targets_id');
	return parameter;
}

function getParameterForTable(){
	//从当前页面取值，组成key=value格式的串  CollectConstants.TYPE_CJLX_FILEUPLOAD值01是数据源中数据库类型
	var data_source_id = getFormFieldValue("record:data_source_id");
	
	if(data_source_id==null || data_source_id == ""){
		alert("请先选择数据源！");
	}else{
		var parameter = "input-data:data_source_id="+ getFormFieldValue('record:_tmp_data_source_id');
		return parameter;
	}
}

function getParameterForTableColumn(){
	//从当前页面取值，组成key=value格式的串  CollectConstants.TYPE_CJLX_FILEUPLOAD值01是数据源中数据库类型
	var data_source_id = getFormFieldValue("record:data_source_id");
	var source_collect_table = getFormFieldValue("record:source_collect_table");
	
	if(source_collect_table==null || source_collect_table == ""){
		alert("请先选择【源采集表】！");
	}else{
		var parameter = "input-data:data_source_id="+ getFormFieldValue('record:data_source_id')+"&input-data:source_collect_table="+ getFormFieldValue('record:source_collect_table');
		return parameter;
	}
}

function func_record_updateDelete(ids){
	alert(1);
	var page = new pageDefine( "/txn30103055.do", "删除参数" );
	page.addValue(ids,"primary-key:webservice_patameter_id" );
	page.updateRecord( "是否删除选中的记录" );
	page.callAjaxService('doCallback_delete');
}

function func_record_updateRecord(ids){
	var page = new pageDefine("/txn30103033.do","根据Id查询单个方法","modal","650","350");
	page.addValue(ids, "primary-key:webservice_patameter_id");
	page.updateRecord();
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var ids = getFormAllFieldValues("dataItem:webservice_patameter_id");
	for(var i=0; i<ids.length; i++){
	   var htm='<a href="#" title="修改" onclick="func_record_updateRecord(\''+ids[i]+'\');"><div class="edit"></div></a>&nbsp;';
	   	   htm+='<a href="#" title="删除" onclick="func_record_updateDelete(\''+ids[i]+'\');"><div class="delete"></div></a>&nbsp;';
	   document.getElementsByName("span_dataItem:oper")[i].innerHTML +=htm;
	 }
	 var paramStyle = document.getElementById("label:dataItem:patameter_style");
	 if(paramStyle){
		 paramStyle.innerText += '(*)';
	 }
	 
	$('#btn_config3').click(function(){
		//var wsTaskId = getFormFieldValue("select-key:database_task_id");
   		//var page = new pageDefine("/txn30102114.do","根据Id查询单个方法","modal");
		//page.addValue(wsTaskId, "select-key:database_task_id");
		//page.updateRecord();
		var page = new pageDefine( "/txn30501009.do", "修改方法信息","modal");
		var idx = getFormFieldValue('record:database_task_id');
		page.addValue(idx,"primary-key:database_task_id");
		var collect_task_id=getFormFieldValue('record:collect_task_id');
		page.addValue(collect_task_id,"primary-key:collect_task_id");
	
		page.updateRecord();
   })
   
   $('#btn_star').click(function(){
   })
   
   $('#btn_pause').click(function(){
   })
         
}

function doCallback_update(errCode, errDesc, xmlResults){
	if(errCode == '000000'){
		alert('修改成功');
		parent.window.location.reload();
	}	  
}


function doDelete(){
	//if(confirm("您确定要删除此方法吗?")){
		//var wsTaskId = getFormFieldValue("select-key:database_task_id");
	 	//var url="/txn30102025.ajax?primary-key:database_task_id="+wsTaskId;
		//var page = new pageDefine(url, "删除方法");
	 	//page.callAjaxService('doCallback_delete');
  	//}
  	
  	if(confirm("是否删除选中的记录")){
	//var page = new pageDefine( "/txn30501005.do", "删除方法信息" );
	var page = new pageDefine( "/txn30501005.ajax", "删除方法信息" );
	var idx = getFormFieldValue('record:database_task_id');
	page.addValue(idx,"primary-key:database_task_id");
	page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	//page.updateRecord();
	page.callAjaxService('doCallback_delete');
	}
}

function doCallback_delete(errCode, errDesc, xmlResults){
	if(errCode == '000000'){
		alert('删除成功');
		parent.window.location.reload();
	}	  
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
	<freeze:errors />

	<freeze:form action="/txn30501003">
		<freeze:frame property="select-key" width="95%">
			<freeze:hidden property="database_task_id" caption="DATABASE任务ID"
				style="width:95%" />
		</freeze:frame>
		<table width="95%" border="0" align="center" class="frame-body"
			cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="5">
					<table width="100%" cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td class="leftTitle"></td>
							<td class="secTitle">
								&nbsp;&nbsp;
							</td>
							<td class="centerTitle">
								<table cellspacing="0" cellpadding="0" class="button_table">
									<tr>
										<td class="btn_left"></td>
										<td>
											<!-- <input name="btn_config3" id='btn_config3' class="grid-menu"
												style="" type="button" value="修改" /> -->
											<div class="edit" id='btn_config3'></div>
										</td>
										<td class="btn_right"></td>
									</tr>
								</table>
								<table cellspacing="0" cellpadding="0" class="button_table">
									<tr>
										<td class="btn_left"></td>
										<td>
											<div class="delete" id='btn_del' onclick="doDelete()"></div>
										</td>
										<td class="btn_right"></td>
									</tr>
								</table>
							</td>
							<td class="rightTitle"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<freeze:block property="record" caption="修改方法信息" width="95%">
			<freeze:hidden property="service_targets_id" caption="所属服务对象"   style="width:95%"/>
      		<freeze:hidden property="data_source_id" caption="数据源"    style="width:95%"/> 
      		<freeze:cell property="task_name" caption="任务名称" datatype="string"  style="width:95%"/>
      		
      		<freeze:cell property="collect_table" caption="目标采集表" valueset="资源管理_采集表" style="width:95%" />
      		<freeze:cell property="collect_mode" caption="采集方式" valueset="资源管理_采集方式" style="width:95%" />
      		<freeze:cell property="source_collect_table" caption="源采集表"   style="width:95%" />
      		<freeze:cell property="source_collect_column" caption="增量字段" style="width:95%" />
      		
      		<freeze:cell property="description" caption="说明"  style="width:95%"/>
      		<freeze:hidden property="created_time" caption="创建时间" datatype="string" maxlength="19"  style="width:95%"/>
      		<freeze:hidden property="database_task_id" caption="采集数据库主键ID" datatype="string" maxlength="32"  style="width:95%"/>
      		<freeze:hidden property="collect_task_id" caption="采集任务ID" datatype="string" maxlength="32" style="width:95%"/>	
				
		</freeze:block>
		<br>
		
	</freeze:form>
</freeze:body>
</freeze:html>
