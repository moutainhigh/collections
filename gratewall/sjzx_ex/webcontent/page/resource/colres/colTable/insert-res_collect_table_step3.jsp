<%@page import="cn.gwssi.common.dao.resource.PublicResource"%>
<%@page import="cn.gwssi.common.dao.resource.code.CodeMap"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>

<script src="<%=request.getContextPath() %> /script/common/js/validator.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-data2table/js/jquery.tablet.js"></script>
<link href="<%=request.getContextPath()%>/script/jquery-plugin-Selector/css/jquery.dataSelector.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %> /script/common/js/validator.js"></script>
<freeze:html width="1000" height="700">
<head>
<title>�޸Ĳɼ����ݱ���Ϣ</title>
<style type="text/css">
#dataitem_tb table{
	width: 95%;
}
</style>
</head>
<%
DataBus context = (DataBus) request.getAttribute("freeze-databus");
if(StringUtils.isNotBlank(context.getValue("jsondata"))){
    out.println("<script>var jsondata=eval('('+'"+context.getValue("jsondata")+"'+')');</script>");
}
%>
<script language="javascript">
<%
TxnContext tbTypeContext = new TxnContext();
CodeMap codeMap = PublicResource.getCodeFactory();
Recordset rs = codeMap.lookup(tbTypeContext, "��Դ����_����������");
out.print("var dataitem_types = new Array; ");
for(int ii=0; ii<rs.size(); ii++){
	out.println("dataitem_types.push({\"key\": \""+rs.get(ii).get("codevalue")
		+"\", \"title\": \""+rs.get(ii).get("codename")+"\"});");
}
%>
<%
TxnContext codeTbContext = new TxnContext();
rs = codeMap.lookup(codeTbContext, "��Դ����_��Ӧ�����");
out.print("var code_table_list = new Array;");
for(int ii=0; ii<rs.size(); ii++){
	out.println("code_table_list.push({\"key\": \""+rs.get(ii).get("codevalue")
		+"\", \"title\": \""+rs.get(ii).get("codename")+"\"});");
}
%>
// ���ɱ�

	function func_record_createTable() {
		var collect_table_id = getFormFieldValue('record:collect_table_id');
		//var ids = getFormAllFieldValues("dataItem:dataitem_name_en");
		var ids = new Array;
		$('#dataitem_tb').find("tr:gt(0)").each(function() {
			ids.push($(this).find("td:eq(1)").text());
		})
		var key = new Array;
		$('#dataitem_tb').find("tr:gt(0)").each(function() {
			key.push($(this).find("td:eq(5) select option:selected").val());
		})

		var num = 0;
		if (ids != null) {
			for (i = 0; i < ids.length; i++) {
				if (key[i] == '1') {
					num = num + 1;
				}
			}
		}
		if (num > 1) {
			alert("ֻ����һ��������������!");
			clickFlag = 0;
			return false;
		}

		var page = new pageDefine("/txn20201009.ajax", "���ɲɼ����ݱ�!");
		page.addParameter("primary-key:collect_table_id", "primary-key:collect_table_id");
		page.addParameter("record:table_name_en", "primary-key:table_name_en");
		page.callAjaxService('creatTableCheck');
	}
	function creatTableCheck(errCode, errDesc, xmlResults) {
		is_name_used = 1;
		if (errCode != '000000') {
			alert('�������[' + errCode + ']==>' + errDesc);
			return;
		}
		is_name_used = _getXmlNodeValues(xmlResults, 'record:name_nums');
		if (is_name_used > 0) {

			if (confirm("�ɼ����Ѵ��ڴ����ݱ����������ݱ����������ݣ��Ƿ�������ɸ����ݱ�?")) {
				var page = new pageDefine("/txn20201008.ajax", "���ɲɼ����ݱ�!");
				page.addParameter("record:collect_table_id",
						"record:collect_table_id");
				page.addValue(is_name_used, "record:name_nums");
				page.callAjaxService('creatTable');
			}

			//alert("�ɼ����Ѵ��ڴ����ݱ����������ݱ�����������,���������ɸñ�!");
			//return false;

		} else if (is_name_used == -1) {

			if (confirm("�ɼ����Ѵ��ڴ����ݱ�����,�����ݱ���û�����ݣ��Ƿ�������ɸ����ݱ�?")) {
				var page = new pageDefine("/txn20201008.ajax", "���ɲɼ����ݱ�!");
				page.addParameter("record:collect_table_id",
						"record:collect_table_id");
				page.addValue(is_name_used, "record:name_nums");
				page.callAjaxService('creatTable');
			}

			//alert("�ɼ����Ѵ��ڴ����ݱ�,���������ɸñ�!");
			//return false;
		} else {
			if (confirm("�Ƿ��������ݱ�?")) {
				var page = new pageDefine("/txn20201008.ajax", "���ɲɼ����ݱ�!");
				page.addParameter("record:collect_table_id",
						"record:collect_table_id");
				page.addValue(is_name_used, "record:name_nums");
				page.callAjaxService('creatTable');
			}
		}
	}
	function creatTable(errCode, errDesc, xmlResults) {
		is_name_used = 1;
		if (errCode != '000000') {
			alert('�������[' + errCode + ']==>' + errDesc);
			return;
		} else {
			alert("���ɱ�ɹ�!");
			goBackWithUpdate();
		}
	}
	function func_record_goBack()
	{

		//var page = new pageDefine( "/txn20201001.do", "��ѯ�ɼ����ݱ�");
		//page.updateRecord();
		goBackWithUpdate();
	}
	// ����������ӣ�ҳ�������ɺ���û���ʼ������
	function __userInitPage() {

	}

	_browse.execute('__userInitPage()');
