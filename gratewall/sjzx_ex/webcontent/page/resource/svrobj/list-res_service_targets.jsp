<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ taglib uri="/WEB-INF/gwssi.tld" prefix="gwssi" %>
<freeze:html width="650" height="350">
<head>
<title>查询对象列表</title>
<script type="text/javascript" src="/script/lib/jquery171.js"></script>
<jsp:include page="../../share/common/top_datepicker.html"></jsp:include>
</head>

<script type="text/javascript" >

function changeDate(){
	var val = arguments[0];
	if(val && val!='点击选择日期' && val != 'date_all'){
		document.getElementById('form_choose').submit();
	}
	else{
		if(val && val=='date_all'){
			document.getElementById('form_choose').submit();
		}
	}
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	document.getElementById('form_choose').submit();
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询对象列表"/>
<freeze:errors/>
 <gwssi:panel action="txn201001" target="wstest" parts="t1,t2" styleClass="wrapper">
   <gwssi:cell id="t1" name="服务对象" key="service_targets_type,service_targets_id" isGroup="true" data="svrTarget" pop="true"  maxsize="10" />
   <gwssi:cell id="t2" name="创建时间 " key="created_time_start" data="svrInterface" date="true"/>
 </gwssi:panel>

<table style="border-collapse: collapse;" cellpadding=0 cellspacing=0 width="100%">
 <tr>
  <td align="center" valign="top"><iframe id='wstest'  name='wstest' scrolling='yes' frameborder=0 width="95%" style="background-color:#F4FAFB;"></iframe>
  </td>
 </tr>
</table>


</freeze:body>
</freeze:html>
