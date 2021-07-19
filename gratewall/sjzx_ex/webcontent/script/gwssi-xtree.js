<!--


document.write('<script language="JavaScript" src="' + rootPath + '/script/mtmcode.js"></script>\n' );

// tree ��Ӧ�Ĳ˵�
var xPopupMenu = null;

// ������
// name=�������ƣ�������ɵ��������ƣ������������ƺ�table������
// root=���ڵ�ı���
// aspect=��ʾ��ʽ; menu=�����˵���ʽ��list=����ʽ��check=��checkbox��tree=��
// visible=�Ƿ�ֱ������Ŀ¼��true=ֱ�����ɲ˵���false=�ֹ�����
// idname=�ؼ����ֶε�����
// rootpage=���ڵ㵼����ҳ������
// bgcolor=����ɫ
// textcolor=������ɫ
// flodercloseicon=Ŀ¼��ͼ��
// floderopenicon=Ŀ¼��ʱ��ͼ��
// nodeicon=�ڵ��ͼ��
// selectedNode=��Ŀ¼��ѡ�еĽڵ�
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
	
	// ����id��pid�ȵ�����
	this.txnCode = txnCode;
	this.nodename = nodename;
	this.pidname = pidname;
	this.idname = idname;
	this.textname = textname;
	this.memoname = memoname;
	this.iconname = iconname;
	this.openname = openname;
	
	// ���ڱ���ýڵ��Ƿ���Ŀ¼�ڵ��־de���ƣ�true=��Ŀ¼��flase=����Ŀ¼����������ǿգ�һ�μ���Ŀ¼�������������
	this.flodername = flodername;
	
	// ������ͼ��
	this.bgcolor = bgcolor;
	this.textcolor = textcolor;
	this.flodercloseicon = flodercloseicon;
	this.floderopenicon = floderopenicon;
	this.nodeicon = nodeicon;
	
	// ����
	this.onclickFunc = onclickFunc;
	this.ondblclickFunc = ondblclickFunc;
	this.onmouseoverFunc = onmouseoverFunc;
	this.onmouseoutFunc = onmouseoutFunc;
	this.onmousemoveFunc = onmousemoveFunc;
	this.onmousedownFunc = onmousedownFunc;
	this.onmouseupFunc = onmouseupFunc;
	this.onfocusFunc = onfocusFunc;
	
	// �ڵ��б�
	this.nodes = nodes;
	
	// ��Ӧ��Ŀ¼
	this.menu = null;
	
	// ��ǰѡ��Ľڵ�
	this.selectedNode = null;			// ��Ŀ¼��ѡ�еĽڵ㣬��MTMenuItem
	this.srcElement = null;				// ѡ�еĽڵ��Ӧ��<TD>
	this.eventName = null;				// �¼�����
	this.setSelectedNode = xmenu_setSelectedNode;	// ���õ�ǰѡ�еĽڵ�
	
	// ��ǰѡ�еĽڵ�:�ȱ���ڵ��ID
	if( selectedNode == '' || selectedNode == null ){
		this.selectedNode = null;
	}
	else if(typeof(selectedNode) != "object"){
		this.selectedNode = selectedNode;
	}
	else{
		this.selectedNode = selectedNode.id;
	}
	
	// checkģʽ�µ�,ȡѡ�нڵ�����ݺ�����ѡ�нڵ�
	this.getCheckValue = xmenu_getCheckValue;
	this.setCheckValue = xmenu_setCheckValue;
	
	// ����
	this.create = xmenu_create;
	this.getHiberarchy = xmenu_getHiberarchy;
	this.lookupNode = xtree_lookupNode;	// ���ݱ�Ų�ѯ�ڵ�
	this.addNode = xtree_addNode;		// ���ӽڵ�
	this.viewNode = xtree_viewNode;
	this.openView = xtree_openView;
	this.moveNode = xtree_moveNode;
	this.openMoveNodeWindow = xtree_openMoveNodeWindow;	//���ƶ��ڵ�Ĵ���
	
	// ����¼�
	this.onMouseEvent = xtree_onMouseEvent;
	
	// ��̬�����ӽڵ�ĺ���
	this.loadFloder = xtree_loadFloder;
	this.insertFloder = xtree_insertFloder;
	
	// �ƶ��ڵ�Ķ���
	this.onMoveNode = null;
	
	// ����
	this.insertAfterNode = xtree_insertAfterNode;
	this.insertBeforeNode = xtree_insertBeforeNode;
	this.updateNode = xtree_updateNode;
	this.deleteNode = xtree_deleteNode;
	
	// �˵�
	this.menuNode = null;	// �ڲ˵���ѡ�еĽڵ�,����˵�����ʱ����
	this.onPopupMenuClick = null;	// ����˵�ʱִ�еĶ��������Ʋ˵��е�onclickFunc����
	this.popupMenuClick = xmenu_popupMenuClick;	// ����˵�ʱִ�еĶ���
	this.createPopupMenu = xmenu_createPopupMenu; // ���������˵��ĺ���
	this.popupMenu = xtree_popupMenu;	// �򿪵����˵��ĺ���
}


