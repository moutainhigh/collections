<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<!DOCTYPE html PUBLIC>
<html> 
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

		<!-- Title -->
		<title>深圳市市场和质量监督管理委员会数据交换平台关系图</title>
		<!-- Stylesheet -->
		<link rel="stylesheet" href="/page/zwt/wp-content/themes/conversation_theme/style.css" type="text/css" />
		<script type="text/javascript" src="/page/zwt/js/jquery.min.js" charset="utf-8"></script>
    
		<!-- Style settings -->
		
		<style type="text/css" media="all">
html {
	font-size: 16px;
}
body,div,p{padding:0;}
h1,h2,h3,h4,h5,h6,dl dt,blockquote,blockquote blockquote blockquote,#site-title,#menu-primary li a
	{
	font-family: Abel, sans-serif;
}

a,a:visited,.page-template-front .hfeed-more .hentry .entry-title a:hover,.entry-title a,.entry-title a:visited
	{
	color: #0da4d3;
}

a:hover,.comment-meta a,.comment-meta a:visited,.page-template-front .hentry .entry-title a:hover,.archive .hentry .entry-title a:hover,.search .hentry .entry-title a:hover
	{
	border-color: #0da4d3;
}

.read-more,.read-more:visited,.pagination a:hover,.comment-navigation a:hover,#respond #submit,.button,a.button,#subscribe #subbutton,.wpcf7-submit,#loginform .button-primary
	{
	background-color: #0da4d3;
}

a:hover,a:focus {
	color: #000;
}

.pic_tilte{
	color: white;
	padding-top: 90px;
	text-align: center;
	font-size: 12px;
}

.read-more:hover,#respond #submit :hover,.button:hover,a.button:hover,#subscribe #subbutton
	:hover,.wpcf7-submit:hover,#loginform .button-primary:hover {
	background-color: #111;
}
#screen span { 
        position:absolute; 
        overflow:hidden; 
        border:#FFF solid 1px; 
        background:#FFF; 
    } 
    #screen img{ 
        position:absolute; 
        left:-32px; 
        top:-32px; 
        cursor: pointer; 
    } 
    #caption, #title{ 
        color: #FFF; 
        font-family: georgia, 'times new roman', times, veronica, serif; 
        font-size: 1em; 
        text-align: center; 
    } 
    #caption b { 
        font-size: 2em; 
    } 
	#allZwt a {
		text-decoration:none;
		color:#fafafa;
	}    
