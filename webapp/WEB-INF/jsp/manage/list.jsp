<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div style="width: 100%;text-align: left;">当前位置：经纪人管理>><b>经纪人管理</b></div> 
<form id="searchForm" action="/agentManage/search" method="get">
<div class="mag" style="border:1px solid #CAD9EA">
<input type="hidden" value="0" id="currentOperationAgentId" />
<select class="cs" name="type" id="type" style="width: 170px;">
		<option value="请选择">请选择搜索类型</option>
		<option value="id">ID</option>
		<option value="name">姓名</option>
		<option value="brand">公司</option>
		<option value="address">门店</option>
		<option value="mobile">手机号码</option>
		<option value="iccard">身份证号码</option>
	</select>
	<input class="def" type="text" id="searchtxt" name="searchtxt" value="请输入搜索内容" style="width: 163px;">
	<select class="cs" id="verifyResult" name="verifyResult" style="width: 170px;">
		<option value="请选择">请选择认证状态</option>
		<!-- <option value="0">已认证</option>
		<option value="1">待认证</option>
		<option value="2">未认证</option> -->
		<option value="0">已认证</option>
		<option value="1">未认证</option>
		<option value="2">已认证-待审核</option>
		<option value="3">未认证-待审核</option>
		<option value="4">已认证-拒绝</option>
		<option value="5">未认证-拒绝</option>
	</select>
	<!-- 
	<select class="cs" id="accountType" name="accountType" style="width: 170px;">
		<option value="请选择">请选择账户类型</option>
		<option value="0">付费用户</option>
		<option value="1">免费用户</option>
	</select>
	 -->
	 <select class="cs" id="startStop" name="banFlag" style="width: 170px;">
		<option value="请选择">请选择开启关闭状态</option>
		<option value="Y">关闭</option>
		<option value="N">开启</option>
	</select>
	 <select class="cs" id="isPay" name="payStatus" style="width: 170px;">
		<option value=0>请选择账户类型</option>
		<option value=1>免费用户</option>
		<option value=2>付费使用中</option>
		<option value=3>付费过期用户</option>
	</select>
	 <br/>
	<label class="def">注册日期:</label>
	<input class="def" name="startDate" class="dateCss" type="text" onclick="SelectDate(this,'yyyy-MM-dd','起始日期')" readonly="readonly" value="起始日期">
	- <input class="def" name="endDate" class="dateCss" type="text" onclick="SelectDate(this,'yyyy-MM-dd','终止日期')" readonly="readonly" value="终止日期">
	<br />
	<select class="cs" id="city" name="city" style="width: 170px;"><option value='请选择'>请选择城市</option></select>
	<select class="cs" id="dist" name="dist" style="width: 170px;"><option value='请选择'>请选择区域</option></select>
	<select class="cs" id="block" name="block" style="width: 170px;"><option value='请选择'>请选择商圈</option></select>
	<select class="cs" id="brand" name="brand" style="width: 170px;"><option value='请选择'>请选择公司</option></select>
	<select class="cs" id="address" name="address" style="width: 170px;"><option value='请选择'>请选择门店</option></select>
	<input class="def" type="submit" value="搜索" style="width: 60px;">
	<input class="def" type="button" id="resetBtn" value="重置" style="width: 60px;">
