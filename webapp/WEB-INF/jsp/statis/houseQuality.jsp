<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="/js/common/jsdate.js"></script>
<script type="text/javascript">
<!--
function search() {
	var houselabelForm = document.getElementById("houseQualityForm");

	if (houselabelForm != null) {
		houselabelForm.submit();
	}

}
function getChangeStatis(){
	var type = $("#divspan").find(".stat_high").attr("type");
	$("#showType").val(type);
	if(type=="houseInfo"){
		$(".houseInfo").show();
		$(".houseScore").hide();
	}else if(type=="houseScore"){
		$(".houseScore").show();
		$(".houseInfo").hide();
	}
}
$(document).ready(function(){
	
	<c:if test="${param.showType==null||param.showType==''||param.showType=='houseInfo'}" >
		$(".houseInfo").show();
		$("#sp2").addClass("stat_high");
		$(".houseScore").hide();
		
	</c:if>
	<c:if test="${param.showType=='houseScore'}" >
		$(".houseScore").show();
		$("#sp3").addClass("stat_high");
		$(".houseInfo").hide();
	</c:if>
	$("#divspan").find(".stat_two").bind("click",function(){$(this).addClass("stat_high").siblings().removeClass("stat_high");getChangeStatis();});
});
//-->
</script>
<div style="width: 100%;text-align: left;">当前位置：数据管理>><b>房源质量统计</b></div> 
<form action="/statis/houseQuality/getcount" method="post" id="houseQualityForm">
<input type="hidden" id="showType" name="showType" value="${param.showType }" >
<div class="mag">
	     <div class="search" >
<table>
	<tr>
		<td>日期:</td><td><input id="date" class="def" name="date" style="width:100px;" class="dateCss" type="text" onclick="SelectDate(this,'yyyy-MM-dd','')" readonly="readonly" value="${param.date}"/></td>
		<td>&nbsp;</td><td><input type="button" onclick="search();" value="查找" /></td>
	</tr>
</table>
</div>
<div id="divspan" class="mag">
	<span id="sp2" class="stat_two"  type="houseInfo">信息完整度</span> 
	<span id="sp3" class="stat_two"   type="houseScore">房源质量</span> 
