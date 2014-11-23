$(document).ready(function() {
	$('.rejectRadio').click(function() {
		var paraArray = this.id.split('_');
		if(paraArray[0] == 'idCard') {
			if($('#idCard_appr_' + paraArray[2]).attr('checked') == 'checked') {
				$('#idCardResult_' + paraArray[2]).val('1');
				$('#idCardMsg_' + paraArray[2]).hide();
			} else {
				$('#idCardResult_' + paraArray[2]).val('2');
				$('#idCardMsg_' + paraArray[2]).show();
			}
		} else if(paraArray[0] == 'head') {
			if($('#head_appr_' + paraArray[2]).attr('checked') == 'checked') {
				$('#headResult_' + paraArray[2]).val('1');
				$('#headMsg_' + paraArray[2]).hide();
			} else {
				$('#headResult_' + paraArray[2]).val('2');
				$('#headMsg_' + paraArray[2]).show();
			}
		} else if(paraArray[0] == 'nameCard') {
			if($('#nameCard_appr_' + paraArray[2]).attr('checked') == 'checked') {
				$('#nameCardResult_' + paraArray[2]).val('1');
				$('#nameCardMsg_' + paraArray[2]).hide();
			} else {
				$('#nameCardResult_' + paraArray[2]).val('2');
				$('#nameCardMsg_' + paraArray[2]).show();
			}
		}
	});
	
	$("input[name=searchtxt]").bind("blur",txtBlur);
	$("input[name=searchtxt]").bind("focus",txtFocus);
	
	$("input[type='checkbox']").click(function() {
		var paraArray = this.id.split('_');
		var msgString = [];
		var $inputs = $(this).parents("ul:eq(0)").find("input[type=checkbox]");
		
		$inputs.each(function(){
			if(this.checked){
				msgString .push( $(this).siblings('label').html());
			}
		});
		if(paraArray[0] == 'icmsg') {
			$('#idCardMsg_text_' + paraArray[2]).val(msgString.join("; "));
		} else if(paraArray[0] == 'headmsg') {
			$('#headMsg_text_' + paraArray[2]).val(msgString.join("; "));
		} else if(paraArray[0] == 'ncmsg') {
			$('#nameCardMsg_text_' + paraArray[2]).val(msgString.join("; "));
		}
	});
	
	$('#submitButton').click(function() {
		var result = '';
		var agentIds = new Array();
		var idCardResults = new Array();
		var idCardMsgs = new Array();
		var headResults = new Array();
		var headMsgs = new Array();
		var nameCardResults = new Array();
		var nameCardMsgs = new Array();
		$("input[name='agentId']").each(function(){
			var index = agentIds.length;
			var agentId = $(this).val();
			agentIds[index] = agentId;
			idCardResults[index] = $('#idCardResult_'+agentId).val();
			idCardMsgs[index] = $('#idCardMsg_text_'+agentId).val() == '' ? '+++' : $('#idCardMsg_text_'+agentId).val();
			headResults[index] = $('#headResult_'+agentId).val();
			headMsgs[index] = $('#headMsg_text_'+agentId).val() == '' ? '+++' : $('#headMsg_text_'+agentId).val();
			nameCardResults[index] = $('#nameCardResult_'+agentId).val();
			nameCardMsgs[index] = $('#nameCardMsg_text_'+agentId).val() == '' ? '+++' : $('#nameCardMsg_text_'+agentId).val();
		});
		for(var i = 0; i < agentIds.length; i++) {
			result += agentIds[i]+"_"+idCardResults[i]+"_"+idCardMsgs[i]+"_"+headResults[i]+"_"+headMsgs[i]+"_"+nameCardResults[i]+"_"+nameCardMsgs[i]+"***";
		}
		$('#verifyResult').val(result.substring(0, result.length - 3));
		$('#verifyForm').submit();
	});
});


