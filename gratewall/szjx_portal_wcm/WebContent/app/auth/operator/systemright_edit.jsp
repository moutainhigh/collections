<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<%@ page import="com.trs.cms.auth.persistent.Role"%>
<%@ page import="com.trs.cms.auth.config.OperationConfig" %>
<%@ page import="com.trs.cms.auth.config.OperationRelatedConfig" %>
<%@ page import="com.trs.cms.auth.config.RightConfigServer" %>
<%@ page import="com.trs.infra.config.XMLConfigServer" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CMyBitsValue"%>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@include file="../../include/public_server.jsp"%>

<%
	int nOperId = currRequestHelper.getInt("OperId",0);
	int nOperType = currRequestHelper.getInt("OperType",0); 
	String sRightIndex = currRequestHelper.getString("RightIndex");
	//权限校验
	if(!loginUser.isAdministrator()){
		throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT,CMyString.format(LocaleServer.getString("systemright_edit.jsp.havenoright", "对不起，您没有权限设置系统级角色[{0}]的权限！"), new String[]{Role.findById(nOperId).getName()}));
	}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../../../style/style.css" rel="stylesheet" type="text/css">
<title WCMAnt:param="systemright_edit.jsp.title">TRS WCM操作者视图权限设置</title>
<style type="text/css">
	fieldset{
	border: 1px dashed #0066cc;
	width:500px;
	height:600px;
	}
	select{
		width: 220px;
		height: 500px;
	}
	optgroup{
		font-weight:500;
		font-style: normal;
		font-family:"宋体";
		color: #996666;
	}
	option{
		font-style: normal;
		font-family:"宋体";
		color: black;
	}
	div.head{
		font-style:normal;
		font: normal 16px "宋体";
		color:#000000;
		word-spacing:10px;
	}
