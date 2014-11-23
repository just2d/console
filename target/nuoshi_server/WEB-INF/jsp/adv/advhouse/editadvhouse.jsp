<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div style="width: 100%;text-align: left;">当前位置：信息质量控制>><b>黑名单管理</b></div>
    <div id="divAdd"  style="distplay:none">
	      <jsp:include page="advHouseMes.jsp"></jsp:include>
	</div>
	<script type="text/javascript">
$(function() {
	locationInit("city","dist","block",-2,-2,-2);
});
</script>