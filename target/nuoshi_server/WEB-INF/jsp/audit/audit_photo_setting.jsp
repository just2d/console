<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.taofang.com/tld/func" prefix="func"%>

<script type="text/javascript">

$(function() {
	$("#addPhotoSettingDiv").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 350,width : 400,title : "图片审核设置",modal : true,buttons : {"确定" : addPhotoSetting,"取消":close}});
});

function addPhotoSetting(){
	var cityId = $("#cityId").val();
	$.ajax({
		type : "post",
		async : false,
		data:$("#addPhotoSettingForm").serialize(),
		url : "/audit/photo/setting/update/"+cityId,
		dataType : "json",
		success : function(data) {
			if(data.error){
				alert(data.error);
				return ;
			}
			if(data.success){
				alert("设置成功");
				$("#myform").submit();
			}
			
		}
	});
	
	
}
 

function add(){
	$("#city").html($("#cityId").find("option:selected").text())
	$("*").stop();
	$("#addPhotoSettingDiv").dialog("close");
	$("#addPhotoSettingDiv").dialog("option", "position", "center");
	$("#addPhotoSettingDiv").dialog("open");
	
}
function doChange(){
	var cityId = $("#cityId").val();
	var myform = $("#myform");
	myform.attr("action","/audit/photo/setting/"+cityId);
	myform.submit();
}
function close(){
	$("*").stop();
	$("#addPhotoSettingDiv").dialog("close");
}
	// ajax获取城市、区域 
	function getDist(selectId,pid) {
		var selectElem = "#" + selectId;
		$.ajax({
			type : "GET",
			async : false,
			url : "/agentManage/ajax/zone/"+pid,
			dataType : "json",
			success : function(data) {
				$(selectElem).empty();
				var global = "";
				$(global).appendTo(selectElem);
				var list = data.distList;
				if (list != null && list.length > 0) {
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
	
</script>

<form id="myform" action="/audit/photo/setting/${cityId}" method="post">
	<input type="hidden" id="totalResult" name="totalResult" />
	<div style="text-align:left;">
		
		城市：
		<select id="cityId" name="cityId" style="width:100px; position:relative; z-index:9"  onchange="doChange();">
		</select>
		<input type="button" value="设置" onclick="add();"/>
	</div>
	
	<div class="todo">
		<table style="width:100%;">
			<thead>
				<tr>
					<th>城市</th>
					<th>违规率(%)</th>
					<th>抽取图片上限（张/日）</th>
					<th>周期抽取次数</th>
					<th>时间规则</th>
				</tr>
			</thead>
			<tbody align="center" >
				<c:choose>
					<c:when test="${not empty photoSettings}">
						<c:forEach items="${photoSettings}" var="t">
							<tr>
								 <td>${func:getName(t.cityId)}</td>
								<td>${t.illegalRate}</td>
								<td>${t.dayMaxPhotoCount}</td>
								<td>${t.auditCount}</td>
								<td>
									<c:if test="${t.timeRule==0 }">
									  本周期									</c:if>
									<c:if test="${t.timeRule==1 }">
									  下周期
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="4">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
</form>
 
 
 
 <div id="addPhotoSettingDiv" style="width:600px;display: none">
 <form id="addPhotoSettingForm">
 <table>
 
  <tr>
   <td align="left"> 城市:
   
   </td>
   <td align="left">
   	<span id="city" ></span>
   </td>
  </tr>
  <tr>
   <td align="left"> 时间规则:
   
   </td>
   <td align="left">
   		<select id="timeRule" name="timeRule" >
   			<option value="0">本周期</option>
   			<option value="1">下周期</option>
   		</select>
   </td>
  </tr>
   <tr>
   <td align="left" >
   违规率(%):
   </td>
   <td>
    <input  id="illegalRate" name="illegalRate" />
   </td>
  </tr>
  <tr>
   <td align="left"> 抽取图片上限（张/日）:
   
   </td>
   <td align="left">
   	<input  id="dayMaxPhotoCount" name="dayMaxPhotoCount" />
   </td>
  </tr>
  <tr>
   <td align="left"> 周期抽取次数:
   
   </td>
   <td align="left">
   	<input  id="auditCount" name="auditCount" />
   </td>
  </tr>
  
 </table>
 </form>
</div>
<script type="text/javascript">
	var cityId = ${requestScope.cityId};
	$(function(){
		getDist("cityId",0);
		if(cityId > 0) {
			setSelected("cityId",cityId);
		}
	});
</script>