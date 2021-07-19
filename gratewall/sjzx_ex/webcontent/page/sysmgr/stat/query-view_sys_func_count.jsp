<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<%@ page import="com.gwssi.common.database.DBOperation" %>
<%@ page import="com.gwssi.common.database.DBOperationFactory" %>
<%@ page import="java.util.*" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ page import="com.gwssi.sysmgr.txn.ContextToMap"%>
<%
	//���һ�������б�
	DBOperation operation = DBOperationFactory.createTimeOutOperation();
	String sql = "SELECT T.JGMC, T.JGID_PK FROM XT_ZZJG_JG T WHERE T.JGID_PK != 'a0426c26efe541f89a3278349951f0c9' and T.SJJGID_FK is null order by plxh,jgid_pk";
	List agencyList = operation.selectInOrder(sql);
	String[] jgmcArray = new String[agencyList.size()];
	String[] jgidArray = new String[agencyList.size()];
	for (int in = 0; in < agencyList.size(); in++){
		Map agencyMap = (Map)agencyList.get(in);
		jgmcArray[in] = (String)agencyMap.get("JGMC");
		jgidArray[in] = (String)agencyMap.get("JGID_PK");
	}
	
	// �õ������͹��ܵ�����
	DataBus context = (DataBus) request.getAttribute("freeze-databus");
%>
<head>
<title>��ѯ����ʹ��ͳ���б�</title>
<style type="text/css">
#frame_record{
	border-collapse:collapse;
}

.veticalScrollLockTr{
	position:relative;
	top:expression(this.offsetParent.scrollTop);
	z-index:20;
}

.veticalScrollLockTd {
	left: expression(parentNode.parentNode.parentNode.parentNode.scrollLeft);
	position: relative;
	z-index:10;
	border-left:none;
	border-right:1px solid #2975AF;
	border-top:none;
	border-bottom:1px solid #2975AF;
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
}

function resetAllValue(){
	document.getElementById("select-key:query_date_from").value = "";
	document.getElementById("select-key:query_date_to").value = "";
}

function submitValue(){
	var query_date_from = document.getElementById("select-key:query_date_from").value;
	var query_date_to = document.getElementById("select-key:query_date_to").value;
	var rgExp = /^\d\d\d\d-\d\d-\d\d$/;
	if(query_date_from && !query_date_from.match(rgExp)){
		alert("��ִ�������ԡ���ʽ����");
		return false;
	}
	if(query_date_to && !query_date_to.match(rgExp)){
		alert("��ִ������������ʽ����");
		return false;
	}
	
	document.forms[0].submit();
}
_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption=""/>
<freeze:errors/>

<freeze:form action="/txn60900001">
  <freeze:block property="select-key" caption="������ͳ��" width="95%">
      <freeze:datebox property="query_date_from" caption="ִ��������" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:datebox property="query_date_to" caption="��" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:button name="submitButton" caption="�� ѯ" onclick="submitValue()"></freeze:button>
      <freeze:reset property="reset" caption="�� ��" onclick="resetAllValue()"></freeze:reset>
  </freeze:block>
  <br>
  <%if(context != null){
  	Map map = ContextToMap.execute(context);
  %>
  <div align="center">
	  <div style="border-right:1px solid #2975AF;border-left:1px solid #2975AF;padding-bottom:18px;width:95%;overflow-x:auto;overflow-y:visible;">
		  <TABLE id="frame_record" border="1" cellSpacing="0" cellPadding="0" width="95%" align="center" bordercolor="#2975AF">
		  	<tr class="title-row">
		  		<td colspan="<%=3+jgmcArray.length%>"> ������ͳ��ʹ������б�</td>
		  	</tr>
		  	<tr class="grid-headrow">
		  		<td nowrap="true" class="veticalScrollLockTd">���</td>
		  		<td nowrap="true" class="veticalScrollLockTd" align="center">��������</td>
		  		<td nowrap="true" class="veticalScrollLockTd">�ܼ�</td>
		  		<%for(int i = 0; i < jgmcArray.length; i++){%>
		  			<td nowrap="true"><%=jgmcArray[i]%></td>
		  		<%}%>
		  	</tr>
		  	<%int rowid = 0; 
		  	for(Iterator iter = map.keySet().iterator(); iter.hasNext();){
		  		String funcName = (String)iter.next();
		  		Map data = (Map)map.get(funcName);
		  		rowid ++;
		  	%>
		  		<tr class="<%=rowid%2==0?"oddrow":"evenrow"%>">
		  			<td nowrap="true" class="veticalScrollLockTd"><%=rowid%></td>
		  			<td nowrap="true" class="veticalScrollLockTd"><%=funcName%></td>
		  			<td nowrap="true" class="veticalScrollLockTd"><%=data.get("total")%></td>
		  			<%for(int i = 0; i < jgidArray.length; i++){
		  				Object o = data.get(jgidArray[i]);
		  				if(o == null){
		  					o = new String("0");
		  				}
		  				%>
		  				<td nowrap="true"><%=o%></td>
		  			<%}%>
		  		</tr>
		  	<% }%>
		  </table>
	  </div>
  </div>
  <%}%>
</freeze:form>
</freeze:body>
</freeze:html>
