<%@ page contentType="text/html; charset=GBK" %>
<%@page import="cn.gwssi.common.context.DataBus"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="1000" height="350">
<head>
<title>�޸Ĺ����������ӿ�������Ϣ</title>
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
var busiSystemData ;
var currentSystem ;
var busiTopicData=eval('(<%=db.getValue("dataSystem")%>)'); ;
var currentTopic ;
var tblData =eval('(<%=db.getValue("dataTable")%>)');
var checkTblData = eval('(<%=db.getValue("dataString")%>)');
var ori_checkTblData = eval('(<%=db.getValue("dataString")%>)');
var checkedTblCount = 0;
var leftTblData = eval('(<%=db.getValue("dataString")%>)');
var leftDataItem = new Array;
var rightTblData = eval('(<%=db.getValue("dataString")%>)');
var rightDataItem = new Array;
var condition = eval('(<%=db.getValue("dataString")%>)');
var condition_item = new Array;
var selected_service_targets_id = '<%=db.getValue("selected_service_targets_id")%>';
var selected_topics_id = '<%=db.getValue("selected_topics_id")%>';
ori_tableCondition = eval('(<%=db.getValue("tableCondition")%>)');
ori_tableParam = eval('(<%=db.getValue("tableParam")%>)');

function limConTdWidth(){
$("#table_param  tr").each(function(){
var s=$(this).children("td").eq(5).width(200);
});
}

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
	goBack();	// /txn401001.do
}

//������ʱչʾ��ǰѡ�еĴ���ֵ
function showSelectedValue(thistd)
{
   var selectOp = window.document.getElementById('pvs');
  if( selectOp.selectedIndex>-1){
	thistd.title=document.getElementById('pvs').options[selectOp.selectedIndex].text;
	}
	else{
	thistd.title="";
	}
}

function deleteReturn(index){
	//return false;
	var data = $("#table_param").tablet("getAllData");
	data = eval('('+ data +')');
	if(data.data.length == 0){
		$("#condition_cond").attr("disabled", true);
	}else{
		$("#condition_cond").attr("disabled", false);
	}
}
var being_used = 'false';
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	being_used = getFormFieldValue('primary-key:being_used');
	 $.tabs({
        selector: "#tabs",
        selected: 0,
        click: function(index, instance) {
        	if(index==1){
        		var data = $("#table_param").tablet("getAllData");
        		data = eval('('+ data +')');
        		if(data.data.length == 0){
        			$("#condition_cond").attr("disabled", true);
        		}else{
        			$("#condition_cond").attr("disabled", false);
        			$("#condition_cond option[value='']").remove();
        			$("#condition_cond option[value=' ']").remove();
        		}
        	}
          if(index==2){
        	showSql();
          	var	tempParam=$("#table_param").tablet("getAllData");
   			bulidTableParam(tempParam);
   			tempParam=$("#selected_columns").tablet("getAllData");
          	bulidTableCondition(tempParam);
        }
        }
      });
      //���ط�������б�ҵ��ϵͳ��
$.ajax({
	  type: "get",
	  url: "<%=request.getContextPath()%>/txn401006.ajax",
	  async: false,
	  success: function(xmlResults){
	  	if (xmlResults.selectSingleNode("//context/error-code").text != "000000"){
	  		alert(xmlResults.selectSingleNode("//context/error-desc").text);
	  		return false;
	  	}else{
	  		var datas = _getXmlNodeValues( xmlResults, "/context/dataString" );
	  		busiSystemData = eval('('+datas+')');
	  	}
	  }
});
	
