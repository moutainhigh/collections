<!--


document.write('<script language="JavaScript" src="' + rootPath + '/script/mtmcode.js"></script>\n' );

// tree 对应的菜单
var xPopupMenu = null;

// 树定义
// name=树的名称，最后生成的树的名称，包括变量名称和table的名称
// root=根节点的标题
// aspect=显示方式; menu=弹出菜单方式，list=树方式，check=带checkbox，tree=树
// visible=是否直接生成目录：true=直接生成菜单、false=手工生成
// idname=关键字字段的名称
// rootpage=根节点导航的页面名称
// bgcolor=背景色
// textcolor=字体颜色
// flodercloseicon=目录的图标
// floderopenicon=目录打开时的图标
// nodeicon=节点的图标
// selectedNode=在目录中选中的节点
function TreeDefine( name, root, rootpage, aspect, visible, 
	txnCode, nodename, pidname, idname, textname, memoname, flodername, iconname, openname,
	bgcolor, textcolor, flodercloseicon, floderopenicon, nodeicon,
	onclickFunc, ondblclickFunc, onmouseoverFunc, onmouseoutFunc, 
	onmousemoveFunc, onmousedownFunc, onmouseupFunc, onfocusFunc, selectedNode, nodes )
{
	this.frame = null;
	this.name = name;
	this.root = root;
	this.rootpage = rootpage;
	this.aspect = aspect;
	this.visible = visible;
	
	// 保存id、pid等的名称
	this.txnCode = txnCode;
	this.nodename = nodename;
	this.pidname = pidname;
	this.idname = idname;
	this.textname = textname;
	this.memoname = memoname;
	this.iconname = iconname;
	this.openname = openname;
	
	// 用于保存该节点是否是目录节点标志de名称：true=是目录、flase=不是目录。如果内容是空，一次加载目录；否则分批生成
	this.flodername = flodername;
	
	// 背景和图标
	this.bgcolor = bgcolor;
	this.textcolor = textcolor;
	this.flodercloseicon = flodercloseicon;
	this.floderopenicon = floderopenicon;
	this.nodeicon = nodeicon;
	
	// 动作
	this.onclickFunc = onclickFunc;
	this.ondblclickFunc = ondblclickFunc;
	this.onmouseoverFunc = onmouseoverFunc;
	this.onmouseoutFunc = onmouseoutFunc;
	this.onmousemoveFunc = onmousemoveFunc;
	this.onmousedownFunc = onmousedownFunc;
	this.onmouseupFunc = onmouseupFunc;
	this.onfocusFunc = onfocusFunc;
	
	// 节点列表
	this.nodes = nodes;
	
	// 对应的目录
	this.menu = null;
	
	// 当前选择的节点
	this.selectedNode = null;			// 在目录中选中的节点，是MTMenuItem
	this.srcElement = null;				// 选中的节点对应的<TD>
	this.eventName = null;				// 事件名称
	this.setSelectedNode = xmenu_setSelectedNode;	// 设置当前选中的节点
	
	// 当前选中的节点:先保存节点的ID
	if( selectedNode == '' || selectedNode == null ){
		this.selectedNode = null;
	}
	else if(typeof(selectedNode) != "object"){
		this.selectedNode = selectedNode;
	}
	else{
		this.selectedNode = selectedNode.id;
	}
	
	// check模式下的,取选中节点的内容和设置选中节点
	this.getCheckValue = xmenu_getCheckValue;
	this.setCheckValue = xmenu_setCheckValue;
	
	// 函数
	this.create = xmenu_create;
	this.getHiberarchy = xmenu_getHiberarchy;
	this.lookupNode = xtree_lookupNode;	// 根据编号查询节点
	this.addNode = xtree_addNode;		// 增加节点
	this.viewNode = xtree_viewNode;
	this.openView = xtree_openView;
	this.moveNode = xtree_moveNode;
	this.openMoveNodeWindow = xtree_openMoveNodeWindow;	//打开移动节点的窗口
	
	// 鼠标事件
	this.onMouseEvent = xtree_onMouseEvent;
	
	// 动态下载子节点的函数
	this.loadFloder = xtree_loadFloder;
	this.insertFloder = xtree_insertFloder;
	
	// 移动节点的动作
	this.onMoveNode = null;
	
	// 保留
	this.insertAfterNode = xtree_insertAfterNode;
	this.insertBeforeNode = xtree_insertBeforeNode;
	this.updateNode = xtree_updateNode;
	this.deleteNode = xtree_deleteNode;
	
	// 菜单
	this.menuNode = null;	// 在菜单中选中的节点,处理菜单动作时设置
	this.onPopupMenuClick = null;	// 点击菜单时执行的动作，类似菜单中的onclickFunc属性
	this.popupMenuClick = xmenu_popupMenuClick;	// 点击菜单时执行的动作
	this.createPopupMenu = xmenu_createPopupMenu; // 创建弹出菜单的函数
	this.popupMenu = xtree_popupMenu;	// 打开弹出菜单的函数
}


