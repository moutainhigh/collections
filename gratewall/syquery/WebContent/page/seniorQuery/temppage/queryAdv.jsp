<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>高级查询</title>
<style type="text/css">
</style>
</head>

<script src="<%=request.getContextPath()%>/static/script/jazz.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/sczt/base64.js"
	type="text/javascript"></script>
<script language="javascript">
	function query() {
		var url = "../../advQuery/getList.do";
		$('#gridpanel').gridpanel('option', 'dataurl', url);
		$('#gridpanel').gridpanel('query', [ 'formpanel' ]);
		//提交form表单，并且回绑gridpanel值。
	}

	function reset() {
		$("#formpanel").formpanel('reset');
		//重置form表单
	}

	function editColumn(event, obj) {
		var data = obj.data;
		console.log(data);
		for (var i = 0; i < data.length; i++) {
			var htm = '<a href="javascript:void(0);" onclick="doIt(\''
					+ data[i]["queryInfoId"] + '\')">' + "执行"
					+ '</a>&nbsp;|&nbsp;'
					+ '<a href="javascript:void(0);" onclick="del(\''
					+ data[i]["queryInfoId"] + '\')">' + "删除"
					+ '</a> &nbsp;|&nbsp;'
					+ '<a href="javascript:void(0);" onclick="detail(\''
					+ data[i]["queryInfoId"] + '\')">' + "详情" + '</a> &nbsp;';
			data[i]["edit"] = htm;
			//此方法从name="gridpanel"的div处的datarender="editColumn"属性开始。
			//生成的htm赋值给字段为edit的列。
		}
		return data;
	}

	function del(id) {
		jazz.confirm("请确认是否删除此查询？", function() {
			var params = {
				url : "../../advQuery/delQuery.do?id=" + id,
				callback : function(data, r, res) {
					if (res.getAttr("back") == 'success') {
						query();
						jazz.info("删除成功");
					}
				}
			}
			$.DataAdapter.submit(params);
		});
	}
	
	function doIt(id){
		alert("尚未开放");
	}
	///sjzx_query/WebContent/page/seniorQuery/queryAdvInfo.jsp
	function detail(id){
		win = top.jazz.widget({
		vtype: 'window',
		name: 'a1_win',
    	title: '查询详情',
    	titlealign: 'center',
    	titledisplay: true,
        width:800,
        height:500,
        modal:true,
        visible: true,
        frameurl: '/query/page/seniorQuery/queryAdvInfo.jsp?id='+id,
        buttons:[{
        	text: '关闭',
        	align: 'center',
        	click: function(e){
        		jazz.window.close(e);
        	}
        }]
		}
	);
		
}
	function addAdvQuery(){
		window.location="/query/page/seniorQuery/query_config.jsp";
		}
	
</script>

<body>
	<!-- 	<div class="title_nav">当前位置：资源管理 > <span>数据源管理</span></div> -->
	<div id="formpanel" name="formpanel" vtype="formpanel"
		titledisplay="true" width="100%" layout="table"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100px"
		title="查询条件">
		<div name='queryName' vtype="textareafield" label="名称"
			labelalign="right" width="300"></div>
		<div name='createUser' vtype="textareafield" label="创建人"
			labelalign="right" width="300"></div>
		<div id="toolbar" name="toolbar" vtype="toolbar" align="center">
			<div id="btn3" name="btn3" vtype="button" text="查询" click="query()">
			</div>
			<div id="btn4" name="btn4" vtype="button" text="重置" click="reset()">
			</div>
		</div>
	</div>
	<div vtype="gridpanel" name="gridpanel" id="gridpanel" width="100%"
		layout="fit" showborder="false" datarender="editColumn"
		rowselectable="false" titledisplay="false" isshowselecthelper="true"
		selecttype="0" dataurl="../../advQuery/getList.do">


		<div name="toolbar" vtype="toolbar">
			<div name="button" vtype="button" text="新增高级查询" click="addAdvQuery()"></div>
		</div>
		<div vtype="gridcolumn" name="grid_column" id="grid_column">
			<div id="dateShowRows">
				<!-- 单行表头 -->
				
				<div name='queryInfoId' key="true" visible="false"></div>
				<div name='queryInfoName' text="查询名称" textalign="center" width="28%"
					dataurl="<%=request.getContextPath()%>/caseselect/code_value.do?type=caseNo"></div>
				<div name='creatorId' text="创建人" textalign="center" width="28%"></div>
				<div name='createdTime' text="创建时间" textalign="center" width="28%"></div>
				<div name='edit' text="操作" textalign="center" width="18%"></div>

			</div>
		</div>
		
		<!-- 表格 -->
		<div vtype="gridtable" name="grid_table" id="grid_table" rowrender=""></div>
		<!-- 分页 -->
		<div vtype="paginator" name="grid_paginator" pagerows="50"
			id="grid_paginator"></div>
	</div>
</body>
</html>