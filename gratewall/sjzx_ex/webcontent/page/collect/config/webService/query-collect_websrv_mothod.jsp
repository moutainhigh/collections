<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<freeze:html width="650" height="300">
<head>
	<title>用户服务树状展示列表</title>
	<link
		href="<%=request.getContextPath()%>/script/lib/skin-vista/ui.dynatree.css"
		rel="stylesheet" type="text/css">
	<script language="javascript"
		src="<%=request.getContextPath()%>/script/lib/jquery.min.js"></script>
	<script language="javascript"
		src="<%=request.getContextPath()%>/script/lib/jquery-ui.custom.min.js"></script>
	<script language="javascript"
		src="<%=request.getContextPath()%>/script/lib/jquery.dynatree.js"></script>

	<style>
.b {
	font-weight: bold;
}

.tr_bg {
	background: #F4FAFB
}

.text_l {
	text-align: left;
}

.text_r {
	text-align: right;
}

.clear_lb {
	border-left: 0;
}

.clear_rb {
	border-right: 0;
}

.clear_tb {
	border-top: 0;
}

td.rb {
	border-right: 1px solid #aed0ea;
}

td.bb {
	border-bottom: 1px solid #aed0ea;
}

td.lb {
	border-left: 1px solid #aed0ea;
}

td.tb {
	border-top: 1px solid #aed0ea;
}

td.bb {
	border: 1px solid #aed0ea;
}

#tree {
	vertical-align: top;
	width: 250px;
	border: 0px;
	padding: 0px;
}

#tree ul {
	border: 0px;
	background-color: #F4FAFB;
}

#s1 ul li {
	list-style-type: none;
}

#s1 ul li span {
	width: 300px;
}

#s1 table tr {
	background-color: white;
}

.popDiv {
	display: none;
	width: 280px;
	height: 70px;
	background-color: #D1EEEE;
	border: 1px solid gray;
	padding-left: 5px;
	position: absolute;
	padding-top: 3px;
	z-index: 1021;
	padding-right: 5px;
	left: 350px;
	top: 30px;
}

span.pause a:link {
	color: #B4B4B4;
	text-decoration: underline;
}

span.pause a:visited {
	color: #B4B4B4;
	text-decoration: none;
}

span.pause a:hover {
	color: #B4B4B4;
	text-decoration: none;
}

span.pause a:active {
	color: #B4B4B4;
	text-decoration: none;
}

.dynatree-title {
	height: 20px;
	line-height: 20px;
}

.servP {
	display: none;
}

.tya {
	background-color: RGB(245, 250, 250);
}

.tyaT td {
	border: 1px solid #bbb;
	height: 30px;
}

.servP td {
	border: 1px solid #bbb;
}

#page_serv a {
	margin-right: 5px;
}
</style>
</head>
<freeze:body>
	<freeze:title caption="配置Webservice任务" />

	<script language="javascript">
	
	var firstKey = "";
	var checkNode = "";
	
function initTree(){
	var page = new pageDefine("/txn30102001.ajax", "查询某个用户的服务树");
	var taskId = getFormFieldValue("primary-key:collect_task_id");
	//alert(taskId);
	page.addValue(taskId,"select-key:collect_task_id");
	page.callAjaxService("callBack()");
}

