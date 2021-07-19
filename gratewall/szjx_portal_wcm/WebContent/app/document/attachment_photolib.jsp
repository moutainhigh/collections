<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.wcm.photo.IImageLibConfig" %>
<%@ page import="com.trs.wcm.photo.IMagicImage" %>
<%@ page import="com.trs.wcm.photo.impl.MagicImageImpl" %>
<%@include file="../../include/public_server.jsp"%>
<%
	IImageLibConfig oImageLibConfig = 
			(IImageLibConfig)DreamFactory.createObjectById("IImageLibConfig");
	IMagicImage oMagicImage = new MagicImageImpl();
	int[] arrScaleSizes = oImageLibConfig.getScaleSizes();
	int nDefaultIndex = 2;
	if(arrScaleSizes.length - 1 < nDefaultIndex) {
		nDefaultIndex = arrScaleSizes.length - 1;
	}
	out.clear();
%>
<HTML xmlns:TRS_UI>
<HEAD>
  <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <TITLE WCMAnt:param="attachment_photolib.jsp.title">从图片库选择图片附件</TITLE>
  <link href="../css/common.css" rel="stylesheet" type="text/css" />
<script src="../js/runtime/myext-debug.js"></script>
<script src="../js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../js/data/locale/document.js"></script>
<script src="../js/source/wcmlib/WCMConstants.js"></script>
<script src="../js/source/wcmlib/core/CMSObj.js"></script>
<script src="../js/source/wcmlib/core/AuthServer.js"></script>
<!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="../../app/js/source/wcmlib/dragdrop/wcm-dd.js"></script>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!--wcm-dialog end-->
<!--AJAX-->
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<!--CrashBoard-->
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></SCRIPT>
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/resource/widget.css" rel="stylesheet" type="text/css" />
<!--calendar-->
<script language="javascript" src="../../app/js/source/wcmlib/calendar/CTRSCalendar_Obj.js" type="text/javascript"></script>
<script language="javascript" src="../../app/js/source/wcmlib/calendar/calendar_lang/cn.js" type="text/javascript" WCMAnt:locale="../../app/js/source/wcmlib/calendar/calendar_lang/$locale$.js"></script>
<script language="javascript" src="../../app/js/source/wcmlib/calendar/CTRSCalendar.js" type="text/javascript"></script>
<link href="../../app/js/source/wcmlib/calendar/calendar_style/calendar-blue.css" rel="stylesheet" type="text/css" />
<script language="javascript">
<!--
function mappingHostWithObjType(objType){
	switch(objType){
		case WCMConstants.OBJ_TYPE_WEBSITEROOT:
			return WCMConstants.TAB_HOST_TYPE_WEBSITEROOT;
		case WCMConstants.OBJ_TYPE_WEBSITE:
			return WCMConstants.TAB_HOST_TYPE_WEBSITE;
		case WCMConstants.OBJ_TYPE_CHANNEL:
			return WCMConstants.TAB_HOST_TYPE_CHANNEL;
		case WCMConstants.OBJ_TYPE_MYFLOWDOCLIST:
			return WCMConstants.TAB_HOST_TYPE_MYFLOWDOCLIST;
		case WCMConstants.OBJ_TYPE_MYMSGLIST:
			return WCMConstants.TAB_HOST_TYPE_MYMSGLIST;
	}
}$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_TREENODE,
	afterclick : function(event){
		//负责导航树对应的页面切换
		var context = event.getContext();
		var objId = context.objId;
		var objType = context.objType;
		var sParams = '';
		var sHostType = mappingHostWithObjType(objType);
		var mySrc = '../photo/photo_list_editor.html';
		switch(objType){
			case WCMConstants.OBJ_TYPE_WEBSITEROOT:
				sParams = 'SiteType=' + context.siteType;
				break;
			case WCMConstants.OBJ_TYPE_WEBSITE:
				sParams = 'SiteId=' + objId;
				sParams += '&SiteType=' + context.siteType;
				break;
			case WCMConstants.OBJ_TYPE_CHANNEL:
				sParams = 'ChannelId=' + objId;
				sParams += '&SiteType=' + context.siteType;
				sParams += '&IsVirtual=' + context.isVirtual;
				sParams += '&ChannelType=' + context.channelType;
				break;
		}
		sParams += '&RightValue=' + context.right;
		$('photo_list').src = mySrc + '?' + sParams;
		return false;
	}
});
$MsgCenter.on({
	sid : 'sys_allcmsobjs',
	objType : WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
	afterselect : function(event){
		//TODO
	}
});
	Calendar.displayType = 'lb';/*hv*///日期控件的显示位置
		var SelectedPhotoIds = [];
		var PhotoSrcMap = {
		};
		var PhotoDescMap = {
		};
		function SetPhotoSelected(_nPhotoId,sPhotoSrcs,_bChecked,_sPhotoDesc,_sDefault,_sPhotoDocId){
			var eDiv = $('myphoto_'+_nPhotoId);
			if(!_bChecked && eDiv){
				SelectedPhotoIds = SelectedPhotoIds.remove(_nPhotoId);
				delete PhotoSrcMap[_nPhotoId];
				$('selected_photos').removeChild(eDiv);
			}
			else if(_bChecked){
				SelectedPhotoIds.push(_nPhotoId);
				PhotoSrcMap[_nPhotoId] = sPhotoSrcs;
				PhotoDescMap[_nPhotoId] = _sPhotoDesc;
				createSpan(_nPhotoId,sPhotoSrcs,_sPhotoDocId);
			}
		}
		function createSpan(_nPhotoId,sPhotoSrcs,_sPhotoDocId){
			var eDiv = $('myphoto_'+_nPhotoId);
			if(eDiv==null){
				eDiv = document.createElement('SPAN');
				eDiv.id = 'myphoto_'+_nPhotoId;
				eDiv.className = 'myphoto';
				eDiv.setAttribute('PhotoId',_nPhotoId);
				eDiv.setAttribute('PhotoDocId',_sPhotoDocId);
				eDiv.src = getWebPicPath(sPhotoSrcs.split(',')[0]);
				eDiv.innerHTML = '<img src="'+ eDiv.src +'"><img id="opl_delete_'+
					_nPhotoId+'" class="opl_delete" PhotoId="'+_nPhotoId+'" style="display:none" src="../images/photo/cancel.png">';
				//new PhotoDragger(eDiv);
				Event.observe(eDiv,'mouseover',function(event){
					showPic(['opl_delete_'],_nPhotoId);
				});
				Event.observe(eDiv,'mouseout',function(event){
					hidePic(['opl_delete_'],_nPhotoId);
				});
				$('selected_photos').appendChild(eDiv);
				//删除绑定
				var eDelete = $('opl_delete_'+_nPhotoId)
				Event.observe(eDelete,'click',function(event){
					eDelete.parentNode.parentNode.removeChild(eDelete.parentNode);
					RemovePhoto(_nPhotoId);
					return false;
				});
			}
			return eDiv;
		}
		function getWebPicPath(r){
			return "/webpic/" + r.substr(0,8) + "/" + r.substr(0,10) + "/" + r;
		}
		
		function showPic(group,_nPhotoId){
			for(var i=0 ; i < group.length; i++){
				var eDelete = $(group[i] + _nPhotoId);
				if(eDelete){
					Element.show(eDelete);
				}
			}
		}
		function hidePic(group,_nPhotoId){
			for(var i=0 ; i < group.length; i++){
				var eDelete = $(group[i] + _nPhotoId);
				if(eDelete){
						Element.hide(eDelete);
					}
			}
		}
		function RemovePhoto(_nPhotoId){
			SelectedPhotoIds = SelectedPhotoIds.remove(_nPhotoId);
			delete PhotoSrcMap[_nPhotoId];
			delete PhotoDescMap[_nPhotoId];
			var oScope = $('photo_list').contentWindow;
			oScope.RefreshSelected(SelectedPhotoIds);
		}
		function Ok(){
			var nScaleIndex = $$F('ScaleSize');
			var childNodes = $('selected_photos').childNodes;
			var tmpSelectedPhotoIds = [];
			var tmpSelectePhotoDocIds = [];
			var aFileNames = [];

			for(var i=0;i<childNodes.length;i++){
				if(!childNodes[i].tagName)continue;
				var nPhotoId = childNodes[i].getAttribute("PhotoId",2);
				if(!nPhotoId)continue;
				tmpSelectedPhotoIds.push(nPhotoId);
				var nPhotoDocId = childNodes[i].getAttribute("PhotoDocId",2);
				tmpSelectePhotoDocIds.push(nPhotoDocId);
				//获取到指定格式大小的文件名称
				var PhotoUrlMap = (PhotoSrcMap[nPhotoId]).split(",");
				//alert(PhotoUrlMap[nScaleIndex]);
				aFileNames.push(PhotoUrlMap[nScaleIndex]);
			}

			new Ajax.Request("../file/file_to_upload.jsp", {
				method : 'post',
				contentType: 'application/x-www-form-urlencoded',
				parameters : "FileNames="+encodeURIComponent(aFileNames.join(",")), 
				onSuccess : function(transport, json){
					var uploadFileNames = eval(transport.responseText);

					var oWindow = window.dialogArguments;
					var result = new oWindow.Array();
					for(var i=0;i<tmpSelectedPhotoIds.length;i++){
						var nPhotoId = tmpSelectedPhotoIds[i];
						var nPhotoDocId = tmpSelectePhotoDocIds[i];
						var sPhotoSrcs = PhotoSrcMap[nPhotoId];
						var arrPhotoSrcs = sPhotoSrcs.split(',');
						var eTmpScale = nScaleIndex;
						if(arrPhotoSrcs.length<=nScaleIndex){
							eTmpScale = arrPhotoSrcs.length-1;
						}
						var tmpObj = new oWindow.Object();
						tmpObj['photoDocid'] = nPhotoDocId;
						tmpObj['photoid'] = nPhotoId;
						tmpObj['desc'] = PhotoDescMap[nPhotoId];

						tmpObj['src'] = uploadFileNames[i]||"";
						tmpObj['url'] = '../file/read_image.jsp?FileName='+uploadFileNames[i]||"";
						result.push(tmpObj);
						window.returnValue = result;
						window.close();
					}
				}
			});
		}
	//-->
	</script>
	<script language="javascript">
	<!--

	//拖动
	wcm.dd.PhotoDragDrop = Ext.extend(wcm.dd.BaseDragDrop, {
		findTarget : function(dom){
			while(dom && dom.tagName != 'BODY'){
				if(dom.getAttribute('PhotoId')) return dom;
				dom = dom.parentNode;
			}
			return null;
		},
		b4StartDrag : function(event){
			var dom = Event.element(event.browserEvent);
			this.dom = this.findTarget(dom);
			if(this.dom != null && this.dom.className == "opl_delete") this.dom = this.dom.parentNode;
			return this.dom != null;
		},
		startDrag : function(x, y, event){
			var dom = this.dom;
			this.page = Position.page(dom);
			this.shadow = dom;
			Element.addClassName(this.shadow, 'dragging');
			this.nextSibling = Element.next(this.shadow);
			this.root = $(this.id);
			this.rootPage = Position.page(this.root);
			var dom = dom.cloneNode(true);
			dom.style.position = 'absolute';
			document.body.appendChild(dom);
			this.dragEl = dom;
			wcm.dd.PhotoDragDrop.superclass.startDrag.apply(this, arguments);
		},
		_moveAfter : function(_eCurr,_ePrev){
			if(_ePrev!=null){
				//alert(_ePrev.innerHTML);
			}
			else{
				//alert('i"m the first.');
			}
			return true;
		},
		drag : function(x, y, event){	
			/*
			if(x < this.rootPage[0] || y > this.rootPage[1]){
				return;
			}
			*/
			wcm.dd.PhotoDragDrop.superclass.drag.apply(this, arguments);
			this.dragEl.style.left = this.page[0] + (x- this.lastPointer[0]);
			this.dragEl.style.top = this.page[1] + (y - this.lastPointer[1]);
			var eThumb = Element.first(this.root);
			while(eThumb){
				var page = Position.page(eThumb);
				var iTop = page[1];
				var iLeft = page[0];
				var iRight = iLeft + eThumb.offsetWidth;
				var iBottom = iTop + eThumb.offsetHeight;
				var iCenter = (iLeft + iRight) / 2;
				if(eThumb!=this.shadow){
					if(y>=iTop&&y<=iBottom){
						if(x>=iLeft&&x<=iCenter){
							eThumb.parentNode.insertBefore(this.shadow, eThumb);
							break;
						}
						else if(x<=iRight&&x>iCenter){
							eThumb.parentNode.insertBefore(this.shadow, eThumb.nextSibling);
							break;
						}
					}				
				}
				eThumb = Element.next(eThumb);
			}
		},
		release : function(){
			if(!this.dragging) return;
			delete this.page;
			delete this.rootPage;
			delete this.root;
			delete this.shadow;
			delete this.nextSibling;
			delete this.dom;
			Element.remove(this.dragEl);
			delete this.dragEl;
			wcm.dd.PhotoDragDrop.superclass.release.apply(this, arguments);
		},
		endDrag : function(x, y, event){
			wcm.dd.PhotoDragDrop.superclass.endDrag.apply(this, arguments);
			var bMoved = true;
			if(Element.next(this.shadow) != this.nextSibling){	
				var previous = Element.previous(this.shadow);
				bMoved = this._moveAfter(this.shadow, previous);
			}
			Element.removeClassName(this.shadow, 'dragging');
			if(!bMoved){
				this.shadow.parentNode.insertBefore(this.shadow, this.nextSibling);
			}
		}
	});

	Event.observe(window, 'load', function(){
		new wcm.dd.PhotoDragDrop({id:'selected_photos'});
	});
	//-->
	</script>
