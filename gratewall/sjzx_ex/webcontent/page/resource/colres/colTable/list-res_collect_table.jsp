<%@page import="com.gwssi.common.util.DateUtil"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ taglib uri="/WEB-INF/gwssi.tld" prefix="gwssi" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�ӿڱ��б�</title>
<script type="text/javascript" src="/script/lib/jquery171.js"></script>
<jsp:include page="../../../share/common/top_datepicker.html"></jsp:include>
<style type="text/css">
li.disabled {cursor: default;}
.pack-up .pack-list hr.hid{display: none;}
.modal_window .pack-list hr.hid{display:block;width: 600px;
.choose_date{width: 196px; height: 1.8em; display:block;font-size: 62.5%; }
}
</style>
</head>

<script type="text/javascript">


	
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{	
	document.getElementById('form_choose').submit();
}


_browse.execute( '__userInitPage()' );
</script>

<freeze:body>
<freeze:title caption="��ѯ�ɼ����ݱ��б�"/>
<freeze:errors/>
<gwssi:panel action="txn20209002" target="wstest" parts="t1,t2,t3,t4,t5" styleClass="wrapper">
  <gwssi:cell id="t1" name="�ɼ���Դ" key="cj_ly" data="colSource" />
  <gwssi:cell id="t2" name="�������" key="service_targets_type,service_targets_id" isGroup="true" data="svrTarget" pop="true"  maxsize="10" />
  <gwssi:cell id="t3" name="�������" key="table_type" data="tabType" />
  <gwssi:cell id="t4" name="����״̬" key="if_creat" data="crtState" /> 
  <gwssi:cell id="t5" name="����ʱ��" key="created_time" data="created_time" date="true"/>
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
