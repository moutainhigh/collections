// Node object
function Node(id, pid, name, url, title, target, icon, iconOpen, open) {
	this.id = id;
	this.pid = pid;
	this.name = name;
	this.url = url;
	this.title = title;
	this.target = target;
	this.icon = icon;
	this.iconOpen = iconOpen;
	this._io = open || false;
	this._is = false;
	this._ls = false;
	this._hc = false;
	this._ai = 0;
	this._p;
};

// Tree object
function dTree(objName) {
	this.config = {
		target					: null,
		folderLinks			: true,
		useSelection		: true,
		useLines				: true,
		useIcons				: true,
		useStatusText		: false,
		closeSameLevel	: false,
		inOrder					: false
	}
	this.icon = {
		//root				: 'tree_img/base.gif',
		root				: 'tree_img/folderopen.gif',
		folder				: 'tree_img/folder.gif',
		folderOpen			: 'tree_img/folderopen.gif',
		node				: 'tree_img/page.gif',
		empty				: 'tree_img/empty.gif',
		line				: 'tree_img/line.gif',
		join				: 'tree_img/join.gif',
		joinBottom			: 'tree_img/joinbottom.gif',
		plus				: 'tree_img/plus.gif',
		plusBottom			: 'tree_img/plusbottom.gif',
		minus				: 'tree_img/minus.gif',
		minusBottom			: 'tree_img/minusbottom.gif',
		nlPlus				: 'tree_img/nolines_plus.gif',
		nlMinus				: 'tree_img/nolines_minus.gif'
	};
	this.obj = objName;
	this.aNodes = [];
	this.aIndent = [];
	this.root = new Node(-1);
	this.selectedNode = null;
	this.selectedFound = false;
	this.completed = false;
};

// Adds a new node to the node array
dTree.prototype.add = function(id, pid, name, url, title, target, icon, iconOpen, open) {
	this.aNodes[this.aNodes.length] = new Node(id, pid, name, url, title, target, icon, iconOpen, open);
};

// Open/close all nodes
dTree.prototype.openAll = function() {
	this.oAll(true);
};
dTree.prototype.closeAll = function() {
	this.oAll(false);
};

// Outputs the tree to the page
dTree.prototype.toString = function() {
	var str = '<div class="dtree">\n';
	str += this.addNode(this.root);
	str += '</div>';
	if (!this.selectedFound) this.selectedNode = null;
	this.completed = true;
	return str;
};

// Creates the tree structure
dTree.prototype.addNode = function(pNode) {
	var str = '';
	var n=0;
	if (this.config.inOrder) n = pNode._ai;
	for (n; n<this.aNodes.length; n++) {
		if (this.aNodes[n].pid == pNode.id) {
			var cn = this.aNodes[n];
			cn._p = pNode;
			cn._ai = n;
			this.setCS(cn);
			if (!cn.target && this.config.target) cn.target = this.config.target;
			if (!this.config.folderLinks && cn._hc) cn.url = null;
			if (this.config.useSelection && cn.id == this.selectedNode && !this.selectedFound) {
					cn._is = true;
					this.selectedNode = n;
					this.selectedFound = true;
			}
			str += this.node(cn, n);
			if (cn._ls) break;
		}
	}
	return str;
};

