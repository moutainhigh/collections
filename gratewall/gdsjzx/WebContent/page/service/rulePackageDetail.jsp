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
		{ id:1, pId:0, name:"登记系统", open:true},
		{ id:2, pId:0, name:"年检系统", open:true},
		{ id:3, pId:0, name:"案件系统", open:true},
		{ id:31, pId:3, name:"案件基本信息", open:true},
		{ id:311, pId:31, name:"案件登记编号字段非空校验"},
		{ id:312, pId:31, name:"办案单位字段非空校验"},
		{ id:313, pId:31, name:"部门标识字段非空校验", open:true},
		{ id:314, pId:31, name:"当事人名称字段非空校验",chkDisabled:true},
		{ id:315, pId:31, name:"法定代表人字段非空校验"},
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
    		<div name="w1" vtype="panel" title="维护规则" titledisplay="false" height="100%">
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