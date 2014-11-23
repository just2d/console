$(function(){
	$('#addUserDiv').dialog({
		show : "slide",
		bgiframe : true,
		autoOpen : false,
		height : 320,
		width : 550,
		title : "添加用户",
		modal : true,
		resizable : false
	});
});

function searchUser() {
	if($('#paraUserName').val() == '') {
		return;
	} else {
		$('#searchUserForm').submit();
	}
}

function addUser() {
	$("#addUserDiv").dialog("option", "position", "center");
	$('#addUserDiv').dialog('open');
}

function checkAddUserInfo(){
	var isCommit = true;
	var userName = $('#userName').val();
	var password = $("#password").val();
	var rePwd = $("#repassword").val();
	var mobile = $("#mobile").val();
	var email = $("#email").val();
	var chnName = $("#chnName").val();
	var mreg=/^(13+\d{9})|(15+\d{9})|(18+\d{9})$/;
	var ereg=/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if(userName == "") {
		$("#usernameSp").show();
		isCommit = false;
	} else {
		$("#usernameSp").hide();
	}
	if(password==""){
		$("#psSp").show();
		isCommit = false;
	}else {
		$("#psSp").hide();
	}
	if(password!=rePwd){
		$("#repsSp").show();
		isCommit = false;
	} else {
		$("#repsSp").hide();
	}
	if(!mreg.test(mobile)){
		$("#mSp").show();
		isCommit = false;
	}else {
		$("#mSp").hide();
	}
	if(!ereg.test(email)){
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
	if(chnName=="") {
		 $("#cnSp").show();
		 isCommit = false;
	} else {
		$("#cnSp").hide();
	}
	if(isCommit) {
		$('#addUserInfo').val(chnName + '_' + password + '_' + email + '_' + mobile + '_' + $('#userSex').val() + '_' + userName);
		$('#addUserForm').submit();
	}
}

function checkUserInfo(){
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

function confirmDelUser(id) {
	if(confirm("确定要删除该用户吗?")) {
		window.location.href="/user/delUser?id=" + id;
	}
}
