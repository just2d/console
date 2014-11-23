<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="/js/common/jquery.blockUI.js"></script>
<script type="text/javascript">
<!--
	$(function() {
		// img点击绑定
		$("[name='parentTR'] td img").bind("click", function() {
			//点击图片后隐藏大图
			$("#parent_display_pic").hide();
			
			var prefix = $(this).parent().attr("name");
			var cur_agentId = $(this).parent().parent().attr("id");
			
			// 名片不需要审核
			if(prefix=="noNeed"){
				return;
			}
			var toggleDiv = $("#"+prefix+"_"+cur_agentId);
			if(toggleDiv.css("display")=="none"){
				$(this).parent().css({background : "pink"});
				toggleDiv.show();
				if("img_id" == this.name){ $("#parent_display_pic").show(); }
				
			}else{
			    if(confirm("确定通过此图片?这样将清空违规原因.")){
					toggleDiv.find("input").attr("checked",false);
					toggleDiv.find("textarea").val("");
					$(this).parent().removeAttr("style");
					toggleDiv.hide();
				} 			
			}
			
			
		});
		
		// checkBox点击绑定 head_123_1
		$("[type='checkbox']").bind("click",function(){
			var prefix = $(this).attr("id").split("_")[0];
			var cur_agentId = $(this).attr("id").split("_")[1];
			var bigDiv = $("#"+prefix+"_"+cur_agentId);
			var textarea = $("#"+prefix+"_"+cur_agentId+"_textarea");
			textarea.val("");
			$(bigDiv).find("input").each(function(){
				var textareaVal = textarea.val();
				if($(this).attr("checked")=="true"||$(this).attr("checked")||$(this).attr("checked")=="checked"){
					textarea.val(textareaVal+$(this).siblings("label").html()+";");
				}
			});
		});
	});
	