<style type="text/css">
	body {
		padding-top:0px;
		background-color: #CCCCCC;
	}
	.input_btn{
		width:60px;
		margin-right:10px;
	}
	.input_text{
		border:1px solid gray;
		width:60px;
	}
	.message{
		font-weight:bold;
		color:green;
		line-height:20px;
	}
	.myphoto{
		position:relative;
		background-repeat:no-repeat;
		background-position:center center;
		width:75px;
		height:75px;
		margin:5px;
		display:block;
		cursor:pointer;
	}
	.opl_delete{
		float:right;
		height:20px;
		margin-top:-20px;
		margin-right:2px;
		cursor:pointer;
	}
	.ext-gecko .opl_delete{
		top:0px;
		position:relative;
	}
	.ext-safari .opl_delete{
		top:0px;
		position:relative;
	}
	.dragging {
		padding:0px; 
		border:3px solid green;
	}
</style>
</HEAD>

<BODY style="margin:0;padding:0;">
		<table height="100%" width="100%">
			<tr>
				<td align="left" valign="top">
	<TABLE width="800" height="100%" border="0" cellpadding="0" cellspacing="0">
		<TR>
			<TD valign="top">
				<DIV id="docs_explorer" style="padding:4px;height:642px;overflow:auto;">
					<TABLE width="800" height="630" border="0" cellpadding="0" cellspacing="1" style="font-size:12px">
						<TR height="620">
							<TD width="200" valign="top">
								<iframe src="../nav_tree/nav_tree_inner.html?siteTypes=1" scrolling="no" frameborder="0" style="width:194px;height:466px;border:1px solid gray"></iframe>
								<div style="height:156px;margin-top:2px;border:1px solid gray;overflow:auto;width:196px;background:#FFF">
