 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div style="width: 100%;text-align: left;">当前位置：房源管理 &gt; 二手房管理 &gt; ${agent.name}-待审核房源</div>  
<form action="/resale/house/${authorId}" method="post" id="resaleForm" >
	
			<div class="mag">

			<div class="todo">

				<table>

					<thead>

						<tr>
						<th style=" width:10%; text-align:center"> 
							</th>
							<th style=" width:18%; text-align:center">房源信息</th>
							<th style=" width:18%; text-align:center">户型图</th>
							<th style=" width:18%; text-align:center">室内图</th>
							<th style=" width:18%; text-align:center">小区图</th>
							<th style=" width:18%; text-align:center">操作</th>

						</tr>

					</thead>

					<tbody class="rasent">

						<c:forEach items="${resaleInfoList}" var="resaleInfo">
								<tr >
									<td style=" width:10%; text-align:center"><input type="checkbox" name="ids" value="${resaleInfo.id}" />
								</td>
									<td style=" width:18%; text-align:left;vertical-align: top;">
									<div style="width: 315px;padding: 5px;white-space: normal;line-height:18px;text-align: left;font-size:12px;">
										 <h3>${resaleInfo.title}(id:${resaleInfo.id})</h3>
										<ul>
                            <li>总价：<strong>${resaleInfo.price}</strong>万</li>
                            <li class="price">单价：
                            	<span>
                            		<fmt:formatNumber type="number" pattern="0" value="${resaleInfo.price/resaleInfo.area*10000}" maxFractionDigits="0"/>
                            	</span>/㎡</li>
                            <li>户型：${resaleInfo.flattype}</li>
                            <li class="area">面积：<span>${resaleInfo.area}</span>平米</li>
                            <li>楼层：${resaleInfo.floorLabel}</li>
                            <li>年份：${resaleInfo.completion}年</li>
                            <li>小区：${resaleInfo.estatename}</li>
                            <li>地址：${resaleInfo.addr}</li>
                            <c:if test="${resaleInfo.extinfo.length() == 0 }">
                                <li class="isempty">没写描述信息</li>
                            </c:if>
                            <c:if test="${resaleInfo.extinfo.length() > 0 }">
                                <li>房屋描述：${resaleInfo.extinfo}</li>
                            </c:if>
                        </ul>
								</div>					
									</td>
									<td style=" width:18%; text-align:center; vertical-align: top;">
									<c:forEach items="${resaleInfo.houseImgUrls}" var="img">
										<span id="${img.houseid }${img.photoid }" >
										<img ondblclick="delPhoto('${img.houseid }','${img.photoid }','${img.category }');" src="${img.mURL}" width="200px" height="150"/>
									</span>	
									</c:forEach>
													
									</td>
									<td style=" width:18%; text-align:center;vertical-align: top;">
											<c:forEach items="${resaleInfo.roomImgUrls}" var="img">
									<div>
										<span id="${img.houseid }${img.photoid }" >
										<img ondblclick="delPhoto('${img.houseid }','${img.photoid }','${img.category }');" src="${img.mURL}" width="200px" height="150"/>
									</span>	
										</div>
									</c:forEach>		
									</td>
									<td  style=" width:18%; text-align:center;vertical-align: top;">
									<c:forEach items="${resaleInfo.estateImgUrls}" var="img">
									<div>
									<span id="${img.houseid }${img.photoid }" >
										<img ondblclick="delPhoto('${img.houseid }','${img.photoid }','${img.category }');" src="${img.mURL}" width="200px" height="150"/>
									</span>	
										</div>
									</c:forEach>
													
									</td>
									<td style=" width:18%; text-align:center"><a href="javascript:openReject(${resaleInfo.id})" > [违规]</a>&nbsp;<a href="javascript:pass(${resaleInfo.id})" >[通过]</a>&nbsp;
													
									</td>
									
								</tr>
								</c:forEach>
					</tbody>

				</table>

			</div>

		</div>
</form>
<div  style="text-align: left;" >
全选 <input type="checkbox" onclick="checkAll(this)" /> &nbsp;
		<input type="button" onclick="openBatchReject();" value="违规房源" />&nbsp;
		<input type="button" onclick="batchPass();" value="通过" />
	</div>