// 节点定义
function XmenuNode( parentid, id, text, icon, openicon, memo, floder )
{
	this.parentid = parentid;
	this.id = id;
	this.text = text;
	this.icon = icon;
	this.openicon = openicon;
	this.memo = memo;
	
	// 是否目录：true=是、false=否 (flodername!='' 时有效)
	if( floder == true || floder == 'true' ){
		this.floder = true;
	}
	else{
		this.floder = false;
	}
	
	// 菜单中的ID
	this.menuId = null;
	
	// 子节点
	this.subNodes = null;
}


// 对菜单项排序，和检索
function _getSortNodes( nodes )
{
	var sortNode = new Array(nodes.length);
	for( var ii=0; ii<nodes.length; ii++ ){
		var node = new Array();
		node[0] = nodes[ii].id;
		node[1] = ii;
		sortNode[ii] = node;
	}
	
	// 排序
	return sortNode.sort();
}

// 生成层次节点
function xmenu_getHiberarchy()
{
	var	h_nodes = new Array();
	
	if( this.nodes == null || this.nodes.length < 1 ){
		return;
	}
	
	// 生成排序列表
	var sortNodes = _getSortNodes( this.nodes );
	
	// 生成层次树
	for( var ii=0; ii<this.nodes.length; ii++ ){
		var node = this.nodes[ii];
		if( node.parentid == null || node.parentid == '' ){
			h_nodes[h_nodes.length] = node;
		}
		else{
			var idx = sortNodes.binarySearch( 0, node.parentid );
			if( idx >= 0 ){
				idx = sortNodes[idx][1];
				var nd = this.nodes[idx];
				
				if( nd.subNodes == null ){
					nd.subNodes = new Array();
				}
				
				nd.subNodes[nd.subNodes.length] = node;
			}
			else{
				//alert( '没有找到节点[' + node.id + ']的父节点[' + node.parentid + ']' );
			}
		}
	}
	
	return h_nodes;
}


// 取选中的节点列表
function xmenu_getCheckValue()
{
	return this.menu.getCheckValue();
}

function xmenu_setCheckValue( value )
{
	return this.menu.setCheckValue( value );
}


