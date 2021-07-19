<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="1000" height="300">
<head>
<title>���ӹ����������ӿ�������Ϣ</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery-ui.custom.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-Selector/js/jquery.dataSelector.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-tab/jquery.tabs.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-plugin-data2table/js/jquery.tablet.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/share/share_interface.js"></script>
<link href="<%=request.getContextPath()%>/script/jquery-plugin-Selector/css/jquery.dataSelector.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/script/jquery-plugin-tab/css/jquery.tabs.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.sec_left{
}
</style>
</head>

<script language="javascript">

function limConTdWidth(){
$("#table_param  tr").each(function(){
var s=$(this).children("td").eq(5).width(200);
});
}
// �� ��
function func_record_goBack()
{
	goBack();	// /txn40200001.do
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
	if(tab_index==1){
		var data = $("#table_param").tablet("getAllData");
		data = eval('('+ data +')');
		if(data.data.length == 0){
			$("#condition_cond").attr("disabled", true);
		}else{
			$("#condition_cond").attr("disabled", false);
		}
	}
}
var tab_index = 0;
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	 $.tabs({
        selector: "#tabs",
        selected: 0,
        click: function(index, instance) {
        	if(index==1){
        		tab_index = 1;
        		var data = $("#table_param").tablet("getAllData");
        		data = eval('('+ data +')');
        		if(data.data.length == 0){
        			$("#condition_cond").attr("disabled", true);
        		}else{
        			$("#condition_cond").attr("disabled", false);
        		}
        	}else if(index==2){
        	  tab_index = 2;
            showSql();
          	var	tempParam=$("#table_param").tablet("getAllData");
   			bulidTableParam(tempParam);
   			tempParam=$("#selected_columns").tablet("getAllData");
          	bulidTableCondition(tempParam);
          }else{
        	  tab_index = 0;
          }
        }
      });
 
}

_browse.execute( '__userInitPage()' );
</script>
<script language=JavaScript>
var busiSystemData ;
var currentSystem ;
var busiTopicData ;
var currentTopic ;
var tblData ;
var checkTblData = new Array;
var checkedTblCount = 0;
var leftTblData = new Array;
var leftDataItem = new Array;
var rightTblData = new Array;
var rightDataItem = new Array;
var condition = new Array;
var condition_item = new Array;

// ���ط�������б�ҵ��ϵͳ��
$.ajax({
	  type: "post",
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
    //���ع�������div
	$('#relation_div').hide();
	//��ʼ��ҵ��ϵͳ�б�����
	var opt={
		data:busiSystemData,
		onClick:function(event,key){
			getTopic(key,'busiTopic_div');
		}
	}
	$('#busiSystem_div').dataSelector(opt);
		
	//��ʼ��ҵ�������б�����
	var opt_Topic={
		data:busiTopicData
	}
	$('#busiTopic_div').dataSelector(opt_Topic);
	
	//��ʼ�����ݱ��б�����	
	var opt_table={
		data:tblData
	}
	$('#tbl_div').dataSelector(opt_table);	
	
	//��ʼ����ѡ���ݱ��б�
	var opt_check={
		data:checkTblData,
		onDblclick:function(event,key){
			backTable(key);
		}}
	$('#checkTbl_div').dataSelector(opt_check);
	
	//��ʼ��������ϵ���
	var opt_leftTable={
		multiple:false,
		data:leftTblData,
		size:1,
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
		data:data4selected_table
	};
	$("#selected_columns").tablet(opt_cond_tab);
    
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
		addOper: true,
		data: param_table,
		shownum: 8
	};
	$("#table_param").tablet(opt_param_table);
    
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
					{"title":" ", "key":" "},
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
	setOperator('#condition_item_div', '#condition_middle');
})

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

//ѡ�����ݱ�-����ѡ���ݱ�
function checkTable(key){
	leftToRight();
}

