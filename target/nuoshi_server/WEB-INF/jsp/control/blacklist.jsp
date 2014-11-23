<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="/js/common/common.js"></script>
</head>
<div style="width: 100%;text-align: left;">当前位置：信息质量控制&gt;&gt;<b>黑名单管理</b></div>
<form id="blacklistForm" action="/control/blacklist/search" method="get">
<div class="mag" style="border:1px solid #CAD9EA">
<label class="def">帐号：</label><input name="searchLoginName" id="searchLoginName"  value="${searchLoginName }"  type="text" style="width: 170px;"/>
<label class="def">ID：</label><input name="searchId" id="searchId"  value="${searchId }"  type="text" style="width: 170px;"/>
	<input class="def" type="button" onclick="javascript:submitForm() "  value="搜索" style="width: 60px;">
	<input class="def" type="button" onclick="javascript:resetSearch() " value="重置" style="width: 60px;"> 
	<input class="def" id="addBtn" type="button" value="添加黑名单" style="width: 110px;">
</div>
<div class="mag">
<div class="todo">
	<table>
	<thead>
	<tr>
	<c:if test="${fn:length(blackList)==0}"><th>黑名单列表为空</th></c:if>
	<c:if test="${fn:length(blackList)!=0}"> 
	<th style="width: 1%;"><input type="checkbox" id="selectAll" name="selectAll" /></th>
	<th style="width: 5%;">ID</th><th style="width: 10%;">用户</th><th style="width: 6%;">用户ID</th><th style="width: 10%;">用户名</th><th style="width: 6%;">用户类别</th>
	<th style="width: 11%;">原因</th><th style="width:  11%">说明</th><th style="width: 4%;">添加时间</th><th style="width:6%;">最后修改人</th>
	<th style="width: 4%;">更新时间</th><th style="width: 6%;">列表类别</th><th style="width: 4%;">状态</th><th style="width: 8%;" colspan="2">编辑</th>
	</c:if>
	</tr>
	</thead>
	<tbody>
		<c:forEach items="${blackList}" var="black">
		<tr>
		<td style="width: 1%;"><input type="checkbox" name="idChk" value="${black.id}" /></td>
		<td style="width: 5%;">${black.id}</td><td style="width: 10%;">${black.loginName}</td><td style="width: 6%;">${black.blackUserId}</td>
		<td style="width: 10%;">
			<c:choose>
				<c:when test="${not empty black.blackUserName}">
					${black.blackUserName}
				</c:when>
				<c:otherwise>
					<font color="gray">无用户名</font>
				</c:otherwise>
			</c:choose>
			
		</td>
		<td style="width: 6%;">
			<c:choose>
				<c:when test="${black.userType==1}">
					普通用户
				</c:when>
				<c:when test="${black.userType==2}">
					经纪人
				</c:when>
				<c:otherwise>
					未注册
				</c:otherwise>
			</c:choose>
		</td>
		<td style="width: 11%;" title="${black.reason}">
		<c:choose>
			<c:when test="${fn:length(black.reason) >=13}">${fn:substring(black.reason, 0, 13)}...</c:when>
			<c:otherwise>${black.reason}&nbsp;&nbsp;</c:otherwise>
		</c:choose>
		</td>
		<td style="width: 11%;" title="${black.comments}">
		<c:choose>
			<c:when test="${fn:length(black.comments) >=13}">${fn:substring(black.comments, 0, 13)}...</c:when>
			<c:otherwise>${black.comments}&nbsp;&nbsp;</c:otherwise>
		</c:choose>
		</td>
		<td style="width: 4%;">${black.entryDate}</td>
		<td style="width: 6%;">${black.lastEntryName}</td>
		<td style="width: 4%;">${black.updateDate}</td>
		<td style="width: 6%;">
			<c:choose>
				<c:when test="${black.listType==101}">
					经纪人
				</c:when>
				<c:when test="${black.listType==102}">
					问答
				</c:when>
				<c:otherwise>
					未知
				</c:otherwise>
			</c:choose>
		</td>
		<td style="width: 4%;">
			<c:choose>
				<c:when test="${black.status==1}">
					<font color="red">有效</font>
				</c:when>
				<c:when test="${black.status==0}">
					无效
				</c:when>
				<c:otherwise>
					未知
				</c:otherwise>
			</c:choose>
		</td>
		<%-- <td style="width: 4%;"><a href="javascript:modify('${black.id}','${black.loginName}','${black.reason}','${black.comments}') ">修改</a></td>--%>
		<td style="width: 4%;"><a href="javascript:changeStatus('${black.id}','${black.status==1?0:1}','${black.blackUserId}','${black.loginName}') ">修改</a></td>
		<td style="width: 4%;"><a href="javascript:deleteBlack('${black.id}')">删除</a></td>
		</tr>
		</c:forEach> 
	</tbody> 
	</table>
	</div>
	</div>
	<c:if test="${fn:length(blackList)!=0}"> 
	<div class="mag">
		<input class="def" type="button" id="delBtn" value="批量删除"  style="width: 80px;"/>
	</div>
	</c:if>
	<div class="page_and_btn">
			<jsp:include page="/WEB-INF/snippets/page.jsp" />
		</div>
	</form>
	<div id="divAdd">
		<p>
			<label>登录名：</label><input type="text" id="loginNameAdd" name="loginNameAdd" />
			或&nbsp;&nbsp;
			<label>ID：</label><input type="text" id="blackUserIdAdd" name="blackUserIdAdd" />
		</p><p >
			<label>名单类型：</label>
			<select class="cs" id="listTypeAdd" name="listTypeAdd" style="width: 145px;">
				<option value="102" selected="selected">问答</option>
				<option value="101">经纪人</option>
			</select>
			<label>名单状态：</label>
			<select class="cs" id="statusAdd" name="statusAdd" style="width: 145px;">
				<option value="1" selected="selected">有效</option>
				<option value="0">无效</option>
			</select>
		</p><p>
			<label>原&nbsp;&nbsp;&nbsp;因：</label><input type="text" id="reasonAdd" name="reasonAdd" style="width: 375px;" />
		</p><p >
			<label>说&nbsp;&nbsp;&nbsp;明：</label><textarea name="commentAdd" id="commentAdd" cols="50" rows="5"></textarea>
		</p>
	</div>
	
	<!-- <div id="divEdit">
		<input type="hidden" id="idEdit" name="idEdit" />
		<label class="lbl">手机号:</label><input type="text" id="mobileEdit" name="mobileEdit" style="margin-left: -240px;" disabled="disabled"/>
		<label class="lbl">登录名:</label><input type="text" id="madeby" name="madeby" style="margin-left: -240px;" disabled="disabled"/>
		<label class="lbl">原&nbsp;&nbsp;&nbsp;因:</label><input type="text" id="reason" name="reason" style="margin-left: -240px;" disabled="disabled"/>
	<p><label class="lbl">说&nbsp;&nbsp;&nbsp;明:</label><textarea name="comment" id="comment" cols="50" rows="5"></textarea></p>
	</div> -->
	
	<div id="question" class="cfm" align="center"> 
        <div style="font-weight: bold;font-size: 13px;">您确定要删除吗?</div> 
        <input type="button" id="yes" value="是" /> 
        <input type="button" id="no" value="否" /> 
	</div> 
	
	<div id="confirm" class="cfm" align="center"> 
        <div style="font-weight: bold;font-size: 13px;">您确定要将此号码列为黑名单吗?</div> 
        <input type="button" id="c_yes" value="是" /> 
        <input type="button" id="c_no" value="否" /> 
	</div> 
	
