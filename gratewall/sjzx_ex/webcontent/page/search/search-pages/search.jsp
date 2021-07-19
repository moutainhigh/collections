<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<%
	String query = request.getParameter("query");
	if (query == null){
		query = "";
	}
%>
	<link href="<%=request.getContextPath()%>/styles/search.css" rel="stylesheet" type="text/css" >
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/page/page-search.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery.js"></script>
	<script type="text/javascript">
	<!--
	$(document).ready(function(){

		// $("div#col_menu_panel").hide();
		var queryStr = '<%=request.getParameter("query")%>';
		var collStr = '5,6,7,8,9';
		/**
		$("#fbk_golbal_search_input").val(queryStr);
		$("#fbk_golbal_search_collIdOptions").val(collStr);
		if(queryStr == 'null' || queryStr.length ==  0){
			$("#result").html("<BR><FONT COLOR=RED><CENTER>请输入关键字</CENTER></FONT>");
		}
		**/
			 	
		search(collStr,10,1,queryStr);
		function search(collId,rows,page,query){
			page = parseInt(page);
			var url = "<%=request.getContextPath()%>/searchHome";
			$.get(url,{"collId":collId,"query":query,"rows":rows,"page":page },
			 function(xml){
			 	$.get("<%=request.getContextPath()%>/search-pages/searchResult.jsp",function(xslt){
			 		var html = xml.documentElement.transformNode(xslt);
			 		$("#result").html(html);
					
					/**
					$("a",$("#result")).ToolTip(
						{
							className: 'linksTooltip',
							position: 'bottom',
							onShow:function(){
								$("#tooltipURL",$("#tooltipHelper")).hide();
							}
						}
					);
					**/
			 		$("#after").click(function(){
			 			search($("#collId",$("#result")).val(),
			 				$("#rows",$("#result")).val(),
			 				parseInt($("#page",$("#result")).val()) + 1,
			 				$("#query",$("#result")).val());
			 		});
			 		$("#before").click(function(){
			 			search($("#collId",$("#result")).val(),
			 				$("#rows",$("#result")).val(),
			 				parseInt($("#page",$("#result")).val()) - 1,
			 				$("#query",$("#result")).val());
			 		});
			 		$("#first").click(function(){
			 			search($("#collId",$("#result")).val(),
			 				$("#rows",$("#result")).val(),
			 				1,
			 				$("#query",$("#result")).val());
			 		});
			 		$("#last").click(function(){
			 			search($("#collId",$("#result")).val(),
			 				$("#rows",$("#result")).val(),
			 				$("#maxPage",$("#result")).val(),
			 				$("#query",$("#result")).val());
			 		});
			 		$("#curPage").keydown(function(event){
			 			if(event.keyCode != 0x0D && event.keyCode != 0x0A) return;
				 		var cur = parseInt($("#curPage").val());
				 		var max = parseInt($("#maxPage",$("#result")).val());
			 			if(cur > max || cur < 1){
			 				alert("页数超出范围");
			 				return;
			 			}
			 			search($("#collId",$("#result")).val(),
			 				$("#rows",$("#result")).val(),
			 				cur,
			 				$("#query",$("#result")).val());
			 		});
			 		$("#pageSize").keydown(function(event){
			 			if(event.keyCode != 0x0D && event.keyCode != 0x0A) return;
				 		var size = parseInt($("#pageSize").val());
			 			if(size < 1 || size > 100){
			 				alert("请输入1-100之间的值");
			 				return false;;
			 			}
			 			search($("#collId",$("#result")).val(),
			 				size,
			 				1,
			 				$("#query",$("#result")).val());
			 			return true;
			 		});
			 	});			 	
			}, "xml");
		}
	});
	
	function checkLink(url){
		if(url == "#"){
			alert("该数据今日发生变化，请次日查询");
			return false;
		}else{
			return true;
		}
	}
	
	//-->
	</script>
<freeze:body style="background:white;">
	<form style="margin:0;" action="<%=request.getContextPath()%>/search-pages/search.jsp" onsubmit="return checkInput()" method="post">
		<div style="margin:10 0 0 0;padding:0 20 0 20">
			<input type="text" name="query" id="query" style="width:300px;height:27px;line-height:24px;" value="<%=query%>"/>
			<input type="submit" value="" style="background:url('../../images/search/btn_search.jpg') #fff no-repeat;height:27px;width:79px;border:0px solid black;"/>
		</div>
	</form>
	<div id="result"></div>
</freeze:body>
</freeze:html>
