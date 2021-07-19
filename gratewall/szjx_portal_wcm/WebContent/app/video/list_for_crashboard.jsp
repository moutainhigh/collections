
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.Documents"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc"%>
<%@ page import="com.trs.components.video.VideoDocUtil" %>
<%@ page import="com.trs.components.video.persistent.XVideo" %>
<%@ page import="com.trs.components.video.VSConfig"%>


<%@include file="../include/public_server.jsp"%>

<%
	String chnId = request.getParameter("ChannelId");
	String sDocIds = request.getParameter("ObjectIds");
	Documents docs = Documents.findByIds(null,sDocIds);
	//XVideo[] docs = VideoDocUtil.findDocsWithDownloadLinks(loginUser, sDocIds);
	//String thumbRoot = VSConfig.getThumbsHomeUrl();
	out.clear();
%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">

<%@page import="com.trs.components.wcm.content.persistent.Channel"%><html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title> 视频 </title>
  <meta name="Author" content="CH">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/locale/cn.js" WCMAnt:locale="../js/locale/$locale$.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>

<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />

<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
	<style>
		body{
			padding:0;
			margin:0;
			font-size:12px;
		}
		table{
			width:100%;
			border-collapse:collapse;
		}
		.dragel td{
			background:yellow;
		}
		.tb th{
			font-size:14px;
			background:#D3E6FA;
		}
		.dragel td,.tb td,.tb th{
			height:24px;
			line-height:24px;
			border:1px solid #646464;
			text-align:center;
		}
	</style>
	<script type="text/javascript">

	Ext.apply(Element, {
		find : function(t, attr, cls, aPAttr){
			aPAttr = aPAttr || [];
			while(t!=null&&t.tagName!='BODY'&& t.nodeType==1){
				for (var i = 0; i < aPAttr.length; i++){
					if(dom.getAttribute(aPAttr[i]) != null) return 0;
				}
				if(cls && Element.hasClassName(t, cls))return t;
				if(attr && t.getAttribute(attr, 2)!=null)return t;
				t = t.parentNode;
			}
			return null;
		}
	});

		window.m_cbCfg = {
			btns : [
				{
					text : '确定',
					cmd : function(){
						this.notify(getDocIds());
					}
				}
			]
		};
		function getDocIds(){
			var docids = [];
			var trEls = document.getElementsByTagName("TR");
			for (var i=0;i< trEls.length; i++)
			{
				var docid = trEls[i].getAttribute("docid");
				if(docid)
					docids.push(docid);
			}
			return docids.join(",");
		}
		function upload(){
			new wcm.CrashBoard({
			id:"videos_upload",
			src:"./video_batch_add1.jsp",
			title:"批量上传视频",
			top:"0px",
			width:'900px',
			height:"400px",
            maskable : true,
            params : {
                    ChannelId : <%=chnId%>
            },
			callback:function(ids){
				window.location.href="list_for_crashboard.jsp?ChannelId="+<%=chnId%>+"&ObjectIds="+getDocIds()+","+ids;
			}
		}).show();
		}

		function getHelper(){
			return new com.trs.web2frame.BasicDataHelper();
		}
		function deleteVideo(id){
			getHelper().call('wcm61_video', 'deleteVideos',
					{"ObjectIds": id, "drop": true,"SiteId":0,"ChannelId":<%=chnId%>}, true,function(){
			});
			Element.remove($("chnldocid_"+id));
		}
		var bSuccess =true;
		
		Event.observe(window,"load",function(){
			Dragger.init();
			if(bSuccess)return;
			$("iframe").src="/wcm/app/video/video_thumb.html?ChannelId=<%=chnId%>";
			 setInterval(function(){
				location.href="list_for_crashboard.jsp?ChannelId="+<%=chnId%>+"&ObjectIds="+getDocIds;
			},5000);
		});
		Event.observe(window,"unload",function(){
			//clearInterval(intever);
		});

