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
	$('#editpowerDiv').dialog({
		show : "slide",
		bgiframe : true,
		autoOpen : false,
		height : 320,
		width : 550,
		title : "权限设置",
		modal : true,
		resizable : false
	});
});



function addRole() {
	$("#addRoleDiv").dialog("option", "position", "center");
	$('#addRoleDiv').dialog('open');
}
function editRole(id) {
	$.ajax({
		type : "post",
		async : false,
		url : "/forum/role/edit/"+id,
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
//权限设置
function power(roleId){
	$('input[name=ids]').attr('checked', false);
	$.ajax({
		type : "get",
		async : false,
		url : "/forum/role/rolefunction/?roleId="+roleId,
		dataType : "json",
		success : function(data) {
			 var  ids=data.roleFunctions;
				 for(var i=0;i<ids.length;i++){
					 $("#function"+ids[i]).attr("checked",'');
			 }

		}
	});

	$("#roleId").val(roleId);
	$("#editpowerDiv").dialog("option", "position", "center");
	$('#editpowerDiv').dialog('open');
};

function checkAddRoleInfo(){
	var roleName = $('#name').val();
	var code = $("#code").val();
	if(roleName == "") {
		$("#rolenameSp").show();
	} else {
		$("#rolenameSp").hide();
	}
	
	
	if(code=="") {
		 $("#codeSp").show();
	} else {
		$("#codeSp").hide();
	}
	if(!checkRoleName(roleName,0)||!checkRoleCode(code,0)){
		return ;
	}else{
		$('#addRoleForm').submit();
	}
	
}
function  checkRoleName(roleName,id){
	var  result=true;
	$.ajax({
		type : "post",
		async : false,
		url : "/forum/role/checkName",
		dataType : "json",
		data : {"name" : roleName,"id":id},
		success : function(data) {
			if(data.error){
				alert(data.error);
				result=false;
			}
		}
	});
	return  result;
	
}
function  checkRoleCode(code,id){
	var  result=true;
	$.ajax({
		type : "post",
		async : false,
		url : "/forum/role/checkCode",
		dataType : "json",
		data : {"code" : code,"id":id},
		success : function(data) {
			if(data.error){
				alert(data.error);
				result=false;
			}
		}
	});
	return  result;
	
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
	if(!checkRoleName(roleName,id)||!checkRoleCode(code,id)){
		return ;
	}else{
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
		window.location.href="/forum/role/delete?id=" + id;
	}
}


function batchupdate(){
	var roleid=$("#roleId").val();
	var tempIds = document.getElementsByName("ids");

	var checkNum = 0;
	var checkedObj = $('input:checkbox[name="ids"]:checked');
	var  ids='';
	checkedObj.each(function() {  ids+= this.value+','; }); 
	if(ids.length==0){
		alert("需要提交的权限不能为空");
		return;
	}
	if(confirm("所选权限是否保存?")){
		$.ajax({
			type : "get",
			async : false,
			url : "/forum/role/power",
			dataType : "json",
			data : {
				"roleid":roleid,
				"ids" : ids },
			success : function(data) {
				if(data.error){
					alert(data.error);
					return;
				}
				if(data.info=="success"){
					alert("所选权限提交成功");
				}else{
					alert("操作失败!");
					return ;
				};
				var currentNo = document.getElementById("currentNo");
				var pageNo = document.getElementById("page.pageNo");
					pageNo.value = currentNo.value;
					$("#rolePowerForm").submit();
			}
		});
	}
}
