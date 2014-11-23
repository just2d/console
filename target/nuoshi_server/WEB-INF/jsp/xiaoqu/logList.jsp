<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">
<!--
contextPath = '${contextPath}';
//-->
</script>

<script type="text/javascript">
<!--
	function searchAndSubmit(){
		var estateid = $.trim($("#estateid").val());
		var targetId = $.trim($("#targetId").val());
		if(estateid == ""){
			estateid = 0;
		}
		if(targetId == ""){
			targetId = 0;
		}
		var reg = /^[0-9]\d*$/;
		if(!reg.test(estateid)||!reg.test(targetId)){
			alert("小区id只能为整数");
			return ;
		}else{
			$("#unionLogListForm").submit();	
		}
	}

	function cancelUnion(sid,tid,showHouse,authstatus,houseId){
		$.ajax({
				type: "post",  
	   			dataType: "json",  
	   			url: 'cancelUnion',//提交到一般处理程序请求数据   
	   			data: "showHouse=" + showHouse + "&estateid=" + sid+"&targetId="+tid+"&s_authStatus="+authstatus+"&houseId="+houseId+"&time="+new Date(),
	   			beforeSend:function(){
					$("#loading").html('<div style="margin:40px;text-align:center" ><img src="'+contextPath+'/images/loading.gif"/></div>');
	   			},
	   			success: function(data){
	   				if(typeof(data)!="undefined" && data != null && data.success == true){
	   					window.location.href = contextPath+"/estate/unionLog";
	   				}else{
	   					alert("系统错误!!!");
	   				}
	   			},
	   			complete: function(){
	   				$("#loading").html("");
				}
			});
	}
//-->
</script>
<div id="loading"></div>
<form action="/estate/unionLog" method="post" id="unionLogListForm" >
<div align="left">合并前小区id：<input type="text" name="estateid" value="${condition.estateid}" id="estateid"/>
合并后小区id：<input type="text" name="targetId" value="${condition.targetId}" id="targetId"/>
<%--显示房源id: 是<input type="radio" name="showHouse" value="1" ${condition.showHouse eq 1 ? 'selected = selected' : ''}/> &nbsp;&nbsp;&nbsp;否<input type="radio" name="showHouse" value="0" ${condition.showHouse == 0 ?  'selected =selected' : ''}/> --%>
<input class="def" type="button" value="搜索" style="width: 60px;" onclick="searchAndSubmit()"/>
</div>
<div class="mag">
	<div class="todo">
		<table id="statTable">
			<thead>
				<tr>
					<th>合并前小区id</th>
					<th>合并后小区id</th>
					<th>合并前小区名</th>
					<th>合并后小区名</th>
					<c:if test="${condition.showHouse eq 1}">
						<th>房源id</th>
					</c:if>
					<th>合并时间</th>
					<th>操作人</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${unionLogList}" var="unionLog">
					<tr >
						<td>${unionLog.estateid}</td>
						<td><a href="http://${unionLog.t_CityCode}.${sysDomain}/xiaoqu/${unionLog.targetId}/" target="_blank">${unionLog.targetId}</a></td>
						<td>${unionLog.estatename}</td>
						<td>${unionLog.targetName}</td>
					<c:if test="${condition.showHouse eq 1}">
						<td>${unionLog.houseId}</td>
					</c:if>
						<td><fmt:formatDate value="${unionLog.cts}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${unionLog.operator}</td>
						<td><a href="javascript:void(0)" onclick="cancelUnion('${unionLog.estateid}','${unionLog.targetId}','${condition.showHouse}','${unionLog.s_authStatus}','${unionLog.houseId}')">恢复</a></td>
					</tr>
				</c:forEach>
			</tbody> 
		</table>
</div>
<div class="page_and_btn" align="center">
	<jsp:include page="/WEB-INF/snippets/page_xq.jsp" />
</div>
</form>
