<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script type="text/javascript" type="text/javascript" src="<%=request.getContextPath()%>/script/My97DatePicker/WdatePicker.js"></script>
<title>��ѯϵͳ��־�б�</title>
<style type="text/css">
.selectMenu {
	padding: 2px;
	background-color: #2B92E8;
	COLOR: #fff;
}
</style>
</head>

<script type="text/javascript">
//TODO ��ť��Ҫ�滻
function _menuClick(id)
{
	func_show_selectPic(true);
}
function func_show_selectPic(flag)
{
	var show_type=document.getElementById('chart_type').value;
	var page = new pageDefine( "/txn6020008.do", "�鿴ͼƬ");
	
	page.addValue(show_type, "select-key:show_type" );
	
	var chk_value=""; 
	var rPort = document.getElementsByName("query_index"); 
	for(i=0;i<rPort.length;i++) 
	{ 
	    if(rPort[i].checked) 
	    	chk_value=rPort[i].value; 
	}
	page.addValue(chk_value, "select-key:query_index" );
	
	var data_select=getFormFieldValue('select-key:query_index');
	var query_date='';
	if(flag){
		if(data_select=='day'){
			if($('#query_date_day_start').val()=='' && $('#query_date_day_end').val()==''){
				alert("��ѡ����ʼ����");
				return;
			}
			query_date=$('#query_date_day_start').val()+","+$('#query_date_day_end').val();
		}else{
			if(document.getElementById('query_date_'+data_select).value==''){
				if(data_select=='year'){
					alert("��ѡ�����");
				}else{
					alert("��ѡ���·�");
				}
				return;
			}
			query_date=document.getElementById('query_date_'+data_select).value;
		}
	}
	page.addValue(data_select, "select-key:query_type" );
	page.addValue(query_date, "select-key:query_date" );
	
    var win = window.frames('showPic');
  	document.getElementById("showPic").style.display = "block";	
	page.goPage( null, win );
}


// ����������ӣ�ҳ�������ɺ���û���ʼ������  
function __userInitPage()
{
	func_show_selectPic(false);
				$("input[name='form_reset']").click(function(){
						$(".Wdate").val("");
						toChageDate();
			});
}

function doQuery(type){
	document.getElementById('chart_type').value=type;
	document.getElementById('chartId').className = '';
	document.getElementById('tableId').className = '';
	document.getElementById(type + "Id").className = 'selectMenu';
	func_show_selectPic(true);
}

function toChageDate(){
	var data_select=getFormFieldValue('select-key:query_index');
	if(data_select!=''){
		document.getElementById('query_date_year').style.display="none";
		document.getElementById('query_date_month').style.display="none";
		document.getElementById('query_date_day').style.display="none";
		document.getElementById('choose_Date').style.display="block";
		document.getElementById('query_date_'+data_select).style.display="block";
	}else{
		document.getElementById('choose_Date').style.display="none";
		document.getElementById('query_date_year').style.display="none";
		document.getElementById('query_date_month').style.display="none";
		document.getElementById('query_date_day').style.display="none";
	}
}

