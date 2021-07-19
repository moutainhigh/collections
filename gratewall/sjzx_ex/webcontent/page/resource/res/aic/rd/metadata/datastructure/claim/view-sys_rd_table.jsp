<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>

<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="400">
<head>
<title>查看物理表信息</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">


// 打 印
function func_record_printDocument()
{
	print(document);
}

// 返 回
function func_record_goBackNoUpdate()
{
	goBackNoUpdate();	
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	$("input[type='checkbox']").after("<label class='checkboxNew'></label>");
	$("input[type='checkbox']").css("display","none");
	var is_query = getFormFieldValue("record:is_query");
	var is_trans = getFormFieldValue("record:is_trans");
	var is_download = getFormFieldValue("record:is_download");
	var authority="";
	
	if(is_query=='1'){
		authority='0';
		$(".checkboxNew")[0].style.backgroundPositionY='top';
	}
	if(is_trans=='1'){
		if(authority.length!=0){
			authority=authority+',1';
		}else{
			authority='1';
		}		
		$(".checkboxNew")[1].style.backgroundPositionY='top';
	}	
	if(is_download=='1'){
		if(authority.length!=0){
			authority=authority+',2';
		}else{
			authority='2';
		}	
		$(".checkboxNew")[2].style.backgroundPositionY='top';
	}

	if(authority.length!=0){
		setFormFieldValue("record:authority",authority);
	}else{
		setFormFieldValue("record:authority"," ");
	}
	
	
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查看物理表信息"/>
<freeze:errors/>

<freeze:form action="/txn80002207">
<freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_table_id" caption="主键" style="width:95%"/>
  </freeze:frame>

  <freeze:block  property="record" caption="查看已认领信息" width="95%">
     
      <freeze:cell property="table_code" caption="物理表" datatype="string"  style="width:95%" />
      <freeze:cell property="sys_name" caption="主题名称" valueset="主题名称" valueset="业务主题" datatype="string"  style="width:95%" />
      <freeze:cell property="first_record_count" caption="数据量" datatype="string"   style="width:95%" />
      <freeze:cell property="table_primary_key" caption="主键" datatype="string"  style="width:95%" />
      <freeze:cell property="table_index" caption="索引" datatype="string"  style="width:95%" visible="false"/>
      <freeze:cell property="table_type" caption="表类型" valueset="表类型" datatype="string"  style="width:95%" />
      <freeze:cell property="table_name" caption="物理表中文名" datatype="string"  style="width:95%" />
      <freeze:cell property="parent_table" caption="主表" datatype="string"  style="width:95%" />
      <freeze:cell property="parent_pk" caption="主表主键" datatype="string"  style="width:95%" />
      <freeze:cell property="table_fk" caption="外键" datatype="string"  style="width:95%" /> 
      <freeze:checkbox property="authority" caption="权限"  valueset="权限"   style="width:95%" readonly="true"/> 
      <freeze:cell property="table_use" caption="用途" colspan="2" datatype="string"  style="width:95%" />
  	  <freeze:cell property="memo" caption="备注"   style="width:95%" colspan="2"/>
  	  
  	  <freeze:hidden property="is_query" caption="是否可查询" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="is_trans" caption="是否可共享" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="is_download" caption="是否可下载" datatype="string" maxlength="1" style="width:95%"/>
  	  <freeze:hidden property="sys_rd_unclaim_table_id" caption="未认领表id" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sys_rd_system_id" caption="业务主体ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sys_rd_table_id" caption="数据表ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sys_no" caption="业务主体编号" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="sys_rd_data_source_id" caption="数据源ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_record_count" caption="最后一次同步数据量" datatype="string" maxlength="" style="width:95%"/>
      <freeze:hidden property="gen_code_column" caption="总局代码字段" datatype="string" maxlength="30" style="width:95%"/>
      <freeze:hidden property="prov_code_column" caption="省局代码字段" datatype="string" maxlength="30" style="width:95%"/>
      <freeze:hidden property="city_code_column" caption="市局代码字段" datatype="string" maxlength="30" style="width:95%"/>
      <freeze:hidden property="content" caption="代码字段内容" datatype="string" maxlength="30" style="width:95%"/>
      <freeze:hidden property="claim_operator" caption="认领人" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="claim_date" caption="认领日期" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:hidden property="changed_status" caption="变化状态" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="object_schema" caption="表模式" datatype="string" maxlength="36" style="width:95%"/>
      <freeze:hidden property="table_no" caption="数据表编号" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="table_sql" caption="数据表sql" maxlength="3000" style="width:98%"/>
      <freeze:hidden property="table_sort" caption="排序字段" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="table_dist" caption="区县字段" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="table_time" caption="时间字段" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="sort" caption="排序" datatype="string" maxlength="" style="width:95%"/>
      <freeze:hidden property="timestamp" caption="时间戳" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>
  
  <p align="center" class="print-menu">
  <!--  <input type="button" name="record_printDocument" value="打 印" class="menu" onclick="func_record_printDocument();" style='width:60px' >-->
  <table cellspacing="0" cellpadding="0" class="button_table">
	<tr>
		<td class="btn_left"></td>
		<td>
  			<input type="button" name="record_goBackNoUpdate" value="关闭" class="menu" onclick="func_record_goBackNoUpdate();" style='width:60px' >
  		</td>
		<td class="btn_right"></td>
	</tr>
  </table>
  </p>

</freeze:form>
</freeze:body>
</freeze:html>
