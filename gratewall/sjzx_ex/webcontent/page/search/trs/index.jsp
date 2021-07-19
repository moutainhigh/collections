<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%
	String queryStrFront = "";
	queryStrFront = request.getParameter("select-key:queryStrFront");
	if (queryStrFront == null)
		queryStrFront = "";
%>
<freeze:html>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/search/trs/js/simplePagination.css" />
<link
	href="<%=request.getContextPath()%>/script/lib/skin-vista/ui.dynatree.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script language="javascript"
	src="<%=request.getContextPath()%>/script/lib/jquery-ui.custom.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/search/trs/js/page-search.js"></script>
<script language="javascript"
	src="<%=request.getContextPath()%>/script/lib/jquery.dynatree.js"></script>

<style>
#tree {
	vertical-align: top;
	width: 200px;
	border: 0px;
	padding: 0px;
}

#tree ul {
	border: 0px;
	background-color: white;
}

#search,.btnSearch {
	float: left;
}

#search {
	padding: 5px 9px;
	height: 28px;
	width: 380px;
	border: 1px solid #a4c3ca;
	font: normal 12px '宋体', arial, helvetica;
	background: #f1f1f1;
	-moz-border-radius: 50px 3px 3px 50px;
	border-radius: 50px 3px 3px 50px;
	-moz-box-shadow: 0 1px 3px rgba(0, 0, 0, 0.25) inset, 0 1px 0
		rgba(255, 255, 255, 1);
	-webkit-box-shadow: 0 1px 3px rgba(0, 0, 0, 0.25) inset, 0 1px 0
		rgba(255, 255, 255, 1);
	box-shadow: 0 1px 3px rgba(0, 0, 0, 0.25) inset, 0 1px 0
		rgba(255, 255, 255, 1);
}

