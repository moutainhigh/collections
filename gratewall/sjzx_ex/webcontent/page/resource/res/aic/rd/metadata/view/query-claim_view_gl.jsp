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
	
	var page = new pageDefine( "/txn80003207.do", "�鿴");
	page.addParameter("record:sys_rd_claimed_view_id","primary-key:sys_rd_claimed_view_id ");
	page.viewRecord();
}

//�� ��
function func_record_goBackNoUpdate()
{
	goBackNoUpdate();
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	/*$(".radioNew").each(function(index){
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
	});*/
	//$(".grid-headrow td:eq(0)").css("width","30");
	$("#customPageRowSeleted option:gt(0)").remove();
}

_browse.execute( __userInitPage );
</script>
<freeze:body>
<freeze:errors/>

<freeze:form action="/txn80003206">

  <br />

  <freeze:grid checkbox="false"  property="record" caption="��ͼ�б�" keylist="sys_rd_claimed_view_id" width="95%" navbar="bottom" fixrow="false" multiselect="false" onclick="func_record_viewRecord();">
    
      <freeze:hidden property="sys_rd_claimed_view_id" caption="������"/>
      <freeze:hidden property="sys_rd_data_source_id" caption="����ԴID"/>
      <freeze:cell property="view_name" caption="��ͼ����" style="width:35%" />
      <freeze:cell property="view_use" caption="��ͼ��;" style="" />
      <freeze:cell property="claim_operator" caption="������" valueset="�û�����" style="width:20%" />
      <freeze:cell property="claim_date" caption="����ʱ��" style="width:20%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