</div>
<div class="mag">
<div class="todo">
	<table>
	<thead>
	<tr>
	<c:if test="${fn:length(agentList)==0}"><th>经纪人列表为空</th></c:if>
	<c:if test="${fn:length(agentList)!=0}"> 
	<th><input type="checkbox" id="selectAll"/></th><th>ID</th><th>姓名</th><th>手机号</th><th>区域</th><th>板块</th><th>公司与门店</th><th>平均分</th><th>已用标签/标签总数</th><th>在线、待发布房源数</th>
	<th>注册日期</th><th>违规次数</th><th>认证状态</th><th>管理员操作</th>
	</c:if>
	</tr>
	</thead>
	<tbody>
		<c:forEach items="${agentList}" var="agent">
		<tr>
			<td><input type="checkbox" name="msgChk" value="${agent.mobile}"/></td>
			<td><a href="${urlInfo}/${agent.citydir}/${agent.id}/info" target="_blank">${agent.id}</a></td>
			<td>${agent.name}</td><td>${agent.mobile}</td>
			<td>${agent.cityname}-${agent.distname}</td>
			<td>${agent.blockname}</td>
			<td>${agent.brand}(${agent.address})</td>
			<td>${agent.aveScore}</td>
			<td>${agent.labelNum-agent.availableLabelNum } / ${agent.labelNum }</td>
			<td>${agent.agentHouseCount}</td>
			<td>${agent.cts}</td>
			<td><a href="/agentManage/house/${agent.citydir}/${agent.id}/4" target="_blank">${agent.illegal}</a></td>
			<td>
				<c:if test="${agent.verifyResult=='0'}"><a href="/agentVerify/search/${agent.id}/=/manage" target="_blank"><span style="color: #AAA0A0">已认证</span></a></c:if>
				<c:if test="${agent.verifyResult=='1'}"><a href="/agentVerify/search/${agent.id}/=/manage" target="_blank">未认证</a></c:if>
				<c:if test="${agent.verifyResult=='2'}"><a href="/agentVerify/search/${agent.id}/=/manage" target="_blank"><span style="color: #AAA0A0">已认证-待审核</span></a></c:if>
				<c:if test="${agent.verifyResult=='3'}"><a href="/agentVerify/search/${agent.id}/=/manage" target="_blank"><span style="color: #F91104">未认证-待审核</span></a></c:if>
				<c:if test="${agent.verifyResult=='4'}"><a href="/agentVerify/search/${agent.id}/=/manage" target="_blank"><span style="color: #AAA0A0">已认证-拒绝</span></a></c:if>
				<c:if test="${agent.verifyResult=='5'}"><a href="/agentVerify/search/${agent.id}/=/manage" target="_blank"><span style="color: #F91104">未认证-拒绝</span></a></c:if>
			</td>
			<td>
				<a href="/agentManage/house/${agent.citydir}/${agent.id}/0" target="_blank">查看</a>-
				<!-- <a href="/agentManage/rate/${agent.id}" target="_blank">查看信用</a>- -->
				<a href="javascript:confirmDelete('${agent.id}')">删除</a>-
				<a href="javascript:editAgent('${agent.id}')">编辑</a>
				<c:if test="${sessionScope.sessionUser.isPass('/agentManage/reset') }">
					-<a href="javascript:confirmReset('${agent.id}')">重置</a>
				</c:if>	
				<c:if test="${sessionScope.sessionUser.isPass('/agent/publishButton') }">
					<c:if test="${agent.isVerifyFreeUser()}">
						<br/>
						<a id='isPublish_${agent.id}'  href="javascript:publishButton('${agent.id}','${agent.banFlag=='Y'?'N':'Y'}')")>${agent.banFlag=="Y"?"开启":"关闭"}</a>-
						<a href="javascript:historyAgent('${agent.id}')">查看历史</a>
					</c:if>	
				</c:if>	
					<c:if test="${sessionScope.sessionUser.isPass('/package/manage/ajax/packageInfo') }">
				-<a href="javascript:getAgentPackageInfo('${agent.id}', '${agent.name}');">设置套餐</a>
				</c:if>	
			</td> 
		</tr>
		</c:forEach> 
	</tbody> 
	</table>
	</div>
	</div>
	<c:if test="${fn:length(agentList)!=0}"> 
	<div class="mag">
		<input class="def" type="button" id="msgBtn" value="发送短信"  style="width: 80px;"/>
		<input class="def" type="button" id="delBtn" value="删除信息不全经纪人"  style="width: 150px;"/>
	</div>
	</c:if>
	<div class="page_and_btn">
			<jsp:include page="/WEB-INF/snippets/page.jsp" />
		</div>
	</form>
	<div id="divMsg"></div>
	<div id="divAdd">
		<label class="lbl">标题:</label><input type="text" id="title" name="title" style="margin-left: -264px;"/><br /><br />
		<div><label class="lbl">内容:</label><textarea rows="8" id="reason" cols="55" name="reason" class="txtarea"></textarea></div><br /><br />
	</div>
	<div id="divAgentPackage">
		<label class="lbl">当前经纪人:</label><label id="packageAgentInfo"></label><br /><br />
		<div id="packageInfo"></div>
	</div>
	<div id="divEdit">
		<p><span class="regtlx">ID</span><input type="text" id="editid" readonly="readonly" style="width: 169px;color: gray;"/></p>
		<p><span class="regtlx">姓名</span><input type="text" id="editname" readonly="readonly" style="width: 169px;color: gray;"/></p>
		<p><span class="regtlx">城市</span><select id="editcity" style="width: 169px;"></select></p>
		<p><span class="regtlx">区域</span><select id="editdist" style="width: 169px;"></select></p>
		<p><span class="regtlx">商圈</span><select id="editblock" style="width: 169px;"></select></p>
		<p><span class="regtlx">公司</span><input type="text" id="editbrand" style="width: 169px;color: gray;" readonly="readonly"/></p>
		<p><span class="regtlx">门店</span><input type="text" id="editaddress" style="width: 169px;color: gray;" readonly="readonly"/></p>
	</div>
	<div id="divColseAgent">
		
		 <p><input type="hidden" id="colseAgentId"  /><input type="checkbox" id="colseAgent"  /> 给予付费提示</p>
	</div>
	<div id="showHistory">
		
	</div>
	<div id="divDel">
		经纪人ID:<input type="text" id="delid"/>
	</div>
	<div id="question" class="cfm" align="center"> 
        <div style="font-weight: bold;font-size: 13px;">您确定要删除吗?</div> 
        <input type="button" id="yes" value="是" /> 
        <input type="button" id="no" value="否" /> 
	</div> 
	<div id="resetDiv" class="cfm" align="center"> 
        <div style="font-weight: bold;font-size: 13px;">您确定要重置认证状态吗?</div> 
        <input type="button" id="resetYes" value="是" /> 
        <input type="button" id="resetNo" value="否" /> 
	</div>
