<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.infra.persistent.BaseObj" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaViews" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.webframework.FrameworkConstants" %>
<%@ page import="com.trs.webframework.context.MethodContext" %>
<%@ page import="com.trs.webframework.xmlserver.ConvertException" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.common.WCMException" %>

<!------- WCM IMPORTS END ------------>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
//2. 获取业务数据
	MethodContext oMethodContext = (MethodContext)request.getAttribute(FrameworkConstants.ATTR_NAME_METHODCONTEXT);
	MetaViews objects = (MetaViews)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);

//3. 构造分页参数，这段逻辑应该都可以放到服务器端	TODO
	int nPageSize = -1, nPageIndex = 1;
	if (oMethodContext != null) {
		nPageSize = oMethodContext.getValue(FrameworkConstants.ATTR_NAME_PAGESIZE, 20);
		nPageIndex = oMethodContext.getValue(FrameworkConstants.ATTR_NAME_CURRPAGE, 1);
	}	
	CPager currPager = new CPager(nPageSize);
	currPager.setCurrentPageIndex(nPageIndex);
	currPager.setItemCount(objects.size());

	response.setHeader("Num",String.valueOf(currPager.getItemCount()));
	response.setHeader("PageSize",String.valueOf(currPager.getPageSize()));
	response.setHeader("PageCount",String.valueOf(currPager.getPageCount()));
	response.setHeader("CurrPageIndex",String.valueOf(currPager.getCurrentPageIndex()));
	out.clear();
%>
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			MetaView object = (MetaView)objects.getAt(i - 1);
			if (object == null)
				continue;
			String sObjectId = String.valueOf(object.getId());
			String sObjectName = CMyString.transDisplay(object.getName());
			String sObjectDesc = CMyString.transDisplay(object.getDesc());
			RightValue rightValue = getRightValue(oMethodContext, loginUser, object);
			String sRight = rightValue.toString();
			String sCrUser = object.getPropertyAsString("CrUser");
			String sCrTime = convertDateTimeValueToString(oMethodContext, object.getPropertyAsDateTime("CrTime"));
			boolean isSingleTable = object.getName().equalsIgnoreCase(object.getMainTableName());
%>
    <div dblclick_fun="dblclickElement" click_fun="clickElement" mousemove_fun="mousemoveElement" class="tabular" title="编号:<%=sObjectId%>&#13;英文名称:<%=sObjectName%>&#13;名称:<%=sObjectDesc%>&#13;创建者:<%=sCrUser%>&#13;创建时间:<%=sCrTime%>" id="tabular_<%=sObjectId%>" _ObjectId="<%=sObjectId%>" _rights=<%=sRight%> _isSingleTable="<%=isSingleTable%>">
        <span class="tabular_left"></span>
        <span class="tabular_center">
        <table id="tb_tabular_<%=sObjectId%>" class="tb_tabular" border=0 cellspacing=0 cellpadding=0 _ObjectId="<%=sObjectId%>" _rights=<%=sRight%>>
            <tbody>
                <tr>
                    <td align="center" colspan="2">
                        <div id="inner_tabular_<%=sObjectId%>" class="inner_tabular" _ObjectId="<%=sObjectId%>" _index="<%=i%>" _rights=<%=sRight%>>
                        </div>
                     </td>
                 </tr>								
                 <tr>
                    <td width="16" valign="middle" align="center" height="25">
                        <input id="chk_<%=sObjectId%>" type="checkbox" class="wcm_pointer element_list_checkbox" name="DocId" value="<%=sObjectId%>" title="选中/取消选中"></input>
                    </td>
                    <td class="inner_tabular_tex" style="padding-top:3px;padding-left:1px;">
						<div class="ellipsisStyle">
							<span type="text" id="txt_dispname_<%=sObjectId%>" style="font-size:12px;" click_fun="clickDesc"><%=sObjectDesc%></span>
						<%if(rightValue.isHasRight(WCMRightTypes.SITE_EDIT)){%>
							<input click_fun="" id="txt_dispname_<%=sObjectId%>_" type="text" value="<%=sObjectDesc%>" style="display:none;" class="input_class" validation="type:'string',required:'',max_len:'60',desc:'视图名称'" name="ViewDesc">
						<%}%>
						</div>
                    </td>
                </tr>
            </tbody>
        </table>
    </span>
    <span class="tabular_right"></span>
    </div>
<%
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
%>
<%!
	private String convertDateTimeValueToString(MethodContext _methodContext, CMyDateTime _dtValue) {
		String sDateTimeFormat = CMyDateTime.DEF_DATETIME_FORMAT_PRG;
		if (_methodContext != null) {
			sDateTimeFormat = _methodContext.getValue("DateTimeFormat");
			if (sDateTimeFormat == null) {
				sDateTimeFormat = CMyDateTime.DEF_DATETIME_FORMAT_PRG;
			}
		}
		String sDtValue = _dtValue.toString(sDateTimeFormat);
		return sDtValue;
	}

    private RightValue getRightValue(MethodContext context, User loginUser, BaseObj _object) throws ConvertException {
		MetaView object = (MetaView) _object;
        if (context.getValue("ContainsRight", false)) {
            try {
                if (loginUser.isAdministrator()) {
                    return RightValue.getAdministratorRightValue();
                }
                return getRightValueByMember(object, loginUser);
            } catch (Exception e) {
                throw new ConvertException("构造[" + object + "]权限信息失败!", e);
            }
        }
        return null;
    }
%>
