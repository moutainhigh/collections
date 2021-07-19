function parseXml(xml){
	var root = xml.documentElement;
	if(root == null) return null;
	var vReturn = {}, json = parseElement(root);
	vReturn[root.nodeName] = json;
	return vReturn;
}
function parseElement(ele){
	if(ele == null)return null;
	var json = {}, attrs = ele.attributes, hasValue = false;
	for(var i=0;attrs && i<attrs.length;i++){
		hasValue = true;
		json[attrs[i].nodeName] = attrs[i].nodeValue.trim();
	}
	var childs = ele.childNodes;
	for(var i=0;childs&&i<childs.length;i++){
		var ndn = childs[i].nodeName.toUpperCase();
		switch(ndn){
			case '#TEXT':
			case '#CDATA-SECTION':
				if(!hasValue)return childs[i].nodeValue.trim();
				hasValue = true;
				json['NODEVALUE'] = childs[i].nodeValue.trim();
				break;
			case '#COMMENT':
				break;
			default:
				hasValue = true;
				var a = json[ndn], b = parseElement(childs[i]);
				if(!a)json[ndn] = b;
				else if(Array.isArray(a))a.push(b);
				else json[ndn] = [a, b];
		}
	}
	if(!hasValue)return '';
	return json;
}