// Creates the node icon, url and text
dTree.prototype.node = function(node, nodeId) {
	var str = '<div class="dTreeNode" onmouseover="XMLTree.FireEvent(arguments[0], this);"'
		+' onclick="XMLTree.FireEvent(arguments[0], this);" ondblclick="XMLTree.FireEvent(arguments[0], this);"'
		+' onmouseout="XMLTree.FireEvent(arguments[0], this);">' + this.indent(node, nodeId);
	if (this.config.useIcons) {
		if (!node.icon) node.icon = (this.root.id == node.pid) ? this.icon.root : ((node._hc) ? this.icon.folder : this.icon.node);
		if (!node.iconOpen) node.iconOpen = (node._hc) ? this.icon.folderOpen : this.icon.node;
		if (this.root.id == node.pid) {
			node.icon = this.icon.root;
			node.iconOpen = this.icon.root;
		}
		str += '<img id="i' + this.obj + nodeId + '" src="' + ((node._io) ? node.iconOpen : node.icon) + '" alt="" />';
	}
	if (node.url) {
		str += '<a id="s' + this.obj + nodeId + '" class="' + ((this.config.useSelection) ? ((node._is ? 'nodeSel' : 'node')) : 'node') + '" href="' + node.url + '"';
		if (node.title) str += ' title="' + node.title + '"';
		if (node.target) str += ' target="' + node.target + '"';
		if (this.config.useStatusText) str += ' onmouseover="window.status=\'' + node.name + '\';return true;" onmouseout="window.status=\'\';return true;" ';
		if (this.config.useSelection && ((node._hc && this.config.folderLinks) || !node._hc))
			str += ' onclick="javascript: ' + this.obj + '.s(' + nodeId + ');"';
		str += '>';
	}
	else if ((!this.config.folderLinks || !node.url) && node._hc && node.pid != this.root.id)
		str += '<a href="javascript: ' + this.obj + '.o(' + nodeId + ');" class="node">';
	str += node.name;
	if (node.url || ((!this.config.folderLinks || !node.url) && node._hc)) str += '</a>';
	str += '</div>';
	if (node._hc) {
		str += '<div id="d' + this.obj + nodeId + '" class="clip" style="display:' + ((this.root.id == node.pid || node._io) ? 'block' : 'none') + ';">';
		str += this.addNode(node);
		str += '</div>';
	}
	this.aIndent.pop();
	return str;
};

// Adds the empty and line icons
dTree.prototype.indent = function(node, nodeId) {
	var str = '';
	if (this.root.id != node.pid) {
		for (var n=0; n<this.aIndent.length; n++)
			str += '<img src="' + ( (this.aIndent[n] == 1 && this.config.useLines) ? this.icon.line : this.icon.empty ) + '" alt="" />';
		(node._ls) ? this.aIndent.push(0) : this.aIndent.push(1);
		if (node._hc) {
			str += '<a href="javascript: ' + this.obj + '.o(' + nodeId + ');"><img id="j' + this.obj + nodeId + '" src="';
			if (!this.config.useLines) str += (node._io) ? this.icon.nlMinus : this.icon.nlPlus;
			else str += ( (node._io) ? ((node._ls && this.config.useLines) ? this.icon.minusBottom : this.icon.minus) : ((node._ls && this.config.useLines) ? this.icon.plusBottom : this.icon.plus ) );
			str += '" alt="" /></a>';
		} else str += '<img src="' + ( (this.config.useLines) ? ((node._ls) ? this.icon.joinBottom : this.icon.join ) : this.icon.empty) + '" alt="" />';
	}
	return str;
};

// Checks if a node has any children and if it is the last sibling
dTree.prototype.setCS = function(node) {
	var lastId;
	for (var n=0; n<this.aNodes.length; n++) {
		if (this.aNodes[n].pid == node.id) node._hc = true;
		if (this.aNodes[n].pid == node.pid) lastId = this.aNodes[n].id;
	}
	if (lastId==node.id) node._ls = true;
};

// Returns the selected node
dTree.prototype.getSelected = function() {
	return null;
};

// Highlights the selected node
dTree.prototype.s = function(id) {
	if (!this.config.useSelection) return;
	var cn = this.aNodes[id];
	if (cn._hc && !this.config.folderLinks) return;
	if (this.selectedNode != id) {
		if (this.selectedNode || this.selectedNode==0) {
			eOld = $("s" + this.obj + this.selectedNode);
			eOld.className = "node";
		}
		eNew = $("s" + this.obj + id);
		eNew.className = "nodeSel";
		this.selectedNode = id;
	}
};

// Toggle Open or close
dTree.prototype.o = function(id) {
	var cn = this.aNodes[id];
	this.nodeStatus(!cn._io, id, cn._ls);
	cn._io = !cn._io;
	if (this.config.closeSameLevel) this.closeLevel(cn);
};

// Open or close all nodes
dTree.prototype.oAll = function(status) {
	for (var n=0; n<this.aNodes.length; n++) {
		if (this.aNodes[n]._hc && this.aNodes[n].pid != this.root.id) {
			this.nodeStatus(status, n, this.aNodes[n]._ls)
			this.aNodes[n]._io = status;
		}
	}
};