</style>
<script language="javascript" src="systemright_edit.js" type="text/javascript"></script>
<script language="javascript" src="../../../console/js/CTRSArray.js" type="text/javascript"></script>
<script language="javascript" src="../../../console/js/CTRSButton.js"></script>
<script language="javascript" src="../../../console/js/CWCMDialogHead.js" type="text/javascript"></script>
<script language="javascript">
<!--
	<%writeRightDefsScript(out);%>


	//指定位置插入option
	function addOptionAt(_oSelect,_sOption){
		var oOptGrps = _oSelect.children;
		var oOptGrp ="";
		for(var i=0;i<oOptGrps.length;i++){
			oOptGrp = oOptGrps[i];
			if(oOptGrp.id == _sOption.className){
				oOptGrp.appendChild(_sOption);
			}
		}
	}

	//添加、删除option
	function moveOption(_oSelect1,_oSelect2,_bInit){
		var nOperatorIndexArray =new Array();
		var oOptions = new Array();
		var oOptGroups = new Array();
		var oOption;
		var nOperatorIndex;
		var sClassName;
		var oConfigs;
		var oConfig;
		var oOptGroup;
		var bInit = _bInit?_bInit:false;
		for(var i=0;i<_oSelect1.options.length;i++){
			if(_oSelect1.options[i].selected){
				oOption = _oSelect1.options[i];
				nOperatorIndex = oOption.value;
				if(nOperatorIndexArray.length == 0) nOperatorIndexArray.push(nOperatorIndex);
				else if(!TRSArray.isExist(nOperatorIndexArray,nOperatorIndex)){ 
					nOperatorIndexArray.push(nOperatorIndex);
				}
				//添加时添加同权限值的和依赖的权限值
				if(_oSelect1.getAttribute("whichside") == "Lst" ){
					var arrSameRights = wcm.Rights.getIndexConfigs(nOperatorIndex);
					for(var oOpt in arrSameRights){
						var sim = arrSameRights[oOpt]
						var depends = sim.Depends; 
						if(!depends) continue;
						//如果是初始化，则不获取“高级发布站点”和“高级发布栏目”依赖的权限值
						if(bInit && (sim.Index==4 || sim.Index==16)) continue;
						nOperatorIndexArray = nOperatorIndexArray.concat(depends.split(","));
					}
				}
				//删除时删除同权限值的和相似的。（相似定义：依赖这个权限值的权限）
				if(_oSelect1.getAttribute("whichside") == "Rst" ){
					var arrSameRights = wcm.Rights.getIndexConfigs(nOperatorIndex);
					for(var oOpt in arrSameRights){
						var sim = arrSameRights[oOpt]
						var similars = sim.Similars; 
						if(!similars) continue;
						nOperatorIndexArray = nOperatorIndexArray.concat(similars.split(","));
					} 
				}
			}
		}

		nOperatorIndexArray = TRSArray.distinct(nOperatorIndexArray);

		//nOperatorIndexArray中的所有元素对应的option都设为选中，并将option存入oOptions中
		for(i=0;i<nOperatorIndexArray.length;i++){
			nOperatorIndex = nOperatorIndexArray[i];
			for(j=0;j<_oSelect1.options.length;j++){
				oOption = _oSelect1.options[j];
				if(oOption.value == nOperatorIndex){
					oOption.selected = true;
					oOptions.push(oOption);
				}
			}
		}

		//为_oSelect2添加oOptions
		for(i=0;i<oOptions.length;i++){
			oOption = oOptions[i];
			sClassName = oOption.className;
			setGroup(_oSelect2,sClassName);
			try{
				addOptionAt(_oSelect2,oOption);
			}catch(error){
				//just skip it.
			}

		}

		//删除_oSelect1中的空的分组
		oOptGroups = _oSelect1.children;
		for(i=oOptGroups.length-1;i>=0;i--){
			oOptGroup = oOptGroups[i];
			if(oOptGroup.children.length == 0) _oSelect1.removeChild(oOptGroup);
		}
	}

	//判断数组nArray中是否包含nIndex
	function include(_nArray,_nIndex){
		var bExist = false;
		for(var i=0;i<_nArray.length;i++){
			if(_nIndex == _nArray[i]){
				bExist = true;
				break;
			}
		}
		return bExist;
	}

	function cancelSelected(_oSelect1){
		for(var k=0;k<_oSelect1.options.length;k++){
			if(_oSelect1.options[k].selected){
				_oSelect1.options[k].selected = false;
			}
		}
	}

	//设置操作所在的分组
	function setGroup(_oSelect,_sClassName){
		var oOption;
		var sClassName;
		if(_oSelect.options.length){//如果已存在该类型，则直接返回
			for(var k=0;k<_oSelect.options.length;k++){
				oOption = _oSelect.options[k];
				sClassName = oOption.className;
				if(sClassName == _sClassName) return;
			}
		}

		//分组不存在，则添加optgroup
		var oGroup = document.createElement('optgroup');
		switch(_sClassName){
			case 'Site': 
				oGroup.id = "Site";
				oGroup.label = "<%=LocaleServer.getString("auth.label.operatesite","站点类操作权限")%>";
				break;
			case 'Channel':
				oGroup.id = "Channel";
				oGroup.label = "<%=LocaleServer.getString("auth.label.operatechannel","栏目类操作权限")%>";
				break;
			case 'Template':
				oGroup.id = "Template";
				oGroup.label = "<%=LocaleServer.getString("auth.label.operatetemplate","模版类操作权限")%>";
				break;
			case 'Document': 
				oGroup.id = "Document";
				oGroup.label = "<%=LocaleServer.getString("auth.label.operatedocument","文档类操作权限")%>";
				break;
			case 'Flow': 
				oGroup.id = "Flow";
				oGroup.label = "<%=LocaleServer.getString("auth.label.operateflow","工作流操作权限")%>";
				break;
		}
		_oSelect.appendChild(oGroup);
	}

	//获取需要保存的操作
	function getOperationIndex(_oSelect){
		var sOperationIndex="";
		for(var i=0;i<_oSelect.options.length;i++){
			if(sOperationIndex == "") sOperationIndex+=_oSelect.options[i].value;
			else{
				sOperationIndex+= ","+_oSelect.options[i].value;
			}
		}
		document.getElementById("frmAction").RightIndex.value = (sOperationIndex == "" || sOperationIndex == null)? "0":sOperationIndex;
		document.getElementById("frmAction").submit();
		//alert("权限设置成功！");
	}
	
	//获取左侧select的options
	function getLeftOptions(_sClassName){
		var oConfigs = wcm.Rights.getTypeConfigs(_sClassName);
		var oConfig;
		var str = new Array();
		for(var i=0;i<oConfigs.length;i++){
			oConfig = oConfigs[i];
			if(oConfig['Index'] == 14){//排除浏览站点的权限
				continue;
			}
			str.push("<option value=",oConfig['Index']," class=",oConfig['Type'],">"+oConfig['Desc'],"</option>");
		}
		return str.join("");
	}
	
	function drawLeftSelect(){
		var str1 = new Array();
		str1 =["<select multiple name='st1' id='st1' whichside='Lst' ondblclick='cancelSelected(document.myform.st2);moveOption(document.myform.st1,document.myform.st2)'><optgroup id='Site' label=<%=LocaleServer.getString("auth.label.operatesite","站点类操作权限")%>>",
		getLeftOptions("Site"),
		"</optgroup><optgroup id='Channel' label=<%=LocaleServer.getString("auth.label.operatechannel","栏目类操作权限")%>>",
		getLeftOptions("Channel"),
		"</optgroup><optgroup id='Document' label=<%=LocaleServer.getString("auth.label.operatedocument","文档类操作权限")%>>",
		getLeftOptions("Document"),
		"</optgroup><optgroup id='Template' label=<%=LocaleServer.getString("auth.label.operatetemplate","模版类操作权限")%>>",
		getLeftOptions("Template"),
		"</optgroup><optgroup id='Flow' label=<%=LocaleServer.getString("auth.label.operateflow","工作流操作权限")%>>",
		getLeftOptions("Flow"),
		"</optgroup></select>"
		].join("");
		document.getElementById("Lst").innerHTML=str1;
	}

	function drawRightSelect(_oLeftSelect,_oRightSelect){
		<%
			int[] index = findIndex(nOperId,nOperType);

			for(int i=0;i<index.length;i++){
				if(index[i]!=0){
		%>
			var oOption;
			for(var j=0;j<_oLeftSelect.options.length;j++){
				oOption = _oLeftSelect.options[j];
				if(<%=index[i]%> == oOption.value){
					oOption.selected = true;
				}
			}
		<%}}%>

		moveOption(_oLeftSelect,_oRightSelect,true);
		cancelSelected(_oRightSelect);

	}

	function bodyOnLoad(){
		document.getElementById("st1").scrollTop = 0;
	}

