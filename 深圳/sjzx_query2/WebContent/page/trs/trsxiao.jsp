<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.gwssi.optimus.plugin.auth.model.User"%>        
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据中心</title>
<%
	String appCode = request.getParameter("code");
	String rootPath = request.getContextPath();
%>
    
<%
	User user = (User)request.getSession().getAttribute("user");
	String username = user.getUserName();
%>

<script type="text/javascript">
	var appCode = "<%=appCode%>";
</script>
<script src="../../static/script/jazz.js" type="text/javascript"></script>
<script src="<%=rootPath%>/static/js/sczt/base64.js" type="text/javascript"></script>
<link type="text/css" href="<%=rootPath%>/static/css/trs/trs_header.css" rel="stylesheet" />

<script type=text/javascript>
		<% 
	 		String search=request.getParameter("search"); 
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
				 location.href = "<%=request.getContextPath()%>/page/trs/trsxiao.jsp?search="+encode(pn);//location.href实现客户端页面的跳转  
			}else{
				
				alert("输入栏不能为空");
			}
		}
		
	
		function openWindow(url){
			window.open(url);
		}
		
		function searchCount(queryKeyWord){
			$.ajax({
				url : rootPath+'/trsSearchxiao/countDualxiao.do',
				async: false,
				data : {
					queryKeyWord : queryKeyWord,
				},
				type : 'post',
				cache : false,
				dataType : 'json',
				success : function(data) {
				
				},
				error : function() {
					
				}
			});
		}
		
		
		function returnOrgNo(orgno,queryKeyWord,labelFlag){
			var pages;
			var listbegin;
			var listend;
			var pagescount;
			var pagedata;
			$('.results').empty();
			$.ajax({
				url : rootPath+'/datatrsxiao/querydataxiao.do',
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
					searchCount(queryKeyWord);
					pages = data.data.pageNo;
					temp=pages;
					listbegin = data.listbegin;
					listend = data.listend;
					pagescount = data.data.totalPageCount;
					pagedata=data.data.items;
					recordCount=data.recordCount;
					useingtime =data.useingtime;
					returnString= data.data.freeMReturnString;
					$("#countnum").html("符合条件的查询共有："+recordCount+"条");
					$("#timeuseing").html("  耗时："+useingtime+"毫秒");
					//alert(returnString);
					//returnString ="'"+returnString+"'";
					//console.log(returnString);
					//$(returnString).appendTo($('.results'));

					//$(returnString).appendTo($('.results'));
					//$('#tabs tbody').append(data.data.freeMReturnString);
					//alert(returnString.length);

				
	
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
<%-- 			for(var i=0;i<pagedata.length;i++){
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
			} --%>
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
	        
			if(returnString==undefined || returnString ==null || returnString.length==0){
				$('<div><a style="color:red;">你搜索的内容为空!</a><div>').appendTo($('.results'));
			}else{
				document.getElementById("result").innerHTML=returnString;
			}
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
			var urlright="";
			var urlleft_1=""
			   var name,value;
			   var str=url; //取得地址
			   var num=str.indexOf("?")
			 
			   str=str.substr(num+1); //取得所有参数   stringvar.substr(start [, length ]

			   var arr=str.split("&"); //各个参数放到数组里
			   for(var i=0;i < arr.length;i++){
			    num=arr[i].indexOf("=");
			    if(num>0){
			     name=arr[i].substring(0,num);
			     value=arr[i].substr(num+1);
			     this[name]=value;
			     urlright =urlright+"&"+name+"="+encode(value);
			     }
			    }
			   urlleft_1=url.substr(0,(url.indexOf("?")+1));
		
			   urlright=urlright.substr(1);

			url=urlleft_1+urlright;
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
<style type="text/css">


#tishi{
	height:40px;
	/* background-color:#4698e6; */
	position:relative;
	height:100%;
	line-height:30px;
	padding-left:15%;
    font-size: 12px;
	/* color: #eff1ef;	 */
	font-family:simsun;

}

.toparrow{
	cursor:pointer;
	width:22px;
	height:10px;
	background:url(../../static/images/other/toparrow.png)  no-repeat;
	position:absolute;
	left:50%;
	top:0px;
	zoom:1;
	z-index:1000;
}
.yincangdiv { overflow-x: hidden; overflow-y: hidden; }
.footer_tishi{
	height:40px;
	 background-color:black; 
	position:relative;
	height:100%;
	line-height:30px;
	/* padding-left:15%; */
    font-size: 12px;
	 color: #eff1ef;	 
	font-family:simsun;

}



.sjzx_content .serach{


	margin-left:15%;
}
 .sjzx_content .serach .content{
	border:1px solid #0086bb;
	height:45px;
	width:700px;
}
.sjzx_content .serach .content input{
	height:43px;
	width:590px;
	line-height:43px;
	border:none;
}

.sjzx_content .serach .content div{
	 height:100%;
	 width:100px;
	 background-color:#0086bb;
	
	
	 float:right;
	 line-height:43px;
     text-align:center;
     color:#ffffff;
     cursor:pointer;
     font-size:14px;
     font-family:  "Microsoft YaHei","宋体","SimSun","helvetica","arial","sans-serif";
} 

</style>
</head>
<body>
	<div id="allcontent" vtype="panel" width="100%" height="100%" showborder="false" 
		 layout="border" layoutconfig="{border: false, north_show_border: true, south_show_border: false ,north_drag:false,west_drag:false}">
		<div class="yincangdiv" region="north">
			<jsp:include page="../../page/home/header.jsp"/>
			<jsp:include page="trs_header.jsp"/>
		</div>

		<div region="center">
	<div style="width:100%; height:40%;" > 
		<div class="float_left imagemenu">
			<div id ="search_1" style="z-index:1;margin-top:2%;text-align: center;">
			<!-- 	<input   id="inputBox" type="text" style="padding-right:0;height:40px;width:63%;border: 1px solid #ACACAC;border-radius:5px 5px;">
				<input type="button" value="搜索" class="s_btn" onclick="allaction();" style="padding-left:0;height:42px;width:8%;margin-left:-1%;border-radius:0px 5px 5px 0px;">
			 -->		
				<div class="sjzx_content" >
				<div class="serach" style="z-index:1;margin-top:2%;text-align: center;">
					<div class="content"><div onclick="allaction();">搜 &nbsp;&nbsp; 索</div><input id="inputBox" type="text"></div>
				</div>
			</div>
			</div>

			<div  id="tishi"><span id="countnum"></span><span id="timeuseing"></span></div>
			<div id="result" class="results" style="margin-top:3%;text-align: center;"></div>
			
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
		</div>
		<div region="south" height="31" class="footer_tishi" style="text-align: center;">
				深圳市市场和质量监督管理委员会	
		<%-- 	<jsp:include page="../../page/integeration/footer.jsp"/> --%>
		</div>
	</div>
</body>
</html>