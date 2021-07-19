<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/page/page-search.js"></script>
	<form action="<%=request.getContextPath()%>/search-pages/search.jsp" onsubmit="return checkInput()" method="post">
		<table width="95%" height="100" border="0" cellPadding="0" cellSpacing="0" align="center">
			<tr>
				<td align="center" valign="bottom"><input type="text" name="query" id="query" style="width:300px;"/> <input type="submit" value="  搜 索" style="background:url('../../images/search/searchButton.jpg') #EBEDDF no-repeat"/></td>
			</tr>
		</table>
	</form>
	<div id="queryHelpDiv" style="display:block">
  	<h3>使用说明：</h3>
  	<ul>
		<li>1、输入相应的搜索条件，点击【搜索】搜索符合条件的登记信息；系统可以搜索内资、外资、私营、个体、非企业、黑牌、案件、变更等相关信息。</li>
		<li>2、输入条件的要求：
			<ul>
				<li>a)	必须多于一个汉字，不能输入引号(“'”、“"”)、斜线(“\”)等特殊字符，不能只输入“深圳市”、“中国”、‘海淀区’、“公司”等文字。</li>
				<li>b)	由于搜索数据量较大，建议采用较为完整的关键字进行搜索以获得最佳搜索结果。</li>
				<li>c)	系统会根据输入的条件进行搜索，不用输入分隔符等信息，如搜索“软件中国”可以搜索出“德信软件(中国)有限公司深圳技术开发分公司”这样的中间带有特殊符号的结果。</li>
				<li>d)	如果需要多个关键字搜索的时候，可以采用空格分隔关键字的形式，如“中国 软件”来进行搜索</li>
				<li>e)	采用空格分隔开的关键字不分先后顺序，如“中国 软件”和“软件 中国”的搜索结果一致</li>
			</ul>
		</li>
		<li>3、注意事项：如果点击搜索结果的时候出现“数据今日发生变化，请次日查询”的提示，是数据于今日产生了变更，请于次日再重新查询！</li>
	</ul>
  </div>
</freeze:html>
