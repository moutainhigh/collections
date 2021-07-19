<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
	<title>广东工商</title>
	<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
	
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/trs/trs.css" />
	<style>
	

		html{
			margin:0px;
			border: 0;
			padding: 0px;
		}
		body{
			margin:0px;
			border: 0;
			padding: 0px;

		}

		.header{
			right: 10px;
			top: 0px;
			position: absolute;
		}
		ol li{
			display:block;

		}
		li{
			list-style-type:none;
			display:inline;
		}
		.gd{
			display: inline-block;
			position: absolute;
			right: 10px;
			width: 60px;
			height: 23px;
			float: left;
			color: white;
			background: #38f;
			line-height: 24px;
			font-size: 13px;
			text-align: center;
			overflow: hidden;
			border-bottom: 1px solid #38f;
			margin-left: 19px;
			margin-right: 2px;
		}
		.under{
			overflow:hidden;
			top: 150px;
			margin:0 auto;
			display: block;
			position: relative;

		}
		li a{
			float: left;
			color: #333;
			font-weight: bold;
			line-height: 24px;
			margin-left: 30px;
			font-size: 13px;
			text-decoration: underline;
		}
		#nav{
			margin: 0px;
		}
		p{
			color: #333;
			line-height: 24px;
			font-size: 10px;
		
			width:100%;
			margin: 4px 0px ;
		}
		li ul{
			display: none;
		}
		.s_btn{
			width: 100px;
			height: 42px;
			color: white;
			font-size: 15px;
			letter-spacing: 1px;
			background: #3385ff;
			border-bottom: 1px solid #2d78f4;
			outline: medium;
			-webkit-appearance: none;
			-webkit-border-radius: 0;
		}


		#search{
			float:right;
		}


		#inputBox{
			border: 1px solid #c0d3dd;
			padding: 0px;
			height: 42px;
			width:539px;
			font: 16px/18px arial;
			line-height: 22px\9;
			margin: 6px 266px 0 0;
			padding: 0;
			background: transparent;
			position:absolute;
			left:76px;
			top:-7px;
		}
		.bri{
			display: inline-block;
			right: 10px;
			width: 60px;
			height: 23px;
			float: left;
			color: white;
			background: #38f;
			line-height: 24px;
			font-size: 13px;
			text-align: center;
			overflow: hidden;
			border-bottom: 1px solid #38f;
			margin-left: 19px;
			margin-right: 2px;
			display: none;
		}
		#searchBtn{
			border: 0;
			padding: 0;
			height: 44px;
			
			position:absolute;
			left:617px;
			top:-1px
		}
		
		
		.versionC{
			width:500px;
			margin-left: auto;
			margin-right: auto;
		}
		.imageblue {
			background: #b5cfd2
			url('<%=request.getContextPath()%>/static/images/public/cell-blue.jpg');
			border-width: 1px;
			padding: 3px;
			border-style: solid;
			border-color: #999999;
			height: 20px;
		}

		.imagegrey {
			background: #dcddc0
			url('<%=request.getContextPath()%>/static/images/public/cell-grey.jpg');
			border-width: 1px;
			padding: 3px;
			border-style: solid;
			border-color: #999999;
			height: 20px;
		}
		.xmkc{
		  width:600px;
		  margin:0 auto;
		  text-align:center;
		  position:relative;
		  float: left;
		  padding-left: 0;
		  padding:12.5px;
		   height: 17px; 
		   width: 50px;
		   border: 1px solid #ACACAC;
		    margin-left:360px;
		     border-radius: 
		     5px 0px 0px 5px;
		     margin-top:6px
		}
		
		
		.wena{
		  display:block;
		  width:70px;
		  height:35px;
		  font-size:16px;
		  line-height:38px;
		  vertical-align:middle;
		  margin:-11px;
		  text-align:center;
		  cursor:pointer;
		  position:absolute;
		}
		.classlist{
		  border-left:1px solid #ddd;
		  border-bottom:1px solid #ddd;
		  border-right:1px solid #ddd;
		  z-index:1;
		  left:-2px;
		  top:42px;
		  position:absolute;
		}
		.lis{
		  display:block;
		  margin:0px;
		  padding:0px;
		}
		.lis a{
		  display:block;
		  width:74px;
		  height:20px;
		  font-size:12px;
		  padding-top:8px;
		  text-align:left;
		  text-decoration:none;
		  color:#333;
		  outline:none;                              
		  hide-focus:expression_r(this.hideFocus=true);  
		}
		.lis a:hover{
		  text-decoration:none;
		  background-color:#6D8DB3;
		  color:#fff;
		}
		
	</style>
	<script type=text/javascript><!--//--><![CDATA[//><!--
	/* var jsonleft=[
		{'dataleft':'登陆'},{'dataleft':'搜索2'},{'dataleft':'搜索3'},{'dataleft':'搜索4'}
	];

	$(document).ready(function(){
		$('#inputBox').keydown(function(e){
			if (e.keyCode == 13){
				allaction();
			}
		});
		//countSum();
	}); */
	/* function countSum(){
	 $.ajax({
	 url : '/gdsjzx/reg/queryCount.do',
	 async: false,
	 data : {
	 },
	 type : 'post',
	 cache : false,
	 dataType : 'json',
	 success : function(data) {
	 $('#a').empty();
	 //$('<div>当前企业登记总数：'+data.countsum+'</div>').appendTo($('#a'));
	 $('<div>当前企业登记总数：'+data+'</div>').appendTo($('#a'));
	 },
	 error : function() {
	 }
	 });
	 } */
	//只初始化第一层级
	$(function(){$.ajax({
				url : '/gdsjzx/admin/getFunTreeByUser.do',//
				async: true,
				data : {
				},
				type : 'post',
				cache : false,
				dataType : 'json',
				success : function(datajson) {
				initMenu(datajson);
				},
				error : function() {
					alert("页面异常！请重新加载");
				}
				}
			)}
	);

	//组织菜单方法
	function initMenu(datajson){
		var n;
		for(var i=0;i<datajson.length;i++){
			n=datajson[i];
			//菜单
			if(n.pid==0){
				$('<li><a code="xxx" href="javascript:window.open(encodeURI(\'<%=request.getContextPath()%>'+n.url+"?name="+n.title+"&id="+n.id+'\'));">'+n.title+'</a></li>' ).appendTo($('#nav'));
			}
		}
		//更多
		$('<li ><a  class="bri"  href="javascript:openWindow();">'+"更多"+'</a></li>').appendTo($('#nav'));
	}

	$(function(){
		$('#xmkc').mouseleave(function () {
                $('#hh').hide();
            });
	})

	function openWindow(url){
		window.open(url);
	}
	//隐藏更多的列表
	function hiddenlable(){
		$('#menu li ul li div').removeClass('SubMenuLayer');
		$('#menu li ul li div').addClass('SubMenuLayerHidden');
		$('#menu li ul').removeClass('SubMenuLayer');
		$('#menu li ul').addClass('SubMenuLayerHidden');
		$('#more  ~ li').hide();
	}
	function showlable(){
		//$('#more').hide();
		//alert("pp");
		//	$('#more ~ li').css("display","list-item");
		$('#more ~ li').show();
		//$('#more').show();
	}

	//搜索
	/* $(function datajsleft(){
	 $.each(jsonleft,function(i,n){
	 $('<a href="#" >'+n.dataleft+'</a>').appendTo($('#search')).end();
	 });
	 }); */
	 
	 
	 var url='/gdsjzx/datatrs/querydata.do';
	 //var url='/gdsjzx/datatrs/queryajdata.do';
	  var title="";
	//点击搜索按钮
	function allaction(){
		var pn = $("#inputBox").val();//文本框的id属性
		//alert('请输入内容'==pn.trim());
		if($.trim(pn)!="" && '请输入内容'!=$.trim(pn)){
		 title=$('#whole').html().trim(); 
		//  alert(title);
		   url='/gdsjzx/datatrs/querydata.do?type='+ encodeURI(encodeURI(title));
			returnOrgNo(url,"1",pn);
		} else {
			alert("输入栏不能为空");
		}
		$('#under').hide();
	}
	function spreadMenu(k, n) {
		var szSubMenuClassName = document.getElementById('SubSubMenu' + k + n).className;
		if (szSubMenuClassName == 'SubMenuLayerHidden') {
			document.getElementById('SubSubMenu' + k + n).className = 'SubMenuLayer';
		} else {
			document.getElementById('SubSubMenu' + k + n).className = 'SubMenuLayerHidden';
		}
		$('#nav').bind("mouseleave", handlerInOut);
		function handlerInOut() {
			$('#nav li ul li div').removeClass('SubMenuLayer');
			$('#nav li ul li div').addClass('SubMenuLayerHidden');
		}

		$('#menu').bind("mouseleave", handlerInOut);
		function handlerInOut() {
			$('#menu li ul li div').removeClass('SubMenuLayer');
			$('#menu li ul li div').addClass('SubMenuLayerHidden');
			$('#menu li ul').removeClass('SubMenuLayer');
			$('#menu li ul').addClass('SubMenuLayerHidden');
		}
	}

		//初始化
		/* 	$(document).ready(function(){
				$('#inputBox').keydown(function(e){
			        if (e.keyCode === 13){
			        	allaction();
			        }
				});
				returnOrgNo("1","${search}","1");
				if('${search}' !=""|| '${search}' !=undefined ||'${search}' !=null){
					$('#inputBox').val('${search}');
				};
			}); */
			
			
				//-->检索内容12315和市场主体<!
		function returnOrgNo(url,orgno,queryKeyWord){
			var pages;
			var listbegin;
			var listend;
			var pagescount;
			var pagedata;
			$('.results').empty();
			$.ajax({
				url : url,
				async: false,
				data : {
					pages : orgno,
					queryKeyWord : queryKeyWord
				},
				type : 'post',
				cache : false,
				dataType : 'json',
				success : function(data) {
				//alert('${theme}');
					pages = data.data.pageNo;
					temp=pages;
					listbegin = data.listbegin;
					listend = data.listend;
					pagescount = data.data.totalPageCount;
					pagedata=data.data.items;
					if(pagedata==undefined || pagedata ==null || pagedata.length==0){
						$('<div><a style="color:red;">你搜索的内容为空!</a><div>').appendTo($('.results'));
					}
				},
				error : function() {
					$('<div><a style="color:red;margin-left:18%;">检索trs库出错!</a><div>').appendTo($('.results'));
				}
			});
			
			
			if($(".page")!=undefined){		
				$(".page").remove(); 
				$("<div class='page'></div>").appendTo($(".p"));	
			}	
			
			if($('.sous_list')){		
				$('.sous_list').remove(); 
				$('html,body').animate({scrollTop:0},1000);//回到顶端
				$("<div class='sous_list'></div>").appendTo($(".results"));	
			}	
			
			var postalCode="";
			var tel="";
			//12315主题的trs内容
			if(title=='12315'){
			for(var i=0;i<pagedata.length;i++){
				$('<div class="sous_list" ><div><h3><a>企业名称:</a>'+pagedata[i].invopt+'&nbsp;&nbsp;&nbsp;&nbsp;<a>企业电话:</a>'+pagedata[i].enttel+'&nbsp;&nbsp;&nbsp;&nbsp;<a>企业地址:</a>'+pagedata[i].entaddr+'</h3>'+
				'<p><b>投诉人:</b>'+pagedata[i].pname+'&nbsp;&nbsp;&nbsp;&nbsp;<b>事发地:</b>'+pagedata[i].accsce+'&nbsp;&nbsp;&nbsp;&nbsp;<b>事发时间:</b>'+pagedata[i].acctime+'&nbsp;&nbsp;&nbsp;&nbsp;'+
				'<p><b>受理登记人:</b>'+pagedata[i].accregper+'</p>'+
				'<p><b>登记部门:</b>'+pagedata[i].regdep+'&nbsp;&nbsp;&nbsp;&nbsp;<b>关键字:</b>'+pagedata[i].keyword+'&nbsp;&nbsp;&nbsp;&nbsp;'+
				'<p style="white-space:normal;"><b>具体原由:</b><span>'+pagedata[i].applidique+
				'</span></div></div>').appendTo($('.results'));
			}
			//市场主体的trs内容
			}else if(title=='市场主体'){
			
	
			for(var i=0;i<pagedata.length;i++){
				$('<div class="sous_list" style="text-align: center;"><div><h3><a onclick="openWindow(\'<%=request.getContextPath()%>/'+pagedata[i].url+'\')" href="#" >'+pagedata[i].entName+'&nbsp;&nbsp;'+pagedata[i].regNo+'</a></h3>'+
				'<p><b>统一社会信用代码:</b>'+pagedata[i].uniscid+'&nbsp;&nbsp;&nbsp;&nbsp;<b>企业状态:</b>'+pagedata[i].entState+'&nbsp;&nbsp;&nbsp;&nbsp;<b>企业类型:</b>'+pagedata[i].entType+'&nbsp;&nbsp;&nbsp;&nbsp;<b>法定代表人:</b>'+pagedata[i].leRep+'&nbsp;&nbsp;&nbsp;&nbsp;<b>成立日期:</b>'+pagedata[i].estDate+'</p>'+
				'<p><b>行业类别:</b>'+pagedata[i].industryPhy+'&nbsp;&nbsp;&nbsp;&nbsp;<b>行业:</b>'+pagedata[i].industryCo+'&nbsp;&nbsp;&nbsp;&nbsp;<b>登记机关:</b>'+pagedata[i].regOrg+'</p>'+
				'<p><b>投资人:</b>'+pagedata[i].inv+'</p>'+
				'<p><b>经营状态:</b>'+pagedata[i].opState+'&nbsp;&nbsp;&nbsp;&nbsp;<b>经营方式:</b>'+pagedata[i].opScoAndForm+'&nbsp;&nbsp;&nbsp;&nbsp;<b>经营范围:</b>'+pagedata[i].opScope+'</p>'+
				'<p><b>住所:</b>'+pagedata[i].dom+'</p>'+
				'<p><b>变更历史:</b>'+pagedata[i].bgsx+
				'</div></div>').appendTo($('.results'));
				}
				//年度报告的trs内容  ----未动
			}else if(title=='年度报告'){
			
				for(var i=0;i<pagedata.length;i++){
				$('<div class="sous_list" style="text-align: center;"><div><h3><a onclick="openWindow(\'<%=request.getContextPath()%>/'+pagedata[i].url+'\')" href="#" >'+pagedata[i].entName+'&nbsp;&nbsp;'+pagedata[i].regNo+'</a></h3>'+
				'<p><b>统一社会信用代码:</b>'+pagedata[i].uniscid+'&nbsp;&nbsp;&nbsp;&nbsp;<b>企业状态:</b>'+pagedata[i].entState+'&nbsp;&nbsp;&nbsp;&nbsp;<b>企业类型:</b>'+pagedata[i].entType+'&nbsp;&nbsp;&nbsp;&nbsp;<b>法定代表人:</b>'+pagedata[i].leRep+'&nbsp;&nbsp;&nbsp;&nbsp;<b>成立日期:</b>'+pagedata[i].estDate+'</p>'+
				'<p><b>行业类别:</b>'+pagedata[i].industryPhy+'&nbsp;&nbsp;&nbsp;&nbsp;<b>行业:</b>'+pagedata[i].industryCo+'&nbsp;&nbsp;&nbsp;&nbsp;<b>登记机关:</b>'+pagedata[i].regOrg+'</p>'+
				'<p><b>投资人:</b>'+pagedata[i].inv+'</p>'+
				'<p><b>经营状态:</b>'+pagedata[i].opState+'&nbsp;&nbsp;&nbsp;&nbsp;<b>经营方式:</b>'+pagedata[i].opScoAndForm+'&nbsp;&nbsp;&nbsp;&nbsp;<b>经营范围:</b>'+pagedata[i].opScope+'</p>'+
				'<p><b>住所:</b>'+pagedata[i].dom+'</p>'+
				'<p><b>变更历史:</b>'+pagedata[i].bgsx+
				'</div></div>').appendTo($('.results'));
				}
			}
			
			
			if (pages > 1) {
				$("<span><a onclick='lazyload("+ (pages - 1) +")'>上一页</a></span>").appendTo($('.page'));
			}	
			for (var i = listbegin; i <= listend; i++) {
	        	if (i != pages) {
	          		$("<span><a onclick='lazyload("+ i +")'>" + i + "</a><i></i></span>").appendTo($('.page'));
	            } else {
	            	$("<span><a onclick='lazyload("+ i +")'>" + i + "</a><i class='active_i'></i></span>").appendTo($('.page'));
	            }
	        }
	        if (pages != pagescount && pagescount!=0) {
	        	$("<span><a onclick='lazyload("+ (pages + 1) +")'>下一页</a></span>").appendTo($('.page'));
	        	};
		
		}
		
	    function sh(x){
       	     document.getElementById(x).style.display = document.getElementById(x).style.display? "" : "none";
          }

        function gets_value(str){
        
            $('#whole').html(str); 
            sh('hh');
           
        }
        
        function clearSel(){
                $('#hh').hide();
        };
        
        //延迟加载
		function lazyload(i){
			var keyword=$('#inputBox').val();
			returnOrgNo(url,i,keyword);
		}
		
	//模拟post提交打开新窗口，并传递参数
	function post(URL, PARAMS) { var temp_form = document.createElement("form");
		temp_form .action = URL;
		temp_form .target = "_blank";
		temp_form .method = "post";
		temp_form .style.display = "none"; for (var x in PARAMS) { var opt = document.createElement("textarea");
			opt.name = x;
			opt.value = PARAMS[x];
			temp_form .appendChild(opt);
		}
		document.body.appendChild(temp);
		temp_form .submit();
	}
	
	
	</script>
