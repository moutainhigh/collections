<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.cms.process.definition.Flow" %>
<%@ page import="com.trs.cms.process.definition.Flows" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.cms.process.definition.FlowEmployMgr" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.ajaxservice.xmlconvertors.ChannelToXML" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.content.WCMSystemObject" %>
<%@include file="../include/public_server.jsp"%>
<link href="../css/workflow.css" rel="stylesheet" type="text/css" />
<%
    response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
    response.addHeader("Cache-Control", "no-store"); //Firefox
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", -1);
    response.setDateHeader("max-age", 0);
%>
<%
    int nFlowId = 0;
    int nChannelId = currRequestHelper.getInt("ChannelId", 0);
    String sFolderRight = currRequestHelper
            .getString("RIGHTVALUE");
    if (nChannelId == 0)
        return;
    Channel channel = Channel.findById(nChannelId);
    if (channel == null) {
        throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,CMyString.format(LocaleServer.getString("flowemployee.object.not.found.channel",LocaleServer.getString("flow_employee_view.jsp.label.chnlnotfind", "没有找到ID为{0}的栏目")), new int[] { nChannelId }));
    }
    WebSite site = channel.getSite();
    if (site == null) {
        throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,CMyString.format(LocaleServer.getString("flowemployee.object.not.found.site","没有找到ID为[{0}]的栏目所在的站点"),new int[] { nChannelId }));
    }
    String siteRight = getSiteRight(site, loginUser);
    String channelRight = getChannelRight(channel,loginUser);
    FlowEmployMgr m_oFlowEmployMgr = (FlowEmployMgr) DreamFactory
            .createObjectById("FlowEmployMgr");
    Flow oFlow = m_oFlowEmployMgr.getFlow(channel);
    String sFlowRight = "";
	String sLinkRight = currRequestHelper.getString("RightValue");
    out.clear();
    int nLoadView = 1;
%>
<%
    if (oFlow != null) {
        nFlowId = oFlow.getId();
        sFlowRight = getFlowRight(oFlow, loginUser);
%>
<script language="javascript">
	var sFolderRight = '<%=CMyString.filterForJs(sFolderRight)%>';
	var sRight = "";
	if(sFolderRight == '111111111111111111111111111111111111111111111111111111111111111') {
		sFolderRight = '11111111100000000000';
	}

	var sSiteRight = '<%=CMyString.filterForJs(siteRight)%>';
	if(sSiteRight) {
		sFolderRight = __mergeRights(sFolderRight, sSiteRight);
	}
	sRight = '<%=CMyString.filterForJs(sFlowRight)%>';
	if(!sRight || sRight==''){
		sRight = '<%=CMyString.filterForJs(sLinkRight)%>';
	}else{
		if(sRight == '111111111111111111111111111111111111111111111111111111111111111') {
			sRight = '111111100000000000000000000000000000000000000000';
		}
		sRight = __mergeRights(sRight, sFolderRight);
	}
	//判断是否有修改权限
	var bCanEdit = wcm.AuthServer.hasRight(sRight, 42);//工作流编辑权限
	//如果不是admin用户，但当前编辑的工作流属于站点类型，则判定为不具有修改权限
	var nLoadView = bCanEdit ? 2 : 1;
	var src = "../../app/flow/flow_addedit.jsp?LoadView="+nLoadView+"&FlowId="+<%=nFlowId%>+"&ShowButtons=1&_fromcb_=1&OwnerType=101&OwnerId=" + <%=nChannelId%>;		
	Element.update('wcm_table_grid', "<iframe id='flowviewer' src=" + src + " style='width:99%;height:100%' frameborder='0' scrolling='no' allowtransparency='true'></iframe>");

  function __mergeRights(_r1, _r2){
		if(_r1 == null || _r2 == null) {
			return null;
		}
		_r1 += '', _r2 += '';
		if((_r1 = _r1.trim()) == '' || (_r2 = _r2.trim()) == '') {
			return;
		}//*/
		var result = [];
		if(_r1.length != _r2.length) {
			if(_r1.length < _r2.length) {
				var temp = _r2;
				_r2 = _r1;
				_r1 = temp;
			}
			var sTemp = '';
			for (var i = 0; i < (_r1.length - _r2.length); i++){
				sTemp += '0';
			}
			_r2 = sTemp + _r2;
		}
		for (var i = 0; i < _r2.length; i++){
			result.push((_r2.charAt(i) + _r1.charAt(i) > 0) ? '1' : '0');
		}
		return result.join('');
	}
</script>

<%
    } else {
        nFlowId = 0;
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tbody id="grid_NoObjectFound">
		<tr><td colspan="8" class="no_object_found"><span WCMAnt:param="flow_employee_view.jsp.none">当前栏目没有设置工作流</span></td></tr>
	</tbody>
</table>
<script type="text/javascript">
	var sRight = '<%=CMyString.filterForJs(sFolderRight)%>';
</script>
<%
    }
%>
<script>
var channelid = '<%=nChannelId%>';
var nFlowId = '<%=nFlowId%>';
var nLoadView = window.nLoadView || 0;
try{
	wcm.Grid.initInChannel({
		objId : nFlowId,
		objType : 'flow',
		right : sRight,
		LoadView : nLoadView
	});
}catch(err){
	alert(err.message);
	//Just skip it.
}
</script>
<%!
	private String getChannelRight(Channel channel, User loginUser) throws WCMException{
        RightValue rightValue = null;
        if (loginUser.isAdministrator()|| loginUser.getName().equalsIgnoreCase(channel.getCrUserName())) {
            rightValue = new RightValue(RightValue.VALUE_ADMINISTRATOR);
        } else {
            rightValue = getRightValueByMember(channel, loginUser);
        }
        ChannelToXML.doFilterRight(rightValue, channel);
        return rightValue.toString();
    }

    private String getSiteRight(WebSite site, User loginUser)
            throws WCMException {
        if (loginUser.isAdministrator()) {
            return RightValue.getAdministratorValues();
        }
        return getRightValueByMember(site, loginUser).toString();
    }

    private String getFlowRight(Flow flow, User loginUser) throws WCMException {
        RightValue rightValue = null;

        if (loginUser.isAdministrator()
                || loginUser.getName().equals(flow.getCrUserName())) {
            rightValue = new RightValue(RightValue.VALUE_ADMINISTRATOR);
        } else {
            //  rightValue = (RightValue) context.getContextCacheData("OwnerValue");
            // if (rightValue == null) {
            // 从缓存获取
            //  CMSObj owner = (CMSObj) context.getContextCacheData("Owner");

            // 如果不存在直接从Flow上获取
            //    if (owner == null) {
            CMSObj owner = (CMSObj) flow.getOwner();
            //   }

            // 构造权限值
            if (owner != null && !(owner instanceof WCMSystemObject)) {
                if (owner instanceof Channel) {
                    owner = ((Channel) owner).getSite();
                }
                rightValue = getRightValueByMember(owner, loginUser);
            } else {
                rightValue = new RightValue(0);
            }
            // context.putContextCacheData("OwnerValue", rightValue);

            //   }

        }
        return rightValue.toString();
    }

    private RightValue getRightValue(CMSObj obj, User loginUser)
            throws WCMException {
        try {
            if (loginUser.isAdministrator()) {
                return RightValue.getAdministratorRightValue();
            }
            return getRightValueByMember(obj, loginUser);
        } catch (Exception e) {
            throw new WCMException(CMyString.format((LocaleServer.getString("flowemployee.view.construct", "构造[{0}]权限信息失败!")),new Object	[]{obj}), e);
        }
    }%>