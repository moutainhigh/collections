<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- style="height: 450px;overflow: hidden;" -->
<table class="table">
	<tbody>
		<tr>
			<td>序号</td>
			<td>代码</td>
			<td>股票名称</td>
			<td>当前价</td>
			<td>昨收</td>
			<td>今日最低</td>
			<td>今日最高</td>
			<td>介入价格</td>
			<td>预设提醒幅度</td>
			<td>是否控制台显示</td>
			<td>是否最佳榜单</td>
			<td>是否最近关注</td>
			<td>是否待关注</td>
			<td>是否是龙虎榜</td>
			<td>介入仓位</td>
			<td>所属市场</td>
			<td>操作</td>
		</tr>
		<c:forEach var="item" items="${list}" varStatus="vs">
			<%-- <tr class="${vs.index%2==0?"":"success" }"  onclick="editStock(this)" data-scode="${item.stockCode}"> --%>
			<tr onclick="editStock(this)" data-scode="${item.stockCode}">
				<td>${vs.index+1}</td>
				<td class="hightLights">${item.stockCode}</td>
				<td nowrap>${item.stockName}</td>
				<td>
				<c:choose>
						<c:when test="${item.currentPrice>item.prevClose}">
							<span style='color:red'>${item.currentPrice}
							</span>
						</c:when>
						<c:otherwise>
							<span style='color:green'>${item.currentPrice}</span>
						</c:otherwise>
				</c:choose>
				</td>
				<td>昨收：${item.prevClose}</td>
				<td>今日最低：${item.todayMinPrice}</td>
				<td>今日最高：${item.todayMaxPrice}</td>
				<td>介入：${item.pointerPrice}</td>
				<td>${item.pointerRates}%</td>
				<td><c:choose>
						<c:when test="${item.isconsole>0}">
							<span class="u-icon-toggle on" data-add="是否移出最佳榜单？" data-min="移入最佳榜单？" data-type="isconsole"> <i></i>
							</span>
						</c:when>
						<c:otherwise>
							<span class="u-icon-toggle" data-add="是否移出最佳榜单？" data-min="移入最佳榜单？" data-type="isconsole"> <i></i>
							</span>
						</c:otherwise>
					</c:choose></td>
				<td><c:choose>
						<c:when test="${item.mostImp>0}">
							<span class="u-icon-toggle on" data-add="是否移出最佳榜单？" data-min="移入最佳榜单？" data-type="mostImp"> <i></i>
							</span>
						</c:when>
						<c:otherwise>
							<span class="u-icon-toggle" data-add="是否移出最佳榜单？" data-min="移入最佳榜单？" data-type="mostImp"> <i></i>
							</span>
						</c:otherwise>
					</c:choose></td>
				<td><c:choose>
						<c:when test="${item.isRecentImport>0}">
							<span class="u-icon-toggle on" data-add="是否移出最近关注？" data-min="是否移入最近关注？" data-type="isRecentImport"> <i></i>
							</span>
						</c:when>
						<c:otherwise>
							<span class="u-icon-toggle" data-add="是否移出最近关注？" data-min="是否移入最近关注？" data-type="isRecentImport"> <i></i>
							</span>
						</c:otherwise>
					</c:choose></td>
				<td><c:choose>
						<c:when test="${item.waitImp>0}">
							<span class="u-icon-toggle on" data-add="是否移出待关注" data-min="是否移入待关注？ " data-type="waitImp"> <i></i>
							</span>
						</c:when>
						<c:otherwise>
							<span class="u-icon-toggle" data-add="是否移出待关注？" data-min="是否移入待关注？" data-type="waitImp"> <i></i>
							</span>
						</c:otherwise>
					</c:choose></td>
				<td><c:choose>
						<c:when test="${item.longhuBang>0}">
							<span class="u-icon-toggle on" data-add="是否移出龙虎榜？" data-min="是否移入龙虎榜？" data-type="longhuBang"> <i></i>
							</span>
						</c:when>
						<c:otherwise>
							<span class="u-icon-toggle" data-add="是否移出龙虎榜？" data-min="是否移入龙虎榜？" data-type="longhuBang"> <i></i>
							</span>
						</c:otherwise>
					</c:choose></td>

				<td>${item.positions}%</td>
				<td>${item.stockCode.indexOf("6")>0?"上海":"深圳"}</td>
				<td><a href="javascript:;"><img src="statics/images/edit.png" width="20" height="20"/></a><a href="javascript:;"><img src="statics/images/del.png" width="20" height="20"/></a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<script>


$(".u-icon-toggle").each(function() {
	var _this = $(this);
	_this.on("click",function() {
		var _that = $(this);
		var isOn = _that.hasClass("on");
		var type = _that.data("type");
		var stockCode = _that.parents("tr").data("scode");
		if (isOn == true) {
			var title = _that.data("add");
			_that.toggleClass("on");
			updateInfo(stockCode, type, 0)
		} else {
			var title = _that.data("min");
			_that.toggleClass("on");
			updateInfo(stockCode, type, 1)
		}
	});
})
</script>