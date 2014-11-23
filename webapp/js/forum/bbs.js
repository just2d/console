function  changeCityForum(cityId,selected,nothavaid,isId){
	var role=$("#visibleRole").val();
	changeCityGetForum(cityId,role,selected,nothavaid,isId);
}
function changeRoleGetForum(role,selected,nothavaid,isId){
	var cityId=$("#cityId").val();
	changeCityGetForum(cityId,role,selected,nothavaid,isId);
}

function changeRoleGetForum2(role,selected,nothavaid,isId){
	var cityId=$("#cityId").val();
	changeCityGetForum2(cityId,role,selected,nothavaid,isId);
}

function changeCityGetForum2(cityId,role,selected,nothavaid,isId) {
	if(typeof(role) =="undefined" ||(typeof(role) == "string" && role == "")){
		role = 1;
	}
	$.getJSON("/bbs/manager/addforumlist?cityId="+cityId+"&forumType=0&visibleRole="+role, null, function(json){
	  	var options="<option value='0'>-请选择-</option>";
	  	if(json!=null&&json.length>0){
	  		for(var i=0;i<json.length;i++){
	  			if(json[i].id!=nothavaid){
	  				 if(!isId){
	  					 options+="<option value='"+json[i].id+"-"+json[i].forumType+"'   >"+json[i].name+"</option>";	
	  				 }else{
	  					 options+="<option value='"+json[i].id+"'   >"+json[i].name+"("+json[i].visibleRoleName+")"+"</option>";	

	  				 }
	  				 }
		  	}
	  	}
	  	$("#forumList").remove("option");
	  	$("#forumList").html(options);
	  	$("#forumList").val(selected);
		});
}

function changeCityGetForum(cityId,role,selected,nothavaid,isId) {
		changeCityGetForum2(cityId,role,selected,nothavaid,isId);
}

function setUserId(){
	var userName = $("#name").val();
	if(typeof(userName)!="undefined" && userName !="" && userName!= null){
		$.ajax({
			type : "post",
			async : false,
			url : "/forumuser/checkUserIsExist",
			dataType : "json",
			data : {"userName" : userName},
			success : function(data) {
				$("#userRole").val(data.role);
				$("#userid").val(data.id);
			}
		});
	}else{
		$("#nameSp").show();
		$("#userid").val(0);
		return;
	}
	
}

function checkForumUserExistByUserName(userName,forumId,id){
	var result = false;
	$.ajax({
		type : "post",
		async : false,
		url : "/forumuser/checkName",
		dataType : "json",
		data : {"name" : userName,"forumid":forumId,"id":id},
		success : function(data) {
			if(typeof(data.msg) != "undefined"&& data.msg){
				alert('该角色的用户已存在!!请勿重复添加');
			}else{
				result = true;
			}
		}
	});
	return result;
}


function checkAddUserInfo(userId){
	var userName = $('#name').val();
	var forumId = $("#forumList").val();
	var userId = $("#userid").val();
	if(userName == "") {
		$("#nameSp").show();
		return;
	} else {
		$("#nameSp").hide();
	}
	if(checkForumUserExistByUserName(userName,forumId,userId)){
		//该板块还没有为这个用户分配版主权限,看这个用户是否存在
		if(userId == 0){
			alert("该用户不存在!");
			return;
		}else{
			$("#addUserForm").submit();
		}
	}else{
		return;
	}
}
function  changeForum(){
	$("#forumname").val($("#forumList").find("option:selected").text());
}

function checkInfo(doType){
	var name = $('#name').val();
	var descript = $("#descript").val();
	var title = $("#title").val();
	var keyword = $("#keyword").val();
	var displayOrder=$("#displayOrder").val();
	var forumId=0;
	if("update"==doType){
		forumId = $("#forumId").val();
	}
	if(name == "") {
		$("#nameSp").show();
		return;
	} else {
		$("#nameSp").hide();
	}
	if(descript==""){
		$("#descriptSp").show();
		return;
	}else {
		$("#descriptSp").hide();
	}
	if(title == "") {
		$("#titleSp").show();
		return;
	} else {
		$("#titleSp").hide();
	}
	if(keyword==""){
		$("#keywordSp").show();
		return;
	}else {
		$("#keywordSp").hide();
	}
	if(displayOrder == "") {
		$("#displayOrderSp").show();
		return;
	} else {
		$("#displayOrderSp").hide();
	}
	var reg = /^[0-9]*[1-9][0-9]*$/;
	if(!reg.test(displayOrder)){
		$("#displayOrderSp").html("只能输入整数");
		$("#displayOrderSp").show();
		return;
	}
	$("#addforum").submit();
	return;
	
}
function  checkForum(name,cityId,visibleRole,pid,excludeId){
		var  result=true;
		$.ajax({
			type : "post",
			async : false,
			url : "/bbs/manager/searchforum",
			dataType : "json",
			data : {"name" : name,"cityId":cityId,"visibleRole":visibleRole,"parentId":pid,"excludeId":excludeId,"callback":"json"},
			success : function(data) {
				if(data.totalNum>0){
					alert('该板块已存在!');
					result=false;
				}
			}
		});
		return  result;
}
function setCityInfo(ch,addSelectId){
	if(ch != ""){
		$.ajax({
			type : "post",
			async : false,
			url : "/local/cityList/"+ch,
			dataType : "json",
			success : function(json) {
				var options="<option value=''>-请选择-</option>";
			  	if(json!=null&&json.length>0){
			  		for(var i=0;i<json.length;i++){
				  		options+="<option value='"+json[i].id+"'>"+json[i].name+"</option>";
				  	}
			  	}
			  	$("#"+addSelectId).remove("option");
			  	$("#"+addSelectId).html(options);
			}
		});
		
	}else{
		var options="<option value=''>-请选择-</option>";
		$("#"+addSelectId).remove("option");
		$("#"+addSelectId).html(options);
	}
}


function edit(id) {
	window.location.href="/forumuser/selectById?id=" + id;
}