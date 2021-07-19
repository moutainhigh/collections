<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<%@ page import="com.trs.cms.auth.persistent.Role" %>
<%@ page import="com.trs.cms.auth.config.OperationConfig" %>
<%@ page import="com.trs.cms.auth.config.OperationRelatedConfig" %>
<%@ page import="com.trs.cms.auth.config.RightConfigServer" %>
<%@ page import="com.trs.infra.config.XMLConfigServer" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CMyBitsValue"%>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@include file="../../include/public_server.jsp"%>
<%
	int nOperId = currRequestHelper.getInt("OperId",0);
	int nOperType = currRequestHelper.getInt("OperType",0);
%>
<html>
<head>
	<title WCMAnt:param="systemright_view.jsp.title">TRS WCM操作者视图权限查看</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<LINK href="../../../style/style.css" rel="stylesheet" type="text/css">
	<style type="text/css">
		fieldset{
			border: 1px dashed #0066cc;

		}
		div.out{
			padding:0;
			margin:0px 50px 10px 50px;
			border:3px double gray;
			overflow-y:auto;
			height:380px;
			width:230px
		}
		div.type{
			font:italic bold 14px;
			padding:0;
			color:#996666;			
		}
		div{
			font-style:normal;
			font: normal 12px "宋体";
			padding:0 20;
			color:#000000;
		}
		div.head{
			font-style:normal;
			font: normal 16px "宋体";
			color:#000000;
			word-spacing:10px;
		}
	</style>
	<script language="javascript" src="systemright_edit.js" type="text/javascript"></script>
	<script language="javascript" src="../../../console/js/CWCMDialogHead.js" type="text/javascript"></script>
	<script language="javascript" src="../../../console/js/CTRSButton.js"></script>
	<script language="javascript">
	<!--
		<%writeRightDefsScript(out);%>

		function addDiv(_sType,_sInnerText){
			var oDiv = document.getElementById(_sType);
			if(!oDiv) return -1;
			var oChildNode = document.createElement("div");
			oDiv.appendChild(oChildNode);
			oChildNode.innerHTML = _sInnerText;
		}

		function drawDiv(){
			var nOperatorIndexArray = new Array();
			var nOperatorIndex;
			var configs;
			var config;
			var sType;
			var sInnerText;
			<%
				int[] nIndexArray = findIndex(nOperId,nOperType);
				for(int i=0;i<nIndexArray.length;i++){
					if(nIndexArray[i] != 0){
			%>
				nOperatorIndexArray.push(<%=nIndexArray[i]%>);
			<%}}%>

			for(var i=0;i<nOperatorIndexArray.length;i++){
				nOperatorIndex = nOperatorIndexArray[i];
				configs = wcm.Rights.getIndexConfigs(nOperatorIndex);
				if(!configs){
					continue;
				}
				for(var j=0;j<configs.length;j++){
					config = configs[j];
					if(config['Index'] == 14){//排除浏览站点的权限
						continue;
					}
					sType = config['Type'];
					sInnerText = config['Desc'];
					if(addDiv(sType,sInnerText) == -1) continue;
				}
			}
			//如果类型下的操作的数目为0，则隐藏该类型
			var oDiv;
			oDiv = document.getElementById("Site");
			if(oDiv.childNodes.length == 1) oDiv.style.display = "none";
			oDiv = document.getElementById("Channel");
			if(oDiv.childNodes.length == 1) oDiv.style.display = "none";
			oDiv = document.getElementById("Template");
			if(oDiv.childNodes.length == 1) oDiv.style.display = "none";
			oDiv = document.getElementById("Document");
			if(oDiv.childNodes.length == 1) oDiv.style.display = "none";
			oDiv = document.getElementById("Flow");
			if(oDiv.childNodes.length == 1) oDiv.style.display = "none";
		}
	//-->
	</script>
</head>

