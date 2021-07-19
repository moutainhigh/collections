<%
/** Title:			channels_get.jsp
 *  Description:
 *		标准WCM V6页面，用于“获取指定节点的路径”。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2006-11-23 11:47 上午
 *  Vesion:			1.0
 *  Last EditTime:	2006-11-23/2006-11-23
 *	Update Logs:
 *		CH@2006-11-23 created the file 
 *
 *  Parameters:
 *		see treenode_path_make.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.config.RightConfigServer" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
//4.初始化（获取数据）

//5.权限校验

//6.业务代码
	createRightRelations(out);	
%>
<%!
	public static void createRightRelations(JspWriter out)throws Exception{
        RightConfigServer oRightConfigServer = RightConfigServer.getInstance();
        int[][] pSimilarIndexs = oRightConfigServer.getSimilarIndexs();
        toJavaScriptArray(out, "m_pSimilarIndexs", pSimilarIndexs);
        
        int[][] pDependIndexs = oRightConfigServer.getDependIndexs();
        toJavaScriptArray(out, "m_pDependIndexs", pDependIndexs);
    }

    /**
     * @param _pSimilarIndexs
     */
    private static void toJavaScriptArray(JspWriter out, String _sArrayName, int[][] _pSimilarIndexs) throws Exception{
        out.println("var "+_sArrayName+" = [");    
        boolean bFirst = true;
        for (int i = 0; i < _pSimilarIndexs.length; i++) {
            
            
            StringBuffer sbTemp = new StringBuffer(_pSimilarIndexs[i].length*2);
            sbTemp.append(_pSimilarIndexs[i][0]);
            for (int j = 1; j < _pSimilarIndexs[i].length; j++) {
                sbTemp.append(",");
                sbTemp.append(_pSimilarIndexs[i][j]);                
            }
            out.println("    "+(bFirst?"":",")+"[" + sbTemp + "]");
            if(bFirst){                
                bFirst = false;
            }
        }
        out.println("];");
    }
%>