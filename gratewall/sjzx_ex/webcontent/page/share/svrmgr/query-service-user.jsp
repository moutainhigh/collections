<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�����û��б�</title>
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
<freeze:title caption="�����û��б�"/>
<freeze:errors/>
<freeze:form action="/txn50202018">
  <freeze:grid property="record" caption="�����û��б�" keylist="sys_svr_user_id" 
    width="95%" checkbox="false" navbar="bottom" fixrow="false" rowselect="false">
      <freeze:cell property="user_no" caption="���" style="width:5%"  align="center"/>
      <freeze:cell property="user_name" caption="�û�����" style="width:18%"  />
      <freeze:cell property="user_type" caption="�û�����" style="width:10%"  valueset="user_type" />
      <freeze:cell property="login_name" caption="��¼��" style="width:10%"  />
  </freeze:grid>
  <div align="center">
  <table cellspacing="0" cellpadding="0" class="button_table">
	<tr>
		<td class="btn_left"></td>
		<td>
  <input type='button' id='goBack2' class="menu" value=" �� �� " onclick="goBack();" />
	</td>
		<td class="btn_right"></td>
	</tr>
</table>
  </div>
</freeze:form>
</freeze:body>
</freeze:html>
