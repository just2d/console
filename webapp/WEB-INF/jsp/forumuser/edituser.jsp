<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="/js/forum/bbs.js" type="text/javascript"></script>

<div style="width: 100%;text-align: left;">当前位置：用户管理 &gt; 修改用户信息</div> 

<div id="editUserDiv" style="width: 100%;text-align: left;">
	<form  action="/forumuser/edit" id="addUserForm" method="post">
		<input   type="hidden"  id=id name="id" value="${forumUser.id}">
        <input type="hidden"  name="userid"  id="userid" value="${forumUser.userid}"/>
         <input type="hidden"  name="userRole"  id="userRole" value="${tfUser.role}"/>
		<table border="0" cellpadding="3" cellspacing="1">
			<tr><td style="width:100px;"><span class="important">*</span>用户名:</td>
			<td style="width:300px;"><input style="width: 200px;"  type="text"  id="name" name="username" value="${forumUser.username}" onblur="setUserId()">
			</td>
			<td><span id="nameSp" class="action_po_top">用户名不能为空</span></td></tr>
			<tr>
				<td><span class="important">*</span>角色:</td>
				<td>
					<select id="editroleid" name="roleid">
						
					   <c:forEach items="${roleList}" var="role">
                        <option value="${role.id}">${role.name}</option>
                    	</c:forEach>
					</select>
				</td>
				<td>
				</td>
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
	  城市:<select name="cityid" id="cityLocale"  onchange="changeCityGetForum(this.value,0,0,0,true);">
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
					<select id="forumList" name="forumid"   onChange="changeForum();">
					</select>
				</td>
				<td>
				</td>
			</tr>
			<tr><td colspan="2" align="center"><input type="button" value="提交" onclick="checkAddUserInfo('${forumUser.id}');"/><input type="button" value="返回" onclick='javaScript:window.location.href="/forumuser/list"' /></td></tr>
		</table>
	</form>
</div>
<script type="text/javascript">	
$("#ch").val("${ch}");
setCityInfo("${ch}","cityLocale");
$("#cityLocale").val("${forumUser.cityid}");
$("#editroleid").val("${forumUser.roleid}");
			
	 changeCityGetForum("${forumUser.cityid}",0,"${forumUser.forumid}",0,true);
</script>
