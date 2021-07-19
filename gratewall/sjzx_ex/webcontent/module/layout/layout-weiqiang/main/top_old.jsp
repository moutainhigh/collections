<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.web.util.RequestUtil,cn.gwssi.common.web.context.UserContext"%>

<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ page import="cn.gwssi.common.context.vo.VoUser"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>
<%@ page import="com.gwssi.common.util.CalendarUtil"%>
<%@ page import="java.util.Date"%>
<%
  HttpSession usersession = request.getSession(false);
  VoUser voUser = (VoUser) usersession.getAttribute(TxnContext.OPER_DATA_NODE); 
  String user=voUser.getOperName();
%>
<freeze:html>
<head>
<title>深圳市市场和质量监督管理委员会</title>

<link href="../newcss/style_new.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../newcss/jquery/jauery.ui/themes/cupertino/jquery.ui.all.css" />  
<link rel="stylesheet" href="../newcss/li-scroller.css" />
<script type="text/javascript" src="../lib/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../lib/jquery/jquery.ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="../lib/jquery/jquery.ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="../lib/jquery/jquery.ui/jquery.effects.core.js"></script>
<script type="text/javascript" src="../lib/jquery/jquery.ui/jquery.effects.slide.js"></script>
<script type="text/javascript" src="../lib/jquery/jquery.ui/jquery.effects.blind.js"></script>
<script type="text/javascript" src="../lib/jquery/jquery.ui/jquery.effects.clip.js"></script>
<script type="text/javascript" src="../lib/jquery/jquery.ui/jquery.effects.fade.js"></script>
<script type="text/javascript" src="../lib/jquery/plugin/jquery.layout.min.js"></script>

<script src="../lib/jquery/plugin/jquery.bgiframe-2.1.2.js"></script>
<script src="../lib/jquery/jquery.ui/jquery.ui.mouse.js"></script>
<script src="../lib/jquery/jquery.ui/jquery.ui.button.js"></script>
<script src="../lib/jquery/jquery.ui/jquery.ui.draggable.js"></script>
<script src="../lib/jquery/jquery.ui/jquery.ui.position.js"></script>
<script src="../lib/jquery/jquery.ui/jquery.ui.resizable.js"></script>
<script src="../lib/jquery/jquery.ui/jquery.ui.dialog.js"></script>
  
<script type="text/javascript" >
	var myLayout={}
	var user='<%=voUser.getUserName()%>';
