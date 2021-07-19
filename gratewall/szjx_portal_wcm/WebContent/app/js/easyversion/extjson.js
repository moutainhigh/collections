//JSON转换
Ext.ns('Ext.Json', 'com.trs.util.JSON');
function parseXml(xml){
	var root = xml.documentElement;
	if(root == null) return null;
	var vReturn = {}, json = parseElement(root);
	vReturn[root.nodeName.toUpperCase()] = json;
	return vReturn;
}
function parseElement(ele){
	if(ele == null)return null;
	var json = {}, attrs = ele.attributes, hasAttr = false, hasValue = false, hasNode = false;
	for(var i=0;attrs && i<attrs.length;i++){
		hasAttr = true;
		json[attrs[i].nodeName.toUpperCase()] = attrs[i].nodeValue.trim();
	}
	var childs = ele.childNodes;
	for(var i=0;childs&&i<childs.length;i++){
		var ndn = childs[i].nodeName.toUpperCase();
		switch(ndn){
			case '#TEXT':
			case '#CDATA-SECTION':
				hasValue = true;
				json.NODEVALUE = childs[i].nodeValue.trim();
				break;
			case '#COMMENT':
				break;
			default:
				hasNode = true;
				var a = json[ndn], b = parseElement(childs[i]);
				if(!a)json[ndn] = b;
				else if(Array.isArray(a))a.push(b);
				else json[ndn] = [a, b];
		}
	}
	if(!hasAttr && !hasNode){
		if(!hasValue)return '';
		return json.NODEVALUE;
	}
	return json;
}
Ext.Json = com.trs.util.JSON = {
	toUpperCase : function(o){
		if(Ext.isEmpty(o) || Ext.isFunction(o))return "";
		if(Ext.isSimpType(o))return o;
		var fn = Ext.Json.toUpperCase, rst = {};
		if(Ext.isArray(o)) {
			rst = [];
			for(var i=0,n=o.length; i<n; i++){
				rst.push(fn(o[i]));
			}
			return rst;
		}
		for(var name in o){
			rst[name.toUpperCase()] = fn(o[name]);
		}
		return rst;
	},
	_json : function(json, xp, bCase){
		var rst = json, arrXp;
		if(!xp)return rst;
		try{
			xp = bCase ? xp.trim() : xp.trim().toUpperCase();
			arrXp = xp.split('.');
			for(var i=0; i<arrXp.length; i++){
				rst = rst[arrXp[i]];
			}
		}catch(err){
			return null;
		}
		return rst;
	},
	value : function(json, xp, bCase){
		var rst = Ext.Json._json(json, xp, bCase);
		return rst==null ? null : (rst['NODEVALUE'] != null ? rst['NODEVALUE'] : rst);
	},
	array : function(json, xp, bCase){
		var rst = Ext.Json._json(json, xp, bCase);
		return !rst ? [] : (Ext.isArray(rst) ? rst : [rst]);
	},
	eval : function(_sJson){
		try{
			eval("var json = " + _sJson);
			return Ext.Json.toUpperCase(json);
		}catch(err){
			Ext.Msg.d$error(err);
		}
	},
	parseXml : function(xml){
		return parseXml(xml);
	}
};
var $v = Ext.Json.value, $a = Ext.Json.array;