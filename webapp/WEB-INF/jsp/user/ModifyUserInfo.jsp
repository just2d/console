<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="/js/user/user.js"></script>

<div style="width: 100%;text-align: left;">当前位置：用户管理 &gt; 修改用户信息</div> 

<div>
	<form name="user" action="/user/confirmModifyUser" onsubmit="return checkUserInfo()">
	<input type="hidden" name="id" value="${user.id}"><br>
	<table border="0" cellpadding="3" cellspacing="1">
	<tr><td style="width:300px;"><span class="important">*</span>用户名:</td><td style="width:400px;"><input style="width: 200px;"  type="text" name="userName" value="${user.userName}" disabled="disabled"></td><td></td></tr>
	<tr><td><span class="important"></span>密码:</td><td><input style="width: 200px;" type="password" id="password" name="password"/></td><td><span id="psSp" class="action_po_top">密码不能为空</span></td></tr>
	<tr><td><span class="important"></span>确认密码:</td><td><input style="width: 200px;"  type="password" id="repassword" name="repassword"></td><td><span id="repsSp" class="action_po_top">两次输入的密码不一致</span></td></tr>
	<tr><td><span class="important">*</span>性别:</td><td><select name="sex">
	<c:if test="${user.sex==0}"><option value="1">男</option><option value="0" selected="selected">女</option></c:if>
	<c:if test="${user.sex==1}"><option value="1" selected="selected">男</option><option value="0">女</option></c:if>
	</select>
	</td><td></td></tr>
	<tr><td><span class="important">*</span>手机:</td><td><input style="width: 200px;"  type="text" id="mobile" value="${user.mobile}" name="mobile"></td><td><span id="mSp" class="action_po_top">手机格式错误</span></td></tr>
	<tr><td><span class="important">*</span>邮件:</td><td><input style="width: 200px;"  type="text" id="email" value="${user.email}" name="email"></td><td><span id="emSp" class="action_po_top">邮箱格式错误</span></td></tr>
	<tr><td><span class="important">*</span>中文名字:</td><td><input style="width: 200px;"  type="text" id="chnName"  value="${user.chnName}"  name="chnName"></td><td><span id="cnSp" class="action_po_top">中文名字不能为空</span></td></tr>
	<tr><td colspan="3" align="center"><input type="submit" value="提交"/><input type="reset" value="重置"/><input type="button" value="返回" onclick="window.location.href='/user/list'" /></td></tr>
	</table>
	</form>
</div>
