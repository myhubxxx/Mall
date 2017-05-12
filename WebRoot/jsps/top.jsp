<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>top</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
	body {
		background: #15B69A;
		margin: 0px;
		color: #ffffff;
	}
	a {
		text-align:right;
		text-decoration:none;
		color: #ffffff;
		font-weight: 900;
	} 
	a:hover {
		text-decoration: underline;
		color: #ffffff;
		font-weight: 900;
	}
</style>
  </head>
  
  <body>
  
  <div style="font-size: 10pt; line-height: 15px; text-align:right">

<%-- 根据用户是否登录，显示不同的链接 --%>
<c:choose>
	<c:when test="${empty sessionScope.sessionUser }">
	<right>
		  <a href="<c:url value='/jsps/user/login.jsp'/>" target="_parent">${sessionUser.username}用户登录</a> |&nbsp; 
		  <a href="<c:url value='/jsps/user/regist.jsp'/>" target="_parent">用户注册</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
	</right>
	</c:when>
	<c:otherwise>
		
		      用户：${sessionScope.sessionUser.username }&nbsp;&nbsp;|&nbsp;&nbsp;
		  <a href="<c:url value='/getMyShopCar.action'/>" target="body">购物车</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		  <a href="<c:url value='/listOrders.action'/>" target="body">订单</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		  <a href="<c:url value='/jsps/user/pwd.jsp'/>" target="body">密码修改</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		  <a href="<c:url value='/exitLogin.action'/>" target="_parent">退出</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		  <!-- <a href="https://www.baidu.com" target="_top">联系我们</a>	 -->
		
	</c:otherwise>
</c:choose>
</div>
<h1 style="text-align: center;">欢迎进入网上购物商城</h1>

  </body>
</html>