function callBack(errCode, errDesc, xmlResults){
	 if(errCode!='000000'){
		alert("查询某个用户的服务树时发生错误！")
		return
	 }
		
	 var wsId =  _getXmlNodeValues(xmlResults,'record:webservice_task_id');
	 var srvNo = _getXmlNodeValues(xmlResults,'record:service_no');
	 var methodNameEn =  _getXmlNodeValues(xmlResults,'record:method_name_en');
	 var methodNameCn =  _getXmlNodeValues(xmlResults,'record:method_name_cn');
	 
     var s = new Array;
     if(wsId!=null&&wsId.length>0){
     	 firstKey = wsId[0];
    	 for(var j=0; j<wsId.length; j++){
    	 	s.push({'title':"("+methodNameEn[j]+")"+methodNameCn[j],'srvNo':srvNo[j],'key':wsId[j],'tooltip':"("+methodNameEn[j]+")"+methodNameCn[j]})
    	 }
     }
   
     
     var r1 =  {"title": "共享接口", "isFolder": true, "children": s,"key":"root" }
     var root = new Array;
     root.push(r1);

     obj = $("#tree").dynatree({
           children: root,
           minExpandLevel: 2,
           generateIds:true,
		   onActivate: function(node) {
				if( node.data.key ){
					if(node.data.key=='root'){
						var page = new pageDefine( "query-collect_ws_info_blank.jsp" );
						var win = getIframeByName('svr_config');
						if( win != null ){
							page.goPage( null, win );
						}
					}else if(node.data.key!=""&&node.data.key!="_2"){
			  			checkNode = node.data.key;
				  		var page = new pageDefine( "/txn30102111.do", "查看方法详细信息" );
						page.addValue(node.data.srvNo, "select-key:service_no");
						page.addValue(node.data.key, "select-key:webservice_task_id");
						var win = getIframeByName('svr_config');
						if( win != null ){
							page.goPage( null, win );
						}
					}
					
				}
			}
		});
		
		
	//将暂停的节点置灰
	var child=root[0].children;
	for(var i=0;i<child.length;i++){
	   if(child[i].is_pause=='1'){
	      var tnode = $("#tree").dynatree("getTree").getNodeByKey(child[i].key);
	      tnode.data.addClass="pause";
	      tnode.render();
	   }
	}
	
	if(firstKey!=""){
	   	var fnode = $("#tree").dynatree("getTree").getNodeByKey(firstKey);
	   	fnode.activate();
	}else{
		var fnode = $("#tree").dynatree("getTree").getNodeByKey("root");
	   	fnode.activate();
	}
}


// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	initTree();
	
	$('#btn_config').click(function(){
		var taskId = getFormFieldValue("primary-key:collect_task_id");
		//alert(taskId);
   		/* var page = new pageDefine("/txn30102112.do","新增方法","modal");
		page.addValue(taskId, "select-key:collect_task_id");
		page.goPage(); */
		var href='/txn30102112.do?select-key:collect_task_id='+taskId;
		window.showModalDialog(href, window, "dialogHeight:280px;dialogWidth:800px;dialogTop:250px;dialogLeft:300px;help:no;scrollbar:no;resizable:no;status:no");
		
		
   })
}

function func_record_prev(){
	//alert('prev');
	var page = new pageDefine( "/txn30101222.do", "配置采集信息");
	var taskId = getFormFieldValue("primary-key:collect_task_id");
	page.addParameter("primary-key:collect_task_id","primary-key:collect_task_id");
	page.goPage();
}

function func_record_next(){ 
	var page = new pageDefine( "/txn30101009.do", "配置采集规则");
	var taskId = getFormFieldValue("primary-key:collect_task_id");
	page.addParameter("primary-key:collect_task_id","primary-key:collect_task_id");
	page.goPage();
}

//删除方法后页面去掉对应的树节点
function delNode(id){
	var fnode = $("#tree").dynatree("getTree").getNodeByKey(id);
	fnode.remove();
	var fnode = $("#tree").dynatree("getTree").getNodeByKey('root');
	fnode.activate();
}

