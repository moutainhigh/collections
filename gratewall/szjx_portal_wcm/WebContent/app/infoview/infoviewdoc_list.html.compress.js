Ext.ns('wcm.CrashBoard', 'wcm.LANG');
var m_template = [
	'<div class="wcm-cbd" id="{0}">',
		'<div class="header l" id="header-{0}"><div class="r"><div class="c">',
			'<div class="spt"></div>',
			'<div class="title" id="dialogTitle-{0}">{1}</div>',
			'<div class="tools" id="tools-{0}">',
				'<a class="close" href="#" id="close-{0}"></a>',
			'</div>',
		'</div></div></div>',
		'<div class="body l"><div class="r"><div class="c">',
			'<table border=0 cellspacing=0 cellpadding=0 class="cb-table">',
				'<tr><td id="content-{0}">',
				'<iframe src="{2}" id="frm-{0}" style="height:100%;width:100%;"',
				' frameborder="0" onload="wcm.CrashBoard.contentLoaded(\'{0}\', this);"></iframe>',
				'</td></tr>',
				'<tr style="display:{5}"><td class="buttons" id="buttons-{0}" style="text-align:center;">',
				'<span class="wcm-btn cbd-btn-right" id="ok-{0}-wrap"><span class="cbd-btn-left"><button class="cbd-btn" id="ok-{0}"><b>{3}</b></button></span></span>',
				'<span style="display:{7}" class="wcm-btn cbd-btn-right" id="next-{0}-wrap"><span class="cbd-btn-left"><button class="cbd-btn" id="next-{0}"><b>{6}</b></button></span></span>',
				'<span class="wcm-btn cbd-btn-right"><span class="cbd-btn-left" id="cancel-{0}-wrap"><button class="cbd-btn" id="cancel-{0}"><b>{4}</b></button></span></span>',
				'</td></tr>',
			'</table>',
		'</div></div></div>',
		'<div class="footer l"><div class="r"><div class="c"></div></div></div>',
	'</div>',
].join("");
function defCrashBoard(){
	var m_id = 0;
	var cache = {};
	function $cb(cfg){
		cfg = Ext.apply({
			id : 'cb-' + (++m_id),
			title : wcm.LANG.crashborad_2011 || '系统提示框',
			appendParamsToUrl : true
		}, cfg);
		Ext.apply(this, cfg);
		this._win = window;
		cache[cfg.id] = this;
	}
	$cb.prototype = {
		getEl : function(id){
			return this._win.$(id || this.id);
		},
		onOk : function(name){
			var frm = $('frm-' + this.id), win, fn;
			try{
				win = frm.contentWindow;
				fn = name ? win[name] : win.onOk;
			}catch(err){
			}
			if(!fn)return;
			var rst = fn(this);
			if(rst===false)return;
			if(this.callback)this.callback(rst);
			this.close();
		},
		onCancel : function(){
			this.close();
		},
		show : function(){
			var t = this;
			if(t.rendered) return;
			t.rendered = true;
			var sHtml = String.format(m_template, t.id, t.title, t.url,
				t.ok || wcm.LANG.DIALOG_BTN_OK || '确定', t.cancel || wcm.LANG.DIALOG_BTN_CANCEL || '取消', t.btns==false?'none':'', t.next , t.next == undefined?'none':'');
			var div = document.createElement('DIV');
			document.body.appendChild(div);
			div.innerHTML = sHtml;
			var cbEle = t.getEl();
			cbEle.style.zIndex = window.$MsgCenter ? $MsgCenter.genId(100) : 999;
			t.getEl("cancel-" + t.id).onclick = t.getEl("close-" + t.id).onclick
				= function(){t.onCancel();return false;};
			if(this.draggable !== false) drag(cbEle, t.getEl('header-' + t.id), this.maskable);
			t.getEl("ok-" + t.id).onclick = function(event){t.onOk()};
			t.getEl("next-" + t.id).onclick = function(event){t.onOk('onNext')};
			cbEle.style.display = '';
			if(t.width) t.getEl().style.width = t.width;
			if(t.height) {			
				var dom = t.getEl('content-' + t.id);
				dom.style.height = t.height;
				dom.style.height = (dom.offsetHeight - 65) + 'px';
			}
			var ua = navigator.userAgent.toLowerCase();
			var isIE = ua.indexOf("opera") == -1 && ua.indexOf("msie") > -1;
			var isStrict = document.compatMode == "CSS1Compat";
			var docEle = isIE && !isStrict ? document.body : document.documentElement;
			var left = (docEle.clientWidth - cbEle.offsetWidth) / 2 + docEle.scrollLeft + 'px';
			var top = (docEle.clientHeight - cbEle.offsetHeight) / 2 + docEle.scrollTop + 'px';
			cbEle.style.left = t.left || left;
			cbEle.style.top = t.top || top;
			this.showShield();
		},
		hide : function(){
			this.getEl().style.display = 'none';
			this.hideShield();
		},
		initShield : function(){
			if(!this.maskable && !Ext.isIE6) return;
			if($(this.id + '-shld')) return;
			var dom = document.createElement('iframe');
			dom.src = Ext.blankUrl;
			dom.style.display = 'none';
			dom.style.border = 0;
			dom.frameBorder = 0;
			Element.addClassName(dom, 'wcm-panel-shield');
			dom.style.zIndex = this.getEl().style.zIndex - 1;
			dom.id = this.id + '-shld';
			document.body.appendChild(dom);
		},
		showShield : function(){
			if(!this.maskable && !Ext.isIE6) return;
			this.initShield();
			var dom = this.getEl();
			var oStyle = $(this.id + '-shld').style;
			if(!this.maskable){
				oStyle.left = (parseInt(dom.style.left, 10) )+"px";
				oStyle.top = (parseInt(dom.style.top, 10) + 1)+"px";
				oStyle.width = (dom.offsetWidth - 4)+"px";
				oStyle.height = (dom.offsetHeight - 4)+"px";
			}	
			oStyle.display = '';
		},
		hideShield : function(){
			if(!this.maskable && !Ext.isIE6) return;
			$(this.id + '-shld').style.display = 'none';
		},
		destroyShield : function(){
			if(!this.maskable && !Ext.isIE6) return;
			var dom = $(this.id + '-shld');
			if(!dom) return;
			dom.parentNode.removeChild(dom);
		},
		close : function(){
			try{
				var t = this;
				t.hide();
				var dom = t.getEl("content-" + t.id);
				t.getEl("frm-" + t.id).src = '';
				dom.innerHTML = "";
				t.getEl("close-" + t.id).onclick = null;	
				t.getEl("ok-" + t.id).onclick = null;
				t.getEl("cancel-" + t.id).onclick = null;
				t.getEl('header-' + t.id).onmousedown = null;
				delete cache[t.id];
				dom = t.getEl();
				dom.parentNode.parentNode.removeChild(dom.parentNode);

			}catch(err){}
		}
	};
	function $toQueryStr(params){
		if(!params)return '';
		if(typeof params!='object')return params;
		var rst = [];
		for(var nm in params){
			var v = params[nm], type = typeof v;
			if(type!='string' && type!='number' && type!='boolean')continue;
			rst.push(nm, '=', encodeURIComponent(params[nm]), '&');
		}
		return rst.join('');
	}
	wcm.CrashBoard.get = function(cfg){
		if(!cfg.appendParamsToUrl)
		cfg = Ext.apply({
			appendParamsToUrl : true
		}, cfg);
		var cjoin = cfg.url.indexOf('?')==-1 ? '?' : '&';
		if(cfg.appendParamsToUrl==true)
		cfg.url = cfg.url + cjoin + $toQueryStr(cfg.params);
		return cache[cfg.id] || (new $cb(cfg));
	}
	wcm.CrashBoard.contentLoaded = function(id, frm){
		var cb = cache[id];
		if(!cb) return;
		try{
			var win = frm.contentWindow;
			if(!win.init)return;
		}catch(err){
		}
		win.init(cb.params, cb);
	}
}
var cbGetStyle = function(){
	return window.getComputedStyle ? function(el, style){
		var cs = window.getComputedStyle(el, "");
		return cs ? cs[style] : null;
	} : function(el, style){
		return el.style[style] || el.currentStyle[style];
	}
}();
function drag(o, p, maskable){
	var id = o.id;
    p.onmousedown=function(a){
		var frm = $('frm-' + id);
		if(frm) frm.style.visibility = 'hidden';
		var sld = $(id + '-shld');
		var d=document;if(!a)a=window.event;
		var l=parseInt(cbGetStyle(o,'left'),10),t=parseInt(cbGetStyle(o,'top'),10);
		var x=a.pageX?a.pageX:a.clientX,y=a.pageY?a.pageY:a.clientY;
		if(p.setCapture)p.setCapture();
		else if(window.captureEvents)window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
		d.onmousemove=function(a){
			if(!a)a=window.event;
			if(!a.pageX)a.pageX=a.clientX;
			if(!a.pageY)a.pageY=a.clientY;
			var tx=a.pageX-x+l,ty=a.pageY-y+t;
			o.style.left=tx+"px";
			o.style.top=ty+"px";
			if(!maskable && sld){
				sld.style.left=(tx)+"px";
				sld.style.top=(ty+1)+"px";
			}
		}
		d.onmouseup=function(){
			if(frm) frm.style.visibility = 'visible';
			if(p.releaseCapture)p.releaseCapture();
			else if(window.releaseEvents)window.releaseEvents(Event.MOUSEMOVE|Event.MOUSEUP);
			d.onmousemove=null;
			d.onmouseup=null;
		}
    }
}
defCrashBoard();
Ext.apply(PageContext, {
	contextMenuEnable : true,
	gridDraggable : true,
	initDraggable : function(){
		var docGridDragDrop = new wcm.dd.GridDragDrop({
			id : 'wcm_table_grid', 
			rootId : 'grid_body',
			captureEnable:false
		});
		docGridDragDrop.addListener('dispose', function(){
			top.DragAcross = null;
			delete this.hintInSelf;
			delete this.hintInTree;
		});
		Ext.apply(docGridDragDrop, {
			_getHint : function(row){
				if(this.hintInSelf) return this.hintInSelf;
				if(!top.DragAcross){
					top.DragAcross = {};
				}
				var sCurrId = row.getObjId();
				var aSelectedIds = wcm.Grid.getRowIds(true);
				if(!aSelectedIds.include(sCurrId)) aSelectedIds.push(sCurrId);			
				Object.extend(top.DragAcross,{
					ObjectType : 605 ,
					FolderId :  row.getAttribute("channelid"),
					ObjectId : sCurrId,
					ObjectIds : aSelectedIds
				});
				if(!PageContext.CanSort){
					return wcm.LANG.infoviewdoc_list_base_1000 || "当前表单列表不支持排序";
				}
				if(PageContext.params["OrderBy"]){
					return wcm.LANG.infoviewdoc_list_base_2000 || "自动排序列表不支持手动排序";
				}
				if(!this.sortable){
					return wcm.LANG.infoviewdoc_list_base_3000 || "当前表单没有权限排序";
				}
				return "[表单-"+sCurrId+"]";
			},
			_isSortable : function(row){
				if(!PageContext.CanSort || PageContext.params["OrderBy"]) return false;
				var sRight = row.dom.getAttribute("right", 2);
				if(sRight!=null&&!wcm.AuthServer.checkRight(sRight, 62)){
					return false;
				}
				return true;
			},
			_move : function(srcRow, iPosition, dstRow, eTargetMore){
				//iPosition表示当前元素相对于目标元素的位置,1表示之前,0表示之后
				if(!PageContext.CanSort&&PageContext.params["OrderBy"]) return false;
				var bCurrTopped = srcRow.getAttribute("isTopped")=='true';
				var bTargetTopped = dstRow.getAttribute("isTopped")=='true';
				var docid = srcRow.getAttribute('docid');
				var rowId = srcRow.getAttribute('rowId');
				var targetDocId = dstRow.getAttribute('docid');
				if(bCurrTopped!=bTargetTopped){//当前行与目标行，一行是置顶，一行是非置顶
					var bFixedUp = true;
					if(iPosition==0){//有前一行，插入到目标行之后的情况
						if(!bCurrTopped&&eTargetMore!=null){//非置顶行判断前一行和后一行
							//若下一行为非置顶行，则不交叉
							//反之，则交叉
							var bTargetMoreTopped = eTargetMore.getAttribute("isTopped")=='true';
							if(!bTargetMoreTopped){
								//用后一行的数据，表示插入到它之前
								targetDocId = eTargetMore.getAttribute('docid');
								iPosition = (iPosition==0)?1:0;
								bFixedUp = false;
							}
						}
						else if(!bCurrTopped&&eTargetMore==null){//非置顶行上一行为置顶行，下一行无
							//相当于至少有n-1个置顶行,而被拖动的那行是非置顶行
							//所以非置顶行本身未移动,此种情况其实不需要考虑
							//考虑的话不计为交叉
							bFixedUp = false;
						}
						else if(bCurrTopped){//置顶行的上一行为非置顶行,必然交叉
							bFixedUp = true;
						}
					}
					else{//无前一行，但有后一行，插入到目标行之前的情况
						if(!bCurrTopped){//当前行非置顶，插在置顶行之前必然交叉
							bFixedUp = true;
						}
						else{//当前行置顶，插在非置顶行之前必然不交叉
							//但此种情况可以不考虑
							//当前置顶行拖动后前无置顶行(在第一行)，后无置顶行
							//可以知道当前只有一个置顶行，且没有交换位置
							//不应该到这里来
							bFixedUp = false;
							//若来到这里就不能按置顶交换顺序的方式处理了
							bCurrTopped = false;
						}
					}
					if(bFixedUp){
						Ext.Msg.$timeAlert(wcm.LANG.infoviewdoc_list_base_5000 || '置顶表单与非置顶表单间不能交叉排序.',5);
						return false;
					}
				}
				if(!confirm(wcm.LANG.infoviewdoc_list_base_6000 || '您确定要调整表单的顺序？')) return false;
				if(bCurrTopped){
					var oPostData = {
						"TopFlag" : 3,/*表示不改变置顶设置*/
						"ChannelId" : PageContext.getParameter("channelid"),
						"DocumentId" : docid,
						"Position" : iPosition,
						"TargetDocumentId" : targetDocId
					}
					BasicDataHelper.call('wcm6_viewdocument', 'setTopDocument', oPostData, true, function(){
						PageContext.updateCurrRows(rowId);
					});
				}
				else{
					var oPostData = {
						FromDocId:docid,
						ToDocId:targetDocId,
						position:iPosition,
						channelid: PageContext.getParameter("channelid")
					};
					BasicDataHelper.call('wcm6_viewdocument', 'changeOrder', oPostData, false, function(){
						PageContext.updateCurrRows(rowId);
					}, function(trans,json){
						wcm.FaultDialog.show({
							code		: $v(json,'fault.code'),
							message		: $v(json,'fault.message'),
							detail		: $v(json,'fault.detail'),
							suggestion  : $v(json,'fault.suggestion')
						}, wcm.LANG.infoviewdoc_list_base_7000 || '与服务器交互时出现错误' , function(){
							PageContext.updateCurrRows(rowId);
						});
					});
				}
				return true;
			}
		});

		var accrossDragger = new wcm.dd.AccrossFrameDragDrop(docGridDragDrop);
		Ext.apply(accrossDragger, {
			getWinInfos : function(){
				if(!top.$('nav_tree'))return [];
				return [
					{win : top}, 
					{
						win : top.$('nav_tree').contentWindow,
						enterMe : function(event, target, opt){
							if(!this.dd.hintInTree){
								if(top.DragAcross.ObjectIds.length>1){
									this.dd.hintInTree = String.format("[引用多篇表单:{0}]",top.DragAcross.ObjectIds);
								}
								else{
									this.dd.hintInTree = String.format("[表单-{0}]",top.DragAcross.ObjectId);
								}
							}
							this.dd.dragEl.innerHTML = this.dd.hintInTree;
						},
						leaveMe : function(event, target, opt){
							this.dd.dragEl.innerHTML = this.dd._getHint(this.dd.row);
						},
						endDrag : function(event, target, opt){
							if(!top.DragAcross || !top.DragAcross.TargetFolderId) return;
							var objId = top.DragAcross.ObjectId;
							BasicDataHelper.call('wcm6_viewdocument', 'quote', {
								"ObjectIds" : top.DragAcross.ObjectIds,
								"ToChannelIds" : top.DragAcross.TargetFolderId
							}, true, function(_transport,_json){
									Ext.Msg.report(_json, wcm.LANG.infoviewdoc_list_base_9000 || '表单引用结果', function(){
										var info = {objId : objId, objType : WCMConstants.OBJ_TYPE_CHNLDOC};
										CMSObj.createFrom(info, PageContext.getContext()).afteredit();
									});
								},
								function(_transport,_json){
									$render500Err(_transport,_json);
								}
							);
						}
				}];	
			}
		});
	}
});
