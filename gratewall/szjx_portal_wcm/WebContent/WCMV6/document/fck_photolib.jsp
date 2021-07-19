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
  <TITLE>TRS WCM V6插入图片库图片</TITLE>
  <link href="../css/common.css" rel="stylesheet" type="text/css" />
	<script src="../js/com.trs.util/Common.js"></script>
	<script label="TagParser">
		top.actualTop = window;
	//	$import('com.trs.ajaxframe.TagParser');
		$import('com.trs.web2frame.DataHelper');
		$import('com.trs.web2frame.TempEvaler');
		$import('com.trs.wcm.Web2frameDefault');
	</script>
	<script language="javascript">
	<!--
	$import('com.trs.wcm.MessageCenter');
	$import('com.trs.logger.Logger');
	$import('com.trs.dialog.Dialog');
	$import('com.trs.wcm.SimpleDragger');
	$import('com.trs.wcm.Grid');
	$import('com.trs.wcm.BubblePanel');
	$import('com.trs.wcm.FloatPanel');		
	$import('com.trs.crashboard.CrashBoard');
	$import('wcm52.calendar.Calendar');	
	//-->
	</script>
	<script language="javascript">
	<!--
	Calendar.displayType = 'lb';/*hv*///日期控件的显示位置
	var oEditor	= window.parent.InnerDialogLoaded() ;
	var FCK		= oEditor.FCK ;
		var SelectedPhotoIds = [];
		var PhotoSrcMap = {
		};
		var PhotoDragger = Class.create("PhotoDragger");
		Object.extend(PhotoDragger.prototype,com.trs.wcm.SimpleDragger.prototype);
		Object.extend(PhotoDragger.prototype,{
			delegate : function(_eRoot){
				this.shadow = _eRoot;
				this.thumbs = $('selected_photos').childNodes;
				var eHandler = this._getHint(_eRoot);
				this._handler = eHandler;
		//		this.shadow.style.display = 'none';
		//		this.shadow.style.height = '5px';
				this.oldBorder = this.shadow.style.border;
				this.lastNextSibling = _eRoot.nextSibling;
				this.shadow.style.border = '1px solid red';
				return eHandler;
			},
			_getHint : function(_eRoot){
				var eDiv = document.createElement('IMG');
				eDiv.className = 'myphoto';
				eDiv.src = _eRoot.src;
				eDiv.style.zIndex = 999;
//				eDiv.style.backgroundImage = _eRoot.style.backgroundImage;
				$('selected_photos').appendChild(eDiv);
				return eDiv;
			},
			_moveAfter : function(_eCurr,_ePrev){
				if(_ePrev!=null){
				}
				else{
				}
				return true;
			},
			onDragStart : function(nx,ny,_pXY,_event){
				Position.clone(this.root,this._handler);
			},
			onDrag : function(nx,ny,_pXY,_event){
				for(var i=0;i<this.thumbs.length;i++){
					var eThumb = this.thumbs[i];
					var offset = Position.cumulativeOffset(eThumb);
					var rOffset = Position.realOffset(eThumb);
					var iTop = offset[1]-rOffset[1]+Position.deltaY;
					var iBottom = iTop+eThumb.offsetHeight;
					var iCenter = (iBottom+iTop)/2;
					if(eThumb!=this.root&&eThumb!=this._handler){
						if(_pXY[1]>=iTop&&_pXY[1]<=iCenter){
							eThumb.parentNode.insertBefore(this.shadow,eThumb);
							this.moved = true;
							break;
						}
						else if(_pXY[1]>iCenter&&_pXY[1]<=iBottom){
							eThumb.parentNode.insertBefore(this.shadow,eThumb.nextSibling);
							this.moved = true;
							break;
						}
					}
				}
			},
			onDragEnd : function(nx,ny,_pXY,_event){
				var bNeedMove = false;
				if(this.moved){
					this.moved = false;
					if(this.shadow.nextSibling!=this.lastNextSibling&&
						this._moveAfter(this.shadow,this.shadow.previousSibling)){
						bNeedMove = true;
					}
				}
				this.shadow.style.border = this.oldBorder;
				if(!bNeedMove){
					this.root.parentNode.insertBefore(this.root,this.lastNextSibling);
				}
				$removeNode(this._handler);
				this.lastNextSibling = null;
				this._handler = null;
				this.shadow = null;
			}
		});
		function getWebPicPath(r){
			return "/webpic/" + r.substr(0,8) + "/" + r.substr(0,10) + "/" + r;
		}
		function createDiv(_nPhotoId,sPhotoSrcs,_nPhotoDocId){
			var eDiv = $('myphoto_'+_nPhotoId);
			if(eDiv==null){
				eDiv = document.createElement('DIV');
				eDiv.id = 'myphoto_'+_nPhotoId;
				eDiv.className = 'myphoto';
				eDiv.setAttribute('PhotoId',_nPhotoId);
				eDiv.setAttribute('PhotoDocId',_nPhotoDocId);
				eDiv.src = getWebPicPath(sPhotoSrcs.split(',')[0]);
				eDiv.innerHTML = '<img src="'+ eDiv.src +'"><img id="opl_delete_'+
					_nPhotoId+'" class="opl_delete" PhotoId="'+_nPhotoId+'" style="display:none" src="../images/photo/cancel.png">';
//				eDiv.style.backgroundImage = 'url('+getWebPicPath(sPhotoSrcs.split(',')[0])+')';
				Event.observe(eDiv,'mouseover',function(event){
					var eDelete = $('opl_delete_'+_nPhotoId);
					if(eDelete){
						eDelete.setAttribute("PhotoId",_nPhotoId);
						Element.show(eDelete);
					}
				});
				new PhotoDragger(eDiv);
				Event.observe(eDiv,'mouseout',function(event){
					var eDelete = $('opl_delete_'+_nPhotoId);
					if(eDelete){
						Element.hide(eDelete);
					}
				});
				$('selected_photos').appendChild(eDiv);
				var eDelete = $('opl_delete_'+_nPhotoId);
				Event.observe(eDelete,'mousedown',function(event){
					return false;
				});
				Event.observe(eDelete,'click',function(event){
					var eDelete = $('opl_delete_'+_nPhotoId);
					eDelete.parentNode.parentNode.removeChild(eDelete.parentNode);
					RemovePhoto(_nPhotoId);
					return false;
				});
			}
			return eDiv;
		}
		function RemovePhoto(_nPhotoId){
			SelectedPhotoIds = SelectedPhotoIds.without(_nPhotoId);
			delete PhotoSrcMap[_nPhotoId];
			var oScope = $('photo_list').contentWindow;
			oScope.RefreshSelected(SelectedPhotoIds);
		}
		function getDiv(_nPhotoId){
			return $('myphoto_'+_nPhotoId);
		}
		function SetPhotoSelected(_nPhotoId,sPhotoSrcs,_bChecked,_nPhotoDocId){
			var eDiv = getDiv(_nPhotoId);
			if(!_bChecked&&eDiv){
				SelectedPhotoIds = SelectedPhotoIds.without(_nPhotoId);
				delete PhotoSrcMap[_nPhotoId];
				$('selected_photos').removeChild(eDiv);
			}
			else if(_bChecked){
				SelectedPhotoIds.push(_nPhotoId);
				PhotoSrcMap[_nPhotoId] = sPhotoSrcs;
				createDiv(_nPhotoId,sPhotoSrcs,_nPhotoDocId);
			}
		}
		function Ok(){
			var nScaleIndex = $$F('ScaleSize');
			var childNodes = $('selected_photos').childNodes;
			var tmpSelectedPhotoIds = [];
			var tmpSelectePhotoDocIds = [];
			for(var i=0;i<childNodes.length;i++){
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
					var sPhotoSrcs = PhotoSrcMap[nPhotoId];
					var arrPhotoSrcs = sPhotoSrcs.split(',');
					var eTmpScale = nScaleIndex;
					if(arrPhotoSrcs.length<=nScaleIndex){
						eTmpScale = arrPhotoSrcs.length-1;
					}
					var sPhotoSrc = getWebPicPath(arrPhotoSrcs[eTmpScale]);
//					var sLink = getWebPicPath(arrPhotoSrcs[arrPhotoSrcs.length-1]);
					//TODO
					var oImage = FCK.CreateElement( 'IMG' ) ;
					oImage.setAttribute('_fcksavedurl', sPhotoSrc ) ;
					oImage.setAttribute('FromPhoto', 1 ) ;
					oImage.setAttribute('photodocid', nPhotoDocId ) ;					
					oImage.src = sPhotoSrc;
				}
				return true;
			}
			else{
				oHelper.Call('wcm6_photo', 'getPublishUrls', oPostData, true, function(_transport,_json){
					var arrUrls = _json["URLS"];
					for(var i=0;i<tmpSelectedPhotoIds.length;i++){
						var nPhotoId = tmpSelectedPhotoIds[i];
						var nPhotoDocId = tmpSelectePhotoDocIds[i];
						var sPhotoSrc = arrUrls[i][0];
	//					var sLink = arrUrls[i][1];
						oEditor.FCKUndo.SaveUndoStep() ;
						var oImage = FCK.CreateElement( 'IMG' ) ;
						oImage.setAttribute('_fcksavedurl', sPhotoSrc ) ;
						oImage.setAttribute('FromPhoto', 1 ) ;
						oImage.setAttribute('photodocid', nPhotoDocId ) ;
						if(arrUrls[i][2]!=""){
							oImage.setAttribute('ignore', 1 ) ;	
						}
						oImage.src = sPhotoSrc;
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
/*
			var nScale = $$F('ScaleSize');
			var eTmpScale = nScale;
			for(var i=0;i<SelectedPhotoIds.length;i++){
				var nPhotoId = SelectedPhotoIds[i];
				var sPhotoSrcs = PhotoSrcMap[nPhotoId];
				var arrPhotoSrcs = sPhotoSrcs.split(',');
				if(arrPhotoSrcs.length<=nScale){
					eTmpScale = arrPhotoSrcs.length-1;
				}
				//TODO .....
				var sPhotoSrc = getWebPicPath(arrPhotoSrcs[eTmpScale]);
				var sLink = getWebPicPath(arrPhotoSrcs[arrPhotoSrcs.length-1]);
				//TODO
				var oImage = FCK.CreateElement( 'IMG' ) ;
				oImage.src = sPhotoSrc;
				oEditor.FCKSelection.SelectNode( oImage ) ;
				var oLink = oEditor.FCK.CreateLink( sLink ) ;
				oEditor.FCKSelection.SelectNode( oLink ) ;
				oEditor.FCKSelection.Collapse( false ) ;
				oLink.setAttribute('_fcksavedurl', sLink ) ;
			}
			return true;
*/
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
		cursor:move;
	}
	.opl_delete{
		float:right;
		height:20px;
		margin-top:-20px;
		margin-right:2px;
		cursor:pointer;
	}
</style>
</HEAD>

<BODY style="margin:0;padding:0;">
		<table height="100%" width="100%">
			<tr>
				<td align="left" valign="top">
	<TABLE width="700" height="100%" border="0" cellpadding="0" cellspacing="0">
		<TR>
			<TD valign="top">
				<DIV id="docs_explorer" style="padding:4px;height:512px;overflow:auto;">
					<TABLE width="700" height="500" border="0" cellpadding="0" cellspacing="1" style="font-size:12px">
						<TR height="500">
							<TD width="200" valign="top">
								<iframe src="../nav_tree/nav_tree_4_photolib_select.html" scrolling="no" frameborder="0" style="width:194px;height:350px;border:1px solid gray"></iframe>
								<div style="height:146px;margin-top:2px;border:1px solid gray;overflow:auto;width:196px;background:#FFF">
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
									<input type="checkbox" name="allReplace" id="allReplace" value="1"><label for="allReplace">使用本地路径</label>
								</div>
							</TD>
							<TD width="500">
								<iframe name="photo_list" id='photo_list' src="javascript:void(0);" scrolling="no" frameborder="0" style="width:100%;height:100%;border:1px solid gray"></iframe>
							</TD>
						</TR>
					</TABLE>
				</DIV>
			</TD>
		</TR>
	</TABLE>
				</td>
				<td width="10" align="center" valign="top">
					<div style="width:2px;border-left:1px solid gray;background:white;overflow:hidden;height:503px;"></div></td>
				<td width="130" valign="top" id="otherContainer">
					<input id="btnOk" class="input_btn" style="margin-left:20px;width:80px" onclick="window.parent.Ok();"
						type="button" value="确定" fcklang="DlgBtnOK"/>
					<div style="height:5px;overflow:hidden"></div>
					<input name="button2" class="input_btn" style="margin-left:20px;width:80px" type="button" value="取消" onclick="window.parent.Cancel();" fcklang="DlgBtnCancel">
					<div style="height:6px;overflow:hidden"></div>
					<div style="height:2px;width:120px;border-top:1px solid gray;background:white;overflow:hidden;"></div>
					<div style="height:6px;overflow:hidden"></div>
					<span class="message">待插入的图片:</span><br>
					<div style="position:absolute;width:120px;height:420px;border:1px solid gray;background:#FFF;overflow:auto;overflow-x:hidden;" id="selected_photos">
					</div>
				</td>
			</tr>
		</table>
	<script language="javascript">
	<!--
	oEditor.FCKLanguageManager.TranslatePage(document) ;
	window.parent.SetBtnsRow(false);
	window.parent.SetAutoSize( true ) ;	
	//-->
	</script>
</BODY>
</HTML>