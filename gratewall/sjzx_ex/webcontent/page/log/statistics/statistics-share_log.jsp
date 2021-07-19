<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/My97DatePicker/WdatePicker.js"></script>
<title>查询系统日志列表</title>
<style type="text/css">
.selectMenu {
	padding: 2px;
	background-color: #2B92E8;
	COLOR: #fff;
}
</style>
</head>

<script language="javascript">

function changeTimezone()
{

      $("#startTime").val("");//设置已选的数值为空
      $("#endTime").val("");
      
      var checkValue=$("#statistical_granularity").find("option:selected").val();
      //alert(checkValue);
      
      $(".timezone").hide();
      switch (checkValue) {
	  case "00": $("#Day_zone").show();
	    break;
	  case "01": $("#Week_zone").show();
	    break;
	  case "02": $("#Month_zone").show();
	    break;
	  case "05": $("#Year_zone").show();
	    break;
	  default: $("#Day_zone").show();
		}
      
}
//TODO 按钮还要替换
function _menuClick(id)
{
	func_show_selectPic(true);
}
function func_show_selectPic(flag)
{
    var show_type=document.getElementById('chart_type').value;
	var page = new pageDefine( "/txn6020002.do", "查看图片");
	var chk_value =[];  
	$('input[name="query_index"]:checked').each(function(){  
	chk_value.push($(this).val());  
	});  
	page.addValue( chk_value, "select-key:query_index" );
	page.addParameter( "select-key:service_targets", "select-key:service_targets_id" );
	page.addParameter( "statistical_granularity", "select-key:statistical_granularity" );
	var startTime=document.getElementById('startTime').value;
	var endTime=document.getElementById('endTime').value;
	//alert(startTime+"---"+endTime);
	if(flag){
		if(startTime=='' || endTime==''){
			alert("请选择时间区间");
			return;
		}
	}
	page.addParameter( "startTime", "select-key:startTime" );
	page.addParameter( "endTime", "select-key:endTime" );
	page.addValue(show_type, "select-key:show_type" );
    var win = window.frames('showPic');
  	document.getElementById("showPic").style.display = "block";
	page.goPage( null, win );
}
function doQuery(type){
	document.getElementById('chart_type').value=type;
	document.getElementById('chartId').className = '';
	document.getElementById('tableId').className = '';
	document.getElementById(type + "Id").className = 'selectMenu';
	func_show_selectPic(true);
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
    //var myDate = new Date();
   // alert(myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate());
	func_show_selectPic(false);
			$("input[name='form_reset']").click(function(){
						$(".Wdate").val("");
						changeTimezone();
			});
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="服务对象统计"/>
<freeze:errors/>
<freeze:form action="/txn6020001">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
   
	 <freeze:browsebox property="service_targets" caption="服务对象" valueset="资源管理_服务对象名称" show="name"   style="width:39%" colspan="2"/>
      <tr>
      <td width="15%" align="right" class="odd12" id="label:select-key:query_index">
      指标：
      </td>
      <td width="35%" class="odd12" colspan="3">
      <input type="checkbox" id="query_index0" value="共享数据量" name="query_index" checked="checked">共享数据量 
      <input type="checkbox" id="query_index1" value="调用次数" name="query_index" checked="checked">调用次数 
      <input type="checkbox" id="query_index2" value="平均响应时间" name="query_index" >平均响应时间
      </td>
      <!--  
      <tr> 
      <td width="15%" class="odd12"></td>
       <td width="35%" class="odd12"></td>
       
       <option value ="01">周</option>
       -->
      </tr>
      
      
		
		      <tr> 
		       <td width="15%" class="odd12">统计粒度：</td>
		       <td width="35%" class="odd12">
				<select name="select-key:statistical_granularity" id="statistical_granularity" onchange="changeTimezone();" style="width: 95%;" notnull="false" checkFlag="true" readOnly="false" _value="00" fieldCaption="统计粒度" _inited="true" _default="00">
					<option value ="00">日</option>
					
					<option value="02">月</option>
					<option value="05">年</option>
				</select>
		 	   </td>
      
		       <td width="15%" class="odd12">起止时间:</td>
		       <td width="35%" class="odd12">
		        <div id="Day_zone" class="timezone" >
		      	 <input style="width: 100px;" id="startDay" type="text" class="Wdate" onFocus="var endTimeInput=$dp.$('endDay');WdatePicker({onpicked:function(){endTimeInput.focus();},skin:'default',minDate:'2009-01-01',maxDate:'#F{$dp.$D(\'endDay\',{d:-1});}',vel:'startTime'})"/>至
		      	 <input style="width: 100px;" id="endDay" type="text" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startDay\',{d:1});}',maxDate:'%y-%M-%d',vel:'endTime'})"/>
		      	 </div>
		      	 
		      	<div id="Month_zone" class="timezone" style="display:none;" >
		      	 <input style="width: 100px;" id="startMonth" type="text" class="Wdate" onFocus="var endTimeInput=$dp.$('endMonth');WdatePicker({onpicked:function(){endTimeInput.focus();},dateFmt:'yyyy年MM月',minDate:'2009年01月',maxDate:'#F{$dp.$D(\'endMonth\',{M:-1});}',vel:'startTime'})"/>至
     			  <input style="width: 100px;" id="endMonth" type="text" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy年MM月',minDate:'#F{$dp.$D(\'startMonth\',{M:1});}',maxDate:'%y年%M月',vel:'endTime'})"/>
		      	 </div>
		      	
		      	<div id="Year_zone" class="timezone" style="display:none;" >
		      	   <input style="width: 100px;" id="startYear" type="text" class="Wdate" onFocus="var endTimeInput=$dp.$('endYear');WdatePicker({onpicked:function(){endTimeInput.focus();},dateFmt:'yyyy年',minDate:'2009',maxDate:'#F{$dp.$D(\'endYear\',{y:-1});}',vel:'startTime'})"/>至
      				<input style="width: 100px;" id="endYear" type="text" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy年',minDate:'#F{$dp.$D(\'startYear\',{y:1});}',maxDate:'%y',vel:'endTime'})"/>
		      	 </div>
		      	
		      	<div id="Week_zone" class="timezone" style="display:none;" >
		      	  	<input style="width: 100px;" id="startWeek" type="text" class="Wdate" onFocus="var endTimeInput=$dp.$('endWeek');WdatePicker({onpicked:function(){endTimeInput.focus();},minDate:'2009-01-01',maxDate:'%y-%M-%d',isShowWeek:true,onpicked:function() {$dp.$('startTime').value=$dp.cal.getP('W','W');}})"/>至
       				<input style="width: 100px;" id="endWeek" type="text" class="Wdate" onFocus="WdatePicker({minDate:'2009-01-01',maxDate:'%y-%M-%d',isShowWeek:true,onpicked:function() {$dp.$('endTime').value=$dp.cal.getP('W','W');}})"/>
		      	 </div> 
		      	 
		       </td>
   
  </freeze:block>
     <input type="hidden" id="startTime" value=""/>
     <input type="hidden" id="endTime" value=""/>
     <input type="hidden" id="chart_type" value="chart"/>
<br/>
  <div align="center">
   <div align="right" style="margin-right: 30px;"><a href="javascript:void('');" onclick="doQuery('chart');"><span id="chartId" class="selectMenu">图表</span></a>|
 <a  href="javascript:void('');" onclick="doQuery('table');"><span id="tableId">表格</span></a></div>
 <iframe id='showPic' scrolling='no' frameborder='0' align="middle" width='95%' style="display:none;height:480px;"></iframe>
</div>
</freeze:form>
</freeze:body>
</freeze:html>