<script type="text/javascript" src="/js/common/jsdate.js"></script>
<script type="text/javascript">
$(function() {
	// 初始化搜索条件，将搜索条件反填
	var searchType = "${agentSearch.type}";
	var searchTxt = "${agentSearch.searchtxt}";
	var searchStatus = "${agentSearch.verifyResult}";
	var searchAccountType = "${agentSearch.accountType}";
	var searchStartDate = "${agentSearch.startDate}";
	var searchEndDate = "${agentSearch.endDate}";
	var searchCity = "${agentSearch.city}";
	var searchDist = "${agentSearch.dist}";
	var searchBlock = "${agentSearch.block}";
	var searchBrand = "${agentSearch.brand}";
	var searchAddress = "${agentSearch.address}";
	$("#type").val(searchType);
	$("#verifyResult").val(searchStatus);
	$("#accountType").val(searchAccountType);
	$("#isPay").val('${agentSearch.payStatus}');
	$("#startStop").val('${agentSearch.banFlag}');
	getDist("city", "/agentManage/ajax/zone/0", searchCity);
	getDist("dist", "/agentManage/ajax/zone/" + searchCity, searchDist);
	getDist("block", "/agentManage/ajax/zone/" + searchDist, searchBlock);
	getBrandAddress("brand", "/agentManage/ajax/brand/" + searchBlock,searchBrand);
	getBrandAddress("address", "/agentManage/ajax/address/" + searchBrand,searchAddress);
	if (searchTxt != "请输入搜索内容" && searchTxt != "") {$("input[name=searchtxt]").val(searchTxt);}
	if (searchStartDate != "起始日期" && searchStartDate != "") {$("input[name=startDate]").val(searchStartDate);}
	if (searchEndDate != "终止日期" && searchEndDate != "") {$("input[name=endDate]").val(searchEndDate);}
	// 为控件绑定事件
	$("#msgBtn").bind("click", getMsg);
	$("#delBtn").bind("click", showDelDiv);
	$("#selectAll").bind("click", selAll);
	$("#resetBtn").bind("click", resetSearch);
	$("input[name=searchtxt]").bind("blur", txtBlur);
	$("input[name=searchtxt]").bind("focus", txtFocus);
	$("#no").bind("click", function() {$.unblockUI();return false;});
	$("#city").bind("change", function() {cityChange($(this).val());});
	$("#dist").bind("change", function() {distChange($(this).val());});
	$("#block").bind("change", function() {blockChange($(this).val());});
	$("#brand").bind("change", function() {brandChange($(this).val());});
	// 初始化弹出窗口
	$("#divDel").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 130,width : 330,title : "删除经纪人",modal : true,buttons : {"删除" : delAgn,"取消":closeDel}});
	$("#divMsg").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 350,width : 600,title : "发送短信",modal : false,buttons : {"新增短信" : addMsg,"确认发送" : sendMsg}});
	$("#divAdd").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 300,width : 500,title : "新增短信",modal : true,resizable : false,buttons : {"增加" : msgAdd}});
	$("#divAgentPackage").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 300,width : 500,title : "设置经纪人特殊套餐",modal : true,resizable : false,buttons : {"保存" : updateAgentPackage}});
	$("#divEdit").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 360,width : 400,title : "编辑经纪人",modal : true,resizable : false,buttons : {"确定" : agentUpdate,"取消":closeEdit}});
	$("#showHistory").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 360,width : 400,title : "操作历史",modal : true,resizable : false,buttons : {"关闭":closeShow}});
	$("#divColseAgent").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 200,width : 300,title : "关闭经纪人",modal : false,buttons : {"确认关闭" : closeAgent}});
});

