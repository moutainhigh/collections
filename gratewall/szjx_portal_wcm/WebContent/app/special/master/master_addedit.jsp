<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../error_for_dialog.jsp"%>
<%@include file="../../include/public_server.jsp"%>
<%@ page import="com.trs.components.common.publish.widget.Master"%>
<%@ page import="com.trs.components.common.publish.widget.SpecialAuthServer"%>
<%@ page import="com.trs.infra.common.WCMException"%>
<%@ page import="com.trs.infra.util.ExceptionNumber"%>
<%
	int nObjId = currRequestHelper.getInt("ObjectId",0);

	Master oMaster = Master.createNewInstance();
	boolean isAddMode = true;
	String sMName="",sMDesc="",sFileName="",sPicFileName="";
	int nMasterType = 0;
	//权限判断
	if(nObjId == 0){
		if(!SpecialAuthServer.hasRight(loginUser, oMaster,SpecialAuthServer.WIDGET_ADD)){
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, LocaleServer.getString("master_addedit.jsp.label.have_no_right2create_template", "您没有权限新建母板"));
		}
	}else{
		oMaster = Master.findById(nObjId);
		if(!oMaster.canEdit(loginUser)){
			throw new WCMException(ExceptionNumber.ERR_OBJ_LOCKED, CMyString.format(LocaleServer.getString("layout_addedit.jsp.locked", "当前对象被[{0}]锁定，您不能修改!"),  new Object[]{oMaster.getLockerUser()}));
		}
		if(!SpecialAuthServer.hasRight(loginUser, oMaster,SpecialAuthServer.WIDGET_EDIT)){
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, CMyString.format(LocaleServer.getString("master_addedit.jsp.label.have_no_right2alter_template", "您没有权限修改母板【{0}】"), new String[]{oMaster.getMName()}));
		}

		isAddMode = false;
		sMName = oMaster.getMName()==null?"":oMaster.getMName();
		sMDesc = oMaster.getMDesc()==null?"":oMaster.getMDesc();
		sFileName = oMaster.getFileName()==null?"":oMaster.getFileName();
		sPicFileName = oMaster.getPicFileName()==null?"":oMaster.getPicFileName();
		nMasterType = oMaster.getMasterType();
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title WCMAnt:param="master_addedit.jsp.title"> 新建/修改母板 </title>
<script src="../../js/easyversion/lightbase.js"></script>
<script src="../../js/easyversion/extrender.js"></script>
<script src="../../js/easyversion/elementmore.js"></script>
<script src="../../js/easyversion/ajax.js"></script>
<script src="../../js/easyversion/lockutil.js"></script>
<script src="../js/adapter4Top.js"></script>

<!--validator start-->
<script src="../../js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../js/source/wcmlib/com.trs.validator/Validator.js"></script>
<link href="../../js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
<!--validator end-->
<style type="text/css">
	body{
		font-size:12px;
	}
	input{
		width:200px;
		height:18px;
		line-height:18px;
		border:1px solid #6A95CF;
	}
	.blankTr{
		height:2px;
	}
	.asterisk{
		color:red;
		height:22px;
		display:inline-block;
	}
	.ext-ie6 .asterisk{
		margin-left:-5px;
	}
	.ext-gecko .asterisk{
		margin-left:-10px;
		vertical-align:super;
	}
	.ext-ie8 .asterisk{
		vertical-align:super;
	}
</style>

