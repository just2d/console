<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="/js/wenda/category.js"></script>
<c:if test="${firstId!=null}">
	<script >
		changeCategory('${firstId}', '${secoundId}');
	</script>
</c:if>
<div style="width: 100%; text-align: left;">当前位置：分类管理 &gt; 分类列表</div>
<br />
<!-- 增加部分 -->
<div id="addCategoryDiv" style="text-align: left;">
	<!-- <b><label style="color:black;">添加分类：</label></b> -->
	<form action="/wenda/category/save" method="post" id="addCategoryForm"
		style="margin-top: 0px; margin-top: 0px;">
		<input type="hidden" id="id" name="id"/>
		<input type="hidden" id="oldName" name="oldName"/>
		<table border="0" cellpadding="3" cellspacing="1">
			<tr>
				<td><span class="important">*</span>分类名称</td>
				<td><input style="width: 200px;" id="name" name="name" type="text" value="" maxlength="30"/>
					<span id="nameSp" style="display:none;"><font color="red">用户名不能为空</font></span>
				</td>
			</tr>
			<tr>
				<td><span class="important">*</span>上级分类</td>
				<td><select style="width: 200px;" id="pid" name="pid">
						<option value="0">无</option>
						<c:choose>
							<c:when test="${not empty categoryParam}">
								<c:forEach items="${categoryParam}" var="categoryParam">
									<option value="${categoryParam.id}"  <c:if test="${pid==categoryParam.id}">slescted="selected"</c:if>>${categoryParam.name}</option>
								</c:forEach>
							</c:when>
						</c:choose>

				</select></td>
			</tr>
			<tr>
				<td>分类关键词</td>
				<td><input style="width: 350px;" id="keyword" name="keyword"
					type="text" value="" onMouseOut="addreadonly();" maxlength="50"
					readonly="readonly" /><input type="button" value="编辑"
					onclick="edit()"><br> <label style="color: gray;">此关键词用于系统问题分类，无下级分类的必填</label>
				</td>
			</tr>
			<tr>
				<td><span class="important">*</span>title</td>
				<td><input style="width: 400px;" id="title" name="title" type="text" value="" maxlength="30"/>
					<span id="titleSp" style="display:none;"><font color="red">title不能为空</font></span>
				</td>
			</tr>
			<tr>
				<td><span class="important">*</span>描述</td>
				<td><input style="width: 400px;" id="describle" name="describle" type="text" value="" maxlength="30"/>
					<span id="descSp" style="display:none;"><font color="red">描述不能为空</font></span>
				</td>
			</tr>
			<tr>
				<td><span class="important">*</span>页面关键词</td>
				<td><input style="width: 400px;" id="pageword" name="pageword" type="text" value="" maxlength="30"/>
				<span id="pagewordSp" style="display:none;"><font color="red">页面关键词不能为空</font></span>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;"><input
					type="button" value="提交" onclick="savecategory();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
					type="button" value="取消" onclick="cancle();"></td>
			</tr>
		</table>
	</form>
</div>
<!-- 主体 -->
<form action="/wenda/category/list/-1" method="post" id="categoryForm"
	style="margin-top: 0px; margin-top: 0px;">
	<!-- 查询部分 -->
	<div style="text-align: center;">
		<div>
			<label>一级分类：</label> <select id="firstid" name="firstid"
				style="width: 100px;" onchange="changeCategory(this.value, null)">
				<option value="0">全部</option>
				<c:choose>
					<c:when test="${not empty categoryParam}">
						<c:forEach items="${categoryParam}" var="categoryParam">
							<option value="${categoryParam.id}"
								<c:if test="${categoryParam.id==firstId}">selected</c:if>>${categoryParam.name}</option>
						</c:forEach>
					</c:when>
				</c:choose>
			</select> <label>二级分类：</label> <select id="secoundid" name="secoundid"
				style="width: 100px;">
				<option value="0">全部</option>
			</select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input
				type="button" value="查询" onclick="query();"> <input
				type="reset" value="重置" > <input
				type="button" value="添加" onclick="add();">&nbsp;
				<span style="float: right"><input type="button" value="更新问答缓存" onclick="updateCache();">&nbsp;&nbsp;&nbsp;&nbsp;</span>
		</div>
		<c:if test="${hide==1}">
			<div style="text-align: left;">
				<a href="javascript:backoff()">返回上一级</a>
			</div>
		</c:if>
	</div>
	<!-- 列表部分 -->
	<div class="todo">
		<table id="packageTable" class="c" style="text-align: center">
			<thead>
				<tr>
					<th style="width: 8%;">分类名称</th>
					<th style="width: 8%;">上级分类</th>
					<th style="width: 30%;">搜索关键词</th>
					<th style="width: 5%;">分类状态</th>
					<th style="width: 8%;">操作</th>
					<th style="width: 15%;">关闭时间</th>
					<c:if test="${hide!=1}">
						<th style="width:5%">定位</th>
					</c:if>
				</tr>
			</thead>
			<tbody align="center">
				<c:choose>
					<c:when test="${not empty categoryList}">
						<c:forEach items="${categoryList}" var="category">
							<tr>
								<td><c:if test="${category.pid==0}">
										<a href="javascript:searchCategoryById(${category.id})">${category.name}</a>
									</c:if> <c:if test="${category.pid!=0}">
									${category.name}
								</c:if>
								</td>
								<td>${category.parentName}</td>
								<td>${category.keyword}</td>
								<td><c:if test="${category.status==0}">正常</c:if> <c:if
										test="${category.status==1}">
										<font color="red">关闭</font>
									</c:if>
								</td>
								<td><a href="javascript:modify(${category.id})">编辑</a> | 
										<c:if test="${category.status==0}"><a href="javascript:closeup(${category.id}, ${category.pid},1)">关闭</a></c:if>
										<c:if test="${category.status==1}"><a href="javascript:closeup(${category.id}, ${category.pid}, 0)">开启</a></c:if>
								</td>
								<td>${category.closeTimeStr}</td>
								<c:if test="${hide!=1}">
									<td><input id="${category.id}" type="text" value="<c:if test="${not empty category.location}">${category.location}</c:if><c:if test="${empty category.location}">1</c:if>" name="ids" style="width:30px;"></td>
								</c:if>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="6">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
	<div class="page_and_btn">
		<jsp:include page="/WEB-INF/snippets/page.jsp" />
	</div>
</form>
