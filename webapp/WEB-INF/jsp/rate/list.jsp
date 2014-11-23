<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="/js/package/package.js"></script>
<script type="text/javascript" src="/js/common/jsdate.js"></script>

<div style="width: 100%;text-align: left;">当前位置：列表页转化率统计 &gt;  统计列表</div> 
<br/>
<form action="/rate/rateList" method="post" id="packageForm" style="margin-top:0px;margin-top:0px;">
	<div style="text-align:left;">
		<div>
			<label>所在城市:</label>
	                <select id="citys" name="cityId" style="width: 100px;margin-left: 10px;"  onkeydown="this.blur();this.focus();">
	               		<option value="-1" <c:if test="${cityId == -1}">selected="selected"</c:if>>选择城市</option>
	               		<option value="0" <c:if test="${cityId == 0}">selected="selected"</c:if>>全国</option>
	               		<option value="1" <c:if test="${cityId == 1}">selected="selected"</c:if>>北京</option>
	               		<option value="2" <c:if test="${cityId == 2}">selected="selected"</c:if>>上海</option>
	               		<option value="3" <c:if test="${cityId == 3}">selected="selected"</c:if>>广州</option>
	               		<option value="4" <c:if test="${cityId == 4}">selected="selected"</c:if>>深圳</option>        		
	               		<option value="414" <c:if test="${cityId == 414}">selected="selected"</c:if>>长沙</option>
	               		<option value="483" <c:if test="${cityId == 483}">selected="selected"</c:if>>西安</option>
	               		<option value="541" <c:if test="${cityId == 541}">selected="selected"</c:if>>昆明</option>
	               		<option value="188" <c:if test="${cityId == 188}">selected="selected"</c:if>>沈阳</option>
	               		<option value="158" <c:if test="${cityId == 158}">selected="selected"</c:if>>武汉</option>
	               		<option value="122" <c:if test="${cityId == 122}">selected="selected"</c:if>>青岛</option>
	               		<option value="304" <c:if test="${cityId == 304}">selected="selected"</c:if>>福州</option>
	               		<option value="2053" <c:if test="${cityId == 2053}">selected="selected"</c:if>>海口</option>
	                </select>
	                
	                <label>统计频道:</label>
	                <select id="rateType" name="rateType" style="width: 100px;margin-left: 10px;"  onkeydown="this.blur();this.focus();">
	               		<option value="0" <c:if test="${rateType == 0}">selected="selected"</c:if>>列表</option>
	               		<option value="1" <c:if test="${rateType == 1}">selected="selected"</c:if>>论坛</option>
	               		<option value="2" <c:if test="${rateType == 2}">selected="selected"</c:if>>详情</option>
	               		<option value="3" <c:if test="${rateType == 3}">selected="selected"</c:if>>小区</option>
	               		<option value="4" <c:if test="${rateType == 4}">selected="selected"</c:if>>经纪人</option>	 
	               		<option value="5" <c:if test="${rateType == 5}">selected="selected"</c:if>>专题</option>
	               		<option value="6" <c:if test="${rateType == 6}">selected="selected"</c:if>>问答</option>
	               		<option value="7" <c:if test="${rateType == 7}">selected="selected"</c:if>>资讯</option> 
	               		<option value="8" <c:if test="${rateType == 8}">selected="selected"</c:if>>58</option>              		
	                </select>
		</div>
		<div>
			<label>起始时间：</label>
			<input name="startTime" value="${startTime }" style="width: 80px;"
					maxLength="10" onclick="InitSelectDate(0,this,'yyyy-MM-dd','')" readonly="readonly" /> 
			<label>结束时间：</label>
			<input name="endTime" value="${endTime }" style="width: 80px;" maxLength="10" onclick="InitSelectDate(0,this,'yyyy-MM-dd','')" readonly="readonly" /> 
				<input type="submit" id="search" value="查询" "/>
		</div>
	</div>
	<div class="todo">
		<table id="packageTable" class="c" style="text-align:center">
			<thead>
				<tr>
					<th style="width:3%;">编号</th>
					<th style="width:8%;">城市</th>
					<th style="width:8%;">出租房详情页PV</th>
					<th style="width:8%;">出租房列表uv</th>
					<th style="width:8%">出租房uv转化率</th>
					<th style="width:8%;">出租房列表页PV</th>
					<th style="width:8%;">出租房转化率</th>
					<th style="width:8%;">二手房详情页PV</th>
					<th style="width:8%;">二手房列表uv</th>
					<th style="width:8%">二手房uv转化率</th>
					<th style="width:8%;">二手房列表页PV</th>
					<th style="width:8%;">二手房转化率</th>
					<th style="width:8%;">日期</th>
				</tr>
			</thead>
			<tbody align="center" >
			<c:choose>
				<c:when test="${not empty rateList}">
					<c:forEach items="${rateList}" var="rate" varStatus="var">
						<tr>
							<td>${var.index + 1}</td>
							<td>${rate.cityName}</td>
							<td>${rate.rentVpPv}</td>
							<td>${rate.rentListUv }</td>
							<td>${rate.rentUvConvRate }</td>
							<td>${rate.rentListPv}</td>
							<td>${rate.rentConvRate}</td>
							<td>${rate.resaleVpPv}</td>
							<td>${rate.resaleListUv  }</td>
							<td>${rate.resaleUvConvRate  }</td>
							<td>${rate.resaleListPv}</td>
							<td>${rate.resaleConvRate}</td>
							<td>${rate.entryDate}</td>
							
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="main_info">
						<td colspan="8">没有相关数据</td>
					</tr>
				</c:otherwise>
			</c:choose>
			</tbody>
		</table>
	</div>
	<div class="page_and_btn">
			<jsp:include page="/WEB-INF/snippets/page.jsp" />
		</div>
</form>
