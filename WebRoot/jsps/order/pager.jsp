<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
	function _go() {
		var pc = $("#pageCode").val();//获取文本框中的当前页码
		if(!/^[1-9]\d*$/.test(pc)) {//对当前页码进行整数校验
			alert('请输入正确的页码！');
			return;
		}
		if(pc > ${page.pageCount}) {//判断当前页码是否大于最大页
			alert('请输入正确的页码！');
			return;
		}
		location = "<c:url value='/listOrders.action?status=${status}&pageNow='/>" + pc;
	}
</script>


<div class="divBody">
  <div class="divContent">
    <%--上一页 --%>
<c:choose>
	<c:when test="${page.currentPage eq 1 }"><span class="spanBtnDisabled">上一页</span></c:when>
	<c:otherwise><a href="<c:url value='/listOrders.action?status=${status}&pageNow='/>${page.currentPage-1}" class="aBtn bold">上一页</a></c:otherwise>
</c:choose>
        
        

<%--我们需要计算页码列表的开始和结束位置，即两个变量begin和end
计算它们需要通过当前页码！
1. 总页数不足6页--> begin=1, end=最大页
2. 通过公式设置begin和end，begin=当前页-1，end=当前页+3
3. 如果begin<1，那么让begin=1，end=6
4. 如果end>tp, 让begin=tp-5, end=tp
 --%>
<%--  <c:choose>
 	<c:when test="${page.tp <= 6 }">
 		<c:set var="begin" value="1"/>
 		<c:set var="end" value="${page.tp }"/>
 	</c:when>
 	<c:otherwise>
 		<c:set var="begin" value="${page.pc-2 }"/>
 		<c:set var="end" value="${page.pc + 3}"/>
 		<c:if test="${begin < 1 }">
 		  <c:set var="begin" value="1"/>
 		  <c:set var="end" value="6"/>
 		</c:if>
 		<c:if test="${end > page.tp }">
 		  <c:set var="begin" value="${page.tp-5 }"/>
 		  <c:set var="end" value="${page.tp }"/>
 		</c:if> 		
 	</c:otherwise>
 </c:choose> --%>
 
 <c:forEach begin="${page.start }" end="${page.end }" var="i">
   <c:choose>
   	  <c:when test="${i eq page.currentPage }">
   	    <span class="spanBtnSelect">${i }</span>
   	  </c:when>
   	  <c:otherwise>
   	    <a href="<c:url value='/listOrders.action?status=${status}&pageNow='/>${i}" class="aBtn">${i }</a>
   	  </c:otherwise>
   </c:choose>
           
          	
 </c:forEach>
    <%-- 计算begin和end --%>
      <%-- 如果总页数<=6，那么显示所有页码，即begin=1 end=${page.tp} --%>
        <%-- 设置begin=当前页码-2，end=当前页码+3 --%>
          <%-- 如果begin<1，那么让begin=1 end=6 --%>
          <%-- 如果end>最大页，那么begin=最大页-5 end=最大页 --%>


    
    <%-- 显示点点点 --%>
    <c:if test="${end < page.pageCount }">
      <span class="spanApostrophe">...</span>
    </c:if> 

    
     <%--下一页 --%>
<c:choose>
	<c:when test="${page.currentPage eq page.pageCount }"><span class="spanBtnDisabled">下一页</span></c:when>
	<c:otherwise><a href="<c:url value='/listOrders.action?status=${status}&pageNow='/>${page.currentPage+1}" class="aBtn bold">下一页</a></c:otherwise>
</c:choose>
        
        
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    
    <%-- 共N页 到M页 --%>
    <span>共${page.pageCount }页</span>
    <span>到</span>
    <input type="text" class="inputPageCode" id="pageCode" value="${page.currentPage }"/>
    <span>页</span>
    <a href="javascript:_go();" class="aSubmit">确定</a>
  </div>
</div>