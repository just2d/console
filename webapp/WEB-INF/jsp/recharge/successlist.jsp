 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<script type="text/javascript" src="/js/common/jsdate.js"></script>
<script type="text/javascript">
<!--
function setSelected(selectItem, selectedValue){
	var $opt = $("#"+selectItem+" option[value="+selectedValue+"]");
	if($opt.size()){
		setTimeout(function(){
			$opt[0].selected = true;
		},100);
	}	
}
$(document).ready(function(){
	var orderState = ${requestScope.orderState};
	if(orderState!=null&&orderState!=""){
		setSelected("orderState",orderState);
	}
	
	var rechargeType= ${requestScope.rechargeType};
	if(rechargeType!=null){
		setSelected("rechargeType",rechargeType);
	}
});

function open(agentNick,amount,operatePerson,operateTime){
	$("#agentnick").html(agentNick);
	if(amount=='') amount='0';
	$("#amount").html(amount+"淘房金币");
	$("#operatePerson").html(operatePerson);
	$("#operateTime").html(operateTime);
	$("#opendialog").dialog("close");
	$("#opendialog").dialog("option", "position", "center");
	$("#opendialog").dialog("open");
}

function backData(){
	$("#nick").val("");
	$("#rechargeType").val("-1");
	$("#start_date").val("");
	$("#end_date").val("");
	$("#orderNo").val("");
	$("#orderState").val("-1");
}

function downsuccess(){
	var form = document.getElementById("tranDetailForm");
	form.action="/order/downloadsuccesslist";
	form.submit();
	form.action="/order/successmanage";
}

function checkData(){
	
	//检查订单号格式
	var orderNo = $("#orderNo").val();
	if(orderNo!=null&&orderNo!=""){
		var reg = /^[0-9]+$/;
		 if (!reg.test(orderNo) || parseInt(orderNo)<=0)
		 {
			alert("订单号必须为正整数");
			$("#orderNo").val("");
			$("#orderNo").focus();
			return false;
		}
	}
	return true;
}
//-->
</script>
<div style="width: 100%;text-align: left;">当前位置：订单管理 &gt; 支付成功订单管理 &gt; 支付成功订单管理</div>  
<br />
<br />

	<form action="/order/successmanage" method="post" id="tranDetailForm" onsubmit="return checkData()">

		<div class="mag">
			<div class="search">
			<table>
	<tr>
		<td>淘房 账号:</td><td><input id="nick" type="text" name="nick" style="width:100px;" value="${param.nick }" /></td>
		<td>支付方式:</td><td>
		<select id="rechargeType" name="rechargeType" value="${param.rechargeType }">
			<option value="-1">全部</option>
			<option value="0">网上银行</option>
			<option value="1">支付宝</option>
			<option value="2">手机充值卡</option>
		</select>
		</td>
		<td>支付日期:</td><td><input id="start_date" class="def" name="startDate" style="width:100px;" class="dateCss" type="text" onclick="SelectDate(this,'yyyy-MM-dd','起始日期')" readonly="readonly" value="${param.startDate}"/> 至  <input id="end_date" class="def" name="endDate" style="width:100px;" class="dateCss" type="text" onclick="SelectDate(this,'yyyy-MM-dd','终止日期')" readonly="readonly" value="${param.endDate}"/> </td>
	</tr>
	<tr>
		<td>订单号:</td><td><input id="orderNo" type="text" name="orderNo" value="${param.orderNo }" /></td>
		<td>订单状态:</td><td>
		<select id="orderState" name="orderState" value="${param.orderState }">
			<option value="-1">全部</option>
			<option value="2">支付成功，等待充值</option>
			<option value="4">充值成功，交易结束</option>
		</select>
		</td>
		<td><input type="submit"  value="查找" />&nbsp;&nbsp;<input type="button" value="重置" onclick="backData();"/></td><td><a href="javascript:downsuccess()">点击下载报表详情</a></td>
	</tr>
