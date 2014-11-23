<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
.single_up {
    background-color: #FFFFFF;
    border: 0 none;
    cursor: pointer;
    font-size: 80px;
    margin: 0 0 0 -330px;
    opacity: 0;
    padding: 0;
    size: 0;
}
.single_up_btn {
    background: url("${contextPath}/images/broker_140x32.gif") repeat scroll 0 0 transparent;
    border: 0 none;
    color: #FFFFFF;
    cursor: pointer;
    font-weight: bold;
    height: 32px;
    margin-right: 20px;
    width: 100px;
}
</style>
<script type="text/javascript">
<!--
	var contextPath = '${contextPath}'; 
//-->`
</script>
<script type="text/javascript" src="${contextPath}/js/xiaoqu/common.js"></script>
<script type="text/javascript" src="${contextPath}/js/xiaoqu/list.js">
</script>
<script type="text/javascript" src="${contextPath}/js/xiaoqu/layout.js">
</script>
<script type="text/javascript">
<!--
$(function(){
	var row=1;
	$(".btr").each(function(){
		$(this).attr("index",row);
		if(row == 23 || row == 24){
			$(this).hide();
		}
  		row++;
  	});
})

function compare(estateid,backupCount,th,photoType,hasOrNot,idOrder){
	var url = contextPath+"/estate/layout/compSel?backupCount="+backupCount+"&estateId="+estateid+"&flag=0&hasOrNot="+hasOrNot+"&idOrder="+idOrder;
	if(photoType =="1"){
		url = contextPath+"/estate/commPhoto/compSel?backupCount="+backupCount+"&estateId="+estateid+"&flag=1&hasOrNot="+hasOrNot+"&idOrder="+idOrder;
	}
	 
	var currTr = $(th).parent().parent();
	var currIdx = currTr.attr("index");
	//var nextIdx = parseInt(currIdx)+1;
	//var thirdIdx = parseInt(currIdx)+2;
	//var nextTr = $("tr[index='"+nextIdx+"']");
	//var thirdTr = $("tr[index='"+thirdIdx+"']");
	//var nextEstateId = nextTr.attr("id");
	//var thirdEstateId = thirdTr.attr("id");
	//if(typeof(nextEstateId)!="undefined"){
		//url += "&nextEstateId="+nextEstateId;
	//}
	//if(typeof(thirdEstateId) !="undefined"){
	//	url += "&thirdEstateId="+thirdEstateId;
	//}
	//获得表单查询条件和当前页信息.
	var cityId = $("#cityLocale").val();
	var distId = $("#distLocale").val();
	var blockId = $("#blockLocale").val();
	var currPageNo = $("#currentNo").val();
	var  estateName = $("#estateName").val();
	//获得点击行的索引.
	url +="&cityId="+cityId+"&distId="+distId+"&blockId="+blockId+"&pageNo="+currPageNo+"&index="+currIdx+"&estateName="+estateName;
	window.location.href = url;
	return false;
}
function ajaxFileUpload(id){
  $("#loading").ajaxStart(function(){
	  $("#uploadbtn").attr("display:true");
      $(this).show();
  }).ajaxComplete(function(){
	  $("#uploadbtn").attr("display:false");
     $(this).hide();
  });//文件上传完成将图片隐藏起来
  $.ajaxFileUpload({
      url:'${contextPath}/estate/layout/uploadAction',//用于文件上传的服务器端请求地址
      secureuri:false,
      fileElementId:id,
      dataType: 'json',//返回值类型 一般设置为json
      success: function (data, status){          
          if(typeof(data.error) != 'undefined'){
              
          }
      },
      error: function (data, status, e){
          alert("传败");
      }
  }) 
   return false;
 }

