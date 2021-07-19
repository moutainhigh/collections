<%
/*
 * @Header 返回父页面
 * @Revision
 * @Date 2007-03-01
 * @author adaFang
 * =====================================================
 *  深圳审计项目组
 * =====================================================
 */
%>
<%@ page contentType="text/html; charset=GBK" %>
<html>
<script language="javascript">
function goParent(){
  window.parent.parent.parent.location.reload();
}
</script>
<body onload="goParent()">
</body>
</html>