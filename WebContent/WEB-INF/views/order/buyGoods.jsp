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
function handleAddressForm(addrId){
	if(addrId){
		$.post(getContextPath()+"/address/getAddressById",{addrId:addrId},function(result){
			if(checkLogin(result)){
				if(result.addr){
					$("#addressAjaxFormModal").modal();
					$("#addrProvince").val(result.addr.addrProvince);
					$("#addrCity").val(result.addr.addrCity);
					$("#addrArea").val(result.addr.addrArea);
					$("#addrContent").val(result.addr.addrContent);
					$("#addrReceiver").val(result.addr.addrReceiver);
					$("#addrTel").val(result.addr.addrTel);
					$("#addrId").val(result.addr.addrId);	
					$("#addrIsdefault").val(result.addr.addrIsdefault);	
				}
				else{
					$("#msgTitle").html("操作失败");
					$("#msgBody").html("未读到当前地址信息");
					$("#msgModal").modal();
				}
			}
		});
	}
	else{
		$("#addrProvince").val("");
		$("#addrCity").val("");
		$("#addrArea").val("");
		$("#addrContent").val("");
		$("#addrReceiver").val("");
		$("#addrTel").val("");
		$("#addrIsdefault").val("0");
		$("#addrId").val("0");	
		$("#addressAjaxFormModal").modal();
	}
	
}
function handleAddress(){
	$("#addressAjaxFormModal").modal("hide");
	
	$.post(getContextPath()+"/address/handleAddressAjax",$("#addrForm").serialize(),
	function(result){
		if(checkLogin(result)){
			if(result){
				var addrId=$("#addrId").val();
				if(addrId==0){
					
					$("#addressTableBody").append(result);
				}
				else{
					var trs=$("#addressTableBody tr");
					for(var i=0;i<trs.length;i++){
						if(addrId==$("#addressTableBody tr").eq(i).attr("id")){
							$("#addressTableBody tr").eq(i).replaceWith(result);
						}
					}
				}
				$("#submitOrder").removeAttr("disabled");
			}
			else{
				$("#msgTitle").html("操作失败");
				$("#msgBody").html("操作当前地址信息失败");
				$("#msgModal").modal();
			}
		}
	});
}
function delAddress(addrId){
	$.post(getContextPath()+"/address/delAddressAjax",{addrId:addrId},function(result){
		if(checkLogin(result)){
			if(result.del=="success"){
				var trs=$("#addressTableBody tr");
				for(var i=0;i<trs.length;i++){
					if(addrId==$("#addressTableBody tr").eq(i).attr("id")){
						$("#addressTableBody tr").eq(i).remove();
					}
				}
			}
			else{
				$("#msgTitle").html("操作失败");
				$("#msgBody").html("删除当前地址信息失败");
				$("#msgModal").modal();
			}
		}
	});
}
function setDefault(addrId,e){
	$.post(getContextPath()+"/address/setDefaultAddress",{addrId:addrId},function(result){
		if(checkLogin(result)){
			if(result.setDefault=="success"){
				$(".isDefault").html("");
				$(e.target).parent().siblings("[class='isDefault']").html("默认地址");
				
				$("input[name='address']").removeAttr("checked");
				$(e.target).parent().siblings("[class='radiocheck']").find("input[name='address']").prop("checked",true);
			}
			else{
				$("#msgTitle").html("操作失败");
				$("#msgBody").html("设置默认收货地址失败");
				$("#msgModal").modal();
			}
		}	
	});
}

