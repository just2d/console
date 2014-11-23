<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel="stylesheet" href="${contextPath}/css/publish.css" type="text/css" />
<script src="/js/forum/bbs.js" type="text/javascript"></script>
<script type="text/javascript">

$(function(){
	if('${result}'!=null&&'${result}'!=""){
		alert('${result}');
	}
});
function search(){
	var rentForm = document.getElementById("rentForm");
	var pageNo = document.getElementById("page.pageNo");
	pageNo = 1;
	
	if(rentForm!=null){
		rentForm.submit();
	}
	
}
search();
</script>


	
	       <div class="cum c">
            	<div>
                <span>当前位置：板块管理</span>
                </div>

            </div>
<FORM action="/bbs/manager/searchforum"   >
            <div>
                 首字母:<select name="ch" id="ch" onchange='setCityInfo(this.value,"cityLocale")'>
	  			<option value="">-请选择-</option>
	  			<option value="a">A</option>
	  			<option value="b">B</option>
	  			<option value="c">C</option>
	  			<option value="d">D</option>
	  			<option value="e">E<J/option>
	  			<option value="f">F</option>
	  			<option value="g">G</option>
	  			<option value="h">H</option>
	  			<option value="i">I</option>
	  			<option value="j">J</option>
	  			<option value="k">K</option>
	  			<option value="l">L</option>
	  			<option value="m">M</option>
	  			<option value="n">N</option>
	  			<option value="o">O</option>
	  			<option value="p">P</option>
	  			<option value="q">Q</option>
	  			<option value="r">R</option>
	  			<option value="s">S</option>
	  			<option value="t">T</option>
	  			<option value="u">U</option>
	  			<option value="v">V</option>
	  			<option value="w">W</option>
	  			<option value="x">X</option>
	  			<option value="y">Y</option>
	  			<option value="z">Z</option>
	     </select>
	  城市:<select name="cityId" id="cityLocale" >
	  		<option value="">-请选择-</option>
			<c:if test="${cityName ne null}">
	 			<option value="${condition.cityId}" selected="selected">${cityName}</option>
	 		</c:if>
	     </select>
                <input type="text" name="search" value="${search}"/>
                <input type="submit"  name="查询" value="查询"/>
                  <input type="button"  name="添加板块" value="添加板块"  onClick="javaScript:window.location.href ='/bbs/manager/addforum';"/>
            </div>


		<div class="mag">

			<div class="todo">

				<table>
					<thead>
						<tr>
							<th style=" width:10%; ">板块ID</th>
							<th style=" width:10%; ">板块名字</th>
							<th style=" width:10%; ">城市</th>
							<th style=" width:10%;">上级板块</th>
							<th style=" width:10%; ">板块属性</th>
							<th style=" width:10%;">显示顺序</th>
							<th style=" width:10%;">板块状态</th>
							
							<th style=" width:10%;">操作</th>
							<th style=" width:10%;">关闭时间</th>
						</tr>
					</thead>
					<tbody class="rasent">
						<c:forEach items="${newList}" var="list">
								<tr >
									<td style=" width:10%; text-align:left ">${list.id}</td>
									<td style=" width:10%; text-align:left ">${list.name}</td>
									<td style=" width:10%; text-align:left ">${list.cityName}</td>
									<td style=" width:10%; text-align:left ">${list.parent}</td>
									<td style=" width:10%; text-align:left ">${list.visibleRoleName}</td>
										<td style=" width:3%; text-align:left ">${list.displayOrder }</td>
									<td style=" width:3%; ">${list.turnoffName }</td>
									<td style=" width:10%; "><a href="/bbs/manager/selectByforumId?id=${list.id}">编辑</a>|
									<c:choose>
									<c:when test="${list.status==1}">
									<a href="javascript:deleteRent('${list.id}',0)" >关闭</a>
									</c:when>
									<c:otherwise>
			        						<a href="javascript:deleteRent('${list.id}',1)" >开启</a>
			        				</c:otherwise>
			        				</c:choose>
			        				</td>
									<td style=" width:10%; "><fmt:formatDate value="${list.turnofftime}" pattern="yyyy.MM.dd HH:mm"></fmt:formatDate></td>
								</tr>
							</c:forEach>
					</tbody>

				</table>

			</div>

		</div>


		<div class="page_and_btn" >
		
			<jsp:include page="/WEB-INF/snippets/page.jsp" />
		</div>
	
	</FORM>

<script type="text/javascript">
  $("#ch").val("${ch}");
  setCityInfo("${ch}","cityLocale");
  $("#cityLocale").val("${cityId}");
  
function deleteRent(id,turnoff){
	if(turnoff == "0"? confirm("确定关闭吗?"):confirm("确定开启吗?")){
		$.ajax({
			type : "post",
			async : false,
			url : "/bbs/manager/updateoff",
			dataType : "json",
			data : {"id" : id,
				"turnoff":turnoff
			},
			success : function(data) {
				if(data.result=="success"){
					alert("板块(ID:"+id+")更改");
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


 //-->
</script>