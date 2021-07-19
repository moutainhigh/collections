if(!window.Ext){
	var Ext = {version: '1'};
}

function $(el) {
	if (typeof el == 'string')
		el = document.getElementById(el) || document.getElementsByName(el)[0];
	return el;
}
Ext.apply = Object.Extend = function(d, s) {
	if(!d || !s)return d;
	for (p in s)d[p] = s[p];
	return d;
}
Ext.applyIf = function(o, c){
	if(o && c){
		for(var p in c){
			if(typeof o[p] == "undefined"){ o[p] = c[p]; }
		}
	}
	return o;
};

String.format = function(format){
	var args = Array.prototype.slice.call(arguments, 1);
	return format.replace(/\{(\d+)\}/g, function(m, i){
		return args[i];
	});
}

Ext.apply(String.prototype, {
  camelize: function() {
    var oStringList = this.split('-');
    if (oStringList.length == 1) return oStringList[0];

    var camelizedString = this.indexOf('-') == 0
      ? oStringList[0].charAt(0).toUpperCase() + oStringList[0].substring(1)
      : oStringList[0];

    for (var i = 1, len = oStringList.length; i < len; i++) {
      var s = oStringList[i];
      camelizedString += s.charAt(0).toUpperCase() + s.substring(1);
    }

    return camelizedString;
  }
})

if(!window.Element){
	var Element = {};
}

Ext.applyIf(Element, {
	addClassName : function(_el, _cls){
		var dom = $(_el);
		if(!dom)return;
		var cls = ' '+dom.className+' ';
		if(cls.indexOf(' '+_cls+' ')==-1){
			dom.className = dom.className + ' ' + _cls;
		}
	},
	removeClassName : function(_el, _cls){
		var dom = $(_el);
		if(!dom)return;
		var cls = dom.className;
		cls = cls.replace(new RegExp('(\\s+|^)'+_cls+'(\\s+|$)','ig'),' ');
		dom.className = cls.trim();
	},
	hasClassName : function(_el, _cls){
		dom = $(_el);
		if(!dom)return false;
		var cls = ' '+dom.className+' ';
		return cls.indexOf(' '+_cls+' ')!=-1;
	},

    getStyle : function(element, style){
		element = $(element);
		var value = element.style[style.camelize()];
		if (!value) {
		  if (document.defaultView && document.defaultView.getComputedStyle) {
			var css = document.defaultView.getComputedStyle(element, null);
			value = css ? css.getPropertyValue(style) : null;
		  } else if (element.currentStyle) {
			value = element.currentStyle[style.camelize()];
		  }
		}

		if (window.opera && ['left', 'top', 'right', 'bottom'].include(style))
		  if (Element.getStyle(element, 'position') == 'static') value = 'auto';

		return value == 'auto' ? null : value;
    }
});

