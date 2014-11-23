<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
<!--
function search(){
	var resaleForm = document.getElementById("resaleForm");
	var pageNo = document.getElementById("page.pageNo");
	pageNo.value = 1;

	if(resaleForm!=null){
		resaleForm.submit();
	}
	
}
//-->
</script>
<div style="width: 100%;text-align: left;">当前位置：房源管理 &gt; 二手房管理 &gt; 房源审核</div>  
<br />
 <div id="tabs" class="tabs">  

     <ul>  

         <li><a href="/resale/verify/allresale/list" style="background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%">房源管理</a></li>  
 		<li ><a href="/resale/verify/baseInfoList" style="background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%" >标题描述审核</a></li>  


     </ul> 
</div>

<br />

<form action="/resale/verify/list" method="post" id="resaleForm" >

<div class="mag">
<div class="search" >
房源ID:<input type="text" name="id" value="${param.id }" />
&nbsp;经纪人ID:<input type="text" name="authorid" value="${param.authorid }" />
&nbsp;姓名:<input type="text" name="name" value="${param.name }" />
&nbsp;手机号:<input type="text" name="mobile" value="${param.mobile}" />
&nbsp;信息标题:<input type="text" name="title" value="${param.title }" />
<input type="button" onclick="search();" value="查找" /><br/>
&nbsp;只搜索北京房源:
<c:choose>
<c:when test="${param.onlyBeiJing}">
	<input id="onlyBeiJing" type="checkbox" value="true" checked="checked" name="onlyBeiJing" onclick="search();"> 
</c:when>
<c:otherwise>
	<input id="onlyBeiJing" type="checkbox" value="true"  name="onlyBeiJing" onclick="search();"> 
</c:otherwise>
</c:choose>


