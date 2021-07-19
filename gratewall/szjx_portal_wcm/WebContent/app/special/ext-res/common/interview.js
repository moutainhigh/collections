/**
*本文件处理设计页面中对访谈资源块的扩展处理以及页面加载后，对访谈资源的初始化操作。
*/
var eventTypes = ['afteradd', 'afteredit', 'afterrefresh','aftermove'];
var m_oJBFTConfigs = {
	listOrder				: "desc"			//排序方式
}
var pageIndex = 1;
var meta;
var m_oJBFTTemplate = {
	topic : [
		'<div class="record {4}" id="{0}">',
			'<div class="speaker">{1}:</div>',
			'<div class="posttime">{2}</div>',
			'<div class="content">{3}</div>',
		'</div>{5}'
	].join(''),
	post : [
		'<div class="postrecord" id="{0}">',
			'<div class="postspeaker">{1}:</div>',
			'<div class="postposttime">{2}</div>',
			'<div class="postcontent">{3}</div>',
		'</div>'
	].join(''),
	blank : '<div id="divNoObjectFound"><div></div></div>',
	nav : {
		total :[
			'<span class="nav">',
				'\u5171<span class="navtotalnum">{0}</span>\u4e2a\u95ee\u9898,',
				'\u6bcf\u9875<span class="navpagecount">{1}</span>\u4e2a&nbsp;&nbsp;',
				'{2}',
			'</span>'
		].join(''),
		curr :[
			'<span class="navcurr">{0}</span>&nbsp;&nbsp;'
		].join(''),
		page :[
			'<span class="navpage" pageindex="{0}">{0}</span>&nbsp;&nbsp;'
		].join('')
	},
	notice : {
		main : [
			 '<ul border="0" width="100%">{0}</ul>'
		].join(''),
		item : '<li class="noticeitem" nowrap="true">{0}({1})&nbsp;&nbsp;&nbsp;&nbsp;</li>',
		list :[
			'<ol border="0" width="100%">{0}</ol>'
		].join(''),
		listItem : '<li class="{2}" nowrap="true">{0}({1})&nbsp;&nbsp;&nbsp;&nbsp;</li>'
	},
	vip : {
		main : [
			'<select name="to" class="dabianselect" id="to">',
				'<option value="-1">\u4e0d\u9009\u62e9\u56de\u7b54\u4eba</option>',
				'{0}',
			'</select>'                                             
		].join(''),
		item : '<option value="{0}">{0}</option>',
		desc : [
			   '<div><span class="jbft_desc">主题: </span><span class="jbft_content">{0}</span></div>',
			   '<div><span class="jbft_desc">开始时间: </span><span class="jbft_content">{1}</span></div>',
			   '<div><span class="jbft_desc">结束时间: </span><span class="jbft_content">{2}</span></div>',		   
			   '<div><span class="jbft_desc">简 介: </span><span class="jbft_content">{3}</span></div>'
			].join(''),
		comments_1 : [
					'<DIV style="float:left;"  class="c_picTxt">',
					'<div class="c_pic_1"><img src="{4}" width=\'{2}\' height=\'{3}\' class=\'img\'></a></div>',
					'<div class="c_txt_1"><h5 style="line-height:23px;"><a class="c_title" title="{0}" target="_blank">{0}</a></h5>',
					'<p class="c_memo">{1}{5}</p></div></DIV><div style="height:10px;clear:both;">&nbsp;</div>'
				].join(''),
		comments_2 : [
					'<DIV style="float:left;"  class="c_picTxt">',
					'<div style="float:right;"><img src="{4}" width=\'{2}\' height=\'{3}\' class=\'img\'></a></div>',
					'<div class="c_txt_2"><h5 style="line-height:23px;"><a class="c_title" title="{0}" target="_blank">{0}</a></h5>',
					'<p class="c_memo">{1}{5}</p></div></DIV><div style="height:10px;clear:both;">&nbsp;</div>'
				].join(''),
		comments_3 : [
					'<DIV style="float:left;"  class="c_picTxt">',
					'<div class="c_pic_3"><img src="{4}" width=\'{2}\' height=\'{3}\' class=\'img\'></a></div>',
					'<div class="c_txt"><h5 style="line-height:23px;"><a class="c_title" title="{0}" target="_blank">{0}</a></h5>',
					'<p class="c_memo">{1}{5}</p></div></DIV><div style="height:10px;clear:both;">&nbsp;</div>'
				].join(''),
		comments_4 : [
					'<DIV style="float:left;"  class="c_picTxt">',
					'<div class="c_txt"><h5 style="line-height:23px;"><a class="c_title" title="{0}" target="_blank">{0}</a></h5>',
					'<p class="c_memo">{1}{5}</p></div>',
					'<div class="c_pic_4"><img src="{4}" width=\'{2}\' height=\'{3}\' class=\'img\'></a></div>',
					'</DIV><div style="height:10px;clear:both;">&nbsp;</div>'
				].join('')
	},
	piclist : {
		list_1	: [
				'<div class="c_pic_list" style="display:block;">',
				'<div class="c_pic" style="margin:0 auto;"><div><a href="{0}" target="_blank"><img src="{1}" width="60px" height="60px"/></a></div>',
				'<div class="c_text"><a href="' + '" target="_blank" title=\'22\'>{2}</a></div></div></div>'
			].join('')
	}
};

