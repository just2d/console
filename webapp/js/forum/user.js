$(function(){
	$('#addUserDiv').dialog({
		show : "slide",
		bgiframe : true,
		autoOpen : false,
		height : 320,
		width : 550,
		title : "添加版主",
		modal : true,
		resizable : false
	});
	$('#editUserDiv').dialog({
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


function addUser() {
	$("#addUser").val('');
	$("#addUserDiv").dialog("option", "position", "center");
	$('#addUserDiv').dialog('open');
}





function confirmDelUser(id) {
	$.ajax({
		type : "post",
		async : false,
		url : "/forumuser/delete?id="+id,
		dataType : "json",
		success : function(data) {
			
			if(data.result){
				alert(data.result);
				return;
			}

		}
	});
	$('#searchUserForm').submit();
}
