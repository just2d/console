<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="/js/common/jsdate.js"></script>
<script type="text/javascript">
<!--
function search(){
	var resaleForm = document.getElementById("resaleForm");
	var pageNo = document.getElementById("page.pageNo");
	pageNo.value = 1;

	if(resaleForm!=null){
		resaleForm.submit();
	}
	
}
//-->
</script>
<div style="width: 100%;text-align: left;">当前位置：58房源管理 &gt; 二手房管理  </div>  

<form action="/resale58/allresalesearch/list" method="post" id="resaleForm" >

<div class="mag">
<div class="search" >
<table>
	<tr>
		<td>房源ID:</td><td><input type="text" name="id" style="width:100px;" value="${param.id }" /></td>
		<td>经纪人ID:</td><td><input type="text" name="authorid" style="width:100px;" value="${param.authorid }" /></td>
		<td>姓名:</td><td><input type="text" name="name" style="width:100px;" value="${param.name }" /></td>
		<td>手机号:</td><td><input type="text" style="width:100px;" name="authorphone" value="${param.authorphone }" /></td>
		<td>&nbsp;</td><td>&nbsp;</td>
	</tr>
	<tr>
		<td>信息标题:</td><td><input type="text" name="title" value="${param.title }" /></td>
		<td>城市:</td><td><select id="city" style="width:100px;" name="cityId" value="${param.cityId}"></select></td>
		<td>起始日期:</td><td><input class="def" name="startDate" style="width:100px;" class="dateCss" type="text" onclick="SelectDate(this,'yyyy-MM-dd','起始日期')" readonly="readonly" value="${param.startDate}"/></td>
		<td>终止日期:</td><td><input class="def" name="endDate" style="width:100px;" class="dateCss" type="text" onclick="SelectDate(this,'yyyy-MM-dd','终止日期')" readonly="readonly" value="${param.endDate}"/></td>
		<td>&nbsp;</td><td><input type="button" onclick="search();" value="查找" /></td>
	</tr>
</table>
</div>
</div>

		<div class="mag">

			<div class="todo">

				<table>
					<thead>
						<tr>
							<th style=" width:12.5%; text-align:center" ><input type="checkbox" onclick="checkAll(this)" /></th>
							<th style=" width:12.5%; text-align:center">房源ID</th>
							<th style=" width:12.5%; text-align:center">经纪人ID</th>
							<th style=" width:12.5%; text-align:center">姓名</th>
							<th style=" width:12.5%; text-align:center">电话</th>
							<th style=" width:12.5%; text-align:center">信息标题</th>
							<th style=" width:12.5%; text-align:center">操作</th>
						</tr>
					</thead>
					<tbody class="rasent">
						<c:forEach items="${resaleList}" var="list">
								<tr >
								<td style=" width:12.5%; text-align:center"><input type="checkbox" name="ids" value="${list.id}" />
								<td style=" width:12.5%; text-align:center"><a href="http://${list.cityDir}.taofang.com/ershoufang/${list.id}-1.html" class="link"  target="_blank" >${list.id}</a>
								</td>
								
									<td style=" width:12.5%; text-align:center">${list.authorid}</td>
									<td style=" width:12.5%; text-align:center"> ${list.authorname}</td>
									<td style=" width:12.5%; text-align:center">${list.authorphone}
									</td>
									<td style=" width:12.5%; text-align:center">
									<a class="cluetip" href="http://${list.cityDir}.taofang.com/ershoufang/${list.id}-1.html"   rel="#img${list.id}" target="_blank" >${list.title}</a>

									<div id="img${list.id}" style="display:none;text-align: left">
									<c:forEach items="${list.photobrowse.split('\\\|')}" var="imgUrl" varStatus="status" >
										<c:if test="${imgUrl.trim().length() gt 0 }">
											<img src="${applicationScope.imgUrl58Prefix}${imgUrl}" width="200px" height="150" />
											
											<c:if test="${status.index%2==1 }">
											<br />
											</c:if>
										</c:if>
									</c:forEach> 
									</div>
									</td>

									<td style=" width:12.5%; text-align:center" ><a href="javascript:deleteResale(${list.id})" >删除</a>&nbsp;</td>


								</tr>
							</c:forEach>
					</tbody>

				</table>

			</div>

		</div>

		<div  style="text-align: left;" >
	<input type="button" onclick="batchDelete();" value="删除" />&nbsp;
	
	</div>
		<div class="page_and_btn" >
		
			<jsp:include page="/WEB-INF/snippets/page.jsp" />
		</div>
	
	</form>

<script type="text/javascript">

$(".cluetip").cluetip({
	width:450,
	cluetipClass:"jtip",
	showTitle:false,
	local:true,
	arrows:true,
	sticky:true,
	mouseOutClose:true,
	closeText:'关闭',
	activation:"hover"
});

function batchDelete(){
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
	if(confirm("所选房源确定删除?")){
		
		$.ajax({
			type : "post",
			async : false,
			url : "/resale58/batch/delete",
			dataType : "json",
			data : {"ids" : ids,"type":2},
			success : function(data) {
				if(data.error){
					alert(data.error);
					return;
				}
				if(data.info=="success"){
					alert("所选房源删除成功");
				}else{
					alert("操作失败!");
					return ;
				};
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
}
function deleteResale(id){
	
	if(confirm("房源(ID:"+id+")确定删除?")){
		
		$.ajax({
			type : "post",
			async : false,
			url : "/resale58/delete",
			dataType : "json",
			data : {"id" : id,"type":2},
			success : function(data) {
				if(data.info=="success"){
					alert("房源(ID:"+id+")删除成功");
				}else{
					alert("操作失败!");
					return ;
				};
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
}

//ajax获取城市、区域 
function getDist(selectId,pid) {
	var selectElem = "#" + selectId;
	$.ajax({
		type : "GET",
		async : false,
		url : "/agentManage/ajax/zone/"+pid,
		dataType : "json",
		success : function(data) {
			$(selectElem).empty();
			var list = data.distList;
			if (list != null && list.length > 0) {
				var global = "<option value='-1'>www全国</option>";
				$(global).appendTo(selectElem);
				for (i in list) {
					var local = list[i];
					var option = "<option value='" + local.localid + "'>" + local.dirName + local.localname + "</option>";
					$(option).appendTo(selectElem);
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

$(document).ready(function(){
	var cityId = ${requestScope.cityId};
	if(cityId == null || cityId == "") {
		cityId = 0;
	}
	getDist("city",0);
	if(cityId > 0) {
		setSelected("city",cityId);
	}
});
 //-->
</script>