<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<script src="<%=request.getContextPath() %> /script/common/js/validator.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<%@page import="com.gwssi.common.constant.ExConstant"%>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="450" height="200">
<head>
<title>����ɼ����ݱ�excel��Ϣ</title>
</head>
<script language='javascript' src='/script/uploadfile.js'></script>
<script language="javascript">
var is_name_used = 1;
var table_id="";
// �� ��
function func_record_saveAndExit(){

	var fjmc=document.getElementById('record:fjmc').value;
	if(fjmc==null||fjmc==""){
	alert("���ȵ����ļ�!");
	return false;
	}else{
	saveAndExit( '', '����excel' );
	}
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	 $("#main_record tr").each(function(){
  	$(this).find("td:first").css("white-space", "nowrap");
  });
}
//�� ��
function func_record_goBack()
{
	goBack("/txn20209001.do");	// /txn20201001.do
}
_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�����ṹ"/>
<freeze:errors/>

<freeze:form action="/txn20201010" enctype="multipart/form-data">
 
  <freeze:block property="record" caption="�ɼ����ݱ���Ϣ" width="100%">
    <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
  	<freeze:button name="record_goBack" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_goBack();"/>
  	<freeze:file property="fjmc" caption="����excel" style="width:80%" accept="*.xls" maxlength="100" colspan="2"/>
    <freeze:hidden property="fj_fk" caption="�ļ��ϴ�id" style="width:90%" />
    <tr><td>����excelģ��</td><td colspan="3"><a title='����' href='/downloadFile?file=<%=ExConstant.RES_TBL_RECORD%>/RES_COLLECT_DATAITEM.xls&&fileName=RES_COLLECT_DATAITEM.xls' onclick=''><div class='download'></div></a></td></tr>
  </freeze:block>
  
</freeze:form>

</freeze:body>
</freeze:html>
