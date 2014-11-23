<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<script type="text/javascript" src="/js/common/jquery.js"></script>
<script type="text/javascript" src="/js/common/jquery-ui.js"></script>
<script type="text/javascript" src="/js/common/jquery.blockUI.js"></script>
<script type="text/javascript" src="/js/common/jquery.tipsy.js"></script>
<script type="text/javascript" src="/js/common/validation.js"></script>
<script type="text/javascript" src="/js/common/common.js"></script>

<script type='text/javascript' src='/js/cluetip/jquery.cluetip.js'></script>

<link rel="stylesheet" type="text/css" href="/js/cluetip/jquery.cluetip.css" />


<link type="text/css" rel="stylesheet" href="/css/common/smoothness/jquery-ui-1.8.14.custom.css"/>
<link type="text/css" rel="stylesheet" href="/css/common/validation.css"/>
<link type="text/css" rel="stylesheet" href="/css/common/tipsy.css"/>
<link type="text/css" rel="stylesheet" href="/css/layout.css"/>
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



function rebackTask(){
	$("#rentForm").attr("action","/rent/verify/backtask");
	document.getElementById('rentForm').submit();
}

//-->
</script>
</head>
<body>
<h1>房源图片</h1>
		<div class="mag">
			<div class="todo">
				<table>
					<tbody class="rasent">
						<tr>
						<c:forEach items="${housePhotoList}" var="list" varStatus="status">
						<c:if test="${(status.count-1)%5==0&&status.count>1 }">
							</tr><tr>
						</c:if>
							<td class="image_wrap" style="color:black" id="${list.houseId }_${list.id}_<c:if test="${houseType==1 }">${list.resaleSmallPhotoUrl}</c:if><c:if test="${houseType==2 }">${list.rentSmallPhotoUrl}</c:if>">
								<img src="<c:if test="${houseType==1 }">${list.resaleSmallPhotoUrl}</c:if><c:if test="${houseType==2 }">${list.rentSmallPhotoUrl}</c:if>" width="100px" height="75px" onerror="this.src='${applicationScope.headPrefix}/m/house_m.png'"/>
								<br/>
								<br/>
								<input type="button" value="查看大图" <c:if test="${houseType==2 }"> onclick="showImg('${list.rentLargePhotoUrl}')" </c:if> <c:if test="${houseType==1 }"> onclick="showImg('${list.resaleLargePhotoUrl}')" </c:if> />
								<br/>
								<c:if test="${category==1 }">
									<br/> 
									<input id="isDirection_${list.id}" type="checkbox" <c:if test="${list.category==11||list.category==12}">checked='checked'</c:if> >有方向指示</input>
								</c:if>
								<c:if test="${category==2 }">${list.coverFlag }</c:if>
								<c:if test="${list.hdFlag =='R' }"><font color="green">(已复审成"通过")</font></c:if>
								<input value="" type="hidden" id="${list.houseId }_${list.id}_<c:if test="${houseType==1 }">${list.resaleSmallPhotoUrl}</c:if><c:if test="${houseType==2 }">${list.rentSmallPhotoUrl}</c:if>_reason" />
								<br/><input type="checkbox" name="_select_pictures" id="_select_pictures" valuetype="${list.houseId }_${list.id}_<c:if test="${houseType==1 }">${list.resaleSmallPhotoUrl}</c:if><c:if test="${houseType==2 }">${list.rentSmallPhotoUrl}</c:if>" onclick="resetCss('${list.houseId }_${list.id}_<c:if test="${houseType==1 }">${list.resaleSmallPhotoUrl}</c:if><c:if test="${houseType==2 }">${list.rentSmallPhotoUrl}</c:if>')" />
								<input value="${list.houseId }_${list.id}_<c:if test="${houseType==1 }">${list.resaleSmallPhotoUrl}</c:if><c:if test="${houseType==2 }">${list.rentSmallPhotoUrl}</c:if>" type="hidden" id="check_row_${status.count}" />
							</td> 
						</c:forEach>
						</tr>
					</tbody>
				</table>
				<input id="currentId" type="hidden" value=''/>
			</div>
		</div>
		<h1>违规图片</h1>
		<div class="mag">
			<div class="todo">
				<table>
					<tbody class="rasent">
						<tr>
						<c:forEach items="${rejectPhotoList}" var="list" varStatus="status">
						<c:if test="${(status.count-1)%5==0&&status.count>1 }">
							</tr><tr>
						</c:if>
							<td style="color:black" id="${list.houseId }_${list.id}_<c:if test="${houseType==1 }">${list.resaleSmallPhotoUrl}</c:if><c:if test="${houseType==2 }">${list.rentSmallPhotoUrl}</c:if>">
								<img src="<c:if test="${houseType==1 }">${list.resaleSmallPhotoUrl}</c:if><c:if test="${houseType==2 }">${list.rentSmallPhotoUrl}</c:if>" width="100px" height="75px" onerror="this.src='${applicationScope.headPrefix}/m/house_m.png'"/>
								<br/>
								<c:if test="${category==1 }">
									<br/> 
									<input id="isDirection_${list.id}" type="checkbox" <c:if test="${list.category==11||list.category==12}">checked='checked'</c:if> >有方向指示</input>
								</c:if>
								<c:if test="${category==2 }">${list.coverFlag }</c:if>
								<c:if test="${list.hdFlag =='R' }"><font color="red">(已复审成"违规")</font></c:if>
								<input value="" type="hidden" id="${list.houseId }_${list.id}_<c:if test="${houseType==1 }">${list.resaleSmallPhotoUrl}</c:if><c:if test="${houseType==2 }">${list.rentSmallPhotoUrl}</c:if>_reason" />
								<br/><input type="checkbox" name="_reject_pictures" id="_reject_pictures" value="${list.id}" valuetype="${list.houseId }_${list.id}_<c:if test="${houseType==1 }">${list.resaleSmallPhotoUrl}</c:if><c:if test="${houseType==2 }">${list.rentSmallPhotoUrl}</c:if>" onclick="resetCss('${list.houseId }_${list.id}_<c:if test="${houseType==1 }">${list.resaleSmallPhotoUrl}</c:if><c:if test="${houseType==2 }">${list.rentSmallPhotoUrl}</c:if>')" />
								<input value="${list.houseId }_${list.id}_<c:if test="${houseType==1 }">${list.resaleSmallPhotoUrl}</c:if><c:if test="${houseType==2 }">${list.rentSmallPhotoUrl}</c:if>" type="hidden" id="check_row_${status.count}" />
							</td>
						</c:forEach>
						</tr>
					</tbody>
				</table>
				<input id="currentId" type="hidden" value=''/>
			</div>
		</div>
	<c:if test="${sessionScope.sessionUser.isPass('/verify/allrent/list/audit')}">	
		<div  style="text-align: center;" >
			<c:if test="${not empty housePhotoList || not empty rejectPhotoList}">
				违规原因: <select id="_reject_reason" name="_reject_reason" >
					<c:forEach items="${reasons}" var="list" varStatus="vs">
	         			<option value="${list.reason }">${list.reason }</option>
	         		</c:forEach>
				</select>
				<input id="submitButton" type="button" onclick="submit();" value="提交本组审核结果" style="height:35px;" />
			</c:if>
		</div>
	</c:if>
	
		<div id="showImgDialog">
		<img  id="limg"></img >
	</div>