// 创建树
function xmenu_create( width, height )
{
	var	nodes = this.getHiberarchy();
	if( nodes == null || nodes.length == 0 ){
		return;
	}
	
	// 样式的字体颜色
	if( this.textcolor == null || this.textcolor == '' ){
		this.textcolor = '#333';
	}
	
	// 选中节点
	var sid = '';
	if( this.selectedNode != null ){
		sid = this.selectedNode;
	}
	
	// 菜单类型
	var xtype = 'tree';
	if( this.aspect == 'menu' ){
		xtype = 'tree';
	}
	else if( this.aspect == 'tree' ){
		xtype = 'tree';
	}
	else if( this.aspect == 'check' ){
		xtype = 'check';
	}
	else{
		this.aspect = "list"
		xtype = "list";
	}
	
	// 创建全局变量
	var menuName = this.name.replace( '-', '_' );
	var varName = 'xmenu_' + menuName;
	
	// 必须是全局变量
	document.write( '<script language="javascript">\n' );
	document.write( 'var ' + varName + ' = new MTMenu("' + varName + '", "' + xtype + '");\n' );
	document.write( menuName + '.menu = ' + varName + ';\n' );
	document.write( '</script>\n' );
	
	document.write( "<input type='hidden' name='" + this.name + ":selected-node' id='" + this.name + ":selected-node' value='" + sid + "'>" );
	
	// 生成DIV
	var xtreeDefine = this;
	this.frame = document.getElementById( 'xtree_' + this.name );
	if( this.frame == null ){
		document.write( "<div id=xtree_" + this.name + " style='z-index:1000; width:" + width + "; height:" + height + "' class='scroll-div'>\n" );
		document.write( "<TABLE margin='0' cellSpacing='0' cellPadding='0' width='100%' border='0'><tr height='1px'><td colspan='2'></td></tr><tr valign='top'><td width='5px'></td><td width='99%'>\n" );
		document.write( "<TABLE id='xmenu_" + this.name + "' width='100%'></table></td></tr></table></div>\n" );
		
		// 背景色
		this.frame = document.getElementById( 'xtree_' + this.name );
		this.frame.style.backgroundColor = this.bgcolor;
	}
	
	var menuTable = document.getElementById( 'xmenu_' + this.name );
	menuTable.style.color = this.textcolor;
	menuTable = null;
	
	// 下载子节点的函数
	if( this.flodername != '' ){
		this.menu.loadFloderFunc = function(menuNode){ return xtreeDefine.loadFloder(menuNode); };
	}
	
	// 事件处理函数
	this.menu.onMouseEventFunction = function( eventName, node, srcElement ){ return xtreeDefine.onMouseEvent(eventName, node, srcElement); };
	
	// 设置缺省值
	if( this.flodercloseicon =! null && this.flodercloseicon != '' ){
		this.menu.menuIcons.floderClosed = this.flodercloseicon;
	}
	
	if( this.floderopenicon != null && this.floderopenicon != '' ){
		this.menu.menuIcons.floderOpen = this.floderopenicon;
	}
	
	if( this.nodeicon != null && this.nodeicon != '' ){
		this.menu.menuIcons.nodeIcon = this.nodeicon;
	}
	
	this.menu.menuIcons.menuText = this.root;
	this.menu.menuIcons.rootIcon = "menu_new_root2.gif";
	
	// 首页
	if( this.rootpage != '' ){
		this.menu.MTHomePage = this.rootpage;
	}
	
	// 创建菜单定义
	xmenu_createTree( this.menu, nodes );
	
	if( this.visible ){
		this.menu.startMenu(true);
		
		// 设置活动节点
		if( this.selectedNode != null ){
			this.menu.fireEvent( this.selectedNode );
		}
	}
}


function xmenu_createTree( menu, nodes, parentNode )
{
	var stack = new Array();
	
	// 初始化:子节点，上级菜单节点
	stack.push( [nodes, null] );
	
	// 生成列表
	var menuItem = null;
	while( stack.length > 0 ){
		var n = stack.pop();
		var ns = n[0];
		var pnd = n[1];
		for( var ii=0; ii<ns.length; ii++ ){
			var nd = ns[ii];
			
			// 创建节点 ==> text, url, target, tooltip, icon, openIcon, id, floder(目录标志)
			var obj = new MTMenuItem(nd.text, null, null, nd.text, nd.icon, nd.openicon, nd.id, nd.floder);
			nd.menuId = obj.number;
			menuItem = (pnd == null) ? menu.addSubItem(obj) : pnd.addSubItem(obj);
			
			// 判断是否有子节点:子节点，上级菜单节点
			if( nd.subNodes != null ){
				stack.push( [nd.subNodes, menuItem] );
			}
		}
	}
}


// 查找节点
function xtree_lookupNode( id )
{
	if( this.nodes == null ){
		return null;
	}
	
	if( typeof id == 'object' ){
		var t = id.constructor.toString();
		var ptr = t.indexOf( '(' );
		if( ptr < 0 ){
			return null;
		}
		
		t = t.substring(0, ptr).ltrim();
		t = t.substring(8).trim();
		if( t != 'MTMenuItem' ){
			return null;
		}
		
		// 查找菜单节点
		id = id.number;
		for( var ii=0; ii<this.nodes.length; ii++ ){
			if( this.nodes[ii].menuId == id ){
				return this.nodes[ii];
			}
		}
	}
	else{
		for( var ii=0; ii<this.nodes.length; ii++ ){
			if( this.nodes[ii].id == id ){
				return this.nodes[ii];
			}
		}
	}
	
	return null;
}

// 设置当前选中的节点
function xmenu_setSelectedNode( node )
{
	// 设置活动节点
	if( node != null && node != '' ){
		this.menu.fireEvent( node );
	}
}


