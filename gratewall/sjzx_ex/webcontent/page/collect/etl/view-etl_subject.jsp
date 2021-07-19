<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus" %>
<%@ page import = "cn.gwssi.common.context.Recordset" %> 

<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�鿴etl������Ϣ</title>

<link rel="StyleSheet"
		href="<%=request.getContextPath()%>/script/dtree/dtree.css"
		type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/script/easyui/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/script/easyui/icon.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/script/easyui/demo.css">

</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<%
	DataBus  db = (DataBus) request.getAttribute("freeze-databus");
	Recordset subjectData = db.getRecordset("subject");
	Recordset subjectNumData = db.getRecordset("subjectNum");
	
	String target_name = "";
	String subj_name = "";
	String inteval = "";
	String start_time = "";
	String add_type = "";
	String subj_desc = "";
	if(db.getRecord("record").getValue("service_targets_name")!=null){
		target_name = db.getRecord("record").getValue("service_targets_name");
	}
	if(db.getRecord("record").getValue("subj_name")!=null){
		subj_name = db.getRecord("record").getValue("subj_name");
	}
	if(db.getRecord("record").getValue("inteval")!=null){
		inteval = db.getRecord("record").getValue("inteval");
	}
	if(db.getRecord("record").getValue("start_time")!=null){
		start_time = db.getRecord("record").getValue("start_time");
	}
	if(db.getRecord("record").getValue("add_type")!=null){
		if("1".equals(db.getRecord("record").getValue("add_type"))){
			add_type = "����";
		}else if ("2".equals(db.getRecord("record").getValue("add_type"))){
			add_type = "ȫ��";
		}else{
			add_type = db.getRecord("record").getValue("add_type");	
		}
		
	}
	if(db.getRecord("record").getValue("subj_desc")!=null){
		subj_desc = db.getRecord("record").getValue("subj_desc");
	}
	
	int subject_num = 0;//������Ŀ
	int table_num = 0;//���ݱ���Ŀ
	
	if (subjectNumData!=null){
		subject_num = subjectNumData.size();
		  for(int i=0;i<subjectNumData.size();i++){
			   DataBus SingleRecord = (DataBus)subjectNumData.get(i);
			   table_num += Integer.parseInt(SingleRecord.getValue("total").toString());
		  }
	}
	
	
	
%>
<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '����etl�����' );	// /txn30300001.do
}

