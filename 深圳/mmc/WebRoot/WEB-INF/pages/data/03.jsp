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
            url: 'grid03',
            width: $(window).width() - 10,
            methord: 'post',
            height: $(window).height() -35,
            fitColumns: true,
            sortName: 'id',
            sortOrder: 'desc',
            idField: 'pripid',
            striped: true, //奇偶行是否区分
            singleSelect: false,//单选模式
            rownumbers: true,//行号
            pagination : true,//在DataGrid控件底部显示分页工具栏。
            pageSize: 20,//每页显示的记录条数，默认为10  
            pageList: [10,20,30],//选择一页显示多少数据             
            columns: [[
                { field: 'id', title: '序号', width: 80,checkbox:true },
                { field: 'pripid', title: '主体身份代码CA14', width: 80 },
                { field: 'entname', title: '名称', width: 120 },
                { field: 'regno', title: '注册号', width: 80, align: 'right' },
                { field: 'oldenttype', title: '市场主体类型【金信】', width: 80, align: 'right' },
            ]],
            toolbar : [ {
                text : '删除选中',
                iconCls : 'icon-add',
                handler : function() {
                	delItem();
                }
            } ],
             onLoadSuccess:function(){
                $('#List').datagrid('selectAll');
            } 
        });
        
        
        
        
        function delItem(){
        	var ids = [];
        	var rows = $('#List').datagrid('getSelections');
        	 var num = rows.length;
             if (num == 0) {
                 $.messager.alert('提示', '请选择一条记录进行操作!', 'info');
                 return;
             }
        	for(var i=0; i<rows.length; i++){
        		ids.push(rows[i].pripid);
        	}
        	$.ajax({
        		url:"del03",
        		type:'get',
        		data:{list:ids},
        		success:function(data){
        			alert(data)
        		},
        		error:function(data){
        			alert(data)
        		}
        		
        	});
        }
    });
</script>