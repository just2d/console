 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<script type="text/javascript" src="/js/common/jsdate.js"></script>
<script type="text/javascript">
<!--
//ajax获取城市、区域 
function getDist(selectId,pid) {
	var selectElem = "#" + selectId;
	$.ajax({
		type : "GET",
		async : false,
		url : "/agentManage/ajax/zone/"+pid,
		dataType : "json",
		success : function(data) {
			$(selectElem).empty();
			var list = data.distList;
			if (list != null && list.length > 0) {
				var global = "<option value='-1'>www全国</option>";
				$(global).appendTo(selectElem);
				for (i in list) {
					var local = list[i];
					var option = "<option value='" + local.localid + "'>" + local.dirName + local.localname + "</option>";
					$(option).appendTo(selectElem);
				}
			}
		}
	});
}
function setSelected(selectItem, selectedValue){
	var $opt = $("#"+selectItem+" option[value="+selectedValue+"]");
	if($opt.size()){
		setTimeout(function(){
			$opt[0].selected = true;
		},100);
	}	
}

$(document).ready(function(){
	var cityId = ${requestScope.cityId};
	if(cityId == null || cityId == "") {
		cityId = 0;
	}
	getDist("city",0);
	if(cityId > 0) {
		setSelected("city",cityId);
	}
	var type = ${requestScope.selecttype};
	if(type!=null){
		setSelected("type",type);
	}
});

function backData(){
	$("#city").val("-1");
	$("#type").val("-1");
	$("#start_date").val("");
	$("#end_date").val("");
	$("#nick").val("");
}


//-->
</script>
<div style="width: 100%;text-align: left;">当前位置：订单管理 &gt; 后台用户明细查询 &gt; 后台用户明细查询</div>  
<br />
<br />

	<form action="/order/query" method="post" id="tranDetailForm" >

		<div class="mag">
			<div class="search">
			<table>
	<tr>
		<td>城市:</td><td><select id="city" style="width:100px;" name="cityId" value="${param.cityId}"></select></td>
		<td>淘房 账号:</td><td><input id="nick" type="text" name="nick" style="width:100px;" value="${param.nick }" /></td>
		<td>类型:</td><td>
		<select id="type" name="type" value="${param.type }">
			<option value="-1">全部</option>
			<option value="0">充值</option>
			<option value="1">消费</option>
			<option value="3">消费激活</option>
		</select>
		</td>
		
	</tr>
	<tr>
		<td>日期:</td><td><input id="start_date" class="def" name="startDate" style="width:100px;" class="dateCss" type="text" onclick="SelectDate(this,'yyyy-MM-dd','起始日期')" readonly="readonly" value="${param.startDate}"/> 至  <input id="end_date" class="def" name="endDate" style="width:100px;" class="dateCss" type="text" onclick="SelectDate(this,'yyyy-MM-dd','终止日期')" readonly="readonly" value="${param.endDate}"/> </td>
		<td>&nbsp;</td><td><input type="submit"  value="查找" />&nbsp;&nbsp;<input type="button" value="清空" onclick="backData()"/></td>
		<td>&nbsp;</td><td><a href="javascript:analysis()">统计查看</a></td>
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
							<th style=" width:12.5%; text-align:center">淘房账号</th>
							<th style=" width:12.5%; text-align:center">销售人员/姓名</th>
							<th style=" width:12.5%; text-align:center">充值</th>
							<th style=" width:12.5%; text-align:center">消费</th>
							<th style=" width:12.5%; text-align:center">时间</th>
							<th style=" width:12.5%; text-align:center">账户余额</th>
							<th style=" width:13%; text-align:center">说明</th>
							</tr>

					</thead>

					<tbody class="rasent">
					
						<c:forEach items="${tranDetailList}" var="list" varStatus="status">
							<tr>

								<td style=" width:6%; text-align:center">${status.index+1 }</td>
								<td style=" width:6%; text-align:center">${list.cityName}</td>
								<td style=" width:12.5%; text-align:center">${list.agentUserName}</td>
								<td style=" width:12.5%; text-align:center"><c:choose><c:when test="${list.salerId != 0}">${list.salerId}/${list.salerName }</c:when><c:otherwise>无</c:otherwise></c:choose></td>
								<td style=" width:12.5%; text-align:center"><c:if test="${list.orderType==0 }">${list.amount }淘房金币</c:if></td>
								<td style=" width:12.5%; text-align:center"><c:if test="${list.orderType==1 }">${list.amount }淘房金币</c:if></td>
								<td style=" width:12.5%; text-align:center">${list.insertTime} </td>
								<td style=" width:12.5%; text-align:center">${list.balance}</td>
								<td style=" width:13%; text-align:center"><c:if test="${list.consumeType==0 }">管理员充值</c:if><c:if test="${list.consumeType==2 }">置顶广告</c:if><c:if test="${list.consumeType==3 }">购买套餐 套餐名称为${list.packageName },套餐端口为${list.port }</c:if><c:if test="${list.consumeType==4 }">竞价消耗</c:if></td>
							</tr>
						</c:forEach>
					</tbody>
					<c:if test="${fn:length(tranDetailList) !=0 }">
					<tbody class="rasent">
						<tr>
									<td style=" width:6%; text-align:center">合计</td>
									<td style=" width:6%; text-align:center">&nbsp;</td>
									<td style=" width:12.5%; text-align:center">&nbsp;</td>
									<td style=" width:12.5%; text-align:center">&nbsp;</td>
									<td style=" width:12.5%; text-align:center">${rechargeHeji } 淘房金币</td>
									<td style=" width:12.5%; text-align:center">${purchaseHeji } 淘房金币 </td>
									<td style=" width:12.5%; text-align:center">&nbsp;</td>
									<td style=" width:12.5%; text-align:center"> &nbsp;</td>
									<td style=" width:13%; text-align:center">&nbsp; </td>
						</tr>
						</tbody>
						<c:if test="${page.pageNo==page.totalPage }">
						<tbody class="rasent">
						<tr>
									<td style=" width:6%; text-align:center">总计</td>
									<td style=" width:6%; text-align:center">&nbsp;</td>
									<td style=" width:12.5%; text-align:center">&nbsp;</td>
									<td style=" width:12.5%; text-align:center">&nbsp;</td>
									<td style=" width:12.5%; text-align:center">${rechargeZhongji } 淘房金币</td>
									<td style=" width:12.5%; text-align:center">${purchaseZhongji } 淘房金币  </td>
									<td style=" width:12.5%; text-align:center">&nbsp; </td>
									<td style=" width:12.5%; text-align:center"> &nbsp;</td>
									<td style=" width:13%; text-align:center">&nbsp; </td>
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
	$("#analysis").dialog({show : "slide",bgiframe : true,autoOpen : false,height :h*0.6,width : '60%',title : "后台用户明细查询",modal : true,resizable: false,buttons : {"返回" : closeDialog}});
});

