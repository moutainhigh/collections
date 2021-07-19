<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="com.gwssi.dw.runmgr.services.vo.VoSysCltUser"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<freeze:html>
<head>
	<title>策略配置</title>
</head>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/script/lib/jquery.js"></script>
<%
	DataBus context = (DataBus) request.getAttribute("freeze-databus");
	DataBus db = context.getRecord("record");
	String sys_clt_user_id = db
			.getValue(VoSysCltUser.ITEM_SYS_CLT_USER_ID);
	String strategy = db.getValue(VoSysCltUser.ITEM_STRATEGY);
	String strategydesc = db.getValue(VoSysCltUser.ITEM_STRATEGYDESC);
	String oldstate = db.getValue(VoSysCltUser.ITEM_OLDSTATE);
	String state = db.getValue(VoSysCltUser.ITEM_STATE);
	String hours = db.getValue(VoSysCltUser.ITEM_HOURS);
	String minutes = db.getValue(VoSysCltUser.ITEM_MINUTES);
	String seconds = db.getValue(VoSysCltUser.ITEM_SECONDS);
	String startdate = db.getValue(VoSysCltUser.ITEM_STARTDATE);
	String enddate = db.getValue(VoSysCltUser.ITEM_ENDDATE);
%>


<script language="javascript">
var strategydesc = "<%=strategydesc%>";
var strategy = "<%=strategy%>";
var sys_clt_user_id = "<%=sys_clt_user_id%>";
var hours = "<%=hours%>";
var minutes = "<%=minutes%>";
var seconds = "<%=seconds%>";
var oldstate = "<%=oldstate%>";
var state = "<%=state%>";
var startdate = "<%=startdate%>";
var enddate = "<%=enddate%>";

// 保 存
function func_record_saveAndExit()
{
	var strategy = $("input[@type='radio'][@name='record:strategy'][@checked]").val();
	var strategydesc = $("input[@type='checkbox'][@name='record:strategydesc1'][@checked]");

	if(strategy=="1"){
	   if(strategydesc==null||strategydesc.length==0){
	       alert("请选择每周的执行策略!");
	       return;
	   }else{
	       var temp = "";
	       for(var i=0;i<strategydesc.length;i++){
	         if(temp!="")
	            temp += ",";
	         temp += strategydesc[i].value;
	       }
	       $("input[@name='record:strategydesc'][@type='hidden']").attr("value",temp);
	   }
	}else{
	   $("input[@name='record:strategydesc'][@type='hidden']").attr("value","");
	}
	var start = $("input[@type='text'][@name='record:startdate']").val();
	var end = $("input[@type='text'][@name='record:enddate']").val();
	var nowdate = getCurrentTime();

	if(end!=null&&end!=""&&start>=end){
	   alert("结束日期必须大于开始日期!");
	   return;
	}
	if(end!=null&&end!=""&&end<=nowdate){
	   alert("结束日期必须大于当前日期!");
	   return;
	}

	saveAndExit( '保存策略成功!', '保存策略失败!' );	
}

function getCurrentTime()
{
  var time = new Date();
  var year = time.getYear();
  var month = time.getMonth() + 1;
  if (month.toString().length == 1)
  {
  month = "0" + month;
  }
  var date = time.getDate();
  if (date.toString().length == 1)
  {
  date = "0" + date;
  }
  var currentDate = year + '-' + month + '-' + date; 
  return currentDate;
}

