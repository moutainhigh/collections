<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>深圳市市场和质量监督管理委员会</title>
<%
	String contextpath = request.getContextPath();
%>
<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=contextpath%>/static/script/home/index.js" type="text/javascript"></script>
<style type="text/css">
#rightcontent{
	background-color: #f8f8f8;
	padding:0px 15px;
}

.right-header{
	height:40px;
	border-bottom:1px dashed #9099b7;
	font-size: 16px;
	color: #333333;	
	font-family:Microsoft YaHei;
	font-weight: 600;
	margin-bottom:25px;
}

.right-header span{
	zoom:1;
	display:inline-block;
	*display:inline;
    margin-top: 10px;
}

.right-footer{
	height:40px;
	position:relative;
}

.right-footer-left{
	width:44px;
	height:44px;
	background:url(../../static/images/other/icon_left.png) no-repeat;
	position:absolute;
	bottom:10px;
	right:73px;
	cursor:pointer;
}
.right-footer-left2{
	background:url(../../static/images/other/icon_left2.png) no-repeat;
}

.right-footer-right{
	width:44px;
	height:44px;
	background:url(../../static/images/other/icon_right.png) no-repeat;
	position:absolute;
	bottom:10px;
	right:17px;
	cursor:pointer;
}
.right-footer-right2{
	background:url(../../static/images/other/icon_right2.png) no-repeat;
}

#leftcontent{
	background-color: #e3e3e3;
	padding:0px 8px;
}

.left-header{
	height: 40px;
}

.left-header .title{
	zoom:1;
	display:inline-block;
	*display:inline;
    margin-top: 10px;
    font-size: 16px;
	color: #333333;	
	font-family:Microsoft YaHei;
	font-weight: 600;
	float:left;
}

.left-header .more{
	zoom:1;
	display:inline-block;
	*display:inline;
    margin-top: 15px;
    font-size: 12px;
	color: #999999;	
	font-family:simsun;
	float:right;
	cursor:pointer;
}

.message-header{
	height: 38px;
	border-bottom:1px dashed #9099b7;
}

.message-header .title{
	zoom:1;
	display:inline-block;
	*display:inline;
    margin-top:10px;
    font-size: 16px;
	color: #333333;	
	font-family:Microsoft YaHei;
	font-weight: 600;
	float:left;
}

.message-header .more{
	zoom:1;
	display:inline-block;
	*display:inline;
    margin-top: 15px;
    font-size: 12px;
	color: #999999;	
	font-family:simsun;
	float:right;
	cursor:pointer;
}

#messagecontent{
	padding-top:5px;
	overflow:hidden;
}

.messagelist {
	
}

.messagelist li{
	list-style:none;
	background:url(../../static/images/other/blue.png) no-repeat 5px center;
	padding-left:15px;
	font-size: 12px;
	color: #666666;	
	font-family:simsun;
	height:25px;
	line-height:25px;
	cursor:pointer;
	overflow:hidden;
	text-overflow:ellipsis;
	-o-text-overflow:ellipsis;
	white-space:nowrap;
}

.dateleft{
	padding-left:5px;
	float:left;
	width:46px;
	height:100%;
	background:url(../../static/images/other/dateinfo.png) repeat-y;
}

.dateright{
	float:right;
	width:205px;
	height:100%;
	background-color:#f3f3f3;
}

.datecontent{
	font-size: 12px;
	color: #666666;	
	font-family:simsun;
	background:white url(../../static/images/other/datearrow.png) no-repeat 21px 0px;
	overflow:hidden;
}

.datecontent ul{
	padding:10px 8px;
	list-style-type:none;
}

.datecontent ul li{
	margin:6px 0px;
}

.datecontent .datetime{
	width:50px;
}

.datecontent .datetitle{
	width:165px;
	padding-left:10px;
	overflow:hidden;
	text-overflow:ellipsis;
	-o-text-overflow:ellipsis;
	white-space:nowrap;
	vertical-align:middle;
}

.datecontent .datedelete{
	width:10px;
	cursor:pointer;
}


.jazz-date-body td{
	position: relative;
	z-index:100;
}

.jazz-date-num{
    width: 15px;
    height: 15px;
    background:url(../../static/images/other/datenum.png) no-repeat;
    position: absolute;
    right: -8px;
    top: -7px;
    font-size: 10px;
	color: #ffffff;	
	font-family:Arial;
	z-index:10000;
}
html { overflow-x: hidden; overflow-y: hidden; }
#contentpanel { overflow-x: hidden; overflow-y: hidden; }
/* .jazz-column-element {
    border: 0 none;
    float: left;
    height: 100%;
    margin: 0;
    overflow: hidden;
    padding: 0;
} */
</style>
</head>
<body>

	<div region="north" height="65">
				<jsp:include page="header.jsp"/>
	</div>

    <div  width="100%" height="100%" vtype="panel" name="panel11"  
  
         layout="column" layoutconfig="{width: ['50%','1000','50%']}"> 
  
        <div> 
  
           <!--  <div name="w1" vtype="panel" title="电影栏目" titledisplay="true" height="200"></div>  -->
  
        </div> 
  
        <div> 
  
           	<div id="contentpanel" vtype="panel" width="100%" height="100%" showborder="false" 
			layout="border" layoutconfig="{border: false, north_show_border: false, south_show_border: false, east_show_border: false}">
			
			<div region="center" id="rightcontent">
				<div class="right-header">
					<span>主要业务</span>
				</div>
				<div id="gnlb" class="right-content"></div>
				<div class="right-footer">
					<div class="right-footer-left"></div>
					<div class="right-footer-right"></div>
				</div>
			</div>
		    <div region="east" width="272" id="leftcontent"> 
		    	<div class="left-header">
					<span class="title">日常安排</span>
					<span class="more"><a herf="javascript:void(0);" onclick="addDate();">添加</a></span>
				</div>
				<div style="height:270px;width:100%;">
					<div style="height:205px;width:100%;">
						<div class="dateleft">  
							<div id="txtyear" style="margin-top:36px;color:#2493d0;font-family:Tahoma;font-size:12px;"></div>
							<div id="txtmonth" style="margin-top:7px;color:#2493d0;"></div>
							<div id="txtdate" style="margin-top:20px;color:#ffffff;font-family:Tahoma;font-size:42px;"></div>
						</div>
						<div class="dateright">
							<div style="margin:0px auto;width:193px;height:200px;">
								<div id="date" name="date" width="193" vtype="date" select="onselect"></div>
							</div>
						</div>
					</div>
					<div class="datecontent">
						<ul id="dateinfo">
							<li>暂无行程安排</li>
						</ul>
					</div>
				</div>
				<div class="message-header">
					<span class="title">消息提醒</span>
					<span class="more"><a herf="javascript:void(0);" onclick="messageMore();">更多</a></span>
				</div>
				<div id="messagecontent">
					<ul class="messagelist">
					</ul>
				</div>	
			</div>
			<div region="south" height="34">
				<jsp:include page="footer.jsp"/>
			</div>
		</div> 
  
        </div> 
  
        <div> 
  
    <!--         <div name="t1" vtype="panel" title="新闻栏目" titledisplay="true" height="120"></div> 
  
            <div name="t2" vtype="panel" title="军事栏目" titledisplay="true" height="120"></div>  -->
  
        </div>    
  
    </div> 
  
	
</body>
</html>