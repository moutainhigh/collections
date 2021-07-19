<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:include href="/script/lib/jquery162.js"/>
<freeze:include href="/script/lib/colResizable-1.3.min.js"/> 
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�淶�б�</title>
</head>
<%
String path=java.util.ResourceBundle.getBundle("app").getString("docFilePath");
%>
<script language="javascript">

// ���ӹ淶
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_standard.jsp", "���ӹ淶", "modal" );
	page.addParameter( "record:sys_rd_standard_id", "record:sys_rd_standard_id" );
	page.addRecord();
}

// �޸Ĺ淶
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn7000204.do", "�޸Ĺ淶", "modal" );
	page.addParameter( "record:sys_rd_standard_id", "primary-key:sys_rd_standard_id" );
	page.updateRecord();
}

// ɾ�����뼯
function func_record_deleteRecord()
{ 
     var page = new pageDefine( "/txn7000211.ajax", "��ѯʵ����Ϣ" ); 
     page.addParameter("record:sys_rd_standard_id", "select-key:sys_rd_standard_id"); 
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
	var codeIndex = _getXmlNodeValues(xmlResults, 'record:sys_rd_standard_id');
	
	if(codeIndex != ''){
		alert("����ɾ�����뼯��Ĵ���ֵ��");
		return;
	}
	
	var page = new pageDefine( "/txn7000205.do", "ɾ���淶" );
	page.addParameter( "record:sys_rd_standard_id", "primary-key:sys_rd_standard_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
	
}
// ��ѯ�淶
function func_record_viewRecord()
{
	var page = new pageDefine( "/txn7000206.do", "�鿴�淶", "modal" ,750,200);
	page.addParameter( "record:sys_rd_standard_id", "primary-key:sys_rd_standard_id" );
	page.viewRecord();
}
// ��ѯʵ��-buttonר��
function func_record_stxxRecord_button()
{
	var page = new pageDefine( "/txn7000211.do", "ʵ����Ϣ");
	page.addParameter( "record:sys_rd_standard_id", "select-key:sys_rd_standard_id" );
	page.addParameter( "record:standard_name", "select-key:standard_name" );
	
	page.goPage();
}

// ��ѯʵ��
function func_record_stxxRecord()
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
	var page = new pageDefine( "/txn7000211.do", "ʵ����Ϣ");
	page.addParameter( "record:sys_rd_standard_id", "select-key:sys_rd_standard_id" );
	page.addParameter( "record:standard_name", "select-key:standard_name" );
	
	page.goPage();
}


// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	//   $("table #record").colResizable();
	var rootPath = '<%=request.getContextPath()%>';
	var paths = getFormAllFieldValues("record:file_path");
	var filenames = getFormAllFieldValues("record:file_name");
	var standard_names = getFormAllFieldValues("record:standard_name");
	for(var i=0;i<standard_names.length;i++){
		var url = paths[i];
		var filename = filenames[i];
		var rname=encodeURI(filename);
  	   // var url = "/downloadFile?file=<%=path%>"+url+"&&fileName="+rname;
  	    var url = "/downloadFile?file="+url+"&&fileName="+rname;
  	    var ihtml ="<a title='����' href='"+url+"' ;>"+filename+"</a>";
	    document.getElementsByName("span_record:file_name")[i].innerHTML = ihtml;
	}
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ�淶�б�"/>
<freeze:errors/>

<freeze:form action="/txn7000201">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="standard_name" caption="�淶����" datatype="string" maxlength="100" style="width:36.5%" colspan="2" />
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="��ѯ�淶�б�" keylist="sys_rd_standard_id" width="95%" navbar="bottom" fixrow="false" onclick="func_record_stxxRecord();">
      <freeze:button name="record_addRecord" caption="���ӹ淶" txncode="7000203" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸Ĺ淶" txncode="7000204" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ���淶" txncode="7000205" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:button name="record_viewRecord" caption="�鿴�淶" txncode="7000206" enablerule="1" hotkey="" align="right" onclick="func_record_viewRecord();"/>
      <freeze:button name="record_stxxRecord" caption="��Ϣʵ��" txncode="7000211" enablerule="1" hotkey="" align="right" onclick="func_record_stxxRecord_button()"/>
      
      <freeze:hidden property="sys_rd_standard_id" caption="��׼ID" style="width:10%" />
      <freeze:cell property="standard_name" caption="�淶����" style="width:16%" />
      <freeze:cell property="standard_issued_unit" caption="�淶������λ" valueset="�淶������λ" style="width:8%" />
      <freeze:cell property="standard_category_no" caption="�淶�����" style="width:10%" />
      <freeze:cell property="standard_issued_time" caption="�淶����ʱ��" style="width:10%" />
      <freeze:cell property="file_name" caption="�ļ�����"  style="width:16%" />
      <freeze:hidden property="file_path" caption="�ļ�·��" style="width:20%" visible="false" />
      <freeze:hidden property="standard_range" caption="�淶��Χ" style="width:20%" visible="false" />
      <freeze:hidden property="standard_category" caption="�淶����" style="width:9%" />
      <freeze:cell property="memo" caption="��ע" style="width:20%" visible="false" />
      <freeze:hidden property="sort" caption="�����" style="width:9%" />
      <freeze:hidden property="timestamp" caption="ʱ���" style="width:12%" />
    
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