<script type="text/javascript">
function delPhoto(houseid,photoid,category){
	
	$.ajax({
		type : "post",
		async : false,
		url : "/resale/photo/delete",
		dataType : "json",
		data : {"houseid" : houseid,"photoid":photoid,"category" : category},
		success : function(data) {
			if(data.error){
				alert(data.error);
				return;
			}
			if(data.info=="success"){
				$("#"+houseid+photoid).html("");
				//alert("删除成功");
			}else{
				//alert("操作失败!");
				return ;
			};
			
		}
	});
	
}
$(function() {
	// 初始化弹出窗口
	$("#divBatchReject").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 300,width : 500,title : "违规房源",modal : true,resizable : false,buttons : {"保存违规原因" : rejectBatchSave}});
	// 初始化弹出窗口
	$("#divBatchDelete").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 300,width : 500,title : "删除房源",modal : true,resizable : false,buttons : {"删除原因" : deleteBatchSave}});
	// 初始化弹出窗口
	$("#divReject").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 300,width : 500,title : "违规房源",modal : true,resizable : false,buttons : {"保存违规原因" : rejectSave}});
	// 初始化弹出窗口
	$("#divDelete").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 300,width : 500,title : "删除房源",modal : true,resizable : false,buttons : {"删除原因" : deleteSave}});
});
//显示拒绝窗口
function openBatchReject() {
	$("*").stop();
	$("#divBatchReject").dialog("close");
	$("#divBatchReject").dialog("option", "position", "center");
	$("#divBatchReject").dialog("open");
}
//显示拒绝窗口
function openBatchDelete() {
	$("*").stop();
	$("#divBatchDelete").dialog("close");
	$("#divBatchDelete").dialog("option", "position", "center");
	$("#divBatchDelete").dialog("open");
}
function rejectBatchSave() {
	var tempIds = document.getElementsByName("ids");
	var checkNum = 0;
	var ids = "";
	for(var j = 0 ;j<tempIds.length;j++){
		if(tempIds[j].checked){
			if(checkNum==0){
				ids = tempIds[j].value;
			}else{
				ids =ids+","+tempIds[j].value;
			}
			checkNum++;
		}
	}
	if(ids.length==0){
		alert("请至少选择一个房源!");
		return;
	}
	
	var reasons = "";
	var i = 1;
	while(true){
		var reason = $("#batchReasons_"+i);
		if(reason[0]){
			if(reason.is(':checked')){
				reasons +=$("#batchReasons_"+i+"_value").html()+ "<br /> ";
			}
		}else{
			break;
		}
		i++;
		if(i>30){
			break;
		}
	}
	reasons += $("#batchReasons").val().trim();
	if(reasons.trim()==""){
		alert("请选择或填写违规原因！");
		return ;
	}

		$.ajax({
			type : "post",
			async : false,
			url : "/resale/verifyReq/batch",
			dataType : "json",
			data : {"ids" : ids,"type":0,"reasons" : reasons},
			success : function(data) {
				if(data.error){
					alert(data.error);
					return;
				}
				if(data.info=="success"){
					alert("所选房源成功设置为违规房源!");
				}else{
					alert("操作失败!");
					return ;
				};
				$("#divBatchReject").dialog("close");
				var form = document.getElementById("resaleForm");
				if(form!=null){
					form.submit();
				}
			}
		});


}

