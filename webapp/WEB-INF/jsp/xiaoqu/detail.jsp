<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style>
<!--
th{
	border-right: none;
}
td{
	border-right:none;
}
-->
</style>
<script type="text/javascript" src="${contextPath}/js/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="${contextPath}/js/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript"	src="${contextPath }/js/ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript">
$(function() {
	$("#buildYear").datepicker({
	});
});
	function edit(authStatus){
		window.location.href="${contextPath}/estate/form?id="+'${estateInfo.estateId}'+"&action=edit&authStatus="+authStatus;
	}
	function audit(auth_Status,from_Status){
		var url ="${contextPath}/estate/authAction?estateId="+'${estateInfo.estateId}'+"&authStatus="+auth_Status;
		var delFlag = '${delFlag}';
		//是否执行物理删除
		if(auth_Status == "3" && typeof(delFlag) !="undefined" ){
			//删除操作
			if(delFlag == '0'){
				alert("此小区有发布的二手房源或出租房源,不可删除");
				return ;
			}else{
				url +="&origStatus=${authStatus}";
				//if(from_Status == '3'){
					//url ="${contextPath}/estate/deleteAction?estateId=${estateInfo.estateId}";	
				//}
			}
		}
		$.getJSON(url, null, function(json){
	  	if(json!=null&&json.length>0){
	  		alert("操作成功"); 
	  		var locationUrl = "${contextPath}/estate/list?authStatus="+from_Status;
	  		var cityId = '${cityId}';
	  		var distId = '${distId}';
	  		var blockId = '${blockId}';
	  		if(typeof("cityId")!="undefined" && cityId !=""){
	  			locationUrl +="&cityId="+cityId;
	  		}
	  		if(typeof("distId")!="undefined" && distId !=""){
	  			locationUrl +="&distId="+distId;
	  		}
	  		if(typeof("blockId")!="undefined" && blockId !=""){
	  			locationUrl +="&blockId="+blockId;
	  		}
	  		window.location.href=locationUrl;
	  	}
	  	
	});
	}

