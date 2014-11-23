function clear(id){
	$("#"+id).val("");
	$("#isClicked").val("1");
}

$(function(){
	//全选按钮点击事件
	$("#allchk").click(function(){
		if(this.checked){
			$("input[class='sel']").attr("checked", true);	
		}else{
			$("input[class='sel']").attr("checked", false);
		}
	});
	
	
	//获得所有选中的复选框的id
	function getSelIds(){
		//获得所有选中的checkBox;
		var selVal = "";
		$(".sel").each(function(){
			if(this.checked){
				selVal += $(this).val()+",";
			}
		});
		return selVal;
	}
	
	//批量删除
	$(".delBt").click(function(){
		//获得所有选中的checkBox;
		var selVal = getSelIds();
		if(selVal == ""){
			alert("请至少选择一条记录删除!!");
			return;
		}else{
			var url = "authAction?estateId="+selVal+"&authStatus=3&origStatus="+auth_status;
			$.getJSON(url, null, function(json){
			  	if(json!=null&&json.length>0){
			  		alert("操作成功");
			  		clear("form_estate_id");
			  		$("#estateListForm").submit();
			  	}
			});
		}
	});
	
	
	
	//批量通过审核
	$(".pastBt").click(function(){
		//获得所有选中的checkBox;
		var selVal = getSelIds();
		if(selVal == ""){
			alert("请至少选择一条记录");
			return ;
		}else{
			var url = "authAction?estateId="+selVal+"&authStatus=1";
			$.getJSON(url, null, function(json){
			  	if(json!=null&&json.length>0){
			  		alert("操作成功");
			  		clear("form_estate_id");
			  		$("#estateListForm").submit();
			  	}
			});
		}
	});
	
	
	//批量不通过审核
	$(".unpastBt").click(function(){
		//获得所有选中的checkBox;
		var selVal = getSelIds();
		if(selVal == ""){
			alert("请至少选择一条记录");
			return ;
		}else{
			var url = "authAction?estateId="+selVal+"&authStatus=2";
			$.getJSON(url, null, function(json){
			  	if(json!=null&&json.length>0){
			  		alert("操作成功");
			  		clear("form_estate_id");
			  		$("#estateListForm").submit();
			  	}
			});
		}
	});
	
	//合并小区
	$(".unionBt").click(function(){
		//获得所有选中的checkBox;
		//获得是否选中了目标小区.
		var mainEstate = $(".main_estate");
		var mainEstateLen = mainEstate.children().length;
		if(mainEstateLen == 0){
			alert("请设置主小区");
			return;
		}
		var checkedLen = $("input[type=checkbox]:checked").length;
		if(checkedLen <2){
			alert("至少选择两条记录!");
			return;
		}
		//获得主小区id
		var mainEstateIdStr = mainEstate.attr("id");
		var mainEstatePinyin = mainEstate.attr("py");
		var mainEstateId = 0;
		var idArray = new Array();
		if(typeof(mainEstateIdStr) !="undefined" && mainEstateIdStr != null && mainEstateIdStr.length >2){
			mainEstateId = mainEstateIdStr.substring(2);
			//获得搜索条件.
			var estateName = $("#origEstateName").text();
			//获得要合并的小区id
			$(".sel:checked").each(function(){
				var chkVal = $(this).val();
				//获得审核状态
				//如果不是主小区,
				if(mainEstateId != chkVal&& chkVal!=""){
					var auth_status = $(this).parent().siblings("#auth_"+chkVal).attr("authstatus");
					idArray.push(chkVal+"_"+auth_status);
				}			
			});
			$.ajax({
				type: "post",  
	   			dataType: "json",  
	   			url: 'union',//提交到一般处理程序请求数据   
	   			data: "targetId=" + mainEstateId+"&targetPinYin="+mainEstatePinyin+ "&sourceId=" + idArray+"&cityId="+cityId,
	   			cache:false,
	   			beforeSend:function(){
					$("#loading").html('<div style="margin:40px;text-align:center" ><img src="'+contextPath+'/images/loading.gif"/></div>');
	   			},
	   			success: function(data){
	   				if(typeof(data)!="undefined" && data != null && data.success == true){
	   					window.location.href = contextPath+"/estate/list?estateName="+estateName+"&authStatus=0&isClicked=0&cityId="+cityId;
	   					alert("合并成功...")
	   				}else{
	   					if(typeof(data)!="undefined" && data != null && data.fail !=null ){
	   						alert(data.fail);
	   					}
	   					
	   				}
	   			},
	   			complete: function(){
	   				$("#loading").html("");
				}
			});
		}else{
			alert("系统错误,请联系技术人员...");
			return ;
		}
		
		
	});
})

/**
 * 设为主小区
 */
function setMainEstate(id){
	$("tr").each(function(){
		$(this).removeClass("main_estate");
	});
	$("#tr"+id).addClass("main_estate");
	//将主小区选中; 
	$("#tr"+id).children().first().children().attr("checked", true);
}

function getValueById(id){
	var returnId = $("#"+id).val();
	if(typeof(returnId)!="undefined" && returnId!=null ){
		return returnId;
	}else{
		return "";
	}
	
}

function changePageNoAndSubmit(){
	var pageNo = document.getElementById("page.pageNo");
	pageNo.value = 0;
	$("#isClicked").val("0");
	$("#estateListForm").submit();
}

function changePageNoAndSubmitWithDel(){
	var pageNo = document.getElementById("page.pageNo");
	pageNo.value = 0;
	$("#isClicked").val("0");
	$("#estateDelListForm").submit();
}

//将小区名填充到搜索框的小区名输入框,避免人工输入.
function setName(sourceName,targetId){
	//获得点击前的小区名
	$("#"+targetId).val(sourceName);
	$("#isClicked").val("1");
}