// 鼠标事件
function xtree_onMouseEvent( eventName, node, srcElement )
{
	var	fn = null;
	this.srcElement = srcElement;
	this.eventName = eventName;
	
	// 设置隐藏域
	var obj = document.getElementById(this.name + ':selected-node');
	
	// 事件
	switch( eventName ){
	case 'onclick':
		this.selectedNode = node;
		if( obj != null ) obj.value = node.id;
		if( this.onclickFunc != null && this.onclickFunc != '' ) fn = this.onclickFunc;
		break;
		
	case 'ondblclick':
		this.selectedNode = node;
		if( obj != null ) obj.value = node.id;
		if( this.ondblclickFunc != null && this.ondblclickFunc != '' ) fn = this.ondblclickFunc;
		break;
		
	case 'onmouseover':
		if( this.onmouseoverFunc != null && this.onmouseoverFunc != '' ) fn = this.onmouseoverFunc;
		break;
		
	case 'onmouseout':
		if( this.onmouseoutFunc != null && this.onmouseoutFunc != '' ) fn = this.onmouseoutFunc;
		break;
		
	case 'onmousemove':
		if( this.onmousemoveFunc != null && this.onmousemoveFunc != '' ) fn = this.onmousemoveFunc;
		break;
		
	case 'onmousedown':
		if( this.onmousedownFunc != null && this.onmousedownFunc != '' ){
			this.selectedNode = node;
			if( obj != null ) obj.value = node.id;
			fn = this.onmousedownFunc;
		}
		
		break;
		
	case 'onmouseup':
		if( this.onmouseupFunc != null && this.onmouseupFunc != '' ){
			this.selectedNode = node;
			if( obj != null ) obj.value = node.id;
			fn = this.onmouseupFunc;
		}
		
		break;
		
	case 'onfocus':
		if( this.onfocusFunc != null && this.onfocusFunc != '' ) fn = this.onfocusFunc;
		break;
	}
	
	if( fn != null ){
		if( fn.indexOf('(') < 0 ) fn = fn + '()';
		var rc = _browse.execute( fn );
		if( rc != null ){
			return rc;
		}
	}
}


// 动态下载子节点的函数
function xtree_loadFloder( menuNode )
{
	// 生成请求数据
	var page = new pageDefine( "/tag.service?txn-code=load-floder", "添加索引信息" );
	page.addValue( menuNode.id, "parent-id" );
	
	// 查找节点
	var nd = this.lookupNode( menuNode.id );
	if( nd != null && nd.memo != null && nd.memo != '' ){
		page.addValue( nd.memo, "node-memo" );
	}
	
	page.addValue( this.txnCode, "txn-loader" );
	page.addValue( this.nodename, "nodename" );
	page.addValue( this.pidname, "pidname" );
	page.addValue( this.idname, "idname" );
	page.addValue( this.textname, "textname" );
	page.addValue( this.memoname, "memoname" );
	page.addValue( this.flodername, "flodername" );
	page.addValue( this.iconname, "iconname" );
	page.addValue( this.openname, "openname" );
	
	// 提交请求
	page.pageType = "ajax";
	page.callAjaxService( this.name + '.insertFloder' );
}

// 下载子节点后的处理
function xtree_insertFloder( errCode, errDesc, xmlResults )
{
	if( errCode != '000000' ){
		return;
	}
	
	// 父节点
	var parentNode = null;
	var parentMenu = null;
	
	var nodeList = ["parent-id", "id", "text", "icon", "open-icon", "memo", "floder"];
	var nodes = _getXmlRecordset( xmlResults, "menu-item", nodeList );
	for( var ii=0; ii<nodes.length; ii++ ){
		var d = nodes[ii];
		var nd = new XmenuNode( d[0], d[1], d[2], d[3], d[4], d[5], d[6] );
		this.nodes.push( nd );
		
		if( parentNode == null ){
			parentNode = this.lookupNode( d[0] );
			parentNode.subNodes = new Array();
			parentMenu = this.menu.lookupItemByNumber( parentNode.menuId );
		}
		
		// 创建节点 ==> text, url, target, tooltip, icon, openIcon, id, floder(目录标志)
		var obj = new MTMenuItem(nd.text, null, null, nd.text, nd.icon, nd.openicon, nd.id, nd.floder);
		nd.menuId = obj.number;
		
		// 添加节点
		parentNode.subNodes.push( nd );
		parentMenu.addSubItem( obj );
	}
	
	if( parentMenu != null ){
		parentMenu.expand( true );
	}
}


// 显示节点的内容，用于显示或修改
function xtree_viewNode( xurl, caption, showtype, width, height )
{
	var id = this.selectedNode.id;
	if( id.indexOf('@temp:') == 0 ){
		return;
	}
	
	if( showtype == null ){
		showtype = 'inner';
	}
	
	if( caption == null || caption == '' ){
		caption = this.selectedNode.text;
	}
	
	if( width == null ){
		width = 500;
	}
	
	if( height == null ){
		height = 300;
	}
	
	var page = new pageDefine( xurl, caption, showtype, width, height );
	page.srcElement = this.srcElement.firstChild;
	page.addRecord( "select-key:" + this.idname + "=" + id );
}

