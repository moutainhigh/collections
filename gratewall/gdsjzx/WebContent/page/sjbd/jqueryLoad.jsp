<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>案件信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>

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

  	var totalheight = 0;     //定义一个总的高度变量
function loaata()
{ 
    totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());     //浏览器的高度加上滚动条的高度 

    if ($(document).height() <= totalheight)     //当文档的高度小于或者等于总的高度的时候，开始动态加载数据
    { 
        //加载数据
        $("#panel_1").append("<div style='border: 1px solid red;'><h2>面</h2><P> 这是一个简单的手风琴组件</P></div>");
    } 
} 
$(window).scroll( function() { 
    //console.log("滚动条到顶部的垂直高度: "+$(document).scrollTop()); 
    //console.log("页面的文档高度 ："+$(document).height());
    //console.log('浏览器的高度：'+$(window).height());
    loaata();
}); 
	


	

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
<div id="panel_1" vtype="panel" name="panel_1" title="panel的标题" titledisplay="false" closable="true" 
		width="100%" height="100%">  
<div style="border: 1px solid red;">
		 <h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
<div style="border: 1px solid red;">
		 <h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
		<div style="border: 1px solid red;">
		 <h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
		<div style="border: 1px solid red;">
		 <h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
		<div style="border: 1px solid red;">
		 <h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
		<div style="border: 1px solid red;">
		 <h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
		<div style="border: 1px solid red;">
		 <h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
		<div style="border: 1px solid red;">
		 <h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
		<div style="border: 1px solid red;">
		 <h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
		<div style="border: 1px solid red;">
		 <h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
		<div style="border: 1px solid red;">
		 <h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
		
		<div style="border: 1px solid red;">
		 <h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
<div style="border: 1px solid red;">
		 <h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
		<div style="border: 1px solid red;">
		 <h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
		<div style="border: 1px solid red;">
		 <h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
		<div style="border: 1px solid red;">
		 <h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
		<div style="border: 1px solid red;">
		 <h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
		<div style="border: 1px solid red;">
		 <h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
		<div style="border: 1px solid red;">
		 <h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
		<div style="border: 1px solid red;">
		 <h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
		<div style="border: 1px solid red;">
		 <h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
		<div style="border: 1px solid red;">
		 <h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
	</div>
	<!-- <div id="container" name="row_id" height="100%" vtype="panel" layout="row" layoutconfig="{rowheight:['20%','40%','*']}">
		<div style="border: 1px solid red;">335</div>
		<div style="border: 1px solid red;">35</div>
		<div style="border: 0px solid red;">
		 <h2>面板一</h2>
	     <P> 这是一个简单的手风琴组件，点击标题部分，会展开当前面板，并且收缩之前打开的面板。哈哈哈哈哈哈哈哈哈</P>
		</div>
	</div> -->


</body>
</html>