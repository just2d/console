 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

$(document).ready(function(){
	var cityId = ${requestScope.cityId};
	if(cityId == null || cityId == "") {
		cityId = 0;
	}
	getDist("city",0);
	if(cityId > 0) {
		setSelected("city",cityId);
	}
});

function deleteExpert(id){
	var flag = confirm("你确定要删除此小区专家吗?");
	if(flag){
		$.ajax({
			type : "post",
			async : false,
			url : "/estateexpert/delete",
			dataType : "json",
			data : {"id" : id},
			success : function(data) {
				$("#estateExpertForm").submit();
			
			}
		});
	}
}

function download(){
	var form = document.getElementById("estateExpertForm");
	form.action="/estateexpert/download";
	form.submit();
	form.action="/estateexpert/list";
}

function editExpert(id,cityName,estateName,agentName,agentPhone,startTime,endTime,expertType){
	$("#u_id").val(id);
	$("#u_cityName").html(cityName);
	$("#u_estateName").html(estateName);
	$("#u_agentName").html(agentName);
	$("#u_agentPhone").html(agentPhone);
	$("#u_startTime").html(startTime);
	$("#u_startTimeStr").val(startTime);
	$("#u_endTime").val(endTime);
	setSelected("u_expertType", expertType);
	
	$("#updateExpert").dialog("close");
	$("#updateExpert").dialog("option", "position", "center");
	$("#updateExpert").dialog("open");
}

//-->
</script>
<div style="width: 100%;text-align: left;">当前位置：小区专家管理 &gt; 小区专家管理 </div>  
  <br />

  <form action="/estateexpert/list" method="post" id="estateExpertForm">
  <div class="mag">
     <br />
     <div class="search" >
<table>
	<tr>
		<td>城市:</td><td><select id="city" style="width:100px;" name="cityId" value="${param.cityId}"></select></td>
		<td>经纪人ID:</td><td><input type="text" name="agentId" style="width:100px;" value="${param.agentId }" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/></td>
		<td>经纪人姓名:</td><td><input type="text" name="agentName" style="width:100px;" value="${param.agentName }" /></td>
		<td>手机号:</td><td><input type="text" style="width:100px;" name="agentPhone" value="${param.agentPhone }" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/></td>
		<td>小区名字:</td><td><input type="text" name="estateName" id="estatename" style="width:100px;" value="${param.estateName }" /><span id="searchEstateInfo" style="color: red;display: none">请点击相匹配的小区或新建小区</span></td>
		<td><input type="submit" value="搜索">&nbsp;</td>
		<td><a href="javascript:download()">点击下载报表详情</a></td>
	</tr>
</table>

</div>
		</div>


			<div class="todo">

				<table>

					<thead>

						<tr>

							<th style=" width:7%; text-align:center">经纪人ID</th>
							<th style=" width:10%; text-align:center">姓名</th>
							<th style=" width:10%; text-align:center">电话</th>
							<th style=" width:10%; text-align:center">城市-区域</th>
							<th style=" width:10%; text-align:center">小区（专家数量）</th>
							<th style=" width:10%; text-align:center">级别</th>
							<th style=" width:10%; text-align:center">开始日期</th>
							<th style=" width:10%; text-align:center">结束日期</th>
							<th style=" width:12%; text-align:center">操作</th>
						</tr>
					</thead>

					<tbody class="rasent">

						<c:forEach items="${estateExpertList}" var="list">
								<tr >
								<td style=" text-align:center">${list.agentId}</td>
								<td style=" text-align:center">${list.agentName}</td>
								<td style=" text-align:center">${list.agentPhone}</td>
								<td style=" text-align:center">${list.cityName}-${list.distName }</td>
								<td style=" text-align:center"><a target="_blank" href="http://www.taofang.com/xq_${list.pinyin }">${list.estateName }</a>(${list.expertCount }) </td>
								<td style=" text-align:center"><c:if test="${list.expertType==1 }">金牌</c:if><c:if test="${list.expertType==2 }">银牌</c:if></td>
								<td style=" text-align:center">${list.startTimeStr}</td>
								<td style=" text-align:center">${list.endTimeStr}</td>
								<td> <a href="javascript:void(0);" onclick="editExpert(${list.id },'${list.cityName }','${list.estateName }','${list.agentName }','${list.agentPhone }','${list.startTimeStr }','${list.endTimeStr }','${list.expertType }');">编辑</a> &nbsp;&nbsp;&nbsp; <a href="javascript:void(0)" onclick="deleteExpert(${list.id })">撤销</a></td>
								</tr>
							</c:forEach>
					</tbody>

				</table>

			</div>

		<div class="page_and_btn" >
		
			<jsp:include page="/WEB-INF/snippets/page.jsp" />
		</div>
	
	</form>
<script type="text/javascript">
<!--
$(function() {
//初始化弹出窗口
$("#updateExpert").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 300,width : 500,title : "小区专家",modal : true,resizable : false,buttons : {"保存" : updateExpert}});
});
function updateExpert(){
	if($("#u_endTime").val()==''){ 
		alert("请选择结束日期"); 
		return;
	}
	var endTimes = $("#u_endTime").val().split("-");
	var startTimes = $("#u_startTimeStr").val().split("-");
	var endDate = new Date(endTimes[0],endTimes[1]-1,endTimes[2]);
	var startDate= new Date(startTimes[0],startTimes[1]-1,startTimes[2]);
	if(startDate>=endDate){
		alert("结束日期必须大于开始日期");
		$("#u_endTime").focus();
		return ;
	}
	
	$.ajax({
		type : "post",
		async : false,
		url : "/estateexpert/update",
		dataType : "json",
		data : {"id" : $("#u_id").val(),"endTimeStr":$("#u_endTime").val(),"expertType":$("#u_expertType").val()},
		success : function(data) {
			alert("编辑成功");
			$("#updateExpert").dialog("close");
			$("#estateExpertForm").submit();
		
		}
	});
}

//-->
</script>	
<div id="updateExpert" class="todo" style="display:none"">
<table>
<tr><td>城市</td><td id="u_cityName"></td><td>小区名称</td><td id="u_estateName"></td></tr>
<tr><td>姓名</td><td id="u_agentName"></td><td>手机号</td><td id="u_agentPhone"></td></tr>
<tr><td>开始日期</td><td id="u_startTime"></td><td>结束日期</td><td><input readonly="readonly" id="u_endTime" onclick="InitSelectDate(1,this,'yyyy-MM-dd','')" /></td></tr>
<tr><td>等级</td><td><select id="u_expertType"><option value="1">金牌</option><option value="2">银牌</option></select></td> <td>&nbsp;<input type="hidden" id="u_startTimeStr" value=""/><input  type="hidden" id="u_id" value=""/></td></tr>
</table>
</div>