<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.vo.VoUser"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>
<%@ page import="com.gwssi.common.util.DateUtil"%>
<%-- template single/single-table-query.jsp --%>
<%
  HttpSession usersession = request.getSession(false);
  VoUser voUser = (VoUser) usersession.getAttribute(TxnContext.OPER_DATA_NODE); 
  String orgCode = voUser.getOrgCode();
%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�־ֵ�¼ͳ���б�</title>
</head>

<script language="javascript">

function queryKs(){

    var count = getFormFieldValues("record:count");
    if(count=="0"){
       return; 
    }    
	var page = new pageDefine("/txn61000004.do", "��ѯ����");
	page.addParameter( "select-key:query_date_start", "select-key:query_date_start" );
	page.addParameter( "select-key:query_date_end", "select-key:query_date_end"  );
	page.addParameter( "record:sjjgid", "select-key:sjjgid" );
	page.goPage();
}

function selectFj(){
    var orgId = '<%=orgCode%>';
    if(orgId!=''){
        return 'orgId=' + orgId ;
    }
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	    setFormFieldValue("select-key:orgcode",0, '<%=orgCode%>');
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�û���¼ͳ���б�"/>
<freeze:errors/>

<freeze:form action="/txn61000003">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:datebox property="query_date_start"  caption="��¼����" prefix="<table width='90%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%" notnull="true"/>
        </td><td width='5%'>��</td><td width='45%'>
      <freeze:datebox property="query_date_end" caption="��¼����" style="width:100%" colspan="0" notnull="true"/>
        </td></tr></table> 
      <freeze:browsebox property="sjjgid" caption="�����־�" style="width:90%" show="name" valueset="�����־�"  parameter="selectFj();" />
      <freeze:hidden property="orgcode" caption="��¼�û�����" style="width:90%" />
  </freeze:block>
<br />
  <freeze:grid property="record" caption="�־ֵ�¼ͳ���б�" keylist="sjjgid" width="95%" checkbox="false" navbar="bottom" fixrow="false" onclick="queryKs()">
      <freeze:cell property="@rowid" caption="���" align="middle" style="width:6%"/>
      <freeze:cell property="query_date" caption="��¼����" style="width:25%" />
      <freeze:cell property="sjjgname" caption="�����־�" style="width:20%" />
      <freeze:cell property="count" caption="��½����" style="width:10%" />
      <freeze:hidden property="sjjgid" caption="�����־�" style="width:10%" />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