function getPassedPhotoCount(obj){
	var sessionUserID = '${sessionScope.sessionUser.user.id}';
	if(sessionUserID==''){
		alert("请先登录.");
		return;
	}

	// 信息类型
	$.ajax({
		type : "post",
		async : false,
		url : "/auditHistory/getCount",
		dataType : "json",
		data : {"auditUserId" : sessionUserID,"type":5,"houseType":"agent"},
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
	
	if(!confirm("图片数量巨大,查询将非常耗时,是否查询?")){
		return;
	}
	$(obj).attr("disabled",true);
	var sessionUserID = '${sessionScope.sessionUser.user.id}';
	if(sessionUserID==''){
		alert("请先登录.");
		return;
	}
	
	// 信息类型
	$.ajax({
		type : "post",
		async : true,
		url : "/auditHistory/getCount",
		dataType : "json",
		data : {"auditUserId" : sessionUserID,"type":5,"houseType":"agent","await":"1"},
		success : function(data) {
			if (data.error) {
				alert(data.error);
				return;
			}
			var content1 = "　　　　剩余待审核任务数量: "+data.await;
			$("#awaitCount").text(content1);
			$("#awaitCount").animate({width:"300px"});
			
			$(obj).attr("disabled",false);
			$(obj).hide();
		}
	});
}
	
	//鼠标移动到图片上显示大图
	function _parent_display(){
		$("#parent_display_pic").css("display","block");
	}
	function turnOff(){
		$("#parent_display_pic").css("display","none");
	}
	function _display_pic(index,src,card,name){
		var d = $("#parent_display_pic");
		var top =  $(window).scrollTop() ;
		d.css("left","600px");
		d.css("top",top+"px");
		$("#_id_src").attr("src",src);
		$("#_id_card").text("身份证号码："+card);
		$("#_id_name").html("真是姓名："+name);
		$("#parent_display_pic").css("display","block");
	}
	function _hide_pic(){
		$("#parent_display_pic").css("display","none");
	}
//-->
</script>

<div style="width: 100%; text-align: left;">当前位置：经纪人管理 &gt; 经纪人审核</div>
<br />

<div class="mag">
	<label>所在城市:</label> <select id="city" name="cityid" style="width: 100px; margin-left: 10px;" onchange="changeCity(this.value);">
		<option value="-1">经纪人城市</option>
		<c:forEach items="${applicationScope.simpleLocaleMap }" var="entry">
			<c:if test="${cityid==entry.key }">
				<option value="${entry.key }" selected="selected">${entry.value.code }${entry.value.name }</option>
			</c:if>
			<c:if test="${cityid!=entry.key }">
				<option value="${entry.key }">${entry.value.code }${entry.value.name }</option>
			</c:if>
		</c:forEach>
	</select> <input type="hidden" id="cityid" value="${cityid}" /> 
	<label id="passedPhotoCount" style="height: 20px;width: 1px;background-color: #AFE4D0;"></label>
                　　　　<input type="button" value="查看今日已审数量" onclick="getPassedPhotoCount(this);"/>
                <label id="awaitCount" style="height: 20px;width: 1px;background-color: #AFE4D0;"></label>　　　　
               <input type="button" value="待审${photoType }数量" onclick="getAllCount(this);"/>
	<!-- 手机号码:<input id="phone" type="text" /> <label id="passedPhotoCount" style="height: 20px; width: 1px; background-color: #AFE4D0;"></label> <input type="button" value="查看今日已审图片数量" onclick="getPassedPhotoCount(this);" /> -->
</div>

<div class="mag">
	<div class="todo">
				<!-- 大图显示开始 -->		
							<div id="parent_display_pic"  style="display: none; z-index: 1002; outline: 0px none;  height: auto; width: 600px;" class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable" tabindex="-1" role="dialog" aria-labelledby="ui-dialog-title-showImgDiv">
						    <div class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix">
							<span class="ui-dialog-title" id="ui-dialog-title-showImgDiv">图片详情</span>
							<!-- <a href="#" class="ui-dialog-titlebar-close ui-corner-all" role="button">
							    <span class="ui-icon ui-icon-closethick">close</span>
							</a> -->
						    </div>
						    <div id="showImgDiv" class="ui-dialog-content ui-widget-content" style="width: 600px; min-height: 0px; height: 455.267px;">
						        <div class="todo">
							    <table align="center">
							        <tbody>
							          <tr>
							        	<td><img id="_id_src" width="550px;" height="350px;" src=""></td>
								      </tr>
								      <tr><td id="_id_card"></td></tr>
								      <tr><td id="_id_name"></td></tr>
								      <tr><td><input type="button" onclick="turnOff();" value="关闭"></td></tr>
								    </tbody>
								</table>
						        </div>
						    </div>
						  </div>
				<!--  大图显示开始  -->
		<table>
			<thead>
				<tr>
					<th width="33%">身份证</th>
					<th width="33%">头像</th>
					<th width="33%">名片</th>
				</tr>
			</thead>
			<tbody class="rasent">
				<c:forEach items="${list}" var="item" varStatus="status">
					<tr name="parentTR" style="color: black;" id="${item.agentId }">
					
						<td name="idCard">
							<%-- <img alt="${item.idCardImg }_imgSpliter" src="${applicationScope.adminpicPrefix}${item.idCardImg }" height="150px"  onerror="this.src='${applicationScope.headPrefix}/m/house_m.png'"/> <br />身份证号码:${item.idcardNumber } <br />真实姓名:${item.name } --%>
							<img  name="img_id" alt="${item.idCardImg }_imgSpliter" src="${item.idCardImg }" onMouseOver="_display_pic('idCard_${item.agentId }',this.src,'${item.idcardNumber}','${item.name}');" onMouseOut="_hide_pic();"     onerror="this.src='${applicationScope.headPrefix}/m/house_m.png'"/> <br />身份证号码:${item.idcardNumber } <br />真实姓名:${item.name }
							
							<div id="idCard_${item.agentId }" style="display: none;text-align: left;" agentName="${item.name }">
								违规原因:<textarea class="rejectTextArea" cols="40" rows="3" id="idCard_${item.agentId }_textarea" extra="身份证号码:${item.idcardNumber}<br/>真实姓名:${item.name}"></textarea>
								<ul>
									<c:forEach items="${badIdreasons}" var="list" varStatus="status">
									<li>
										<input type="checkbox" id="idCard_${item.agentId }_${status.count}">
										<label for="idCard_${item.agentId }_${status.count}">${list.reason}</label>
									</li>
									</c:forEach>
								</ul>
							</div></td>
						
						<td name="head"><img alt="${item.headImg }_imgSpliter" src="${item.headImg }" onerror="this.src='${applicationScope.headPrefix}/m/house_m.png'"/>
						<div id="head_${item.agentId }" style="display: none;text-align: left;" agentName="${item.name }">
							违规原因:<textarea class="rejectTextArea" cols="40" rows="3" id="head_${item.agentId }_textarea" extra=""></textarea>
								<ul>
									<c:forEach items="${badHeadreasons}" var="list" varStatus="status">
									<li>
										<input type="checkbox" id="head_${item.agentId }_${status.count}">
										<label for="head_${item.agentId }_${status.count}">${list.reason}</label>
									</li>
									</c:forEach>
								</ul>
							</div></td>
						
						<td name="noNeed"><img  src="${item.nameCardImg }"   onerror="this.src='${applicationScope.headPrefix}/m/house_m.png'"/> <br />公司:${item.company } <br />门店:${item.store }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<c:if test="${not empty list }">
		<div align="center"><input style="height:35px;" onclick="submit();" type="button" value="提交本页"/></div>
	</c:if>
	<c:if test="${empty list }">
		<h3>暂无数据</h3>
	</c:if>
</div>

<script type="text/javascript">
<!--

	function changeCity(cityid){
	window.location.href = "/agentVerify/photoList?cityid="+cityid+"&ranNum=" + Math.random();
	}

	// 提交事件
	function submit() {
		var flag = true;
		var totalResult = "";
		var cityid = $("#city").val();
		$("textarea").each(function(){
			if ($(this).parent().css("display") != "none" && ($.trim($(this).val()) == "")) {
				flag = false;
				return;
			}
			if(!flag){
				return;
			}
			var typeAndAgentId = $(this).parent().attr("id");
			var imgUrlAndId = $(this).parent().parent().find("img").attr("alt");
			var rejectReason = $(this).val();
			if(rejectReason==""){
				rejectReason="Tnul";
			}
			var authorName = $(this).parent().attr("agentName");
			if(authorName==""){
				authorName = "Tnul";
			}
			var extra = $(this).attr("extra");
			if(extra==""){
				extra="Tnul";
			}
			totalResult += typeAndAgentId + "_placeHolder1_" + imgUrlAndId + "_placeHolder1_" + rejectReason + "_placeHolder1_" + authorName + "_placeHolder1_" + extra +"_placeHolder2_";
		});
		
		if(!flag){
			alert("请填写完整违规原因.");
			return;
		}

		$.ajax({
			type : "post",
			async : false,
			url : "/agentVerify/verify/photos",
			dataType : "json",
			data : {
				"totalResult" : totalResult
			},
			success : function(data) {
				/* 			if(data.error){
				 alert(data.error);
				 return;
				 }*/
				if(data.info=="scs"){
					window.location.href = "/agentVerify/photoList?cityid="+cityid+"&ranNum=" + Math.random();
				}
			}
		});
	}
//-->
</script>