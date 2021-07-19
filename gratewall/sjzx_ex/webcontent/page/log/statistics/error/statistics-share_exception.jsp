<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ taglib uri="/WEB-INF/gwssi.tld" prefix="gwssi" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<jsp:include page="/page/share/common/top_datepicker.html"></jsp:include>
<script type="text/javascript" type="text/javascript" src="<%=request.getContextPath()%>/script/My97DatePicker/WdatePicker.js"></script>
<title>查询系统日志列表</title>
<style type="text/css">
.selectMenu {
	padding: 2px;
	background-color: #2B92E8;
	COLOR: #fff;
}
</style>

</head>

<script type="text/javascript">

function dateChoose()
{
	//alert("datechoose");
	var data_select=getFormFieldValue('select-key:query_index');
	var query_date='';
	if(!data_select){
		alert("请先选择统计粒度!");
		return ;
	}
	if(data_select=='day'){
		query_date=$('#query_date_day_start').val()+","+$('#query_date_day_end').val();
		if($('#query_date_day_start').val()=='' && $('#query_date_day_end').val()==''){
			alert("请选择起始日期");
			return;
		}
		
	}else if(data_select=='month'){
		query_date=$('#query_date_month').val();
		 if($('#query_date_month').val()=='' ){
			alert("请选择月份");
			return;
		} 
		
	}else if(data_select=='year'){
		query_date=$('#query_date_year').val();
		if($('#query_date_year').val()=='' ){
			alert("请选择年份");
			return;
		} 
		
	}
	
	//alert(query_date);
	document.getElementById('select-key:query_date').value=query_date;
	document.getElementById('form_choose').submit();
	//func_show_selectPic(true);
}
/* function func_show_selectPic(flag)
{
	//alert("1");
	var show_type=document.getElementById('chart_type').value;
	//alert("show_type="+show_type);
	var page = new pageDefine( "/txn6020007.do", "查看图片");
	page.addParameter( "select-key:service_targets_id", "select-key:service_targets_id" );
	page.addParameter( "select-key:service_targets_type", "select-key:service_targets_type" );
	page.addParameter( "select-key:query_index", "select-key:query_index" );
	page.addValue(show_type, "select-key:show_type" );
	page.addValue($("#show_num").val(), "select-key:show_num" );
	var data_select=getFormFieldValue('select-key:query_index');
	
	if(flag){
		if(data_select=='day'){
			if($('#query_date_day_start').val()=='' && $('#query_date_day_end').val()==''){
				alert("请选择起始日期");
				return;
			}
			//query_date=$('#query_date_day_start').val()+","+$('#query_date_day_end').val();
		}else{
			if($('#query_date_'+data_select).val()==''){
				if(data_select=='year'){
					alert("请选择年份");
				}else{
					alert("请选择月份");
				}
				return;
			}
			//query_date=$('#query_date_'+data_select).val();
		}
	}
	page.addParameter("select-key:query_date", "select-key:query_date" );
    var win = window.frames('showPic');
  	document.getElementById("showPic").style.display = "block";	
	page.goPage( null, win );
}
 */

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var t3html='<div class="pack-up" id="t3"><ul class="pack-list" id="t3_ul"  >'
  		+'<li class="disabled" id="query_date" ><a>选择时间</a></li>'
  		+'<li  ><table style="width: 100%"><tr><td>'
  		+'<input id="query_date_year" style="display: none;width:160px" type="text" class="Wdate"  onFocus="WdatePicker({onpicked:function(){dateChoose();},dateFmt:\'yyyy\',minDate:\'2009\',maxDate:\'%y\'})"/>'
        +'<input id="query_date_month" type="text" style="width:160px" class="Wdate"  onFocus="var endTimeInput=$dp.$(\'endMonth\');WdatePicker({onpicked:function(){dateChoose();},dateFmt:\'yyyy-MM\',minDate:\'2009年01月\',maxDate:\'%y年%M月\'})"/>'
        +'<span id="query_date_day" style="display: none;">'
        +'<input id="query_date_day_start" style="width:120px"  type="text" class="Wdate" onFocus="var endTimeInput=$dp.$(\'endDay\');WdatePicker({onpicked:function(){dateChoose();},minDate:\'2009-01-01\',maxDate:\'%y-%M-%d\'})"/> 至'
        +'<input id="query_date_day_end" style="width:120px" type="text" class="Wdate"  onFocus="WdatePicker({onpicked:function(){dateChoose();},minDate:\'2009-01-01\',maxDate:\'%y-%M-%d\'})"/>'
        +'</span></td></tr></table></li></ul></div>';
	$('#t1').after(t3html);//添加时间选择框
	var t2html='<div class="pack-up" id="t2" >'
		+'<ul class="pack-list" id="t2_ul" ><li class="disabled" id="query_index"><a>统计粒度</a></li>'
		+'<li ><a id="year" href="javascript:;" onclick="toChageDate(\'year\')" >年 </a></li>'
		+'<li class="active" ><a id="month" href="javascript:;" onclick="toChageDate(\'month\')" >月 </a></li>'
		+'<li ><a id="day" href="javascript:;" onclick="toChageDate(\'day\')" >日<span /></a></li></ul></div>';
	$('#t1').after(t2html);//添加粒度选择框	
	
	toChageDate('month');
	
	doQuery("chart");
}