function refreshIndex(){
	var trEls = document.getElementsByTagName("TR");
	for (var i=0,j=0;i< trEls.length; i++){
		if(trEls[i].getAttribute("docid")){
			Element.first(trEls[i]).innerHTML = ++j;
		}
	}
}
var getTargets = function(dom){
	var doms = [];
	var trs = dom.getElementsByTagName('TR');
	for(var index = 0; index < trs.length; index++){
		if(!trs[index].getAttribute("docid"))continue;
		doms.push(trs[index]);
	}
	return doms;
}
var inElement = function(x, y, element){
	var page = Position.cumulativeOffset(element);
	var scrollEl = document.body;
	page = [page[0]-scrollEl.scrollLeft,page[1]-scrollEl.scrollTop,]
	var width = parseInt(element.offsetWidth, 10);
	var height = parseInt(element.offsetHeight, 10);
	return x> page[0] && x<page[0] + width && y > page[1] && y < page[1] + height;
}
var Dragger = window.Dragger || {};
Ext.apply(Dragger, {
	init : function(){
		Ext.EventManager.on(document, 'mousedown', this.handleMouseDown, this);
	},
	findDragEl : function(e){
		e = window.event || e;
		var dom = Event.element(e);
		//if(dom.tagName=="INPUT") return null;
		return Element.find(dom,"docid","");
	},
	getXY : function(e){
		return [parseInt(e.getPageX(), 10), parseInt(e.getPageY(), 10)];
	},
	handleMouseDown : function(e){
		//获取当前单击的元素是否为需要拖动的元素，如果不是，则直接退出
		this.dragEl = this.findDragEl(e);
		if(!this.dragEl) return;

		//重置拖动状态，以便重新执行onDragStart接口
		this.dragging = false;

		//记录拖动的开始位置及偏移量
		var xy = this.getXY(e);
		this.lastPointer = xy;
		var page = Position.cumulativeOffset(this.dragEl);
		// 需要去除滚动条高度
		var scrollEl = document.body;
		this.deltaX = xy[0] - parseInt(page[0]-scrollEl.scrollLeft, 10);
		this.deltaY = xy[1] - parseInt(page[1]-scrollEl.scrollTop, 10);
		//window.status = xy.join(":")+":"+page.join(":");

		//注册移动相应的事件
		Ext.EventManager.on(document, "mousemove", this.handleMouseMove, this);
		Ext.EventManager.on(document, "mouseup", this.handleMouseUp, this);						
	},
	handleMouseMove : function(e){
		e.preventDefault();
		if(!this.dragEl)return;
		//判断鼠标是否有小范围的移动
		var xy = this.getXY(e);
		if(Math.abs(xy[0] - this.lastPointer[0]) < 2 && Math.abs(xy[1] - this.lastPointer[1]) < 2){
			return;
		}
		if(!this.dragging){
			this.dragging = true;
			this.onDragStart(xy[0], xy[1], e);
		}
		this.onDrag(xy[0], xy[1], e);
		// 如果鼠标离页面的顶部和底部较近，需要移动滚动条的位置
		//rollPage(xy[0], xy[1],this.dragEl);
	},
	handleMouseUp : function(e){
		Ext.EventManager.un(document, "mousemove", this.handleMouseMove, this);
		Ext.EventManager.un(document, "mouseup", this.handleMouseUp, this);
		if(this.dragging){
			var xy = this.getXY(e);
			this.onDragEnd(xy[0], xy[1], e);
		}
		this.dragEl = null;		
		this.dragging = false;
	},
	onDragStart : function(x, y, e){
		Event.stop(e.browserEvent);
		var dragEl = this.dragEl;
		var destPosition = $('wcm-drag-destPosition');
		if(!destPosition){
			new Insertion.Before(dragEl, "<tr id='wcm-drag-destPosition' style='background:#cdcdcd;'><td colspan=5></td><tr>");
			destPosition = $('wcm-drag-destPosition');
		}
		Element.addClassName(dragEl,"dragel");
		document.body.appendChild(dragEl);
		//Element.addClassName(dragEl, dragElCls);
		dragEl.style.position="absolute";
		dragEl.style.zIndex = 10000;
		this.targets = getTargets($("tb"));
	},
	onDrag : function(x, y, e){
		var dragEl = this.dragEl;
		dragEl.style.left = (x - this.deltaX) + "px";
		dragEl.style.top = (y - this.deltaY) + "px";
		//window.status = $("curr_pics").scrollTop;//(x - this.deltaX)+":"+(y - this.deltaY-100);
		var targets = this.targets;
		// 如果没有目标，直接返回
		if(!targets)return;
		for(var index = 0; index < targets.length; index++){
			var target = targets[index];
			if(inElement(x, y, target)){
				var destPosition = $('wcm-drag-destPosition');
				/* 主要区分空白元素与当前元素之间的位置来判断是否放在前面还是后面 */
				if(destPosition != target.previousSibling)
					target.parentNode.insertBefore(destPosition,target);
				else
					target.parentNode.insertBefore(destPosition,Element.next(target));
				break;
			}
		}
	},
	onDragEnd : function(x, y, e){
		var destPosition = $('wcm-drag-destPosition');
		destPosition.parentNode.insertBefore(this.dragEl, destPosition);
		this.dragEl.style.position = "static";
		Element.remove(destPosition);
		refreshIndex();
		Element.removeClassName(this.dragEl,"dragel");
		this.widgets = null;
		//防止在复制时，由于targets不为空，而导致拖动元素时不产生占位元素
		this.targets = null;
		this.dragEl = null;
	},
	destroy : function(){
		Ext.EventManager.un(document, "mousedown", this.handleMouseDown, this);
	}
});
	</script>