</table>
			</div>


		</div>

		<div class="mag">

			<div class="todo">

				<table>

					<thead>

						<tr>
							<th style=" width:6%; text-align:center">序号</th>
							<th style=" width:6%; text-align:center">城市</th>
							<th style=" width:12%; text-align:center">订单号</th>
							<th style=" width:12%; text-align:center">金额</th>
							<th style=" width:12%; text-align:center">充值方式</th>
							<th style=" width:12%; text-align:center">支付时间</th>
							<th style=" width:12%; text-align:center">淘房账号</th>
							<th style=" width:12%; text-align:center">订单状态</th>
							<th style=" width:12%; text-align:center">操作人</th>
							<th style=" width:12%; text-align:center">操作</th>
							</tr>

					</thead>

					<tbody class="rasent">
					
						<c:forEach items="${rechargeList}" var="list" varStatus="status">
							<tr>

								<td style=" width:6%; text-align:center">${status.index+1 }</td>
								<td style=" width:6%; text-align:center">${list.cityName}</td>
								<td style=" width:12%; text-align:center">t${list.rechargeOrderNo}</td>
								<td style=" width:12%; text-align:center">${list.rechargeAmount}	</td>
								<td style=" width:12%; text-align:center"><c:if test="${list.rechargeType==0}">网上银行</c:if><c:if test="${list.rechargeType==1}">支付宝</c:if><c:if test="${list.rechargeType==2}">手机充值卡</c:if></td>
								<td style=" width:12%; text-align:center"> ${list.paidTime}</td>
								<td style=" width:12%; text-align:center">${list.agentUserName} </td>
								<td style=" width:12%; text-align:center"><c:if test="${list.rechargeState==2 }">支付成功，等待充值</c:if><c:if test="${list.rechargeState==4 }">充值成功，交易结束</c:if></td>
								<td style=" width:12%; text-align:center">${list.entryId }</td>
								<td style=" width:12%; text-align:center"><c:if test="${list.rechargeState==4 }"><a href="javascript:open('${list.agentUserName }','${list.rechargeAmount}','${list.entryId }','${list.entryTime }')">查看</a></c:if></td>
							</tr>
						</c:forEach>
					</tbody>
					<c:if test="${fn:length(rechargeList) !=0 }">
					<tbody class="rasent">
					<tr>
								<td style=" width:6%; text-align:center">合计</td>
								<td style=" width:6%; text-align:center">&nbsp;</td>
								<td style=" width:12%; text-align:center">&nbsp;</td>
								<td style=" width:12%; text-align:center">${heji }</td>
								<td style=" width:12%; text-align:center"> &nbsp;</td>
								<td style=" width:12%; text-align:center">&nbsp; </td>
								<td style=" width:12%; text-align:center"> &nbsp;</td>
								<td style=" width:12%; text-align:center"> &nbsp;</td>
								<td style=" width:12%; text-align:center"> &nbsp;</td>
								<td style=" width:12%; text-align:center"> &nbsp;</td>
					</tr>
					</tbody>
					<c:if test="${page.pageNo==page.totalPage }">
					<tbody class="rasent">
					<tr>
								<td style=" width:6%; text-align:center">总计</td>
								<td style=" width:6%; text-align:center">&nbsp;</td>
								<td style=" width:12%; text-align:center">&nbsp;</td>
								<td style=" width:12%; text-align:center">${zhongji }</td>
								<td style=" width:12%; text-align:center">&nbsp; </td>
								<td style=" width:12%; text-align:center"> &nbsp;</td>
								<td style=" width:12%; text-align:center"> &nbsp;</td>
								<td style=" width:12%; text-align:center">&nbsp; </td>
								<td style=" width:12%; text-align:center"> &nbsp;</td>
								<td style=" width:12%; text-align:center"> &nbsp;</td>
					</tr>
					</tbody>
					</c:if>
					</c:if>
				</table>

			</div>

		</div>

	
		<div class="page_and_btn" >
		
			<jsp:include page="/WEB-INF/snippets/page.jsp" />
		</div>
	</form>
	<script type="text/javascript">
<!--
$(function() {
	var h = $(window).height();
	// 初始化弹出窗口
	$("#opendialog").dialog({show : "slide",bgiframe : true,autoOpen : false,height :h*0.4,width : '40%',title : "查看",modal : true,resizable: false,buttons : {"返回" : closeDialog}});
});

function closeDialog(){
	$("#opendialog").dialog("close");
}
//-->
</script>
	
<div id = "opendialog" class="todo"  >
	<table>
	<tr><td>淘房账号：</td><td id="agentnick"></td></tr>
	<tr><td>充值金额：</td><td id="amount"></td></tr>
	<tr><td>操作人员：</td><td id="operatePerson"></td></tr>
	<tr><td>操作时间：</td><td id="operateTime"></td></tr>
	</table>
</div>	
