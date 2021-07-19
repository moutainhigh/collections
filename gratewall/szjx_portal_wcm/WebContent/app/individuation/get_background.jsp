<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.io.File" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="java.io.FilenameFilter" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.util.ExceptionNumber" %>
<%@ page import="com.trs.presentation.util.LoginHelper" %>
<%@ page import="com.trs.presentation.util.ResponseHelper" %>

<%@ page import="com.trs.infra.util.CMyString" %>

<%@include file="../../include/public_server.jsp"%>

<% out.clear(); %>
<%=getSystemBackground(loginUser, application)%>

<%!
	 private String getSystemBackground(User loginUser, ServletContext application)throws Exception{                
        StringBuffer sb = new StringBuffer();

		//处理系统背景
       String webpicPath = FilesMan.getFilesMan().getPathConfigValue(FilesMan.FLAG_WEBFILE,FilesMan.PATH_LOCAL);
	   webpicPath = CMyString.setStrEndWith(webpicPath,'/');
        File sysDirectory = new File(webpicPath+"images/login/background");
		FilenameFilter filter = new Filter("(.)+\\.(jpg|bmp|gif|png)$");               
        File[] sysImgFiles = sysDirectory.listFiles(filter);
		setBackground(sysImgFiles, sb, "");

		//处理用户背景
		String currUserId = String.valueOf(loginUser.getId());		
		File userDirectory = new File(sysDirectory, currUserId);
		File[] userImgFiles = new File[0];
		if(userDirectory.exists() && userDirectory.isDirectory()){
			userImgFiles = userDirectory.listFiles(filter);
		}
		setBackground(userImgFiles, sb, currUserId + "/");

		//返回处理结果
		return sb.toString();
    }
    
	/**
	*@param	files	当前处理的文件数组
	*@param	sb		处理过程中，文件名添加到sb
	*@param	prefix	处理过程中，文件名需要添加的前缀信息
	*/
	private void setBackground(File[] files, StringBuffer sb, String prefix){
		if(files == null)return;
		List fileLists = Arrays.asList(files);
		Collections.sort(fileLists, new Comparator(){
			public int compare(Object obj1, Object obj2){
				File file1 = (File)obj1;
				File file2 = (File)obj2;
				return file1.getName().compareToIgnoreCase(file2.getName());
			}
		});
        for(int i = 0, size = fileLists.size(); i < size; i++){
            String fileName = ((File)fileLists.get(i)).getName();
            sb.append(prefix + fileName + ",");        
		}
	}

	/*
     * 文件过滤器
     */
    private class Filter implements FilenameFilter{       
        private Pattern suffixPattern;
        
        public Filter(String suffix){           
            this.suffixPattern = Pattern.compile(suffix, Pattern.CASE_INSENSITIVE);
        }
        public boolean accept(File dir, String name) {
            if(new File(dir, name).isDirectory()) return false;
            if(this.suffixPattern.matcher(name).matches()) return true;
            return false;
        }
    }
%>