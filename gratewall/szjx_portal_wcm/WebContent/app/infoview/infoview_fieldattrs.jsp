<%--
//WCM5.2 自定义表单字段扩展属性维护界面。
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%-- ----- WCM IMPORTS BEGIN ---------- --%>
<%@ page import="com.trs.components.infoview.persistent.InfoView" %>
<%@ page import="com.trs.components.infoview.InfoViewMgr" %>
<%@ page import="com.trs.components.infoview.InfoViewConfig" %>
<%@ page import="com.trs.components.infoview.config.BindingBean" %>
<%@ page import="com.trs.components.infoview.config.GatewayConfig" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewView" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewViews" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewField" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewFields" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.IInfoViewService" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Vector" %>
<%-- ----- WCM IMPORTS END ---------- --%>
<%@include file="./infoview_public_include.jsp"%>
<%-- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --%>
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
// 初始化相关的描述
	aDataDisplayNames = new String[]{LocaleServer.getString("infoview.fieldattrs.char","字符型"),LocaleServer.getString("infoview.fieldattrs.integer","整型"),LocaleServer.getString("infoview.fieldattrs.float","精度小数型"),LocaleServer.getString("infoview.fieldattrs.secondpattern","二进制型"),LocaleServer.getString("infoview.fieldattrs.boolean","是非型")};

	aDataTypes = new String[]{"string","int","double","base64Binary","boolean"};

	 m_aFieldTypes = new String[]{"InlineImage","LinkedImage","FileAttachment","RichText","PlainText","DropDown","DTPicker_DTText","RepeatingTable",
		"ListItem_Plain","Section","CheckBox","OptionButton","ListBox","ExpressionBox"};

	m_aFieldDisplayNames = new String[]{LocaleServer.getString("infoview.fieldattrs.photoapp","图片附件"),LocaleServer.getString("infoview.fieldattrs.photoapp","图片附件"),LocaleServer.getString("infoview.fieldattrs.appadix","附件"),LocaleServer.getString("infoview.fieldattrs.edit","文本编辑器"),LocaleServer.getString("infoview.fieldattrs.input","输入框"),LocaleServer.getString("infoview.fieldattrs.downlist","下拉框"),LocaleServer.getString("infoview.fieldattrs.data","日期控件"),LocaleServer.getString("infoview.fieldattrs.reppet","重复表格"),
		LocaleServer.getString("infoview.fieldattrs.reppettype","重复项"),LocaleServer.getString("infoview.fieldattrs.value","数据节"),LocaleServer.getString("infoview.fieldattrs.checkbox","复选框"),LocaleServer.getString("infoview.fieldattrs.dadio","单选框"),LocaleServer.getString("infoview.fieldattrs.combox","多选下拉框"),LocaleServer.getString("infoview.fieldattrs.express","表达式")};

	m_aValidTypes = new String[]{"string","email","ip","common_char","common_char2","url"/*,"date"*/};
	
	m_aValidDisplayNames = new String[]{LocaleServer.getString("infoview.fieldattrs.zidingyi","自定义类型"),"Email",LocaleServer.getString("infoview.fieldattrs.inadd","IP地址"),LocaleServer.getString("infoview.fieldattrs.startletternumxiahua","以字母开头的字母,数字,下划线"),LocaleServer.getString("infoview.fieldattrs.letternumxiahua","字母,数字,下划线"),LocaleServer.getString("infoview.fieldattrs.normallink","普通链接")/*,"日期型"*/};
	

