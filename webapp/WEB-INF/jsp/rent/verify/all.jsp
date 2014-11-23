 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="/js/common/jsdate.js"></script>
<script type="text/javascript">
<!--
	function search() {
		var rentForm = document.getElementById("rentForm");
		var pageNo = document.getElementById("page.pageNo");
		pageNo.value = 1;

		if (rentForm != null) {
			rentForm.submit();
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

	
$(document).ready(function(){
	$("#houseSt").val('${param.type}');
	$("#houseSt").bind("change",selChange);
	var cityId = ${requestScope.cityId};
	if(cityId == null || cityId == "") {
		cityId = 0;
	}
	getDist("city",0);
	if(cityId > 0) {
		setSelected("city",cityId);
	}
});

function selChange(){
	var form  = document.getElementById("rentForm");
	if(form!=null){
		form.submit();
	}
}
//-->
</script>
<div style="width: 100%;text-align: left;">当前位置：房源管理 &gt; 出租房管理 &gt; 租房管理</div>  
<br />
 <div id="tabs" class="tabs">  

     <ul>  


         <li class="tabs_active" ><a href="/rent/verify/allrent/list" >租房管理</a></li>  
         <li class="tabs_active" style="background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%" ><a href="/rent/verify/baseInfoList"  >标题描述审核</a></li>  
         <li ><a href="/rent/verify/list/verifyPhoto/1" style="background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%" >户型图审核</a></li>  
         <li ><a href="/rent/verify/list/verifyPhoto/2" style="background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%" >室内图审核</a></li>  
         <li ><a href="/rent/verify/list/verifyPhoto/3" style="background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%" >小区图审核</a></li> 


     </ul> 

 </div> 
<br />

	<form action="/rent/verify/allrent/list" method="post" id="rentForm" >

		<div class="mag">
			<div class="search">
			<table>
	<tr>
		<td>房源ID:</td><td><input type="text" name="id" style="width:100px;" value="${param.id }" /></td>
		<td>经纪人ID:</td><td><input type="text" name="authorid" style="width:100px;" value="${param.authorid }" /></td>
		<td>姓名:</td><td><input type="text" name="name" style="width:100px;" value="${param.name }" /></td>
		<td>手机号:</td><td><input type="text" style="width:100px;" name="authorphone" value="${param.authorphone }" /></td>
	</tr>
	<tr>
		<td>信息标题:</td><td><input type="text" name="title" value="${param.title }" /></td>
		<td>城市:</td><td><select id="city" style="width:100px;" name="cityId" value="${param.cityId}"></select></td>
		<td>起始日期:</td><td><input id="start_date" class="def" name="startDate" style="width:100px;" class="dateCss" type="text" onclick="SelectDate(this,'yyyy-MM-dd','起始日期')" readonly="readonly" value="${param.startDate}"/></td>
		<td>终止日期:</td><td><input id="end_date" class="def" name="endDate" style="width:100px;" class="dateCss" type="text" onclick="SelectDate(this,'yyyy-MM-dd','终止日期')" readonly="readonly" value="${param.endDate}"/></td>
		<td>&nbsp;</td><td><input type="button" onclick="search();" value="查找" /></td>
	</tr>
</table>
			</div>


		</div>

		<div class="mag">

			<div class="todo">

				<table>

					<thead>

						<tr>

							<th style=" width:4%; text-align:center">
							</th>
							<th style=" width:7%; text-align:center">房源ID</th>
							<th style=" width:7%; text-align:center">经纪人ID</th>
							<th style=" width:10%; text-align:center">姓名</th>
							<th style=" width:10%; text-align:center">电话</th>
							<th style=" width:10%; text-align:center">公司与门店</th>
							<th style=" width:10%; text-align:center">信息标题</th>
							<th style=" width:3%; text-align:center">已用标签</th>
							<th style=" width:10%; text-align:center">图片</th>
							<th style=" width:5%; text-align:center">
								<select id="houseSt" name="type" style="border:0px solid #CAD9EA;white-space: nowrap;background-color: #B6E6F9;line-height:30px; font-size:12px; font-family:微软雅黑;font-weight: bold;">
									<option value="0">房源状态</option>
									<option value="1">在线房源</option>
									<option value="5">在线房源(未审核)</option>
									<option value="6">在线房源(通过)</option>
									<option value="2">待发布房源</option>
									<option value="3">过期房源</option>
									<option value="4">违规房源</option>
									<option value="9">未审核经纪人房源</option>
									<option value="7">删除房源</option>
									<option value="8">草稿箱</option>
								 </select>
							</th>
							<c:if test="${sessionScope.sessionUser.isPass('/verify/allrent/list/audit') }">
								<th style=" width:5%; text-align:center">审核操作</th>
							</c:if>	
							<th>现有分值</th>
							<c:if test="${sessionScope.sessionUser.isPass('/rent/addExtraScore') }">
							<th style=" width:10%; text-align:center">加分操作</th>
							</c:if>
						</tr>

					</thead>

					<tbody class="rasent">
					
						<c:forEach items="${rentList}" var="list">
							<tr>

								<td style="  text-align:center"><input type="checkbox" name="ids" value="${list.id}" />
								</td>
								<td style="  text-align:center"><a href="http://${list.cityDir}.${applicationScope.sysDomain}/zufang/${list.id}-0.html" target="_blank">${list.id}</a></td>
								<td style=" text-align:center"><a
									href="javascript:postform('/agentManage/search',{type:'id',searchtxt:'${list.authorid}'});">${list.authorid}</a>
								</td>
								<td style="text-align:center"> <a href="${applicationScope.urlInfo}/${list.cityDir}/${list.authorid}/info/" target="_blank">${list.authorName}</a></td>
								<td style="text-align:center">${list.authorMobile} </td>
								<td style="text-align:center">${list.brand}<br /> ${list.brandAddress}</td>
								<td style="text-align:center">
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
													<div style="width: 315px;padding: 5px;white-space: normal;line-height:20px;text-align: left;font-size:12px;">
														 <h3>${list.title}(id:${list.id})</h3>
													<ul>
							                            <li class="rent">租金：<span>${list.price}</span>元/月(<c:if test="${list.flatting == 1}">整租</c:if><c:if test="${list.flatting == 2}">合租</c:if><c:if test="${list.flatting == 3}">床位</c:if>)</li>
							                            <li class="estate" id="eid_${list.estateid}"><span>小区：</span>${list.estatename}</li>
							                            <li class="address"><span>地址：</span>${list.brandAddress}</li>
							                            <li><span>面积：</span>${list.area}平米</li>
							                            <li><span>户型：</span>${list.flattype}</li>
							                            <li><span>楼层：</span>${list.floorLabel}</li>
							                            <li><span>朝向：</span>${list.houseFaceto}</li>
							                            <li><span>装修：</span>${list.houseDecoration}</li>
							                            <li><span>年代：</span>${list.completion}年</li>
							                            <li><span>配置：</span>${list.houseEquipment.toString()}</li>
							                            <li><span>设施：</span>${list.houseFacility.toString()}</li>
							                            <c:if test="${list.extinfo.length() == 0 }">
							                                <li>暂时没有描述信息</li>
							                            </c:if>
							                            <c:if test="${list.extinfo.length() > 0 }">
							                                <li>详细描述：${list.extinfo}</li>
							                            </c:if>
							
							                        </ul>		
														</div>
													</td>
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
								<td>${list.houseLabelName }</td>
								<td style="text-align:center"><c:if test="${list.hxNum>0 || sessionScope.sessionUser.isPass('/verify/allrent/list/audit')}">
										<a
											href="javascript:openImgWindow('/rent/house/photo/${list.id}/1')">
											户型图</a>(${list.hxNum})
										</c:if> <c:if test="${list.hxNum<=0 && !sessionScope.sessionUser.isPass('/verify/allrent/list/audit')}">
											户型图(${list.hxNum})
										</c:if> <br /> <c:if test="${list.snNum>0 || sessionScope.sessionUser.isPass('/verify/allrent/list/audit')}">
										<a
											href="javascript:openImgWindow('/rent/house/photo/${list.id}/2')">
											室内图</a>(${list.snNum})
										</c:if> <c:if test="${list.snNum<=0 && !sessionScope.sessionUser.isPass('/verify/allrent/list/audit')}">
											室内图(${list.snNum})
										</c:if> <br /> <c:if test="${ list.xqNum>0 || sessionScope.sessionUser.isPass('/verify/allrent/list/audit')}">
										<a
											href="javascript:openImgWindow('/rent/house/photo/${list.id}/3')">
											小区图</a> (${list.xqNum})
										</c:if> <c:if test="${list.xqNum<=0 && !sessionScope.sessionUser.isPass('/verify/allrent/list/audit')}">
											小区图(${list.xqNum})
										</c:if> <br />
								</td>
								<td style="text-align:center" >${list.houseStatusLabel }</td>
								<c:if test="${sessionScope.sessionUser.isPass('/verify/allrent/list/audit') }">
								<td style=" text-align:center" >
									<c:choose>
										<c:when test="${list.treated&&!list.approvaled}"> 违规  </c:when>
										<c:otherwise><a href="javascript:openReject(${list.id})" > [违规]</a> </c:otherwise>
									</c:choose> &nbsp;
									<c:choose>
										<c:when test="${list.approvaled&&list.treated }" > 已通过</c:when>
										<c:otherwise> <a href="javascript:pass(${list.id})" >[通过]</a></c:otherwise>
									</c:choose> &nbsp;
								</td>
								</c:if>
								<td>${list.searchScore }</td>
								<c:if test="${sessionScope.sessionUser.isPass('/rent/addExtraScore') }">
									<td>
									 <a href="javascript:openAddExtraScroe(${list.id})" >[加分]</a>&nbsp;<a href="javascript:showExrtaScoreHistory(${list.id})" >[加分历史]</a>
									</td>
									</c:if>
							</tr>
						</c:forEach>
					</tbody>

				</table>

			</div>

		</div>

	<div  style="text-align: left;" >
	<!-- 全选 <input type="checkbox" onclick="checkAll(this)" /> &nbsp;
		<input type="button" onclick="openBatchReject();" value="违规房源" />&nbsp;
		<input type="button" onclick="batchPass();" value="通过" /> -->
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
	$("#divReject").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 300,width : 500,title :  "违规房源",modal : true,resizable : false,buttons : {"保存违规原因" : rejectSave}});
	// 初始化弹出窗口
	$("#divDelete").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 300,width : 500,title : "查看房源",modal : true,resizable : false});
	$("#divAddExtraScore").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 300,width : 500,title : "加分操作",modal : true,resizable : false,buttons : {"保存" : saveExtraScore}});
	$("#divExtraScoreHistory").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 300,width : 500,title : "加分历史",modal : true,resizable : false});
});
//显示审核窗口
function openCheck(id) {
	$("*").stop();
	$("#rentId").html(id);
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
function openBatchReject() {
	$("*").stop();
	$("#divBatchReject").dialog("close");
	$("#divBatchReject").dialog("option", "position", "center");
	$("#divBatchReject").dialog("open");
}
//显示加分窗口
function openAddExtraScroe(id) {
	$("*").stop();
	$("#extraScoreRentId").html(id);
	$("#divAddExtraScore").dialog("close");
	$("#divAddExtraScore").dialog("option", "position", "center");
	$("#divAddExtraScore").dialog("open");
}

function showExrtaScoreHistory(id){
	
	$("*").stop();
	$("#divExtraScoreHistory").html("");
	$("#divExtraScoreHistory").dialog("close");
	$("#divExtraScoreHistory").dialog("option", "position", "center");
	 
		
		$.ajax({
			type : "post",
			async : false,
			url : "/rent/getExtraScoreHistory/"+id,
			dataType : "json",
			success : function(data) {
				
				if(data.error){
					alert(data.error);
					return;
				}else{
					var dataHtml = '<table style="width:100%; border:solid #AFE4D0; border-width:1px 0px 0px 1px;" ><tr><th style="border:solid #AFE4D0; border-width:0px 1px 1px 0px;">房源ID</th><th style="border:solid #AFE4D0; border-width:0px 1px 1px 0px;">操作时间</th><th style="border:solid #AFE4D0; border-width:0px 1px 1px 0px;">操作人</th><th style="border:solid #AFE4D0; border-width:0px 1px 1px 0px;">加分</th></tr>';
					var houseExtraScoreHistorys = data.houseExtraScoreHistorys;
					for(var i =0;i<houseExtraScoreHistorys.length;i++){
						var houseExtraScoreHistory = houseExtraScoreHistorys[i];
						dataHtml+='<tr><td style="border:solid #AFE4D0; border-width:0px 1px 1px 0px;">'+houseExtraScoreHistory.houseId+'</td><td style="border:solid #AFE4D0; border-width:0px 1px 1px 0px;" > '+getDate(new Date(houseExtraScoreHistory.entryDate))+'</td><td style="border:solid #AFE4D0; border-width:0px 1px 1px 0px;" >'+houseExtraScoreHistory.operator+'</td><td style="border:solid #AFE4D0; border-width:0px 1px 1px 0px;" >'+houseExtraScoreHistory.score+'</td></tr>';
					}
					dataHtml+='</table>';
					
					$("#divExtraScoreHistory").html(dataHtml);
				}
				 
			}
		});
 
	
	
	$("#divExtraScoreHistory").dialog("open");
	
	
}
function getDate(d){
	return (d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds());
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
			url : "/rent/verifyReq/batch",
			dataType : "json",
			data : {"ids" : ids,"type":0,"reasons" : reasons},
			success : function(data) {
				if(data.error){
					alert(data.error);
					return;
				}
				if(data.info=="success"){
					alert("成功设置为违规房源!");
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
			url : "/rent/verifyReq/batch",
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
			url : "/rent/verifyReq/batch",
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
//显示拒绝窗口
function openReject(id) {
	$("*").stop();
	$("#rentId").html(id);
	$("#divReject").dialog("close");
	$("#divReject").dialog("option", "position", "center");
	$("#divReject").dialog("open");
}
//显示拒绝窗口
function openDelete(id) {
	$("*").stop();
	$("#deleterentId").html(id);
	$("#divDelete").dialog("close");
	$("#divDelete").dialog("option", "position", "center");
	$("#divDelete").dialog("open");
}
function rejectSave() {

	var id = $("#rentId").html();
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
			url : "/rent/verifyReq",
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
function saveExtraScore(){
	
	var id = $("#extraScoreRentId").html();
	var score = $("#extraScore").val();
	if(isNaN(score)||score==""){
		alert("请填写正确的分数");
		return ;
	}
		$.ajax({
			type : "post",
			async : false,
			url : "/rent/addExtraScore",
			dataType : "json",
			data : {"id" : id,"score" : score},
			success : function(data) {
				if(data.error){
					alert(data.error);
					return;
				}
				if(data.info=="success"){
					alert("房源(ID:"+id+")加分成功");
				}else{
					alert("操作失败!");
					return ;
				};
				$("#divAddExtraScore").dialog("close");
			}
		});
	
	
	
}

function deleteSave(){
	var id = $("#deleteRentId").html();
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
			url : "/rent/verifyReq",
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
	var id = $("#rentId").html();
	pass(id);
	
}
function pass(id){
	
	if(confirm("房源(ID:"+id+")确定审核通过?")){
		
		$.ajax({
			type : "post",
			async : false,
			url : "/rent/verifyReq",
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
<div id = "divCheck" class="todo"  >
	
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



<div id="divReject">
<table>
	<tr>
		<td>
		房源ID:&nbsp;
		</td>
		<td style="text-align: left">
		<label id="rentId"></label>
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
		
	
	<div id="divAddExtraScore" >
<table>
	<tr>
		<td>
		房源ID:&nbsp;
		</td>
		<td style="text-align: left">
		<label id="extraScoreRentId"></label>
		</td>
	</tr>
	<tr>
		<td style="text-align: left" valign="top">
		加分:&nbsp;
		</td>
		<td style="text-align: left" valign="top">
         	 <input type="text"  id="extraScore" name="extraScore"  /> 
		</td>
	</tr>
	<tr>
	
</table>
	</div>
	<div id="divExtraScoreHistory" >

	</div>
	<div id="divDelete">
<table>
	<tr>
		<td>
		房源ID:&nbsp;
		</td>
		<td style="text-align: left">
		<label id="deleteRentId"></label>
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