 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="/js/common/jquery.blockUI.js"></script>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<script type="text/javascript">
<!--

$(function(){
	
	$('#showImgDialog').dialog({
		show : "slide",
		bgiframe : true,
		autoOpen : false,
		height : 540,
		width : 670,
		title : "房源图片大图",
		modal : true,
		resizable : false
	});
});

function showImg(path){
	$("#limg").attr("src",path);
	$("#showImgDialog").dialog("option", "position", "center");
	$('#showImgDialog').dialog('open');
}

$(function(){
	$("#submitButton").attr("disabled",false);
	// 图片点击绑定
	$(".image_wrap img").bind("click",function(){
		var parent=$(this).parent();
		// 火狐/IE
		if($(parent).css("color")=="rgb(0, 0, 0)"||$(parent).css("color")=="black"){
			//打开删除原因对话框
			openBatchReject(parent[0]);
			$(parent).find("img").animate({height:"100px",width:"150px"},"slow");
			$(parent).css({color:"red",background:"pink"});
		}else{
			//取消删除置空打回原因
			var reasonsId=$(parent).attr("id")+'_reason';
			//alert(reasonsId);
			//清空对应图片的删除原因
			document.getElementById(reasonsId).value = '';
			$(parent).find("img").animate({height:"75px",width:"100px"},"slow");
			$(parent).removeAttr("style");
			$(parent).css({color:"black"});
		}
	});
	// 房源ID
	//alert("${houseIdList}");
});
$(function() {
	var h = $(window).height();
	// 初始化弹出窗口
	$("#divBatchReject").dialog({show : "slide",close: function(event, ui) { closeDialog(); },bgiframe : true,autoOpen : false,height : 300,width : 500,title : "违规图片",modal : true,resizable : false,buttons : {"确认" : rejectBatchSave}});
});

//这是dialg关闭事件的回调函数
function closeDialog(){
		   var reasonsId=$("#currentId").val();
		   var reasons=document.getElementById(reasonsId).value;
		   var ids=reasonsId.replace("_reason",'');
		   var obj=document.getElementById(ids);
		 	//取消选择
			putOffChoose();
		   if(reasons==null ||reasons ==''){
				alert("未保存打回原因，该图片的状态将改为通过。");
				$(obj).find("img").animate({height:"75px",width:"100px"},"slow");
				$(obj).removeAttr("style");
				$(obj).css({color:"black"});
				resetCss(reasonsId);
		   }
	   }


function getPassedPhotoCount(obj){
	var sessionUserID = '${sessionScope.sessionUser.user.id}';
	if(sessionUserID==''){
		alert("请先登录.");
		return;
	}
	// 信息类型
	var type = '${type}';
	$.ajax({
		type : "post",
		async : false,
		url : "/auditHistory/getCount",
		dataType : "json",
		data : {"auditUserId" : sessionUserID,"type":type,"houseType":"resale"},
		success : function(data) {
			if (data.error) {
				alert(data.error);
				return;
			}
			var content = "　　　　今日审核图片数量: "+data.res;
			$("#passedPhotoCount").text(content);
			$("#passedPhotoCount").animate({width:"300px"});
			$(obj).hide();
		}
	});
}

