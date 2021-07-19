<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%-- ----- LUCENE IMPORTS BEGIN ---------- --%>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.infra.support.security.SecureKey" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="sun.misc.BASE64Decoder" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="java.io.File" %>

<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;
%>
<%

boolean bRender = currRequestHelper.getBoolean("render", false);
String result = null;
if (bRender){
	result = exportKey("InfogateKey", true);
}	
//*/
%>
<HTML>
<HEAD>
<TITLE>建立InfogateKey密钥文件</TITLE> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</HEAD>

<BODY>
<div>
<form method=get action="syskey_render.jsp">
	<table border="0" cellspacing="5" cellpadding="8">
	<tbody>
		<tr>
			<td colspan="2" align="center"><input type="submit" value="开始执行" style="width: 120px;"><input type="hidden" name="render" value="1"></td>
		</tr>
	</tbody>
	</table>
</form>
</div>
<br>
<%
	if(result != null){
%>
<div>
	<pre>
<%=result%>
	</pre>
</div>
<%	
	}
%>
<br>
</BODY>
</HTML>

<%!

    public String exportKey(String _sKeyName, boolean _zIsPrivate)
            throws WCMException {
        SecureKey secureKey = SecureKey.findByName(_sKeyName);
        assertNotNull(secureKey, _sKeyName);
        return exportKey(secureKey, _zIsPrivate);
    }

    private String exportKey(SecureKey _secureKey, boolean _zIsPrivate)
            throws WCMException {
        FilesMan fileMan = FilesMan.getFilesMan();
        String fn = fileMan
                .getNextFilePathName(FilesMan.FLAG_SYSTEMTEMP, "dat");
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(new File(fn));
            BASE64Decoder decoder = new BASE64Decoder();
            String keyInfo = null;
            if (_zIsPrivate) {
                keyInfo = _secureKey.getPropertyAsString("PRIVATEKEY");
            } else {
                keyInfo = _secureKey.getPropertyAsString("PUBLICKEY");
            }

            fout.write(decoder.decodeBuffer(keyInfo));
            fout.close();
        } catch (Exception e) {
            throw new WCMException(ExceptionNumber.ERR_FILEOP_FAIL, "生成密钥文件失败！");
        }

        return CMyFile.extractFileName(fn);
    }
    private void assertNotNull(SecureKey _secureKey, String _sKeyName)
            throws WCMException {
        if (_secureKey == null) {
            throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,
                    "没有找到[KeyName=" + _sKeyName + "]的SecureKey对象!");
        }
    }
%>