<%
	int nScaleSize = 0;
	String strScaleDesc = null;
	for(int i=0; i<arrScaleSizes.length; i++) {
		nScaleSize = arrScaleSizes[i];
		strScaleDesc = oImageLibConfig.getScaleDescAt(i);
%>
<input type="radio" value="<%=i%>" name="ScaleSize" <%=nDefaultIndex==i?"checked":""%> id="ScaleSize_<%=nScaleSize%>">
<label for="ScaleSize_<%=nScaleSize%>">
<%if(i == 0) {%>
	<%=strScaleDesc%>(<%=nScaleSize%> x <%=nScaleSize%>)
<%} else {%>
	<%=strScaleDesc%>(<%=nScaleSize%>)
<%}%>
</label><br>
<%
	}
%>
								</div>
							</TD>
							<TD width="800">
								<iframe id='photo_list' src="../photo/photo_list_editor.html?SiteType=1" scrolling="no" frameborder="0" style="width:100%;height:100%;border:1px solid gray;background:#FFF;"></iframe>
							</TD>
						</TR>
					</TABLE>
				</DIV>
			</TD>
		</TR>
	</TABLE>
				</td>
				<td width="10" align="center" valign="top">
					<div style="width:2px;border-left:1px solid gray;background:white;overflow:hidden;height:623px;"></div></td>
				<td width="130" valign="top" id="otherContainer">
					<input id="btnOk" class="input_btn" style="margin-left:20px;width:80px" onclick="Ok();"
						type="button" value="确定" fcklang="DlgBtnOK" WCMAnt:paramattr="value:attachment_photolib.jsp.sure"/>
					<div style="height:5px;overflow:hidden"></div>
					<input name="button2" class="input_btn" style="margin-left:20px;width:80px" type="button" value="取消" onclick="window.close();" fcklang="DlgBtnCancel" WCMAnt:paramattr="value:attachment_photolib.jsp.cancelSelected">
					<div style="height:6px;overflow:hidden"></div>
					<div style="height:2px;width:120px;border-top:1px solid gray;background:white;overflow:hidden;"></div>
					<div style="height:6px;overflow:hidden"></div>
					<span class="message" WCMAnt:param="attachment_photolib.jsp.selectedPic">选中的图片:</span><br>
					<div style="position:absolute;width:120px;height:540px;border:1px solid gray;background:#FFF;overflow:auto;overflow-x:hidden;" id="selected_photos">
					</div>
				</td>
			</tr>
		</table>
</BODY>
</HTML>