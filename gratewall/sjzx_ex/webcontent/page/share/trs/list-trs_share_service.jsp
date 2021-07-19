<%@page import="com.gwssi.common.util.DateUtil"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ taglib uri="/WEB-INF/gwssi.tld" prefix="gwssi" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询服务表列表</title>
<script type="text/javascript" src="/script/lib/jquery171.js"></script>
<jsp:include page="/page/share/common/top_datepicker.html"></jsp:include>
<style type="text/css">
li.disabled {cursor: default;}
.pack-up .pack-list hr.hid{display: none;}
.modal_window .pack-list hr.hid{display:block;width: 600px;
.choose_date{width: 196px; height: 1.8em; display:block;font-size: 62.5%; }
}
</style>
</head>

<script type="text/javascript">
	
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{	
	document.getElementById('form_choose').submit();
}


_browse.execute( '__userInitPage()' );
</script>

<freeze:body>
<freeze:title caption="查询服务表列表"/>
<freeze:errors/>
 <gwssi:panel action="txn40300001" target="wstest" parts="t2,t3" styleClass="wrapper">
  <gwssi:cell id="t2" name="服务对象" key="service_targets_type,service_targets_id" isGroup="true" data="svrTarget" pop="true"  maxsize="10" />
  <gwssi:cell id="t3" name="创建日期" key="created_time" data="svrInterface" date="true"/>
 </gwssi:panel>
<table style="border-collapse: collapse;" cellpadding=0 cellspacing=0 width="100%">
 <tr>
  <td align="center"><iframe id='wstest'  name='wstest' scrolling='yes' frameborder=0 width="95%" style="background-color:#F4FAFB;"></iframe>
  </td>
 </tr>
</table>
<br/>
</freeze:body>
</freeze:html>
