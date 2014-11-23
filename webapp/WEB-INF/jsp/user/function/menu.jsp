<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/js/common/jquery.js"></script>
<script type="text/javascript" src="/js/common/jquery-ui.js"></script>
<script type="text/javascript" src="/js/common/jquery.blockUI.js"></script>
<script type="text/javascript" src="/js/common/jquery.tipsy.js"></script>
<script type="text/javascript" src="/js/common/validation.js"></script>
<script type="text/javascript" src="/js/common/common.js"></script>


<script type='text/javascript' src='/js/cluetip/jquery.cluetip.js'></script>
<script type="text/javascript" src="/js/user/function.js"></script>

<script type="text/javascript" src="/js/ztree/jquery.ztree.js"></script>

<link rel="stylesheet" type="text/css" href="/js/cluetip/jquery.cluetip.css" />


<link type="text/css" rel="stylesheet" href="/css/common/smoothness/jquery-ui-1.8.14.custom.css"/>
<link type="text/css" rel="stylesheet" href="/css/common/validation.css"/>
<link type="text/css" rel="stylesheet" href="/css/layout.css"/>
<link rel="stylesheet" href="/js/ztree/zTreeStyle/zTreeStyle.css" type="text/css" />
</head>
<body>

  <SCRIPT type="text/JavaScript">
 var zTree;
	var demoIframe;

	var setting = {
		isSimpleData: true,
		checkable : false,
		treeNodeKey: "id",
		treeNodeParentKey: "pId",
		showLine: true,
		root:{ 
			isRoot:true,
			nodes:[]
		}
	};
	zNodes =${ZtreeNodes};

	$(document).ready(function(){
		setting.expandSpeed = ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast";
		zTree = $("#tree").zTree(setting, zNodes);
	});
  //-->
  

  </SCRIPT>
 <span style="cursor: pointer;" onclick="window.location.reload();" >刷新菜单</span> &nbsp;<span style="cursor: pointer;" onclick="top.location='/login/index'" >返回主菜单</span>
<TABLE border="0" height="600px" align="left">
	<TR>
		<TD width=180px align=left valign=top style="BORDER-RIGHT: #999999 1px dashed">
			<ul id="tree" class="tree" style="overflow:auto;"></ul>
		</TD>
		<TD width=770px align=left valign=top></TD>
	</TR>
</TABLE>
</body>
</html>
