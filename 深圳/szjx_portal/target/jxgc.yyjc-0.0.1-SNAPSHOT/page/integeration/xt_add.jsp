<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>应用集成-新增</title>
<%
	String contextpath = request.getContextPath();
	String pkId = request.getParameter("pkId");
	String type = request.getParameter("type");
	String systype = request.getParameter("systype");
%>
<script type="text/javascript">
	var type = '<%=type%>';
	var pkId = '<%=pkId%>';
	var systype='<%=systype%>';
</script>
<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=contextpath%>/static/script/home/xt_add.js" type="text/javascript"></script>
<style type="text/css">
.jazz-field-comp-in2{
	background-color:transparent;
}
</style>
</head>
<body>
   <div id="formpanel" name="formpanel" vtype="formpanel" titledisplay="false" showborder="false" width="100%" layout="table" 
	     layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%">
   		<div name='pkSysIntegration' id='pkSysIntegration' vtype="hiddenfield" ></div>
   		<div name='systemName' id='systemName' vtype="textfield" label="子系统名称" labelalign="right" labelwidth="100" width="80%" rule="must" editable="true"></div>
		<div name='systemCode' id='systemCode' vtype="textfield" label="子系统编码" labelalign="right" labelwidth="100" width="80%"  maxlength="4" editable="true"  rule="customFunction;changeCode()"></div>
		<div name='systemType' id='systemType' vtype="radiofield" label="是否业务域" labelalign="right" labelwidth="100" width="80%" itemwidth="60" rule="must" dataurl="[{value: 'NOSYS',text: '是'},{checked: true,value: 'SYS',text: '否'}]"></div>
	     		     	
     	<div name='parentCode' id='parentCode' vtype="comboxfield" label="所属" labelalign="right" labelwidth="100" width="300" rule="customFunction;parentCodeVailue()"dataurl="<%=contextpath%>/integration/getBusiDomain.do" ></div>
     	
   		<div name='integratedUrl' id='integratedUrl' vtype="textfield" label="集成URL" colspan="2"   labelalign="right" labelwidth="100" width="90%" height="50" rule="customFunction;changeUrl()" editable="true"></div>
   		<%-- 显示为第一层--%> 
   		<div name='levelCode' id='levelCode' vtype="hiddenfield" label="" labelalign="right" labelwidth="100" width="80%" itemwidth="60" dataurl="[{value: '1',text: '是'},{checked: true,value: '2',text: '否'}]"></div>
		<%-- 是否启用 --%> 
		<div name='systemState' id='systemState'  vtype="hiddenfield" label="" labelalign="right" labelwidth="100" width="80%" itemwidth="60" dataurl="[{checked: true,value: '0',text: '启用'},{value: '1',text: '停止'}]"></div>
		<div name='pkSmFirm' id='pkSmFirm' vtype="comboxfield" label="系统厂家" labelalign="right" labelwidth="100" width="300" dataurl="<%=contextpath%>/firmList/getFrim.do" change="selectFirm();"></div>
		<div name='pkSmLikeman' id='pkSmLikeman' vtype="comboxfield" label="联系人" labelalign="right" labelwidth="100" width="300" dataurl="<%=contextpath%>/firmList/getLinkman.do" disabled="true"></div>
		<div name='createrId' id='createrId' vtype="textfield" label="创建人" labelalign="right" labelwidth="100" width="80%" editable="true"></div>
		<div name='createrTime' id='createrTime' vtype="datefield" label="创建时间" labelalign="right" labelwidth="100" width="80%" editable="true"></div>
		<div name='remarks' id='remarks' vtype="textareafield"  colspan="2" rowspan="1" label="备注" labelalign="right" labelwidth="100" width="90%" height="80" editable="true" maxlength="1500"></div>
		<div name='systemImgUrl' id='systemImgUrl' vtype="hiddenfield" ></div>
		<div id="toolbar" name="toolbar" vtype="toolbar" >		
	    	<div id="btn5" name="btn5" vtype="button" text="保  存" defaultview="1" align="center" click="save()"></div>
	    	<div id="btn4" name="btn4" vtype="button" text="返  回" defaultview="1" align="center" click="leave()"></div>
	    </div>
   </div>
   	<div id="zhezhao"style="text-align: center;position:fixed;z-index:1000;display:none;top:0;left:0;">
		<div style="background:#999;opacity: 0.8;width:100%;height:100%; position: absolute;z-index:-1;"></div>
		<div id="close-img" style="position:fixed;right:30px;top:30px;color:red;cursor:pointer;"><img src="<%=contextpath%>/static/images/icons/6.png" width="13" height="13"/></div>
		<img id="zhezhao-img" alt="" src="" style="margin-top:80px;"> 
	</div>
</body>
</html>
