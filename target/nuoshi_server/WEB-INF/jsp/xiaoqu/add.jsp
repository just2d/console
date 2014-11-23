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
<link type="text/css" rel="stylesheet" href="/css/jquery-ui.css"/>
<link type="text/css" rel="stylesheet" href="/css/jquery.ui.datepicker.css"/>
<script type="text/javascript" src="${contextPath}/js/xiaoqu/test.js"></script>
<script type="text/javascript" src="${contextPath}/js/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="${contextPath}/js/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript"	src="${contextPath }/js/ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="${contextPath }/js/jquery.validate.js"></script>
<script type="text/javascript" src="${contextPath }/js/jquery.metadata.js"></script>
<script type="text/javascript">
$(function() {
	var options = {
		changeYear:true,
		yearRange:'c-100:c'
	};
	$("#buildYear").datepicker(options);
});

<!--
function setCityInfo(ch){
	if(ch != ""){
		$.getJSON("${contextPath}/local/cityList/"+ch, null, function(json){
		  	var options="<option value=''>-请选择-</option>";
		  	if(json!=null&&json.length>0){
		  		for(var i=0;i<json.length;i++){
			  		options+="<option value='"+json[i].id+"'>"+json[i].name+"</option>";
			  	}
		  	}
		  	$("#cityLocale").remove("option");
		  	$("#cityLocale").html(options);
		});
	}
}

	
function setDistInfo(cityId){
	if(cityId != ""){
		$.getJSON("${contextPath}/local/distList/"+cityId, null, function(json){
	  	var options="<option value=''>-请选择-</option>";
	  	if(json!=null&&json.length>0){
	  		for(var i=0;i<json.length;i++){
		  		options+="<option value='"+json[i].id+"'>"+json[i].name+"</option>";
		  	}
	  	}
	  	$("#distLocale").remove("option");
	  	$("#distLocale").html(options);
		});
		}
}

function setBlockInfo(distId){
	if(distId != ""){
		$.getJSON("${contextPath}/local/distList/"+distId, null, function(json){
	  	if(json!=null&&json.length>0){
	  		var options="<option value=''>-请选择-</option>";
	  		for(var i=0;i<json.length;i++){
		  		options+="<option value='"+json[i].id+"'>"+json[i].name+"</option>";
		  	}
	  	}else{
	  		$("#blockLocale").remove("option");
	  		options = "<option value='0'>-请选择-</option>";
	  		$("#blockLocale").html(options);
	  		$("#blockSel").hide();
	  	}
	  	$("#blockLocale").remove("option");
	  	$("#blockLocale").html(options);
	});
	}
}

	function save(){
		var cityId = $.trim($("#cityLocale").val());
		var distId = $.trim($("#distLocale").val());
		var blockId = $.trim($("#blockLocale").val());
		var estateName = $.trim($("#estateName").val());
		var address = $.trim($("#address").val());
		var resaleAvgPrice = $.trim($("#resaleAvgPrice").val());
		var rentAvgPrice = $.trim($("#rentAvgPrice").val()); 
		if(resaleAvgPrice == "" ||resaleAvgPrice == null){
			$("#resaleAvgPrice").val(0);
		}if(rentAvgPrice == "" ||resaleAvgPrice == null){
			$("#rentAvgPrice").val(0);
		}
		
		//var regNum = /^-?([1-9]+\d*\.?|0\.)\d*$/;
		var regNum = /^[0-9]+(\.[0-9]+){0,1}$/;
		var lon = $.trim($("#lon").val()); 
		var lat = $.trim($("#lat").val());
		if(lon == "" ||lon == null){
			$("#lon").val(0.0);
		}if(lat == "" ||lat == null){
			$("#lat").val(0.0);
		}
		if(typeof(cityId)!="undefined"&&cityId == "" ){
			alert("请选择城市名称");
			return;
		}else if(typeof(distId)!="undefined"&&distId == ""){
			alert("请选择区域名称");
			return;
		}else if(typeof(blockId)!="undefined"&&blockId == ""){
			alert("请选择板块名称");
			return;
		}else if(typeof(estateName)!="undefined"&&estateName == ""){
			alert("请填写小区名称");
			return;
		}else if(typeof(address)!="undefined"&&address == ""){
			alert("请填写小区地址");
			return;
		}else if(isNaN(resaleAvgPrice)){
			alert("小区二手房均价只能是数字");
			return;
		}else if(isNaN(rentAvgPrice)){
			alert("小区租房均价只能是数字");
			return;
		}else if(lon !="undefined"&& lon !="" && !regNum.test(lon)){
			alert("经度设置有误");
			return;
		}else if(lat !="undefined"&& lat !="" && !regNum.test(lat)){
			alert("纬度设置有误");
			return;
		}else if(estateName!="" && estateName != null){
			//验证是否有重名
			$.ajax({
				type: "post",  
	   			dataType: "json", 
	   			async :false,
	   			url: 'validateName',//提交到一般处理程序请求数据   
	   			data: "estateName="+estateName+"&cityId="+cityId,
	   			cache:false,
	   			success: function(json){
	   				json = json.toString();
					var flag = json.substring(7,json.length-1);
			  		if(json!=null&&json.length>0 && flag == "true"){
			  			alert("小区名已存在!!!");
			  			return ;
			  		}else{
			  			$("#estateInfoForm").attr("action","${contextPath}/estate/addAction");
						$("#estateInfoForm").submit();
			  		}
	   			}   
			});
		}
	}