//-->
</script>
</head>
<body onload="bodyOnLoad()">
<FORM NAME="frmAction" ID="frmAction" METHOD=POST ACTION="systemright_edit_dowith.jsp" style="margin-top:0">
	<input name="OperId" type="hidden" value="<%=nOperId%>">
	<input name="OperType" type="hidden" value="<%=nOperType%>">
	<input name="RightIndex" type="hidden" id="RightIndex"value="">
</FORM>
    <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
	<td height="25">
		<script language="JavaScript">
			WCMDialogHead.draw("<%=LocaleServer.getString("role.label.setright", "设置权限")%>",true);
		</script>	    
	</td>
    </tr>
    <tr>
	<td align="center" valign="top" class="tanchu_content_td">
	    <table width="100%" border="0" cellspacing="0" cellpadding="10">		
	    <tr>
		<td class="tanchu_content">
		<form id="myform" method=post action="" name="myform">
			<div align="center" class="head">
				<strong><font color="#0066cc">
				<%
					out.print(CMyString.format(LocaleServer.getString("systemright_edit.jsp.setrolesright","设置<font color='red'>【{0}】</font>的权限"),new String[] {Role.findById(nOperId).getName()}));
				%></font></strong>
			</div>
			<hr/>
			<table width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr>
					<td width="40%" align="center"> 
						<%=LocaleServer.getString("role.tip.selectopration","从操作列表中选择:")%>
						<div id="Lst">
							<script language="javascript">
							<!--
								drawLeftSelect();
							//-->
							</script>
						</div>
					</td>
					<td align="center" valign="middle">
					<script language="javascript">
					<!--
						var oTRSButtons = new CTRSButtons();
						oTRSButtons.nType = TYPE_ROMANTIC_BUTTON;

						oTRSButtons.addTRSButton("<%=LocaleServer.getString("role.button.addoperation","添加>>")%>", "cancelSelected(document.myform.st2);moveOption(document.myform.st1,document.myform.st2)");

						oTRSButtons.draw();
					//-->
					</script>
						<br/><br/><br/><br/>
					<script language="javascript">
					<!--
						var oTRSButtons = new CTRSButtons();
						oTRSButtons.nType = TYPE_ROMANTIC_BUTTON;

						oTRSButtons.addTRSButton("<%=LocaleServer.getString("role.button.dropoperation","<<删除")%>", "cancelSelected(document.myform.st1);moveOption(document.myform.st2,document.myform.st1)");

						oTRSButtons.draw();
					//-->
					</script>
					</td>
					<td width="40%" align="center">
						<%=LocaleServer.getString("role.tip.operationlist","可做的操作:")%>
						<div id="Rst" class="">
						<select name="st2" id="st2" whichside="Rst" multiple ondblclick='cancelSelected(document.myform.st1);moveOption(document.myform.st2,document.myform.st1)'></select>
						<script language="javascript">
						<!--
							drawRightSelect(document.myform.st1,document.myform.st2);
						//-->
						</script>
						</div>
					</td>	
				</tr>
		    </table>
		</form>
		</td>
	    </tr>
	    <tr>
		<td align="center">
		<script language="javascript">
		<!--
			var oTRSButtons = new CTRSButtons();
			
			oTRSButtons.cellSpacing = "0";
			oTRSButtons.nType = TYPE_ROMANTIC_BUTTON;

			oTRSButtons.addTRSButton("<%=LocaleServer.getString("role.button.okbutton", "确定")%>", "getOperationIndex(document.myform.st2);");
			oTRSButtons.addTRSButton("<%=LocaleServer.getString("role.button.cancelbutton", "取消")%>", "window.top.close();");
			
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
		int []index =new int[65];
		try{

			//获取当前角色
			Role role = null;
			role = Role.findById(_nOperId);

			//获取当前角色的权限
			long lValue = 0;
			boolean bl = false;
			if(role != null){
				lValue = role.getRightValue();
				//当权限值不为-1时，才执行此操作
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