// �ڵ㶨��
function XmenuNode( parentid, id, text, icon, openicon, memo, floder )
{
	this.parentid = parentid;
	this.id = id;
	this.text = text;
	this.icon = icon;
	this.openicon = openicon;
	this.memo = memo;
	
	// �Ƿ�Ŀ¼��true=�ǡ�false=�� (flodername!='' ʱ��Ч)
	if( floder == true || floder == 'true' ){
		this.floder = true;
	}
	else{
		this.floder = false;
	}
	
	// �˵��е�ID
	this.menuId = null;
	
	// �ӽڵ�
	this.subNodes = null;
}


// �Բ˵������򣬺ͼ���
function _getSortNodes( nodes )
{
	var sortNode = new Array(nodes.length);
	for( var ii=0; ii<nodes.length; ii++ ){
		var node = new Array();
		node[0] = nodes[ii].id;
		node[1] = ii;
		sortNode[ii] = node;
	}
	
	// ����
	return sortNode.sort();
}

// ���ɲ�νڵ�
function xmenu_getHiberarchy()
{
	var	h_nodes = new Array();
	
	if( this.nodes == null || this.nodes.length < 1 ){
		return;
	}
	
	// ���������б�
	var sortNodes = _getSortNodes( this.nodes );
	
	// ���ɲ����
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
				//alert( 'û���ҵ��ڵ�[' + node.id + ']�ĸ��ڵ�[' + node.parentid + ']' );
			}
		}
	}
	
	return h_nodes;
}


// ȡѡ�еĽڵ��б�
function xmenu_getCheckValue()
{
	return this.menu.getCheckValue();
}

function xmenu_setCheckValue( value )
{
	return this.menu.setCheckValue( value );
}


