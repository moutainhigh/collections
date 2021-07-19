<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/datachange/servicefw-sjzx.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/datachange/servicefw.js" type="text/javascript"></script>
</head>
<body>
<!-- <div name="row_id" height="1100" vtype="panel" layout="row" layoutconfig="{rowheight:['65%','*','25%']}"> -->
<div name="row_id" height="100%" vtype="panel" layout="row" layoutconfig="{rowheight:['*','28%']}">
	<div>
	<div id="formpanel_edit" name="formpanel_edit" vtype="formpanel"
		tableStyleClass="tablecss" titledisplay="false" showborder="false" width="100%"
		layout="table" layoutconfig="{cols:2, columnwidth: ['50%','*']}" dataurl="" height="100%">

		<div name='serviceid' vtype="hiddenfield" label="ID" labelAlign="right" width="400" labelwidth="130"></div>
		<!-- <div name='servicename' vtype="textfield" label="服务名称" labelAlign="right" rule="must" width="400" labelwidth="130"></div> -->
		<div style="margin-left:35px;display:none" class="jazz-field-comp jazz-datefield-comp">
			<label class="jazz-field-comp-label jazz-form-text-label" for="comp_j_09638185_1005_input" style="display: block; text-align: right; width: 100px;">所属地市:</label>
			<select id="BelongArea" onChange="checkArea(this,'1')" style="margin-left:10px;width:245px;height:30px;border:1px solid #C5D6E0;">
			</select>
		</div>
			<div style="margin-left:35px;display:none" class="jazz-field-comp jazz-datefield-comp">
			<label class="jazz-field-comp-label jazz-form-text-label" for="comp_j_09638185_1005_input" style="display: block; text-align: right; width: 100px;">所属机构:</label>
			<select id="BelongOrg"  style="margin-left:10px;width:245px;height:30px;border:1px solid #C5D6E0;">
			</select>
		</div>
		<div style="display:none;margin-left:35px" class="jazz-field-comp jazz-datefield-comp">
			<label class="jazz-field-comp-label jazz-form-text-label" for="comp_j_09638185_1005_input" style="display: none; text-align: right; width: 100px;">服务提供对象:</label>
			<select id="ServiceObject" isSjzx="1" style="margin-left:10px;width:245px;height:30px;border:1px solid #C5D6E0;">
			</select>
		</div>
		<div name='serviceorgname' vtype="hiddenfield" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		<div name='region' vtype="hiddenfield"  labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		<div style="display:none" name='businessname' vtype="textfield" label="业务系统名称" labelAlign="right" width="400" labelwidth="130"></div>
		<div name='interfacename' vtype="textfield" label="接口名称" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		<div name='servicename' vtype="hiddenfield" label="服务名字" labelAlign="right" width="400" labelwidth="130"></div>
		<div name='defaulttime' vtype="textfield" label="超时时间" labelAlign="right" rule="must" width="400" labelwidth="130" ></div>
		<div name='description' style="display:none" vtype="textareafield" label="服务描述" labelAlign="right" width="850" labelwidth="130" height="60" colspan="2"></div>
		<!-- <div name='servicetype' vtype="comboxfield" dataurl="[{value: '0',text: 'webservice'},{value: '1',text: '数据库'},{value: '2',text: '订阅'}]" label="服务类型" labelAlign="right" rule="must" width="400" labelwidth="130"></div> -->
		<div name='createtype' vtype="hiddenfield"  label="服务类型" labelAlign="right" width="400" labelwidth="130"></div>
		
		<div name='alias' vtype="textfield" label="服务别名" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		<div style="margin-left:35px" class="jazz-field-comp jazz-datefield-comp">
			<label class="jazz-field-comp-label jazz-form-text-label" style="display: block; text-align: right; width: 100px;">服务分类:</label>
			<select id="serviceclassify" style="margin-left:10px;width:245px;height:30px;border:1px solid #C5D6E0;">
					<option value="synUnifiedService">同步</option>
					<option value="specialSynUnifiedService">同步特例</option>
					<option value="asynUnifiedService">异步</option>
			</select>
		</div>
		<div name='servicestate' vtype="radiofield" dataurl="[{value: '0',text: '启用',checked:'true'},{value: '1',text: '停用'}]" label="服务状态" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		<div name='executetype' vtype="radiofield" dataurl="[{value: '0',text: '正常',checked:'true'},{value: '1',text: '降级'}]" label="执行类型" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		<div name='serviceurl' vtype="textareafield"  label="服务URI" labelAlign="right" width="850" labelwidth="130" height="60" rule="must" colspan="2"></div>
		<label class="jazz-field-comp-label jazz-form-text-label" style="color:red;display: block; text-align: right; width: 620px;"></label>
		<div name='invokeclass' vtype="textareafield" label="服务执行类" labelAlign="right" width="850" labelwidth="130" height="60" rule="must" colspan="2"></div>
		<!-- <div vtype="textfield" label="服务内容" readonl labelAlign="right" colspan="2" width="850" labelwidth="130" readonly="true"></div> -->
		<!-- <div style="padding-left: 100px;height: 10px;"  id="funcTree" name="funcTree" class="ztree" title="sfsdfs" titledisplay="true"></div> -->
		<div name='serviceconentshow' vtype="hiddenfield" labelAlign="right" width="400" labelwidth="130"></div>
		<div name='serviceconentid' vtype="comboxfield" filterable=true label="服务内容" labelAlign="right" labelwidth="130" rule="must"
			multiple="false" width="400"></div>
	</div>
	</div>
	<div id="dy" style="display:none">
		<!--<div vtype="textfield" label="订阅配置" readonl labelAlign="right" colspan="2" width="850" labelwidth="130" readonly="true"></div>
		 <div style="padding-left: 100px;height: 10px;"  id="funcTree1" name="funcTree1" class="ztree" title="sfsdfs1" titledisplay="true"></div> -->
		<div id="formpanel_edit1" name="formpanel_edit1" vtype="formpanel"
		tableStyleClass="tablecss" titledisplay="true"  title="订阅配置" showborder="false" width="98%"
		layout="table" layoutconfig="{cols:2, columnwidth: ['50%','*']}" dataurl="">

		<div name='fwdypzjbxxid' vtype="hiddenfield" label="ID" labelAlign="right" width="400" labelwidth="130"></div>
		<div name='subscriptionobject' vtype="textfield" label="订阅对象" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		<div name='startsubscribetime' vtype="datefield" label="开始订阅时间" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		<div name='frequency' vtype="textfield" label="频率" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		<div name='path' vtype="textfield" label="路径" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		<div name='acceptway' vtype="comboxfield" dataurl="[{value: '0',text: '停用'},{value: '1',text: '启用'}]" label="接受方式" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		<div name='reason' vtype="textareafield" label="原因" labelAlign="right" width="850" rule="must" labelwidth="130" height="60" colspan="2"></div>
	</div>
	</div>
	<div>
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div id="btn1" name="btn1" vtype="button" text="保存" icon="../../../style/default/images/save.png" click="save()"></div>
			<div id="btn2" name="btn2" vtype="button" text="返回" icon="../../../style/default/images/fh.png" click="back()"></div>
		</div>
	</div>
</div>
</body>
<script type="text/javascript">

$(window).load(function(){
	//要执行的方法体
	setTimeout('analysis()',1000);
	$('div[name="serviceurl"]').textareafield('setValue','http://10.1.2.122:9000/SOAService/synUnifiedService/clientAuthenticationData');
});

function analysis(){
	var servicetype = $("div[name='servicetype']").comboxfield('getValue');
	if(servicetype=='2'){
		$('#dy').show();
		var serviceid = $("#serviceidupdate",parent.document).val();
		if(serviceid != null){
			$('#formpanel_edit1 .jazz-panel-content').loading();
			$("#formpanel_edit1").formpanel('option', 'dataurl',rootPath+'/dataservice/subscriptionDetailByServiceid.do?serviceid='+serviceid);
			$("#formpanel_edit1").formpanel('reload', "null", function(){$('#formpanel_edit1 .jazz-panel-content').loading('hide');});
		}
	}else{
		$('#dy').hide();
	}
}
</script>
</html>