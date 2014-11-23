//验证小区名是否重复
function validateName(id,name){
	$.getJSON(contextPath + "/estate/validateName?estateId="+id+"&estateName="+name, null, function(data){
	});
}