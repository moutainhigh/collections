<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtField" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtFields" %>
<%@ page import="com.trs.cms.content.ExtendedField" %>
<%@ page import="com.trs.components.wcm.content.persistent.BaseChannel" %>
<%@ page import="com.trs.components.wcm.content.domain.ContentExtFieldMgr" %>
<%@ page import="java.sql.Types" %>
<%@include file="../include/public_server.jsp"%>
<%
    //接收页面参数
    int nChannelId = currRequestHelper.getInt("ChannelId",0);
    int nSiteId = currRequestHelper.getInt("SiteId",0);
    int nDocId =  currRequestHelper.getInt("DocId",0);
    int nFlowDocId =  currRequestHelper.getInt("FlowDocId",0);
    Document currDocument = Document.findById(nDocId);
    Channel currChannel = null;
    if(currDocument == null){
        throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,CMyString.format(LocaleServer.getString("photodoc_edit.jsp.doc_notfound", "没有找到ID为[{0}]的文档!"), new int[]{nDocId}));
        //"没有找到ID为"+ nDocId + "的文档");
    }
    if(nChannelId == 0){
        nChannelId = currDocument.getChannelId();
    }else{
        currChannel = Channel.findById(nChannelId);
        if(currChannel == null){
            throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, CMyString.format(LocaleServer.getString("photodoc_edit.jsp.channel_notfound", "没有找到ID为[{0}]的栏目!"), new int[]{nChannelId}));
        }
        nSiteId = currChannel.getSiteId();
    }
    int nSiteType = currDocument.getChannel().getSite().getType();
    FilesMan currFilesMan = FilesMan.getFilesMan();
    String sFileName = currDocument.getRelateWords();
    //扩展字段逻辑
    ContentExtFieldMgr m_oExtFieldMgr = (ContentExtFieldMgr) DreamFactory
                .createObjectById("ContentExtFieldMgr");
    BaseChannel host = currDocument.getChannel();// getHost(_context);
    ContentExtFields fields = m_oExtFieldMgr.getExtFields(host, null);
    ContentExtField currExtendedField = null;
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title WCMAnt:param="photodoc_edit.jsp.title">图片基本属性编辑</title>
    <style>
        .input_text{
            height:20px;
            border:1px solid lightgray;
            margin:0px;         
        }
        .input_textarea{
            height:100;
            width:600;
            border:1px solid lightgray;
            margin-left:35px;
            font-size:14px;
        }
        #attr_extend{
            float:right;
            width:190px;
            margin:5px;
            height:180px;
            overflow:auto;
        }       
        #photo_container{
            width:220px;
            margin:10px -10px;        
            float:left; 
            text-align:center;          
        }
        label{
            font-weight:bold;
            font-size:14px; 
            width:80px;
            text-align:right;
            display:inline-block;
        }
        .calendarShow{
            width:28px;
            line-height:18px;
            display:inline;
        }
        .ext-ie .calendarShow{
            height:20px;
        }
        .ext-gecko .calendarShow{
            width:30px;
        }
        .ext-safari .calendarShow{
            width:30px;
        }
        .ext-safari .img{
            margin-left:-5px;
        }
        .ext-ie .calendarShow{
            padding:0px;
        }
        .DTImg{
            width:28px;
            height:20px;
            line-height:18px;
            display:inline;
        }
    </style>
    <link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
    <link href="../../app/js/easyversion/resource/calendar.css" rel="stylesheet" type="text/css" />
    <link href="../../app/js/easyversion/resource/crashboard.css" rel="stylesheet" type="text/css" />
    <link href="../../app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
    <link href="../../app/js/source/wcmlib/suggestion/resource/suggestion.css" rel="stylesheet" type="text/css" />
</head>
<script language="javascript">
<!--
    var calenderControls = [];