/* ----------------------- */
.btnSearch {
	background: #1A75C8;
	background-image: -moz-linear-gradient(#95d788, #6cbb6b);
	background-image: -webkit-gradient(linear, left bottom, left top, color-stop(0, #6cbb6b),
		color-stop(1, #95d788) );
	-moz-border-radius: 3px 50px 50px 3px;
	border-radius: 3px 50px 50px 3px;
	border-width: 1px;
	border-style: solid;
	border-color: #7eba7c #578e57 #447d43;
	-moz-box-shadow: 0 0 1px rgba(0, 0, 0, 0.3), 0 1px 0
		rgba(255, 255, 255, 0.3) inset;
	-webkit-box-shadow: 0 0 1px rgba(0, 0, 0, 0.3), 0 1px 0
		rgba(255, 255, 255, 0.3) inset;
	box-shadow: 0 0 1px rgba(0, 0, 0, 0.3), 0 1px 0 rgba(255, 255, 255, 0.3)
		inset;
	height: 28px;
	margin: 0 0 0 10px;
	padding: 2px;
	width: 60px;
	cursor: pointer;
	font: bold 14px Arial, Helvetica;
	color: #FFFFFF;
	text-shadow: 0 1px 0 rgba(255, 255, 255, 0.5);
}

.btnSearch:hover {
	background: #95d788;
	background-image: -moz-linear-gradient(#6cbb6b, #95d788);
	background-image: -webkit-gradient(linear, left bottom, left top, color-stop(0, #95d788),
		color-stop(1, #6cbb6b) );
}

.btnSearch:active {
	background: #95d788;
	outline: none;
	-moz-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.5) inset;
	-webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.5) inset;
	box-shadow: 0 1px 4px rgba(0, 0, 0, 0.5) inset;
}

.btnSearch ::-moz-focus-inner {
	border: 0; /* Small centering fix for Firefox */
}

#config {
	width: 80px;
}

.body-div {
	background-image: none;
}
</style>
<script type="text/javascript">
var stats = new Array;
var filters = new Array;
function setFilter(s){
	var flag = true;
	for(var j=0; j<filters.length; j++){
		if(filters[j]==s){
			flag = false;
			break;
		}
	}
	if(flag){
		filters.push(s);
	}
}

//暂时不用
function setStats(str){
	
	var root = new Array;
		 var a1 = str.split('&');
		 for(var j=0; j<a1.length ; j++){
		 	var a2 = a1[j];
		 	var a3 = a2.split(";");
		 	var s = new Array;
		 	var k1 = a3[0].split(":")
		 	
		 	for(var i=1; i<a3.length; i++){
		 	 	var k = a3[i].split(':');
		 		var t = {"title": a3[i], "isFolder": true, "key":k1[0]+"!=("+k[0]+")"}
		 		s.push(t);
		 	}
		 	
		 	var r = {"title": a3[0], "isFolder": true, "key":k1[0] , "children": s }
		 	root.push(r);
		 }
    stats = root;
	//alert(root[0].children[0].key);
	createStats();
	
}
/*
function createStats(){
	if(stats.length>0){
		
	}
}
*/

function createStats(str){
	if(str!=""){
		 var root = new Array;
		 var a1 = str.split('&');
		 for(var j=0; j<a1.length ; j++){
		 	var a2 = a1[j];
		 	var a3 = a2.split(";");
		 	var s = new Array;
		 	var k1 = a3[0].split(":")
		 	for(var i=1; i<a3.length; i++){
		 	 	var k = a3[i].split(':');
		 	//	var t = {"title": a3[i], "isFolder": true, "key": k[0],select: true }
		 		var t = {"title": a3[i], "isFolder": true, "key": k1[0]+"="+k[0]  }
		 		s.push(t);
		 	}
		 	
		 	var r = {"title": a3[0], "isFolder": true, "key":k1[0] , "children": s }
		 	root.push(r);
		 	stats = root;
		 }
	
		obj = $("#tree").dynatree({
	           children: root,
	           checkbox: true, 
	           selectMode: 3, 
	           
	           minExpandLevel: 2,
	           onSelect:function(flag,node){
	          		//alert(node.data.key)
	           },
	           
	           onActivate: function(node) {
	           		var level = node.getLevel();
	           		
	           		if(level==2){
	           			var parent = node.getParent() ;
			 		    var str = parent.data.key+ "=" +node.data.key;
			 		    var strWhere = $('#strWhere').val();
			 		    //alert(str);
			 		    var strs=str.split("=");
			 		    var qstr=strs[0]+"="+strs[strs.length-1];
			 		    //return;
			 		    if(strWhere==""){
			 		   		 $('#strWhere').val( " and " +qstr);
			 		    }else{
			 		    	if(strWhere.indexOf(qstr)>-1){
			 		    		
			 		    	}else{
			 		    		 $('#strWhere').val( strWhere+" and "+ qstr);
			 		    	}
			 		    }
			 		    $('#currentPage').val('1');
			 		   query();
	           		}
	           		
			   }
		 });
	 
	   // var rootNode = $("#tree").dynatree("getRoot");
	    $("#tree").dynatree("getTree").reload();
	    $('#splitBtnSpan').show();
	 
	} 
}


function hideStats(){

	 var root = new Array;
	 obj = $("#tree").dynatree({
	           children: root,
	           minExpandLevel: 2
		 });
	 
	   // var rootNode = $("#tree").dynatree("getRoot");
	  $("#tree").dynatree("getTree").reload();
	  //$('#leftTd').hide();
	  // $('#leftDiv').hide();
	  $('#splitBtnSpan').hide();
	
}
function doQuery(){
	$('#strWhere').val('');
	$('#currentPage').val('1');
	query();
}

function query(){
	var flag = true;
	flag = checkInput();
	if(!flag){
		return false;
	}
	
	var page = new pageDefine( "/txn50030122.do", "trs检索信息" );
	page.addValue( $('#search').val(), "select-key:queryStr" );
	page.addValue( $('#strWhere').val(), "select-key:strWhere" );
	page.addValue( $('#filterWhere').val(), "select-key:filterWhere" );
	page.addValue($('#currentPage').val(), "page:currentPage" );
	page.addValue($('#countPerPage').val(), "page:countPerPage" );
	page.addValue(0, "page:totalCount" );
	page.addValue(0, "page:totalPage" );
	var win = window.frames('result');
	if(win!=null){
		showFrame();
		page.goPage( null, win );
	}
}

function subQuery(subStr){
	var flag = true;
	flag = checkInput();
	if(!flag){
		return false;
	}
	var page = new pageDefine( "/txn50030122.do", "trs检索信息" );
	var sstr=$('#strWhere').val().replace(' and '+subStr.trim()," ");
	page.addValue( $('#search').val(), "select-key:queryStr" );
	page.addValue( sstr, "select-key:strWhere" );
	page.addValue( $('#filterWhere').val(), "select-key:filterWhere" );
	page.addValue($('#currentPage').val(), "page:currentPage" );
	page.addValue($('#countPerPage').val(), "page:countPerPage" );
	page.addValue(0, "page:totalCount" );
	page.addValue(0, "page:totalPage" );
	$('#strWhere').val(sstr);
	var win = window.frames('result');
	if(win!=null){
		showFrame();
		page.goPage( null, win );
	}
}

function setPageInfo(currentPage,countPerPage){
	$("#currentPage").val(currentPage);
	$("#countPerPage").val(countPerPage);
}

//在提交检索时验证检索信息
function validateForm(){
	var str = $('#search').val();
	if(str&&str==""){
		return false;
	}else if(str&&str!=""){
		var a = $.trim(str);
		if(a==''){
			return false;
			}
	}else{
		return false;
	}
	return true;	
//	showLoading();
}

function queryAll(){
	$('#strWhere').val('');
	$('#filterWhere').val('');
	$('#currentPage').val('1');
	if(filters.length>0){
		filters=new Array;
		query();
	}
}


function filterQuery(){
	//$('#strWhere').val('');
	var s = "";
	var a = $("#tree").dynatree("getSelectedNodes");
	var queryArray=new Array;
	for(var j=0; j<a.length; j++){
		if(a[j].getLevel()==2){
			queryArray.push(a[j].data.key);
		}
	}
	/*if(filters.length>0){
    	s = $('#strWhere').val()+" and ("+ filters.join(" or ")+")";
    }*/
    s = $('#strWhere').val()+" and ("+ queryArray.join(" or ")+")";
	$('#strWhere').val(s);
	$('#currentPage').val('1');
	if(a.length>0){
		query();
	}	
}

function showFrame(){
	$('#result').show();
}

function enter(e){
		var e = window.event || e
		if (e&&e.keyCode == 13){
			query();
		}
	}

var oldHeight = 1000;

function __userInitPage(){
	$('#splitBtnSpan').toggle(function() {
	    $("#queryOne").css("margin-left","0px");
		$('#splitBtn').html('隐藏<img border="0" src="<%=request.getContextPath()%>images/search/hidden.png" />');
	 	$('#leftTd').show();
	 	$('#leftDiv').show();
	}, function() {
		$('#splitBtn').html('筛选<img border="0" src="<%=request.getContextPath()%>images/search/show.png" />');
		$('#leftDiv').hide();
	});

	var ss = "<%=queryStrFront%>";
	if(ss!=""){
		//从首页查询
		$('#search').val(ss);
		query();
	}
	
	document.onkeypress = enter;	
}

_browse.execute( '__userInitPage()' );

</script>

<freeze:body>
	<table id="queryOne" width="98%" style="margin: 0 auto;"
		cellpadding="0" cellspacing="0" border="0" height="100%">
		<tr>
			<td id="leftTd" width="200px" valign="top">
				<br>
				<div id="leftDiv" style="display: none;">
					<div  style="padding-right: 10px; text-align: right;">
						<button class="menu" onclick="filterQuery();">过 滤</button>
						&nbsp;
						<button class="menu" onclick="queryAll();">全 部</button>
					</div>
					<div id="tree"></div>
				</div>
			</td>
			<td id="rightTd" valign="top">
				<table width="99.5%">

					<tr>
						<!-- 
					<td width="10%"><a href="javascript:history.back(-1);" >返回</a></td>
					-->
						<td>

							<div style="margin-top: 10px;">
								<div id="splitBtnSpan"
									style="cursor: pointer; color: #00d; position: relative; top: 35px; left: 0px; width: 20px; float: left; font-size: 13px; height: 38px; padding-top: 2px; background-color: #eeF; text-align: center; margin-right: 5px;">
									<span id="splitBtn">筛选<img border="0"
											src="<%=request.getContextPath()%>images/search/show.png" />
									</span>
								</div>

								<input type="text" name="select-key:queryStr" id="search" />
								<input type="hidden" name="select-key:currentPage"
									id="currentPage" value="1" />
								<input type="hidden" name="select-key:countPerPage"
									id="countPerPage" value="10" />
								<input type="hidden" name="select-key:strWhere" id="strWhere"
									value="<freeze:out value="${select-key:strWhere}"/>" />
								<input type="hidden" name="select-key:filterWhere"
									id="filterWhere" value="" />
								<input type="hidden" name="select-key:oldStrWhere"
									id="oldStrWhere" />
								<input id="submit" type=button onclick="doQuery();"
									class='btnSearch' value="搜 索">

							</div>


						</td>
					</tr>

					<tr>
						<td>

							<!-- 搜索结果信息 -->
							<iframe id='result' name='result' scrolling='no' frameborder=0
								width=100% style="display: none;"></iframe>
						</td>
					</tr>

				</table>
			</td>
		</tr>
	</table>

</freeze:body>
<script language="javascript">

</script>
</freeze:html>