</script> 
<script type="text/JavaScript">
	function getSelectedNo(){
		return document.getElementById("menuTable").selectedNo;
	}
	
	function setSelectedNo(menuId){
		document.getElementById("menuTable").selectedNo = menuId;
	}
	
	function MM_findObj(n, d) { //v4.01
		var p,i,x;  
		if(!d) 
			d=document; 
		if((p=n.indexOf("?"))>0&&parent.frames.length) {
		    d=parent.frames[n.substring(p+1)].document; 
			n=n.substring(0,p);
		}
		if(!(x=d[n])&&d.all) 
			x=d.all[n]; 
		for (i=0;!x&&i<d.forms.length;i++) 
			x=d.forms[i][n];
		for(i=0;!x&&d.layers&&i<d.layers.length;i++) 
			x=MM_findObj(n,d.layers[i].document);
		if(!x && d.getElementById) 
			x=d.getElementById(n); 
		return x;
	}
		
	// 当前选中的节点编号:一级菜单
	var _selected_toolbar_level1 = null;
		
	// 退出系统
	function system_logout(){
		if(!window.confirm("是否退出系统？")){
		    return;
		}
		var page = new pageDefine( "<%=request.getContextPath()%>/logout.jsp", "", "window", 0, 0);
		page.goPage( );	
	}

	// 退出系统
	function logoutOnWindowClose(){		
	}

	// 修改密码
	function update_pwd(){
		//var page = new pageDefine( "<%=request.getContextPath()%>/page/sysmgr/user/modifypwd-yhgl.jsp", "", "modal");
		//page.goPage( );	
		var result = window.showModalDialog("<%=request.getContextPath()%>/page/sysmgr/user/modifypwd-yhgl.jsp", "修改用户密码", "dialogWidth:420px; dialogHeight:220px;status:no;help:yes"); 
		if(result=='success'){
			var page = new pageDefine( "<%=request.getContextPath()%>/logout.jsp", "", "window", 0, 0);
			page.goPage( );	
		}
	}

	// 设置按钮的状态
	function toolbar_setItemStyle( menuId, className ){
		if( menuId != null ){
			var tbl = document.getElementById( 'toolbar' );
			var row = tbl.rows[0];
			var cell = row.cells['toolbar_' + menuId];
			if( cell != null ){
				cell.className = className;
			}
		}
	}
	
	// 释放DOM对象
	function _menu_release( menuDefine ){
		var menuTable = menuDefine.menuTable;
		if( menuTable != null ){
			var rowNumber = menuTable.rows.length;
			for( var ii=0; ii<rowNumber; ii++ ){
				menuTable.deleteRow( 0 );
			}		
			menuTable = null;
		}	
	}
		
	function getLinkElement(){
		var linkElement = event.srcElement;
		while(linkElement = linkElement.parentElement){
			if(linkElement.tagName.toUpperCase() == "A"){
				break;
			}
		}
		return linkElement;
	}
	
	//csdb点击一级菜单事件
	function toolbar_onclick( menuId ){
		top.code.initFrame();
		// getFuncCodeOnLink
		var oldMenuId = '';
		//为首页6个监控图标跳转做特殊标志
		if(menuId == 52000001){
			oldMenuId += menuId;
			menuId = "5000000";
		}
		if (!menuId){
			menuId = getLinkElement().funcCode;
		}
		//清除上一次选中的菜单样式
		var oldMenuSelectedId = menuSelectId();
		var osbtn = document.getElementById(oldMenuSelectedId);
		if(osbtn){
			$(osbtn).removeClass('on');
			var oldsrc = $(osbtn).find("div").css("background-image");
			if(oldsrc){
				oldsrc = oldsrc.replace(/_on\.png\"\)$/ig, ".png\")");
				$(osbtn).find("div").css({"background-image": oldsrc, "color": "white"});
			}
		}
		setSelectedNo(menuId);
		if (menuId == '000000'){
		    //parent.window.location='/main.jsp';
			// top.window.document.getElementById("mainOldMenuId").value=menuId;
			top.window.document.getElementById("mainOldForm").submit();
			//parent.listset.cols="0,0,100%";
			
			//parent._showFunctionPage('/txn53000009.do' );
		}else if(menuId == '9900000'){
			parent.listset.cols="0,0,100%";
			parent._showFunctionPage('/txn53000010.do' );
		}else{
			//parent._showFunctionPage('/welcome.jsp' );
			// 执行清除操作	
			var row = document.getElementById("menuTable").rows[0];
			var selectedNo = getSelectedNo();
			//判断一级菜单是否有权限
			var obj = top.menu.lookupItem(menuId);
			if(obj==null){
			//清空右边frame
			//清空左边菜单
			_menu_release( top.menu);
			}else{
				top.showSubMenu( menuId );
				var root = top.menu.rootNode;
				var firstLeafNode = getFirstLeafNode( obj );
				if(oldMenuId == "52000001"){
					firstLeafNode = "5500000";
				}
			}
		}
		//显示选中的菜单样式
		var newMenuSelectedId = menuSelectId();
		var sbtn =document.getElementById(newMenuSelectedId)
		if(sbtn){
			var oldsrc = $(sbtn).find("div").css("background-image");
			if(oldsrc && oldsrc.indexOf('_on.png') == -1){
				oldsrc = oldsrc.replace(/\.png\"\)$/ig, "_on.png\")").replace(/_hover/g, '');
				$(sbtn).find("div").css("background-image", oldsrc);
			}
			$(sbtn).find("div").css({"color": "RGB(53, 153, 255)"});
			$(sbtn).addClass("on");
		}
		parent.window.frames['code'].document.getElementById("menu_new").innerHTML = bulidLeft(menuId);
		parent.window.frames['code'].document.getElementById("left_1").click();
		top.code.bindMenu();
		top.code.hoverMenu();
		top.code.goFirst(firstLeafNode);
	}
	
	//形成左侧菜单面板用
	function getSubNodes(menuId){
		var obj = top.menu.lookupItem(menuId);
		if(obj!=null && obj.submenu!=null && obj.submenu!=undefined){
			return obj.submenu.items;
		}
        return;
	}
	
	function URLencode(sStr) {
    	return escape(sStr).replace(/\=/g, '%3D').replace(/\//g,'%2F').replace(/\&/g,'%26 ');
	}

	function bulidLeft(menuId){
		if(menuId == 10000000){
			return '';
		}
		var html='<ul id="newmenu" class="menu">';
        var rootItems=getSubNodes(menuId);
		//遍历一级菜单
		if(rootItems!=null){
			for(var i=0;i<rootItems.length;i++){
				//写一级内容
				var level1_content=rootItems[i].text;
				var lever2=getSubNodes(rootItems[i].id);
				if(lever2==null){
					var newId = parseInt(rootItems[i].id);
					/*if(newId && newId>41000000 && newId<43000000){
						level1_content='<a href="/csdb/report/report.jsp?url='+URLencode(rootItems[i].url)+'" id="'+rootItems[i].id+'" target="main">'+rootItems[i].text+'</a>';
					}else{*/
					level1_content='<a id="'+rootItems[i].id+'" target="main" href="'+rootItems[i].url+'">'+rootItems[i].text+'</a>';
					//}
				}else{
					if(rootItems[i].url.length > 0){
						level1_content='<a style="" id="'+rootItems[i].id+'" target="main" href="'+rootItems[i].url+'">'+rootItems[i].text+'</a>';
					}else{
						level1_content='<a style="" id="'+rootItems[i].id+'" href="#">'+rootItems[i].text+'</a>';
					}
				}
				html+='<li class="level1">'+level1_content;
		    //遍历二级菜单
				if(lever2!=null && lever2.length>0){
					html+='<ul id="item_'+i+'" class="level2">';
					for(var j=0;j<lever2.length;j++){
					   //写二级内容
					   var isExist="";
					   var level2_content="";
					   level2_content=lever2[j].text;
                       if(lever2[j].url.length!=0){
							html+='<li '+isExist+'><a id="'+lever2[j].id+'" target="main" href="'+lever2[j].url+'">'+level2_content+'</a>';
					   }else{
							html+='<li '+isExist+'><a id="'+lever2[j].id+'" href="#">'+level2_content+'</a>';
					   }
						html+='</li>';	
					}
					html+='</ul>';
				}
				html+='</li>'
			}
		}
		html+='</ul>';
		return html;
	}

	function getFirstLeafNode(menuObj){
		if(typeof(menuObj)!="undefined" && menuObj!=null){
			var submenu = menuObj.submenu;
			if ( submenu && submenu.items && submenu.items.length > 0){
				return getFirstLeafNode(menuObj.submenu.items[0]);
			}else{
				return menuObj.id;
			}
	   }
	}
		
	function _createToolbar(){
		var tbl = document.getElementById( 'toolbar' );
			
		// 设置样式
		tbl.cellSpacing='0';
		tbl.cellPadding='0';
		tbl.border='0';
		tbl.style.tableLayout = 'fixed';
		tbl.onselectstart = new Function( 'return false;' );
			
		// 取TR
		var row = tbl.insertRow( 0 );
		row.width = '100%';
		row.align = "center";
		row.vAlign = "center";
			
		// 生成按钮
		var items = top.menu.submenu.items;
		for( var ii=0; ii<items.length; ii++ ){
			var id = items[ii].id;
			var text = items[ii].text;
			
			// 插入<TD>
			var myCell = row.insertCell( row.cells.length );
			myCell.noWrap = true;
			myCell.id = 'toolbar_' + id;
			myCell.width = '72px';
			myCell.innerHTML = text;
			myCell.className = 'toolbar-item';
				
			// 事件：onmouseover、onmouseout、onclick
			myCell.onclick = new Function( 'toolbar_onclick("' + id + '")' );
			myCell.onmouseover = new Function( 'toolbar_onmouseover("' + id + '")' );
			myCell.onmouseout = new Function( 'toolbar_onmouseout("' + id + '")' );
				
			// 分割符
		
			//var myCell = row.insertCell( row.cells.length );
			//myCell.width = '1px';
			//myCell.innerHTML = "<img src=../img/main/mail_10.jpg>";
		}
			
		var myCell = row.insertCell( row.cells.length );
			
		
		// 设置当前选中的节点
		var selectedNode = top.menu.selectedNode;
		if( selectedNode == null ){
			selectedNode = top.menu.submenu.items[0];
		}else{
			while( selectedNode.parentNode != top.menu ){
				selectedNode = selectedNode.parentNode;
				
			}
		}
			
		// 选中节点
		if( selectedNode != null ){
			toolbar_onclick( selectedNode.id );
		}
	}

	// 显示菜单
	function showMenuWithFuncCode(){
		var items = top.menu.submenu.items;
		if (top.menu.lookupItem("000000") ){
				$('#index').show();
				//$('#index').next('li.line').show();
		}
		if (top.menu.lookupItem("2000000") ){
				$('#qua').show();
				$('#qua').prev('li.line').show();
		}
		if (top.menu.lookupItem("3000000") ){
				$('#col').show();
				$('#col').next('li.line').show();
		}
		if (top.menu.lookupItem("4000000") ){
				$('#share').show();
				$('#share').prev('li.line').show();
		}
		if (top.menu.lookupItem("5000000")){
				$('#mon').show();
				$('#mon').prev('li.line').show();
		}
		if (top.menu.lookupItem("6000000") ){
				$('#log').show();
				$('#log').prev('li.line').show();
		}
		if (top.menu.lookupItem("100000") ){
				$('#sys').show();
				$('#sys').prev('li.line').show();
		}
		if(top.menu.lookupItem("7000000") ){
				$("#help").show();
				$('#help').prev('li.line').show();
		}
		var menuId="<%=null!=request.getParameter("menuId") ? request.getParameter("menuId") : ""%>";
		if(menuId!=''){
		   toolbar_onclick(menuId);
		}	
	}
	//页面框架left的隐藏与显示
	function menuShow(){
		if(top.rowset.rows == "114,*"){
			$("#logo").hide();
			top.rowset.rows = "60,*";
			document.getElementById("yc").src="../images_new/show.jpg";
			document.getElementById("yc").title="点击展开";
		}else{
			$("#logo").show();
			top.rowset.rows = "114,*";
			document.getElementById("yc").src="../images_new/hidden.jpg";
			document.getElementById("yc").title="点击隐藏";
		}
	}
	//记录当前菜单项id,方便显示样式操作
	function menuSelectId(){
		if(getSelectedNo() == '000000')
			return 'index';
		else if(getSelectedNo() == '4000000')
			return 'share';
		else if(getSelectedNo() == '2000000')
			return 'qua';
		else if(getSelectedNo() == '100000')
			return 'sys';
		else if(getSelectedNo() == '200000')
			return 'query';
		else if(getSelectedNo() == '5000000')
			return 'mon';
		else if(getSelectedNo() == '6000000')
			return 'log';
		else if(getSelectedNo() == '3000000')
			return 'col';
		else if(getSelectedNo() == '7000000')
			return 'help';
	}
	
	function menuOver(type){
		var typeStyle = document.getElementById(type);
		if(typeStyle){
			var oldsrc = $(typeStyle).find("div").css("background-image");
			if(oldsrc.indexOf("_on")  == -1 && oldsrc.indexOf("_hover")  == -1)
				oldsrc = oldsrc.replace(/\.png\"\)$/ig, "_hover.png\")");
			$(typeStyle).find("div").css({"background-image": oldsrc, "color": "RGB(53, 153, 255)"});
		}
			//typeStyle.className = 'on';
	}
	
	function menuOut(type){
		var typeStyle = document.getElementById(type);
		var nowMenuSelected = menuSelectId();
		if(type != nowMenuSelected){
			var oldsrc = $(typeStyle).find("div").css("background-image");
			oldsrc = oldsrc.replace(/_hover\.png\"\)$/ig, ".png\")");
			$(typeStyle).find("div").css({"background-image": oldsrc, "color": "white"});

		}
		//	typeStyle.className = '';

	}

	//意见反馈
	function feedback(){
		var page = new pageDefine("/sys/feedback/insert-sys_feedback.jsp", "意见反馈","model");
		page.goPage();
	}

	function bulidQuick(menuId){
		if(menuId != 10000000){
			return '';
		}
		var html='<ul id="quickmenu" class="menu">';
        var rootItems=getSubNodes(menuId);
		//遍历一级菜单
		if(rootItems!=null){
			for(var i=0;i<rootItems.length;i++){
				//写一级内容
				var level1_content=rootItems[i].text;
				var lever2=getSubNodes(rootItems[i].id);
				if(lever2==null){
					var newId = parseInt(rootItems[i].id);
					level1_content='<a style="display:none;" id="'+rootItems[i].id+'" target="main" href="'+rootItems[i].url+'"><span>'+rootItems[i].text+'</span></a>';
				}else{
					if(rootItems[i].url.length > 0){
						level1_content='<a style="display:none;" id="'+rootItems[i].id+'" target="main" href="'+rootItems[i].url+'"><span>'+rootItems[i].text+'</span></a>';
					}else{
						level1_content='<a style="display: none;" id="'+rootItems[i].id+'" href="#"><span>'+rootItems[i].text+'</span></a>';
					}
				}
				html+='<li class="level1 item_'+(i+1)+'" title="'+rootItems[i].text+'" onclick="clickandfire(\''+i+'\')">'+level1_content + '<span>'+rootItems[i].text+'</span>';
				html+='</li>'
			}
		}
		html+='</ul>';
		return html;
	}

	$(document).ready(function(){
		var htmlStr = bulidQuick(10000000);
		parent.window.frames['code'].document.getElementById("ipage1").innerHTML = htmlStr;
		top.code.bindMouseOver();
		top.code.bindMouseOut();
	})

</script>
</head>

<body style="overflow-y:hidden;overflow-x:hidden;" onLoad="showMenuWithFuncCode();" >
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr>
		<td colspan="2" id="logo">
		<div class="ui-layout-north" align="center">
			<div class="header" >
				<div  class="l_logo"></div>	
				<div  class="r_cont">
					<ul>
						<!-- <li onclick="system_logout()" title="退出系统" class="quit">&nbsp;</li> -->
						<li title="加入收藏" onclick="window.external.AddFavorite(top.window.location.href, '北京工商数据交换管理平台');" class="reg">&nbsp;</li>
						<!-- <li onclick="update_pwd()" title="修改密码" class="update_pwd">&nbsp;</li> -->
					</ul>
				</div>  
			</div>	
		</div>	
		</td>
    </tr>
	<tr style="background:url(../images_new/nav_bg.png)">
    <td valign="middle" style="" width="200">
    	<div class="login_user"><span style="margin:20px;">登陆用户: <%=user%></span></div>
    	<div class="login_date">
    		<script language=JavaScript>
								s_date = new Date();
								var weekDay = "";
								if(s_date.getDay() == 1){
									weekDay = "星期一";
								}
								if(s_date.getDay() == 2){
									weekDay = "星期二";
								}
								if(s_date.getDay() == 3){
									weekDay = "星期三";
								}
								if(s_date.getDay() == 4){
									weekDay = "星期四";
								}
								if(s_date.getDay() == 5){
									weekDay = "星期五";
								}
								if(s_date.getDay() == 6){
									weekDay = "星期六";
								}
								if(s_date.getDay() == 7){
									weekDay = "星期日";
								}
								if(s_date.getDay() == 0){
									weekDay = "星期日";
								}
								var currYear = s_date.getFullYear();
								var currMonth = s_date.getMonth() + 1;
								var currDay = s_date.getDate();
								document.write("<span style='margin-left:22px;margin-top: 3px;height:10px;'>"+currYear + "年" + currMonth + "月" +	currDay + "日" + " " + weekDay+"</span>");
							</script>
    	</div>
    </td>
    <td>
	<div class="nav" id="nav">
		<ul>
		<li  id="index" style="cursor:pointer;display:;" class="on" style="" onmouseOver="menuOver('index');" onmouseOut="menuOut('index');" onClick="toolbar_onclick('000000');">
			<div class="pngFix" style="background:url(../images_new/sys_home_on.png) 50% 10px no-repeat;color:RGB(53, 153, 255);">
				首&nbsp;&nbsp;页
			</div>
		</li>
        <li class="line"></li>
        <li  id="qua"  style="cursor:pointer;display:none;" onmouseOver="menuOver('qua');" onmouseOut="menuOut('qua');" onClick="toolbar_onclick(2000000);">
        	<div class="pngFix" style="background:url(../images_new/sys_res.png) 50% 10px no-repeat;">
				资源管理
			</div>
        </li>
		<li class="line"></li>
		<li  id="share"  style="cursor:pointer;display:none;" onmouseOver="menuOver('share');" onmouseOut="menuOut('share');" onClick="toolbar_onclick(4000000);">
			<div class="pngFix" style="background:url(../images_new/sys_svr.png) 50% 10px no-repeat;">
				共享服务
			</div>
		</li>
		<li class="line"></li>
		<li  id="col"  style="cursor:pointer;display:none;" onmouseOver="menuOver('col');" onmouseOut="menuOut('col');" onClick="toolbar_onclick(3000000);">
			<div class="pngFix" style="background:url(../images_new/sys_col.png) 50% 10px no-repeat;">
				采集任务
			</div>
		</li>
        <li class="line"></li>
        <li  id="mon"  style="cursor:pointer;display:none;" onmouseOver="menuOver('mon');" onmouseOut="menuOut('mon');" onClick="toolbar_onclick(5000000);">
			<div class="pngFix" style="background:url(../images_new/sys_mon.png) 50% 10px no-repeat;">
				运行监控
			</div>
		</li>
		<li class="line"></li>
		<li  id="log"  style="cursor:pointer;display:none;" onmouseOver="menuOver('log');" onmouseOut="menuOut('log');" onClick="toolbar_onclick(6000000);" >
			<div class="pngFix" style="background:url(../images_new/sys_log.png) 50% 10px no-repeat;">
				日志管理
			</div>
		</li>
		<li class="line"></li>
		<li  id="sys"  style="cursor:pointer;display:none;" onmouseOver="menuOver('sys');" onmouseOut="menuOut('sys');" onClick="toolbar_onclick(100000);">
			<div class="pngFix" style="background:url(../images_new/sys_mgr.png) 50% 10px no-repeat;">
				系统管理
			</div>
		</li>
		<li class="line"></li>
		<li  id="help"  style="cursor:pointer;display:none;" onmouseOver="menuOver('help');" onmouseOut="menuOut('help');" onClick="toolbar_onclick(7000000);">
			<div class="pngFix" style="background:url(../images_new/sys_help.png) 50% 10px no-repeat;">
				帮助中心
			</div>
		</li><!-- 
		<li class="yc" style="float:right;" style="cursor:pointer; display: none;"><img id="yc" style="border:0px;margin-top:2px;" width="30px" height="30px" onClick="menuShow();" src="../images_new/hidden.jpg" title="点击隐藏" /></li> -->
		</ul>
	</div>
    </td>
  
    </tr>
</table>
</div>

<div style="display:none">
<table width="100%" height="27" border="0" cellpadding="0" cellspacing="0" class="daohang_bg" id="menuTable" selectedNo="000000">
  <tr>
    <td width="70" align="center"><a href="#" funcCode="000000" onClick="toolbar_onclick();" ><img src="../img/dh_1_1.jpg" name="Image3" width="50" height="27" border="0" id="Image3" /></a></td>
    <td width="93" align="center"><a href="#" id="funccodeLink" style="display:none" funcCode="40000000" onClick="toolbar_onclick();" ><img src="../img/dh_3_1.jpg" name="Image5" width="73" height="27" border="0" id="Image5" /></a></td>
    <td width="91" align="center"><a href="#" id="funccodeLink" style="display:none" funcCode="50000000" onClick="toolbar_onclick();"><img src="../img/dh_4_1.jpg" name="Image6" width="71" height="27" border="0" id="Image6" /></a></td>
    <td width="107" align="center"><a href="#" id="funccodeLink" style="display:none" funcCode="30000000" onClick="toolbar_onclick();" ><img src="../img/dh_5_1.jpg" name="Image7" width="87" height="27" border="0" id="Image7" /></a></td>
    <td width="97" align="center"><a href="#" id="funccodeLink" style="display:none" funcCode="100000" onClick="toolbar_onclick();" ><img src="../img/dh_6_1.jpg" name="Image8" width="77" height="27" border="0" id="Image8" /></a></td>
	<td width="97" align="center"><a href="#" id="funccodeLink" style="display:none" funcCode="3000000" onClick="toolbar_onclick();" ><img src="../img/dh_6_1.jpg" name="Image8" width="77" height="27" border="0" id="Image8" /></a></td>
    <td></td>
</tr>
</table>
</div> 

<iframe style="display:none;position:absolute; right:0;top:0;width:35px;" src="refreshPage.html" ></iframe>
</body>
<script type="text/javascript" language="javascript">
	//定时更新当前用户的登录时间 
	window.setInterval(updateSessionTime, 1000*60*30);
	function updateSessionTime(){
		var page = new pageDefine("/txn81100004.ajax", "企业详细信息");  
		page.addValue("<%=usersession.getId()%>","select-key:session_id");
		page.callAjaxService("doUpdate");
	}
	function doUpdate(errCode, errDesc, xmlResults){
		if(errCode == '000000'){
			return;
		}
	}
</script>
</freeze:html>
