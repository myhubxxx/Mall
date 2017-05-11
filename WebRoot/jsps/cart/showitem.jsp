<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>showitem.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/cart/showitem.css'/>">
	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script src="<c:url value='/js/round.js'/>"></script>
<style type="text/css">
#addr{width: 500px; height: 32px;border: 1px solid #7f9db9; padding-left: 10px; line-height: 32px;}
</style>

<script type="text/javascript">
	//计算合计
	$(function() {
		var total = 0;
		$(".subtotal").each(function() {
			total += Number($(this).text());
		});
		$("#total").text(round(total, 2));
	});
</script>
  </head>
  
  <body>
  <c:choose>
  	<c:when test="${empty shopCarList }"></c:when>
  	<c:otherwise>
<form id="form1" action="<c:url value='/addOrder.action'/>" method="post">
	<input type="hidden" name="shopCarIds" value="${shopCarIds }"/>
	<input type="hidden" name="method" value="createOrder"/>
<table width="95%" align="center" cellpadding="0" cellspacing="0">
	<tr bgcolor="#efeae5">
		<td width="400px" colspan="5"><span style="font-weight: 900;">生成订单</span></td>
	</tr>
	<tr align="center">
		<td width="10%">&nbsp;</td>
		<td width="50%">商品名称</td>
		<td>单价</td>
		<td>数量</td>
		<td>小计</td>
	</tr>


<c:forEach items="${shopCarList }" var="cartItem">
	<tr align="center">
		<td align="right">
			<a class="linkImage" href="<c:url value='/goodsInfo.action?good.gid=${cartItem.goods.gid}'/>"><img border="0" width="54" align="top" src="<c:url value='/${cartItem.goods.image_b }'/>"/></a>
		</td>
		<td align="left">
			<a href="<c:url value='/goodsInfo.action?good.gid=${cartItem.goods.gid}'/>"><span>${cartItem.goods.gname }</span></a>
		</td>
		<td>&yen;${cartItem.goods.nowPrice }</td>
		<td>${cartItem.number }</td>
		<td>
			<span class="price_n">&yen;<span class="subtotal">${cartItem.total }</span></span>
		</td>
	</tr>
</c:forEach>
	
	
	







	<tr>
		<td colspan="6" align="right">
			<span>总计：</span><span class="price_t">&yen;<span id="total">${total }</span></span>
		</td>
	</tr>
	<tr>
		<td colspan="5" bgcolor="#efeae5"><span style="font-weight: 900">收货地址</span></td>
	</tr>
	<tr>
		<td colspan="6">
			<input id="addr" type="text" name="address" value="四川省成都市新都大道8号"/>
		</td>
	</tr>
	<tr>
		<td colspan="5" bgcolor="#efeae5"><span style="font-weight: 900">联系方式</span></td>
	</tr>
	<tr>
		<td colspan="5">
			<input id="phoneNumber" type="text" name="phoneNumber" style="width: 200" value="18428346868"/>
		</td>
	</tr>
	<tr>
		<td style="border-top-width: 4px;" colspan="5" align="right">
			<a id="linkSubmit" href="javascript:validate()">提交订单</a>
		</td>
	</tr>
</table>
</form>
  	</c:otherwise>
  </c:choose>
<script> 
  function validate() {
	var bool = true;
	
	var address = $("#addr").val();
	if(!address){
		alert("请填写收获地址");
		bool = false;
	}
	var phoneNumber = $("#phoneNumber").val();
	if(!phoneNumber){
		alert("请填写联系方式");
		bool = false;
	}else if(phoneNumber.length <7 || phoneNumber.length >11){
		alert("联系方式长度必读在7 ~ 11之间");
		bool = false;
	}
/*	$("#loginnameError").css("display", "none");
	var value = $("#loginname").val();
	if(!value) {// 非空校验
		$("#loginnameError").css("display", "");
		$("#loginnameError").text("用户名不能为空！");
		bool = false;
	} else if(value.length < 3 || value.length > 20) {//长度校验
		$("#loginnameError").css("display", "");
		$("#loginnameError").text("用户名长度必须在3 ~ 20之间！");
		bool = false;
	}
*/	
	if(bool){
		$("#form1").submit();
	}
	return bool;
};
</script>
  </body>
</html>