</head>
<body style="height: 100%;width: 100%">
<div class="headertop">
	<div class='header'>
		<ul id="nav" >
		</ul>
	</div>
	<div class="float_left imagemenu">
		<div id="menudiv"				>
			<ul id="menu" onMouseLeave="hiddenlable()"
				style="z-index: 2; list-style-type: none; padding: 5px;">
			</ul>
		</div>
	</div>
</div>

<div style="z-index: 1; margin-top:6%; text-align: center;">
	<img alt="广东省工商局"
		 src="<%=request.getContextPath()%>/static/images/public/bdlogo.png"
		 style="background: #ffffff;  vertical-align: middle;"/>
</div>

<div style="z-index: 1; margin-top: 2%; text-align: center">
						<div id="xmkc" class="xmkc"  >
						  <div name="class" id="whole" class="wena" onClick="sh('hh');">市场主体
							</div>
						  <div id="hh" onMouseLeave="clearSel()" class="classlist" style="display:none">
						 
						      <div class="lis"><a onClick="gets_value('市场主体')">市场主体</a></div>
						      <div class="lis"><a onClick="gets_value('12315')">12315</a></div>
						 	  <div class="lis"><a onClick="gets_value('年度报告')">年度报告</a></div>
						  </div><!--classlist -->
						  
							<input id="inputBox"  type="text" value="请输入内容"
							   onfocus="javascript:if(this.value=='请输入内容')this.value='';"  >
							<input id="searchBtn" type="button" value="搜索" class="s_btn" onclick="allaction();">
						</div><!--xmkc -->
	
			</div>
	
					<div class="results" style="margin-top:10%;"></div>
					<div class="p" style="margin-left:15%"></div>  
<div id="under" class="under">
	<p>版权所有：广东省工商行政管理局	</p>
	<p>	地址：广州市天河体育西路57号 邮政编码：510620</p>
	<p>技术支持：广东省工商行政管理局信息中心 长城计算机软件与系统有限公司</p>
	<p>	ICP备案号：粤ICP备05028***号-1 网站备案编号：44010****1622</p>
	<div>
		<MARQUEE id="a" direction="left" behavior="scroll" loop="0"
				 scrollAmount="1" scrolldelay="10" onmouseover="this.stop()"
				 style="FONT-SIZE: 9pt; COLOR: white" onmouseout="this.start()"
				 width="150" align="middle" hspace="10" vspace="10">
			<!-- bgColor="#6699ff" height="150" -->
			<div>当前企业登记总数：0</div>
		</MARQUEE>
	</div>
</div>


</body>
</html>