function closeDialog(){
	$("#analysis").dialog("close");
}
function analysis(){
	var city  = $("#city").val();
	var nick = $("#nick").val();
	var startDate = $("#start_date").val();
	var endDate = $("#end_date").val();
	$.ajax({
		type : "post",
		async : false,
		url : "/order/querytranAnalysis",
		dataType : "json",
		data : {"city" : city,"nick":nick,"startDate":startDate,"endDate":endDate},
		success : function(data) {
			var tranDetailAnalysis=data.tranDetailAnalysis;
			$("#rechargeAmountWithSales").html(tranDetailAnalysis.rechargeAmountWithSales);
			$("#purchaseAmountWithSales").html(tranDetailAnalysis.purchaseAmountWithSales);
			$("#rechargeAmountWithoutSales").html(tranDetailAnalysis.rechargeAmountWithoutSales);
			$("#purchaseAmountWithoutSales").html(tranDetailAnalysis.purchaseAmountWithoutSales);
			$("#purchaseActiveAmountWithSales").html(tranDetailAnalysis.purchaseActiveAmountWithSales);
			
			$("#purchaseActiveAmountWithoutSales").html(tranDetailAnalysis.purchaseActiveAmountWithoutSales);
			$("#rechargeNumWithSales").html(tranDetailAnalysis.rechargeNumWithSales);
			
			$("#rechargeNumWithoutSales").html(tranDetailAnalysis.rechargeNumWithoutSales);
			$("#rechargeOnlineAmount").html(tranDetailAnalysis.rechargeOnlineAmount);
			$("#rechargeZhifuboaAmount").html(tranDetailAnalysis.rechargeZhifuboaAmount);
			$("#rechargeShoujikaAmount").html(tranDetailAnalysis.rechargeShoujikaAmount);
			$("#rechargeAmountWithOperator").html(tranDetailAnalysis.rechargeAmountWithOperator);
			$("#purchaseAmountWithOperator").html(tranDetailAnalysis.purchaseAmountWithOperator);
			$("#purchaseActiveAmountWithOperator").html(tranDetailAnalysis.purchaseActiveAmountWithOperator);
			$("#rechargeNumWithOperator").html(tranDetailAnalysis.rechargeNumWithOperator);
			
			
			
			$("#analysis").dialog("close");
			$("#analysis").dialog("option", "position", "center");
			$("#analysis").dialog("open");
			}
		}
	);
	
	
}
//-->
</script>	
<div id="analysis" class="todo">
<h2>经纪人使用情况</h2>
<table>
<tr><td>&nbsp;</td><td>有对应销售人员</td><td>有对应运营人员</td><td>无人跟进</td></tr>
<tr><td>充值金额</td><td id="rechargeAmountWithSales"></td><td id=""></td><td id="rechargeAmountWithoutSales"></td></tr>
<tr><td>消费金额</td><td id="purchaseAmountWithSales"></td><td id=""></td><td id="purchaseAmountWithoutSales"></td></tr>
<tr><td>消费激活金额</td><td id="purchaseActiveAmountWithSales"></td><td id=""></td><td id="purchaseActiveAmountWithoutSales"></td></tr>
<tr><td>充值经纪人总数</td><td id="rechargeNumWithSales"></td><td id=""></td><td id="rechargeNumWithoutSales"></td></tr>
</table>
<br>
<br>
<h2>经纪人充值明细</h2>
<table>
<tr><td>&nbsp;</td><td>网上银行</td><td>支付宝</td><td>手机卡</td></tr>
<tr><td>金额</td><td id="rechargeOnlineAmount"></td><td id="rechargeZhifuboaAmount"></td><td id="rechargeShoujikaAmount"></td></tr>
</table>
</div>