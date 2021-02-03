<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小白专卖店</title>
<script type="text/javascript">
function check(){
	if(!validatePass("userForm","userPass","userPass1","两次密码输入不一致"))
		return false;
}
</script>
</head>
<body>
<%@include file="../../common/adminTopNav.jsp" %>

    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
          <%@include file="../../common/adminLeftNav.jsp" %>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		  

          <h2 class="sub-header">${empty user.userId?"添加":"修改" }用户</h2>
          <div class="col-sm-4 col-sm-offset-3 col-md-5 col-md-offset-2">
            
         	<form role="form" id="userForm" method="post" action="${pageContext.request.contextPath}/admin/user/doHandleUser" onsubmit="return check()">
				<input type="hidden" name="userId" value="${empty user.userId?0:user.userId }"/>
				<div class="form-group">
					<label for="userName"> 用户名 </label> 
					<input class="form-control" name="userName" id="userName" type="text" placeholder="用户名" value="${user.userName}" ${empty user.userId?"required":"disabled" }/>					
				</div>
				<div class="form-group">
						<label for="userPass"> 密码 </label> 
						<input class="form-control" name="userPass" id="userPass" type="password" value="${user.userPass}" required/>
					</div>
					<div class="form-group">
						<label for="userPass1"> 密码确认 </label> 
						<input class="form-control" id="userPass1" type="password" value="${user.userPass}" required/>
					</div>	
				<div class="form-group">
					<label for="userSex"> 性别 </label> 
					<div class="radio">
					  <label>
					    <input type="radio" name="userSex" id="sex1" value="0" ${empty(user.userSex)||user.userSex==0?'checked':'' }> 男
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
    </div>

<%@include file="../../common/adminFooter.jsp" %>
</body>
</html>