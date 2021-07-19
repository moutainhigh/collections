<!DOCTYPE html>
<%@ page contentType="text/html; charset=GBK"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/page/share/service/subgrid/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/page/share/service/subgrid/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/page/share/service/subgrid/demo.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/page/share/service/subgrid/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/page/share/service/subgrid/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/page/share/service/subgrid/datagrid-detailview.js"></script>
    <script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/page/share/service/subgrid/subgrid_data.js"></script>
    
</head>
<body>
   <table id="dg" style="width:750px;height:400px"
            title="接口信息"
            singleSelect="true" fitColumns="true">
        <thead>
            <tr>
                <th field="INTERFACE_ID" hidden="true">接口ID</th>
                <th field="INTERFACE_NAME" width="230">接口名称</th>
                <th field="TABLE_ID" align="left" hidden="true">表ID</th>
                <th field="YHXM" align="center" width="70">创建者</th>
                <th field="LAST_MODIFY_TIME" align="center" width="200">最后修改时间</th>
            </tr>
        </thead>
    </table>

    <script type="text/javascript">
    	/* //父表数据
    	var grid_data=[{'interface_id':'eef258fe9e2f412a9eb5ed2c1be93c9b', 'interface_name':'股权质押信息','table_id':'QYDJT011,QYDJT001','interface_state':'启用','last_modify_time':'2014-01-03 11:43:39'},
    					{'interface_id':'918092358c8c4cebbff0b8a084de9e7c', 'interface_name':'外商(机构)投资企业投资','table_id':'QYDJT006,QYDJT001','interface_state':'启用','last_modify_time':'2014-01-03 11:43:39'},
    					{'interface_id':'918092358c8c4cebbff0b8a084de9e7c', 'interface_name':'外商(机构)投资企业投资','table_id':'QYDJT006,QYDJT001','interface_state':'启用','last_modify_time':'2014-01-03 11:43:39'},
    					{'interface_id':'918092358c8c4cebbff0b8a084de9e7c', 'interface_name':'外商(机构)投资企业投资','table_id':'QYDJT006,QYDJT001','interface_state':'启用','last_modify_time':'2014-01-03 11:43:39'},
    					{'interface_id':'918092358c8c4cebbff0b8a084de9e7c', 'interface_name':'外商(机构)投资企业投资','table_id':'QYDJT006,QYDJT001','interface_state':'启用','last_modify_time':'2014-01-03 11:43:39'},
    					{'interface_id':'918092358c8c4cebbff0b8a084de9e7c', 'interface_name':'外商(机构)投资企业投资','table_id':'QYDJT006,QYDJT001','interface_state':'启用','last_modify_time':'2014-01-03 11:43:39'},
    					{'interface_id':'918092358c8c4cebbff0b8a084de9e7c', 'interface_name':'外商(机构)投资企业投资','table_id':'QYDJT006,QYDJT001','interface_state':'启用','last_modify_time':'2014-01-03 11:43:39'},
    					{'interface_id':'918092358c8c4cebbff0b8a084de9e7c', 'interface_name':'外商(机构)投资企业投资','table_id':'QYDJT006,QYDJT001','interface_state':'启用','last_modify_time':'2014-01-03 11:43:39'},
    					{'interface_id':'918092358c8c4cebbff0b8a084de9e7c', 'interface_name':'外商(机构)投资企业投资','table_id':'QYDJT006,QYDJT001','interface_state':'启用','last_modify_time':'2014-01-03 11:43:39'},
    					{'interface_id':'918092358c8c4cebbff0b8a084de9e7c', 'interface_name':'外商(机构)投资企业投资','table_id':'QYDJT006,QYDJT001','interface_state':'启用','last_modify_time':'2014-01-03 11:43:39'},
    					{'interface_id':'918092358c8c4cebbff0b8a084de9e7c', 'interface_name':'外商(机构)投资企业投资','table_id':'QYDJT006,QYDJT001','interface_state':'启用','last_modify_time':'2014-01-03 11:43:39'},
    					{'interface_id':'918092358c8c4cebbff0b8a084de9e7c', 'interface_name':'外商(机构)投资企业投资','table_id':'QYDJT006,QYDJT001','interface_state':'启用','last_modify_time':'2014-01-03 11:43:39'}];
    	 */
    	 
    
    	$(function(){
            $('#dg').datagrid({
                view: detailview,
                 //data:grid_data,
                 url:'/page/share/service/subgrid/grid_data.json',
                 loadMsg:'数据加载中，请稍候……',
                 rownumbers:true,
                 pagination:false,//分页控件
                detailFormatter:function(index,row){
                    return '<div style="padding:2px"><table class="ddv"></table></div>';
                },
                onExpandRow: function(index,row){
                    var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');
                    ddv.datagrid({
                        //url:'datagrid_getdetail.jsp?interface_id='+row.interface_id,
                        fitColumns:true,
                        singleSelect:true,
                        rownumbers:true,
                        loadMsg:'',
                        height:'auto',
                        columns:[[
                            {field:'TABLE_NAME_CN',title:'表中文名称',width:150},                                                      
                            {field:'TOPICS_NAME',title:'业务主题名称',width:100},
                            {field:'SERVICE_TARGETS_NAME',title:'所属系统',width:150}
                            
                        ]],
                        data: subgrid_data[0][row.INTERFACE_ID],
                        onResize:function(){
                            $('#dg').datagrid('fixDetailRowHeight',index);
                        },
                        
                        
                        onLoadSuccess:function(){
                            setTimeout(function(){
                                $('#dg').datagrid('fixDetailRowHeight',index);
                            },0);
                        }
                    });
                    $('#dg').datagrid('fixDetailRowHeight',index);
                }
            });
          /* //设置分页控件  
            var p = $('#dg').datagrid('getPager');  
            $(p).pagination({  
                pageSize: 10,//每页显示的记录条数，默认为10  
                pageList: [5,10,15],//可以设置每页记录条数的列表  
                beforePageText: '第',//页数文本框前显示的汉字  
                afterPageText: '页    共 {pages} 页',  
                displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',  
                 onBeforeRefresh:function(){ 
                    $(this).pagination('loading'); 
                    alert('before refresh'); 
                    $(this).pagination('loaded'); 
                }  
            });   */
        });
    </script>
    
    
</body>
</html>