<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/js/common/jsdate.js"></script>
<title>显示图片</title>
</head>
<body>
		<input class="def" name="startDate" style="width:300px;" class="dateCss" type="text" onclick="SelectDate(this,'yyyy-MM-dd','起始日期')" readonly="readonly" value="${searchTopic.startDate}">
</body>
</html>