<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小白专卖店</title>
</head>
<body>
<%@include file="../../common/setReferUrl.jsp" %>
<%@include file="../../common/adminTopNav.jsp" %>
<script type="text/javascript">
function selUsersBut(formId,cbName){
	if($("#selAllUsers").is(":checked"))
		selectAll(formId,cbName);
	else
		unSelectAll(formId,cbName);
}
function check(){
	if(!validateCheckbox("userForm","userIds","请至少选中一个用户"))
		return false;
	else
		return confirm("确实要删除选中的所有用户吗？");
}
</script>

    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
          <%@include file="../../common/adminLeftNav.jsp" %>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
         <div class="row">
        	<div class="col-md-10">
            <form class="form-inline" role="form" id="searchForm"  method="post" 
          action="${pageContext.request.contextPath}/admin/user/getAdminUsers">			
				
				<div class="form-group">
					<label for="userName"> 用户名</label> 
					<input class="form-control input-sm" name="userName" id="userName" value="${fn:escapeXml(param.userName)}" type="text" placeholder="用户名称" />
				</div>
				<div class="form-group">
					<label for="userSex"> 性别 </label> 
					<select class="form-control input-sm" name="userSex" id="userSex">
					  	<option value="" ${empty(param.userSex)?'selected':''}>---不限制---</option>
					  	<option value="0" ${!empty(param.userSex)&&param.userSex==0?'selected':''}>男</option>
					  	<option value="1" ${!empty(param.userSex)&&param.userSex==1?'selected':''}>女</option>
					</select>
				</div>	
				<button class="btn btn-primary btn-sm" type="submit" >搜索</button>
			</form>
			</div>
			<div class="col-md-2">
			<div class="dropdown">
			  <button class="btn btn-default dropdown-toggle btn-sm" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
			    	排序
			    <span class="caret"></span>
			  </button>
			  <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
			    
					<li>
						<a href="${pageContext.request.contextPath}/admin/user/getAdminUsers?userName=${fn:escapeXml(param.userName)}&userSex=${param.userSex }&sort=1">按用户ID从低到高</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/admin/user/getAdminUsers?userName=${fn:escapeXml(param.userName)}&userSex=${param.userSex }&sort=2">按用户ID从高到低</a>
					</li>								
					<li class="divider">
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/admin/user/getAdminUsers?userName=${fn:escapeXml(param.userName)}&userSex=${param.userSex }&sort=3">按用户名从低到高</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/admin/user/getAdminUsers?userName=${fn:escapeXml(param.userName)}&userSex=${param.userSex }&sort=4">按用户名从高到低</a>
					</li>
				
			  </ul>
		    </div>
		    </div>
		  </div>
		  
          
        
		  <h2 class="sub-header">用户信息列表</h2>
		  
          <c:if test="${!empty users.list }">
          <div class="table-responsive ">
          	<form id="userForm" method="post" action="${pageContext.request.contextPath}/admin/user/delUsersByIds" onsubmit="return check()">
          	<button class="btn btn-primary" type="submit">批量删除</button>		
            <table class="table table-striped">
              <thead>
                <tr>
                  <th><input type="checkbox" id="selAllUsers" onchange="selUsersBut('userForm', 'userIds')"/> 全选</th>
                  <th>#</th>
                  <th>用户名称</th>
                  <th>用户性别</th>
                  <th>用户邮箱</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
              	<c:forEach items="${users.list}" var="u" varStatus="vs">
                <tr>
                  <td><input type="checkbox" name="userIds" value="${u.userId }"/></td>
                  <td>${vs.count }</td>
                  <td>${fn:escapeXml(u.userName) }</td>
                  <td>${u.userSex==0?'男':'女' }</td>
                  <td>${fn:escapeXml(u.userEmail) }</td>
                  
                  <td>
                  	<a href="${pageContext.request.contextPath}/admin/user/handleUser?userId=${u.userId}">修改</a> |
                  	<a href="${pageContext.request.contextPath}/admin/user/delUser?userId=${u.userId}" onclick="return confirm('确定要删除这个用户吗？')">删除</a>   
				  </td>
                </tr>
               </c:forEach>
              </tbody>
            </table>
            </form>
            <%@include file="../../common/pageList.jsp" %>
          </div>
          </c:if>
          <c:if test="${empty users.list }">暂无用户信息</c:if>
        </div>
      </div>
    </div>

<%@include file="../../common/adminFooter.jsp" %>
</body>
</html>