</script>
<div style="width: 100%;text-align: left;">当前位置:小区管理 &nbsp;&gt;&nbsp;查看小区</div> 
<br>
<div class="todo" style= "clear:both; text-align:left;width:80%;margin:0px;padding:0px; float:left;">
<form action="" method="post"  id="estateInfoForm" >
	<input type="hidden" value="${estateInfo.estateId}" name="estateId"/>
	<table border="1" cellpadding="3" cellspacing="1" width="80%" align="center" style="margin-bottom:15px">
	        <tr><th colspan="8" align="left" style="border-bottom:none">查看小区</th></tr>
			<tr>
			 <td width="5%" ><span class="important">*</span>小区名称:</td>
			 <td width="45%" align="left" >${estateInfo.estateName}</td>
			 <td width="5%"><span class="important">*</span>小区地址:</td>
			 <td width="45%" align="left" >${estateInfo.address}</td>
		    </tr>
		    <tr>
			 <td width="5%" ><span class="important">*</span>城市:</td>
			 <td width="45%" align="left" >${estateInfo.cityName}</td>
			 <td width="5%"><span class="important">*</span>区域-板块:</td>
			 <td width="45%" align="left" ><div id = 'bd'>${estateInfo.distName}</div>-${estateInfo.blockName}</td>
		    </tr>
		    <tr>
			 <td width="5%" >小区别名:</td>
			 <td width="45%" align="left" >${estateInfo.alias}</td>
			 <td width="5%">小区拼音:</td>
			 <td width="45%" align="left" >${estateInfo.namepy}</td>
		    </tr>
		    <tr>
			 <td width="5%" >小区二手房均价:</td>
			 <td width="45%" align="left" >${estateInfo.resaleAvgPrice}</td>
			 <td width="5%" >小区租房均价:</td>
			 <td width="45%" align="left" >${estateInfo.rentAvgPrice}</td>
		    </tr>
	</table>
	<table>		
			<tr>
			 <td width="5%" >物业公司:</td>
			 <td width="25%" align="left" >${estateInfo.wyCompany}</td>
			 <td width="5%" >物业类型:</td>
			 <td width="28%" align="left" >
			 	<c:if test="${fn:contains(estateInfo.wyType,'1')}" >住宅</c:if> 
			 	<c:if test="${fn:contains(estateInfo.wyType,'2')}" >商铺</c:if>
			 	<c:if test="${fn:contains(estateInfo.wyType,'3')}" >别墅</c:if>
			 	<c:if test="${fn:contains(estateInfo.wyType,'4')}" >写字楼</c:if>
			 	<c:if test="${fn:contains(estateInfo.wyType,'5')}" >其他</c:if>
			</td>
			 <td width="5%">物业费:</td>
			<td width="22%" align="left" >${estateInfo.wyFee}元/平方米</td>
		    </tr>
		    <tr>
			 <td width="5%" >开发商:</td>
			 <td width="25%" align="left" >${estateInfo.devCompany}</td>
			 <td width="5%" >建筑年代:</td>
			 <td width="25%" align="left" >${estateInfo.buildYear}</td>
			 <td width="5%">建筑面积:</td>
			<td width="25%" align="left" >${estateInfo.buildArea}</td>
		    </tr>	
		     <tr>
			 <td width="5%" >占地面积:</td>
			 <td width="25%" align="left" >${estateInfo.area}</td>
			 <td width="5%" >容积率:</td>
			 <td width="25%" align="left" >${estateInfo.areaRate}</td>
			 <td width="5%">绿化率:</td>
			<td width="25%" align="left" >${estateInfo.greenRate}</td>
		    </tr>	
		     <tr>
			 <td width="5%" >车位信息:</td>
			 <td width="25%" align="left" >${estateInfo.carInfo}</td>
			 <td width="5%" >小区经度:</td>
			 <td width="25%" align="left" >${estateInfo.lon }</td>
			 <td width="5%">小区纬度:</td>
			<td width="25%" align="left"> ${estateInfo.lat}</td>
		    </tr>	
	</table>
	
	<fieldset class="xt_xqxg">
		 	<legend>教育配套</legend>
		 	<table>
			<tr>
			 <td width="5%" >大学:</td>
			 <td width="80%" align="left" >${estateInfo.university}</td>
		    </tr>
		    <tr>
			 <td width="5%" >中小学:</td>
			 <td width="80%" align="left" >${estateInfo.school}</td>
		    </tr>
		    <tr>
			 <td width="5%" >幼儿园:</td>
			 <td width="80%" align="left" >${estateInfo.nursery}</td>
		    </tr>
		    </table>
		 </fieldset>
		 <fieldset class="xt_xqxg">
		 	<legend>商业配套</legend>
		 	<table>
			<tr>
			 <td width="5%" >商场:</td>
			 <td width="80%" align="left" >${estateInfo.market}</td>
		    </tr>
		    <tr>
			 <td width="5%" >医院:</td>
			 <td width="80%" align="left" >${estateInfo.hospital}</td>
		    </tr>
		    <tr>
			 <td width="5%" >邮局:</td>
			 <td width="80%" align="left" >${estateInfo.postOffice}</td>
		    </tr>
		    <tr>
			 <td width="5%" >银行:</td>
			 <td width="80%" align="left" >${estateInfo.bank}</td>
		    </tr>
		    <tr>
			 <td width="5%" >其他:</td>
			 <td width="80%" align="left" >${estateInfo.otherInfo}</td>
		    </tr>
		    </table>
		 </fieldset>
		  <fieldset class="xt_xqxg">
		 	<legend>交通配套</legend>
		 	<table>
			<tr>
			 <td width="5%" >公交:</td>
			 <td width="80%" align="left" >${estateInfo.bus}</td>
		    </tr>
		    <tr>
			 <td width="5%" >地铁:</td>
			 <td width="80%" align="left" >${estateInfo.subWay}</td>
		    </tr>
		    </table>
		 </fieldset>
	<table border="1" cellpadding="3" cellspacing="1" width="80%" align="center" style="margin-top:15px">
	        <tr>
	        <td><input type="button" value="编辑" onclick="edit('${authStatus}')"/>
	       	<c:choose>
	       		<c:when test="${authStatus eq '0'}">
	       			<input type="button" value="通过审核" onclick="audit('1','${authStatus}')"/>
	         		<input type="button" value="不通过" onclick="audit('2','${authStatus}')"/>
	       		</c:when>
	       		<c:when test="${authStatus eq '1'}">
	       			<input type="button" value="不通过" onclick="audit('2','${authStatus}')"/>
	       		</c:when>
	       		<c:when test="${authStatus eq '2'}">
	       			<input type="button" value="通过审核" onclick="audit('1','${authStatus}')"/>
	       		</c:when>
	       	</c:choose>
	         <input type="button" value="删除" onclick="audit('3','${authStatus}')"/></td>
	        </tr>
	</table>	
	</form>
</div>