// 在新窗口中显示目录
function xtree_openView( xurl, caption, showtype, iType, width, height )
{
	if( xurl == null ){
		xurl = "/script/xtree-move-node.html?stamp=1";
	}
	
	if( caption == null ){
		caption = "移动节点到指定的位置";
	}
	
	if( showtype != "inner" ){
		showtype = "frame";
	}
	
	if( width == null ){
		width = "300px";
	}
	
	if( height == null ){
		height = "350px";
	}
	
	// 打开窗口
	var page = new pageDefine( xurl, caption, showtype, width, height );
	if( window.event != null ){
		page.srcElement = window.event.srcElement;
	}
	
	// 传递参数
	page.addArgs( this );
	page.addArgs( iType );
	
	page.goPage( )
}


// 移动节点
function xtree_moveNode( iType )
{
	if( this.menuNode == null ){
		return;
	}
	
	var srcId = this.selectedNode.id;
	var destId = this.menuNode.id;
	if( srcId == null || srcId == '' ){
		return;
	}
	
	if( destId == null || destId == '' ){
		return;
	}
	
	if( srcId == destId ){
		alert( '目标节点和原节点相同' );
		return;
	}
	
	// 判断是否把节点移动到子孙节点上
	if( this.selectedNode.isChild(this.menuNode) ){
		alert( '上级节点不能移动到下级节点上' );
		return;
	}
	
	var func = this.onMoveNode;
	if( func.indexOf('(') < 0 ){
		func = func + '(iType)';
	}
	
	eval( func );
}

// 打开移动节点的窗口
function xtree_openMoveNodeWindow( onclick, iType, width, height )
{
	if( onclick == null || onclick == '' ){
		alert( '没有设置移动节点的函数' );
		return;
	}
	
	if( this.selectedNode == null ){
		alert( '没有选择需要移动的节点' );
		return;
	}
	
	if( iType != 'sub' && iType != 'pre' ){
		iType = 'post';
	}
	
	this.onMoveNode = onclick;
	
	// 打开窗口
	this.openView( "/script/xtree-move-node.html", "移动节点", "frame", iType, width, height );
}



// 增加节点
function xtree_addNode( parentid, id, text, icon, openicon )
{
	if( icon == null ){
		icon = "";
	}
	
	if( openicon == null ){
		openicon = "";
	}
	
	var node = new XmenuNode( parentid, id, text, icon, openicon );
	if( this.nodes == null ){
		this.nodes = new Array();
	}
	
	this.nodes[this.nodes.length] = node;
}

function xtree_insertAfterNode( nodeid, id, text, icon, openicon )
{
	
}

function xtree_insertBeforeNode( nodeid, id, text, icon, openicon )
{
	
}

function xtree_updateNode( id, text, icon, openicon )
{
	
}

function xtree_deleteNode( id, text, icon, openicon )
{
	
}



/* ************************* 弹出菜单 ************************* */


// 菜单的处理函数
function xmenu_popupMenuClick(name)
{
	if( this.onPopupMenuClick != null ){
		this.menuNode = null;
		for( var ii=0; ii<this.nodes.length; ii++ ){
			if( this.nodes[ii].id == name ){
				this.menuNode = this.nodes[ii];
				break;
			}
		}
		
		eval( this.onPopupMenuClick );
	}
}


// 创建菜单
function xmenu_createPopupMenu( minWidth )
{
	xPopupMenu = new makeCoolMenu("xPopupMenu") //Making the menu object. Argument: menuname
	initStrutsMenuInfo( xPopupMenu );
	
	// 设置宽度
	if( minWidth == null || minWidth < 150 ){
		minWidth = 150;
	}
	
	for( var ii=1; ii<xPopupMenu.level.length; ii++ ){
		xPopupMenu.level[ii].width = minWidth;
	}
	
	// 菜单的top座标
	xPopupMenu.fromtop = 0;
	
	// 菜单的left座标
	xPopupMenu.fromleft = 0;
	
	// 菜单的width
	xPopupMenu.barwidth = 100;
	
	// 菜单背景色
	cmBGColorOff = 'F0F0F0';
	
	// 活动菜单的背景色
	cmBGColorOn='0000FF'
	
	// 菜单的文字颜色
	cmTxtColor='000000'
	
	// 活动时的字体颜色
	cmHoverColor='FFFFFF'
	
	// 处理函数
	var onclick = this.name + '.popupMenuClick(name)';
	
	// 排列方式
	xPopupMenu.setMenuType( 'popup' );
	xPopupMenu.makeMenu('xtree','',this.root,'','','','','','','');
	
	// 生成节点
	var	nodes = this.getHiberarchy();
	if( nodes != null && nodes.length != 0 ){
		xmenu_createMenu( nodes, 'xtree', onclick );
	}
	
	xPopupMenu.makeStyle();
	xPopupMenu.construct();
}


