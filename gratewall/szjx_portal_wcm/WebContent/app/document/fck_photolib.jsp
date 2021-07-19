<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.wcm.photo.IImageLibConfig" %>
<%@ page import="com.trs.wcm.photo.IMagicImage" %>
<%@ page import="com.trs.wcm.photo.impl.MagicImageImpl" %>
<%@include file="../include/public_server.jsp"%>
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
<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
  <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <TITLE WCMAnt:param="fck_photolib.jsp.trswcmpicinserttopiclib">TRS WCM插入图片库图片</TITLE>
	<script src="../../app/js/runtime/myext-debug.js"></script>
	<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
	<script src="../../app/js/data/locale/photo.js"></script>
	<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
	<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
	<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
	<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
	<!--wcm-dialog start-->
	<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
	<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
	<script src="../../app/js/source/wcmlib/dragdrop/wcm-dd.js"></script>
	<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
	<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
	<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
	<!--wcm-dialog end-->
	<!--AJAX-->
	<script src="../../app/js/data/locale/system.js"></script>
	<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
	<!--CrashBoard-->
	<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></SCRIPT>
	<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></SCRIPT>
	<link href="../../app/js/resource/widget.css" rel="stylesheet" type="text/css" />
	<!--Calendar-->
	<script language="javascript" src="../../app/js/source/wcmlib/calendar/CTRSCalendar_Obj.js" type="text/javascript"></script>
	<script language="javascript" src="../../app/js/source/wcmlib/calendar/calendar_lang/cn.js" type="text/javascript" WCMAnt:locale="../../app/js/source/wcmlib/calendar/calendar_lang/$locale$.js"></script>
	<script language="javascript" src="../../app/js/source/wcmlib/calendar/CTRSCalendar.js" type="text/javascript"></script>
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
	<script language="javascript">
	<!--
	top.actualTop = window;
	var oEditor	= window.parent.InnerDialogLoaded() ;
	var FCK		= oEditor.FCK ;
	function getWebPicPath(r){
			return "/webpic/" + r.substr(0,8) + "/" + r.substr(0,10) + "/" + r;
		}
	function getWebPicPathReal(_sFileName,_nindex,_sdefault){
		var fg = _sFileName.split(",");
		var r = "";
		if(fg.length <= 1){
			r = WCMConstants.WCM6_PATH + 'system/read_image.jsp?FileName=' +  _sdefault;
			return r;
		}
		var eTmpScale = _nindex;
		if(fg.length <= _nindex){
			eTmpScale = fg.length-1;
		}
		r = fg[eTmpScale];
		return "/webpic/" + r.substring(0,8) + "/" + r.substring(0,10) + "/" + r;	
	}
	function Ok(){
		var nScaleIndex = $$F('ScaleSize');
		var childNodes = $('selected_photos').childNodes;
		var arrChildNodes = [];
		//兼容多浏览器判断是否选中待插入图片，子节点中文字节点的影响
		for (var i=0; i<childNodes.length; i++) {
			if(childNodes[i].nodeType == 3) continue;
			arrChildNodes.push(childNodes[i]);
		}
		if(arrChildNodes.length <=0){
			Ext.Msg.alert( "未选择任何要插入的图片" || wcm.LANG.NOPICFOUNDED );
			return;
		}
		var tmpSelectedPhotoIds = [];
		var tmpSelectePhotoDocIds = [];
		for(var i=0;i<childNodes.length;i++){
			if(!childNodes[i].tagName)continue;
			var nPhotoId = childNodes[i].getAttribute("PhotoId",2);
			if(!nPhotoId)continue;
			tmpSelectedPhotoIds.push(nPhotoId);
			var nPhotoDocId = childNodes[i].getAttribute("PhotoDocId",2);
			tmpSelectePhotoDocIds.push(nPhotoDocId);
		}
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		var oPostData = {
			"ChnlDocIds" : tmpSelectedPhotoIds.join(),
			"ScaleIndex" : nScaleIndex
		}
		if($('allReplace').checked){
			for(var i=0;i<tmpSelectedPhotoIds.length;i++){
				var nPhotoId = tmpSelectedPhotoIds[i];
				var nPhotoDocId = tmpSelectePhotoDocIds[i];
				var sPhotoSrc = getWebPicPathReal(PhotoSrcMap[nPhotoId],nScaleIndex,photoDefault[nPhotoId]);
//					var sLink = getWebPicPath(arrPhotoSrcs[arrPhotoSrcs.length-1]);
				//TODO
				var oImage = FCK.CreateElement( 'IMG' ) ;
				oImage.setAttribute('_fcksavedurl', sPhotoSrc ) ;
				oImage.setAttribute('FromPhoto', 1 ) ;
				oImage.setAttribute('border', 0 ) ;
				oImage.setAttribute('photodocid', nPhotoDocId ) ;					
				oImage.src = sPhotoSrc;
				if($('canlink').checked){
					var sLink = FCK.CreateElement( 'A' ) ;
					sLink.href = WCMConstants.WCM6_PATH + 'system/read_image.jsp?FileName=' + photoDefault[nPhotoId];
					sLink.target = '_blank';
					sLink.appendChild(oImage);
				}
			}
			return true;
		}
		else{
			oHelper.Call('wcm6_photo', 'getPublishUrls', oPostData, true, function(_transport,_json){
				var arrUrls = _json["URLS"];
				for(var i=0;i<tmpSelectedPhotoIds.length;i++){
					var nPhotoId = tmpSelectedPhotoIds[i];
					var nPhotoDocId = tmpSelectePhotoDocIds[i];
					var sPhotoSrcs = PhotoSrcMap[nPhotoId];
					var arrPhotoSrcs = sPhotoSrcs.split(',');
					var sPhotoSrc = arrUrls[i][0];
					var sSourceLink = arrUrls[i][1];
					oEditor.FCKUndo.SaveUndoStep() ;
					var oImage = FCK.CreateElement( 'IMG' ) ;
					oImage.setAttribute('_fcksavedurl', sPhotoSrc ) ;
					oImage.setAttribute('FromPhoto', 1 ) ;
					oImage.setAttribute('border', 0 ) ;
					oImage.setAttribute('photodocid', nPhotoDocId ) ;
					if(arrUrls[i][2]!=""){
						oImage.setAttribute('ignore', 1 ) ;	
					}
					if(arrPhotoSrcs.length <= 1){
						oImage.src = WCMConstants.WCM6_PATH + 'system/read_image.jsp?FileName=' +  photoDefault[nPhotoId];
					}else{
						oImage.src = sPhotoSrc;
					}
					if($('canlink').checked){
						var sLink = FCK.CreateElement( 'A' ) ;
						sLink.href = sSourceLink;
						sLink.target = '_blank';
						sLink.appendChild(oImage);
					}
/*
					oEditor.FCKSelection.SelectNode( oImage ) ;
					var oLink = FCK.CreateLink( sLink ) ;
					oLink.setAttribute('_fcksavedurl', sLink ) ;
					oEditor.FCKSelection.SelectNode( oLink ) ;
					oEditor.FCKSelection.Collapse( false ) ;
*/
				}
				setTimeout(function(){
					window.parent.Cancel(true);
				},10);
			});
			return false;
		}
	}
	$MsgCenter.on({
		objType : WCMConstants.OBJ_TYPE_TREENODE,
		afterclick : function(event){
			//负责导航树对应的页面切换
			var context = event.getContext();
			var objId = context.objId;
			var objType = context.objType;
			var sParams = '';
			var mySrc = '../photo/photo_list_editor.html?';
			switch(objType){
				case WCMConstants.OBJ_TYPE_WEBSITEROOT:
					mySrc += "siteType=" + 1;
					break;
				case WCMConstants.OBJ_TYPE_WEBSITE:
					mySrc += "siteid=" + objId;
					break;
				case WCMConstants.OBJ_TYPE_CHANNEL:
					mySrc += "channelid=" + objId ;
					break;
			}
			$('photo_list').src = mySrc;
			return false;
		}
	});
	var SelectedPhotoIds = [];
	var PhotoSrcMap = {};
	var photoDefault = {};
	function SetPhotoSelected(_nPhotoId,sPhotoSrcs,_bChecked,_sPhotoDesc,_sDefault,_sPhotoDocId){
		var eDiv = $('myphoto_'+_nPhotoId);
		if(!_bChecked && eDiv){
			SelectedPhotoIds = SelectedPhotoIds.remove(_nPhotoId);
			delete PhotoSrcMap[_nPhotoId];
			delete photoDefault[_nPhotoId];
			$('selected_photos').removeChild(eDiv);
		}
		else if(_bChecked){
			SelectedPhotoIds.push(_nPhotoId);
			PhotoSrcMap[_nPhotoId] = sPhotoSrcs;
			photoDefault[_nPhotoId] = _sDefault;
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
		delete photoDefault[_nPhotoId];
		var oScope = $('photo_list').contentWindow;
		oScope.RefreshSelected(SelectedPhotoIds);
	}
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
	.opl_moveleft{
		height:20px;
		margin-top:-20px;
		margin-left:-60px;
	}
	.opl_moveright{
		height:20px;
		margin-top:-20px;
		margin-left:-40px;
	}
	.dragging{
		padding:0px; 
		border:3px solid green;
	}
	.dragging1{
		background:buttonface; 
		color:black;
	}
	.ext-ie8 .wcm-btn{
		display:inline;
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
				<DIV id="docs_explorer" style="padding:4px;height:632px;width:800px;overflow:hidden;">
					<TABLE width="800" height="620" border="0" cellpadding="0" cellspacing="1" style="font-size:12px">
						<TR height="620">
							<TD width="200" valign="top" style="overflow:hidden;">
								<iframe src="../nav_tree/nav_tree_inner.html?siteTypes=1" scrolling="no" frameborder="0" style="height:450px;border:1px solid gray;overflow:hidden;"></iframe>
								<div style="height:166px;margin-top:2px;border:1px solid gray;overflow:auto;width:196px;background:#FFF">
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
	<!-- 由于使用pub目录下图片会间接导致图片显示为红叉，所以不再保留此接口，默认采用本地webpic目录下图片 -->
	<div style="display:none;">
		<input type="checkbox" name="allReplace" id="allReplace" value="1" ><label for="allReplace" WCMAnt:param="fck_photolib.jsp.localURL">使用本地路径</label><br/>
	</div>
									<input type="checkbox" name="canlink" id="canlink" value="1" checked="true"><label for="canlink" WCMAnt:param="fck_photolib.jsp.canlink">点击图片可链接到原图</label>
								</div>
							</TD>
							<TD width="600">
								<iframe name="photo_list" id='photo_list' scrolling="no" frameborder="0" style="width:100%;height:100%;border:1px solid gray;" src="../photo/photo_list_editor.html?siteType=1"></iframe>
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
					<input id="btnOk" class="input_btn" style="margin-left:20px;width:80px" onclick="window.parent.Ok();"
						type="button" value="确定" fcklang="DlgBtnOK" WCMAnt:paramattr="value:fck_photolib.jsp.ok"/>
					<div style="height:5px;overflow:hidden"></div>
					<input name="button2" class="input_btn" style="margin-left:20px;width:80px" type="button" value="取消" WCMAnt:paramattr="value:fck_photolib.jsp.cancel" onclick="window.parent.Cancel();" fcklang="DlgBtnCancel">
					<div style="height:6px;overflow:hidden"></div>
					<div style="height:2px;width:120px;border-top:1px solid gray;background:white;overflow:hidden;"></div>
					<div style="height:6px;overflow:hidden"></div>
					<span class="message" WCMAnt:param="fck_photolib.jsp.pictobeInsert">待插入的图片:</span><br>
					<div style="position:absolute;width:120px;height:540px;border:1px solid gray;background:#FFF;overflow:auto;overflow-x:hidden;" id="selected_photos">
					</div>
				</td>
			</tr>
		</table>
	<script language="javascript">
	<!--
	//保存导入图片
	oEditor.FCKLanguageManager.TranslatePage(document) ;
	window.parent.SetBtnsRow(false);
	window.parent.SetAutoSize( true ) ;	
	//-->
	</script>
</BODY>
</HTML>