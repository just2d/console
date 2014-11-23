var estateId ;
var userId ;
var estateName ;
var origStatus;
    
function pass(estateId,userId,estateName){
	var url = contextPath+"/estate/authAction?estateId="+estateId+"&authStatus=1";
	var sendMsgUrl = contextPath+"/msg/sendestatePassMsg?estateName="+estateName+"&userId="+userId;
    $.getJSON(url, null, function(json){
    	//对北京地区经纪人发送站内信
        if(typeof(cityId)!="undefined"&& cityId =="1" && json!=null&&json.length>0){
        	//如果审核通过,发送站内信.
            $.getJSON(sendMsgUrl,null,function(data){
            	alert("站内信发送成功");            	    
            });
        	
        }
        clear("form_estate_id");           
        $("#estateListForm").submit();
    });
}

function delEstate(estate_Id,user_Id,estate_Name,orig_Status){
	$("#confirmDel").dialog("option", "position", "center");
    $('#confirmDel').dialog('open');
	
    estateId = estate_Id;
    userId = user_Id;
    estateName = estate_Name;
    origStatus = orig_Status;
    
	
}

function showDialog(){
        $("#uploadlp").dialog("option", "position", "center");
        $('#uploadlp').dialog('open');
}