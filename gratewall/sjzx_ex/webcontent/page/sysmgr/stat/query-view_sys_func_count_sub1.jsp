<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ����ʹ��ͳ���б�</title>
<style type="text/css">
#navbar{
	display:none;
}
#frame_record{
	border-collapse:collapse;
}
</style>
</head>

<script language="javascript">
// ��ֹ�����Զ���ã��Է�ֹ�û�ѡ���������ʱ���Զ����ء�
function _setFocus(){
	return;
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	document.getElementById("label:record:@rowid").className = "addBoder";
	document.getElementById("label:record:func_name").className = "addBoder";
	document.getElementById("label:record:querytimes").className = "addBoder";
}

function funcViewDetail(){
	    //document.getElementById("div").style.height = 800;
  		var index = getSelectedRowid("record");
  		var funcName = getFormFieldText("record:func_name",index);
  		var dateFrom = getFormFieldValue("select-key:query_date_from");
  		var dateTo = getFormFieldValue("select-key:query_date_to");
  		var param = '?select-key:func_name='+funcName;
  		if(dateFrom != ''){
  			param += '&select-key:query_date_from='+dateFrom;
  		}
  		if(dateTo != ''){
  			param += '&select-key:query_date_to='+dateTo;
  		}
  		var page = new pageDefine( '/txn60900003.do'+param, "���ӹ���ͳ��","_blank",screen.availWidth-30,screen.availHeight-60 );
	 	page.goPage( null, window );
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption=""/>
<freeze:errors/>

<freeze:form action="/txn60900001">
  <freeze:frame property="select-key" caption=""  width="100%" >
	<freeze:hidden property="func_name" caption="��ҵ(����)ID" style="width:95%"/>
	<freeze:hidden property="query_date_from" caption="��ҵ���" style="width:95%" />
	<freeze:hidden property="query_date_to" caption="��ҵ���ƣ�" style="width:95%"/>
  </freeze:frame>
  <div style="position: absolute;">
  <freeze:grid property="record" caption="�ӹ���ʹ�����ͳ��" width="100%" navbar="bottom" checkbox="false" fixrow="false" onclick="funcViewDetail()" >
  	<freeze:cell property="@rowid" caption="���" align="middle" style="width:6%"/>
    <freeze:cell property="func_name" caption="�ӹ���" style="width:15%"/>
  	<freeze:cell property="querytimes" caption="ʹ�ô���" value="" style="width:15%"/>
  </freeze:grid>
  </div>
</freeze:form>
</freeze:body>
</freeze:html>
