<%@page import="com.trs.infra.common.WCMException"%>
<%!
	/**
	*  判断文件后缀名是否是不允许上传的文件后缀名
	*/
	public boolean isForbidFileExt(String fileName) throws WCMException{

		String strExt = CMyFile.extractFileExt(fileName).toUpperCase();
		// 文件后缀为空
		if(CMyString.isEmpty(strExt))
			return false;

		// 获取系统允许上传文件后缀配置
		String strSuffixConfig = com.trs.infra.support.config.ConfigServer.getServer().getInitProperty("FILE_UPLOAD_SUFFIX_CONFIG");
		// 为空表示为禁止
		if(CMyString.isEmpty(strSuffixConfig))
			return true;
		
		// 如果是启用了白名单
		if("FILE_UPLOAD_ALLOW_SUFFIX".equals(strSuffixConfig)){
			String strAllowExt = com.trs.infra.support.config.ConfigServer.getServer().getInitProperty(strSuffixConfig);
			if(CMyString.isEmpty(strAllowExt))
				return true;
			strAllowExt = strAllowExt.toUpperCase().trim();
			return (","+strAllowExt+",").indexOf(","+strExt+",")<0;
		}else if("FILE_UPLOAD_FORBIDEN_SUFFIX".equals(strSuffixConfig)){// 如果是启用了黑名单
			String strForbidExt = com.trs.infra.support.config.ConfigServer.getServer().getInitProperty(strSuffixConfig);
			if(CMyString.isEmpty(strForbidExt))
				return false;
			strForbidExt = strForbidExt.toUpperCase().trim();
			return (","+strForbidExt+",").indexOf(","+strExt+",")>0;
		}
		// 默认为禁止
		return true;
	}
%>