<script language="javascript">
<!--
	window.m_cbCfg = {
		btns : [
			{
				text : '确定',
				id : 'btnSave',
				cmd : function(){
					//保存
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

	
	function init(){
		//注册校验成功时执行的回调函数
		ValidationHelper.addValidListener(function(){
			//按钮有效处理
			wcmXCom.get('btnSave').enable();
		}, "formData");

		//注册校验失败时执行的回调函数
		ValidationHelper.addInvalidListener(function(){
			//按钮失效处理
			wcmXCom.get('btnSave').disable();
		}, "formData");

		//初始化页面中需要校验的元素
		ValidationHelper.initValidation();
	}
	//保存母板
	function save(){
		if(!ValidationHelper.doValid('name')){
			return false;
		}

        if(!validMasterType()){//校验母板类型
			return false;
		}
		var winUpload = $("frmUploadFile").contentWindow;
		var sNewFile = winUpload.document.getElementById("fileUpload").value;

		if(!<%=isAddMode%> && sNewFile == ""){//没有修改母板文件
			postToServer();
		}else{//修改了母板文件
			uploadFile();
		}
	}

	//发送服务保存母板
	function postToServer(){
		var oForm = document.getElementById('formData');
		var oPostData = {
			OBJECTID : oForm.OBJECTID.value,
			MNAME : oForm.MNAME.value,
			MDESC : oForm.MDESC.value,
			FILENAME : oForm.FILENAME.value,
			PICFILENAME : oForm.PICFILENAME.value,
			MASTERTYPE : oForm.MASTERTYPE.value
		};
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		var c_bWin = wcm.CrashBoarder.get(window);
		oHelper.Call("wcm61_master", "save", oPostData, true, function(_trans,_json){
			c_bWin.hide();
			c_bWin.notify('true');
		});
	}

	//上传母板文件
	function uploadFile(){
		var winUpload = $("frmUploadFile").contentWindow;
		var frm = winUpload.document.getElementById("frmPost");
		var sFileName = winUpload.document.getElementById("fileUpload").value;
		if(!winUpload.valid(sFileName)){
			return false;
		}

		frm.submit();
		return false;
	}

	//上传母板文件的upload_dowith页面中用到的方法
	function addFile(_sFilePath){
		if(_sFilePath==null){
			Ext.Msg.$alert("文件路径为空，上传失败！");
			return false;
		}

		var oForm = document.getElementById('formData');
		oForm.FILENAME.value = _sFilePath;
		//发送服务保存母板
		postToServer();
	}

	//上传母板文件错误提示
	function notifyOnUploadFileError(_sErrorMsg) {
		Ext.Msg.$alert(_sErrorMsg);
	}

			
	// 处理上传到临时目录的图片，在 upload_dowith 页面中使用
	function dealWithUploadedPicFile(_saveFileHttpPath, _saveFileName){
		if(_saveFileHttpPath.indexOf("upload")<0){
			Ext.Msg.$alert("上传文件失败");
		}
		if(!_saveFileHttpPath && _saveFileHttpPath=="")
			return;
		$("img_ViewThumb").src = "../../../file/read_image.jsp?FileName=" + _saveFileName;
		$("PicFile").value = _saveFileName;
	}

	// 将缩略图还原为默认状态
	function resumeThumb(){
		if($("PicFile").value == "" || $("PicFile").value == "0"){
			Ext.Msg.$alert("未发现上传的缩略图！");
			return;
		}
		Ext.Msg.confirm('您确定要清除此母板的缩略图吗？',{
			yes : function(){
				$("img_ViewThumb").src = "../images/list/none.gif";
				$("PicFile").value = "";
			}
		});
	}
	//校验母板类型
	function validMasterType(){
		var nMasterType = parseInt($("MASTERTYPE").value);
		var isAddMode = <%=isAddMode%>;
		if(isAddMode){//创建母板时检查类型
			if(nMasterType < 1){
				Ext.Msg.$alert("请选择母板类型！");
				return false;
			}
		}
		return true;
	}
//-->
</script>

<script language="javascript">
<!--
//校验母板信息
Event.observe(window, 'load', function(){
	ValidationHelper.registerValidations([
		{
			renderTo : 'MNAME',
			type : 'string',
			required : true,
			max_len : 20,
			rpc : function(){
				var dom = $('MNAME');
				if(dom.value == dom.getAttribute("_value")){//no change.
					return;
				}
				var oPostData = {
					objectId : $('objectId').value,
					MName : dom.value
				}
				var oHelper = com.trs.web2frame.BasicDataHelper();
				var oHelper = new com.trs.web2frame.BasicDataHelper();
				// 检查母板名称是否已存在
				oHelper.Call("wcm61_master","existsMName",oPostData, true,function(_trans,_json){
					if(com.trs.util.JSON.value(_json, "result") == 'false'){
						ValidationHelper.successRPCCallBack();
					}else{
						ValidationHelper.failureRPCCallBack("母板名称不唯一");
					}
				});
			}
		}
	]);
});

var nObjId = <%=nObjId%>;
var nObjType = <%=oMaster.getWCMType()%>
Event.observe(window, 'unload', onFrameClose);

function onFrameClose(){
	if(arguments.callee.invoked0) return;
	arguments.callee.invoked0 = true;
	if(nObjId != 0){
		LockerUtil.unlock(nObjId, nObjType);
	}
}

//-->
</script>
</head>

<body>
<form name="formData" id="formData" method=post action="master_addedit_dowith.jsp">
	<input type="hidden" name="OBJECTID" id="objectId" VALUE="<%=nObjId%>">
	<table style="border:1px solid #acddfd;margin:0 auto;width:445px">
		<tr class="blankTr"></tr>
		<tr>
			<td width="90px" align="right" 	WCMAnt:param="master_addedit.jsp.mastername">母板名称：</td>
			<td id="name"><input type="text" name="MNAME"  id="MNAME" value="<%=isAddMode?"":CMyString.filterForHTMLValue(sMName)%>" _value="<%=CMyString.filterForHTMLValue(sMName)%>" validation="required:true,desc:母板名称,type:string"  validation_desc="母板名称"  WCMAnt:paramattr="validation_desc:master_addedit.jsp.mastername"></td>
		</tr>
		<tr class="blankTr"></tr>
		<tr>
			<td align="right"  WCMAnt:param="master_addedit.jsp.masterdesc">母板描述：</td>
			<td><input type="text" name="MDESC" value="<%=isAddMode?"":CMyString.filterForHTMLValue(sMDesc)%>"  validation="desc:母板描述,type:string,max_len:50" validation_desc="母板描述" WCMAnt:paramattr="validation_desc:master_addedit.jsp.masterdescattr"></td>
		</tr>
		<tr class="blankTr"></tr>
		<tr>
			<td align="right">母板类型：</td>
			<td>
				<select  id="MASTERTYPE" name="MASTERTYPE" <%=isAddMode? "" : "disabled"%> _value = <%=nMasterType%>>
					<option value="0">=请选择=</option>
					<option value="1">概览</option>
					<option value="2">细览</option>
				</select>&nbsp;<span style="color: red; font-size: 12px;">*</span>
			</td>
		</tr>
		<tr class="blankTr"></tr>
		<tr>
			<td align="right" WCMAnt:param="master_addedit.jsp.masterfile">母板文件：</td>
			<td>
				<div id="divFileUpload">
					<IFRAME name="frmUploadFile" id="frmUploadFile" style="height:22px; width:225px" frameborder="0" vspace="0" src="../../file/file_upload.jsp?SelfControl=0&AllowExt=ZIP&ShowText=0" scrolling="NO" noresize="true"></IFRAME><%=isAddMode?"<span class=\"asterisk\">*</span>":""%>
					<input type="hidden" name="FILENAME" value="<%=isAddMode?"":CMyString.filterForHTMLValue(sFileName)%>">
				</div>
			
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td style="color:#528B3C" WCMAnt:param="master_addedit.jsp.zipfile">zip文件中只允许包含一个HTML格式的文件。</font>
			</td>
		</tr>
		<tr class="blankTr"></tr>
		<tr>
			<td align="right" WCMAnt:param="master_addedit.jsp.master_thumb">母板缩略图：</td>
			<td>
			<div id="" class="" style="color:#074B9E;font-size:10pt" WCMAnt:param="master_addedit.jsp.pic_size">图片尺寸（172*112）</div>
			<img id="img_ViewThumb" src="<%=isAddMode?"../images/list/none.gif":mapFile(sPicFileName)%>" width="172px" height="112px" />
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>
				<table>
					<tr>
						<td><span style="padding-left:15px;padding-top:5px;cursor:pointer;" onclick="resumeThumb()"><img src="../images/zt-xj_an1.gif" border=0 alt="" /></span></td>
						<td colSpan="2">&nbsp;</td>
						<td valign="top" align="right" width="45px" style="padding-right:20px;cursor:pointer;"><IFRAME src="../file/file_upload.jsp" id="PortalViewThumbUpload" name="PortalViewThumbUpload" frameborder="no" border="2px solid red" framespacing="0" width="45px" height="23px" scrolling="no" ></IFRAME></td>
					</tr>
				</table>
			</td>
		</tr>
		
	</table>
	<input type="hidden" name="PICFILENAME" id="PicFile" value="<%=isAddMode?"":CMyString.filterForHTMLValue(sPicFileName)%>">
</form>
<script language="javascript">
<!--
	function initSelectedMasterType(){
		var isAddMode = <%=isAddMode %>;
		var dom = document.getElementById("MASTERTYPE");
		if(!isAddMode && dom){//当不是新增模式时
			dom.value = dom.getAttribute("_value");
		}
	}
	// 改变选中的选项，_value中存储页面加载时，master的类型值
	initSelectedMasterType();
//-->
</script>
</body>
</html>
<%!
	private String mapFile(String _sFileName){
		if(CMyString.isEmpty(_sFileName) || _sFileName.equals("0") || _sFileName.equalsIgnoreCase("none.gif")){
			return "../images/list/none.gif";
		}else{
			return "/webpic/" + _sFileName.substring(0,8) +"/" + _sFileName.substring(0,10) +"/" +_sFileName;
		}
	}
%>