// �� ��
function func_record_goBack()
{
	//goBack();	// /txn30300001.do
		window.close();
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

function toChangeDiv(type,len){
		for(var i=0;i<len;i++){
			if(i==type){
				$('#div_subject'+i).show();
				$('#li_tab'+i).addClass("tabs-selected");
			}else {
				$('#div_subject'+i).hide();
				$('#li_tab'+i).removeClass("tabs-selected");
			}
		}
		
		
		//if(type=='share'){
			//$('#div_'+type).show();
			//$('#div_collect').hide();
			//$('#li_share').addClass("tabs-selected");
			//$('#li_collect').removeClass("tabs-selected");
			
		//}else{
			//$('#div_share').hide();
			//$('#div_'+type).show();
			//$('#li_collect').addClass("tabs-selected");
			//$('#li_share').removeClass("tabs-selected");
		//}
	}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�鿴etl������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn30300008">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="etl_id" caption="����ID" style="width:95%"/>
  </freeze:frame>
  <br>
  <table class='frame-body' align='center' cellpadding='0' cellspacing='0' style='width: 95%; border: 1px solid #ccc;background-color:#f7f7f7;'>
  
   <tr height="30" >
  	<td align="center" width="30%"   rowspan="3" style="border: 1px solid #ccc"><span  style="color: #006699;"><strong><div style="font-size:18pt;"><%=target_name %></div><br><br><div style="font-size:18px;"><%=inteval %></div></strong></span></td>
  	<td align="center" width="70%" colspan="4" style="border: 1px solid #ccc">����<strong>&nbsp;&nbsp;<span style="font-size:18pt;color: #006699;"><%=subject_num %></span>&nbsp;&nbsp;</strong>��ҵ�����⣬�õ�&nbsp;&nbsp;<strong><span style="font-size:18pt;color: #006699;"><%=table_num %></span>&nbsp;&nbsp;</strong>�����ݱ�</td>
  </tr>
  <tr height="30" >
  	<td align="right" width="15%" style="border: 1px solid #ccc">��������&nbsp;&nbsp;</td>
  	<td align="left" width="20%" style="border: 1px solid #ccc">&nbsp;&nbsp;<%=subj_name %></td>
  	<td align="right" width="15%" style="border: 1px solid #ccc">��������&nbsp;&nbsp;</td>
  	<td align="left" width="20%" style="border: 1px solid #ccc">&nbsp;&nbsp;<%=subj_desc %></td>
  </tr>
  <tr height="30" style="border: 1px solid #ccc">
  	<td align="right" width="15%" style="border: 1px solid #ccc">��ʼִ��ʱ��&nbsp;&nbsp;</td>
  	<td align="left" width="20%" style="border: 1px solid #ccc">&nbsp;&nbsp;<%=start_time %></td>
  	<td align="right" width="15%" style="border: 1px solid #ccc">���ݴ�������&nbsp;&nbsp;</td>
  	<td align="left" width="20%" style="border: 1px solid #ccc">&nbsp;&nbsp;<%=add_type %></td>
  </tr>
  
  <!--  
  <tr height="30"  >
  <td  align="right" width="10%">�������</td><td align="left" width="40%"><%=target_name %></td>
  <td  align="right" width="10%">�������ƣ�</td><td align="left" width="40%"><%=subj_name %></td>
  </tr>
  
  
  <tr height="30" >
  <td  align="right" width="10%">�������ڣ�</td><td align="left" width="40%" ><%=inteval %></td>
  <td  align="right" width="10%">��ʼִ��ʱ�䣺</td><td align="left" width="40%"><%=start_time %></td>
  </tr>
  
  <tr height="30" >
  <td  align="right" width="10%">���ݴ������ͣ�</td><td align="left" width="40%" ><%=add_type %></td>
  <td  align="right" width="10%">����������</td><td align="left" width="40%" ><%=subj_desc %></td>
  </tr>
  -->
  </table>
  
  <br>
  
  <div class="easyui-tabs tabs-container" data-options="tabWidth:112"
		style="width: 98%;margin-top: 5px;margin-left: 35px;">
		<div class="tabs-header" style="width: 99.5%;">
			<div class="tabs-scroller-left" style="display: none;"></div>
			<div class="tabs-scroller-right" style="display: none;"></div>
			<div class="tabs-wrap"
				style="margin-left: 0px; margin-right: 0px; width: 99.5%;">
				<ul class="tabs" style="height: 26px;">
				<% if (subjectNumData!=null){
					
					for(int i=0;i<subjectNumData.size();i++){
						   DataBus SingleRecord = (DataBus)subjectNumData.get(i);
						   String name = SingleRecord.getValue("sys_name");
						   
						   if(i==0){
					%>
						<li class="tabs-selected" id="li_tab<%=i %>" onclick="toChangeDiv('<%=i %>','<%=subjectNumData.size() %>');"><a href="javascript:void(0)"
						class="tabs-inner" style="width: 90px; height: 25px; line-height: 25px;"><span
							class="tabs-title"><%=name %></span><span class="tabs-icon"></span></a></li>
					<%}else{ %>
						<li id="li_tab<%=i %>" onclick="toChangeDiv('<%=i %>','<%=subjectNumData.size() %>');"><a href="javascript:void(0)" class="tabs-inner"
						style="width: 90px; height: 25px; line-height: 25px;"><span
							class="tabs-title"><%=name %></span><span class="tabs-icon"></span></a></li>
					<%} %>
					
					
				<% }
				} %>	
				
				<!-- 
					<li class="tabs-selected" id="li_share" onclick="toChangeDiv('share');"><a href="javascript:void(0)"
						class="tabs-inner" style="width: 90px; height: 25px; line-height: 25px;"><span
							class="tabs-title">�������������</span><span class="tabs-icon"></span></a></li>
					<li id="li_collect" onclick="toChangeDiv('collect');"><a href="javascript:void(0)" class="tabs-inner"
						style="width: 90px; height: 25px; line-height: 25px;"><span
							class="tabs-title">�ɼ�����������</span><span class="tabs-icon"></span></a></li>
				 -->
				</ul>
			</div>
		</div>
		
		<div class="tabs-panels" style="width: 99.5%">
			<div class="panel" style="display: block; width: 99.5%">
				<div title="" style="padding: 0px; width: 99.5%; "
					class="panel-body panel-body-noheader panel-body-noborder">
<!-- ��һ����ǩҳ��ʼ -->
<% if (subjectNumData!=null){
	  for(int i=0;i<subjectNumData.size();i++){
		   DataBus SingleRecord = (DataBus)subjectNumData.get(i);
		   String name = SingleRecord.getValue("sys_name");
		   
		   if(i==0){
		   %>
		   <div style="width: 100%; border: 0;height:400px;padding: 0px; text-align: left; overflow: auto;background-color:#f7f7f7;" id="div_subject<%=i %>">
		   
		   <%} else { %>
		   <div style="width: 100%; border: 0;height:400px; text-align: left; overflow: auto;display: none;background-color:#f7f7f7;" id="div_subject<%=i %>">
		   <%} %>

 <!-- 
  <table  align='center' cellpadding='0' cellspacing='0' width="95%">
  	<tr>
  		<td colspan="4"><%=name %></td>
  	</tr>
  </table>
  -->
  <table class='frame-body' align='center' cellpadding='0' cellspacing='0' style='width: 100%; border: 1px solid #ccc;overflow: auto'>
	<tr height="30" class="grid-headrow">
		<td align="center"  width="10%">���</td>
		<td align="center"  width="30%">������</td>
		<td align="center"  width="30%">�����</td>
		<td align="center"  width="30%">���Ӧ��ϵ����</td>
	</tr>
		    <%
		   int indexNum = 1;
		   
		   for(int j=0;j<subjectData.size();j++){
			   DataBus  subjectRecord = (DataBus)subjectData.get(j);
			   String sys_name = subjectRecord.getValue("sys_name");
			   if(name.equals(sys_name)){//��������һ�µ������
				   String table_cn = "";
				   String table_en = "";
				   String task_name = "";
				   if(subjectRecord.getValue("table_name_cn")!=null){
					   table_cn = subjectRecord.getValue("table_name_cn");
				   }
				   if(subjectRecord.getValue("table_name_en")!=null){
					   table_en = subjectRecord.getValue("table_name_en");
				   }
				   if(subjectRecord.getValue("task_name")!=null){
					   task_name = subjectRecord.getValue("task_name");
				   }
				   %>
		<tr id="row_0" height="30" class='oddrow'>
		<td id="td_@rowid" align="center"><%=indexNum %></td>
		<td id="td_table_name_cn"><%=table_cn %></td>
		<td id="td_table_name_en"><%=table_en %></td>
		<td id="td_task_name"><%=task_name %></td>
	</tr>
				   <%
				   indexNum++;
			   }
		   }%>
	</table>

<!-- ��һ����ǩҳ���� -->
</div>
<% }
  }%>
<!-- �ɼ���ǩҳ��ʼ -->
<!--  <div style="width: 100%; border: 0; text-align: left; display: none; " id="div_collect">-->
	
<!-- �ɼ���ǩҳ����-->
<!--  </div> -->

</div>	
</div>

<table  align='center' cellpadding='0' cellspacing='0' width="95%">
  	<tr><td colspan="2" align="center"><div class="btn_cancel" onclick="func_record_goBack()"></div></td></tr>
  </table>	
</freeze:form>
</freeze:body>
</freeze:html>
