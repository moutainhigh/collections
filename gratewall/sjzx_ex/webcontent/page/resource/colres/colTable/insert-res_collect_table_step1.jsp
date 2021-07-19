<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@page import="cn.gwssi.common.dao.resource.PublicResource"%>
<%@page import="cn.gwssi.common.dao.resource.code.CodeMap"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script src="<%=request.getContextPath() %> /script/common/js/validator.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-data2table/js/jquery.tablet.js"></script>
<link href="<%=request.getContextPath()%>/script/jquery-plugin-Selector/css/jquery.dataSelector.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %> /script/common/js/validator.js"></script>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="1000" height="400">
<head>
<title>增加采集数据表信息</title>
<style type="text/css">
</style>
</head>

<script language="javascript">
<%
TxnContext tbTypeContext = new TxnContext();
CodeMap codeMap = PublicResource.getCodeFactory();
Recordset rs = codeMap.lookup(tbTypeContext, "资源管理_数据项类型");
String tmp = "";
out.print("var dataitem_types = new Array; ");
for(int ii=0; ii<rs.size(); ii++){
	out.println("dataitem_types.push({\"key\": \""+rs.get(ii).get("codevalue")
		+"\", \"title\": \""+rs.get(ii).get("codename")+"\"});");
}
%>
<%
TxnContext codeTbContext = new TxnContext();
rs = codeMap.lookup(codeTbContext, "资源管理_对应代码表");
out.print("var code_table_list = new Array;");
for(int ii=0; ii<rs.size(); ii++){
	out.println("code_table_list.push({\"key\": \""+rs.get(ii).get("codevalue")
		+"\", \"title\": \""+rs.get(ii).get("codename")+"\"});");
}
%>
var now_adding = -1;
var is_name_used = 1;
var row_index = -1;
var now_line_no = -1;
var table_id="";
// 保 存
function func_record_saveRecord(){
	var en=getFormFieldValue('record:table_name_en');
	if(!checkEnName(en,'表名称')){
	return false;
	}
	var page = new pageDefine("/txn20201000.ajax", "检查数据表名是否已经使用", "window");	
	page.addParameter("record:table_name_en","primary-key:table_name_en");
	page.addParameter("record:collect_table_id","primary-key:collect_table_id");
	page.callAjaxService('nameCheckCallback');
}
function nameCheckCallback(errCode,errDesc,xmlResults){
		is_name_used = 1;
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}
		is_name_used=_getXmlNodeValues(xmlResults,'record:name_nums');
		if(is_name_used>0){
  			alert("表名称已存在，请重新起名");
  		}else{
  		  var item=getFormFieldValue('record:service_targets_id');
  		  if(!checkItem(item,"100","所属服务对象")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:table_name_en');
  		  if(!checkItem(item,"100","表名称")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:table_name_cn');
  		  if(!checkItem(item,"100","表中文名称")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:table_type');
  		  if(!checkItem(item,"100","表类型")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:table_desc');
  		  if(!checkItemLength(item,"1000","表描述")){
  		    return false;
  		  }
	      saveRecord( '', '保存采集数据表信息表' );
  		}
}
function insertTable(errCode,errDesc,xmlResults){
		
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}else{
		    table_id=_getXmlNodeValues(xmlResults,'record:collect_table_id');
		    setFormFieldValue("record:collect_table_id",table_id);
		    setFormFieldValue("primary-key:collect_table_id",table_id);
		    alert("保存成功!");
		}
}


// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存采集数据表信息表' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	var en=getFormFieldValue('record:table_name_en');
	if(!checkEnName(en,'表名称')){
	return false;
	}
	var page = new pageDefine("/txn20201000.ajax", "检查数据表名是否已经使用");	
	page.addParameter("record:table_name_en","primary-key:table_name_en");
	page.callAjaxService('nameCheckCallback2');
}
function nameCheckCallback2(errCode,errDesc,xmlResults){
		is_name_used = 1;
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}
		is_name_used=_getXmlNodeValues(xmlResults,'record:name_nums');
		if(is_name_used>0){
  			alert("表名称已存在，请重新起名");
  		}else{
  		   saveAndExit( '', '保存采集数据表信息表' );
  		}
}

// 返 回
function func_record_goBack()
{
	//var page = new pageDefine( "/txn20201001.do", "查询采集数据表");
	//page.updateRecord();
	goBack("/txn20201001.do");	// /txn20201001.do
}

// 修改采集数据项表信息
function func_record_updateRecord(idx)
{
	var page = new pageDefine( "/txn20202004.do", "修改采集数据项信息" );
	page.addValue(idx,"primary-key:collect_dataitem_id");
	var collect_table_id=getFormFieldValue('record:collect_table_id');
	page.addValue(collect_table_id,"primary-key:collect_table_id");
	
	page.updateRecord();
}

