<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../../include/error_for_dialog.jsp"%>
<%@ page import = "com.trs.webframework.controler.JSPRequestProcessor"%>
<%@ page import = "com.trs.components.common.publish.persistent.template.Templates"%>
<%@ page import = "com.trs.components.common.publish.persistent.template.Template"%>
<%@ page import = "com.trs.components.metadata.resource.domain.IResourceStructureMgr"%>
<%@ page import = "com.trs.components.metadata.definition.MetaView"%>
<%@ page import = "com.trs.components.common.publish.PublishConstants"%>
<%@ page import = "com.trs.DreamFactory"%>
<%@ page import = "com.trs.infra.common.WCMException"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import = "java.util.HashMap"%>
<%@include file="../../include/public_processor.jsp"%>
<%
	IResourceStructureMgr m_oRSMgr = (IResourceStructureMgr) DreamFactory.createObjectById("IResourceStructureMgr");
	// 0.获取参数
	int nViewId = processor.getParam("ViewId",0);
	String fieldIds = CMyString.showNull(processor.getParam("objectIds"), "" );
	/**页面需要的数据**/
	String sViewShowName = "",sViewDesc = "";
	int nOutlineTemplate = 0,nDetailTemplate = 0,nClassId = 0;
	MetaView metaView = null;
	if(nViewId>0){
		metaView= MetaView.findById(nViewId);
		if(metaView == null)
			throw new WCMException("没有获取到[ID="+nViewId+"]的资源结构对象！");
		else{
			sViewShowName = metaView.getDesc();
			sViewDesc = metaView.getViewDescCon();

			// 获取首页模板
			Template template = m_oRSMgr.getTemplateByRestr(metaView,PublishConstants.TEMPLATE_TYPE_OUTLINE);
			nOutlineTemplate = template==null?0:template.getId();
			// 获取细览模板
			template = m_oRSMgr.getTemplateByRestr(metaView,PublishConstants.TEMPLATE_TYPE_DETAIL);
			nDetailTemplate = template==null?0:template.getId();
		}
	}
	// 2. 获取可使用的首页模板
	Templates outlineTemps = m_oRSMgr.getTemplatesByRestr(metaView,PublishConstants.TEMPLATE_TYPE_OUTLINE);
	// 3. 获取资源模板
	Templates detailTemps = m_oRSMgr.getTemplatesByRestr(metaView,PublishConstants.TEMPLATE_TYPE_DETAIL);

%>
<%out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>新建资源结构：步骤一</title>
	<link href="resourcestr_addedit_step01.css" rel="stylesheet" type="text/css" />
	<link href="../css/blue/jquery-wcm-ui-extend.css" rel="stylesheet" type="text/css" />
	<script language="javascript" src="../js/jquery.js" type="text/javascript"></script>
	<script language="javascript" src="../js/jquery-wcm-extend.js" type="text/javascript"></script>
	<script language="javascript" src="../js/jquery-ui.js" type="text/javascript"></script>
	<script language="javascript" src="../js/jquery-wcm-ui-extend.js" type="text/javascript"></script>
	<script language="javascript">
	<!--
		function next(){
			//1. TODO validation
			if(!$("#viewDesc").val()){
				alert("资源结构名称不能为空！");
				return;
			}
			//2. ajax save
			$.wcmAjax("wcm61_resourcestructure","save","#resource_baseinfo",function(data, textStatus){
					if(<%=CMyString.isEmpty(fieldIds)%>) {
						window.location.href = "./resourcestr_addedit_step02.jsp?viewid="+data.RESULT;
					} else {
						$.wcmAjax("wcm61_MetaField","createFieldsFromLibrary",{FieldIds:'<%=fieldIds%>',ViewId:data.RESULT},function(){
							window.location.href = "./resourcestr_addedit_step02.jsp?viewid="+data.RESULT;
						});
					}
				}
			);
		}
		function cancel(){
			//1. close page
			parent.$(document.body).tabpage({
				id:'resourcestr_addedit_step01',
				fn:"close"
			});
		}
		function showClassInfo(){
			$.CrashBoard({
				id : 'select-category-info',
				title : '选择资源结构分类',
				maskable : true,
				draggable : true,
				url : '../category/category_select_tree.html?treeType=1&objectid=1&objectname=资源结构所属分类',
				width : '460px',
				height : '300px',
				params : {},
				callback : function(params){
					alert(params);
				}
			}).show();
		}
	//-->
	</script>
