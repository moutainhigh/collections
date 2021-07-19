<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel"%>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite"%>
<%@ page import="com.trs.cms.auth.domain.AuthServer"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.infra.common.WCMRightTypes"%>
<%@ page import="com.trs.infra.common.WCMException"%>
<%@ page import="com.trs.infra.util.ExceptionNumber"%>
<%@ include file="../include/public_server.jsp"%>
<%
	Channel parent = null;
	WebSite site = null;
	int nParentId = 0;
	try{
		nParentId = Integer.parseInt(request.getParameter("parentid"));
	}catch(Exception e){
	}

	int nSiteId = 0;
	
	if(nParentId == 0){	
		try{
			nSiteId = Integer.parseInt(request.getParameter("siteid"));
		}catch(Exception e){}
		if(nSiteId == 0){
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, LocaleServer.getString("epresschannel_create.jsp.notvalidparameter", "没有指定有效的参数！"));
		}
		site = WebSite.findById(nSiteId);
		if(site == null || site.isDeleted()){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("epresschannel_create.jsp.sitenotfondordeleted", "站点没有找到或已删除！[id={0}]"), new int[]{nSiteId}));
		}
		if(!AuthServer.hasRight(loginUser,site,WCMRightTypes.CHNL_ADD)){
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, LocaleServer.getString("epresschannel_create.jsp.nocreatechnlright", "对不起，您没有创建栏目的权限！"));
		}
	}else{
		parent = Channel.findById(nParentId);
		if(parent == null || parent.isDeleted()){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("epresschannel_create.jsp.parentchnlnotfondordeleted", "父栏目没有找到或已删除！[id={0}]"), new int[]{nSiteId}));
		}
		if(!AuthServer.hasRight(loginUser,parent,WCMRightTypes.CHNL_ADD)){
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT,LocaleServer.getString("epresschannel_create.jsp.nocreatechnlright", "对不起，您没有创建栏目的权限！"));
		}
		site = parent.getSite();
	}

	String sSiteName = CMyString.transDisplay(site.getName());
	String sParentName = LocaleServer.getString("epresschannel_creat.jsp.label.none", "无");
	if(parent != null){
		sParentName = CMyString.transDisplay(parent.getName());
	}	
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title WCMAnt:param="epresschannel_creat.jsp.new">创建TRSWCM电子报栏目::::::::::..</title>
<link href="../channel/channel_add_edit.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	body{
		font-size:12px;
		background: Transparent;
		padding:0px;
		margin:0px;
		overflow:hidden;
	}	
	.spAttrClue, .spAttrShort, .spAttrLongClue{
		width:85px; 
		height:22px; 
		line-height:22px; 
		margin-right:5px; 
		padding-left:10px; 
		white-space:nowrap;
		text-align: right;
	}
	.spAttrItem, .spAttrSpcItem{
		height:22px; 
		line-height:22px; 
        padding-top:0px;
	}
	.spAttrSpcItem{
        vertical-align:top;
		/*margin-bottom:8px; */
	}
	.spAttrLongClue{
		width:120px; 
	}
	.spAttrShort{
		width: 30px;
	}
	.spAttrPadding{
		width:20px;
	}
	.full_register{
		margin-top: 10px;
	}
	.middle_register_text4 .middle_register_text1{
		padding: 10px;
	}
	
    .length_limit{
        width:200px;
        overflow:hidden;
        white-space:nowrap;
        text-overflow:ellipsis;
    }	
	#selSiblings{
		width:140px;
	}
</style>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<link href="../../app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
<script src="epresschannel_create.js"></script>
</head>

