$(function(){
	$("#divAdd").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 400,width : 400,title : "新增套餐",modal : true,resizable : false,buttons : {"增加" : addNewPackage}});
	$("#divEdit").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 400,width : 400,title : "编辑套餐",modal : true,resizable : false,buttons : {"确定" : savePackage,"取消":closeEdit}});
});

function addPackage(){
	$("#houseAmount").val("");
	$("#labelAmount").val("");
	$("#vipPicNum").val("");
	$("#refreshHouseTimes").val("");
	$("#price").val("");
	$("#defaultValue").attr("checked", false);
	$("#packageName").val("");
	$("*").stop();
	$("#divAdd").dialog("close");
	$("#divAdd").dialog("option", "position", "center");
	$("#divAdd").dialog("open");
}

function addNewPackage() {
	String.prototype.Trim = function() {return this.replace(/(^\s*)|(\s*$)/g,"");};
	var cityId = $("#packageCityId").val().Trim();
	var houseAmount = $("#houseAmount").val().Trim();
	var labelAmount = $("#labelAmount").val().Trim();
	var refreshHouseTimes = $("#refreshHouseTimes").val().Trim();
	var vipPicNum = $("#vipPicNum").val().Trim();
	var price = $("#price").val().Trim();
	var effectiveMonth = $("#effectiveMonth").val().Trim();
	var effectiveDays = $("#effectiveDays").val().Trim();
	var orderIndex = $("#orderIndex").val().Trim();
	var defaultValue =$("#displayType").val().Trim();
	var defaultFree = 0;
	if($("#defaultFree").attr("checked")) {
		defaultFree = 1;
	}
	var packageName = $("#packageName").val().Trim();
	var reg =/^\d+$/;
	
	var flag = 0;
	if (!reg.test(houseAmount)) {
		blockAlert("房源数量只能是正整数", 2000);
		flag++;
	} else if (!reg.test(labelAmount)) {
		blockAlert("标签数量只能是正整数", 2000);
		flag++;
	} else if (!reg.test(vipPicNum)) {
		blockAlert("VIP专区数量只能是正整数", 2000);
		flag++;
	}else if(!reg.test(refreshHouseTimes)){
		blockAlert("预约房源数只能是数字", 2000);
		flag++;
		
	}else if(!reg.test(price)) {
		blockAlert("套餐价格只能是数字", 2000);
		flag++;
	} else if((defaultValue == 1 || defaultValue == 2) && packageName == "") {
		blockAlert("请输入套餐名称", 2000);
		flag++;
	} else if(!reg.test(effectiveMonth)) {
		blockAlert("套餐使用期限只能是数字", 2000);
		flag++;
	} else if(!reg.test(effectiveDays)) {
		blockAlert("套餐使用天数只能是数字", 2000);
		flag++;
	} else if(!reg.test(orderIndex)) {
		blockAlert("排序序列只能是数字", 2000);
		flag++;
	}
	if(effectiveDays>30){
		blockAlert("天数大于30 请使用月", 2000);
		flag++;
	}
	
	if (flag == 0) {
		$.ajax({
			type : "GET",
			async : false,
			url : "/package/manage/add",
			dataType : "json",
			data : {"cityId" : cityId, "defaultFree": defaultFree, "orderIndex" :orderIndex, "effectiveMonth": effectiveMonth,"effectiveDays":effectiveDays, "houseAmount" : houseAmount, "labelAmount" : labelAmount,"vipPicNum":vipPicNum,"refreshHouseTimes":refreshHouseTimes, "price" : price, "defaultValue" : defaultValue, "packageName" : packageName},
			success : function(data) {
				$("#houseAmount").val("");
				$("#labelAmount").val("");
				$("#refreshHouseTimes").val("");
				$("#vipPicNum").val("");
				$("#price").val("");
				$("#defaultValue").attr("checked", false);
				$("#defaultValue2").attr("checked", false);
				$("#defaultValue3").attr("checked", false);
				$("#defaultFree").attr("checked", false);
				$("#packageName").val("");
				$("#effectiveMonth").val("");
				$("#effectiveDays").val("");
				$("#orderIndex").val("");
			}
		});
		$("#divAdd").dialog("close");
		changeCity(cityId);
	}
}

