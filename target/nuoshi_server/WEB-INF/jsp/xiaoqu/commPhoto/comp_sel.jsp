<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="${contextPath}/css/plot-compare.css" type="text/css" />
<script type="text/javascript">
	var contextPath = '${contextPath}';
	var url = '&${paramMap}';
	var nextPage = '${nextpageUrl}';
</script>
<script type="text/javascript" src="${contextPath}/js/xiaoqu/common.js"></script>
<script type="text/javascript" src="${contextPath}/js/xiaoqu/commPhoto.js"></script>
<script type="text/javascript">
$(function(){
	
	$("#cpEstateid").val('${estateInfo.estateId}');
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
	$('#uploadcp').dialog({
		show : "slide",
		bgiframe : true,
		autoOpen : false,
		height : 230,
		width : 330,
		title : "小区图上传",
		modal : true,
		resizable : false
	});
});
window.onload=function(){
	var error ='${errorStr}';
	if(error != ''){
		alert(error);
	}
}
function showImg(path){
	$("#limg").attr("src",path);
	$("#showImgDialog").dialog("option", "position", "center");
	$('#showImgDialog').dialog('open');
}

<!--
	function toNextEstate(){
		if(${hasNext}){
			window.location.href = contextPath+"/estate/commPhoto/compSel?"+nextPage;	
		}else{
			alert("亲....所有小区都已被你光顾了....不能再往前了....");
			return;
		}
	}
	
	/**
 	*设置默认小区户型图. 
 	*/
	function setDefaultPhoto(){
		//获得被选中的checkbox;
		$(".radio_sel").each(function(){
			if(this.checked){
				var photoId = $(this).val();
				var order = $(this).attr("index");
				$.getJSON("${contextPath}/estate/commPhoto/setDefaultLayout?photoId="+photoId+"&estateId=${estateInfo.estateId}", null, function(json){
				  	if(json!=null&&json.length>0){
				  		alert("设置成功");
				  	}
				});
			}
		});
		
	}
	
	function showCommDig(){
		$("#uploadcp").dialog("option", "position", "center");
		$('#uploadcp').dialog('open');
	}

//-->
</script>
<%@ include file="../layout/tab.jsp" %>
<div class="compare">
<!--      <form name="form1" method="post" action="" class="form1">
    </form>-->
    <div class="mag" style="border: 1px solid #CAD9EA">
    <form action="${contextPath}/estate/photoList" method= "post">
    <%@ include file="/WEB-INF/jsp/xiaoqu/search.jsp" %>
          图片数量:
        <select name="hasOrNot" id="hasOrNot">
        	<option value="">不限</option>
            <option value="null" ${condition.hasOrNot eq 'null'? 'selected=selected' :''}>空</option>
            <option value="full" ${condition.hasOrNot eq 'full'? 'selected=selected' :''}>满</option>
            <option value="has"  ${condition.hasOrNot eq 'has'? 'selected=selected' :''}>存在</option>
        </select>
       ID升序排列：
        <input type="checkbox" name="idOrder" value="idOrder" ${condition.idOrder eq 'idOrder'? 'checked':''}>  
            小区名:<input type="text" name="estateName" value="${condition.estateName}" />
            小区ID:<input type="text" value="${estateId ne 0 ? estateId :''}"name="estateId" size="10"/>
          <input type="hidden" value="${index}" name="index"/>
          <input type="hidden" value="${pageNo}" name="pageNo"/>
          <input type="hidden" value="${flag}" name="flag"/>
          <input type="hidden" value="1" name="photoType"/>
    <input type="submit" value="查询" />
    </form>
    </div>
    <table width="100%" border="0" class="plot_fram">
          <tr>
            <td width="25%" style="vertical-align:top">
                <div class="plot_l">
                    <h2>${estateInfo.estateName}小区(${condition.estateId }) 精选库(${photoNum})<span><input type="button" value="上传小区图" onclick="showCommDig()"/></span></h2>
                    <div class="l_scrll">
                     <ul class="c">
                    <c:forEach items="${layoutList}" var="layoutPhoto">
	 				<li>
	 					<span>
	 						<img src="${layoutPhoto.sPhoto}" style="width:100px;height:75px;" class="jin_nostyle"/><input type="checkbox" class="sel" value="${layoutPhoto.id}" style="display: none"/>
	 						<input type="radio" name="estateid" style="margin-right:6px;" class="radio_sel" value="${layoutPhoto.id}" index="${layoutPhoto.order}" <c:if test="${layoutPhoto.id == estateInfo.photoId}">checked="checked"</c:if> />默认
	 						<input type="button" value="大图" onclick="showImg('${layoutPhoto.lPhoto}')"/>
	 					</span>
	 				</li>		
	 				</c:forEach>
                    </ul>
                    </div>
                    <p align="center">
                        <input type="submit" class="btn_2" name="button" value="设为默认" onclick="setDefaultPhoto()"/>
                       <input type="submit" class="btn_2" name="button" value="从精选库删除" onclick="delToBackup('sel',3)"/>
                    </p>
                </div>
            </td>
             <td width="75%" style="vertical-align:top">
                <div class="plot_r">
                    <h2><span>${estateInfo.estateName}小区 备选库(${backupCount})</span><span>物业类型：${estateInfo.wyType}</span><span>竣工日期：${estateInfo.buildYear}</span></h2>
                    <div class="r_scrll">
                    <ul class="c">
                    	 <c:forEach items="${backupPhotoList}" var="backupPhoto">
	 				<li>
	 					<span>
	 						<img src="${backupPhoto.mPhoto}" style="width:152px;height:102px;" id="" class="nostyle" value="${backupPhoto.id}"></img>
	 						<input type="checkbox" value="${backupPhoto.id}" style="display: none" class="backSel"/><input type="button" value="大图" onclick="showImg('${backupPhoto.lPhoto}')"/>
	 					</span>
	 				</li>		 
	 				</c:forEach>
                    </ul>
                    </div>
                    <input type="hidden" id="del_flag" name="del_flag" value="${delFlag }" />
                    <p><input type="submit" name="button" class="btn_3" value="移动到精选库" onclick="mv('backSel','${60-layoutList.size()}')"/><span class="plot_del"><input type="submit" name="button" class="btn_3" value="从备选库删除" onclick="del('backSel')"/></span></p>
                </div>
            </td>
          </tr>
         <tr><td colspan="2"><input type="button" class="btn_3" value="下一个小区" onclick="toNextEstate()"/></td></tr> 
    </table>
    
    <div id="showImgDialog">
		<img  id="limg"></img >
	</div>
</div>

<div id="uploadcp">
	<form name="role" action="${contextPath}/estate/commPhoto/uploadAction?${paramMap}" method="post" enctype="multipart/form-data">
		<input type="hidden" name="estateId" id="cpEstateid"/>
		<table border="0" cellpadding="3" cellspacing="1">
			<tr><td>图片:</td><td><input style="width: 200px;"  type="file" name="commPhoto" id="commPhoto"></td></tr>
			<tr><td align="center" colspan="2"><input type="submit" value="上传" id="lpBtn"/></td></tr>
		</table>
	</form>
</div>