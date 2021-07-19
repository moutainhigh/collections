/**
 * <h1>通过标注方式，做输入参数验证。</h1>
 * 
 * <p>
 * 支持：
 * 	<ol>
 * 		<li>单个字段验证。</li>
 * 		<li>多个字段联合验证。</li>
 * 		<li>基于表达式的验证。</li>
 * 		<li>基于数据库查询的验证。</li>
 * 	</ol>
 * </p>
 * 
 * <p>
 * 使用方法：
 * 	<table>
 * 		<tr>
 * 			<th>验证功能</th>
 * 			<th>标注</th>
 * 			<th>范例</th>
 * 		</tr>
 * 		<tr>
 * 			<td>非空</td>
 * 			<td>@NotNull</td>
 * 			<td>
 * 				public void reg(@NotNull(label="用户名")String userName)
 * 			</td>
 * 		</tr>
 * 	</table>
 * </p>
 * 配置信息结构：
 * 	
 * 
 * TODO chaiyongbing 实现。
 * 
 * @author liuhailong
 */
package com.gwssi.rodimus.validate;