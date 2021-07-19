<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="500" height="300">
<head>
<title>修改任务调度信息</title>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/uploadfile.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery-ui.custom.min.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery.ui.timepicker.js"></script>
	<link href="<%=request.getContextPath()%>/script/lib/jquery.ui.timepicker.css" rel="stylesheet" type="text/css">
	<link href="<%=request.getContextPath()%>/script/css/cupertino/jquery.ui.all.css" rel="stylesheet" type="text/css">
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	var time=document.getElementById("stime").value;
	
	setFormFieldValue("record:jhrw_sj",time);//执行时间
	if(document.getElementById("everyWeek").style.display == "block"){
	
		var weekDay=document.getElementsByName("weekDay");
		var week="";
		if(weekDay!=null&&weekDay.length>0){
			for(var i=0;i<weekDay.length;i++){
			   if(weekDay[i].checked==true){
			    // week+=weekDay[i].nextSibling.nodeValue+",";
			    week+=weekDay[i].value+",";
			   }
			}
		}
		if(week!=null&&week!=""){
		   week=week.substring(0,week.length-1);
		   setFormFieldValue("record:jhrw_zt",week);//周天
		}else{
		   alert("至少选择一个周天");
		   return false;
		}
		
		document.getElementById("record:jhrwzx_cs").value="";//次数
		document.getElementById("record:jhrw_rq").value="";//日期
	}
	if(document.getElementById("everyMonth").style.display == "block"){
		var day=document.getElementById("day").value;
		
		if(day!=null&&day!=""){
		  setFormFieldValue("record:jhrw_rq",day);//日期
		}else{
		   alert("至少选择一个日期");
		   return false;
		}
		document.getElementById("record:jhrwzx_cs").value="";//次数
		document.getElementById("record:jhrw_zt").value="";//周天
	}
	if(document.getElementById("zxcs").style.display == "block"){
		var cs=document.getElementById("jhzxcs").value;
		
		if(cs!=null&&cs!=""){
		  setFormFieldValue("record:jhrwzx_cs",cs);//次数
		}
		document.getElementById("record:jhrw_zt").value="";//周天
		document.getElementById("record:jhrw_rq").value="";//日期
	}
	saveAndExit( '', '保存任务调度表' );	// /txn30801001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn30801001.do
}
// 改变任务调度类型
function changeType()
{
	var taskType=getFormFieldValue( "record:jhrw_lx");
	var time=getFormFieldValue("record:jhrw_sj");//执行时间
	document.getElementById("stime").value=time;
	
	if(taskType!=null&&(taskType=="01")){
	  var taskType=getFormFieldValue( "record:jhrw_lx");
	  document.getElementById("everyWeek").style.display = "none"; 
	  document.getElementById("everyMonth").style.display = "none"; 
	  document.getElementById("zxcs").style.display = "block"; 
	  var cs=getFormFieldValue("record:jhrwzx_cs");//执行次数
	  document.getElementById("jhzxcs").value=cs;
	}else if(taskType!=null&&taskType=="02"){
	  document.getElementById("everyWeek").style.display = "block"; 
	  document.getElementById("everyMonth").style.display = "none"; 
	  document.getElementById("zxcs").style.display = "none"; 
	  
	  var cs=getFormFieldValue("record:jhrw_zt");//周天
	  var week=cs.split(",");
	  if(week!=null&&week!=""){
	      for(var i=0;i<week.length;i++){
	         if(week[i]!=null&&week[i]=="1"){
	            document.getElementsByName("weekDay")[0].checked="true";
	         }else if(week[i]!=null&&week[i]=="2"){
	         	document.getElementsByName("weekDay")[1].checked="true";
	         }else if(week[i]!=null&&week[i]=="3"){
	         	document.getElementsByName("weekDay")[2].checked="true";
	         }else if(week[i]!=null&&week[i]=="4"){
	         	document.getElementsByName("weekDay")[3].checked="true";
	         }else if(week[i]!=null&&week[i]=="5"){
	         	document.getElementsByName("weekDay")[4].checked="true";
	         }else if(week[i]!=null&&week[i]=="6"){
	         	document.getElementsByName("weekDay")[5].checked="true";
	         }else if(week[i]!=null&&week[i]=="7"){
	         	document.getElementsByName("weekDay")[6].checked="true";
	         }
	      }
	  }
	  
	}else if(taskType!=null&&taskType=="03"){
	  document.getElementById("everyWeek").style.display = "none"; 
	  document.getElementById("everyMonth").style.display = "block"; 
	  document.getElementById("zxcs").style.display = "none"; 
	}
}
function initWeek() {
 var weeks = ['x', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日'];
 var str="";
  for (var j = 1; j < 8; j++) {
      str += " <input type=checkbox  name='weekDay' value='" + j + "' " //+ds
      +
      "/> " + weeks[j] + "";
  }
  $('#weekTd').html(str);
}
function initDay() {
  var str = "<select name='day' id='day' style='width: 53px'>"; 
  for (var j = 1; j < 32; j++) {
      str += " <option  value= '" + j + "'> " + j + "</option>";
  }
  $('#dayTd').html(str);
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	  $('#stime').timepicker({
	    showLeadingZero: false,
	    showCloseButton: true,
	    showNowButton: true
  	  });
	  $("#main_record tr").each(function(){
	  	$(this).find("td:first").css("white-space", "nowrap");
	  });
	 initWeek();
	 initDay();
	 changeType();
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改任务调度信息"/>
<freeze:errors/>

<freeze:form action="/txn30801002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="jhrw_id" caption="计划任务ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改任务调度信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回"hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="jhrw_id" caption="计划任务ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
   
      <freeze:select property="jhrw_lx" caption="计划任务类型" colspan="3"  notnull="true" onchange="changeType()" valueset="资源管理_计划任务类型" style="width:95%"/>
      <freeze:hidden property="jhrw_sj" caption="计划任务时间" colspan="3"  datatype="string" maxlength="11" style="width:95%"/>
      <tr>
		 <td height="32" align="right">计划任务时间：</td>
		 <td nowrap  align="left" colspan=""><input id="stime" readonly="true" type="text" value='00:00' style="width:30%" /></td>
	  </tr>
	  <tr id="everyWeek" style="display:none">
		   <td nowrap  align="left" width="150px">周天：</td>
		   <td colspan=3 id="weekTd" ></td>
	  </tr>
	   <tr id="everyMonth" style="display:none">
		   <td nowrap  align="left" width="150px">月天：</td>
		   <td colspan=3 id="dayTd" ></td>
	  </tr>
	  <tr id="zxcs" style="display:none">
		   <td nowrap  align="left" width="150px">计划任务执行次数：</td>
		   <td colspan=3 ><input type="text" id="jhzxcs" name="jhzxcs" style="width:30%"></td>
	  </tr>
	  <freeze:hidden  property="jhrw_zt" caption="计划任务周天"   datatype="string" maxlength="100" style="width:95%"/>
     
      <freeze:hidden  property="jhrw_rq" caption="计划任务日期"  style="width:95%"/>
      
      <freeze:hidden  property="jhrw_zq" caption="计划任务周期"   datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden  property="jhrwzx_cs" caption="计划任务执行次数"  datatype="int" maxlength="3" />
      <freeze:hidden property="jhrwzx_zt" caption="计划任务执行状态" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="jhrwjs_sj" caption="计划任务结束时间" datatype="string" maxlength="7" style="width:95%"/>
      <freeze:hidden property="jhrwsczx_sj" caption="计划任务上次执行时间" datatype="string" maxlength="11" style="width:95%"/>
      
      <freeze:hidden property="creator_id" caption="创建人" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="创建时间" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="修改人" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="修改时间" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="yx_bj" caption="有效标记" datatype="string" maxlength="1" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
