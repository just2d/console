<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.taofang.com/tld/func" prefix="func"%>
<head>
<meta http-equiv="Expires" CONTENT="0">
<meta http-equiv="Cache-Control" CONTENT="no-cache">
<meta http-equiv="Pragma" CONTENT="no-cache">
</head>
<script type="text/javascript" src="/js/common/jquery.blockUI.js"></script>

<div style="width: 100%;text-align: left;">当前位置：小区完善管理 &gt; 小区完善审核 &gt; 小区数据审核</div>  
<br />
 <div id="tabs" class="tabs">  

     <ul>  
        <li class="tabs_active"  ><a href="/estateupc/getupctext" >小区数据审核</a></li>  
        <li ><a href="/estateupc/getupcphoto" style="background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%" >小区相册审核</a></li>  
     </ul> 

 </div> 
<br />

	<form action="/estateupc/submitupctext" method="post" id="estateUpcForm" >
		<c:if test="${upc != null }">
		<input type="hidden" name="taskId" value="${upc.taskId }" />
		<input type="hidden" name="checkCount" value="${upc.validFieldCount }" />
		<input type="hidden" name="creatorId" value="${upc.creatorId }" />
		<input type="hidden" name="creatorName" value="${upc.creatorName }" />
		<input type="hidden" name="createTime" value="${upc.createTime }" />
		<input type="hidden" name="id" value="${estate.id }" />
		<input type="hidden" name="cityId" value="${estate.cityId }" />
		<input type="hidden" name="distId" value="${estate.distId }" />
		<input type="hidden" name="blockId" value="${estate.blockId }" />
		<input type="hidden" name="estateName" value="${estate.estateName }" />
		</c:if>
		<div class="mag">
			<div style="float:left">
			<label>所在城市:</label>
                <select id="city" name="cityid" style="width: 100px;margin-left: 10px;" onchange="refreshPage();">
               		<option value="0">小区城市</option>
                	<c:forEach items="${applicationScope.simpleLocaleMap }" var="entry">
                		<c:if test="${cityid==entry.key }">
	                		<option value="${entry.key }" selected="selected">${entry.value.code }${entry.value.name }</option>
                		</c:if>
                		<c:if test="${cityid!=entry.key }">
                			<option value="${entry.key }">${entry.value.code }${entry.value.name }</option>
                		</c:if>
                	</c:forEach>
                </select>
               </div>
		</div>
		<c:if test="${objs  != null }">
		<div  style="height:32px;line-height:32px;clear:both">
			<h2 style="float:left;font-size:14px;margin:0px;padding:0px;margin-right:20px;">${estate.estateName }-${estate.id}</h2>
			<p style="float:left;margin:0px;padding:0px;margin-right:20px;">${func:getName(estate.distId) }-${func:getName(estate.blockId) } </p>
			<p style="float:left;margin:0px;padding:0px;margin-right:20px;">经纪人-${upc.creatorName }</p>
			<p style="float:left;margin:0px;padding:0px;color:red">选中的批量通过,则当前页面没有被选中的为不通过</p>
		</div>
		</c:if>
	<div>
	<c:if test="${objs != null }">
		<div class="todo"> 
			<table>
				<thead>
					<tr><th style="width:5%;"><input type="checkbox" onclick="checkAll(this)"/>全选</th><th style="width:9%;">内容项</th><th style="width:43%;">修改前</th><th style="width:43%;">修改后</th></tr>
				</thead>
				<tbody>
						<c:if test="${upc.alias !=null && upc.alias !='' }" ><tr><td align="left"><input type="checkbox" value="${upc.alias}" name="alias"/></td><td>小区别名</td><td>${estate.alias }</td><td>${upc.alias }</td></tr></c:if>
						<c:if test="${upc.addr !=null && upc.addr !='' }"><tr><td align="left"><input type="checkbox"  value="${upc.addr}" name="addr"/></td><td>小区地址</td><td>${estate.addr }</td><td>${upc.addr }</td></tr></c:if>
						<c:if test="${upc.propertyCompany !=null && upc.propertyCompany !='' }" ><tr><td align="left"><input type="checkbox" value="${upc.propertyCompany}" name="propertyCompany"/></td><td>物业公司</td><td>${estate.propertyCompany }</td><td>${upc.propertyCompany }</td></tr></c:if>
						<c:if test="${upc.propertyType !=null && upc.propertyType !='' }" ><tr><td align="left"><input type="checkbox" value="${upc.propertyType}" name="propertyType"/></td><td>物业类型</td><td><c:if test="${estate.propertyType ==1}">住宅</c:if><c:if test="${estate.propertyType ==2}">商铺</c:if><c:if test="${estate.propertyType ==3}">别墅</c:if><c:if test="${estate.propertyType ==4}">写字楼</c:if><c:if test="${estate.propertyType ==5}">其他</c:if></td><td><c:if test="${upc.propertyType ==1}">住宅</c:if><c:if test="${upc.propertyType ==2}">商铺</c:if><c:if test="${upc.propertyType ==3}">别墅</c:if><c:if test="${upc.propertyType ==4}">写字楼</c:if><c:if test="${upc.propertyType ==5}">其他</c:if></td></tr></c:if>
						<c:if test="${upc.propertyFee !=null && upc.propertyFee !='' }" ><tr><td align="left"><input type="checkbox" value="${upc.propertyFee}" name="propertyFee"/></td><td>物业费</td><td>${estate.propertyFee }</td><td>${upc.propertyFee }</td></tr></c:if>
						<c:if test="${upc.buildYear !=null && upc.buildYear !='' }" ><tr><td align="left"><input type="checkbox" value="${upc.buildYear}" name="buildYear"/></td><td>建筑年代</td><td>${estate.buildYear }</td><td>${upc.buildYear }</td></tr></c:if>
						<c:if test="${upc.developer !=null && upc.developer !='' }" ><tr><td align="left"><input type="checkbox" value="${upc.developer}" name="developer"/></td><td>开发商</td><td>${estate.developer }</td><td>${upc.developer }</td></tr></c:if>
						<c:if test="${upc.areaRate !=null && upc.areaRate !='' }"  ><tr><td align="left"><input type="checkbox" value="${upc.areaRate}" name="areaRate"/></td><td>容积率</td><td>${estate.areaRate }</td><td>${upc.areaRate }</td></tr></c:if>
						<c:if test="${upc.floorArea !=null && upc.floorArea !='' }" ><tr><td align="left"><input type="checkbox" value="${upc.floorArea}" name="floorArea"/></td><td>占地面积</td><td>${estate.floorArea }</td><td>${upc.floorArea }</td></tr></c:if>
						<c:if test="${upc.buildingArea !=null && upc.buildingArea !='' }" ><tr><td align="left"><input type="checkbox" value="${upc.buildingArea}" name="buildingArea"/></td><td>建筑面积</td><td>${estate.buildingArea }</td><td>${upc.buildingArea }</td></tr></c:if>
						<c:if test="${upc.parking !=null && upc.parking !='' }" ><tr><td align="left"><input type="checkbox" value="${upc.parking}" name="parking"/></td><td>车位信息</td><td>${estate.parking }</td><td>${upc.parking }</td></tr></c:if>
						<c:if test="${upc.greenRate !=null && upc.greenRate !='' }" ><tr><td align="left"><input type="checkbox" value="${upc.greenRate}" name="greenRate"/></td><td>绿化率</td><td>${estate.greenRate }</td><td>${upc.greenRate }</td></tr></c:if>
						<c:if test="${upc.description !=null && upc.description !='' }" ><tr><td align="left"><input type="checkbox" value="${upc.description}" name="description"/></td><td>项目介绍</td><td>${estate.description }</td><td>${upc.description }</td></tr></c:if>
						<tr><td><input type="checkbox"onclick="checkAll(this)"/>全选</td></tr>
				</tbody>
			</table>
		</div>
	</c:if>
	</div>
	<div  style="text-align: center;" >
		<c:if test="${objs != null }">
			<input type="button" value="提交并继续" onclick="submitIt();"/> 
		</c:if>
		<c:if test="${objs  == null }">
			<br/>
			<h2>已经审核完该城市小区文字数据的提交,请选择其他城市审核或者返回.</h2>
		</c:if>
	
	</div>
</form>
<script>
function checkAll(obj){
	if(obj.checked){
		$(":checkbox").attr("checked",true); 
	}else{
		$(":checkbox").attr("checked",false); 
	}
}
function refreshPage(){
	$("#estateUpcForm").attr("action","/estateupc/getupctext");
	document.getElementById('estateUpcForm').submit();
}

function submitIt(){
	var checkFlag = false;
	
	for(var i=0;i<$(":checkbox").size();i++){
		if($(":checkbox").eq(i).attr("checked")){
			checkFlag =true;
			break;
		}
	}
	if(!checkFlag){
		if(confirm("您未选中任何要审核的项,确认要继续吗")){
			$("#estateUpcForm").attr("action","/estateupc/submitupctext");
			document.getElementById('estateUpcForm').submit();
		}else {
			return ;
		}
	}else{
		$("#estateUpcForm").attr("action","/estateupc/submitupctext");
		document.getElementById('estateUpcForm').submit();
	}
}
</script>