/*=================================Interview Cotent Start=============================*/
(function(){
	Event.observe(window, 'load', function(event){
		var doms = document.getElementsByClassName("c_interview_list");
		for(var i = 0; i < doms.length; i++){
			var dom = doms[i];
			dom.id = dom.id || genExtId();
			//增加有效性的校验
			var value = Element.next(dom).value;
			value = String.format(value, pageIndex)
			+ '?r=' + new Date().getTime();
			doAction(value,1);
		}
		refreshContent(true);
	});
})();
/*=================================Interview Content End=============================*/

/*=================================Interview Introduce Start=============================*/
(function(){
	Event.observe(window, 'load', function(event){
		var doms = document.getElementsByClassName("c_interview_introduce");
		for(var i = 0; i < doms.length; i++){
			var dom = doms[i];
			dom.id = dom.id || genExtId();
			//增加有效性的校验
			var value = Element.next(dom).value;
			doAction(value,2);
		}
	});
})();
/*=================================Interview Introduce End=============================*/


/*=================================Interview JBIntroduce Start=============================*/
(function(){
	Event.observe(window, 'load', function(event){
		var doms = document.getElementsByClassName("c_interview_jbintroduce");
		for(var i = 0; i < doms.length; i++){
			var dom = doms[i];
			dom.id = dom.id || genExtId();
			//增加有效性的校验
			var value = Element.next(dom).value;
			doAction(value,2);
		}
	});
})();
/*=================================Interview JBIntroduce End=============================*/


/*=================================Interview PhotoList Start=============================*/
(function(){
	Event.observe(window, 'load', function(event){
		var doms = document.getElementsByClassName("c_interview-piclist");
		for(var i = 0; i < doms.length; i++){
			var dom = doms[i];
			dom.id = dom.id || genExtId();
			//增加有效性的校验
			var value = Element.next(dom).value;
			if(existURL(value) != ""){
				var data = eval("("+value+")");
				data.container = dom.id;
				showPicList(data);
			}
		}
	});
})();
/*=================================Interview PhotoList End=============================*/

/*=================================Interview PostQuestion Start=============================*/
(function(){
	Event.observe(window, 'load', function(event){
		var doms = document.getElementsByClassName("c_interview_postQuestion");
		for(var i = 0; i < doms.length; i++){
			var dom = doms[i];
			dom.id = dom.id || genExtId();
			//增加有效性的校验
			var value = Element.next(dom).value;
			if(existURL(value) != ""){
				var data = eval("("+value+")");
				postActionAttach(data);
			}
		}
	});
})();
/*=================================Interview PostQuestion End=============================*/
function doAction(data,flag){
	var head = document.getElementsByTagName("head")[0]; 
	//动态创建script节点
	var script = document.createElement("script"); 
	script.type = "text/javascript";
	script.src = data;
	head.appendChild(script); 
	//回调函数
	script.onload = script.onreadystatechange = function(){ 
		if (!this.readyState || this.readyState == "loaded" || this.readyState == "complete") { 
			//JS加载完毕了.
			head.removeChild(script);
			switch (flag){
				case 1:
					initEvent();
					break;
			}			
		} 
	}
}

