<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CMyBitsValue" %>
<%@ page import="com.trs.components.wcm.MyPlugin" %>
<%@ page import="com.trs.infra.support.config.Config" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="java.io.File" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.OutputStreamWriter" %>
<%@ page import="java.io.Writer" %>
<%@ page import="org.dom4j.Document" %>
<%@ page import="org.dom4j.DocumentException" %>
<%@ page import="org.dom4j.DocumentHelper" %>
<%@ page import="org.dom4j.Element" %>
<%@ page import="org.dom4j.Node" %>
<%@ page import="com.trs.infra.BaseConfig" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.infra.config.ConfigConstants" %>
<%@ page import="com.trs.infra.util.CMyException" %>
<%@ page import="com.trs.infra.persistent.db.LicenseMgr" %>
<%@include file="../include/public_server.jsp"%>
<%
	//权限判断
	if(!loginUser.isAdministrator()){
		throw new WCMException(ExceptionNumber.ERR_UNKNOWN, LocaleServer.getString("config_manager_label_notadmin", "您不是管理员，不能执行此操作！"));
	}
	String licensePath = getLicenseFileName();
	String license = getFileContent(licensePath);
	String sTab = CMyString.showNull(request.getParameter("tabtype"),"tab-general");

	CMyBitsValue bitsValue = new CMyBitsValue(MyPlugin.getPluginCode());
%>
<%!

	public String getLicenseFileName()throws CMyException{
		String LICENSE = "license/LICENSE.trswcm";
		Class clazz = LicenseMgr.class;
		return clazz.getClassLoader().getResource(LICENSE).getFile();
	}
	public String getFileContent(String _fileName) throws CMyException{
		return CMyFile.readFile(_fileName).trim();
	}

	public String getXMLValue(String _info) throws CMyException, DocumentException{
		String[] info = _info.split(":");
        String sFilepath = info[1];
        String sFileName = info[2];
        String sXPath = info[3];
        int nAttrNode = Integer.parseInt(info[4]);
		String sAttrName = "";
		if(nAttrNode ==1){
            sAttrName = info[5];
        }
        String sRootPath = ConfigConstants.getConfigRootPath();
        String sFile = CMyString.setStrEndWith((sRootPath + sFilepath),
                File.separatorChar)
                + sFileName;
        String sXml = CMyFile.readFile(sFile, "GB2312");
        Document doc = DocumentHelper.parseText(sXml);
		String sPubFaildValue = "";
		if(sXPath.equalsIgnoreCase("/plugin/extension/scheme")){
            List nodes = doc.selectNodes(sXPath);
            for(int i=0;i<nodes.size();i++){
                Element currEl = (Element)nodes.get(i);
                if(!currEl.attribute("event").getValue().equalsIgnoreCase("Publish.WhenTaskFailed")){
					continue;
				}else{
					sPubFaildValue = currEl.attribute("enabled").getValue();
				}
            }
			return sPubFaildValue;
        }
		if(sXPath.equalsIgnoreCase("/plugin/extension/scheme/notification/users")){
            List nodes = doc.selectNodes(sXPath);
            for(int i=0;i<nodes.size();i++){
                Element currEl = (Element)nodes.get(i);
                if(currEl.getParent().getParent().attribute("event").getValue().equalsIgnoreCase("Publish.WhenTaskFailed")){
                    return currEl.getText();
                }
                continue;
            }
        }
		if(sXPath.equalsIgnoreCase("/plugin/extension/scheme/notification/groups")){
            List nodes = doc.selectNodes(sXPath);
            for(int i=0;i<nodes.size();i++){
                Element currEl = (Element)nodes.get(i);
				if(currEl.getParent().getParent().attribute("event").getValue().equalsIgnoreCase("Publish.WhenTaskFailed")){
                    return currEl.getText();
                }
                continue;
            }
        }

        Node node = doc.selectSingleNode(sXPath);
		Element el = (Element)node;
        String sReValue = "";
        if(nAttrNode==1){
            String sAttrValue = el.attribute(sAttrName).getValue();
            sReValue = sAttrValue;
        }else if(nAttrNode==0){
            String sNodeValue = node.getText();
            sReValue = sNodeValue;
        }
        return sReValue.trim();
    }

	public String getFileValue(){
		return "";
	}
%>
<%
/*
	对于name的构造规则的说明：都以冒号隔开，
	1、xml类的配置文件，第一个是所属类型，第二个是所属目录，第三个是文件名，第四个是节点的xpath，第五个是是否是属性节点的标识
	2、db类的配置，第一个是所属类型，第二个是ckey，第三个是desc，第四个是ctype，第五个是Encrypted。
	3、properties类的配置，第一个是所属类型，第二个是文件名，第三个是key。
*/
%>
<html>
<head>
	<title WCMAnt:param="config_manager.jsp.title">WCM配置统一管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<style>
	body{
		font-size:12px;
		margin:0px;
		padding:0px;
		background-color:white;
	}
	.body-box{
		padding-left:30px;
	}
	.wcm-tab-panel{
		width:96%;
		height:85%;
	}
	.odiv{
		margin-left:30px;
	}
	.descdiv{
		border:solid 1px #608c8c;width:650px;height:50px;
		padding:10px;
		position:relative;
		top:5px;
	}
	.row{
		height:20px;
	}
	</style>
