<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@page import="com.trs.infra.support.config.ConfigServer"%>
<%@include file="../../include/public_server.jsp"%>
<%
	String sPubSiteURL = "http://127.0.0.1:"+request.getServerPort()+"/pub/root6/";
	String sGovSiteURL = ConfigServer.getServer().getSysConfigValue("GOV_SITE_URL", sPubSiteURL);
%>
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>政府信息公开服务系统--纸本书在线生成</title>
<!-- 必须引入的公用部分 BEGIN -->
<script language="javascript" src="../../../XWCMV6/js/com.trs.wcmv6/base.js"></script>
<script language="javascript">
<!--
	$import('com.trs.wcmv6.DataHelper');
//-->
</script>
<!-- 必须引入的公用部分 END -->
<link href="../css.css" rel="stylesheet" type="text/css" />
<link href="downloadebook.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" src="export_word.js"></SCRIPT>
<script type="text/javascript">
<!--
function MM_preloadImages() { //v3.0
	 var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>





<style type="text/css">
<!--
#apDiv1 {
	position:absolute;
	width:140px;
	visibility: hidden;
}
#apDiv2 {
	position:absolute;
	width:140px;
	z-index:1;
	visibility: hidden;
}
#apDiv3 {
	position:absolute;
	width:140px;
	visibility: hidden;
}
#apDiv4 {
	position:absolute;
	width:126px;
	z-index:1;
	visibility: hidden;
}
text-align: center;
}



-->
</style>
</head>
<body style="margin:0px;padding:0px;">
<table width="900" border="0" align="center" cellpadding="0" cellspacing="0" class="bj">
  <tr>
    <td height="535" align="center" valign="top">
	<table width="879" border="0" cellpadding="0" cellspacing="0">
		<tr>
		  <td height="109" align="right" valign="top" background="../images/banner_bj.jpg"><table border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td colspan="2" align="right" valign="top"><img src="../images/mlyl.jpg" width="94" height="40" border="0" usemap="#Map"></td>
			  </tr>
			  <tr>
				<td width="241" height="60" align="right" valign="bottom"><span class="q_huang_12hover"><%=loginUser.getName()%>，欢迎您！</span> <span class="q_huang_12hover">[</span><a href="../../WCMV6/logout.jsp" class="q_huang_12hover">退出</a><span class="q_huang_12hover">]</span></td>
				<td width="16" align="right" valign="bottom">&nbsp;</td>
			  </tr>
			</table></td>
		</tr>
	</table>
      <table width="879" border="0" cellpadding="0" cellspacing="0">
        <tr>
		  <td width="14" background="../images/bian_l.gif">&nbsp;</td>
          <td  width="851" height="44" background="../images/menu_bj2.jpg">
		  <table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="17">&nbsp;</td>
              <td width="21" align="left" valign="middle"><img src="../images/shou.gif" width="14" height="12"></td>
              <td width="803" align="left" valign="middle"><span class="hui_12hover">您现在的位置：</span><span class="hui_12hover">内容管理</span><span class="hui_12hover">&gt; <span class="hong_12hover_c">纸制目录生成</span></td>
            </tr>
          </table>
		  </td>
		  <td width="14" background="images/bian_r.gif"></td>
        </tr>
      </table>
      <table width="879" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="14" background="../images/bian_l.gif">&nbsp;</td>
          <td width="851" height="293" align="center" valign="middle" background="../images/menu_bj2.jpg">
		  <table width="661" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td height="242" align="center" valign="middle" background="../images/zlhq_bj.jpg"><table width="384" border="0" cellpadding="0" cellspacing="0">
					<tr>
					   <td colspan="2" width="330" height="47" align="right" valign="middle" class="hui_14hover_c">
							请选择目录年份：
					   </td>
					   <td colspan="2" width="330" height="47" align="left" valign="middle">
						 <select name="STARTYEAR" id="STARTYEAR" style="width:150px">
							  <%
								//年份显示的策略 上10年
								java.util.Date currDate = new java.util.Date();
								int year = currDate.getYear()+1900;
								int startYear = year - 8;
							for(int curYear = year;curYear>=startYear;curYear--)
								{
								%>
									<option value="<%=curYear%>"><%=curYear%></option>
								<%
								}	
								%>
						  </select>                  
						</td>
					</tr>
					<tr align="center">
						<td width="200" >&nbsp</td>
						<td width="130" align="center" valign="middle">
							<table width="61" height="29" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td height="29" align="center" valign="middle" background="../images/button/huang_bt.jpg"><a href="#" class="bai_12hover_c" onclick="getEBook()">生成目录</a></td>
								</tr>
							</table>
						</td>
						<td width="130" align="center" valign="middle">
							<table width="61" height="29" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td height="29" align="center" valign="middle" background="../images/button/huang_bt.jpg"><a href="#" class="bai_12hover_c"  onclick="window.close()">关 闭</a></td>
								</tr>
							</table>
						</td>
						<td width="200" >&nbsp</td>
                  </tr>
              </table></td>
            </tr>
          </table></td>
          <td width="14" background="images/bian_r.gif">&nbsp;</td>
        </tr>
      </table>
	  <input type="hidden" name="WORDPDF" ID="WORDPDF" value="doc">
      <table width="855" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><img src="../images/bian_d.jpg" width="855" height="40"></td>
        </tr>
        <tr>
          <td height="31" align="center" valign="bottom"><span class="s_huang_12">2012 版权所有：北京拓尔思信息技术股份有限公司</span></td>
        </tr>
      </table></td>
  </tr>
</table>
<map name="Map">
  <area shape="rect" coords="7,11,85,33" href="<%=sGovSiteURL %>" target="_blank">
</map>

 <div id="loading" class="loading" style="display:none;"></div>
 <div id="shield" class="shield" style="display:none;"></div>
</body>
</HTML>