<body>
<form id="addEditForm" name="addEditForm" method="post" action="">
	<div id="channel_attr" style="margin-top: 0;">
	<div class="tab-pane guidong" id="tab-pane-1" style="width:100%; height:100%; overflow-x:hidden; overflow-y: auto; background: Transparent;" select="MultiResult">		
		<input type="hidden" name="siteId" id="siteid" value="<%=site.getId()%>">
		<input type="hidden" name="sitetype" id="sitetype" value="0">
		<input type="hidden" name="parentId" value="<%=nParentId%>">		
		<div class="body-box" style="padding:0px;">
	        <div class="tab-body" id="tab-general">
				<div class="block_box">
					<div class="header_row_left">
						<div class="header_row_right">
							<div class="header_row_center">
								<div class="header_row_text" WCMAnt:param="channel_add_edit.jsp.desc">基本描述</div>
							</div>
						</div>
					</div>
					<div class="body_box">
						<div>
							<span class="spAttrClue" WCMAnt:param="epresschannel_creat.jsp.site">
								所属站点：
							</span>
							<span class="length_limit" title="<%=sSiteName%>"><%=sSiteName%></span>
						</div>
						<div>
							<span class="spAttrClue" WCMAnt:param="epresschannel_creat.jsp.parentchnl">
								父栏目：
							</span>
							<span id="spParentName" class="spAttrItem length_limit" title="<%=sParentName%>"><%=sParentName%></span>
						</div>
						<div>
							<span class="spAttrClue" WCMAnt:param="epresschannel_creat.jsp.creator">
								创建者：
							</span>
							<span class="spAttrItem"><%=loginUser.getName()%></span>
						</div>
						<div>
							<span class="spAttrClue" WCMAnt:param="epresschannel_creat.jsp.chnltype">
								栏目类型：
							</span>
							<span id="spSelType">
								<input type="hidden" id="hdChnlType" value="0">
								<select disabled="true" name="ChnlType" id="selChnlType" class="kuang_as"  style="margin-left:2px;">
									<option value="0" selected="true" WCMAnt:param="epresschannel_creat.jsp.commonchnl">普通栏目</option>
									<option value="1" WCMAnt:param="epresschannel_creat.jsp.picnews">图片新闻</option>
									<option value="2" WCMAnt:param="epresschannel_creat.jsp.headline">头条新闻</option>
									<option value="11" WCMAnt:param="epresschannel_creat.jsp.linkaddr">链接栏目</option>
								</select>						
							</span>						
						</div>
						<div>
							<span class="spAttrClue" WCMAnt:param="epresschannel_creat.jsp.unikey">
								唯一标识：
							</span>
							<span class="spAttrItem">
								<input id="txtChnlName" name="ChnlName" onblur="syncChannelDesc(this)" type="text" class="kuang_as" value="" validation="type:'string',required:'',max_len:'50',no_desc:'',rpc:'checkChannelName'"></input>
							</span>
						</div>
						<div>
							<span class="spAttrClue" WCMAnt:param="epresschannel_creat.jsp.desc">
								显示名称：
							</span>
							<span class="spAttrItem">
								<input name="ChnlDesc" id="txtChnlDesc" type="text" class="kuang_as" value="" validation="type:'string',required:'',max_len:'200',no_desc:''"></input>
							</span>
						</div>
						<div>
							<span class="spAttrClue" WCMAnt:param="epresschannel_creat.jsp.papertag">
								报纸标识：
							</span>
							<span class="spAttrItem">
								<input name="EPressUUID" id="EPressUUID" type="text" class="kuang_as" value="" style="ime-mode:disabled" validation="type:'common_char',required:'',max_len:'200',no_desc:'',rpc:'checkEPressUuid'"></input>
							</span>
						</div>
						<div>
							<span class="spAttrClue" WCMAnt:param="epresschannel_creat.jsp.paperpages">
								报纸版面数：
							</span>
							<span class="spAttrItem">
								<input name="PageCount" id="txtPageCount" type="text" class="kuang_as" value="" validation_desc="版面数" WCMAnt:paramattr="validation_desc:eoresschannel_create.jsp.pages" validation="type:'int',required:'',min:'1',max:100,desc:'版面数' "></input>
							</span>
						</div>
						<div>
							<span class="spAttrClue">
								<label for="chkHasCoverPage">报纸含封面：</label>
							</span>
							<span class="spAttrItem">
								<input name="HasCoverPage" id="chkHasCoverPage" type="checkbox" ></input>
							</span>
						</div>
						<div id="divCoverPage" style="display:none">
							<span class="spAttrClue">
								<label for="chkHasCoverPage">封面名称：</label>
							</span>
							<span class="spAttrItem">
								<input name="CoverPageName" id="txtCoverPageName" type="text" class="kuang_as" value="" validation="type:'string',max_len:'20',no_desc:''"></input>
							</span>
						</div>
						<div id="divBasicChannelOrder">
							<span class="spAttrClue" WCMAnt:param="epresschannel_creat.jsp.previouschnl">
								前一个栏目：
							</span>
							<span class="spAttrItem">
								<input type="hidden" id="hdChnlOrder" value="{#Channel.ChnlOrder}"></input>
								<select id="selSiblings" name="ChnlOrder" style="margin-left:3px;">
									<option value="-1" WCMAnt:param="epresschannel_creat.jsp.front">最前面</option>
									<%
										List brothers = null;
										if(nParentId > 0){
											brothers = parent.getChildren(loginUser,Channel.TYPE_NORM);
										}else{
											brothers = site.getChildren(loginUser,Channel.TYPE_NORM);
										}
										Channel brother = null;
										for(int i=0,size=brothers.size();i<size;i++){
											brother = (Channel)brothers.get(i);
											if(brother == null) continue;
									%>
										<option value="<%=brother.getOrder()%>"><%=CMyString.transDisplay(brother.getDesc())%></option>
									<%}%>
								</select>
							</span>
						</div>
					</div>
				</div>
			</div>				
		</div>	
	</div>
</form>
</body>
</html>