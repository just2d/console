<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>500错误页面</title>
<script type="text/javascript" src="/js/common/jquery.js"></script>
<script type="text/javascript" src="/js/common/jquery-ui.js"></script>
<script type="text/javascript" src="/js/common/jquery.blockUI.js"></script>
<script type="text/javascript" src="/js/common/jquery.tipsy.js"></script>
<script type="text/javascript" src="/js/common/validation.js"></script>
<script type="text/javascript" src="/js/common/common.js"></script>

<link type="text/css" rel="stylesheet" href="/css/common/tipsy.css"/>
<link type="text/css" rel="stylesheet" href="/css/common/smoothness/jquery-ui-1.8.14.custom.css"/>
<link type="text/css" rel="stylesheet" href="/css/common/validation.css"/>

<link type="text/css" rel="stylesheet" href="/css/layout.css"/>
<link rel="stylesheet" href="/css/tab/tab.css" type="text/css" />
<script type="text/javascript">
$(document).ready(function(){

			$(".table-data:even").addClass("table-data-even");

		});

	</script>
</head>
<body>
<div >

<table border="0" cellpadding="2" cellspacing="2" align="center" width="100%">
	<tr>
		<td colspan="2" style="height:64px;">
		<jsp:include page="../tiles/header.jsp"/>

</td>
	</tr>
	<tr>
		<td  style="vertical-align: top ;width:100px;">
		<jsp:include page="../tiles/menu.jsp" />

</td>
		<td>
	<img src="/images/500.jpg" height="500">
</td>
	</tr>
	
</table>
		<jsp:include page="../tiles/footer.jsp"></jsp:include>
</div>
</body>
</html>
