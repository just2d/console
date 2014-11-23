<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div style="width: 100%;text-align: left;">当前位置：广告房源管理>><b>房源管理</b></div>
<form id="advHouseListForm" action="/adv/advhouse/search" method="get">
<div class="mag" style="border:1px solid #CAD9EA">
<select class="cs" name="searchCity" id="scity" style="width: 100px;">
		<option value="-2">请选择城市</option>
	</select>
	<select class="cs" name="searchDist" id="sdist" style="width: 100px;">
		<option value="-2">请选择区域</option>
	</select>
	<select class="cs" name="searchBlock" id="sblock" style="width: 100px;">
		<option value="-2">请选择商圈</option>
	</select>
	    <select class="cs" name="searchWebsite" id="searchWebsite" style="width: 100px;">
		<option value="-1">投放的网站</option>
		<option value="1">58.com</option>
	   </select>
	    <select class="cs" name="searchWebsitePos" id="searchWebsitePos" style="width:120px;">
		<option value="-1">投放的网站位置</option>
		<option value="1">二手房</option>
		<option value="2">租房-整租</option>
		<option value="3">租房-合租</option>
	   </select>
	    <select class="cs" name="searchHouseType" id="searchHouseType" style="width: 100px;">
		<option value="-1">房源的类别</option>
		<option value="1">二手房</option>
	    <option value="2">租房-整租</option>
		<option value="3">租房-合租</option>
	   </select>
	   <br>
	  展示时间:
		 <input type="text" id="searchSdated" name="searchSdated" style="width:80px;" maxLength="10"   onclick="InitSelectDate(0,this,'yyyy-MM-dd','')" readonly="readonly" />日
		 <select class="cs" id="searchSdateh" name="searchSdateh" style="width:70px;">
		     <c:forEach  var="hour"   begin="0"  end="23"  >
		     
		       <c:if test="${hour<10}">  <option value="0${hour}">0${hour}时 </option></c:if>
		        <c:if test="${hour>=10}"><option value="${hour}">${hour}时 </option></c:if>
		     
		    </c:forEach>
	</select>至
		 <input type="text" id="searchEdated" name="searchEdated" style="width:80px;" maxLength="10"  onclick="InitSelectDate(0,this,'yyyy-MM-dd','')" readonly="readonly" />日
		 <select class="cs" id="searchEdateh" name="searchEdateh" style="width:70px;">
		    <c:forEach  var="hour"   begin="0"  end="23"  >
		     
		       <c:if test="${hour<10}">  <option value="0${hour}">0${hour}时 </option></c:if>
		        <c:if test="${hour>=10}"><option value="${hour}">${hour}时 </option></c:if>
		     
		    </c:forEach>
	</select>
	   房源Id:<input  type="text" value=""  name="searchHouseId"  style="width: 60px;">
	<input class="def" type="submit" value="搜索" style="width: 60px;">
	<input class="def" id="addBtn" type="button" value="添加广告房源" style="width: 110px;">