// ������
function xmenu_create( width, height )
{
	var	nodes = this.getHiberarchy();
	if( nodes == null || nodes.length == 0 ){
		return;
	}
	
	// ��ʽ��������ɫ
	if( this.textcolor == null || this.textcolor == '' ){
		this.textcolor = '#333';
	}
	
	// ѡ�нڵ�
	var sid = '';
	if( this.selectedNode != null ){
		sid = this.selectedNode;
	}
	
	// �˵�����
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
	
	// ����ȫ�ֱ���
	var menuName = this.name.replace( '-', '_' );
	var varName = 'xmenu_' + menuName;
	
	// ������ȫ�ֱ���
	document.write( '<script language="javascript">\n' );
	document.write( 'var ' + varName + ' = new MTMenu("' + varName + '", "' + xtype + '");\n' );
	document.write( menuName + '.menu = ' + varName + ';\n' );
	document.write( '</script>\n' );
	
	document.write( "<input type='hidden' name='" + this.name + ":selected-node' id='" + this.name + ":selected-node' value='" + sid + "'>" );
	
	// ����DIV
	var xtreeDefine = this;
	this.frame = document.getElementById( 'xtree_' + this.name );
	if( this.frame == null ){
		document.write( "<div id=xtree_" + this.name + " style='z-index:1000; width:" + width + "; height:" + height + "' class='scroll-div'>\n" );
		document.write( "<TABLE margin='0' cellSpacing='0' cellPadding='0' width='100%' border='0'><tr height='1px'><td colspan='2'></td></tr><tr valign='top'><td width='5px'></td><td width='99%'>\n" );
		document.write( "<TABLE id='xmenu_" + this.name + "' width='100%'></table></td></tr></table></div>\n" );
		
		// ����ɫ
		this.frame = document.getElementById( 'xtree_' + this.name );
		this.frame.style.backgroundColor = this.bgcolor;
	}
	
	var menuTable = document.getElementById( 'xmenu_' + this.name );
	menuTable.style.color = this.textcolor;
	menuTable = null;
	
	// �����ӽڵ�ĺ���
	if( this.flodername != '' ){
		this.menu.loadFloderFunc = function(menuNode){ return xtreeDefine.loadFloder(menuNode); };
	}
	
	// �¼�������
	this.menu.onMouseEventFunction = function( eventName, node, srcElement ){ return xtreeDefine.onMouseEvent(eventName, node, srcElement); };
	
	// ����ȱʡֵ
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
	
	// ��ҳ
	if( this.rootpage != '' ){
		this.menu.MTHomePage = this.rootpage;
	}
	
	// �����˵�����
	xmenu_createTree( this.menu, nodes );
	
	if( this.visible ){
		this.menu.startMenu(true);
		
		// ���û�ڵ�
		if( this.selectedNode != null ){
			this.menu.fireEvent( this.selectedNode );
		}
	}
}


function xmenu_createTree( menu, nodes, parentNode )
{
	var stack = new Array();
	
	// ��ʼ��:�ӽڵ㣬�ϼ��˵��ڵ�
	stack.push( [nodes, null] );
	
	// �����б�
	var menuItem = null;
	while( stack.length > 0 ){
		var n = stack.pop();
		var ns = n[0];
		var pnd = n[1];
		for( var ii=0; ii<ns.length; ii++ ){
			var nd = ns[ii];
			
			// �����ڵ� ==> text, url, target, tooltip, icon, openIcon, id, floder(Ŀ¼��־)
			var obj = new MTMenuItem(nd.text, null, null, nd.text, nd.icon, nd.openicon, nd.id, nd.floder);
			nd.menuId = obj.number;
			menuItem = (pnd == null) ? menu.addSubItem(obj) : pnd.addSubItem(obj);
			
			// �ж��Ƿ����ӽڵ�:�ӽڵ㣬�ϼ��˵��ڵ�
			if( nd.subNodes != null ){
				stack.push( [nd.subNodes, menuItem] );
			}
		}
	}
}


// ���ҽڵ�
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
		
		// ���Ҳ˵��ڵ�
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

// ���õ�ǰѡ�еĽڵ�
function xmenu_setSelectedNode( node )
{
	// ���û�ڵ�
	if( node != null && node != '' ){
		this.menu.fireEvent( node );
	}
}


// ����¼�
function xtree_onMouseEvent( eventName, node, srcElement )
{
	var	fn = null;
	this.srcElement = srcElement;
	this.eventName = eventName;
	
	// ����������
	var obj = document.getElementById(this.name + ':selected-node');
	
	// �¼�
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


// ��̬�����ӽڵ�ĺ���
function xtree_loadFloder( menuNode )
{
	// ������������
	var page = new pageDefine( "/tag.service?txn-code=load-floder", "���������Ϣ" );
	page.addValue( menuNode.id, "parent-id" );
	
	// ���ҽڵ�
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
	
	// �ύ����
	page.pageType = "ajax";
	page.callAjaxService( this.name + '.insertFloder' );
}

// �����ӽڵ��Ĵ���
function xtree_insertFloder( errCode, errDesc, xmlResults )
{
	if( errCode != '000000' ){
		return;
	}
	
	// ���ڵ�
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
		
		// �����ڵ� ==> text, url, target, tooltip, icon, openIcon, id, floder(Ŀ¼��־)
		var obj = new MTMenuItem(nd.text, null, null, nd.text, nd.icon, nd.openicon, nd.id, nd.floder);
		nd.menuId = obj.number;
		
		// ��ӽڵ�
		parentNode.subNodes.push( nd );
		parentMenu.addSubItem( obj );
	}
	
	if( parentMenu != null ){
		parentMenu.expand( true );
	}
}


