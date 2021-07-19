<%
/** Title:              cssflag_used_check.jsp
 *  Description:
 *        检查CssFlag是否可用。
 *  Copyright:      www.trs.com.cn
 *  Company:        TRS Info. Ltd.
 *  Author:             Archer
 *  Created:            2010年5月6日
 *  Vesion:             1.0
 *  Last EditTime   :none
 *  Update Logs:    none
 *  Parameters:     see view_system_skin_name_used_check.xml
 */
%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>
<!-- WCM IMPORTS  @ BEGIN -->
<%@ page  import = "com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page  import = "java.util.HashMap" %>
<%@ page  import="java.io.File" %>
<%@ page  import="java.util.ArrayList" %>
<%@ page  import="com.trs.infra.util.CMyString" %>
<%@ page  import="com.trs.infra.common.WCMException" %>
<%@ page  import="com.trs.components.common.publish.widget.PageStyle" %>
<%@ page  import="com.trs.infra.util.CPager" %>

<!-- WCM IMPORTS  @ END -->
<!--引用公共的登录控制页面 BEGIN-->
<%@include file="../../include/public_server.jsp"%>
<%@include file="./pager_generate.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//1.获取参数
    int nPageStyleId = currRequestHelper.getInt("PageStyleId",0);
    int nPageSize = currRequestHelper.getInt("PageSize",8);
    int nPageIndex = currRequestHelper.getInt("PageIndex",1);

//2.权限校验

//3.业务逻辑
    JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
    HashMap parameters = new HashMap();
    parameters.put("ObjectId", String.valueOf(nPageStyleId));
    PageStyle aPageStyle = (PageStyle)processor.excute("wcm61_PageStyle","findById", parameters);
    if(aPageStyle==null){
        throw new WCMException(CMyString.format(LocaleServer.getString("manager_photos.jsp.style_notfound", "风格[id={0}]不存在！"), new int[]{nPageStyleId}));
    }

    //获取当前页面风格所有的上传图片
    processor.reset();
    parameters = new HashMap();
    parameters.put("ObjectId", String.valueOf(nPageStyleId));
    ArrayList uploadFileList = (ArrayList)processor.excute("wcm61_PageStyle","queryUploadImageFileList", parameters); //返回的是文件的对象
    
    //计算上传图片的路径
    String sHost = request.getServerName();
    int nPort = request.getServerPort();
    String sUploadRelativePath = "http://" + sHost + ":" + nPort + "/template/style/style_common/" + "/" + aPageStyle.getStyleName() + "/";

    // 翻页相关逻辑
    CPager currPager = new CPager( nPageSize );
    int nPageCount = uploadFileList.size();
    currPager.setItemCount( nPageCount );

    // 兼容一下当传入的页码大于当前总页码时候的场景，主要用于删除最后一页的唯一一条记录的场景
    int nPageNum = currPager.getPageCount();
    if( nPageIndex>nPageNum ) {
        nPageIndex = nPageNum;
    }
    currPager.setCurrentPageIndex( nPageIndex );