// ajax城市复位
function cityChange(id) {
	getDist("dist", "/agentManage/ajax/zone/" + id);
	$("#block").empty().append("<option value='请选择'>请选择商圈</option>");
	$("#brand").empty().append("<option value='请选择'>请选择公司</option>");
	$("#address").empty().append("<option value='请选择'>请选择门店</option>");
}

// ajax区域复位
function distChange(id) {
	getDist("block", "/agentManage/ajax/zone/" + id);
	$("#brand").empty().append("<option value='请选择'>请选择公司</option>");
	$("#address").empty().append("<option value='请选择'>请选择门店</option>");
}

// ajax商圈复位
function blockChange(id) {
	getBrandAddress("brand", "/agentManage/ajax/brand/" + id);
	$("#address").empty().append("<option value='请选择'>请选择门店</option>");
}

// ajax公司复位
function brandChange(brand) {
	getBrandAddress("address", "/agentManage/ajax/address/" + brand);
}

// ajax删除经纪人
function confirmDelete(id) {
	var msg;
	$.blockUI({message : $("#question"),css : {width : "275px"}});
	$("#yes").click(function() {
		$.ajax({
			type : "get",
			async : false,
			url : "/agentManage/delete/" + id,
			dataType : "json",
			cache : false,
			success : function(data) {
				msg = data.message;
			},
			error : function(data) {
				blockAlert("调用后台服务错误,请稍后重试", 2000);
			}
		});
		$.unblockUI();
		window.location.reload();
		blockAlert(msg, 2000);
	});
}

//ajax重置经纪人
function confirmReset(id) {
	var msg;
	$.blockUI({message : $("#resetDiv"),css : {width : "275px"}});
	$("#resetYes").click(function() {
		$.ajax({
			type : "get",
			async : false,
			url : "/agentManage/reset/" + id,
			dataType : "json",
			cache : false,
			success : function(data) {
				msg = data.message;
			},
			error : function(data) {
				blockAlert("调用后台服务错误,请稍后重试", 2000);
			}
		});
		
		
		$.unblockUI();
		window.location.reload();
		blockAlert(msg, 2000);
	});
	$("#resetNo").click(function() {
		$.unblockUI();
	});
	
}

// ajax获取城市、区域、商圈
function getDist(selectId, _url, selectedValue) {
	_url = encodeURI(_url);
	_url = encodeURI(_url);
	var selectElem = "#" + selectId;
	var def;
	if (selectId == "city" || selectId == "editcity") {
		def = "请选择城市";
	} else if (selectId == "dist" || selectId == "editdist") {
		def = "请选择区域";
	} else {
		def = "请选择商圈";
	}
	$.ajax({
		type : "GET",
		async : false,
		url : _url,
		dataType : "json",
		success : function(data) {
			$(selectElem).empty();
			var option = "<option value='请选择'>" + def + "</option>";
			$(option).appendTo(selectElem);
			var list = data.distList;
			if (list != null && list.length > 0) {
				for (i in list) {
					var local = list[i];
					var option;
					if (local.localid == selectedValue) {
						option = "<option value='" + local.localid
								+ "' selected=\"selected\">" + local.localname
								+ "</option>";
					} else {
						option = "<option value='" + local.localid + "'>"
								+ local.localname + "</option>";
					}
					$(option).appendTo(selectElem);
				}
			}
		}
	});
}