// 返 回
function func_record_goBack()
{
	goBack();
}
function initStrategy(){    
		if(strategydesc!=null){
		  var desc = strategydesc.split(',');
		  for(var i=0;desc!=null&&i<desc.length;i++){
		      $("input[@name='record:strategydesc1'][@type='checkbox'][@value='"+ desc[i] +"']").attr("checked",true);
		  }
		}
}
// 返 回
function changeStrategy(str)
{
    if(str=="0"){
	    $("input[@name='record:strategydesc1'][@type='checkbox']").attr("checked",false);
	    $("input[@name='record:strategydesc1'][@type='checkbox']").attr("disabled","disabled");
	}
	else{
	    $("input[@name='record:strategydesc1'][@type='checkbox']").removeAttr("disabled");
	    initStrategy();
	}
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
//  $("input[@name='record:sys_clt_user_id'][@type='hidden']").attr("value",sys_clt_user_id);
  if(strategy=="0"){
		$("input[@name='record:strategy'][@type='radio'][@value='0']").click();
		$("input[@name='record:strategy'][@type='radio'][@value='0']").attr("checked","checked");  
  }else{
		$("input[@name='record:strategy'][@type='radio'][@value='1']").click();
		$("input[@name='record:strategy'][@type='radio'][@value='1']").attr("checked","checked");
        initStrategy();
  }
  $("select[@name='record:hours'] > option[@value='"+ hours +"']").attr("selected", "selected");
  $("select[@name='record:minutes'] > option[@value='"+ minutes +"']").attr("selected", "selected"); 
  $("select[@name='record:seconds'] > option[@value='"+ seconds +"']").attr("selected", "selected"); 
  
  if(state=="0"){
		$("input[@name='record:state'][@type='radio'][@value='0']").click();
		$("input[@name='record:state'][@type='radio'][@value='0']").attr("checked","checked");  
  }else{
		$("input[@name='record:state'][@type='radio'][@value='1']").click();
		$("input[@name='record:state'][@type='radio'][@value='1']").attr("checked","checked");  
  }  
  
  $("input[@name='record:oldstate'][@type='hidden']").attr("value",oldstate);

  if(startdate!=null&&startdate!="")
      $("input[@name='record:startdate'][@type='text']").attr("value",startdate);
  if(enddate!=null&&enddate!="")
      $("input[@name='record:enddate'][@type='text']").attr("value",enddate);
}


