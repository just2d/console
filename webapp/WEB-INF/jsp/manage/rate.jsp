<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link type="text/css" rel="stylesheet" href="/css/manage.css"/>
<form>
<div class="tit">
<span style="font-weight: bold;">${agent.name}</span>(id:${agent.id})--${agent.brand}(${agent.address})
--信用值(<span style="font-weight: bold;">${agent.points}</span>)
</div>

<div class="mag">
<div class="todo">
	<table>
	<thead>
	<tr>
	<c:if test="${fn:length(rateList)==0}"><th>该用户没有评价</th></c:if>
	</tr>
	</thead>
	<tbody>
		<c:forEach items="${rateList}" var="rate">
		<tr>
		<td align="left">${rate.customername}给${agent.name}的
	<c:if test="${rate.type=='0'}"><span style="font-weight: bold;color: #04789E">好评:<br />${rate.cmt}</span></c:if>
	<c:if test="${rate.type=='1'}"><span style="font-weight: bold;color: #FF5E00">中评:<br />${rate.cmt}</span></c:if>
	<c:if test="${rate.type=='2'}"><span style="font-weight: bold;color: #F91104">差评:<br />${rate.cmt}</span></c:if>
	<br />
	${rate.cts}<br />
	<c:if test="${rate.flags=='1'}"><span style="color: red">经审核，该评价为无效评价</span></c:if>
	<c:if test="${rate.type=='2'&&rate.flags=='0'}"><span style="color: gray">该评价还未经过审核……</span></c:if>
	</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
	</div>
	</div>
	<div class="page_and_btn">
		<jsp:include page="/WEB-INF/snippets/page.jsp" />
	</div>
	</form>