function savePackage() {
	String.prototype.Trim = function() {return this.replace(/(^\s*)|(\s*$)/g,"");};
	var id = $("#editId").val().Trim();
	var cityId = $("#editPackageCityId").val().Trim();
	var houseAmount = $("#editHouseAmount").val().Trim();
	var refreshHouseTimes = $("#editRefreshHouseTimes").val().Trim();
	var labelAmount = $("#editLabelAmount").val().Trim();
	var vipPicNum = $("#editVipPicNum").val().Trim();
	var price = $("#editPrice").val().Trim();
	var effectiveMonth = $("#editEffectiveMonth").val().Trim();
	var effectiveDays = $("#editEffectiveDays").val().Trim();
	var orderIndex = $("#editOrderIndex").val().Trim();
	var defaultValue = $("#displayType").val().Trim();
	
	var defaultFree = 0;
	if($("#editDefaultFree").attr("checked")) {
		defaultFree = 1;
	}
	var packageName = $("#editPackageName").val().Trim();
	var reg =/^\d+$/;
	
	var flag = 0;
	if (!reg.test(houseAmount)) {
		blockAlert("房源数量只能是正整数", 2000);
		flag++;
	} else if (!reg.test(labelAmount)) {
		blockAlert("标签数量只能是正整数", 2000);
		flag++;
	}else if (!reg.test(refreshHouseTimes)) {
		blockAlert("预约房源数只能是正整数", 2000);
		flag++;
	}else if (!reg.test(vipPicNum)) {
		blockAlert("VIP专区数量只能是正整数", 2000);
		flag++;
	} else if(!reg.test(price)) {
		blockAlert("套餐价格只能是数字", 2000);
		flag++;
	} else if(defaultValue == 1 && packageName == "") {
		blockAlert("请输入套餐名称", 2000);
		flag++;
	} else if(!reg.test(effectiveMonth)) {
		blockAlert("套餐使用期限只能是数字", 2000);
		flag++;
	} else if(!reg.test(effectiveDays)) {
		blockAlert("套餐使用天数只能是数字", 2000);
		flag++;
	} else if(!reg.test(orderIndex)) {
		blockAlert("排序序列只能是数字", 2000);
		flag++;
	}
	
	if (flag == 0) {
		$.ajax({
			type : "GET",
			async : false,
			url : "/package/manage/save",
			dataType : "json",
			data : {"id" : id, "defaultFree": defaultFree, "effectiveMonth": effectiveMonth,"effectiveDays":effectiveDays, "houseAmount" : houseAmount, "labelAmount" : labelAmount,"refreshHouseTimes":refreshHouseTimes,"vipPicNum":vipPicNum ,"price" : price, "defaultValue" : defaultValue, "packageName" : packageName, "orderIndex": orderIndex},
			success : function(data) {
				$("#editHouseAmount").val("");
				$("#editRefreshHouseTimes").val("");
				$("#editLabelAmount").val("");
				$("#editVipPicNum").val("");
				$("#editPrice").val("");
				$("#editDefaultValue").attr("checked", false);
				$("#editDefaultValue2").attr("checked", false);
				$("#editDefaultValue3").attr("checked", false);
				$("#editPackageName").val("");
				$("#editDefaultFree").attr("checked", false);
				$("#editEffectiveMonth").val("");
				$("#editEffectiveDays").val("");
				$("#editOrderIndex").val("");
			}
		});
		$("#divEdit").dialog("close");
		changeCity(cityId);
	}
}

