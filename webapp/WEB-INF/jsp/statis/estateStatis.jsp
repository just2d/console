<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link type="text/css" rel="stylesheet" href="/css/layout.css"/>

<form action="/statis/estate/postcount" method="post" id="estateForm" enctype="multipart/form-data" style="position:relative;margin-top:6px; z-index:90">

	<label>文件：</label><input type="file" name="file" />
	<input type="submit" value="房源数据下载"/>
	
	
</form>