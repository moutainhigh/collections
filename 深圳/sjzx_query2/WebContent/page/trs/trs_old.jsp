<%@ page language="java" import="org.apache.commons.lang.StringUtils" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>深圳市场监督管委员会</title>
    <%
	String rootPath = request.getContextPath();
	%>
	<script src="../../static/script/jazz.js" type="text/javascript"></script>
	<script src="<%=rootPath%>/static/js/sczt/base64.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/trs/trs.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/trs/index.css" />

	<script type=text/javascript>
		<% 
	 		String search=request.getParameter("search"); 
		
/* 			if(StringUtils.isNotBlank(search)){
			request.setAttribute("search", new String( search.getBytes("iso-8859-1"), "UTF-8"));
			}else{
			request.setAttribute("search", null);
			}  */
		%>
		//初始化
		$(document).ready(function(){
			$('#inputBox').keydown(function(e){
		        if (e.keyCode === 13){
		        	allaction();
		        }
			});
			var s="<%=search%>";	

			s=decode(s);
			if(s !=""|| s !=undefined ||s !=null){
				$('#inputBox').val(s);
			};
			returnOrgNo("1",s,"1");

		});

		function allaction(){
			var pn = $("#inputBox").val();//文本框的id属性  
			if($.trim(pn)!="" && '请输入内容'!=$.trim(pn)){
				 location.href = "<%=request.getContextPath()%>/page/trs/trs.jsp?search="+encode(pn);//location.href实现客户端页面的跳转  
			}else{
				alert("输入栏不能为空");
			}
		}
		
	
		function openWindow(url){
			window.open(url);
		}
		
		function returnOrgNo(orgno,queryKeyWord,labelFlag){
			var pages;
			var listbegin;
			var listend;
			var pagescount;
			var pagedata;
			$('.results').empty();
			$.ajax({
				url : rootPath+'/datatrs/querydata.do',
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
				var urlleft="<%=request.getContextPath()%>";
				
				var url =pagedata[i]["url"];
			    var searchValue=pagedata[i]["searchValue"];
			/* 			var enttype=searchValue.enttype;
				
				var flag=searchValue.flag;
				var priPid =searchValue.priPid; */
				url=url+"?"+allPrpos(searchValue)
				url=	encodeURIComponent(url);
				//console.log(allPrpos(searchValue));
				
				/* //var url=urlleft+"/"+urlr; */
				//console.log(url);
				$('<div class="sous_list" style="text-align: left;"><div><h3><a onclick="openWin(\''+url+'\')" href="#" >'+pagedata[i].entName+'&nbsp;&nbsp;'+pagedata[i].regNo+'</a></h3>'+
						'<p><b>统一社会信用代码:</b>'+pagedata[i].uniscid+'&nbsp;&nbsp;&nbsp;&nbsp;<b>企业状态:</b>'+pagedata[i].entState+'&nbsp;&nbsp;&nbsp;&nbsp;<b>企业类型:</b>'+pagedata[i].entType+'&nbsp;&nbsp;&nbsp;&nbsp;<b>法定代表人:</b>'+pagedata[i].leRep+'&nbsp;&nbsp;&nbsp;&nbsp;<b>成立日期:</b>'+pagedata[i].estDate+'</p>'+
						'<p><b>行业类别:</b>'+pagedata[i].industryPhy+'&nbsp;&nbsp;&nbsp;&nbsp;<b>行业:</b>'+pagedata[i].industryCo+'&nbsp;&nbsp;&nbsp;&nbsp;<b>登记机关:</b>'+pagedata[i].regOrg+'</p>'+
						'<p><b>投资人:</b>'+pagedata[i].inv+'</p>'+
						'<p><b>经营范围:</b>'+pagedata[i].opScope+'</p>'+
						'<p><b>住所:</b>'+pagedata[i].dom+'</p>'+
						'<p>'+ 
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
		
		
		
		function allPrpos ( obj ) { //遍历对象的属性和值  
			// 用来保存所有的属性名称和值   
			var props = "" ;   
			// 开始遍历   
			for ( var p in obj ){   
				// 方法   
				if ( typeof ( obj [ p ]) == " function " ){   
					obj [ p ]() ;   
				} else {   
				// p 为属性名称，obj[p]为对应属性的值   
					if(props.length<1){
						props=p+"="+encode(obj [ p ]);
					}else{
						props=props+"&"+p+"="+encode(obj [ p ]);
					}
					   
				}   
			}   
			// 最后显示所有的属性   
			 return props  ;   
			}  
		
		function openWin(url){
	
			var urlleft="<%=request.getContextPath()%>";
			url =decodeURIComponent(url)
			url=urlleft+"/"+url;
			
			//alert(url);
			window.open(url);
		}
		
	//延迟加载
	function lazyload(i){
		var keyword=$('#inputBox').val();
		returnOrgNo( i ,keyword,keyword);
	}
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
				<!-- <img alt="广东省工商局" src="<%=request.getContextPath()%>/static/images/public/bdlogo.png" style="background:#ffffff;margin-left:0%;border-radius:5px 5px 5px 5px;"  width=30%/>  -->
			</div>
			<div style="z-index:1;margin-top:2%;text-align: center;">
				<input   id="inputBox" type="text" style="padding-right:0;height:40px;width:63%;border: 1px solid #ACACAC;border-radius:5px 5px;">
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