function changeCity(value) {
	$("#packageForm").attr("action", "/package/manage/list/" + value);
	$("#packageForm").submit();
}

function enablePackageName(defaultValueId, defaultValue){
	if("defaultValue" ==  defaultValueId) {
		if($("#defaultValue").attr("checked")) {
			$("#displayType").val(defaultValue);
			$("#defaultValue2").attr("checked", false);
			$("#defaultValue3").attr("checked", false);
			$("#packageName").attr("disabled", false);
		} else {
			$("#packageName").attr("disabled", true);
		}
	} else if("defaultValue2" == defaultValueId) {
		if($("#defaultValue2").attr("checked")) {
			$("#displayType").val(defaultValue);
			$("#defaultValue").attr("checked", false);
			$("#defaultValue3").attr("checked", false);
			$("#packageName").attr("disabled", false);
		} else {
			$("#packageName").attr("disabled", true);
		}
	} else if("defaultValue3" == defaultValueId) {
		if($("#defaultValue3").attr("checked")) {
			$("#displayType").val(defaultValue);
			$("#defaultValue").attr("checked", false);
			$("#defaultValue2").attr("checked", false);
			$("#packageName").attr("disabled", false);
		} else {
			$("#packageName").attr("disabled", true);
		}
	}
}

function enablePackageName2(defaultValueId, defaultValue){
	if("editDefaultValue" ==  defaultValueId) {
		if($("#editDefaultValue").attr("checked")) {
			$("#displayType").val(defaultValue);
			$("#editDefaultValue2").attr("checked", false);
			$("#editDefaultValue3").attr("checked", false);
			$("#packageName").attr("disabled", false);
		} else {
			$("#packageName").attr("disabled", true);
		}
	} else if("editDefaultValue2" == defaultValueId) {
		if($("#editDefaultValue2").attr("checked")) {
			$("#displayType").val(defaultValue);
			$("#editDefaultValue").attr("checked", false);
			$("#editDefaultValue3").attr("checked", false);
			$("#packageName").attr("disabled", false);
		} else {
			$("#packageName").attr("disabled", true);
		}
	} else if("editDefaultValue3" == defaultValueId) {
		if($("#editDefaultValue3").attr("checked")) {
			$("#displayType").val(defaultValue);
			$("#editDefaultValue").attr("checked", false);
			$("#editDefaultValue2").attr("checked", false);
			$("#packageName").attr("disabled", false);
		} else {
			$("#packageName").attr("disabled", true);
		}
	}
}

//ajax获取经纪人信息，将信息反填在编辑经纪人弹出窗口
function getPackage(id) {
	$.ajax({
		type : "GET",
		async : false,
		url : "/package/manage/edit/"+id,
		dataType : "json",
		success : function(data) {
			var agentPackage = data.agentPackage;
			$("#editId").val(agentPackage.id);
			$("#editHouseAmount").val(agentPackage.houseAmount);
			$("#editRefreshHouseTimes").val(agentPackage.refreshHouseTimes);
			$("#editLabelAmount").val(agentPackage.labelAmount);
			$("#editVipPicNum").val(agentPackage.vipPicNum);
			$("#editPrice").val(agentPackage.price);
			$("#editEffectiveMonth").val(agentPackage.effectiveMonth);
			$("#editEffectiveDays").val(agentPackage.effectiveDays);
			$("#editOrderIndex").val(agentPackage.orderIndex);
			if(agentPackage.defaultValue == '1') {
				$("#editDefaultValue").attr("checked", true);
			} else if(agentPackage.defaultValue == '0') {
				$("#editDefaultValue2").attr("checked", true);
			} else if(agentPackage.defaultValue == '2') {
				$("#editDefaultValue3").attr("checked", true);
			}
			if(agentPackage.defaultFree == '1') {
				$("#editDefaultFree").attr("checked", true);
				if($("#defaultPackageId").val() == agentPackage.id) {
					$("#editDefaultFree").attr("disabled", false);
				}
			}
			$("#editPackageName").val(agentPackage.packageName);
			$("*").stop();
			$("#divEdit").dialog("close");
			$("#divEdit").dialog("option", "position", "center");
			$("#divEdit").dialog("open");
		}
	});
}


