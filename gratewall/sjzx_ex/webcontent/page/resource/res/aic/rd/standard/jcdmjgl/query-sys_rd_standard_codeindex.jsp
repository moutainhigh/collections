<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ���뼯�б�</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// ���Ӵ��뼯
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_standard_codeindex.jsp", "���Ӵ��뼯", "modal" );
	page.addRecord();
}

// �޸Ĵ��뼯
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn7000404.do", "�޸Ĵ��뼯", "modal" );
	page.addParameter( "record:sys_rd_standar_codeindex", "primary-key:sys_rd_standar_codeindex" );
	page.updateRecord();
}
// ɾ�����뼯
function func_record_deleteRecord()
{ 
     var page = new pageDefine( "/txn7000411.ajax", "��ѯ���������" ); 
     page.addParameter("record:sys_rd_standar_codeindex", "select-key:sys_rd_standar_codeindex"); 
	 page.callAjaxService('doCallback');
	 page.callAjaxService( null, true );
}

function doCallback(errorCode, errDesc, xmlResults) 
{ 
    //�����ú�������ȡʹ��Ajax�����õ�������
	if ( errorCode != '000000' ) {
		alert("��ѯ��¼ʧ�ܣ����������");
		return;
	} 
	var codeIndex = _getXmlNodeValues(xmlResults, 'record:sys_rd_standar_codeindex');
	
	if(codeIndex != ''){
		alert("����ɾ�����뼯��Ĵ���ֵ��");
		return;
	}
	
	var page = new pageDefine( "/txn7000405.do", "ɾ���������뼯" );
	page.addParameter( "record:sys_rd_standar_codeindex", "primary-key:sys_rd_standar_codeindex" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
	
}

// �鿴���뼯
function func_record_viewRecord()
{
	var page = new pageDefine( "/txn7000406.do", "�鿴���뼯", "modal" );
	page.addParameter( "record:sys_rd_standar_codeindex", "primary-key:sys_rd_standar_codeindex" );
	
	page.viewRecord();
}

//�鿴����-���ڰ�ť
function func_record_codeRecord_button()
{
	var page = new pageDefine( "/txn7000411.do", "�鿴���뼯");
	page.addParameter( "record:sys_rd_standar_codeindex", "select-key:sys_rd_standar_codeindex" );
	page.addParameter( "record:codeindex_name", "select-key:codeindex_name" );
	page.goPage();
}

// �鿴����
function func_record_codeRecord()
{
	if (event.srcElement.tagName.toUpperCase() == 'LABEL'){
		return;
	}
	if (event.srcElement.tagName.toUpperCase() == 'INPUT'){
		return;
	}
	if (event.srcElement.tagName.toUpperCase() == 'A'){
		return;
	}
	var page = new pageDefine( "/txn7000411.do", "�鿴���뼯");
	page.addParameter( "record:sys_rd_standar_codeindex", "select-key:sys_rd_standar_codeindex" );
	page.addParameter( "record:codeindex_name", "select-key:codeindex_name" );
	page.goPage();
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{

}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ���뼯�б�"/>
<freeze:errors/>

<freeze:form action="/txn7000401">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="sys_rd_standar_codeindex" caption="���뼯��ʶ��" datatype="string" maxlength="255" style="width:90%"/>
       <freeze:text property="codeindex_name" caption="���뼯����" datatype="string" maxlength="255" style="width:90%"/>
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="��ѯ���뼯�б�" keylist="sys_rd_standar_codeindex" width="95%" navbar="bottom" fixrow="false" onclick="func_record_codeRecord();">
      <freeze:button name="record_addRecord" caption="���Ӵ��뼯" txncode="7000403" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸Ĵ��뼯" txncode="7000404" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ�����뼯" txncode="7000405" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:button name="record_viewRecord" caption="�鿴���뼯" txncode="7000406" enablerule="1" hotkey="" align="right" onclick="func_record_viewRecord();"/>
      <freeze:button name="record_codeRecord" caption="��������" txncode="7000411" enablerule="1" hotkey="CODE" align="right" onclick="func_record_codeRecord_button();"/>
      <freeze:cell property="sys_rd_standar_codeindex" caption="���뼯��ʶ��" style="width:10%" />
      <freeze:cell property="codeindex_name" caption="���뼯����" style="width:26%" />
      <freeze:cell property="codeindex_category" caption="���뼯����" valueset="���뼯����" style="width:13%" />
      <freeze:cell property="representation" caption="��ʾ" style="width:10%" />
      <freeze:cell property="standard_codeindex_version" caption="�汾" style="width:13%" />
      <freeze:cell property="code_table" caption="�����" style="width:22%" />
      <freeze:cell property="coding_methods" caption="���뷽��" style="width:20%" visible="false" />
      <freeze:cell property="description" caption="˵��" style="width:20%" visible="false" />
 
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