function deleteBatchSave(){
	var tempIds = document.getElementsByName("ids");
	var checkNum = 0;
	var ids = "";
	for(var j = 0 ;j<tempIds.length;j++){
		if(tempIds[j].checked){
			if(checkNum==0){
				ids = tempIds[j].value;
			}else{
				ids =ids+","+tempIds[j].value;
			}
			checkNum++;
		}
	}
	if(ids.length==0){
		alert("请至少选择一个房源!");
		return;
	}
	var reasons = "";
	var i = 1;
	while(true){
		var reason = $("#deleteBatchReasons_"+i);
		if(reason[0]){
			if(reason.is(':checked')){
				reasons +=$("#deleteBatchReasons_"+i+"_value").html()+ "<br /> ";
			}
		}else{
			break;
		}
		i++;
		if(i>30){
			break;
		}
	}
	reasons += $("#deleteBatchReasons").val().trim();
	if(reasons.trim()==""){
		alert("请选择或填写删除原因！");
		return ;
	}

		$.ajax({
			type : "post",
			async : false,
			url : "/resale/verifyReq/batch",
			dataType : "json",
			data : {"ids" : ids,"type":1,"reasons" : reasons},
			success : function(data) {
				if(data.error){
					alert(data.error);
					return;
				}
				if(data.info=="success"){
					alert("删除成功");
				}else{
					alert("操作失败!");
					return ;
				};
				$("#divDelete").dialog("close");
				var form = document.getElementById("resaleForm");
				if(form!=null){
					form.submit();
				}
			}
		});

}
function batchPass(){
	var tempIds = document.getElementsByName("ids");
	var checkNum = 0;
	var ids = "";
	for(var j = 0 ;j<tempIds.length;j++){
		if(tempIds[j].checked){
			if(checkNum==0){
				ids = tempIds[j].value;
			}else{
				ids =ids+","+tempIds[j].value;
			}
			checkNum++;
		}
	}
	if(ids.length==0){
		alert("请至少选择一个房源!");
		return;
	}
	if(confirm("所选房源确定审核通过?")){
		
		$.ajax({
			type : "post",
			async : false,
			url : "/resale/verifyReq/batch",
			dataType : "json",
			data : {"ids" : ids,"type":2},
			success : function(data) {
				if(data.error){
					alert(data.error);
					return;
				}
				if(data.info=="success"){
					alert("所选房源审核通过");
				}else{
					alert("操作失败!");
					return ;
				};
				var form = document.getElementById("resaleForm");
				if(form!=null){
					form.submit();
				}
			}
		});
	}
}
//显示拒绝窗口
function openReject(id) {
	$("*").stop();
	$("#resaleId").html(id);
	$("#divReject").dialog("close");
	$("#divReject").dialog("option", "position", "center");
	$("#divReject").dialog("open");
}
//显示拒绝窗口
function openDelete(id) {
	$("*").stop();
	$("#deleteresaleId").html(id);
	$("#divDelete").dialog("close");
	$("#divDelete").dialog("option", "position", "center");
	$("#divDelete").dialog("open");
}
function rejectSave() {

	var id = $("#resaleId").html();
	var reasons = "";
	var i = 1;
	while(true){
		var reason = $("#reasons_"+i);
		if(reason[0]){
			if(reason.is(':checked')){
				reasons +=$("#reasons_"+i+"_value").html()+ "<br /> ";
			}
		}else{
			break;
		}
		i++;
		if(i>30){
			break;
		}
	}
	reasons += $("#reasons").val().trim();
	if(reasons.trim()==""){
		alert("请选择或填写违规原因！");
		return ;
	}

		$.ajax({
			type : "post",
			async : false,
			url : "/resale/verifyReq",
			dataType : "json",
			data : {"id" : id,"type":0,"reasons" : reasons},
			success : function(data) {
				if(data.error){
					alert(data.error);
					return;
				}
				if(data.info=="success"){
					alert("房源(ID:"+id+")成功设置为违规房源");
				}else{
					alert("操作失败!");
					return ;
				};
				$("#divReject").dialog("close");
				var form = document.getElementById("resaleForm");
				if(form!=null){
					form.submit();
				}
			}
		});

}

