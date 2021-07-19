<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/bootstrap.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/multiple-select.css" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>

<script src="<%=request.getContextPath()%>/static/script/multiple-select.js"></script>
<title>Insert title here</title>
<script type="text/javascript">

    $(function() {
        $('#ms').change(function() {
        }).multipleSelect({
        	minimumCountSelected:10,//多少个以后采用占位符
        	isOpen:false,//类型: boolean是否打开下拉列表。默认值为 false。
            width: '20%',//类型: integer定义下拉列表的宽度。默认值为 undefined。 默认与 select 的宽度相同。
            placeholder: "请选择",//类型: string 显示默认的提示信息。默认值为 ''。
            selectAll: true,//类型: boolean 是否显示全选复选框。默认值为 true。
            selectAllText:'全部',//类型: string 全选复选框的显示内容。默认值为 Select all。
            filter: true,//类型: boolean是否开启过滤功能。默认值为 false。
            multiple: false,//类型: boolean 是否在一行中显示多个选项。默认值为 false。
            //multipleWidth://类型: integer 一行中每个选项的宽度。默认值为 80。
            single:false,//类型: boolean是否只允许你选择一行。默认值为 false。
            position:'bottom',//类型: string定义下拉列表的位置，只能是 bottom 或者 top。默认值为 bottom.
            maxHeight:250,//类型: integer下拉列表的最大高度。默认值为 250。
            onOpen:function() {
            	//alert('d');
            },
            onClose:function() {
            	//alert('d');
            },
            onCheckAll:function() {
            	//alert('d');
            },
            onUncheckAll:function() {
            	//alert('d');
            },
            onOptgroupClick:function(view) {
            	//alert('d');
            },
            onClick:function() {
            	//alert('d');
            }
        });
        $("select").multipleSelect("setSelects", [13, 14]);//设置选择的值
        
        alert('Selected values: ' + $('select').multipleSelect('getSelects'));
        alert('Selected texts: ' + $('select').multipleSelect('getSelects', 'text'));
        /* getSelects获取 Multiple Select 选择内容。参数: type类型: string选择内容的类型，value 或者 text。默认值为 value。
        alert('Selected values: ' + $('select').multipleSelect('getSelects'));
        alert('Selected texts: ' + $('select').multipleSelect('getSelects', 'text'));
        setSelects设置 Multiple Select 的内容。参数: values类型: array选择框的内容信息。
        $('select').multipleSelect('setSelects', [1, 3]);
        enable启用 Multiple Select。
        $('select').multipleSelect('enable');
        disable禁用 Multiple Select。在这种模式下，不允许用户操作。
        $('select').multipleSelect('disable');
        checkAll全选所有的复选框。
        $('select').multipleSelect('checkAll');
        uncheckAll全不选所有的复选框。
        $('select').multipleSelect('uncheckAll');
        refresh重新加载 Multiple Select。假如你是通过 AJAX 或者 DOM 来手动添加或者删除 option 选项，可以通过 refresh 方法来重新加载 Multiple Select。 */
        //alert("Selected values: " + $("select").multipleSelect("getSelects"));//获取设置选择的值
        //alert("Selected texts: " + $("select").multipleSelect("getSelects", "text"));
        //$("select").multipleSelect("disable");设置是否显示
        //$("select").multipleSelect("enable");
        //$("select").multipleSelect("checkAll");设置是否全选
        //$("select").multipleSelect("uncheckAll");设置是否全选
        //增加值
        var $select = $("select"),
        //$input = $("#refreshInput"),
        //$selected = $("#refreshSelected"),
        //$disabled = $("#refreshDisabled"),
        value = $.trim($input.val()),
        $opt = $("<option />", {
            value: value,
            text: value
        });
	    /* if (!value) {
	        $input.focus();
	        return;
	    } */
	    /* if ($selected.is(":checked")){
	        $opt.prop("selected", true);
	    }
	    if($disabled.is(":checked")){
	        $opt.attr("disabled", true);
	    } */
	    //$input.val("");
	    $select.append($opt).multipleSelect("refresh");
        
    });
</script>
</head>
<body>

<div class="container">
    <div class="form-group">
        <label>Email address</label>
        <input type="email" class="form-control" placeholder="Enter email">
    </div>

    <div class="form-group">
        <label>Month</label>
        <select id="ms" multiple="multiple">
            <option value="1" >January</option>
            <option value="2">February</option>
            <option value="3">March</option>
            <option value="4">April</option>
            <option value="5">May</option>
            <option value="6">June</option>
            <option value="7">July</option>
            <option value="8">August</option>
            <option value="9">September</option>
            <option value="10">October</option>
            <option value="11">November</option>
            <option value="12">December</option>
        </select>
    </div>
</div>
</body>
</html>