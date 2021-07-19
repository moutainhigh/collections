<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="1000" height="700">
<head>

	<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<title>查看采集任务信息</title>
 <%
    DataBus context = (DataBus) request.getAttribute("freeze-databus");
 	//System.out.println("JSP:==============="+context);
 	Recordset dataitems = null;
 	String column_html="";
    try{
    	dataitems	 = context.getRecordset("dataitems");
    	column_html = "<table class='haa'  width='100%'>";
    	while(dataitems.hasNext()){
    		DataBus data = (DataBus) dataitems.next();
			String table_name_cn = data.getValue("table_name_cn");
			String table_name_en = data.getValue("table_name_en");
			String collect_table_id = data.getValue("collect_table");
			String dataitem = data.getValue("dataitems");
			//dataitem = dataitem.replaceAll(",","&nbsp;&nbsp;|&nbsp;&nbsp;");
			String[] d1 = dataitem.split(","); 
			String tmp = "<table style='table-layout:fixed;' width='100%' cellspacing='0' cellpadding='0'><tr>";
			for(int  ii=0; ii<d1.length; ii++){
				
				tmp += "<td width='"+(100/5)+"%' class='tsd' title='"+d1[ii]+"'><div style='display:inline; white-space:nowrap;'><div class='table_name' >";
				String col_name= d1[ii];
				String column_name = "";
				if(col_name.length()>8){
					column_name = col_name.substring(0,8)+"...";
					tmp += column_name+"</div><div class='line'>|</div></div></td>";
				}else{
					tmp += d1[ii]+"</div><div class='line'>|</div></div></td>";	
				}
				
				if((ii+1) % 5 == 0){
				 	 tmp += "</tr><tr>";
				 }
			}
			if( d1.length % 4 != 0){
				tmp += "<td width='"+(100/5*(5 - d1.length % 5))+"%' colspan='"+(5 - d1.length % 5)+"'>&nbsp;</td>";
				tmp += "</tr>";
			}
			tmp += "</table>";
			column_html += "<tr class='rows'><td nowrap width='15%' valign='top' style='text-align:right;'><span style='color:red;'>[ "+table_name_cn+" ]:</span></td><td colspan='3' valign='top'><div class='divHeightControll'>";
			 column_html +=  tmp;
			 column_html += "</div></td></tr>";
    	}
    	column_html += "</table>";
   }catch(Exception e){
	   System.out.println(e);
   }
   %>
   <style>
   .divHeightControll{line-height:120%;}
	.table_name, .line{  white-space:nowrap; overflow:hidden; text-overflow:ellipsis;text-align:left; display:inline; height:24px; line-height: 1.5;}
	.line{margin: 0 10px; width:5px;}
	.tsd{white-space:nowrap;overflow:hidden;  text-align:right;}
   </style>
</head>
<script language="javascript">
//查看采集数据项表信息
function func_record_viewRecord(idx)
{
	var page = new pageDefine( "/txn30102006.do", "查看方法信息", "modal" );
	page.addValue(idx,"primary-key:webservice_task_id");
	
	page.updateRecord();
}

// 保 存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存采集任务表' );	// /txn30101001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn30101001.do
}
function getParameter(){
	//从当前页面取值，组成key=value格式的串
    var parameter = 'input-data:service_targets_id=' + getFormFieldValue('record:service_targets_id');
	return parameter;
}


// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
    var column_html = "<%=column_html%>";
    if(column_html ==  "<table class='haa'  width='100%'></table>"){
    		$('#limit_table').hide();
	
    }else{
    	document.getElementById('column_name_cn').innerHTML = column_html;
   		 $('#column_space').show()
    }
    if(!(  getFormFieldValue('record:scheduling_day1') =='')){
    	 $('#time_space').show()
    } 
    $('.divHeightControll').each(function(){
    	$(this).find(".line:last").html("&nbsp;");
    })
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:errors/>

<freeze:form action="/txn30101006">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="collect_task_id" caption="采集任务ID" style="width:95%"/>
  </freeze:frame>
