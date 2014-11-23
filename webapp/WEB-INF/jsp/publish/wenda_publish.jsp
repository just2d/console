<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<div style="width: 100%;text-align: left;">当前位置：发布&gt;&gt;<b>问答发布</b></div>
<form id="listForm" action="/publish/userlist" method="post">
	<div class="mag" style="border: 1px solid #CAD9EA">
		提问&nbsp; 
		<label class="def" style="margin-left: 15px;">角色：</label> 
		<select name="userRole" id="userRole" style='margin-right: 20px;' onchange="addPublisher($(this).val());">
			<option selected="selected" value="1">普通用户</option>
			<option value="2">经纪人</option>
		</select> 
		<label class="def">用户名：</label> 
		<select name="answerUserId" id="userId" style='margin-right: 20px;' >
		</select> 
		<!-- <input type="checkbox" value="0" id="userRandom" />
		<label class="def" style='margin-right: 20px;' >随机选择用户</label>  -->
		<label class="def">城市：</label> 
		<select name="cityId" id="cityId" style='margin-right: 20px;'>
		</select> 
		<input class="def"  id="addBtn" type="button" value="添加" style="width: 110px; margin-left: 20px;"/>
		<a style='margin-left: 40px;' href='#addAnswerMao'>去添加新回答</a>
	</div>
	<div class="mag">
		<div class="todo">
			<label class="def">分类：</label> 
			<select id="firstid" name="firstid" style="width: 100px;" onchange="changeCategory(this.value, null)">
				<option value="0" selected="selected">一级分类</option>
				<c:if test="${not empty categoryParam}">
					<c:forEach items="${categoryParam}" var="categoryParam">
						<option value="${categoryParam.id}" >${categoryParam.name}</option>
					</c:forEach>
				</c:if>
			</select> &gt;&gt;
			<select id="secoundid" name="secoundid" style="width: 100px;">
				<option value="0" selected="selected">二级分类</option>
			</select>
			
			<label class="def" style="margin-left: 20px; color: red;">问题50字以内</label>
			<div>
				<textarea name="title" id="questionTitle" cols="50" rows="3">${title }</textarea>
			</div>
			<div onclick="showDetails()">
				<h3>+问题详情(点击展开)</h3>
				<span id="surplusDetailWords"></span>
			</div>
			<div style="<c:if test="${empty description}"> display:none;</c:if>" id="questionDetail">
				<textarea name="description" id="questionDescription" cols="92" rows="5">${description }</textarea>
			</div>
		</div>
	</div>
	<!--华丽的分割线  -->
	<HR style="FILTER: alpha(opacity = 100, finishopacity = 0, style = 3); margin-top: 20px; margin-bottom: 20px;" width="90%" color=#987cb9 SIZE=10 />
	<div id="answerDetail">
		<!--增加回答框的地方  -->
		<div id=answerDivList></div>
	</div>
	<div onclick="addAnswerDiv()" class="mag" id="addAnswerMao"  >
		<h2>&nbsp;+&nbsp;回答(点击添加)</h2>
	</div>
	<!-- 其他列表中该去掉的发布者  -->
<!-- 	<input type="hidden" id="chengePublisherId" value=""/>
	<input type="hidden" id="chengePublisherName" value=""/> -->
</form>


