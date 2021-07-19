<%
/*
 * @Header 机构维护右侧详细页签框架页面
 * @Revision
 * @Date 2007-03-01
 * @author adaFang
 * =====================================================
 *  深圳审计项目组
 * =====================================================
 */
%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus" %>
<freeze:html>
<head>
<title>机构详细信息</title>
</head>
<freeze:body>
<freeze:title caption="机构和人员信息"/>
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
    <freeze:tab caption="人员信息" onclick="showPage_0(caption)" alt="人员信息"/>
    <freeze:tab caption="分组信息" onclick="showPage_1(caption)" alt="分组信息"/>
  </freeze:tabs>

<script>
<%
	//刷新树形显示页面
	try{
 		// 获取刷新参数
        DataBus db = (DataBus)request.getAttribute("freeze-databus");
        // 刷新左侧机构树
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
