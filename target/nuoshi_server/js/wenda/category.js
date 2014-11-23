/**
 * 对话框设置
 */
$(function(){
	$('#addCategoryDiv').dialog({
		show : "slide",
		bgiframe : true,
		autoOpen : false,
		height : 300,
		width : 650,
		title : "添加分类",
		modal : true,
		resizable : false
	});
});

/**
 * 根据一级分类查询
 * @param id
 */
function searchCategoryById(id) {
	if (id != null) {
		window.location.href = "/wenda/category/list/" + id;
	}
}

/**
 * 表单提交查询
 */
function query(){
	$("#categoryForm").submit();
}

/**
 * 返回上一级
 */
function backoff() {
	window.history.go(-1);
}

/**
 * 分级联动
 * 
 * @param firstCategory
 */
function changeCategory(firstId, secoundId) {
	var options = "<option value='0'>全部</option>";
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

/**
* 增加分类
*/
function add(){
	clearparam();
	$("#addCategoryDiv").dialog("option", "position", "center");
	$('#addCategoryDiv').dialog('open');
}

function clearparam(){
	$("#id").val("");
	$("#name").val("");
	$("#oldName").val("");
	$("#pid").val(0);
	$("#keyword").val("");
	$("#title").val("");
	$("#describle").val("");
	$("#pageword").val("");
}

/**
 * 取消
 */
function cancle(){
	$('#addCategoryDiv').dialog('close');
}

/**
 * 编辑
 */
function edit(){
	 $("#keyword").removeAttr("readonly");
	 $("#keyword").attr({"style":"width:350px;color:black"});
}

/**
 * 编辑属性
 */
function addreadonly(){
	$("#keyword").attr({"readonly":"readonly"});
	$("#keyword").attr({"style":"width:350px;color:gray"}); 
}

/**
 * 添加分类
 */
function savecategory(){
	var isCommit = true;
	var name = $("#name").val();
	var title = $("#title").val();
	var desc = $("#describle").val();
	var pageword = $("#pageword").val();
	//参数判空
	if(name == ""){
		$("#nameSp").show();
		isCommit = false;
	}else{
		$("#nameSp").hide();
	}
	if(title == ""){
		$("#titleSp").show();
		isCommit = false;
	}else{
		$("#titleSp").hide();
	}
	if(desc == ""){
		$("#descSp").show();
		isCommit = false;
	}else{
		$("#descSp").hide();
	}
	if(pageword == ""){
		$("#pagewordSp").show();
		isCommit = false;
	}else{
		$("#pagewordSp").hide();
	}
	if(isCommit){
		$("#addCategoryForm").submit();
		cancle();		
	}
}

/**
 * 关闭/开启
 * @param id
 * @param status
 * @returns
 */
function closeup(id, pid, status){
	window.location.href = "/wenda/category/close/"+id+"/"+pid+"/"+status;
	
}

/**
 * 修改
 * @param id
 * @returns
 */
function modify(id){
	$.ajax({
		type : "get",
		async : true,
		url : "/wenda/category/modify/" + id,
		dataType : "json",
		success : function(data) {
			if(data.category!=null){
				var category = data.category;
				$("#id").val(category.id);
				$("#name").val(category.name);
				$("#oldName").val(category.name);
				$("#pid").val(category.pid);
				$("#keyword").val(category.keyword);
				$("#title").val(category.title);
				$("#describle").val(category.describle);
				$("#pageword").val(category.pageword);
				$("#addCategoryDiv").dialog("option", "position", "center");
				$('#addCategoryDiv').dialog('open');
			}
		}
	});
	
}

/**
 * 更新缓存
 */
function updateCache(){
	var tempIds = document.getElementsByName("ids");
	var locations = "";
	var ids = "";
	for(var i=0;i<tempIds.length;i++){
		ids =ids+tempIds[i].id+",";
		locations=locations+document.getElementById(tempIds[i].id).value+",";	
	}
	$.ajax({
		url : "/wenda/category/updateCache",
		type : "post",
		data:{"ids":ids,"locations":locations},
		success : function(data) {
			if(data.result){
				alert("更新成功。");
			}else{
				alert("更新失败！");
			}
		}
	});
}