// ajax获取公司、门店
function getBrandAddress(selectId, _url, selectedValue) {
	_url = encodeURI(_url);
	_url = encodeURI(_url);
	var selectElem = "#" + selectId;
	var def;
	if (selectId == "brand") {
		def = "请选择公司";
	} else {
		def = "请选择门店";
	}
	$.ajax({
		type : "GET",
		async : false,
		url : _url,
		dataType : "json",
		success : function(data) {
			var list = data.list;
			$(selectElem).empty();
			var option = "<option value='请选择'>" + def + "</option>";
			$(option).appendTo(selectElem);
			if (list != null && list.length > 0) {
				for (i in list) {
					var obj = list[i];
					var option;
					if (obj == selectedValue) {
						option = "<option value='" + obj
								+ "' selected=\"selected\">" + obj
								+ "</option>";
					} else {
						option = "<option value='" + obj + "'>" + obj
								+ "</option>";
					}
					$(option).appendTo(selectElem);
				}
			}
		}
	});
}

// 为搜索输入框绑定焦点事件
function txtFocus() {
	$("input[name=searchtxt]").val("");
}

// 为搜索输入框绑定失去焦点事件
function txtBlur() {
	String.prototype.Trim = function() {
		return this.replace(/(^\s*)|(\s*$)/g, "");
	};
	var val = $("input[name=searchtxt]").val().Trim();
	if (val == "") {
		$("input[name=searchtxt]").val("请输入搜索内容");
		$("input[name=searchtxt]").css("color", "gray");
	}
}

// 全选信息
function selAll() {
	if ($("#selectAll").attr("checked")) {
		$("input[name=msgChk]").attr("checked", true);
	} else {
		$("input[name=msgChk]").attr("checked", false);
	}
}

// 重置搜索条件
function resetSearch() {
	$("#type option:first").attr("selected", "selected");
	$("#verifyResult option:first").attr("selected", "selected");
	$("#accountType option:first").attr("selected", "selected");
	$("#city option:first").attr("selected", "selected");
	$("#dist").empty().append("<option value='请选择'>请选择区域</option>");
	$("#block").empty().append("<option value='请选择'>请选择商圈</option>");
	$("#brand").empty().append("<option value='请选择'>请选择公司</option>");
	$("#address").empty().append("<option value='请选择'>请选择门店</option>");
	$("#searchtxt").val("请输入搜索内容").css("color", "gray");
	$("input[name=startDate]").val("起始日期").css("color", "gray");
	$("input[name=endDate]").val("终止日期").css("color", "gray");
}

// ajax获取短信信息列表，将信息拼接显示在弹出窗口
function getMsg() {
	$.ajax({
				type : "GET",
				async : false,
				url : "/agentManage/ajax/msg",
				dataType : "json",
				success : function(data) {
					var content = "";
					var list = data.msgList;
					if (list[0] != null) {
						content += "<div class='todo'><table><tr><th></th><th>标题</th><th>内容</th><th>操作</th></tr>";
						$.each(list,function(i, msg) {
							var id = msg.id;
							var title = msg.title;
							var msg = msg.reason;
							var temp;
							if (msg.length > 30) {
								temp = msg.substr(0, 30) + "....";
							} else {
								temp = msg;
							}
							content += "<tr><td><input type='radio' name='msgRadio' value='"
									+ msg + "'/></td>" + "<td>" + title + "</td><td><span class='tdtip' data='"
									+ msg + "'>" + temp + "</span></td><td><a style='color:blue' href='javascript:delMsg(" + id
									+ ")'>删除</a></td></tr>";
						});
						content += "</table></div>";
					} else {
						content += "<div class='todo'><table><tr><th>短信列表为空</th></tr></table></div>";
					}
					$("#divMsg").html(content);
					$(".tdtip").tipsy({
						gravity : "n",
						title : function() {
							return $(this).attr("data");
						}
					});
				}
			});
	$("*").stop();
	$("#divMsg").dialog("close");
	var top = $("#msgBtn").offset().top;
	var left = $("#msgBtn").offset().left;
	$("#divMsg").dialog("option", "position", [ left, top ]);
	$("#divMsg").dialog("open");
}

