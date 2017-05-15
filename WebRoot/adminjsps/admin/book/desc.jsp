<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>book_desc.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/admin/css/book/desc.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/jquery/jquery.datepick.css'/>">
<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
<script type="text/javascript" src="<c:url value='/jquery/jquery.datepick.js'/>"></script>
<script type="text/javascript" src="<c:url value='/jquery/jquery.datepick-zh-CN.js'/>"></script>

<script type="text/javascript" src="<c:url value='/adminjsps/admin/js/book/desc.js'/>"></script>

<script type="text/javascript">

$(function() {
	$("#box").attr("checked", false);
	$("#formDiv").css("display", "none");
	$("#show").css("display", "");	
	
	// 操作和显示切换
	$("#box").click(function() {
		if($(this).attr("checked")) {
			$("#show").css("display", "none");
			$("#formDiv").css("display", "");
		} else {
			$("#formDiv").css("display", "none");
			$("#show").css("display", "");		
		}
	});
});

function loadChildren() {
	/*
	1. 获取pid
	2. 发出异步请求，功能之：
	  3. 得到一个数组
	  4. 获取cid元素(<select>)，把内部的<option>全部删除
	  5. 添加一个头（<option>请选择2级分类</option>）
	  6. 循环数组，把数组中每个对象转换成一个<option>添加到cid中
	*/
	// 1. 获取pid
	var pid = $("#pid").val();
	// 2. 发送异步请求
	$.ajax({
		async:true,
		cache:false,
		url:"/Mall/ajaxSecondCategory.action",
		data:{"form.fid":pid},
		type:"POST",
		dataType:"json",
		success:function(arr) {
			// 3. 得到cid，删除它的内容
			$("#cid").empty();//删除元素的子元素
			$("#cid").append($("<option>====请选择2级分类====</option>"));//4.添加头
			// 5. 循环遍历数组，把每个对象转换成<option>添加到cid中
			for(var i = 0; i < arr.length; i++) {
				var option = $("<option>").val(arr[i].cid).text(arr[i].name);
				$("#cid").append(option);
			}
		}
	});
}

/*
 * 点击编辑按钮时执行本函数
 */
function editForm() {
	$("#method").val("edit");
	$("#form").submit();
}
/*
 * 点击删除按钮时执行本函数
 */
 function deleteForm() {
	$("#method").val("delete");
	$("#form").submit();	
}

</script>
  </head>
  
  <body>
    <input type="checkbox" id="box"><label for="box">编辑或删除</label>
    <br/>
    <br/>
  <div id="show">
    <div class="sm">${goodInfo.gname }</div>
    <img align="top" src="<c:url value='/${goodInfo.image_w }'/>" class="tp"/>
    <div id="book" style="float:left;">
	    <ul>
	    	<li>商品编号：${goodInfo.gid }</li>
	    	<li>当前价：<span class="price_n">&yen;${goodInfo.nowPrice }</span></li>
	    	<li>定价：<span style="text-decoration:line-through;">&yen;${goodInfo.price }</span>　折扣：<span style="color: #c30;">${goodInfo.discount }</span>折</li>
	    </ul>
		<hr style="margin-left: 50px; height: 1px; color: #dcdcdc"/>
		<table class="tab">
			<tr>
				<td colspan="3">
					<%-- 作者：${goodInfo.author }著 --%>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<%-- 出版社：${goodInfo.press }</a> --%>
				</td>
			</tr>
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
				<td>商品描述：${goodInfo.desc_4 }</td>
				<td>商品描述：${goodInfo.desc_6 }</td>
				<td>商品描述：${goodInfo.desc_7 }</td>
				<td>商品描述：${goodInfo.desc_8 }</td>
			</tr>
		</table>
	</div>
  </div>
  
  
  <div id='formDiv'>
   <div class="sm">&nbsp;</div>
   <form action="<c:url value='/goodAdmin.action'/>" method="post" id="form">
    <input type="hidden" name="op" id="method"/>
   	<input type="hidden" name="good.gid" value="${goodInfo.gid }"/>
    <img align="top" src="<c:url value='/${goodInfo.image_w }'/>" class="tp"/>
    <div style="float:left;">
	    <ul>
	    	<li>商品编号：${goodInfo.gid }</li>
	    	<li>商品名称：　<input id="bname" type="text" name="good.gname" value="${goodInfo.gname }" style="width:500px;"/></li>
	    	<li>当前价：<input id="currPrice" type="text" name="good.nowPrice" value="${goodInfo.nowPrice }" style="width:50px;"/></li>
	    	<li>定价：　<input id="price" type="text" name="good.price" value="${goodInfo.price }" style="width:50px;"/>
	    	折扣：<input id="discount" type="text" name="good.discount" value="${goodInfo.discount }" style="width:30px;"/>折</li>
	    </ul>
		<hr style="margin-left: 50px; height: 1px; color: #dcdcdc"/>
		<table class="tab">
			<%-- <tr>
				<td colspan="3">
					作者：　　<input id="author" type="text" name="author" value="${goodInfo.author }" style="width:150px;"/>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					出版社：　<input id="press" type="text" name="press" value="${goodInfo.press }" style="width:200px;"/>
				</td>
			</tr>--%>
			<tr> 
				<td colspan="4">生产时间：<input id="publishtime" type="text" name="good.producetime" value="${goodInfo.producetime }" style="width:100px;"/></td>
			</tr>
			<tr>
				<td>描述：<input id="edition" type="text" name="good.desc_1" value="${goodInfo.desc_1 }" style="width:100px;"/></td>
				<td>描述：<input id="pageNum" type="text" name="good.desc_2" value="${goodInfo.desc_2}" style="width:100px;"/></td>
				<td>描述：<input id="wordNum" type="text" name="good.desc_3" value="${goodInfo.desc_3 }" style="width:100px;"/></td>
				<td>描述：<input id="wordNum" type="text" name="good.desc_4" value="${goodInfo.desc_4 }" style="width:100px;"/></td>
			</tr>
			<tr>
				<td>描述：<input id="printtime" type="text" name="good.desc_5" value="${goodInfo.desc_5 }" style="width:100px;"/></td>
				<td>描述：<input id="booksize" type="text" name="good.desc_6" value="${goodInfo.desc_6 }" style="width:100px;"/></td>
				<td>描述：<input id="paper" type="text" name="good.desc_7" value="${goodInfo.desc_7 }" style="width:100px;"/></td>
				<td>描述：<input id="paper" type="text" name="good.desc_8" value="${goodInfo.desc_8 }" style="width:100px;"/></td>
			</tr>
			<tr>
				<td>
					一级分类：<select name="form.fid" id="pid" onchange="loadChildren()">
						<option value="">==请选择1级分类==</option>
<c:forEach items="${categorys }" var="parent">
  <option value="${parent.cid }">${parent.name }</option>
</c:forEach>
					</select>
				</td>
				<td>
					二级分类：<select name="good.category.cid" id="cid">
						<option value="">==请选择2级分类==</option>
<c:forEach items="${children }" var="child">
  <option value="${child.cid }" <c:if test="${goodInfo.category.cid eq child.cid }">selected="selected"</c:if>>${child.name }</option>
</c:forEach>
					</select>
				</td>
				<td></td>
			</tr>
			<tr>
				<td colspan="2">
					<input onclick="editForm()" type="button" name="op" id="editBtn" class="btn" value="修　　改">
					<input onclick="deleteForm()" type="button" name="op" id="delBtn" class="btn" value="删　　除">
				</td>
				<td></td>
			</tr>
		</table>
	</div>
   </form>
  </div>

  </body>
</html>