</div>
</div>

		<div class="mag">

			<div class="todo">

				<table>
					<thead>
						<tr>
							<th style=" width:12.5%; text-align:center"></th>
							<th style=" width:12.5%; text-align:center">房源ID</th>
							<th style=" width:12.5%; text-align:center">经纪人ID</th>
							<th style=" width:12.5%; text-align:center">姓名</th>
							<th style=" width:12.5%; text-align:center">电话</th>
							<th style=" width:12.5%; text-align:center">公司与门店</th>
							<th style=" width:12.5%; text-align:center">信息标题</th>
							<th style=" width:12.5%; text-align:center">图片</th>
							<th style=" width:12.5%; text-align:center">操作</th>
						</tr>
					</thead>
					<tbody class="rasent">
						<c:forEach items="${resaleList}" var="list">
								<tr >
								<td style=" width:12.5%; text-align:center"><input type="checkbox" name="ids" value="${list.id}" /></td>
								<td style=" width:12.5%; text-align:center"><a href="http://${list.cityDir}.${applicationScope.sysDomain}/ershoufang/${list.id}-0.html" target="_blank">${list.id}</a></td>
									<td style=" width:12.5%; text-align:center"><a href="javascript:postform('/agentManage/search',{type:'id',searchtxt:'${list.authorid}'});" >${list.authorid}</a></td>
									<td style=" width:12.5%; text-align:center"> <a href="${applicationScope.urlInfo}/${list.cityDir}/${list.authorid}/info/" target="_blank">${list.authorName}</a></td>
									<td style=" width:12.5%; text-align:center">${list.authorMobile}
													<br />${list.authorCallnumber}
									</td>
									<td style=" width:12.5%; text-align:center">
									${list.brand}<br />
									${list.brandAddress}
									</td>
									<td style=" width:12.5%; text-align:center">
									<a href="javascript:void(0)" class="link" onclick="openCheck('${list.id}')"  >${list.title}</a>
									<div id="box${list.id}" style="display: none;" >
										<table style="width: 95%;" >
											<tr>
												<th style="width: 25%; text-align: center">房源信息</th>
												<th style="width: 25%; text-align: center">户型图</th>
												<th style="width: 25%; text-align: center">室内图</th>
												<th style="width: 25%; text-align: center">小区图</th>
											</tr>
											<tr style="background-color: #FFFFFF">
												
												<td style="width: 25%; text-align: left; vertical-align: top;">
													<div style="width: 315px; padding: 5px; white-space: normal; line-height: 18px; text-align: left; font-size: 12px;">
														<h3>${list.title}(id:${list.id})</h3>
														<ul>
															<li>总价：<strong>${list.price}</strong>万</li>
															<li class="price">单价：<span><fmt:formatNumber
																		type="number" pattern="0"
																		value="${list.price/list.area*10000}"
																		maxFractionDigits="0" />
															</span>/㎡</li>
															<li>户型：${list.flattype}</li>
															<li class="area">面积：<span>${list.area}</span>平米</li>
															<li>楼层：${list.floorLabel}</li>
															<li>年份：${list.completion}年</li>
															<li>小区：${list.estatename}</li>
															<li>地址：${list.addr}</li>
															<c:if test="${list.extinfo.length() == 0 }">
																<li class="isempty">没写描述信息</li>
															</c:if>
															<c:if test="${list.extinfo.length() > 0 }">
																<li>房屋描述：${list.extinfo}</li>
															</c:if>
														</ul>
													</div></td>
												<td
													style="width: 25%; text-align: center; vertical-align: top;">
													<c:forEach items="${list.houseImgUrls}" var="img">
														<img src="${img.mURL}" />
													</c:forEach></td>
												<td
													style="width: 25%; text-align: center; vertical-align: top;">
													<c:forEach items="${list.roomImgUrls}" var="img">
														<div>
															<img src="${img.mURL}"
																width="200px" height="150" />
														</div>
													</c:forEach></td>
												<td
													style="width: 25%; text-align: center; vertical-align: top;">
													<c:forEach items="${list.estateImgUrls}" var="img">
														<div>
															<img src="${img.mURL}"
																width="200px" height="150" />
														</div>
													</c:forEach></td>
	
											</tr>
										</table>
	
									</div>
									
									</td>
									<td style=" width:12.5%; text-align:center">
									<c:if test="${list.hxNum>0}" >
										<a href="javascript:openImgWindow('/resale/house/photo/${list.id}/1')"> 户型图</a>(${list.hxNum})
									</c:if>
									<c:if test="${list.hxNum<=0}" >
										户型图(${list.hxNum})
									</c:if>
									<br/>
									
									<c:if test="${list.snNum>0}" >
										<a href="javascript:openImgWindow('/resale/house/photo/${list.id}/2')"> 室内图</a>(${list.snNum})
									</c:if>
									<c:if test="${list.snNum<=0}" >
										室内图(${list.snNum})
									</c:if>
									<br/>
									<c:if test="${ list.xqNum>0}" >
										<a href="javascript:openImgWindow('/resale/house/photo/${list.id}/3')"> 小区图</a> (${list.xqNum})
									</c:if>
									<c:if test="${list.xqNum<=0 }" >
										小区图(${list.xqNum})
									</c:if>
									<br/></td>
									

									<td style=" width:12.5%; text-align:center" ><a href="javascript:openReject(${list.id})" > []违规]</a>&nbsp;<a href="javascript:pass(${list.id})" >[通过]</a>&nbsp;<a href="/agentManage/house/${list.cityDir}/${list.authorid}/0" >[查看]</a>&nbsp;<a href="/resale/house/${list.authorid}" > [图片]</a></td>


								</tr>
							</c:forEach>
					</tbody>

				</table>

			</div>

		</div>

		<div  style="text-align: left;" >
		全选 <input type="checkbox" onclick="checkAll(this)" /> &nbsp;
	<input type="button" onclick="openBatchReject();" value="违规房源" />&nbsp;
	<input type="button" onclick="batchPass();" value="通过" />
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
	$("#divCheck").dialog({show : "slide",bgiframe : true,autoOpen : false,height :h*0.8,width : '80%',title : "查看房源",modal : true,resizable : false});
	// 初始化弹出窗口
	$("#divBatchReject").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 300,width : 500,title : "查看房源",modal : true,resizable : false});
	// 初始化弹出窗口
	$("#divBatchDelete").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 300,width : 500,title : "查看房源",modal : true,resizable : false});
	// 初始化弹出窗口
	$("#divReject").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 300,width : 500,title : "查看房源",modal : true,resizable : false});
	// 初始化弹出窗口
	$("#divDelete").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 300,width : 500,title : "查看房源",modal : true,resizable : false});
});
//显示拒绝窗口
function openBatchReject() {
	$("*").stop();
	$("#divBatchReject").dialog("close");
	$("#divBatchReject").dialog("option", "position", "center");
	$("#divBatchReject").dialog("open");
}
//显示拒绝窗口
function openBatchDelete() {
	$("*").stop();
	$("#divBatchDelete").dialog("close");
	$("#divBatchDelete").dialog("option", "position", "center");
	$("#divBatchDelete").dialog("open");
}
function rejectBatchSave() {
	var tempIds = document.getElementsByName("ids");
	var checkNum = 0;
	var ids = "";
	for(var j = 0 ;j<tempIds.length;j++){
		if(tempIds[j].checked){
			if(checkNum==0){
				ids = tempIds[j].value;
			}else{
				ids =ids+","+tempIds[j].value;
			}
			checkNum++;
		}
	}
	if(ids.length==0){
		alert("请至少选择一个房源!");
		return;
	}
	
	var reasons = "";
	var i = 1;
	while(true){
		var reason = $("#batchReasons_"+i);
		if(reason[0]){
			if(reason.is(':checked')){
				reasons +=$("#batchReasons_"+i+"_value").html()+ "<br /> ";
			}
		}else{
			break;
		}
		i++;
		if(i>30){
			break;
		}
	}
	reasons += $("#batchReasons").val().trim();
	if(reasons.trim()==""){
		alert("请选择或填写违规原因！");
		return ;
	}

		$.ajax({
			type : "post",
			async : false,
			url : "/resale/verifyReq/batch",
			dataType : "json",
			data : {"ids" : ids,"type":0,"reasons" : reasons},
			success : function(data) {
				if(data.error){
					alert(data.error);
					return;
				}
				if(data.info=="success"){
					alert("所选房源成功设置为违规房源!");
				}else{
					alert("操作失败!");
					return ;
				};
				$("#divBatchReject").dialog("close");
				var currentNo = document.getElementById("currentNo");
				var pageNo = document.getElementById("page.pageNo");
				var form = getForm();
				if(form!=null){
					pageNo.value = currentNo.value;
					form.submit();
				}
			}
		});


}