// Opens the tree to a specific node
dTree.prototype.openTo = function(nId, bSelect, bFirst) {
	if (!bFirst) {
		for (var n=0; n<this.aNodes.length; n++) {
			if (this.aNodes[n].id == nId) {
				nId=n;
				break;
			}
		}
	}
	var cn=this.aNodes[nId];
	if (!cn || cn.pid==this.root.id || !cn._p) return;
	cn._io = true;
	cn._is = bSelect;
	if (this.completed && cn._hc) this.nodeStatus(true, cn._ai, cn._ls);
	if (this.completed && bSelect) this.s(cn._ai);
	else if (bSelect) this._sn=cn._ai;
	this.openTo(cn._p._ai, false, true);
};

// Closes all nodes on the same level as certain node
dTree.prototype.closeLevel = function(node) {
	for (var n=0; n<this.aNodes.length; n++) {
		if (this.aNodes[n].pid == node.pid && this.aNodes[n].id != node.id && this.aNodes[n]._hc) {
			this.nodeStatus(false, n, this.aNodes[n]._ls);
			this.aNodes[n]._io = false;
			this.closeAllChildren(this.aNodes[n]);
		}
	}
}

// Closes all children of a node
dTree.prototype.closeAllChildren = function(node) {
	for (var n=0; n<this.aNodes.length; n++) {
		if (this.aNodes[n].pid == node.id && this.aNodes[n]._hc) {
			if (this.aNodes[n]._io) this.nodeStatus(false, n, this.aNodes[n]._ls);
			this.aNodes[n]._io = false;
			this.closeAllChildren(this.aNodes[n]);		
		}
	}
}

// Change the status of a node(open or closed)
dTree.prototype.nodeStatus = function(status, id, bottom) {
	eDiv	= $('d' + this.obj + id);
	eJoin	= $('j' + this.obj + id);
	if (this.config.useIcons) {
		eIcon	= $('i' + this.obj + id);
		eIcon.src = (status) ? this.aNodes[id].iconOpen : this.aNodes[id].icon;
	}
	eJoin.src = (this.config.useLines)?
	((status)?((bottom)?this.icon.minusBottom:this.icon.minus):((bottom)?this.icon.plusBottom:this.icon.plus)):
	((status)?this.icon.nlMinus:this.icon.nlPlus);
	eDiv.style.display = (status) ? 'block': 'none';
};