%>
<%
	int nInfoViewId = currRequestHelper.getInt("InfoViewId", 0);
	if (nInfoViewId <= 0){
		throw new WCMException(LocaleServer.getString("infoview_fieldattrs.id.zero","InfoViewId为0，无法找到InfoView！"));
	}
	InfoView currInfoView = InfoView.findById(nInfoViewId);
	if (currInfoView == null){
		throw new WCMException(LocaleServer.getString("infoview_fieldattrs.obj.not.found","无法找到InfoView！"));
	}
	String strFieldName = CMyString.showNull(currRequestHelper.getString("IVFieldName"));
	InfoViewFields oIVFields = InfoViewFields.findByName(strFieldName, nInfoViewId);
	if(oIVFields.size()<=0){
		throw new WCMException( CMyString.format(LocaleServer.getString("infoview_fieldattrs.field.not.found","无法找到表单字段[IVFieldName={0},InfoViewId={1}]！"),new Object[]{strFieldName,String.valueOf(nInfoViewId)}));
	}
	String sIVFieldIds = "";
	for(int i=0;i<oIVFields.size();i++){
		if(i!=0){
			sIVFieldIds += ",";
		}
		sIVFieldIds += ((InfoViewField)oIVFields.getAt(i)).getId();
	}
	InfoViewField oIVField = (InfoViewField)oIVFields.getAt(0);
	String sDefaultValue = CMyString.filterForHTMLValue(CMyString.showNull(oIVField.getDefaultValue()));
	String sDataPattern = CMyString.showNull(oIVField.getDataPattern());
	String sDataControl = CMyString.filterForHTMLValue(CMyString.showNull(oIVField.getDataControl()));
	int nMinLength = oIVField.getMinLength();
	/*
	if(nMinLength<=0 && !oIVField.isNillable()){
		nMinLength = 1;
	}
	*/
	String sDataTypeDesc = oIVField.getDataTypeAsString();
	boolean bIsString = "string".equalsIgnoreCase(sDataTypeDesc);
	boolean bIsNumber = "int".equalsIgnoreCase(sDataTypeDesc)
		|| "double".equalsIgnoreCase(sDataTypeDesc);

	InfoViewMgr m_oInfoViewMgr = (InfoViewMgr) DreamFactory
			.createObjectById("InfoViewMgr");
	InfoViewConfig m_oInfoViewConfig = m_oInfoViewMgr.getInfoViewConfig();
	//infoview binding
	List lstInfoviewBinding = m_oInfoViewConfig.getInfoviewBindings();
	Vector vcInfoviewBindingFields = new Vector();
	Vector vcInfoviewBindingDescs = new Vector();
	for (Iterator iter = lstInfoviewBinding.iterator(); iter.hasNext();) {
		BindingBean oBean = (BindingBean) iter.next();
		if(!oBean.getDataType().equalsIgnoreCase(sDataTypeDesc) && sDataTypeDesc!="text" && sDataTypeDesc!="string")
			continue;

		vcInfoviewBindingFields.add(oBean.getName());
		vcInfoviewBindingDescs.add(oBean.getDesc());
	}
	String[] arrInitorFields = new String[vcInfoviewBindingFields.size()];
	vcInfoviewBindingFields.toArray(arrInitorFields);
	String[] arrInitorFieldDescs = new String[vcInfoviewBindingDescs.size()];
	vcInfoviewBindingDescs.toArray(arrInitorFieldDescs);

	//GateWay binding
	GatewayConfig oGatewayConfig = m_oInfoViewConfig.getGatewayConfig();
	List lstGatewayBinding = oGatewayConfig.getGatewayBindings();
	Vector vcGatewayBindingFields = new Vector();
	Vector vcGatewayBindingDescs = new Vector();
	for (Iterator iter = lstGatewayBinding.iterator(); iter.hasNext();) {
		BindingBean oBean = (BindingBean) iter.next();
		if(!oBean.getDataType().equalsIgnoreCase(sDataTypeDesc) && sDataTypeDesc!="text" && sDataTypeDesc!="string")
			continue;
		vcGatewayBindingFields.add(oBean.getName());
		vcGatewayBindingDescs.add(oBean.getDesc());
	}
	String[] arrGateWayBindings = new String[vcGatewayBindingFields.size()];
	vcGatewayBindingFields.toArray(arrGateWayBindings);
	String[] arrGateWayBindingDescs = new String[vcGatewayBindingDescs.size()];
	vcGatewayBindingDescs.toArray(arrGateWayBindingDescs);

	String sDisplayType = CMyString.showNull(oIVField.getFieldType(), "PlainText");
