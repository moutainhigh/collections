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
var setting = {
		check: {
			enable: true,
			chkboxType:{ "Y":'ps', "N":'ps'},
			chkDisabledInherit: true
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};

	var zNodes =[
		{ id:1, pId:0, name:"随意勾选 1", open:true},
		{ id:11, pId:1, name:"随意勾选 1-1", open:true},
		{ id:111, pId:11, name:"随意勾选 1-1-1"},
		{ id:112, pId:11, name:"随意勾选 1-1-2"},
		{ id:12, pId:1, name:"随意勾选 1-2", open:true},
		{ id:121, pId:12, name:"不可选",chkDisabled:true},
		{ id:122, pId:12, name:"随意勾选 1-2-2"},
		{ id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
		{ id:21, pId:2, name:"随意勾选 2-1"},
		{ id:22, pId:2, name:"随意勾选 2-2", open:true},
		{ id:221, pId:22, name:"随意勾选 2-2-1", checked:true},
		{ id:222, pId:22, name:"随意勾选 2-2-2"},
		{ id:23, pId:2, name:"随意勾选 2-3"}
	];
		
	$(document).ready(function(){
		$("#treeDemo").tree({setting: setting, data: zNodes});

	});
	function back(){
		parent.winEdit.window("close");
	}
	function save(){
		parent.winEdit.window("close");
	}
	function reset() {
		$("#treeDemo").ztree('reset');
	}
</script>
</head>
<body>
	<div id="column_id" width="100%" height="100%" vtype="panel" name="panel" layout="row" layoutconfig="{height:['*']}">
        <div>
    		<div name="w1" vtype="panel" title="维护主题库，当前被选择主题(12135主题)" titledisplay="true" height="100%">
    		   	<div>
					<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="left">
						<div id="btn1" name="btn1" vtype="button" text="保存"
							icon="../../../style/default/images/save.png" click="save()"></div>
						<div id="btn2" name="btn2" vtype="button" text="重置"
							icon="../../../style/default/images/save.png" click="reset()"></div>
						<div id="btn3" name="btn3" vtype="button" text="关闭"
							icon="../../../style/default/images/fh.png" click="back()"></div>
					</div>
    			</div>
    			<div id="treeDemo" class="ztree" height="90%"></div>
    		</div>
    	</div>
    </div>
</body>
</html>