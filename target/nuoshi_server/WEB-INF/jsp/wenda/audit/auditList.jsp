<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<head>
<meta http-equiv="Expires" CONTENT="0">
<meta http-equiv="Cache-Control" CONTENT="no-cache">
<meta http-equiv="Pragma" CONTENT="no-cache">
</head>

<script type="text/javascript" src="/js/common/jquery.blockUI.js"></script>
<div style="width: 100%;text-align: left;">当前位置：${type==1?"问题":"回答"}审核</div>  
<br />
	<form action="" method="post" id="wendaForm" >
		<div class="mag ">
               <div style="float: right">
					<c:if test="${not empty auditTaskList }">
               		<input  type="button" value="清空当前用户领取的任务" onclick="emptyWenDaTask();"/>
               		<input id="remainingNum" type="button" value="获取剩余数量" onclick="getRemainingNum(${type});"/>
						<input type="checkbox" name="chk_all" id="chk_all"  />全选/取消全选
					</c:if>
					<c:if test="${empty auditTaskList }">
					</c:if>
				</div>
		</div>
		<input type="hidden" id="wendaType" value="${type} "/>
		<div class="mag">
			<div class="todo">
				<table>
					<tbody class="rasent">
						<c:forEach items="${auditTaskList}" var="list">
							<tr style=" width:12.5%; text-align:center;" class="todo" onclick="openDialog(this);" id="changeCategory_${list.id}">
								<th colspan="2">
									<span style=" width:12.5%;float:left;padding-left:10px;">
										<c:if test="${list.categoryName!=null}">（<label>分类：</label><span id="category_${list.id}">${list.categoryName}&nbsp;-&nbsp;${list.categoryName1}</span>）</c:if>
										<c:choose>
											<c:when test="${fn:length(list.title) >= 90 }"><c:out value="${fn:substring(list.title, 0, 90)}..." escapeXml="true"/></c:when>
											<c:otherwise><c:out value="${list.title}" escapeXml="true"/></c:otherwise>
										</c:choose>
									</span>
								</th>
							</tr>
							<tr >
								<td style=" width:12.5%; text-align:center" colspan="2">
								<div style="width:990px;;word-wrap:break-word;text-align:left;">
										<c:if test="${list.description == null ||list.description =='' }">
			                               	 没有描述信息
			                            </c:if>
			                            <c:if test="${list.description != null&&list.description !='' }">
			                                <c:out value="${list.description}" escapeXml="true"/>
			                            </c:if>
			                      </div>
								</td>
							</tr>
							<tr  >
								<td colspan="2"  id="span_${list.id}" onclick="runReject(this);">
									<div style=" width:100%; " >
										<span style="float:left;">
											<label >发布者：</label>&nbsp;${list.author }&nbsp;&nbsp;(ID ：${list.authorId})
											&nbsp;&nbsp;&nbsp;&nbsp;
											<label>发布时间：</label>&nbsp;<fmt:formatDate value="${list.pubTime}" pattern="yyyy-MM-dd"/>
										</span>
										<span style="	float:right">
											<input type="checkbox" value="${list.id}" name="reject_${list.id}"  disabled="disabled"/>
											<input type="hidden" name="idAndResult"  id="result_${list.id}" value="${list.id}" />
											 <span style="" id="deleteSign_${list.id}">删除</span>&nbsp;&nbsp;&nbsp;
											<!-- <a href="javascript;" >[展开]</a> -->
										</span>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	<div  style="text-align: center;" >
	<c:if test="${not empty auditTaskList }">
			<input id="submitButton" type="button" value="提交并继续" onClick="submitIt();" />
		</c:if>
		<c:if test="${empty auditTaskList }">
			<h2>已经全部审核完。</h2>
		</c:if>
	</div>
	</form>
