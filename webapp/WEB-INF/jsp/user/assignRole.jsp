<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
<!--
function check(){
	
	 $("#editUserForm").submit();
}
//-->
</script>
<div style="width: 100%;text-align: left;">当前位置：用户管理 &gt; 用户列表  &gt; 指派角色 </div> 
<div style="width: 100%;">
	<label style="color:#000;line-height:25px;width:100%;font-size:16px;font-weight:bold">设置用户角色</label>
	<form name="role" action="/user/assignRole/save" id="editUserForm" method="post" style="width:100%">
		<input type="hidden" name="id" id="userId" value="${user.id }" />
		<table border="0" cellpadding="3" cellspacing="1" align="center">
			<tr><td style="width:60px;">用户名称:</td><td align="left">${user.userName }</td></tr>
			<tr><td>角色名称:</td><td> 
			<c:forEach items="${roles}" var="role">
					<input type="checkbox" name="ids" 
					<c:if test="${role.checked}">
					checked="checked" 
					</c:if>
					value="${role.id }" />${role.name}
			</c:forEach>
				
			</td></tr>
			<tr><td colspan="2" align="center"><input type="button" value="保存" onclick="check();"/> </td></tr>
		</table>
	</form>
</div>