</script>
<div class="container-fluid">
	<div class="row">
	<form action="${pageContext.request.contextPath}/order/addOrder" id="addressForm" method="post" onsubmit="return validateRadio('addressForm','address','请选择收货地址');">
		<div class="col-md-10 col-md-offset-1" id="myAddress">
		<c:if test="${!empty addrs}">
		<h3>确认收货地址</h3>
		<table class="table table-condensed table-hover">
			<tbody id="addressTableBody">				
				<c:forEach items="${addrs}" var="a" varStatus="vs">
				<tr id="${a.addrId}">
					<td class="radiocheck">
						<div class="radio">
						  <label>
						    <input type="radio" name="address" value="${fn:escapeXml(a.addrProvince)} ${fn:escapeXml(a.addrCity)} ${fn:escapeXml(a.addrArea)} ${fn:escapeXml(a.addrContent)} (${fn:escapeXml(a.addrReceiver)}收) ${fn:escapeXml(a.addrTel)}" ${a.addrIsdefault==1?'checked=checked':'' }>
						   
						  </label>
						</div>						
					</td>
					<td>${fn:escapeXml(a.addrProvince)} ${fn:escapeXml(a.addrCity)} ${fn:escapeXml(a.addrArea)}</td><td>${fn:escapeXml(a.addrContent)}</td>
					<td>(${fn:escapeXml(a.addrReceiver)}收) ${fn:escapeXml(a.addrTel)} </td>
					<td class="isDefault">${a.addrIsdefault==1?'默认地址':'' }</td>
					<td>
					 	<a href="#" onclick="handleAddressForm(${a.addrId})">修改</a> | 
					 	<a href="#" onclick="if(confirm('确定要删除这个收货地址吗？')) delAddress(${a.addrId});">删除</a>	| 
					 	<a href="#" onclick="setDefault(${a.addrId},event)">设为默认地址</a>				
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<button class="btn btn-primary" type="button" onclick="handleAddressForm()">添加收货地址</button>
		</c:if>
		
		<c:if test="${empty addrs}">
			<h3>您目前没有收货地址，请先 <button class="btn btn-primary" type="button" onclick="handleAddressForm()">添加收货地址</button></h3>
			可以到个人中心<a href="${pageContext.request.contextPath}/address/getMyAddress">管理您的收货地址</a>
		</c:if>
		
		</div>
		<div class="col-md-10 col-md-offset-1">
			<h3>确认订单信息</h3>
			
			<table class="table table-hover table-condensed">
				<thead>
					<tr>
						<th>#</th><th>商品图片</th><th>商品名称</th><th>商品单价</th><th>数量</th><th>小计</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="totalAmount" value="0"/>
					<c:set var="postalfee" value="${goodsList[0].goodsPostalfee}"/>
					<c:forEach items="${goodsList}" var="g" varStatus="vs">

					<tr ${vs.count%2==0?'class=\"info\"':''}>
						<td>
							${vs.count}
						</td>
						<td>
							<img src="${pageContext.request.contextPath}${g.goodsPic}" width="30" height="30">
						</td>
						<td>
							${fn:escapeXml(g.goodsName)}&nbsp;&nbsp;
							${fn:escapeXml(g.size)}&nbsp;&nbsp;${fn:escapeXml(g.color)}
						</td>
						<td>
							原价<span class="glyphicon glyphicon-yen" aria-hidden="true"></span>${g.goodsPrice}&nbsp;&nbsp;
							现售<span class="glyphicon glyphicon-yen" aria-hidden="true"></span>${g.goodsDiscount}
						</td>
						<td>
							${g.num}
						</td>
						<td>
							<span class="glyphicon glyphicon-yen" aria-hidden="true"></span>
							${g.num*g.goodsDiscount}
						</td>
						<c:set var="totalAmount" value="${totalAmount+g.num*g.goodsDiscount}"/>
						<c:set var="postalfee" value="${postalfee>g.goodsPostalfee?g.goodsPostalfee:postalfee}"/>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			<table class="table table-condensed">
				<tr><td class="text-right">运费：<span class="glyphicon glyphicon-yen" aria-hidden="true"></span>${postalfee}</td></tr>
				<tr><td class="text-right">合计（含运费）：<span class="glyphicon glyphicon-yen" aria-hidden="true"></span>${totalAmount+postalfee}</td></tr>
			</table>
			<div ></div>
			<div class="col-md-12 text-right"><button id="submitOrder" class="btn btn-primary" type="submit" ${empty addrs?"disabled='disabled'":"" }>提交订单</button></div>				 
		</div>
		<input type="hidden" name="orderPostalfee" value="${postalfee}"/>
	</form>	
	</div>
</div>
<%@include file="../common/addressAjaxFormModal.jsp" %>
<%@include file="../common/userFooter.jsp" %>
</body>
</html>