%>
<html>
<HEAD>
<TITLE WCMAnt:param="infoview.fieldattrs.jsp.title">维护自定义表单字段扩展属性</TITLE>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/data/locale/infoview.js"></script>
<script src="../../app/js/easyversion/extrender.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<script>
var m_sFieldName = "<%=CMyString.filterForJs(strFieldName)%>";
var m_sDataType = '<%=oIVField.getDataTypeAsString()%>';
var m_bShowValid = <%=isShowValid(sDisplayType)%>;
var m_bFileAttachment = <%=isFileAttach(sDisplayType)%>;
var m_bAttachment = <%=isAttach(sDisplayType)%>;
var m_bShowNillable = <%=isShowNillable(sDisplayType)%>;
</script>
<script src="infoview_fieldattrs.js"></script>
<style>
	body{
		font-size:14px;
		line-height:22px;
	}
	fieldset{
		padding:5px 0 5px 5px;
		margin-bottom:5px;
	}
	legend{
		font-weight:bold;
	}
	.inputtext{
		height:20px;
		font-size:14px;
		line-height:18px;
		border:1px solid gray;
	}
	.label{
		display:inline-block;
		width:100px;
	}
	.value{
		display:inline;
	}
	.input_checkbox{
	}
	.desc{
		color:gray;
		font-size:12px;
		margin-left:10px;
	}
	#VarNameContainer{
		overflow:hidden;
		width:21px;
		margin-left:-21px;
		height:19px;
	}
	#VarName{
		margin-left:-100px;
		margin-top:-2px;
		width:120px;
	}
	#GateWayBinding{
		width:120px;
	}
	#DefaultValue{
		width:115px;
	}
	.button{
		border:1px solid gray;
		width:60px;
		cursor:pointer;
	}
</style>
</HEAD>

<BODY>
<form name="ObjectForm" id="ObjectForm" action="" method="POST" style="display:none;">
    <input type="hidden" name="InfoViewId" id="InfoViewId" value="<%=nInfoViewId%>">
    <input type="hidden" name="IVFieldIds" id="IVFieldIds" value="<%=sIVFieldIds%>">
    <input type="hidden" name="CaseSensitive" id="CaseSensitive" value="true" ignore="1">
	<INPUT TYPE="hidden" NAME="ObjectXML" Value="">
