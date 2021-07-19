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
<style type="text/css">
.btn{
width:40px;
height:20px;
}
</style>
<script>
	function save() {
		parent.winEdit.window("close");
	}
	
	var update;
	$(function(){
		var priPid = jazz.util.getParameter("dmbId");
		update = jazz.util.getParameter("update");
		if(priPid != null){
			//$('#formpanel_edit .jazz-panel-content').loading();
			$("#formpanel_edit").formpanel('option', 'dataurl',rootPath+'/query/detail.do?priPid='+priPid);
			$("#formpanel_edit").formpanel('reload', "null", function(){$('#formpanel_edit .jazz-panel-content').loading('hide');});
		}
	});
	
	function back() {
		parent.winEdit.window("close");
	}
	function addAnd(){
		
	}
	function addOr(){
		
	}
</script>
</head>
<body>
	<div id="formpanel_edit" name="formpanel_edit" vtype="formpanel"
		tableStyleClass="tablecss" titledisplay="false" showborder="false" width="100%"
		layout="table" layoutconfig="{cols:2, columnwidth: ['50%','*']}"
		height="100%" dataurl="">
		<div name='pripid' vtype="hiddenfield" label="ID" labelAlign="right"
			rule="must" width="400" labelwidth="130"></div>
		<div name='regno' vtype="textfield" label="接口名称" labelAlign="right"
			rule="must" width="400" labelwidth="130"></div>
		<div name='entname' vtype="textareafield" label="接口说明" colspan="2" height="60"
			labelAlign="right" width="850" labelwidth="130"></div>
		<div name='n' vtype="textfield" label="接口内容配置" labelAlign="left"
			width="850" labelwidth="130" readonly="true" colspan="2"></div>
			
		<div name="tab_name" vtype="tabpanel" overflowtype="2" colspan="2" tabtitlewidth="130"  width="100%" height="760" 
			 orientation="top" id="tab_name">    
		    <ul>    
		        <li><a href="#tab1">配置查询范围</a></li>    
		        <li><a href="#tab2">配置查询条件</a></li>
		        <li><a href="#tab3">预览</a></li>     
		    </ul>    
		    <div>    
		        <div id="tab1">   
		        	<div id="column_id" width="98%" height="205" vtype="panel" name="panel" 
				     	 layout="column" layoutconfig="{width: ['22%','22%','22%','*','22%'],border:false}">
				        <div>
				    		<div name="w1" vtype="panel" title="选择业务系统" titledisplay="true" height="200">
				    			<div name='list1' id="listId1" vtype="boxlist" dataurl="data.json" height="100%" width="100%"></div>
				    		</div>
				    	</div>
				    	 
				    	 <div>
				    		<div name="w2" vtype="panel" title="选择主题" titledisplay="true" height="200">
				    			<div name='list2' id="listId2" vtype="boxlist" dataurl="data.json" height="100%" width="100%"></div>
				    		</div>
				    	</div>
				    	<div>
				    		<div name="w3" vtype="panel" title="选择数据表" titledisplay="true" height="200">
				    			<div name='list3' id="listId3" vtype="boxlist" dataurl="data.json" height="100%" width="100%"></div>
				    		</div>
				    	</div>
				    	<div>
				    		<div name="w4" vtype="panel" title="电影栏目" titledisplay="false" height="200">
				    			<div align="center" style="vertical-align: middle;padding:25px;">
						    		<input value=">" type="button" onlcick="" class="btn"/>
									<br/><br/>
		 							<input value=">>" type="button" onlcick="" class="btn"/>
		 							<br/><br/>
		 							<input value="<<" type="button" onlcick="" class="btn"/>
		 							<br/><br/>
		 							<input value="<" type="button" onlcick="" class="btn"/>
	 							</div>
				    		</div>
				    	</div>
				    	<div>
				    		<div name="w5" vtype="panel" title="已选数据表" titledisplay="true" height="200">
				    			<div name='list4' id="listId4" vtype="boxlist" dataurl="data.json" height="100%" width="100%"></div>
				    		</div>
				    	</div>
				    </div> 
		        	
					<div name='v' vtype="textfield" label="配置表间关系" labelAlign="left"
						width="850" labelwidth="130" readonly="true" colspan="2"></div>
						
					<table>
						<tr>
							<td><div name='y' vtype="comboxfield" label="表选择" dataurl="[{checked: true,value: '1',text: '登记信息表'},{value: '2',text: '主要人员信息表'}]" labelAlign="right" labelwidth='100px' width="250"></div></td>
							<td></td>
							<td><div name='i' vtype="comboxfield" label="关联表选择" labelAlign="right"  labelwidth='100px' width="250" dataurl="[{checked: true,value: '1',text: '企业基本信息表'},{value: '2',text: '主要人员信息表'},{value: '3',text: '归档'}]" ></div></td></tr>
						<tr>
							<td><div name='o' vtype="comboxfield" label="关联关系" labelAlign="right"  labelwidth='100px' width="250" dataurl="[{checked: true,value: '1',text: 'ID号'},{value: '2',text: '企业名称'},{value: '3',text: '注册号'}]" ></div></td>
							<td><div name='p' vtype="comboxfield" label="条件" labelAlign="right"  labelwidth='100px' width="250" dataurl="[{checked: true,value: '1',text: '等于'},{value: '2',text: '左连接'}]" ></div></td>
							<td><div name='l' vtype="comboxfield" labelAlign="right" labelwidth='100px' width="250" dataurl="[{checked: true,value: '1',text: 'ID号'},{value: '2',text: '企业名称'},{value: '3',text: '注册号'}]" ></div></td></tr>
					</table>
					
					<div vtype="gridpanel" name="zzjgGrid" height="200" width="100%"  id='zzjgGrid' datarender=""
						titledisplay="false" title="查询列表"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
						<div name="toolbar" vtype="toolbar">
							<div name="btn1" align="left" vtype="button" text="添加为“并且”关系" click="addAnd()"></div>
							<div name="btn2" align="left" vtype="button" text="添加为“或者”关系" click="addOr()"></div>
				    	</div>
						<!-- 表头 -->
						<div vtype="gridcolumn" name="grid_column" width="85%">
							<div>
								<div name='pripid' width="1%" ></div>
								<div name='regno' text="主表" textalign="center"  width="10%"></div>
								<div name='entname' text="从表" textalign="center"  width="10%"></div>
								<div name='regorg' text="关联数据项" textalign="center"  width="15%"></div>
								<div name='enttype' text="关联条件" textalign="center"  width="15%"></div>
								<div name='custom' text="逻辑关系" textalign="center"  width="10%"></div>
								<div name='custom' text="备注" textalign="center"  width="20%"></div>
							</div>
						</div>
						<!-- 表格 -->
						<!-- ../../grid/reg3.json -->
						<div vtype="gridtable" name="grid_table" ></div>
						<!-- 分页
						<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div> -->
					</div>
		       
		        </div>    
		        <div id="tab2">    
		             <div id="formpanel" name="formpanel" vtype="formpanel"
						tableStyleClass="tablecss" titledisplay="false" showborder="true" width="98%"
						layout="table" layoutconfig="{cols:6, columnwidth: ['15%','18%','22%','15%','15%','*'],border:false}"
						height="120" dataurl="" >
				
						<div name='pripid' vtype="hiddenfield" label="ID" labelAlign="right"
							 width="400" labelwidth="130"></div>
						<div name='nb' vtype="textfield" label="配置查询条件" labelAlign="left"
							 width="850" labelwidth="130" readonly="true" colspan="6"></div>
							 
						<div name='mn' vtype="comboxfield" label="括弧" dataurl="[{checked: true,value: '1',text: '('},{value: '2',text: '(('},{value: '3',text: '((('}]"
							labelAlign="right" width="120" labelwidth="30" ></div>
							
						<div name='nu' vtype="comboxfield" label="选择表" labelAlign="right" dataurl='[{"text":"登记信息表","value":"1"},{"text":"主要人员信息表","value":"2"}]'
							width="150" labelwidth="50" ></div>
							
						<div name='io' vtype="comboxfield" label="选择数据项" dataurl='[{"text":"ID号","value":"1"},{"text":"企业名称","value":"2"},{"text":"注册号","value":"3"}]'
							labelAlign="right" width="200" labelwidth="80" ></div>
							
						<div name='uj' vtype="comboxfield" label="条件" dataurl="[{checked: true,value: '1',text: '等于'},{value: '2',text: '不等于'},{value: '3',text: '大于'}]"
							labelAlign="right" width="120" labelwidth="30" ></div>
							
						<div name='rt' vtype="textfield" label="值" 
							labelAlign="right" width="120" labelwidth="30" ></div>
							
						<div name='gb' vtype="comboxfield" label="括弧" dataurl="[{checked: true,value: '1',text: ')'},{value: '2',text: '))'},{value: '3',text: ')))'}]"
							labelAlign="right" width="120" labelwidth="30" ></div>
					</div>
					
					<div vtype="gridpanel" name="zzjgGrid1" height="200" width="100%"  id='zzjgGrid1' datarender=""
						titledisplay="false" title="查询列表"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
						<div name="toolbar" vtype="toolbar">
							<div name="btn1" align="left" vtype="button" text="添加条件" click="addAnd()"></div>
				    	</div>
						<!-- 表头 -->
						<div vtype="gridcolumn" name="grid_column" width="85%">
							<div>
								<div name='pripid' width="1%" ></div>
								<div name='regno' text="逻辑关系" textalign="center"  width="10%"></div>
								<div name='entname' text="表名" textalign="center"  width="10%"></div>
								<div name='regorg' text="数据项" textalign="center"  width="15%"></div>
								<div name='enttype' text="条件" textalign="center"  width="15%"></div>
								<div name='custom' text="值" textalign="center"  width="10%"></div>
								<div name='custom' text="备注" textalign="center"  width="20%"></div>
							</div>
						</div>
						<!-- 表格 -->
						<!-- ../../grid/reg3.json -->
						<div vtype="gridtable" name="grid_table" ></div>
						<!-- 分页
						<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div> -->
					</div>
		        </div>  
		        <div id="tab3">    
		             <table border="1" style="text-align: center;vertical-align: middle;padding: 25px;margin-left: 50px;margin-top: 50px;width: 80%;height: 30%;">
		             	<tr><td>选择表</td><td>企业基本信息表</td></tr>
		             	<tr><td>查询条件</td><td>ID号等于10000</td></tr>
		             	<tr><td>显示结果</td><td>表.字段，表.字段</td></tr>
		             	<tr><td>查询结果</td><td>记录总数：1112</td></tr>
		             </table>
		        </div>  
		    </div>    
		</div>
		<div name="checkboxfield_name" vtype="checkboxfield" labelalign="right" width="200"
						dataurl="[{checked: true,value: '1',text: '保存后立即启动'}]"></div>
		
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div id="btn1" name="btn1" vtype="button" text="保存"
				icon="../../../style/default/images/fh.png" click="back()"></div>
			<div id="btn2" name="btn2" vtype="button" text="返回"
				icon="../../../style/default/images/fh.png" click="back()"></div>
		</div>
	</div>
</body>
</html>