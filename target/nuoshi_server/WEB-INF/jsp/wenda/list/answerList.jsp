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
	当前位置：问答管理&gt;&gt;<b>回答列表</b>
</div>
<form id="searchForm" action="/wenda/queryAnswerList" method="post">
	<div class="mag" style="border: 1px solid #CAD9EA">
		<span>回答ID：</span>
		<input class="def" type="text" id="searchId" name="answerId" value="${condition.answerId }" style="width: 163px;"/> 
		<span>问题ID：</span>
		<input class="def" type="text" id="searchQuestionId" name="questionId" value="${condition.questionId }" style="width: 163px;"/> 
		<span>标题：</span>
		<input class="def" type="text" id="searchTitile" name="questionTitle" value="${condition.questionTitle}" style="width: 163px;"/> 
		<span>回答详情：</span>
		<input class="def" type="text" id="searchDetails" name="content" value="${condition.content }" style="width: 163px;"/>
		
		<input  type="submit" value="搜索" style="width: 60px;"/>
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
		<span>作者ID：</span>
		<input class="def" type="text" id="searchAuthorId" name="authorId" value="${condition.authorId }" style="width: 163px;"/>
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
						<c:if test="${empty answerList}">
							<th>回答列表为空</th>
						</c:if>
						<c:if test="${not empty answerList}">
							<!-- <th><input type="checkbox" id="selectAll" /></th> -->
							<th>回答ID</th>
							<th>问题ID</th>
							<th>问题标题</th>
							<th>回答详情</th>
							<th>回答者ID</th>
							<th>回答者用户名</th>
							<th>回答时间</th>
							<th>&nbsp;&nbsp;审核状态&nbsp;&nbsp;</th>
							<th>黑名单</th>
							<th>投票数</th>
							<th>&nbsp;&nbsp;&nbsp;操&nbsp;&nbsp;&nbsp;&nbsp;作&nbsp;&nbsp;&nbsp;</th>
						</c:if>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${answerList}" var="q">
						<tr>
							<%-- <td> <input type="checkbox" name="msgChk" value="${q.id}" /> </td> --%>
							<td>${q.id}</td>
							<td>${q.questionId}</td>
							<td> 
								<a href="http://www.taofang.com/wen/0/${q.questionId}.html" target="_blank" title="${q.title}">
									<c:choose>
										<c:when test="${fn:length(q.title) >= 40 }"><c:out value="${fn:substring(q.title, 0, 38)}..." escapeXml="true"/></c:when>
										<c:otherwise><c:out value="${q.title}" escapeXml="true"/></c:otherwise>
									</c:choose>
								</a> 
							</td>
							<td>
							<!-- 回答详情 -->
								<span  title="${q.content}">
									<c:if test="${not empty q.content}">
										<c:choose>
											<c:when test="${fn:length(q.content) >= 40 }"><c:out value="${fn:substring(q.content, 0, 38)}..." escapeXml="true"/></c:when>
											<c:otherwise><c:out value="${q.content}" escapeXml="true"/></c:otherwise>
										</c:choose>
									</c:if>
								</span>
							<!-- 回答详情 -->
							</td>
							<td>${q.authorId}</td>
							<td>${q.author}</td>
							<td><fmt:formatDate value="${q.pubTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td><span <c:if test="${q.status==2 }">style='color: red;'</c:if>>${statusMap[q.status]}</span></td>
							
							<td>${not empty q.blackSign&&q.blackSign!=0?"<font style='color: red;'>拉黑</font>":"正常"}</td>
							<td>${q.poll}</td>
							<td>
								<c:if test="${q.status!=0}">
										<a href="javascript:auditReset('${q.id}');">重新审核</a>
								</c:if>
								<c:if test="${q.status==0}">
									无操作
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
<script type="text/javascript">
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
			url : "/wenda/reaudit/2/"+id,
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
</script>
</body>
</html>