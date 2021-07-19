<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>自定义检查</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<style type="text/css">
td{
	text-align: center;
}
.jazz-pagearea{
	height: 0px;
}
</style>
<script>
function reset() {
	$("#formpanel").formpanel('reset');
}
function queryUrl() {
	$("#zzjgGrid").gridpanel("hideColumn", "approvedate");
		$("#zzjgGrid").gridpanel("showColumn", "modfydate");
	$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+
			'/query/list.do');
	$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
}
function queryUrl1() {
	$('#zzjgGrid1').gridpanel('option', 'dataurl',rootPath+
			'/query/list.do');
	$('#zzjgGrid1').gridpanel('query', [ 'formpanel']);
}

function queryUrl2() {
	$('#zzjgGrid2').gridpanel('option', 'dataurl',rootPath+
			'/query/list.do');
	$('#zzjgGrid2').gridpanel('query', [ 'formpanel']);
}
$(document).ready(function(){
	queryUrl();
	queryUrl1();
	queryUrl2();
});
</script>
</head>
<body>
<div>位置：数据服务>数据治理>检查结果统计</div>
<div id="demo">
		<div name="tab_name" vtype="tabpanel" overflowtype="2" tabtitlewidth="130"  width="100%" height="760" 
			 orientation="top" id="tab_name">    
		    <ul>    
		        <li><a href="#tab1">按主题分类</a></li>    
		        <li><a href="#tab2">按区县分类</a></li>    
		        <li><a href="#tab3">按规则分类</a></li>  
		    </ul>    
		    <div>    
		        <div id="tab1">    
                	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" 
						showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*'],border:false}" height="100" title=">>选择检查活动">
						<div dataurl="[{checked: true,value: '1',text: 'item1'},{value: '2',text: 'item2'}]" name='entname' vtype="comboxfield" label="活动期数" labelAlign="right" labelwidth='100px' width="410"></div>
					</div>
				
					<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid' datarender=""
						titledisplay="true" title="统计"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
						<!-- 表头 -->
						<div vtype="gridcolumn" name="grid_column" width="100%">
							<div>
								<div name='pripid'  width="1%"></div>
								<div name='regno' text="业务系统" textalign="center"  width="10%"></div>
								<div name='enttype' text="期初问题点" textalign="center"  width="10%"></div>
								<div name='regorg' text="期初问题点" textalign="center"  width="10%"></div>
								<div name='servicestate' text="修改数量" textalign="center"  width="10%"></div>
								<div name='estdate' text="核实数量" textalign="center"  width="10%"></div>
								<div name='industryco' text="留存问题数" textalign="center"  width="10%"></div>
								<div name='industryphy' text="检查点数量" textalign="center"  width="10%"></div>
								<div name='custom' text="数据处理率" textalign="center"  width="18%"></div>
							</div>
						</div>
						<!-- 表格 -->
						<!-- ../../grid/reg3.json -->
						<div vtype="gridtable" name="grid_table" ></div>
						<!-- 分页 -->
						<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
					</div>
		        </div>    
		        <div id="tab3">    
		             <div vtype="formpanel" id="formpanel2" displayrows="1" name="formpanel2" titledisplay="true" width="100%" layout="table" 
						showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*'],border:false}" height="100" title=">>选择检查活动">
						<div dataurl="[{checked: true,value: '1',text: 'item1'},{value: '2',text: 'item2'}]" name='entname' vtype="comboxfield" label="活动期数" labelAlign="right" labelwidth='100px' width="410"></div>
					</div>
				
					<div vtype="gridpanel" name="zzjgGrid2" height="400" width="100%"  id='zzjgGrid2' datarender=""
						titledisplay="true" title="统计"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
						<!-- 表头 -->
						<div vtype="gridcolumn" name="grid_column" width="100%">
							<div>
								<div name='pripid'  width="1%"></div>
								<div name='regno' text="规则类型" textalign="center"  width="10%"></div>
								<div name='enttype' text="期初问题点" textalign="center"  width="10%"></div>
								<div name='regorg' text="期初问题点" textalign="center"  width="10%"></div>
								<div name='servicestate' text="修改数量" textalign="center"  width="10%"></div>
								<div name='estdate' text="核实数量" textalign="center"  width="10%"></div>
								<div name='industryco' text="留存问题数" textalign="center"  width="10%"></div>
								<div name='industryphy' text="检查点数量" textalign="center"  width="10%"></div>
								<div name='custom' text="数据处理率" textalign="center"  width="18%"></div>
							</div>
						</div>
						<!-- 表格 -->
						<!-- ../../grid/reg3.json -->
						<div vtype="gridtable" name="grid_table" ></div>
						<!-- 分页 -->
						<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
					</div>
		        </div> 
		        
		        <div id="tab2">    
		             <div vtype="formpanel" id="formpanel1" displayrows="1" name="formpanel1" titledisplay="true" width="100%" layout="table" 
						showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*'],border:false}" height="100" title=">>选择检查活动">
						<div dataurl="[{checked: true,value: '1',text: 'item1'},{value: '2',text: 'item2'}]" name='entname' vtype="comboxfield" label="活动期数" labelAlign="right" labelwidth='100px' width="410"></div>
					</div>
					<div vtype="gridpanel" name="zzjgGrid1" height="400" width="100%"  id='zzjgGrid1' datarender=""
						titledisplay="true" title="统计"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
						<!-- 表头 -->
						<div vtype="gridcolumn" name="grid_column" width="100%">
							<div>
								<div name='pripid'  width="1%"></div>
								<div name='regno' text="区县名称" textalign="center"  width="10%"></div>
								<div name='enttype' text="期初问题点" textalign="center"  width="10%"></div>
								<div name='regorg' text="期初问题点" textalign="center"  width="10%"></div>
								<div name='servicestate' text="修改数量" textalign="center"  width="10%"></div>
								<div name='estdate' text="核实数量" textalign="center"  width="10%"></div>
								<div name='industryco' text="留存问题数" textalign="center"  width="10%"></div>
								<div name='industryphy' text="检查点数量" textalign="center"  width="10%"></div>
								<div name='custom' text="数据处理率" textalign="center"  width="18%"></div>
							</div>
						</div>
						<!-- 表格 -->
						<!-- ../../grid/reg3.json -->
						<div vtype="gridtable" name="grid_table" ></div>
						<!-- 分页 -->
						<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
					</div>
		        </div>   
		    </div>    
		</div>
					   
	</div>
</body>
</html>