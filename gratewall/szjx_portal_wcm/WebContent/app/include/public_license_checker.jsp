<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.key.wcm.BaseWCMKeyUtil" %>
<%@ page import="com.trs.infra.I18NMessage" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%
if(!rightLicense()){
	session.setAttribute("sLicenseInfo",sLicenseInfo);
	//System.out.println("---------------"+sLicenseInfo);
	response.sendRedirect("license_edit.html");
}
%>

<%!
private String sLicenseInfo="";
private boolean rightLicense() {
        BaseWCMKeyUtil keyUtil = null;
        try {
            keyUtil = BaseWCMKeyUtil.getInstance();
        } catch (Exception e) {
            sLicenseInfo+=e.getMessage();
            return false;
        }

        // wenyh@2009-6-8 comment:将日志输出到stdout,方便日志查看.
        int nSiteNum = keyUtil.func2();
        if (nSiteNum < 0) {
            sLicenseInfo+=I18NMessage.get(DBManager.class,
                    "DBManager.label2", LocaleServer.getString("license_checker.error","注册码有问题，非法使用！\n"));
            return false;
        }
        if (nSiteNum == 0)
            return true;
        try {
            String sSQL = "select count(*) num from WCMWebSite";
            int nCount = DBManager.getDBManager().sqlExecuteIntQuery(sSQL);
            if (nCount <= 0)
                return true;

            if (nCount > nSiteNum) {
                sLicenseInfo+=I18NMessage.get(DBManager.class,
                        "DBManager.label3",
                        LocaleServer.getString("license_checker.mes","站点数超出当前注册码限制，如需增加，请与TRS公司联系，感谢您使用TRS产品！\n"));
                return false;
            }

        } catch (Throwable e) {
            sLicenseInfo+=I18NMessage.get(DBManager.class,
                    "DBManager.label4", LocaleServer.getString("license_checker.wrong","注册码有问题，获取站点数不通过！\n"));
            return false;
        }

        return true;
    }
%>