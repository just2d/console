<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="/js/common/jsdate.js"></script>
<title>淘房网后台管理系统</title>
</head>
<body>
<div style="width: 100%; text-align: left;">
	当前位置：问答管理&gt;&gt;<b>问题列表</b>
</div>
<!-- 文字、ID，审核状态分为未审核、已审核，问题状态分为待解决、零回答、已解决和关闭。列表字段包括审核状态、问题标题、分类、提问者、提问时间、问题状态、回答数和操作。点击标题弹出显示问题详情，可修改问题分类，操作为重审 -->
<form id="searchForm" action="/wenda/queryQuestionList" method="post">
	<div class="mag" style="border: 1px solid #CAD9EA">
		<span>ID：</span>
		<input class="def" type="text" id="searchId" name="id" value="${condition.id }" style="width: 163px;"/> 
		<span>标题：</span>
		<input class="def" type="text" id="searchTitile" name="title" value="${condition.title}" style="width: 163px;"/> 
		<span>详情：</span>
		<input class="def" type="text" id="searchDetails" name="details" value="${condition.details }" style="width: 163px;"/>
		<span>作者ID：</span>
		<input class="def" type="text" id="searchAuthorId" name="authorId" value="${condition.authorId }" style="width: 163px;"/>
		
		<input type="submit" value="搜索" style="width: 60px;"/>
		<input  type="button" id="clear" value="清空" style="width: 60px;" onclick="javascript:clearInput(this);"/>
		<br /><br />
		<label>提问日期:</label> 
		<input  name="beginD" class="dateCss" type="text" onclick="SelectDate(this,'yyyy-MM-dd','起始日期')" readonly="readonly" value="<fmt:formatDate value="${condition.beginPubTime }" type="both" pattern="yyyy-MM-dd"/>" />
			 - 
		<input  name="endD" class="dateCss" type="text" onclick="SelectDate(this,'yyyy-MM-dd','终止日期')" readonly="readonly" value="<fmt:formatDate value="${condition.endPubTime }" type="both" pattern="yyyy-MM-dd"/>" /> 
		<select class="cs" id="verifyResult" name="status" style="width: 170px;">
			<option value="-1">请选择审核状态</option>
			<c:forEach items="${statusMap}" var="entry">
				<option value="${entry.key }" <c:if test="${entry.key == condition.status }">selected="selected"</c:if> >${entry.value}</option>
			</c:forEach>
		</select>
		<select class="cs" id="solveResult" name="solvingStatus" style="width: 170px;" >
			<option value="-1" >请选择问题解决状态</option>
			<c:forEach items="${solvingStatusMap}" var="entry">
				<option value="${entry.key }"  <c:if test="${entry.key==condition.solvingStatus }">selected="selected"</c:if> >${entry.value}</option>
			</c:forEach>
		</select> 
		<select class="cs" id="blackSign" name="blackSign" style="width: 170px;" >
			<option value="-1" >提问者是否为黑名单</option>
			<option value="0"  <c:if test="${'0'==condition.blackSign }">selected="selected"</c:if>>正常</option>
			<option value="1"  <c:if test="${'1'==condition.blackSign }">selected="selected"</c:if>>拉黑</option>
		</select> 
	</div>
	<div class="mag">
		<div class="todo">
			<table>
				<thead>
					<tr>
						<c:if test="${empty questionList}">
							<th>问题列表为空</th>
						</c:if>
						<c:if test="${not empty questionList}">
							<!-- <th><input type="checkbox" id="selectAll" /></th> -->
							<th>ID</th>
							<th>&nbsp;&nbsp;问题分类&nbsp;&nbsp;</th>
							<th>问题标题</th>
							<th>详情</th>
							<th>提问者ID</th>
							<th>提问者用户名</th>
							<th>提问时间</th>
							<th>&nbsp;&nbsp;审核状态&nbsp;&nbsp;</th>
							<th>&nbsp;&nbsp;处理状态&nbsp;&nbsp;</th>
							<th>回答数</th>
							<th>黑名单</th>
							<th>&nbsp;&nbsp;&nbsp;操&nbsp;&nbsp;&nbsp;&nbsp;作&nbsp;&nbsp;&nbsp;</th>
						</c:if>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${questionList}" var="q">
						<tr>
							<%-- <td> <input type="checkbox" name="msgChk" value="${q.id}" /> </td> --%>
							<td>${q.id}</td>
							<td id="category_${q.id}">
								<a href="${q.cateUrl }" target="_blank">
									<c:choose>
										<c:when test="${not empty q.categoryName1}">${q.categoryName1}</c:when>
										<c:otherwise>${q.categoryName}</c:otherwise>
									</c:choose>
								</a>
							</td>
							<td> 
								<a href="http://www.taofang.com/wen/${q.url}" target="_blank" title="${q.title}">
									<c:choose>
										<c:when test="${fn:length(q.title) >= 40 }"><c:out value="${fn:substring(q.title, 0, 38)}..." escapeXml="true"/></c:when>
										<c:otherwise><c:out value="${q.title}" escapeXml="true"/></c:otherwise>
									</c:choose>
								</a> 
							</td>
							<td>
								<span  title="${q.description}">
									<c:if test="${empty q.description}">
										无详情
									</c:if>
									<c:if test="${not empty q.description}">
										<c:choose>
											<c:when test="${fn:length(q.description) >= 40 }"><c:out value="${fn:substring(q.description, 0, 38)}..." escapeXml="true"/></c:when>
											<c:otherwise><c:out value="${q.description}" escapeXml="true"/></c:otherwise>
										</c:choose>
									</c:if>
								</span>
							</td>
							<td>${q.authorId}</td>
							<td>${q.author}</td>
							<td><fmt:formatDate value="${q.pubTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td><span <c:if test="${q.status==2 }">style='color: red;'</c:if>>${statusMap[q.status]}</span></td>
							<td><span <c:if test="${q.solvingStatus==3 }">style='color: red;'</c:if>>${solvingStatusMap[q.solvingStatus]}</span></td>
							<td>${q.answers}</td>
							<td>${not empty q.blackSign&&q.blackSign!=0?"<font style='color: red;'>拉黑</font>":"正常"}</td>
							<td>
								<a href="javascript:openDialog(${q.id},${q.categoryId},${q.categoryId1});">编辑类别</a> 
								<c:if test="${q.status!=0}">
									<br/> <a href="javascript:auditReset('${q.id}');">重新审核</a>
								</c:if>
							</td>
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
<div id="divModifyCategory" style="display:none;">
	<input type="hidden" id="questionIds" value=""/>
	<span>
		<select  id="levelOne" onchange='selectOne(this.value);'>
		</select>
	</span>&nbsp;&nbsp;&gt;&gt;&nbsp;&nbsp;
	<span>
		<select id="levelTwo"><option  value="-1" >二级分类</option>
		</select>
	</span>
	<input type="button" value="确定" onclick='changeCategory()'/>
