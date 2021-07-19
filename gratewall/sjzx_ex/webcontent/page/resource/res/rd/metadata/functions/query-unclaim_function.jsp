<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="850" height="350">
<head>
<title>δ���캯��</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">
function func_record_addRecord()
{
	var page = new pageDefine( "/txn80004102.do", "���캯��", "modal", 800, 500 );
	page.addParameter("record:sys_rd_unclaim_table_id","select-key:sys_rd_unclaim_table_id ");
	//var page = new pageDefine( "../../../ysjgl/xtstgl/insert-unclaim_view.jsp", "���캯��", "modal", 800, 500 );
	//page.callback = rlCallback;
	//page.updateRecord(null, _gridRecord);
	page.updateRecord();
}
function rlCallback(successNumber, responses, requests, nodeName){
	if(successNumber>0){
		_gridRecord.deleteSelectedRow();
	}
}

function func_record_viewRecord_Button()
{
	var page = new pageDefine( "/txn80004104.do", "�鿴δ���캯����Ϣ","modal");
	page.addParameter("record:sys_rd_unclaim_table_id","select-key:sys_rd_unclaim_table_id");
	page.addParameter("record:unclaim_table_code","select-key:unclaim_tab_code");
	page.goPage();
		
}

function func_record_viewRecord()
{
	if (event.srcElement.tagName.toUpperCase() == 'INPUT'){
		return;
	}
	if (event.srcElement.tagName.toUpperCase() == 'LABEL'){
	return;
	}
	if (event.srcElement.tagName.toUpperCase() == 'A'){
		return;
	}
	var page = new pageDefine( "/txn80004104.do", "�鿴δ���캯����Ϣ","modal");
	page.addParameter("record:sys_rd_unclaim_table_id","select-key:sys_rd_unclaim_table_id");
	page.addParameter("record:unclaim_table_code","select-key:unclaim_tab_code");
	page.goPage();
		
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	$(".radioNew").each(function(index){
		$($(this).prev()[0]).css("display","");
		$($(this).prev()[0]).css("margin-left","-1000");
		$(this).click(function(){
			$(".radioNew").css("background-position-y","bottom");
			$(this).prev()[0].click();
			if($(this).prev()[0].checked){
				$(this).css("background-position-y","top");
			}
		});
		if($(this).prev()[0].checked){
			$(this).css("background-position-y","top");
		}
	});
}


_browse.execute( __userInitPage );
</script>
<freeze:body>
<freeze:title caption="�����б�"/>
<freeze:errors/>

<freeze:form action="/txn80004101">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
 	  <freeze:select property="sys_rd_data_source_id" caption="����Դ����" valueset="ȡ����Դ" style="width:90%"/>
      <freeze:text property="object_schema" caption="�û�" datatype="string" maxlength="36" style="width:90%"/>
      <freeze:text property="unclaim_table_code" caption="��������" datatype="string" maxlength="100" style="width:36.5%" colspan="2" />
  </freeze:block>
  <br />

  <freeze:grid property="record" caption="�����б�" keylist="sys_rd_unclaim_table_id" width="95%" navbar="bottom" fixrow="false" onclick="func_record_viewRecord();" multiselect="false">
      <freeze:button name="record_addRecord" caption="����" txncode="80004103" enablerule="1" hotkey="ADD" icon="/script/button-icon/icon_add.gif" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_viewRecord" caption="�鿴" txncode="80004104" enablerule="1" hotkey="VIEW" icon="/script/button-icon/icon_view.gif" align="right" onclick="func_record_viewRecord();" visible="false"/>
      <freeze:cell property="sys_rd_unclaim_table_id" caption="����" style="width:10%" visible="false"/>
      <freeze:cell property="db_name" caption="����Դ����" style="width:10%"/>
      <freeze:cell property="object_schema" caption="�û�" style="width:10%"/>
      <freeze:cell property="unclaim_table_code" caption="��������" style="width:15%" />
      <freeze:cell property="remark" caption="������;" style="width:15%" visible="false"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