var Position = {
  // set to true if needed, warning: firefox performance problems
  // NOT neeeded for page scrolling, only if draggable contained in
  // scrollable elements
  includeScrollOffsets: false,

  // must be called before calling withinIncludingScrolloffset, every time the
  // page is scrolled
  prepare: function() {
    this.deltaX =  window.pageXOffset
                || document.documentElement.scrollLeft
                || document.body.scrollLeft
                || 0;
    this.deltaY =  window.pageYOffset
                || document.documentElement.scrollTop
                || document.body.scrollTop
                || 0;
  },

  realOffset: function(element) {
    var valueT = 0, valueL = 0;
    do {
      valueT += element.scrollTop  || 0;
      valueL += element.scrollLeft || 0;
      element = element.parentNode;
    } while (element);
    return [valueL, valueT];
  },

  cumulativeOffset: function(element) {
    var valueT = 0, valueL = 0;
    do {
      valueT += element.offsetTop  || 0;
      valueL += element.offsetLeft || 0;
      element = element.offsetParent;
    } while (element);
    return [valueL, valueT];
  },

  positionedOffset: function(element) {
    var valueT = 0, valueL = 0;
    do {
      valueT += element.offsetTop  || 0;
      valueL += element.offsetLeft || 0;
      element = element.offsetParent;
      if (element) {
        p = Element.getStyle(element, 'position');
        if (p == 'relative' || p == 'absolute') break;
      }
    } while (element);
    return [valueL, valueT];
  },

  offsetParent: function(element) {
    if (element.offsetParent) return element.offsetParent;
    if (element == document.body) return element;

    while ((element = element.parentNode) && element != document.body)
      if (Element.getStyle(element, 'position') != 'static')
        return element;

    return document.body;
  },

  // caches x/y coordinate pair to use with overlap
  within: function(element, x, y) {
    if (this.includeScrollOffsets)
      return this.withinIncludingScrolloffsets(element, x, y);
    this.xcomp = x;
    this.ycomp = y;
    this.offset = this.cumulativeOffset(element);

    return (y >= this.offset[1] &&
            y <  this.offset[1] + element.offsetHeight &&
            x >= this.offset[0] &&
            x <  this.offset[0] + element.offsetWidth);
  },

  withinIncludingScrolloffsets: function(element, x, y) {
    var offsetcache = this.realOffset(element);

    this.xcomp = x + offsetcache[0] - this.deltaX;
    this.ycomp = y + offsetcache[1] - this.deltaY;
    this.offset = this.cumulativeOffset(element);

    return (this.ycomp >= this.offset[1] &&
            this.ycomp <  this.offset[1] + element.offsetHeight &&
            this.xcomp >= this.offset[0] &&
            this.xcomp <  this.offset[0] + element.offsetWidth);
  },

  // within must be called directly before
  overlap: function(mode, element) {
    if (!mode) return 0;
    if (mode == 'vertical')
      return ((this.offset[1] + element.offsetHeight) - this.ycomp) /
        element.offsetHeight;
    if (mode == 'horizontal')
      return ((this.offset[0] + element.offsetWidth) - this.xcomp) /
        element.offsetWidth;
  },

  page: function(forElement) {
    var valueT = 0, valueL = 0;

    var element = forElement;
    do {
      valueT += element.offsetTop  || 0;
      valueL += element.offsetLeft || 0;

      // Safari fix
      if (element.offsetParent==document.body)
        if (Element.getStyle(element,'position')=='absolute') break;

    } while (element = element.offsetParent);

    element = forElement;
    do {
      valueT -= element.scrollTop  || 0;
      valueL -= element.scrollLeft || 0;
    } while (element = element.parentNode);

    return [valueL, valueT];
  },
	getAbsolutePositionInTop : function(dom, topWin){
		var valueT = 0, valueL = 0;
		var doc = dom.ownerDocument;
		var win = doc.parentWindow || doc.defaultView;
		try{
			do{
				positions = Position.cumulativeOffset(dom)
				valueL += positions[0];
				valueT += positions[1];
				if(topWin == win) break;
				dom = win.frameElement;
				win = win.parent;
			}while(dom);
		}catch(error){
			//just skip it.
		}
		return [valueL, valueT]
	},
	getPageInTop : function(dom, topWin){
		var valueT = 0, valueL = 0;
		var doc = dom.ownerDocument;
		var win = doc.parentWindow || doc.defaultView;
		try{
			do{
				positions = Position.page(dom)
				valueL += positions[0];
				valueT += positions[1];
				if(topWin == win) break;
				dom = win.frameElement;
				win = win.parent;
			}while(dom);
		}catch(error){
			//just skip it.
		}
		return [valueL, valueT]
	},
  clone: function(source, target) {
    var options = Object.Extend({
      setLeft:    true,
      setTop:     true,
      setWidth:   true,
      setHeight:  true,
      offsetTop:  0,
      offsetLeft: 0
    }, arguments[2] || {})

    // find page position of source
    source = $(source);
    var p = Position.page(source);

    // find coordinate system to use
    target = $(target);
    var delta = [0, 0];
    var parent = null;
    // delta [0,0] will do fine with position: fixed elements,
    // position:absolute needs offsetParent deltas
    if (Element.getStyle(target,'position') == 'absolute') {
      parent = Position.offsetParent(target);
      delta = Position.page(parent);
    }

    // correct by body offsets (fixes Safari)
    if (parent == document.body) {
      delta[0] -= document.body.offsetLeft;
      delta[1] -= document.body.offsetTop;
    }

    // set position
    if(options.setLeft)   target.style.left  = (p[0] - delta[0] + options.offsetLeft) + 'px';
    if(options.setTop)    target.style.top   = (p[1] - delta[1] + options.offsetTop) + 'px';
    if(options.setWidth)  target.style.width = source.offsetWidth + 'px';
    if(options.setHeight) target.style.height = source.offsetHeight + 'px';
  }
}

// Safari returns margins on body which is incorrect if the child is absolutely
// positioned.  For performance reasons, redefine Position.cumulativeOffset for
// KHTML/WebKit only.
if (/Konqueror|Safari|KHTML/.test(navigator.userAgent)) {
  Position.cumulativeOffset = function(element) {
    var valueT = 0, valueL = 0;
    do {
      valueT += element.offsetTop  || 0;
      valueL += element.offsetLeft || 0;
      if (element.offsetParent == document.body)
        if (Element.getStyle(element, 'position') == 'absolute') break;

      element = element.offsetParent;
    } while (element);

    return [valueL, valueT];
  }
}