function toChange(type){
	document.getElementById('span_type').style.display="none";
	document.getElementById('span_target').style.display="none";
	document.getElementById('span_'+type).style.display="block";
	if(type=='type'){
	  $("input[name=query_index][value='type_quantity']").attr("checked",true); 
	}else{
	  $("input[name=query_index][value='target_quantity']").attr("checked",true); 
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="����ֲ����ͳ��"/>

<freeze:errors/>
<freeze:form action="/txn6020001">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
 	  <!-- <tr> 
      	<td width="15%" align="right" class="odd12" id="label:select-key:query_index">
      		ͳ�Ʒ��ࣺ
      	</td>
      	<td width="75%" class="odd12" colspan="3">
      		<input type="radio" id="query_index0" value="type_quantity" name="query_index" checked="checked">����������(������)
      		<input type="radio" id="query_index1" value="target_quantity" name="query_index" >��Ӧ�������(������)
      		<input type="radio" id="query_index2" value="target_quantity" name="query_index" >����������(���������)
      		<input type="radio" id="query_index3" value="target_number" name="query_index" >��Ӧ�������(���������)
      	</td>
      </tr> -->
      <tr> 
      	<td width="15%" align="right" class="odd12" id="label:select-key:query_index">ͳ�Ʒ���</td>
      	<td width="75%" class="odd12" colspan="3">
      		<input type="radio" id="query_type0"  name="query_type" checked="checked" onclick="toChange('type');">�������������
      		<input type="radio" id="query_type1"  name="query_type" onclick="toChange('target');">���������
      	</td>
      </tr>
      <tr> 
      	<td width="15%" align="right" class="odd12" id="label:select-key:query_index">ͳ��ָ�꣺</td>
      	<td width="75%" class="odd12" colspan="3">
      	   <span id="span_type">
      		<input type="radio" id="query_index0" value="type_quantity" name="query_index" checked="checked">����������
      		<input type="radio" id="query_index1" value="type_number" name="query_index" >��Ӧ�������
      		</span>
      		<span id="span_target" style="display: none;">
      		<input type="radio" id="query_index0" value="target_quantity" name="query_index" >����������
      		<input type="radio" id="query_index1" value="target_number" name="query_index" >��Ӧ�������
      		</span>
      	</td>
      </tr>
	   <tr valign="middle" id="row_1" height="32">
		<td id='label:select-key:query_index'>ͳ�����ȣ�</td>
		<td>
		<script type="text/javascript" >
			addSelectInputField( new SelectInputField( 'select-key', 'query_index', 'select', '��־����_ͳ������(��/��)', 'name', 'query_index', '', '', '', '',
			[['year', '��', true, '', ''],
			['month', '��', true, '', ''],
			['day', '��', true, '', '']] ) );
		</script>
		<select name='select-key:query_index' id='select-key:query_index' onchange="toChageDate();" checkFlag='true' notnull='false' _default='month' _value='month' readOnly='false' style="width:90%"></select>
	    </td>
       <td width="100%" class="odd12"  colspan="2">
       <table style="width: 100%">
       <tr><td width="80px"><span id="choose_Date">ѡ��ʱ�䣺</span></td>
       <td>
        <input id="query_date_year" style="display: none; width: 90%" type="text" class="Wdate"
					onFocus="WdatePicker({dateFmt:'yyyy',minDate:'2009',maxDate:'%y'})" />
		<input id="query_date_month" type="text" style="width: 90%" class="Wdate"
					onFocus="WdatePicker({dateFmt:'yyyy-MM',minDate:'2009��01��',maxDate:'%y��%M��'})" />
		<span id="query_date_day" style="display: none;">
		    <input  id="query_date_day_start" type="text" class="Wdate" onFocus="var endTimeInput=$dp.$('query_date_day_end');WdatePicker({onpicked:function(){endTimeInput.focus();},minDate:'2009-01-01',maxDate:'%y-%M-%d'})" />
			�� <input id="query_date_day_end" type="text" class="Wdate" onFocus="WdatePicker({minDate:'2009-01-01',maxDate:'%y-%M-%d'})" /></span>
	    </td></tr>
	    </table>
       </td>
       </tr>
 
  </freeze:block>
<br/>
<div align="center">
  <div align="right" style="margin-right: 30px;"><a href="javascript:void('');" onclick="doQuery('chart');"><span id="chartId" class="selectMenu">ͼ��</span></a>|
 <a  href="javascript:void('');" onclick="doQuery('table');"><span id="tableId">���</span></a>
 </div>
 <iframe id='showPic' scrolling='no' frameborder='0' align="middle" 
     width='95%' style="display:none;height:480px;"></iframe>
</div>
<input type="hidden" id="chart_type" value="chart"/>
</freeze:form>
</freeze:body>
</freeze:html>
