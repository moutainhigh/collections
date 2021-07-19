<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@include file="../include/public_server.jsp"%>
<%
    //接收页面参数
	String sWatermarkFile = currRequestHelper.getString("WatermarkFile");
	String sWatermarkPos = currRequestHelper.getString("WatermarkPos");
	//
    int nChannelId = currRequestHelper.getInt("ChannelId",0);
    int nSiteId = currRequestHelper.getInt("SiteId",0);
    int nSiteId4KeyWord = currRequestHelper.getInt("SiteId4KeyWord",0);
    String sDocs = currRequestHelper.getString("DocIds");   
    String[] str = sDocs.split(",");
    int[] intStr = new int[str.length]; 
    //获取整数型文档集合
    for(int i=0;i<str.length;i++){ 
        intStr[i] = Integer.parseInt(str[i]); 
    } 
    int nDocId = currRequestHelper.getInt("currDocId",0);
    if(nDocId == 0){
        nDocId = intStr[0];
    }
    Document currDocument = Document.findById(nDocId);
    if(currDocument == null){
        throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,CMyString.format(LocaleServer.getString("photoprops_edit.jsp.doc_notfound", "没有找到指定ID为[{0}]的文档!"), new int[]{nDocId}));
    }
    int nSiteType = currDocument.getChannel().getSite().getType();
    //保存相关文档的chnldocid，做changestatus用
    Document newDocument = null;
    ChnlDoc currChnlDoc = null;
    String sChnlDoc = "";
    for(int i=0;i<intStr.length;i++){ 
        newDocument = Document.findById(intStr[i]); 
        currChnlDoc = ChnlDoc.findByDocument(newDocument);
        sChnlDoc += (currChnlDoc.getId() + ",");
    } 
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title WCMAnt:param="photoprops_edit.jsp.title">图片基本属性编辑</title>
    <style>
        .input_text{
            height:20px;
            border:1px solid lightgray;
            margin:0 5px;           
        }
        .input_textarea{
            height:100;
            width:600;
            border:1px solid lightgray;
            margin-left:35px;
            font-size:14px;
        }
        #photo_container{
            width:240px;
            margin:10px;        
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
        #toptip{
            width:100%;height:16px;font-size:12px;border-bottom:1 solid green;margin-top:2px
        }
        .toptip_num{
            font-size:16px;color:#9f0000;padding:0 2px
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
        .cropHandler{
            display:block;
            padding:4px;
            text-decoration:underline;
            color:blue;
            cursor:pointer;
        }
        .scopeTip{
            padding:4px;
            text-decoration:underline;
            color:blue;
            cursor:pointer;
        }
        .cancelCrop{
            margin-left:4px;
            text-decoration:underline;
            color:blue;
            cursor:pointer;
        }
		.validTip{
			margin-left:50px;
		}
		.ext-ie6 .validTip{
			margin-left:-200px;
		}
        .ext-ie .calendarShow{
            padding:0px;
        }
        
    </style>
    <link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
    <link href="../../app/js/easyversion/resource/calendar.css" rel="stylesheet" type="text/css" />
    <link href="../../app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
    <link href="../../app/js/source/wcmlib/suggestion/resource/suggestion.css" rel="stylesheet" type="text/css" />
</head>

<body>
    <div id="bodyDiv" style="display:none;">
        <div id="toptip">
        <span style="margin-left:50px">
            <span WCMAnt:param="photolib_edit.jsp.count">图片共有:</span><span id="picnums" class="toptip_num"><%=intStr.length%></span><span WCMAnt:param="photolib_edit.jsp.tip">当前顺序为:</span><span id="currpic" class="toptip_num">1</span>
        </span>
        </div>
        <div id="photoprops">
            <form id="form_photoprops">
            <input type="hidden" name="ObjectId" id="ObjectId" value="">
            <input type="hidden" name="ChannelId" id="ChannelId" value="<%=nChannelId%>">
			<div style="margin-top:10px;height:180px">				
                <div id="photo_container" style="margin-top:10px;height:120px;">            
                    <img src="" width="130px" height="100px" id="imgId" style="display:none">   
                    <span class="cropHandler" onclick="crop(<%=nSiteId4KeyWord%>,'<%=sWatermarkFile%>','<%=sWatermarkPos%>')" WCMAnt:param="photoprops_edit.jsp.editpic">编辑图片</span>
                    <span class="cancelCrop" id="cancelCropHandler" onclick="cancelCrop();" style="display:none;" WCMAnt:paramattr="title:photoprops_edit.jsp.titleAttr" title="放弃当前的编辑操作，不保存编辑后的图片" WCMAnt:param="photoprops_edit.jsp.cancel_edit">取消编辑</span>
                </div>
                <div  id="infoContainer">
                    <div style="margin-right:-6px">
                        <label WCMAnt:param="photoprops_edit.jsp.headTitle">标题</label>
                        <input type="text" name="DocTitle" id="DocTitle" value="" class="input_text" validation="required:'true',type:'string',max_len:'200',desc:'标题',showid:'validatetip'" validation_desc="标题" WCMAnt:paramattr="validation_desc:photoprops_edit.jsp.headTitle"></input><br/>
                    </div>
                    <div>
                        <label WCMAnt:param="photoprops_edit.jsp.author">作者</label>
                        <input type="text" name="DocAuthor" id="DocAuthor" value="" class="input_text" validation="type:'string',max_len:'100',desc:'作者',showid:'validatetip'" validation_desc="作者" WCMAnt:paramattr="validation_desc:photoprops_edit.jsp.author"></input><br />        
                    </div>
                    <div>
                        <label WCMAnt:param="photoprops_edit.jsp.people">人物</label>
                        <input type="text" name="DocPeople" id="DocPeople" value="" class="input_text" validation="type:'string',max_len:'100',desc:'人物',showid:'validatetip'" validation_desc="人物" WCMAnt:paramattr="validation_desc:photoprops_edit.jsp.people"></input><br />
                    </div>
                    <div>
                        <label WCMAnt:param="photoprops_edit.jsp.place">地点</label>
                        <input type="text" name="DocPlace" id="DocPlace" value="" class="input_text" validation="type:'string',max_len:'100',desc:'地点',showid:'validatetip'" validation_desc="地点" WCMAnt:paramattr="validation_desc:photoprops_edit.jsp.place"></input><br />
                    </div>
                    <div id="time">
                        <label WCMAnt:param="photoprops_edit.jsp.time">时间</label>   
                        <input type="text" name="DocRelTime" id="DocRelTime" elname="<%=LocaleServer.getString("photoprops_edit.label.picRelTime", "时间")%>" value="<%=currDocument.getReleaseTime()%>" class="input_text"><button id="embed" class="calendarShow" type="button"><IMG alt="" src="../images/icon/TRSCalendar.gif" border=0 class="img"></button><br/>
                    </div>
                    <div>
                        <label WCMAnt:param="photoprops_edit.jsp.keywords">关键词</label>
                        <input type="text" name="DocKeywords" id="DocKeywords" value="" class="input_text" validation="type:'string',max_len:'100',desc:'关键词',showid:'validatetip'" validation_desc="关键词" WCMAnt:paramattr="validation_desc:photoprops_edit.jsp.keywords"></input><br />
                    </div>
				<div style="height:18px;">&nbsp;</div><br/>
				<span id="validatetip" class="validTip">&nbsp;</span>
                </div>              
            </div>
            <div id="attr_desc" style="margin:10px">
                <label style="float:left;margin-left:35px" WCMAnt:param="photoprops_edit.jsp.describe">描述</label><br />
                <textarea name="DocContent" id="DocContent" class="input_textarea" scroll="auto" validation="type:'string',max_len:'200',showid:'validatetip',desc:'图片描述'" validation_desc="图片描述" WCMAnt:paramattr="validation_desc:photoprops_edit.jsp.picdescribe"></textarea>
            </div>
            </form>
        </div>
    </div>  
<INPUT TYPE="hidden" id="ChnlDocs" value="<%=sChnlDoc%>"/>
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
<script src="photoprops_edit.js"></script>
<script language="javascript">
    var cale = wcm.TRSCalendar.get({
        input : 'DocRelTime',
        dtFmt : 'yyyy-mm-dd',
        handler : 'embed',
        withtime : true
    });
    var nSiteId4KeyWord = <%=nSiteId4KeyWord%>;
    var nSiteType = <%=nSiteType%>;
</script>
<script>
    var controler = 0;
    window.m_fpCfg = {
        m_arrCommands : [{
            cmd : 'ok',
            name : wcm.LANG.PHOTO_CONFIRM_1 || '确定'
        },{
            cmd : 'onNext',
            name : wcm.LANG.PHOTO_CONFIRM_59 || '下幅'
        },{
            cmd : 'onPrevious',
            name : wcm.LANG.PHOTO_CONFIRM_60 || '上幅'
        }],
        withclose : false,
        size : [690, 350]
    };
</script>   
</body>
</html>