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
	$("#customPageRowSeleted option:gt(0)").remove();
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:errors/>

<freeze:form action="/txn7000207">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="standard_name" caption="�淶����" datatype="string" maxlength="100" style="width:36.5%" colspan="2" />
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="��ѯ�淶�б�" keylist="sys_rd_standard_id" width="95%" navbar="bottom" checkbox="false" fixrow="false" rowselect="false">
     
      <freeze:hidden property="sys_rd_standard_id" caption="��׼ID" style="width:10%" />
      <freeze:cell property="standard_name" caption="�淶����" style="width:25%" />
      <freeze:cell property="standard_issued_unit" caption="������λ" valueset="�淶������λ" style="width:25%" />
      <freeze:hidden property="standard_category_no" caption="�淶�����" style="width:10%" />
      <freeze:cell property="standard_issued_time" caption="����ʱ��" style="width:10%" />
      <freeze:cell property="file_name" caption="�ļ�����"  style="" />
      <freeze:hidden property="file_path" caption="�ļ�·��" style="width:20%" visible="false" />
      <freeze:hidden property="standard_range" caption="�淶��Χ" style="width:20%" visible="false" />
      <freeze:hidden property="standard_category" caption="�淶����" style="width:9%" />
      <freeze:hidden property="memo" caption="��ע" style="width:20%" />
      <freeze:hidden property="sort" caption="�����" style="width:9%" />
      <freeze:hidden property="timestamp" caption="ʱ���" style="width:12%" />
    
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
