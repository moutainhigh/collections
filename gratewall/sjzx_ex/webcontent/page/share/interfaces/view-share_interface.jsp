<%@ page contentType="text/html; charset=GBK" %>
<%@page import="cn.gwssi.common.context.DataBus"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="800" height="450">
<head>
<title>���ӹ����������ӿ�������Ϣ</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery-ui.custom.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-Selector/js/jquery.dataSelector.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-tab/jquery.tabs.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-data2table/js/jquery.tablet.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/share/share_interface.js"></script>
<link href="<%=request.getContextPath()%>/script/jquery-plugin-Selector/css/jquery.dataSelector.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/script/jquery-plugin-tab/css/jquery.tabs.css" rel="stylesheet" type="text/css" />
<%
DataBus db = (DataBus)request.getAttribute("freeze-databus");

%>
<style type="text/css">
.sec_left{
}
</style>
</head>

<script language="javascript">

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '���湲�����-�����ӿڱ�' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '���湲�����-�����ӿڱ�' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '���湲�����-�����ӿڱ�' );	// /txn401001.do
}

// �� ��
function func_record_goBack()
{
	window.close();
	//goBack();	// /txn401001.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
   var condition=eval('(<%=db.getValue("tableCondition")%>)');
   var cond_html="";
   for(var i=0;i<condition.length;i++){
     var cond=condition[i];
     cond_html+="["+cond.leftTable.name_cn+"] �� "+cond.leftColumn.name_cn+" <span style='color:red'> "+cond.middleParen+" </span>";
     cond_html+="["+cond.rightTable.name_cn+"] �� "+cond.rightColumn.name_cn+"<br/>";
   }
   document.getElementById('span_record:table_condition').innerHTML=cond_html;
   var param=eval('(<%=db.getValue("tableParam")%>)');
   var param_html="";
   for(var i=0;i<param.length;i++){
     var parm=param[i];
     param_html+=parm.cond+" ["+parm.leftTable.name_cn+"] �� "+parm.leftColumn.name_cn+" <span style='color:red'> "+parm.middleParen.value+" </span>";
     param_html+=parm.paramValue+"<br/>";
   }
   document.getElementById('span_record:table_param').innerHTML=param_html;
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�鿴�����ӿ�������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn401002">
<freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="interface_id" caption="�ӿ�ID" style="width:95%"/>
  </freeze:frame>
<freeze:block property="record" caption="�鿴�ӿڻ�����Ϣ" width="95%">
  <freeze:hidden property="interface_id" caption="�ӿ�ID" datatype="string" maxlength="32" style="width:95%"/>
  <freeze:cell property="interface_name" caption="�ӿ�����" datatype="string"  style="width:75%" />
  <freeze:cell property="created_time" caption="����ʱ��" datatype="string"  style="width:75%" />
  <freeze:cell property="interface_description" caption="�ӿ�˵��"  style="width:75%" colspan="2"/>
  <freeze:cell property="crename" caption="������" datatype="string"  style="width:95%"/>
  <freeze:cell property="cretime" caption="����ʱ��" datatype="string"  style="width:95%"/>
  <freeze:cell property="modname" caption="����޸���" datatype="string"  style="width:95%"/>
  <freeze:cell property="modtime" caption="����޸�ʱ��" datatype="string"  style="width:95%"/>
  
  <freeze:hidden property="table_code" caption="�ӿڱ����ƴ�" datatype="string"/>
  <freeze:hidden property="condition" caption="��������" datatype="string"/>
  <freeze:hidden property="param" caption="��ѯ����" datatype="string"/>
  <freeze:hidden property="interface_state" caption="�ӿ�״̬" datatype="string"/>
  <freeze:hidden property="is_markup" caption="�Ƿ���Ч" datatype="string"/>
  <freeze:hidden property="creator_id" caption="������ID" datatype="string"/>
</freeze:block>
<br/>
<freeze:block property="record" caption="�鿴�ӿ�������Ϣ" width="95%">
  <freeze:hidden property="interface_id" caption="�ӿ�ID" datatype="string" maxlength="32" style="width:95%"/>
  <freeze:cell property="table_name_cn" caption="�ӿ���ѡ���ݱ�" datatype="string"  style="width:75%" colspan="2"/>
  <freeze:cell property="table_condition" caption="�ӿڹ�������" datatype="string"  style="width:75%" colspan="2"/>
  <freeze:cell property="table_param" caption="�ӿڲ�ѯ����" datatype="string"  style="width:75%" colspan="2"/>
  <freeze:cell property="sql" caption="�ӿ�SQL" datatype="string" colspan="2" />
</freeze:block>
<Br/>
<p align="center" class="print-menu">
    	<table cellspacing='0' cellpadding='0' class='button_table'>
    	<tr><td class='btn_left'></td>
    	<td><input type="hidden" type="button" name="record_goBackNoUpdate" value="�� ��" class="menu" onclick="func_record_goBack()" />
    	<div class="btn_cancel" onclick="func_record_goBack()"></div>
    	</td><td class='btn_right'></td></tr>
    	</table>
    </p>
</freeze:form>
</freeze:body>
</freeze:html>
