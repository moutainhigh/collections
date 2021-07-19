<%
	/**
	 *@description : 此页面主要用来决定导航树和列表页面的选择问题。
	 *1)左边显示什么类型的树,如：传统栏目导航、政府信息公开、等元数据视图
	 *2)列表页面显示什么对象的列表，如：站点列表页面、政府信息的记录页面等
	 */
%>
<%@ page import="com.trs.presentation.nav.*" %> 
<%@ page import="java.util.Map,java.util.HashMap" %> 
<%	
	/**
	*@variable  navMode
	*决定导航树当前显示的模式.
	*如：传统的模式（仅在“高级检索”和“导航树”之间的切换）NavConstatns.SIMPLE_MODE、
	*包含视图列表在内的模式，如：添加了“政府信息公开目录的切换”NavConstatns.MULTI_MODE
	*如果需要改为其它形式的导航，可以在这个地方进行设置
	*枚举值为：NavConstatns.SIMPLE_MODE, NavConstatns.MULTI_MODE
	*/
	//int navMode = NavConstatns.MULTI_MODE;
	int navMode = currRequestHelper.getInt("navMode", NavConstatns.SIMPLE_MODE);

	/**
	*@variable  itemKey
	*在当前导航模式下，需要显示的默认活动Item标签，默认为“栏目导航”树
	*可能有的枚举值为：\"nav_tree\"、\"adv_search\"、"classinfo"等
	*如果需要设置活动标签，可以在这个地方进行设置
	*/
	//String sCurrItemkey  = "classinfo";
	String sCurrItemkey = currRequestHelper.getString("itemKey");

	//String sViewDesc	= "政府公开信息";// 视图名称，这个参数只有当sCurrItemkey="classinfo"生效
	// 视图名称，这个参数只有当sCurrItemkey="classinfo"生效
	String sViewDesc = currRequestHelper.getString("viewDesc");

	/**
	*@variable  mainFirstLink
	*变量mainFirstLink已经经过了个性化定制的处理，如显示“站点列表”还是显示“工作流列表”等。
	*在此再进行视图链接的处理，如：显示“政府信息公开目录”还是显示其他视图的数据列表
	*/

	//处理当前的导航和列表	
	INavHandler oHandler = NavHandlerFactory.getNavHandler(sCurrItemkey);
	
	Map oArguments = new HashMap(3);
	oArguments.put("ViewDesc", sViewDesc);

	String sNavInitJSON = oHandler.toNavInfoJSON(oArguments);
	String sURL = oHandler.makeDefaultMainURL();
	if(sURL != null && sURL.length()>0){
		mainFirstLink = sURL;
	}

	//处理主页面传递过来的mainUrl参数
	String sMainUrl = currRequestHelper.getString("mainUrl");
	if(sMainUrl != null){
		mainFirstLink = sMainUrl;
	}
%>

<script language="javascript">
<!--
	if(!window.PageContext){
		var PageContext = {};
	}
	PageContext.PageConfig = {
		<%=sNavInitJSON%>
		"NavModeType" : {
			SIMPLE_MODE : <%=NavConstatns.SIMPLE_MODE%>,
			MULTI_MODE : <%=NavConstatns.MULTI_MODE%>
		},
		navMode : <%=navMode%>
	}


	
//-->
</script>