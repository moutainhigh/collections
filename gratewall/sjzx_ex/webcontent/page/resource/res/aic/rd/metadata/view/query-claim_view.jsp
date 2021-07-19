<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="850" height="350">
<head>
<title>��������ͼ�б�</title>
<style>
.even2,.even1{padding-left:0px;}
</style>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn80003204.do", "�޸���ͼ", "modal", 700, 600 );
	page.addParameter("record:sys_rd_claimed_view_id","primary-key:sys_rd_claimed_view_id");
	page.updateRecord();
}


// �鿴����
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
	
	var page = new pageDefine( "/txn80003205.do", "�鿴", "modal" );
	page.addParameter("record:sys_rd_claimed_view_id","primary-key:sys_rd_claimed_view_id ");
	page.viewRecord();
}

// ɾ����
function func_record_deleteRecord()
{
    var page = new pageDefine( "/txn80003203.do", "ɾ����ͼ" );
    page.addParameter("record:sys_rd_claimed_view_id","primary-key:sys_rd_claimed_view_id ");
    page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

//�� ��
function func_record_goBackNoUpdate()
{
	goBackNoUpdate();
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
	$(".grid-headrow td:eq(0)").css("width","30");
}

_browse.execute( __userInitPage );
</script>
<freeze:body>
<freeze:title caption="��ͼ�б�"/>
<freeze:errors/>

<freeze:form action="/txn80003201">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
  	  <freeze:hidden property="sys_rd_claimed_view_id" caption="������" datatype="string" style="width:95%"/>	
  	   <freeze:select property="sys_rd_data_source_id" caption="����Դ����"  valueset="ȡ����Դ"  style="width:95%"/>
      <freeze:text property="view_name" caption="��ͼ����" datatype="string" maxlength="100" style="width:95%"/>
  </freeze:block>
  <br />

  <freeze:grid property="record" caption="��ͼ�б�" nowrap="true" keylist="sys_rd_claimed_view_id" width="95%" navbar="bottom" fixrow="false" multiselect="false" onclick="func_record_viewRecord();">
      <freeze:button name="record_updateRecord" caption="�޸�" txncode="80003202" enablerule="1" hotkey="UPDATE" icon="/script/button-icon/icon_update.gif" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��" txncode="80003203" enablerule="2" hotkey="DELETE" icon="/script/button-icon/icon_delete.gif" align="right" onclick="func_record_deleteRecord();" visible="false"/>
      <freeze:button name="record_viewRecord" caption="�鿴" txncode="80003205" enablerule="1" hotkey="VIEW" icon="/script/button-icon/icon_view.gif" align="right" onclick="func_record_viewRecord();" visible="false"/>

      <freeze:hidden property="sys_rd_claimed_view_id" caption="������"/>
      <freeze:hidden property="sys_rd_data_source_id" caption="����ԴID"/>
      <freeze:cell property="view_name" caption="��ͼ����" style="width:25%" />
      <freeze:cell property="view_use" caption="��ͼ��;" style="width:35%" />
      <freeze:cell property="claim_operator" caption="������" valueset="�û�����" style="width:20%" />
      <freeze:cell property="claim_date" caption="����ʱ��" style="width:20%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
