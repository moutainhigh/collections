<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<%
	String rootPath = request.getContextPath();
%>
<freeze:html>
<head>
<style type="text/css">
</style>
<title>�޸�����������Ϣ</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveRecord( '', '������������' );	// /txn105001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var download_purv_id = getFormFieldValue("record:download_purv_id");
	document.getElementById("showCheckPurvFrame").src = 
		"<%=rootPath%>/txn60600001.do?select-key:download_purv_id=" + download_purv_id;
	var radios = document.getElementsByName("record:has_purv");
	for(var ii=0;ii<radios.length;ii++){
		radios[ii].style.display='';
		if(radios[ii].checked){
			$(".radioNew")[ii].style.backgroundPositionY='top';
		}
	}
	$(".radioNew").each(function(index){
		$($(this).prev()[0]).css("display","");
		$($(this).prev()[0]).css("margin-left","-1000");
		$(this).click(function(){
			$(".radioNew").css("background-position-y","bottom");
			$(this).css("background-position-y","top");
			$(this).prev()[0].click();
		});
		$(this).next()[0].onclick=function(){
			fireRadioClickEvent('record:has_purv', index);
			$(".radioNew").css("background-position-y","bottom");
			$($(".radioNew")[index]).css("background-position-y","top");
		};
	});
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸�����������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn105002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="download_purv_id" caption="����" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸�����������Ϣ" width="100%" columns="1">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:hidden property="download_purv_id" caption="����" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="agency_id" caption="�������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:radio property="has_purv" caption="�Ƿ���������" valueset="�Ƿ���������" style="width:95%" notnull="true"/>
      <freeze:text property="max_result" caption="������������" datatype="number" maxlength="10" style="width:95%" notnull="true"/>
      <freeze:hidden property="last_modi_user" caption="����޸���" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modi_date" caption="����޸�����" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:hidden property="reg_org" caption="���ش���" style="width:95%"/>
  </freeze:block>
  <iframe id="showCheckPurvFrame" name="showCheckPurvFrame" style="margin-top:20px;width:100%" frameborder="0"></iframe>
</freeze:form>
</freeze:body>
</freeze:html>