</script>
<freeze:body>
<freeze:title caption="�޸Ĳɼ����ݱ���Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn20201014">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="collect_table_id" caption="�ɼ����ݱ�ID" style="width:95%"/>
  </freeze:frame>
 <div style="width:95%;margin-left:20px" >
  <table style="width:65%"  >
   <tr>
   
    <td width="30%">
   
    	<table   cellpadding="0" cellspacing="0">
    		<tr>
    			<td style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;" width="2" height="25" valign="middle"></td>
    			<td height="25" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
    				<div style="background:url(<%=request.getContextPath()%>/images/xzcjbg/icon_bg.png) left 50% no-repeat; width:20px; display: inline;"></div>
					��һ��,¼��ɼ��������Ϣ</td>
    			<td width="5" height="25" valign="middle" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_r.png') left 50% no-repeat;"></td>
    		</tr>
    	</table>
    </td>
    <td width="30%" >
    	<table   cellpadding="0" cellspacing="0">
    		<tr>
    			<td style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;" width="2" height="25" valign="middle"></td>
    			<td height="25" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
    				<div style="background:url(<%=request.getContextPath()%>/images/xzcjbg/icon_bg.png) left 50% no-repeat; width:20px; display: inline;"></div>
					    �ڶ���,¼��ɼ����ֶ���Ϣ</td>
    			<td width="5" height="25" valign="middle" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_r.png') left 50% no-repeat;"></td>
    		</tr>
    	</table>
    </td>
    <td width="30%">
 		<table   cellpadding="0" cellspacing="0">
    		<tr>
    			<td style="background:url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_l.png') left 50% no-repeat;" width="2" height="25" valign="middle"></td>
    			<td height="25" style="color:white;background:url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_c.png') left 50% repeat-x;">
    				<div style="background:url(<%=request.getContextPath()%>/images/xzcjbg/icon_bg.png) left 50% no-repeat; width:20px; display: inline;"></div>
					������,Ԥ�������ɱ�</td>
    			<td width="5" height="25" valign="middle" style="background:url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_r.png') left 50% no-repeat;"></td>
    		</tr>
    	</table>   
     </td>
     
   </tr> 
  </table>
  </div>
<freeze:block property="record" caption="�ɼ����ݱ���Ϣ" width="95%">
      <freeze:hidden property="collect_table_id" caption="�ɼ����ݱ�ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:cell property="service_targets_id" caption="�����������" show="name"  valueset="��Դ����_�����������"   style="width:95%"/>
      <freeze:cell property="table_name_en" caption="������" datatype="string" style="width:95%"/>
      <freeze:cell property="table_name_cn" caption="����������" datatype="string" style="width:95%"/>
      <freeze:cell property="table_type" caption="������" show="name" valueset="��Դ����_������"  style="width:95%"/>
      <freeze:cell property="table_desc" caption="������"   style="width:98%"/>
 </freeze:block>
 <br>
  <freeze:grid property="dataItem" caption="�ɼ��������б�" keylist="collect_dataitem_id" multiselect="false" checkbox="false" width="95%"  fixrow="false" >
       <freeze:hidden property="collect_dataitem_id" caption="�ɼ�������ID"  />
      <freeze:hidden property="collect_table_id" caption="�ɼ����ݱ�ID"  />
      <freeze:cell property="index" caption="���"  style="width:6%" align="center" />
      <freeze:cell property="dataitem_name_en" caption="����������" style="width:12%" />
      <freeze:cell property="dataitem_name_cn" caption="��������" style="width:12%" />
      <freeze:cell property="dataitem_type" caption="����������"  show="name" valueset="��Դ����_����������" style="width:12%" />
      <freeze:cell property="dataitem_long" caption="�������" style="width:10%" />
      <freeze:cell property="is_key" caption="�Ƿ�����" valueset="��Դ����_�Ƿ�����" style="width:10%" />
      <freeze:hidden property="is_code_table" caption="�Ƿ�����" style="width:10%" />
      <freeze:cell property="code_table" caption="��Ӧ�����" valueset="��Դ����_��Ӧ�����" style="width:12%" />
      <freeze:hidden property="dataitem_long_desc" caption="����������" style="width:20%"  />
      <freeze:hidden property="is_markup" caption="��Ч���" style="width:10%" />
      <freeze:hidden property="creator_id" caption="������ID" style="width:12%" />
      <freeze:hidden property="created_time" caption="����ʱ��" style="width:12%" />
      <freeze:hidden property="last_modify_id" caption="����޸���ID" style="width:12%" />
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��" style="width:12%" />
  </freeze:grid> 
  <table align='center' cellpadding=0 cellspacing=0 width="95%" >
  <tr><td>
  
  
  
  <td align="center" height="50" valign="bottom">
  <div class="btn_gen"  onclick="func_record_createTable();"></div>
&nbsp;&nbsp;&nbsp;&nbsp;
<div class="btn_cancel"  onclick="func_record_goBack();"></div>
  </td></tr>
  
  
  </table>

  
</freeze:form>
</freeze:body>
</freeze:html>