</head>
<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<script src="../../app/js/wcm52/CTRSHashtable.js"></script>
<script src="../../app/js/wcm52/CTRSRequestParam.js"></script>
<script src="../../app/js/wcm52/CTRSAction.js"></script>
<script src="../../app/js/wcm52/CTRSArray.js"></script>
<script src="../../app/js/wcm52/CTRSString.js"></script>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/TempEvaler.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
<link href="../../app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<script src="../../app/js/source/wcmlib/tabpanel/TabPanel.js"></script>
<link href="../../app/js/source/wcmlib/tabpanel/resource/tabpanel.css" rel="stylesheet" type="text/css" />
<script language="javascript">
<!--

	var g_IsRegister = {
		//文字库是否注册的标记
		'0' : true,

		//图片库是否注册的标记
		'1' : true,
		'photo': true,

		//视频库是否注册的标记
		'2' : true,
		'video': true,

		//我定制的栏目
		'3' : true,

		//资源库是否注册的标记
		'4' : true,
		'metadata': true,

		'infoview': true
	};
	
	<%
		if (!bitsValue.getBit(2)) {
			out.println("g_IsRegister['4'] = g_IsRegister['metadata'] = false;");
		}
	%>

	var oTabPanel;
    Event.observe(window, 'load', function(){
        oTabPanel = new wcm.TabPanel({
            id : 'tabPanel',
            activeTab : "<%=CMyString.filterForJs(sTab)%>"||'tab-general'
        }).show();
    });

	function initCheck(){
		//ValidationHelper.registerValidations(m_arrValidations);
        ValidationHelper.addValidListener(function(){
            $('save').disabled = false;
        }, "dataform");
        ValidationHelper.addInvalidListener(function(){
           $('save').disabled = true;
        }, "dataform");
        ValidationHelper.initValidation();
    }
	Event.observe(window,'load',function(){
		initCkb();
		initCheck();
		if($('sendemail').checked){
			$('email_box').style.display='';
		}
	});
	
	function initCkb(){
		var eles = document.getElementsByTagName("input");
		for (var i = 0; i < eles.length; i++){
			if(eles[i].type.toLowerCase() != "checkbox")continue;
			if(eles[i].value.toLowerCase()=="true")eles[i].checked=true;
		}
		if($('user').value.indexOf($('puber').value)>-1)
			$('puber').checked=true;
	}
	
	function showmore(){
		var bShow = document.getElementById('more').style.display=='block';
		if(bShow){
			document.getElementById('more').style.display = 'none';
			return;
		}
			document.getElementById('more').style.display = 'block';
	}

	function dosub(){
		//value与_value比较，不同的，就提到
		var eles = document.getElementById('dataform').getElementsByTagName("input");		
		var nlen = eles.length;
		var count = 0;
		$('tabtype').value = oTabPanel.activeTab;
	
		if($('tabtype').value == "tab-document" && g_IsRegister['4']){
			var synChnldoc = document.getElementById("document_syn_chnldoc");
			if(g_IsRegister['4'] && !synChnldoc.checked){
				alert(wcm.LANG.config_manager_101 || "安装了元数据则\"同步字段到WCMCHNLDOC\"必选。");
				synChnldoc.checked = true;
				return;
			}

			var synChnldocFields = document.getElementById("synChnldocFields");
			if(synChnldocFields.value && synChnldocFields.value.toLowerCase().indexOf("dockind") == -1){
				alert(wcm.LANG.config_manager_102 || "安装了元数据的文档同步字段中必须包含dockind字段。");
				synChnldocFields.focus();
				return;
			}
		}

		for(var i = 0; i < nlen; i++){
			if(eles[i].type.toLowerCase() =="hidden")continue;
			var sValue = eles[i].value;
			var sOldValue = eles[i].getAttribute("_value");
			if(eles[i].type.toLowerCase() == 'checkbox'){
				var bChanged = (sValue == 'true' && !eles[i].checked) || (sValue =='false' && eles[i].checked);
				if(bChanged){
					count++;
					appendFormElement(eles[i], eles[i].checked);
				}
			}else if(sValue != sOldValue){
				count++;
				// 如果修改了注册码值,添加原始注册码的值
				if(eles[i].getAttribute("id")=="license"){
					var orign = document.getElementById('orignlicense');
					appendFormElement(orign,orign.value);
				}
				//以hidden的形式放到actionform中
				appendFormElement(eles[i], sValue);
			}
		}
		//如果发现没有修改，就不需要提交
		if(count<=0){
			alert(wcm.LANG.config_manager_103 || '您没有做任何修改。');
			return;
		}
		//3、再提交form
		document.getElementById('actionform').submit();
	}

	function appendFormElement(el, sValue){
		var elName = el.getAttribute("name");
		var inputDom = document.createElement('INPUT');
		inputDom.value = sValue;
		inputDom.name = elName;
		inputDom.type = "hidden";
		//以hidden的形式放到actionform中
		document.getElementById('actionform').appendChild(inputDom);
	}
	var m_sUserIds = "";
	var m_sUserNames = "";
	var m_sGroupIds = "";
	var m_sGroupNames = "";
	WCM52_CONSOLE_ROOT_PATH = '../../include/';
	function setOwnerUser(){
		var sUsers = $('user').value;
		var nPuberLen = "${Submitter}".length;
		var nIndex = sUsers.indexOf("${Submitter}");
		if(nIndex > -1){
			if(sUsers.charAt(nIndex-1)==","){
				sUsers = sUsers.substring(0,sUsers.length-nPuberLen-1);
			}else{
				sUsers = sUsers.substring(nIndex+nPuberLen+1,sUsers.length);
			}
		}
		retrieveUserIds(sUsers.trim(), function(_sUserIds){
			//renderOwnerSeting(_sUserIds, PageContext.gid);
			openSelUser(_sUserIds);
		});
	}

	function retrieveUserIds(_sUserNames, _fDoAfter){
		BasicDataHelper.call('wcm6_user', 'getUsersByNames', {usernames: _sUserNames}, true, function(_trans, _json){
			//var sUserId = $v(_json, 'users.user.userid');
			var arUsers = $a(_json, 'users.user');
			if(arUsers == null) {
				Ext.Msg.warn(String.format("无法获取用户[<b>{0}</b>]的任何信息！",_sUserNames), function(){
					$('txtUsers').focus();
					$('txtUsers').select();
				});
				return false;
			}
			//else
			var arUserIds = [];
			var arUserNames = [];
			for (var i = 0; i < arUsers.length; i++){
				var user = arUsers[i];
				arUserIds.push($v(user, 'userid'));
				arUserNames.push($v(user, 'USERNAME'));
			}
			var sUserIds = arUserIds.join(',');
			var sUserNames = arUserNames.join(',');
			if(_fDoAfter && typeof(_fDoAfter) == 'function') {
				_fDoAfter(sUserIds);
			}
		}.bind(this), function(_trans, _json){
			var sExp = $v(_json, 'Fault.Message');
			if(sExp == null || sExp.trim() == '') {
				sExp = String.format("获取用户[<b>{0}</b>]的用户信息失败!",_sUserNames);
			}else{
				var nPos = sExp.indexOf("[ERR-");
				 if (nPos >= 0) {
                    nPos = sExp.indexOf(']', nPos + 1);
                    if (nPos > 0) sExp = sExp.substring(nPos + 1);
				 }
			}
			Ext.Msg.warn(sExp, function(){
				$('user').focus();
				$('user').select();				
			});			
		});	
	}

	function openSelUser(_sUserIds){
		var oTRSAction = new CTRSAction(WCM52_CONSOLE_ROOT_PATH + "select_index.jsp");
		oTRSAction.setParameter("UserIds", _sUserIds);
		//oTRSAction.setParameter("GroupIds", m_sGroupIds);
		oTRSAction.setParameter("IsGroup", 0);
		var arOwners = oTRSAction.doDialogAction(800, 650);
		if(arOwners == null){
			return;
		}
		if(arOwners.length < 2){
			Ext.Msg.alert(wcm.LANG['RETURN_VALUE_FALSE'] || "返回值不正确！");
			return;
		}
		m_sUserNames = arOwners[1] || "";
		m_sUserIds = arOwners[0] || "";
		//m_sUserNames = m_sUserNames.join(",");
		if($('puber').checked){
			$("user").value = $('puber').value + "," +m_sUserNames;
		}else{
			$("user").value = m_sUserNames;
		}
		return;
	}

	function setOwnerGroup(){
		var sGroupIds = $('group').value;
		var oTRSAction = new CTRSAction(WCM52_CONSOLE_ROOT_PATH + "select_index.jsp");
		oTRSAction.setParameter("GroupIds", sGroupIds);
		oTRSAction.setParameter("IsGroup", 1);
		var arOwners = oTRSAction.doDialogAction(800, 600);
		if(arOwners == null){
			return;
		}
		if(arOwners.length < 2){
			Ext.Msg.alert(wcm.LANG['RETURN_VALUE_FALSE'] || "返回值不正确！");
			return;
		}
		m_sGroupNames = arOwners[3] || "";
		m_sGroupIds = arOwners[2] || "";
		//m_sGroupIds = m_sGroupIds.join(",");
		//m_sGroupNames = m_sGroupNames.join(",");
		$("group").value = m_sGroupIds;
		return;
	}
	
