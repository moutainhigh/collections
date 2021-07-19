<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>专项整治信息</title>
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


		
			<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
				titledisplay="true" width="100%" layout="table"  showborder="false"
				layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="200"
				title="查询条件">
		
				<div name='zzjgdm' vtype="textfield" label="标题" labelAlign="right" labelwidth='100px' width="410"></div>
				<div name='qymc' vtype="textfield" label="发起单位" labelAlign="right"  labelwidth='100px' width="410"></div>
				<div name='zch' vtype="datefield" label="发起日期" dataurl="[{checked: true,value: '1',text: '分公司'},{value: '2',text: '总公司'},{value: '3',text: '集团'}]"  labelAlign="right"  labelwidth='100px' width="410"></div>
				<div name='ztfl' vtype="datefield" label="结束日期" dataurl="[{checked: true,value: '1',text: '分公司'},{value: '2',text: '总公司'},{value: '3',text: '集团'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
				<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
					<div name="query_button" vtype="button" text="查询" 
						icon="../query/queryssuo.png" onclick="queryUrl();"></div>
					<div name="reset_button" vtype="button" text="重置"
						icon="../query/queryssuo.png" click="reset();" ></div>
				</div>
			</div>
		
			<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid'
				titledisplay="true" title="专项整治信息"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column" width="100%">
					<div>
						<div name='orgno' text="标题" textalign="center" width="20%" ></div>
						<div name='registerno' text="发起单位" textalign="left" width="10%"></div>
						<div name='principal' text="发起日期" textalign="left" width="20%"></div>
						<div name='corpname' text="结束日期" textalign="left" width="25%"></div>
						<div name='subentitytype' text="发起内容" textalign="center" width="20%" ></div>
						
					</div>
				</div>
				<!-- 表格 -->
				<!-- ../../grid/reg3.json -->
				<div vtype="gridtable" name="grid_table"></div>
				<!-- 分页 -->
				<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
			</div>


</body>
</html>