//-->
</script>
<form action="/estate/photoList" method="post" id="estateListForm">
	<div class="mag" style="border: 1px solid #CAD9EA">
		首字母:
		<select name="ch" id="ch" onchange="setCityInfo(this.value)">
			<option value="">
				-请选择-
			</option>
			<option value="a">
				A
			</option>
			<option value="b">
				B
			</option>
			<option value="c">
				C
			</option>
			<option value="d">
				D
			</option>
			<option value="e">
				E
				<J /option>
				<option value="f">
					F
				</option>
				<option value="g">
					G
				</option>
				<option value="h">
					H
				</option>
				<option value="i">
					I
				</option>
				<option value="j">
					J
				</option>
				<option value="k">
					K
				</option>
				<option value="l">
					L
				</option>
				<option value="m">
					M
				</option>
				<option value="n">
					N
				</option>
				<option value="o">
					O
				</option>
				<option value="p">
					P
				</option>
				<option value="q">
					Q
				</option>
				<option value="r">
					R
				</option>
				<option value="s">
					S
				</option>
				<option value="t">
					T
				</option>
				<option value="u">
					U
				</option>
				<option value="v">
					V
				</option>
				<option value="w">
					W
				</option>
				<option value="x">
					X
				</option>
				<option value="y">
					Y
				</option>
				<option value="z">
					Z
				</option>
		</select>
		城市:
		<select name="cityId" id="cityLocale"
			onchange="setDistInfo(this.value)">
			<option value="">
				-请选择-
			</option>
			<c:if test="${cityName ne null}">
				<option value="${condition.cityId}" selected="selected">
					${cityName}
				</option>
			</c:if>
		</select>
		区域:
		<select name="distId" id="distLocale"
			onchange="setBlockInfo(this.value)">
			<option value="">
				-请选择-
			</option>
			<c:forEach items="${distList}" var="dist">
				<option value="${dist.id}" ${dist.id eq
					condition.distId ? 'selected=selected' :''}>
					${dist.name}
				</option>
			</c:forEach>
		</select>
		板块:
		<select name="blockId" id="blockLocale">
			<option value="">
				-请选择-
			</option>
			<c:forEach items="${blockList}" var="block">
				<option value="${block.id}" ${block.id eq
					condition.blockId ? 'selected=selected' :''}>
					${block.name}
				</option>
			</c:forEach>
		</select>
		图片类型:
		<select name="photoType" id="photoType">
			<option value="0" ${condition.photoType eq '0'? 'selected=selected' :''}>户型图</option>
			<option value="1" ${condition.photoType eq '1'? 'selected=selected' :''}>小区图</option>
		</select>
		图片数量:
        <select name="hasOrNot" id="hasOrNot">
        	<option value="">不限</option>
            <option value="null" ${condition.hasOrNot eq 'null'? 'selected=selected' :''}>空</option>
            <option value="full" ${condition.hasOrNot eq 'full'? 'selected=selected' :''}>满</option>
            <option value="has"  ${condition.hasOrNot eq 'has'? 'selected=selected' :''}>存在</option>
        </select>
        ID升序排列：
        <input type="checkbox" name="idOrder" value="idOrder" ${condition.idOrder eq 'idOrder'? 'checked':''}>
		小区名:
		<input type="text" name="estateName"  id="estateName" value="${condition.estateName}" size="15"/>
		小区ID:
		<input type="text" value="${estateId ne 0 ? estateId :''}"
			name="estateId" size="9" id="estateId"/>
		<input class="def" type="button" value="搜索" style="width: 60px;"
			onclick="search()">
	</div>
	<div id="statDiv"></div>
	<div class="mag">
		<div class="todo">
			<table id="statTable">
				<thead>
					<tr>
						<th>
							<input type="checkbox" name="allchk" id="allchk" />
						</th>
						<th>
							小区ID
						</th>
						<th>
							小区名称
						</th>
						<th>
							所在区域
						</th>
						<th>
							板块
						</th>
						<th>
							精选库
						</th>
						<th>
							备选库
						</th>
						<th>
							操作
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${layoutList}" var="layout">
						<tr id="${layout.estateId}" class="btr">
							<td>
								<input type="checkbox" name="sel_${layout.estateId}" class='sel'
									value="${layout.estateId}" />
							</td>
							<td>
								${layout.estateId}
							</td>
							<td>${layout.estateName}
								<!-- 
								<a
									href="http://${layout.estateId}.${sysDomain}/xiaoqu/${layout.estateId}/"
									target="_blank">${layout.estateName}</a> -->
							</td>
							<td>
								${layout.cityName}--${layout.distName}
							</td>
							<td>
								${layout.blockName}
							</td>
 							<c:choose>
								<c:when test="${condition.photoType eq '0'}">
									<td>${layout.layoutCount}</td>
									<td>${layout.backupLayoutCount}</td>
								</c:when>
								<c:when test="${condition.photoType eq '1'}">
									<td>${layout.commCount}</td>
									<td>${layout.backupCommCount}</td>
								</c:when>
							</c:choose>
							<td>
								<a href="javascript:void(0)"
									onclick="return compare('${layout.estateId}','${condition.photoType eq '0' ?layout.backupLayoutCount :layout.backupCommCount}',this,'${condition.photoType}','${condition.hasOrNot}','${condition.idOrder }')">比较精选</a>
								<div id="loading" style="display: none;">
										<img src="${contextPath}/images/wait.gif">
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="page_and_btn" align="center">
			<jsp:include page="/WEB-INF/snippets/page_xq.jsp" />
		</div>
</form>