function creatTableCheck(errCode,errDesc,xmlResults){
		is_name_used = 1;
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}
		is_name_used=_getXmlNodeValues(xmlResults,'record:name_nums');
		if(is_name_used>0){
		
		if(confirm("采集库已存在此数据表名称且数据表里已有数据，是否继续生成该数据表?")){
		   var page = new pageDefine( "/txn20201008.ajax", "生成采集数据表!");
  		   page.addParameter("record:collect_table_id","record:collect_table_id");
  		   page.addValue(is_name_used,"record:name_nums");
  		   page.callAjaxService('creatTable');
		}
		
	  		//alert("采集库已存在此数据表名称且数据表里已有数据,不能再生成该表!");
	  		//return false;
  		}else if(is_name_used==-1){
  		
	  		if(confirm("采集库已存在此数据表名称,但数据表里没有数据，是否继续生成该数据表?")){
			   var page = new pageDefine( "/txn20201008.ajax", "生成采集数据表!");
	  		   page.addParameter("record:collect_table_id","record:collect_table_id");
	  		   page.addValue(is_name_used,"record:name_nums");
	  		   page.callAjaxService('creatTable');
			}
			
			//alert("采集库已存在此数据表,不能再生成该表!");
  			//return false;
  		}else{
  		 if(confirm("是否生成数据表?")){
	  		   var page = new pageDefine( "/txn20201008.ajax", "生成采集数据表!");
	  		   page.addParameter("record:collect_table_id","record:collect_table_id");
	  		   page.addValue(is_name_used,"record:name_nums");
	  		   page.callAjaxService('creatTable');
  		   }
  		}
}
function creatTable(errCode,errDesc,xmlResults){
		is_name_used[0] = 1;
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}
		else{
		   alert("生成表成功!");
		}
}


// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var collect_table_id = getFormAllFieldValues("record:collect_table_id");
	
	if(collect_table_id!=""){
	  alert("保存成功!");
	}
	
	var ids = getFormAllFieldValues("dataItem:collect_dataitem_id");
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
<freeze:title caption="增加采集数据表信息"/>
<freeze:errors/>

<freeze:form action="/txn20201003" enctype="multipart/form-data">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="collect_table_id" caption="采集数据表ID" style="width:95%"/>
  </freeze:frame>
  <div style="width:95%;margin-left:20px" >
  <table style="width:65%"  >
   <tr>
   
    <td width="30%">
   
    	<table width="100%" cellpadding="0" cellspacing="0">
    		<tr>
    			<td style="background:url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_l.png') left 50% no-repeat;" width="2" height="25" valign="middle"></td>
    			<td height="25" style="color:white;background:url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_c.png') left 50% repeat-x;">
    				<div style="background:url(<%=request.getContextPath()%>/images/xzcjbg/icon_bg.png) left 50% no-repeat; width:20px; display: inline;"></div>
					第一步,录入采集表基本信息</td>
    			<td width="5" height="25" valign="middle" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_r.png') left 50% no-repeat;"></td>
    		</tr>
    	</table>
    </td>
    <td width="30%" >
    	<table width="100%" cellpadding="0" cellspacing="0">
    		<tr>
    			<td style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;" width="2" height="25" valign="middle"></td>
    			<td height="25" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
    				<div style="background:url(<%=request.getContextPath()%>/images/xzcjbg/icon_bg.png) left 50% no-repeat; width:20px; display: inline;"></div>
					   第二步,录入采集表字段信息</td>
    			<td width="5" height="25" valign="middle" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_r.png') left 50% no-repeat;"></td>
    		</tr>
    	</table>
    </td>
    <td width="30%">
 		<table width="100%" cellpadding="0" cellspacing="0">
    		<tr>
    			<td style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;" width="2" height="25" valign="middle"></td>
    			<td height="25" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
    				<div style="background:url(<%=request.getContextPath()%>/images/xzcjbg/icon_bg.png) left 50% no-repeat; width:20px; display: inline;"></div>
					第三步,预览并生成表</td>
    			<td width="5" height="25" valign="middle" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_r.png') left 50% no-repeat;"></td>
    		</tr>
    	</table>   
     </td>
     
   </tr> 
  </table>
  </div>
  
  <freeze:block property="record" caption="采集数据表信息" width="95%">
  	  <freeze:button name="record_nextRecord" caption="下一步" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:hidden property="collect_table_id" caption="采集数据表ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:browsebox property="service_targets_id" caption="所属服务对象" show="name" notnull="true" valueset="资源管理_服务对象名称"   style="width:95%"/>
      <freeze:text property="table_name_en" caption="表名称" datatype="string" notnull="true"  style="width:95%"/>
      <freeze:text property="table_name_cn" caption="表中文名称" datatype="string"  notnull="true"  style="width:95%"/>
      <freeze:select property="table_type" caption="表类型" show="name" valueset="资源管理_表类型" notnull="true" style="width:95%"/>
      <freeze:textarea property="table_desc" caption="表描述" colspan="2" rows="2"  style="width:98%"/>
      <freeze:hidden property="table_status" caption="表状态" datatype="string"  style="width:95%"/>
      <freeze:hidden property="is_markup" caption="有效标记" datatype="string"  style="width:95%"/>
      <freeze:hidden property="creator_id" caption="创建人ID" datatype="string"  style="width:95%"/>
      <freeze:hidden property="created_time" caption="创建时间" datatype="string"  style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" datatype="string"  style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="最后修改时间" datatype="string"  style="width:95%"/>
      <freeze:hidden property="cj_ly" caption="采集来源" datatype="string"  style="width:95%"/>
      <freeze:hidden property="if_creat" caption="采集库是否生成采集表" datatype="string"  style="width:95%"/>
  </freeze:block>
</freeze:form>

</freeze:body>
</freeze:html>