function deleteBatchSave(){
	var tempIds = document.getElementsByName("ids");
	var checkNum = 0;
	var ids = "";
	for(var j = 0 ;j<tempIds.length;j++){
		if(tempIds[j].checked){
			if(checkNum==0){
				ids = tempIds[j].value;
			}else{
				ids =ids+","+tempIds[j].value;
			}
			checkNum++;
		}
	}
	if(ids.length==0){
		alert("请至少选择一个房源!");
		return;
	}
	var reasons = "";
	var i = 1;
	while(true){
		var reason = $("#deleteBatchReasons_"+i);
		if(reason[0]){
			if(reason.is(':checked')){
				reasons +=$("#deleteBatchReasons_"+i+"_value").html()+ "<br /> ";
			}
		}else{
			break;
		}
		i++;
		if(i>30){
			break;
		}
	}
	reasons += $("#deleteBatchReasons").val().trim();
	if(reasons.trim()==""){
		alert("请选择或填写删除原因！");
		return ;
	}

		$.ajax({
			type : "post",
			async : false,
			url : "/resale/verifyReq/batch",
			dataType : "json",
			data : {"ids" : ids,"type":1,"reasons" : reasons},
			success : function(data) {
				if(data.error){
					alert(data.error);
					return;
				}
				if(data.info=="success"){
					alert("删除成功");
				}else{
					alert("操作失败!");
					return ;
				};
				$("#divDelete").dialog("close");
				var currentNo = document.getElementById("currentNo");
				var pageNo = document.getElementById("page.pageNo");
				var form = getForm();
				if(form!=null){
					pageNo.value = currentNo.value;
					form.submit();
				}
			}
		});

}
function batchPass(){
	var tempIds = document.getElementsByName("ids");
	var checkNum = 0;
	var ids = "";
	for(var j = 0 ;j<tempIds.length;j++){
		if(tempIds[j].checked){
			if(checkNum==0){
				ids = tempIds[j].value;
			}else{
				ids =ids+","+tempIds[j].value;
			}
			checkNum++;
		}
	}
	if(ids.length==0){
		alert("请至少选择一个房源!");
		return;
	}
	if(confirm("所选房源确定审核通过?")){
		
		$.ajax({
			type : "post",
			async : false,
			url : "/resale/verifyReq/batch",
			dataType : "json",
			data : {"ids" : ids,"type":2},
			success : function(data) {
				if(data.error){
					alert(data.error);
					return;
				}
				if(data.info=="success"){
					alert("所选房源审核通过");
				}else{
					alert("操作失败!");
					return ;
				};
				var currentNo = document.getElementById("currentNo");
				var pageNo = document.getElementById("page.pageNo");
				var form = getForm();
				if(form!=null){
					pageNo.value = currentNo.value;
					form.submit();
				}
			}
		});
	}
}
//显示审核窗口
function openCheck(id) {
	$("*").stop();
	$("#resaleId").html(id);
	$("#divCheck").html($("#box"+id).html());
	$("#divCheck").dialog("close");
	$("#divCheck").dialog("option", "position", "center");
	$("#divCheck").dialog("open");
}
//显示拒绝窗口
function openRejectForCheck() {
	$("*").stop();
	$("#divReject").dialog("close");
	$("#divReject").dialog("option", "position", "center");
	$("#divReject").dialog("open");
}
//显示拒绝窗口
function openReject(id) {
	$("*").stop();
	$("#resaleId").html(id);
	$("#divReject").dialog("close");
	$("#divReject").dialog("option", "position", "center");
	$("#divReject").dialog("open");
}
//显示拒绝窗口
function openDelete(id) {
	$("*").stop();
	$("#deleteresaleId").html(id);
	$("#divDelete").dialog("close");
	$("#divDelete").dialog("option", "position", "center");
	$("#divDelete").dialog("open");
}
function rejectSave() {

	var id = $("#resaleId").html();
	var reasons = "";
	var i = 1;
	while(true){
		var reason = $("#reasons_"+i);
		if(reason[0]){
			if(reason.is(':checked')){
				reasons +=$("#reasons_"+i+"_value").html()+ "<br /> ";
			}
		}else{
			break;
		}
		i++;
		if(i>30){
			break;
		}
	}
	reasons += $("#reasons").val().trim();
	if(reasons.trim()==""){
		alert("请选择或填写违规原因！");
		return ;
	}

		$.ajax({
			type : "post",
			async : false,
			url : "/resale/verifyReq",
			dataType : "json",
			data : {"id" : id,"type":0,"reasons" : reasons},
			success : function(data) {
				if(data.error){
					alert(data.error);
					return;
				}
				if(data.info=="success"){
					alert("房源(ID:"+id+")成功设置为违规房源");
				}else{
					alert("操作失败!");
					return ;
				};
				$("#divReject").dialog("close");
				var currentNo = document.getElementById("currentNo");
				var pageNo = document.getElementById("page.pageNo");
				var form = getForm();
				if(form!=null){
					pageNo.value = currentNo.value;
					form.submit();
				}
			}
		});

}

