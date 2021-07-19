/*条件：页面中存在一个id为 process_mask_iframe 的iframe*/
function $removeNode(_node){
	if(_node){
		var childs = _node.childNodes;
		for(var i=childs.length-1;i>=0;i--){
			$removeNode(childs[i]);
		}
		childs = [];
		if(_node.parentNode){
			_node.parentNode.removeChild(_node);
		}
		delete _node;
	}
}
//
var nLeft=0;
var nTop=0;
ProcessBar={
	IFRAME_MASK_ID:"process_mask_iframe",
	PROCESSBAR_ID : "processbar_div",
	title:"进度执行中，请稍候…",
	info:"请稍候……",
	init : function(_time,eTop,eLeft){
		nLeft = eLeft;
		nTop=eTop;
		this.width=260;
		var eDiv = document.getElementById(this.PROCESSBAR_ID);
		if(eDiv==null){
			var struction = '<div id="processbar_header" class="process_header">'
								+ '<div class="process_header_title">' + this.title + '</div>'
							+'</div>'
							+'<div class="process_body">'
								+'<div class="processbar_gundong"></div>'
								+'<div class="processbar_force_close">如果长时间无法响应，请<a class="force_a" href="#" onclick="ProcessBar.close();return false;">点击关闭</a></div>'
							+'</div>';

			eDiv = document.createElement('DIV');
			eDiv.id=this.PROCESSBAR_ID;
			eDiv.className="process_container";
			document.body.appendChild(eDiv);
			$(eDiv).html(struction);
		}
		$.style(eDiv,"width",this.width);

		//计算left
		//使用传过来的定位参数
		if(eTop > 0 && eLeft > 0){
			$.style(eDiv,"left",eLeft);
			$.style(eDiv,"top",eTop);
		}else{
			var actualTop = window.top || window;
			var nDivLeft = $(actualTop).width();
			nDivLeft = nDivLeft - parseInt(eDiv.offsetWidth);
			nDivLeft = nDivLeft/2;
			nDivLeft = nDivLeft + $(document).scrollLeft();
			$.style(eDiv,"left",nDivLeft);
			
			//计算Top
			var nDivTop = $(actualTop).height();
			nDivTop = nDivTop - parseInt(eDiv.offsetHeight);
			nDivTop = nDivTop/2;
			nDivTop = nDivTop + $(document).scrollTop();
			$.style(eDiv,"top",nDivTop);
		}
	},
	showIframe : function(){
		var eIframe = document.getElementById(this.IFRAME_MASK_ID);
		if(eIframe){
			this.bodyWidth = document.body.offsetWidth;
			this.bodyHeight = document.body.offsetHeight||document.body.scrollHeight;
			eIframe.className="process_cover_iframe";
			//eIframe.style.width=this.bodyWidth;
			/*$.style(eIframe,"width",this.bodyWidth);
			//eIframe.style.height=this.bodyHeight;
			$.style(eIframe,"height",this.bodyHeight);*/
			$.style(eIframe,"top",0);
			$.style(eIframe,"left",0);
			$.style(eIframe,"backgroundColor","#A9A9A9");
			//eIframe.style.top	= 0;
			//eIframe.style.left	= 0;
			eIframe.style.display='';
			//eIframe.style.zIndex = $MsgCenter.genId(10000);
			eIframe.style.zIndex = 10000;//不用再计算，默认值为10000
			document.getElementById(this.PROCESSBAR_ID).style.zIndex = eIframe.style.zIndex+1;
		}
	},
	hiddenIframe:function(){
		try{
			var eIframe = document.getElementById(this.IFRAME_MASK_ID);
			eIframe.className="";
			eIframe.style.display='none';
		}catch(e){}
	},
	setInfo : function(_sInfo){
		if((_sInfo!=null) && (_sInfo!="")){
			$('processbar_info').html(_sInfo);
		};
	},
	start : function(_sInfo,_time){
		//alert();
		this.TIME = 100;
		if(_time&&!isNaN(_time)&&_time>100){this.TIME=_time;}
		this.open(_sInfo);
	},
	next : function(_sInfo){
		this.setInfo(_sInfo);
	},
	exit : function(){
		//IE8 不支持所以注销
		//setTimeout(function(){
			this.close();
		//}.bind(this),this.TIME);
	},
	addState : function(_sState){
		$('processbar').contentWindow.addState(_sState);
	},
	open : function(_sInfo){
		//1,show iframe
		this.showIframe();
		//2 show processor
		//this.init();
		// set info
		this.setInfo(_sInfo);
	},
	close : function(){
		// del processor
		var eDiv = document.getElementById("processbar_div");
		$removeNode(eDiv);
		//del iframe
		this.hiddenIframe();
	}
}
/*测试
setTimeout(function(){
		com.trs.portal.ProcessBar.start("别理我，烦着呢！");
		setTimeout(function(){
			com.trs.portal.ProcessBar.next("我不烦，你比较烦。");
			setTimeout(function(){
				com.trs.portal.ProcessBar.exit();
			},2000)
		},1000)
	},1000)
*/