//控件助手
Array.prototype.xJoin = function(c, s, e){
	var rst = [];
	for(var i=s; i<=e; i++)rst.push(this[i]);
	return rst.join(c);
}
var EleHelper = {
	getFile : function(nd, bjson){
		var xct = nd.getAttribute("xd:xctname", 2);
		xct = xct ? xct.toLowerCase() : '';
		var arr = nd.childNodes;
		for(var i=0; i<arr.length; i++) {
			var c1 = arr[i];
			if(!c1.tagName)continue;
			if(xct == "fileattachment"){
				if(c1.getAttribute("text_file") == "1") {
					if(!c1.getAttribute("_fileDesc", 2))continue;
					if(bjson){
						return {
							FileDesc : c1.getAttribute("_fileDesc", 2),
							FileName : c1.getAttribute("_fileName", 2)
						}
					}
					return c1.getAttribute("_fileName", 2);
				}
			}
			else{
				if(c1.getAttribute("image_body") == "1") {
					if(!c1.getAttribute("_fileDesc", 2))continue;
					if(bjson){
						return {
							FileDesc : c1.getAttribute("_fileDesc", 2),
							FileName : c1.getAttribute("_fileName", 2)
						}
					}
					return c1.getAttribute("_fileName", 2);
				}
			}
		}
		return null;
	},
	setValue : function(nd, myv, info){
		var v = (typeof myv=='string') ? myv : XmlHelper._xvalue(myv);
		if(v == null)return false;
		var xp = nd.getAttribute("trs_temp_id", 2);
		var nElType = nd.getAttribute('element-type', 2);
		if(!xp || !nElType)return false;
		if(nElType==1 || nElType==2 || nElType==11 || nElType==101){
			nd.value = v;
		}
		else if(nElType==7){
			if(nd.tagName=='SPAN')nd.innerHTML = v;
			else if(nd.tagName=='IFRAME'){
				try{
					nd.contentWindow.document.body.innerHTML = v;
				}catch(err){}
			}
		}
		else if(nElType==8){
			nd.value = v;
			if(v!='' && nd.value==''){
				var a = document.createElement('OPTION');
				nd.appendChild(a);
				a.value = a.innerHTML = v;
				a.selected = true;
			}
			nd.value = v;
		}
		else if(nElType==10){
			if(nd.getAttribute("xd:onValue")==v)nd.checked = true;
		}
		else if(nElType==9){
			var sv = ',' + nd.getAttribute("xd:onValue") + ',';
			if((','+v+',').indexOf(sv)!=-1)nd.checked = true;
			else nd.checked = false;
		}
		else if(nElType==5){
			var xct = nd.getAttribute("xd:xctname", 2);
			xct = xct ? xct.toLowerCase() : '';
			var arr = nd.childNodes;
			for(var i=0; i<arr.length; i++) {
				var c1 = arr[i];
				if(!c1.tagName)continue;
				if(xct == "fileattachment"){
					if(c1.getAttribute("text_body") == "1") {
						c1.style.display = v ? "none" : "";
						continue;
					}
					if(c1.getAttribute("text_file") == "1") {
						c1.style.display = v ? "" : "none";
						var v2 = '';
						try{
							v2 = (info==null ? myv.getAttribute('FileName') : info.FileDesc) || v;	
						}catch(err){
							v2 = v;
						}
						c1.innerHTML = v2;
						c1.title = v;
						c1.setAttribute("_fileDesc", v2);
						c1.setAttribute("_fileName", v);
						continue;
					}
				}
				else{
					if(c1.getAttribute("image_body") == "1") {
						var v2 = '';
						try{
							v2 = (info==null ? myv.getAttribute('FileName') : info.FileDesc) || v;	
						}catch(err){
							v2 = v;
						}
						if(v != '')c1.src = m_readFileUrl + v;
						else c1.src =  m_sRootPath + 'spacer.gif';
						c1.title = v2;
						c1.style.display = v == null ? "none" : "";
						//c1.style.background = '';
						c1.setAttribute("_fileDesc", v2);
						c1.setAttribute("_fileName", v);
						continue;
					}
					c1.style.display = v ? "none" : "";
				}
			}
		}
		else if(this.isContainerEle(nd)){
			if(v!='')return;
			var arr = nd.getElementsByTagName('*');
			for(var i=0; i<arr.length;i++){
				var flag = arr[i].getAttribute('infodoc_data');
				if(!flag)continue;
				if(this.isContainerEle(arr[i]))continue;
				EleHelper.setValue(arr[i], v);
			}
		}
		return true;
	},
	getValue :  function(nd){
		var nElType = nd.getAttribute("element-type", 2);
		if(nElType==1 || nElType==2 || nElType==11 || nElType==8 || nElType==101){
			return nd.value;
		}
		if(nElType==7){
			if(nd.tagName=='SPAN')return nd.innerHTML;
			if(nd.tagName=='IFRAME'){
				try{
					return nd.contentWindow.document.body.innerHTML;
				}catch(err){
					return '';
				}
			}
		}
		if(nElType==10 || nElType==9){
			var rst = nd.checked ? nd.getAttribute("xd:onValue") : nd.getAttribute("xd:offValue");
			return rst==null ? '' : rst;
		}
		else if(nElType==5){
			var file = this.getFile(nd, true) || {};
			var xct = nd.getAttribute("xd:xctname", 2).toLowerCase();
			var flag = 'trs_is_inline_file';
			if(xct=='inlineimage')
				flag = 'trs_is_inline_image';
			else if(xct=='linkedimage')
				flag = 'trs_is_linked_image';
			var rst = {};
			if(file.FileName){
				rst[flag] = 1;
			}else{
				rst['blank_node'] = 1;
			}
			rst.nodeValue = file.FileName || '';
			rst.FileName = file.FileDesc || '';
			return rst;
		}
		return null;
	},
	setDefaultValue : function(nd){
		var v = nd.getAttribute('default_value', 2) || "";
		var nElType = nd.getAttribute("element-type", 2);
		switch(nElType){
			case "1":
			case "2":
			case "11":
			case "8":
			case "101":
				nd.value = v;
				break;
			case "7":
				var dom = null;
				if(nd.tagName=='SPAN'){
					dom = nd;
				}else if(nd.tagName=='IFRAME'){
					dom = nd.contentWindow.document.body;
				}
				if(dom) dom.innerHTML = v;
				break;
			case "9":
			case "10":
				var sv = "," + nd.getAttribute("xd:onValue") + ",";
				nd.checked = ("," + v + ",").indexOf(sv) >= 0;
				break;
		}
	},
	getValueFields : function(){
		var arr = document.body.getElementsByTagName('*');
		var rst = [];
		for(var i=0, n=arr.length; i<n; i++){
			var flag = arr[i].getAttribute('infodoc_data');
			if(!flag)continue;
			rst.push(arr[i]);
		}
		return rst;
	},
	getElesByTrsTmpId : function(arrTrsTmpId, cntEl){
		cntEl = cntEl || document.body;
		var xps = Array.isArray(arrTrsTmpId) ? arrTrsTmpId.join(',') : arrTrsTmpId;
		xps = ',' + xps + ',';
		var rst = [], tags = ['input', 'select', 'textarea'];
		for(var k=0, nK=tags.length; k<nK; k++){
			var arr = cntEl.getElementsByTagName(tags[k]);
			for(var i=0, n=arr.length; i<n; i++){
				var flag = arr[i].getAttribute('trs_temp_id');
				if(!flag || xps.indexOf(','+flag+',')==-1)continue;
				rst.push(arr[i]);
			}
		}
		return rst;
	},
	isRepeatNode : function(xp){
		var el = $(xp);
		if(!el)return false;
		return this.isRepeatEle(el);
	},
	isRepeatEle : function(nd){
		var type = nd.getAttribute("element-type", 2);
		return type==3 || type==4;
	},
	isContainerEle : function(nd){
		var type = nd.getAttribute("element-type", 2);
		return type==3 || type==13 || type==4;
	},
	isfileattachmentEle : function(nd){
		var type = nd.getAttribute("element-type", 2);
		return type==5;
	},
	jsonData :  function() {
		var els = this.getValueFields();
		var vData = {}, attrs = [];
		for(var i=0; i<els.length; i++){
			var ele = els[i], v = this.getValue(ele);
			if(v==null)continue;
			
			//单选和多选的处理
			var eleTag = ele.tagName;
			var eleType = ele.type ? ele.type.toLowerCase() : '';
			if(eleType =='radio' && v == '')continue;//单选的值为空，则返回
			
			var eleName = ele.getAttribute('trs_temp_id', 2);
			var tmpFor = vData[eleName]; //js是没有{}级作用域的,所以在for循环中定义的tmpFor变量在for循环外也可以使用
			if(tmpFor == undefined){
				//在组织的数据中未找到同名的，则将其key和value存放起来
				vData[eleName] = v;
				continue;
			}
			//如果已经存在同key的值，则如果不是数组，则创建出一个数组，将这个数组作为已经组织好的数据
			if(!Array.isArray(tmpFor)){
				tmpFor = vData[eleName] = [tmpFor];
			}
			
			tmpFor.push(v);//将当前元素的值放到已经组织好的数组中
			if(eleTag=='INPUT' && eleType=='checkbox'){
				vData[eleName] = tmpFor.join(',');//多选情况下，将值用逗号连接起来
			}
		}//以上组织的结果是：元素的key和value都存放到vData中，同名的元素，其值是一个数组
		
		var rst = {}, arrName, tmpRst, tmp, tmp2; //由于for循环中也定义了tmp变量
		for(var name in vData){
			tmpRst = rst;
			arrName = name.split('/');
			var i=0, n = arrName.length-1;
			for(; i<n; i++){
				if(this.isRepeatNode(arrName.xJoin('/', 0, i))){
					//重复表情况，初始化数组
					tmpRst[arrName[i]] = tmpRst[arrName[i]] || [];
					tmpRst = tmpRst[arrName[i]];//最终tmpRst表示当前name元素的结果，是一个数组
					break;
				}
				//到此，tmpRst为当前元素的结果
				tmp = tmpRst[arrName[i]];
				if(!tmp){//如果结果中还没有当前元素的key
					tmp = tmpRst[arrName[i]] = {};
				}
				tmpRst = tmp;//是一个空json
			}
			//到此的结果是:tmpRst为当前元素的一个结果类型，重复表为[]，其他为{}
			var v = vData[name];
			if(Array.isArray(tmpRst)){
				tmp = null;
				v = Array.isArray(v) ? v : [v];//将当前值也组织为一个数组
				for(var k=0, mK=v.length; k<mK; k++){
					var j = i+1;
					tmp2 = tmpRst[k] = tmpRst[k] || {};
					for(;j<n;j++){
						tmp = tmp2[arrName[j]] = tmp2[arrName[j]] || {};
						tmp2 = tmp;
					}
					var el = document.getElementById(name);
					if(this.isfileattachmentEle(el)){
						tmp2[arrName[n]] = v; 
						break;
					}
					else{
						tmp2[arrName[n]] = v[k];
					}
				}
			}else{
				tmpRst[arrName[n]] = v;
			}
		}
		return rst;
	},
	jsonIntoEle : function(xmlDoc, parent, json){
		if(json==null || typeof json!='object')return;
		if(Array.isArray(json)){
			for(var i=0,n=json.length;i<n;i++){
				var newEle = parent;
				var value = json[i];
				if(i!=0 && value!=''){
					newEle = xmlDoc.createElement(parent.nodeName);
					parent.parentNode.appendChild(newEle);
				}
				if(typeof value=='object'){
					if(value.nodeValue==null)newEle.setAttribute('complex_field', 1);
					this.jsonIntoEle(xmlDoc, newEle, value);
					continue;
				}
				if(value!="")
				newEle.appendChild(xmlDoc.createCDATASection(value));
			}
			return;
		}
		var bAttrNode = json.nodeValue!=null;
		if(bAttrNode){
			for(var name in json){
				var value = json[name];
				if(name!='nodeValue'){
					parent.setAttribute(name, value);
					continue;
				}
				parent.appendChild(xmlDoc.createCDATASection(value));
			}
			return;
		}
		for(var name in json){
			var value = json[name];
			var newEle = xmlDoc.createElement(name);
			parent.appendChild(newEle);
			if(typeof value=='object'){
				this.jsonIntoEle(xmlDoc, newEle, value);
				continue;
			}
			newEle.appendChild(xmlDoc.createCDATASection(value));
		}
	}
}