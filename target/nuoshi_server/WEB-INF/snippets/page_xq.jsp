<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<input type="hidden" id="page.pageSize" name="page.pageSize" value="${page.pageSize}" />
<input type="hidden" id="page.pageNo" name="page.pageNo" value="1" />
<input type="hidden" id="currentNo" name="currentNo" value="${page.pageNo }" />
<script type="text/javascript">

function getForm() {
	var e = document.getElementById("page.pageNo");
		while(e=e.parentNode){
			if(e.tagName=="FORM"){
				return e;
			}
			if(e.tagName=="BODY"){
				return null;
			}
		}
		
	}

function getEstateName(isClicked){
	if(isClicked = "1"){
		//最后点击了行
		$("#form_estate_id").val("");
	}
}

function openPage(pagerNo) {
		var pageNo = document.getElementById("page.pageNo");
		if(pagerNo == 1){
			pagerNo = 0;
		}
		var form = getForm();
			pageNo.value = pagerNo;
		if(form!=null){
			form.submit();
		}
	}
function changePageSize(pageSize) {
		var page_size = document.getElementById("page.pageSize");
		var pageNo = document.getElementById("page.pageNo");
		pageNo.value = 0;
		var form = getForm();
		page_size.value = pageSize;
		if(form!=null){
			form.submit();
		}
	}

function jumpPage(){
	var reg = /^[0-9]*[1-9][0-9]*$/;
	var pageNo = $("#jumpNo").val();
	var totalPage = ${page.totalPage};
	if(!reg.test(pageNo)){
		alert("只能输入整数");
	}else{
		if(pageNo>totalPage){
			pageNo = totalPage;
		}
		openPage(pageNo);
	}
	
	
}
</script>
页数：${page.pageNo}/${page.totalPage} 总记录数：${page.totalCount }
&nbsp;&nbsp; <a href="javascript:openPage(1);">首页</a>&nbsp;&nbsp;
<c:choose>
	<c:when test="${page.pageNo==1}">
		<a href="#">上一页</a>
	</c:when>
	<c:otherwise>
		<a href="javascript:openPage(${page.pageNo-1});">上一页</a>
	</c:otherwise>
</c:choose>
<c:if test="${page.pageNo>5}">
<c:forEach begin="${page.pageNo-5}" end="${page.pageNo+5>=page.totalPage?page.totalPage:page.pageNo+2}" var="index">
	&nbsp;
	<c:choose>

	<c:when test='${page.pageNo==index }'>
		<a href="javascript:openPage(${index});">${index}</a>
	</c:when>

	<c:otherwise>
		<a href="javascript:openPage(${index});">[${index}]</a>
	</c:otherwise>

	</c:choose>
</c:forEach>

</c:if>
<c:if test="${page.pageNo<=5&&page.totalPage>1}">
<c:forEach begin="1" end="${page.totalPage>=8?8:page.totalPage}" var="index">
&nbsp;
	<c:choose>

	<c:when test='${page.pageNo==index }'>
		<a href="javascript:openPage(${index});">${index}</a>
	</c:when>

	<c:otherwise>
		<a href="javascript:openPage(${index});">[${index}]</a>
	</c:otherwise>

	</c:choose>
	
</c:forEach>

</c:if>
<c:choose>

	<c:when test='${page.pageNo==page.totalPage }'>
		<a href="#">下一页</a>
	</c:when>

	<c:otherwise>
		<a href="javascript:openPage(${page.pageNo+1});">下一页</a>
	</c:otherwise>

</c:choose>
&nbsp;&nbsp; <a href="javascript:openPage(${page.totalPage});">末页</a>&nbsp;
每页20
<%--
<select onchange="changePageSize(this.value)">
	<option value="20" <c:if test="${page.pageSize==20 }"> selected="selected"</c:if>>20</option>
	<option value="30" <c:if test="${page.pageSize==30 }"> selected="selected"</c:if>>30</option>
	<option value="50" <c:if test="${page.pageSize==50 }"> selected="selected"</c:if>>50</option>
	<option value="100" <c:if test="${page.pageSize==100 }"> selected="selected"</c:if>>100</option>
	<option value="200" <c:if test="${page.pageSize==200}"> selected="selected"</c:if>>200</option>
	<option value="300" <c:if test="${page.pageSize==300 }"> selected="selected"</c:if>>300</option>
	<option value="500" <c:if test="${page.pageSize==500 }"> selected="selected"</c:if>>500</option>
</select> --%>
条
跳到第<input type="text" name="jumpNo" id="jumpNo" size="3" />页&nbsp;&nbsp;&nbsp;<input type="button" value="确定" onclick="jumpPage()"/>