$(function(){
	//��ʼ��ҵ��ϵͳ�б�����
	var opt={
		data:busiSystemData,
		onClick:function(event,key){
			getTopic(key,'busiTopic_div');
		}
	}
	$('#busiSystem_div').dataSelector(opt);
	$("#"+selected_service_targets_id).attr("selected", true);
	
	//��ʼ��ҵ�������б�����
	var opt_Topic={
		data:busiTopicData, 
		onClick: function(event, key){
           getTable(key, 'tbl_div');
        }
	}
	$('#busiTopic_div').dataSelector(opt_Topic);
	$("#"+selected_topics_id).attr("selected", true);
	
	//��ʼ�����ݱ��б�����	
	var opt_table={
		data:tblData,
		onClick: function(event, key){
            checkTable(key);
        }
	}
	$('#tbl_div').dataSelector(opt_table);	
	
	//��ʼ����ѡ���ݱ��б�
	var opt_check={
		data: checkTblData,
		onDblclick:function(event,key){
			backTable(key);
		}}
	$('#checkTbl_div').dataSelector(opt_check);
	
	//��ʼ��������ϵ���
	var opt_leftTable={
		multiple: false,
		data: leftTblData,
		size: 1,
		onChange:function(event,key){
			getDataItemByKey(key,"leftItem_div");
		}
	}
	$('#leftTbl_div').dataSelector(opt_leftTable);
	
	//��ʼ��������ϵ�ұ�
	var opt_rightTable={
		multiple:false,
		data:rightTblData,
		size:1,
		onChange:function(event,key){
			getDataItemByKey(key,"rightItem_div");
		}
	}
	$('#rightTbl_div').dataSelector(opt_rightTable);	
	
	//��ʼ��������ϵ����ֶ��б�
	var opt_leftDataItem={
		multiple:false,
		data:{},
		size:1
	}
	$('#leftItem_div').dataSelector(opt_leftDataItem);
	
	//��ʼ��������ϵ����ֶ��б�
	$('#rightItem_div').dataSelector(opt_leftDataItem);
	$('#condition_item_div').dataSelector(opt_leftDataItem);
	
	//����������б�
	if(checkTblData.length>0){
	  getDataItemByKey(checkTblData[0].key,"leftItem_div");
	  getDataItemByKey(checkTblData[0].key,"rightItem_div");
	  getDataItemByKey(checkTblData[0].key,"condition_item_div");
	}
	//��ʼ��������ϵ����ֶ��б�
	var opt_leftDataItem={
		multiple:false,
		data:leftDataItem,
		size:1
	}
	$('#leftItem_div').dataSelector(opt_leftDataItem);
	
	//��ʼ��������ϵ����ֶ��б�
	var opt_rightDataItem={
		multiple:false,
		data:rightDataItem,
		size:1
	}
	$('#rightItem_div').dataSelector(opt_rightDataItem);
	
	//��ʼ�����ò�ѯ�������ݱ��б�
	var opt_condition={
		multiple:false,
		data:condition,
		size:1,
		onChange:function(event,key){
			getDataItemByKey(key,"condition_item_div");
		}
	}
	$('#condition_div').dataSelector(opt_condition);
	
	//��ʼ�����ò�ѯ�����������б�
	var opt_condition_item={
		multiple:false,
		data:condition_item,
		size:1
	}
	$('#condition_item_div').dataSelector(opt_condition_item);
	
	//��ʼ��������ϵ��ͷ
	var data4selected_table = [{
		leftTable : "���",
		leftColumn : "����������",
		middleParen: "��������",
		rightTable : "�ұ�",
		rightColumn : "����������"
	   }];
    var opt_cond_tab = {
		addOper:true,
		onDelete:re_cond_init,
		data:data4selected_table
	};
	$("#selected_columns").tablet(opt_cond_tab);
	$("#selected_columns").tablet("addData",eval('(<%=db.getValue("tableCondition")%>)'));
	if(being_used == 'true'){
		//����ӿ�����ʹ�� ���ε�ɾ������
		$("#selected_columns").find('td a').remove();
	}
	//��ʼ��Ԥ����ǩ
	bulidTableCondition(eval('(<%=db.getValue("tableCondition")%>)'));
    
    //��ʼ����ѯ��ϵ��ͷ
	var param_table = [{
	    cond : "�߼�����",
	    leftParen : "����",
		leftTable : "���",
		leftColumn : "����������",
		middleParen: "����",
		paramValue : "����ֵ",
		rightParen : "����"
	    }
    ];
    var opt_param_table = {
		addOper:true,
		onDelete:re_param_init,
		data:param_table
	};
	$("#table_param").tablet(opt_param_table);
	$("#table_param").tablet("addData",eval('(<%=db.getValue("tableParam")%>)'));
	if(being_used == 'true'){
		//����ӿ�����ʹ�� ���ε�ɾ������
		$("#table_param").find('td a').remove();
	}
	
	//��ʼ��Ԥ����ǩ
	bulidTableParam(eval('(<%=db.getValue("tableParam")%>)'));
	
    var leftParen=[
                    {"title":"��ѡ��","key":" "},
                    {"title":"(","key":"("},
                    {"title":"((","key":"(("},
                    {"title":"(((","key":"((("},
                    {"title":"((((","key":"(((("}
                    ];
    var opt_condition_left={
		multiple:false,
		data:leftParen,
		size:1,
		className: "sec_left"
	};
	$('#condition_left').dataSelector(opt_condition_left);
	
    var middleParen=[
                    {"title":"����","key" :"="},
                    {"title":"����" ,"key":">"},
                    {"title":"С��" ,"key":"<"},
                    {"title":"���ڵ���" ,"key":">="},
                    {"title":"С�ڵ���" ,"key":"<="}
                    ];
                    
    var opt_condition_middle={
		multiple:false,
		data:middleParen,
		size:1,
		className: "sec_left"
	};
	$('#condition_middle').dataSelector(opt_condition_middle);
	
	var rightParen=[
	                {"title":"��ѡ��","key":" "},
	                {"title":")","key":")"},
                    {"title":"))","key":"))"},
                    {"title":")))","key":")))"},
                    {"title":"))))","key":"))))"}
                    ];
    var opt_condition_right={
		multiple:false,
		data:rightParen,
		size:1,
		className: "sec_left"
	};
	$('#condition_right').dataSelector(opt_condition_right);
	//��������
	var condition_paren=[
                    {"title":"����","key":"="},
                    {"title":"������","key":"left join"}
                    ];
    var opt_condition_paren={
		multiple: false,
		data: condition_paren,
		size: 1,
		className: "sec_left"
	};
	$('#condition_paren').dataSelector(opt_condition_paren);
	//��ѯ����
	var condition_cond=[
                    {"title":" ","key":" "},
                    {"title":"����(AND)","key":"AND"},
                    {"title":"����(OR)","key":"OR"}
                    ];
    var opt_condition_cond={
		multiple: false,
		data: condition_cond,
		size: 1,
		className: "sec_left"
	};
	$('#condition_cond').dataSelector(opt_condition_cond);
	//���Ԥ����ǩ��ѡ��
	var tables=$('#checkTbl_div').dataSelector('getAllNodes');
	var tables_html="";
	for(var i=0;i<tables.length;i++){
	  tables_html+= (tables_html=="" ? tables[i].title : ","+tables[i].title);
	}
	$("#cond_checkTable").html(tables_html);
	setOperator('#condition_item_div', '#condition_middle');
	var check_table= $('#checkTbl_div').dataSelector("getAllNodes");
	if(check_table.length>=2){
	   $('#relation_div').show();
	//   $('#rightTbl_div').find("option:eq(1)")[0].selected = true;
		if($('#leftTbl_div_id').find("option:selected").attr("id")!=$('#rightTbl_div_id').find("option[index=1]").attr("id"))
		   $('#rightTbl_div_id').find("option[index=1]").attr("selected", true);
	   else{
		   $('#rightTbl_div_id').find("option[index=0]").attr("selected", true);
	   }
	   getDataItemByKey( $('#rightTbl_div').find("option:selected").val(),"rightItem_div");
	}else{
	   $('#relation_div').hide();
	}
	
	deleteCheckTableFromTableList();
	
});