</head>
<body>
<button onclick="upload()" style="padding:0 10px;">上传</button>
<table class="tb" id="tb">
	<tr id="list_title">
		<th width="10%">序号</th>
		<th width="30%">视频名</th>
		<th width="20%">编辑</th>
		<th width="20%">删除</th>
		<th width="20%">截取缩略图</th>
	</tr>
	<%
		
		for ( int i = 0; i < docs.size(); i++ ) {
			Document doc = (Document)docs.getAt(i);
			int docId = doc.getId();
			Channel chn  =  Channel.findById(Integer.parseInt(chnId));
			int siteId = chn.getSiteId();
			List list = XVideo.findXVideosByDocId(docId);
			XVideo xVideo = (XVideo)list.get(0); 
			
	%>
		<tr id="chnldocid_<%=ChnlDoc.findByDocument(doc).getId()%>" docid="<%=docId%>">
			<td width="10%">&nbsp;<%=i+1 %></td>
			<td align="center" style="width:30%;">
				<%= doc.getTitle() %>
			</td>
	<%if(xVideo.getConvertStatus()==1){%>
			<td width="20%">	
				<a href="./video_addedit.jsp?DocumentId=<%=doc.getId() %>&ChannelId=<%=chnId %>&SiteId=<%=siteId%>&FromEditor=1" target="_blank">编辑</a>
			</td>
			<td width="20%">	
				<a href="#" onclick="deleteVideo(<%=ChnlDoc.findByDocument(doc).getId()%>)">删除</a>
			</td>
			<td width="20%">	
				<a href="updateThumb.jsp?docId=<%=docId%>" onclick="" target="_blank">截取</a>
			</td>
	<%}else{%>
			<script language="javascript">
			<!--
				bSuccess = false;
			//-->
			</script>
			<td colspan=3>正在转码，请稍后...</td>
	<%}%>
		</tr>
	<%
		}
	%>
</table>

<iframe id="iframe" src="" width="" height="" style="display:none;">
</iframe>
</body>
</html>