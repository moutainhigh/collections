<%
/*
 * @Header ����ά���Ҳ���ϸҳǩ���ҳ��
 * @Revision
 * @Date 2007-03-01
 * @author adaFang
 * =====================================================
 *  ���������Ŀ��
 * =====================================================
 */
%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus" %>
<freeze:html>
<head>
<title>������ϸ��Ϣ</title>
</head>
<freeze:body>
<freeze:title caption="��������Ա��Ϣ"/>
<freeze:errors/>

<script language="javascript">
function showPage_1(caption){
  var param = "primary-key:jgid_pk="+ document.getElementById("select-key:jgid_pk").value;
  setTabUrl( "/txn806004.do?" + param );
}
function showPage_0(caption){

 var param = "select-key:jgid_fk="+ document.getElementById("select-key:jgid_pk").value;
 	param += "&select-key:jgname="+ document.getElementById("select-key:jgname").value; 
  setTabUrl( "/txn807011.do?" + param );
}
      
</script>

<freeze:form action="/txn806001">
  <freeze:frame property="select-key" width="95%">
 	<freeze:hidden name="jgid_pk" property="jgid_pk" />
 	<freeze:hidden name="jgname" property="jgname" />
  </freeze:frame>
  
  <freeze:tabs name="pageList" width="100%" bordercolor="#ff0000" focusIndex="1" border="0">
    <freeze:tab caption="��Ա��Ϣ" onclick="showPage_0(caption)" alt="��Ա��Ϣ"/>
    <freeze:tab caption="������Ϣ" onclick="showPage_1(caption)" alt="������Ϣ"/>
  </freeze:tabs>

<script>
<%
	//ˢ��������ʾҳ��
	try{
 		// ��ȡˢ�²���
        DataBus db = (DataBus)request.getAttribute("freeze-databus");
        // ˢ����������
        if (db.getValue("refresh") != null && !db.getValue("refresh").equals(""))
        {
        %>
        
          var jgid = document.getElementById("select-key:jgid_pk").value;
          var param = "select-key:selectedid="+jgid;
          window.parent.frames['xtreeframe'].location.href = "<%=request.getContextPath()%>/txn806001.do?"+param;
        <%
        }
	} catch(Exception e){
		e.printStackTrace();
	}
      %>
</script>
</freeze:form>
</freeze:body>
</freeze:html>
