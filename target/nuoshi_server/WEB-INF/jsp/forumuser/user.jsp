<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript" src="/js/forum/user.js"></script>
<script src="/js/forum/bbs.js" type="text/javascript"></script>
<div style="width: 100%;text-align: left;">当前位置：论坛管理 &nbsp;&gt;版主管理 </div> 
<div id="addUserDiv">
	<form action="/forumuser/adduser" id="addUserForm" method="post">
		<table border="0" cellpadding="3" cellspacing="1">
			<tr><td style="width:100px;"><span class="important">*</span>用户名:</td>
			<td style="width:300px;"><input style="width: 200px;"  type="text" id="name" name="username" onblur="setUserId()">
			 <input type="hidden"  name="userid"  id="userid" value=""/>
			 <input type="hidden"  name="userRole"  id="userRole" value="0"/>
			</td>
			<td><span id="nameSp" class="action_po_top">用户名不能为空</span></td></tr>
			<tr>
				<td><span class="important">*</span>角色:</td>
				<td>
				<select id="roleid" name="roleid" >
				
				 <c:forEach items="${roleList}" var="role">
                        <option value="${role.id}">${role.name}</option>
                    </c:forEach>
				</select>
				</td>
				<td></td>
			</tr>

			<tr>
				<td><span class="important">*</span>城市:</td>
				<td>
				    首字母:<select name="ch" id="ch" onchange='setCityInfo(this.value,"cityLocale")'>
	  			<option value="">-请选择-</option>
	  			<option value="a">A</option>
	  			<option value="b">B</option>
	  			<option value="c">C</option>
	  			<option value="d">D</option>
	  			<option value="e">E<J/option>
	  			<option value="f">F</option>
	  			<option value="g">G</option>
	  			<option value="h">H</option>
	  			<option value="i">I</option>
	  			<option value="j">J</option>
	  			<option value="k">K</option>
	  			<option value="l">L</option>
	  			<option value="m">M</option>
	  			<option value="n">N</option>
	  			<option value="o">O</option>
	  			<option value="p">P</option>
	  			<option value="q">Q</option>
	  			<option value="r">R</option>
	  			<option value="s">S</option>
	  			<option value="t">T</option>
	  			<option value="u">U</option>
	  			<option value="v">V</option>
	  			<option value="w">W</option>
	  			<option value="x">X</option>
	  			<option value="y">Y</option>
	  			<option value="z">Z</option>
	     </select>
	  城市:<select name="cityid" id="cityLocale" onchange="changeCityGetForum(this.value,0,0,0,true);">
	  		<option value="">-请选择-</option>
			<c:if test="${cityName ne null}">
	 			<option value="${condition.cityId}" selected="selected">${cityName}</option>
	 		</c:if>
	     </select>
				</td>
			
			</tr>

			<tr>
				<td>主论坛:</td>
				<td>
				   <input type="hidden"  name="forumname"  id="forumname"  value=""/>
					<select id="forumList" name="forumid"   onChange="changeForum();">
					</select>
				</td>
		
			</tr>
		

			</table>
			<table>
			<tr><td colspan="2" align="center"><input type="button" value="提交" onclick="checkAddUserInfo();"/><input type="button" value="取消" onclick="$('#addUserDiv').dialog('close');" /></td></tr>
		</table>
	</form>
</div>

<form action="/forumuser/list" id="searchUserForm" method="post">
	
	  首字母:<select name="ch" id="chUser" onchange='setCityInfo(this.value,"cityLocaleUser")'>
	  			<option value="">-请选择-</option>
	  			<option value="a">A</option>
	  			<option value="b">B</option>
	  			<option value="c">C</option>
	  			<option value="d">D</option>
	  			<option value="e">E<J/option>
	  			<option value="f">F</option>
	  			<option value="g">G</option>
	  			<option value="h">H</option>
	  			<option value="i">I</option>
	  			<option value="j">J</option>
	  			<option value="k">K</option>
	  			<option value="l">L</option>
	  			<option value="m">M</option>
	  			<option value="n">N</option>
	  			<option value="o">O</option>
	  			<option value="p">P</option>
	  			<option value="q">Q</option>
	  			<option value="r">R</option>
	  			<option value="s">S</option>
	  			<option value="t">T</option>
	  			<option value="u">U</option>
	  			<option value="v">V</option>
	  			<option value="w">W</option>
	  			<option value="x">X</option>
	  			<option value="y">Y</option>
	  			<option value="z">Z</option>
	     </select>
	  城市:<select name="cityId" id="cityLocaleUser"  >
	  		<option value="">-请选择-</option>
			<c:if test="${cityName ne null}">
	 			<option value="${condition.cityId}" selected="selected">${cityName}</option>
	 		</c:if>
	     </select>
	     <label></label><input type="text" id="search" name="search" value="${search}"/>
	<input type="submit" value="查询"/>
	<input type="button" value="添加版主"   onClick="addUser();"/>

<div class="todo">
	<table style="text-align:center">
		<thead>
			<tr>
				<th style="width:20%;">角色名称</th>
				<th style="width:20%;">用户名</th>
				<th style="width:20%;">城市</th>
				<th style="width:20%;">所属板块</th>
				<th style="width:20%;">操作</th>
			</tr>
		</thead>
		<tbody align="center" >
			<c:choose>
				<c:when test="${not empty userList}">
					<c:forEach items="${userList}" var="paraUser">
						 <tr>
						     <td><c:out value="${paraUser.rolename}" /></td>
						     <td><c:out value="${paraUser.username}" /></td>
						     <td><c:out value="${paraUser.cityName}" /></td>
						     <td><c:out value="${paraUser.parent}" /></td>
						     <td>
						     	<a href="javaScript:confirmDelUser(${paraUser.id})">删除</a>  
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
<script type="text/javascript">
  $("#chUser").val("${ch}");
  setCityInfo("${ch}","cityLocaleUser");
  $("#cityLocaleUser").val("${cityId}");
  </script>