// 显示增加短信信息窗口
function addMsg() {
	$("*").stop();
	$("#divAdd").dialog("close");
	$("#divAdd").dialog("option", "position", "center");
	$("#divAdd").dialog("open");
}

// ajax增加短信，增加完毕重新载入短信列表窗口
function msgAdd() {
	String.prototype.Trim = function() {return this.replace(/(^\s*)|(\s*$)/g,"");};
	var title = $("#title").val().Trim();
	var reason = $("#reason").val().Trim();
	var flag = 0;
	if (title == "") {
		blockAlert("请输入标题", 2000);
		flag++;
	} else if (reason == "") {
		blockAlert("请输入内容", 2000);
		flag++;
	}
	if (flag == 0) {
		$.ajax({
			type : "GET",
			async : false,
			url : "/agentManage/ajax/msg/add",
			dataType : "json",
			data : {"title" : title,"reason" : reason},
			success : function(data) {
				$("#title").val("");
				$("#reason").val("");
				blockAlert(data.message, 2000);
			}
		});
		$("#divAdd").dialog("close");
		getMsg();
	}
}

//ajax保存经纪人特殊的套餐关系
function updateAgentPackage() {
	var agentId = $("#currentOperationAgentId").val();
	var packageId = '';
	var price = '';
	$(".packageBox").each(function(){
		if($(this).attr("checked")){
			packageId += ($(this).val() + "_");
			price += $("#"+$(this).val()+"price").val() + "_";
		}
	});
	 
	$.ajax({
		type : "GET",
		async : false,
		url : "/package/manage/ajax/packageInfo/update",
		dataType : "json",
		data : {"agentId" : agentId,"packageIds" : packageId.substring(0, packageId.length - 1),"prices":price.substring(0, price.length - 1)},
		success : function(data) {
			$("#currentOperationAgentId").val("0");
			$("#packageInfo").html("");
			$("#packageAgentInfo").html("");
			blockAlert(data.message, 2000);
		}
	});
	$("#divAgentPackage").dialog("close");
}

// ajax给用户发送短信
function sendMsg() {
	var $msg = $("input[name=msgRadio]:checked");
	if ($msg.size() == 0) {
		blockAlert("请选择要发送的短信", 2000);
	} else {
		if ($("input[name=msgChk]:checked").size() > 0) {
			var content = $msg.val();
			var mobiles = "";
			$("input[name=msgChk]:checked").each(function() {
				mobiles += $(this).val() + ",";
			});
			$.ajax({
				type : "GET",
				async : false,
				url : "/agentManage/ajax/msg/send",
				dataType : "json",
				data : {"mobiles" : mobiles,"content" : content},
				success : function(data) {
					blockAlert(data.message, 2000);
				}
			});
			$("#divMsg").dialog("close");
		} else {
			blockAlert("请选择要发送短信的用户", 2000);
		}

	}
}

// ajax删除短信
function delMsg(id) {
	$.ajax({
		type : "GET",
		async : false,
		url : "/agentManage/ajax/msg/del/"+id,
		dataType : "json",
		success : function(data) {
			blockAlert(data.message, 2000);
		}
	});
	getMsg();
}