// ��ʾ�ڵ�����ݣ�������ʾ���޸�
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

// ���´�������ʾĿ¼
function xtree_openView( xurl, caption, showtype, iType, width, height )
{
	if( xurl == null ){
		xurl = "/script/xtree-move-node.html?stamp=1";
	}
	
	if( caption == null ){
		caption = "�ƶ��ڵ㵽ָ����λ��";
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
	
	// �򿪴���
	var page = new pageDefine( xurl, caption, showtype, width, height );
	if( window.event != null ){
		page.srcElement = window.event.srcElement;
	}
	
	// ���ݲ���
	page.addArgs( this );
	page.addArgs( iType );
	
	page.goPage( )
}


// �ƶ��ڵ�
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
		alert( 'Ŀ��ڵ��ԭ�ڵ���ͬ' );
		return;
	}
	
	// �ж��Ƿ�ѽڵ��ƶ�������ڵ���
	if( this.selectedNode.isChild(this.menuNode) ){
		alert( '�ϼ��ڵ㲻���ƶ����¼��ڵ���' );
		return;
	}
	
	var func = this.onMoveNode;
	if( func.indexOf('(') < 0 ){
		func = func + '(iType)';
	}
	
	eval( func );
}

// ���ƶ��ڵ�Ĵ���
function xtree_openMoveNodeWindow( onclick, iType, width, height )
{
	if( onclick == null || onclick == '' ){
		alert( 'û�������ƶ��ڵ�ĺ���' );
		return;
	}
	
	if( this.selectedNode == null ){
		alert( 'û��ѡ����Ҫ�ƶ��Ľڵ�' );
		return;
	}
	
	if( iType != 'sub' && iType != 'pre' ){
		iType = 'post';
	}
	
	this.onMoveNode = onclick;
	
	// �򿪴���
	this.openView( "/script/xtree-move-node.html", "�ƶ��ڵ�", "frame", iType, width, height );
}



// ���ӽڵ�
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



/* ************************* �����˵� ************************* */


// �˵��Ĵ�����
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


// �����˵�
function xmenu_createPopupMenu( minWidth )
{
	xPopupMenu = new makeCoolMenu("xPopupMenu") //Making the menu object. Argument: menuname
	initStrutsMenuInfo( xPopupMenu );
	
	// ���ÿ��
	if( minWidth == null || minWidth < 150 ){
		minWidth = 150;
	}
	
	for( var ii=1; ii<xPopupMenu.level.length; ii++ ){
		xPopupMenu.level[ii].width = minWidth;
	}
	
	// �˵���top����
	xPopupMenu.fromtop = 0;
	
	// �˵���left����
	xPopupMenu.fromleft = 0;
	
	// �˵���width
	xPopupMenu.barwidth = 100;
	
	// �˵�����ɫ
	cmBGColorOff = 'F0F0F0';
	
	// ��˵��ı���ɫ
	cmBGColorOn='0000FF'
	
	// �˵���������ɫ
	cmTxtColor='000000'
	
	// �ʱ��������ɫ
	cmHoverColor='FFFFFF'
	
	// ������
	var onclick = this.name + '.popupMenuClick(name)';
	
	// ���з�ʽ
	xPopupMenu.setMenuType( 'popup' );
	xPopupMenu.makeMenu('xtree','',this.root,'','','','','','','');
	
	// ���ɽڵ�
	var	nodes = this.getHiberarchy();
	if( nodes != null && nodes.length != 0 ){
		xmenu_createMenu( nodes, 'xtree', onclick );
	}
	
	xPopupMenu.makeStyle();
	xPopupMenu.construct();
}


