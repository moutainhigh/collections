<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%@page import="com.gwssi.common.constant.CollectConstants"%>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>修改采集任务信息</title>
</head>
<script language='javascript' src='<%=request.getContextPath()%>/script/uploadfile.js'></script>
<script language="javascript">

function func_record_prevRecord(){
	var page = new pageDefine( "/txn30101003.do", "配置采集方法");
	var taskId = getFormFieldValue("primary-key:collect_task_id");
	page.addParameter("primary-key:collect_task_id","primary-key:collect_task_id");
	page.goPage();
}
// 保 存
function func_record_saveRecord(){
	if(parent.name!=''){
		parent.window.location.href="txn30101001.do";
	}else{
		var page = new pageDefine( "/txn30101001.do", "采集列表");
		page.goPage();
	}
	/* document.forms["CollectTaskUpdateForm"].setAttribute("action", "/txn30101002.do");
	saveRecord( '', '保存采集任务信息' ); */
	/**
		  var page = new pageDefine( "/txn30101002.do", "保存采集任务信息");
	      page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	      page.addParameter("record:collect_task_id","record:collect_task_id");
	      page.addParameter("record:service_targets_id","record:service_targets_id");
	      page.addParameter("record:task_name","record:task_name");
	      page.addParameter("record:data_source_id","record:data_source_id");
	      page.addParameter("record:collect_type","record:collect_type");
	      page.addParameter("record:task_description","record:task_description");
	      page.addParameter("record:record","record:record");
	      page.addParameter("record:task_status","record:task_status");
	      page.addParameter("record:is_markup","record:is_markup");
	      page.addParameter("record:creator_id","record:creator_id");
	      page.addParameter("record:created_time","record:created_time");
	      page.addParameter("record:last_modify_id","record:last_modify_id");
	      page.addParameter("record:last_modify_time","record:last_modify_time");
	      page.addParameter("record:fj_fk","record:fj_fk");
	      page.addParameter("record:fjmc","record:fjmc");
	      page.updateRecord();
	
	var page = new pageDefine("/txn30101000.ajax", "检查数据源名称是否使用");	
	page.addParameter("record:data_source_id","primary-key:data_source_id");
	page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	page.callAjaxService('nameCheckCallback');
	*/
}
function nameCheckCallback(errCode,errDesc,xmlResults){
		is_name_used = 1;
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}
		is_name_used=_getXmlNodeValues(xmlResults,'record:name_nums');
		if(is_name_used>0){
  			alert("数据源名称已经使用");
  		}else{
  		  var page = new pageDefine( "/txn30101002.ajax", "保存采集任务信息");
	      page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	      page.addParameter("record:collect_task_id","record:collect_task_id");
	      page.addParameter("record:service_targets_id","record:service_targets_id");
	      page.addParameter("record:task_name","record:task_name");
	      page.addParameter("record:data_source_id","record:data_source_id");
	      page.addParameter("record:collect_type","record:collect_type");
	      page.addParameter("record:task_description","record:task_description");
	      page.addParameter("record:record","record:record");
	      page.addParameter("record:task_status","record:task_status");
	      page.addParameter("record:is_markup","record:is_markup");
	      page.addParameter("record:creator_id","record:creator_id");
	      page.addParameter("record:created_time","record:created_time");
	      page.addParameter("record:last_modify_id","record:last_modify_id");
	      page.addParameter("record:last_modify_time","record:last_modify_time");
	      page.callAjaxService('updateTable');
  		}
}
function updateTable(errCode,errDesc,xmlResults){
		
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}else{
		    alert("保存成功!");
		}
}

// 保 存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存采集任务表' );	// /txn30101001.do
}

// 返 回
function func_record_goBack()
{
	var page = new pageDefine( "/txn30101001.do", "查询采集任务");
	page.addParameter("select-key:service_targets_id","select-key:service_targets_id");
	page.addParameter("select-key:collect_type","select-key:collect_type");
	page.addParameter("select-key:task_status","select-key:task_status");
	page.updateRecord();
	//goBack();	// /txn30101001.do
}
function getParameter(){
	//从当前页面取值，组成key=value格式的串
    var parameter = "input-data:service_targets_id="+ getFormFieldValue('record:service_targets_id')+"&input-data:collectType=<%=CollectConstants.TYPE_CJLX_WEBSERVICE%>";
	return parameter;
}

