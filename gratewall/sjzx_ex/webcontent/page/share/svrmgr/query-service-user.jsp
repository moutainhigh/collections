<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>查询服务用户列表</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>

</head>

<script language="javascript">
function __userInitPage(){
  var no=document.getElementsByName('span_record:user_no');
  for(var i=0;i<no.length;i++){
     no[i].innerHTML=i+1;
  }


}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="服务用户列表"/>
<freeze:errors/>
<freeze:form action="/txn50202018">
  <freeze:grid property="record" caption="服务用户列表" keylist="sys_svr_user_id" 
    width="95%" checkbox="false" navbar="bottom" fixrow="false" rowselect="false">
      <freeze:cell property="user_no" caption="序号" style="width:5%"  align="center"/>
      <freeze:cell property="user_name" caption="用户名称" style="width:18%"  />
      <freeze:cell property="user_type" caption="用户类型" style="width:10%"  valueset="user_type" />
      <freeze:cell property="login_name" caption="登录名" style="width:10%"  />
  </freeze:grid>
  <div align="center">
  <table cellspacing="0" cellpadding="0" class="button_table">
	<tr>
		<td class="btn_left"></td>
		<td>
  <input type='button' id='goBack2' class="menu" value=" 返 回 " onclick="goBack();" />
	</td>
		<td class="btn_right"></td>
	</tr>
</table>
  </div>
</freeze:form>
</freeze:body>
</freeze:html>