//����ѡ��Ϊ�ղ�Ϊ��ʱ���������򲻿�����
/* $('#condition_middle_id').change(function(){
    var p1=$(this).children('option:selected').val();//�����selected��ֵ
    if(p1=="is null" || p1=="is not null")
    {
    	$('#paramValue').attr("disabled",true);
    	$('#paramValue').attr("value",'');
    }
    else
    {
    	$('#paramValue').attr("disabled",false);
    }
    }); */
    
limConTdWidth();
}

	_browse.execute( '__userInitPage()' );
</script>
<script language=JavaScript>

//ѡ�����ݱ�Ĳ���
function leftToRight(){
	var checkTab = $('#tbl_div').dataSelector('removeSelectedNodes');
	$('#checkTbl_div').dataSelector('addNodes',checkTab);
	$('#condition_div').dataSelector('addNodes',checkTab);
	$('#leftTbl_div').dataSelector('addNodes',checkTab);
	$('#rightTbl_div').dataSelector('addNodes',checkTab);
	var left_item= $('#leftItem_div').dataSelector("getAllNodes");
	var check_table= $('#checkTbl_div').dataSelector("getAllNodes");
	if(check_table.length>=2){
	   $('#relation_div').show();
	   $('#rightTbl_div').find("option:eq(1)")[0].selected = true;
	   getDataItemByKey( $('#rightTbl_div').find("option:selected").val(),"rightItem_div");
	}else{
	   $('#relation_div').hide();
	   $('#leftItem_div_id').empty();
	   $('#leftItem_div_id').empty();
	}
	//�����һ��������ݱ����������ֶε�����
	if(left_item.length==0){
	   //����ֶ��������
	   getDataItemByKey(checkTab[0].key,"leftItem_div");
	   getDataItemByKey(checkTab[0].key,"condition_item_div");		
	}
	//���Ԥ����ǩ��ѡ��
	var tables=$('#checkTbl_div').dataSelector('getAllNodes');
	var tables_html="";
	for(var i=0;i<tables.length;i++){
	  tables_html+= (tables_html=="" ? tables[i].title : ","+tables[i].title);
	}
	$("#cond_checkTable").html(tables_html);
}