</div>
<script type="text/javascript">
	$(function() {
		$(window).height();
		// 初始化弹出窗口
		$("#divModifyCategory").dialog({show : "slide",close: function(event, ui) { closeDialog(); },bgiframe : true,autoOpen : false,height : 300,width : 500,title : "更改问题类别",modal : true,resizable : false});
	});
	//这是dialg关闭事件的回调函数
	function closeDialog(){
		$("#questionIds").val("");
	}
	function openDialog(qId,levelOne,levelTwo) {
		initSelect(levelOne,levelTwo);
		$("#questionIds").val(qId);
		$("#divModifyCategory").removeAttr("style");
		$("#divModifyCategory").dialog("option", "position", "center");
		$("#divModifyCategory").dialog("open");
	}
	
	// 初始化分类
	function initSelect(levelOne,levelTwo){
		$("#levelOne").val(levelOne);
		if(levelTwo==null&&levelTwo<=0){
			levelTwo==null;
		}
		selectOne(levelOne,levelTwo);
	}
	
	//修改问题对应的分类
	function changeCategory(){
		var questionIds=$("#questionIds").val();
		var levelOne=$("#levelOne").val();
		var levelTwo=$("#levelTwo").val();
		var categoryName1=$("#levelOne").find(":selected").text();
		if(questionIds==""||levelOne<=0){
			alert("修改分类出错！请选择分类。");
			return;
		}
		var categoryName2='';
		if(levelTwo>0){
			categoryName2=$("#levelTwo").find(":selected").text();
		}
		$.ajax({
			type : "post",
			async : false,
			url : "/wenda/category/changeCategory/"+questionIds,
			dataType : "json",
			data : {"levelOne":levelOne,"levelTwo":levelTwo,"categoryName1":categoryName1,"categoryName2":categoryName2},
			success : function(data) {
				if(data.result){
					//修改页面上对应的分类id
					//changePage(questionIds,categoryName1,categoryName2);
					$("#divModifyCategory").dialog("close");
					location.reload();
				}
				if(!data.result){
					alert("修改分类失败！");
				}
			}
		});
	}
	
	function selectOne(id,levelTwo){
		if(id<0){
			var options="<option value='-1' >二级分类</option>";
			$("#levelTwo").html(options);
			return;
		}
		$.ajax({
			type : "post",
			async : false,
			url : "/wenda/category/levelOneCategory/"+id,
			dataType : "json",
			data : {},
			success : function(data) {
				var categoryList=data.categoryList;
				var options="";
				if(id==0){
					options="<option value='-1' >一级分类</option>";
				}else{
					options="<option value='-1' >二级分类</option>";
				}
				for(var i=0;i<categoryList.length;i++){
					options+="<option value='"+categoryList[i].id+"' >"+categoryList[i].name+"</option>";
				}
				if(id==0){
					$("#levelOne").html(options);
				}else{
					$("#levelTwo").html(options);
					if(levelTwo!=null){
						$("#levelTwo").val(levelTwo);
					}
				}
			}
		});
	}
	
	//清空搜索选项
	function clearInput(obj){
		var text=$(obj).siblings("input['type'='text']").filter(".def");
		var dates=$(obj).siblings("input['type'='text']").filter(".dateCss");
		var selects=$(obj).siblings("select").filter(".cs");
		text.each(function(){ 
			$(this).val(""); 
		}); 
		dates.each(function(){ 
			$(this).val("");
		}); 
		selects.each(function(){ 
			 $(this).get(0).selectedIndex=0;
		}); 
	}
	//重新审核
	function auditReset(id){
		if(!confirm("是否重新审核。")){
			return; 
		 }
		$.ajax({
			type : "post",
			async : false,
			url : "/wenda/reaudit/1/"+id,
			dataType : "json",
			data : {},
			success : function(data) {
				var flag=data.result;
				if(flag==0){
					alert("修改成功。");
					location.reload();
				}else{
					alert("修改失败！");
				}
			}
		});
	}
	selectOne(0);
</script>
</body>
</html>