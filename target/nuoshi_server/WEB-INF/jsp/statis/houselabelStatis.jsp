<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="/js/common/jsdate.js"></script>
<script type="text/javascript">
<!--
function search() {
	var houselabelForm = document.getElementById("houselabelForm");

	if (houselabelForm != null) {
		houselabelForm.submit();
	}

}

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
	if(type=="resale"){
		$(".resale").show();
		$(".all").hide();
		$(".rent").hide();
	}else if(type=="rent"){
		$(".rent").show();
		$(".all").hide();
		$(".resale").hide();
	}else{
		$(".all").show();
		$(".rent").hide();
		$(".resale").hide();
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
	$(".all").show();
	$(".resale").hide();
	$(".rent").hide();
	
	$("#divspan").find(".stat_two").bind("click",function(){$(this).addClass("stat_high").siblings().removeClass("stat_high");getChangeStatis();});
});
//-->
</script>
<div style="width: 100%;text-align: left;">当前位置：数据管理>><b>标签统计</b></div> 
<form action="/statis/houselabel/getcount" method="post" id="houselabelForm">
<div class="mag">
	     <div class="search" >
<table>
	<tr>
		<td>城市:</td><td><select id="city" style="width:100px;" name="cityId" value="${param.cityId}"></select></td>
		<td>开始日期:</td><td><input id="startDate" class="def" name="startDate" style="width:100px;" class="dateCss" type="text" onclick="SelectDate(this,'yyyy-MM-dd','开始日期')" readonly="readonly" value="${param.startDate}"/></td>
		<td>终止日期:</td><td><input id="endDate" class="def" name="endDate" style="width:100px;" class="dateCss" type="text" onclick="SelectDate(this,'yyyy-MM-dd','终止日期')" readonly="readonly" value="${param.endDate}"/></td>
		<td>&nbsp;</td><td><input type="button" onclick="search();" value="查找" /></td>
	</tr>
</table>
</div>
<div id="divspan" class="mag">
	<span id="sp1" class="stat_two stat_high"   type="all">全部</span> 
	<span id="sp2" class="stat_two"  type="resale">二手房</span> 
	<span id="sp3" class="stat_two"   type="rent">租房</span> 
</div>
</div>
<div class="mag">
	<div class="todo">
		<table id="statTable">
				<tr>
				<th>城市</th><th>日期</th><th>标签总数 / 新增</th><th>视频标签总数 / 新增</th><th>新推标签总数 / 新增</th><th>随时看房标签总数 / 新增</th>
				</tr>
				<tbody>
				<c:forEach items="${labelsList }" var="houselabel">
					<tr>
					<td><c:if test="${requestScope.cityId==null ||requestScope.cityId=='' || requestScope.cityId=='-1'}">全国</c:if ><c:if test="${requestScope.cityId!=null ||requestScope.cityId!='' || requestScope.cityId!='-1'}">${houselabel.cityName }</c:if></td>
					<td>${houselabel.entryDateTime }</td>
					<td class="all">${houselabel.resaleLabeTotal+houselabel.rentLabeTotal} / ${houselabel.resaleNewLabelTotal+houselabel.rentNewLabelTotal }</td>
					<td class="resale">${houselabel.resaleLabeTotal }  / ${houselabel.resaleNewLabelTotal }</td>
					<td class="rent">${houselabel.rentLabeTotal } / ${houselabel.rentNewLabelTotal }</td>
					<td class="all">${houselabel.resaleVcrLabelNum+houselabel.rentVcrLabelNum } / ${houselabel.resaleNewVcrLabelNum+houselabel.rentNewVcrLabelNum }</td>
					<td class="resale">${houselabel.resaleVcrLabelNum } / ${houselabel.resaleNewVcrLabelNum }</td>
					<td class="rent">${houselabel.rentVcrLabelNum } / ${houselabel.rentNewVcrLabelNum }</td>
					<td class="all">${houselabel.resaleXtLabelNum+houselabel.rentXtLabelNum } / ${houselabel.resaleNewXtLabelNum+houselabel.rentNewXtLabelNum }</td>
					<td class="resale">${houselabel.resaleXtLabelNum } / ${houselabel.resaleNewXtLabelNum }</td>
					<td class="rent">${houselabel.rentXtLabelNum } / ${houselabel.rentNewXtLabelNum }</td>
					<td class="all">${houselabel.resaleSskfLabelNum+houselabel.rentSskfLabelNum } / ${houselabel.resaleNewSskfLabelNum+houselabel.rentNewSskfLabelNum }</td>
					<td class="resale">${houselabel.resaleSskfLabelNum } / ${houselabel.resaleNewSskfLabelNum }</td>
					<td class="rent">${houselabel.rentSskfLabelNum } / ${houselabel.rentNewSskfLabelNum }</td>
				</c:forEach>
				</tbody>
				
		</table>
	</div>
</div>
</form>