// �����˵��ڵ�
function xmenu_createMenu( nodes, parentNode, onclick )
{
	var	nd;
	var	id;
	
	// �����б�
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


// ����
function searchDefine( objName, property, width, height, bgcolor, txnCode, onclick,
		queryString, pageRow, currentRow, totalRow, scopeKey, scopeList )
{
	// ����
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
	
	// ������Χ
	this.scopeKey = scopeKey;
	this.scopeList = scopeList;
	
	// ��ʾ����ĺ���
	if( onclick.indexOf('(') < 0 ){
		onclick = onclick + '(' + objName + ')';
	}
	
	this.onclick = onclick;
	
	// ��ǰѡ�м�¼��id �� ����Ϣ
	this.id = null;
	this.srcElement = null;
	
	// ��ѯ·��
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
	
	// ��ǰҳ����ѯҳ
	if( currentRow > 0 && pageRow == 1 ){
		this.startPage = currentRow;
	}
	else if( currentRow > 0 && pageRow > 0 ){
		this.startPage = Math.floor( currentRow / pageRow ) + 1;
	}
	else{
		this.startPage = 0;
	}
	
	// ��ҳ��
	if( pageRow > 0 ){
		this.totalPage = Math.floor( (totalRow + pageRow - 1) / pageRow );
	}
	else{
		this.totalPage = 0;
	}
	
	// ��ѯ����
	this.prepareSearchInputStart = search_prepareSearchInputStart;
	this.prepareSearchInputStop = search_prepareSearchInputStop;
	this.prepareSearchStart = search_prepareSearchStart;
	this.prepareSearchStop = search_prepareSearchStop;
	this.prepareNavigateBar = search_prepareNavigateBar
	this.goPage = search_goPage;
	this.onkeyup = search_onkeyup;
	this.query = search_query;
	this.showResult = search_showResult;		// ��ʾ���
	this.getScopeValue = search_getScopeValue;	// ȡ������Χ������
}

// ���ɲ�ѯ��������
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
		str = str + '������Χ��<select size="1" style="width: 216;"';
		str = str + ' name="' + this.property + ':scope"';
		str = str + ' id="' + this.property + ':scope">\n';
		str = str + '<option value="">������Ϣ</option>\n';
		
		// ѡ���б�
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
	
	str = str + '����������<input type="text" size="30"';
	str = str + ' onkeyup="' + this.objName + '.onkeyup()"';
	str = str + ' name="' + this.property + ':query-string"';
	str = str + ' id="' + this.property + ':query-string"';
	str = str + ' value="' + this.queryString + '">';
	
	str = str + '<input type="button" name="query" value=" ���� " class="menu"';
	str = str + ' onclick="' + this.objName + '.query()"/>\n';
	str = str + '</td></tr>\n';
	
	document.write( str );
}


// ���ɲ�ѯ���������Ľ�������
function search_prepareSearchInputStop()
{
	document.write( '</td></tr></table>\n' );
}


