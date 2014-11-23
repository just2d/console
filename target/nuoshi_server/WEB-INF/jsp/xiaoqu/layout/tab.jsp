<style>
	.tab{
		background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%
	}
</style>
<div id="tabs" class="tabs">  
  <ul>  
	<li class="
		<c:choose>
			<c:when test="${flag == '0'}"> tabs_active</c:when>
			<c:when test="${flag != '0'}"> tab</c:when>
		</c:choose>">	
		<a href="${contextPath}/estate/layout/compSel?${paramMap}&flag=0">小区户型图精选</a>
	</li>
	<li class="
	 	<c:choose>
			<c:when test="${flag == '1'}"> tabs_active</c:when>
			<c:when test="${flag != '1'}"> tab</c:when>
		</c:choose>">
	 	<a href="${contextPath}/estate/commPhoto/compSel?${paramMap}&flag=1">小区图精选</a>
	 </li>
	</ul>
</div>
