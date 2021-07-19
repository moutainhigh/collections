<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html  style="height:100%;width: 100%;">
<head>
	<title>企业族谱</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
    <%-- <script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
    <link rel="icon" href="<%=request.getContextPath()%>/static/images/system/index/favicon.ico" type="image/x-icon">
	<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/static/images/system/index/favicon.ico" media="screen" /> --%>

<style type="text/css">
table{
	font-family:微软雅黑,宋体;
	font-size:12;
 	border-top:solid 1px #333333;
 	border-collapse: collapse;
 	width:100%;
	height:100%;
}
tr{
	height:25px;
}
td{
 	height:25px;
}
.row1{
 	background:#eff6fa;
 	width:100%;
}
.row2{
 	background:#fbfdfd;
 	width:100%;
}
td{
 	height:25px;
}
.col1{
	font-weight:bold;
	text-align:right;
 	width:30%;
}
.col2{
	padding-left:1px;
 	width:70%;
}
</style>
</head>
<body style="height:100%;width: 100%;overflow:auto; ">
    <!--Step:1 为ECharts准备一个具备大小（宽高）的Dom-->
      <div id="Header" style="background: #1c97ca">
        <div class="headertop"  style="height:50px; width:100%;">
			 <img alt="" style="position:absolute;z-index:2;margin-left: 10px;" height="37" src="<%=request.getContextPath()%>/page/sjbd/img/12.png">
			<div class='header' style="position:absolute;z-index:10;">
				<ul id="nav" >
				</ul>
			</div>
		</div>
    </div>
    <div id="mainforce" style="height:1500px;border:0px solid #ccc;padding:0px;"></div>
    <div><p style="width:100%;height:30px;line-height:30px; text-align:center;vertical-align: middle;font-family: '微软雅黑,宋体';color: white-space: border;font-size: 14px;color: white;background-color: #26363b;position: fixed;bottom: 0px;z-index:100;">版权所有：广东省工商行政管理局  地址：广州市天河区体育西路57号红盾大厦  邮编：510620  技术支持：长城计算机软件与系统有限公司   ICP备案号：粤ICP备05028****号*1   网站备案编码：44010****1622</p></div>
    <!--Step:2 引入echarts.js-->
    <script src="echarts.js"></script>
    <script type="text/javascript">
    	var nodes=[];
    	var links=[];
    	var pripid;
   		var entname;
   		var executionTimes = 0;
    	$(function(){
    		$("#mainforce").loading();
    		$("#mainforce").loading("show");
    		pripid=getUrlParam('priPid');
       		entname=getUrlParam('entname');
       		initChart("\'"+pripid+"\'");
       	    /* $.getJSON(url,function(data){
       	        
       	    }); */
    	});
    	function initChart(allPripid){
    		if(allPripid==""){
    			return;
    		}
    		executionTimes ++;
       	    var url = '/gdsjzx/datatrs/queryGenealogyDdataNew1.do';
       	    $.ajax({
	     		url:url,
	     		data:{pripid:pripid,entname:entname,allPripid:allPripid,findGen:executionTimes},
	     		type:"post",
	     		dataType : 'json',
	     		success:function(data){
	     			console.info(data);
	     			var Lnodes=data.nodes;
	     			var lnodesPripid = "";
	       		    for(var i=0;i<Lnodes.length;i++){
	       		        var nd=Lnodes[i];
	       		        nd.category=nd.category-1;
	       		          //nd.value=5;
	       		        nodes.push(nd);
	       		        if(executionTimes>1){
	       		        	lnodesPripid +="\'"+nd.pripid+"\',";
	       		        }else{//除去
	       		        	if(pripid!=nd.pripid){
	       		        		lnodesPripid +="\'"+nd.pripid+"\',";
       		        		}
	       		        }
	       		    }
	       	     	var linksSr=data.links;
	       	     	for(var i=0;i<linksSr.length;i++) {
	       	        	var s=node_indx(linksSr[i].source);
	       	            var t=node_indx(linksSr[i].target);
	       	            if(typeof s === 'undefined'){

	       	            }else if(typeof t === 'undefined'){

	       	            }else{
	       	            	linksSr[i].source=s;
	       	            	linksSr[i].target=t;
	       	            	links.push(linksSr[i]);
	       	            }
	       	      	}
	       	     	initFroces(lnodesPripid);
	       	     	$("#mainforce").loading("hide");
	     		},
	     		error: function(e) { 
	     			$("#mainforce").loading("hide");
	     			//$("#tab1").loading("hide");
	     		} 
	     	});
    	}
    	function getUrlParam(name) {
    	    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    		var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    		if (r != null) return unescape(decodeURI(r[2])); return null; //返回参数值 
    	    //escape() encodeURI() encodeURIComponent() decodeURI() 
    	}
   		
   		function node_indx(pid){
	      	for(var i=0;i<nodes.length;i++){
		        if(pid==nodes[i].pripid){
		        	return i;
		        }
	      	}
		}
    
    // Step:3 conifg ECharts's path, link to echarts.js from current page.
    // Step:3 为模块加载器配置echarts的路径，从当前页面链接到echarts.js，定义所需图表路径
    require.config({
        paths: {
            echarts: '.'
        }
    });
    // Step:4 require echarts and use it in the callback.
    // Step:4 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
    function initFroces(lnodesPripid){
	    var ecConfig = require(
	        [
	            'echarts',
	            'echarts/chart/bar',
	            'echarts/chart/line',
	            'echarts/chart/map',
	            'echarts/chart/chord',
	            'echarts/chart/force'
	        ],
	        function (ec) {
				var myChart3 = ec.init(document.getElementById('mainforce'));
				option = {
	            	    title : {
	            	        text: entname,//nodes[0].name,
	            	        //subtext: '数据来自人立方',
	            	        x:'left',
	            	        y:'top'
	            	    },
	            	    tooltip : {
	            	        trigger: 'item',
	            	        formatter: '{b}'//'{a} : 
	            	    },
	            	    toolbox: {
	            	        show : true,
	            	        feature : {
	            	            restore : {show: true},
	            	            magicType: {show: true, type: ['force', 'chord']},
	            	            saveAsImage : {show: true}
	            	        }
	            	    },
	            	    legend: {
	            	        x: 'left',
	            	        y:'35',
	            	        data:['当前企业','自然人','法人']
	            	    },
	            	    series : [
	            	        {
	            	            type:'force',
	            	            name : "族谱关系",
	            	            ribbonType: false,
	            	            categories : [
	            	                {
	            	                    name: '法人'
	            	                },
	            	                {
	            	                    name: '当前企业',
	            	                    symbol: 'diamond'
	            	                },
	            	                {
	            	                    name:'自然人'
	            	                }
	            	            ],
	            	            itemStyle: {
	            	                normal: {
	            	                    label: {
	            	                        show: true,
	            	                        textStyle: {
	            	                            color: '#333'
	            	                        }
	            	                    },
	            	                    nodeStyle : {
	            	                        brushType : 'both',
	            	                        borderColor : 'rgba(255,215,0,0.4)',
	            	                        borderWidth : 1
	            	                    }
	            	                },
	            	                emphasis: {
	            	                    label: {
	            	                        show: false
	            	                        // textStyle: null      // 默认使用全局文本样式，详见TEXTSTYLE
	            	                    },
	            	                    nodeStyle : {
	            	                        //r: 30
	            	                    },
	            	                    linkStyle : {}
	            	                }
	            	            },
	            	            minRadius : 15,
	            	            maxRadius : 25,
	            	            gravity: 3.1,
	            	            scaling: 2.5,
	            	            draggable: true,
	            	            linkSymbol: 'arrow',
	            	            steps: 10,
	            	            coolDown: 0.9,
	            	            //preventOverlap: true,
	            	            nodes:nodes,
	            	            links : links
	            	        }
	            	    ]
	            	};
           		myChart3.setOption(option);
            	var ecConfig = require('echarts/config');
            	myChart3.on(ecConfig.EVENT.CLICK, focus);//点击事件
            	function focus(param) {
            		var data = param.data;
            		//console.log(data);
            		$.ajax({
            			url:rootPath+'/datatrs/zpOverview.do',
         	     		data:{
         	     			pripid : data.pripid,
         	   				name : data.name,
         					category : data.category
         				},
         	     		type:"post",
         	     		dataType : 'json',
         	     		success:function(datas){
         	     			win = jazz.widget({
                    			vtype: 'window',
                    			name: 'win',
                    	    	title: data.name,
                    	    	titlealign: 'center',
                    	    	titledisplay: true,
                    	        width: 400,
                    	        height: 260,
                    	        visible: true,
                    		    modal: true,
                    		    content:"<div style='background-color: #fff;border: 0px solid #ddd;width:100%;height:100%;'>"+datas.returnString+"</div>"
                    		});
         	     		},
        	     		error: function(e) { 
        	     			//$("#mainforce").loading("hide");
        	     		} 
        	     	});
            	}
            	myChart3.on(ecConfig.EVENT.DBLCLICK, doubleclick);//双击事件
            	function doubleclick(param){
            		var data = param.data;
            	    window.open(encodeURI('/gdsjzx/page/trs/detail.jsp?pripid='+data.pripid+'&name='+data.name+'&category='+data.category));//修改
            	}
            	if(executionTimes<3){
            		initChart(lnodesPripid);
            	}
	        }
	    );
    }
    
    
   	function alertContent(LeanA,LeanB,LeanC){
   	  	$(LeanB).hide();
   	  	$(LeanA).click(function(){
   	  		$(LeanB).fadeIn(200); 
   		});
   	  	$(LeanC).click(function(){
   	  		$(LeanB).fadeOut(200); 
   		});
   	}
    </script>
</body>
</html>