<script type="text/javascript">
<!--

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
	// 所有photoids
	var photoids = '${photoids}';
	//复审通过的ids
	var resumes='';
	var houseId = '${houseId}';
	
	var category='${category}';
	
	var houseType ='${houseType}';
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
			   blockList  += $(this).attr("valuetype")+"#_#"+batchRejectReasons+"<br /> ";
			   blockList  += ",";
		   }
	});
	
	$('input[name="_reject_pictures"]').each(function(){  
		   var temp = $(this).attr('checked'); 
		   if(temp=="checked"){
			   resumes += $(this).attr("value");
			   resumes += ",";
		   }
	});
	var url='';
	if(houseType==1){
		url="/resale/verify/reauditphotos";
	}else if(houseType==2){
		url="/rent/verify/reauditphotos";
	}
	$.ajax({
		type : "post",
		async : false,
		url : url,
		dataType : "json",
		data : {"houseId":houseId,"blockList" : blockList,"photoids":photoids,"resumes":resumes},
		success : function(data) {
			 if(data.error){
				alert(data.error);
				return;
			}
			if(data.info=="success"){ 
				alert('审核完成');
				
				var durl='';
				if(houseType==1){
					durl="/resale//house/photo/"+houseId+"/"+category;
				}else if(houseType==2){
					durl="/rent//house/photo/"+houseId+"/"+category;
				}
				location.href=durl;
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


<div id="divBatchReject" style="display:none">
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
</body>
</html>