//关闭编辑弹出层
function closeEdit(){
	$("*").stop();
	$("#divEdit").dialog("close");
}

//设置jquery blockUI弹出窗口
function blockAlert(msg, sec) {
	$.blockUI({
		css : {border : "none",padding : "15px",backgroundColor : "#000",'-webkit-border-radius' : "10px",'-moz-border-radius' : "10px",opacity : .6,color : "#fff"},
		message : msg
	});
	setTimeout($.unblockUI, sec);
}

// ajax获取城市、区域 
function getDist(selectId) {
	var selectElem = "#" + selectId;
	var exeDefaultCity = "#exeDefaultCity";
	var exeDelayCity = "#exeDelayCity";
	$.ajax({
		type : "GET",
		async : false,
		url : "/agentManage/ajax/zone/0",
		dataType : "json",
		success : function(data) {
			$(selectElem).empty();
			var list = data.distList;
			if (list != null && list.length > 0) {
				var defaultOption = "<option value='-2'>选择区域</option><option value='0'>全国默认</option>";
				$(defaultOption).appendTo(selectElem);
				$(defaultOption).appendTo(exeDefaultCity);
				$(defaultOption).appendTo(exeDelayCity);
				for (i in list) {
					var local = list[i];
					var option = "<option value='" + local.localid + "'>" + local.dirName + local.localname + "</option>";
					$(option).appendTo(selectElem);
					$(option).appendTo(exeDefaultCity);
					$(option).appendTo(exeDelayCity);
				}
			}
		}
	});
}

//设定选中菜单
function setSelected(selectItem, selectedValue){
	var $opt = $("#"+selectItem+" option[value="+selectedValue+"]");
	if($opt.size()){
		setTimeout(function(){
			$opt[0].selected = true;
		},100);
	}
}

function delPackage(cityId, packageId) {
	var result = confirm("确定要删除该套餐");
	if(result) {
		$("#packageForm").attr("action", "/package/manage/del/" + cityId + "/" + packageId);
		$("#packageForm").submit();
	}
}

function exeDefault() {
	$("#exeDefaultButton").attr("disabled", true);
	var defaultPackageMonth = $("#defaultPackageMonth").val();
	var exeDefaultCity = $("#exeDefaultCity").val();
	var reg =/^\d+$/;
	var flag = 0;
	if(exeDefaultCity < 0) {
		blockAlert("请选择要执行默认套餐期限的城市", 2000);
		flag++;
	}
	if(!reg.test(defaultPackageMonth)) {
		blockAlert("套餐有效期只能是正整数", 2000);
		flag++;
	}
	if (flag == 0) {
		$.ajax({
			type : "GET",
			async : false,
			url : "/package/manage/exedefault/" + exeDefaultCity + "/" + defaultPackageMonth,
			dataType : "json",
			success : function(data) {
				alert(data.info);
			}
		});
	}
}

function exeDelay(){
	$("#exeDelayButton").attr("disabled", true);
	var addDays = $("#addDays").val();
	var exeDelayCity = $("#exeDelayCity").val();
	var reg =/^\d+$/;
	var flag = 0;
	if(exeDelayCity < 0) {
		blockAlert("请选择要延长默认套餐期限的城市", 2000);
		flag++;
	}
	if(!reg.test(addDays)) {
		blockAlert("延长天数只能是正整数", 2000);
		flag++;
	}
	if (flag == 0) {
		$.ajax({
			type : "GET",
			async : false,
			url : "/package/manage/exedefaultdelay/" + exeDelayCity + "/" + addDays,
			dataType : "json",
			success : function(data) {
				alert(data.info);
			}
		});
	}
}