// ajax获取经纪人信息，将信息反填在编辑经纪人弹出窗口
function editAgent(id) {
	$.ajax({
		type : "GET",
		async : false,
		url : "/agentManage/ajax/edit/"+id,
		dataType : "json",
		success : function(data) {
			var agent = data.agent;
			var id = agent.id;
			var name = agent.name;
			var cityid = agent.cityid;
			var distid = agent.distid;
			var blockid = agent.blockid;
			var brand = agent.brand;
			var address = agent.address;
			$("#editid").val(id);
			$("#editname").val(name);
			$("#editcity").val(cityid);
			$("#editdist").val(distid);
			$("#editblock").val(blockid);
			$("#editbrand").val(brand);
			$("#editaddress").val(address);
			$("#editid").tipsy({gravity : "w",title : function() {return $(this).val();}});
			$("#editname").tipsy({gravity : "w",title : function() {return $(this).val();}});
			$("#editbrand").tipsy({gravity : "w",title : function() {return $(this).val();}});
			$("#editaddress").tipsy({gravity : "w",title : function() {return $(this).val();}});
			$("#editcity").bind("change",function() {getDist("editdist", "/agentManage/ajax/zone/" + $(this).val());$("#editblock").empty().append("<option value='请选择'>请选择商圈</option>");});
			$("#editdist").bind("change",function() {getDist("editblock", "/agentManage/ajax/zone/"+ $(this).val());});
			getDist("editcity", "/agentManage/ajax/zone/0", cityid);
			getDist("editdist", "/agentManage/ajax/zone/" + cityid, distid);
			getDist("editblock", "/agentManage/ajax/zone/" + distid, blockid);
			$("*").stop();
			$("#divEdit").dialog("close");
			$("#divEdit").dialog("option", "position", "center");
			$("#divEdit").dialog("open");
		}
	});
}


//ajax获取经纪人信息，将信息反填在编辑经纪人弹出窗口
function getAgentPackageInfo(id, name) {
	$.ajax({
		type : "GET",
		async : false,
		url : "/package/manage/ajax/packageInfo/get/"+id,
		dataType : "json",
		success : function(data) {
			var html = "";
			var packages = data.packages;
			var agentPackages = data.agentPackages;
			if(packages != null && packages.length > 0) {
				for(var i = 0; i < packages.length; i++) {
					var currentPackage = packages[i];
					html += "<input class='packageBox' type='checkbox' id='box"+currentPackage.id+"' value='"+currentPackage.id+"'></input>  套餐名称："+currentPackage.packageName+"  <label for='box"+currentPackage.id+"'>"+currentPackage.info+"</label>,<br />价格(原价"+currentPackage.price+"): <input id='"+currentPackage.id+"price' type='text' value='"+currentPackage.price+"' /> <br/>";
				}
			}
			$("#packageInfo").html(html);
			$("#packageAgentInfo").html(name);
			$("#currentOperationAgentId").val(id);
			if(agentPackages != null && agentPackages.length > 0) {
				for(var j = 0; j < agentPackages.length; j++) {
					var currentInfo = agentPackages[j];
					$("#box" + currentInfo.packageId).attr("checked", true);
					$("#"+currentInfo.packageId+"price").val(currentInfo.price);
				}
			}
			
			$("*").stop();
			$("#divAgentPackage").dialog("close");
			$("#divAgentPackage").dialog("option", "position", "center");
			$("#divAgentPackage").dialog("open");
		}
	});
}


// ajax更新经纪人，更新完毕刷新页面
function agentUpdate() {
	var id = $("#editid").val();
	var name = $("#editname").val();
	var cityid = $("#editcity").val();
	var distid = $("#editdist").val();
	var blockid = $("#editblock").val();
	var brand = $("#editbrand").val();
	var address = $("#editaddress").val();
	if (cityid == "请选择")
		cityid = -2;
	if (distid == "请选择")
		distid = -2;
	if (blockid == "请选择")
		blockid = -2;
	$.ajax({
		type : "GET",
		async : false,
		url : "/agentManage/update",
		dataType : "json",
		data : {"id" : id,"name" : name,"cityid" : cityid,"distid" : distid,"blockid" : blockid,"brand" : brand,"address" : address},
		success : function(data) {
			blockAlert(data.message, 2000);
		}
	});
	$("#divEdit").dialog("close");
	window.location.href = window.location.href;
}
//关闭经纪人
function closeAgent(){
	
	$("#divColseAgent").dialog("close");
	var agentId = $("#colseAgentId").val();
	var colseAgent = $("#colseAgent");
	var url = "/agentManage/ajax/switch/"+agentId+"/Y/0";
	if(colseAgent.attr("checked")){
		url = "/agentManage/ajax/switch/"+agentId+"/Y/1";
	}
	$.ajax({
		type : "GET",
		async : false,
		url : url,
		dataType : "json",
		success : function(data) {
			//提示操作成功
			if(data.result=="succeed"){
				alert("操作成功。");
				$('#isPublish_'+agentId).html("关闭");
				var a='Y';
				$('#isPublish_'+agentId).attr("href","javascript:publishButton('"+agentId+"','"+a+"')");
			}else{
				alert("操作失败！");
			}
		}
	});
	
	
}
//ajax开启关闭经纪人在线房源发布功能
function publishButton(id,type) {
	var str=type=="Y"?"关闭":"开启";
	
	if(type=="Y"){
		$("#colseAgentId").val(id);
		$("*").stop();
		$("#divColseAgent").dialog("open");
		$("#colseAgent").attr("checked",false);
	}else{
	if(!confirm("确定"+str+"?")){
		return;
	}
	$.ajax({
		type : "GET",
		async : false,
		url : "/agentManage/ajax/switch/"+id+"/"+type+"/0",
		dataType : "json",
		success : function(data) {
			//提示操作成功
			if(data.result=="succeed"){
				alert("操作成功。");
				$('#isPublish_'+id).html(type=="Y"?"开启":"关闭");
				var a=type=='Y'?'N':'Y';
				$('#isPublish_'+id).attr("href","javascript:publishButton('"+id+"','"+a+"')");
			}else{
				alert("操作失败！");
			}
		}
	});
	}
}

