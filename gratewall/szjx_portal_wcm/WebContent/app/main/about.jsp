<%@ page import="java.io.File" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%!
	public String sEndFix = "";
	/* 
	 * Java文件操作 获取更新到的FIX编号
	 * @param String
	 * @return String
	 */  
	public String getFixNum(File file){
		if (file.isFile() || (0 == file.list().length)){
			return "";
		}else{
			File[] files = file.listFiles();
			String sFix = "";
			//倒序寻找最后一个fix
			int nBigNum = 0;
			String sBigFixName = "";
			for (int i=files.length-1;i>=0;i--){
				File f=files[i];
				StringBuffer sbCompareBuff = new StringBuffer();
				if (f.isFile()){
					//获取文件名称
					sbCompareBuff.append(f.getName());
				}else{
					//获取文件夹名称
					sbCompareBuff.append(f.getName());
				}
				//若文件扩展名是否为WCMFIX和文件名开头是否为FIX且存在"_"，返回是否为最大编号标示
				if(getExtensionName(sbCompareBuff.toString()).equalsIgnoreCase("WCMFIX") && CMyString.truncateStr(sbCompareBuff.toString(),3,"").equalsIgnoreCase("FIX") && sbCompareBuff.toString().indexOf("_")>0){
					int nTempNum = Integer.parseInt(sbCompareBuff.toString().substring(3,sbCompareBuff.toString().indexOf("_")));
					if(nBigNum < nTempNum){
						nBigNum=nTempNum;
						sBigFixName = sbCompareBuff.toString();
					}
				}
			}
			if(nBigNum>0){
				sFix = sBigFixName.substring(0,sBigFixName.indexOf("_"));
			}
			return sFix;
		}
	}
	/* 
	 * Java文件操作 获取文件扩展名
	 * @param String
	 * @return String
	 */  
	public String getExtensionName(String filename){
		if ((filename != null) && (filename.length() > 0)){
			int dot = filename.lastIndexOf('.');
			if ((dot >-1) && (dot < (filename.length() - 1))){
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}
	%>
	<%
	try{
		String path = ConfigServer.getServer().getInitProperty("WCM_PATH");
		File f = new File(path+"/fix");
		sEndFix = getFixNum(f);
		if(!CMyString.isEmpty(sEndFix)){
			sEndFix = "."+sEndFix;
		}
	}catch(Exception e){}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title WCMAnt:param="about.html.aboutwcm">关于WCM...................................................................</title>
<style type="text/css">
    body{
        margin:0px;
        padding:0px;
        font-size:12px;
        padding:5px 10px 20px 10px;
    }
    #TRSLogo{
        float:left;
        width:45px;
        height:100%;
        margin:0px;
        padding:0px;
        background:url('../images/main/trslogo.bmp') 0 bottom no-repeat;
    }
    #main{
        float:right;
        height:100%;
    }
    #WCMLogo{
        height:113px;
        background:url('../images/main/wcmlogo.bmp') center center no-repeat;
    }
    #info1{
        margin:0px;
        margin-top:5px;
    }
    #info2{
        margin:0px;
        margin-top:5px;
    }
    #CopyRight{
        margin:0px;
        margin-top:15px;
    }
    #CopyRight a{
        color:black;
        float:left;
    }
    #btn{
        float:right;
        width:80px;
    }
</style>
<script language="javascript">
<!--
    window.onload = function(){
        document.getElementById("btn").focus();
    }
//-->
</script>
</head>

<body>
    <div id="TRSLogo"></div>
    <div id="main">
        <div id="WCMLogo"></div>
        <div id="info1">
            <div WCMAnt:param="about.html.version">
                版本：V7.0-1015.6612-20130506<%=sEndFix%>
            </div>
            <div WCMAnt:param="about.html.length">
                密钥长度：41-位
            </div>
            <div WCMAnt:param="about.html.proid">
                产品ID：76481-0011903-01808
            </div>
            <div  WCMAnt:param="about.html.fenbian">
                最佳分辨率：1280*1024
            </div>			
			<div>
				<a href="../../wcm_use/wcmsys_info.jsp" style="color:black;" WCMAnt:param="about.html.detailsysinfo">
					查看系统详细信息
				</a>
			</div>
        </div>		
        <div id="info2">
             <div contentEditable="true" style="width:100%;height:70px;line-height:1.5em;overflow:auto;border:solid 1px lightblue;"  WCMAnt:param="about.html.info2">
TRS公司于2013年推出WCM系列最新产品:TRS WCM 内容协作平台 7.0 (以下简称TRS WCM V7.0 ),这是TRS集自身内容管理先进理念和技术,汇聚最新内容管理应用实践经验,推出的一套完全基于Java和浏览器技术的网络内容管理软件,TRS WCM V7.0 实现全浏览器界面的内容创建,编辑和基于模板的内容发布,具备强大的站点管理功能,并提供企业级协作环境.TRS WCM V7.0是2013年TRS推向市场的最重量级产品之一,将大力引领内容管理应用深化和升级.<br /><div style="text-indent:2em;">TRS WCM V7.0面向网站内容管理,企业内容管理和信息资源建设,在网络媒体,电子政务,信息服务,知识管理等领域有广泛的应用前景.</div>     
            </div>
        </div>		
        <div id="CopyRight">
            <a href="http://www.trs.com.cn" target="_blank" WCMAnt:param="about.html.CopyRight">版权所有 &copy; 2002-2013 TRS Corp.</a>
            <button type="button" id="btn" onclick="window.close();" WCMAnt:param="about.html.CopyRightbtn">确定</button>
        </div>
    </div>
</body>
</html>