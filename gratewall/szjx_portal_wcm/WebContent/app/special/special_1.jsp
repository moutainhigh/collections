<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="error_for_dialog.jsp"%>
<%@ page import="com.trs.components.special.Special" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.common.publish.widget.Master" %>
<%@ page import="com.trs.components.common.publish.widget.PageStyles" %>
<%@ page import="com.trs.components.common.publish.widget.PageStyle" %>
<%@ page import="com.trs.components.special.ISpecialMgr" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.common.WCMTypes" %>
<%@ page import="com.trs.components.common.publish.widget.SpecialAuthServer"%>
<%@include file="../include/public_server.jsp"%>
<%
	//接受页面参数
	int nObjectId = currRequestHelper.getInt("ObjectId",0);
	int nIsNew = currRequestHelper.getInt("isNew",0);
	
	Special special = null;
	String sSepcialName = "";
	String sSepcialDesc = "";
	String sViewThumb = null;
	String sViewThumn_url = "images/zt_wt.gif";
	String sSytleName = "";
	String sMasterIds = "";
	Master oIndexMaster = null;
	Master oOutlineMaster = null;
	Master oDetailMaster = null;
	int nIndexMasterId = 0;
	int nOutlineMasterId = 0;
	int nDetailMasterId = 0;
	String sIndexMName = "无";
	String sOutlineMName = "无";
	String sDetailMName = "无";

	if(nObjectId == 0){
		special = Special.createNewInstance();
		if(!SpecialAuthServer.hasRight(loginUser, special,SpecialAuthServer.SPECIAL_ADD)){
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, LocaleServer.getString("special1.jsp.label.have_noright", "您没有权限新建专题！"));
		}
	}else{
		special = Special.findById(nObjectId);
		//参数校验
		if(special == null){
		    throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("special_1.jsp.specialId_not_found", "没有找到指定的专题 [ID={0}]!"), new String[]{String.valueOf(nObjectId)}));
		}
		//权限校验
		if (!SpecialAuthServer.hasRight(loginUser, special,SpecialAuthServer.SPECIAL_EDIT)) {
            throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT,CMyString.format(LocaleServer.getString("special_1.jsp.have_noright_edit", "您没有权限修改专题【{0}】！"), new String[]{special.getSpecialName()}));
        }
		sSepcialName = special.getSpecialName();
		sSepcialDesc = special.getSpecialDesc();
		sViewThumb = special.getViewThumb();
		if(!(sViewThumb == null || sViewThumb.equals("null") || sViewThumb.trim().equals("")))
			sViewThumn_url = mapFile(sViewThumb);
		sMasterIds = special.getMasterIds();
		if(sMasterIds != null && sMasterIds.length() > 0){
			String[] sMasterIdsTmp = sMasterIds.split(",");
			nIndexMasterId = Integer.parseInt(sMasterIdsTmp[0]);
			nOutlineMasterId = Integer.parseInt(sMasterIdsTmp[1]);
			nDetailMasterId = Integer.parseInt(sMasterIdsTmp[2]);
		}
		if(nIndexMasterId > 0){
			oIndexMaster = Master.findById(nIndexMasterId);
			sIndexMName = oIndexMaster.getMName();
		}
		if(nOutlineMasterId > 0){
			oOutlineMaster = Master.findById(nOutlineMasterId);
			sOutlineMName = oOutlineMaster.getMName();
		}
		if(nDetailMasterId > 0){
			oDetailMaster = Master.findById(nDetailMasterId);
			sDetailMName = oDetailMaster.getMName();
		}		
		//获取当前专题的所属风格
		ISpecialMgr m_oSpecialMgr = (ISpecialMgr) DreamFactory
                .createObjectById("ISpecialMgr");
		PageStyle pageStyle = m_oSpecialMgr.getStyle(special);
		if(pageStyle != null){
			sSytleName = pageStyle.getStyleName();
		}
	}

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
 <HEAD>
  <TITLE WCMAnt:param="special_1.jsp.title"> 新建专题 </TITLE>
  <META NAME="Generator" CONTENT="EditPlus">
  <META NAME="Author" CONTENT="">
  <META NAME="Keywords" CONTENT="">
  <META NAME="Description" CONTENT="">
  <link href="css/style.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" type="text/css" href="../css/wcm-common.css">
  <script src="../js/easyversion/lightbase.js"></script>
  <script src="../js/easyversion/extrender.js"></script>
  <script src="../js/easyversion/elementmore.js"></script>
  <script src="js/adapter4Top.js"></script>
  <!--validator start-->
  <script src="../js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
  <script src="../js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
  <script src="../js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
  <script src="../js/source/wcmlib/com.trs.validator/Validator.js"></script>
  <link href="../js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
  <!--validator end-->
  <script src="special_1.js"></script>
  <script src="./master/MasterSelector.js"></script>
  <style type="text/css">
	.option_container{
		border:0px solid #90b9d1;  
		overflow:hidden;
		height:20px;
	}
	.masterContainer{
		width:603px;
		height:150px;
		overflow:auto;
		border:1px solid #90b9d1;
	}
	.ext-gecko .masterContainer{
		height:140px;
	}
	.ext-gecko .adapteMargin{
		padding-left:10px;
	}
	.ext-ie8 .masterContainer{
		height:140px;
	}
	.master_select{
		display:inline-block;
		zoom:1;
		width:16px;
		height:16px;
		background:url(../images/icon/TempSelect.gif) center center no-repeat;
	}
	.ext-gecko2 .master_select{
		display:-moz-inline-box;
		position:relative;
		top:-10px;
	}
	.indexNameCss{
		vertical-align:middle;
		line-height:35px;
	}
  </style>
  <!--[if IE 6]>
	<style>
		.heightPerOne{
			_height:100%;
			_line-height:100%
		}
	</style>
  <![endif]-->
  <script language="javascript">
	<!--
		window.m_cbCfg = {
			btns : [
				{
					text : '下一步',
					id : 'ParambtnSave',
					cmd : function(){
						save();
						return false;
					}
				},
				{
					extraCls : 'wcm-btn-close',
					text : '取消'
				}
			]
		};		
	//-->
	</script>
	<script language="javascript">
    <!--
        Event.observe(window, 'load', function(){
            //初始化页面中需要校验的元素
            ValidationHelper.initValidation();
        });
    //-->
    </script>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		function redirect(){
			var specialName = '<%=CMyString.filterForJs(sSepcialName)%>';
			specialName = encodeURIComponent(specialName);
			location.href = "special_2.jsp?ObjectId=<%=nObjectId%>&specialName="+specialName+"&isNew=<%=nIsNew%>";
			wcm.CrashBoarder.get(window).getCrashBoard().setSize('720px','360px');
			save();
		}
	//-->
	</SCRIPT>
 </HEAD>

 <BODY>
    <form style="margin:0px" id="addEditForm" name="addEditForm" method="post" action="">
	<table width="697" height="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
	  <tr>
		<td valign="top">
		  <!--步骤图-->
		  <table width="98%" align="center" height="40" border="0" cellspacing="0" cellpadding="0" class="table-grey">
			<tr>
			  <td class="font" WCMAnt:param="special_1.jsp.step">您需要按以下几个步骤创建专题：</td>
			</tr>
			<tr>
			  <td >&nbsp;</td>
			</tr>
			<tr>
			  <td align="left" style="width:200px;"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="box-focus">
				  <tr>
					<td class="font-focus">1. <span class="font-focus01" WCMAnt:paramattr="title:special_1.jsp.maintain_special_info" 
 title="进行专题基本信息的维护"><a href="#" onclick="location.href=location.href;" style="font-family:幼圆" WCMAnt:param="special_1.jsp.input_basic_info">录入基本信息</a></span></td>
				  </tr>
			  </table></td>
			  <td align="center" style="width:40px;"><img src="images/zt_jt.gif" width="31" height="22" /></td>
			  <td align="center" style="width:180px;"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="box-gray">
				  <tr>
					<td class="font-gray">2. <span class="font-gray01" style="font-family:幼圆;font-weight:bold;" WCMAnt:paramattr="title:special_1.jsp.this_step_channel" title="该步骤将进行专题所属栏目的组织结构维护">
					<%
						if(nObjectId != 0){
					%>
						<a href="#" onclick="redirect()">
					<%
						}
					%>
					<span WCMAnt:param="special_1.jsp.maintain_stuctrue">维护结构</span>
					<%
						if(nObjectId != 0){
					%>
						</a>
					<%
						}
					%></span></td>
				  </tr>
			  </table></td>
			  <td align="center" style="width:40px;"><img src="images/zt_jt.gif" width="31" height="22" style="display:<%=nIsNew == 0? "none":""%>"/></td>
			  <td align="left" class="adapteMargin"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="box-gray" style="display:<%=nIsNew == 0? "none":""%>">
				  <tr>
					<td class="font-gray" style="display:<%=nIsNew == 0? "none":""%>">3. <span class="font-gray01" style="font-family:幼圆;font-weight:bold;" WCMAnt:param="title:special_1.jsp.this_step_special_page" title="该步骤将进入专题设计页面" WCMAnt:param="special_1.jsp.design_display">	
						设计表现</span></td>
				  </tr>
			  </table></td>
			</tr>
			<tr>
			  <td >&nbsp;</td>
			</tr>
		  </table>
		  <!--内容区-->
		  <table border="0" align="center" cellpadding="0" cellspacing="0" class="box-border">
			<tr>
			  <td valign="top" width="73%;">
				<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="bg-1">
				  <tr>
				    <td>&nbsp;</td>
				  </tr>
				  <tr>
					<td><span class="bg-desc" WCMAnt:param="special_1.jsp.special_name">专题名称：</span>&nbsp;<input type="text" id="SpecialName" name="SpecialName" validation_desc="专题名称" value="<%=CMyString.filterForHTMLValue(sSepcialName)%>" validation="required:true;type:'string';max_len:50;rpc:'checkSpecialName'" onkeydown="if(event.keyCode==13) return false;">
					</td>
				  </tr>
				  <tr>
				    <td>&nbsp;</td>
				  </tr>
				  <tr>
					<td><span class="bg-desc"  WCMAnt:param="special_1.jsp.special_desc">专题描述：</span>
					<textarea id="SpecialDesc" name="SpecialDesc" style="overflow:auto;"><%=CMyString.filterForHTMLValue(sSepcialDesc)%></textarea>
					</td>
				  </tr>
				</table>
			  </td>
			  <td valign="top">
				<table border="0" cellpadding="0" cellspacing="0">
				  <tr><td>&nbsp;</td></tr>
				  <tr><td><span class="font01"  WCMAnt:param="special_1.jsp.special_thumb">专题缩略图(尺寸: 130*70)</span></td></tr>
				  <tr><td><img src="<%=sViewThumn_url%>" border=0 alt="" id="img_ViewThumb" width="130px" height="70px;"/></td></tr>
				  <tr><td>&nbsp;</td></tr>
				  <tr>
					<td>
					 <table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%">
					 <tbody>
						<tr>
							<td height="23px"><span style="padding-left:10px;padding-top:5px;cursor:pointer;" onclick="resumeThumb()"><img src="images/zt-xj_an1.gif" border=0 alt="" /></span></td>
							<td valign="top" align="right" width="45px"  height="23px" style="padding-right:20px;"><IFRAME src="file/file_upload.jsp" id="PortalViewThumbUpload" name="PortalViewThumbUpload" frameborder="no" border="2px solid red" framespacing="0" scrolling="no" style="width:45px;height:23px;"></IFRAME></td>
						</tr>
					 </tbody>
					 </table>
					</td>
				  </tr>
				</table>
			  </td>
			</tr>
			<tr>
			  <td colspan="2" style="height:3px;">&nbsp;</td>
			</tr>
			<tr>
			  <td>
				<table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%">
				<tbody>
					<tr>
						<td><span class="bg-desc" style="padding-left:12px;"  WCMAnt:param="special_1.jsp.special_style">专题风格：</span>
							<span class="option_container">
							<select name="styleName" id="styleName" style="width:210px;margin:0px;">
							<option value="-1"  WCMAnt:param="special_1.jsp.please_select">---- 请选择 ---</option>
							<%
								WCMFilter filer = new WCMFilter();
								PageStyles styles = PageStyles.openWCMObjs(loginUser, filer);
								PageStyle style = null;
								String sStyleName = null;
								for(int i=styles.size()-1;i>=0;i--){
									style = (PageStyle)styles.getAt(i);
									if(style == null) continue;
									sStyleName = style.getPropertyAsString("STYLEDESC");
							%>
							<option value="<%=style.getStyleName()%>" <%=sSytleName.equals(style.getStyleName())? "selected='selected'":""%>><%=CMyString.transDisplay(CMyString.truncateStr(sStyleName,28))%></option>
							<%
								}
							%>
						</select></span></td>
					</tr>
				</tbody>
				</table>
			  </td>
			</tr>
			<tr>
			  <td colspan="2" style="height:3px;">&nbsp;</td>
			</tr>
			<tr>
			  <td colspan="2">
			   <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
				  <tr>
					<td  style="height:35px;">
						<span class="bg-desc" style="padding-left:12px;vercial-align: middle;">首页基准母板
						<%if(nObjectId == 0) { %><a href="#" name="master" index="0" class="master_select" title="选择首页基准母板"></a><%}%>
						：</span>
						<span style="color:red;" class="indexNameCss heightPerOne">*</span>
						<input type="hidden" value="<%=nIndexMasterId%>" id="indexMasterId"></input>
						<span id="indexMasterName" class="indexNameCss heightPerOne"><%=sIndexMName%></span>
					</td>
				</tr>
				<tr>
					<td style="height:35px;">
						<span class="bg-desc" style="padding-left:12px;">分类首页母板
						<%if(nObjectId == 0) { %><a href="#" name="master" index="1" class="master_select" title="选择分类首页母板" ></a><%}%>
						：</span>
						<input type="hidden" value="<%=nOutlineMasterId%>" id="outlineMasterId"></input>
						<span id="outlineMasterName" class="indexNameCss heightPerOne"><%=sOutlineMName%></span>
					</td>
				</tr>
				<tr>
					<td  style="height:35px;">
						<span class="bg-desc" style="padding-left:12px;">文档页面母板
						<%if(nObjectId == 0) { %><a href="#" name="master" index="2" class="master_select" title="选择文档页面母板" ></a><%}%>
						：</span>
						<input type="hidden" value="<%=nDetailMasterId%>" id="detailMasterId"></input>
						<span id="detailMasterName" class="indexNameCss heightPerOne"><%=sDetailMName%></span>
					</td>
				</tr>
			   </table>
			  </td>
			</tr>
			<tr>
			  <td>&nbsp;</td>
			 </tr>
		  </table>
		  <!--页脚-->
		  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="yejiao">
			<tr>
			  <td></td>
			</tr>
		  </table>
		</td>
	  </tr>
	</table>
 <input type="hidden" name="isNew" id="isNew" value="<%=nIsNew%>">
 <input type="hidden" name="ObjectId" id="ObjectId" value="<%=nObjectId%>">
 <input type="hidden" name="ViewThumb" id="ViewThumb" value="<%=CMyString.showNull(sViewThumb)%>">
 <input type="hidden" name="MasterIds" id="MasterIds" value="<%=sMasterIds%>">
 <input type="hidden" name="MasterId" id="MasterId" value="0">
 </form>
 </BODY>
</HTML>
<%!
	private String mapFile(String _sFileName){
		return "/webpic/" + _sFileName.substring(0,8) +"/" + _sFileName.substring(0,10) +"/" +_sFileName;
	}
%>