function checkTable(key){
	leftToRight();
}

function doShowRelationDiv(){
	var checkedTab = $('#checkTbl_div').dataSelector('getAllNodes');
	if( $('#relation_div').is(':hidden')){
		$('#relation_div').show();
		$('#leftTbl_div').dataSelector('addNodes',checkedTab);
		$('#rightTbl_div').dataSelector('addNodes',checkedTab);
	} else {
	
	}
}
function rightToLeft(){
	if(being_used == 'true'){
		var nodes = $('#checkTbl_div').dataSelector('getSelectedNodes');
		for(var jj =0;jj<nodes.length;jj++){
			for(var ii=0; ii<ori_checkTblData.length; ii++){
				if( ori_checkTblData[ii].key == nodes[jj].key){
					alert("�ñ�Ϊ�ӿ�ԭ�����ñ�����ɾ��.");
					return;
				}
			}
		}
		
	}
	var checkTab = $('#checkTbl_div').dataSelector('removeSelectedNodes');
	//Ϊ�˷ǵ�ǰ�����µı�ɾ��ʱ�����б��г��֣�ɾ��ʱˢ��һ�����»�ȡ����ɾ���ظ���
	getTable($('#busiTopic_div_id').val(), 'tbl_div');
	//$('#tbl_div').dataSelector('addNodes',checkTab);
	
	
	if(checkTab != "" ){
	
		for(var i=0;i<checkTab.length;i++){
		        var key = checkTab[i].key;
	    		
				$('#leftTbl_div').dataSelector('removeNodes',key);
				$('#rightTbl_div').dataSelector('removeNodes',key);
				$('#condition_div').dataSelector('removeNodes',key);
				var check_table= $('#checkTbl_div').dataSelector("getAllNodes");
				if(check_table.length>=2){
				   $('#relation_div').show();
				   if($('#leftTbl_div_id').find("option:selected").attr("id")!=$('#rightTbl_div_id').find("option[index=1]").attr("id"))
					   $('#rightTbl_div_id').find("option[index=1]").attr("selected", true);
				   else{
					   $('#rightTbl_div_id').find("option[index=0]").attr("selected", true);
				   }
				}else{
				   $('#relation_div').hide();
				   $('#leftItem_div_id').empty();
				   $('#leftItem_div_id').empty();
				}
		}
    }
}

