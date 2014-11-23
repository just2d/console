<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="/css/common/colorbox.css"/>
<link type="image/gif" href="/images/colorbox/loading.gif"/>
<title>管理系统后台</title>
<script type="text/javascript">
$(function(){
	var h = $(window).height();
	$("#divCheck").dialog({
		bgiframe : true,
		autoOpen : false,
		height : 300,
		width : 500, 
		modal : true,
		resizable : false,
		buttons:{"确定":saveButton}
	});
});
function saveButton(){
	collectReason();
	if($.trim($("#submitId").val())=='' || ($.trim($("#reasons").val())=='' && $("#submitResult").val()=='-2')){
		alert("没有打回原因,不保存.");
	}else{
		submitIt();
	}
}

function jumpToClick(obj){
	$(obj).siblings("a").click();
}

function reauditIt(self,result){
	if($(self).css("display")=="none"){
		return;
	}
	$(self).hide();
	$(self).siblings("font").hide();
	var obj = $(self).siblings("img");
	// 置状态
	$("#submitId").val($(obj).attr("id"));
	$("#submitResult").val(result);
	if(result=='-2'){// 变成违规
		$("#divCheck").dialog({autoOpen:false});
		$("#divCheck").dialog("option", "position", "center");
		$("#divCheck").html($("#toShowDiv").html());
		$("#divCheck").dialog("open");
	}else{// 变成通过
		submitIt();
	}
}
// 收集原因
function collectReason(){
	var checkBoxReason = "";
	$("[name='checkBox']").each(function(){
		if(this.checked){
			checkBoxReason += $(this).attr("title");
			checkBoxReason += "<br/>";
		}
	});
	$("#reasons").val(checkBoxReason + $("[name='reasons_txt']:eq(1)").val());
}
// ajax提交
function submitIt(){
	var submitId = $("#submitId").val();
	var submitResult = $("#submitResult").val();
	var reason = $("#reasons").val();
	var house_type = '${house_type}';
	var auditStep = '${auditStep}';
	$.ajax({
		type : "post",
		async : false,
		url : "/auditHistory/reaudit/photo/" + submitId + "/" + submitResult,
		dataType : "json",
		data : {"reason" : reason,"house_type":house_type,"auditStep":auditStep},
		success : function(data) {
			if (data.info =="error") {
				alert("错误发生!");
				return;
			}
			if (data.info == "success") {
				$("#submitId").val("");
				$("#reasons").val("");
				$("#submitResult").val("");
				$("[name='reasons_txt']").val("");
				$("#divCheck").dialog("close");
				$.blockUI({ fadeIn: 200, fadeOut: 300, timeout: 500,message:"<h3>修改成功!</h3>" });
			}
		}
	});
}
</script>
</head>
<body>
<c:if test="${not empty list }">
<table id="popup" border="1">
	<tr>
	<c:forEach items="${list }" var="item" varStatus="status">
		<c:set var="fail" value="${item.auditResult=='-2'?'true':'false'}"></c:set>
		<c:if test="${fail }">
			<td><span style="background-color: red;">违规</span>
 				<img width="250px" height="200px" id="${item.photoId }" name="-1" title="${item.extra }" src="${applicationScope.headPrefix}/${item.photoUrl}" onclick="jumpToClick(this);"/>
 				<br/>
 				<c:if test="${not empty item.reResult }"><font color="green">(已复审成"通过")</font><a href="javascript:void(0);" onclick="reauditIt(this,'-2');">打回</a></c:if>
 				<c:if test="${empty item.reResult }"><a href="javascript:void(0);" onclick="reauditIt(this,'-1');">通过</a></c:if>
			</td>
		</c:if>
		<c:if test="${!fail }">
			<td>
 				<img width="250px" height="200px" id="${item.photoId }" name="-2" title="${item.extra }" src="${applicationScope.headPrefix}/${item.photoUrl}" onclick="jumpToClick(this);"/>
 				<br/>
 				<c:if test="${not empty item.reResult }"><font color="red">(已复审成"违规")</font><a href="javascript:void(0);" onclick="reauditIt(this,'-1');">通过</a></c:if>
 				<c:if test="${empty item.reResult }"><a href="javascript:void(0);" onclick="reauditIt(this,'-2');">打回</a></c:if>
			</td>
		</c:if>
		
		<c:if test="${status.count==list.size() }">
			</tr>
		</c:if>
		<c:if test="${(status.count)%4==0 && status.count!=list.size()}">
			</tr>
			<tr>
		</c:if>
	</c:forEach>
</table>

<!-- 提交ID -->
<input id="submitId" type="hidden" value=""/>
<input id="submitResult" type="hidden" value=""/>

<br/>
<br/>
<!-- <div align="center">
	<input type="button" value="提交本页"/>
</div> -->
<br/>
<br/>
</c:if>
<c:if test="${empty list }">
	暂无图片
</c:if>
</body>
<!-- 提交原因 -->
<input id="reasons" type="hidden" value=""/>
</html>




<div id="divCheck" style="display: none;">
</div>
<div id="toShowDiv" style="display: none;">
<table>
	<tr>
		<td style="text-align: left" valign="top">违规原因:&nbsp;</td>
		<td style="text-align: left" valign="top"><c:forEach items="${photoReasons}" var="list" varStatus="vs">
				<label><input name="checkBox" type="checkbox" title="${list.reason}" onclick="saveButton();">${list.reason}</label><br/>
			</c:forEach></td>
	</tr>
	<tr>
		<td valign="top">其它原因:&nbsp;</td>
		<td style="text-align: left"><textarea class="rejectTextArea" name="reasons_txt" cols="40" rows="3"></textarea></td>
	</tr>
</table>
</div>