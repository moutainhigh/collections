<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.gwssi.dw.runmgr.etl.txn.TxnSysNoticeInfo"%>
<%@page import="java.util.Map"%>
<%@page import="cn.gwssi.common.context.Recordset"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="cn.gwssi.common.context.TxnContext"%>
<freeze:html width="850" height="550">
<head>
<title>错误日志信息</title>
<% 
	
	DataBus db =(DataBus)request.getAttribute("freeze-databus");
	DataBus bus= db.getRecord("select-key");
	String service_targets_name= bus.getValue("service_targets_name");
	String dateFrom=bus.getValue("startTime");
	String dateTo=bus.getValue("endTime");
	String FromTo="";
	
	if(dateFrom!=null&&dateTo!=null&&dateFrom.equals(dateTo)){
		FromTo=dateFrom;
	}else{
		if(StringUtils.isNotBlank(dateFrom)){
			FromTo=dateFrom;
		}
		if(StringUtils.isNotBlank(dateTo)){
			FromTo+=" 至 "+dateTo;
		}else{
			FromTo+="至今";
		}
		
	}
	
 %>
</head>

<script type="text/javascript">

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	document.getElementById('span_staticinfo:service_targets_name').innerHTML="<%=service_targets_name%>";
	document.getElementById('span_staticinfo:dateFromTo').innerHTML="<%=FromTo%>";
	
}


function goBack(){
	
}

_browse.execute( '__userInitPage()' );
</script>

<freeze:body>
<freeze:title caption="错误日志信息"/>
<freeze:errors/>
<div style="text-align: center;">
<freeze:form action="/txn53000217" >

  <freeze:block property="staticinfo"  width="95%">
      <freeze:cell property="service_targets_name" caption="服务对象" ></freeze:cell>
      <freeze:cell property="dateFromTo" caption="时间" align="center"></freeze:cell>
  </freeze:block>
	  <freeze:frame property="select-key" >
     	<freeze:hidden property="type" caption="分类" />
     	<freeze:hidden property="location" caption="节点位置" />
    	<freeze:hidden property="service_targets_id" caption="服务对象id" />
    	<freeze:hidden property="service_targets_name" caption="服务对象名称" />
     	<freeze:hidden property="startTime" caption="开始时间" />
     	<freeze:hidden property="endTime" caption="结束时间" />
     	<freeze:hidden property="service_id" caption="服务id" />
     	<freeze:hidden property="collect_task_id" caption="任务id" />
  	  </freeze:frame>
<freeze:grid property="errinfos" checkbox="false" caption="出错信息列表" keylist="row_id" width="95%" navbar="bottom" fixrow="false">
			<freeze:hidden property="row_id" caption="主键" />
			<freeze:cell property="@rowid" caption="序号" style="width:5%"  align="center"/>
			<freeze:cell property="name" align="center" caption="任务/服务名称"
				style="width:20%" />
			<freeze:cell property="return_codes" caption="错误码" align="center" nowrap="true" />
			<freeze:cell property="errdes" caption="错误描述" align="center"
				style="width:20%" />
			<freeze:cell property="start_time" caption="开始时间" align="center"
				nowrap="true" />
			<freeze:cell property="end_time" caption="结束时间" align="center"
				nowrap="true" />
			
  </freeze:grid>
<%-- 
<table  cellpadding="0" cellspacing="0" width="95%" align="center" style="border-collapse: collapse;">
<tr ><td>
    <div id="main" style="width:100%; background: #f2f2f2;">
   
      <table id="example-advanced" style="align:center;font-size:12px; margin-top: 0px;">
        <thead>
          <tr>
            <th nowrap width="5%">序号</th>
            <th  width="30%">任务/服务名称</th>
            <th  nowrap width="">错误码</th>
            <th  nowrap width="">错误描述</th>
            <th   width="24%">开始时间</th>
            <th   width="24%">结束时间</th>
          </tr>
          
        </thead>
        <tbody>
        <%
        Recordset rslist=db.getRecordset("errinfos");
        String treeData="";
        if (!rslist.isEmpty()) {
        	int len = rslist.size();
			for (int i = 0; i < len; i++) {
				DataBus rsTmp = rslist.get(i);
				
				treeData += ("<tr data-tt-id='"
						+ rsTmp.getValue("task_id")
						+ "'><td>"
						+ (i + 1)
						+ "</td><td nowrap>"+rsTmp.getValue("task_name")
						+ "</td><td align='center'>"+rsTmp.getValue("return_codes")
						+"</td><td align='center'>"
						+ rsTmp.getValue("errdes")
						+ "</td><td style='text-align:right;' nowrap>"
						+ rsTmp.getValue("task_start_time")
						+ "</td><td style='text-align:right;' nowrap>"
						+ rsTmp.getValue("task_end_time") 
						+ "</td></tr>");
				
			}
        }
        out.println(treeData);
        %>
         
        </tbody>
      </table>
</div>
</td></tr></table>
    <script src="<%=request.getContextPath()%>/page/zwt/statistcs/vendor/jquery.js"></script>
    <script src="<%=request.getContextPath()%>/page/zwt/statistcs/vendor/jquery-ui.js"></script>
    <script src="<%=request.getContextPath()%>/page/zwt/statistcs/javascripts/src/jquery.treetable.js"></script>
    <script>
      
      $("#example-advanced").treetable({ expandable: true ,column : 1});

      // Highlight selected row
      $("#example-advanced tbody").on("mousedown", "tr", function() {
        $(".selected").not(this).removeClass("selected");
        $(this).toggleClass("selected");
      });

      // Drag & Drop Example Code
      $("#example-advanced .file, #example-advanced .folder").draggable({
        helper: "clone",
        opacity: .75,
        refreshPositions: true, // Performance?
        revert: "invalid",
        revertDuration: 300,
        scroll: true
      });

      $("#example-advanced .folder").each(function() {
        $(this).parents("#example-advanced tr").droppable({
          accept: ".file, .folder",
          drop: function(e, ui) {
            var droppedEl = ui.draggable.parents("tr");
            $("#example-advanced").treetable("move", droppedEl.data("ttId"), $(this).data("ttId"));
          },
          hoverClass: "accept",
          over: function(e, ui) {
            var droppedEl = ui.draggable.parents("tr");
            if(this != droppedEl[0] && !$(this).is(".expanded")) {
              $("#example-advanced").treetable("expandNode", $(this).data("ttId"));
            }
          }
        });
      });

      $("form#reveal").submit(function() {
        var nodeId = $("#revealNodeId").val()

        try {
          $("#example-advanced").treetable("reveal", nodeId);
        }
        catch(error) {
          alert(error.message);
        }

        return false;
      });
      
      $(function(){
    	  $('span.indenter:first a')[0].click();
      })
    </script>

   --%>

</freeze:form>

</div>
</freeze:body>
</freeze:html>