//ɾ����ѡ���Ĳ���
function backTable(key){
	//����Ƿ������˱������ϵ
	var selData = $("#selected_columns").tablet("getAllData");
	selData = eval("("+selData+")") || {"data": []};
	for(var ii=0; ii<selData.data.length; ii++){
		if(selData.data[ii].leftTable.id == key){
			alert("�� "+selData.data[ii].leftTable.name_cn+"["+selData.data[ii].leftTable.name_en+"] �����ñ��������,����ɾ��.");
			return;
		}
		if(selData.data[ii].rightTable.id == key){
			alert("�� "+selData.data[ii].rightTable.name_cn+"["+selData.data[ii].rightTable.name_en+"] �����ñ��������,����ɾ��.");
			return;
		}
	}
	
	//����Ƿ������˲�ѯ����
	selData = $("#table_param").tablet("getAllData");
	selData = eval("("+selData+")") || {"data": []};
	for(var ii=0; ii<selData.data.length; ii++){
		if(selData.data[ii].leftTable.id == key){
			alert("�� "+selData.data[ii].leftTable.name_cn+"["+selData.data[ii].leftTable.name_en+"] �����ò�ѯ����,����ɾ��.");
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
	//alert("nihao");
}

function rightToLeft(){
	/* var keySingle = $('#checkTbl_div').dataSelector("getSelectedNodes");
	if(keySingle.length < 1)
		return;
	var selData = $("#selected_columns").tablet("getAllData");
	selData = eval("("+selData+")") || {"data": []};
	for(var ii=0; ii<selData.data.length; ii++){
		if(selData.data[ii].leftTable.id == keySingle[0].key){
			alert("�ñ������ñ��������,����ɾ��.");
			return;
		}
		if(selData.data[ii].rightTable.id == keySingle[0].key){
			alert("�ñ������ñ��������,����ɾ��.");
			return;
		}
	}
	
	//����Ƿ������˲�ѯ����
	selData = $("#table_param").tablet("getAllData");
	selData = eval("("+selData+")") || {"data": []};
	for(var ii=0; ii<selData.data.length; ii++){
		if(selData.data[ii].leftTable.id == keySingle[0].key){
			alert("�ñ������ò�ѯ����,����ɾ��.");
			return;
		}
	} */

	var checkResult = checkUsedTb();
	if(checkResult){
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
}

//ɾ����֮ǰ����Ƿ��ڱ���������Ͳ�ѯ���������Ƿ�������
function checkUsedTb(){
	var flag = true;
	var keyAll = $('#checkTbl_div').dataSelector("getSelectedNodes");
	var selData = $("#selected_columns").tablet("getAllData");
	selData = eval("("+selData+")") || {"data": []};
	var keys = '';
	for(var jj=0; jj<keyAll.length; jj++){
		keys += keyAll[jj].key;
	}
	for(var ii=0; ii<selData.data.length; ii++){
		if(keys.indexOf(selData.data[ii].leftTable.id) > -1){
			alert("�� "+selData.data[ii].leftTable.name_cn+"["+selData.data[ii].leftTable.name_en+"] �����ñ��������,����ɾ��.");
			flag = false;
			break;
		}
		if(keys.indexOf(selData.data[ii].rightTable.id) > -1){
			alert("�� "+selData.data[ii].rightTable.name_cn+"["+selData.data[ii].rightTable.name_en+"] �����ñ��������,����ɾ��.");
			flag = false;
			break;
		}
	}
	
	//����Ƿ������˲�ѯ����
	selData = $("#table_param").tablet("getAllData");
	selData = eval("("+selData+")") || {"data": []};
	for(var ii=0; ii<selData.data.length; ii++){
		if(keys.indexOf(selData.data[ii].leftTable.id) > -1){
			alert("�� "+selData.data[ii].leftTable.name_cn+"["+selData.data[ii].leftTable.name_en+"] �����ò�ѯ����,����ɾ��.");
			flag = false;
			break;
		}
	}
	return flag;
}

function rightToLeftAll(){
	var checkResult = checkUsedTb();
	if(checkResult){
		var checkTab = $('#checkTbl_div').dataSelector('selectAllBlockNodes');
		rightToLeft();
	}
}

//�����ύ����
function getSelect(){
    //��ȡѡ������ݱ�
	var e= $('#checkTbl_div').dataSelector("getAllNodes");
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
	//alert(getFormFieldValue("record:sql"));
	var checkResult=testSql(getFormFieldValue("record:sql"));
	if(checkResult==false){
		return;
	}
	
	saveAndExit( '', '���湲�����-�����ӿڱ�' );
}			


</script>
<freeze:body>
<freeze:title caption="���ӹ����������ӿ�������Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn401003">
	<freeze:block property="record" caption="���ӻ����ӿ�" width="95%">
		<freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="getSelect();"/>
		<freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
	  <freeze:text property="interface_name" caption="�ӿ�����" datatype="string" maxlength="100" notnull="true" style="width:75%" colspan="2"/>
	  <freeze:textarea property="interface_description" caption="�ӿ�˵��"  maxlength="50"  style="width:75%" colspan="2"/>
	  <freeze:hidden property="table_id" caption="�ӿڱ�ID��" datatype="string"/>
	  <freeze:hidden property="table_name_cn" caption="�ӿڱ����ƴ�" datatype="string"/>
	  <freeze:hidden property="table_code" caption="�ӿڱ����ƴ�" datatype="string"/>
	  <freeze:hidden property="condition" caption="��������" datatype="string"/>
	  <freeze:hidden property="param" caption="��ѯ����" datatype="string"/>
	  <freeze:hidden property="sql" caption="��ѯsql" datatype="string"/>
	</freeze:block>
</freeze:form>

<Br/>
<table border="0" cellpadding="0" cellspacing="0" width="95%" align="center" style="border-collapse:collapse;">
<tr><td><div style="width:100%;">
<dl class="tabs" id="tabs">
     <dt>
	        <a>���ò�ѯ��Χ</a>
	      	<a>���ò�ѯ����</a>
	      	<a>Ԥ��</a>
     </dt>
     <dd><div>
      <table class="dd_table" border=1 cellpadding=3 cellspacing=0 width="100%" align="center">
		<tr><td width="120">ѡ��ҵ��ϵͳ��</td>
			<td width="120">ѡ�����⣺</td>
			<td width="120">ѡ�����ݱ�</td>
			<td width="50"></td>
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
	   			<button onclick="leftToRight()" > >&nbsp;</button> 
	   			<br/>
	   			<button onclick="leftToRightAll()" > &gt;&gt; </button> 
	   			<br/>
	   			-->
	   			
	 	  		<button onclick="rightToLeft()"> <&nbsp; </button> 
	   			<br/>
	   			<button onclick="rightToLeftAll()" > &lt;&lt; </button> 
	   			</td>
	   			<td width="120"><div id="checkTbl_div"></div></td>
			</tr>
		</table>
		<br />
		<div id="relation_div">
			<table class="dd_table" border=1 cellpadding=3 cellspacing=0 width="100%" align="center">
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
		</div><br /></div>
 </dd>
  <dd><div>
  <table class="dd_table" border=1 cellpadding=3 cellspacing=0 width="100%" align="center">
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
	       <input type="text" name="param_value" id="paramValue" /> 
	       <select id='pvs' style='display:none;width:100px;'></select> 
	       </td>
	       <td><div id="condition_right"></div></td>
	     </tr>
     <tr><td colspan="7" align="right"><input type="button" value="��Ӳ�ѯ����" onclick="addParam();"/>&nbsp;&nbsp;</td></tr>
     <tr><td colspan="7"><div id="table_param"></div></td></tr>
	</table><br /></div>
  </dd>
  <dd>  <div>
   <table class="dd_table" border=1 cellpadding=3 cellspacing=0 width="100%" align="center">
		<tr><td width="15%">ѡ�����ݱ�Ϊ:</td><td align="left"><div id="cond_checkTable">��</div></td></tr>
		<tr><td>���ݱ������ϵ:</td><td><div id="cond_condition">��</div></td></tr>
		<tr><td>���ݱ��ѯ����:</td><td><div id="cond_param">��</div></td></tr>
		<tr><td>SQL���:</td><td><div id="sql_last"></div></td></tr>
		<tr><td colspan="2"><a href="javascript:preview();">����鿴Ԥ��</a></td></tr>
		<tr><td colspan="2"><iframe id="preview_data" name="preview_data" src="" width="100%" style="width: 100%;"></iframe></td></tr>
	</table><br /></div>
  </dd>
</dl>
		</div>
		</td>
	</tr>
</table>


<form id="form1" name="form1" action="/page/share/interfaces/preview_interface.jsp" method="post" target="preview_data">
  <input type="hidden" name="tableIds" id="tableIds"/>
  <input type="hidden" name="table_sql" id="table_sql"/>
</form>
</freeze:body>
</freeze:html>