</div>
</div>
<div class="mag">
	<div class="todo">
			
		<table class = "houseInfo" id="houseInfo">
				<tr>
				<th>${date}</th><th>选填项均填写</th><th>选填项≥4项</th><th>户型图≥1张</th><th>室内图≥4张</th><th>小区图≥2张</th><th>户型图≥1 室内图≥4 小区图≥2</th><th>图片清晰度（宽640*高480以上）</th><th>室内图类别选择已标识</th><th>房源数量</th><th>照片总数</th>
				</tr>
				<tbody>
				<c:if test="${resaleQuality!=null&&rentQuality!=null}">
				<tr>
				<td>二手房</td>
				<td>${resaleQuality.allOptionNum }</td>
				<td>${resaleQuality.fourOptionNum }</td>
				<td>${resaleQuality.hasLayoutNum }</td>
				<td>${resaleQuality.hasFourInnerNum }</td>
				<td>${resaleQuality.hasTwoCommunity }</td>
				<td>${resaleQuality.hasAllPhoto}</td>
				<td>${resaleQuality.hasHdPhotoNum}</td>
				<td>${resaleQuality.hasInnertypePhotoNum}</td>
				<td>${resaleQuality.houseNum}</td>
				<td>${resaleQuality.photoNum}</td>
				</tr>
				<tr>
				<td>出租房</td>
				<td>${rentQuality.allOptionNum }</td>
				<td>${rentQuality.fourOptionNum }</td>
				<td>${rentQuality.hasLayoutNum}</td>
				<td>${rentQuality.hasFourInnerNum}</td>
				<td>${rentQuality.hasTwoCommunity}</td>
				<td>${rentQuality.hasAllPhoto}</td>
				<td>${rentQuality.hasHdPhotoNum}</td>
				<td>${rentQuality.hasInnertypePhotoNum}</td>
				<td>${rentQuality.houseNum}</td>
				<td>${rentQuality.photoNum}</td>
				</tr>
				<tr>
				<td>二手房+出租房</td>
				<td>${resaleQuality.allOptionNum+rentQuality.allOptionNum }</td>
				<td>${resaleQuality.fourOptionNum+rentQuality.fourOptionNum}</td>
				<td>${resaleQuality.hasLayoutNum+rentQuality.hasLayoutNum}</td>
				<td>${resaleQuality.hasFourInnerNum+rentQuality.hasFourInnerNum}</td>
				<td>${resaleQuality.hasTwoCommunity+rentQuality.hasTwoCommunity}</td>
				<td>${resaleQuality.hasAllPhoto+rentQuality.hasAllPhoto}</td>
				<td>${resaleQuality.hasHdPhotoNum+rentQuality.hasHdPhotoNum}</td>
				<td>${resaleQuality.hasInnertypePhotoNum+rentQuality.hasInnertypePhotoNum}</td>
				<td>${resaleQuality.houseNum+rentQuality.houseNum}</td>
				<td>${resaleQuality.photoNum+rentQuality.photoNum}</td>
				</tr>
				<tr>
				<td>占比</td>
				<td><fmt:formatNumber value="${100*(resaleQuality.allOptionNum+rentQuality.allOptionNum)/(resaleQuality.houseNum+rentQuality.houseNum)}" pattern="#,###.##" />%</td>
				<td><fmt:formatNumber value="${100*(resaleQuality.fourOptionNum+rentQuality.fourOptionNum)/(resaleQuality.houseNum+rentQuality.houseNum)}" pattern="#,###.##" />%</td>
				<td><fmt:formatNumber value="${100*(resaleQuality.hasLayoutNum+rentQuality.hasLayoutNum)/(resaleQuality.houseNum+rentQuality.houseNum)}" pattern="#,###.##" />%</td>
				<td><fmt:formatNumber value="${100*(resaleQuality.hasFourInnerNum+rentQuality.hasFourInnerNum)/(resaleQuality.houseNum+rentQuality.houseNum)}" pattern="#,###.##" />%</td>
				<td><fmt:formatNumber value="${100*(resaleQuality.hasTwoCommunity+rentQuality.hasTwoCommunity)/(resaleQuality.houseNum+rentQuality.houseNum)}" pattern="#,###.##" />%</td>
				<td><fmt:formatNumber value="${100*(resaleQuality.hasAllPhoto+rentQuality.hasAllPhoto)/(resaleQuality.houseNum+rentQuality.houseNum)}" pattern="#,###.##" />%</td>
				<td><fmt:formatNumber value="${100*(resaleQuality.hasHdPhotoNum+rentQuality.hasHdPhotoNum)/(resaleQuality.photoNum+rentQuality.photoNum)}" pattern="#,###.##" />%</td>
				<td><fmt:formatNumber value="${100*(resaleQuality.hasInnertypePhotoNum+rentQuality.hasInnertypePhotoNum)/(resaleQuality.photoNum+rentQuality.photoNum)}" pattern="#,###.##" />%</td>
				<td colspan="2"><fmt:formatNumber value="${(resaleQuality.photoNum+rentQuality.photoNum)/(resaleQuality.houseNum+rentQuality.houseNum)}" pattern="#,###.##" /></td>
				</tr>
				</c:if>
				</tbody>
				
		</table>
		<table class="houseScore" id="houseScore">
				<tr>
				<th>${date}</th><th>60分以下</th><th>60-70分</th><th>70-80分</th><th>80-90分</th><th>90分以上</th>
				</tr>
				<tbody>
				<c:if test="${resaleQuality!=null&&rentQuality!=null}">
				<tr>
				<td>二手房</td>
				<td>${resaleQuality.score0HouseNum }</td>
				<td>${resaleQuality.score1HouseNum }</td>
				<td>${resaleQuality.score2HouseNum }</td>
				<td>${resaleQuality.score3HouseNum }</td>
				<td>${resaleQuality.score4HouseNum }</td>
				
				</tr>
				<tr>
				<td>出租房</td>
				<td>${rentQuality.score0HouseNum}</td>
				<td>${rentQuality.score1HouseNum}</td>
				<td>${rentQuality.score2HouseNum}</td>
				<td>${rentQuality.score3HouseNum}</td>
				<td>${rentQuality.score4HouseNum}</td>
				
				</tr>
				<tr>
				<td>二手房+出租房</td>
				<td>${resaleQuality.score0HouseNum+rentQuality.score0HouseNum }</td>
				<td>${resaleQuality.score1HouseNum+rentQuality.score1HouseNum }</td>
				<td>${resaleQuality.score2HouseNum+rentQuality.score2HouseNum }</td>
				<td>${resaleQuality.score3HouseNum+rentQuality.score3HouseNum }</td>
				<td>${resaleQuality.score4HouseNum+rentQuality.score4HouseNum }</td>
				
				</tr>
				<tr>
				<td>占比</td>
				<td><fmt:formatNumber value="${100*(resaleQuality.score0HouseNum+rentQuality.score0HouseNum)/(resaleQuality.houseNum+rentQuality.houseNum)}" pattern="#,###.##" />%</td>
				<td><fmt:formatNumber value="${100*(resaleQuality.score1HouseNum+rentQuality.score1HouseNum)/(resaleQuality.houseNum+rentQuality.houseNum)}" pattern="#,###.##" />%</td>
				<td><fmt:formatNumber value="${100*(resaleQuality.score2HouseNum+rentQuality.score2HouseNum)/(resaleQuality.houseNum+rentQuality.houseNum)}" pattern="#,###.##" />%</td>
				<td><fmt:formatNumber value="${100*(resaleQuality.score3HouseNum+rentQuality.score3HouseNum)/(resaleQuality.houseNum+rentQuality.houseNum)}" pattern="#,###.##" />%</td>
				<td><fmt:formatNumber value="${100*(resaleQuality.score4HouseNum+rentQuality.score4HouseNum)/(resaleQuality.houseNum+rentQuality.houseNum)}" pattern="#,###.##" />%</td>
				</tr>
				</c:if>
				</tbody>
				
		</table>
	</div>
</div>
</form>