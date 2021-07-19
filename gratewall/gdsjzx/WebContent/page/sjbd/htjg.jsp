<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>合同监管信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>

<style type="text/css">

    td{
		text-align: center;
	}
.jazz-pagearea{
	height: 0px;
}

</style>

<script>

	//http://localhost:8088/sjbd/datacompre/returnHistoryExsists.do?entityNo=c7f9f0e9-013d-1000-e000-184e0a0e0115

  	 $(function(){
  		var entityNo=getUrlParam("entityNo");
  		//获取传递过来的参数，进行初始化请求
  		if(entityNo!=null){
  			$("#zzjgGrid").gridpanel("hideColumn", "modfydate");
  			$("#zzjgGrid").gridpanel("showColumn", "approvedate");
 			queryHistory(entityNo);
  		}
 	}); 
	function queryUrl() {
		$("#zzjgGrid").gridpanel("hideColumn", "approvedate");
			$("#zzjgGrid").gridpanel("showColumn", "modfydate");
		$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+
				'/datacompre/queryZzjgDm.do');
		$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
	}
	
	function queryHistory(entityNo){
		$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+
		'/datacompre/returnHistoryExsists.do?entityNo='+entityNo);
		$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
	}

	function reset() {
		$("#formpanel").formpanel('reset');
	}
	
	 function getUrlParam(name) {
         var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
         var r = window.location.search.substr(1).match(reg);  //匹配目标参数
         if (r != null) return unescape(r[2]); return null; //返回参数值
     }

	function returnOrgNo(orgno){
		//路径  rootPath+'/datacompre/writeZzjgDm.do'
		 $.ajax( {    
		    url:rootPath+'/datacompre/writeZzjgDm.do',//、  
		    data:{    
		    	orgno:orgno  
		    },    
		    type:'post',  
		    cache:false,    
		    dataType:'json',    
		    success:function(data) {    
		        
		     },    
		     error : function() {    
		          // view("异常！");    
		          alert("异常！");    
		     }    
		});  
	} 
	


</script>
</head>
<body>
<div name="tab_name" vtype="tabpanel" overflowtype="2" tabtitlewidth="130"  width="100%" height="100%" 
			 orientation="top" id="tab_name">    
		    <ul>    
		        <li><a href="#tab1">经纪人查询</a></li>    
		        <li><a href="#tab2">经纪人员查询</a></li>    
		        <li><a href="#tab3">动产抵押查询</a></li>
		    </ul>    
		    <div>    
		        <div id="tab1">    
		     
		     <div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
				titledisplay="true" width="100%" layout="table"  showborder="false"
				layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="150"
				title="查询条件">
		
				<div name='zzjgdm' vtype="textfield" label="主体名称" labelAlign="right" labelwidth='100px' width="410"></div>
				<div name='qymc' vtype="comboxfield" label="经纪人类型" labelAlign="right"  labelwidth='100px' width="410"></div>
				<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
					<div name="query_button" vtype="button" text="查询" 
						icon="../query/queryssuo.png" onclick="queryUrl();"></div>
					<div name="reset_button" vtype="button" text="重置"
						icon="../query/queryssuo.png" click="reset();" ></div>
				</div>
			</div>
		
			<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid'
				titledisplay="true" title="经纪人信息"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column" width="100%">
					<div>
						<div name='orgno' text="主体名称" textalign="center" width="20%" ></div>
						<div name='registerno' text="注册号" textalign="left" width="20%"></div>
						<div name='principal' text="经纪人类型" textalign="left" width="10%"></div>
						<div name='corpname' text="经营范围" textalign="left" width="40%"></div>
					</div>
				</div>
				<!-- 表格 -->
				<!-- ../../grid/reg3.json -->
				<div vtype="gridtable" name="grid_table"></div>
				<!-- 分页 -->
				<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
			</div>
		      
		        </div>    
		        <div id="tab2">    
		                
