<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.taofang.com/tld/func" prefix="func"%>
<head>
<meta http-equiv="Expires" CONTENT="0">
<meta http-equiv="Cache-Control" CONTENT="no-cache">
<meta http-equiv="Pragma" CONTENT="no-cache">
</head>
<script type="text/javascript" src="/js/common/jquery.blockUI.js"></script>
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
	function triggerCheckbox(obj){
		var targetCheckbox = $(obj).next().children().children("input[type='checkbox']");
		if (targetCheckbox.attr("checked") == "checked") {
			targetCheckbox.removeAttr("checked");
		} else {
			j=1;
			while(true){
				var reason = $("#batchReasons_"+j);
				reason.attr("checked",false);
				j++;
				if(j>30){
					break;
				}
			}
			$("#batchReasons").val("");
			targetCheckbox.attr("checked",true);
		}
		openBatchReject(targetCheckbox[0]);
	}
	
	function getPassedBaseCount(obj){
		var sessionUserID = '${sessionScope.sessionUser.user.id}';
		if(sessionUserID==''){
			alert("请先登录.");
			return;
		}
		
		// 信息类型
		var type = '4';
		$.ajax({
			type : "post",
			async : false,
			url : "/auditHistory/getCount",
			dataType : "json",
			data : {"auditUserId" : sessionUserID,"type":type,"houseType":"rent"},
			success : function(data) {
				if (data.error) {
					alert(data.error);
					return;
				}
				var content1 = "　　　　今日审核任务数量: "+data.res;
				$("#passedBaseCount").text(content1);
				$("#passedBaseCount").animate({width:"300px"});
				
				$(obj).hide();
			}
		});
	}
	
	function getAllCount(obj){
		var sessionUserID = '${sessionScope.sessionUser.user.id}';
		if(sessionUserID==''){
			alert("请先登录.");
			return;
		}
		
		// 信息类型
		var type = '4';
		$.ajax({
			type : "post",
			async : false,
			url : "/auditHistory/getCount",
			dataType : "json",
			data : {"auditUserId" : sessionUserID,"type":type,"houseType":"rent","await":"1"},
			success : function(data) {
				if (data.error) {
					alert(data.error);
					return;
				}
				var content1 = "　　　　剩余待审核任务数量: "+data.await;
				$("#awaitCount").text(content1);
				$("#awaitCount").animate({width:"300px"});
				$("#notPassedPhotoCount").hide();
				
				$(obj).hide();
			}
		});
	}
	
	function rebackTask(){
		$("#rentForm").attr("action","/rent/verify/backtask");
		document.getElementById('rentForm').submit();
	}
//-->
</script>
<div style="width: 100%;text-align: left;">当前位置：房源管理 &gt; 出租房管理 &gt; 标题描述审核</div>  
<br />
 <div id="tabs" class="tabs">  

     <ul>  

         <li ><a href="/rent/verify/allrent/list" style="background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%" >租房管理</a></li>  
         <li class="tabs_active"  ><a href="/rent/verify/baseInfoList"  >标题描述审核</a></li>  
         <li ><a href="/rent/verify/list/verifyPhoto/1" style="background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%" >户型图审核</a></li>  
         <li ><a href="/rent/verify/list/verifyPhoto/2" style="background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%" >室内图审核</a></li>  
         <li ><a href="/rent/verify/list/verifyPhoto/3" style="background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%" >小区图审核</a></li>  

     </ul> 

 </div> 
