$(function(){
	var delMsgUrl = contextPath+"/msg/sendestateDelMsg";
	$("#sendBt").click(function(){
		var userId= $("#createUserId").val();
		var content = $("#content").val();
		$.getJSON(delMsgUrl+"?createUserId="+userId+"&content="+content, null, function(json){
            if(json!=null && json.result){
            	hiddenDialog("uploadlp");
            }else{
            	alert("小区删除成功,为找到创建该小区的经纪人,消息发送失败");
            	hiddenDialog("uploadlp");
            }
            clear("form_estate_id");
            $("#estateListForm").submit();
        });
	});
	
	$("#cannelBt").click(function(){
         hiddenDialog("uploadlp");
         clear("form_estate_id");
         $("#estateListForm").submit();
	});
	
});

function hiddenDialog(dialogId){
	$('#'+dialogId).dialog("close");
}