// 获取采集任务对应方法
function func_record_getFunction()
{
    var collect_task_id=getFormFieldValue('record:collect_task_id');
	if(collect_task_id==null||collect_task_id==""){
	    alert("请先填写采集任务信息!");
	    clickFlag=0;
	    return false;
    }
	var page = new pageDefine( "/txn30101009.do", "获取采集任务对应方法");
	page.addParameter("record:collect_task_id","record:collect_task_id");
	page.addParameter("record:data_source_id","record:data_source_id");
	page.updateRecord();
	//page.callAjaxService('getFun');
}
function getFun(errCode,errDesc,xmlResults){
		
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}else{
		    alert("获取方法成功!");
		}
}
// 修改方法信息
function func_record_updateRecord(idx)
{
	var page = new pageDefine( "/txn30102004.do", "修改方法信息");
	page.addValue(idx,"primary-key:webservice_task_id");
	var service_targets_id=getFormFieldValue("record:service_targets_id");
	page.addValue(service_targets_id,"primary-key:service_targets_id");
	
	page.updateRecord();
}
// 删除方法信息
function func_record_deleteRecord(idx)
{
if(confirm("是否删除选中的记录")){
	var page = new pageDefine( "/txn30102005.do", "删除方法信息" );
	page.addValue(idx,"primary-key:webservice_task_id");
	page.addParameter("record:service_targets_id","primary-key:service_targets_id");
	page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	page.addParameter("record:fj_fk","record:fj_fk");
	page.addParameter("record:fjmc","record:fjmc");
	//page.deleteRecord( "是否删除选中的记录" ); 
	page.updateRecord();
	}
	
}
//查看采集数据项表信息
function func_record_viewRecord(idx)
{
	var page = new pageDefine( "/txn30102006.do", "查看方法信息", "modal" );
	page.addValue(idx,"primary-key:webservice_task_id");
	
	page.updateRecord();
}
//改变服务对象
function changeTarget()
{
	if (getFormFieldValue("record:service_targets_id") != null && getFormFieldValue("record:service_targets_id") != "")
	{
		var name=document.getElementById("record:_tmp_service_targets_id").value;
		setFormFieldValue("record:task_name",name+"_Web Service");
		setFormFieldValue("record:data_source_id",0,"");
	}
}
//定制采集周期
function chooseCjzq(){
 var cjzq=getFormFieldValue("record:scheduling_day1");
 if(cjzq==null||""==cjzq){
 	//var page = new pageDefine( "/page/collect/config/schedule/insert-collect_task_scheduling.jsp", "增加任务调度", "modal");
	//page.addRecord();
	window.showModalDialog("/page/collect/config/schedule/insert-collect_task_scheduling.jsp", window, "dialogHeight:350px;dialogWidth:500px;dialogTop:250px;dialogLeft:300px;help:no;scrollbar:no;resizable:no;status:no");
	//window.open("/page/collect/config/schedule/insert-collect_task_scheduling.jsp", null, "dialogHeight:350px;dialogWidth:500px;dialogTop:250px;dialogLeft:300px;help:no;scrollbar:no;resizable:no;status:no");
 }else{
 	window.showModalDialog("/page/collect/config/schedule/insert-collect_task_scheduling.jsp", window, "dialogHeight:350px;dialogWidth:500px;dialogTop:250px;dialogLeft:300px;help:no;scrollbar:no;resizable:no;status:no");
 	//var page = new pageDefine( "/txn30801004.do", "修改任务调度", "modal");
	//page.addParameter("primary-key:collect_task_id","primary-key:collect_task_id");
	//page.updateRecord();
 }
}
// 页面跳转
function grid_goRecord(gridName, index, totalRow, flag)
{

  if( flag == null || flag == '' ){
  	flag = 'go';
  }
  
  var pageRowField = getAttributeObject(gridName, index, 'page-row');
  var startRowField = getAttributeObject(gridName, index, 'start-row');
  
  var pageRow=parseInt(pageRowField.value);
  //DC2-jufeng-20120724 add pageRow selected
  /*var ss = document.getElementById('customPageRowSeleted')
  var pageRow2 = parseInt( ss.options[ss.selectedIndex].text );
  if(pageRow2){
 	 pageRow = pageRow2
  }*/
  if( pageRow > 500 ){
    pageRow = 500;
  }
  else if( pageRow < 1 ){
    pageRow = 10;
  }
  
  if( flag == 'go' ){
  	var objs = document.getElementsByName( gridName + '_selectPage' );
  	var pageno = parseInt(objs[index].value);
  }
  else{
  	var pageno = parseInt(flag);
  }
  
  var startRow = (pageno-1) * pageRow + 1;
  pageRowField.value = pageRow;
  startRowField.value = startRow;
  
  var action =  pageRowField.form.action+'?primary-key:collect_task_id=' + getFormFieldValue("record:collect_task_id");
  action += (action.indexOf('?') > 0) ? '&' : '?';
  
  var param = 'attribute-node:' + gridName + '_page-row=' + pageRow
		+ '&attribute-node:' + gridName + '_start-row=' + startRow
		+ '&attribute-node:' + gridName + '_record-number=' + totalRow;
	
  //alert("111  \n"+pageRow2 +'\n 222 \n'+param)
  
  pageRowField = startRowField = null;
  // modified By WeiQiang 2009-02-12
  // fixed the bug that when change grid's page and the open type is new-window, window will be close
  // alert( action+param );
  var nextPage = action+param+ '&primary-key:' + gridName ;
  // goWindowBack( action+param );
	
	// 缺省导航到上一个页面
	if( nextPage == null ){
		nextPage = _prePageName;
	}
	
	// 取缓存数据标志
	var addr = _addHrefParameter( nextPage, 'inner-flag:back-flag', 'true' );
 	// alert("全路径:" + addr);
 	
 	// 打开窗口的类型
	if( openWindowType == 'subframe' ){
		addr = _addHrefParameter( addr, 'inner-flag:open-type', 'subframe' );
	}else if ( openWindowType == 'new-window' ){
		addr = _addHrefParameter( addr, 'inner-flag:open-type', 'new-window' );
	}else{
		addr = _addHrefParameter( addr, 'inner-flag:open-type', 'window' );
	}
	
	// 生成全路径
	addr = _browse.resolveURL( addr );
	// alert("全路径:" + addr);
 	// 转换编码
 	addr = page_setSubmitData( window, addr );
	window.location = addr;
  
}