//-->
</script>
<body>
    <div style="display:none;" id="bodyDiv">
        <div id="photoprops">
            <form id="form_photoprops">
            <input type="hidden" name="ObjectId" id="ObjectId" value="<%=nDocId%>">
            <input type="hidden" name="ChannelId" id="ChannelId" value="<%=nChannelId%>">
            <input type="hidden" name="FlowDocId" id="FlowDocId" value="0">
            <div id="attr_extend">
            <%
                if(fields.size()>0){
                    for(int i =0; i < fields.size();i++){
                        currExtendedField = (ContentExtField) fields.getAt(i);
                        if(currExtendedField == null) continue;
                        ExtendedField newContentExtField = null;
                        String sExtFieldName = "";
                        String sExtFieldValue = "";
                        newContentExtField = ExtendedField.findById(currExtendedField.getExtFieldId());
                        sExtFieldName = PageViewUtil.toHtml(currExtendedField.getName());

                        if(currExtendedField.getType().getType() == java.sql.Types.TIMESTAMP) {
                            CMyDateTime tmpDateTime = currDocument.getPropertyAsDateTime(currExtendedField.getName());
                            if(tmpDateTime == null) {
                                sExtFieldValue = "";
                            } else {
                                sExtFieldValue= PageViewUtil.toHtmlValue(tmpDateTime.toString("yyyy-MM-dd HH:mm"));
                                if(sExtFieldValue == ""){
                                    sExtFieldValue = PageViewUtil.toHtmlValue(newContentExtField.getPropertyAsString("FIELDDEFAULT"));
                                }
                            }
                        } else {
                            sExtFieldValue= PageViewUtil.toHtmlValue(currDocument.getPropertyAsString(currExtendedField.getName()));
                            if(sExtFieldValue == ""){
                                String sContentExtFieldTypeName = CMyString.filterForHTMLValue(newContentExtField.getAttributeValue("FIELDTYPE"));
                                    sExtFieldValue = PageViewUtil.toHtmlValue(newContentExtField.getPropertyAsString("FIELDDEFAULT"));
                            }
                        }
            %>  
                            <span style="font-size:12px;white-space:nowrap"><label><%=currExtendedField.getDesc()%></label></span>              
                            <span style="font-weight:bold;white-space:nowrap;">
            <%=getHtml(currExtendedField,sExtFieldValue)%>
                            </span>
            <%  
                    }
                }
            %>
            </div>
            <div style="margin-top:10px;height:180px">              
                <div id="photo_container">          
                    <img src="<%=mapfileName(sFileName)%>" width="130px" height="100px" id="photoitem">
                    <div style="margin-top:10px">
                        <a href="#" id="photouploader" WCMAnt:param="photodoc_edit.jsp.reload">重新上传</a>
                        &nbsp;&nbsp;|&nbsp;&nbsp;
                        <a href="#" onclick="editPhoto(<%=nDocId%>,<%=nSiteId%>);return false;" id="photoeditor" WCMAnt:param="photodoc_edit.jsp.editPic">编辑图片</a>
                    </div>
                </div>              
                <div id="infoContainer">            
                    <div id="title">
                        <label WCMAnt:param="photodoc_edit.jsp.headTitle">标题</label>
                        <input type="text" name="DocTitle" id="DocTitle" value="<%=trimNull(currDocument.getTitle())%>" class="input_text" title="<%=trimNull(currDocument.getTitle())%>" validation="required:'true',type:'string',max_len:'200',desc:'标题',showid:'validatetip'" validation_desc="标题" WCMAnt:paramattr="validation_desc:photo_edit.jsp.headTitle"></input>
                    </div>
                    <div id="author">
                        <label WCMAnt:param="photodoc_edit.jsp.author">作者</label>
                        <input type="text" name="DocAuthor" id="DocAuthor" value="<%=trimNull(currDocument.getAuthorName())%>" class="input_text" validation="type:'string',max_len:'100',desc:'作者',showid:'validatetip'" validation_desc="作者" WCMAnt:paramattr="validation_desc:photo_edit.jsp.author"></input>
                    </div>
                    <div id="people">
                        <label WCMAnt:param="photodoc_edit.jsp.people">人物</label>
                        <input type="text" name="DocPeople" id="DocPeople" value="<%=trimNull(currDocument.getPeople())%>" class="input_text" validation="type:'string',max_len:'100',desc:'人物',showid:'validatetip'" validation_desc="人物" WCMAnt:paramattr="validation_desc:photo_edit.jsp.people"></input>
                    </div>
                    <div id="place">
                        <label WCMAnt:param="photodoc_edit.jsp.place">地点</label>
                        <input type="text" name="DocPlace" id="DocPlace" value="<%=trimNull(currDocument.getPlace())%>" class="input_text" validation="type:'string',max_len:'100',desc:'地点',showid:'validatetip'" validation_desc="地点" WCMAnt:paramattr="validation_desc:photo_edit.jsp.place"></input>
                    </div>
                    <div id="time">
                        <label WCMAnt:param="photodoc_edit.jsp.time">时间</label>
                        <input type="text" name="DocRelTime" id="DocRelTime" elname="<%=LocaleServer.getString("photodoc_edit.label.picRelTime", "时间")%>" value="<%=trimNull(currDocument.getReleaseTime().toString("yyyy-MM-dd HH:mm:ss"))%>" class="input_text"><button id="embed" class="calendarShow" type="button"><IMG alt="" src="../images/icon/TRSCalendar.gif" border=0 class="img"></button>
                    </div>
                    <div id="keyword">
                        <label WCMAnt:param="photodoc_edit.jsp.keywords">关键词</label>
                        <input type="text" name="DocKeywords" id="DocKeywords" value="<%=trimNull(currDocument.getKeywords())%>" class="input_text"validation="type:'string',max_len:'100',desc:'关键词',showid:'validatetip'" validation_desc="关键词" WCMAnt:paramattr="validation_desc:photo_edit.jsp.keywords"></input>
                    </div>
                </div>  
                <span id="validatetip" style="margin-left:20px;top:160px;position:absolute"></span>
            </div>
            <div id="attr_desc" style="margin:10px">
                <label style="float:left;margin-left:35px" WCMAnt:param="photodoc_edit.jsp.describe">描述</label><br />
                <textarea name="DocContent" id="DocContent" class="input_textarea" scroll="auto" validation="type:'string',max_len:'200',showid:'validatetip',desc:'图片描述'" validation_desc="图片描述" WCMAnt:paramattr="validation_desc:photo_edit.jsp.describe"><%=trimNull(currDocument.getContent())%></textarea>                
            </div>
            </form>
        </div>
    </div>  
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/data/locale/photo.js"></script>
<script src="../../app/js/easyversion/extrender.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="../../app/js/easyversion/basicdatahelper.js"></script>
<script src="../../app/js/easyversion/web2frameadapter.js"></script>
<script src="../../app/js/easyversion/elementmore.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<!-- Component Start -->
<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
<script language="javascript" src="../../app/js/easyversion/crashboard.js" type="text/javascript"></script>
<!-- Component End -->
<!--validator-->
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>   
<!--calendar-->
<script language="javascript" src="../../app/js/easyversion/calendar_lang/cn.js" WCMAnt:locale="../../app/js/easyversion/calendar_lang/$locale$.js" type="text/javascript"></script>
<script language="javascript" src="../../app/js/easyversion/calendar.js" type="text/javascript"></script>
<!--locker-->
<SCRIPT src="../../app/js/source/wcmlib/core/LockUtil.js"></SCRIPT>
<script src="../../app/js/source/wcmlib/suggestion/suggestion.js"></script>
<script src="photodoc_edit.js"></script>
<script>
    window.m_fpCfg = {
        m_arrCommands : [{
            cmd : 'Ok',
            name : wcm.LANG.PHOTO_CONFIRM_1 || '确定'
        }],
        size : [800, 350]
    };  
