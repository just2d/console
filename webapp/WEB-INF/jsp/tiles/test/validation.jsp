<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<form id="myform" style="padding:50px 0 0 100px;">
	<table cellspacing="0">
		<tbody>
			<tr><th><p style="width:100px;">*用户名:</p></th><td width="100%">
				<input validation="required, help (UserNameHelp), regex(onlyLetter), ajax(ajaxUser) " type="text" />
				<p class="pre">&lt;input validation="required, help (UserNameHelp), regex(onlyLetter), ajax(ajaxUser) " type="text" /&gt;</p>
				<p class="gray">注：baby,javascript,firefox,hello 被占用(参见<a href="ajaxUser.txt">ajaxUser.txt</a>)</p>
			</td></tr>
			<tr><th>*邮箱:</th><td><input validation="required, help(emailHelp), regex(email)" type="text" />
			<p class="pre">&lt;input validation="required, help(emailHelp), regex(email)" type="text" /&gt;</p>
			</td></tr>
			<tr><th>手机:</th><td><input validation="regex(mobile)" type="text" />
			<p class="pre">&lt;input validation="required, regex(mobile)" type="text" /&gt;</p></td></tr>
			<tr><th>*密码:</th><td><input validation="required, length(6-14)" id="pwd" type="password" />
			<p class="pre">&lt;input validation="required, length(6-14)" id="pwd" type="password" /&gt;</p>
			</td></tr>
			<tr><th>*重复密码:</th><td><input validation="required, repeat(pwdRepeat)" type="password" />
			<p class="pre">&lt;input validation="required, repeat(pwdRepeat)" type="password" /&gt;</p></td></tr>
			<tr><th>*序列号:</th><td><input validation="required, regex(onlyNumber), length(12)" type="text" />
			<p class="pre">&lt;input validation="required, regex(onlyNumber), length(12)" type="text" /&gt;</p></td></tr>
			<tr><th>中文姓名:</th><td><input validation="regex(chineseCharaters), length(2-4)" type="text" />
			<p class="pre">&lt;input validation="regex(chineseCharaters), length(2,4)" type="text" /&gt;</p></td></tr>
			<tr><td colspan="2"><input type="submit" value="提交" class="button" /></td></tr>
		</tbody>
	</table>
	<p class="pre">
/** js 代码部分  **/


		// 表单验证初始化
		$('#myform').validateable({
				invalidClass:"error",		// 无效 class
				validClass:"pass",			// 有效 class
		});
		
		//扩展添加自定义规则
		$.validationEngine.addRules({
			ajaxUser:{	// ajax 规则
				url:'ajaxUser.txt',
				dataType:'text',
				result: function(r) {
					return !(r.indexOf(this.value)>=0);
				},
				errorText: function(r) {
					return "* {0}已经被占用"; // String
				},
				successText: "* 恭喜，{0}可以使用",
				loadText:"* 验证中..."
			},
			pwdRepeat:{	// repeat 规则
				repeat:'#pwd',
				alertText:"* 两次输入的密码不一致" 
			},
			// help 规则
			UserNameHelp:{ helpText:"* 请输入您唯一的用户名，只能输入英文" },
			emailHelp:{ helpText:"* 请输入您常用的邮箱地址" }
			
		});
	</p>
</form>
<script type="text/javascript">
	// 添加规则
	$.validationEngine.addRules({
		// ajax 规则
		ajaxUser:{
			url:'ajaxUser.txt',
			dataType:'text',
			result: function(r) {
				return !(r.indexOf(this.value)>=0);
			},
			errorText: function(r) {
				return "* {0}已经被占用"; // String
			},
			successText: "* 恭喜，{0}可以使用",
			loadText:"* 验证中..."
		},
		// repeat 规则
		pwdRepeat:{
			repeat:'#pwd',
			alertText:"* 两次输入的密码不一致" 
		},
		// help 规则
		UserNameHelp:{ helpText:"* 请输入您唯一的用户名，只能输入英文" },
		emailHelp:{ helpText:"* 请输入您常用的邮箱地址" }
	});
	
	// 表单验证初始化
	$('#myform').validateable({
		invalidClass:"error", // 无效 class
		validClass:"pass", // 有效 class
		
	});
</script>