function rightToLeftAll(){
	var checkTab = $('#checkTbl_div').dataSelector('selectAllBlockNodes');
	rightToLeft();
}

//ɾ����ѡ���Ĳ���
function backTable(key){
	var selTblData = $("#table_param").tablet("getAllData");
	selTblData = eval("("+selTblData+")") || {"data": []};
	if(being_used == 'true'){
		for(var ii=0; ii<ori_checkTblData.length; ii++){
			if( ori_checkTblData[ii].key == key){
				alert("�ñ�Ϊ�ӿ�ԭ�����ñ�����ɾ��.");
				return;
			}
		}
	}
	
	for(var ii=0; ii<selTblData.data.length; ii++){
		
		
		if(selTblData.data[ii].leftTable.id == key){
			
			alert("�ñ������ò�ѯ����,����ɾ��.");
			return;
		}
		
	}
    var backedTab = $('#checkTbl_div').dataSelector('removeSelectedNodes');
	//Ϊ�˷ǵ�ǰ�����µı�ɾ��ʱ�����б��г��֣�ɾ��ʱˢ��һ�����»�ȡ����ɾ���ظ���
	getTable($('#busiTopic_div_id').val(), 'tbl_div');
	//$('#tbl_div').dataSelector('addNodes',checkTab);
	$('#leftTbl_div').dataSelector('removeNodes',key);
	$('#rightTbl_div').dataSelector('removeNodes',key);
	$('#condition_div').dataSelector('removeNodes',key);
	var check_table= $('#checkTbl_div').dataSelector("getAllNodes");
	
	if(check_table.length == 0){
		setFormFieldValue("record:sql", " ");
		document.getElementById('table_sql').value = "";
		$('#condition_item_div').find("option").remove();
	}else{
		var nowTbl = $('#condition_div_id').find("option:selected");
		var item_data=getDataItemByKey(nowTbl.val(),"condition_item_div");	
	}
	if(check_table.length>=2){
	   $('#relation_div').show();
	}else{
	   $('#relation_div').hide();
	   $('#leftItem_div_id').empty();
	   $('#leftItem_div_id').empty();
	}
}

//�����ύ����
function getSelect(){
    //��ȡѡ������ݱ�
	var e = $('#checkTbl_div').dataSelector("getAllNodes");
	var tableIds="";
	var tableNames="";
	var tableCodes="";
	if(e.length==0){
	  alert("��ѡ�����ݱ�!");
	  return;
	}else{
	  for(var ii=0;ii<e.length;ii++){
	    tableIds+= (tableIds=='' ? e[ii].key :','+e[ii].key);
	    tableNames+= (tableNames=='' ? e[ii].title :','+e[ii].title);
	    tableCodes+= (tableCodes=='' ? e[ii].code :','+e[ii].code);
	  }
	}
	setFormFieldValue("record:table_id", tableIds);
	setFormFieldValue("record:table_name_cn", tableNames);
	setFormFieldValue("record:table_code", tableCodes);
	//ƴװsql
	setFormFieldValue("record:sql", createSql('1'));
	//��ȡ�������ϵֵ
	var relation=$("#selected_columns").tablet("getAllData");
	if(relation!='{data:[]}'){
	   setFormFieldValue("record:condition", relation);
	   setFormFieldValue("record:sql", createSql('2'));
	}
	//��ȡ��ѯ����ֵ
	var param=$("#table_param").tablet("getAllData");
	if(param!='{data:[]}'){
	   setFormFieldValue("record:param", param);
	   setFormFieldValue("record:sql", createSql('3'));
	}
	var checkResult=testSql(getFormFieldValue("record:sql"));
	if(checkResult==false){
	  return;
	}
	setFormFieldValue("record:interface_name", getFormFieldValue("record:interface_name"));
	setModifyFlag(true);
	saveAndExit( '', '���湲�����-�����ӿڱ�' );
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn40200001.do
}					
</script>
<freeze:body>
<freeze:title caption="�޸Ļ����ӿ�������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn401002">
<freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="interface_id" caption="�ӿ�ID" style="width:95%"/>
      <freeze:hidden property="being_used" caption="�Ƿ�ʹ��" style="width:95%"/>
  </freeze:frame>