<br />

	<form action="/rent/verify/baseInfoList" method="post" id="rentForm" >
	
	<input type="hidden" value="1" name="postSubmit"/>

		<div class="mag ">
		<div style="float:left">
			<label>所在城市:</label>
                <select id="city" name="cityid" style="width: 100px;margin-left: 10px;" onchange="refreshPage();" onkeydown="this.blur();this.focus();">
               		<option value="-1">房源城市</option>
                	<c:forEach items="${applicationScope.simpleLocaleMap }" var="entry">
                		<c:if test="${cityid==entry.key }">
	                		<option value="${entry.key }" selected="selected">${entry.value.code }${entry.value.name }</option>
                		</c:if>
                		<c:if test="${cityid!=entry.key }">
                			<option value="${entry.key }">${entry.value.code }${entry.value.name }</option>
                		</c:if>
                	</c:forEach>
                </select>
                <input type="hidden" name="citycb" value="${cityid}"/> 
				
                <input type="hidden" id="chk_all_reason" value=''/> 　　　　
               
               	<label id="passedBaseCount" style="height: 20px;width: 1px;background-color: #AFE4D0;"></label>　　　　
               <input type="button" value="今日审核任务数量" onclick="getPassedBaseCount(this);"/>
				<label id="awaitCount" style="height: 20px;width: 1px;background-color: #AFE4D0;"></label>　　　　
               <input type="button" value="剩余待审核任务数量" onclick="getAllCount(this);"/>&nbsp;&nbsp;&nbsp;<label id="notPassedPhotoCount">${notPassedPhotoCount}</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

               
               </div>
               <div style="float: right">
					<c:if test="${not empty rentList }">
						<input type="checkbox" name="chk_all" id="chk_all" />全选/取消全选
						</c:if>
					<c:if test="${empty rentList }">
		
					</c:if>
				</div>
		</div>

		<div class="mag">

			<div class="todo">

				<table>

					<tbody class="rasent">
						<c:forEach items="${rentList}" var="list">
							<tr style=" width:12.5%; text-align:center;" class="todo">
							<th colspan="2"><span style=" width:12.5%;float:left;padding-left:10px;">
							<c:choose>
								<c:when test="${fn:length(list.title) >= 90 }">${fn:substring(list.title, 0, 90)}...</c:when>
								<c:otherwise>${list.title}&nbsp;&nbsp;</c:otherwise>
							</c:choose>
							 <!-- 防注释 -->
							</span><a style=" width:12.5%;float:right" href="http://${func:getCode(list.cityId)}.${applicationScope.sysDomain}/zufang/${list.houseId}-0.html" target="_blank">房源ID：${list.houseId}</a>
							</th>
							</tr>
							<tr onclick="triggerCheckbox(this);">
								<td style=" width:12.5%; text-align:center" colspan="2">
										<c:if test="${list.description.length() == 0 }">
			                               	 暂时没有描述信息
			                            </c:if>
			                            <c:if test="${list.description.length() > 0 }">
			                             <iframe src="/rent/getDescription/${list.houseId}" id="description${list.houseId}" width="100%" scrolling="no" frameborder="0" ></iframe>
			                               <script type="text/javascript"> 
											 
												$(function(){ 
												//iframe高度随内容自动调整 
												$("#description${list.houseId}").load(function() { 
												  		$(this).height($(this).contents().find("body").height()+60); 
												}); 
													} 
												); 
										 
												</script>
			                            </c:if>
			                             <!-- 防注释 -->
								</td>
							</tr>
							<tr style=" width:12.5%; text-align:right" >
								<td colspan="2" style=" width:12.5%; text-align:right">
									<input type="checkbox" value="${list.houseId}" id="reject_${list.houseId}" name="reject_${list.houseId}" onclick="openBatchReject(this);"/>
									<input type="hidden" name="reason"  id="reason_${list.houseId}" value="${list.houseId}">
									 违规&nbsp;&nbsp;&nbsp;
								 <!-- 防注释 -->
								</td>
							</tr>
						</c:forEach>
				
						
					</tbody>

				</table>
				<input id="currentId" value="" type="hidden"/>
			</div>

		</div>
	<div  style="text-align: center;" >

	<c:if test="${not empty rentList }">
			<input type="button" value="提交并继续" onClick="submitIt();" />
		</c:if>
		<c:if test="${empty rentList }">
			<h2>已经审核完该城市出租房房源的标题和描述信息,请选择其他城市审核或者返回.</h2>
		</c:if>
	
	</div>
		<%-- <div class="page_and_btn" >
		
			<jsp:include page="/WEB-INF/snippets/page.jsp" />
		</div> --%>
	
	</form>

<script type="text/javascript">
<!--

$(function() {
	var h = $(window).height();
	// 初始化弹出窗口
	$("#divBatchReject").dialog({show : "slide",close: function(event, ui) { closeDialog(); },bgiframe : true,autoOpen : false,height : 330,width : 500,title : "违规房源",modal : true,resizable : false,buttons : {"确认" : rejectBatchSave}});
});

function submitIt(){
	// 遮罩层
    $.blockUI({message: "<h3>正在提交数据，请稍候……</h3>"});
    document.getElementById('rentForm').submit();
}
//这是dialg关闭事件的回调函数
function closeDialog(){
		   var reasonsId=$("#currentId").val();
		   var checkBoxs=$("[name=reject_"+reasonsId+"]");
		  // var reasonValue=$("#reason_"+reasonsId).val();
		   if($('#chk_all_reason').val()==''){
			   //未填写打回原因
			   	alert("未保存打回原因，该房源的状态将改为通过。");
			   if($("#currentId").val()==''){
				   $("#chk_all").attr("checked",false);
				   checkAllBox($("#chk_all")[0]);
			   }else{
				   	checkBoxs.attr("checked",false);
			   }
		   }
	   }
function refreshPage(){
	$("#rentForm").attr("action","/rent/verify/refreshBaseInfoPage");
	document.getElementById('rentForm').submit();
}