</form>
<form name="DataForm" id="DataForm" style="margin:0;padding:0;">
<div style="width:100%;height:465px;overflow:auto;">
	<fieldset><legend WCMAnt:param="infoview.fieldattrs.jsp.nomarlinfo">基本信息</legend>
    <div class="row" style="display:none">
        <span class="label" WCMAnt:param="infoview.fieldattrs.jsp.fieldname">字段名称：</span>
        <span class="value">
            <input class="inputtext" type="text" name="FieldName" id="FieldName" value="<%=CMyString.filterForHTMLValue(strFieldName)%>" disabled>
        </span>
    </div>    
    <div class="row">
        <span class="label" WCMAnt:param="infoview.fieldattrs.jsp.fieldothername">字段名称：</span>
        <span class="value">
            <input class="inputtext" type="text" name="FieldDesc" id="FieldDesc" value="<%=CMyString.filterForHTMLValue(oIVField.getDesc())%>" validation="type:'string',max_len:'100',no_desc:''" disabled>
        </span>
		<span class="desc" WCMAnt:param="infoview.fieldattrs.jsp.maxlength100">(最大长度为100)</span>
    </div>
    <div class="row">
        <span class="label" WCMAnt:param="infoview.fieldattrs.jsp.fieldtype">字段类型：</span>
        <span class="value">
			<%=getDataTypes(sDataTypeDesc)%>
        </span>
    </div>
    <div class="row">
        <span class="label" WCMAnt:param="infoview.fieldattrs.jsp.displaytype">表现形式：</span>
        <span class="value">
			<%=getDisplayTypes(sDisplayType)%>
        </span>
    </div>
    <div class="row" id="div_readonly" style="display:<%=isShowReadOnly(sDisplayType)?"":"none"%>;">
        <span class="label" WCMAnt:param="infoview.fieldattrs.jsp.allowedit">外网允许编辑：</span>
        <span class="value">
            <input isBoolean="true" value="0|1" class="input_checkbox"
			type="checkbox" name="ReadOnly" id="ReadOnly"<%=oIVField.isReadOnly()?"":" checked"%>>
        </span>
		<span class="desc" WCMAnt:param="infoview.fieldattrs.jsp.desc">(只在外网采集页面有效)</span>
    </div>
    <div class="row" id="div_nillable" style="display:<%=isShowNillable(sDisplayType)?"":"none"%>;">
        <span class="label" WCMAnt:param="infoview.fieldattrs.jsp.allownone">允许为空：</span>
        <span class="value">
            <input isBoolean="true"  class="input_checkbox"
			type="checkbox" name="Nillable" id="Nillable" value='<%=oIVField.isNillable()? 1 : 0%>' 
			<%=oIVField.isNillable()?" checked":""%>>
        </span>
    </div>
	<div class="row" id="div_readonly" style="display:<%=isShowReadOnly(sDisplayType)?"":"none"%>;">
        <span class="label" WCMAnt:param="infoview_fieldattrs.jsp.allowedit">内网允许编辑：</span>
        <span class="value">
            <input isBoolean="true" value="0|1" class="input_checkbox"
			type="checkbox" name="BackReadOnly" id="ReadOnly"<%=oIVField.isBackReadOnly()?"":" checked"%>>
        </span>
		<span class="desc" WCMAnt:param="infoview_fieldattrs.jsp.neweffective">(只在后台的新建修改页有效)</span>
    </div>
	<div class="row" id="div_showable" style="display:<%=isShowBackShowAbel(sDisplayType)?"":"none"%>;">
        <span class="label">内网允许显示：</span>
        <span class="value">
            <input isBoolean="true" value="1|0" class="input_checkbox"
			type="checkbox" name="BackShowAble" id="BackShowAble" <%=oIVField.isBackShowAble()?" checked":""%>>
        </span>
    </div>
	</fieldset>
	<fieldset id="fs_valid" style="display:<%=isShowValid(sDisplayType)?"":"none"%>;"><legend WCMAnt:param="infoview.fieldattrs.jsp.validationinfo">校验信息</legend>
	<input type="hidden" name="DataType" id="DataType" value="<%=oIVField.getDataType()%>">
	<input type="hidden" name="DataControl" id="DataControl" value="<%=sDataControl%>">
    <div class="row" id="MaxLengthContainer"
		style="display:<%=bIsString && !isDate(sDisplayType)?"":"none"%>;">
        <span class="label" WCMAnt:param="infoview.fieldattrs.jsp.maxlength">最大长度：</span>
        <span class="value">
            <input class="inputtext" type="text" name="MaxLength" id="MaxLength" value="<%=oIVField.getMaxLength()%>">
        </span>
		<span class="desc" WCMAnt:param="infoview.fieldattrs.jsp.notlimit">(&lt;=0或者为空表示无限制)</span>
    </div>
    <div class="row" id="MinLengthContainer"
		style="display:<%=bIsString && !isDate(sDisplayType)?"":"none"%>;">
        <span class="label" WCMAnt:param="infoview.fieldattrs.jsp.nimlength">最小长度：</span>
        <span class="value">
            <input class="inputtext" type="text" name="MinLength" id="MinLength" value="<%=nMinLength%>">
        </span>
    </div>
    <div class="row" id="MaxNumContainer"
		style="display:<%=bIsNumber?"":"none"%>;">
        <span class="label" WCMAnt:param="infoview.fieldattrs.jsp.namvalue">最大值：</span>
        <span class="value">
            <input class="inputtext" type="text" name="MaxNum" ignore="1" id="MaxNum" value="">
        </span>
		<span class="desc" WCMAnt:param="infoview.fieldattrs.jsp.shownonelimit">(不填表示无限制)</span>
    </div>
    <div class="row" id="MinNumContainer"
		style="display:<%=bIsNumber?"":"none"%>;">
        <span class="label" WCMAnt:param="infoview.fieldattrs.jsp.minvalue">最小值：</span>
        <span class="value">
            <input class="inputtext" type="text" name="MinNum" ignore="1" id="MinNum" value="">
        </span>
		<span class="desc" WCMAnt:param="infoview.fieldattrs.jsp.shownonelimit">(不填表示无限制)</span>
    </div>
    <div class="row" style="display:<%=bIsString && !isDate(sDisplayType)?"":"none"%>;">
        <span class="label" WCMAnt:param="infoview.fieldattrs.jsp.sDataPattern1">校验器：</span>
        <span class="value">
			<%=getValidTypes(sDataPattern)%>
        </span>
    </div> 
	<div class="row" id="FormatContainer" style="display:<%=bIsString && !isDate(sDisplayType)?"":"none"%>;">
        <span class="label" WCMAnt:param="infoview.fieldattrs.jsp.ValidFormat">自定义匹配式：</span>
        <span class="value">
            <input class="inputtext" type="text" name="ValidFormat" id="ValidFormat" ignore="1" value="">
        </span>
		<span class="desc" WCMAnt:param="infoview.fieldattrs.jsp.Validzhengze">(正则表达式:如<span style="color:blue">/^\d{5,10}$/</span>)</span>
    </div>
	 <div class="row" id="FormatMsg" style="display:<%=bIsString && !isDate(sDisplayType)?"":"none"%>;">
        <span class="label" WCMAnt:param="infoview_fieldattrs.jsp.matchtype">字符匹配类型：</span>
        <span class="value">
            <input class="inputtext" type="text" name="ValidFormatMsg" id="ValidFormatMsg" ignore="1" value=""><span style="color:red;display:none" id="ValidFormatMsgTip">&nbsp;*</span>
        </span>
    </div> 
    <div class="row" id="DateTimeContainer"
		style="display:<%=isDate(sDisplayType)?"":"none"%>;">
        <span class="label" WCMAnt:param="infoview.fieldattrs.jsp.DatePattern">日期格式：</span>
        <span class="value">
            <select name="DatePattern" ignore="1" id="DatePattern">
				<option value=" "> </option>
				<option value="yyyy-mm-dd">yyyy-mm-dd</option>
				<option value="yyyy/mm/dd">yyyy/mm/dd</option>
				<option value="yyyy-mm-dd HH:MM">yyyy-mm-dd HH:MM</option>
				<option value="yyyy-mm-dd HH:MM:ss">yyyy-mm-dd HH:MM:ss</option>
			</select>
        </span>
    </div>
	</fieldset>
	<fieldset id="fs_attachment" style="display:<%=isAttach(sDisplayType)?"":"none"%>;"><legend WCMAnt:param="infoview.fieldattrs.jsp.validationinfo">校验信息</legend>
    <div class="row">
        <span class="label" WCMAnt:param="infoview.fieldattrs.jsp.FileExt">附件后缀：</span>
        <span class="value">
            <input class="inputtext" type="text" name="FileExt" id="FileExt" ignore="1" value="">
        </span>
		<span style="margin-left: 10px" class="desc" WCMAnt:param="infoview.fieldattrs.jsp.muchsplit">多个后缀以逗号(,)分隔，如：jpg,gif,doc</span>
    </div> 
	</fieldset>
	<fieldset id="fs_default" style="display:<%=isShowDefault(sDisplayType)?"":"none"%>;"><legend WCMAnt:param="infoview.fieldattrs.jsp.ShowDefault">缺省值设置</legend>
    <div class="row" style="display:<%=isAttach(sDisplayType)?"none":""%>;">
        <span class="label" WCMAnt:param="infoview.fieldattrs.jsp.initcalue">初始值/变量：</span>
        <span class="value" style="position:absolute;" id="DefaultValueSp">
            <input class="inputtext" type="text" name="DefaultValue" id="DefaultValue" value="<%=sDefaultValue%>" style="position:absolute;">
			<span id="VarNameContainer" style="position:absolute; margin-left:100px;"><%=getDefaultVars(arrInitorFields, arrInitorFieldDescs, sDefaultValue)%></span>
        </span>
    </div>
    <div class="row">
        <span class="label" WCMAnt:param="infoview.fieldattrs.jsp.gatewaybinding">网关绑定字段：</span>
        <span class="value">
			<%=getGateWayBindings(arrGateWayBindings, arrGateWayBindingDescs, oIVField.getGateWayBinding())%>
        </span>
    </div>
	</fieldset>