</div>
<div class="mag">
<div class="todo">
	<table>
	<thead>
	<tr>
	<c:if test="${fn:length(houseList)==0}"><th>广告房源列表为空</th></c:if>
	<c:if test="${fn:length(houseList)!=0}"> 
	<th><input type="checkbox" id="selectAll" name="selectAll"/></th><th>投放网站</th><th>投放网站位置</th><th>房源类别</th><th>广告位置</th><th>推荐房源1</th>
	<th>推荐房源2</th><th>推荐房源3</th><th>展示时间</th><th colspan="2">编辑</th>
	</c:if>
	</tr>
	</thead>
	<tbody>
		<c:forEach items="${houseList}" var="tmp">
		<tr>
		<td><input type="checkbox" name="idChk" value="${tmp.id}"/></td>
		<td>
		<c:if test="${tmp.website==1}">58.com</c:if>
		</td>
		<td>
		<c:if test="${tmp.websitePos==1}">二手房</c:if>
	    <c:if test="${tmp.websitePos==2}">租房-整租</c:if>
	    <c:if test="${tmp.websitePos==32}">租房-合租</c:if>
		</td>
		<td>
		<c:if test="${tmp.houseType==1}">二手房</c:if>
	     <c:if test="${tmp.websitePos==2}">租房-整租</c:if>
	    <c:if test="${tmp.websitePos==32}">租房-合租</c:if>
		</td>
		<td>${tmp.locationName}</td>
		<td>
		<a href="http://${tmp.cityCode}.taofang.com/58/${tmp.houseType==1?'ershoufang':'zufang'}/${tmp.location}-${tmp.f1}.html"  target="_blank">${tmp.f1}</a>
		</td>
		<td><a href="http://${tmp.cityCode}.taofang.com/58/${tmp.houseType==1?'ershoufang':'zufang'}/${tmp.location}-${tmp.f1}.html"  target="_blank">${tmp.f2}</a></td>
		<td><a href="http://${tmp.cityCode}.taofang.com/58/${tmp.houseType==1?'ershoufang':'zufang'}/${tmp.location}-${tmp.f1}.html"  target="_blank">${tmp.f3}</a></td>
				<td>${tmp.showDate}</td>
		<td><a href="javascript:showAdvHouse('${tmp.id}','${tmp.website}','${tmp.websitePos}','${tmp.houseType}','${tmp.cityId}','${tmp.distId}','${tmp.blockId}','${tmp.f1},${tmp.f2},${tmp.f3},${tmp.f4},${tmp.f5},${tmp.f6}','${tmp.showSDate}','${tmp.showSDateH}','${tmp.showEDate}','${tmp.showEDateH}')">修改</a></td>
		<td><a href="javascript:deleteAdvHouse('${tmp.id}')">删除</a></td>
		</tr>
		</c:forEach> 
	</tbody> 
	</table>
	</div>
	<c:if test="${fn:length(houseList)!=0}"> 
	<div class="mag">
		<input class="def" type="button" id="delBtn" value="批量删除"  style="width: 80px;"/>
	</div>
	</c:if>
	</div>
	<c:if test="${fn:length(blackList)!=0}"> 
	<div class="mag">
		<input class="def" type="button" id="delBtn" value="批量删除"  style="width: 80px;"/>
	</div>
	</c:if>
	<div class="page_and_btn">
			<jsp:include page="/WEB-INF/snippets/page.jsp" />
		</div>
	</form>

	<div id="divAdd"  style="distplay:none">
	      <input type="hidden" id="idEdit" name="idEdit" />
	      <span  id="error"  style="color:red;"></span>
	      <jsp:include page="advHouseMes.jsp"></jsp:include>
	</div>


	
	<div id="question" class="cfm" align="center"> 
        <div style="font-weight: bold;font-size: 13px;">您确定要删除吗?</div> 
        <input type="button" id="yes" value="是" /> 
        <input type="button" id="no" value="否" /> 
	</div> 
	<input type="hidden"  name="changeDateEvent" id="changeDateEvent"  value="true"/>
<script type="text/javascript" src="/js/common/jsdate.js"></script>
 <script src="/js/common/location.js" type="text/javascript"></script>
	<script type="text/javascript">
$(function() {
	locationInit("scity","sdist","sblock",'${searchConditions.searchCity}','${searchConditions.searchDist}','${searchConditions.searchBlock}');

	
	$("#searchHouseType").val(${searchConditions.searchHouseType});
	$("#searchWebsite").val(${searchConditions.searchWebsite});
	$("#searchWebsitePos").val(${searchConditions.searchWebsitePos});
	
	$("#searchSdated").val('${searchConditions.searchSdated}');
	$("#searchSdateh").val('${searchConditions.searchSdateh}');
	$("#searchEdated").val('${searchConditions.searchEdated}');
	$("#searchEdateh").val('${searchConditions.searchEdateh}');
	
		
	$("#housetype").bind("change",function(){changeDateEvent();});
	$("#website").bind("change",function(){changeDateEvent();});
	$("#websitePos").bind("change",function(){changeDateEvent();});
	
	
	
	$("#edateh").bind("change",function(){initCity();});
	$("#sdateh").bind("change",function(){initCity();});
	
	$("#addBtn").bind("click",add);
	$("#selectAll").bind("click",selAll);
	$("#delBtn").bind("click",deleteMore);
	$("#no").bind("click", function() {$.unblockUI();return false;});
	$("#c_no").bind("click", function() {$.unblockUI();return false;});
	$("#divAdd").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 350,width : 500,title : "添加广告房源",modal : true,resizable : false,buttons : {"添加" : advHouseAdd,"取消" : clearAddDiv}});

	//checkbox状态重置，未被选中
	clearCheckBox();
});


// 全选信息
function selAll() {
	if ($("#selectAll").attr("checked")) {
		$("input[name=idChk]").attr("checked", true);
	} else {
		$("input[name=idChk]").attr("checked", false);
	}
}

// 显示增加黑名单窗口
function add() {
	$("#divAdd").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 350,width : 500,title : "添加广告房源",modal : true,resizable : false,buttons : {"添加" : advHouseAdd,"取消" : clearAddDiv}});
	var fArray=new Array();
	for(var i=0;i<5;i++){
		fArray[i]='';
	}
	setHouseValue(0,'','','','','','',fArray,'','','','');
	
	$("*").stop();
	$("#divAdd").dialog("close");
	$("#divAdd").dialog("option", "position", "center");
	$("#divAdd").dialog("open");
}

