<style>
	.tab{
		background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%
	}
</style>
<div id="tabs" class="tabs">  
  <ul>  
	<li class="
		<c:choose>
			<c:when test="${authStatus == '0'}"> tabs_active</c:when>
			<c:when test="${authStatus != '0'}"> tab</c:when>
		</c:choose>">	
	<a href="${contextPath}/estate/list?authStatus=0&page.pageNo=0&cityId=${cityId}">待审核小区</a></li>
	<li class="
	 	<c:choose>
			<c:when test="${authStatus == '1'}"> tabs_active</c:when>
			<c:when test="${authStatus != '1'}"> tab</c:when>
		</c:choose>">
	 <a href="${contextPath}/estate/list?authStatus=1&page.pageNo=0&cityId=${cityId}">通过审核小区</a></li>
	<li class="
		<c:choose>
			<c:when test="${authStatus == '2'}"> tabs_active</c:when>
			<c:when test="${authStatus != '2'}"> tab</c:when>
		</c:choose>">
	<a  href="${contextPath}/estate/list?authStatus=2&page.pageNo=0&cityId=${cityId}">未通过审核小区</a></li>
	<li class="
		<c:choose>
			<c:when test="${authStatus == '3'}"> tabs_active</c:when>
			<c:when test="${authStatus != '3'}"> tab</c:when>
		</c:choose>">
		<a   href="${contextPath}/estate/list?authStatus=3&page.pageNo=0&cityId=${cityId}">已删除小区</a></li>
	</ul>
</div>