</div>
<table border=0 cellspacing=0 cellpadding=0 width="100%" height="40">
	<tbody>
		<tr>
			<td align="center" valign="middle">
				<input type="button" class="button" value="确定" onclick="Ok();" WCMAnt:paramattr="value:infoview.fieldattrs.jsp.okvalue"/>&nbsp;&nbsp;&nbsp;
				<input type="reset" class="button" value="重置" WCMAnt:paramattr="value:infoview.fieldattrs.jsp.resetvalue"/>&nbsp;&nbsp;&nbsp;
				<input type="button" class="button" value="取消" onclick="parent.Cancel();" WCMAnt:paramattr="value:infoview.fieldattrs.jsp.cancelvalue"/>
			</td>
		</tr>
	</tbody>
</table>
</form>
</BODY>
</HTML>

<%!
	private String[] aDataDisplayNames = null;
	private String[] aDataTypes = null;
	private String[] m_aFieldTypes = null;
	private String[] m_aFieldDisplayNames = null;
	private String[] m_aValidTypes = null;
	private String[] m_aValidDisplayNames = null;
	
	private String getDataTypes(String _sCurrDataType){
		String sRetHtml = "<select name=\"DataTypeStr\" id=\"DataTypeStr\" disabled ignore=\"1\">";
		for(int i=0;i<aDataTypes.length;i++){
			sRetHtml += "<option value=\""+aDataTypes[i]+"\""
				+(aDataTypes[i].equalsIgnoreCase(_sCurrDataType)?" selected":"")+">"
				+aDataDisplayNames[i]+"</option>";
		}
		sRetHtml += "</select>";
		return sRetHtml;
	}
	
	
	/**
	 * 图片附件,附件 只读,必填项,附件类型限制
	 * 文本编辑器,输入框 只读,较验,缺省值,网关缺省值
	 * 下拉框,复选框,单选框,多选下拉框 只读,必填项[,缺省值,网关缺省值]
	 * 日期控件 只读,必填项,缺省值,网关缺省值
	 * 表达式 只允许只读
	 */
	private boolean isShowReadOnly(String _sCurrFieldType){
		return !"ExpressionBox".equalsIgnoreCase(_sCurrFieldType);
	}
	private boolean isShowBackShowAbel(String _sCurrFieldType){
		return !"ExpressionBox".equalsIgnoreCase(_sCurrFieldType);
	}
	private boolean isShowNillable(String _sCurrFieldType){
		return !"ExpressionBox".equalsIgnoreCase(_sCurrFieldType);
	}
	private boolean isAttach(String _sCurrFieldType){
		return "InlineImage".equalsIgnoreCase(_sCurrFieldType)
			|| "LinkedImage".equalsIgnoreCase(_sCurrFieldType)
			|| "FileAttachment".equalsIgnoreCase(_sCurrFieldType);
	}
	private boolean isFileAttach(String _sCurrFieldType){
		return "FileAttachment".equalsIgnoreCase(_sCurrFieldType);
	}
	private boolean isDate(String _sCurrFieldType){
		return "DTPicker_DTText".equalsIgnoreCase(_sCurrFieldType);
	}
	private boolean isShowDefault(String _sCurrFieldType){
		return !"ExpressionBox".equalsIgnoreCase(_sCurrFieldType) && !isFileAttach(_sCurrFieldType)
			&& !("CheckBox".equalsIgnoreCase(_sCurrFieldType)
			|| "ListBox".equalsIgnoreCase(_sCurrFieldType));
	}
	private boolean isShowValid(String _sCurrFieldType){
		return "RichText".equalsIgnoreCase(_sCurrFieldType) || "PlainText".equalsIgnoreCase(_sCurrFieldType) || isDate(_sCurrFieldType);
	}
	private String getDisplayTypes(String _sCurrFieldType){
		String sRetHtml = "<select name=\"DisplayType\" id=\"DisplayType\" disabled ignore=\"1\">";
		for(int i=0;i<m_aFieldTypes.length;i++){
			sRetHtml += "<option value=\""+m_aFieldTypes[i]+"\""
				+(m_aFieldTypes[i].equalsIgnoreCase(_sCurrFieldType)?" selected":"")+">"
				+m_aFieldDisplayNames[i]+"</option>";
		}
		sRetHtml += "</select>";
		return sRetHtml;
	}
	
	private String getValidTypes(String _sDataPattern){
		String sRetHtml = "<select id=\"DataPattern\" name=\"DataPattern\" onchange=\"SetDataControl(this.value);\">";
		for(int i=0;i<m_aValidTypes.length;i++){
			sRetHtml += "<option value=\""+m_aValidTypes[i]+"\""
				+(m_aValidTypes[i].equalsIgnoreCase(_sDataPattern)?" selected":"")+">"
				+m_aValidDisplayNames[i]+"</option>";
		}
		sRetHtml += "</select>";
		return sRetHtml;
	}
	private String getDefaultVars(String[] _aVarNames, String[] _aDisplays, String _sDefaultValue){
		String sRetHtml = "<select id=\"VarName\" name=\"VarName\" ignore=\"1\" style=\"position:absolute;margin-top:0px;\" onclick=\"GetDefaultValue(this);\" onchange=\"SetDefaultValue(this.value);\" id=\"VarName\">";
		sRetHtml += "<option value=\"\"></option>";
		for(int i=0;i<_aVarNames.length;i++){
			sRetHtml += "<option value=\"$$"+_aVarNames[i]+"$$\""
				+(("$$"+_aVarNames[i]+"$$").equalsIgnoreCase(_sDefaultValue)?" selected":"")+">"
				+_aDisplays[i]+"</option>";
		}
		sRetHtml += "</select>";
		return sRetHtml;
	}
	private String getGateWayBindings(String[] _aValues, String[] _aDisplays, String _sDefaultValue){
		String sRetHtml = "<select id=\"GateWayBinding\" name=\"GateWayBinding\" id=\"GateWayBinding\">";
		sRetHtml += "<option value=\"\"></option>";
		for(int i=0;i<_aValues.length;i++){
			sRetHtml += "<option value=\""+_aValues[i]+"\""
				+(_aValues[i].equalsIgnoreCase(_sDefaultValue)?" selected":"")+">"
				+_aDisplays[i]+"</option>";
		}
		sRetHtml += "</select>";
		return sRetHtml;
	}
%>