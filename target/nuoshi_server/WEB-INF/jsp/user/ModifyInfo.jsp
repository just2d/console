<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
function checkInfo(){
	var isCommit = true;
	var password = document.getElementById("password").value;
	var rePwd = document.getElementById("repassword").value;
	var mobile = document.getElementById("mobile").value;
	var email = document.getElementById("email").value;
	var chnName = document.getElementById("chnName").value;
	var mreg=/^(13+\d{9})|(15+\d{9})|(18+\d{9})$/;
	var ereg=/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if(password==""){
	document.getElementById("psSp").style.display = "inline";
	isCommit = false;
	}else {
		document.getElementById("psSp").style.display = "none";
	}
	if(password!=rePwd){
	document.getElementById("repsSp").style.display = "inline";
	isCommit = false;
	} else {
		document.getElementById("repsSp").style.display = "none";
	}
	 if(!mreg.test(mobile))
	  {
		 document.getElementById("mSp").style.display = "inline";
		 isCommit = false;
	  }else {
			document.getElementById("mSp").style.display = "none";
		}
	 if(!ereg.test(email))
	  {
		 document.getElementById("emSp").style.display = "inline";
		 isCommit = false;
	  } else {
			document.getElementById("emSp").style.display = "none";
		}
	 if(!ereg.test(email))
	  {
		 document.getElementById("emSp").style.display = "inline";
		 isCommit = false;
	  } else {
			document.getElementById("emSp").style.display = "none";
		}
	 if(chnName=="")
	 {
		 document.getElementById("cnSp").style.display = "inline";
		 isCommit = false;
	 }  else {
			document.getElementById("cnSp").style.display = "none";
		}
	 return isCommit;
}

</script>
<div>
	<h2>修改个人信息</h2>
	<form name="user" action="/user/modifyInfo/save" 
		onsubmit="return checkInfo()">
		<input type="hidden" name="id" value="${user.id}">
		<table border="0" cellpadding="3" cellspacing="1" align="center" >
			<tr>
				<td style="text-align: right;" ><span class="important">*</span>用户名:</td>
				<td style="text-align: left;" ><input style="width: 200px;" type="text" name="userName"
					value="${user.userName}" disabled="disabled"></td>
				<td style="width: 200px;"  >&nbsp;</td>
			</tr>
			<tr>
				<td  style="text-align: right;"><span class="important">*</span>密码:</td>
				<td style="text-align: left;" ><input type="password" id="password" name="password" /></td>
				<td><span id="psSp" class="action_po_top">密码不能为空</span></td>
			</tr>
			<tr>
				<td  style="text-align: right;" ><span class="important">*</span>确认密码:</td>
				<td style="text-align: left;" ><input type="password" id="repassword" name="repassword">
				</td>
				<td><span id="repsSp" class="action_po_top">两次输入的密码不一致</span></td>
			</tr>
			<tr>
				<td  style="text-align: right;" ><span class="important">*</span>性别:</td>
				<td style="text-align: left;" ><select name="sex">
						<c:if test="${user.sex==0}">
							<option value="1">男</option>
							<option value="0" selected="selected">女</option>
						</c:if>
						<c:if test="${user.sex==1}">
							<option value="1" selected="selected">男</option>
							<option value="0">女</option>
						</c:if>
				</select>
				</td>
				<td></td>
			</tr>
			<tr>
				<td  style="text-align: right;"> <span class="important">*</span>手机:</td>
				<td style="text-align: left;" ><input style="width: 200px;" type="text" id="mobile"
					value="${user.mobile}" name="mobile"></td>
				<td><span id="mSp" class="action_po_top">手机格式错误</span></td>
			</tr>
			<tr>
				<td  style="text-align: right;"><span class="important">*</span>邮件:</td>
				<td style="text-align: left;" ><input style="width: 200px;" type="text" id="email"
					value="${user.email}" name="email"></td>
				<td><span id="emSp" class="action_po_top">邮箱格式错误</span></td>
			</tr>
			<tr>
				<td  style="text-align: right;" ><span class="important">*</span>中文名字:</td>
				<td style="text-align: left;" ><input style="width: 200px;" type="text" id="chnName"
					value="${user.chnName}" name="chnName"></td>
				<td><span id="cnSp" class="action_po_top">中文名字不能为空</span></td>
			</tr>
			<tr>
				<td colspan="3" align="center"><input type="submit" value="提交" /><input
					type="reset" value="重置" /></td>
			</tr>
		</table>
	</form>
</div>