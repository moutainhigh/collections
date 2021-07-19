<%@ page contentType="text/html; charset=GBK" %>
<%@page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ page import="cn.gwssi.common.context.TxnContext" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>

<freeze:html>
<head>
<title>增加交易信息</title>
<style>
.even1 input,.even2 input{
	display:inline !important;
}

.btn_cancel{
  margin-left: 5px;
}

</style>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
</head>
<freeze:body>
<freeze:title caption="增加交易信息"/>
<freeze:errors/>
  <script language="javascript">
  function saveColumns( selRows )
  {
    // 保存
    document.forms[currentFormName].action = rootPath  + '/txn980317.do';
    saveAndExit( '', '保存数据时错误', '/txn980311.do' );
  }
  $(document).ready(function(){
  	$(".checkboxNew").each(function(){
  		$(this).prev().css("margin-left","-1000");
  	})
  })
  </script>
  
  <freeze:form action="/txn980316">
	<freeze:block property="select-key" width="95%" columns="1" captionWidth="0.2" caption="从模块中选择交易">
		<freeze:hidden property="funccode" caption="功能代码" style="width:70%"/>
	  	<freeze:browsebox property="file-name" valueset="STRUTS配置文件列表" caption="模块名称" check="false" show="mix" onchange="saveRecord('','');" style="width:95%"/>
	</freeze:block>
	<br />
	<freeze:grid caption="交易列表" property="record" keylist="txncode" fixrow="false"  width="95%">
		<freeze:button name="record_saveRecord" caption="保存" enablerule="2" align="right"  onclick="saveColumns(selRows);"/>
		<freeze:button name="record_goBack" caption="返回" align="right" style="margin-left:5px;" onclick="goBackNoUpdate('/txn902231.do');"/>
		<freeze:text property="txncode" caption="交易代码" width="25%" readonly="true"/>
		<freeze:text property="txnname" caption="交易名称" width="35%"/>
		<freeze:text property="memo" caption="备注" width="40%"/>
	</freeze:grid>
</freeze:form>
<%
  	TxnContext context_info = (TxnContext) request.getAttribute("freeze-databus");
	if(context_info.keySet().contains("record")){
	Recordset db_record = context_info.getRecordset("record");
	int al = 0;
  	for(int ii=0;ii<db_record.size();ii++){
  		if(db_record.get(ii).getValue("status")==null){
  			al++;
  			continue;
  		}else{
  			%>
  			<script type="text/javascript">
  			document.getElementById("label_<%=al%>").className='checkboxNew back_down';
  			</script>
  			<%
  			al++;
  		}
  	}}
  %>
</freeze:body>
</freeze:html>