</script>   
<script language="javascript">
    var cale = wcm.TRSCalendar.get({
        input : 'DocRelTime',
        dtFmt : 'yyyy-mm-dd',
        handler : 'embed',
        withtime : true
    });
    var nSiteId = <%=nSiteId%>;
    var nSiteType = <%=nSiteType%>;
</script>
</body>
</html>
<%!
    private String trimNull(String _sParam) throws WCMException{
        if(_sParam==null){
            return "";
        }
        return CMyString.filterForHTMLValue(_sParam);
    }
    private String mapfileName(String _fn) throws WCMException{
        String fn = "../images/photo/pic_notfound.gif";
        if(_fn == null || (_fn.trim()).equals("")){
            return fn;
        }
        String[] fs = _fn.split(",");
        if(fs.length == 0){
            return fn;
        }
        String r = fs[0];
        return "/webpic/" + r.substring(0,8) + "/" + r.substring(0,10) + "/" + r;
    }
    private String getHtml(ContentExtField _currField,String _sValue) throws Exception{
        if(_currField == null || _currField.getType() == null) return "";
        int nDataType = _currField.getType().getType();
        String sHtml = "";
        String sName = CMyString.filterForJs(_currField.getName());
        String sValue = CMyString.filterForJs(_sValue);
        //时间型使用控件显示
        if(nDataType == java.sql.Types.DATE || nDataType == java.sql.Types.TIMESTAMP) {
            sHtml = "<input style=\"display:inline-block;width:130px;\" type=\"text\" name=\"" +  sName + "\" id=\"" + sName + "\" value=\"" + sValue + "\"/><button type=\"button\" class=\"DTImg\" id=\"btn" + sName + "\"><img src=\"../images/icon/TRSCalendar.gif\" border=0 alt=\"\"></button>";
            sHtml += "<script>window.calenderControls.push(\'" + sName + "\');</script>";
            return sHtml;
        }
        String sPattern = getPattern(nDataType);
        String sEnmValue = CMyString.transDisplay(_currField.getAttributeValue("ENMVALUE"));
        String[] arValues = null;
        StringBuffer buffValidation = new StringBuffer(512);
        String sDbFieldName = _currField.getName().toUpperCase();
        if("string".equals(sPattern)){
            if(_currField.getAttributeValue("FIELDTYPE") == null){
                sHtml = "<TEXTAREA rows=3 ID=\"" + sName + "\" NAME=\"" + sName +  "\" validation=\"type:'string',max_len:'" + _currField.getMaxLength() + "',showid:'validatetip'\" validation_desc=\"" + CMyString.filterForHTMLValue(_currField.getDesc()) + "\" style=\"width:153px;border: #b7b7a6 1px solid;margin-top:4px;margin-bottom:4px;\">"+ _sValue+"</TEXTAREA>";
                return sHtml;
            }
            int nFieldType = Integer.parseInt(_currField.getAttributeValue("FIELDTYPE"));
            switch(nFieldType){
                case 2 :  //密码文本
                    sHtml += "<SPAN>";
                    sHtml += "<input class=input_text style=\"WIDTH: 152px;\" type=\"password\" ID=\"" + sName + "\"  NAME=\"" + sName +  "\" value=\"" + sValue + "\" validation=\"type:'string',max_len:'" + _currField.getMaxLength() + "',showid:'validatetip'\" validation_desc=\"" + CMyString.filterForHTMLValue(_currField.getDesc()) + "\"/>";
                    sHtml += "</span>";
                    break;
                case 3 :  //普通文本
                    sHtml += "<SPAN style=\"WIDTH: 142px; HEIGHT: 20px\">";
                    sHtml += "<input class=input_text type=\"text\" ID=\"" + sName + "\" NAME=\"" + sName +  "\" value=\"" + _sValue + "\" validation=\"type:'string',max_len:'" + _currField.getMaxLength() + "',showid:'validatetip'\" validation_desc=\"" + CMyString.filterForHTMLValue(_currField.getDesc()) + "\"/>";
                    sHtml += "</span>";
                    break;
                case 6 : //单选
                    if(CMyString.isEmpty(sEnmValue)){
                       break;
                    }
                    arValues = sEnmValue.split("~");
                    sHtml += "<div style=\"display:inline-block;width:153px;border: #b7b7a6 1px solid;margin-top:4px;margin-bottom:4px;\">";
                    for(int ix=0,len=arValues.length;ix<len;ix++){
                        String[] arValue = pairSplit(arValues[ix]);
                        boolean bSelectFlag = false;
                        if(!CMyString.isEmpty(sValue) && !CMyString.isEmpty(arValue[1])){
                            if((sValue).indexOf(arValue[1])!=-1){
                                bSelectFlag = true;                 
                            }   
                        }
                        sHtml += "<input type=\"radio\" style=\"border:0px;\" ID=\"" + sName + "_" + ix + "\" NAME=\"" + sName +  "\" value=\"" + arValue[1] + "\" " + (bSelectFlag ?"checked":"")  + "/>&nbsp;<label for=\"" + sName + "_" + ix + "\">" + arValue[0] + "</label>&nbsp;</br>";
                    }
                    sHtml += "</div>";
                    break;
                case 7 : //下拉列表
                    sHtml = "<select class=\"select_container\" style=\"width:153px;\" id=\"" +  sName + "\" NAME=\"" + sName + "\">";
                    sHtml += "<option value=\"\">" + 
                        LocaleServer.getString("contentextfield.label.pleaseSel", "--请选择--") 
                        + "</option>";
                    if(!CMyString.isEmpty(sEnmValue)){
                        arValues = sEnmValue.split("~");
                        for(int ix=0,len=arValues.length;ix<len;ix++){
                            String[] arValue = pairSplit(arValues[ix]);
                            boolean bSelectFlag = false;
                            if(!CMyString.isEmpty(sValue) && sValue.equals(arValue[1])){
                                bSelectFlag = true;                 
                            }
                            sHtml += "<option value=\"" + arValue[1] + "\" " + (bSelectFlag ? "selected":"") + ">" + arValue[0] + "</option>";
                        }
                        sHtml += "</select>";
                    }
                    break;
                case 8 : //多选
                    if(CMyString.isEmpty(sEnmValue)){
                       break;
                    }

                    arValues = sEnmValue.split("~");
                    sHtml += "<div style=\"display:inline-block;width:153px;border: #b7b7a6 1px solid;margin-top:4px;margin-bottom:4px;\">";
                    for(int ix=0,len=arValues.length;ix<len;ix++){
                        String[] arValue = pairSplit(arValues[ix]);
                        boolean bSelectFlag = false;
                        if(!CMyString.isEmpty(sValue) && !CMyString.isEmpty(arValue[1])){
                            if((","+sValue).indexOf(","+arValue[1])!=-1){
                                bSelectFlag = true;                 
                            }
                        }
                        sHtml += "<input type=\"checkbox\" style=\"border:0px;\" ID=\"" + sName + "_" + ix + "\" NAME=\"" + sName +  "\" value=\"" + arValue[1] + "\" "  + (bSelectFlag ?"checked":"")  + "/>&nbsp;<label for=\"" + sName + "_" + ix + "\">" + arValue[0] + "</label>&nbsp;</br>";
                    }
                    sHtml += "</div>";
                    break;
                default : //多行文本
                    sHtml = "<TEXTAREA rows=3 ID=\"" +sName + "\" NAME=\"" + sName +  "\"  validation=\"type:'string',max_len:'" + _currField.getMaxLength() + "',showid:'validatetip'\" validation_desc=\"" + CMyString.filterForHTMLValue(_currField.getDesc()) + "\" style=\"width:153px;border: #b7b7a6 1px solid;margin-top:4px;margin-bottom:4px;\" >"+ _sValue+"</TEXTAREA>";
            }
        }else if("int".equals(sPattern)){
            sHtml += "<SPAN class=attr_input_text style=\"WIDTH: 142px; HEIGHT: 20px\">";
            sHtml += "<INPUT ID=\"" + sName + "\" NAME=\"" + sName +  "\" value=\""+_sValue+"\"  validation=\"type:'number',min:'-2147483648',max:'2147483647',showid:'validatetip'\" validation_desc=\"" + CMyString.filterForHTMLValue(_currField.getDesc()) + "\"></span>";
        }else if("float".equals(sPattern)){
            sHtml += "<SPAN class=attr_input_text style=\"WIDTH: 142px; HEIGHT: 20px\">";
            sHtml += "<INPUT ID=\"" + sName + "\" NAME=\"" + sName +  "\" value=\""+_sValue+"\" validation=\"type:'float',min:'-2e125',max:'2e125',showid:'validatetip'\"  scale=\"" + _currField.getAttributeValue("DBSCALE") + "\" validation_desc=\"" + CMyString.filterForHTMLValue( _currField.getDesc()) + "\"></span>";
        }
        return sHtml;
    }
    private String getPattern(int _nType){
        switch(_nType){
            case Types.BIGINT:
            case Types.INTEGER:
            case Types.SMALLINT:
            case Types.NUMERIC:
                return "int";
            case Types.FLOAT:
            case Types.DOUBLE:          
                return "float";
            case Types.DATE:
            case Types.TIMESTAMP:
                return "time";
                //return " ";
            case Types.CLOB:
                return "";
            default:
                return "string";
        
        }
    }
    private String[] pairSplit(String sPair){
        String[] arValue = {"",""};
        if(sPair.indexOf("`") != -1){
            String[] tempArr = sPair.split("`");
            if(tempArr.length == 2){
                arValue[0] = tempArr[0];
                arValue[1] = tempArr[1];
            }else if(tempArr.length == 1){
                arValue[0] = arValue[1] = tempArr[0];
            }else{
                return arValue;
            }
        }else{
            arValue[0] = sPair;
            arValue[1] = sPair;
        }
        return arValue;
    }
%>
<script language="javascript">
<!--
    Event.observe(window, 'load', function(){
        if(calenderControls.length > 0){
            for (var i = 0; i < calenderControls.length; i++){
                wcm.TRSCalendar.get({input:calenderControls[i],handler:'btn' + calenderControls[i], dtFmt:'yyyy-mm-dd HH:MM', withtime:true,top:"70px",left:"140px"});
            }
        }
    });
//-->
</script>