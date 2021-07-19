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
	var wsTaskId = getFormFieldValue("select-key:webservice_task_id");
	var page = new pageDefine("/txn30103033.do","根据Id查询单个方法","modal","650","350");
   	//var page = new pageDefine("insert-collect_parameter.jsp","根据Id查询单个方法","modal","650","350");
	page.addValue(wsTaskId, "select-key:webservice_task_id");
	page.updateRecord();
}

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存webservice任务表' );	// /txn30102001.do
}

// 返 回
function func_record_goBack()
{
	var page = new pageDefine( "/txn30101004.do", "查询采集服务");
	page.addParameter("record:webservice_task_id","primary-key:webservice_task_id");
	page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	page.updateRecord();
}

function getParameter()
{
	//从当前页面取值，组成key=value格式的串
    var parameter = 'input-data:service_targets_id=<freeze:out  value="${record.service_targets_id}"/>';
	return parameter;
}

//删除方法参数
function func_record_updateDelete(ids){
	//alert("func_record_updateDelete ids="+ids);
	var page = new pageDefine( "/txn30103055.ajax", "删除参数" );
	page.addValue(ids,"primary-key:webservice_patameter_id" );
	//page.deleteRecord( "是否删除选中的记录" );
	page.callAjaxService('doCallback_delparam');
}
function doCallback_delparam(errCode, errDesc, xmlResults){
	if(errCode == '000000'){
		alert('删除成功');	
	}else{
		alert("删除失败 errCode="+errCode);
	}
	
	
	var wsTaskId   = getFormFieldValue("select-key:webservice_task_id");
	var service_no = getFormFieldValue("record:service_no");
	var href="/txn30102111.do?select-key:service_no="+service_no
			+"&select-key:webservice_task_id="+wsTaskId;
	window.location.href=href; 
	
}

function func_record_updateRecord(ids){
	//alert("func_record_updateRecord ids="+ids);
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
		var wsTaskId = getFormFieldValue("select-key:webservice_task_id");
   		var page = new pageDefine("/txn30102114.do","根据Id查询单个方法","modal");
		page.addValue(wsTaskId, "select-key:webservice_task_id");
		page.updateRecord();
   })
   
   $('#btn_star').click(function(){
   })
   
   $('#btn_pause').click(function(){
   })
         
}

//删除共享方法
function doDelete(){
	if(confirm("您确定要删除此方法吗?")){
		var wsTaskId = getFormFieldValue("select-key:webservice_task_id");
	 	var url="/txn30102025.ajax?primary-key:webservice_task_id="+wsTaskId;
		var page = new pageDefine(url, "删除方法");
	 	page.callAjaxService('doCallback_delete');
  	}
}

function doCallback_delete(errCode, errDesc, xmlResults){
	if(errCode == '000000'){
		alert('删除成功');
		
		//调用父窗口方法删除对应的树节点
		var wsTaskId = getFormFieldValue("select-key:webservice_task_id");
		parent.window.delNode(wsTaskId);
		
	}	  
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
	<freeze:errors />

	<freeze:form action="/txn30102111">
		<freeze:frame property="select-key" width="95%">
			<freeze:hidden property="webservice_task_id" caption="WEBSERVICE任务ID"
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
											<!-- <input name="btn_del" id='btn_del' class="grid-menu"
												style="" type="button" onclick="doDelete()" value="删除" />  -->
										</td>
										<td class="btn_right"></td>
									</tr>
								</table>
								<!-- 
								<freeze:if test="${record.method_status=='0'}">
									<table cellspacing="0" cellpadding="0" class="button_table">
										<tr>
											<td class="btn_left"></td>
											<td>
											<div class="run" id='btn_star'></div>
											</td>
											<td class="btn_right"></td>
										</tr>
									</table>
								</freeze:if>
								<freeze:if test="${record.method_status=='1'}">
									<table cellspacing="0" cellpadding="0" class="button_table">
										<tr>
											<td class="btn_left"></td>
											<td>
											<div class="stop" id='btn_stop'></div>
											</td>
											<td class="btn_right"></td>
										</tr>
									</table>
								</freeze:if>
								 -->
							</td>
							<td class="rightTitle"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<freeze:block property="record" caption="修改方法信息" width="95%">
			<freeze:hidden property="webservice_task_id" caption="WEBSERVICE任务ID"
				datatype="string" maxlength="32" style="width:95%" />
			<freeze:hidden property="collect_task_id" caption="采集任务ID"
				datatype="string" maxlength="32" style="width:95%" />
			<freeze:hidden property="service_targets_id" caption="服务ID"
				datatype="string" maxlength="32" style="width:95%" />
			<freeze:cell property="service_no" caption="任务ID：" datatype="string"
				style="width:95%" />
			<freeze:cell property="method_name_en" caption="方法名称："
				datatype="string" style="width:95%" />
			<freeze:cell property="method_name_cn" caption="方法中文名称"
				datatype="string" style="width:95%" />
			<freeze:cell property="collect_table" caption="对应采集表" show="name"
				notnull="true" valueset="资源管理_采集表" style="width:95%" />
			<freeze:cell property="collect_mode" caption="采集方式" show="name"
				notnull="true" valueset="资源管理_采集方式" style="width:95%" />
			<freeze:hidden property="is_encryption" caption="是否加密"
				datatype="string" style="width:95%" />
			<freeze:cell property="encrypt_mode" caption="解密方法" notnull="true"
				show="name" valueset="资源管理_解密方法" value="01" style="width:95%" />
			<freeze:cell property="method_description" caption="方法描述" colspan="2"
				style="width:98%" />
			<freeze:cell property="web_name_space" caption="命名空间：" colspan="2"
				style="width:95%" />
			<freeze:hidden property="method_status" caption="方法状态"
				datatype="string" style="width:95%" />
		</freeze:block>
		<br>
		<freeze:grid property="dataItem" caption="参数列表"
			keylist="webservice_patameter_id" multiselect="false"
			checkbox="false" width="95%" fixrow="false">
			<freeze:button name="record_addRecord" caption="增加参数"
				enablerule="0" hotkey="ADD" align="right"
				onclick="func_record_addRecord();" />
			<freeze:hidden property="webservice_patameter_id" caption="参数ID" />
			<freeze:hidden property="webservice_task_id" caption="方法ID" />
			<freeze:cell property="patameter_type" caption="参数类型"
				style="width:25%"  align="center"/>
			<freeze:cell property="patameter_name" caption="参数名"
				style="width:40%"  align="center"/>
			<freeze:cell property="patameter_style" caption="参数格式"
				valueset="采集任务_参数格式" style="width:20%"  align="center" />
			<freeze:cell property="oper" caption="操作" align="center"
				style="width:15%" />
		</freeze:grid>
		
	</freeze:form>
</freeze:body>
</freeze:html>
