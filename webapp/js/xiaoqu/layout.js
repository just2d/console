$(function(){
	$('#uploadlp').dialog({
		show : "slide",
		bgiframe : true,
		autoOpen : false,
		height : 320,
		width : 550,
		title : "上传小区户型图",
		modal : true,
		resizable : false
	});
});
/**
 * 移动到精选库
 * @param {Object} cls
 */
function mv(cls, selNum) {
	var selIds = "";
	var unselIds = "";
	selIds = getSelIds(cls);
	//获得选中的复选框个数
	var num = $("input[class=" + cls + "]:checked").length;
	if (num > 0) {
		if (selNum < num) {
			alert("目前最多可选择" + selNum + "张图片进入精选库");
			return;
		} else {
			unselIds = getUnSelIds(cls);
			window.location.href = contextPath
					+ "/estate/layout/moveToUsing?toUsingIds=" + selIds
					+ "&unUsingIds=" + unselIds + url;
		}
	}
}
function del(cls) {
	var num = $("input[class=" + cls + "]:checked").length;
	if (num > 0) {
		var selIds = getSelIds(cls);
		window.location.href = contextPath
				+ "/estate/layout/delBackup?unUsingIds=" + selIds + url;
	}
}

function delToBackup(cls, category) {
	var num = $("input[class=" + cls + "]:checked").length;
	if (num > 0) {
		var selIds = getSelIds(cls);
		//category:1户型图,3:小区图.
		window.location.href = contextPath
				+ "/estate/layout/mvToBackup?unUsingIds=" + selIds + url
				+ "&category=" + category;
	}
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

