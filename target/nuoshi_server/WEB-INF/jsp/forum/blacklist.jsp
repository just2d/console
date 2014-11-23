<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div style="width: 100%; text-align: left;">
	当前位置：论坛管理&gt;&gt;<b>黑名单管理</b>
</div>
<form id="blacklistForm" action="/forum/black/list" method="post">
	<div class="mag" style="border: 1px solid #CAD9EA">
		<label class="def">用户名:</label> 
		<input class="def" type="text" id="userName" name="userName" style="width: 163px;" value="${userName }"> 
		<input class="def" type="submit" value="搜索" style="width: 60px;"> 
		<input class="def" type="reset" value="重置" style="width: 60px;"> 
		<input class="def" id="addBtn" type="button" value="添加黑名单" style="width: 110px;">
	</div>
	<div class="mag">
		<div class="todo">
			<table>
				<thead>
					<tr>
						<c:if test="${empty blackList}">
							<th>黑名单列表为空</th>
						</c:if>
						<c:if test="${not empty blackList}">
							<th>用户ID</th>
							<th>用户名</th>
							<th>角色</th>
							<th>添加时间</th>
							<th>状态</th>
							<th>操作</th>
						</c:if>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${blackList}" var="black">
						<tr>
							<td>${black.userId}</td>
							<td>${black.userName}</td>
							<td>
								<c:if test="${black.role==1}">普通用户</c:if> 
								<c:if  test="${black.role==2}">经纪人</c:if>
							</td>
							<td>
								<fmt:formatDate value="${black.cts}" 	pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<c:choose>
								<c:when test="${black.status == 0}">
									<td id="${black.id}_${black.status}">正常</td>
									<td id="${black.id}"><a
										href="javascript:updateStatus(1,'${black.userId}','${black.id}')">加入黑名单</a>
									</td>
								</c:when>
								<c:otherwise>
									<td id="${black.id}_${black.status}">黑名单</td>
									<td id="${black.id}"><a
										href="javascript:updateStatus(0,'${black.userId}','${black.id}')">解锁</a>
									</td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div class="page_and_btn">
		<jsp:include page="/WEB-INF/snippets/page.jsp" />
	</div>
</form>
<div id="divAdd" >
	<p style="width: 400px;">
		<select style="float: left;width:100px;" name="dia_type" id="dia_type" >
			<option value="1" >用户名：</option>
			<option value="2" >I&nbsp;&nbsp;&nbsp;D：</option>
		</select>
		<input type="text" id="dia_userInfo" name="userInfo" style="float:left;margin-left: 10px;"/>
	</p>
	<p style="clear:both;width: 400px;margin-top: 50px;"" >
		<label style="float: left;display:block;width:100px;text-align: left;"><b>角色:</b></label> 
		<select id="dia_role" name="role" style="color: gray;float:left;margin-left: 10px;">
			<option value="-1">请选择角色</option>
			<option value="1">普通用户</option>
			<option value="2">经纪人</option>
		</select>
	</p>
</div>
<script type="text/javascript">
function updateStatus(status,userId,id){
 $.getJSON("${contextPath}/forum/black/udpateStatus?status="+status+"&userId="+userId, null, function(json){
	  if(json.success){
		  if(status == 1){
			  $("#"+id+"_0").html("黑名单");
			  $("#"+id+"_0").attr("id",id+"_1");
			  $("#"+id).html("<a href=javascript:updateStatus(0,"+userId+","+id+")>解锁</a>");
		  }else{
			  $("#"+id+"_1").html("正常");
                 $("#"+id+"_1").attr("id",id+"_0");
                 $("#"+id).html("<a href=javascript:updateStatus(1,"+userId+","+id+")>加入黑名单</a>");
		  }
	  }   	    
 });    	 
}
// 显示增加黑名单窗口
function showAddDialog() {
    $("*").stop();
    $("#err_msg_userInfo").html("");
    $("#err_msg_role").html("");
    $("#divAdd").dialog("close");
    $("#divAdd").dialog("option", "position", "center");
    $("#divAdd").dialog("open");
}
function blackAdd(){
	var userInfo = $.trim($("#dia_userInfo").val());
	var role = $.trim($("#dia_role").val());
	var type = $.trim($("#dia_type").val());
	if(role == null || role == "-1"){
        alert("请选择角色！");
        return ;
    }
	if(userInfo == null || userInfo == ""){
		alert("请输入用户名!");
		return ;
	}else {
		$.getJSON("${contextPath}/forum/black/userNameValidate?userInfo="+userInfo+"&type="+type, null, function(json){
			if(json.exist){
			    alert("用户已在列表中,请勿重复添加！");
			    return;
			}else{
				$.getJSON("${contextPath}/forum/black/addBlackUser?userInfo="+userInfo+"&role="+role+'&type='+type, null, function(json){
			        if(json.success){
			            window.location.href="${contextPath}/forum/black/list";
			        }else{
			            alert("用户不存在或添加过程中出现错误!");
			            return;
			        }
			    });			
			}
		});
	}
	
	
}

//清空增加黑名单页面
function clearAddDiv(){
    $("#mobile").val("");
    $("#reasonAdd").val("请选择");
    $("#commentAdd").val("");
    $("#divAdd").dialog("close");
}

$(function() {
	 $("#addBtn").bind("click",showAddDialog);
	 $("#divAdd").dialog({
		 show : "slide", 
		 bgiframe : true, 
		 autoOpen : false,
		 //height : 280,
		 width : 450,
		 title : "添加黑名单", 
		 modal : true,
		 resizable : false, 
		 buttons : {"添加" : blackAdd,"取消" : clearAddDiv}
	 });
});
     
</script>