_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
	<freeze:errors />
	<br>
	<br>
	<freeze:form action="/txn50211003">
      <freeze:frame property="record" width="95%">
        <freeze:hidden property="sys_clt_user_id" caption="ID" style="width:95%"/>
        <freeze:hidden property="name" caption="name" style="width:95%"/>
        <freeze:hidden property="jobname" caption="jobname" style="width:95%"/>
        <freeze:hidden property="groupname" caption="groupname" style="width:95%"/>
        <freeze:hidden property="classname" caption="classname" style="width:95%"/>
        <freeze:hidden property="oldstate" caption="oldstate" style="width:95%"/>
        <freeze:hidden property="user_type" caption="user_type" style="width:95%"/>
      </freeze:frame>
		<table width="95%" height="204" border="1" align="center"
			cellspacing="0" class="TableFormStyle" id="chaxun">
			<tr>
				<td height="26" colspan="4" class="title-row" align="left">
					采集策略配置
					<input name="record:strategydesc" type="hidden" />
				</td>
			</tr>
			<tr align="center">
				<td width="15%" height="30" align="right">
					采集周期：
				</td>
				<td width="35%" height="30" align="left">
					<div>
						<input type="radio" name="record:strategy" value="0"
							onclick="changeStrategy('0')">
						每天
					</div>
					<div>
						<input type="radio" name="record:strategy" value="1"
							onclick="changeStrategy('1')">
						每周
					</div>
				</td>
				<td width="20%" height="30" align="left" colspan="2">
					<div>
						<input type="checkbox" name="record:strategydesc1" value="MON" />星期一&nbsp;
						<input type="checkbox" name="record:strategydesc1" value="TUE" />星期二&nbsp;
						<input type="checkbox" name="record:strategydesc1" value="WED" />星期三&nbsp;
						<input type="checkbox" name="record:strategydesc1" value="THU" />星期四
						<input type="checkbox" name="record:strategydesc1" value="FRI" />星期五&nbsp;
						<input type="checkbox" name="record:strategydesc1" value="SAT" />星期六&nbsp;
						<input type="checkbox" name="record:strategydesc1" value="SUN" />星期日
					</div>
				</td>
			</tr>
			<tr align="center">
				<td width="15%" height="30" align="right">
					执行时间：
				</td>
				<td width="35%" height="30" align="left">
					<div id="createDate" class="entNameContentDiv" align="left">
						<select name="record:hours" size="1">
							<option value="00">
								00
							</option>
							<option value="01">
								01
							</option>
							<option value="02">
								02
							</option>
							<option value="03">
								03
							</option>
							<option value="04">
								04
							</option>
							<option value="05">
								05
							</option>
							<option value="06">
								06
							</option>
							<option value="07">
								07
							</option>
							<option value="08">
								08
							</option>
							<option value="09">
								09
							</option>
							<option value="10">
								10
							</option>
							<option value="11">
								11
							</option>
							<option value="12">
								12
							</option>
							<option value="13">
								13
							</option>
							<option value="14">
								14
							</option>
							<option value="15">
								15
							</option>
							<option value="16">
								16
							</option>
							<option value="17">
								17
							</option>
							<option value="18">
								18
							</option>
							<option value="19">
								19
							</option>
							<option value="20">
								20
							</option>
							<option value="21">
								21
							</option>
							<option value="22">
								22
							</option>
							<option value="23">
								23
							</option>
						</select>点
						<select name="record:minutes" size="1">
							<option value="00">
								00
							</option>
							<option value="01">
								01
							</option>
							<option value="02">
								02
							</option>
							<option value="03">
								03
							</option>
							<option value="04">
								04
							</option>
							<option value="05">
								05
							</option>
							<option value="06">
								06
							</option>
							<option value="07">
								07
							</option>
							<option value="08">
								08
							</option>
							<option value="09">
								09
							</option>
							<option value="10">
								10
							</option>
							<option value="11">
								11
							</option>
							<option value="12">
								12
							</option>
							<option value="13">
								13
							</option>
							<option value="14">
								14
							</option>
							<option value="15">
								15
							</option>
							<option value="16">
								16
							</option>
							<option value="17">
								17
							</option>
							<option value="18">
								18
							</option>
							<option value="19">
								19
							</option>
							<option value="20">
								20
							</option>
							<option value="21">
								21
							</option>
							<option value="22">
								22
							</option>
							<option value="23">
								23
							</option>
							<option value="24">
								24
							</option>
							<option value="25">
								25
							</option>
							<option value="26">
								26
							</option>
							<option value="27">
								27
							</option>
							<option value="28">
								28
							</option>
							<option value="29">
								29
							</option>
							<option value="30">
								30
							</option>
							<option value="31">
								31
							</option>
							<option value="32">
								32
							</option>
							<option value="33">
								33
							</option>
							<option value="34">
								34
							</option>
							<option value="35">
								35
							</option>
							<option value="36">
								36
							</option>
							<option value="37">
								37
							</option>
							<option value="38">
								38
							</option>
							<option value="39">
								39
							</option>
							<option value="40">
								40
							</option>
							<option value="41">
								41
							</option>
							<option value="42">
								42
							</option>
							<option value="43">
								43
							</option>
							<option value="44">
								44
							</option>
							<option value="45">
								45
							</option>
							<option value="46">
								46
							</option>
							<option value="47">
								47
							</option>
							<option value="48">
								48
							</option>
							<option value="49">
								49
							</option>
							<option value="50">
								50
							</option>
							<option value="51">
								51
							</option>
							<option value="52">
								52
							</option>
							<option value="53">
								53
							</option>
							<option value="54">
								54
							</option>
							<option value="55">
								55
							</option>
							<option value="56">
								56
							</option>
							<option value="57">
								57
							</option>
							<option value="58">
								58
							</option>
							<option value="59">
								59
							</option>
						</select>分
						<select name="record:seconds" size="1">
							<option value="00">
								00
							</option>
							<option value="01">
								01
							</option>
							<option value="02">
								02
							</option>
							<option value="03">
								03
							</option>
							<option value="04">
								04
							</option>
							<option value="05">
								05
							</option>
							<option value="06">
								06
							</option>
							<option value="07">
								07
							</option>
							<option value="08">
								08
							</option>
							<option value="09">
								09
							</option>
							<option value="10">
								10
							</option>
							<option value="11">
								11
							</option>
							<option value="12">
								12
							</option>
							<option value="13">
								13
							</option>
							<option value="14">
								14
							</option>
							<option value="15">
								15
							</option>
							<option value="16">
								16
							</option>
							<option value="17">
								17
							</option>
							<option value="18">
								18
							</option>
							<option value="19">
								19
							</option>
							<option value="20">
								20
							</option>
							<option value="21">
								21
							</option>
							<option value="22">
								22
							</option>
							<option value="23">
								23
							</option>
							<option value="24">
								24
							</option>
							<option value="25">
								25
							</option>
							<option value="26">
								26
							</option>
							<option value="27">
								27
							</option>
							<option value="28">
								28
							</option>
							<option value="29">
								29
							</option>
							<option value="30">
								30
							</option>
							<option value="31">
								31
							</option>
							<option value="32">
								32
							</option>
							<option value="33">
								33
							</option>
							<option value="34">
								34
							</option>
							<option value="35">
								35
							</option>
							<option value="36">
								36
							</option>
							<option value="37">
								37
							</option>
							<option value="38">
								38
							</option>
							<option value="39">
								39
							</option>
							<option value="40">
								40
							</option>
							<option value="41">
								41
							</option>
							<option value="42">
								42
							</option>
							<option value="43">
								43
							</option>
							<option value="44">
								44
							</option>
							<option value="45">
								45
							</option>
							<option value="46">
								46
							</option>
							<option value="47">
								47
							</option>
							<option value="48">
								48
							</option>
							<option value="49">
								49
							</option>
							<option value="50">
								50
							</option>
							<option value="51">
								51
							</option>
							<option value="52">
								52
							</option>
							<option value="53">
								53
							</option>
							<option value="54">
								54
							</option>
							<option value="55">
								55
							</option>
							<option value="56">
								56
							</option>
							<option value="57">
								57
							</option>
							<option value="58">
								58
							</option>
							<option value="59">
								59
							</option>
						</select>秒
					</div>
				</td>
				<td width="15%" height="30" align="right">
					状态：
				</td>
				<td width="35%" height="30" align="left">
					<div id="createBy" class="entRegNoContentDiv" align="left">
						<input name="record:state" type="radio" value="0" />
						启用
						<input name="record:state" type="radio" value="1" />
						停用
					</div>
				</td>
			</tr>
			<tr align="center">
				<td width="15%" height="30" align="right">
					开始日期：
				</td>
				<td width="35%" height="30" align="left">
					<div id="table_name" class="entRegNoContentDiv" align="left">
						<freeze:datebox caption="开始时间" property="record:startdate"
							numberformat="1" />
					</div>
				</td>
				<td width="15%" height="30" align="right">
					结束日期：
				</td>
				<td width="35%" height="30" align="left">
					<div id="maxNum" class="entRegNoContentDiv" align="left">
						<freeze:datebox caption="结束时间" property="record:enddate"
							numberformat="1" />
					</div>
				</td>
			</tr>


		</table>

		<table width="95%" border="0" align="center" cellspacing="0"
			class="TableFormStyle">
			<tr align="center">
				<td height="30" align="right">
					<input type="button" value="保存" class="menu" style="width:60px;"
						onclick="func_record_saveAndExit()" />
				</td>
				<td height="30" align="left">
					<input type="button" value="返回" class="menu" style="width:60px;"
						onclick="func_record_goBack();" />
				</td>
			</tr>
		</table>

	</freeze:form>
</freeze:body>
</freeze:html>
