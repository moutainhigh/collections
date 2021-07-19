//绘制视图
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
function parseWcmObjs(_sXML){
	var arWCMObjs = [];
	var xmlDoc = loadXml(_sXML);
	var nSize = xmlDoc.documentElement.childNodes.length;
	for(var i=0; i<nSize; i++)
		arWCMObjs.push(parseWcmobj(xmlDoc.documentElement.childNodes[i]));
	delete xmlDoc;
	return arWCMObjs;
}
function getNodeText(_node){
	if(_node.childNodes.length==0){
		return _node.nodeValue;
	}
	return getNodeText(_node.childNodes[0]);
}
function parseWcmobj(_xmlNode){
	var nSize = _xmlNode.childNodes.length;
	for(var i=0; i<nSize; i++){
		if(_xmlNode.childNodes[i].nodeName.toUpperCase() != 'PROPERTIES')continue;
		var o = {
			get : function(s){
				return this[s.toUpperCase()];
			}
		}
		var props = _xmlNode.childNodes[i];
		var len = props.childNodes.length;
		for(var j=0; j<len; j++){
			var name = props.childNodes[j].nodeName.toUpperCase();
			var v = getNodeText(props.childNodes[j]) || "";
			if(v.indexOf('(TRSWCM_CDATA_END_HOLDER_TRSWCM)') >= 0){
				v = v.replace(/\(TRSWCM_CDATA_END_HOLDER_TRSWCM\)/g, "]]>");
			}
			o[name] = v;
		}
		return o;
	}
	return null;
}
Ext.ns('wcm.MultiView');
function defMultiView(){
	function myViews(id){
		this.id = id;
		$(id).innerHTML = [
			'<table border=0 cellspacing=0 cellpadding=0 width="100%" height="100%">',
			'<tbody>',
				'<tr bgcolor="#FFFFFF">',
					'<td id="', id, '_frms"></td>',
				'</tr>',
				'<tr height="20" bgcolor="#ECE9D8">',
					'<td id="', id, '_ng"></td>',
				'</tr>',
			'</tbody>',
			'</table>'
		].join('');
		this.m_arrViews = [];
		var caller = this;
		Ext.get(id + '_ng').on('click', function(ev, target){
			target = findItem(target, 'iv_ng');
			if(target==null)return;
			var viewId = target.getAttribute('viewid', 2);
			caller.SwitchView(viewId);
		});
		if(wcm.MultiView.doAfterMvRender){
			wcm.MultiView.doAfterMvRender(this);
		}
	}
	var m_sTabHtml = [
		'<span class="iv_ng {2}" id="iv_ng_{0}" infoview_navigator_tab="1"',
		' trs_base_id="{3}" trs_item_id="{0}" trs_item_default_view="{4}"', 
		' trs_item_public_fill="{5}" element-type="12" trs_type="text" trs_temp_id="{0}" viewid="{0}">',
		'<div class="l"><div class="r"><div class="c">&nbsp;{1}&nbsp;</div></div></div></span>'
	].join('');
	var m_sFrmHtml = [
		'<iframe id="iv_frm_{0}" style="width:100%;height:100%;display:{1}" src="{2}"',
		' trs_type="iframe" frameborder="0" scrolling="auto"></iframe>'
	].join('');
	var blankUrl = '';
	myViews.prototype = {
		add : function(_sId, _sName, _sURL, _oArgs) {
			this.m_arrViews.push({
				id      : _sId,
				name    : _sName,
				url     : _sURL,
				args    : _oArgs
			});
		},
		SwitchView : function(viewId){
			m_nCurrViewId = this.m_nCurrViewId;
			if(m_nCurrViewId==viewId)return;
			if(m_nCurrViewId){
				$('iv_ng_' + m_nCurrViewId).className = 'iv_ng deactive-view';
				$('iv_frm_' + m_nCurrViewId).style.display = 'none';
			}
			$('iv_ng_' + viewId).className = 'iv_ng curr-view';
			$('iv_frm_' + viewId).style.display = '';
			if($('iv_frm_' + viewId).src==blankUrl){
				for(var i=0; i<this.m_arrViews.length; i++) {
					var iv = this.m_arrViews[i];
					if(viewId!=iv.id)continue;
					$('iv_frm_' + viewId).src = iv.url;
					break;
				}
			}
			this.m_nCurrViewId = viewId;
		},
		getNavTab : function(viewId){
			return $('iv_ng_' + viewId);
		},
		getNavTd : function(){
			return $(this.id + '_ng');
		},
		render : function() {
			var nav = $(this.id + '_ng');
			var frms = $(this.id + '_frms');
			nav.innerHTML = frms.innerHTML = "";
			this.m_nCurrViewId = 0;
			var navRst = [], frmsRst = [];
			for(var i=0; i<this.m_arrViews.length; i++) {
				var iv = this.m_arrViews[i];
				var b = iv.args.default_view==1;
				navRst.push(String.format(m_sTabHtml,
					iv.id, iv.name, b?'curr-view':'deactive-view',
					this.id, iv.args.default_view, iv.args.public_fill
				));
				frmsRst.push(String.format(m_sFrmHtml,
					iv.id, b?'':'none', b?iv.url:blankUrl));
				if(b)this.m_nCurrViewId = iv.id;
			}
			nav.innerHTML = navRst.join('');
			frms.innerHTML = frmsRst.join('');
			if(this.m_nCurrViewId==0&&this.m_arrViews.length>0){
				this.SwitchView(this.m_arrViews[0].id);
			}
		}
	}
	var $mv0 = null;
	var $mv = wcm.MultiView = {
		draw : function(ctnid, info) {
			var fra = $('frmAction');
			info = info || {
				InfoViewId : fra.InfoViewId.value,
				ViewMode : fra.ViewMode ? fra.ViewMode.value : 0,
				FlowDocId : fra.FlowDocId ? fra.FlowDocId.value : 0
			};
			var nInfoViewId = info.InfoViewId;
			var url = "infoview_load_views.jsp?InfoViewId=" + nInfoViewId
			new ajaxRequest({
				url : url,
				onSuccess : function(trans){
					$mv._draw(trans.responseText, ctnid, info);
				},
				onFailure : function(trans){
					alert("Problem retrieving XML data:\n" + trans.responseText);
				}
			});
		},
		_draw : function(xml, ctnid, info) {
			var nViewMode = info.ViewMode;
			var isAddEdit = 0;
			if(nViewMode==4) isAddEdit = 1;
			var nFlowDocId = info.FlowDocId || 0;
			$mv0 = new myViews(ctnid || "infoview");
			var arrObjs = parseWcmObjs(xml);
			if(arrObjs.length==0)return;
			for(var i=0; i<arrObjs.length; i++) {
				var o = arrObjs[i];
				if(!o)continue;
				var sIvViewId = o.get("IvViewId");
				var sViewId = o.get("InfoViewId");
				var sURL = 'infoview_view_to_html.jsp?InfoViewId=' + sViewId
					+ '&IVViewId=' + sIvViewId
					+ '&ViewMode=' + nViewMode
					+ '&FlowDocId=' + nFlowDocId
					+ '&isAddEdit=' + isAddEdit;
				$mv0.add(sIvViewId, o.get("ViewDesc"), sURL, {
					default_view : o.get("DefaultView"),
					public_fill  : o.get("PublicFill")
				});
			}
			$mv0.render();
		},
		mv0 : function(){
			return $mv0;
		}
	}
}
defMultiView();