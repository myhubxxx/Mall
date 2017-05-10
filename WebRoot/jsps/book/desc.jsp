<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>图书详细</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/pager/pager.css'/>" />
    <script type="text/javascript" src="<c:url value='/jsps/pager/pager.js'/>"></script>
	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/book/desc.css'/>">
	<script src="<c:url value='/jsps/js/book/desc.js'/>"></script>
  </head>
  
  <body>
  <div class="divBookName">${goodInfo.gname }</div>
  <div>
    <img align="top" src="<c:url value='/${goodInfo.image_w }'/>" class="img_image_w"/>
    <div class="divBookDesc">
	    <ul>
	    	<li>商品编号：${goodInfo.gid }</li>
	    	<li>折后价：<span class="price_n">&yen;${goodInfo.nowPrice }</span></li>
	    	<li>原价：<span class="spanPrice">&yen;${goodInfo.price }</span>　折扣：<span style="color: #c30;">${goodInfo.discount }</span>折</li>
	    </ul>
		<hr class="hr1"/>
		<table>
			<%-- <tr>
				<td colspan="3">
					作者：${goodInfo.author } 著
				</td>
			</tr>
			<tr>
				<td colspan="3">
					出版社：${goodInfo.press }
				</td>
			</tr> --%>
			<tr>
				<td colspan="3">生产时间：${goodInfo.producetime }</td>
			</tr>
			<tr>
				<td>商品描述：${goodInfo.desc_1 }</td>
				<td>商品描述：${goodInfo.desc_2 }</td>
				<td>商品描述：${goodInfo.desc_3 }</td>
				<td>商品描述：${goodInfo.desc_4 }</td>
			</tr>
			<tr>
				<td width="180">商品描述：${goodInfo.desc_5 }</td>
				<td>商品描述：${goodInfo.desc_6 }</td>
				<td>商品描述：${goodInfo.desc_7 }</td>
				<td>商品描述：${goodInfo.desc_8 }</td>
			</tr>
		</table>
		<div class="divForm">
			<form id="form1" action="<c:url value='/addShopCar.action'/>" method="post">
				<input type="hidden" name="method" value="add"/>
				<input type="hidden" name="goodsId" value="${goodInfo.gid }"/>
  				购买：<input id="cnt" style="width: 40px;text-align: center;" type="text" name="count" value="1"/>件
  			</form>
  			<a id="btn" href="javascript:$('#form1').submit();"></a>
  		</div>
	</div>
  </div>
  </body>
</html>
