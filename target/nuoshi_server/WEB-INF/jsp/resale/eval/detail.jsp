<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div style="width: 100%;text-align: left;">当前位置：二手房评价列表</div> 

<form action="/resale/eval/evalList/${houseId}/${sourceId}" method="post" id="evaluationForm" style="margin-top:0px;margin-top:0px;">
	
	<div class="todo">
		<table id="resaleTable" class="c" style="text-align:center">
			<thead>
				<tr>
					<th style="width:10%;">
						<input type="checkbox" id="selectAll" onClick="checkSelectAll(this.checked);"/>
					</th>
					<th style="widht:10%">房源真实性</th>
					<th style="widht:10%">价格</th>
					<th style="widht:10%">照片</th>
					<th style="widht:10%">描述</th>
					<th style="widht:10%">评价内容</th>
					<th style="width:30%;">操作</th>
				</tr>
			</thead>
			<tbody align="center" >
			<c:choose>
				<c:when test="${not empty evaluations}">
					<c:forEach items="${evaluations}" var="evaluation">
						<tr id="tr${evaluation.id}">
							<td><input type="checkbox" class="myCheck" value="${evaluation.id}" onClick="checkInfo(this.checked);"/></td>
							<td>
								<c:choose>
									<c:when test="${evaluation.exist == 0}">
										假房源
									</c:when>
									<c:when test="${evaluation.exist == 1}">
										真房源
									</c:when>
									<c:otherwise>
										未选择
									</c:otherwise>
								</c:choose>
							</td>
							<td>${evaluation.priceAcu}</td>
							<td>${evaluation.photoAcu}</td>
							<td>${evaluation.infoAcu}</td>
							<td>${evaluation.commentWords}</td>
							<td>
								<a href="javascript:delEvaluation('${evaluation.id}', '${houseId}', '${sourceId}');">删除评价</a>
							</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="main_info">
						<td colspan="7">没有相关数据</td>
					</tr>
				</c:otherwise>
			</c:choose>
			</tbody>
		</table>
	</div>
	<div style="text-align:left;">
		<input type="button" value="删除评价"  style="width: 80px;" onclick="delAllEvaluation('${houseId}', '${sourceId}');"/>
	</div>
	<jsp:include page="/WEB-INF/snippets/page.jsp" />
	
</form>

<script type="text/javascript">

	function checkSelectAll(checked) {
		if(checked) {
			$(".myCheck").attr("checked", true);
		} else {
			$(".myCheck").attr("checked", false);
		}
	}
	
	function checkInfo(checked) {
		if(checked) {
			$(".myCheck").each(function(){
				$("#selectAll").attr("checked", true);
				if(!$(this).attr("checked")) {
					$("#selectAll").attr("checked", false);
				}
			});
		} else {
			$("#selectAll").attr("checked", false);
		}
	}

	function delAllEvaluation(houseId, sourceId) {
		var evaluationIds = "";
		$(".myCheck").each(function(){
			if($(this).attr("checked")) {
				evaluationIds = evaluationIds + $(this).val() + "_";
			}
		});
		if(evaluationIds.length > 0) {
			evaluationIds = evaluationIds.substring(0, evaluationIds.length - 1);
			$.ajax({
				type : "GET",
				async : false,
				url : "/resale/eval/delEval/" + houseId + "/" + sourceId,
				data : {"ids": evaluationIds},
				dataType : "json",
				success : function(data) {
					$("#page\\.pageNo").val($("#currentNo").val());
					$("#evaluationForm").submit();
				}
			});
			
		} else {
			alert("请选择要删除的评价");
		}
		
	}
	
	function delEvaluation(id, houseId, sourceId) {
		$.ajax({
			type : "GET",
			async : false,
			url :  "/resale/eval/delEval/" + houseId + "/" + sourceId,
			data : {"ids": id},
			dataType : "json",
			success : function(data) {
				$("#page\\.pageNo").val($("#currentNo").val());
				$("#evaluationForm").submit();
			}
		});
	}

</script>