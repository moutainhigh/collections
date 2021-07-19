<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>GIRD首页</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/style/redmond/jquery-ui-1.8.11.css" type="text/css" media="all" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/style/ui.grid/ui.guigrid.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery-1.5.1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery-ui-1.8.11.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery.guigrid.js"></script>
<script type="text/javascript">
    $(function() {
        $("#gridtest").guigrid({
            height : 200, //flexigrid插件的高度，单位为px
            width : 800,
            url : rootPath+'/userView.ajax?id=1', //ajax url,ajax方式对应的url地址page/grid/sample-data.json
            keyCol:"id",
            colModel : [{
                display : '编号',
                name : 'id',
                width : 60,
                sortable : true,
                align : 'left',
                hide : false
            }, {
                display : '姓名',
                name : 'name',
                width : 100,
                sortable : true,
                align : 'left'
            }],
            buttons : [{
                name : 'Add',
                displayname : "新增",
                bclass : 'Add',
                onpress : toolbarItem_onclick
            }, {
                name : 'Modify',
                displayname : "修改",
                bclass : 'Modify',
                onpress : toolbarItem_onclick
            }, {
                name : 'Delete',
                displayname : "删除",
                bclass : 'Delete',
                onpress : toolbarItem_onclick
            } ],
            sortname : "codetype",
            sortorder : "asc",
            usepager : true,
            useRp : true,
            rowbinddata : true,
            showcheckbox : true,
            showTableToggleBtn : true
        });
        function toolbarItem_onclick(cmd, grid) {
            if (cmd == "Add") {
                alert("cmd add is excuted");
            } else if (cmd == "Delete") {
                alert("cmd Delete is excuted");
            } else{
                //var o = $("#gridtest").guigrid("getCheckedRows");
                //alert(o);
                $("#gridtest").guigrid("option",{
                        colModel : [ {
                            display : '111代码类型',
                            name : 'codetype'
                        }, {
                            display : '222代码描述',
                            name : 'description'
                        }, {
                            display : '333状态',
                            name : 'status'
                        }]
                });
            }
        }
    })
    
    function query(){
        $("#gridtest").guigrid("reload", {
            "extParam":[
                { name: 'codetype', value: $("#codetype").val() },
                { name: 'description', value: $("#description").val() }
            ]
        });
        return false;
    }
</script>
</head>
<body>
<div>
  <form action="/" onSubmit="return query()">
    <table>
        <tr><td>代码类型：</td><td><input type="text" name="codetype" id="codetype" value="" /></td>
            <td>代码描述：</td><td><input type="text" name="description" id="description" value="" /></td></tr>
        <tr><td colspan="4" align="center"><input type="submit" value="查询" /></td></tr>
    </table>
  </form>
</div>
<div id="gridtest"></div>
</body>
</html>