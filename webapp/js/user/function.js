$(function(){
	$('#addFunctionDiv').dialog({
		show : "slide",
		bgiframe : true,
		autoOpen : false,
		height : 420,
		width : 650,
		title : "添加功能菜单",
		modal : true,
		resizable : false
	});
	$('#editFunctionDiv').dialog({
		show : "slide",
		bgiframe : true,
		autoOpen : false,
		height : 420,
		width : 650,
		title : "编辑功能菜单",
		modal : true,
		resizable : false
	});
});

function searchFunction() {
	if($('#paraFunctionName').val() == '') {
		return;
	} else {
		$('#searchFunctionForm').submit();
	}
}

function addFunction() {
	$("#addFunctionDiv").dialog("option", "position", "center");
	$('#addFunctionDiv').dialog('open');
}
function edit(id) {
	$.ajax({
		type : "post",
		async : false,
		url : "/function/edit/"+id,
		dataType : "json",
		success : function(data) {
			
			if(data.error){
				alert(data.error);
				return;
			}
			$("#editFunctionId").val(data.functions.id);
			$("#editName").val(data.functions.name);
			$("#editurl").val(data.functions.url);
			$("#editfuncs").val(data.functions.funcs);
			$("#editdescription").val(data.functions.description);
			

		}
	});
	$("#editFunctionDiv").dialog("option", "position", "center");
	$('#editFunctionDiv').dialog('open');
}

function checkAddFunctionInfo(){
	var isCommit = true;
	var functionName = $('#name').val();
	var url = $("#url").val();
	if(functionName == "") {
		$("#functionnameSp").show();
		isCommit = false;
	} else {
		$("#functionnameSp").hide();
	}
	
	
	if(url=="") {
		 $("#urlSp").show();
		 isCommit = false;
	} else {
		$("#urlSp").hide();
	}
	
	/*$.ajax({
		type : "post",
		async : false,
		url : "/function/checkName",
		dataType : "json",
		data : {"name" : functionName},
		success : function(data) {
			if(data.error){
				alert(data.error);
				 isCommit = false;
				return;
			}
		}
	});*/
	
	if(isCommit) {
		$('#addFunctionForm').submit();
	}
	
	
	
	
}
function checkEditFunctionInfo(){
	var isCommit = true;
	var id = $("#editFunctionId").val();
	var functionName = $('#editName').val();
	var url = $("#editurl").val();
	if(functionName == "") {
		$("#editfunctionnameSp").show();
		isCommit = false;
	} else {
		$("#editfunctionnameSp").hide();
	}
	
	
	if(url=="") {
		$("#editurlSp").show();
		isCommit = false;
	} else {
		$("#editurlSp").hide();
	}
	
	/*$.ajax({
		type : "post",
		async : false,
		url : "/function/checkName",
		dataType : "json",
		data : {"id":id,"name" : functionName},
		success : function(data) {
			if(data.error){
				alert(data.error);
				isCommit = false;
				return;
			}
		}
	});*/
	if(isCommit) {
		$('#editFunctionForm').submit();
	}
	
	
	
	
}


function confirmDelFunction(id,parentId) {
	if(confirm("确定要删除该用户吗?")) {
		window.location.href="/function/delete?id=" + id+"&parentId="+parentId;
	}
}