function _parseData(jsonData, inner){
	if(!inner){
		return String.format(m_oJBFTTemplate.topic,
			jsonData.pt, jsonData.un, jsonData.pt,
			jsonData.c,
			jsonData.index % 2 == 1 ? 'even' : 'odd',
			_parseData(jsonData.a, true)
		);
	}
	var result = [];
	var arrAnswer = jsonData;
	if(!arrAnswer)return "";
	for(var i=0, n=arrAnswer.length; i<n; i++){
		var answer = arrAnswer[i];
		result.push(String.format(m_oJBFTTemplate.post,
			answer.pt, answer.un, answer.pt,
			answer.c)
		);
	}
	return result.join('');
}
function initEvent(){
		var fillQuestionDom = $('fillQuestion');
		if(fillQuestionDom && $('submitA')){
			Event.observe(fillQuestionDom, 'click', function(){
				$('submitA').scrollIntoView(false);
				$('content').focus();
				$('content').select();
			}.bind(this));
		}
	var rset = $('reset');
	if(rset){
		Event.observe(rset, 'click', function(){
			var eles = document.getElementsByClassName("dabiankuang");
			for(var i=0; i<eles.length; i++){
				if(eles[i]) eles[i].value = "";
			}
		}.bind(this));
	}
	var ascOrderDom = $('ascOrderId');
	if(ascOrderDom){
		Event.observe(ascOrderDom, 'click', function(){
			m_oJBFTConfigs.listOrder = 'asc';
			if(json)window.jbftObj.renderList(json);
		}.bind(this));
	}
	var descOrderDom = $('descOrderId');
	if(descOrderDom){
		Event.observe(descOrderDom, 'click', function(){
			m_oJBFTConfigs.listOrder = 'desc';
			if(json)window.jbftObj.renderList(json);
		}.bind(this));
	}
	var refresh = $('refreshId');
	if(refresh){
		Event.observe(refresh, 'click', function(){
			var bChecked = true;
			var refreshAuto = $('chkz');
			if(!refreshAuto.checked) bChecked = false;
			refreshContent();
			var refreshAuto = $('chkz');
			if(!bChecked) refreshAuto.checked = false;
		}.bind(this));
	}
	var refreshAuto = $('chkz');
	if(refreshAuto){
		Event.observe(refreshAuto, 'click', function(){
			if(refreshAuto.checked){
				refreshContent(true);
			}else{
				if(meta)clearTimeout(meta);
			}
		}.bind(this));
	}

	//指定刷新频率
	var interval = 2;
	var tempEle = document.getElementById("interval");
	if(tempEle) interval = tempEle.value;
	if($("scrollt")) $("scrollt").selectedIndex = interval - 1;
}

function refreshContent(bLoop){
	if(!$("jbft_content")) return;
	$("jbft_content").innerHTML = '';
	var doms = document.getElementsByClassName("c_interview_list");
	for(var i = 0; i < doms.length; i++){
		var dom = doms[i];
		dom.id = dom.id || genExtId();
		//增加有效性的校验
		var value = Element.next(dom).value;
		value = String.format(value, pageIndex)
		+ '?r=' + new Date().getTime();
		doActionNotCallBack(value,1);
	}
	var ele = $("scrollt").value;
	var bFresh = $("chkz").checked;
	if(bFresh && bLoop){
		meta = setTimeout("refreshContent(true)",ele);
	}
}
function doActionNotCallBack(data,flag){
	var head = document.getElementsByTagName("head")[0]; 
	//动态创建script节点
	var script = document.createElement("script"); 
	script.type = "text/javascript";
	script.src = data;
	head.appendChild(script); 
	//回调函数
	script.onload = script.onreadystatechange = function(){ 
		if (!this.readyState || this.readyState == "loaded" || this.readyState == "complete") { 
			//JS加载完毕了.
			head.removeChild(script);		
		} 
	}
}

