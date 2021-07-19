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
	var update;
	var serviceobjectname;
	var themeid;
	$(function(){
		themeid = jazz.util.getParameter("themeid");
		 update = jazz.util.getParameter("update");
		//$("#formpanel_edit").formpanel('option', 'disabled', true);
		if(update=="true"){
			$('#formpanel_edit .jazz-panel-content').loading();
			$("#formpanel_edit").formpanel('option', 'dataurl',rootPath+'/datatheme/themeDetail.do?themeid='+themeid);
			$("#formpanel_edit").formpanel('reload', "null", function(){
			$('#formpanel_edit .jazz-panel-content').loading('hide');
			});
		}
	});
	
	function back() {
		parent.winEdit.window("close");
	}
	function save() {
		var isstart = $("div[name='isstart']").radiofield('getValue');
		var lastuupdatetime = $("div[name='lastuupdatetime']").textareafield('getValue');
		var themeobjectname = $("div[name='themename']").textfield('getValue');
		if(isstart==''||lastuupdatetime==''|| themeobjectname==''){
			jazz.info("有必填选项未填写，添加失败");
		}else{
			//	if(selectedData.length<=0){
				//	jazz.info("请选择配置服务内容！");
			//	}else{
					var params = {
						url : rootPath+'/datatheme/saveTheme.do?update='+update,
						components : [ 'formpanel_edit' ],
						callback : function(data, r, res) { 
							if (res.getAttr("back") == 'success'){
							jazz.info("保存服务名:"+themeobjectname);
								parent.queryUrl();
								parent.winEdit.window("close");
								jazz.info("保存成功！");
							}else{
								jazz.error("添加服务对象失败！");
							}
						}
					};
					$.DataAdapter.submit(params);
			//	}
		}
	}
	function submintSave(){
	}
</script>
</head>
<body>
<div name="row_id" height="300" vtype="panel" layout="row" layoutconfig="{rowheight:['55%','*','8%']}">
	<div id="formpanel_edit" name="formpanel_edit" vtype="formpanel"
		tableStyleClass="tablecss" titledisplay="false" showborder="false" width="100%"
		layout="table" layoutconfig="{cols:2, columnwidth: ['50%','*']}" dataurl="" height="100%">
		<div name='ztid' vtype="hiddenfield" label="ID" labelAlign="right" width="400" labelwidth="130"></div>
		<!-- <div name='themeid' vtype="hiddenfield" label="ID" labelAlign="right" width="400" labelwidth="130"></div> -->
		<div name='themename' vtype="textfield" label="主题名字" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
        <!-- <div name='servicecode' vtype="textfield" label="服务代码" labelAlign="right" rule="must" width="400" labelwidth="130"></div> -->	
		<div name='isstart' vtype="radiofield" dataurl="[{value: '0',text: '启用'},{value: '1',text: '停用'}]" label="状态" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		<div name='lastuupdatetime' vtype="textareafield" colspan="2" label="主题说明" labelAlign="right" rule="must" width="850" labelwidth="130"  dataurl="[{checked: true,value:'1',text:'30000'}]"></div>
	</div>
	</div>
	<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div id="btn1" name="btn1" vtype="button" text="保存" icon="../../../style/default/images/save.png" click="save()"></div>
			<div id="btn2" name="btn2" vtype="button" text="返回" icon="../../../style/default/images/fh.png" click="back()"></div>
		</div>
</body>
</html>