/**
//定制采集周期
function chooseCjzq(){
 var cjzq=getFormFieldValue("record:interval_time");
 if(cjzq==null||""==cjzq){
 	var page = new pageDefine( "/page/collect/config/schedule/insert-collect_task_scheduling.jsp", "增加任务调度", "modal");
	page.addRecord();
 }else{
 	var page = new pageDefine( "/txn30801004.do", "修改任务调度", "modal");
	page.addParameter("primary-key:collect_task_id","primary-key:collect_task_id");
	page.updateRecord();
 }
}
*/
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
   
	var ids = getFormAllFieldValues("dataItem:webservice_task_id");
	for(var i=0; i<ids.length; i++){
	   var htm='<a href="#" title="查看" onclick="func_record_viewRecord(\''+ids[i]+'\');"><div class="detail"></div></a>&nbsp;';
	   htm+='<a href="#" title="修改" onclick="func_record_updateRecord(\''+ids[i]+'\');"><div class="edit"></div></a>&nbsp;';
	   htm+='<a href="#" title="删除" onclick="func_record_deleteRecord(\''+ids[i]+'\');"><div class="delete"></div></a>';
	   document.getElementsByName("span_dataItem:oper")[i].innerHTML +=htm;
	 }
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改采集任务信息"/>
<freeze:errors/>

<freeze:block  property="select-key" caption="查询条件" width="95%">
      <freeze:hidden property="service_targets_id" caption="所属服务对象"   style="width:95%"/>
      <freeze:hidden property="collect_type" caption="采集类型"    style="width:95%"/>
      <freeze:hidden property="task_status" caption="任务状态"    style="width:95%"/>
  </freeze:block>