//-->
</script>
<body style="overflow:hidden;">

<form method="post" name="actionform" id="actionform" action="config_manager_dowith.jsp">
<input type="hidden" name="tabtype" id="tabtype" value="" />
<input type="hidden" name="first" id="first" value="" />
</form>
<form method="post" name="dataform" id="dataform" onSubmit="return false" action="">
<table style="border:solid 1px #608c8c;margin-left:10px;background-color:#608c8c;width:96%;height:40px;" cellspacing="0" cellpadding="0">
	<tbody>
		<tr>
			<td><span WCMAnt:param="config_manager.jsp.titledesc">WCM配置统一管理：在这里，你可以实现WCM常用配置的查看修改操作，免去了您去相应目录修改配置文件、修改config表的麻烦。</span></td>
		</tr>
	</tbody>
</table>
<div class="wcm-tab-panel" id="tabPanel">
	<div class="head-box" style="padding-left:20px;">
			<div class="tab-head" item="tab-general"><a href="#" WCMAnt:param="config_manager.jsp.general">常用</a></div>
			<div class="tab-head" item="tab-document"><a href="#" WCMAnt:param="config_manager.jsp.document">文档相关</a></div>
			<div class="tab-head" item="tab-publish"><a href="#" WCMAnt:param="config_manager.jsp.publish">发布相关</a></div>
			<div class="tab-head" item="tab-flow"><a href="#" WCMAnt:param="config_manager.jsp.flow">工作流相关</a></div>
			<div class="tab-head" item="tab-email"><a href="#" WCMAnt:param="config_manager.jsp.email">邮件服务器</a></div>
			<div class="tab-head" item="tab-plugin"><a href="#" WCMAnt:param="config_manager.jsp.plugin">选件相关</a></div>
			<div class="tab-head" item="tab-license"><a href="#" WCMAnt:param="config_manager.jsp.registerrelate">注册码相关</a></div>
	</div>
	<div class="body-box" style="width:99%;height:94%;padding-left:30px;overflow-y:auto;">
		<div class="tab-body" id="tab-general">
			<div style="height:15px;"></div>
			<div class="odiv">
				<b><span WCMAnt:param="config_manager.jsp.distribute">站点分发上限数：</span></b><input type="text" validation="no_desc:'',type:'int',min:'0',max:'10'" name="xml:com.trs.components.common.publish:config.xml:/plugin/extension/publish-server/publish-distribution/max-targets-each-folder:0" id="" value="<%=getXMLValue("xml:com.trs.components.common.publish:config.xml:/plugin/extension/publish-server/publish-distribution/max-targets-each-folder:0")%>" _value="<%=getXMLValue("xml:com.trs.components.common.publish:config.xml:/plugin/extension/publish-server/publish-distribution/max-targets-each-folder:0")%>"/>
				<div class="descdiv" WCMAnt:param="config_manager.jsp.distributedesc">说明：<span style="font-size:14px;color:green;">设置后需要重启生效，</span>可以通过修改这个数字来控制站点分发数的最大值。</div>
			</div>
			<div class="row"></div>
			<div class="odiv">
				<input type="checkbox" name="xml:com.trs.components.wcm.publish:config.xml:/plugin/extension/scheme:1:enabled" id="sendemail" value="<%=getXMLValue("xml:com.trs.components.wcm.publish:config.xml:/plugin/extension/scheme:1:enabled")%>" _value="<%=getXMLValue("xml:com.trs.components.wcm.publish:config.xml:/plugin/extension/scheme:1:enabled")%>"/><label for="sendemail"><b><span WCMAnt:param="config_manager.jsp.pubfailed">发布失败后发送邮件</span></b></label><br/>
				<div id="email_box" style="display:none;"><b><span WCMAnt:param="config_manager.jsp.getemailuser">接收邮件的用户：</span></b><input type="text" validation="no_desc:'',type:'string'" name="xml:com.trs.components.wcm.publish:config.xml:/plugin/extension/scheme/notification/users:0" id="user" value="<%=getXMLValue("xml:com.trs.components.wcm.publish:config.xml:/plugin/extension/scheme/notification/users:0")%>" _value="<%=getXMLValue("xml:com.trs.components.wcm.publish:config.xml:/plugin/extension/scheme/notification/users:0")%>" disabled/>
				<span style="cursor:pointer;">
					<img onclick="setOwnerUser();" src="../images/icon/icon_user.gif" align="absmiddle">
				</span>
				<input type="checkbox" name="puber" id="puber" value="${Submitter}" /><b><label for="puber" WCMAnt:param="config_manager.jsp.puber">发布者</label></b><br/>
				<b><span WCMAnt:param="config_manager.jsp.getemailgroup">接收邮件的组织ID：</span></b><input type="text" validation="no_desc:'',type:'string'" name="xml:com.trs.components.wcm.publish:config.xml:/plugin/extension/scheme/notification/groups:0" id="group" value="<%=getXMLValue("xml:com.trs.components.wcm.publish:config.xml:/plugin/extension/scheme/notification/groups:0")%>" _value="<%=getXMLValue("xml:com.trs.components.wcm.publish:config.xml:/plugin/extension/scheme/notification/groups:0")%>" disabled/>
				<span style="cursor:pointer;">
					<img onclick="setOwnerGroup();" src="../images/icon/icon_group.gif" align="absmiddle">
				</span></div>
				<div class="descdiv" WCMAnt:param="config_manager.jsp.pubfaileddesc">说明：<span style="font-size:14px;color:green;">设置后需要重启生效，</span>通过这些配置来指定发布失败是否发送邮件，同时可以指定接收邮件用户或组织，用户可以通过用户名和特别一类人——发布者来指定，组织可以通过指定组织ID来指定。</div>
			</div>
			<div class="row"></div>

		</div>

		<div class="tab-body" id="tab-document">
			<div style="height:15px;"></div>
			<div class="odiv">
				<input type="checkbox" id="DOCUMENT_INFLOW_ONCOPY" value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("DOCUMENT_INFLOW_ONCOPY", "false"))%>" _value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("DOCUMENT_INFLOW_ONCOPY", "false"))%>" name="db:DOCUMENT_INFLOW_ONCOPY:复制后是否走工作流:20:0"/><label for="DOCUMENT_INFLOW_ONCOPY"><b><span WCMAnt:param="config_manager.jsp.copytoflow">复制后自动进入工作流流转</span></b></label>
				<div class="descdiv" WCMAnt:param="config_manager.jsp.copytoflowdesc">说明：设置文档被复制后是否自动进入工作流流转。</div>
			</div>
			<div class="row"></div>
			<div class="odiv">
				<input type="checkbox" id="CHANGENAME" value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("CHANGENAME", "false"))%>" _value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("CHANGENAME", "false"))%>" name="db:CHANGENAME:复制文档是否要对cruser字段更名为当前操作者:20:0"/><label for="CHANGENAME"><b><span WCMAnt:param="config_manager.jsp.copytochangename">复制文档将CRUSER字段更名为当前操作者</span></b></label>
				<div class="descdiv" WCMAnt:param="config_manager.jsp.changenamdesc">说明：设置复制文档操作是否要对复制的文档的CRUSER字段更名为当前操作者。</div>
			</div>
			<div class="row"></div>
			<div class="odiv">
				<b><span WCMAnt:param="config_manager.jsp.copytopre">复制需要增加的前缀：</span></b><input type="text" id="" validation="no_desc:'',type:'string'" value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("DOC_COPY_TITLE_PRE", ""))%>" _value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("DOC_COPY_TITLE_PRE", ""))%>" name="db:DOC_COPY_TITLE_PRE:复制需要增加的前缀:20:0"/>
				<div class="descdiv" WCMAnt:param="config_manager.jsp.copytopredesc">说明：指定复制的文档标题需要增加的前缀。</div>
			</div>
			<div class="row"></div>
			<div class="odiv">
				<input type="checkbox" name="xml:com.trs.components.wcm:config.xml:/plugin/extension/wcmapp-config/document-syn-chnldoc:0" id="document_syn_chnldoc" value="<%=getXMLValue("xml:com.trs.components.wcm:config.xml:/plugin/extension/wcmapp-config/document-syn-chnldoc:0")%>" _value="<%=getXMLValue("xml:com.trs.components.wcm:config.xml:/plugin/extension/wcmapp-config/document-syn-chnldoc:0")%>"/><label for="document_syn_chnldoc"><b><span WCMAnt:param="config_manager.jsp.sysfieldornot">同步字段到WCMCHNLDOC</span></b></label>
				<div class="descdiv" WCMAnt:param="config_manager.jsp.sysfieldornotdesc">说明：<span style="font-size:14px;color:green;">设置后需要重启生效，</span>是否开启从WCMDOCUMENT表同步字段到WCMCHNLDOC表的设置。</div>
			</div>
			<div class="row"></div>

			<div class="odiv">
				<b><span WCMAnt:param="config_manager.jsp.sysfield">从WCMDOCUMENT表同步到WCMCHNLDOC表的字段：</span></b><input style="width:330px;" type="text" validation="no_desc:'',type:'string'" name="xml:com.trs.components.wcm:config.xml:/plugin/extension/wcmapp-config/document-syn-chnldoc-fields:0" id="synChnldocFields" value="<%=getXMLValue("xml:com.trs.components.wcm:config.xml:/plugin/extension/wcmapp-config/document-syn-chnldoc-fields:0")%>" _value="<%=getXMLValue("xml:com.trs.components.wcm:config.xml:/plugin/extension/wcmapp-config/document-syn-chnldoc-fields:0")%>"/>
				<div class="descdiv" WCMAnt:param="config_manager.jsp.sysfielddesc">说明：<span style="font-size:14px;color:green;">设置后需要重启生效，</span>如果开启了同步字段的设置，这里可以设置从WCMDOCUMENT表同步到WCMCHNLDOC表的字段有哪些。</div>
			</div>
			<div class="row"></div>

		</div>

		<div class="tab-body" id="tab-publish">
			<div style="height:15px;"></div>
			<div class="odiv">
				<input type="checkbox" id="NAME_BY_TEMPLATE_FILE" value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("NAME_BY_TEMPLATE_FILE", "false"))%>" _value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("NAME_BY_TEMPLATE_FILE", "false"))%>" name="db:NAME_BY_TEMPLATE_FILE:发布模板名是否带上模板ID:20:0"/><label for="NAME_BY_TEMPLATE_FILE"><b><span WCMAnt:param="config_manager.jsp.namebytemp">模板发布产生的页面不带上模板ID</span></b></label>
				<div class="descdiv" WCMAnt:param="config_manager.jsp.namebytempdesc">说明：模板发布产生的页面是否带上模板ID的设置。</div>
			</div>
			<div class="row"></div>
			<div class="odiv">
				<input type="checkbox" id="ENABLE_LIMIT_PUBLISH_DATE" value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("ENABLE_LIMIT_PUBLISH_DATE", "false"))%>" _value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("ENABLE_LIMIT_PUBLISH_DATE", "false"))%>" name="db:ENABLE_LIMIT_PUBLISH_DATE:控制文档列表是否启用时间限制:20:0"/><label for="ENABLE_LIMIT_PUBLISH_DATE"><b><span WCMAnt:param="config_manager.jsp.limitpubdate">控制文档列表启用时间限制</span></b></label>
				<div class="descdiv" WCMAnt:param="config_manager.jsp.limitpubdatedesc">说明：是否启用在发布的时候，根据站点或栏目上指定的时间限制，发布该时间之后的文档。</div>
			</div>
			<div class="row"></div>
			<div class="odiv">
				<input type="checkbox" id="ONLY_FIRST_ON_PREVIEW" value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("ONLY_FIRST_ON_PREVIEW", "false"))%>" _value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("ONLY_FIRST_ON_PREVIEW", "false"))%>" name="db:ONLY_FIRST_ON_PREVIEW:在预览的时候是否不产生其他页面:20:0"/><label for="ONLY_FIRST_ON_PREVIEW"><b><span WCMAnt:param="config_manager.jsp.onlyfirst">多页预览时只产生第一页</span></b></label>
				<div class="descdiv" WCMAnt:param="config_manager.jsp.onlyfirstdesc">说明：多页预览时是否只产生第一页的设置。</div>
			</div>
			<div class="row"></div>
			<div class="odiv">
				<input type="checkbox" id="PUBLISH_RELDOC_ON_DESTROY" value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("PUBLISH_RELDOC_ON_DESTROY", "false"))%>" _value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("PUBLISH_RELDOC_ON_DESTROY", "false"))%>" name="db:PUBLISH_RELDOC_ON_DESTROY:撤稿的时候，相关文档是否要同步发布:20:0"/><label for="PUBLISH_RELDOC_ON_DESTROY"><b><span WCMAnt:param="config_manager.jsp.destroypub">撤稿的时候，相关文档是否要同步发布</span></b></label>
				<div class="descdiv" WCMAnt:param="config_manager.jsp.destroypubdesc">说明：撤稿的时候，是否更新发布相关文档的细览页面。<br />比如有文档D1、D2，D1是D2的相关文档（即在D2细览上相关新闻显示D1）。<br />该选项的设置控制在撤销发布D1时是否需要更新发布D2的细览。</div>
			</div>
			<div class="row"></div>
			
			<div class="odiv">
				<input type="checkbox" id="START_IMAGE_SITE" value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("START_IMAGE_SITE", "false"))%>" _value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("START_IMAGE_SITE", "false"))%>" name="db:START_IMAGE_SITE:是否启用独立的图片域名:20:0"/><label for="START_IMAGE_SITE"><b><span WCMAnt:param="config_manager.jsp.startimage">启用独立的图片域名</span></b></label>
				<div class="descdiv" WCMAnt:param="config_manager.jsp.startimagedesc">说明：是否启用独立的图片域名，即图片附件发布出来所存放的地址，这是为了对图片附件支持多域名，将通过下面3个配置来指定具体的图片域名。</div>
			</div>
			<div class="row"></div>

			<div id="forpublish" >
				<div class="odiv">
					<label for="IMAGE_SITE_COUNT"><b><span WCMAnt:param="config_manager.jsp.imagecount">设置图片域名数</span></b></label><input type="text" id="IMAGE_SITE_COUNT" value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("IMAGE_SITE_COUNT", ""))%>" _value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("IMAGE_SITE_COUNT", ""))%>" name="db:IMAGE_SITE_COUNT:设置图片域名数:20:0" validation="no_desc:'',type:'int',min:'0',max:'10'"/>
					<div class="descdiv" WCMAnt:param="config_manager.jsp.imagecountdesc">说明：指定有多少个图片域名。</div>
				</div>
				<div class="row"></div>
				<div class="odiv">
					<label for="IMAGE_SITE_PRE"><b><span WCMAnt:param="config_manager.jsp.imagepre">图片域名前缀</span></b></label><input type="text" id="IMAGE_SITE_PRE" value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("IMAGE_SITE_PRE", ""))%>" _value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("IMAGE_SITE_PRE", ""))%>" name="db:IMAGE_SITE_PRE:图片域名指定前缀:20:0" validation="no_desc:'',type:'string'"/>
					<div class="descdiv" WCMAnt:param="config_manager.jsp.imagepredesc">说明：指定图片域名的前缀。如指定为images，则图片域名可能是http://images1.xxx.xx.cn/xxx。</div>
				</div>
				<div class="row"></div>
				<div class="odiv">
					<label for="IMAGE_SITE_MAIN_DOMAIN"><b><span WCMAnt:param="config_manager.jsp.imagedomain">图片域名的主域名</span></b></label><input type="text" id="IMAGE_SITE_MAIN_DOMAIN" value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("IMAGE_SITE_MAIN_DOMAIN", ""))%>" _value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("IMAGE_SITE_MAIN_DOMAIN", ""))%>" name="db:IMAGE_SITE_MAIN_DOMAIN:指定主域名:20:0" validation="no_desc:'',type:'string'"/>
					<div class="descdiv" WCMAnt:param="config_manager.jsp.imagedomaindesc">说明：指定图片域名的主域名。如设置为trs.com.cn，则图片域名可能是http://xxx.trs.com.cn/xxx。</div>
				</div>
				<div class="row"></div>
				<div class="odiv">
					<b><span WCMAnt:param="config_manager.jsp.pubregulation">发布文件的存放规则：</span></b><br/>
					<input type="checkbox" name="xml:com.trs.components.common.publish:config.xml:/plugin/extension/publish-server/detail-page-seperate-to-51style:0" id="" value="<%=getXMLValue("xml:com.trs.components.common.publish:config.xml:/plugin/extension/publish-server/detail-page-seperate-to-51style:0")%>" _value="<%=getXMLValue("xml:com.trs.components.common.publish:config.xml:/plugin/extension/publish-server/detail-page-seperate-to-51style:0")%>"/><b><span WCMAnt:param="config_manager.jsp.detailpage">细览分页直接发布出HTML代码</span></b>&nbsp;&nbsp;
					
					<input type="checkbox" name="xml:com.trs.components.common.publish:config.xml:/plugin/extension/publish-server/dir-divided-by-date:0" id="" value="<%=getXMLValue("xml:com.trs.components.common.publish:config.xml:/plugin/extension/publish-server/dir-divided-by-date:0")%>" _value="<%=getXMLValue("xml:com.trs.components.common.publish:config.xml:/plugin/extension/publish-server/dir-divided-by-date:0")%>"/><b><span WCMAnt:param="config_manager.jsp.pubsep">发布目录用日期分隔</span></b><br/>
					<b><span WCMAnt:param="config_manager.jsp.pubdateformat">发布目录的日期格式：</span></b><input type="text" name="xml:com.trs.components.common.publish:config.xml:/plugin/extension/publish-server/dir-divided-by-date-format:0" id="" value="<%=getXMLValue("xml:com.trs.components.common.publish:config.xml:/plugin/extension/publish-server/dir-divided-by-date-format:0")%>" _value="<%=getXMLValue("xml:com.trs.components.common.publish:config.xml:/plugin/extension/publish-server/dir-divided-by-date-format:0")%>"/>
					<div class="descdiv" WCMAnt:param="config_manager.jsp.pubregulationdesc">说明：<span style="font-size:14px;color:green;">设置后需要重启生效</span> <br /><strong>细览分页是否直接发布出HTML代码：</strong>是否直接发布细览分页导航链接，建议设置为否，在细览模板上控制细览分页导航<br /><strong>发布目录规则：</strong><ol style="margin-left:40px"><li>发布目录用日期分隔，细览页面发布后根据文档创建时间创建子目录存放</li><li>发布目录的日期格式，根据日期格式确定子目录名称。<br/>yyyyMMdd表示取创建时间的年月日组成目录名，如20120607</li></ol></div>
				</div>
				<div class="row"></div>
				<div class="odiv">
					<b><span WCMAnt:param="config_manager.jsp.tempapdext">支持的模板附件后缀名：</span></b><input type="text" validation="no_desc:'',type:'string'" name="xml:com.trs.components.common.publish:config.xml:/plugin/extension/publish-server/template-parse/supported-apd-exts:0" id="" value="<%=getXMLValue("xml:com.trs.components.common.publish:config.xml:/plugin/extension/publish-server/template-parse/supported-apd-exts:0")%>" _value="<%=getXMLValue("xml:com.trs.components.common.publish:config.xml:/plugin/extension/publish-server/template-parse/supported-apd-exts:0")%>"/>
					<div class="descdiv" WCMAnt:param="config_manager.jsp.tempapdextdesc">说明：<span style="font-size:14px;color:green;">设置后需要重启生效，</span>设置支持的模板附件后缀名。</div>
				</div>
				<div class="row"></div>

				<div class="odiv">
					<b><span WCMAnt:param="config_manager.jsp.tempext">支持的模板后缀名：</span></b><input type="text" validation="no_desc:'',type:'string'" name="xml:com.trs.components.common.publish:config.xml:/plugin/extension/publish-server/template-parse/supported-exts:0" id="" value="<%=getXMLValue("xml:com.trs.components.common.publish:config.xml:/plugin/extension/publish-server/template-parse/supported-exts:0")%>" _value="<%=getXMLValue("xml:com.trs.components.common.publish:config.xml:/plugin/extension/publish-server/template-parse/supported-exts:0")%>"/>
					<div class="descdiv" WCMAnt:param="config_manager.jsp.tempextdesc">说明：<span style="font-size:14px;color:green;">设置后需要重启生效，</span>设置支持的模板后缀名。</div>
				</div>
				<div class="row"></div>
			</div>
		</div>

		<div class="tab-body" id="tab-flow">
			<div style="height:15px;"></div>
			<div class="odiv">
				<input type="checkbox" id="FLOW_RIGHT_PRI" value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("FLOW_RIGHT_PRI", "false"))%>" _value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("FLOW_RIGHT_PRI", "false"))%>" name="db:FLOW_RIGHT_PRI:是否启用了工作流权限优先:20:0"/><label for="FLOW_RIGHT_PRI"><b><span WCMAnt:param="config_manager.jsp.flowpri">启用工作流权限优先</span></b></label>
				<div class="descdiv" WCMAnt:param="config_manager.jsp.flowpridesc">说明：设置是否启用工作流权限优先。</div>
			</div>
			<div class="row"></div>
			<div class="odiv">
				<input type="checkbox" id="ONLY_ADMIN_CTR_FLOW" value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("ONLY_ADMIN_CTR_FLOW", "false"))%>" _value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("ONLY_ADMIN_CTR_FLOW", "false"))%>" name="db:ONLY_ADMIN_CTR_FLOW:是否只有管理员可以强制结束流转:20:0"/><label for="ONLY_ADMIN_CTR_FLOW"><b><span WCMAnt:param="config_manager.jsp.adminflow">只有管理员可以强制结束流转</span></b></label>
				<div class="descdiv" WCMAnt:param="config_manager.jsp.adminflowdesc">说明：设置是否只有管理员可以强制结束工作流的流转。</div>
			</div>
			<div class="row"></div>
		</div>

		<div class="tab-body" id="tab-email">
			<div style="height:15px;"></div>
			<div class="odiv">
				<b><span WCMAnt:param="config_manager.jsp.emailserver">邮件服务器信息：</span></b><br/>
				<b><span WCMAnt:param="config_manager.jsp.emailaddr">邮件服务器地址：</span></b><input type="text" validation="no_desc:'',type:'string'" name="xml:com.trs.infra:config.xml:/plugin/extension/smtp-servers/smtp-server:1:name" id="" value="<%=getXMLValue("xml:com.trs.infra:config.xml:/plugin/extension/smtp-servers/smtp-server:1:name")%>" _value="<%=getXMLValue("xml:com.trs.infra:config.xml:/plugin/extension/smtp-servers/smtp-server:1:name")%>"/>
				<b><span WCMAnt:param="config_manager.jsp.emailuser">用户名：</span></b><input type="text" validation="no_desc:'',type:'string'" name="xml:com.trs.infra:config.xml:/plugin/extension/smtp-servers/smtp-server:1:user" id="" value="<%=getXMLValue("xml:com.trs.infra:config.xml:/plugin/extension/smtp-servers/smtp-server:1:user")%>" _value="<%=getXMLValue("xml:com.trs.infra:config.xml:/plugin/extension/smtp-servers/smtp-server:1:user")%>"/>
				<b><span WCMAnt:param="config_manager.jsp.emailpsw">密码：</span></b><input type="password" validation="no_desc:'',type:'string'" autocomplete="off"  name="xml:com.trs.infra:config.xml:/plugin/extension/smtp-servers/smtp-server:1:password" id="" value="<%=getXMLValue("xml:com.trs.infra:config.xml:/plugin/extension/smtp-servers/smtp-server:1:password")%>" _value="<%=getXMLValue("xml:com.trs.infra:config.xml:/plugin/extension/smtp-servers/smtp-server:1:password")%>"/>
				<div class="descdiv" WCMAnt:param="config_manager.jsp.emailserverdesc">说明：<span style="font-size:14px;color:green;">设置后需要重启生效，</span>对发送的邮件服务器地址，发送邮件服务器的用户名，发送邮件服务器的密码的设置。</div>
			</div>
			<div class="row"></div>
		</div>

		<div class="tab-body" id="tab-plugin">
			<div style="height:15px;"></div>
			<div class="odiv">
				<input type="checkbox" id="CLASS_DOCUMENTS_ONLY_LEAF" value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("CLASS_DOCUMENTS_ONLY_LEAF", "false"))%>" _value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("CLASS_DOCUMENTS_ONLY_LEAF", "false"))%>" name="db:CLASS_DOCUMENTS_ONLY_LEAF:是否需要包含所有子分类的文档:20:0"/><label for="CLASS_DOCUMENTS_ONLY_LEAF"><b><span WCMAnt:param="config_manager.jsp.classleaf">包含所有子分类的文档</span></b></label>
				<div class="descdiv" WCMAnt:param="config_manager.jsp.classleafdesc">说明：TRS_CLASSDOCUMENTS置标取分类法数据时，是否取所有子分类法下的文档。</div>
			</div>
			<div class="row"></div>
			<div class="odiv">
				<b><span WCMAnt:param="config_manager.jsp.infoviewid">新规则起始的ID配置：</span></b><input type="text" id="" validation="no_desc:'',type:'int',min:'-1'" value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("NEWRULE_FROM_INFOVIEWID", ""))%>" _value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("NEWRULE_FROM_INFOVIEWID", ""))%>" name="db:NEWRULE_FROM_INFOVIEWID:新规则起始的id配置:20:0"/>
				<div class="descdiv" WCMAnt:param="config_manager.jsp.infoviewiddesc">说明：表单升级后，表单字段在数据库中的字段名可以显示为中文拼音，设置启用这个规则的起始表单ID。</div>
			</div>
			<div class="row"></div>
			<div id="forplugin" style="display:none;">
				<div class="odiv">
					<b><span WCMAnt:param="config_manager.jsp.parentindex">场景式服务入口首页模板名称：</span></b><input type="text" id="" validation="no_desc:'',type:'string'" value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("PARENT_INDEX", ""))%>" _value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("PARENT_INDEX", ""))%>" name="db:PARENT_INDEX:场景式服务入口首页模板名称:20:0"/>
					<div class="descdiv" WCMAnt:param="config_manager.jsp.parentindexdesc">说明：场景式服务入口首页模板名称的设置。</div>
				</div>
				<div class="row"></div>
				<div class="odiv">
					<b><span WCMAnt:param="config_manager.jsp.childindex">场景式服务默认首页模板名称：</span></b><input type="text" id="" validation="no_desc:'',type:'string'" value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("CHILD_INDEX", ""))%>" _value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("CHILD_INDEX", ""))%>" name="db:CHILD_INDEX:场景式服务默认首页模板名称:20:0"/>
					<div class="descdiv" WCMAnt:param="config_manager.jsp.childindexdesc">说明：场景式服务默认首页模板名称的设置。</div>
				</div>
				<div class="row"></div>
				<div class="odiv">
					<b><span WCMAnt:param="config_manager.jsp.childother">场景式服务的信息列表模板名称：</span></b><input type="text" id="" validation="no_desc:'',type:'string'" value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("CHILD_OTHER", ""))%>" _value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("CHILD_OTHER", ""))%>" name="db:CHILD_OTHER:场景式服务的信息列表模板名称:20:0"/>
					<div class="descdiv" WCMAnt:param="config_manager.jsp.childotherdesc">说明：场景式服务的信息列表模板名称的设置。</div>
				</div>
				<div class="row"></div>
				<div class="odiv">
					<b><span WCMAnt:param="config_manager.jsp.parentother">场景式服务的流程XML模板名称：</span></b><input type="text" id="" validation="no_desc:'',type:'string'" value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("PARENT_OTHER", ""))%>" _value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("PARENT_OTHER", ""))%>" name="db:PARENT_OTHER:场景式服务的流程XML模板名称:20:0"/>
					<div class="descdiv" WCMAnt:param="config_manager.jsp.parentotherdesc">说明：场景式服务的流程XML模板名称的设置。</div>
				</div>
				<div class="row"></div>
				<div class="odiv">
					<b><span WCMAnt:param="config_manager.jsp.generaldetail">场景式服务信息通用细缆模板名称：</span></b><input type="text" id="" validation="no_desc:'',type:'string'" value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("GENERAL_DETAIL", ""))%>" _value="<%=CMyString.showNull(ConfigServer.getServer().getSysConfigValue("GENERAL_DETAIL", ""))%>" name="db:GENERAL_DETAIL:场景式服务信息通用细缆模板名称:20:0"/>
					<div class="descdiv" WCMAnt:param="config_manager.jsp.generaldataildesc">说明：场景式服务信息通用细缆模板名称的设置。</div>
				</div>
			</div>
		</div>
	
		<div class="tab-body" id="tab-license">
			<div style="height:15px;"></div>
			<div class="odiv">
				<b><span WCMAnt:param="config_manager.jsp.license">注册码：</span></b><input style="width:597px" type="text"  name="license" id="license"  validation="no_desc:'',type:'string'"  value="<%=license%>" _value="<%=license%>"/>
				<div class="descdiv" WCMAnt:param="config_manager.jsp.licensedesc">说明：<span style="font-size:14px;color:green;">设置后需要重启生效</span>，对WCM产品注册码的设置。<p>注意：<span style="font-size:14px;color:green;">若访问的是集群环境请谨慎修改，注册码修改可能不成功。</span></div>
			</div>
			<div class="odiv">
				<input  type="hidden"  name="orignlicense" id="orignlicense" value="<%=license%>" _value="<%=license%>" />
			</div>
			<div class="row"></div>
		</div>
	</div>
