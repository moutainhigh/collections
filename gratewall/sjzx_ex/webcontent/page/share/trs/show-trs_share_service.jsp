<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>

<%@ page import = "java.util.ArrayList"%>
<%@ page import = "java.util.HashMap"%>
<%@ page import = "java.util.List"%>
<%@ page import = "java.util.Map"%>
<%@ page import = "com.gwssi.common.util.JsonDataUtil"%>

<%-- template single/single-table-insert.jsp --%>
<freeze:html width="1000" height="850">
<head>
<%
String trs_db_str = java.util.ResourceBundle.getBundle("trs").getString("searchDB");
String trs_db_str_cn = java.util.ResourceBundle.getBundle("trs").getString("searchDBCN");
String trs_db_ctrl = java.util.ResourceBundle.getBundle("trs").getString("searchDBctrl");
String[] trs_dbs = trs_db_str.split(",");
String[] trs_dbs_cn = trs_db_str_cn.split(",");

List dataList = new ArrayList();
Map columnMap = new HashMap();
for (int i = 0; i < trs_dbs.length; i++) {
	Map dataMap = new HashMap();
	dataMap.put("key", trs_dbs[i]);
	dataMap.put("title", trs_dbs_cn[i]);
	dataList.add(dataMap);
	
	String colums_key = java.util.ResourceBundle.getBundle("trs").getString(trs_dbs[i]+"_key");
	columnMap.put(trs_dbs[i]+"_key", colums_key);
	
	if(java.util.ResourceBundle.getBundle("trs").containsKey(trs_dbs[i]+"_value")){
		String colums_value = java.util.ResourceBundle.getBundle("trs").getString(trs_dbs[i]+"_value");
		columnMap.put(trs_dbs[i]+"_value", colums_value);
	}else{
		columnMap.put(trs_dbs[i]+"_value", colums_key);
	}
	
}
String dbList = JsonDataUtil.toJSONString(dataList);
String colList = JsonDataUtil.toJSONString(columnMap);
%>

