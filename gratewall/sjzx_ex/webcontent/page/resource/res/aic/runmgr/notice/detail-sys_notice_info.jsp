<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
<title>资料信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit('保存成功','保存失败',"txn53000001.do");
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var sys_notice_filepath = document.getElementById("span_record:sys_notice_filepath");
	sys_notice_filepath.innerHTML = '<a href="#" onclick="chakanfj();">' + sys_notice_filepath.innerHTML + '</a>';
	sys_notice_filepath = null;
}

//查看附件
function chakanfj(){
    
    var sys_notice_id = getFormFieldValue('record:sys_notice_id',0);
    $.ajax({
	  type: "post",
	  url: "<%=request.getContextPath()%>/txn53000011.ajax?primary-key:sys_notice_id=" + sys_notice_id,
	  async: false,
	  success: function(xmlResults){
	  	if (xmlResults.selectSingleNode("//context/error-code").text != "000000"){
	  		alert(xmlResults.selectSingleNode("//context/error-desc").text);
	  		return false;
	  	}else{
	  		var sys_notice_id = _getXmlNodeValues( xmlResults, "/context/record/sys_notice_id" );
			var sys_notice_filepath = _getXmlNodeValues( xmlResults, "/context/record/sys_notice_filepath" );
			//alert(sys_notice_filepath);
			sys_notice_filepath = encodeURI(sys_notice_filepath);
			sys_notice_filepath = encodeURI(sys_notice_filepath);
	        var page = new pageDefine("<%=request.getContextPath()%>/homepage/download.jsp?fid="+sys_notice_id+"&fname="+sys_notice_filepath, "查看附件", "_blank");  
	        //page.addValue(sys_notice_id,"record:sys_notice_id");	
	        //page.addValue(sys_notice_filepath,"record:sys_notice_filepath");
	        page.goPage();
	  	}
	  }
	});     
}


_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="资料信息"/>
<freeze:errors/>

<freeze:form action="/txn53000007">
  <freeze:block property="record" caption="资料信息" width="95%" bodyline="true" border="1">
      <freeze:hidden property="sys_notice_id" caption="资料下载ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:cell property="sys_notice_title" caption="标题" datatype="string" style="width:95%" colspan="2"/>
      <freeze:cell property="sys_notice_matter" caption="内容" colspan="2" style="width:98%"/>
      <freeze:hidden property="sys_notice_promulgator" caption="发布人" datatype="string" style="width:95%" colspan="2"/>
      <freeze:hidden property="sys_notice_org" caption="发布单位" datatype="string" style="width:95%" colspan="2"/>
      <freeze:cell property="sys_notice_date" caption="发布时间" datatype="string" style="width:95%" colspan="2"/>
      <freeze:hidden property="sys_notice_state" caption="发布状态" datatype="string" style="width:95%" colspan="2"/>
      <freeze:cell property="sys_notice_filepath" caption="附件" style="width:95%" colspan="2"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
