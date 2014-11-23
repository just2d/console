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



<link rel="stylesheet" type="text/css" href="/js/cluetip/jquery.cluetip.css" />


<link type="text/css" rel="stylesheet" href="/css/common/smoothness/jquery-ui-1.8.14.custom.css"/>
<link type="text/css" rel="stylesheet" href="/css/common/validation.css"/>
<link type="text/css" rel="stylesheet" href="/css/layout.css"/>

</head>
<body>
<div style="width: 100%;text-align: left;">当前位置：菜单管理 </div> 

<div id="addFunctionDiv">
	<label style="color:gray;">菜单信息</label>
	<form name="function" action="/function/addFunction" id="addFunctionForm" method="post">
		<input type="hidden" name="addFunctionInfo" id="addFunctionInfo" />
		<table border="0" cellpadding="3" cellspacing="1" style="text-align: left;" >
			<tr><td style="width:100px;"><span class="important">*</span> 名称:</td><td  ><input style="width: 200px;"  type="text"  id="name" name="name" ></td><td><span id="functionnameSp" class="action_po_top"> 名称不能为空</span></td></tr>
			<tr><td style="width:100px;">上级目录:</td><td  >  ${parent.name}<input type="hidden" name="parentId" value="${parent.id}" /></td><td> &nbsp;</td></tr>
			<tr><td><span class="important">*</span>资源路径:</td><td><input style="width: 200px;"  type="text" id="url" name="url" ></td><td><span id="urlSp" class="action_po_top">功能路径不能为空</span></td></tr>
			<tr><td>功能集:</td><td colspan="2"><textarea cols="57" rows="7"  id="funcs" name="funcs" ></textarea></td><td> &nbsp;</td></tr>
			<tr><td> 描述:</td><td colspan="2"><textarea   cols="57" rows="7"  id="description" name="description" ></textarea></td><td> &nbsp;</td></tr>
			<tr><td colspan="2" align="center"><input type="button" value="提交" onclick="checkAddFunctionInfo();"/><input type="reset" value="重置"/><input type="button" value="退出" onclick="$('#addFunctionDiv').dialog('close');" /></td></tr>
		</table>
	</form>
</div>
<div id="editFunctionDiv">
	<label style="color:gray;">编辑菜单</label>
	<form name="editFunctionForm" id="editFunctionForm" action="/function/edit/save" id="addFunctionForm" method="post">
		<input type="hidden" name="id" id="editFunctionId" />
		<table border="0" cellpadding="3" cellspacing="1" style="text-align: left;" >
			<tr><td style="width:100px;"><span class="important">*</span> 名称:</td><td  ><input style="width: 200px;"  type="text"  id="editName" name="name" ></td><td><span id="editfunctionnameSp" class="action_po_top"> 名称不能为空</span></td></tr>
			<tr><td style="width:100px;">上级目录:</td><td  >  ${parent.name}<input type="hidden" name="parentId" value="${parent.id}" /></td><td> &nbsp;</td></tr>
			<tr><td><span class="important">*</span>资源路径:</td><td><input style="width: 200px;"  type="text" id="editurl" name="url" ></td><td><span id="editurlSp" class="action_po_top">功能路径不能为空</span></td></tr>
			<tr><td>功能集:</td><td colspan="2"><textarea cols="57" rows="7"  id="editfuncs" name="funcs" ></textarea></td><td> &nbsp;</td></tr>
			<tr><td> 描述:</td><td colspan="2"><textarea   cols="57" rows="7"  id="editdescription" name="description" ></textarea></td><td> &nbsp;</td></tr>
			<tr><td colspan="2" align="center"><input type="button" value="提交" onclick="checkEditFunctionInfo();"/><input type="reset" value="重置"/><input type="button" value="退出" onclick="$('#editFunctionDiv').dialog('close');" /></td></tr>
		</table>
	</form>
</div>
<form action="/function/searchFunction" id="searchFunctionForm" method="post">
	<label>名称：</label><input type="text" id="paraFunctionName" name="paraFunctionName" value="${searchText}"/>
	<input type="button" value="查询" onClick="searchFunction();"/>
	<input type="button" value="添加菜单" onClick="addFunction();" />
<input type="hidden" name="parentId" value="${parent.id }" > 
<div class="todo">
	<table style="text-align:center">
		<thead>
			<tr>
				<th style="width:30%;">功能名称</th>
				<th style="width:30%;">资源路径</th>
				<th style="width:30%;">上级目录</th>
				<th style="width:10%;">操作</th>
			</tr>
		</thead>
		<tbody align="center" >
			<c:choose>
				<c:when test="${not empty functionList}">
					<c:forEach items="${functionList}" var="paraFunction">
						 <tr>
						     <td><c:out value="${paraFunction.name}" /></td>
						     
						     <td><c:out value="${paraFunction.url}" /></td>
						     <td><c:out value="${parent.name}" /></td>
						     <td>
						     	<a href="javaScript:edit('${paraFunction.id}')">编辑</a> | <a href="javaScript:confirmDelFunction(${paraFunction.id},${parent.id })">删除</a>
						     </td>
						 </tr>
					 </c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="main_info">
						<td colspan="4">没有相关数据</td>
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
</body>
</html>