function deleteSave(){
	var id = $("#deleteResaleId").html();
	var reasons = "";
	var i = 1;
	while(true){
		var reason = $("#deleteReasons_"+i);
		if(reason[0]){
			if(reason.is(':checked')){
				reasons +=$("#deleteReasons_"+i+"_value").html()+ "<br /> ";
			}
		}else{
			break;
		}
		i++;
		if(i>30){
			break;
		}
	}
	reasons += $("#deleteReasons").val().trim();
	if(reasons.trim()==""){
		alert("请选择或填写删除原因！");
		return ;
	}

		$.ajax({
			type : "post",
			async : false,
			url : "/resale/verifyReq",
			dataType : "json",
			data : {"id" : id,"type":1,"reasons" : reasons},
			success : function(data) {
				if(data.error){
					alert(data.error);
					return;
				}
				if(data.info=="success"){
					alert("房源(ID:"+id+")删除成功");
				}else{
					alert("操作失败!");
					return ;
				};
				$("#divDelete").dialog("close");
				var currentNo = document.getElementById("currentNo");
				var pageNo = document.getElementById("page.pageNo");
				var form = getForm();
				if(form!=null){
					pageNo.value = currentNo.value;
					form.submit();
				}
			}
		});

}
function passForCheck(){
	var id = $("#resaleId").html();
	pass(id);
	
}

