//定位
var camelRe = /(-[a-z])/gi, propCache = {};
var camelFn = function(m, a){ return a.charAt(1).toUpperCase(); };
function getStyle(el, prop){
	var view = document.defaultView;
	var v, cs, camel;
	if(view && view.getComputedStyle){
		if(prop == 'float')prop = "cssFloat";
		if(v = el.style[prop])return v;
		if(cs = view.getComputedStyle(el, "")){
			if(!(camel = propCache[prop])){
				camel = propCache[prop] = prop.replace(camelRe, camelFn);
			}
			return cs[camel];
		}
		return null;
	}
	if(prop == 'float')prop = "styleFloat";
	if(!(camel = propCache[prop])){
		camel = propCache[prop] = prop.replace(camelRe, camelFn);
	}
	if(v = el.style[camel])return v;
	if(cs = el.currentStyle)return cs[camel];
	return null;
}
var Position = {
	page: function(oriEl) {
		var t = 0, l = 0;
		var el = oriEl;
		do {
			t += el.offsetTop  || 0;
			l += el.offsetLeft || 0;
			if (el.offsetParent==document.body
				&& getStyle(el, 'position')=='absolute') break;
		} while (el = el.offsetParent);
		el = oriEl;
		do {
			t -= el.scrollTop  || 0;
			l -= el.scrollLeft || 0;
		} while (el = el.parentNode);
		return [l, t];
	},
	offsetParent: function(el) {
		if (el.offsetParent) return el.offsetParent;
		var bd = document.body;
		if (el == bd) return el;
		while ((el = el.parentNode) && el != bd){
			var ps = getStyle(el, 'position');
			if (ps == 'relative' ||  ps== 'absolute')continue;
			return el;
		}
		return bd;
	},
	clone: function(es, et, cfg) {
		cfg = Object.extend({
			setLeft:    true,
			setTop:     true,
			setWidth:   true,
			setHeight:  true,
			offsetTop:  0,
			offsetLeft: 0
		}, cfg || {})
		es = $(es);
		var p = Position.page(es);
		et = $(et);
		var delta = [0, 0];
		var parent = null;
		if (getStyle(et, 'position') == 'absolute') {
			parent = Position.offsetParent(et);
			delta = Position.page(parent);
		}
		var bd = document.body;
		if (parent == bd) {
			delta[0] -= bd.offsetLeft;
			delta[1] -= bd.offsetTop;
		}
		var o = et.style;
		if(cfg.setLeft)   o.left  = (p[0] - delta[0] + cfg.offsetLeft) + 'px';
		if(cfg.setTop)    o.top   = (p[1] - delta[1] + cfg.offsetTop) + 'px';
		if(cfg.setWidth)  o.width = es.offsetWidth + 'px';
		if(cfg.setHeight) o.height = es.offsetHeight + 'px';
	}
}