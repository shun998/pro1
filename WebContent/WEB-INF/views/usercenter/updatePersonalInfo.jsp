<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小白专卖店</title>

</head>
<body>
<%@include file="../common/userCenterTopNav.jsp" %>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-2">
			<%@include file="../common/userCenterLeftNav.jsp" %>
		</div>
		<div class="col-md-10">
			<form role="form"  method="post" action="${pageContext.request.contextPath}/user/updatePersonalInfo">
				<input type="hidden" name="userId" value="${user.userId }"/>
				<div class="form-group">
					<label for="userName"> 用户名 </label> 
					<input class="form-control" name="userName" id="userName" type="text" placeholder="用户名" value="${user.userName}" disabled/>					
				</div>
				<div class="form-group">
					<label for="userSex"> 性别 </label> 
					<div class="radio">
					  <label>
					    <input type="radio" name="userSex" id="sex1" value="0" ${user.userSex==0?'checked':'' }> 男
					  </label>
					  <label>
					    <input type="radio" name="userSex" id="sex2" value="1" ${user.userSex==1?'checked':'' }> 女
					  </label>
					</div>						
				</div>					
				<div class="form-group"> 
					<label for="userEmail">	邮箱</label>
					<input class="form-control" name="userEmail" id="userEmail" type="email" value="${user.userEmail}"/>
				</div>	
				<div class="form-group"> 
					<button class="btn btn-primary" type="submit" >确定</button>
				</div>				
			</form>
		</div>
	</div>
</div>
<%@include file="../common/userFooter.jsp" %>
</body>
</html>