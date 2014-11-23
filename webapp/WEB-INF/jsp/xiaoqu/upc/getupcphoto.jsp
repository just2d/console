<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.taofang.com/tld/func" prefix="func"%>
<link rel="stylesheet" href="${contextPath}/css/plot-compare.css" type="text/css" />
<script type="text/javascript" src="${contextPath}/js/xiaoqu/common.js"></script>
<script type="text/javascript">
<!--
$(function(){
	
	$('#showImgDialog').dialog({
		show : "slide",
		bgiframe : true,
		autoOpen : false,
		height : 540,
		width : 670,
		title : "小区图大图",
		modal : true,
		resizable : false
	});
});

function showImg(path){
	$("#limg").attr("src",path);
	$("#showImgDialog").dialog("option", "position", "center");
	$('#showImgDialog').dialog('open');
}


/**
 * 移动到精选库
 * @param {Object} cls
 */
function mv(cls, selNum) {
	var selIds = "";
	var unselIds = "";
	selIds = getSelIds(cls);
	//获得选中的复选框个数
	var num = $("input[class=" + cls + "]:checked").length;
		if (selNum < num) {
			alert("目前最多可选择" + selNum + "张图片进入精选库");
			return;
		} else {
			unselIds = getUnSelIds(cls);
			window.location.href = "/estateupc/submitupcphoto?toUsingIds=" + selIds
					+ "&unUsingIds=" + unselIds +"&cityid="+$('#city').val();
		}
}

 
 function refreshPage(){
		$("#estateUpcForm").attr("action","/estateupc/getupcphoto");
		document.getElementById('estateUpcForm').submit();
	}
//-->
</script>
<div style="width: 100%;text-align: left;">当前位置：小区完善管理 &gt; 小区完善审核 &gt; 小区相册审核</div>  
<br />
 <div id="tabs" class="tabs">  
     <ul>  
        <li><a href="/estateupc/getupctext" style="background:url(/images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x 50% 50%" >小区数据审核</a></li>  
        <li  class="tabs_active"  ><a href="/estateupc/getupcphoto" >小区相册审核</a></li>  
     </ul> 

 </div> 
 <br />
<div class="compare">
	<form action="/estateupc/getupcphoto" method="post" id="estateUpcForm" >
	<div class="mag">
    	<div>
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
    </form>
	<c:if test="${photoUpcList  == null }">
		<div  style="text-align: center;" >
				<br/>
				<h2 style="font-size:18px;">已经审核完该城市小区图片的提交,请选择其他城市审核或者返回.</h2>
			
		</div>
	</c:if>
	
	<c:if test="${photoUpcList  != null }">
    <table width="100%" border="0" class="plot_fram">
          <tr>
            <td width="25%" style="vertical-align:top">
                <div class="plot_l">
                    <h2>${estate.estateId} - ${estate.estateName }小区    ${func:getName(estate.distId) }-${func:getName(estate.blockId) }</h2>
                    <div class="l_scrll">
                     <ul class="c">
                    <c:forEach items="${photoList}" var="layoutPhoto">
	 				<li>
	 					<span>
	 						<img src="${layoutPhoto.sPhoto}" style="width:100px;height:75px;" class="jin_nostyle"/><input type="checkbox" class="sel" value="${layoutPhoto.id}" style="display: none"/>
	 						<input type="button" value="大图" onclick="showImg('${layoutPhoto.lPhoto}')"/>
	 					</span>
	 				</li>		
	 				</c:forEach>
                    </ul>
                    </div>
                    <p align="center">
                    </p>
                </div>
            </td>
             <td width="75%" style="vertical-align:top">
                <div class="plot_r">
                	<h2></h2>
                    <div class="r_scrll">
                    <ul class="c">
                    	 <c:forEach items="${photoUpcList}" var="backupPhoto">
	 				<li>
	 					<span>
	 						<img src="${backupPhoto.smallPhotoUrl}" style="width:152px;height:102px;" id="" class="nostyle" value="${backupPhoto.photo.id}"></img>
	 						<input type="checkbox" value="${backupPhoto.photo.id}" style="display: none" class="backSel"/><input type="button" value="大图" onclick="showImg('${backupPhoto.originalPhotoUrl}')"/>
	 					</span>
	 				</li>		 
	 				</c:forEach>
                    </ul>
                    </div>
                    <input type="hidden" id="del_flag" name="del_flag" value="${delFlag }" />
                    <p><input type="submit" name="button" class="btn_3" value="移动到精选库" onclick="mv('backSel','${60-layoutList.size()}')"/></p>
                </div>
            </td>
          </tr>
    </table>
    </c:if>
    
    <div id="showImgDialog">
		<img  id="limg"></img >
	</div>
</div>