function submitVerify(agentId) {
	var idCardResult = $('#idCardResult_'+agentId).val();
	var idCardMsg = $('#idCardMsg_text_'+agentId).val() == '' ? '+++' : $('#idCardMsg_text_'+agentId).val();
	var headResult = $('#headResult_'+agentId).val();
	var headMsg = $('#headMsg_text_'+agentId).val() == '' ? '+++' : $('#headMsg_text_'+agentId).val();

	if(idCardResult == '0' || headResult == '0') {
		alert("请审核该经纪人的所有项目，然后再提交。");
		return;
	}
	if((idCardResult == '2' && idCardMsg == '+++') || (headResult == '2' && headMsg == '+++')) {
		alert("请选择拒绝的原因。");
		return;
	}
	var result = agentId+"_"+idCardResult+"_"+idCardMsg+"_"+headResult+"_"+headMsg;
	
	$.ajax({
		type : "post",
		async : false,
		url : "/agentVerify/verifyReq",
		dataType : "json",
		data : {"verifyResult" : result},
		success : function(data) {
			alert(data.verifyInfo);
			var currentNo = document.getElementById("currentNo");
			var pageNo = document.getElementById("page.pageNo");
			var form = getForm();
			if(form!=null){
				pageNo.value = currentNo.value;
				form.submit();
			}
		}
	});
}

function searchAgentVerify() {
	var id = $('#agentIdSearch').val() == '请输入搜索内容' ? 0 : $('#agentIdSearch').val();
	var mobile = $('#agentMobile').val() == '请输入搜索内容' ? '=' : $('#agentMobile').val();
	
	window.location.href="/agentVerify/search/"+id+"/"+mobile + "/verify";
}

function showAddReasonDiv(open) {
	if(open) {
		$('#addReasonDiv').slideDown("slow");
	} else {
		$('#addReasonDiv').slideUp("slow");
	}
}

function showUpdateReasonDiv(id,type,reason) {
	$('#updateReasonDiv').fadeIn("slow");
	$('#updateReasonType').val(type);
	$('#updateInputReason').val(reason);
	$('#updateRecordId').val(id);
}

function submitInputReason() {
	var type = $('#reasonType').val();
	var reason = $('#inputReason').val();
	
	if(reason == '') {
		alert('请填写具体理由');
		return;
	}
	$('#inputRecord').val(type+ "_" + reason);
	$('#addRejectReasonForm').submit();
}

function submitUpdateReason(){
	var type = $('#updateReasonType').val();
	var reason = $('#updateInputReason').val();
	var id=$('#updateRecordId').val();
	
	if(reason == '' || id == '') {
		alert('请选择修改的记录并填写新的理由');
		return;
	}
	$('#updateRecord').val(id+ "_" + type+ "_" + reason);
	$('#updateRejectReasonForm').submit();
}

function delReason(id) {
	var isDel = confirm("您确定要删除该条记录吗？");
	if(isDel) {
		$('#delReasonId').val(id);
		$('#delRejectReasonForm').submit();
	}
}

function txtFocus() {
	$(this).css("color", "black");
	$(this).val("");
}

function txtBlur(id) {
	String.prototype.Trim = function() {
		return this.replace(/(^\s*)|(\s*$)/g, "");
	}
	var val = $(this).val().Trim();
	if (val == "") {
		$(this).val("请输入搜索内容");
		$(this).css("color", "gray");
	}
}

//ajax删除经纪人
function connect400Phone() {
	var agentId = $('#agentIdFor400').val();
	if(agentId == '' || isNaN(agentId)) {
		alert("请输入数字格式的经纪人编号");
		return;
	}
	var msg;
	$.ajax({
		type : "get",
		async : false,
		url : "/agentVerify/connect400phone/" + agentId,
		dataType : "json",
		cache : false,
		success : function(data) {
			alert(data.connectResult);
		},
		error : function(data) {
			alert("调用后台服务错误,请稍后重试");
		}
	});
}