function pass(id){
	
	if(confirm("房源(ID:"+id+")确定审核通过?")){
		
		$.ajax({
			type : "post",
			async : false,
			url : "/resale/verifyReq",
			dataType : "json",
			data : {"id" : id,"type":2},
			success : function(data) {
				if(data.info=="success"){
					alert("房源(ID:"+id+")审核通过");
				}else{
					alert("操作失败!");
					return ;
				};
				var currentNo = document.getElementById("currentNo");
				var pageNo = document.getElementById("page.pageNo");
				var form = getForm();
				if(form!=null){
					pageNo.value = currentNo.value;
					form.submit();
				}
			}
		});
	}
}
//-->
</script>


<div id="divBatchReject">
<table>
	<tr>
		<td style="text-align: left" valign="top">
		违规原因:&nbsp;
		</td>
		<td style="text-align: left" valign="top">
		<c:forEach items="${reasons}" var="list" varStatus="vs">
         			<input type="checkbox" id="batchReasons_${vs.index+1 }"><label id="batchReasons_${vs.index+1 }_value">${list.reason}</label><br />
         		</c:forEach>
		</td>
	</tr>
	<tr>
	<td valign="top">
		其它原因:&nbsp;
		</td>
		<td style="text-align: left" >
		<textarea class="rejectTextArea" id="batchReasons" name="batchReasons" cols="40" rows="3"></textarea>
		</td>
	</tr>	
</table>
	</div>
	<div id="divBatchDelete">
<table>
	<tr>
		<td style="text-align: left" valign="top">
		删除原因:&nbsp;
		</td>
		<td style="text-align: left" valign="top">
		<c:forEach items="${deleteReasons}" var="list" varStatus="vs">
         			<input type="checkbox" id="batchDeleteReasons_${vs.index+1 }"><label id="batchDeleteReasons_${vs.index+1 }_value">${list.reason}</label><br />
         		</c:forEach>
		</td>
	</tr>
	<tr>
	<td valign="top">
		其它原因:&nbsp;
		</td>
		<td style="text-align: left" >
		<textarea class="rejectTextArea" id="batchDeleteReasons" name="batchDeleteReasons" cols="40" rows="3"></textarea>
		</td>
	</tr>	
</table>
</div>
<div id = "divCheck" class="todo" style="text-align: center;" >
	
</div>


<div id="divReject" >
<table>
	<tr>
		<td>
		房源ID:&nbsp;
		</td>
		<td style="text-align: left">
		<label id="resaleId"></label>
		</td>
	</tr>
	<tr>
		<td style="text-align: left" valign="top">
		违规原因:&nbsp;
		</td>
		<td style="text-align: left" valign="top">
		<c:forEach items="${reasons}" var="list" varStatus="vs">
         			<input type="checkbox" id="reasons_${vs.index+1 }"><label id="reasons_${vs.index+1 }_value">${list.reason}</label><br />
         		</c:forEach>
		</td>
	</tr>
	<tr>
	<td valign="top">
		其它原因:&nbsp;
		</td>
		<td style="text-align: left" >
		<textarea class="rejectTextArea" id="reasons" name="reasons" cols="40" rows="3"></textarea>
		</td>
	</tr>	
</table>
	</div>
	<div id="divDelete">
<table>
	<tr>
		<td>
		房源ID:&nbsp;
		</td>
		<td style="text-align: left">
		<label id="deleteResaleId"></label>
		</td>
	</tr>
	<tr>
		<td style="text-align: left" valign="top">
		删除原因:&nbsp;
		</td>
		<td style="text-align: left" valign="top">
		<c:forEach items="${deleteReasons}" var="list" varStatus="vs">
         			<input type="checkbox" id="deleteReasons_${vs.index+1 }"><label id="deleteReasons_${vs.index+1 }_value">${list.reason}</label><br />
         		</c:forEach>
		</td>
	</tr>
	<tr>
	<td valign="top">
		其它原因:&nbsp;
		</td>
		<td style="text-align: left" >
		<textarea class="rejectTextArea" id="deleteReasons" name="deleteReasons" cols="40" rows="3"></textarea>
		</td>
	</tr>	
</table>
</div>