</head>

<body>
	<div class="container">
		<div class="header"><div class="desc">步骤一  填写基本信息</div></div>
		<div class="content">
			<table border="0" cellspacing="0" cellpadding="0" class="tb">
				<tbody>
					<tr>
						<td class="lc">
							<!-- 左边内容区 -->
							<div class="left_content">
								<!-- 顶部 -->
								<div class="t">
									<div class="t_r">
										<div class="t_c"><b>请输入下列基本信息</b><div class="hr"></div></div>
									</div>
								</div>
								<!-- 中间内容列表 -->
								<div class="c">
									<div class="c_r">
										<div class="c_c">
											<div class="basic_info" id="resource_baseinfo">
												<input type="hidden" name="objectid" value="<%=nViewId%>">
												<!--01 资源结构名称-->
												<div class="name">
													<div class="desc">资源结构名称:</div>
													<div class="text">
														<input type="text" name="VIEWDESC" id="viewDesc" value="<%=CMyString.filterForHTMLValue(sViewShowName)%>" /></div>
												</div>
												<!--02 资源结构描述-->
												<div class="name">
													<div class="desc">资源结构描述:</div>
													<div class="text">
														<input type="text" name="VIEWDESCCON" id="viewDescCon" value="<%=CMyString.filterForHTMLValue(sViewDesc)%>" /></div>
												</div>
												<!--03 首页模板-->
												<div class="name">
													<div class="desc">首页模板:</div>
													<div class="text">
														<select name="indexTempId" id="" value="<%=nOutlineTemplate%>" class="option">
														<option value="0">==请选择==</option>
											<%
												Template temp = null;
												for (int i = 0; i < outlineTemps.size(); i++){
													temp = (Template)outlineTemps.getAt(i);
													if(temp == null)continue;
											%>
														<option value="<%=temp.getId()%>" <%=temp.getId() == nOutlineTemplate?"selected":""%>>
														<%=CMyString.transDisplay(temp.getName())%>
														</option>
											<%
												}
											%>
														</select>
													</div>
												</div>
												<!--04 资源模板-->
												<div class="name">
													<div class="desc">资源模板:</div>
													<div class="text">
														<select name="detailTempId" id="" value="<%=nDetailTemplate%>" class="option">
														<option value="0">==请选择==</option>
											<%
												for (int i = 0; i < detailTemps.size(); i++){
													temp = (Template)detailTemps.getAt(i);
													if(temp == null)continue;
											%>
														<option value="<%=temp.getId()%>" <%=temp.getId() == nDetailTemplate?"selected":""%>>
														<%=CMyString.transDisplay(temp.getName())%>
														</option>
											<%
												}
											%>
														</select>
													</div>
												</div>
												<!--05 所属分类-->
												<div class="name">
													<div class="desc">所属分类:</div>
													<div class="text">
														<button onclick="showClassInfo()" id="" class="classic"></button>
													</div>
												</div>
												<!--06 按钮-->
												<div class="button">
													<button name="" id="" class="btn" onclick="next()" >下一步</button>
													<button name="" id="" class="btn" onclick="cancel()" >取  消</button>
												</div>
											</div>
										</div>
									</div>
								</div>
								<!-- 底部 -->
								<div class="b">
									<div class="b_r">
										<div class="b_c"></div>
									</div>
								</div>
							</div>
						</td>
						<td class="rc">
							<!-- 右边内容区 -->
							<div class="right_content">
								<!-- 顶部 -->
								<div class="t">
									<div class="t_r">
										<div class="t_c">
											<div class="topInfo">"新建资源结构"总共有三步：</div>
										</div>
									</div>
								</div>
								<!-- 中间内容列表 -->
								<div class="c">
									<div class="c_r">
										<div class="c_c">
											<div class="info">

												<div>
													<div class="stepInfo">步骤一</div>
													<div class="stepDesc">填写基本信息</div>
												</div>
												<div>
													<div class="stepInfo">步骤二</div>
													<div class="stepDesc">选择或新建字段</div>
												</div>
												<div>
													<div class="stepInfo">步骤三</div>
													<div class="stepDesc">资源录入界面设计</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<!-- 底部 -->
								<div class="b">
									<div class="b_r">
										<div class="b_c"></div>
									</div>
								</div>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>