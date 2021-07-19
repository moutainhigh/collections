<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="common/common.jsp" %>
<c:forEach items="${exams}" var="exam">
<!-- 	2(0) Banner特效2(1) Banner特效2(2) 100KB(3) zip(4) 2(5) 1(6) 2015-10-31 23:25:42.0(7) 2015-10-31 23:26:24.0(8) 1(9) 0(10) 1(11) 韩培磊(12) -->
	<li data-opid="${exam[0]}" data-url="${exam[12]}"><!-- class='on' -->
		<span class="icon"><i class="fa fa-file-archive-o"></i></span>
		<span class="op">
			<a href="${basePath}${exam[12]}" target="_blank" class="download"><i class="fa fa-download"></i></a>
			<a href="javascript:void(0);" class="looksee"><i class="fa fa-street-view"></i></a></span>
		<div class='clearfix' style="padding-top:3px;"><span class="cnt">${exam[1]}</span>
		<span class="cnt" style="font-size:12px;">${exam[13]}&nbsp;${tz:dateFormate(exam[7])}</span></div>
	</li>
</c:forEach>
