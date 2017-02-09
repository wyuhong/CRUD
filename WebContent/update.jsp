<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <!-- 导包 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<form action="UserAction" method="post">
	<input type="hidden" name="flag" value="doUpdate"/>
	<input type="hidden" name="id" value="${ui.id}"/>
	登录名：<input type="text" name="userName" value="${ui.userName}"/></br>
	密码：<input type="password" name="userPwd" value="${ui.userPwd}"/></br>
	<input type="submit"  value="更新"/>
	
	</form>

</body>
</html>