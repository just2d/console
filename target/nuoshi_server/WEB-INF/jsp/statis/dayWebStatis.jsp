<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="/js/common/jsdate.js"></script>
<script type="text/javascript">
<!--
function search() {
	var webStatisForm = document.getElementById("webStatisForm");

	if (webStatisForm != null) {
		webStatisForm.submit();
	}

}

//ajax获取城市、区域 
function getDist(selectId,pid) {
	var selectElem = "#" + selectId;
	var def;
	if (selectId == "city") {
		def = "全国城市";
	} else {
		def = "请选择区域";
	}
	$.ajax({
		type : "GET",
		async : false,
		url : "/agentManage/ajax/zone/"+pid,
		dataType : "json",
		success : function(data) {
			$(selectElem).empty();
			var option = "<option value='-2'>" + def + "</option>";
			$(option).appendTo(selectElem);
			var list = data.distList;
			if (list != null && list.length > 0) {
				for (i in list) {
					var local = list[i];
					var option = "<option value='" + local.localid + "'>"
								+ local.localname + "</option>";
					$(option).appendTo(selectElem);
				}
			}
		}
	});
}


//设定选中菜单
function setSelected(selectItem, selectedValue){
	var $opt = $("#"+selectItem+" option[value="+selectedValue+"]");
	if($opt.size()){
		setTimeout(function(){
			$opt[0].selected = true;
		},100);
	}	
}

function getChangeStatis(){
	var type = $("#divspan").find(".stat_high").attr("type");
	if(type=="month"){
		var webStatisForm = document.getElementById("webStatisForm");
		window.location.href="/statis/webStatis/getMonthCount" ;
	}
	
}

$(document).ready(function(){
	var cityId = "${cityId}";
	var distId = "${distId}";
	if(cityId == null || cityId == "") {
		cityId = 0;
	}
	if(distId == null || distId == "") {
		distId = 0;
	}
	//初始化地标
	getDist("city",0);
	if(cityId>0){ 
		getDist("dist",cityId);
		if(distId>0){ 
			setSelected("dist",distId);
		}
	}
	//为控件绑定事件
	$("#dist").bind("change",function(){getChangeStatis();});
	$("#city").bind("change",function(){getDist("dist",$(this).val());getChangeStatis();});
	
	if("city"=="${param.type}"){
		$("#showCity").addClass("stat_high");
		$("#showCity").bind("click",function(){
			$("#queryType").val("all");
			search();
		});
	}else{
		if(cityId > 0) {
			setSelected("city",cityId);
		}
		$("#showCity").bind("click",function(){
			$("#queryType").val("city");
			search();
		});
	}
	
	$("#changeSpan").find(".stat_two").bind("click",function(){$(this).addClass("stat_high").siblings().removeClass("stat_high");getChangeStatis();});
});

//-->
</script>
<div style="width: 100%;text-align: left;">当前位置：数据管理>><b>网站数据统计</b></div> 
<form action="/statis/webStatis/getDayCount" method="post" id="webStatisForm">
<input type="hidden" name="type" id="queryType" value="${param.type }"/>
<div class="mag">
	     <div class="search" >
<table>
	<tr>
	<c:if test="${param.type=='city' }">
		<td>日期:</td><td><input id="startDate" class="def" name="date" style="width:100px;" class="dateCss" type="text" onclick="SelectDate(this,'yyyy-MM-dd','')" readonly="readonly" value="${date}"/></td>
	</c:if>
	<c:if test="${param.type!='city' }">
		<td>城市:</td><td><select id="city" style="width:100px;" name="cityId" value="${cityId}"></select></td>
		<td>区域:</td><td><select id="dist" name="distId" class="stat_one" value="${distId}" ><option value="-2">请选择区域</option></select></td>
		<td>开始日期:</td><td><input id="startDate" class="def" name="startDate" style="width:100px;" class="dateCss" type="text" onclick="SelectDate(this,'yyyy-MM-dd','')" readonly="readonly" value="${startDate}"/></td>
		<td>结束日期:</td><td><input id="endDate" class="def" name="endDate" style="width:100px;" class="dateCss" type="text" onclick="SelectDate(this,'yyyy-MM-dd','')" readonly="readonly" value="${endDate}"/></td>
	</c:if>
		
		
		<td>&nbsp;</td><td><input type="button" onclick="search();" value="查找" /></td>
	</tr>
</table>
</div>
<div id="divspan" class="mag">
<span id="changeSpan" >
	<span id="sp1" class="stat_two stat_high"   type="day">每日</span> 
	<span id="sp2" class="stat_two"  type="month">每月</span> 
</span>	
			<span id="showCity"  class="stat_two" >按城市展开</span> 
	
</div>
</div>
<div class="mag">
	<div class="todo">
		<table id="statTable">
				<tr>
				 <th>城市</th><th>日期</th><th>免费经纪人</th><th>付费经纪人</th><th>认证经纪人</th><th>在线二手房</th><th>在线出租房</th><th>发布二手房</th><th>发布出租房</th><th>登录经纪人</th><th>发布房源经纪人</th>
				</tr>
				<tbody>
				<c:forEach items="${dayWebStatisList }" var="dayWebStatis">
					<tr>
					<td><c:if test="${dayWebStatis.cityId==null ||dayWebStatis.cityId=='' || dayWebStatis.cityId=='-1'}">全国</c:if ><c:if test="${dayWebStatis.cityId!=null ||dayWebStatis.cityId!='' || dayWebStatis.cityId!='-1'}">${dayWebStatis.cityName }</c:if></td>
					<td>${dayWebStatis.entryDate}</td>
					<td>${dayWebStatis.freeAgentNum}</td>
					<td>${dayWebStatis.payAgentNum}</td>
					<td>${dayWebStatis.verifyAgentNum}</td>
					<td>${dayWebStatis.onlineResaleNum}</td>
					<td>${dayWebStatis.onlineRentNum}</td>
					<td>${dayWebStatis.pubResaleNum}</td>
					<td>${dayWebStatis.pubRentNum}</td>
					<td>${dayWebStatis.loginUserNum}</td>
					<td>${dayWebStatis.pubHouseAgentNum}</td>
				</c:forEach>
				</tbody>
				
		</table>
	</div>
</div>
</form>