function toChageDate(data_select){
	
	//var data_select=getFormFieldValue('select-key:query_index');
	var q_date=document.getElementById('select-key:query_date').value;
	$('#t2').find('.active').removeClass('active');
	//alert(data_select);
	$('#query_date_year').hide();
	$('#query_date_month').hide();
	$('#query_date_day').hide();
	if(data_select=='year'){
		$('#query_date_year').val("");
		$('#t2').find('.pack-list li:eq(1)').addClass('active');
		document.getElementById('select-key:query_index').value='year';
		document.getElementById('select-key:query_date').value="";
		$('#query_date_year').show();
	}else if(data_select=='month'){
		if(!q_date){
			var date = new Date();
			var year = date.getYear();
			var month = date.getMonth();
			
	    	if(month<10){
	    		month="0"+month;
	    	}
	    	var current_Date=year+"-"+month;
	    	
			$("#query_date_month").val(current_Date);
			
			document.getElementById('select-key:query_date').value=current_Date;
		}else{
			
			$('#query_date_month').val("");
			document.getElementById('select-key:query_date').value="";
		}	
		document.getElementById('select-key:query_index').value='month';
		$('#t2').find('.pack-list li:eq(2)').addClass('active');
		$('#query_date_month').show();
	}else if(data_select=='day'){
		//var date_s_e=q_date.split(',');
		$("#query_date_day_start").val("");
		$("#query_date_day_end").val("");
		document.getElementById('select-key:query_index').value='day';
		document.getElementById('select-key:query_date').value="";
		$('#t2').find('.pack-list li:eq(3)').addClass('active');
		$('#query_date_day').show();
	}
		
	
}

function doQuery(type){
	//alert("doQuery");
	document.getElementById('select-key:show_type').value=type;
	document.getElementById('chart_type').value=type;
	document.getElementById('chartId').className = '';
	document.getElementById('tableId').className = '';
	document.getElementById(type + "Id").className = 'selectMenu';
	document.getElementById('form_choose').submit();
	//func_show_selectPic(true);
}



_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="服务异常情况统计"/>

<freeze:errors/>
<gwssi:panel action="txn6020007" target="showPic" parts="t1" styleClass="wrapper" >
   <gwssi:cell id="t1" name="服务对象" key="service_targets_type,service_targets_id"   isGroup="true" data="svrTarget" pop="true"  maxsize="10"  />
  

   <input type='hidden' name='select-key:query_date' id='select-key:query_date' value='' />
   <input type='hidden' name='select-key:query_index' id='select-key:query_index' value='' />
   <input type='hidden' name='select-key:show_type' id='select-key:show_type' value='' />
   
  </gwssi:panel>

<freeze:form action="/txn6020007">
	<freeze:frame property="select-key" >
     
  	  </freeze:frame>
	
<br/>
<div align="center">
 <div align="right" style="margin-right: 30px;"><a href="javascript:void('');" onclick="doQuery('chart');"><span id="chartId" class="selectMenu">图表</span></a>|
 <a  href="javascript:void('');" onclick="doQuery('table');"><span id="tableId">表格</span></a>
 显示条数
 <select id="show_num" onchange="func_show_selectPic();">
   <option value="10">10</option>
   <option value="20">20</option>
   <option value="30">30</option>
   <option value="50">50</option>
   <option value="100">100</option>
   <option value="1000000">全部</option>
 </select></div>
  <iframe id='showPic' name='showPic' scrolling='no' frameborder='0' align="middle" 
     width='95%' style="height:400px;"></iframe>
</div>
<input type="hidden" id="chart_type" value="chart"/>
<input type="hidden" id="query_date" value="chart"/>
</freeze:form>
</freeze:body>
</freeze:html>