<script type="text/javascript">
$("#divAdd").hide();
$(function() {
	//反添查询条件
	var type = "${search.userType}";
	var reason = "${search.reason}";
	var comment = "${search.comments}";
	$("#type").val(type);
	$("#reason").val(reason);
	if(comment!=null&&comment!=""){$("#comment").val(comment);}
	//为控件绑定事件 
	$("#addBtn").bind("click",add);
	$("#selectAll").bind("click",selAll);
	$("#delBtn").bind("click",deleteMore);
	$("#no").bind("click", function() {$.unblockUI();return false;});
	$("#c_no").bind("click", function() {$.unblockUI();return false;});
	//初始化弹出窗口
	$("#divAdd").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 380,width : 500,title : "添加黑名单",modal : true,resizable : false,buttons : {"添加" : blackAdd,"取消" : clearAddDiv}});
	// $("#divEdit").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 380,width : 500,title : "修改黑名单",modal : true,resizable : false,buttons : {"修改" : blackEdit,"取消" : clearEditDiv}}); 
	//checkbox状态重置，未被选中
	clearCheckBox();
});


//重置搜素
function resetSearch(){
	$("#searchLoginName" ).val("");
	$("#searchId" ).val("");
}
//提交搜索内容

	function submitForm() {
		String.prototype.Trim = function() {
			return this.replace(/(^\s*)|(\s*$)/g, "");
		};
		var patrn = /^[0-9]{0,20}$/;
		var flag = 0;
		var searchId = $("#searchId").val().Trim();
		if (searchId != "" && !patrn.exec(searchId)) {
			blockAlert("ID必须为数字！", 2000);
			flag++;
		}
		if (flag == 0) {
			$("#blacklistForm").submit();
		}

	}

	// 全选信息
	function selAll() {
		if ($("#selectAll").attr("checked")) {
			$("input[name=idChk]").attr("checked", true);
		} else {
			$("input[name=idChk]").attr("checked", false);
		}
	}

	// 显示增加黑名单窗口
	function add() {
		$("*").stop();
		$("#divAdd").dialog("close");
		$("#divAdd").dialog("option", "position", "center");
		$("#divAdd").dialog("open");
	}

	// ajax增加黑名单，增加完毕显示增加操作结果，刷新当前页面
	function blackAdd() {
		String.prototype.Trim = function() {
			return this.replace(/(^\s*)|(\s*$)/g, "");
		};
		var patrn = /^[0-9]{0,20}$/;
		var loginName = $("#loginNameAdd").val().Trim();
		var reason = $("#reasonAdd").val().Trim();
		var comments = $("#commentAdd").val();
		var userId = $("#blackUserIdAdd").val().Trim();
		var listType = $("#listTypeAdd").val();
		var status = $("#statusAdd").val();
		var flag = 0;
		//各种验证k
		var reasonLength = getStrLength(reason, 1);
		var commentsLength = getStrLength(comments, 1);
		if (userId == null || userId == "") {
			userId = 0;
		} else if (!patrn.exec(userId)) {
			blockAlert("ID必须为数字！", 2000);
			flag++;
		}
		if (loginName == "" && userId == "") {
			blockAlert("登录名或者用户ID至少输入一项！", 2000);
			flag++;
		} else if (reasonLength > 25) {
			blockAlert("原因不能超过25个字！", 2000);
			flag++;
		} else if (commentsLength > 150) {
			blockAlert("说明不能超过150个字！", 2000);
			flag++;
		}
		if (flag == 0) {
			var resultFlag = true;
			//$.blockUI({message : $("#confirm"),css : {width : "550px"}});
			//$("#c_yes").click(function() {
			$.ajax({
				type : "GET",
				async : false,
				url : "/control/blacklist/add",
				dataType : "json",
				data : {
					"loginName" : loginName,
					"reason" : reason,
					"comments" : comments,
					"blackUserId" : userId,
					"listType" : listType,
					"status" : status
				},
				success : function(data) {
					blockAlert(data.message, 2000);
					resultFlag = data.result;
				}
			});
			if (resultFlag) {
				clearAddDiv();
				$.unblockUI();
				window.location.reload();
			}
			//});
		}
	}

	//清空增加黑名单页面
	function clearAddDiv() {
		$("#loginNameAdd").val("");
		$("#reasonAdd").val("");
		$("#commentAdd").val("");
		$("#blackUserIdAdd").val("");
		$("#divAdd").dialog("close");
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

	//弹出修改黑名单窗口
	function modify(id, mobile, reason, comment) {
		$("#idEdit").val(id);
		$("#mobileEdit").val(mobile);
		$("#reasonEdit").val(reason);
		$("#commentEdit").val(comment);
		$("*").stop();
		$("#divEdit").dialog("close");
		$("#divEdit").dialog("option", "position", "center");
		$("#divEdit").dialog("open");
	}
	//修改状态
	function changeStatus(id, status,userId,loginName) {
		$.ajax({
			type : "GET",
			async : false,
			url : "/control/blacklist/changeStatus",
			dataType : "json",
			data : {
				"id" : id,
				"status" : status,
				"blackUserId":userId,
				"loginName":loginName
			},
			success : function(data) {
				blockAlert(data.message, 2000);

			}
		});
		window.location.reload();
	}

	//ajax修改黑名单，显示修改结果，刷新当前页面
	function blackEdit() {
		String.prototype.Trim = function() {
			return this.replace(/(^\s*)|(\s*$)/g, "");
		};
		var id = $("#idEdit").val();
		var reason = $("#reasonEdit").val();
		var comment = $("#commentEdit").val().Trim();
		var flag = 0;
		if (reason == "-1") {
			blockAlert("请选择原因", 2000);
			flag++;
		} else if (comment == "") {
			blockAlert("请输入说明", 2000);
			flag++;
		}
		if (flag == 0) {
			$.ajax({
				type : "GET",
				async : false,
				url : "/control/blacklist/edit",
				dataType : "json",
				data : {
					"id" : id,
					"reason" : reason,
					"comment" : comment
				},
				success : function(data) {
					blockAlert(data.message, 2000);
				}
			});
			clearEditDiv();
			window.location.reload();
		}
	}

	//清空修改弹出窗口
	function clearEditDiv() {
		$("#divEdit").dialog("close");
	}

	//删除黑名单，显示删除操作结果，刷新当前页面
	function deleteBlack(id) {
		$.blockUI({
			message : $("#question"),
			css : {
				width : "275px"
			}
		});
		$("#yes").click(function() {
			$.ajax({
				type : "get",
				async : false,
				url : "/control/blacklist/delete/" + id,
				dataType : "json",
				cache : false,
				success : function(data) {
					blockAlert(data.message, 2000);
				}
			});
			$.unblockUI();
			clearCheckBox();
			window.location.reload();
		});
	}

	//清空checkbox选中状态，全部置为未被选中状态
	function clearCheckBox() {
		$("input[name=selectAll]").attr("checked", false);
		$("input[name=idChk]").attr("checked", false);
	}

	//删除多个黑名单
	function deleteMore() {
		var $ids = $("input[name=idChk]:checked");
		if ($ids.size() == 0) {
			blockAlert("请选择要删除的黑名单", 2000);
		} else {
			var content = $ids.val();
			var ids = "";
			$("input[name=idChk]:checked").each(function() {
				ids += $(this).val() + ",";
			});
			deleteBlack(ids);
		}
	}
</script>
</html>