//新增方法后刷新页面
function reloadtree(nodeobj){
	
	nodeobj.srvNo=nodeobj.srvNo.replace(/(\s|\u00A0)+$/,'');
	nodeobj.key=nodeobj.key.toString().replace(/(\s|\u00A0)+$/,'');
	
	//获取根节点
	var fnode=$("#tree").dynatree("getTree").getNodeByKey("root");
	//增加节点
	fnode.addChild(nodeobj);
	
	var newnode = $("#tree").dynatree("getTree").getNodeByKey(nodeobj.key);
	//设置新增方法为选中以显示信息
	newnode.activate();
	
	//为新节点绑定点击事件
	/* var a=$('.dynatree-lastsib ul li:last').find('a').click(function(){
		//id格式    dynatree-id-ff07dd51c0a7450ea88a6c53d1027ea2
		//获取选中节点id
		var nodeid = $(this).parent().parent().attr('id');
		console.log(nodeid);
		var keyid=nodeid.substr(12,nodeid.length);
		console.log(keyid);
		//var page = new pageDefine( "/txn30102111.do", "查看方法详细信息" );		
		//page.addValue(node.data.srvNo, "select-key:service_no");		
		//page.addValue(node.data.key, "select-key:webservice_task_id");
		//var win = getIframeByName('svr_config');
		//if( win != null ){
		//	page.goPage( null, win );
		//}
		
   }) */	 
}
_browse.execute( '__userInitPage()' );
</script>

	<freeze:frame property="primary-key" width="95%">
		<freeze:hidden property="collect_task_id"  caption="采集任务ID" style="width:95%" />
	</freeze:frame>

	<div style="width: 95%; margin-left: 20px">
		<table style="width: 65%">
			<tr>
				<td width="30%">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;"
								width="2" height="25" valign="middle"></td>
							<td height="25"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
								<div
									style="background: url(<%=request.getContextPath()%>/ images/ xzcjbg/ icon_bg .                                                                     png ) left 50% no-repeat; width: 20px; display: inline;"></div>
								第一步，配置基本信息
							</td>
							<td width="5" height="25" valign="middle"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_r.png') left 50% no-repeat;"></td>
						</tr>
					</table>
				</td>
				<td width="30%">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_l.png') left 50% no-repeat;"
								width="2" height="25" valign="middle"></td>
							<td height="25"
								style="color: white; background: url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_c.png') left 50% repeat-x;">
								<div
									style="background: url(<%=request.getContextPath()%>/ images/ xzcjbg/ icon_bg .                                                                     png ) left 50% no-repeat; width: 20px; display: inline;"></div>
								第二步，配置方法信息
							</td>
							<td width="5" height="25" valign="middle"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_r.png') left 50% no-repeat;"></td>
						</tr>
					</table>
				</td>
				<td width="30%">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;"
								width="2" height="25" valign="middle"></td>
							<td height="25"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
								<div
									style="background: url(<%=request.getContextPath()%>/ images/ xzcjbg/ icon_bg .                                                                     png ) left 50% no-repeat; width: 20px; display: inline;"></div>
								第三步，配置规则信息
							</td>
							<td width="5" height="25" valign="middle"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_r.png') left 50% no-repeat;"></td>
						</tr>
					</table>
				</td>

			</tr>
		</table>
	</div>
	<div id="containter">
		<table width="95%" align="center" cellpadding="0" cellspacing="0"
			border="0" >

			<tr>
				<td rowspan="2" valign="top">
					<div id="interInfo" style="width: 100%;">
						<table width="98%" border="0" align="center" class="frame-body"
							cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<table width="100%" cellspacing="0" cellpadding="0" border="0">
										<tr>
											<td class="leftTitle"></td>
											<td class="secTitle">
												&nbsp;接口信息
											</td>
											<td class="centerTitle">
												<table cellspacing="0" cellpadding="0" class="button_table">
													<tr>
														<td class="btn_left"></td>
														<td>
															<div class="btn_add" id="btn_config"></div>
															<!-- INPUT style="WIDTH: 50px" class="btn_prev"
																id="btn_config" value='增加' type=button name='btn_config' /> -->
														</td>
														<td class="btn_right"></td>
													</tr>
												</table>
											</td>
											<td class="rightTitle"></td>
										</tr>
									</table>
								</td>
							</tr>
							<!-- <tr><td height="10px" style="backgrond-color: #f2f2f2;"></td></tr> -->
							<tr class="framerow" style="backgrond-color: #ebffed;">
								<td style="padding: 0px;" colspan='2'>
									<div id="tree" style="border: 2px solid #006699;width:300px;overflow-x:hidden" ></div>
								</td>
							</tr>
						</table>
					</div>
				</td>
				<td width="80%" valign="top">
					<iframe id='svr_config' name='svr_config' scrolling='no'
						frameborder=0 width=100% height=100%
						style="display: block; background-color: #F4FAFB;">
					</iframe>
				</td>
			</tr>
			<tr>
				<td></td>
			</tr>
		</table>
	</div>
	<table align='center' cellpadding=0 cellspacing=0 width="95%">
			<tr>
				<td align="center" height="50">
					<div class="btn_prev" onclick="func_record_prev();"></div>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<div class="btn_next" onclick="func_record_next();"></div>
				</td>
			</tr>
	</table>

</freeze:body>
</freeze:html>
