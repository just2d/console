<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.taofang.com/tld/func" prefix="func"%>
<div style="width: 100%;text-align: left;">当前位置：发送站内信</div>

<form action="/sendmsg/send" method="post" id="msgsendForm" style="margin-top:0px;margin-top:0px;">
	
	<div class="todo">
		<table id="msgTable" class="c" style="text-align:center">
			<tr><td>站内信标题：</td><td><input id="title" name="title" style="width:600px"></td></tr>
			<tr><td>站内信内容：</td><td><input id="content" name="content"  style="width:600px"></td></tr>
			<tr><td>经纪人ID：</td><td><textarea id="agentIds" name="agentIds" rows="10" cols="80"></textarea></td></tr>
			<tr><td>&nbsp;</td><td><input type="button" value="发送" onclick="send();"></td></tr>
		</table>
	</div>
</form>
<script type="text/javascript">
<!--
function send(){
	if($("#title").val()==''){
		alert('请输入标题');
		return;
	}
	if($("#content").val()==''){
		alert('请输入内容');
		return;
	}
	if($("#agentIds").val()==''){
		alert('请输入经纪人ID');
		return;
	}
	
	$form = $("#msgsendForm");
	$.ajax({
		url: '/sendmsg/send',
		type:"post",
		data: $form.serialize(),
		dataType: "json",
		success:function(data){
			if(data.result == 'success'){
				alert("成功发送");
			}else if(data.result == 'fail'){
				alert(data.error);
			}
		}
		
	});
}
//-->
</script>			 