<div vtype="formpanel" id="formpanel1" displayrows="1" name="formpanel1"
				titledisplay="true" width="100%" layout="table"  showborder="false"
				layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="200"
				title="查询条件 ">
		
				<div name='zzjgdm' vtype="textfield" label="经纪人员姓名" labelAlign="right" labelwidth='100px' width="410"></div>
				<div name='qymc' vtype="comboxfield" label="证件类型" labelAlign="right"  labelwidth='100px' width="410"></div>
				<div name='zgzsh' vtype="textfield" label="资格证书号" labelAlign="right" labelwidth='100px' width="410"></div>
				<div name='zjhm' vtype="textfield" label="证件证号" labelAlign="right" labelwidth='100px' width="410"></div>
				<div id="toolbar1" name="toolbar" vtype="toolbar" location="bottom" align="center">
					<div name="query_button" vtype="button" text="查询" 
						icon="../query/queryssuo.png" onclick="queryUrl();"></div>
					<div name="reset_button" vtype="button" text="重置"
						icon="../query/queryssuo.png" click="reset();" ></div>
				</div>
			</div>
		
			<div vtype="gridpanel" name="zzjgGrid1" height="400" width="100%"  id='zzjgGrid1'
				titledisplay="true" title="经纪人员信息"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column" width="100%">
					<div>
						<div name='orgno' text="姓名" textalign="center" width="20%" ></div>
						<div name='registerno' text="证件类型" textalign="left" width="20%"></div>
						<div name='principal' text="证件证号" textalign="left" width="10%"></div>
						<div name='corpname' text="资格证书号" textalign="left" width="30%"></div>
						<div name='corpname1' text="证书类别" textalign="left" width="20%"></div>
					</div>
				</div>
				<!-- 表格 -->
				<!-- ../../grid/reg3.json -->
				<div vtype="gridtable" name="grid_table"></div>
				<!-- 分页 -->
				<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
			</div>
		        </div>    
		        <div id="tab3">    
		  
<div vtype="formpanel" id="formpanel2" displayrows="1" name="formpanel2"
				titledisplay="true" width="100%" layout="table"  showborder="false"
				layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="200"
				title="查询条件 ">
		
				<div name='zzjgdm' vtype="textfield" label="抵押人姓名" labelAlign="right" labelwidth='100px' width="410"></div>
				<div name='qymc' vtype="comboxfield" label="主债权类型" labelAlign="right"  labelwidth='100px' width="410"></div>
				<div name='zgzsh' vtype="textfield" label="抵押权人姓名" labelAlign="right" labelwidth='100px' width="410"></div>
				<div id="toolbar2" name="toolbar" vtype="toolbar" location="bottom" align="center">
					<div name="query_button" vtype="button" text="查询" 
						icon="../query/queryssuo.png" onclick="queryUrl();"></div>
					<div name="reset_button" vtype="button" text="重置"
						icon="../query/queryssuo.png" click="reset();" ></div>
				</div>
			</div>
		
			<div vtype="gridpanel" name="zzjgGrid2" height="400" width="100%"  id='zzjgGrid2'
				titledisplay="true" title="动产抵押信息"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column" width="100%">
					<div>
						<div name='orgno' text="抵押人姓名" textalign="center" width="20%" ></div>
						<div name='registerno' text="主债权类型" textalign="left" width="20%"></div>
						<div name='principal' text="抵押登记时间" textalign="left" width="30%"></div>
						<div name='corpname' text="是否注销" textalign="left" width="10%"></div>
						<div name='corpname1' text="抵押权人姓名" textalign="left" width="20%"></div>
					</div>
				</div>
				<!-- 表格 -->
				<!-- ../../grid/reg3.json -->
				<div vtype="gridtable" name="grid_table"></div>
				<!-- 分页 -->
				<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
			</div>

 
		        </div> 
		    </div>    
		</div>



			


</body>
</html>