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
<script>
	

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
		height="495" dataurl="" readonly="true">

		<div name='pripid' vtype="hiddenfield" label="ID" labelAlign="right"
			rule="must" width="400" labelwidth="130"></div>
		<div name='regno' vtype="textfield" label="数据项名称" labelAlign="right"
			rule="must" width="400" labelwidth="130"></div>
		<div name='entname' vtype="textfield" label="中文名"
			labelAlign="right" width="400" labelwidth="130"></div>
		
			
		<div name='n' vtype="textfield" label="数据项类型（长度）" labelAlign="right"
			width="400" labelwidth="130" ></div>
			
		<div name='regorg' vtype="comboxfield" label="是否主键" dataurl='[{"text":"是","value":"1"},{"text":"否","value":"2"}]'
			labelAlign="right" width="400" labelwidth="130" ></div>
			
		<div name='regorg' vtype="textfield" label="对应代码表" 
			labelAlign="right" width="400" labelwidth="130" ></div>
		<!-- <div name="tip" vtype="textfield" label="提示" labelAlign="right"
			width="850" labelwidth="130" defaultvalue="SQL语句中代码值的标识为“value”，文本的标识为“text”。
			示例：select t.wb as text,dm as value from T_PT_DMSJB t" readonly="true"></div> -->

		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div id="btn2" name="btn2" vtype="button" text="关闭"
				icon="../../../style/default/images/fh.png" click="javascript:history.go(-1)"></div>
		</div>
	</div>
</body>
</html>