%>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <meta name="author" content="TRSArcher">
    <title WCMAnt:param="manager_photos.jsp.title">当前标识已经被使用</title>
    <link rel="stylesheet" type="text/css" href="./pager1.css"/>
    <script src="../../js/easyversion/lightbase.js"></script>
    <script src="../../js/easyversion/extrender.js"></script>
    <script src="../../js/easyversion/elementmore.js"></script>
    <style type="text/css">
        body{
            background:#ffffff;
            font-size:14px;
            font-weight:normal;
            scrollbar-face-color: #f6f6f6; 
            scrollbar-highlight-color: #ffffff; 
            scrollbar-shadow-color: #cccccc; 
            scrollbar-3dlight-color: #cccccc; 
            scrollbar-arrow-color: #330000; 
            scrollbar-track-color: #f6f6f6; 
            scrollbar-darkshadow-color: #ffffff;
        }
        body,div,ul,li,span{
            padding:0;
            margin:0;
        }

        .note_color{
            font-size:14px;
            font-family:"宋体";
            color:#097f12;
        }
        .td_head_bg{
            font-size:12px;
            height:30px;
            left:0px;
            top:0px;
        }
        .thumb_container{
            overflow:hidden;
            overflow-x:hidden;
            overflow-y:auto;
        }
        .thumb_item{
            float:left;
            width:105px;
            margin:6px;
            overflow: hidden;
            cursor:default;
        } 
        .thumb_item .thumb{
            position:relative;
            height:90px;
            padding:2px;
            margin-bottom:4px;
            background:center center no-repeat;
            border:1px solid lightgrey;
            text-align:center;
            vertical-align:middle;
            cursor:hand;
        }
        .thumb_item .attrs{
            height:20px;
            line-height:20px;
            white-space:nowrap;
            overflow:hidden;
        }
        .thumb_item .attrs_selected{
            height:20px;
            line-height:20px;
            white-space:nowrap;
            overflow:hidden;
            background-color:#bef1c2;
        }
        .thumb_span{
            font-size:12px;
            font-family:"宋体";
            color:#013f06;/*#097f12*/
        }
        .font14_td{
            font-size:14px;
            font-family:"宋体";
        }
        .page-nav{
            float:right;
            margin-right:60px;
            margin-top:10px;
            font-size:12px;
            line-height:25px;
        }
        .list_navigator{height:29px;background:#ffffff;}
        .list-navigator *{font-size:12px!important;}
        .list_navigator_div{float:right;font-size:10.5pt;padding:2px;}
    </style>
    <script type="text/javascript" src="./manager_photos.js"></script>
    
</head>
<body style="margin:0px;padding:0px;">
    <table border="0" cellspacing="0" cellpadding="0" width="100%" style="border:1px gray solid;margin:0px;padding:0px;">
        <!-- 头部 begin -->
        <tr>
            <td width="100%" class="td_head_bg">
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                        <td style="padding-left:8px;"><span class="note_color"  WCMAnt:param="manager_photos.jsp.page_style">页面风格[<%=CMyString.transDisplay(aPageStyle.getStyleName())%>(<%=CMyString.transDisplay(aPageStyle.getStyleDesc())%>)的图片:</span>]</td>
                        <td width="17px" style="cursor:hand;" onclick="deleteSelectedPics(<%=nPageStyleId%>);"><img src="./images/delete.gif" width="17px" height="16px"/></td>
                        <td width="35px" align="center" class="font14_td" style="cursor:hand;" onclick="deleteSelectedPics(<%=nPageStyleId%>);"><a href="#" onclick="return false;"  WCMAnt:param="manager_photos.jsp.delete">删除</a></td>
                    </tr>
                </table>
            </td>
        </tr>
        <!-- 头部 end -->

        <!-- 内容 begin -->
        <tr>
            <td width="100%">
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                        <div class="thumb_container">
                        <%
                            for( int i=(currPager.getFirstItemIndex()-1);i<currPager.getLastItemIndex(); i++ ){
                                File aFile = (File)uploadFileList.get(i);
                                if(aFile==null){
                                    continue;
                                }
                        %>
                            <div class="thumb_item">
                                <div class="thumb" onmousewheel="img_zoom(<%=i+1%>)" onclick="openPic(<%=i+1%>)"  WCMAnt:paramattr="title:manager_photos.jsp.title_attr" title="点击查看原图，滚动滚轮进行缩放">
                                    <img src="<%=(sUploadRelativePath + aFile.getName())%>" id="div_img_<%=i+1%>" width="97px" style="position:absolute;left:2px;top:2px;" onload="resizeIfNeed(this);" complete="complete"/>
                                </div>
                                <div id="div_attr_<%=i+1%>" class="attrs" onclick="changeSelected(<%=i+1%>,event)" IdNumber="<%=i+1%>" fileName="<%=aFile.getName()%>">
                                    <input type="checkbox" value="on" id="checkbox_attr_<%=i+1%>"/>
                                    <span title="<%=aFile.getName()%>" class="thumb_span" id="span_attr_<%=i+1%>"><%=aFile.getName()%></span>
                                </div>
                            </div>
                        <%
                            }// end for
                        %>
                        </div>
                    </tr>
                </table>
            </td>
        </tr>
        <!-- 内容 end -->
        <!-- 翻页 begin -->
        <tr>
            <td valign="bottom">
                <form id="frmPage" name="frmPage" action="" method="get" style="margin:0px;padding:0px;">
                    <input type="hidden" name="PageSize" value="<%=nPageSize%>">
                    <input type="hidden" name="PageIndex" value="<%=nPageIndex%>">
                    <input type="hidden" name="PageStyleId" value="<%=nPageStyleId%>">
                    <TABLE width="100%" height="38px" border="0" cellspacing="0" cellpadding="0">
                        <TR>
                            <td align="right" style="padding-right:10px;">
                                <%=generatePager(currPager)%>
                            </td>
                        </TR>
                    </TABLE>
                </form>
            </td>
        </tr>
        <!-- 翻页 begin -->
    </table>
</body>
</html>