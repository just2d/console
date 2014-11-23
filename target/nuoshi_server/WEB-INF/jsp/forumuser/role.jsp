<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="/js/forum/role.js"></script>
<script src="/js/forum/bbs.js" type="text/javascript"></script>

<div style="width: 100%;text-align: left;">当前位置：论坛管理 &nbsp;&gt;角色管理 </div> 

<div id="addRoleDiv">
	<label style="color:gray;">角色信息</label>
	<form name="role" action="/forum/role/addRole" id="addRoleForm" method="post">
		<input type="hidden" name="addRoleInfo" id="addRoleInfo" />
		<table border="0" cellpadding="3" cellspacing="1">
			<tr><td style="width:100px;"><span class="important">*</span>角色名称:</td><td style="width:200px;"><input style="width: 200px;"  type="text"  id="name" name="name" ></td><td><span id="rolenameSp" class="action_po_top">角色名称不能为空</span></td></tr>
			<tr><td><span class="important">*</span>编号:</td><td><input style="width: 200px;"  type="text" id="code" name="code" ></td><td><span id="codeSp" class="action_po_top">角色编号不能为空</span></td></tr>
			<tr><td>状态:</td><td style="text-align: left ">有效<input type="radio"  name="status" checked="checked" value="1" />&nbsp;无效<input type="radio"  name="status" value="0" /></td><td></td></tr>
			<tr><td colspan="2" align="center"><input type="button" value="提交" onclick="checkAddRoleInfo();"/><input type="reset" value="重置"/><input type="button" value="退出" onclick="$('#addRoleDiv').dialog('close');" /></td></tr>
		</table>
	</form>
</div>
<div id="editRoleDiv">
	<label style="color:gray;">编辑角色</label>
	<form name="role" id="editRoleForm" action="/forum/role/edit/save" id="editRoleForm" method="post">
		<input type="hidden" name="id" id="editRoleId" />
		<table border="0" cellpadding="3" cellspacing="1">
			<tr><td style="width:100px;"><span class="important">*</span>角色名称:</td><td style="width:200px;"><input style="width: 200px;"  type="text"  id="editName" name="name" ></td><td><span id="editrolenameSp" class="action_po_top">角色名称不能为空</span></td></tr>
			<tr><td><span class="important">*</span>编号:</td><td><input style="width: 200px;"  type="text" id="editCode" name="code" ></td><td><span id="editcodeSp" class="action_po_top">角色编号不能为空</span></td></tr>
			<tr><td>状态:</td><td style="text-align: left ">有效<input type="radio"  id="editStatus1"  name="status" checked="checked" value="1" />&nbsp;无效<input type="radio" id="editStatus0"  name="status" value="0" /></td><td></td></tr>
			<tr><td colspan="2" align="center"><input type="button" value="提交" onclick="checkEditRoleInfo();"/><input type="button" value="退出" onclick="$('#editRoleDiv').dialog('close');" /></td></tr>
		</table>
	</form>
</div>

<div id="editpowerDiv">
<form  action="/forum/role/list"  id="rolePowerForm"  method="post">
				<input type="hidden" name="userid" id="roleId" />
				<table>
					<thead>
						<tr>
							<th style=" width:3%;" ><input type="checkbox" onclick="checkAll(this)" /></th>
							<th style=" width:84%; ">权限设置</th>
						</tr>
					</thead>
					<tbody class="rasent">
						<c:forEach items="${functions}" var="list">
								<tr >
									<td  ><input type="checkbox" name="ids"   id="function${list.id}" value="${list.id}" />
									<td >${list.name}</td>
								</tr>
							</c:forEach>
					</tbody>

				</table>
				<input type="button" onclick="batchupdate();" value="提交" />&nbsp;
				<input type=button  value="取消" onclick="$('#editpowerDiv').dialog('close');"/>&nbsp;
				</form>
</div>
<form action="/forum/role/searchRole" id="searchRoleForm" method="post">
	<label></label><input type="text" id="paraRoleName" name="condition" value="${searchText}"/>
	<input type="submit" value="查询" />
	<input type="button" value="添加角色" onClick="addRole();" />

<div class="todo">
	<table style="text-align:center">
		<thead>
			<tr>
				<th style="width:30%;">角色名称</th>
				<th style="width:10%;">编号</th>
				<th style="width:30%;">状态</th>
				<th style="width:30%;">操作</th>
			</tr>
		</thead>
		<tbody align="center" >
			<c:choose>
				<c:when test="${not empty roleList}">
					<c:forEach items="${roleList}" var="paraRole">
						 <tr>
						     <td><c:out value="${paraRole.name}" /></td>
						     
						     <td><c:out value="${paraRole.code}" /></td>
						     <td><c:out value="${paraRole.statusLabel}" /></td>
						     <td>
						     	<a href="javaScript:editRole('${paraRole.id}')">编辑</a> | <a href="javaScript:confirmDelRole(${paraRole.id})">删除</a> | <a href="javaScript:power(${paraRole.id})">权限设置</a>
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