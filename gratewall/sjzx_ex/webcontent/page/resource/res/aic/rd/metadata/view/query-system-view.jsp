<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>��ѯ��������ͼ�б�</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">
function func_go_view()
{   
	var page = new pageDefine( "/txn80003201.do", "��ͼ����" );
	page.addParameter("record:sys_rd_data_source_id","select-key:sys_rd_data_source_id");
	page.addParameter("record:db_name","select-key:db_name");
	page.addParameter("record:object_type","select-key:object_type");
	page.goPage();
}

function func_export() 
{   
    var page = new pageDefine("/txn8000276.do", "����Excel");
    page.addParameter("select-key:object_type","select-key:object_type");
    page.addParameter("record:jc_sys_name","select-key:jc_sys_name");
	page.downFile();	
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
<freeze:title caption="��������ͼ�б�"/>
<freeze:errors/>

<freeze:form action="/txn80003200">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%" columns="1">
      <freeze:hidden property="object_type" caption="���ݿ��������" datatype="string" style="width:95%"/>
      <freeze:text property="db_name" caption="����Դ����" datatype="string" maxlength="16" style="width:50%"/>
  </freeze:block>

  <freeze:grid property="record" caption="��ͼ��������б�" nowrap="true" keylist="sys_rd_data_source_id" width="95%" navbar="bottom" fixrow="false" multiselect="false">
  <freeze:button name="go_view" caption="��ͼ����" txncode="80003201" enablerule="1" align="right" onclick="func_go_view();"/>
  	  <freeze:hidden property="sys_rd_data_source_id" caption="����ԴID" style="width:30%" />
  	  <freeze:hidden property="object_type" caption="���ݿ��������" style="width:5%" />
      <freeze:cell property="db_name" caption="����Դ����" style="width:30%" />
      <freeze:cell property="cnt" caption="����������" style="width:30%" />
      <!--freeze:link property="sys_svrname" caption="��������" value="����" style="width:30%" onclick="func_export();" /-->
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
