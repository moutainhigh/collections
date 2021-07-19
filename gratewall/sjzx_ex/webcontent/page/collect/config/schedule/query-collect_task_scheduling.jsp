<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询任务调度列表</title>
</head>

<script language="javascript">

// 增加任务调度
function func_record_addRecord()
{
	var page = new pageDefine( "insert-collect_task_scheduling.jsp", "增加任务调度", "modal");
	page.addRecord();
}

// 修改任务调度
function func_record_updateRecord(idx)
{
	var page = new pageDefine( "/txn30801004.do", "修改任务调度", "modal" );
	page.addValue(idx,"primary-key:jhrw_id");
	page.updateRecord();
}

// 删除任务调度
function func_record_deleteRecord(idx)
{
	var page = new pageDefine( "/txn30801005.do", "删除任务调度" );
	page.addValue(idx,"primary-key:jhrw_id");
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var ids = getFormAllFieldValues("record:jhrw_id");
	var week=getFormAllFieldValues("record:jhrw_zt");
	var weekday= new Array();
	var name="";
	for(var i=0; i<ids.length; i++){
	   name="";
	   var htm='<a href="#" title="修改" onclick="func_record_updateRecord(\''+ids[i]+'\');"><div class="edit"></div></a>&nbsp;';
	   htm+='<a href="#" title="删除" onclick="func_record_deleteRecord(\''+ids[i]+'\');"><div class="delete"></div></a>';
	   document.getElementsByName("span_record:oper")[i].innerHTML +=htm;
	   
	   if(week[i]!=null&&week[i]!=""){
	  
	   weekday=week[i].split(",");
	   for(var j=0;j<weekday.length;j++){
	      if(weekday[j]=="1"){
	         name+="星期一"+",";
	      }else if(weekday[j]=="2"){
	         name+="星期二"+",";
	      }else if(weekday[j]=="3"){
	         name+="星期三"+",";
	      }else if(weekday[j]=="4"){
	         name+="星期四"+",";
	      }else if(weekday[j]=="5"){
	         name+="星期五"+",";
	      }else if(weekday[j]=="6"){
	         name+="星期六"+",";
	      }else if(weekday[j]=="7"){
	         name+="星期日"+",";
	      }
	   }
	    name=name.substring(0,name.length-1);
	   document.getElementsByName("span_record:jhrw_zt")[i].innerHTML =name;
	   }
	  
	 }
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询任务调度列表"/>
<freeze:errors/>

<freeze:form action="/txn30801001">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="jhrw_mc" caption="计划任务名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:select property="jhrw_lx" caption="计划任务类型"  show="name" valueset="资源管理_计划任务类型"  style="width:95%"/>
  </freeze:block>
  <br/>
  <freeze:grid property="record" caption="查询任务调度列表" keylist="jhrw_id" width="95%" multiselect="false" checkbox="false" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="增加任务调度" txncode="30801003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
     
      <freeze:hidden property="jhrw_id" caption="计划任务ID"  />
      <freeze:hidden property="jhrw_mc" caption="计划任务名称" style="color:#0000FF; text-decoration: underline;width:20%" onclick="func_record_viewRecord1();"/>
     
      <freeze:hidden property="jhrwzx_zt" caption="计划任务执行状态"  />
      <freeze:cell property="jhrw_lx" caption="计划任务类型" valueset="资源管理_计划任务类型" style="width:10%" />
      <freeze:cell property="jhrw_sj" caption="计划任务时间" style="width:10%" />
      <freeze:cell property="jhrw_rq" caption="计划任务日期" style="width:10%" />
      <freeze:hidden property="jhrw_zq" caption="计划任务周期"  />
      <freeze:cell property="jhrw_zt" caption="计划任务周天" style="width:25%" />
       <freeze:cell property="jhrwzx_cs" caption="计划任务执行次数" style="width:15%" />
      <freeze:cell property="oper" caption="操作" style="width:10%" />
      <freeze:hidden property="jhrwjs_sj" caption="计划任务结束时间"  />
      <freeze:hidden property="jhrwsczx_sj" caption="计划任务上次执行时间"  />
     
      <freeze:hidden property="creator_id" caption="创建人"  />
      <freeze:hidden property="created_time" caption="创建时间"  />
      <freeze:hidden property="last_modify_id" caption="修改人"  />
      <freeze:hidden property="last_modify_time" caption="修改时间"  />
      <freeze:hidden property="yx_bj" caption="有效标记"  />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
