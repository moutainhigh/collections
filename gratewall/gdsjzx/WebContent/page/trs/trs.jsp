<%@ page language="java" import="org.apache.commons.lang.StringUtils" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>广东工商</title>
	<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/trs/trs.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/trs/index.css" />

<style>
.imagemenu {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	color:#333333;
	border-width: 1px;
	border-color: #999999;
	border-collapse: collapse;
}
.imageblue {
	background:#b5cfd2 url('<%=request.getContextPath()%>/static/images/public/cell-blue.jpg');
	border-width: 1px;
	padding: 3px;
	border-style: solid;
	border-color: #999999;
}
.imagegrey {
	background:#dcddc0 url('<%=request.getContextPath()%>/static/images/public/cell-grey.jpg');
	border-width: 1px;
	padding: 3px;
	border-style: solid;
	border-color: #999999;
}


#menu li ul li a {
    border-radius: 4px;
    margin: 0;
    padding-left: 20px;
    text-decoration:none;
}
#menu li ul a {
    display: block;
    text-align: left;
    width: 100px;
    text-decoration:none;
}

#menu a {
    position: relative;
    text-decoration:none;
}

.xmkc{
  width:600px;
  margin:0 auto;
  text-align:center;
  position:relative;
}
.wena{
  display:block;
/*   border: 1px solid #ACACAC; */
  width:70px;
  height:35px;
  font-size:16px;
  line-height:38px;
  vertical-align:middle;
  margin:-11px;
  text-align:center;
  cursor:pointer;
}
.classlist{
  border-left:1px solid #ddd;
  border-bottom:1px solid #ddd;
  border-right:1px solid #ddd;
  position:absolute;
  z-index:1;
  left:0px;
  top:45px;
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
		<% 
			String search=request.getParameter("search"); 
			String theme=request.getParameter("theme");
			if(StringUtils.isNotBlank(search)){
			request.setAttribute("search", new String( search.getBytes("iso-8859-1"), "UTF-8"));
			}else{
			request.setAttribute("search", null);
			} 
			
			
			if(StringUtils.isNotBlank(theme)){
			request.setAttribute("theme", new String( theme.getBytes("iso-8859-1"), "UTF-8"));
			}else{
			request.setAttribute("theme", null);
			} 
		%>
		//初始化
		$(document).ready(function(){
			$('#inputBox').keydown(function(e){
		        if (e.keyCode === 13){
		        	allaction();
		        }
			});
			returnOrgNo("1","${search}","1");
			if('${search}' !=""|| '${search}' !=undefined ||'${search}' !=null){
				$('#inputBox').val('${search}');
			};
		});

		function allaction(){
			var theme = $('#whole').html();
			var pn = $("#inputBox").val();//文本框的id属性  
			if($.trim(pn)!="" && '请输入内容'!=$.trim(pn)){
				 location.href = "<%=request.getContextPath()%>/page/trs/trs.jsp?search="+pn+"&theme="+theme;//location.href实现客户端页面的跳转  
			}else{
				alert("输入栏不能为空");
			}
		}
		//应用
		$($.ajax({
			url : '/gdsjzx/reg/list.do',
			async: true,
			data : {
			theme:"${theme}"
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(datajson) {
				var n;
				for(var i=0;i<datajson.length;i++){
					n=datajson[i];
					var titlesize=5;
					//----start---
					if(n.url==""){
						if(i<titlesize){
							$('<li><a href="#"><b>'+n.title+'</b></a><ul></ul></li>').appendTo($('#nav')); 
							$.each(n.l,function(j,m){
								if(m.url==""){//三级目录加链接
									$('<li><a class="imagegrey" onclick="javascript:spreadMenu('+i+','+j+')">'+m.title+'&nbsp<img src="<%=request.getContextPath()%>/static/images/public/jiahao.png" width="15px" height="10px"/></a><div id="SubSubMenu'+i+j+'" class="SubMenuLayerHidden" style="overflow-y:block;"></div></li>').appendTo($('#nav li:last-child ul'));
									if(m.l!=null){
										$.each(m.l,function(jj,mm){
											$('<a class="imageblue" href="javascript:openWindow(\'<%=request.getContextPath()%>/'+mm.url+'\');" class="MenuLevel4">'+mm.title+'</a></li>').appendTo($('#nav li:last-child ul  li:last-child div'));
										});
									}
							 	}else{//二级目录加链接
									$('<li><a href="javascript:openWindow(\'<%=request.getContextPath()%>/'+m.url+'\');" class="imagegrey" id="SubSubMenu'+i+j+'" >'+m.title+'</a></li>').appendTo($('#nav li:last-child ul'));
								} 
							});
						}else{
							if(i==titlesize){
								$('<li id="more"><a id="moremenu" onMouseMove="showlable()" style="margin-left:10px" href="#"><b>更多>></b></a></li>').appendTo($('#menu')); 
							}
							$('<li style="display:none;margin-left:10px"><a href="javascript:spreadMenu(99,'+i+')"><b>'+n.title+'</b></a><ul style="list-style-type:none;" class="SubMenuLayerHidden"  id="SubSubMenu'+99+i+'"></ul></li>').appendTo($('#menu')); 
							$.each(n.l,function(j,m){
								if(m.url==""){//三级目录加链接
									$('<li><a class="imagegrey" onclick="javascript:spreadMenu('+i+','+j+')">'+m.title+'&nbsp<img src="<%=request.getContextPath()%>/static/images/public/jiahao.png" width="15px" height="10px"/></a><div id="SubSubMenu'+i+j+'" class="SubMenuLayerHidden" style="overflow-y:block;"></div></li>').appendTo($('#menu li:last-child ul'));
									$.each(m.l,function(jj,mm){
										$('<a class="imageblue" href="javascript:openWindow(\'<%=request.getContextPath()%>/'+mm.url+'\');" class="MenuLevel4">'+mm.title+'</a></li>').appendTo($('#menu li:last-child ul  li:last-child div'));
									});	
							 	}else{//二级目录加链接
									$('<li><a href="javascript:openWindow(\'<%=request.getContextPath()%>/'+m.url+'\');" class="imagegrey">'+m.title+'</a></li>').appendTo($('#menu li:last-child ul'));
								} 
							});
						}
					}else{
						if(n.l!=null){
							if(i<titlesize){
								$('<li><a href="javascript:openWindow(\'<%=request.getContextPath()%>/'+n.url+'\');" class="imagegrey"><b>'+n.title+'</b></a><ul></ul></li>').appendTo($('#nav')); 
							}else{
								if(i!=titlesize){
									$('<li style="display:none;"  class="imagegrey"><a href="javascript:spreadMenu(99,'+i+')"><b>'+n.title+'</b></a><ul style="list-style-type:none;" class="SubMenuLayerHidden"  id="SubSubMenu'+99+i+'"></ul></li>').appendTo($('#menu')); 
								}	
							}
						}else{
							if(i<titlesize){
								$('<li><a href="javascript:openWindow(\'<%=request.getContextPath()%>/'+n.url+'\');"><b>'+n.title+'</b></a></li>').appendTo($('#nav')); 
							}else{
								if(i!=titlesize){
									$('<li style="display:none;margin-left: 10px; display: none;"><a href="javascript:openWindow(\'<%=request.getContextPath()%>/'+n.url+'\');"><b>'+n.title+'</b></a></li>').appendTo($('#menu')); 
								}	
							}
						}
					}
					//----end---
				}
			},
			error : function() {
				alert("页面异常！请重新加载");
			}
		}));
	
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
			$('#more  ~ li').show();
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
		
		//--><!
		function returnOrgNo(orgno,queryKeyWord,labelFlag){
			var pages;
			var listbegin;
			var listend;
			var pagescount;
			var pagedata;
			$('.results').empty();
			$.ajax({
				url : '/gdsjzx/datatrs/queryajdata.do',
				async: false,
				data : {
					pages : orgno,
					queryKeyWord : queryKeyWord,
					labelFlag : labelFlag
				},
				type : 'post',
				cache : false,
				dataType : 'json',
				success : function(data) {
					$('#whole').html('${theme}');
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
			for(var i=0;i<pagedata.length;i++){
				$('<div class="sous_list" style="text-align: left;"><div><h3><a onclick="openWin(\'<%=request.getContextPath()%>/'+pagedata[i].url+'\')" href="#" >'+pagedata[i].entName+'&nbsp;&nbsp;'+pagedata[i].regNo+'</a></h3>'+
				'<p><b>统一社会信用代码:</b>'+pagedata[i].uniscid+'&nbsp;&nbsp;&nbsp;&nbsp;<b>企业状态:</b>'+pagedata[i].entState+'&nbsp;&nbsp;&nbsp;&nbsp;<b>企业类型:</b>'+pagedata[i].entType+'&nbsp;&nbsp;&nbsp;&nbsp;<b>法定代表人:</b>'+pagedata[i].leRep+'&nbsp;&nbsp;&nbsp;&nbsp;<b>成立日期:</b>'+pagedata[i].estDate+'</p>'+
				'<p><b>行业类别:</b>'+pagedata[i].industryPhy+'&nbsp;&nbsp;&nbsp;&nbsp;<b>行业:</b>'+pagedata[i].industryCo+'&nbsp;&nbsp;&nbsp;&nbsp;<b>登记机关:</b>'+pagedata[i].regOrg+'</p>'+
				'<p><b>投资人:</b>'+pagedata[i].inv+'</p>'+
				'<p><b>经营状态:</b>'+pagedata[i].opState+'&nbsp;&nbsp;&nbsp;&nbsp;<b>经营方式:</b>'+pagedata[i].opScoAndForm+'&nbsp;&nbsp;&nbsp;&nbsp;<b>经营范围:</b>'+pagedata[i].opScope+'</p>'+
				'<p><b>住所:</b>'+pagedata[i].dom+'</p>'+
				'<p><b>变更历史:</b>'+pagedata[i].bgsx+
				'</div></div>').appendTo($('.results'));
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
		
		function openWin(url){
			window.open(url);
		}
		
	//延迟加载
	function lazyload(i){
		var keyword=$('#inputBox').val();
		returnOrgNo( i ,keyword,keyword);
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
	</script>
</head> 
<body>
	<div style="width:100%; height:40%;" > 
		<div class="float_left imagemenu">
			<div class='header' >
				<ul id="nav" style="float: left;"></ul>
			</div>
			<div id="menudiv" style="z-index:2;height:auto;width:80px;float:left;word-wrap:break-word;position:absolute; left:500px; text-decoration:none; ">
				<ul id="menu" onMouseLeave="hiddenlable()" style="z-index:2;list-style-type:none;padding:5px;">
				</ul>
			</div>
			<div id="search" style="float:right;margin-top:0.5%;margin-right: 1px;"><a href="#">登陆</a></div>
			<div align="center" style="margin-top:10%;">
				<img alt="广东省工商局" src="<%=request.getContextPath()%>/static/images/public/bdlogo.png" style="background:#ffffff;margin-left:0%;border-radius:5px 5px 5px 5px;"  width=30%/>
			</div>
			<div style="z-index:1;margin-top:2%;">
					<div id="xmkc" class="xmkc"  style="float: left;padding-left: 0;padding:12.5px; height: 15px; width: 50px;border: 1px solid #ACACAC; margin-left:300px; border-radius: 5px 0px 0px 5px;">
						  <div name="class" id="whole"  class="wena" onClick="sh('hh');">
							</div>
						  <div id="hh" onMouseLeave="clearSel()" style="display:none" class="classlist">
						 
						      <div class="lis"><a onClick="gets_value('市场主体')">市场主体</a></div>
						      <div class="lis"><a onClick="gets_value('12315')">12315</a></div>
						 
						  </div><!--classlist -->
						</div><!--xmkc -->
				<input   id="inputBox" type="text" style="padding-right:0;height:40px;width:63%;border: 1px solid #ACACAC;">
				<input type="button" value="搜索" class="s_btn" onclick="allaction();" style="padding-left:0;height:42px;width:8%;margin-left:-1%;border-radius:0px 5px 5px 0px;">
			</div>
			<div class="results" style="margin-top:3%;text-align: center;"></div>
			<!-- 
			<div class="pagination">
				总记录数:&nbsp;859155 &nbsp;&nbsp;消耗时间:&nbsp;32 ms 当前为第1页 共85916页 <a
					class="next-page" href="/solr/collection1/browse?&q=&start=10">下一页</a>
				<br />
			</div> 
			-->
			<div class="p" style="margin-left:15%"></div>  
		</div>
	</div>
 

	
</body> 
</html>
