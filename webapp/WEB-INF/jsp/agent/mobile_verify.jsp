<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<script type="text/javascript" src="/js/common/jquery.blockUI.js"></script>
<script type="text/javascript">
<!--

	$(function() {
		$(":input").bind("keyup", function(event) {
			if (event.keyCode == "13") {
				search();
			}
		});
	});
//-->
</script>

<div style="width: 100%; text-align: left;">
	当前位置：经纪人管理 &gt; 手机验证
</div>
<br />
<div id="tabs" class="tabs">
	<ul>
		<li class="${type==1?'tabs_active':'' }" style="${type==1?'':'background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%'}"><a href="/agentVerify/verifyMobileList/1">手机验证审核 </a></li>
		<li class="${type==2?'tabs_active':'' }" style="${type==2?'':'background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%'}"><a href="/agentVerify/verifyMobileList/2">手机验证审核记录</a></li>
	</ul>
</div>

<div class="mag">
	经纪人ID：<input type="text" id="agentId" value="${agentId}" /> 手机号码:<input id="phone" type="text" value="${phone }" /> <input type="button" value="搜索" onclick="search();" />　　　　(一共有<font color="green">${page.totalCount }</font>条,${page.totalPage}页)
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
						<th width="16.67%">ID</th>
						<th width="16.67%">手机号码</th>
						<th width="16.67%">提交时间</th>
						<th width="16.67%">姓名</th>
						<th width="16.67%">操作</th>
						<th width="16.67%">提交</th>
					</tr>
				</thead>
				<tbody class="rasent">

					<c:forEach items="${list}" var="item" varStatus="status">
						<tr style="color: black" id="${item.agentId }" string="${item.agentId }_${empty item.cityId||item.cityId==''?'T.nul':item.cityId}_${empty item.status||item.status==''?'T.nul':item.status}_${empty item.mobile||item.mobile==''?'T.nul':item.mobile}_${empty item.name||item.name==''?'T.nul':item.name}_${empty item.idcardNumber||item.idcardNumber==''?'T.nul':item.idcardNumber}">
							<td>${item.agentId }</td>
							<td>${item.mobile }</td>
							<td>
								<c:if test="${not empty item.submitMobileDate }">
									<fmt:formatDate value="${item.submitMobileDate }" pattern="yyyy.MM.dd HH:mm"></fmt:formatDate>
								</c:if>
								<c:if test="${empty item.submitMobileDate }">暂无信息</c:if>
							</td>
							<td>${item.name }</td>
							<td>
								<input type="radio" name="${item.agentId }" value="1" alt="option" onclick="chooseOne(this);">通过　　　　
								<input type="radio" name="${item.agentId }" value="0" alt="option" onclick="chooseOne(this);">拒绝
							</td>
							<td><input type="button" value="提交本条" onclick="submitOne(this);" disabled="disabled"></td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</c:if>
		<c:if test="${empty list }">
			<h2>暂时没有数据.</h2>
		</c:if>
		<br>
		<div style="text-align: center;">
			<c:if test="${not empty list }">
				<input type="button" value="提交本页" onclick="submit();">
			</c:if>
			<c:if test="${empty list }">
				<input type="button" value="重新请求数据" onclick="window.location.href='/agentVerify/verifyMobileList/1';">
			</c:if>
		</div>
	</div>
</div>

<script type="text/javascript">
<!--

	
	// 选择一个
	function chooseOne(obj) {
		var TD = $(obj).parent();
		var TR = $(obj).parent().parent();
		$(TD).next().find("input").attr("disabled",false);
		if ($(obj).val()=='0') {
			$(TR).css({
				color : "red",
				background : "pink"
			});
		} else if($(obj).val()=='1'){
			$(TR).removeAttr("style");
			$(TR).css({
				color : "green",
				background : "#AFE4D0"
			});
		}
	}
	// 搜索
	function search() {
		var agentId = $("#agentId").val();
		var phone = $("#phone").val();
		window.location.href = "/agentVerify/verifyMobileList/1?agentId=" + agentId + "&phone=" + phone + "&type=" + '${type}' + "&ranNum=" + Math.random();
	}

	// 提交本条事件
	function submitOne(obj) {
		// 违规List
		var blockList = "";
		// 通过id
		var passList = "";
		var t_id1 = $(obj).parent().parent().attr("id");
		var t_id = $(obj).parent().parent().attr("string");
		var val = $("input:radio[name='" + t_id1 + "']:checked").val();
		if (val == '0') {
			blockList = "" + t_id + ",";
		} else if (val == "1") {
			passList = "" + t_id + ",";
		}
		doAjax(blockList, passList);
	}

	// 提交本页事件
	function submit() {

		// 遮罩层
		$.blockUI({
			message : "<h3>正在提交数据，请稍候……</h3>"
		});

		// 违规List
		var blockList = "";
		// 通过id
		var passList = "";

		$(".rasent").find("tr").each(function() {
			if ($(this).css("color") == "rgb(255, 0, 0)" || $(this).css("color") == "red") {
				blockList += $(this).attr("string");
				blockList += ",";
			} else if ($(this).css("color") == "rgb(0, 128, 0)" || $(this).css("color") == "green") {
				passList += $(this).attr("string");
				passList += ",";
			}
		});
		doAjax(blockList, passList);

	}

	// 提交
	function doAjax(blockList, passList) {
		$.ajax({
			type : "post",
			async : false,
			url : "/agentVerify/verifyMobile",
			dataType : "json",
			data : {
				"blockList" : blockList,
				"passList" : passList
			},
			success : function(data) {
			/* 	if (data.error) {
					alert(data.error);
					return;
				} */
				window.location.href = "/agentVerify/verifyMobileList/1?ranNum=" + Math.random()+"&type="+'${type}';
			}
		});
	}
//-->
</script>