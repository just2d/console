<style>
	.tab{
		background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%
	}
</style>
<div id="tabs" class="tabs">  
  <ul>  
	<li class="
		<c:choose>
			<c:when test="${forum == '0'}"> tabs_active</c:when>
			<c:when test="${forum != '0'}"> tab</c:when>
		</c:choose>">	
	<a href="/bbs/manager/forum?forum=0">添加论坛</a></li>
	<li class="
	 	<c:choose>
			<c:when test="${forum == '1'}"> tabs_active</c:when>
			<c:when test="${forum != '1'}"> tab</c:when>
		</c:choose>">
	 <a href="/bbs/manager/manageforum?forum=1">管理论坛</a></li>

	</ul>
</div>