function deleteSave(){
	var id = $("#deleteResaleId").html();
	var reasons = "";
	var i = 1;
	while(true){
		var reason = $("#deleteReasons_"+i);
		if(reason[0]){
			if(reason.is(':checked')){
				reasons +=$("#deleteReasons_"+i+"_value").html()+ "<br /> ";
			}
		}else{
			break;
		}
		i++;
		if(i>30){
			break;
		}
	}
	reasons += $("#deleteReasons").val().trim();
	if(reasons.trim()==""){
		alert("请选择或填写删除原因！");
		return ;
	}

		$.ajax({
			type : "post",
			async : false,
			url : "/resale/verifyReq",
			dataType : "json",
			data : {"id" : id,"type":1,"reasons" : reasons},
			success : function(data) {
				if(data.error){
					alert(data.error);
					return;
				}
				if(data.info=="success"){
					alert("房源(ID:"+id+")删除成功");
				}else{
					alert("操作失败!");
					return ;
				};
				$("#divDelete").dialog("close");
				var form = document.getElementById("resaleForm");
				if(form!=null){
					form.submit();
				}
			}
		});

}
function pass(id){
	
	if(confirm("房源(ID:"+id+")确定审核通过?")){
		
		$.ajax({
			type : "post",
			async : false,
			url : "/resale/verifyReq",
			dataType : "json",
			data : {"id" : id,"type":2},
			success : function(data) {
				if(data.info=="success"){
					alert("房源(ID:"+id+")审核通过");
				}else{
					alert("操作失败!");
					return ;
				};
				var form = document.getElementById("resaleForm");
				if(form!=null){
					form.submit();
				}
			}
		});
	}
}
//-->
</script>


<div id="divBatchReject">
	<table>
		<tr>
			<td style="text-align: left" valign="top">违规原因:&nbsp;</td>
			<td style="text-align: left" valign="top"><c:forEach
					items="${reasons}" var="list" varStatus="vs">
					<input type="checkbox" id="batchReasons_${vs.index+1 }">
					<label id="batchReasons_${vs.index+1 }_value">${list.reason}</label>
					<br />
				</c:forEach></td>
		</tr>
		<tr>
			<td valign="top">其它原因:&nbsp;</td>
			<td style="text-align: left"><textarea class="rejectTextArea"
					id="batchReasons" name="batchReasons" cols="40" rows="3"></textarea>
			</td>
		</tr>
	</table>
</div>
<div id="divBatchDelete">
	<table>
		<tr>
			<td style="text-align: left" valign="top">删除原因:&nbsp;</td>
			<td style="text-align: left" valign="top"><c:forEach
					items="${deleteReasons}" var="list" varStatus="vs">
					<input type="checkbox" id="batchDeleteReasons_${vs.index+1 }">
					<label id="batchDeleteReasons_${vs.index+1 }_value">${list.reason}</label>
					<br />
				</c:forEach></td>
		</tr>
		<tr>
			<td valign="top">其它原因:&nbsp;</td>
			<td style="text-align: left"><textarea class="rejectTextArea"
					id="batchDeleteReasons" name="batchDeleteReasons" cols="40"
					rows="3"></textarea></td>
		</tr>
	</table>
</div>

<div id="divReject">
	<table>
		<tr>
			<td>房源ID:&nbsp;</td>
			<td style="text-align: left"><label id="resaleId"></label></td>
		</tr>
		<tr>
			<td style="text-align: left" valign="top">违规原因:&nbsp;</td>
			<td style="text-align: left" valign="top"><c:forEach
					items="${reasons}" var="list" varStatus="vs">
					<input type="checkbox" id="reasons_${vs.index+1 }">
					<label id="reasons_${vs.index+1 }_value">${list.reason}</label>
					<br />
				</c:forEach></td>
		</tr>
		<tr>
			<td valign="top">其它原因:&nbsp;</td>
			<td style="text-align: left"><textarea class="rejectTextArea"
					id="reasons" name="reasons" cols="40" rows="3"></textarea></td>
		</tr>
	</table>
</div>

<div id="divDelete">
	<table>
		<tr>
			<td>房源ID:&nbsp;</td>
			<td style="text-align: left"><label id="deleteResaleId"></label>
			</td>
		</tr>
		<tr>
			<td style="text-align: left" valign="top">删除原因:&nbsp;</td>
			<td style="text-align: left" valign="top"><c:forEach
					items="${deleteReasons}" var="list" varStatus="vs">
					<input type="checkbox" id="deleteReasons_${vs.index+1 }">
					<label id="deleteReasons_${vs.index+1 }_value">${list.reason}</label>
					<br />
				</c:forEach></td>
		</tr>
		<tr>
			<td valign="top">其它原因:&nbsp;</td>
			<td style="text-align: left"><textarea class="rejectTextArea"
					id="deleteReasons" name="deleteReasons" cols="40" rows="3"></textarea>
			</td>
		</tr>
	</table>
</div>