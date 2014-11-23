<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<script type="text/javascript" src="/js/common/jquery.js"></script>
<script type="text/javascript" src="/js/common/jquery-ui.js"></script>
<script type="text/javascript" src="/js/common/jquery.blockUI.js"></script>
<script type="text/javascript" src="/js/common/jquery.tipsy.js"></script>
<script type="text/javascript" src="/js/common/validation.js"></script>
<script type="text/javascript" src="/js/common/common.js"></script>

<script type='text/javascript' src='/js/cluetip/jquery.cluetip.js'></script>

<link rel="stylesheet" type="text/css" href="/js/cluetip/jquery.cluetip.css" />


<link type="text/css" rel="stylesheet" href="/css/common/smoothness/jquery-ui-1.8.14.custom.css"/>
<link type="text/css" rel="stylesheet" href="/css/common/validation.css"/>
<link type="text/css" rel="stylesheet" href="/css/common/tipsy.css"/>
<link type="text/css" rel="stylesheet" href="/css/layout.css"/>

</head>
<body>
<div >

<table border="0" cellpadding="2" cellspacing="2" align="center" width="100%">
	<tr>
		<td colspan="2" style="height:64px;"><tiles:insertAttribute name="header" /></td>
	</tr>
	<tr>
		<td  style="vertical-align: top ;width:100px;"><tiles:insertAttribute name="menu" /></td>
		<td style="vertical-align: top; overflow-x: scoll"><tiles:insertAttribute name="body" /></td>
	</tr>
	
</table>
<tiles:insertAttribute name="footer" />
</div>
</body>
</html>