<script type="text/javascript">
//显示问题描述输入框
function showDetails(){
	var display=document.getElementById("questionDetail").style.display;
	var values="block";
	if(values==display){
		values="none";
	}
	document.getElementById("questionDetail").style.display=values;
}
/* 	<div class="mag" style="border:2px solid #CAD9EA;" >
	<label class="def"  style="margin-left: 15px;">角色：</label>
	<select name="answerUserRole"  style='margin-right: 20px;'>
		<option selected="selected" value="1">普通用户</option>
		<option value="2">经纪人</option>
	</select>
<label class="def">用户名：</label>
	<select name="answerUserId"  >
		<option selected="selected" value="0">选择用户(默认随机)</option>
		<option value="11111">超能小猫咪</option>
		<option value="23333">超能小熊猫</option>
		<option value="44441">超能小猫</option>
	</select>
<input type="checkbox" value="0" /><label class="def" style='margin-right: 20px;'>随机选择用户</label>
<textarea name="answerList"  cols="92" rows="5" >${answer}</textarea>
</div> */
//
function MyAnswer() {
	 this.content='';        
     this.authorId= 0;
}
function MyQuestion() {
	 this.categoryId=0;        
     this.categoryId1= 0;
     this.authorId= 0;
     this.title= "";
     this.description= "";
     this.cityId= 0;//默认作者城市
}
//回答的框子数目
var index=1;
//显示回答框
function addAnswerDiv(){
	//alert(answers);
	var pub="<div class='mag' style='border:2px solid #CAD9EA;' >回答"+index+
	" 	<label class='def'  style='margin-left: 15px;'>角色：</label> 	<select name='answerUserRole'  style='margin-right: 20px;'> 	"+
	"	<option selected='selected' value='1'>普通用户</option> 		<option value='2'>经纪人</option> 	</select> "+
	"<label class='def'>用户名：</label> ";
	
/* 	pub+="	<select name='answerUserId' id='answerUser"+index+"'  onchange='operateOption(this);'> 	<option selected='selected' value='0'>选择用户(默认随机)</option> ";
	
	<c:forEach var="user" items="${userList}" >
		pub += "<option value='${user.id}'>${user.userName}</option>  ";
	</c:forEach>
	
	pub+=" </select> "; */
	
	pub+="	<input type='checkbox'  checked='checked' disabled='disabled'/><label class='def' style='margin-right: 20px;' >随机选择用户</label>    <a href='#top'><u> TOP</u> </a>"+
	"<textarea name='answerList'  cols='92' rows='5' >${answer}</textarea> </div>";
	
	$(pub).insertBefore("#answerDivList");
	index++;
}
//发布问题和答案
function pubQuestion(){
	//问题
	var question=new MyQuestion();
	question.categoryId=$("#secoundid").val();        
	question.categoryId1= $("#firstid").val();
	question.authorId= $("#userId").val();
	question.title = $("#questionTitle").val();
	question.description= $("#questionDescription").val();
	question.cityId=$("#cityId").val();
	var userRole=$("#userRole").val();
	if(question.authorId==0){
		//随机时为角色类型 1普通用户   2经纪人
		question.authorId=userRole;
	}
	
	//回答
	var answerList=new Array();
	$("textarea[name='answerList']").each(function() {
		var obj=new MyAnswer();
		obj.content=$(this).val();
		/* //获取发布人id
		var selectObj=$(this).siblings("select[name='answerUserId']");
		if(selectObj.length > 0){
			obj.authorId=selectObj.val();
			} */
		//获取发布人角色(放在作者id中  1为普通用户，2为经纪人)
		var selectObjRole=$(this).siblings("select[name='answerUserRole']");
		if(selectObjRole.length > 0){
			obj.authorId=selectObjRole.val();
			}
		answerList.push(obj);
		}
	);
	
	
	$.ajax({
		type : "post",
		async : false,
		url : "/publish/wenda/pub",
		dataType : "json",
		data : {
			"answerObj" : JSON.stringify(answerList),
			"questionObj" : JSON.stringify(question)
		},
		success : function(data) {
		}
	});
	
}

$(function() {
	//为控件绑定事件 
	$("#addBtn").bind("click",pubQuestion);
});


