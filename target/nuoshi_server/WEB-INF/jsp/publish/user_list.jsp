<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="/js/common/common.js"></script>
</head>
<div style="width: 100%;text-align: left;">当前位置：发布&gt;&gt;<b>帐号管理</b></div>
<form id="listForm" action="/publish/userlist" method="post">
<div class="mag" style="border:1px solid #CAD9EA">
<label class="def">用户名或ID：</label><input name="userNameOrId" id="userNameOrId"  value="${userNameOrId }"  type="text" style="width: 170px;"/>
<label class="def">角色：</label>
	<select name="role" id="userRole">
		<option selected="selected" value="0">选择角色</option>
		<option value="1">普通用户</option>
		<option value="2">经纪人</option>
	</select>
	<input class="def" type="button" onclick="javascript:submitForm() "  value="搜索" style="width: 60px;">
	<input class="def" id="addBtn" type="button" value="添加用户" style="width: 110px;">
</div>
<div class="mag">
<div class="todo">
	<table>
	<thead>
	<tr>
	<c:if test="${empty publisher}"><th>列表为空</th></c:if>
	<c:if test="${not empty publisher}"> 
	<th style=""><input type="checkbox" id="selectAll" name="selectAll" />全选</th>
	<th style="">用户ID</th>
	<th style="">用户名</th>
	<th style="">用户帐号</th>
	<th style="">用户角色</th>
	<th style="">城市</th>
	<th style="">编辑</th>
	</c:if>
	</tr>
	</thead>
	<tbody>
		<c:forEach items="${publisher}" var="user">
			<tr>
				<td style=""><input type="checkbox" name="idChk" value="${user.id}" /></td>
				<td style="">${user.userId}</td>
				<td style="">${user.userName}</td>
				<td style="">${user.loginName}</td>
				<td style="">
					<c:choose>
						<c:when test="${user.role==1}">
							普通用户
						</c:when>
						<c:when test="${user.role==2}">
							经纪人
						</c:when>
						<c:otherwise>
							未知
						</c:otherwise>
					</c:choose>
				</td>
				<td style="">${cityMap.get(user.cityId)}</td>
				<td style=""><a href="javascript:deleteUser('${user.id}')">删除</a></td>
			</tr>
		</c:forEach> 
	</tbody> 
	</table>
	</div>
	</div>
	<c:if test="${not empty publisher}"> 
	<div class="mag">
		<input class="def" type="button" id="delBtn" value="批量删除" onclick="javascript:deleteMore();" style="width: 80px;"/>
	</div>
	</c:if>
	<div class="page_and_btn">
			<jsp:include page="/WEB-INF/snippets/page.jsp" />
		</div>
	</form>
	<div id="divAdd">
		<p>
			<label>ID：</label><input type="text" id="userIdAdd" name="userId" />
		</p>
	</div>
	
	<div id="question" class="cfm" align="center"> 
        <div style="font-weight: bold;font-size: 13px;">您确定要删除吗?</div> 
        <input type="button" id="yes" value="是" /> 
        <input type="button" id="no" value="否" /> 
	</div> 
	
<script type="text/javascript">
$("#divAdd").hide();
$(function() {
	//为控件绑定事件 
	$("#addBtn").bind("click",add);
	$("#selectAll").bind("click",selAll);
	$("#c_no").bind("click", function() {$.unblockUI();return false;});
	//初始化弹出窗口
	$("#divAdd").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 180,width : 500,title : "添加用户",modal : true,resizable : false,buttons : {"添加" : userAdd,"取消" : clearAddDiv}});
	clearCheckBox();
	$("#userRole").val('${role}');
});

//提交搜索内容
	function submitForm() {
		String.prototype.Trim = function() {
			return this.replace(/(^\s*)|(\s*$)/g, "");
		};
		$("#listForm").submit();
	}

	// 全选信息
	function selAll() {
		if ($("#selectAll").attr("checked")) {
			$("input[name=idChk]").attr("checked", true);
		} else {
			$("input[name=idChk]").attr("checked", false);
		}
	}

	// 显示增加用户窗口
	function clearAddDiv() {
		$("*").stop();
		$("#divAdd").dialog("close");
	}
	function add() {
		$("*").stop();
		$("#divAdd").dialog("close");
		$("#divAdd").dialog("option", "position", "center");
		$("#divAdd").dialog("open");
	}

	// ajax增加用户
	function userAdd() {
		String.prototype.Trim = function() {
			return this.replace(/(^\s*)|(\s*$)/g, "");
		};
		var patrn = /^[0-9]{0,20}$/;
		var userId = $("#userIdAdd").val().Trim();
		var flag = 0;
		if (userId == null || userId == "") {
			userId=0;
		} else if (!patrn.exec(userId)) {
			alert("ID必须为数字！", 2000);
			flag++;
		}
		
		if (flag == 0) {
			var resultFlag = true;
			$.ajax({
				type : "GET",
				async : false,
				url : "/publish/add",
				dataType : "json",
				data : {
					"userId" : userId
				},
				success : function(data) {
					resultFlag = data.result.result;
					if (resultFlag) {
						clearAddDiv();
						$.unblockUI();
						window.location.href="/publish/userlist"; 
					}else{
						alert(data.result.message);
					}
				}
			});
			//});
		}
	}


	//提示弹出层，msg要显示的消息，sec显示停留的时间
	function blockAlert(msg, sec) {
		$.blockUI({
			css : {
				border : "none",
				padding : "15px",
				backgroundColor : "#000",
				'-webkit-border-radius' : "10px",
				'-moz-border-radius' : "10px",
				opacity : .6,
				color : "#fff"
			},
			message : msg
		});
		setTimeout($.unblockUI, sec);
	}


	//删除用户，显示删除操作结果，刷新当前页面
	
	function deleteUser(id) {
		if (confirm("确认删除吗？")) {
			var resultFlag = true;
			$.ajax({
				type : "post",
				async : false,
				url : "/publish/delete",
				dataType : "json",
				data : {
					"userIds" : id
				},
				success : function(data) {
					resultFlag = data.result.result;
					if (resultFlag) {
						window.location.href = "/publish/userlist";
					} else {
						alert(data.result.message);
					}
				}
			});
		}
	}

	//清空checkbox选中状态，全部置为未被选中状态
	function clearCheckBox() {
		$("input[name=selectAll]").attr("checked", false);
		$("input[name=idChk]").attr("checked", false);
	}

	//删除多个用户
	function deleteMore() {
		var ids = $("input[name=idChk]:checked");
		if (ids.size() == 0) {
			alert("请选择要删除的用户。");
		} else {
			ids = "";
			$("input[name=idChk]:checked").each(function() {
				ids += $(this).val() + ",";
			});
			deleteUser(ids);
		}
	}
</script>
</html>