// 创建菜单节点
function xmenu_createMenu( nodes, parentNode, onclick )
{
	var	nd;
	var	id;
	
	// 生成列表
	for( var ii=0; ii<nodes.length; ii++ ){
		nd = nodes[ii];
		id = nd.id;
		if( id.indexOf('@temp:') == 0 ){
			continue;
		}
		
		xPopupMenu.makeMenu(id, parentNode, nd.text,'','','','','','',onclick,'','');
		
		if( nd.subNodes != null && nd.subNodes.length != 0 ){
			xmenu_createMenu( nd.subNodes, id, onclick );
		}
	}
}

function xtree_popupMenu( onclick, rootNode )
{
	if( rootNode == null || rootNode == '' ){
		rootNode = 'xtree';
	}
	
	if( onclick.indexOf('(') < 0 ){
		onclick = onclick + '()';
	}
	
	this.onPopupMenuClick = onclick;
	xPopupMenu.popupMenu( rootNode );
}


// 搜索
function searchDefine( objName, property, width, height, bgcolor, txnCode, onclick,
		queryString, pageRow, currentRow, totalRow, scopeKey, scopeList )
{
	// 输入
	this.objName = objName;
	this.property = property;
	
	if( width == null || width == '' ){
		width = '100%';
	}
	
	this.width = width;
	
	if( height == null || height == '' ){
		height = '100%';
	}
	
	this.height = height;
	
	this.bgcolor = bgcolor;
	this.txnCode = txnCode;
	this.pageRow = pageRow;
	this.currentRow = currentRow;
	this.totalRow = totalRow;
	this.queryString = queryString;
	
	// 检索范围
	this.scopeKey = scopeKey;
	this.scopeList = scopeList;
	
	// 显示结果的函数
	if( onclick.indexOf('(') < 0 ){
		onclick = onclick + '(' + objName + ')';
	}
	
	this.onclick = onclick;
	
	// 当前选中记录的id 和 行信息
	this.id = null;
	this.srcElement = null;
	
	// 查询路径
	if( txnCode.indexOf('.jsp') > 0 ){
		if( txnCode.indexOf('/') == 0 ){
			this.reqPath = txnCode;
		}
		else{
			this.reqPath = menuPath + txnCode;
		}
	}
	else if( txnCode.indexOf('.do') > 0 ){
		if( txnCode.indexOf('/') == 0 ){
			this.reqPath = txnCode;
		}
		else{
			this.reqPath = '/' + txnCode;
		}
	}
	else{
		this.reqPath = '/txn' + txnCode + '.do';
	}
	
	// 当前页、查询页
	if( currentRow > 0 && pageRow == 1 ){
		this.startPage = currentRow;
	}
	else if( currentRow > 0 && pageRow > 0 ){
		this.startPage = Math.floor( currentRow / pageRow ) + 1;
	}
	else{
		this.startPage = 0;
	}
	
	// 总页数
	if( pageRow > 0 ){
		this.totalPage = Math.floor( (totalRow + pageRow - 1) / pageRow );
	}
	else{
		this.totalPage = 0;
	}
	
	// 查询函数
	this.prepareSearchInputStart = search_prepareSearchInputStart;
	this.prepareSearchInputStop = search_prepareSearchInputStop;
	this.prepareSearchStart = search_prepareSearchStart;
	this.prepareSearchStop = search_prepareSearchStop;
	this.prepareNavigateBar = search_prepareNavigateBar
	this.goPage = search_goPage;
	this.onkeyup = search_onkeyup;
	this.query = search_query;
	this.showResult = search_showResult;		// 显示结果
	this.getScopeValue = search_getScopeValue;	// 取检索范围的内容
}

