<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/pub/pub.jsp"%>
<%-- 	<table>
		<tr>
			<td>pripid 主体身份代码CA14</td>
			<td>uniscid 统一信用代码</td>
			<td>entname 企业名笱</td>
			<td>regno 注册号</td>
			<td>oldenttype 市场主体类型（金信）</td>
		</tr>
		<c:forEach var="item" items="${list}" varStatus="status">
			<tr>
				<td>${item.pripid }</td>
				<td>${item.uniscid }</td>
				<td>${item.entname }</td>
				<td>${item.regno }</td>
				<td>${item.oldenttype}</td>
			</tr>
		</c:forEach>
	</table> --%>

<!-- https://www.cnblogs.com/sjqq/p/7420489.html -->
<table id="List"></table>

<script type="text/javascript">
    $(function () {
        $('#List').datagrid({
            url: 'grid06',
            width: $(window).width() - 10,
            methord: 'post',
            height: $(window).height() -35,
            fitColumns: true,
            sortName: 'Id',
            sortOrder: 'desc',
            idField: 'Id',
            striped: true, //奇偶行是否区分
            singleSelect: true,//单选模式
            rownumbers: true,//行号
            pageSize: 20,//每页显示的记录条数，默认为10 
            pageList: [10,20,30],//选择一页显示多少数据               
            pagination : true,//在DataGrid控件底部显示分页工具栏。
            columns: [[
                { field: 'regno', title: '注册号', width: 120 },
                { field: 'entname', title: '名称', width: 280 },
                { field: 'regcap', title: '注册资本', width: 80, align: 'right' },
                { field: 'regcapcur_cn', title: '币种', width: 80, align: 'right' },
                { field: 'enttype_cn', title: '企业类型', width: 80, align: 'right' },
                { field: 'industryco', title: '行业分类', width: 80, align: 'right' },
                { field: 'estdate', title: '成立日期', width: 180, align: 'right' },
                { field: 'apprdate', title: '核准日期', width: 180, align: 'right' },
                { field: 'dom', title: '地址', width: 180, align: 'right' },
                { field: 'regorg_cn', title: '管辖区域', width: 80, align: 'right' },
            ]]
        });
    });
</script>