window.jbftObj = {
	//输出访谈介绍信息
	renderVip : function(arrJson){
		if(!arrJson)return;
		//初始化提问者
		if($('vipsel')){
			var temp = arrJson["Comments"];
			var result = [];
			for(var i=0, n=temp.length; i<n; i++){
				var json = temp[i];
				result.push(String.format(m_oJBFTTemplate.vip.item,
					json["CommentName"])
				);
			}
			$('vipsel').innerHTML = 
				String.format(m_oJBFTTemplate.vip.main,
					result.join('')
				);
		}

		//访谈介绍
		var elements = document.getElementsByClassName("jbft_introduce");
		if(elements == null) return;
		for(var i=0; i < elements.length; i++){
			if(elements[i].innerHTML.trim() == ""){
				elements[i].innerHTML = String.format(m_oJBFTTemplate.vip.desc,
					arrJson["Name"],arrJson["StartTime"],arrJson["EndTime"],arrJson["Desc"]
				);
				break;
			}
		}

		//访谈嘉宾介绍
		var elements = document.getElementsByClassName("jbft_jbintroduce");
		if(elements == null) return;
		var img_width = document.getElementById("jb_width");
		if(img_width) img_width = img_width.value;
		var img_height = document.getElementById("jb_height");
		if(img_height) img_height = img_height.value;
		var iDirect = 1;
		var img_derection = document.getElementById("jb_direct");
		if(img_derection) iDirect = img_derection.value;
        
		var urls = document.getElementsByClassName("jb_url");
		var bShowMore = 1;
		var showEle = document.getElementById("jb_detail");
		if(showEle) bShowMore = showEle.value;
		for(var i=0; i < elements.length; i++){
			if(elements[i].innerHTML.trim() == ""){
				var temp = arrJson["Comments"];
				var result = [];
				var sExtra = "";
				for(var k=0, n=temp.length; k<n; k++){
					var json = temp[k];
					if(bShowMore > 0 && urls[k]){
						sExtra = "&nbsp;&nbsp;<a href='" + urls[k].value + "' target='_blank' class='extra'>>>></a>";
					}
					elements[i].innerHTML = elements[i].innerHTML + String.format(m_oJBFTTemplate.vip["comments_" + iDirect],
						json["CommentName"],json["CommentDesc"],img_width,img_height,json["CommentPic"],sExtra
					);
				}			
				break;
			}
		}
	},
	//刷新访谈文字直播列表
	renderList : function(arrData){
		var result = [];
		if(m_oJBFTConfigs.listOrder == 'desc'){
			for(var i=0, n=arrData.length; i<n; i++){
				var jsonData = arrData[i];
				jsonData.index = i;
				result.push(_parseData(jsonData));
			}
		}else{
			for(var i=arrData.length-1; i>=0; i--){
				var jsonData = arrData[i];
				jsonData.index = arrData.length-1-i;
				result.push(_parseData(jsonData));
			}
		}
		if($("jbft_content")){
			$("jbft_content").innerHTML = result.join('');
		}
	}
}

function existURL(_sParam){
  var preSub = _sParam.split(",")[0].trim();
  var index = preSub.indexOf(":");
  var url = preSub.substring(index,preSub.length-1);
  return url;
}

//访谈问答资源块form提交构造
function postActionAttach(data){
	doAction(data["data"],2);
	var formPost = $('frmPost');
	formPost.id.value = data.id;
	formPost.action = data.url + 'wcmonline/ajaxservice/postviaform.jsp';
	Event.observe($("submitA"), 'click', postAQuestion);
	Event.observe($("content"), "keypress", function(){	
		if(event.ctrlKey&&event.keyCode==10){
			postAQuestion();
		}
	});	
}

function postAQuestion(){
	var formPost = $('frmPost');
	var content = formPost.title.value;
	if(content == null || content.length==0){
		alert("请输入提问内容！");
		return false;
	}
	content = content.replace(/<\/?[^><]*>/g,'');
	var postername = formPost.myname.value;
	if(postername.length > 10 || (postername.length < 2 && postername.length > 0)){
		alert("你的名字不符合要求,应该由2-10个字符组成！");
		return false;
	}else if(postername.length == 0){
		postername = "网友";
	}else{
		var exp = new RegExp("^([a-z]|[A-Z]|[\u4E00-\u9FA5])([0-9]|[a-z]|[A-Z]|[\u4E00-\u9FA5])*([0-9]|[a-z]|[A-Z]|[\u4E00-\u9FA5])$","g");
		if(!exp.test(postername)){						
			alert("发言人只能以汉字或字母开头；且只能由汉字，字母及数字组成:"+postername);
			formPost.myname.focus();
			return false;
		}
		postername = postername.replace(/<\/?[^><]*>/g,'');
	}

	formPost.title.value = content;
	formPost.myname.value = postername;
	formPost.submit();	
	$("content").value = "";
	return false;
}

//访谈图片直播资源展示
function existURL(_sParam){
  var preSub = _sParam.split(",")[0].trim();
  var index = preSub.indexOf(":");
  var url = preSub.substring(index,preSub.length-1);
  return url;
}

function showPicList(data){
	var container = document.getElementById("jbft_photoList");
	if(container == null || !(data && data.url)) return;
	var src = data["url"] + "/app/interview/getPhotoList.jsp";
	src += "?channelId=" + data["channelId"] + "&showTitle=" + data["showTitle"] + "&perNum=" + data["perNum"] + "&totalNum=" + data["totalNum"] + "&width=" + data["width"] + "&height=" + data["height"];
	container.innerHTML = "<iframe id=\"inter_pic_list\" FRAMEBORDER=0 src=\"" + src + "\" style=\"overflow:hidden;width:100%;height:" +data["widgetHeight"] + "px;\"></iframe>";
	setInterval("showPicList1()",data["interval"]*60000);
}

function showPicList1(interval){
	var ele = document.getElementById("inter_pic_list");
	if(!ele) return;
	ele.contentWindow.location.reload();
}