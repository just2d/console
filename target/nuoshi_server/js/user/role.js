$(function(){
	$('#addRoleDiv').dialog({
		show : "slide",
		bgiframe : true,
		autoOpen : false,
		height : 320,
		width : 550,
		title : "添加角色",
		modal : true,
		resizable : false
	});
	$('#editRoleDiv').dialog({
		show : "slide",
		bgiframe : true,
		autoOpen : false,
		height : 320,
		width : 550,
		title : "编辑角色",
		modal : true,
		resizable : false
	});
});

function searchRole() {
	if($('#paraRoleName').val() == '') {
		return;
	} else {
		$('#searchRoleForm').submit();
	}
}

function addRole() {
	$("#addRoleDiv").dialog("option", "position", "center");
	$('#addRoleDiv').dialog('open');
}
function edit(id) {
	$.ajax({
		type : "post",
		async : false,
		url : "/role/edit/"+id,
		dataType : "json",
		success : function(data) {
			
			if(data.error){
				alert(data.error);
				return;
			}
			$("#editRoleId").val(data.role.id);
			$("#editName").val(data.role.name);
			$("#editCode").val(data.role.code);
			if(data.role.status==1){
				$("#editStatus1").attr("checked","checked");
			}else{
				$("#editStatus0").attr("checked","checked");
			}

		}
	});
	$("#editRoleDiv").dialog("option", "position", "center");
	$('#editRoleDiv').dialog('open');
}

function checkAddRoleInfo(){
	var isCommit = true;
	var roleName = $('#name').val();
	var code = $("#code").val();
	if(roleName == "") {
		$("#rolenameSp").show();
		isCommit = false;
	} else {
		$("#rolenameSp").hide();
	}
	
	
	if(code=="") {
		 $("#codeSp").show();
		 isCommit = false;
	} else {
		$("#codeSp").hide();
	}
	
	$.ajax({
		type : "post",
		async : false,
		url : "/role/checkName",
		dataType : "json",
		data : {"name" : roleName},
		success : function(data) {
			if(data.error){
				alert(data.error);
				 isCommit = false;
				return;
			}
		}
	});
	$.ajax({
		type : "post",
		async : false,
		url : "/role/checkCode",
		dataType : "json",
		data : {"code" : code},
		success : function(data) {
			if(data.error){
				alert(data.error);
				 isCommit = false;
				return;
			}
		}
	});
	if(isCommit) {
		$('#addRoleForm').submit();
	}
	
	
	
	
}
function checkEditRoleInfo(){
	var isCommit = true;
	var id = $("#editRoleId").val();
	var roleName = $('#editName').val();
	var code = $("#editCode").val();
	if(roleName == "") {
		$("#editrolenameSp").show();
		isCommit = false;
	} else {
		$("#editrolenameSp").hide();
	}
	
	
	if(code=="") {
		$("#editcodeSp").show();
		isCommit = false;
	} else {
		$("#editcodeSp").hide();
	}
	
	$.ajax({
		type : "post",
		async : false,
		url : "/role/checkName",
		dataType : "json",
		data : {"id":id,"name" : roleName},
		success : function(data) {
			if(data.error){
				alert(data.error);
				isCommit = false;
				return;
			}
		}
	});
	$.ajax({
		type : "post",
		async : false,
		url : "/role/checkCode",
		dataType : "json",
		data : {"id":id,"code" : code},
		success : function(data) {
			if(data.error){
				alert(data.error);
				 isCommit = false;
				return;
			}
		}
	});
	
	if(isCommit) {
		$('#editRoleForm').submit();
	}
	
	
	
	
}

function checkRoleInfo(){
	var isCommit = true;
	var password = $("#password").val();
	var rePwd = $("#repassword").val();
	var mobile = $("#mobile").val();
	var email = $("#email").val();
	var chnName = $("#chnName").val();
	var mreg=/^(13+\d{9})|(15+\d{9})|(18+\d{9})$/;
	var ereg=/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if(password!=rePwd){
		$("#repsSp").show();
		isCommit = false;
	} else {
		$("#repsSp").hide();
	}
	if(!mreg.test(mobile)){
		$("#mSp").show();
		isCommit = false;
	} else {
		$("#mSp").hide();
	}
	if(!ereg.test(email)) {
		$("#emSp").show();
		isCommit = false;
	} else {
		$("#emSp").hide();
	}
	if(!ereg.test(email)){
		$("#emSp").show();
		isCommit = false;
	} else {
		$("#emSp").hide();
	}
	if(chnName==""){
		$("#cnSp").show();
		isCommit = false;
	} else {
		$("#cnSp").hide();
	}
	return isCommit;
}

function confirmDelRole(id) {
	if(confirm("确定要删除该角色吗?")) {
		window.location.href="/role/delete?id=" + id;
	}
}