// 生成查询输入条件
function search_prepareSearchInputStart()
{
	var str = '<table border="0" cellpadding="0" cellspacing="0"';
	
	// bgcolor
	if( this.bgcolor != null && this.bgcolor != '' ){
		str = str + ' bgcolor="' + this.bgcolor + '"';
	}
	
	// width
	if( this.width != null && this.width != '' ){
		str = str + ' width="' + this.width + '"';
	}
	
	// height
	if( this.height != null && this.height != '' ){
		str = str + ' height="' + this.height + '"';
	}
	
	str = str + 'align="center" style="table-layout:fixed;">\n';
	
	str = str + '<tr height="5px"><td></td></tr>\n';
	str = str + '<tr height="26" valign="top"><td width="100%">&nbsp;\n';
	
	if( this.scopeList != null && this.scopeList.length > 0 ){
		str = str + '检索范围：<select size="1" style="width: 216;"';
		str = str + ' name="' + this.property + ':scope"';
		str = str + ' id="' + this.property + ':scope">\n';
		str = str + '<option value="">所有信息</option>\n';
		
		// 选择列表
		var scopeInfo;
		var selectedFlag = '';
		for( var ii=0; ii<this.scopeList.length; ii++ ){
			scopeInfo = this.scopeList[ii];
			
			if( this.scopeKey == scopeInfo[0] ){
				selectedFlag = ' selected';
			}
			else{
				selectedFlag = '';
			}
			
			str = str + '<option value="' + scopeInfo[0] + '"' + selectedFlag + '>' + scopeInfo[1] + '</option>\n';
		}
		
		str = str + '</select>\n&nbsp;&nbsp;';
	}
	
	str = str + '检索条件：<input type="text" size="30"';
	str = str + ' onkeyup="' + this.objName + '.onkeyup()"';
	str = str + ' name="' + this.property + ':query-string"';
	str = str + ' id="' + this.property + ':query-string"';
	str = str + ' value="' + this.queryString + '">';
	
	str = str + '<input type="button" name="query" value=" 搜索 " class="menu"';
	str = str + ' onclick="' + this.objName + '.query()"/>\n';
	str = str + '</td></tr>\n';
	
	document.write( str );
}


// 生成查询输入条件的结束部分
function search_prepareSearchInputStop()
{
	document.write( '</td></tr></table>\n' );
}


// 生成检索的开始部分
function search_prepareSearchStart( )
{
	// 生成查询输入条件
	this.prepareSearchInputStart();
	
	// 生成结果的开始部分
	var str = '<tr><td height="1" bgcolor="3366FF"></td></tr>\n';
	str = str + '<tr valign="top"><td width="100%">\n';
	str = str + "<table border='0' cellpadding='0' cellspacing='0' width='100%' align='center' valign='top' style='table-layout:fixed;'>\n";
	document.write( str );
}

function search_prepareSearchStop( )
{
	document.write( '</table>\n' );
	
	this.prepareSearchInputStop();
}


// 生成页面的导航条
function search_prepareNavigateBar( )
{
	// 换行
	var	result = '<tr><td>\n';
	
	// 左边记录数量信息
	result = result + '<table width="100%" style="border-top-style:solid; border-top-width:1px"';
	result = result + ' bordercolor="#7f9db9">\n<tr>\n<td valign=center>\n';
	
	// 当前页
	result = result + '当前第【<span style="color:red" id="search_curPage"' +
		' name="search_curPage">' + this.startPage + '</span>】页\n';
	
	// 总共页
	result = result + '/共【<span style="color:red" id="search_totalPage"' +
		' name="search_totalPage">' + this.totalPage + '</span>】页\n';
	
	// 共记录数量
	result = result + ' 记录总数【<span style="color:red" id="search_totalRow"' +
		' name="search_curRow">' + this.totalRow + '</span>】条\n';
	
	// 选择页
	if( this.totalPage <= 10 ){
		result = result + ' 转到 <select style="color:red" id="' + this.property + ':start-page"' + 
			' name="' + this.property + ':start-page" onchange="' + this.objName + ".goPage()" + '">\n';
	 	
	 	var	ii;
	 	for( ii=1; ii<=this.totalPage; ii++ ){
	 		if( ii == this.startPage ){
	  			result = result + '<option value="' + ii +'" selected>' + ii + '</option>\n';
	  		}
	  		else{
	  			result = result + '<option value="' + ii +'">' + ii + '</option>\n';
	  		}
	  	}
	 	
		result = result + '</select> 页\n';
	}
	else{
		result = result + ' 转到 <input type="text" style="color:red" id="' + this.property + ':start-page"' + 
			' name="' + this.property + ':start-page" value="' + this.startPage + '" onkeydown="if(event.keyCode == 13){' +
			this.objName + ".goPage()" + '}" size="3" style="border: 1px solid #7f9db9"> 页\n';
	}
	
	// 每页的记录数量
	result = result + ' 每页显示：' + 
		'<input type="text" style="color:red" id="' + this.property + ':page-row"' +
		' name="' + this.property + ':page-row" value="' + this.pageRow + '"' +
		' onkeydown="if(event.keyCode == 13){' + this.objName + ".goPage()" + '}" size="3" style="border: 1px solid #7f9db9"> 条信息\n';
	
	// 结束
	result = result + '</td>\n';
	
	// 按钮第一页
	result = result + '<td align="right">\n' + 
		'<button style="WIDTH:25px" onclick="' + this.objName + ".goPage('1')" +
		'" class="menu" title="第一页"';
	if( this.startPage <= 1 ){
	 	result = result + ' disabled';
	}
	
	result = result + '>|&lt;</button>&nbsp;\n';
	
	// 上一页
	result = result + '<button style="WIDTH:25px" onclick="' + this.objName + ".goPage('" + (this.startPage-1) + "')" +
		'" class="menu" title="上一页"';
	if( this.startPage <= 1 ){
	 	result = result + ' disabled';
	}
	
	result = result + '>&lt;</button>&nbsp;\n';
	
	// 下一页
	result = result + '<button style="WIDTH:25px" onclick="' + this.objName + ".goPage('" + (this.startPage+1) + "')" +
		'" class="menu" title="下一页"';
	if( this.startPage >= this.totalPage ){
	 	result = result + ' disabled';
	}
	
	result = result + '>&gt;</button>&nbsp;\n';
	
	// 最后一页
	result = result + '<button style="WIDTH:25px" onclick="' + this.objName + ".goPage('" + this.totalPage + "')" +
		'" class="menu" title="最后一页"';
	if( this.startPage >= this.totalPage ){
	 	result = result + ' disabled';
	}
	
	result = result + '>&gt;|</button>\n';
	
	// 导航条结束
	result = result + '</td></tr></table>\n' + '</td></tr>\n';
	
	document.write( result );
}