<div id="divModifyCategory" style="display:none;">
	<input type="hidden" id="questionIds" value=""/>
	<span>
		<select  id="levelOne" onChange='selectOne(this.value);'>
		</select>
	</span>&nbsp;&nbsp;&gt;&gt;&nbsp;&nbsp;
	<span>
		<select id="levelTwo"><option  value="-1" >二级分类</option>
		</select>
	</span>
	<input type="button" value="确定" onclick="changeCategory();"/>
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
function openDialog(obj) {
	var type= $("#wendaType").val();
	if(type!=1){
		return;
	}
	$("#divModifyCategory").removeAttr("style");
	var getId=obj.id;
	//判读是否选择打回原因
	var ids=getId.split('_')[1];
	$("#questionIds").val(ids);
	$("#divModifyCategory").dialog("option", "position", "center");
	$("#divModifyCategory").dialog("open");
}
//全选
$("#chk_all").click(function(){
	checkAllBox($("#chk_all")[0]);
});
//全选执行的方法
function checkAllBox(obj){
	//清空单选时存放id的input
	$("input[name^='reject_']").attr("checked",$(obj).attr("checked")=='checked'?true:false);
	if($(obj).attr("checked")=='checked'){
		deleteForAll();
		setFontColorAll(true);
	}else{
		var reasonAll=$("input[id^='result_']");
		setFontColorAll(false);
		for(var j = 0 ;j<reasonAll.length;j++){
			var getId=reasonAll.eq(j).attr("id");
			var wendaId=getId.split('_')[1];
			$('#'+getId).val(wendaId);
		}
	}
}
//全选时页面删除事件
function deleteForAll(){
	var reasonAll=$("input[id^='result_']");
	for(var j = 0 ;j<reasonAll.length;j++){
		var getId=reasonAll.eq(j).attr("id");
		var wendaId=getId.split('_')[1];
		$('#'+getId).val(wendaId+"#_#2");
	}
}
//提交审核
function submitIt(){
	var type= $("#wendaType").val();
	$("#submitButton").attr("disabled","disabled");
	// 遮罩层
  	$.blockUI({message: "<h3>正在提交数据，请稍候……</h3>"});
	$("#wendaForm").attr("action","/wenda/doverify/"+type);
    document.getElementById('wendaForm').submit();
    $("#submitButton").removeAttr("disabled");
}
//点击表格联动对应的checkbox
function runReject(obj){
	var divId=$(obj).attr("id");
	var wendaId=divId.split('_')[1];
	var targetCheckbox = $("input[name='reject_"+wendaId+"']");
	if (targetCheckbox.attr("checked") == "checked") {
		targetCheckbox.removeAttr("checked");
		setFontColor(false,wendaId);
	} else {
		targetCheckbox.attr("checked",true);
		setFontColor(true,wendaId);
	}
	reject(targetCheckbox[0]);
}
//设置删除的颜色
function setFontColor(flag,id){
	if(flag){
		$("#deleteSign_"+id).attr("style","color:red;");
		//alert($("#deleteSign_"+id).attr("style"));
	}else{
		//$("#deleteSign_"+id).attr("style","color:black;");
		$("#deleteSign_"+id).removeAttr("style");
		//alert($("#deleteSign_"+id).attr("style"));
	}
}
//设置全部删除的颜色
function setFontColorAll(flag){
	if(flag){
		$("span[id^='deleteSign_']").attr("style","color:red;");
	}else{
		$("span[id^='deleteSign_']").removeAttr("style");
	}
}
//删除
function reject(obj){
	var wendaId=$(obj).val();
	var resultId="result_"+wendaId;
	if($(obj).attr("checked")=="checked"){
		$("#"+resultId).val(wendaId+"#_#2");
	}else{
		$("#"+resultId).val(wendaId);
	}
}
function selectOne(id){
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
			}
		}
	});
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
				changePage(questionIds,categoryName1,categoryName2);
				$("#divModifyCategory").dialog("close");
			}
			if(!data.result){
				alert("修改分类失败！");
			}
		}
	});
}
function changePage(questionIds,categoryName1,categoryName2){
	str="";
	if(categoryName2!=''){
		str=" - "+categoryName2;
	}
	$("#category_"+questionIds).text(categoryName1+str);
}
//获取审核剩余数量
function getRemainingNum(type){
	$.ajax({
		type : "post",
		async : false,
		url : "/wenda/getRemainingNum/"+type,
		dataType : "json",
		data : {},
		success : function(data) {
			$("#remainingNum").val("剩余："+data.count);
		}
	});
}
//清空当前登录人的任务
function emptyWenDaTask(){
	$.ajax({
		type : "post",
		async : false,
		url : "/wenda/emptyWenDaTask",
		dataType : "json",
		data : {},
		success : function(data) {
			var result=data.result;
			if(result){
				 window.location.href="http://console.taofang.com/login/index"; 
			}else{
				alert("清空任务失败！");
			}
		}
	});
}
selectOne(0);
</script>