<freeze:block property="record" caption="�޸Ļ����ӿ�" width="95%">
	<freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="getSelect();"/>
	<freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
  <freeze:hidden property="interface_id" caption="�ӿ�ID" datatype="string" maxlength="32" style="width:95%"/>
  <freeze:text property="interface_name" caption="�ӿ�����" datatype="string" maxlength="100" notnull="true" style="width:75%" colspan="2"/>
  <freeze:textarea property="interface_description" caption="�ӿ�˵��"  maxlength="50"  style="width:75%" colspan="2"/>
  <freeze:cell property="crename" caption="�����ˣ�" datatype="string"  style="width:95%"/>
  <freeze:cell property="cretime" caption="����ʱ�䣺" datatype="string"  style="width:95%"/>
  <freeze:cell property="modname" caption="����޸��ˣ�" datatype="string"  style="width:95%"/>
  <freeze:cell property="modtime" caption="����޸�ʱ�䣺" datatype="string"  style="width:95%"/>
 
  <freeze:hidden property="table_id" caption="�ӿڱ�ID��" datatype="string"/>
  <freeze:hidden property="table_name_cn" caption="�ӿڱ����ƴ�" datatype="string"/>
  <freeze:hidden property="table_code" caption="�ӿڱ����ƴ�" datatype="string"/>
  <freeze:hidden property="sql" caption="�ӿڱ����ƴ�" datatype="string"/>
  <freeze:hidden property="condition" caption="��������" datatype="string"/>
  <freeze:hidden property="param" caption="��ѯ����" datatype="string"/>
  <freeze:hidden property="interface_state" caption="�ӿ�״̬" datatype="string"/>
  <freeze:hidden property="is_markup" caption="�Ƿ���Ч" datatype="string"/>
  <freeze:hidden property="creator_id" caption="������ID" datatype="string"/>
  <freeze:hidden property="created_time" caption="����ʱ��" datatype="string" visible="false"/>
</freeze:block>
</freeze:form>
<Br/>
<table border=0 cellpadding=0 cellspacing=0 width="95%" align="center" style="border-collapse:collapse;">
	<tr>
		<td><div style="width:100%;">