</div>
<div style="text-align:center"><input type="submit" name="" onclick="dosub();" style="width:70px;" id="save" value="保存" _value="保存" WCMAnt:param="value:config_manager.jsp.submitsave"/>&nbsp;<input type="button" id="btcancel" style="display:none;" WCMAnt:paramattr="value:template_add_edit.jsp.close" style="width:70px;" value="关闭" _value="关闭" onclick="window.close(); return false;"></div>
</form>
<script language="javascript">
<!--
	Event.observe($('sendemail'),'click',function(){
		if($('sendemail').checked){
			$('email_box').style.display='';
		}else{
			$('email_box').style.display='none';
		}
	});
	Event.observe($('puber'),'click',function(){
		var sUser = $("user").value;
		var sPuber = $('puber').value;
		if($('puber').checked){
			$("user").value = sUser + "," + sPuber;
		}else{
			//输入框去掉这个串
			var nIndex = sUser.indexOf(sPuber);
			if(sUser.charAt(nIndex-1)==","){
				$("user").value = sUser.substring(0,sUser.length-sPuber.length-1);
			}else{
				$("user").value = sUser.substring(nIndex+sPuber.length+1,sUser.length);
			}
		}
	})
	Event.observe(window,'load',function(){
		if(getParameter('first')){
			$('btcancel').style.display='';
			$('first').value = 1;
		}
	})
//-->
</script>
</body>
</html>