<title>修改trs接口信息</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-boxy/js/jquery.boxy.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-boxy/js/jquery-webox.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/script/jquery-plugin-boxy/css/boxy.css" type="text/css" />
<style type="text/css">
label.title {
	padding: 3px 5px;
	background: #e4ebf4;
	color: #333;
	font-size: 13px;
	border: 1px solid #ccf;
}
.sec_left{
}
.allTheme {width:99%;margin: 2px 0 0; border: 1px solid #ddd; background: #f8f8f8;padding:5px 0px;}
.allTheme .line{margin: 0 5px; color: #666;}
.allTheme a {padding: 2px 4px;}
.allTheme a:hover{color:white !important;background:#39f; text-decoration:none;}
.allTheme .nSelected {color:white !important;background:#39f; }
.allTheme .title {
	display: none;
	padding: 3px 5px;
	background: #e4ebf4;
	color: #333;
	font-size: 13px;
	border: 1px solid #ccf;
}
.allTheme .content {
	line-height: 1.8;
}
.allTheme .content a{
	white-space: nowrap;
}
.soo{margin: 1px 5px; display:inline-block;}
</style>
</head>

<script language="javascript">
var pop = false;
// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存trs服务接口' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存trs服务接口' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存trs服务接口' );	// /txn40300001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn40300001.do
}

function init_table_list(){
	if(typeof tblData == 'undefined'){
		return;
	}
	var trs_db_ctrl = '<%=trs_db_ctrl%>'; 
	trs_db_ctrl = trs_db_ctrl.replace(/,/gi, '');
	var trs_db_length = trs_db_ctrl.lastIndexOf('1');
	for(var ii=0; ii<=trs_db_length; ii++){
		$('.first #level_table #span_table').append('<span class="soo"><a id=\'a_'+tblData[ii].key
				+'\' href="javascript:addTrsDbColumn(\''+tblData[ii].key
				+'\', \''+tblData[ii].title+'\');">'+tblData[ii].title+
				'</a><input type="checkbox" value="0" /></span>');
		if(ii!=tblData.length-1){
			$('.first #level_table #span_table').append('<span class="line soo">|</span>');
		}
	}
	for(var ii=trs_db_length+1; ii<tblData.length; ii++){
		$('.second #pro_table #cspan_table').append('<span class="soo"><a id=\'a_'+tblData[ii].key
				+'\' href="javascript:addTrsDbColumn(\''+tblData[ii].key
				+'\', \''+tblData[ii].title+'\');">'+tblData[ii].title+
				'</a><input type="radio" name="group" value="1" /></span>');
		if(ii!=tblData.length-1){
			$('.second #pro_table #cspan_table').append('<span class="line soo">|</span>');
		}
	}
}

function selectAllTable(){
	if($('.first #span_table a').length == $('.first #span_table a.nSelected').length){
		$('.first #span_table a').each(function(){
			if($(this).hasClass('nSelected'))
				$(this)[0].click();
		});
		$('.first #selectAll').text("全选");
	}else{
		$('.first #span_table a').each(function(){
			if(!$(this).hasClass('nSelected'))
				$(this)[0].click();
		});
		$('.first #selectAll').text("取消全选");
	}
}
var tableNames = '';
var tableColumns = '';
var searchColumns = '';

function bindChangeDbType(){
	$('input[name="db_type"]').bind('click', function(){
		if($(this).val()=='0'){
			$(".first").show();
			$('.second').hide();
			$('#info').html("注：多库查询为默认查询方式，无需配置检索字段.").show();
		}
		if($(this).val()=='1'){
			$(".first").hide();
			$('.second').show();
			$('#info').html('').hide();
		}
		$('#cond_checkTable tr').remove();
	})
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	//初始化数据库列表
	init_table_list();
	//绑定数据库切换点击事件
	bindChangeDbType();
	
	tableNames = getFormFieldValue("record:trs_data_base");
	tableColumns = getFormFieldValue("record:trs_column");
	searchColumns = getFormFieldValue("record:trs_search_column");
	
    var tableArr = tableNames.split(',');
    var tableData = '';
    for(var i=0;i<tableArr.length;i++){
    	var tableCN = "";
        for(var ii=0;ii<tblData.length;ii++){
        	if(tblData[ii].key==tableArr[i]){
        		tableCN = tblData[ii].title;
        	}
        }
        $('#a_'+tableArr[i])[0].click();
    	tableData += (tableData=='' ? '{"title":"'+tableCN+'","key":"'+tableArr[i]+'"}' :',{"title":"'+tableCN+'","key":"'+tableArr[i]+'"}');
    }
    tableData = '['+tableData+']';
    tableData  = eval('('+tableData+')');
  //初始化多选框
	$("#cond_checkTable input[type=checkbox]").each(function() {
		$(this).attr("checked", false);
	});
		
		$('.first #span_table input[type="checkbox"]').bind('click', function(){
			$('.second #cspan_table a.nSelected').each(function(){
				//$(this)[0].click();
				$(this).removeClass('nSelected');
				$('#div'+$(this).attr('id').replace('a_', 'div')).remove();
			});
			$(this).prev('a')[0].click();
			if($(this).prev('a').hasClass('nSelected')){
				$(this).attr('checked', true);
			}else{
				$(this).attr('checked', false);
			}
			//当选择多个库时，不能选择检索
			if($('.first a.nSelected').length>1){
				$('#cond_checkTable tr').each(function(){
					$(this).find('td:eq(1)').find('.'+$(this).attr('id').replace('div', '')+"_query").hide();
					$('#info').html("注：多库查询为默认查询方式，无需配置检索字段.").show();
				})
			}else{
				$('#info').html("").hide();
			}
		});
		
		$('.second #cspan_table input[type="radio"]').bind('click', function(){
			$('.second #cspan_table a.nSelected').each(function(){
				$(this)[0].click();
			});
			$(this).prev('a')[0].click();
			if($(this).prev('a').hasClass('nSelected')){
				$(this).attr('checked', true);
			}else{
				$(this).attr('checked', false);
			}
		});
		
		if($('.first #span_table a').length == $('.first #span_table a.nSelected').length){
			$('.first #selectAll').text("取消全选");
		}	
	pop = true;	
	var used = $('.nSelected').parent().parent().parent();
	if(used){
		used = used.attr('id');
		if('pro_table' == used){
			$('.first').hide();
			$('.second').show();
			$('table.dd_table tr:first input[value="1"]').attr('checked', true);
		}else{
			$('.second').hide();
			$('.first').show();
			$('table.dd_table tr:first input[value="0"]').attr('checked', true);
		}
	}
	$('#tt1 tr:lt(2)').hide();
	$('tr.second').hide();
}

var tblData;
var columnData;
var checkTblData = new Array;
var datas = '<%=dbList%>';
tblData = eval('('+datas+')');
datas = '<%=colList%>';
columnData  = eval('('+datas+')');
var columnMap = columnData[0];

function addTrsDbColumn(dbName,dbNamecn){
	if($('.second #a_'+dbName).length > 0){
		$('.first a.nSelected').each(function(){
			$(this).removeClass('nSelected');
			$(this).next('input[type="checkbox"]').attr("checked", false);
			$('#'+$(this).attr('id').replace('a_', 'div')).remove();
		});
		$('.second a.nSelected').each(function(){
			$(this).removeClass('nSelected');
			$(this).next('input[type="radio"]').attr("checked", false);
			$('#'+$(this).attr('id').replace('a_', 'div')).remove();
		});
		//$('.second #a_'+dbName)[0].click();
	}else{
		$('.second a.nSelected').each(function(){
			$(this).removeClass('nSelected');
			$(this).next('input[type="radio"]').attr("checked", false);
			$('#'+$(this).attr('id').replace('a_', 'div')).remove();
		});
	}
	if(!$('#a_'+dbName).hasClass('nSelected')){
		$('#a_'+dbName).addClass('nSelected');
		$('#a_'+dbName).next('input[type="checkbox"]').attr("checked", true);
		$('#a_'+dbName).next('input[type="radio"]').attr("checked", true);
		if($('.first #span_table a').length == $('.first #span_table a.nSelected').length){
			$('.first #selectAll').text("取消全选");
		}else{
			$('.first #selectAll').text("全选");
		}
	}else{
		$('#a_'+dbName).removeClass('nSelected');
		$('#div'+dbName).remove();
		$('#a_'+dbName).next('input[type="checkbox"]').attr("checked", false);
		$('#a_'+dbName).next('input[type="radio"]').attr("checked", false);
		if($('.first #span_table a').length == $('.first #span_table a.nSelected').length){
			$('.first #selectAll').text("取消全选");
		}else{
			$('.first #selectAll').text("全选");
		}
		return;
	}
	
	toInitContent(dbName, dbNamecn);
	//}
}

function toInitContent(dbName, dbNamecn){
	var columnArr = columnMap[dbName+"_key"].split(',');
	var columnArrVal = columnMap[dbName+"_value"].split(',');
	var htm = '<tr style=" background: #f9f9f9;" id="div'
		+dbName+'" ><td style="width: 15%; border: 1px solid #ddd;"><span> '
		+dbNamecn+'</span></td>';
	htm += '<td style="padding: 5px 0;border: 1px solid #ddd">';
	for(var i=0; i<columnArr.length;i++)
	{
		//alert(i + ": " + columnArr[i]);
   		htm += '<div style="white-space:nowrap;display:inline;width:180px;margin-right:10px;"><span style="white-space:nowrap; display:inline-block;margin-right:10px;line-height:1.5;color:#069;">'
   			+ columnArrVal[i]+'</span><input type="hidden" value="'+columnArr[i]+'" />&nbsp;';
   			if(tableNames.indexOf(dbName) > -1){
	   	   	if(tableColumns.indexOf(columnArr[i]) > -1){
	   	   		htm += '<span class="'+dbName+'_show">[显示]</span>';
	   	   	}///
	   		if(searchColumns.indexOf(columnArr[i]) > -1){
	   			htm += '<span class="'+dbName+'_query">[检索]</span>';
	   		}else if(searchColumns.indexOf(dbName+":all") > -1){
	   			htm += '<span class="'+dbName+'_query">[检索]</span>';
	   		}
		}else{
			htm += '<span class="'+dbName+'_show">[显示]</span><span class="'+dbName+'_query">[检索]</span>';
		}
   		htm += '</div>';
	}

	if($('#cond_checkTable').html()=='无'){
		$('#cond_checkTable').html(htm);
	}else{
	  $('#cond_checkTable').append(htm);
	}
	if($('.first a.nSelected').length>1){
		$('#cond_checkTable tr').each(function(){
			$(this).find('td:eq(1)').find('.'+$(this).attr('id').replace('div', '')+"_query").hide();
			$('#info').html("注：多库查询为默认查询方式，无需配置检索字段.").show();
		})
	}else{
		$('#info').html("").hide();
	}
}

function toEditContent(dbName, dbNamecn){
	//$('#div'+dbName).remove();
	var oldHtml = $('#div'+dbName).html();
	var columnArr = columnMap[dbName+'_key'].split(',');
	var columnArrVal = columnMap[dbName+'_value'].split(',');
	var htm = '<tr style=" background: #f2f2f2;">';
	htm += '<td colspan="2" style="border: 1px solid #ddd;height:30px;text-align:center;"><span> '
		+dbNamecn+'</span></td></tr>';
	if($('.first a.nSelected').length>1){
		for(var i=0; i<columnArr.length;i++){
			var checked1 = '';
			var checked2 = '';
			if($('#div'+dbName+' td:eq(1) div:eq('+i+') span.'+dbName+"_show").text().indexOf('显示') > -1){
				checked1 = 'checked';
			}
			if($('#div'+dbName+' td:eq(1) div:eq('+i+') span.'+dbName+"_query").text().indexOf('检索') > -1){
				checked2 = 'checked';
			}
	   		htm += '<tr><td class="'+columnArr[i]+'" style="width: 60%; padding: 5px 0;border: 1px solid #ddd">'
	   		+ columnArrVal[i]+'</td><td nowrap style="padding: 5px 0;border: 1px solid #ddd">'
	   		+'<label style="line-height: 1.2;white-space: nowrap; margin:0 5px;"><input value="0" type="checkbox" name="'
	   		+dbName+'" '+checked1+' />显示</label>'
	   		+'</td></tr>';
		}
	}else{
		for(var i=0; i<columnArr.length;i++){
			var checked1 = '';
			var checked2 = '';
			if($('#div'+dbName+' td:eq(1) div:eq('+i+') span.'+dbName+"_show").text().indexOf('显示') > -1){
				checked1 = 'checked';
			}
			if($('#div'+dbName+' td:eq(1) div:eq('+i+') span.'+dbName+"_query").text().indexOf('检索') > -1){
				checked2 = 'checked';
			}
	   		htm += '<tr><td class="'+columnArr[i]+'" style="width: 60%; padding: 5px 0;border: 1px solid #ddd">'
	   		+ columnArrVal[i]+'</td><td nowrap style="padding: 5px 0;border: 1px solid #ddd">'
	   		+'<label style="line-height: 1.2;white-space: nowrap; margin:0 5px;"><input value="0" type="checkbox" name="'
	   		+dbName+'" '+checked1+' />显示</label>'
	   		+'<label style="line-height: 1.2;white-space: nowrap; margin:0 5px;"><input value="1" type="checkbox" name="'
	   		+dbName+'" '+checked2+' />检索</label></td></tr>';
		}
	}
	Boxy.confirm("<table id='pre_sel' cellpadding='0' cellspacing='0' style='width:100%; border-collapse:collapse;'>"+htm+"</table>", 
			function() {
				toSelContent(dbName, dbNamecn);
			}, function(){
				//alert('cancel()');
			});
}

//将选择的字段信息展示在页面上
function toSelContent(dbName, dbNamecn){
	var columnArr = columnMap[dbName+"_key"].split(',');
	var columnArrVal = columnMap[dbName+"_value"].split(',');
	var htm = '<td style="width: 12%; border: 1px solid #ddd;"><span> '
		+dbNamecn+'</span></td>';
	htm += '<td style="padding: 5px 0;border: 1px solid #ddd">';
	/* for(var i=0; i<columnArr.length;i++)
	{
		//console.log(i + ": " + columnArr[i]);
   //		htm += '<label style="width: 100px; line-height: 1.2;white-space: nowrap;"><input type="checkbox" name="'+dbName+'" value="'+columnArr[i]+'" checked />'+ columnArr[i]+'</label> ';
		htm += columnArr[i];
	} */
	$('#pre_sel tr:gt(0)').each(function(index){
		htm += '<div style="white-space:nowrap; display:inline;width:180px;margin-right:10px;line-height:1.5;">'
			+'<span style="color:#069;">'+$(this).find('td:first').text()+'</span><input type="hidden" value="'+
			$(this).find('td:first').attr('class')+'" />&nbsp;&nbsp;';
		$(this).find('td:last input:checked').each(function(){
			//var tstr = '';
			if('0' == $(this).val()){
			//	htm += '显示<label style="white-space:nowrap;line-height: 1.2;white-space: nowrap; "><input disabled type="checkbox" checked /></label>&nbsp;&nbsp;';
				htm += '<span class="'+dbName+'_show">[显示]</span>'; 
			}
			if('1' == $(this).val()){
			//	htm += '检索<label style="white-space:nowrap; line-height: 1.2;white-space: nowrap;"><input disabled type="checkbox" checked /></label>';
				htm += '<span class="'+dbName+'_query">[检索]</span>';
			}
			//htm += '['+tstr+']';
		})
		htm += '</div>'
	})

	//alert($('#div'+dbName).html());
	if($('#div'+dbName).length == 0){
		if($('#cond_checkTable').html()=='无'){
			$('#cond_checkTable').html(htm);
		}else{
		  $('#cond_checkTable').append(htm);
		}
	}else{
		$('#div'+dbName).html(htm);
	}
	//$('#pre_sel tr:gt(0)').each(function(index){
	//	alert($(this).find('td:last input:checked').val());
	//})
}

function removeTrsDbColumn(dbName){
	$('#div'+dbName).remove();
}

//删除表之前检查是否在表关联条件和查询条件两处是否有引用
function checkUsedTb(){
	var flag = true;
	return flag;
}

function rightToLeftAll(){
	var checkResult = checkUsedTb();
	if(checkResult){
		var checkTab = $('#checkTbl_div').dataSelector('selectAllBlockNodes');
		rightToLeft();
	}
}

//最终提交方法
function getSelect(){
	//获取选择的数据表
	var e = new Array;
	$('#level_table a.nSelected').each(function(){
		e.push({'key': $(this).attr('id').replace('a_', '')});
	});
	if(e.length ==0){
		$('#pro_table a.nSelected').each(function(){
			e.push({'key': $(this).attr('id').replace('a_', '')});
		});
	}
	var tableFormNames="";
	var tableFormColumns="";
	var searchFormColumns="";
	if(e.length==0){
	  alert("请选择数据表!");
	  return;
	}else{
	  for(var ii=0;ii<e.length;ii++){
	    	tableFormNames+= (tableFormNames=='' ? e[ii].key :','+e[ii].key);
	    	var colStr = getColumns(e[ii].key);
	    	if(colStr.length==0){alert("表"+e[ii].key+"未选择字段！");return;}
	    	tableFormColumns+= e[ii].key+':'+colStr+';' ;
	    	colStr = getSearchColumns(e[ii].key);
	    	searchFormColumns+= e[ii].key+':'+colStr+';' ;
	  }
	}
	//alert(tableFormNames + '\n' +tableFormColumns + '\n' + searchFormColumns );
	setFormFieldValue("record:trs_data_base", tableFormNames);
	setFormFieldValue("record:trs_column", tableFormColumns);
	setFormFieldValue("test:service_description1", "2");
	setFormFieldValue("record:trs_search_column", searchFormColumns);
	//func_record_saveAndExit( '', '保存trs服务接口' );	
	saveAndExit( '', '保存trs服务接口' );
}

function getColumns(key){  //jquery获取复选框值  
  var chk_value = "";
  $('.'+key+'_show').each(function(index){
	  chk_value += (chk_value=='' ? $(this).prev('input').val() :','+$(this).prev('input').val());
  });
  //alert(chk_value);
  /* $('input[name="'+key+'"]:checked').each(function(){  
  	chk_value += (chk_value=='' ? $(this).val() :','+$(this).val());
  });   */
  return chk_value;
} 

function getSearchColumns(key){  //jquery获取复选框值  
	  var chk_value = ""; 
	  if($('.'+key+'_query').length == $('#div'+key).find("td:eq(1) div").length || $('.'+key+'_query').length == 0){
		  chk_value = 'all';
	  }else{
		  $('.'+key+'_query').each(function(index){
			  chk_value += (chk_value=='' ? ($(this).parent().find('input[type="hidden"]').val()) : (','+$(this).parent().find('input[type="hidden"]').val()));
		  });
	  }
	  return chk_value;
	} 

//获取服务编号
	function getService_no(){
		    var page = new pageDefine("/txn40300006.ajax", "获取服务编号");	
 			page.callAjaxService('setTrsServiceNo');
	}
	function setTrsServiceNo(errCode,errDesc,xmlResults){
	
			if(errCode != '000000'){
			setFormFieldValue( "record:trs_service_no" ,"trsService1");
			return;
		}
		var service_no   = _getXmlNodeValues( xmlResults, "/context/record/trs_service_no");
		//alert(service_no);
		setFormFieldValue( "record:trs_service_no" ,service_no);
	}
	
_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查看trs接口"/>
<freeze:errors/>

<freeze:form action="/txn40300002">
  <freeze:block property="record" caption="查看trs接口信息" width="95%">

      <freeze:hidden property="trs_service_id" caption="服务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:cell property="service_targets_id" caption="所属服务对象"  notnull="true" valueset="资源管理_共享服务对象名称" show="name" style="width:95%"/>
      <freeze:cell property="trs_service_name" caption="服务名称" datatype="string" style="width:95%"/>
      <freeze:cell readonly="true" property="trs_service_no" caption="服务编号"  datatype="string"  style="width:95%"/>
      <freeze:hidden property="trs_data_base" caption="服务库"  maxlength="1000" style="width:98%"/>
      <freeze:hidden property="trs_column" caption="展示字段"  maxlength="4000" style="width:98%"/>
      <freeze:hidden property="trs_search_column" caption="检索字段"  maxlength="4000" style="width:98%"/>
      <freeze:cell property="service_description" caption="服务说明"  style="width:98%"/>
      <freeze:cell property="trs_template" colspan="2" caption="模版文件"  style="width:98%"/>
      <freeze:cell property="crename" caption="创建人" datatype="string"  style="width:95%"/>
	  <freeze:cell property="cretime" caption="创建时间" datatype="string"  style="width:95%"/>
	  <freeze:cell property="modname" caption="最后修改人" datatype="string"  style="width:95%"/>
	  <freeze:cell property="modtime" caption="最后修改时间" datatype="string"  style="width:95%"/>
      <freeze:hidden property="service_state" caption="是否启用" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="is_markup" caption="是否有效" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="创建人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="创建时间" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="最后修改时间" datatype="string" maxlength="19" style="width:95%"/>
  </freeze:block>
  <div style="display:none;">
  	<freeze:block property="test">
	  <freeze:textarea property="service_description1" caption="服务说明" colspan="2" rows="2" maxlength="2000" style="width:98%"/>
  	</freeze:block>
  </div>
</freeze:form>

<br />
<table cellpadding="0" cellspacing="0" width="95%" align="center" style="border-collapse:collapse;">
<tr><td>
      <table id="tt1"  class="dd_table" border="0" cellpadding="0" cellspacing="0" width="100%" align="left">
     
      <tr><td>
      	<label style="margin-left: 5px;" class="title">通用库<input type="radio" name="db_type" value='0' /></label> 
      	<label style="margin-left: 5px;" class="title">专用库<input type="radio" name="db_type" value='1' /></label> 
      </td></tr>
      <tr class="first">
      	<td><!-- <div style="width:100%;" id="level_table"><ul id="table_ul"></ul></div> -->
      		<div style="width:100%;" class="allTheme" id="level_table">
					<span style="margin-left: 5px;" class="title">通用库</span> 
					<span id="span_table" class="content"></span>&nbsp;&nbsp;&nbsp;&nbsp;
					<span class="soo" style="white-space: nowrap;"><a id='selectAll' href="javascript:selectAllTable();">全选</a></span>
			</div>
      	</td>
      </tr>
      <tr class="second">
      	<td><!-- <div style="width:100%;" id="level_table"><ul id="table_ul"></ul></div> -->
      		<div style="width:100%;" class="allTheme" id="pro_table">
					<span style="margin-left: 5px;" class="title">专用库</span> 
					<span id="cspan_table" class="content"></span>
			</div>
      	</td>
      </tr>
      <tr><td width="15%" height='25' valign="middle">
      	<div id="info"></div>
      </td></tr>

      <tr>
      	<td style=""><table cellpadding='0' cellspacing='0' style='border-collapse:collapse;width:100%;' id="cond_checkTable"></table></td>
      </tr>
      	<tr>
		<td align="center" height="50" valign="bottom"><div class="btn_cancel"  onclick="func_record_goBack()"></div></td>
	
	</tr>

</table>

<form id="form1" name="form1" action="/page/share/interfaces/preview_interface.jsp" method="post" target="preview_data">
  <input type="hidden" name="tableIds" id="tableIds"/>
  <input type="hidden" name="table_sql" id="table_sql"/>
</form>
<div id="pre_add_div" style="display:none;position:absolute;
	left:50%;top:50%;height:400px;width:800px;margin-left:-400px;
	margin-top:-200px;background:white;border:1px solid #369;"></div>

</freeze:body>
</freeze:html>
