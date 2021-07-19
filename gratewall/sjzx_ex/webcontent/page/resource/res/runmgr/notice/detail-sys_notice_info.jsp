<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
<title>������Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit('����ɹ�','����ʧ��',"txn53000001.do");
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var sys_notice_filepath = document.getElementById("span_record:sys_notice_filepath");
	sys_notice_filepath.innerHTML = '<a href="#" onclick="chakanfj();">' + sys_notice_filepath.innerHTML + '</a>';
	sys_notice_filepath = null;
}

//�鿴����
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
	        var page = new pageDefine("<%=request.getContextPath()%>/homepage/download.jsp?fid="+sys_notice_id+"&fname="+sys_notice_filepath, "�鿴����", "_blank");  
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
<freeze:title caption="������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn53000007">
  <freeze:block property="record" caption="������Ϣ" width="95%" bodyline="true" border="1">
      <freeze:hidden property="sys_notice_id" caption="��������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:cell property="sys_notice_title" caption="����" datatype="string" style="width:95%" colspan="2"/>
      <freeze:cell property="sys_notice_matter" caption="����" colspan="2" style="width:98%"/>
      <freeze:hidden property="sys_notice_promulgator" caption="������" datatype="string" style="width:95%" colspan="2"/>
      <freeze:hidden property="sys_notice_org" caption="������λ" datatype="string" style="width:95%" colspan="2"/>
      <freeze:cell property="sys_notice_date" caption="����ʱ��" datatype="string" style="width:95%" colspan="2"/>
      <freeze:hidden property="sys_notice_state" caption="����״̬" datatype="string" style="width:95%" colspan="2"/>
      <freeze:cell property="sys_notice_filepath" caption="����" style="width:95%" colspan="2"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