//-->
</script>
<div style="width: 100%;text-align: left;">当前位置：小区管理 &nbsp;&gt;&nbsp;新增小区</div> 
<div class="todo" style=""clear:both; text-align:left;width:80%;margin:0px;padding:0px; float:left;">
<form action="" method="post"  id="estateInfoForm" >
	<!-- 设置审核状态为已通过审核 -->
	<input type="hidden" name="authStatus" value="1"/>
	<input type="hidden" name="fromHouse" value="2"/>
	<table border="1" cellpadding="3" cellspacing="1" width="80%" align="center" style="margin-bottom:15px">
	 <tr><th colspan="8" align="left" style="border-bottom:none">新增小区</th></tr>
	  <tr>
	  <td colspan="4" align="left">
	   首字母:<select name="ch" id="ch" onchange="setCityInfo(this.value)">
	  			<option value="">-请选择-</option>
	  			<option value="a">A</option>
	  			<option value="b">B</option>
	  			<option value="c">C</option>
	  			<option value="d">D</option>
	  			<option value="e">E<J/option>
	  			<option value="f">F</option>
	  			<option value="g">G</option>
	  			<option value="h">H</option>
	  			<option value="i">I</option>
	  			<option value="j">J</option>
	  			<option value="k">K</option>
	  			<option value="l">L</option>
	  			<option value="m">M</option>
	  			<option value="n">N</option>
	  			<option value="o">O</option>
	  			<option value="p">P</option>
	  			<option value="q">Q</option>
	  			<option value="r">R</option>
	  			<option value="s">S</option>
	  			<option value="t">T</option>
	  			<option value="u">U</option>
	  			<option value="v">V</option>
	  			<option value="w">W</option>
	  			<option value="x">X</option>
	  			<option value="y">Y</option>
	  			<option value="z">Z</option>
	     </select>
          城市:<select name="cityId" id="cityLocale" onchange="setDistInfo(this.value)">
	     </select>
	 区域:<select name="distId" id="distLocale" onchange="setBlockInfo(this.value)">
	 	</select>
		<span id="blockSel">板块:<select name="blockId" id="blockLocale">
                <option value="">-请选择-</option>
                <c:if test="${estateInfo.blockName ne null}">
                    <option value="${estateInfo.blockId}" selected="selected">${estateInfo.blockName}</option>
                </c:if>
         </span>
		</td></tr>
	 
	</div>
			<tr>
			 <td width="5%" ><span class="important">*</span>小区名称:</td>
			 <td width="45%" align="left" ><input  type="text"  id="estateName" name="estateName" size="35" value="${estateInfo.estateName}"/></td>
			 <td width="5%"><span class="important">*</span>小区地址:</td>
			 <td width="45%" align="left" ><input  type="text"  id="address" name="address" size="50" value="${estateInfo.address}"/></td>
		    </tr>
		    <tr>
			 <td width="5%" >小区别名:</td>
			 <td width="45%" align="left" ><input  type="text"  id="alias" name="alias" size="35" value="${estateInfo.alias}"/></td>
		    </tr>
		    <%--
		    <tr>
			 <td width="5%" >小区二手房均价:</td>
			 <td width="45%" align="left" ><input  type="text"  id="resaleAvgPrice" name="resaleAvgPrice" size="35" value="${estateInfo.resaleAvgPrice}"/></td>
			 <td width="5%" >小区租房均价:</td>
			 <td width="45%" align="left" ><input  type="text"  id="rentAvgPrice" name="rentAvgPrice" size="35" value="${estateInfo.rentAvgPrice}"/></td>
		    </tr>
		     --%>
	</table>
	<table>		
			<tr>
			 <td width="5%" >物业公司:</td>
			 <td width="25%" align="left" ><input  type="text"  id="wyCompany" name="wyCompany" value="${estateInfo.wyCompany}"/></td>
			 <td width="5%" >物业类型:</td>
			 <td width="28%" align="left" >
			 	<input type="radio" name="wyType" value="1"  >住宅</input>
			 	<input type="radio" name="wyType" value="2" >商铺</input>
			 	<input type="radio" name="wyType" value="3" >别墅</input>
			 	<input type="radio" name="wyType" value="4" >写字楼</input>
			 	<input type="radio" name="wyType" value="5" >其他</input>
			</td>
			 <td width="5%">物业费:</td>
			<td width="22%" align="left" ><input  type="text"  id="wyFee" name="wyFee" value="${estateInfo.wyFee}"/>元/平方米</td>
		    </tr>
		    <tr>
			 <td width="5%" >开发商:</td>
			 <td width="25%" align="left" ><input  type="text"  id="devCompany" name="devCompany" value="${estateInfo.devCompany}"/></td>
			 <td width="5%" >建筑年代:</td>
			 <td width="25%" align="left" ><input  type="text"  id="buildYear" name="buildYear" value="${estateInfo.buildYear}" readonly="readonly"/></td>
			 <td width="5%">建筑面积:</td>
			<td width="25%" align="left" ><input  type="text"  id="buildArea" name="buildArea" value="${estateInfo.buildArea}"/></td>
		    </tr>	
		     <tr>
			 <td width="5%" >占地面积:</td>
			 <td width="25%" align="left" ><input  type="text"  id="area" name="area" value="${estateInfo.area}"/></td>
			 <td width="5%" >容积率:</td>
			 <td width="25%" align="left" ><input  type="text"  id="areaRate" name="areaRate" value="${estateInfo.areaRate}"/></td>
			 <td width="5%">绿化率:</td>
			<td width="25%" align="left" ><input  type="text"  id="greenRate" name="greenRate" value="${estateInfo.greenRate}"/></td>
		    </tr>	
		     <tr>
			 <td width="5%" >车位信息:</td>
			 <td width="25%" align="left" ><input  type="text"  id="carInfo" name="carInfo" value="${estateInfo.carInfo}"/></td>
			 <td width="5%" >小区经度:</td>
			 <td width="25%" align="left" ><input  type="text"  id="lon" name="lon" value="${estateInfo.lon }"/></td>
			 <td width="5%">小区纬度:</td>
			<td width="25%" align="left" ><input  type="text"  id="lat" name="lat" value="${estateInfo.lat}"/></td>
		    </tr>	
	</table>
	

		 <fieldset class="xt_xqxg">
		 	<legend>教育配套</legend>
		 	<table>
			<tr>
			 <td width="5%" >大学:</td>
			 <td width="80%" align="left" ><input style="width:90%" type="text"  id="university" name="university" size="80" value="${estateInfo.university}"/></td>
		    </tr>
		    <tr>
			 <td width="5%" >中小学:</td>
			 <td width="80%" align="left" ><input style="width:90%"   type="text"  id="school" name="school" size="80" value="${estateInfo.school}"/></td>
		    </tr>
		    <tr>
			 <td width="5%" >幼儿园:</td>
			 <td width="80%" align="left" ><input style="width:90%"   type="text"  id="nursery" name="nursery" size="80" value="${estateInfo.nursery}"/></td>
		    </tr>
		    </table>
		 </fieldset>
		 <fieldset class="xt_xqxg">
		 	<legend>商业配套</legend>
		 	<table>
			<tr>
			 <td width="5%" >商场:</td>
			 <td width="80%" align="left" ><input style="width:90%"   type="text"  id="market" name="market" size="80" value="${estateInfo.market}"/></td>
		    </tr>
		    <tr>
			 <td width="5%" >医院:</td>
			 <td width="80%" align="left" ><input style="width:90%"   type="text"  id="hospital" name="hospital" size="80" value="${estateInfo.hospital}"/></td>
		    </tr>
		    <tr>
			 <td width="5%" >邮局:</td>
			 <td width="80%" align="left" ><input style="width:90%"   type="text"  id="postOffice" name="postOffice" size="80" value="${estateInfo.postOffice}"/></td>
		    </tr>
		    <tr>
			 <td width="5%" >银行:</td>
			 <td width="80%" align="left" ><input style="width:90%"   type="text"  id="bank" name="bank" size="80" value="${estateInfo.bank}"/></td>
		    </tr>
		    <tr>
			 <td width="5%" >其他:</td>
			 <td width="80%" align="left" ><input style="width:90%"   type="text"  id="otherInfo" name="otherInfo" size="80" value="${estateInfo.otherInfo}"/></td>
		    </tr>
		    </table>
		 </fieldset>
		  <fieldset class="xt_xqxg">
		 	<legend>交通配套</legend>
		 	<table>
			<tr>
			 <td width="5%" >公交:</td>
			 <td width="80%" align="left" ><input style="width:90%"   type="text"  id="bus" name="bus" size="80" value="${estateInfo.bus}"/></td>
		    </tr>
		    <tr>
			 <td width="5%" >地铁:</td>
			 <td width="80%" align="left" ><input style="width:90%"   type="text"  id="subWay" name="subWay" size="80" value="${estateInfo.subWay}"/></td>
		    </tr>
		    </table>
		 </fieldset>
		 <table border="1" cellpadding="3" cellspacing="1" width="80%" align="center" style="margin-top:10px">
		 <tr>
			 <td width="80px"  style="border-right:none;border-bottom:none;">&nbsp;项目介绍:</td>
			 <td align="left" style="border-bottom:none;"><textarea name="desc" rows="5" cols="98" >${estateInfo.desc}</textarea></td>
		    </tr> 

	
	        <tr>
	        <td colspan="2" align="center"><input type="button" value="保存" onclick="save()"/></td>
	        </tr>
	</table>	
	</form>
</div>