// ���ɼ����Ŀ�ʼ����
function search_prepareSearchStart( )
{
	// ���ɲ�ѯ��������
	this.prepareSearchInputStart();
	
	// ���ɽ���Ŀ�ʼ����
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


// ����ҳ��ĵ�����
function search_prepareNavigateBar( )
{
	// ����
	var	result = '<tr><td>\n';
	
	// ��߼�¼������Ϣ
	result = result + '<table width="100%" style="border-top-style:solid; border-top-width:1px"';
	result = result + ' bordercolor="#7f9db9">\n<tr>\n<td valign=center>\n';
	
	// ��ǰҳ
	result = result + '��ǰ�ڡ�<span style="color:red" id="search_curPage"' +
		' name="search_curPage">' + this.startPage + '</span>��ҳ\n';
	
	// �ܹ�ҳ
	result = result + '/����<span style="color:red" id="search_totalPage"' +
		' name="search_totalPage">' + this.totalPage + '</span>��ҳ\n';
	
	// ����¼����
	result = result + ' ��¼������<span style="color:red" id="search_totalRow"' +
		' name="search_curRow">' + this.totalRow + '</span>����\n';
	
	// ѡ��ҳ
	if( this.totalPage <= 10 ){
		result = result + ' ת�� <select style="color:red" id="' + this.property + ':start-page"' + 
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
	 	
		result = result + '</select> ҳ\n';
	}
	else{
		result = result + ' ת�� <input type="text" style="color:red" id="' + this.property + ':start-page"' + 
			' name="' + this.property + ':start-page" value="' + this.startPage + '" onkeydown="if(event.keyCode == 13){' +
			this.objName + ".goPage()" + '}" size="3" style="border: 1px solid #7f9db9"> ҳ\n';
	}
	
	// ÿҳ�ļ�¼����
	result = result + ' ÿҳ��ʾ��' + 
		'<input type="text" style="color:red" id="' + this.property + ':page-row"' +
		' name="' + this.property + ':page-row" value="' + this.pageRow + '"' +
		' onkeydown="if(event.keyCode == 13){' + this.objName + ".goPage()" + '}" size="3" style="border: 1px solid #7f9db9"> ����Ϣ\n';
	
	// ����
	result = result + '</td>\n';
	
	// ��ť��һҳ
	result = result + '<td align="right">\n' + 
		'<button style="WIDTH:25px" onclick="' + this.objName + ".goPage('1')" +
		'" class="menu" title="��һҳ"';
	if( this.startPage <= 1 ){
	 	result = result + ' disabled';
	}
	
	result = result + '>|&lt;</button>&nbsp;\n';
	
	// ��һҳ
	result = result + '<button style="WIDTH:25px" onclick="' + this.objName + ".goPage('" + (this.startPage-1) + "')" +
		'" class="menu" title="��һҳ"';
	if( this.startPage <= 1 ){
	 	result = result + ' disabled';
	}
	
	result = result + '>&lt;</button>&nbsp;\n';
	
	// ��һҳ
	result = result + '<button style="WIDTH:25px" onclick="' + this.objName + ".goPage('" + (this.startPage+1) + "')" +
		'" class="menu" title="��һҳ"';
	if( this.startPage >= this.totalPage ){
	 	result = result + ' disabled';
	}
	
	result = result + '>&gt;</button>&nbsp;\n';
	
	// ���һҳ
	result = result + '<button style="WIDTH:25px" onclick="' + this.objName + ".goPage('" + this.totalPage + "')" +
		'" class="menu" title="���һҳ"';
	if( this.startPage >= this.totalPage ){
	 	result = result + ' disabled';
	}
	
	result = result + '>&gt;|</button>\n';
	
	// ����������
	result = result + '</td></tr></table>\n' + '</td></tr>\n';
	
	document.write( result );
}

// ����ҳ��
function search_goPage( flag )
{
	if( flag == null ){
		flag = 'go';
	}
	
	// ÿҳ��¼
	var pageRow=parseInt(document.getElementById(this.property + ':page-row').value);
	if( pageRow > 100 ){
		pageRow = 100;
	}
	else if( pageRow < 1 ){
		pageRow = 10;
	}
	
	// ��������ҳ��
	if( flag == 'go' ){
		var startPage = parseInt(document.getElementById(this.property + ':start-page').value);
	}
	else{
		var startPage = parseInt(flag);
	}
	
	// ִ�в�ѯ
	var	page = new pageDefine( this.reqPath, '������Ϣ', 'window' );
	page.addValue( this.queryString, this.property + ':query-string' );
	page.addValue( startPage, this.property + ':start-page' );
	page.addValue( pageRow, this.property + ':page-row' );
	
	// ��ѯ��Χ
	var val = this.getScopeValue();
	if( val != null && val.length != 0 ){
		page.addValue( val, this.property + ':scope-key1' );
	}
	
	page.goPage();
}


// ȡ������Χ������
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


// ��ʾ���
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
	
	// ִ�в�ѯ
	var	page = new pageDefine( this.reqPath, '������Ϣ', 'window' );
	page.addValue( queryString, this.property + ':query-string' );
	page.addValue( startPage, this.property + ':start-page' );
	page.addValue( pageRow, this.property + ':page-row' );
	
	// ��ѯ��Χ
	var val = this.getScopeValue();
	if( val != null && val.length != 0 ){
		page.addValue( val, this.property + ':scope-key1' );
	}
	
	page.goPage();
}




-->