<freeze:form action="/txn30101009" enctype="multipart/form-data">
  <freeze:frame property="primary-key" width="95%" >
      <freeze:hidden property="collect_task_id" caption="采集任务ID" style="width:95%"/>
  </freeze:frame>
  
  <div style="width: 95%; margin-left: 20px">
		<table style="width: 65%">
			<tr>
				<td width="30%">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;"
								width="2" height="25" valign="middle"></td>
							<td height="25"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
								<div
									style="background: url(<%=request.getContextPath()%>/ images/ xzcjbg/ icon_bg .                                                                     png ) left 50% no-repeat; width: 20px; display: inline;"></div>
								第一步，配置基本信息
							</td>
							<td width="5" height="25" valign="middle"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_r.png') left 50% no-repeat;"></td>
						</tr>
					</table>
				</td>
				<td width="30%">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;"
								width="2" height="25" valign="middle"></td>
							<td height="25"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
								<div
									style="background: url(<%=request.getContextPath()%>/ images/ xzcjbg/ icon_bg .                                                                     png ) left 50% no-repeat; width: 20px; display: inline;"></div>
								第二步，配置方法信息
							</td>
							<td width="5" height="25" valign="middle"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_r.png') left 50% no-repeat;"></td>
						</tr>
					</table>
				</td>
				<td width="30%">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_l.png') left 50% no-repeat;"
								width="2" height="25" valign="middle"></td>
							<td height="25"
								style="color: white; background: url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_c.png') left 50% repeat-x;">
								<div
									style="background: url(<%=request.getContextPath()%>/ images/ xzcjbg/ icon_bg .                                                                     png ) left 50% no-repeat; width: 20px; display: inline;"></div>
								第三步，配置规则信息
							</td>
							<td width="5" height="25" valign="middle"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_r.png') left 50% no-repeat;"></td>
						</tr>
					</table>
				</td>

			</tr>
		</table>
	</div>

  <freeze:block property="record" caption="修改采集任务信息" width="95%">
  	  <freeze:button name="record_prevRecord" caption="上一步"  onclick="func_record_prevRecord();"/>
      <freeze:button name="record_saveRecord" caption="保存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:hidden property="collect_task_id" caption="采集任务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:cell property="service_targets_id" caption="所属服务对象" show="name" notnull="true" valueset="资源管理_服务对象名称" style="width:95%"/>
      <freeze:cell property="task_name" caption="任务名称" datatype="string" style="width:95%"/>
      <freeze:cell property="data_source_id" caption="数据源" show="name" valueset="资源管理_数据源" notnull="true" style="width:95%"/>
      <freeze:text property="scheduling_day1" caption="采集周期" datatype="string" readonly="true" style="width:80%"/>&nbsp;<INPUT TYPE="button" Value="设置" onclick="chooseCjzq()" class="FormButton">
      <freeze:hidden property="collect_type" caption="采集类型" datatype="string" maxlength="20" />
      
      <freeze:hidden property="task_description" caption="任务说明"  maxlength="2000" style="width:98%"/>
      <freeze:hidden property="record" caption="备案说明" maxlength="2000" style="width:98%"/>
      <freeze:hidden property="is_markup" caption="有效标记" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="创建人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="创建时间" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="最后修改时间" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="fjmc" caption="文件上传" />
      <freeze:hidden property="fj_fk" caption="文件上传id" style="width:90%" />
      <freeze:hidden property="delIDs" caption="multi file id" style="width:90%" />
      <freeze:hidden property="delNAMEs" caption="multi file name" style="width:90%" />
      <freeze:hidden property="task_scheduling_id" caption="计划任务ID"  />
      <freeze:hidden property="scheduling_type" caption="计划任务类型"  />
      <freeze:hidden property="start_time" caption="计划任务开始时间"  />
      <freeze:hidden property="end_time" caption="计划任务结束时间"  />
      <freeze:hidden property="scheduling_week" caption="计划任务周天"  />
      <freeze:hidden property="scheduling_day" caption="计划任务日期"  />
      <freeze:hidden property="scheduling_count" caption="计划任务执行次数"  />
      <freeze:hidden property="interval_time" caption="每次间隔时间"  />
      <freeze:hidden property="log_file_path" caption="日志文件路径"  />
      <freeze:hidden property="collect_status" caption="采集状态"  />
  </freeze:block>
<br>
   <freeze:grid property="dataItem" caption="方法列表" keylist="webservice_task_id" multiselect="false" checkbox="false" width="95%" navbar="bottom" fixrow="false" >
      <freeze:hidden property="webservice_task_id" caption="方法ID"  />
      <freeze:hidden property="collect_task_id" caption="采集任务ID"  />
      <freeze:cell property="@rowid" caption="序号"  style="width:5%" align="center" />
      <freeze:cell property="method_name_cn" caption="方法中文名称" style="width:20%" />
      <freeze:cell property="method_name_en" caption="方法名称" style="width:15%" />
      <freeze:cell property="collect_table" caption="采集表" show="name"
				 valueset="资源管理_采集表" style="width:15%" />
      <freeze:cell property="method_description" caption="方法描述" style="width:30%" />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
