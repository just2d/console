<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="/js/user/user.js"></script>

<div style="width: 100%;text-align: left;">当前位置：用户管理 </div> 

<div id="addUserDiv">
	<label style="color:gray;">用户信息：</label>
	<form name="user" action="/user/addUser" id="addUserForm" method="post">
		<input type="hidden" name="addUserInfo" id="addUserInfo" />
		<table border="0" cellpadding="3" cellspacing="1">
			<tr><td style="width:100px;"><span class="important">*</span>用户名:</td><td style="width:300px;"><input style="width: 200px;"  type="text" value="" id="userName"></td><td><span id="usernameSp" class="action_po_top">用户名不能为空</span></td></tr>
			<tr><td><span class="important">*</span>密码:</td><td><input style="width: 200px;" type="password" id="password" /></td><td><span id="psSp" class="action_po_top">密码不能为空</span></td></tr>
			<tr><td><span class="important">*</span>确认密码:</td><td><input style="width: 200px;" type="password" id="repassword" ></td><td><span id="repsSp" class="action_po_top">两次输入的密码不一致</span></td></tr>
			<tr>
				<td><span class="important">*</span>性别:</td>
				<td>
					<select id="userSex">
						<option value="1" selected="selected">男</option>
						<option value="0">女</option>
					</select>
				</td>
				<td>
				</td>
			</tr>
			<tr><td><span class="important">*</span>手机:</td><td><input style="width: 200px;"  type="text" id="mobile"></td><td><span id="mSp" class="action_po_top">手机格式错误</span></td></tr>
			<tr><td><span class="important">*</span>邮件:</td><td><input style="width: 200px;"  type="text" id="email"></td><td><span id="emSp" class="action_po_top">邮箱格式错误</span></td></tr>
			<tr><td><span class="important">*</span>中文名字:</td><td><input style="width: 200px;"  type="text" id="chnName"></td><td><span id="cnSp" class="action_po_top">中文名字不能为空</span></td></tr>
			<tr><td> 角色:</td><td><c:forEach items="${roles}" var="role">
					${role.name}<input type="checkbox" name="roleIds"  value="${role.id }" />
			</c:forEach></td><td>&nbsp;</td></tr>
			<tr><td colspan="2" align="center"><input type="button" value="提交" onclick="checkAddUserInfo();"/><input type="reset" value="重置"/><input type="button" value="退出" onclick="$('#addUserDiv').dialog('close');" /></td></tr>
		</table>
	</form>
</div>
<form action="/user/searchUser" id="searchUserForm" method="post">
	<label>姓名：</label><input type="text" id="paraUserName" name="paraUserName" value="${searchText}"/>
	<input type="button" value="查询" onClick="searchUser();"/>
	<input type="button" value="添加用户" onClick="addUser();" />

<div class="todo">
	<table style="text-align:center">
		<thead>
			<tr>
				<th style="width:10%;">姓名</th>
				<th style="width:15%;">登录名</th>
				<th style="width:15%;">所属角色</th>
				<th style="width:10%;">性别</th>
				<th style="width:10%;">手机</th>
				<th style="width:25%;">邮箱</th>
				<th style="width:30%;">操作</th>
			</tr>
		</thead>
		<tbody align="center" >
			<c:choose>
				<c:when test="${not empty userList}">
					<c:forEach items="${userList}" var="paraUser">
						 <tr>
						     <td><c:out value="${paraUser.chnName}" /></td>
						     <td><c:out value="${paraUser.userName}" /></td>
						     <td><c:out value="${paraUser.roleName}" /></td>
						     <td>
						     	<c:choose>
						     		<c:when test="${paraUser.sex == 0}">
						     			<c:out value="女" />
						     		</c:when>
						     		<c:otherwise>
						     			<c:out value="男" />
						     		</c:otherwise>
						     	</c:choose>
						     </td>
						     <td><c:out value="${paraUser.mobile}" /></td>
						     <td><c:out value="${paraUser.email}" /></td>
						     <td>
						     	<a href="modifyUserInfo?id=${paraUser.id}">编辑</a> | <a href="javaScript:confirmDelUser(${paraUser.id})">删除</a>  | <a href="/user/assignRole/${paraUser.id}">指派角色</a>
						     </td>
						 </tr>
					 </c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="main_info">
						<td colspan="7">没有相关数据</td>
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