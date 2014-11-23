<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<script type="text/javascript">	
	$(function() {
		/* $(":input").bind("keyup", function(event) {
			if (event.keyCode == "13") {
				search();
			}
		}); */
		
		var h = $(window).height();
		// 初始化窗口
		$("#replyDiv").dialog({show : "",close: function(event, ui) { closeDialog(); },bgiframe : true,autoOpen : false,height : h * 0.5,width : '50%',title : "反馈问题详情",modal : true,resizable : false,buttons : {"确认" : enterBtn}});
	});
	
	// 按下确认键
	function enterBtn(){
		var id = $("#cur_id").val();
		var value = $.trim($("#reply").val());
		if(value==""){
			alert("没有内容输入!将不回复此信息.");
			$("#replyDiv").dialog("close");
			return;
		}else{
			$.ajax({
				type : "post",
				async : false,
				url : "/feedBack/changeReply",
				dataType : "json",
				data : {
					"id" : id,
					"value":value
				},
				success : function(data) {
					if(data.error){
						alert("提交答复失败!是否输入字符过长?");
						return;
					}
					$("#"+id).attr("title",value);
					if(value.length>12){
						value = value.substring(0,12)+"...";
					}
					$("#"+id).html(value);
					$("#"+id).parent().animate({backgroundColor: "#f00"}, 800);
					$("#"+id).parent().animate({backgroundColor: "#fff"}, 800);
					setTimeout("$('#'+"+id+").parent().removeAttr('style');",2000);
				}
			});
		}
		$("#feed").text("");
		$("#reply").val("");
		$("#replyDiv").dialog("close");
	}
	
	// 按下关闭键
	function closeDialog(){
	}
	
	// 滑出窗口
	function slideOut(id){
		$("#cur_id").val(id);
		$.ajax({
			type : "post",
			async : false,
			url : "/feedBack/getOne",
			dataType : "json",
			data : {"id" : id},
			success : function(data) {
				if(data.error){
					alert("获取数据失败!");
					return;
				}
				var res = JSON.parse(data.res);
				$("#feed").text(res.content);
				$("#reply").val(res.reply);
				$("#replyDiv").dialog("option", "position", "center");
				$("#replyDiv").dialog("open");
			}
		});
	}
	
	// 搜索
	function search() {
		var ahForm = document.getElementById("form");
		var pageNo = document.getElementById("page.pageNo");
		pageNo.value = 1;
		if (ahForm != null) {
			$("#mobile").val($.trim($("#mobile").val()));
			ahForm.submit();
		}
	}
	
	// 删除
	function delOne(id){
		if(confirm("确定删除?")){
			$.ajax({
				type : "post",
				async : false,
				url : "/feedBack/delOne",
				dataType : "json",
				data : {"id" : id},
				success : function(data) {
					if(data.error){
						alert("删除失败!");
						return;
					}
					window.location.href = "/feedBack/list?ranNum=" + Math.random();
				}
			});
		}
	}
</script>

<div style="width: 100%; text-align: left;">当前位置：反馈信息管理 &gt; 反馈信息列表</div>
<%-- <br />
<div id="tabs" class="tabs">
	<ul>
		<li class="${type==1?'tabs_active':'' }" style="${type==1?'':'background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%'}"><a href="/agentVerify/verifyMobileList/1">手机验证审核 </a></li>
		<li class="${type==2?'tabs_active':'' }" style="${type==2?'':'background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%'}"><a href="/agentVerify/verifyMobileList/2">手机验证审核记录</a></li>
	</ul>
</div> --%>
<hr>
<form id="form" action="/feedBack/list" method="post">
	<input type="hidden" id="cur_id"/>
	<div class="mag">
		手机号码:<input name="mobile" id="mobile" type="text" value="${mobile }" />　　　　
		 <input type="button" value="搜索" onclick="search();" />
		<div style="float: right;">回复状态:<select name="flags" onchange="search();">
			<option value="">全部</option>
			<option value="1" ${flags=='1'?'selected=selected':'' }>已回复</option>
			<option value="0" ${flags=='0'?'selected=selected':'' }>未回复</option>
		</select>　　　　　</div> 
	</div>

	<div class="mag">
		<div class="todo">
			<c:if test="${not empty err }">
				<font color="red" size="5px">请检查您的搜索条件,输入有误!</font>
			</c:if>
			<c:if test="${not empty list }">
				<table>
					<thead>
						<tr>
							<th>提问者</th>
							<th>手机</th>
							<th>邮箱</th>
							<th>反馈问题</th>
							<th>回复</th>
							<th>日期</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody class="rasent">

						<c:forEach items="${list}" var="item" varStatus="status">
							<tr style="color: black">
								<td onclick="slideOut(${item.id })">${item.author }</td>
								<td>${item.mobile }</td>
								<td onclick="slideOut(${item.id })">${item.email }</td>
								<td title="${item.content }" onclick="slideOut(${item.id })"><c:choose>
										<c:when test="${fn:length(item.content) >= 12 }">${fn:substring(item.content, 0, 12)}...</c:when>
										<c:otherwise>${item.content}</c:otherwise>
									</c:choose></td>
								<td id="${item.id }" title="${item.reply }" onclick="slideOut(${item.id })"><c:choose>
										<c:when test="${fn:length(item.reply) >= 12 }">${fn:substring(item.reply, 0, 12)}...</c:when>
										<c:otherwise>${item.reply}</c:otherwise>
									</c:choose></td>
								<td onclick="slideOut(${item.id })"><fmt:formatDate value="${item.cts }" pattern="yyyy.MM.dd HH:mm"></fmt:formatDate></td>
								<td><input type="button" value="删除" onclick="delOne(${item.id});" /></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</c:if>
			<c:if test="${empty list }">
				<h2>暂时没有数据.</h2>
			</c:if>
			<br>
		</div>
		<div class="page_and_btn" style="text-align: center;">
			<jsp:include page="/WEB-INF/snippets/page.jsp" />
		</div>
	</div>
</form>
<div id="replyDiv">
	<table>
		<tr>
			<td width="15%" valign="top">反馈问题:&nbsp;</td>
			<td style="text-align: left" valign="top"><font id="feed"></font></td>
		</tr>
		<tr>
			<td  width="15%">回复:&nbsp;</td>
			<td style="text-align: left"><textarea class="rejectTextArea" id="reply" cols="55" rows="3"></textarea></td>
		</tr>
	</table>
</div>