</style>



	</head>
     
	<body>
	<form >
	
	<div style="padding:5px;font-size:12px;line-height:1.3; position:absolute; right:120px; top:20px;text-align:left; ">
	 <strong>区县分局：</strong><input id="qx"  onclick="msg('qx')" type="checkbox" name="sObject" checked=true value="qx"/></br>
	 <strong>内部系统：</strong><input id="nb" onclick="msg('nb')" type="checkbox" name="sObject" checked=true value="nb"/></br>
	 <strong>外部系统：</strong><input id="wb" onclick="msg('wb')" type="checkbox" name="sObject" checked=true value="wb"/></br>
	</div>
	<div style="padding:5px;font-size:12px;line-height:1.3;  right:120px; top:20px;text-align:left; ">
	<strong>接口名称：</strong><input id="interName"  type="text" name="iObject"  ></input></br>
	<input  onclick="msg('interface')" type="button" value="查询"  ></input> 
	<input  onclick="reset()" type="button" value="重置"  ></input> 
	 <br/>
	</div>
	<!--
	<div id="allZwt" style="padding:5px;font-size:12px;line-height:1.3; color:#fafafa;background-color:#8AB0D7;position:absolute; left:20px; top:90px;text-align:left;">
		 <a href="#" onclick="_winOpen('zwtDetail.jsp')">查看全部</a><br/>
		
		 <a href="#" onclick="_winOpen('systablein.jsp')">系统-表-接口</a><br/>
		 <a href="#" onclick="_winOpen('tableinobject.jsp')">表-接口-服务对象</a><br/>
	</div>	
	--> 
	<div id="container" style="width:800px;height:500px;margin:0 auto;">
	</div>
	<script type="text/javascript" src="/page/zwt/js/echarts-all.js" charset="utf-8"></script>
	<script type="text/javascript" src="/page/zwt/ECharts_files/esl.js" charset="utf-8"></script>
    <!-- <script type="text/javascript" src="/page/zwt/ECharts_files/echartsExample.js" charset="utf-8"></script>
     --><script>
       var myChart = echarts.init(document.getElementById('container'));
        
        myChart.showLoading({
            text: '正在努力的读取数据中...'
        });
        msg('init');
        
       
        function linkFun(param){
        	
       	 	var href="";
       	 	if(param.data.category=='2')//基础接口
       	 	{
       	 		href="/txn401010.do?primary-key:interface_id="+param.data.pid;
       	 	}else if(param.data.category=='1'){//服务对象
       	 		href="/txn201009.do?primary-key:service_targets_id="+param.data.pid;
       	 	}
        	
        	window.showModalDialog(href, window, "dialogHeight:350px;dialogWidth:700px;dialogTop:250px;dialogLeft:300px;help:no;scrollbar:no;resizable:no;status:no");
        }
        
        function msg(vv){
        	
        	var qx = document.getElementById("qx").checked;//区县
        	var nb = document.getElementById("nb").checked;//内部系统
        	var wb = document.getElementById("wb").checked;//外部系统
        	//alert(document.getElementById("wb").);
        	
        	if(!(qx||nb||wb)){
        		alert("请至少选则一个过滤条件！");
        		if(vv!='init'){
        			if(vv=="interface"){
        				
        			}else{
        				document.getElementById(vv).checked=true;
        			}
        			
        		}
        		//return;
        	}
        	
        	qx = document.getElementById("qx").checked;//区县
        	nb = document.getElementById("nb").checked;//内部系统
        	wb = document.getElementById("wb").checked;//外部系统
        	
        	var iname = document.getElementById("interName").value;
        	
        	var url="";
        	if(iname!=""){
        		url="select-key:iname="+iname;
        	}
        	
        	if(qx){
        		if(url!=""){
        			url=url+"&select-key:qx=1";
        		}else{
        			url="select-key:qx=1";
        		}
        		
        	}else{
        		if(url!=""){
        			url=url+"&select-key:qx='";
        		}else{
        			url="select-key:qx=0";
        		}
        	}
        	if(nb){
        		if(url!=""){
        			url=url+"&select-key:nb=1";
        		}else{
        			url="select-key:nb=1";
        		}
        		
        	}else{
        		if(url!=""){
        			url=url+"&select-key:nb=0";
        		}else{
        			url="select-key:nb=0";
        		}
        	}
        	if(wb){
        		if(url!=""){
        			url=url+"&select-key:wb=1";
        		}else{
        			url="select-key:wb=1";
        		}
        		
        	}else{
        		if(url!=""){
        			url=url+"&select-key:wb=0";
        		}else{
        			url="select-key:wb=0";
        		}
        	}
        	url="/txn6030013.ajax?"+url;
        	//alert('url='+url);
        	$.ajax({
    			type : "post",
    			url:url,
    			//url : "/txn6030013.ajax?select-key:qx="+qx+"&select-key:nb="+nb+"&select-key:wb="+wb,
    			
    			async : false,
    			
    			success : function(xmlResults) {
    				var strNodes;
    				var strLinks;
    				
    				if (xmlResults.selectSingleNode("//context/error-code").text != "000000") {
    					alert(xmlResults.selectSingleNode("//context/error-desc").text);
    					return false;
    				} else {
    					
    					strNodes = eval(xmlResults.selectSingleNode("//context/record/strNodes").text);
    					strLinks = eval(xmlResults.selectSingleNode("//context/record/strLinks").text);
    					
    					
    				}
    				//alert("strNodes="+strNodes);
    				//alert("strLinks="+strLinks);
					if(strLinks==""){
						alert('没有符合条件的数据，请检查您的查询条件!');
						return;
					}
    				
					var option = {
	            			title : {
	            		        text: '接口与服务对象关系图',
	            		        x:'center',
	            		        y:'top'
	            		    },
	            		    tooltip : {
	            		        trigger: 'item',
	            		        formatter: function (params) {
	            		            if (params.indicator2) {    // is edge
	            		                return params.indicator2 + ' ' + params.name + ' ' + params.indicator;
	            		            } else {    // is node
	            		                return params.name
	            		            }
	            		        }
	            		    },
	            		    legend: {
	            		        x: 'left',
	            		        selectedMode:false,
	            		        data:['服务对象', '基础接口']
	            		    },
	                	    series : [{
	                	    	name: '服务对象与接口关系图',
	                            type:'chord',
	                            sort : 'ascending',
	                            sortSub : 'descending',
	                            ribbonType: false,
	                            categories:[
	                            {
	                               name:'关系图'
	                            },
	                            {
	                               name:'服务对象'
	                            },
	                            {
	                               name:'基础接口'
	                            }
	                            ],
	                            radius: '60%',
	                            itemStyle : {
	                                normal : {
	                                    label : {
	                                        rotate : true
	                                    }
	                                }
	                            },
	                            minRadius: 7,
	                            maxRadius: 20,
	                            nodes : strNodes,
	                            links : strLinks
	                        }
	                	           ]
	                	    
	                	};
	            	
	            	//alert("data.nodes::"+JSON.stringify(data.nodes));
	            	if (myChart && myChart.dispose) {
	                    myChart.dispose();
	                }
	                myChart = echarts.init(document.getElementById('container'));
	                
	                myChart.setOption(option,true);
	                myChart.hideLoading();  
	                
	                var ecConfig = echarts.config;
	                myChart.on(ecConfig.EVENT.CLICK, linkFun);
					
    			}
    		});
        	
        }
        
       function reset(){
    	   document.getElementById("interName").value="";
       }
    </script>
<a href="" target="_blank" style="display: none;" id="turnDetail">click</a>
</form>
	</body>