function getAllCount(obj){
	$(obj).attr("disabled",true);
	var sessionUserID = '${sessionScope.sessionUser.user.id}';
	if(sessionUserID==''){
		alert("请先登录.");
		return;
	}
	
	// 信息类型
	var type = '${type}';
	$.ajax({
		type : "post",
		async : true,
		url : "/auditHistory/getCount",
		dataType : "json",
		data : {"auditUserId" : sessionUserID,"type":type,"houseType":"resale","await":"1"},
		success : function(data) {
			if (data.error) {
				alert(data.error);
				return;
			}
			var content1 = "　　　　剩余待审核任务数量: "+data.await;
			$("#awaitCount").text(content1);
			$("#awaitCount").animate({width:"300px"});
			$("#notPassedPhotoCount").hide();
			$(obj).attr("disabled",false);
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

<%--得到图片类型 --%>
<c:if test="${type==1}">
	<c:set var="photoType" value="户型图"></c:set>
</c:if>
<c:if test="${type==2}">
	<c:set var="photoType" value="室内图"></c:set>
</c:if>
<c:if test="${type==3}">
	<c:set var="photoType" value="小区图"></c:set>
</c:if>
<div style="width: 100%;text-align: left;">当前位置：房源管理 &gt; 二手房管理 &gt; ${photoType==""?'图片':photoType }审核</div>  
<br />
 <div id="tabs" class="tabs">  

     <ul>  

         <li style="background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%" ><a href="/resale/verify/allresale/list" >二手房管理</a></li> 
         <li ><a href="/resale/verify/baseInfoList" style="background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%"  >标题描述审核</a></li> 
         
         <li class="${type==1?'tabs_active':'' }" style="${type==1?'':'background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%'}"><a href="/resale/verify/list/verifyPhoto/1">户型图审核</a></li>
         <li class="${type==2?'tabs_active':'' }" style="${type==2?'':'background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%'}"><a href="/resale/verify/list/verifyPhoto/2">室内图审核</a></li>
         <li class="${type==3?'tabs_active':'' }" style="${type==3?'':'background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%'}"><a href="/resale/verify/list/verifyPhoto/3">小区图审核</a></li>
     </ul> 
 </div> 
<br />

		<div class="mag">
			<label>所在城市:</label>
                <select id="city" name="cityid" style="width: 100px;margin-left: 10px;" onchange="changeCity(this.value);">
               		<option value="-1">房源城市</option>
                	<c:forEach items="${applicationScope.simpleLocaleMap }" var="entry">
                		<c:if test="${cityid==entry.key }">
	                		<option value="${entry.key }" selected="selected">${entry.value.code }${entry.value.name }</option>
                		</c:if>
                		<c:if test="${cityid!=entry.key }">
                			<option value="${entry.key }">${entry.value.code }${entry.value.name }</option>
                		</c:if>
                	</c:forEach>
                </select> <input type="hidden" name="citycb" value="${cityid}" /> 
			　<label id="passedPhotoCount" style="height: 20px;width: 1px;background-color: #AFE4D0;"></label>
                　　　　<input type="button" value="查看今日已审图片数量" onclick="getPassedPhotoCount(this);"/>
                <label id="awaitCount" style="height: 20px;width: 1px;background-color: #AFE4D0;"></label>　　　　
               <input type="button" value="待审${photoType }房源数量" onclick="getAllCount(this);"/>&nbsp;&nbsp;&nbsp;&nbsp;<label id="notPassedPhotoCount">${notPassedPhotoCount}</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

		</div>

		<div class="mag">
			<div class="todo">
				<table>
					<thead>
						<tr>
							<th colspan="6">
								<c:if test="${not empty resaleList }">
									<input type="checkbox" name="chk_all" id="chk_all" />全选/取消全选
								</c:if>
							</th>
						</tr>
					</thead>
					<tbody class="rasent">
						<tr>
						<c:forEach items="${resaleList}" var="list" varStatus="status">
						<c:if test="${(status.count-1)%5==0&&status.count>1 }">
							</tr><tr>
						</c:if>
							<td class="image_wrap" style="color:black" id="${list.houseId }_${list.id}_${list.resaleSmallPhotoUrl}">
								<img src="${list.resaleSmallPhotoUrl }" width="100px" height="75px" onerror="this.src='${applicationScope.headPrefix}/m/house_m.png'"/>
								<br/>
								<br/>
								<input type="button" value="查看大图" onclick="showImg('${list.resaleLargePhotoUrl}')"/>
								<br/>
								<c:if test="${type==1 }">
									<br/> 
									<input id="isDirection_${list.id}" type="checkbox" <c:if test="${list.category==11||list.category==12}">checked='checked'</c:if> >有方向指示</input>
								</c:if>
								<c:if test="${type==2 }">${list.coverFlag }</c:if>
								<input value="" type="hidden" id="${list.houseId }_${list.id}_${list.resaleSmallPhotoUrl}_reason" />
								<br/><input type="checkbox" name="_select_pictures" id="_select_pictures" valuetype="${list.houseId }_${list.id}_${list.resaleSmallPhotoUrl}" onclick="resetCss('${list.houseId }_${list.id}_${list.resaleSmallPhotoUrl}')" />
								<input value="${list.houseId }_${list.id}_${list.resaleSmallPhotoUrl}" type="hidden" id="check_row_${status.count}" />
							</td>
							<c:if test="${(status.count)%5==0&&status.count>1 }">
								<td><input type="checkbox" name="chk_row" id="chk_row" onclick="javascript:check_row(this.checked,'${status.count}')" /></td>
							</c:if>
						</c:forEach>
						<c:if test="${resaleList.size() >5}">
						<c:choose>
							<c:when test="${resaleList.size() >0 && (resaleList.size())%5!=0 && (resaleList.size())%5==1 }">
								<td></td><td></td><td></td><td></td><td><input type="checkbox" name="chk_row" id="chk_row" onclick="javascript:check_row(this.checked,'${resaleList.size()+4}')" /></td>
							</c:when>
							<c:when test="${resaleList.size() >0 && (resaleList.size())%5!=0 && (resaleList.size())%5==2 }">
								<td></td><td></td><td></td><td><input type="checkbox" name="chk_row" id="chk_row" onclick="javascript:check_row(this.checked,'${resaleList.size()+3}')" /></td>
							</c:when>
							<c:when test="${resaleList.size() >0 && (resaleList.size())%5!=0 && (resaleList.size())%5==3 }">
								<td></td><td></td><td><input type="checkbox" name="chk_row" id="chk_row" onclick="javascript:check_row(this.checked,'${resaleList.size()+2}')" /></td>
							</c:when>
							<c:when test="${resaleList.size() >0 && (resaleList.size())%5!=0 && (resaleList.size())%5==4 }">
								<td></td><td><input type="checkbox" name="chk_row" id="chk_row" onclick="javascript:check_row(this.checked,'${resaleList.size()+1}')" /></td>
							</c:when>
						</c:choose>
						</c:if>
						</tr>
					</tbody>
				</table>
				<input id="currentId" type="hidden" value=''/>
			</div>
		</div>
	<div id="showImgDialog">
		<img  id="limg"></img >
	</div>
	<div  style="text-align: center;" >
		<c:if test="${not empty resaleList }">
			违规原因: <select id="_reject_reason" name="_reject_reason" >
				<c:forEach items="${reasons}" var="list" varStatus="vs">
         			<option value="${list.reason }">${list.reason }</option>
         		</c:forEach>
			</select>
			<input id="submitButton" type="button" onclick="submit();" value="提交本组审核结果" style="height:35px;" />
		</c:if>
		<c:if test="${empty resaleList && empty circleCount  }">
			<h2>已经审核完该城市二手房房源的<font color="red">${photoType }</font>图片,请选择其他类型图片审核或者返回.</h2>
		</c:if>
		<c:if test="${empty resaleList && not empty circleCount }">
			<h2><input type="button" onclick="requestPage();" value="再次请求数据" style="height:35px;" /></h2>
		</c:if>
	</div>
	
<script type="text/javascript">
<!--
//全选/取消全选操作
$("#chk_all").click(function(){
	checkAllBox($("#chk_all")[0]);
});
function check_row(chk,ind){
	var index = ind - 4;
	if(chk==true){
		for(index;index<=ind;index++){
			var obj = $("#check_row_"+index);
			var value = obj.val();
			var e = document.getElementById("check_row_"+index);
			var temp = e.parentNode;
			if(temp.tagName=="TD"){
				var d = $(temp).children("input[name='_select_pictures']");
				d.attr("checked",true);
			}				
			resetCss(value);			
		}
	}else{
		for(index;index<=ind;index++){
			var obj = $("#check_row_"+index);
			var value = obj.val();
			var e = document.getElementById("check_row_"+index);
			var temp = e.parentNode;
			if(temp.tagName=="TD"){
				var d = $(temp).children("input[name='_select_pictures']");
				d.attr("checked",false);
			}				
			resetCss(value);			
		}
	}
}
function checkAllBox(obj){
	if($(obj).attr("checked")){
		$('input[name="_select_pictures"]').each(function(){  
			var tempId = $(this).attr("valuetype");
			resetCss(tempId);
		    $(this).attr('checked',true); 
		});  
		$('input[name="chk_row"]').each(function(){  
			$(this).attr('checked',true); 
		});
	}else{
		$('input[name="_select_pictures"]').each(function(){  
		    $(this).attr('checked',false); 
		});
		$('input[name="chk_row"]').each(function(){  
			$(this).attr('checked',false); 
		});
	}
	
}

function resetCss(tempId){
	var reasonsId=tempId+"_reason";
	var reasonsValue = document.getElementById(reasonsId).value;
	var objtemp = document.getElementById(tempId);
	if(reasonsValue!=null && reasonsValue.length>0){
		document.getElementById(reasonsId).value = '';
		$(objtemp).find("img").animate({height:"75px",width:"100px"},"slow");
		$(objtemp).removeAttr("style");
		$(objtemp).css({color:"black"});
	}
}

function requestPage(){
	window.location.href="/resale/verify/list/verifyPhoto/${type}?cityid=${cityid }";
}

function changeDirection(){
	var checkBoxs= $("input[id^='isDirection_']");
	var passIds="[";
	var noPassIds="[";
	for(var i=0;i<checkBoxs.size();i++){
		var obj=checkBoxs[i];
		var flag=obj.checked;
		var id=obj.id.split("_")[1];
		if(flag){
			passIds+=id+",";
		}else{
			noPassIds+=id+",";
		}
	}
	if(passIds.lastIndexOf(",")!=-1){
		passIds=passIds.substr(0,passIds.length-1);
	}
	if(noPassIds.lastIndexOf(",")!=-1){
		noPassIds=noPassIds.substr(0,noPassIds.length-1);
	}
	passIds+="]";
	noPassIds+="]";
	var isSuccess= updatePhotoForDirection(passIds,noPassIds);
	return isSuccess;
}

function updatePhotoForDirection(passIds,noPassIds){
	var flag=false;
	$.ajax({
		type : "post",
		async : false,
		url : "/rent/ajax/updateDirection",
		dataType : "json",
		data : {"passIds":passIds,"noPassIds":noPassIds},
		success : function(data) {
			flag= data.result;
		}
	});
			return flag;
}

var ranNum = Math.random();

// 修改城市信息
function changeCity(cityid){
	// 图片类型
	var type = '${type}';
	window.location.href="/resale/verify/list/verifyPhoto/"+type+"?ranNum="+ranNum+"&cityid="+cityid;
}

// 提交事件
function submit(){
	$("#submitButton").attr("disabled",true);
	var flag=true;
	if('${type==1}'=='true'){
		flag=changeDirection();
	}
	if(!flag){
		//提交时更新户型朝向失败
		alert("更新户型方向指示失败");
		return;
	}
	// 遮罩层
    $.blockUI({message: "<h3>正在提交数据，请稍候……</h3>"});
	
	// 违规图片List
	var blockList = "";
	// 所有房源id
	var houseIdList = '${houseIdList}';
	// 所有photoids
	var photoids = '${photoids}';
	//所有taskids
	var taskids ='${taskids}';
	
	// type
	var type = '${type}';
	// cityid
	var cityid=$("#city").val();
	
	//批量删除
	var batchRejectReasons = $("#_reject_reason").val();
	$(".image_wrap").each(function(){
		if($(this).css("color")=="rgb(255, 0, 0)"||$(this).css("color")=="red"){
			var reasonsIds=$(this).attr("id")+'_reason';
			var reason=document.getElementById(reasonsIds).value;
			var submitInfo=$(this).attr("id")+"#_#"+reason;
			blockList += submitInfo;
			blockList += ",";
			
		}
	});
	$('input[name="_select_pictures"]').each(function(){  
		   var temp = $(this).attr('checked'); 
		   if(temp=="checked"){
			   blockList += $(this).attr("valuetype")+"#_#"+batchRejectReasons+"<br /> ";
			   blockList += ",";
		   }
	});
	
	$.ajax({
		type : "post",
		async : false,
		url : "/resale/verify/photos",
		dataType : "json",
		data : {"blockList" : blockList,"type":type,"houseIdList":houseIdList,"cityid":cityid,"photoids":photoids,"taskids":taskids},
		success : function(data) {
			 if(data.error){
				alert(data.error);
				return;
			}
			if(data.info=="success"){ 
				window.location.href="/resale/verify/list/verifyPhoto/"+type+"?ranNum="+ranNum+"&cityid="+cityid;
			}
		}
	});
	
}

function clickRejectReason(id, checked) {
	
	if($("#" +  id).attr("checked")){
		$("#" +  id).attr("checked", false);
		var reasonsId=$("#currentId").val();
		document.getElementById(reasonsId).value = "";
	}else{
		$("#" +  id).attr("checked", checked);
		rejectBatchSave(true);
	}
	
}

function clickRejectReasonLbl(id){
	if(id==null || id.length<1)return;
	var index = id.indexOf("_value"); 
	var temp = id.substr(0,index);
	if($("#" +  temp).attr("checked")){
		$("#" +  temp).attr("checked", false);
		var reasonsId=$("#currentId").val();
		document.getElementById(reasonsId).value = "";
		
	}else{
		$("#" +  temp).attr("checked", true);
		rejectBatchSave(true);
	}
	
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
	var reasonsId=$("#currentId").val();
	
//	var signs='#_#';
	document.getElementById(reasonsId).value = reasons;
	
	//将重复的复选框取消选中	
	$('input[name="_select_pictures"]').each(function(){  
		var temp = $(this).attr("valuetype");
		temp = temp + "_reason";
		if(temp == reasonsId){
			$(this).attr("checked",false);
		}
	});
		//$('#'+reasonsId).val(reasons);
	
	//判读是否选择打回原因
//	$('#chk_all_reason').val(reasons);
	//取消选择
//	putOffChoose();
	//关闭打回原因选择
	if(flag != true){
		$("#divBatchReject").dialog("close");
	}
}


//取消对话框的选择
function putOffChoose(){
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
}

function openBatchReject(obj) {
	//判读是否选择打回原因
	//$('#chk_all_reason').val('');
	$("*").stop();
	//当前操作的id
	var reasonsId=obj.id+'_reason';
//	if(!obj.checked){
//		$("#reason_"+reasonsId).val(reasonsId);
//		return;
//	}
	$("#currentId").val(reasonsId);
	//$("#divBatchReject").dialog("close");
	$("#divBatchReject").dialog("option", "position", "center");
	$("#divBatchReject").dialog("open");
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
	         			<input type="checkbox" id="batchReasons_${vs.index+1 }" onClick="clickRejectReason(this.id,this.checked);" />
	         			<label id="batchReasons_${vs.index+1 }_value" onClick="clickRejectReasonLbl(this.id);">${list.reason}</label><br />
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

	<form id="rentForm"></form>