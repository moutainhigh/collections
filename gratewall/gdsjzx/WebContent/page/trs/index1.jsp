<%@ page language="java" import="org.apache.commons.lang.StringUtils" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>广东工商</title>
	<link rel="stylesheet" type="text/css" media="screen" href="api/framework/page.css" />
	<link type="text/css" rel="stylesheet" href="api/dp.SyntaxHighlighter/Styles/SyntaxHighlighter.css"/>
	<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/trs/trs.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/trs/index.css" />

	<script type=text/javascript><!--//--><![CDATA[//><!-- 
		var datajson=[{	
			'bbgl':'报表管理1',
			'data':[{'rcbb':'日常报表1','sanji':[{'java':'java书本1'},{'asp':'asp书本1'}]}
			,{'rcbb':'自定义报表1','sanji':[{'java':'java书本2'},{'asp':'asp书本2'}]}
			,{'rcbb':'啊啊啊啊1','sanji':[{'java':'java书本3'},{'asp':'asp书本3'}]}]
		},{	
			'bbgl':'报表管理2',
			'data':[{'rcbb':'日常报表2223','sanji':[{'java':'java书本'},{'asp':'asp书本4111'}]}
			,{'rcbb':'自定义报表2','sanji':[{'java':'java书本5'},{'asp':'asp书本5'}]}
			,{'rcbb':'啊啊啊啊2','sanji':[{'java':'java书本6'},{'asp':'asp书本6'}]}]
		},{	
			'bbgl':'报表管理3',
			'data':[{'rcbb':'日常报表3','sanji':[{'java':'java书本7'},{'asp':'asp书本7'}]}
			,{'rcbb':'自定义报表3','sanji':[{'java':'java书本8'},{'asp':'asp书本8'}]}
			,{'rcbb':'啊啊啊啊3','sanji':[{'java':'java书本9'},{'asp':'asp书本9'}]}]
		},{	
			'bbgl':'报表管理4',
			'data':[{'rcbb':'日常报表4','sanji':[{'java':'java书本10'},{'asp':'asp书本10'}]}
			,{'rcbb':'自定义报表4','sanji':[{'java':'java书本111'},{'asp':'asp书本11'}]}
			,{'rcbb':'啊啊啊啊4','sanji':[{'java':'java书本12'},{'asp':'asp书本12'}]}]
		}];
		
		var jsonleft=[
			{'dataleft':'搜索1'},{'dataleft':'搜索2'},{'dataleft':'搜索3'},{'dataleft':'搜索4'}
		];
		<% 
			String search=request.getParameter("search"); 
			if(StringUtils.isNotBlank(search)){
			request.setAttribute("search", new String( search.getBytes("iso-8859-1"), "UTF-8"));
			}else{
			request.setAttribute("search", null);
			} 
		%>
		//初始化
		$(document).ready(function(){
			$('#inputBox').keydown(function(e){
		        if (e.keyCode === 13){
		        	queryKeyword();
		        }
			});
			returnOrgNo("1","${search}","1");
			if('${search}' !=""|| '${search}' !=undefined ||'${search}' !=null){
				$('#inputBox').val('${search}');
			};
		});
		//应用
		$(function datajsright(){	
			 $.each(datajson, function(i, n){
				$('<li><a href="#"><b>'+n.bbgl+'</b></a><ul></ul></li>').appendTo($('#nav')).end(); 
				$.each(n.data,function(j,m){
					$('<li><a href="#"  onclick="javascript:spreadMenu('+i+','+j+')">'+m.rcbb+'</a><div id="SubSubMenu'+i+j+'" class="SubMenuLayerHidden" style="overflow-y:block;"></div></li>').appendTo($('#nav li:last-child ul ')).end();
					$.each(m.sanji,function(jj,mm){
						for(var key in mm){
							$('<a href="#" class="MenuLevel4">'+mm[key]+'</a></li>').appendTo($('#nav li:last-child ul  li:last-child div')).end();
						}
					});	
				});
			});
		});
		//搜索
		$(function datajsleft(){
			$.each(jsonleft,function(i,n){
				$('<a href="#" >'+n.dataleft+'</a>').appendTo($('#search')).end(); 
			});
		});
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
		}
		//-----------
	

	//--加载搜索之后的内容><!   
	
	function returnOrgNo(orgno,queryKeyWord,labelFlag){
			var pages;
			var listbegin;
			var listend;
			var pagescount;
			var pagedata;
			$('.results').empty();
			$.ajax({
				url : '/gdsjzx/datatrs/querydata.do',
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
						$('<div><a style="color:red;margin-left:18%;">你搜索的内容为空!</a><div>').appendTo($('.results'));
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
		
			for(var i=0;i<pagedata.length;i++){
				$('<div class="sous_list"><div><h3><a href="<%=request.getContextPath()%>/'+pagedata[i].url+'" >'+pagedata[i].entName+pagedata[i].regNo+'</a></h3>'+
				'<p><b>旧注册号:</b>'+pagedata[i].oldRegNo+'&nbsp;&nbsp;<b>成立日期:</b>'+pagedata[i].estDate+'&nbsp;&nbsp;<b>地址:</b>'+pagedata[i].addr+'</p>'+
				'<p><b>企业状态:</b>'+pagedata[i].entState+'&nbsp;&nbsp;<b>企业类型:</b>'+pagedata[i].entType+'&nbsp;&nbsp;<b>行业类别:</b>'+pagedata[i].industryPhy+'&nbsp;&nbsp;<b>行业代码:</b>'+pagedata[i].industryCo+'&nbsp;&nbsp;<b>登记机关:</b>'+pagedata[i].regOrg+'&nbsp;&nbsp;<b>经营状态:</b>'+pagedata[i].opState+'</p>'+
				'<p><b>法定代表人:</b>'+pagedata[i].leRep+'&nbsp;&nbsp;<b>投资人:</b>'+pagedata[i].inv+'&nbsp;&nbsp;<b>证件号:</b>'+pagedata[i].cerNo+'</p>'+
				'<p><b>邮编:</b>'+pagedata[i].postalCode+'&nbsp;&nbsp;<b>电话:</b>'+pagedata[i].tel+'&nbsp;&nbsp;<b>经营范围及方式:</b>'+pagedata[i].opScoAndForm+'&nbsp;&nbsp;<b>属地监管所:</b>'+pagedata[i].localAdm+'&nbsp;&nbsp;<b>年检机关:</b>'+pagedata[i].insAuth+'</p>'+
				'<p><b>住所:</b>'+pagedata[i].dom+'&nbsp;&nbsp;<b>经营范围:</b>'+pagedata[i].opScope+'</p>'+
				'</div></div>').appendTo($('.results'));
				//<b>企业类型:</b>&nbsp;
			}
		
		
		if (pages > 1&& pagescount!=0) {
					 	$("<span><a onclick='lazyload("+ (pages - 1) +")'>上一页</a></span>").appendTo($('.page'));
					 }	
			 for (var i = listbegin; i <= listend; i++) {
                    if (i != pages) {
                        $("<span><a onclick='lazyload(" + i +")'>" + i + "</a><i></i></span>").appendTo($('.page'));
                    } else {
                        $("<span><a onclick='lazyload(" + i + ")'>" + i + "</a><i class='active_i'></i></span>").appendTo($('.page'));
                    }
                }
                 if (pages != pagescount && pagescount!=0) {
                    $("<span><a onclick='lazyload("+ (pages + 1) +")'>下一页</a></span> ").appendTo($('.page'));
                };
	
	
	}
	
	//--->
	
	//延迟加载
	function lazyload(i){
		setTimeout("returnOrgNo(" + i + ",\""+$('#inputBox').val()+"\",\""+$('#inputBox').val()+"\")",0);  
	}
	//点击搜索
	function queryKeyword(){
			var keyword=$('#inputBox').val();
			if(keyword!=null&&keyword!=undefined){
				returnOrgNo("1",$('#inputBox').val(),null);
			}else{
				alert("输入栏不能为空");		
			}
		}
	
	
	
</script>
</head> 
<body>
<div style="width:100%; height:40%;position:relative;" > 
	<div  class="float_left" >
		<div id="search" style="float:left;margin:0.5%;margin-left:1%;"></div>	
		<div style="width:100%;" >
			<ul id="nav" style="float:right;"></ul> 
		</div >
		<div align="center" style="margin-top:10%;">
			<img alt="广东省工商局" src="<%=request.getContextPath()%>/static/images/public/bdlogo.png" style="background:#ffffff;margin-left:0%;border-radius:5px 5px 5px 5px;"  width=30%/>
		</div>
			
		<div style="margin-top:2%;text-align: center;">
				<input type="text" id="inputBox"  style="padding-right:0;height:40px;width:63%;border: 1px solid #ACACAC;border-radius:5px 5px;">
				<input type="button" value="查询" class="s_btn" onclick="queryKeyword();" style="padding-left:0;height:42px;width:8%;margin-left:-1%;border-radius:0px 5px 5px 0px;">
			</div>
	</div>
</div>
 

	<div class="results" style="margin-top:3%;">
	</div>
	<!-- <div class="pagination">
		总记录数:&nbsp;859155 &nbsp;&nbsp;消耗时间:&nbsp;32 ms 当前为第1页 共85916页 <a
			class="next-page" href="/solr/collection1/browse?&q=&start=10">下一页</a>
		<br />
	</div>  -->
	
	<div class="p" style="margin-left:14%">
	</div> 
</body> 
</html>
