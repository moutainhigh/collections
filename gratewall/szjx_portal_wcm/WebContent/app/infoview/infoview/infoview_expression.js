//表达式
var xdMath = {
	Nz : function(xp, elId) {
		var eles = EleHelper.getElesByTrsTmpId(xp, xExpression.cntEl($(elId)));
		if(eles.length == 0)
			return 0;
		if(eles.length == 1)
			return parseInt(eles[0].value, 10) || 0;
		var arrValues = [];
		for(var i=0; i<eles.length; i++)
			arrValues.push(parseInt(eles[i].value, 10));
		return arrValues;
	}
}
var xExpression = {
	caches : {},
	cntEl : function(el){
		while(el!=null && el.tagName!='BODY'){
			if(EleHelper.isContainerEle(el))
				return el;
			el = el.parentNode;
		}
		return document.body;
	},
	calMyXp : function(el){
		return this.cntEl(el).getAttribute('trs_temp_id', 2) || '';
	},
	parse : function(el) {
		if(!el)return;
		var eps = el.getAttribute("trs_calc_expression"), elId = el.id;
		if(!eps)return null;
		var names = [], xp = this.calMyXp(el);
		if(this.caches[elId])return this.caches[elId];
		var rEps = eps.replace(/xdMath(:|\.)([^\(\)]+)\(([^\(\)]+)\)/g,
			function(a, dot, fn, cxp){
				cxp = xExpression.calXp(xp, cxp);
				names.push(cxp);
				return "xdMath." + fn + "('" + cxp + "', '" + elId + "')";
			}
		);
		var rst = this.caches[elId] = {
			eps : rEps,
			names : names,
			xp : xp,
			id : elId
		};
		rst.fn = function(){
			var el = $(rst.id);
			try {
				el.value = eval(rst.eps);
				if(el.onchange)el.onchange();
			} catch(e){
			}
		}
		return rst;
	},
	init : function(el) {
		if(!el)return;
		var result = this.parse(el);
		if(result==null)return;
		var ref = el.getAttribute("trs_calc_refresh");
		if(ref && ref.toLowerCase() == "oninit") {
			result.fn();
			return;
		}
		if(result.names.length==0)return;
		var eles = EleHelper.getElesByTrsTmpId(result.names, this.cntEl(el));
		for(var i=0; i<eles.length; i++) {
			Event.observe(eles[i], 'change', result.fn);
		}
	},
	renderEl : function(cntEl) {
		for(var xp in this.caches){
			this.init($(xp));
		}
		var eles = cntEl.getElementsByTagName('INPUT'), el;
		for(var i=0; i<eles.length; i++) {
			el = eles[i];
			var xct = el.getAttribute('xd:xctname', 2);
			if(!xct || xct.toLowerCase()!='expressionbox')continue;
			if(this.caches[el.id]!=null){
				el.id = el.id + '_' + genId();
			}
			this.init(el);
		}
	},
	calXp : function(axp, rxp) {
		if(!axp || !rxp)return rxp;
		var arrR = (axp + '/' + rxp).split("/"), rst = [];
		for(var i=0; i<arrR.length; i++) {
			if(!arrR[i] || arrR[i]=='.')continue;
			if(arrR[i]!='..'){
				rst.push(arrR[i]);
				continue;
			}
			if(rst.length==0)return null;
			rst.pop();
		}
		return rst.join('/');
	}
};
exCenter.onafterTrans(function(){
	xExpression.renderEl(document.body);
});
exCenter.onafterModify(function(newEl){
	xExpression.renderEl(newEl);
});
/*
//扩展示例
xdMath.qiuhe = function(xp){
	var eles = EleHelper.getElesByTrsTmpId(xp);
	if(eles.length == 0)
		return 0;
	if(eles.length == 1)
		return parseInt(eles[0].value, 10) || 0;
	var rst = 0;
	for(var i=0; i<eles.length; i++)
		rst += parseInt(eles[i].value, 10) || 0;
	return rst;
}
exCenter.onafterTrans(function(){
	$('my:field2').setAttribute('trs_calc_expression', 'xdMath.qiuhe(my:group1/my:group2/my:field4)');
	xExpression.init($('my:field2'));
});
*/