<dl class="tabs" id="tabs">
     <dt>
	        <a>���ò�ѯ��Χ</a>
	      	<a>���ò�ѯ����</a>
	      	<a>Ԥ��</a>
     </dt>
     <dd>
      <div>
         <table class="dd_table" border="1" cellpadding="3" cellspacing="0" width="100%" align="center">
		<tr><td width="120">ѡ��ҵ��ϵͳ��</td>
			<td width="120">ѡ�����⣺</td>
			<td width="120">ѡ�����ݱ�</td>
			<td width="50">&nbsp;</td>
			<td width="120">��ѡ���ݱ�</td>
			</tr>
			<tr>
				<td width="120">
					<div id="busiSystem_div"></div>
				</td>
				<td width="120">
					<div id="busiTopic_div"></div>
				</td>
				<td width="120">
					<div id="tbl_div"></div>
				</td>
				<td align="center" valign="middle" width="50">
				<!--  
	   				<button onclick="leftToRight()" > >&nbsp;</button><br/>
	   				<button onclick="leftToRightAll()" > >> </button><br/>
	   			-->	
	 	  			<button onclick="rightToLeft()"> <&nbsp; </button><br/>
	   				<button onclick="rightToLeftAll()" > << </button> 
	   			</td>
	   			<td width="120"><div id="checkTbl_div"></div></td>
			</tr>
		</table>
		<br />
		<div id="relation_div">
			<table style="width: 100%;background-color: #dee6e9; border:1px solid #069;border-collapse:collapse;" cellpadding=2 cellspacing=0 border=1 align="center">
			<tr><td width="15%">��ѡ��</td><td width="25%"><div id="leftTbl_div"></div></td><td colspan="2"></td>
				<td width="15%">��ѡ��</td><td width="25%"><div id="rightTbl_div"></div></td>
			</tr>
			<tr><td width="15%">������ѡ��</td><td width="25%"><div id="leftItem_div"></div></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;����</td>
				<td><div id="condition_paren"></div></td>
				<td width="15%">������ѡ��</td><td width="25%"><div id="rightItem_div"></div>
			</tr>
			<tr><td colspan="6" align="right"><input type="button" value="��ӹ�������" onclick="addRelation();"/>&nbsp;&nbsp;</td></tr>
			<tr><td colspan="6"><div id="selected_columns"></div></td></tr>
			</table>
			<br />
		</div>
  </div>
 </dd>
  <dd>
  
<div>
    <table class="dd_table" border="1" cellpadding="3" cellspacing="0" width="100%" align="center">
		<tr align="center">
		   <td width="5%">�߼�����</td>
		   <td width="5%">����</td>
		   <td>ѡ���</td>
		   <td>ѡ��������</td>
		   <td>����</td>
		   <td>ֵ</td>
		   <td>����</td>
		 </tr>
		<tr>
		   <td><div id="condition_cond"></div></td>
		   <td><div id="condition_left"></div></td>
           <td><div id="condition_div"></div></td>
           <td><div id="condition_item_div"></div></td>
           <td><div id="condition_middle"></div></td>
	       <td onmouseover="showSelectedValue(this);">
	       <input type="text" id="paramValue" name="paramValue" size="8"/>
	       <select id='pvs' style='display:none;width:100px;'></select> 
	       </td>
	       <td><div id="condition_right"></div></td>
	     </tr>
     <tr><td colspan="7" align='right'><input type="button" value="��Ӳ�ѯ����" onclick="addParam();"/>&nbsp;&nbsp;</td></tr>
     <tr><td colspan="7"><div id="table_param"></div></td></tr>
	</table>
    </div>
  </dd>
  <dd>  
   <div>
    <table class="dd_table" border="1" cellpadding="3" cellspacing="0" width="100%" align="center">
		<tr><td colspan="6">����Ԥ����</td></tr>
		<tr><td width="15%">ѡ�����ݱ�Ϊ:</td><td align="left"><div id="cond_checkTable">��</div></td></tr>
		<tr><td>���ݱ������ϵ:</td><td><div id="cond_condition">��</div></td></tr>
		<tr><td>���ݱ��ѯ����:</td><td><div id="cond_param">��</div></td></tr>
		<tr><td>SQL:</td><td><div id="sql_last"></div></td></tr>
		<tr><td colspan="6"><a href="javascript:preview();">����鿴Ԥ��</a></td></tr>
		<tr><td colspan="6"><iframe id="preview_data" name="preview_data" src="" width="100%" style="width: 100%;height:200px;"></iframe></td></tr>
	</table>
  </div></dd>
</dl></div></td></tr></table>

<form id="form1" name="form1" action="/page/share/interfaces/preview_interface.jsp" method="post" target="preview_data">
  <input type="hidden" name="tableIds" id="tableIds"/>
  <input type="hidden" name="table_sql" id="table_sql"/>
</form>
</freeze:body>
</freeze:html>