//ajax查看经纪人在线房源发布功能的开关历史记录
function historyAgent(id) {
	$.ajax({
		type : "GET",
		async : false,
		url : "/agentManage/ajax/operatHistory/"+id+"/"+1,
		dataType : "json",
		success : function(data) {
			var historyList = data.operatHistorys;
			var info="<table border='1'><thead><tr><th style='width: 100px;'>操作人</th><th style='width: 100px;'>操作日期</th><th style='width: 390px;'>备注</th></tr></thead><tbody>";
			if(historyList==null||historyList.length==0){
				info+="<tr><td colspan='3'>无操作记录</td></tr>";
			}else{
				for(var i=0;i<historyList.length;i++){
					var history=historyList[i];
					//操作人名字
					var operatorName = history.operatorName;
					//操作内容
					var contexts = history.operationalContext;
					//操作时间
					var dates = history.dates;
					var formatDates=show(new Date(dates));
					info+="<tr><td>"+operatorName+"</td><td>"+formatDates+"</td><td>"+contexts+"</td></tr>";
				}
			}
			info+="</tbody></table>";
			$('#showHistory').html(info);
		}
	});
	$("*").stop();
	$("#showHistory").dialog("close");
	$("#showHistory").dialog("option", "position", "center");
	$("#showHistory").dialog("open");
}
function show(date){
	var formatDate = "";
	formatDate = date.getFullYear()+"-"; //读英文就行了
	formatDate = formatDate + (date.getMonth()+1)+"-";//取月的时候取的是当前月-1如果想取当前月+1就可以了
	formatDate = formatDate + date.getDate()+" ";
	formatDate = formatDate + date.getHours()+":";
	formatDate = formatDate + date.getMinutes()+":";
	formatDate = formatDate + date.getSeconds()+"";
	return formatDate;
	}

//关闭编辑弹出层
function closeEdit(){
	$("*").stop();
	$("#divEdit").dialog("close");
}
//关闭编辑弹出层
function closeShow(){
	$("*").stop();
	$("#showHistory").dialog("close");
}

// 设置jquery blockUI弹出窗口
function blockAlert(msg, sec) {
	$.blockUI({
		css : {border : "none",padding : "15px",backgroundColor : "#000",'-webkit-border-radius' : "10px",'-moz-border-radius' : "10px",opacity : .6,color : "#fff"},
		message : msg
	});
	setTimeout($.unblockUI, sec);
}

//显示删除经纪人窗口
function showDelDiv(){
	$("*").stop();
	$("#divDel").dialog("close");
	$("#divDel").dialog("option", "position", "center");
	$("#divDel").dialog("open");
}

//删除经纪人
function delAgn(){
	String.prototype.Trim = function() {return this.replace(/(^\s*)|(\s*$)/g,"");};
	var id = $("#delid").val().Trim();
	if(id == ""){
		blockAlert("请输入经纪人ID",2000);
	}else{
		confirmDelete(id);
	}
}

//关闭删除窗口
function closeDel(){
	$("#delid").val("");
	$("*").stop();
	$("#divDel").dialog("close");
}
</script>