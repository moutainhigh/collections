<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template master-detail-1/detail-table-insert.jsp --%>
<freeze:html width="650" height="200">
<head>
<title>����������Ϣ</title>
</head>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '��������' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '��������' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '��������' );	// /txn8000111.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn8000111.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	//getDataSourceInfo();
	$(".radioNew").each(function(index){
		$($(this).prev()[0]).css("display","");
		$($(this).prev()[0]).css("margin-left","-1000");
		$(this).click(function(){
			$(".radioNew").css("background-position-y","bottom");
			$(this).prev()[0].click();
			if($(this).prev()[0].checked){
				$(this).css("background-position-y","top");
			}
		});
		$(this).next()[0].onclick=function(){
			$(this).prev()[0].click();
			if($(this).prev()[0].checked){
				$(this).css("background-position-y","top");
			}
		};
		if($(this).prev()[0].checked){
			$(this).css("background-position-y","top");
		}
	});
}
//����db_name��ѯ����Դ
function getDataSourceInfo(){
	var page = new pageDefine( "/txn8000116.ajax", "����db_name��ѯ����Դ" );
	page.addParameter("record:db_name","record:db_name");
	page.callback = dataSourceCallBack;
	page.callAjaxService( null, true );
}

function dataSourceCallBack(errorCode, errDesc, xmlResults){
	if(errorCode=="000000"){
		var db_type = _getXmlNodeValue( xmlResults, "/context/select-key/db_type" );
		var jndi_name = _getXmlNodeValue( xmlResults, "/context/select-key/jndi_name" );
		var db_url = _getXmlNodeValue( xmlResults, "/context/select-key/db_url" );
		var db_driver = _getXmlNodeValue( xmlResults, "/context/select-key/db_driver" );
		var db_username = _getXmlNodeValue( xmlResults, "/context/select-key/db_username" );
		var db_password = _getXmlNodeValue( xmlResults, "/context/select-key/db_password" );
		setFormFieldValue("select-key:db_type",db_type);
		setFormFieldValue("select-key:jndi_name",jndi_name);
		setFormFieldValue("select-key:db_url",db_url);
		setFormFieldValue("select-key:db_driver",db_driver);
		setFormFieldValue("select-key:db_username",db_username);
		setFormFieldValue("select-key:db_password",db_password);
	}
}

//��֯��ȡ���ݿ���Schema�Ĳ���
function getDbSchemaList() {
  	var	dbType = getFormFieldValue( 'select-key:db_type' );

  	var params = "";
  	if( dbType == 'datasource' ){
  		params += "database:jndi-name=" + getFormFieldValue("select-key:jndi_name");
	}
	else{
		params += "database:db-url=" + getFormFieldValue("select-key:db_url");
		params += "&database:db-driver=" + getFormFieldValue("select-key:db_driver");
	}
  	params += "&database:db-username=" + getFormFieldValue("select-key:db_username");
  	params += "&database:db-password=" + getFormFieldValue("select-key:db_password");
	
	return params;  	
}
_browse.execute( __userInitPage );
</script>
<freeze:body>
<freeze:title caption="����������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn8000113">
  <freeze:block property="record" caption="����������Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_saveAndContinue" caption="���沢����" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_goBack" caption="�ر�" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_rd_system_id" caption="����" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sys_rd_data_source_id" caption="����ԴID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="db_name" caption="����Դ" datatype="string"  style="width:95%" readonly="true"/>
      <freeze:text property="sys_no" caption="������" datatype="string" maxlength="20" style="width:95%" notnull="true"/>
      <freeze:text property="sys_name" caption="��������" datatype="string" maxlength="100" style="width:95%" notnull="true"/>
      <freeze:text property="sys_simple" caption="ҵ��ϵͳ" datatype="string" maxlength="100" style="width:95%" notnull="true"/>
      
      <freeze:radio property="isshow" caption="�Ƿ����" valueset="��������" notnull="true" style="width:98%" value="1"></freeze:radio>
      <freeze:textarea property="memo" caption="��ע" colspan="2" rows="4" maxlength="500" style="width:98%"/>
      <freeze:hidden property="db_username" caption="�û�" style="width:95%"/>
      <freeze:hidden property="timestamp" caption="ʱ���" datatype="string"  style="width:95%"/>
      <freeze:hidden property="sort" caption="����" datatype="string" maxlength="16" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