<body>
    <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
	<td height="25">
		<SCRIPT language="JavaScript">
			WCMDialogHead.draw("<%=LocaleServer.getString("role.label.viewright", "查看权限")%>",true);
		</SCRIPT>	    
	</td>
    </tr>
    <tr>
	<td align="center" valign="top" class="tanchu_content_td">
	    <table width="100%" border="0" cellspacing="0" cellpadding="10">	
	    <tr>
		<td class="tanchu_content">
		    <table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr>
				<td align="center">
			
					<div align="center" class="head">
						<strong><font color="#0066cc">
						<% 
							out.println(CMyString.format(LocaleServer.getString("systemright_view.jsp.haverightare", "<font color='red'>【{0}】</font>拥有的权限"), new String[]{Role.findById(nOperId).getName()}));
						%></font></strong>
					</div>
					<div align="left" id="out" class="out">
						<div id="Site" class="type"><%=LocaleServer.getString("auth.label.operatesite","站点类操作权限")%></div>
						<div id="Channel" class="type"><%=LocaleServer.getString("auth.label.operatechannel","栏目类操作权限")%></div>
						<div id="Document" class="type"><%=LocaleServer.getString("auth.label.operatedocument","文档类操作权限")%></div>
						<div id="Template" class="type"><%=LocaleServer.getString("auth.label.operatetemplate","模版类操作权限")%></div>
						<div id="Flow" class="type"><%=LocaleServer.getString("auth.label.operateflow","工作流操作权限")%></div>
					</div>

						<script language="javascript">
						<!--
							drawDiv();
						//-->
						</script>
				</td>
		    </tr>
		    </table>
		</td>
	    </tr>
	    <tr>
		<td align="center">
		<SCRIPT SRC="../js/CTRSButton.js"></SCRIPT>
		<script language="javascript">
		<!--
			var oTRSButtons = new CTRSButtons();
			
			oTRSButtons.nType = TYPE_ROMANTIC_BUTTON;

			oTRSButtons.addTRSButton("<%=LocaleServer.getString("role.button.close", "关闭")%>", "window.top.close();");

			oTRSButtons.draw();
		//-->
		</script>
		</td>
	    </tr>
	    </table>
	</td>
    </tr>
    </table>
</body>
</html>

<%!
	public int[] findIndex(int _nOperId,int _nOperType){
		int []index =new int[64];
		try{

			//获取当前角色
			Role role = null;
			role = Role.findById(_nOperId);

			//获取当前角色的权限
			long lValue = 0;
			boolean bl = false;
			if(role != null){
				lValue = role.getRightValue();
				if(lValue != -1){
					for(int i=0;i<index.length;i++){
						bl = CMyBitsValue.getBit(lValue,i);
							if(bl){
							//保存index的值
								index[i] = i;
							}
					}
				}
			}
			return index;
		}
		catch(WCMException e){
		}
		return index;
	}

	private void writeRightDefsScript(JspWriter out) throws Exception {
        XMLConfigServer oXMLConfigServer = XMLConfigServer.getInstance();
        List listOperation = oXMLConfigServer
                .getConfigObjects(OperationConfig.class);
        for (int i = 0, nSize = listOperation.size(); i < nSize; i++) {
            OperationConfig operationConfig = (OperationConfig) listOperation
                    .get(i);
            if (operationConfig == null)
                continue;

			String sDepends = getDepends(operationConfig), sSimilar = getSimilar(operationConfig);
            
			out.println("wcm.Rights.add({"
				+ "\n\tIndex:" + operationConfig.getIndex()
				+ ",\n\tName:'" + CMyString.filterForJs(operationConfig.getDispName())
				+ "',\n\tDesc:'" + CMyString.filterForJs(CMyString.showNull(operationConfig.getDesc()))
				+ "',\n\tType:'" + CMyString.showNull(operationConfig.getType())
				+ "',\n\tDepends:" + sDepends
				+ ",\n\tSimilars:" + sSimilar
			+"\n});");
        }
    }

	private String getSimilar(OperationConfig operationConfig){
        int[] pResult = RightConfigServer.getInstance().getSimilarIndexs(
                operationConfig.getIndex());
        if(pResult == null || pResult.length == 0)return null;
        
        int nSize = pResult.length;
        StringBuffer sbResult = new StringBuffer(nSize*4+2);
        sbResult.append("\"");
        boolean bFirst = true;
        for (int i = 0; i < nSize; i++) {            
            if (pResult[i] == 0)
                continue;
            
            if(bFirst){                
                bFirst = false;
            }else{
                sbResult.append(",");
            }
            sbResult.append(pResult[i]);
        }
        sbResult.append("\"");
        if(bFirst)return null;
        return sbResult.toString();
    }
    
    private String getDepends(OperationConfig operationConfig) {
        OperationRelatedConfig includes = operationConfig
                .getDepends();
        if (includes == null)
            return null;        
        
        ArrayList elements = includes.getOperations();
        int nSize = elements.size();
        StringBuffer sbResult = new StringBuffer(nSize*4);
        boolean bFirst = true;
        sbResult.append("\"");
        for (int i = 0; i < nSize; i++) {
            OperationConfig includeOperation = (OperationConfig) elements
                    .get(i);
            if (includeOperation == null)
                continue;
            
            if(bFirst){                
                bFirst = false;
            }else{
                sbResult.append(",");
            }
            sbResult.append(includeOperation.getIndex());
        }
        sbResult.append("\"");
        return sbResult.toString();
    }

%>