var XMLTree = function() {}
XMLTree.createInstance = function(str, type, dft, dis, name) {
	var doc = loadXml(str);
	if(!doc)return null;
	var oTree = new dTree(name || "oTree");
	var arrdft = dft ? dft.split(",") : [];
	var arrdis = dis ? dis.split(",") : [];
	XMLTree.recurNode(oTree, doc.documentElement, type, arrdft, arrdis);
	return oTree;
}
function hasChildNodes(node) {
	var arrNodes = node.childNodes;
	var nLength = 0;
	for(var i=0; i<arrNodes.length; i++) {
		if(!arrNodes[i])
			continue;
		var sName = arrNodes[i].nodeName;
		if(!sName)
			continue;
		if(sName == "#text")
			continue;
		nLength ++;
	}
	return nLength > 0;
}
XMLTree.recurNode = function(tree, node, type, arrdft, arrdis, path) {
	if(!tree || !node)
		return;

	var sParentId = -1;
	var oParentNode = node.parentNode;
	if(oParentNode && oParentNode.nodeName) {
		sParentId = oParentNode.nodeName;
	}
	if(sParentId == "#document")
		sParentId = "-1";
	else 
		sParentId = path || "";

	var sName = node.nodeName;
	if(!sName || sName == "#text")
		return;
	var bUnUse = node.getAttribute("trs_field_unuse")=="1";
	if(bUnUse)return;
	if(path == null) {
		path = "";
	} else if(path == "") {
		path = sName;
	} else {
		path += "/" + sName;
	}
	var bEntryNode = hasChildNodes(node);
	if(type && !bEntryNode) {
		var sChecked = "";
		for(var i=0; i<arrdft.length; i++) {
			if(arrdft[i] != path)
				continue;
			sChecked = "checked";
			break;
		}
		var sDisabled = "";
		for(var i=0; i<arrdis.length; i++) {
			if(arrdis[i] != path)
				continue;
			sDisabled = "disabled";
			break;
		}
		var sInputHTML = '<input type="' + type + '" name="FieldIds" value="'
								+ path + '" ' + sChecked + ' ' + sDisabled
								+ ' onmousedown="XMLTree.click(this)" onclick="return false">';
		sName = sInputHTML + sName.replace(/\w+:/g, "");
	} else {
		var sClassName = "";
		if(window.PowerHelper){
			sClassName = window.PowerHelper.getNodeClass(path, bEntryNode?1:0);
		}
		sName = '<span unselectable="on" _isNode="true" class="xtreenode '+ sClassName +'" _nodeName="'+ sName +'" _isEntry="'+bEntryNode+'"'
		+' _nodeXPath="'+ path +'"'+' _parentId="'+sParentId+'">'+sName.replace(/\w+:/g, "")+'</span>';
	}
	tree.add(path, sParentId, sName);
	var arrNodes = node.childNodes;
	for(var i=0; i<arrNodes.length; i++) {
		XMLTree.recurNode(tree, arrNodes[i], type, arrdft, arrdis, path);
	}
}
XMLTree.click = function(el) {
	if(el.checked)
		el.checked = false;
	else 
		el.checked = true;
}
XMLTree._findTrueNode = function(node){
	if(node==null)return null;
	var ar = node.getElementsByTagName('SPAN');
	for(var i=0,n=ar.length;i<n;i++){
		if(ar[i]&&ar[i].getAttribute("_isNode", 2)!=null)
			return ar[i];
	}
	return null;
}
XMLTree._findNodeByPath = function(xp, node){
	if(xp==null)return null;
	if(node==null)node = document;
	var ar = node.getElementsByTagName('SPAN');
	for(var i=0,n=ar.length;i<n;i++){
		if(ar[i] && ar[i].getAttribute("_isNode", 2)!=null){
			var sNodeXPath = ar[i].getAttribute("_nodeXPath", 2) || '';
			if(sNodeXPath==xp){
				return ar[i];
			}
		}
	}
	return null;
}
XMLTree._findNodeByName = function(_sNodeName, node){
	if(_sNodeName==null)return null;
	if(node==null)node = document;
	var ar = node.getElementsByTagName('SPAN');
	_sNodeName = _sNodeName.split('/').pop();
	for(var i=0,n=ar.length;i<n;i++){
		if(ar[i] && ar[i].getAttribute("_isNode", 2)!=null 
			&& ar[i].getAttribute("_nodeName", 2)==_sNodeName)
			return ar[i];
	}
	return null;
}
XMLTree.FireEvent = function(event, el) {
	event = event || window.event;
	var sType = event.type;
	if(XMLTree[sType+'Node']){
		var elTrueNode = XMLTree._findTrueNode(el);
		return XMLTree[sType+'Node'](elTrueNode, event);
	}
}
XMLTree.getValue = function() {
	var arrElements = document.getElementsByName("FieldIds");
	var arrValues = new Array();
	for(var i=0; i<arrElements.length; i++) {
		if(!arrElements[i].checked)
			continue;
		arrValues.push(arrElements[i].value);
	}
	return arrValues.toString();
}
if ( window.DOMParser &&
	  window.XMLSerializer &&
	  window.Node && Node.prototype && Node.prototype.__defineGetter__ ) {
   if (!Document.prototype.loadXML) {
      Document.prototype.loadXML = function (s) {
         var doc2 = (new DOMParser()).parseFromString(s, "text/xml");
         while (this.hasChildNodes())
            this.removeChild(this.lastChild);
         for (var i = 0; i < doc2.childNodes.length; i++) {
            this.appendChild(this.importNode(doc2.childNodes[i], true));
         }
      };
	}
	Document.prototype.__defineGetter__( "xml", function () {
	   return (new XMLSerializer()).serializeToString(this);
	});
}
function loadXml(str){
	var doc = Try(
	  function() {return new ActiveXObject('Microsoft.XMLDOM');},
	  function() {return document.implementation.createDocument("","",null);}
	);
	doc.loadXML(str);
	return doc;
}