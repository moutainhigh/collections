<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="850" height="350">
<head>
<title>δ������ͼ</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">
function func_record_addRecord()
{
	var page = new pageDefine( "/txn80003102.do", "������ͼ", "modal", 800, 500 );
	page.addParameter("record:sys_rd_unclaim_table_id","select-key:sys_rd_unclaim_table_id ");
	//var page = new pageDefine( "../../../ysjgl/xtstgl/insert-unclaim_view.jsp", "������ͼ", "modal", 800, 500 );
	//page.addParameter("record:unclaim_table_code","record:unclaim_table_code");
	//page.addParameter("record:remark","record:remark");
	//page.addParameter("record:data_source_id","record:data_source_id");	
	//page.addParameter("record:jc_unclaim_table_id","record:jc_unclaim_table_id");
	//page.addParameter("record:object_schema","record:object_schema");
	//page.addParameter("record:object_script","record:view_script");
	//page.callback = rlCallback;
	//page.updateRecord(null, _gridRecord);
	page.updateRecord();
}
function rlCallback(successNumber, responses, requests, nodeName){
	if(successNumber>0){
		_gridRecord.deleteSelectedRow();
	}
}

function func_record_viewRecord()
{	
	if (event.srcElement.tagName.toUpperCase() == 'LABEL'){
	return;
	}
	if (event.srcElement.tagName.toUpperCase() == 'INPUT'){
		return;
	}
	if (event.srcElement.tagName.toUpperCase() == 'A'){
		return;
	}
	
	var page = new pageDefine( "/txn80003104.do", "�鿴δ������ͼ��Ϣ","modal");
	page.addParameter("record:sys_rd_unclaim_table_id","select-key:sys_rd_unclaim_table_id");
	page.addParameter("record:unclaim_table_code","select-key:unclaim_tab_code");
	page.goPage();
		
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	//var sys_name = getFormFieldValue("select-key:sys_name");
	//var str = sys_name+"<span style=\"font-size:10pt;color:red\" align=center>[�����죺50����δ���죺18��]</span>";
	//_gridRecord.setCaption(sys_name);
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
<freeze:title caption="��ͼ�б�"/>
<freeze:errors/>

<freeze:form action="/txn80003101">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
 	  <freeze:select property="sys_rd_data_source_id" caption="����Դ����" valueset="ȡ����Դ"  style="width:90%"/>
      <freeze:text property="object_schema" caption="�û�" datatype="string" maxlength="36" style="width:90%"/>
      <freeze:text property="unclaim_table_code" caption="��ͼ����" datatype="string" maxlength="100" style="width:36.5%" colspan="2" />
  </freeze:block>
  <br />

  <freeze:grid property="record" caption="��ͼ�б�" keylist="sys_rd_unclaim_table_id" width="95%" navbar="bottom" fixrow="false" onclick="func_record_viewRecord();" multiselect="false">
      <freeze:button name="record_addRecord" caption="����" txncode="80003103" enablerule="1" hotkey="ADD" icon="/script/button-icon/icon_add.gif" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_viewRecord" caption="�鿴" txncode="80003104" enablerule="1" hotkey="VIEW" icon="/script/button-icon/icon_view.gif" align="right" onclick="func_record_viewRecord();" visible="false"/>
      <freeze:cell property="sys_rd_unclaim_table_id" caption="����" style="width:20%" visible="false"/>
      <freeze:cell property="db_name" caption="����Դ����" style="width:20%"/>
      <freeze:cell property="object_schema" caption="�û�" style="width:20%"/>
      <freeze:cell property="unclaim_table_code" caption="��ͼ����" style="width:30%" />
      <freeze:cell property="remark" caption="��ͼ��;" style="width:25%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
