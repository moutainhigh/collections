<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../../include/error_for_dialog.jsp"%>

<table cellspacing=0 border="0" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll();" width="50" class="head_td selAll">全选</td>
		<td class="head_td">编辑</td>
		<td class="head_td">字段名称</td>
		<td class="head_td">显示名称</td>
		<td class="head_td">类型</td>
		<td class="head_td">概览显示</td>
		<td class="head_td">库中类型</td>
		<td class="head_td_last">删除</td>
	</tr>
	<tbody class="grid_body" id="grid_body">
		<tr>
			<td onclick="wcm.Grid.selectAll();" width="50" class="body_td"><input type="checkbox" name="" id="" value="" /> 1</td>
			<td class="body_td"><span class="edit">&nbsp;</span></td>
			<td class="body_td"><span>userName</span></td>
			<td class="body_td"><span>用户名称</span></td>
			<td class="body_td"><span>普通文本</span></td>
			<td class="body_td"><span>是</span></td>
			<td class="body_td"><span>字符串</span></td>
			<td class="body_td"><span class="delete">&nbsp;</span></td>
		</tr>
		<tr>
			<td onclick="wcm.Grid.selectAll();" width="50" class="body_td grid_row_odd"><input type="checkbox" name="" id="" value="" /> 2</td>
			<td class="body_td grid_row_odd"><span class="edit">&nbsp;</span></td>
			<td class="body_td grid_row_odd"><span>userName</span></td>
			<td class="body_td grid_row_odd"><span>用户名称</span></td>
			<td class="body_td grid_row_odd"><span>普通文本</span></td>
			<td class="body_td grid_row_odd"><span>是</span></td>
			<td class="body_td grid_row_odd"><span>字符串</span></td>
			<td class="body_td grid_row_odd"><span class="delete">&nbsp;</span></td>
		</tr>
	</tbody>
	
</table>