<!-- <br>
	<table border=0 cellpadding=0 cellspacing=0 width="95%" align="center"
		style="border-collapse: collapse;">
		<tr>
			<td id='targetName' width="45%" style="font-weight:bold;text-align:right;padding-right:10px;"></td>
			<td width="15%"></td>
			<td id='taskName' style="text-align:left;font-weight:bold;padding-left:10px;"></td>
		</tr>
	</table> -->
	<br/>
 <table  border=0 cellpadding=0 cellspacing=0 width="95%" align="center" style="border-collapse:collapse;">
	<tr>
		<td >
			<div style="width:100%;">
<DIV >
<SPAN style="FONT-STYLE: normal;COLOR: #000000; FONT-SIZE: 13px; FONT-WEIGHT: bold; TEXT-DECORATION: none">基本信息</SPAN>
</DIV>
		</div>
		</td>
	</tr>
</table>	
	
  <freeze:block property="record" caption="查看采集任务信息" width="95%">
	  <freeze:cell property="collect_type" caption="采集类型" show="name" valueset="采集任务_采集类型"  style="width:95%"/>
	  <freeze:cell property="service_targets_id" caption="所属服务对象" show="name" valueset="资源管理_采集任务服务对象名称"  style="width:95%"/>
      <freeze:cell property="data_source_id" caption="数据源" show="name" valueset="资源管理_数据源"  style="width:95%"/>
 	  
      
      
      <freeze:hidden property="task_description" caption="任务说明" colspan="2"  style="width:98%"/>
      <freeze:hidden property="record" caption="备案说明" colspan="2"  style="width:98%"/>
     
	  <freeze:hidden property="collect_task_id" caption="采集任务ID" datatype="string" maxlength="32" style="width:95%"/>
     
      <freeze:hidden property="task_name" caption="任务名称" datatype="string"  style="width:95%"/>
      <freeze:hidden property="task_status" caption="任务状态" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="is_markup" caption="有效标记" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="创建人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="创建时间" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="最后修改时间" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="service_targets_name" caption="服务对象名称" datatype="string" maxlength="32" style="width:95%"/>
   </freeze:block>
   
 	<br/>
 	<DIV id="time_space"  style="display:none;"> 
 <table  border=0 cellpadding=0 cellspacing=0 width="95%" align="center" style="border-collapse:collapse;">
	<tr>
		<td >
			<div style="width:100%;">
<DIV >
<SPAN style="FONT-STYLE: normal;COLOR: #000000; FONT-SIZE: 13px; FONT-WEIGHT: bold; TEXT-DECORATION: none">采集周期</SPAN>
</DIV>
		</div>
		</td>
	</tr>
</table>	  
	<freeze:block property="record" caption="采集周期" width="95%">
	 <freeze:cell property="scheduling_day1" caption="采集周期" datatype="string"  style="width:95%" colspan="2"/>
	   <freeze:cell property="start_time" caption="采集开始时间" datatype="string"  style="width:95%"/>
	   <freeze:cell property="end_time" caption="采集结束时间" datatype="string"  style="width:95%"/>
	</freeze:block>
</DIV>
<br/>
<DIV id="column_space"  style="display:none;"> 
 <table  border=0 cellpadding=0 cellspacing=0 width="95%" align="center" style="border-collapse:collapse;">
	<tr>
		<td >
			<div style="width:100%;">
<DIV >
<SPAN style="FONT-STYLE: normal;COLOR: #000000; FONT-SIZE: 13px; FONT-WEIGHT: bold; TEXT-DECORATION: none">采集表字段信息</SPAN>
</DIV>
		</div>
		</td>
	</tr>
</table>	
	
  <freeze:block property="record" caption="服务字段信息" width="95%">
		 <tr valign="center" id="row_0" height="32">
		
		<td colspan='4'><div id="column_name_cn" style="width:95%"><%=column_html%></div>
	</td>
</tr>
   </freeze:block>
 </DIV>
</freeze:form>
<table border=0 cellpadding=0 cellspacing=0 width="95%" align="center" style="border-collapse:collapse;">
<tr>
	<td height="30" align="center">
	<div onclick="func_record_goBack()" class="btn_cancel"></div>
	</td>
</tr>
</table>
</freeze:body>
</freeze:html>
