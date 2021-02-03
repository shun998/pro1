<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小白专卖店</title>
</head>
<body>
<%@include file="../common/userTopNav.jsp" %>
<script type="text/javascript">
	$(document).ready(function(){
		$("#nav li").removeClass("active");
		$("#nav>li").eq(3).addClass("active");
	});	
</script>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<div class="row">
				
				<div class="col-md-2">		
					<%@include file="../common/userLeftNav.jsp" %>
				</div>
				
				<div class="col-md-10">
					<div class="row">
					<c:if test="${!empty goods }">
					<c:forEach items="${goods.list}" var="g">
						<div class="col-md-4">
							<div class="thumbnail">
								
								<a href="${pageContext.request.contextPath}/goods/getGoodsDetailById?goodsId=${g.goodsId}&cateId=${g.cateId}">
								<img alt="${fn:escapeXml(g.goodsName)}" src="${pageContext.request.contextPath}${fn:escapeXml(g.goodsPic)}" /></a>
								<div class="caption text-center">
									<label>
										${fn:escapeXml(g.goodsName)}
									</label>
									<p>
										原价<span class="glyphicon glyphicon-yen" aria-hidden="true"></span>${g.goodsPrice}
										<span class="glyphicon glyphicon-hand-right" aria-hidden="true"></span>
									</p>
									<p>
										现售<span class="label label-pill label-info"><span class="glyphicon glyphicon-yen" aria-hidden="true"></span>${g.goodsDiscount}</span>
									</p>
									<p>
										共售出${g.goodsSales}件
									</p>
									<p>
										<a class="btn btn-primary" href="${pageContext.request.contextPath}/goods/getGoodsDetailById?goodsId=${g.goodsId}" >查看详情</a>
										<!-- <a class="btn btn-primary" href="${pageContext.request.contextPath}/order/buyGoods?goodsId=${g.goodsId}" >立即购买</a> 
										<a class="btn" href="add()">加入购物车</a>  -->
									</p>
								</div>
							</div>
						</div>
					</c:forEach>
					</c:if>		
					</div>
					<%@include file="../common/pageList.jsp" %>
					<c:if test="${empty goods.list}">
						<div class="alert alert-danger col-md-2" role="alert">对不起，暂无此类商品</div>
					</c:if>			
				</div>
			</div>
		</div>
	</div>
</div>
<%@include file="../common/userFooter.jsp" %>
</body>
</html>