// ajax增加广告房源，增加完毕显示增加操作结果，刷新当前页面
function advHouseAdd() {
	 if(!check()){
		 return; 
	 }
	var para=paraMes();
		$.ajax({
			type : "GET",
			async : false,
			url : "/adv/advhouse/add",
			dataType : "text",
			data : para,
			success : function(data) {
                if(data=="have"){
                	$("#error").text("在该时间段此类房屋已存在!");
                }else{
                	$.unblockUI();
            		clearCheckBox();
            		window.location.reload();
            		$("#divAdd").dialog("close");
                }
				 
			}
		
		});
		
//	});
}
function  check(){

	var  sdated=$("#sdated").val();
	if(sdated==null||sdated==''){
	   	 blockAlert("请选择展示开始时间", 3000);
	   	 return false;
	   }
	var  edated=$("#edated").val();
	if(edated==null||edated==''){
	   	 blockAlert("请选择展示结束时间", 3000);
	   	 return false;
	   }
	
	if(edated<sdated){
		 blockAlert("请选择展示开始时间要小于结束时间", 3000);
	   	 return false;
	}
	if(edated==sdated){
		var  sdateh=$("#sdateh").val();
		var  edateh=$("#edateh").val();
		if(edateh<=sdateh){
			 blockAlert("请选择展示开始时间要小于结束时间", 3000);
			 return false;
		}	
	}
	var  city=$("#city").val();
    if(city<1){
    	 blockAlert("请选择广告位置", 3000);
    	 return  false;
    }
	
	

	var  f1=$("#f1").val();
	  if(f1==null||f1==''){
 	  blockAlert("请填写推荐房源", 3000);
 	  return false;
  }
	  var pattern =/^[1-9]{1}\d*-[01]{1}$/;
	 for(var i=1;i<=6;i++){
		var  tmp=$("#f"+i).val(); 
		if(tmp!=null&&tmp.length>0){
			 if(!pattern.exec(tmp)){
				 blockAlert("请输入正确的推荐房源，格式:id-0/1", 3000);
				 return false;
			 }
		}
	}
	return  true;

}
//ajax增加广告房源，增加完毕显示增加操作结果，刷新当前页面
function   paraMes(){
	 var para={};
	
		var  website=$("#website").val();
		var  websitePos=$("#websitePos").val();
		var  housetype=$("#housetype").val();
		var  city=$("#city").val();
		var  dist=$("#dist").val();
		var  block=$("#block").val();
		var  location=1;
		if(block==null||block<=0){
			 if(dist==null||dist<=0){
				  if(city==null||city<=0){
					     blockAlert("请选择广告位置", 2000);
					    return;
				  }else{
					  location=city; 
				  }
			 }else{
					location=dist;
				}
		}else{
			location=block;
		}

	    para.website=website;
	    para.websitePos=websitePos;
	    para.houseType=housetype;
	    para.location=location;
	    para.f1=$("#f1").val();
	    para.f2=$("#f2").val();
	    para.f3=$("#f3").val();
	    para.f4=$("#f4").val();
	    para.f5=$("#f5").val();
	    para.f6=$("#f6").val();
	    para.sdated=$("#sdated").val();
	    para.edated=$("#edated").val();
	    para.sdateh=$("#sdateh").val();
	    para.edateh=$("#edateh").val();
	    para.cityId=city;
	    para.distId=dist;
	    para.blockId=block;
	    return  para;
}
function advHouseEdit() {
	 if(!check()){
		 return; 
	 }
	var para=paraMes();
	var  id=$("#idEdit").val();
	para.id=id;
		$.ajax({
			type : "GET",
			async : false,
			url : "/adv/advhouse/edit",
			dataType : "text",
			data : para,
			success : function(data) {
				 if(data=="have"){
	                	$("#error").text("在该时间段此类房屋已存在!");
	                }else{
	                	$.unblockUI();
	            		clearCheckBox();
	            		window.location.reload();
	            		$("#divAdd").dialog("close");
	                }
			}
		});
}

function clearAddDiv(){
	$("#divAdd").dialog("close");
}

//提示弹出层，msg要显示的消息，sec显示停留的时间
function blockAlert(msg, sec) {
	$.blockUI({
		css : {border : "none",padding : "15px",backgroundColor : "#000",'-webkit-border-radius' : "10px",'-moz-border-radius' : "10px",opacity : .6,color : "#fff"},
		message : msg
	});
	setTimeout($.unblockUI, sec);
}

//弹出修改黑名单窗口