// 导航页面
function search_goPage( flag )
{
	if( flag == null ){
		flag = 'go';
	}
	
	// 每页记录
	var pageRow=parseInt(document.getElementById(this.property + ':page-row').value);
	if( pageRow > 100 ){
		pageRow = 100;
	}
	else if( pageRow < 1 ){
		pageRow = 10;
	}
	
	// 导航到的页号
	if( flag == 'go' ){
		var startPage = parseInt(document.getElementById(this.property + ':start-page').value);
	}
	else{
		var startPage = parseInt(flag);
	}
	
	// 执行查询
	var	page = new pageDefine( this.reqPath, '检索信息', 'window' );
	page.addValue( this.queryString, this.property + ':query-string' );
	page.addValue( startPage, this.property + ':start-page' );
	page.addValue( pageRow, this.property + ':page-row' );
	
	// 查询范围
	var val = this.getScopeValue();
	if( val != null && val.length != 0 ){
		page.addValue( val, this.property + ':scope-key1' );
	}
	
	page.goPage();
}


// 取检索范围的内容
function search_getScopeValue()
{
	var obj = document.getElementById( this.property + ':scope' );
	if( obj == null ){
		return "";
	}
	
	for (var i=0; i<obj.options.length; i++) {
		if( obj.options[i].selected == true ){
			break;
		}
	}
	
	if( i == 0 || i >= obj.options.length ){
		return "";
	}
	
	return obj.options[i].value;
}


// 显示结果
function search_showResult( id )
{
	this.id = id;
	this.srcElement = window.event.srcElement;
	if( this.onclick != null ){
		eval( this.onclick );
	}
}

function search_onkeyup()
{
	if( window.event == null ){
		return;
	}
	
	if( window.event.keyCode == 13 ){
		this.query();
	}
}

function search_query()
{
	var startPage = 1;
	var pageRow = this.pageRow;
	if( pageRow == null || pageRow < 1 ){
		pageRow = 10;
	}
	
	var queryString = document.getElementById(this.property + ':query-string').value;
	if( queryString == null || queryString == '' ){
		return;
	}
	
	// 执行查询
	var	page = new pageDefine( this.reqPath, '检索信息', 'window' );
	page.addValue( queryString, this.property + ':query-string' );
	page.addValue( startPage, this.property + ':start-page' );
	page.addValue( pageRow, this.property + ':page-row' );
	
	// 查询范围
	var val = this.getScopeValue();
	if( val != null && val.length != 0 ){
		page.addValue( val, this.property + ':scope-key1' );
	}
	
	page.goPage();
}




-->
