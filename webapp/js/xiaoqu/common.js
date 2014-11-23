
$(function(){
	$(".nostyle").toggle(function () {
   			$(this).addClass("backSel");
   			//获得它的兄弟节点checkbox(隐藏了)
   			$(this).parent().find('input[type=checkbox]').attr("checked",true);
		},function () {
	  		$(this).removeClass("backSel");
	  		$(this).parent().find('input[type=checkbox]').attr("checked",false);
    	}
	);
	
	$(".jin_nostyle").toggle(
		function () {
   			$(this).addClass("backSel");
   			//获得它的兄弟节点checkbox(隐藏了)
   			$(this).parent().find('input[type=checkbox]').attr("checked",true);
		},
		function () {
	  		$(this).removeClass("backSel");
	  		$(this).parent().find('input[type=checkbox]').attr("checked",false);
    	}
	);
});


//获得所有选中的复选框的id
function getSelIds(cls) {
	//获得所有选中的checkBox;
	var selVal = "";
	$("." + cls).each(function() {
		if (this.checked) {
			selVal += $(this).val() + ",";
		}
	});
	return selVal;
}
//获得所有没被选中的复选框
function getUnSelIds(cls) {
	//获得所有选中的checkBox;
	var selVal = "";
	
	$("." + cls).each(function() {
		if (!this.checked) {
			var thisVal =  $(this).val();
			if(thisVal !=""){
				selVal += $(this).val() + ",";	
			}	
		}
	});
	return selVal;
}


function setCityInfo(ch) {
	if (ch != "") {
		$.getJSON(contextPath + "/local/cityList/" + ch, null, function(json) {
			var options = "<option value=''>-请选择-</option>";
			if (json != null && json.length > 0) {
				for ( var i = 0; i < json.length; i++) {
					options += "<option value='" + json[i].id + "'>"
							+ json[i].name + "</option>";
				}
			}
			$("#cityLocale").remove("option");
			$("#cityLocale").html(options);
		});
	}
}

function setDistInfo(cityId) {
	if (cityId != "") {
		$.getJSON(contextPath + "/local/distList/" + cityId, null, function(
				json) {
			var options = "<option value=''>-请选择-</option>";
			if (json != null && json.length > 0) {
				for ( var i = 0; i < json.length; i++) {
					options += "<option value='" + json[i].id + "'>"
							+ json[i].name + "</option>";
				}
			}
			$("#distLocale").remove("option");
			$("#distLocale").html(options);
		});
	}
}

function setBlockInfo(distId) {
	if (distId != "") {
		$.getJSON(contextPath + "/local/distList/" + distId, null, function(
				json) {
			var options = "<option value=''>-请选择-</option>";
			if (json != null && json.length > 0) {
				for ( var i = 0; i < json.length; i++) {
					options += "<option value='" + json[i].id + "'>"
							+ json[i].name + "</option>";
				}
			}
			$("#blockLocale").remove("option");
			$("#blockLocale").html(options);
		});
	}
}

function search() {
	var pageNo = document.getElementById("page.pageNo");
	pageNo.value = 0;
	$("#estateListForm").submit();
}