function  setHouseValue(id,website,websitepos,housetype,city,dist,block,fArray,sdated,sdateh,edated,edateh){	
	    $("#idEdit").val(id);
	    $("#error").text("");
	    $("#website").val(website);
		$("#websitePos").val(websitepos);
		$("#housetype").val(housetype); 
		for(var i=0;i<6;i++){
			$("#f"+(i+1)).val(fArray[i]);
		}
		$("#sdated").val(sdated);
		$("#edated").val(edated);
		$("#sdateh").val(sdateh);
		$("#edateh").val(edateh);
	
}
function showAdvHouse(id,website,websitepos,housetype,city,dist,block,fstr,sdated,sdateh,edated,edateh){	
	$("#divAdd").dialog({show : "slide",bgiframe : true,autoOpen : false,height : 350,width : 500,title : "修改广告房源",modal : true,resizable : false,buttons : {"修改" : advHouseEdit,"取消" : clearAddDiv}});
	
	var fArray=fstr.split(',');
	setHouseValue(id,website,websitepos,housetype,city,dist,block,fArray,sdated,sdateh,edated,edateh);
	initSelCity("/adv/advhouse/loc/","city","dist","block",city,dist,block);

					$("*").stop();
					$("#divAdd").dialog("option", "position", "center");
					$("#divAdd").dialog("open");
	
}


//删除黑名单，显示删除操作结果，刷新当前页面
function deleteAdvHouse(id){
	$.blockUI({message : $("#question"),css : {width : "275px"}});
	$("#yes").click(function() {
		$.ajax({
			type : "get",
			async : false,
			url : "/adv/advhouse/del/" + id,
			dataType : "json",
			cache : false,
			success : function(data) {
				blockAlert(data.message, 2000);
			}
		});
		$.unblockUI();
		clearCheckBox();
		window.location.reload();
	});
}

//清空checkbox选中状态，全部置为未被选中状态
function clearCheckBox(){
	$("input[name=selectAll]").attr("checked",false);
	$("input[name=idChk]").attr("checked",false);
}

//删除多个黑名单
function deleteMore(){
	var $ids = $("input[name=idChk]:checked");
	if ($ids.size() == 0) {
		blockAlert("请选择要删除的广告房源", 2000);
	} else {
		var content = $ids.val();
		var ids = "";
		$("input[name=idChk]:checked").each(function() {
			ids += $(this).val() + ",";
		});
		deleteAdvHouse(ids);
	}
}
function   getPara(){
	var  houseType=$("#housetype").val();
	var  website=$("#website").val();
	var  websitepos=$("#websitePos").val();
	var  sdated=$("#sdated").val();
	var  edated=$("#edated").val();
	var  sdateh=$("#sdateh").val();
	var  edateh=$("#edateh").val();
   
    if(sdated!=null&&sdated!=''&&edated!=null&&edated!=''){
    	 return  "searchWebsite="+website+"&searchWebsitePos="+websitepos+"&searchHouseType="+houseType+"&searchSdated="+sdated+"&searchEdated="+edated+"&searchEdateh="+sdateh+"&searchSdateh="+edateh;
    }else{
    	return '';
    }

}
function   initCity(){
	initSelCity("/adv/advhouse/loc/","city","dist","block",-2,-2,-2);
	
}

function  initSelCity(url,cityPageId,distPageId,blockPageId,selectCityId,selectDistId,selectBlockId){
	var  para=getPara();
	if(para!=null&&para!=''){
		getDist(cityPageId,url+0+"?"+para, selectCityId);
	}
		urlCityChange(url,distPageId,blockPageId, selectCityId,selectDistId);
		urlDistChange(url,blockPageId, selectDistId,selectBlockId);
	

	$("#"+cityPageId).bind("change", function() {urlCityChange(url,distPageId,blockPageId,$(this).val(),-1);});
	$("#"+distPageId).bind("change", function() {urlDistChange(url,blockPageId,$(this).val(),-1);});
}

function urlCityChange(url,distPageId,blockPageId,cityid,selectDistId) {
	if(cityid!=null&&cityid!=''&&cityid!='-2'){
		var  para=getPara();
		getDist(distPageId, url + cityid+"?"+para,selectDistId);
	}else{
		$("#"+distPageId).empty().append("<option value='-2'>请选择区域</option>");
	}
	
}
function urlDistChange(url,blockPageId,distid,selectBlockId) {
	var  para=getPara();
	if(distid!=null&&distid!=''&&distid!='-2'){
	   getDist(blockPageId, url + distid+"?"+para,selectBlockId);
	}else{
		$("#"+blockPageId).empty().append("<option value='-2'>请选择商圈</option>");
	}
}
function changeDateEvent() {
	initCity();
}


</script>
