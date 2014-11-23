<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>淘房网后台管理系统</title>
<link rel="stylesheet" href="/css/login.css" type="text/css" />
<script type="text/javascript" src="/js/common/jquery.js"></script>
<script type="text/javascript">
		var errInfo = "${errInfo}";
		$(document).ready(function(){
			if(errInfo!=""){
				if(errInfo.indexOf("验证码")>-1){
					$("#codeerr").show();
					$("#codeerr").html(errInfo);
					$("#code").focus();
				}else{
					$("#nameerr").show();
					$("#nameerr").html(errInfo);
				}
			}
			$("#loginname").focus();
		});
	
		function genTimestamp(){
			var time = new Date();
			return time.getTime();
		}
	
		function resetErr(){
			$("#nameerr").hide();
			$("#nameerr").html("");
			$("#pwderr").hide();
			$("#pwderr").html("");
			$("#codeerr").hide();
			$("#codeerr").html("");
		}
		
		function check(){
			resetErr();
			if($("#loginname").val()==""){
				$("#nameerr").show();
				$("#nameerr").html("用户名不得为空！");
				$("#loginname").focus();
				return false;
			}
			if($("#password").val()==""){
				$("#pwderr").show();
				$("#pwderr").html("密码不得为空！");
				$("#password").focus();
				return false;
			}
			if($("#code").val()==""){
				$("#codeerr").show();
				$("#codeerr").html("验证码不得为空！");
				$("#code").focus();
				return false;
			}
			return true;
		}
	</script>
</head>
<body>
<div class="container">
	<div class="title">诺食管理系统</div>
	<div class="box">
		<form name="loginForm" action="login.html" method="post" onsubmit="return check();" >
			<div style="height:30px;line-height:30px;"><span class="lable">用户名</span><input type="text" name="loginname" id="loginname" class="ipt" />&nbsp;<span id="nameerr" class="errInfo"></span></div>
			<div style="height:30px;line-height:30px;margin-top:19px;"><span class="lable">密　码</span><input type="password" name="password" class="ipt" id="password"  />&nbsp;<span id="pwderr" class="errInfo"></span></div>
			<div style="height:30px;line-height:30px;margin-top:19px;"><span class="lable">验证码</span><input type="text" name="code" id="code" class="ipt yzm"/>&nbsp;<img src="/CheckCode.svl" style="" onclick="this.src='/CheckCode.svl?d='+new Date()*1"/>&nbsp;<span id="codeerr" class="errInfo"></span></div>
			<div  style="padding-left:60px;padding-top:20;margin-top:19px;">
				<input type="submit" value="登录" class="btn"/>
				<input type="reset" value="重置" class="btn"/>
			</div>
		</form>
	</div>
</div>
</body>
</html>