//添加备选发布者
function addPublisher(userRole){
 	if(userRole!=1 && userRole!=2){
 		userRole==1;
	}
	var optionStr="<option selected='selected' value='0'>选择用户(默认随机)</option> " ;
	<c:forEach var="user" items="${userList}" >
		var role='${user.role}';
		if(role==userRole){
			optionStr += "<option value='${user.id}'>${user.userName}</option>  ";
		}
	</c:forEach>
	$("select[name='answerUserId']").html(optionStr);
}
//添加城市
function addCity(){
	var optionStr="<option selected='selected' value='0'>城市(默认发帖人城市)</option> " +
			"<option value='1'>bj北京</option>  "+
			"<option value='2'>sh上海</option>  "+
			"<option value='3'>gz广州</option>  "+
			"<option value='4'>sz深圳</option>  "+
			"<option value='18'>tj天津</option>  "+
			"<option value='37'>cq重庆</option>  "+
			"<option value='122'>qd青岛</option>  "+
			"<option value='158'>wh武汉</option>  "+
			"<option value='304'>fz福州</option>  "+
			"<option value='414'>cs长沙</option>  "+
			"<option value='483'>xa西安</option>  "+
			"<option value='541'>km昆明</option>  "+
			"<option value='2053'>haikou海口</option>  ";
	$("#cityId").html(optionStr);
}


//初始化下拉列表
$(function(){
	//初始化一级分类
	 $("#firstid").val(0); 
	//初始化二级分类
	//changeCategory($('#firstid').val(), null);
	//添加下拉列表发布者
	addPublisher($(userRole).val());
	//初始化城市下拉列表
	addCity();
});

//排除已经选中的用户
function operateOption(object){
	var obj=$(object);
	
 	var allObj=$("select[name='answerUserId']");
	allObj.each(function() {
		if(obj.attr('id')!=$(this).attr('id') && obj.val()!=0){
				$(this).find("option[value='"+obj.val()+"']").remove();
				$("#chengePublisherId").val(obj.val());
				$("#chengePublisherName").val(obj.find("option[value='"+obj.val()+"']").text());
		}
	});
	if(obj.val()==0){
		$("#chengePublisherId").val("");
		$("#chengePublisherName").val("");
	}
	var chengeId=$("#chengePublisherId").val();
	var chengeName=$("#chengePublisherName").val();
		if(chengeId!="" && chengeName!=""){
			//addOption(obj);
			//$("#chengePublisherId").val("");
			//$("#chengePublisherName").val("");
		}
}
//添加 html option
function addOption(obj){
 	var allObj=$("select[name='answerUserId']");
	var chengeId=$("#chengePublisherId").val();
	var chengeName=$("#chengePublisherName").val();
	allObj.each(function() {
		if(obj.attr('id')!=$(this).attr('id')){
			if(chengeId!=""&&chengeName!=""){
				$(this).append("<option value='"+chengeId+"'>"+chengeName+"</option>");
			}
		}
	});
}

	// ajax增加用户
	function userAdd() {
		String.prototype.Trim = function() {
			return this.replace(/(^\s*)|(\s*$)/g, "");
		};
		var patrn = /^[0-9]{0,20}$/;
		var userId = $("#userIdAdd").val().Trim();
		var flag = 0;
		if (userId == null || userId == "") {
			userId=0;
		} else if (!patrn.exec(userId)) {
			alert("ID必须为数字！", 2000);
			flag++;
		}
		
		if (flag == 0) {
			var resultFlag = true;
			$.ajax({
				type : "GET",
				async : false,
				url : "/publish/add",
				dataType : "json",
				data : {
					"userId" : userId
				},
				success : function(data) {
					resultFlag = data.result.result;
					if (resultFlag) {
						clearAddDiv();
						$.unblockUI();
						window.location.href="/publish/userlist"; 
					}else{
						alert(data.result.message);
					}
				}
			});
			//});
		}
	}
//问答分类
function changeCategory(firstId, secoundId) {
	var options = "<option value='0' selected='selected'>二级分类</option>";
	if (firstId==0) {
		$("#secoundid").html(options);
	} else {
		$.ajax({
			type : "get",
			async : true,
			url : "/wenda/category/change/" + firstId,
			dataType : "json",
			success : function(data) {
				var obj = eval(data.categoryList);
				for ( var i = 0; i < obj.length; i++) {
					if(obj[i].id==secoundId){
						options += "<option value='" + obj[i].id + "' selected>"
						+ obj[i].name + "</option>";
					}else{
						options += "<option value='" + obj[i].id + "' >"
						+ obj[i].name + "</option>";
					}	
				}
				$("#secoundid").html(options);
			}
		});
	}
}
</script>
</html>