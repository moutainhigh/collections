<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
	<title>查询共享数据项信息列表</title>
</head>

<style >
.activerow{cursor: default;}
</style>

<script language="javascript">

// 增加共享数据项信息
function func_record_addRecord()
{
	var page = new pageDefine( "insert-res_share_dataitem.jsp", "增加共享数据项信息", "modal" );
	page.addRecord();
}

// 修改共享数据项信息
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn20301024.do", "修改共享数据项信息", "modal" );
	page.addParameter( "record:share_dataitem_id", "primary-key:share_dataitem_id" );
	page.updateRecord();
}

// 删除共享数据项信息
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn20301025.do", "删除共享数据项信息" );
	page.addParameter( "record:share_dataitem_id", "primary-key:share_dataitem_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var ids =document.getElementsByName("span_record:code_table");
	var value=getFormAllFieldValues("record:code_table");
	
	for(var i=0; i<ids.length; i++){
		var val=value[i];
		if(val==null||val==""){
		}else{
		  ids[i].innerHTML='<a href="javascript:func_record_querycode(\''+value[i]+'\');" title="" >'+val+'</a>';
		}
    }
	var keys = document.getElementsByName('span_record:is_key');
	for(var i=0; i<keys.length; i++){
		if(keys[i].innerText == '否'){
			keys[i].innerText = '';
		}
	}
}
function func_record_querycode(val){
   
    var url="txn20301026.do?select-key:code_table="+val;
    var page = new pageDefine( url, "查看代码集信息", "代码集查询" );
	page.addRecord();
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<br/>
	<freeze:errors />

	<freeze:form action="/txn20301021">
		<freeze:frame property="select-key" width="95%">
		</freeze:frame>

		<freeze:block property="fatherRecord" caption="" width="95%">
			<freeze:cell property="service_targets_name" caption="业务系统"
				style="width:95%" />
			<freeze:cell property="topics_name" caption="主题" style="width:95%" />
			<freeze:cell property="table_name_cn" caption="表中文名称"
				style="width:95%" />
			<freeze:cell property="table_name_en" caption="表名称" style="width:95%" />

		</freeze:block>
		<freeze:grid checkbox="false" property="record" caption="查询共享数据项信息列表"
			keylist="share_dataitem_id" width="95%" navbar="bottom" fixrow="false" rowselect="false" >
			<freeze:hidden property="share_dataitem_id" caption="共享数据项ID"
				style="width:10%" visible="false" />
			<freeze:hidden property="share_table_id" caption="共享表ID"
				style="width:12%" />
				
			<freeze:cell property="@rowid" caption="序号" align="center" style="width:5%"/>
			<freeze:cell property="dataitem_name_en" caption="数据项名称"
				style="width:20%" align="center"/>
			<freeze:cell property="dataitem_name_cn" caption="数据项中文名称"
				style="width:20%" align="center"/>
			<freeze:hidden property="dataitem_type" caption="数据项类型"
				style="width:12%" align="center"/>
			<freeze:cell property="dataitem_long" caption="数据项长度"
				style="width:12%" align="center"/>
			<freeze:hidden property="code_table_name" caption="系统代码名"
				style="width:12%" align="center"/>
			<freeze:cell property="is_key" caption="是否主键" valueset="资源管理_是否主键" align="center" style="width:10%" />
			<freeze:cell property="code_table" caption="代码集" align="center" style="width:10%" />
			<freeze:hidden property="dataitem_desc" caption="描述"
				style="width:20%" visible="false" />
			<freeze:hidden property="show_order" caption="显示顺序" style="width:10%" />
			<freeze:hidden property="is_markup" caption="有效标记" style="width:10%" />
		</freeze:grid>

	</freeze:form>
</freeze:body>
<!--     
	  <freeze:button name="record_addRecord" caption="增加共享数据项信息" txncode="20301023" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改共享数据项信息" txncode="20301024" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除共享数据项信息" txncode="20301025" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      -->
</freeze:html>
