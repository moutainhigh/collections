<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>表管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script>
	jQuery(function($){
		/* $('div[name="dmbLx"]').on("comboxfieldchange",function(event, ui){ 
			//alert(ui.newValue+"---"+ui.newText);
			if(ui.newValue=='1'||ui.newValue=='3'){
				$("div[name='ccm']").textfield("option", "disabled", true);
				$("div[name='sfdc']").comboxfield("option", "disabled", true);
			} else{
				$("div[name='ccm']").textfield("option", "disabled", false);
				$("div[name='sfdc']").comboxfield("option", "disabled", false);
			}
		});
		$('div[name="sjccLx"]').on("comboxfieldchange", function(event, ui) {
			//alert(ui.newValue+"---"+ui.newText);
			if (ui.newValue == '1' || ui.newValue == '2') {
				$("div[name='sql']").textareafield("option", "disabled", true);
				$("div[name='tip']").hide();
			} else {
				$("div[name='sql']").textareafield("option", "disabled", false);
				$("div[name='tip']").show();
			}
		}); */
	});

	function save() {
		parent.winEdit.window("close");
		/* var dmbId = $("div[name='dmbId']").textfield('getValue');
		var dmbMc = $("div[name='dmbMc']").textfield('getValue');
		var sjccLx = $("div[name='sjccLx']").comboxfield('getValue');
		var dmbLx = $("div[name='dmbLx']").comboxfield('getValue');
		var hcLx = $("div[name='hcLx']").comboxfield('getValue');
		var qyBj = $("div[name='qyBj']").comboxfield('getValue');
		if(dmbId == ''||dmbMc == ''||sjccLx==''||dmbLx==''||hcLx==''||qyBj==''){
			jazz.info("有必填选项未填写，添加失败");
		}else{
			var sql = $("div[name='sql']").textareafield('getValue');
			var sqlnew = encodeURI(encodeURI(sql));
			sqlnew = sqlnew.replace(/'/g, "#");
			$("div[name='sql']").textareafield('setValue',sqlnew);
			var params = {
				url : rootPath+'/dictionaryList/save.do?update='+update,
				components : [ 'formpanel_edit' ],
				
				callback : function(data, r, res) { 
					if (res.getAttr("back") == 'success'){
						parent.queryDef();
						parent.winEdit.window("close");
						jazz.info("保存成功");
					} else if (res.getAttr("back") == 'sameId'){
						$("div[name='sql']").textfield('setValue',sql);
						jazz.error("代码表ID已经存在，添加失败");
					} else if (res.getAttr("back") == 'empty'){
						jazz.error("sql不能为空");
					} else if (res.getAttr("back") == 'sql'){
						jazz.error("通用表和独立表不能有自定义SQL");
					}else{
						$("div[name='sql']").textfield('setValue',sql);
						jazz.error("sql错误");
					}
				}
			};
			$.DataAdapter.submit(params);
		} */
	}
	
	/*function initData(res){
		var dmbId = res.getAttr("dmbId");
		$('div[name="formpanel_edit"]').formpanel('option', 'dataurl',rootPath+
		'/page/editinit.do?dmbId='+dmbId);
		$('div[name="formpanel_edit"]').formpanel('reload');
	}*/
	
	var update;
	$(function(){
		var priPid = jazz.util.getParameter("dmbId");
		update = jazz.util.getParameter("update");
		if(priPid != null){
			//$('div[name="formpanel_edit"]').formpanel('option', 'dataurl',rootPath+'/dictionaryList/editinit.do?dmbId='+dmbId);
			//$('div[name="formpanel_edit"]').formpanel('reload');
			$('#formpanel_edit .jazz-panel-content').loading();
			$("#formpanel_edit").formpanel('option', 'dataurl',rootPath+'/query/detail.do?priPid='+priPid);
			$("#formpanel_edit").formpanel('reload', "null", function(){$('#formpanel_edit .jazz-panel-content').loading('hide');});
		}
	});
	
	function back() {
		parent.winEdit.window("close");
	}
	
</script>
</head>
<body>
	<div id="formpanel_edit" name="formpanel_edit" vtype="formpanel"
		tableStyleClass="tablecss" titledisplay="false" showborder="false" width="100%"
		layout="table" layoutconfig="{cols:2, columnwidth: ['50%','*']}"
		height="auto" dataurl="">

		<div name='pripid' vtype="hiddenfield" label="ID" labelAlign="right"
			rule="must" width="400" labelwidth="130"></div>
		<div name='regno' vtype="textfield" label="代码" labelAlign="right"
			rule="must" width="400" labelwidth="130"></div>
		<div name='entname' vtype="textfield" label="代码名称"
			labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		<div name='dmccb' vtype="comboxfield" label="代码标准来源" labelAlign="right"
			width="400" labelwidth="130" dataurl='[{"text":"国家标准","value":"1"},{"text":"行业标准","value":"2"}]'></div>
		<div name='dmlMc' vtype="comboxfield" label="代码标准标识符" labelAlign="right"
			width="400" labelwidth="130" dataurl='[{"text":"CE09","value":"1"},{"text":"CE07","value":"2"}]'></div>
		<div name='regorg' vtype="textareafield" label="代码描述" colspan="2"
			labelAlign="right" width="850" labelwidth="130" height="60"></div>
		<!-- <div name="tip" vtype="textfield" label="提示" labelAlign="right"
			width="850" labelwidth="130" defaultvalue="SQL语句中代码值的标识为“value”，文本的标识为“text”。
			示例：select t.wb as text,dm as value from T_PT_DMSJB t" readonly="true"></div> -->

		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div id="btn1" name="btn1" vtype="button" text="保存"
				icon="../../../style/default/images/save.png" click="save()"></div>
			<div id="btn2" name="btn2" vtype="button" text="返回"
				icon="../../../style/default/images/fh.png" click="back()"></div>
		</div>
	</div>
</body>
</html>