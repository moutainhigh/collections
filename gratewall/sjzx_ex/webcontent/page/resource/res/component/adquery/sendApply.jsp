<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="cn.gwssi.common.context.vo.VoUser"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>
<%@ page import="com.gwssi.common.util.DateUtil"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%
  HttpSession usersession = request.getSession(false);
  VoUser voUser = (VoUser) usersession.getAttribute(TxnContext.OPER_DATA_NODE); 
  String sjjgname = "";
  if (voUser.getValue("sjjgname") != null){
	  sjjgname = voUser.getValue("sjjgname");
  }
  
  DataBus context = (DataBus) request.getAttribute("freeze-databus");
  DataBus record=context.getRecord("record");
  DataBus condition=context.getRecord("condition");
  DataBus selectKey=context.getRecord("select-key");
  DataBus pruv=context.getRecord("pruv");
  //System.out.println(pruv);
  String reg_org = pruv.getValue("reg_org");
  String sql=record.getValue("query_sql");
  System.out.println("申请sql:"+sql);
	//如果为区县用户
	if(StringUtils.isNotBlank(reg_org)){
	  if(sql.indexOf("REG_BUS_ENT")!=-1){
		  if(sql.indexOf("WHERE")!=-1){
			  sql+=" AND REG_BUS_ENT.DOM_DISTRICT ='"+reg_org+"' ";
		  }else{
			  sql+=" WHERE REG_BUS_ENT.DOM_DISTRICT ='"+reg_org+"' ";
		  }
	  }else{
		  if(sql.indexOf("WHERE")!=-1){
			  sql+=" AND REG_INDIV_BASE.REG_ORG LIKE '"+reg_org+"%' ";
		  }else{
			  sql+=" WHERE REG_INDIV_BASE.REG_ORG LIKE '"+reg_org+"%' ";
		  }
	  }
	}
	record.setValue("query_sql",sql);
%>
<freeze:html>
<head>
<title>数据下载</title>
<style type="text/css">
#totalTableDiv{
	display:none;
}
</style>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.sp"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/processBarPlugin.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/page/page-exec-apply.js"></SCRIPT>
<script type="text/javascript">
	window.totalRecords = "<%=selectKey.getValue("totalRecords") %>";
	window.max_result = "<%=pruv.getValue("max_result") %>";
	window.rootPath = "<%=request.getContextPath()%>";
	window.isDownload = "<%=pruv.getValue("has_purv")%>";
</script>
</head>

<script language="javascript">


function __userInitPage(){
	var currDate = "<%=DateUtil.getYMDTime()%>";
	setFormFieldValue("record:apply_date", currDate);
    }

function toReturn(){
 window.returnValue=document.getElementById('checkFlag').value;
}


_browse.execute("__userInitPage()");
</script>
<freeze:body onunload="toReturn();">
<input type="hidden" id="checkFlag" value="0"/>
<form action="http://www.baidu.com" id="form_sq" name="form_sq" method="post" target="downloadIFrame" style="margin:0;padding:0">
    <input type="hidden" name="record:query_condition" id="query_condition" value=""/>
    <div id="div_query_condition" style="display: none">
      <%=condition.getValue("sqlCondition")%>
    </div>
    
    <input type="hidden" name="record:columnsenarray" id="columnsenarray" value="<%=record.getValue("display_columns_en_array")%>"/>
    <input type="hidden" name="record:columnscnarray" id="columnscnarray" value="<%=record.getValue("display_columns_cn_array")%>"/>
    <input type="hidden" name="record:apply_count" value="<%=selectKey.getValue("totalRecords") %>"/>
    <input type="hidden" name="record:display_name" id="display_name"/>
    <input type="hidden" name="record:sql" id="apply_sql" value="<%=record.getValue("query_sql")%>"/>
    <input type="hidden" name="record:adquery_id" id="apply_sql" value="<%=request.getParameter("select-key:sys_advanced_query_id")%>"/>
    <input type="hidden" name="record:status" id="apply_status"/>
    <input type="hidden" name="record:fj_file_path" id="fj_file_path"/>
    <input type="hidden" name="record:fj_file_name" id="fj_file_name"/>
    
	<freeze:block property="record" caption="申请详细信息" width="95%">
      <freeze:text property="apply_name" caption="申请名称：" datatype="string" minlength="1" maxlength="50" style="width:95%"/>
      <freeze:cell property="apply_date" caption="申请日期：" style="width:95%"/>
      <tr style="height:32px">
      	<td align="right" valign="bottom" width="100">申请机构：</td>
      	<td align="left" valign="bottom"><%=sjjgname%><%=voUser.getOrgName()%></td>
      	<td align="right" valign="bottom">申请人：</td>
      	<td align="left" valign="bottom"><%=voUser.getOperName()%></td>
      </tr>
      <tr style="height:32px">
      	<td align="right" valign="bottom">申请条数：</td>
      	<td  align="left" valign="bottom"><%=selectKey.getValue("totalRecords")%></td>
      	<td align="left" valign="bottom">使用单位:</td>
      	<td  align="left" valign="bottom">
      	<input type="text" name="record:operdept" id="operdept" maxlength="50 ">
      <div style="display: none">
      	<input type="radio" name="record:is_mutil_download" id="is_mutil_download_Y" value="1" onclick="isMutilDownloadRadio()" /><label for="is_mutil_download_Y">是</label>
      	<input type="radio" name="record:is_mutil_download" id="is_mutil_download_N" value="0" checked="true" onclick="isMutilDownloadRadio()" /><label for="is_mutil_download_N">否</label>
      </div>
      </tr>
      <freeze:textarea property="apply_reason" caption="用途：" colspan="2" rows="4" minlength="1" maxlength="1000" style="width:98%"/>
 </form>
 <form action="/txn60300010.do" id="uploadForm" method="post" target="fileUploadFrame" enctype="multipart/form-data">
      <tr><td>附件:</td>
      <td colspan="3"><input type="file" name="FJ_FLIE"/>
      <input type="button" id="genTempTable" class="menu" onclick="genTempTableHandler()" value="上传附件" />
      <div id="uploadFileProcessBarDiv" style="width:65%;display:none"></div>
      </td>
      </tr>
 </form>
      <freeze:button name="record_saveRecord"  caption=" 提交 " onclick="sendApply()" />
    </freeze:block>
   

    <div align="center">
      <iframe id="downloadIFrame" name="downloadIFrame" frameborder="0" style="width:95%;display:none"></iframe>
    </div>
    <div align="center" id="download_type" style="display:none">
    	<a href="#" id="d_excel" onclick="downloadData('xls')">EXCEL下载</a>
    	<a href="#" id="d_txt" onclick="downloadData('txt')">TXT下载</a>
    	<a href="#" id="d_database" onclick="downloadData('database')">数据库下载</a>
    	<div id="processBar" style="width:300px;" style="display:none;"></div>
    </div>
    <script type="text/javascript">
    	try{
    		document.writeln("<br>" + dialogArguments.document.getElementById("totalTableDiv").outerHTML);
    	}catch(e){};
    </script> 
    
<div style="display: none">
	<iframe src="" id="fileUploadFrame" name="fileUploadFrame"></iframe>
</div>


</freeze:body>
</freeze:html>