$("#chk_all").click(function(){
	checkAllBox($("#chk_all")[0]);
});

function checkAllBox(obj){
	//清空单选时存放id的input
	$("#currentId").val('');
	$("input[name^='reject_']").attr("checked",$(obj).attr("checked")=='checked'?true:false);
	if($(obj).attr("checked")){
		openBatchRejectForAll();
	}
	if(!$(obj).attr("checked")){
		var reasonAll=$("input[id^='reason_']");
		for(var j = 0 ;j<reasonAll.length;j++){
			var getId=reasonAll.eq(j).attr("id");
			var houseId=getId.split('_')[1];
			$('#'+getId).val(houseId);
		}
	}
}

function openBatchRejectForAll() {
	//判读是否选择打回原因
	$('#chk_all_reason').val('');
	$("*").stop();
	//$("#divBatchReject").dialog("close");
	$("#divBatchReject").dialog("option", "position", "center");
	$("#divBatchReject").dialog("open");
}

function openBatchReject(obj) {
	//判读是否选择打回原因
	$('#chk_all_reason').val('');
	$("*").stop();
	//当前操作的id
	var reasonsId=obj.value;
	if(!obj.checked){
		$("#reason_"+reasonsId).val(reasonsId);
		return;
	}
	$("#currentId").val(reasonsId);
	//$("#divBatchReject").dialog("close");
	for(var i=0;i<$("input[name='batchReasons_select']").size();i++){
		if($("input[name='batchReasons_select']").eq(i).is(":checked")){
			$("input[name='batchReasons_select']").eq(i).attr("checked",false);			
		}
	}
	$("#divBatchReject").dialog("option", "position", "center");
	$("#divBatchReject").dialog("open");
}

function clickRejectReason(id, checked) {
	if(checked==true || checked =='checked'){
		$("#" +  id).attr("checked", true);
	}else{
		$("#" +  id).attr("checked", false);
	}
	rejectBatchSave(true);
}

function clickRejectReasonLbl(id){
	if(id==null || id.length<1)return;
	var index = id.indexOf("_value"); 
	var temp = id.substr(0,index);
	if($("#" +  temp).attr("checked")){
		$("#" +  temp).attr("checked", false);
	}else{
		$("#" +  temp).attr("checked", true);
	}
	rejectBatchSave(true);
}

//保存违规原因
function rejectBatchSave(flag) {
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
//	if(ids.length==0){
//		alert("请至少选择一个房源!");
//		return;
//	}
	
	var reasons = "";

	for(var i=0;i<$("input[name='batchReasons_select']").size();i++){
		if($("input[name='batchReasons_select']").eq(i).is(":checked")){
			var selectRadioId = $("input[name='batchReasons_select']").eq(i).attr("id");
			reasons += $("#"+selectRadioId+"_value").html()+ "<br /> ";
			//break;
		}
	}

	reasons += $("#batchReasons").val().trim();
	var reasonsId=$("#currentId").val();
	if(reasons.trim()==""){
		alert("请选择或填写违规原因！");
		var obj = document.getElementById("reject_"+reasonsId);
		if(obj!=null){
			obj.checked = false
		}
		return ;
	}else{
		var obj = document.getElementById("reject_"+reasonsId);
		if(obj!=null){
			obj.checked = true
		}
	}
	
	var signs='#_#';
	if(flag != true){
		if($("#currentId").val()==''){
			//点过全选后 就变为空了
			var reasonAll=$("input[id^='reason_']");
			for(var j = 0 ;j<reasonAll.length;j++){
				var getId=reasonAll.eq(j).attr("id");
				var idValue=$('#'+getId).val();
				if(idValue.indexOf(signs)!=-1){
					$('#'+getId).val(idValue+'<br/>'+reasons);
				}else{
					$('#'+getId).val(idValue+signs+reasons);
				}
			}
		}else{
			$("#reason_"+reasonsId).val(reasonsId+signs+reasons);
		}
	}
	//判读是否选择打回原因
	$('#chk_all_reason').val(reasons);
	//取消选择
	/* j=1;
	while(true){
		var reason = $("#batchReasons_"+j);
		reason.attr("checked",false);
		j++;
		if(j>30){
			break;
		}
	}
	$("#batchReasons").val(""); */
	//关闭打回原因选择
	if(flag!=true){
		$("#divBatchReject").dialog("close");
	}
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
         			<input type="checkbox" id="batchReasons_${vs.index+1 }" name="batchReasons_select" onClick="clickRejectReason(this.id, this.checked);">
         			<label id="batchReasons_${vs.index+1 }_value"   onClick="clickRejectReasonLbl(this.id);">${list.reason}</label><br />
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
